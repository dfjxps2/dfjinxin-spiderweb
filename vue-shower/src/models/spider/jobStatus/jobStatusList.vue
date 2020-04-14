<template>
  <WorkMain :headerItems="['采集监控','采集监控页面']">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-input placeholder="请输入采集名称" v-model="job_name" style="width:180px" ></el-input>
        <el-button @click="getTableData(1)" >查询</el-button>
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
            align="left"
            label="采集名称">
          </el-table-column>
          <el-table-column
            prop="start_time_str"
            align="left" width="180"
            label="启动时间">
          </el-table-column>
          <el-table-column
            prop="run_status_cn"
            align="left" width="80"
            label="运行状态">
          </el-table-column>
          <el-table-column width="100"
            prop="user.user_name_cn"
            align="left"
            label="用户">
          </el-table-column>
          <el-table-column
            prop="download_page_num" width="80"
            label="已抓取页面数">
          </el-table-column>
          <el-table-column
            prop="pending_page_num" width="80"
            label="待抓取页面数">
          </el-table-column>
          <el-table-column
            prop="error_page_num" width="80"
            label="抓取失败页面数">
          </el-table-column>
          <!--<el-table-column
            label="操作"
            align="left"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="viewEdit(scope.row,'view')" size="mini" >页面浏览</el-button>
            </template>
          </el-table-column>-->
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
    name: "JobStatusList",
    describe:"采集监控页面",
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

        let seachOriginId = null
        if(this.seachOriginList!=null&&this.seachOriginList.length>0){
          seachOriginId = this.seachOriginList[this.seachOriginList.length-1]
        }

        const $this = this
        this.BaseRequest({
          url: "crawler/jobStatus/pagingList",
          method: 'get',
          params: {
            currPage: pageNum,
            pageSize: this.eachPageNum,
            job_id:this.job_id,
            job_name:this.job_name,
          }
        }).then(response => {
          $this.dataList = response.dataList
          // $this.origins = response.origins
          $this.totalPage = response.totalPage

        })

      },
      viewEdit(row,viewType){
        this.$router.push({
          name: "jobPageList",
          params: {
            "job_id": row.job_id,
            "user_id": row.user_id,
            "view_type": viewType
          }
        });
      },
    },
    mounted() {
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
