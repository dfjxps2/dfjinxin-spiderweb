
<template>
    <div class="home" >
      <div class="container-header">
        <el-header style="text-align: right; font-size: 12px">
          <div class="work-menu-group">
            <WorkLeftMenuGroup :sysName="sysName" ></WorkLeftMenuGroup>
          </div>
          <div class="header-options">
            <!--<icon name="home"></icon>-->

            <div @click="logout" class="icon-mount">
              <el-tooltip class="item" effect="dark" content="退出系统" placement="bottom-end">
                <div class="logout-icon"></div>
              </el-tooltip>
            </div>
            <div @click="gotoWelcome" class="icon-mount">
              <el-tooltip class="item" effect="dark" content="主页" placement="bottom-end">
                <div class="home-icon"></div>
              </el-tooltip>
            </div>
            <!--<div @click="gotoWelcome" class="icon-mount">-->
              <!--<el-tooltip class="item" effect="dark" content="主页" placement="bottom-end">-->
                <!--<icon name="homeallfill"></icon>-->
              <!--</el-tooltip>-->
            <!--</div>-->
            <div v-if="!fullScreen" @click="changeScreen" class="icon-mount">
              <el-tooltip class="item" effect="dark" content="全屏" placement="bottom-end">
                <div class="fullscreen-icon"></div>
              </el-tooltip>
            </div>
            <div v-if="fullScreen" @click="changeScreen" class="icon-mount">
              <el-tooltip class="item" effect="dark" content="退出全屏" placement="bottom-end">
                <div class="fullscreen-icon-quit"></div>
              </el-tooltip>
            </div>
            <div @click="showModalPage=true" class="icon-mount">
              <el-tooltip class="item" effect="dark" content="修改密码" placement="bottom-end">
                <div class="changepwd-icon"></div>
              </el-tooltip>
            </div>
            <div class="icon-mount">
              <div class="login-user">当前登陆用户:{{loginUserInfo.user_name_cn}}</div>
              <div class="login-user-img"></div>
            </div>

          </div>
        </el-header>
      </div>


      <div class="container-root">
        <div class="container-root-menu">
          <el-menu :collapse="isCollapse" class="menu-style">
            <el-menu-item v-on:click.native="collapseMenu">
              <div class="sprit-menu" v-bind:class="{'coll-menu':!isCollapse,'open-menu':isCollapse}"></div><span class="title-style">收起菜单</span>
            </el-menu-item>
            <WorkLeftMenu v-for="(menuObj,menuIndex) in menuList"
                          :key="menuObj.id"
                          :menuData="menuObj"
                          :menuIndex="menuIndex+1" :spritBaseClass="'sprit-menu'+(menuIndex+1)"></WorkLeftMenu>
          </el-menu>
        </div>

        <div :class="isCollapse?'container-root-context-collapse':'container-root-context'">
          <router-view></router-view>
        </div>

        <el-dialog title="修改密码" :visible.sync="showModalPage" >
          <el-form  style="margin:0 100px 0 100px" class="modal-form" label-position="left" label-width="100px" :model="changePwdForm">
            <!--<h3 class="title">欢迎！</h3>-->
            <el-form-item size="mini" label="原密码" >
              <el-input  name="old_user_pwd" type="text" v-model="changePwdForm.old_user_pwd" show-password  placeholder="请输入原密码"/>
            </el-form-item>
            <el-form-item size="mini" label="新密码" >
              <el-input placeholder="请输入密码" v-model="changePwdForm.user_pwd" show-password></el-input>
              <!--<span class="show-pwd" @click="showPwd"><svg-icon icon-class="eye" /></span>-->
            </el-form-item>
            <el-form-item size="mini" label="确认密码" >
              <el-input placeholder="请确认密码" v-model="changePwdForm.validate_user_pwd" show-password></el-input>
              <!--<span class="show-pwd" @click="showPwd"><svg-icon icon-class="eye" /></span>-->
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="showModalPage=false">取 消</el-button>
            <el-button type="primary" @click="changePwd()">确 定</el-button>
          </div>
      </el-dialog>


      </div>
    </div>
</template>

