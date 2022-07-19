import { Button, Card, Input, Typography, Form, Spin } from 'antd'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '@redux/index'

import { toStyledModuleNames } from '@utils/styledModuleName'

import { Dispatch } from '@redux/index'

import style from './styles.module.scss'

const styledModule = toStyledModuleNames(style)

type ShareMovieForm = {
  url: String
}

export const ShareMovie = () => {
  const [form] = Form.useForm<ShareMovieForm>()
  const dispatch = useDispatch<Dispatch>()
  const isLoading = useSelector((state: RootState) => state.loading.global)

  const { error } = useSelector((state: RootState) => state.movie)

  const handleFormSubmit = (value: ShareMovieForm) => dispatch.movie.share(value)

  return (
    <div className={styledModule`root`}>
      <Card className={styledModule`wrapper`}>
        <Spin spinning={isLoading}>
          <Typography.Title className={styledModule`title`} level={2}>
            Share a Youtube movie
          </Typography.Title>
          <Form
            className={styledModule`form`}
            layout="horizontal"
            size="middle"
            form={form}
            onFinish={handleFormSubmit}>
            <Form.Item
              name="url"
              label="Youtube URL"
              rules={[
                { required: true, message: 'Please input an URL' },
                { type: 'url', message: 'Please input an correct URL' }
              ]}>
              <Input type="text" placeholder="A Youtube URL" name="url" disabled={isLoading} />
            </Form.Item>
            <div className={styledModule`form-action`}>
              <Button type="primary" htmlType="submit" disabled={isLoading}>
                Share
              </Button>
            </div>
          </Form>
        </Spin>
      </Card>
      <Typography.Paragraph type="danger" strong>
        {error?.reason}
      </Typography.Paragraph>
    </div>
  )
}
