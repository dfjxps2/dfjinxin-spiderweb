<template>
  <TopMenuHome v-if="menuAlign==='top'" :sysName="sysNameStr" :menuList="menuList" :loginUserInfo="loginUserInfo"></TopMenuHome>
  <LeftMenuHome v-else :sysName="sysNameStr" :menuList="menuList" :loginUserInfo="loginUserInfo"></LeftMenuHome>
</template>

<script>
  import TopMenuHome from '@/models/home/menuTopHome'
  import LeftMenuHome from '@/models/home/menuLeftHome'
  import MenuTurnner from '@/models/public/utils/menuTurner'

  export default {
    name: 'HomeBridging',
    props:['menuAlign'],
    components:{TopMenuHome,LeftMenuHome},
    data() {
      let menuList = [];
      const sysNameStr = "互联网采集";
      return {
        sysNameStr: sysNameStr,
        menuList : menuList,
        loginUserInfo : {}
      }
    },
    methods: {},
    mounted:function(){
      const $this = this;

      this.BaseRequest({
        url:'/sys/login/checkLoginUser',
        method:"get"
      }).then(loginUserInfo=>{
          // console.log(JSON.stringify(loginUserInfo))
          if(loginUserInfo!=null){
            $this.loginUserInfo = loginUserInfo
          }
          this.BaseRequest({
            url:'/sys/user/userMenuList',
            method:"get"
          }).then(response=>{
            $this.menuList = MenuTurnner.modalParse(response)
            console.log('=====',$this.menuList)
          })
      })
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
