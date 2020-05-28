
<template>
  <el-container class="home" >

    <el-header>
      <el-row>
        <el-col :span="5" style="text-align: left">
            <img src="~@/assets/img_20200527163401.png" class="mr-3">
          <!--<span  class="header-tile">{{sysName}}</span>-->
        </el-col>
        <el-col :span="13">
          <el-menu :default-active="activeIndex" mode="horizontal" @select="select">
            <el-submenu v-if="menu.name" v-for="(menu, index) in menuList" :index="menu.id+''">
              <template slot="title">{{menu.name}}</template>
              <el-menu-item v-if="menu.children.length > 0" v-for="(item,ind) in menu.children" :index="item.id+''" @click="toLink(item.url)">{{item.name}}</el-menu-item>
            </el-submenu>
          </el-menu>
        </el-col>
        <el-col :span="6">
          <div style="color: #ffffff;line-height: 50px; text-align: right; font-size: 16px;">
            <el-menu  default-active="1"  background-color="#183078" text-color="#FFFFFF" style="float: right;" mode="horizontal">
              <el-submenu index="2">
                <template slot="title" >
                  <img style="width:25px;height:25px;background-color: #ffffff;border-radius: 50%;" src="~@/assets/avatar.svg"/>
                  {{loginUserInfo.user_name}}</template>
                <el-menu-item index="2-1">
                  <a style="color: #1f69c8;font-size: 13px;" disabled="disabled">{{loginUserInfo.user_name_cn}}</a>
                </el-menu-item>
                <el-menu-item index="2-2">
                  <a class="logout" @click="logout()">退出</a>
                </el-menu-item>
              </el-submenu>
            </el-menu>
          </div>
          <!--<div style="color: #ffffff; line-height: 50px; text-align: right; font-size: 16px;">
            &lt;!&ndash;<span>{{loginUserInfo.user_name_cn}}，欢迎您访问！</span>&ndash;&gt;
            <el-button><img src="~@/assets/avatar.svg" height="16px"></el-button>
            <span>{{loginUserInfo.user_name}}</span>

            <span>{{loginUserInfo.user_name_cn}}</span>
            <el-button type="text" class="quit" @click="logout()">退出</el-button>

            &lt;!&ndash;<el-button type="text" class="quit" @click="logout()"><i class="el-icon-switch-button"></i>退出</el-button>&ndash;&gt;
          </div>-->
        </el-col>
      </el-row>

    </el-header>
    <el-container style="background-color: #f5f4f9;">
      <el-main >
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>


<script>
  import WorkTopMenu from "@/models/menu/top-menu"
  import { MessageBox } from 'element-ui'

  export default {
    name:"MenuTopHome",
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
        activeIndex:''
      }
    },
    components: {
      WorkTopMenu
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
          window.location.href='http://10.217.17.116:8094/cas/logout'
          /*this.$http.post(process.env.BASE_API+"/sys/login/logout.do",{},{withCredentials: true}).then(res => {
            let responseData = res.data ;
            try{
              responseData = JSON.parse(responseData)
            }catch(e){}
            if (responseData.result == 'SUCCESS') {
              if(responseData.faild_reason === 'FORWARD_CAS'){
                let forwardUrl = responseData.resultData
                window.location = forwardUrl
              }else{
                $this.$router.push("/login");
              }
            }else{
                $this.Message.success("退出成功")
                $this.$router.push({'path':'/'})
            }
          })*/
        })


      },
      getActiveTab(){
        this.checkActiveTab(location.href, this.menuList)
      },
      checkActiveTab(url, menuList){
        for(let i = 0; i < menuList.length; i++){
          let m = menuList[i]
          if(m.url && url.indexOf(m.url)>-1){
            this.activeIndex = m.id+''
            return
          }
          if(m.children && m.children.length>0){
            this.checkActiveTab(url, m.children)
          }
        }
      },
      select(index){
        this.activeIndex = index;
      },
      toLink(url) {
        top.location.href = '#' + url
      }
    },
    watch:{
      menuList: function (val) {
        this.getActiveTab()
      },
    },
    mounted:function(){
      if(this.$route.fullPath=='/home'){
        this.$router.push({"path":"welcome"})
      }
    }
  };
</script>


