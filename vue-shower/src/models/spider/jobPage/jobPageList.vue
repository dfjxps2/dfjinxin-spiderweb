<template>
  <WorkMain :headerItems="['采集监控','采集页面浏览']">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-input placeholder="请输入采集名称" v-model="job_name" style="width:180px" ></el-input>
        <el-button @click="getTableData(1)" type="success">查询</el-button>
        <el-button type="primary" @click="goBack">返回</el-button>
      </el-col>
    </el-row>
    <el-row class="table-page-root-outoptions">
      <el-col :span="24">
        <el-table
          :data="dataList"
          header-row-class-name="table-header-style"
          row-class-name="mini-font-size" stripe
          style="width: 100%;">
          <el-table-column
            prop="job_id" width="80"
            align="left"
            label="采集编号">
          </el-table-column>
          <el-table-column
            prop="job_name"
            align="left" width="180"
            label="采集名称">
          </el-table-column>
          <el-table-column width="100"
            prop="page_url"
            align="left"
            label="页面URL">
          </el-table-column>
          <el-table-column width="100"
             prop="download_time"
             align="left"
             label="采集时间">
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <!-- 分页 refreshData:点击页码上一页下一页时调用的方法、pageCount:总页数-->
    <WorkTablePager @refreshData="getTableData"
                    :pageCount="totalPage">
    </WorkTablePager>
  </WorkMain>

</template>

<script>
  import WorkTablePager from '@/models/public/WorkTablePager'
  import WorkMain from '@/models/public/WorkMain'

  export default {
    name: "JobPageList",
    describe:"采集页面浏览",
    components: {
      WorkTablePager,
      WorkMain
    },
    data(){
      return {
        dataList: [],
        currPageNum: 1,
        eachPageNum: 10,
        totalPage: 1,
        job_id: '',
        job_name: ''
      }
    },
    methods:{
      getTableData(pageNum){
        if (pageNum && pageNum !== '') {
          this.currPageNum = pageNum
        } else {
          pageNum = this.currPageNum
        }

        const $this = this
        this.BaseRequest({
          url: "crawler/pageView/listCrawlerPage",
          method: 'get',
          params: {
            currPage: pageNum,
            pageSize: this.eachPageNum,
            jobPage:{
              job_id:this.job_id,
              job_name:this.job_name,
            }
          }
        }).then(response => {
          $this.dataList = response.dataList
          $this.totalPage = response.totalPage

        })

      },
      goBack(){
        this.$router.push({
          name: "jobStatusList"
        });
      }
    },
    mounted() {
      this.job_id = this.$route.params.job_id
      this.getTableData(1)
    }
  }
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "@/styles/table-page.scss";

  .search-row{
    margin:5px 0 0 0;
  }
</style>
