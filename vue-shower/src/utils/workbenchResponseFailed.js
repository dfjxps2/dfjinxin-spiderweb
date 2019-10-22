/**
 * Created by SongCQ on 2018/9/28.
 */
import { Message,MessageBox } from 'element-ui'

import router from "@/router"


const failedProcess = {}
let hasOpenLogoutMessageBox = false



failedProcess.checkWorkbenchResultOutMessage = function(response){
  return this.checkWorkbenchResult(response,false)
}

failedProcess.checkWorkbenchResult = function(response,withMessage){
  let res = response.data
  try{
    res = JSON.parse(response.data)
  }catch (e){

  }

  const $this = this

  if (res.result !== 'SUCCESS') {
    if (res.faild_reason === 'USER_NOT_LOGIN') {
      if(withMessage==null||withMessage==true){
        if(!hasOpenLogoutMessageBox){
          hasOpenLogoutMessageBox = true
          MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            // window.location = "/"
            router.push("/login");
            hasOpenLogoutMessageBox = false
          })
        }

      }else{
        return false
      }

    }else if(res.faild_reason === 'FORWARD_CAS'){
      if(withMessage==null||withMessage==true) {
        MessageBox.confirm('未登陆或登录已超时,请重新登录', '', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let forwardUrl = res.resultData
          window.location = forwardUrl
        })
        return
      }else{
        return res
      }

    }else if(res.faild_reason === 'PWD_EXPIRED'){
      router.push("/login");
      hasOpenLogoutMessageBox = false
    }else{
      console.error('parse error:'+(res.result_msg || res))
      Message.error(res.result_msg || res)
    }
    return false
  } else {
    return res.resultData
  }
}

export default failedProcess
