<template>

</template>

<script>
  export default {
    name: 'MockLogin',
    data() {
        return {}
    },
    methods: {
    },
    mounted(){
      this.$http({
        url:process.env.BASE_API+"/sys/cqNyCasLogin/mockLogin.do",
        timeout: 20000, // 请求超时时间 20秒,
        withCredentials:true
      }).then(response=>{
        this.$router.push({'path':'/home'})
      })
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
