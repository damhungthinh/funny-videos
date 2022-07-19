import { memo, useCallback } from 'react'
import { useNavigate, useNavigationType } from 'react-router-dom'

import { Button, Result, Space } from 'antd'
import { HomeFilled } from '@ant-design/icons'

import { PUSH_STATE } from '@enums/AppConst'

import { toStyledModuleNames } from '@utils/styledModuleName'
import styles from './styles.module.scss'


const styledModule = toStyledModuleNames(styles)

const ForbiddenPage = () => {
  const navigate = useNavigate()
  const navigationType = useNavigationType()

  // Only re-created function when navigate changed
  const handleBackToHome = useCallback(() => { navigate('/') }, [navigate])

  // Only re-created function when navigate changed
  const handleGoBack = useCallback(() => { navigate(-1) }, [navigate])

  return (
    <Result
      className={styledModule`root`}
      status={403}
      title='Permission Denied.'
      subTitle="Oops, you DO NOT have permisison to access this page..."
      extra={
        <Space>
          <Button type='primary' onClick={handleBackToHome}>
            Back to <HomeFilled />
          </Button>
          {navigationType === PUSH_STATE && (
            <Button type='link' onClick={handleGoBack}>
              Go Back
            </Button>
          )}
        </Space>
      }
    />
  )
}

// Prevent renders
export default memo(ForbiddenPage, () => true)
