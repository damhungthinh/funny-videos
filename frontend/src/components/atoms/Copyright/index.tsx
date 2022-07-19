import { memo } from 'react'

import { Space, Typography } from 'antd'
import { HeartFilled, MailOutlined } from '@ant-design/icons'
import { toStyledModuleNames } from '@utils/styledModuleName'
import style from './styles.module.scss'

const styledModule = toStyledModuleNames(style)

export const Copyright = memo(
  () => (
    <div className={styledModule`root`}>
      <Space>
        Copyright ©2021 All rights reserved | This system is made with
        <Typography.Text type="danger">
          <HeartFilled />
        </Typography.Text>
        by
        <Typography.Title level={5} style={{ margin: 0 }}>
          Edward.D
        </Typography.Title>
      </Space>
      <Space>
        Contact me via
        <Typography.Text type="danger">
          <MailOutlined />
        </Typography.Text>
        to
        <Typography.Text italic>damhungthinh@gmail.com</Typography.Text>
      </Space>
    </div>
  ),
  () => true
)
