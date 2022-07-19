import { FunctionComponent, useCallback } from 'react'
import { useDispatch, useSelector } from 'react-redux'

import { Button, Typography } from 'antd'
import { Dispatch, RootState } from '@redux/index'

import { toStyledModuleNames } from '@utils/styledModuleName'

import style from './styles.module.scss'
import { useNavigate } from 'react-router'

const styledModule = toStyledModuleNames(style)

export const InlineUserInfo: FunctionComponent = () => {
  const dispatch = useDispatch<Dispatch>()
  const navigate = useNavigate()

  const { user } = useSelector((state: RootState) => state.auth)

  const handleLogout = useCallback(() => dispatch.auth.logOut(), [dispatch])

  const handleGoToSharing = useCallback(() => navigate('/sharing'), [navigate])

  return (
    <div className={styledModule`root`}>
      <Typography.Text>Wellcome {user?.username}</Typography.Text>
      <Button type="primary" onClick={handleGoToSharing}>
        Share a movie
      </Button>
      <Button type="primary" ghost onClick={handleLogout}>
        Logout
      </Button>
    </div>
  )
}
