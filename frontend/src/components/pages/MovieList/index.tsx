import { useCallback, useEffect } from 'react'
import { LikeOutlined } from '@ant-design/icons'

import {
  Card,
  Typography,
  List,
  Col,
  Row,
  Space,
  Statistic,
  notification,
  Pagination,
  BackTop
} from 'antd'
import { useDispatch, useSelector } from 'react-redux'
import { RootState } from '@redux/index'

import { toStyledModuleNames } from '@utils/styledModuleName'

import { Dispatch } from '@redux/index'

import style from './styles.module.scss'

const styledModule = toStyledModuleNames(style)

export const MovieList = () => {
  const dispatch = useDispatch<Dispatch>()

  const isLoading = useSelector((state: RootState) => state.loading.global)

  const { list, youtubeItems, error, pageable, conditions } = useSelector(
    (state: RootState) => state.movie
  )

  const handlePageChange = useCallback(
    (page: number, size: number) => {
      dispatch.movie.fetchList({ ...conditions, page: page - 1, size })
    },
    [dispatch, conditions]
  )

  const handlePageSizeChange = useCallback(
    (_: number, size: number) => {
      dispatch.movie.fetchList({ ...conditions, size })
    },
    [dispatch, conditions]
  )

  const handleShowTotal = (total: number, range: Array<number>) =>
    `${range[0]} ~ ${range[1]} of ${total} videos`

  useEffect(() => {
    dispatch.movie.fetchList(conditions)
    return () => {
      dispatch.movie.cleanError()
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
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
      <div className={styledModule`pagination`}>
        <Pagination
          hideOnSinglePage
          showSizeChanger
          showQuickJumper
          showTotal={handleShowTotal}
          current={pageable.page + 1}
          total={pageable.totalElements}
          onChange={handlePageChange}
          onShowSizeChange={handlePageSizeChange}
        />
        <BackTop />
      </div>
      <List
        itemLayout="vertical"
        size="large"
        loading={isLoading}
        dataSource={youtubeItems}
        renderItem={(item) => {
          const shared = list.find((it) => it.id === item.key)
          return (
            <List.Item key={item.key}>
              <Card>
                <Row gutter={16}>
                  <Col>
                    <div dangerouslySetInnerHTML={{ __html: item.player.embedHtml }} />
                  </Col>
                  <Col flex="1">
                    <a href={shared?.videoUrl || ''}>
                      <Typography.Title level={3}>{item.snippet.localized.title}</Typography.Title>
                    </a>
                    <Typography.Text>Shared by {shared?.sharedBy.username}</Typography.Text>
                    <br />
                    <Space>
                      <Statistic value={item.statistics.likeCount} prefix={<LikeOutlined />} />
                    </Space>
                    <br />
                    <Typography.Text strong>Description:</Typography.Text>
                    <Typography.Paragraph ellipsis={{ rows: 5 }}>
                      {item.snippet.localized.description}
                    </Typography.Paragraph>
                    <Typography.Text strong>Tags:</Typography.Text>
                    <Typography.Paragraph ellipsis={{ rows: 2 }}>
                      {item.snippet.tags.join()}
                    </Typography.Paragraph>
                  </Col>
                </Row>
              </Card>
            </List.Item>
          )
        }}
      />
    </div>
  )
}
