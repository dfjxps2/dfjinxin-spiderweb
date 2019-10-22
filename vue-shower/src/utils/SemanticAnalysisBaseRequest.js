import axios from 'axios'
import { Message,MessageBox } from 'element-ui'
import workbenchReponse from './workbenchResponseFailed'
import { getToken,setToken } from '@/utils/auth' // 验权
import Qs from 'qs'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api的base_url
  timeout: 120000, // 请求超时时间 120秒,
  // headers:{'Content-Type':'application/x-www-form-urlencoded'},
  withCredentials:true
})


service.defaults.headers = {
  // 'Accept-Charset':'utf-8'
};
// request拦截器
service.interceptors.request.use(config => {
  const requestData = config.data

  // if(config.data instanceof FormData){
  //   console.log("this is form data")
  //   //do nothing......
  // }else{
  //   config.transformRequest = [function (requestData) {//不适用request body
  //     debugger
  //     requestData = Qs.stringify(requestData);
  //     return requestData;
  //   }]
  // }

  return config
})

// respone拦截器
service.interceptors.response.use(
  response => {
    const checkResult = workbenchReponse.checkWorkbenchResult(response)
    return checkResult
  },
  error => {
    console.log('err' + error)// for debug
    Message.error("系统异常："+error.message)
    return Promise.reject(error)
  }
)

export default service
