<template>

</template>

<script>
  export default {
    name: 'CasLogin',
    data() {
        return {}
    },
    methods: {
    },
    mounted(){
      this.$http({
        url:process.env.BASE_API+"/sys/casLogin/checkCasLoginUser.do",
        timeout: 20000, // 请求超时时间 20秒,
        withCredentials:true
      }).then(response=>{
        const res = response.data
        if (res.result !== 'SUCCESS') {
          if(res.faild_reason === 'FORWARD_CAS'){
            let forwardUrl = res.resultData
            window.location = forwardUrl
          }
        } else {
          this.$router.push({path:"/lexicalAnalyze/baseModel"})
        }
      })
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
