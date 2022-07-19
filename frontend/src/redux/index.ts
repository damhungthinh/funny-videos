import { init, Models, RematchDispatch, RematchRootState } from '@rematch/core'
import loadingPlugin, { ExtraModelsFromLoading } from '@rematch/loading'
import immerPlugin from '@rematch/immer'
import { auth } from './auth'
import { movie } from './movie'

export interface RootModel extends Models<RootModel> {
  auth: typeof auth
  movie: typeof movie
}

const models: RootModel = { auth, movie }

type FullModel = ExtraModelsFromLoading<RootModel>

export const store = init<RootModel, FullModel>({
  models,
  plugins: [loadingPlugin({ type: 'boolean' }), immerPlugin()]
})

export type Store = typeof store
export type Dispatch = RematchDispatch<RootModel>
export type RootState = RematchRootState<RootModel, FullModel>
