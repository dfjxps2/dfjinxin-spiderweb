<template>
    <el-submenu  ref="subMenu"
                v-if="menuData.children!=null&&menuData.children.length>0"
                :key="menuData.id"
                :index="'M'+menuData.id">
      <template slot="title">
        <div class="sprit-menu" ></div>
        <!--<i :class="menuIcon[menuData.name]"></i>-->
        <!--<icon name="record-checked"></icon>-->
        <span class="title-style" slot="title">{{menuData.name}}</span>
      </template>
      <WorkTopMenu class="sub-menu" v-for="(menuObj,childIndex) in menuData.children"
                   :key="'C-'+menuObj.id" :menuData="menuObj" :spritBaseClass="spritBaseClass"></WorkTopMenu>
    </el-submenu >
    <el-menu-item class="what-heppend"
                  v-else
                  @click="menuClick(menuData.url)"
                  :index="'M'+menuData.id">
      <div class="sprit-menu" ></div>
      <!--<i :class="menuIcon[menuData.name]"></i>-->
      <span class="title-style">{{menuData.name}}</span>
    </el-menu-item>

  <!--</el-menu>-->

</template>

<script>
  export default {
    name: 'WorkTopMenu',
    data() {
        return {
          menuIcon:{
            '系统管理':'el-icon-setting',
            '功能查看':'el-icon-more',
            '用户管理':'el-icon-service',
            '角色管理':'el-icon-tickets',
            '机构管理':'el-icon-menu',
            '行政机构管理':'el-icon-menu',
            '监管用户管理':'el-icon-service',
            '报送管理':'el-icon-edit',
            '报表定义':'el-icon-date',
            '报送填报':'el-icon-upload',
            '上级审核':'el-icon-check',
            '复核':'el-icon-check',
            '报送监管':'el-icon-view'
          }
        }
    },
    props:{
      menuData:{
        type: Object
      },
      menuIndex:{
        type: Number
      },
      spritBaseClass:{
        type:String
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
          // subMenuTitleDom.setAttribute("style",styleHtml+";padding:0 30px 0 30px")//将原padding：0 20 0 20修改为30
          subMenuTitleDom.removeChild(subMenuTitleDom.lastChild)//删除一级菜单后面的箭头
        }
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .el-menu-item ,.title-style{
    color: #b1b4c3;
  }
  .sub-menu{
    background-color: #3E3958 !important;
  }

  .what-heppend{
    float: left;
  }



</style>
