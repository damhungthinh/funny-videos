import { FunctionComponent, useCallback, useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux'

import { Button, Input, Form, Spin, notification } from 'antd'
import { UserOutlined, LockOutlined } from '@ant-design/icons'
import { Dispatch, RootState } from '@redux/index'

import { toStyledModuleNames } from '@utils/styledModuleName'
import style from './styles.module.scss'

const styledModule = toStyledModuleNames(style)

type LoginProps = {
  username: string
  password: string
  remember: boolean
}

export const InlineLoginForm: FunctionComponent = () => {
  const [form] = Form.useForm<LoginProps>()
  const dispatch = useDispatch<Dispatch>()

  const { error } = useSelector((state: RootState) => state.auth)

  const isLoading = useSelector((state: RootState) => state.loading.global)

  const handleFormSubmit = useCallback(
    (value: LoginProps) => dispatch.auth.login(value),
    [dispatch]
  )

  useEffect(() => {
    return () => {
      dispatch.auth.clearError()
    }
  }, [dispatch])

  useEffect(() => {
    if (error) {
      notification.error({
        message: error.code,
        description: error.reason,
        placement: 'bottomRight'
      })
    }
  }, [error])

  return (
    <div className={styledModule`root`}>
      <Spin spinning={isLoading}>
        <Form
          className={styledModule`form`}
          layout="inline"
          size="middle"
          form={form}
          onFinish={handleFormSubmit}>
          <Form.Item
            name="username"
            label=""
            rules={[
              {
                required: true,
                message: ''
              }
            ]}>
            <Input
              type="text"
              prefix={<UserOutlined className={styledModule`form-item-icon`} />}
              placeholder="Your username"
              name="username"
              disabled={isLoading}
            />
          </Form.Item>
          <Form.Item name="password" label="" rules={[{ required: true, message: '' }]}>
            <Input.Password
              prefix={<LockOutlined className={styledModule`form-item-icon`} />}
              placeholder="Your password"
              name="password"
              disabled={isLoading}
            />
          </Form.Item>
          <Button type="primary" htmlType="submit" disabled={isLoading}>
            Login / Register
          </Button>
        </Form>
      </Spin>
    </div>
  )
}
