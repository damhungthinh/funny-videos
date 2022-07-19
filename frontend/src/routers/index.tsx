import { memo } from 'react'

import { BrowserRouter, Routes, Route } from 'react-router-dom'
import NotFound from '@components/pages/404'
import Forbbiden from '@components/pages/403'
import { MainLayout } from '@components/templates/MainLayout'
import { ShareMovie } from '@components/pages/ShareMovie'
import { MovieList } from '@components/pages/MovieList'
import { LoginRequired } from '@components/templates/LoginRequired'

const ApplicationRouters = () => (
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<MainLayout />}>
        <Route index element={<MovieList />} />
        <Route path="sharing" element={<LoginRequired />} >
          <Route index element={<ShareMovie />} />
        </Route>
      </Route>
      <Route path="/403" element={<Forbbiden />} />
      <Route path="*" element={<NotFound />} />
    </Routes>
  </BrowserRouter>
)

export default memo(ApplicationRouters, () => true)
