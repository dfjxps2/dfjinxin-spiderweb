<template>
  <WorkMain :headerItems="['采集管理','采集列表']">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">

        <el-input placeholder="请输入采集编号" v-model="job_id" style="width:180px" ></el-input>
        <el-input placeholder="请输入采集名称" v-model="job_name" style="width:180px" ></el-input>
        <el-select clearable placeholder="请选择是否有效" v-model="is_valid" style="width:180px" >
          <el-option value="1" label="是"></el-option>
          <el-option value="0" label="否"></el-option>
        </el-select>
        <el-button @click="getTableData(1)" type="success">查询</el-button>
        <el-button @click="viewEdit(null,'new')" type="info">新增</el-button>
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
            align="center"
            label="采集编号">
          </el-table-column>
          <el-table-column
            prop="job_name"
            align="center" width="180"
            label="采集名称">
          </el-table-column>
          <el-table-column
            prop="entry_page_id"
            align="center"
            width="80"
            label="起始页面">
          </el-table-column>
          <el-table-column width="100"
            prop="user.user_name_cn"
            align="center"
            label="用户">
          </el-table-column>
          <el-table-column
            prop="is_valid_cn" width="80"
            align="center"
            label="是否有效">
          </el-table-column>
          <el-table-column
            prop="jobStatus.run_status_cn" width="80"
            label="运行状态">
          </el-table-column>
          <el-table-column
            label="操作"
            align="left"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="viewEdit(scope.row.job_id,'view')" size="mini" >查看</el-button>
              <el-button type="primary" @click="viewEdit(scope.row.job_id,'edit')" size="mini" >编辑</el-button>
              <el-button type="primary" @click="delJob(scope.row.job_id)" size="mini" >删除</el-button>
              <el-button type="primary" @click="jobChanged(scope.row.job_id,'START')" size="mini" >启动</el-button>
              <el-button type="primary" @click="jobChanged(scope.row.job_id,'STOP')" size="mini" >停止</el-button>
              <el-button type="primary" @click="jobChanged(scope.row.job_id,'UPDATE')" size="mini" >更新</el-button>
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
    name: "JobList",
    describe:"采集管理页面",
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
        job_name: '',
        is_valid: '',
        rowStyle:{height:'20px'}
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
          url: "crawler/jobMg/pagingList",
          method: 'get',
          params: {
            currPage: pageNum,
            pageSize: this.eachPageNum,
            job_id:this.job_id,
            job_name:this.job_name,
            is_valid:this.is_valid
          }
        }).then(response => {
          $this.dataList = response.dataList
          // $this.origins = response.origins
          $this.totalPage = response.totalPage

        })

      },
      viewEdit(jobId,viewType){
        this.$router.push({
          name: "jobEdit",
          params: {
            "job_id": jobId,
            "view_type": viewType
          }
        });
      },
      delJob(jobId){
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
            url:'crawler/jobMg/deleJob',
            method:'get',
            params:{'job_id':jobId}
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
      },
      jobChanged(jobId,type){
        let jobConfirmTitle = ""
        let jobUrl = ""
        if(type=='START'){
          jobConfirmTitle = "启动"
          jobUrl = "crawler/jobMg/startJob"
        }else if(type=='STOP'){
          jobConfirmTitle = "停止"
          jobUrl = "crawler/jobMg/stopJob"
        }else if(type=='UPDATE'){
          jobConfirmTitle = "更新"
          jobUrl = "crawler/jobMg/updateJob"
        }

        this.$confirm('确定'+jobConfirmTitle+'该任务？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          dangerouslyUseHTMLString:true,
          type: 'warning'
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: jobConfirmTitle+'中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });

          this.BaseRequest({
            url:jobUrl,
            method:'get',
            params:{'job_id':jobId}
          }).then(response=>{
            this.Message.success(jobConfirmTitle+"成功")
            loading.close()
            this.getTableData(1)
          }).catch(error=>{
            loading.close()
            this.Message.error(jobConfirmTitle+"失败"+error)
          })
        }).catch(() => {
        });
      },
      getStatus(is_valid){
        return is_valid == 1 ? '是' : '否';
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
