<template>
    <el-submenu ref="subMenu"
                v-if="menuData.children!=null&&menuData.children.length>0"
                :key="menuData.id"
                :index="'M'+menuData.id">
      <template style="margin-left:40px;margin-right:40px;" slot="title">
        {{menuData.name}}
      </template>
      <WorkTopMenu class="sub-menu" v-for="menuObj in menuData.children" :key="'C-'+menuObj.id" :menuData="menuObj"></WorkTopMenu>
    </el-submenu >
    <el-menu-item class="what-heppend"
                  v-else
                  @click="menuClick(menuData.url)"
                  :index="'M'+menuData.id">
      {{menuData.name}}
    </el-menu-item>

  </el-menu>

</template>

<script>
  export default {
    name: 'WorkTopMenu',
    data() {
        return {}
    },
    props:{
      menuData:{
        type: Object
      }
    },
    methods: {
      menuClick:function(menuUrl){
        this.$router.push({path: menuUrl});
      }
    },
    mounted:function(){
      const subMenus = this.$refs.subMenu
      if(subMenus){
        subMenus.removeItem("class")
//        subMenus.$el.setAttribute("class","")
        const subMenuTitleDom = subMenus.$el.firstChild
        const subMenuTitleDomClass = subMenuTitleDom.getAttribute("class")
        if(subMenuTitleDomClass==='el-submenu__title'){//修改带下拉的一级菜单的样式
          let styleHtml = subMenuTitleDom.getAttribute("style")
          subMenuTitleDom.setAttribute("style",styleHtml+";padding:0 30px 0 30px")//将原padding：0 20 0 20修改为30
          subMenuTitleDom.removeChild(subMenuTitleDom.lastChild)//删除一级菜单后面的箭头
        }
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

  .el-menu{
    padding:2px 0 2px 0;
  }

  .el-submenu-title{
    padding-left:30px;
    padding-right:30px;
  }

  .el-menu-item{
    padding:2px 30px 2px 30px;
  }

  .sub-menu{
    margin:0px;
    padding:0 30px 0 30px !important;
    font-size:14px;
    height:50px !important;
    line-height:50px !important;
    text-align: center;
    background-color: #ffffff !important;
    color:black !important;
    top:4px;
    box-shadow: 0px 1px 0px 1px #cdcdcd ;
  }

  .is-opened>div:after{
    width: 0;
    height: 0;
    display:block;
    content:'';
    border-width:9px 8px 8px 8px;
    border-style:solid;
    border-color:transparent  transparent #ffffff transparent ;
    margin-left:calc(50% - 8px);
    margin-top:-12px;
  }

  .is-opened>div:hover:after{
    border-color:transparent  transparent #ffffff transparent ;
  }

  .el-menu--popup-bottom-start{
    margin:0 0 0 0 !important;
    padding:0 !important;
    width:auto !important;
    min-width: 50px !important;
  }

  .el-submenu__title{
    height:50px !important;
    line-height: 50px !important;
    padding:0 22px !important;

  }

  .is-active>div{
    color:#ffffff !important;
    background-color: #448aff !important;
    border:0px !important;
    border-bottom-color: #ffffff !important;
  }

  .el-submenu__title:hover{
    background-color: #448aff !important;
  }

</style>
