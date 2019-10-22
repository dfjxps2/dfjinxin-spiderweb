import axios from 'axios'
import { Message } from 'element-ui'
import workbenchReponse from './workbenchResponseFailed'
import Qs from 'qs'


// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API+"/nlp/", // api的base_url
  // baseURL: "http://192.168.1.116:8888/nlp/", // api的base_url
  // headers:{'Content-Type':'application/x-www-form-urlencoded'},
  timeout: 600000, // 请求超时时间 20秒,
  withCredentials:true
})

const mockOptions = true;//是否绕过OPTIONS请求 true:绕过 false:开启options

service.defaults.headers = {
  'Accept-Charset':'utf-8'
}

// request拦截器
service.interceptors.request.use(config => {
  const requestData = config.data

  if(config.data instanceof FormData){
      //do nothing......
  }else{
    config.transformRequest = [function (requestData) {//不适用request body
      requestData = Qs.stringify(requestData);
      return requestData;
    }]
  }




  return config
})

// respone拦截器
service.interceptors.response.use(
  response => {
    const checkResult = workbenchReponse.checkWorkbenchResult(response)
    if(checkResult){
      const status = checkResult.status
      if(status!=null&&status!='0'){
        Message.error(checkResult.message)
        return
      }else{
        return checkResult
      }
    }
  },
  error => {
    console.log('err' + error)// for debug
    Message.error("系统异常："+error.message)
    return Promise.reject(error)
  }
)

export default service
