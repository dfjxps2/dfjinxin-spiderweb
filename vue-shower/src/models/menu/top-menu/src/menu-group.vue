<template>
  <div class="menu-group-root">
    <WorkLogo :sysName="sysName"  class="work-menu-logo" :class="{'work-menu-logo-full' : menuList==null}"></WorkLogo>
    <div class="menu-group-context">
      <el-menu mode="horizontal" class="rootElMenu"
               menu-trigger="hover"
               background-color="rgba(255,255,255,0)"
               text-color="#fff"
               active-text-color="#ffd04b">
        <WorkTopMenu v-for="menuObj in menuList" :key="menuObj.id" :menuData="menuObj"></WorkTopMenu>
      </el-menu>
    </div>
    <!--<slot></slot>-->
  </div>
</template>

<script>
  import WorkTopMenu from "./menu"
  import WorkLogo from "./menu-logo"
  export default {
    name: 'WorkTopMenuGroup',
    components: {
      WorkTopMenu,
      WorkLogo
    },
    props: {
      menuList:{
        type:Array
      },
      sysName:{
        type:String
      }
    },
    data() {
        return {}
    },
    methods: {},
    mounted:function(){

    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  $full:100%;
  $full-height:100%;

  .menu-group-root{
    width:$full;
    height:$full;
  }

  .work-menu-logo{
    width:210px;
    height:$full;
    float:left;
    margin:0 0 0 0;
    font-size:20px;
    color:#ffffff;
  }

  .work-menu-logo-full{
    width:$full;
  }

  .menu-group-context{
    width:calc($full - 200px);
    height: $full;
    float:left;
  }
</style>
