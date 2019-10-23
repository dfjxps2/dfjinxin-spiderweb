<template>

  <div class="login-container" >
    <vue-particle-line>
      <div v-bind:class="loadingLogin" >
        <el-form v-if="!resetPwd" class="login-form" autoComplete="on" ref="loginForm" label-position="left">
          <h3 class="title">互联网采集</h3>
          <!--<h3 class="title">欢迎！</h3>-->
          <el-form-item  prop="user_name">
            <el-input  name="user_name" type="text" v-model="loginForm.user_name" autoComplete="on"
                       placeholder="请输入用户名"/>
          </el-form-item>
          <el-form-item  prop="user_pwd">
            <el-input placeholder="请输入密码" v-model="loginForm.user_pwd" show-password></el-input>
            <!--<span class="show-pwd" @click="showPwd"><svg-icon icon-class="eye" /></span>-->
          </el-form-item>

          <el-form-item>
            <el-button type="primary" style="width:100%;" :loading="loading" @click.native.prevent="handleLogin">
              <!--<el-button type="primary" style="width:100%;" :loading="loading">-->
              登入系统
            </el-button>
          </el-form-item>
        </el-form>

        <el-form v-if="resetPwd" class="login-form" autoComplete="on" ref="loginForm" label-position="left">
          <h3 class="title">您的密码已过期,请输入您的密码</h3>
          <!--<h3 class="title">欢迎！</h3>-->
          <el-form-item  prop="user_name">
            <el-input  name="user_name" type="text" v-model="changePwdForm.user_name" autoComplete="on"
                       placeholder="请输入用户名"/>
          </el-form-item>
          <el-form-item  prop="user_pwd">
            <el-input placeholder="请输入密码" v-model="changePwdForm.user_pwd" show-password></el-input>
            <!--<span class="show-pwd" @click="showPwd"><svg-icon icon-class="eye" /></span>-->
          </el-form-item>
          <el-form-item  prop="user_pwd">
            <el-input placeholder="请确认密码" v-model="changePwdForm.validate_user_pwd" show-password></el-input>
            <!--<span class="show-pwd" @click="showPwd"><svg-icon icon-class="eye" /></span>-->
          </el-form-item>


          <el-form-item>
            <el-button type="primary" style="width:100%;" :loading="loading" @click="changePwd">
              <!--<el-button type="primary" style="width:100%;" :loading="loading">-->
              确定
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </vue-particle-line>


  </div>
</template>