<script>
  import WorkLeftMenuGroup from "@/models/menu/left-menu-group"
  import WorkLeftMenu from "@/models/menu/left-menu"
  import { MessageBox } from 'element-ui'

  export default {
    name:"MenuLeftHome",
    props:{
      menuList:{
        type:Array
      },
      sysName:{
        type:String
      },
      loginUserInfo:{
        type:Object
      }
    },
    data() {
      return {
        isCollapse:false,
        fullScreen:false,
        showModalPage:false,
        changePwdForm: {
          old_user_pwd: '',
          user_pwd: '',
          validate_user_pwd: ''
        }
      }
    },
    components: {
      WorkLeftMenuGroup,
      WorkLeftMenu
    },
    methods:{
      mainPageChange:function(pageName){
        //console.log(pageName);
      },
      logout:function(commound){
        MessageBox.confirm('退出系统将丢失当前未保存的相关操作，确定退出？', '提示', {
          confirmButtonText: '退出',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const $this = this

          this.$http.post(process.env.BASE_API+"/sys/login/logout.do",{},{withCredentials: true}).then(res => {
            let responseData = res.data ;
            try{
              responseData = JSON.parse(responseData)
            }catch(e){}
            if (responseData.result == 'SUCCESS') {
              if(responseData.faild_reason === 'FORWARD_CAS'){
                let forwardUrl = responseData.resultData
                window.location = forwardUrl
              }else{
                $this.Message.success("登出成功")
                $this.$router.push({'path':'/'})
              }
            }else{
              $this.Message.error(responseData.result)

            }
          })
        })


      },
      gotoWelcome(){
        this.$router.push({'path':'/'})
      },
      changePwd(){
        if(this.changePwdForm.old_user_pwd&&this.changePwdForm.old_user_pwd&&this.changePwdForm.old_user_pwd){
        }else{
          this.Message.error("请输入密码信息")
          return
        }
        if(this.changePwdForm.user_pwd!=this.changePwdForm.validate_user_pwd){
          this.Message.error("两次输入密码不一致")
          this.changePwdForm.user_pwd = ""
          this.changePwdForm.validate_user_pwd = ""
        }else{
          this.BaseRequest({
            url:"sys/user/currUserPwdChange",
            method:"post",
            data:this.changePwdForm
          })
            .then(response=>{
              if('SUCCESS'==response){
                this.Message.success("密码修改成功")
                this.showModalPage = false
              }else{
                this.Message.error(response)
              }
            })
            .catch(errorMsg=>{
              //console.log("response ......")
            });
        }
      },
      changeScreen(){
        let isFullScreen = document.fullscreenElement || document.mozFullScreenElement||document.webkitFullscreenElement

        this.fullScreen = !isFullScreen
        const element = document.documentElement
        if(this.fullScreen){//全屏
          if (element.requestFullscreen) {
            element.requestFullscreen();
          } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
          } else if (element.webkitRequestFullscreen) {
            element.webkitRequestFullscreen();
          } else if (element.msRequestFullscreen) {
            element.msRequestFullscreen();
          }
        }else{
          if (document.exitFullscreen) {
            document.exitFullscreen();
          } else if (document.mozCancelFullScreen) {
            document.mozCancelFullScreen();
          } else if (document.webkitExitFullscreen) {
            document.webkitExitFullscreen();
          }
        }
      },
      exitHandler(){
        //console.log("ehere")
        if (!document.webkitIsFullScreen &&!document.mozFullScreen &&!document.msFullscreenElement) {
          this.fullScreen = false
          //console.log("mmmmmmm")
        }else{
          //console.log("yyyyy")
        }
      },
      collapseMenu(){
        //console.log("collapseMenu is running.....")
        this.isCollapse = !this.isCollapse
      }
    },
    mounted:function(){
      if(this.$route.fullPath=='/home'){
        this.$router.push({"path":"welcome"})
      }
      const $this = this
      document.onkeydown = function(e) {
        let key = window.event.keyCode;
        if (key== 122 ) {//屏蔽F11快捷键
          return false
        }
      }
      if (document.addEventListener) {
        document.addEventListener('webkitfullscreenchange', this.exitHandler, false);
        document.addEventListener('mozfullscreenchange', this.exitHandler, false);
        document.addEventListener('fullscreenchange', this.exitHandler, false);
        document.addEventListener('MSFullscreenChange', this.exitHandler, false);
      }
    }
  };
