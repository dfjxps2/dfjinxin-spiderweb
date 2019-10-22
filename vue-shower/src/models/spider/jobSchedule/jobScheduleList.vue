<template>
  <WorkMain :headerItems="['调度配置','调度列表']">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-button @click="viewAdd()" type="info">新增</el-button>
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
          <el-table-column width="200"
             prop="jobSchedule.job_schedule_id"
             align="center"
             label="任务调度编号">
          </el-table-column>
          <el-table-column
            prop="jobSchedule.job_schedule_type"
            align="center" width="200"
            :formatter="getOriginName"
            label="调度类型编号">
          </el-table-column>
          <el-table-column
            prop="param_name"
            width="230"
            align="center"
            label="调度类型">
          </el-table-column>
          <el-table-column
            label="操作"
            align="left"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="viewEdit(scope.row.job_schedule_id,scope.row.job_schedule_type,scope.row.param_name)" size="mini" >编辑</el-button>
              <el-button type="primary" @click="delJob(scope.row.job_schedule_id,scope.row.param_name)" size="mini" >删除</el-button>
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
    name: "jobScheduleList",
    describe:"调度配置页面",
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
          url: "job/jobSchedule/pagingList",
          method: 'get',
          params: {
            currPage: pageNum,
            pageSize: this.eachPageNum
          }
        }).then(response => {
          $this.dataList = response.dataList
          // $this.origins = response.origins
          $this.totalPage = response.totalPage

        })

      },
      viewEdit(scheduleId,type,name){
        this.$router.push({
          name: "jobScheduleEdit",
          params:{
            'job_schedule_id': scheduleId,
            'job_schedule_type': type,
            'param_name': name
          }
        });
      },
      viewAdd(){
        this.$router.push({
          name: "jobScheduleAdd"
        });
      },
      delJob(scheduleId,name){
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
            url:'job/jobSchedule/delSchedule',
            method:'get',
            params:{'job_schedule_id': scheduleId,'param_name': name}
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