<script>
  import workbenchReponse from '@/utils/workbenchResponseFailed'
  import { Loading } from 'element-ui';

  export default {
  name: 'login',
  data() {
    return {
        loginForm: {
            user_name: '',
            user_pwd: ''
        },
        changePwdForm: {
          user_name: '',
          user_pwd: '',
          validate_user_pwd: ''
        },
        resetPwd:false,
        loading: false,
        loadingLogin:{
          'loading-login':true
        },
        pwdType: 'user_pwd'
    }
  },
  methods: {
    showPwd() {
        if (this.pwdType === 'user_pwd') {
            this.pwdType = ''
        } else {
            this.pwdType = 'user_pwd'
        }
    },
    handleLogin() {
      const $this = this
      try{
        this.BaseRequest({
          url:"sys/login/doLogin.do",
          method:"get",
          params:this.loginForm
        })
        .then(response=>{
            if('LOGIN_SUCCESS'==response){
              $this.forwardToHome()
            }else if('PWD_EXPIRED'==response){
              $this.resetPwd = true
              this.changePwdForm.user_name = this.loginForm.user_name
            }
        })
        .catch(errorMsg=>{
          //console.log("response ......")
        });
      }catch(e){
        //console.log("catch ......"+e)
      }
    },
    changePwd(){
      if(this.changePwdForm&&this.changePwdForm.user_name&&this.changePwdForm.user_pwd&&this.changePwdForm.validate_user_pwd){
        if(this.changePwdForm.user_pwd!=this.changePwdForm.validate_user_pwd)
        {
          this.Message.error("两次输入密码不一致")
          this.changePwdForm.user_pwd = ""
          this.changePwdForm.validate_user_pwd = ""
        }else{
          this.BaseRequest({
            url:"sys/user/changePwd",
            method:"post",
            data:this.changePwdForm
          })
          .then(response=>{
            if('SUCCESS'==response){
              this.forwardToHome()
            }else{
              if(response.faild_reason){
                this.Message.error(response.faild_reason)

              }else if(response.result_msg){
                this.Message.error(response.result_msg)

              }else{
                this.Message.error("登陆失败")
              }
            }
          })
          .catch(errorMsg=>{
            //console.log("response ......")
          });
        }
      }else{
        this.Message.error("请输入用户名以及密码")
      }


        // sys/user/changePwd
    },
    forwardToHome:function(){
      this.$router.push({'path':'/home'})
    }
  },
  mounted:function () {
    //检查用户是否已经登录
    let loadingInstance = Loading.service({ fullscreen: true,background:'rgba(0, 0, 0, 0.7)',	text:'加载中........' });
    const $this = this
    this.$http.post(process.env.BASE_API+"/sys/user/userMenuList.do",{},{withCredentials: true})
      .then(response => {
        let res = response.data
        try{
          res = JSON.parse(response.data)
        }catch(e){
          //console.log(e)
        }

        if (res.result !== 'SUCCESS') {
          if (res.faild_reason === 'USER_NOT_LOGIN') {
            freeLoading()
          }else if(res.faild_reason === 'FORWARD_CAS'){
            let forwardUrl = res.resultData
            window.location = forwardUrl
          }else{
            freeLoading()
          }
        }else{
          if(res.resultData=='PWD_EXPIRED'){
            //console.log("重定向到修改密码页面")
          }else{
            freeLoading()
            this.forwardToHome()
          }
        }
      })
      .catch(error=>{
        $this.Message.error("获取用户登录状态过程中出现异常："+error)
        freeLoading()
      })

    function freeLoading(){
      if(loadingInstance.visible){
        loadingInstance.close()
        $this.loadingLogin['loading-login']=false
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
    $bg: #2d3a4b;
    $light_gray: #eee;

    /* reset element-ui css */
    .login-container {
        .el-input {
            display: inline-block;
            height: 47px;
            width: 85%;
            input {
                background: transparent;
                border: 0px;
                -webkit-appearance: none;
                border-radius: 0px;
                padding: 12px 5px 12px 15px;
                color: $light_gray;
                height: 47px;
                &:-webkit-autofill {
                    -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
                    -webkit-text-fill-color: #fff !important;
                }
            }
        }
        .el-form-item {
            border: 1px solid rgba(255, 255, 255, 0.1);
            background: rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            color: #454545;
        }
    }

</style>

<style rel="stylesheet/scss" lang="scss" scoped>
    $bg: #2d3a4b;
    $dark_gray: #889aa4;
    $light_gray: #eee;
    .login-container {
        position: fixed;
        height: 100%;
        width: 100%;
        background-color: $bg;
        .login-form {
          position: absolute;
          z-index: 10086;
          left: 0;
          right: 0;
          width: 300px;
          padding: 35px 35px 15px 35px;
          margin: 120px auto;
        }
        .tips {
            font-size: 14px;
            color: #fff;
            margin-bottom: 10px;
            span {
                &:first-of-type {
                    margin-right: 16px;
                }
            }
        }
        .svg-container {
            padding: 6px 5px 6px 15px;
            color: $dark_gray;
            vertical-align: middle;
            width: 30px;
            display: inline-block;
            &_login {
                font-size: 20px;
            }
        }
        .title {
            font-size: 20px;
            font-weight: 100;
            color: $light_gray;
            margin: 0px auto 40px auto;
            text-align: center;
            font-weight: bold;
        }
        .show-pwd {
            position: absolute;
            right: 10px;
            top: 7px;
            font-size: 16px;
            color: $dark_gray;
            cursor: pointer;
            user-select: none;
        }
    }
  .loading-login{
    visibility:hidden;
  }
</style>