</script>


<style lang="css">
  .menu-style:not(.el-menu--collapse){
    background-color:#3E3958;
    text-color:"#ffffff";
    /*active-text-color:"#3f8aff";*/
    height:95%;
    width: 183px;
  }

  .el-menu--collapse:not(.el-menu--collapse){
    background-color:#3E3958;
    height:95%;
  }

  .menu-title-cust{
    float: left;
    height:28px;
    color:#b1b4c3;
    margin:0 0 0 10px;
  }
</style>

<style rel="stylesheet/scss" lang="scss" scoped>

  .menu{
    background-color: #2e3d50;
    height:100%;
  }

  .el-header{
    height:100% !important;
  }

  .work-left-menu-header{
    width:calc(100% - 300px);
    height:100%;
    float: left;
    text-align: left;
    font-size:20px;
  }

  .el-menu{
    background-color: #3E3958 !important;
    text-align: left;
    border:0px solid black !important;
    padding:0 !important;
  }

  .el-aside {
    color: #333;
  }

  .el-menu-item,.el-menu-item a{
    text-decoration:none;
    color:#ffffff;
  }

  .home{
    width:100%;
    height:100%;
    /*padding:20px;*/
    background-color: #3CD2E6 !important;
  }

  .work-menu-group{
    width:50%;
    height:100%;
    float: left;
  }

  .header-options{
    width:50%;
    height:100%;
    float:right;
    color:black;
    text-align: right;
  }

  .menu{
    background-color: rgb(238, 241, 246);
  }

  .container-header {
    width:100%;
    color: black;
    background: -webkit-linear-gradient(left, #1a78f3, #4892f3); /* Safari 5.1 - 6.0 */
    background: -o-linear-gradient(right, #1a78f3, #4892f3); /* Opera 11.1 - 12.0 */
    background: -moz-linear-gradient(right, #1a78f3, #4892f3); /* Firefox 3.6 - 15 */
    background: linear-gradient(to right, #1a78f3, #4892f3); /* 标准的语法 */
    height:45px !important;
    float:left;
  }

  .container-root{
    width:100%;
    height:calc(100% - 48px);
    background-color: #ffffff;
    float:left;
  }

  .container-root-menu{
    height:100%;
    float:left;
    background-color: #3E3958;
    overflow: auto;
  }

  .container-root-context-collapse{
    width:calc(100% - 65px);
    height:100%;
    float:left;
    /*background-color: #F6F6F6;*/
  }

  .container-root-context{
    width:calc(100% - 201px);
    height:100%;
    float:left;
    /*background-color: #F6F6F6;*/
  }

  .el-aside {
    color: #333;
  }

  .el-container{
    background-color: #ffffff;
  }

  .personal-name{
    color:white;
    width:120px;
    height:100%;
    float: right;
    padding:10px 0 0 0 ;
    text-align: left;
  }

  .icon-mount{
    float: right;
    height:calc(100% - 16px);
    margin:8px 0 8px 0;
    padding:2px 15px 0 15px;
    border-left: 1px dashed #1f69c8;
    text-align: center;
  }

  .icon-mount:last-child{
    border: 0 solid black;
  }

  .icon-mount:not(:last-child):hover{
    /*background-color: #8D0063;*/
    /*box-shadow: 0px 5px 20px black;*/
    cursor: pointer;
  }

  .login-user{
    font-size: 13px;
    /*font-weight: bold;*/
    color:white;
    float: right;
    line-height: 25px;
    margin:0 0 0 10px;
  }

  .fa-icon {
    width:25px;
    height:25px;
    margin:0px 40px 0 15px;
  }

  .fa-icon-changescreen{
    width:25px;
    height:25px;
    margin:0px 15px 0 15px;
  }

  .login-user-name{
    width:120px;
    height:25px;
    line-height: 25px;
    border-radius: 15px;
    background-color: #00f2a4;
    margin-top:13px;
    color:white;
    font-size: 18px;
    text-align: center;
    font-weight: bolder;
    padding:0px;
    cursor: pointer;
    float:right;
  }

  .loginUserInfoRow{
    margin:0 !important;
  }
  .title-style-hide{
    color: #b1b4c3 !important;
    padding-top:2px;
  }

</style>