<style rel="stylesheet/scss" lang="scss">
  /*.site-menu {
    float: left;
    //background-color: transparent;
    border-bottom: 0;
  }
  .site-menu--right {
      float: right;
    }
  .site-avatar {
      border-bottom: none !important;
      * {
        vertical-align: inherit;
      }
      .el-dropdown-link {
        > img {
          width: 18px;
          height: 20px;
          //margin-right: 5px;
         // border-radius: 100%;
          //vertical-align: middle;
        }
      }
    }*/

  .el-dropdown-link {
    cursor: pointer;
    color: #409EFF;
  }
  /*.ej-app-user{
    -webkit-box-flex: 0;
    flex: none;
    flex-grow:0;
    flex-shrink: 0;
    flex-basis: 0;
    display: flex;
    -webkit-box-align: center;
    align-items: center;
    margin-right: 0;
    cursor: pointer;
  }
  .app-usermenu{
    width: 26px;
    height: 26px;
  }
  .ej-app__avatar{
    width: 26px;
    height: 26px;
  }

  .mr-2{
    margin-right: 0.5rem;
  }
  .flex-one{
    -webkit-box-flex: 0;
    flex: none;
    flex-grow: 0;
    flex-shrink: 0;
    flex-basis: auto;
  }
   .rounded-full{
     border-radius: 9999px;
     border-top-left-radius: 9999px;
     border-top-right-radius: 9999px;
     border-bottom-left-radius: 9999px;
     border-bottom-right-radius: 9999px;

   }
   .bg-white{
     background-color: #FFFFFF;
   }*/
  .home{
    width:100%;
    height:100%;


    .work-menu-group{
      width:calc(100% - 300px);
      height:100%;
      float: left;
    }

    .personal-infos{
      width:280px;
      height:100%;
      float:left;
      color:white;
      text-align: left;
    }

    .mr-3{
      margin-right: 0.75rem
    }

    .menu{
      background-color: rgb(238, 241, 246);
    }

    .el-header {
      /*background-color: #2b5ca9;*/
      color: #333;
      height:50px !important;
      /*background-image: url("/static/image/header_backgroud.png");*/
      /*background-image: url("/nlp/static/image/header_backgroud.png");*/
      background-color: #183078;
    }

    .el-aside {
      color: #333;
    }

    .el-container{
      background-color: #ffffff;
    }

    .fa-icon {
      width:30px;
      height:20px;
      padding:18px 0 0 0;
    }

    .logout-font{
      float: right;
      font-size: 14px;
      line-height: 54px;
    }

    .login-user{
      float: right;
      margin:0 44px 0 0;
      width:178px;
      height:54px;
      line-height: 54px;
      font-size: 14px;
      color:#ffffff;
      overflow:hidden;text-overflow:ellipsis;white-space:nowrap;

    }

    .login-user-infomation{
      width:178px;
      height:54px;
      line-height: 54px;
      font-size: 14px;
      color:#ffffff;
      overflow:hidden;text-overflow:ellipsis;white-space:nowrap;
    }

    .logout-icon{
      float: right;
      cursor: pointer;
      width:58px;
    }

    .loginUserInfoRow{
      margin:0 !important;
    }
    .header-tile{
      line-height: 50px;
      color: #ffffff;
      font-size: 16px;
    }
    .mu-s{
      line-height: 50px;
      //color: #ffffff;
      font-size: 50px;
    }

    .el-menu{
      background-color:#183078;
    }
    .el-menu--popup{
      /*background-color:#1F2E4D;*/
      background-color:red;
    }
    .el-menu--horizontal>.el-submenu .el-submenu__title{
      font-size: 16px;
      height: 50px;
      line-height: 50px;
      color: #fff;
      background-color: #183078;
    }
    .el-menu--popup-bottom-start{
      margin-top: 0;
    }
    .el-menu--popup{
      border-radius:0;
    }
    .el-submenu{
      width: 120px;
    }
    .el-menu--horizontal>.el-submenu .el-submenu__icon-arrow{
      display: none;
    }
    .el-menu--horizontal>.el-submenu.is-active .el-submenu__title{
      color: #fff;
      background-color: rgba(255,255,255,0.1);
      /*border-bottom:none;*/
      border-bottom: 2px solid rgba(255,255,255,0.1);

    }
    .el-menu--horizontal .el-menu .el-menu-item, .el-menu--horizontal .el-menu .el-submenu__title {
      background-color: red;
      float: none;
      height: 36px;
      line-height: 36px;
      padding: 0 10px;
      color: #909399;
    }
    .el-menu-item{
      background-color: #1F2E4D;
    }
    /*.el-submenu.is-active .el-submenu__title{*/
    /*  border-bottom-color: rgba(255,255,255,0.1);*/
    /*}*/
    /*.el-submenu.is-active .el-submenu__title{*/
    /*  border-bottom-color: rgba(255,255,255,0.1);*/
    /*}*/
    .quit{
      width: 80px;
      height: 32px;
      letter-spacing: 1px;
      border-radius: 4px;
      font-size: 14px;
      color:#fff;
    }
  }
  .el-menu--horizontal .el-menu .el-menu-item,.el-menu{
    background:#183078;color:#fff;
  }
  .el-menu--horizontal .el-menu-item:not(.is-disabled):focus, .el-menu--horizontal .el-menu-item:not(.is-disabled):hover,.el-menu--horizontal .el-menu .el-menu-item.is-active{
    background:#477DE9;color:#fff;
  }

</style>
