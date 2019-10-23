<template>
  <JobList></JobList>
</template>


<script>
  import WorkTablePager from '@/models/public/WorkTablePager'
  import WorkMain from '@/models/public/WorkMain'
  import JobList from '@/models/spider/job/jobList'

  export default {
    name: 'Welcome',
    data() {
        return {

        }
    },
    components: {
      WorkTablePager,
      WorkMain,
      JobList
    },
    methods: {

    },
    mounted() {
      // this.getSelfReportInfo()
    }

  }
</script>

<style lang="css">
  .welcome-table-context{
    height:calc(100% - 50px) !important;
  }
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "@/styles/table-page.scss";

  .welcome-root{
    width:100%;
    height:100%;
    background-size: 100%;
  }

  .self-report{
    width:100%;
    height:50% !important;
    overflow: auto;
  }

  .full-height{
    height:100% !important;
  }

  .slef-report-tag{
    height:30px;
    line-height: 30px;
    float:left;
    margin:10px 0 0 50px;
    font-size: 15px;
  }

  .echarts{
    width:100%;
    height:100%;
  }

  .self-report-chart{
    width:30%;
    height:100%;
    max-height: 300px;
    float:left;
  }

  .son-origin-report{
    width:70%;
    overflow: hidden;
    height:100%;
    float:left;
  }


</style>
