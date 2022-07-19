import { useEffect } from 'react'
import { useDispatch } from 'react-redux'
import { useSelector } from 'react-redux'

import { Outlet, useLocation, useNavigate } from 'react-router'
import { Dispatch, RootState } from '@redux/index'
import { AUTH_KEY } from '@enums/AppConst'

export const LoginRequired = () => {
  const location = useLocation()
  const dispatch = useDispatch<Dispatch>()
  const navigate = useNavigate()
  const { token } = useSelector((state: RootState) => state.auth)

  useEffect(() => {
    if (token) {
      if (localStorage.getItem(AUTH_KEY) !== token) {
        localStorage.setItem(AUTH_KEY, token)
      }
      dispatch.auth.verify()
    } else {
      navigate('/403', { replace: true })
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [location.pathname, token])

  return <Outlet />
}
