<template>
  <div class="menu-logo">
    <div class="logo">
      <icon name="logo"></icon>

    </div>
    <div class="logo-content">{{sysName}}</div>

  </div>
</template>

<script>
  import '@/styles/logo.css'


  export default {
    name: 'WorkLeftLogo',
    components: {},
    data() {
      return {}
    },
    props:{
      sysName:{
        type:String
      }
    },
    methods: {}
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .menu-logo{
    text-align: left;
    font-size:20px;
    font-weight: bold;
    color:#ffffff;
    /*color:black;*/
  }

  .fa-icon {
    width:25px;
    height:25px;
    margin:10px 15px 0 15px;
  }

  .logo{
    float: left;
  }

  .logo-content{
    float: left;
    /*width:calc(100% - 42px);*/
    height: 45px;
    line-height:45px;
    margin-left:20px;
    text-shadow: 2px 3px 7px black;
  }

</style>
