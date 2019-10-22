<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'App',
    provide(){
      return {
        reload : this.reload
      }
    },
    data(){
      return {
        isRouteAlive :true
      }
    },
    methods:{
      reload(){
        this.isRouteAlive = false
        this.$nextTick(function(){
          this.isRouteAlive = true
        })
      }
    }
  }
</script>
<style>
  body,html{
    width:100%;
    height:100%;
  }

  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin:0;
    height:100%;
    width:100%;
    /*margin-top: 60px;*/
  }
  body{
    margin:0
  }
</style>
