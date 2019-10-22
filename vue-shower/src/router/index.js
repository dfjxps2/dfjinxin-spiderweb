import Vue from 'vue'
import VueRouter from 'vue-router'

// 引入组件
import login from '@/models/login.vue'
import casLogin from '@/models/casLogin.vue'
import home from '@/models/home/homeBridging.vue'

// 要告诉 vue 使用 vueRouter
Vue.use(VueRouter)

const routes = [
  {
    name:"root",
    path: '/',
    // redirect: 'casLogin'
    redirect: 'login'
  },
  {
    path: `/login`,
    component: login
  },
  {
    path: `/casLogin`,
    component: casLogin
  },
  {
    path: `/mockLogin`,
    component: () => import("@/models/mockLogin")
  },
  {
    path: `/home`,
    component: home,
    // props: { menuAlign: 'top' },//left==>菜单在左侧 top==>菜单在上方
    props: { menuAlign: 'left' },//left==>菜单在左侧 top==>菜单在上方
    children: [
      {
        path: `/welcome`,
        component: () => import("@/models/home/welcome")
      },
      {
        name: 'auth',
        path: '/auth',
        component: () => import('@/models/sys/main'),
        children:[
          {
            name: 'menu',
            path: '/sys/menu',
            component: () => import('@/models/sys/menu/menuMain')
          },
          {
            name: 'user',
            path: '/sys/user',
            component: () => import('@/models/sys/user/userMain')
          },
          {
            name: 'role',
            path: '/sys/role',
            component: () => import('@/models/sys/role/roleMain')
          }
        ]
      },
      {
        name: 'jobManage',
        path: '/jobManage',
        component: () => import('@/models/spider/job/jobMain'),
        children:[
          {
            name: 'jobList',
            path: '/jobManage/jobList',
            component: () => import('@/models/spider/job/jobList')
          },
          {
            name: 'jobEdit',
            path: '/jobManage/jobEdit',
            component: () => import('@/models/spider/job/jobEdit')
          }
        ]
      },
      {
        name: 'jobConfigManage',
        path: '/jobConfigManage',
        component: () => import('@/models/spider/Jobconfig/jobConfigMain'),
        children:[
          {
            name: 'jobConfigList',
            path: '/jobConfigManage/jobConfigList',
            component: () => import('@/models/spider/jobconfig/jobConfigList')
          },
          {
            name: 'jobConfigEdit',
            path: '/jobConfigManage/jobConfigEdit',
            component: () => import('@/models/spider/jobconfig/jobConfigEdit')
          }
        ]
      },
      {
        name: 'jobScheduleManage',
        path: '/jobScheduleManage',
        component: () => import('@/models/spider/jobSchedule/jobScheduleMain'),
        children: [
          {
            name: 'jobScheduleList',
            path: '/jobScheduleManage/jobScheduleList',
            component: () => import('@/models/spider/jobSchedule/jobScheduleList')
          },
          {
            name: 'jobScheduleAdd',
            path: '/jobScheduleManage/jobScheduleAdd',
            component: () => import('@/models/spider/jobSchedule/jobScheduleAdd')
          },
          {
            name: 'jobScheduleEdit',
            path: '/jobScheduleManage/jobScheduleEdit',
            component: () => import('@/models/spider/jobSchedule/jobScheduleEdit')
          }
        ]
      },
      {
        name: 'jobStatus',
        path: '/jobStatus',
        component: () => import('@/models/spider/jobStatus/jobStatusMain'),
        children:[
          {
            name: 'jobStatusList',
            path: '/jobStatus/jobStatusList',
            component: () => import('@/models/spider/jobStatus/jobStatusList')
          },
          {
            name: 'jobPageList',
            path: '/jobPage/jobPageList',
            component: () => import('@/models/spider/jobPage/jobPageList')
          }
        ]
      },
      {
        name: 'resourceManage',
        path: '/resourceManage',
        component: () => import('@/models/spider/resource/resourceMain'),
        children:[
          {
            name: 'offlinePage',
            path: '/resourceManage/offlinePage',
            component: () => import('@/models/spider/resource/offlinePage')
          },
          {
            name: 'offlinePageDetail',
            path: '/resourceManage/offlinePageDetail',
            component: () => import('@/models/spider/resource/offlinePageDetail')
          }
        ]
      },
      {
        name: 'pageManage',
        path: '/pageManage',
        component: () => import('@/models/spider/page/pageMain'),
        children:[
          {
            name: 'pageList',
            path: '/pageManage/pageList',
            component: () => import('@/models/spider/page/pageList')
          },
          {
            name: 'pageEdit',
            path: '/pageManage/pageEdit',
            component: () => import('@/models/spider/page/pageEdit')
          }
        ]
      },
      {
        name: 'pageField',
        path: '/pageField',
        component: () => import('@/models/spider/pageField/pageFieldMain'),
        children:[
          {
            name: 'pageFieldList',
            path: '/pageField/pageFieldList',
            component: () => import('@/models/spider/pageField/pageFieldList')
          }
        ]
      },
      {
        name: 'pageLink',
        path: '/pageLink',
        component: () => import('@/models/spider/pageLink/pageLinkMain'),
        children:[
          {
            name: 'pageLinkList',
            path: '/pageLink/pageLinkList',
            component: () => import('@/models/spider/pageLink/pageLinkList')
          }
        ]
      },
      {
        name: 'dataField',
        path: '/dataField',
        component: () => import('@/models/spider/dataField/dataFieldMain'),
        children:[
          {
            name: 'dataFieldList',
            path: '/dataField/dataFieldList',
            component: () => import('@/models/spider/dataField/dataFieldList')
          },
          {
            name: 'dataFieldEdit',
            path: '/dataField/dataFieldEdit',
            component: () => import('@/models/spider/dataField/dataFieldEdit')
          },
          {
            name: 'dataFieldAdd',
            path: '/dataField/dataFieldAdd',
            component: () => import('@/models/spider/dataField/dataFieldAdd')
          },
          {
            name: 'dataFieldSql',
            path: '/dataField/dataFieldSql',
            component: () => import('@/models/spider/dataField/dataFieldSql')
          }
        ]
      },
      {
        name: 'crawlServer',
        path: '/crawlServer',
        component: () => import('@/models/spider/crawlServer/crawlServerMain'),
        children:[
          {
            name: 'crawlServerList',
            path: '/crawlServer/crawlServerList',
            component: () => import('@/models/spider/crawlServer/crawlServerList')
          },
          {
            name: 'crawlServerEdit',
            path: '/crawlServer/crawlServerEdit',
            component: () => import('@/models/spider/crawlServer/crawlServerEdit')
          }
        ]
      },
      {
        name: 'proxyServer',
        path: '/proxyServer',
        component: () => import('@/models/spider/proxyServer/proxyServerMain'),
        children:[
          {
            name: 'proxyServerList',
            path: '/proxyServer/proxyServerList',
            component: () => import('@/models/spider/proxyServer/proxyServerList')
          },
          {
            name: 'proxyServerEdit',
            path: '/proxyServer/proxyServerEdit',
            component: () => import('@/models/spider/proxyServer/proxyServerEdit')
          }
        ]
      },
    ]
  }
]

var router =  new VueRouter({
  routes
})
export default router
