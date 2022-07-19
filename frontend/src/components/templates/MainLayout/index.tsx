import { useEffect } from 'react'
import { useLocation, Outlet } from 'react-router-dom'
import { useSelector } from 'react-redux'

import { Layout } from 'antd'
import { Logo } from '@components/atoms/Logo'

import { Copyright } from '@components/atoms/Copyright'
import { InlineUserInfo } from '@components/organisms/InlineUserInfo'
import { InlineLoginForm } from '@components/organisms/InlineLoginForm'
import { RootState } from 'src/redux'

import { toStyledModuleNames } from '@utils/styledModuleName'
import style from './styles.module.scss'

const styledModule = toStyledModuleNames(style)

const { Content, Footer, Header } = Layout

export const MainLayout = () => {
  const location = useLocation()
  const { user } = useSelector((state: RootState) => state.auth)

  useEffect(() => {
    // Scroll to top
    window.scrollTo(0, 0)
  }, [location.pathname])
  return (
    <Layout className={styledModule`root`}>
      <Header className={styledModule`header`}>
        <Logo />
        {user?.username ? <InlineUserInfo /> : <InlineLoginForm />}
      </Header>
      <Content className={styledModule`container`}>
        <Outlet />
      </Content>
      <Footer className={styledModule`footer`}>
        <Copyright />
      </Footer>
    </Layout>
  )
}
