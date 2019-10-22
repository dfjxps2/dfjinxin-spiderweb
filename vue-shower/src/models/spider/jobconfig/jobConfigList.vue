<template>
  <WorkMain :headerItems="['采集配置','配置列表']">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-input placeholder="请输入参数名称" v-model="param_name" style="width:180px" ></el-input>
        <el-button @click="getTableData(1)" type="success">查询</el-button>
        <el-button @click="viewEdit(null,null,null,'new')" type="info">新增</el-button>
      </el-col>
    </el-row>
    <el-row class="table-page-root-outoptions">
      <el-col :span="24">
        <el-table
          :data="dataList"
          header-row-class-name="table-header-style"
          row-class-name="mini-font-size" stripe
          row-style="height:20px"
          style="width: 100%;">
          <el-table-column width="150"
             prop="user_info.user_name_cn"
             align="center"
             label="用户名称">
          </el-table-column>
          <el-table-column
            prop="job_info.job_name"
            align="center" width="210"
            :formatter="getOriginName"
            label="采集名称">
          </el-table-column>
          <el-table-column
            prop="param_name" width="210"
            align="center"
            label="参数名称">
          </el-table-column>
          <el-table-column
            prop="param_value" width="280"
            align="center"
            label="参数值">
          </el-table-column>
          <el-table-column
            label="操作"
            align="left"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="viewEdit(scope.row.job_id,scope.row.user_id,scope.row.param_name,'edit')" size="mini" >编辑</el-button>
              <el-button type="primary" @click="delJob(scope.row.job_id,scope.row.user_id,scope.row.param_name)" size="mini" >删除</el-button>
            </template>
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
    name: "JobConfigList",
    describe:"采集配置页面",
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
        param_name: '',
        param_value: ''

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
          url: "crawler/jobConfig/pagingList",
          method: 'get',
          params: {
            currPage: pageNum,
            pageSize: this.eachPageNum,
            param_name: this.param_name
          }
        }).then(response => {
          $this.dataList = response.dataList
          $this.dataList.forEach(x => {
               if (x.param_value) {
                  x.param_value = decodeURIComponent(x.param_value)
               }
          })
          // $this.origins = response.origins
          $this.totalPage = response.totalPage

        })

      },
      viewEdit(jobId,userId,name,viewType){
        this.$router.push({
          name: "jobConfigEdit",
          params:{
            'job_id': jobId,
            'user_id': userId,
            'param_name': name,
            'view_type': viewType
          }
        });
      },
      delJob(jobId,userId,name){
        this.$confirm('确定删除该任务？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          dangerouslyUseHTMLString:true,
          type: 'warning'
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: '删除中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });

          this.BaseRequest({
            url:'crawler/jobConfig/delCrawlConfig',
            method:'get',
            params:{'job_id':jobId,'user_id':userId,'param_name':name}
          }).then(response=>{
            this.Message.success("删除成功")
            loading.close()
            this.getTableData(1)
          }).catch(error=>{
            loading.close()
            this.Message.error("删除失败"+error)
          })
        }).catch(() => {
        });
      }
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
