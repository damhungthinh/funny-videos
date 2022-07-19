import { Movie, MovieConditions } from '@entity/Movie'
import { Pageable } from '@entity/Pageable'
import { createModel } from '@rematch/core'
import { apiErrorHandler, CustomError } from '@utils/apiErrorHandler'
import { fetch, fetchExternal } from '@utils/axiosFetch'
import { RootModel } from '..'

type MoveState = {
  list: Array<Movie>
  youtubeItems: Array<any>
  conditions: MovieConditions
  pageable: Pageable
  error?: CustomError
}

export const movie = createModel<RootModel>()({
  state: {
    list: [],
    youtubeItems: [],
    conditions: {
      page: 0,
      size: 20
    },
    pageable: {
      numberOfElements: 0,
      totalElements: 0,
      totalPages: 0,
      size: 20,
      page: 0,
      first: true,
      last: true
    }
  } as MoveState,
  reducers: {
    fetchListSucceed(state, payload) {
      state.list = payload.list
      state.pageable = payload.pageable
      state.youtubeItems = payload.youtubeItems
      state.conditions = payload.conditions
    },
    shareVideoSucceed(state, payload) {
      state.list = payload.list
      state.pageable = payload.pageable
    },
    handleFailed(state, { data, status }) {
      const apiError = apiErrorHandler({ status, ...data })
      state.error = { ...apiError }
    },
    cleanError(state) {
      state.error = undefined
    }
  },
  effects: (dispatch) => ({
    // handle state changes with impure functions.
    // use async/await for async actions
    async share(request: { url: String }) {
      await fetch({
        url: 'videos/shared',
        method: 'POST',
        data: request
      })
        .then(({ data }) => dispatch.movie.fetchListSucceed({ data }))
        .catch((reason) => dispatch.movie.handleFailed(reason))
    },
    async fetchList(conditions) {
      //    Verify login token every time page reload
      await fetch({
        url: 'videos',
        method: 'GET',
        params: conditions
      })
        .then(async ({ data }) => {
          const { content, pageable } = data
          const ids = content.map((it: Movie) => it.videoUrl.split('/').pop())
          if (ids.length) {
            await fetchExternal({
              baseURL: 'https://www.googleapis.com/youtube/v3/videos',
              params: {
                part: 'snippet,statistics,player',
                id: ids.join(','),
                key: process.env.REACT_APP_GG_API_KEY
              }
            })
              .then(({ data }) => {
                dispatch.movie.fetchListSucceed({
                  youtubeItems: data.items,
                  list: content,
                  pageable,
                  conditions
                })
              })
              .catch((reason) => {
                dispatch.movie.handleFailed({
                  status: 500,
                  reason: "Can't connect to Youtube, please try again later"
                })
              })
          }
        })
        .catch((reason) => {
          console.log('reason', reason)
          dispatch.movie.handleFailed(reason)
        })
    }
  })
})
