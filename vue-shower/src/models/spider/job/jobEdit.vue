<template>
  <WorkMain :headerItems="['采集管理','任务编辑']">
    <div class="job-edit-root">
      <el-form :model="jobEditForm" :rules="validateRules" ref="jobEditForm" label-width="150px">
        <el-form-item a label="采集名称" prop="job_name">
          <el-input :disabled="view_type=='view'" v-model="jobEditForm.job_name" ></el-input>
        </el-form-item>
        <el-form-item label="生效标志" prop="is_valid_cn">
          <el-select :disabled="view_type=='view'" STYLE="width: 100%" v-model="jobEditForm.is_valid_cn" placeholder="是否有效">
            <el-option label="有效" value="1"></el-option>
            <el-option label="无效" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="最大采集页面数" prop="max_page_num">
          <el-input :disabled="view_type=='view'" v-model.number="jobEditForm.max_page_num" ></el-input>
        </el-form-item>
        <el-form-item label="起始URL列表"  prop="start_urls">
          <el-input :disabled="view_type=='view'" v-model="jobEditForm.start_urls" ></el-input>
        </el-form-item>
        <el-form-item label="页面保存周期">
          <el-input :disabled="view_type=='view'" v-model.number="jobEditForm.page_life_cycle" ></el-input>
        </el-form-item>
        <el-form-item label="起始页面">
          <el-input :disabled="view_type=='view'" v-model.number="jobEditForm.entry_page_id" ></el-input>
        </el-form-item>
        <el-form-item label="最大采集深度">
          <el-input :disabled="view_type=='view'" v-model="jobEditForm.max_depth" ></el-input>
        </el-form-item>
        <el-form-item label="数据源类型">
          <el-input :disabled="view_type=='view'" v-model.number="jobEditForm.crawl_src_type_id" ></el-input>
        </el-form-item>
        <el-form-item label="数据存储">
          <el-input :disabled="view_type=='view'" v-model="jobEditForm.data_store_id" ></el-input>
        </el-form-item>
        <el-form-item label="任务调度">
          <el-input :disabled="view_type=='view'" v-model="jobEditForm.job_schedule_id" ></el-input>
        </el-form-item>
        <el-form-item label="采集服务器">
          <el-input :disabled="view_type=='view'" v-model.number="jobEditForm.host_id" ></el-input>
        </el-form-item>
        <el-form-item label-width="0" style="text-align: right">
          <el-button v-if="view_type!='view'" type="success" @click="saveJob" size="mini">保存</el-button>
          <el-button type="primary" @click="goBack" size="mini">放弃</el-button>
        </el-form-item>
      </el-form>


      <el-table
        :data="proxyDataList"
        header-row-class-name="table-header-style"
        row-class-name="mini-font-size" stripe
        style="width: 100%;">
        <el-table-column
          prop="proxy_server_id"
          align="left"
          label="服务器编号">
        </el-table-column>
        <el-table-column
          prop="proxy_server_name"
          align="left"
          label="服务器名称">
        </el-table-column>
        <el-table-column
          prop="proxy_server_ip"
          align="left"
          label="服务器IP">
        </el-table-column>
        <el-table-column
                         prop="user.user_name_cn"
                         align="left"
                         label="用户">
        </el-table-column>
        <el-table-column
          prop="proxy_user_name"
          label="用户名">
        </el-table-column>
        <el-table-column
          prop="proxy_user_password"
          label="口令">
        </el-table-column>
        <el-table-column
          label="操作"
          align="left"
        >
          <template slot-scope="scope">
            <el-button @click="selectProxy(scope)"  type="info" size="mini" >{{scope.row.selected == 'N'?'选择':'删除'}}</el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>



  </WorkMain>

</template>

<script>
  import WorkMain from '@/models/public/WorkMain'

  export default {
    name: "JobEdit",
    describe:"采集任务编辑页面",
    components: {
      WorkMain
    },
    data(){
      return {
        view_type: 'view',//view edit new
        pageTitle:'',
        job_id: '',
        job_name: '',
        is_valid: '',
        jobEditForm:{
          job_name:'',
          is_valid:'',
          is_valid_cn:'1',
          max_page_num:null,
          start_urls:null,
          page_life_cycle:null,
          entry_page_id:null,
          max_depth:null,
          crawl_src_type_id:null,
          data_store_id:null,
          job_schedule_id:null,
          host_id:null,
          proxyServerList:[]
        },
        proxyDataList:[],
        validateRules:{
          job_name: [
            { required: true, message: '请输入任务名称', trigger: 'blur' }
          ],
          is_valid_cn: [
            { required: true, message: '请选择是否有效', trigger: 'blur' }
          ],
          max_page_num: [
            { required: false, message: '可为空', trigger: 'blur' },
            { type: 'number', message: '必须为数字'}
          ],
          host_id: [
            { required: false, message: '可为空', trigger: 'blur' },
            { type: 'number', message: '必须为数字'}
          ],
          page_life_cycle: [
            { required: false, message: '可为空', trigger: 'blur' },
            { type: 'number', message: '必须为数字'}
          ],
          entry_page_id: [
            { required: false, message: '可为空', trigger: 'blur' },
            { type: 'number', message: '必须为数字'}
          ],
          crawl_src_type_id: [
            { required: false, message: '可为空', trigger: 'blur' },
            { type: 'number', message: '必须为数字'}
          ]
        }
      }
    },
    methods:{
      getJobInfo(){
        this.BaseRequest({
          url: "crawler/jobMg/getCrawlAndProxy",
          method: 'get',
          params: {
            job_id: this.job_id
          }
        }).then(response => {
          this.jobEditForm = response.crawlData
          this.jobEditForm.proxyServerList = response.allProxyServerList
        })

      },
      getProxList(){
        this.BaseRequest({
          url: "/crawler/proxyServer/listProxyServers",
          method: 'get'
        }).then(response => {
          this.proxyDataList = response

          if(this.proxyDataList&&this.proxyDataList.length>0){
            this.proxyDataList.forEach((proxyData,i)=>{
              const serverId = proxyData.proxy_server_id
              this.proxyDataList[i]['selected'] = 'N'
              if(this.jobEditForm.proxyServerList&&this.jobEditForm.proxyServerList.length>0) {
                this.jobEditForm.proxyServerList.forEach(proxyServer=>{
                  if(serverId==proxyServer.proxy_server_id){
                    proxyData['selected'] = 'Y'
                  }
                })
              }
            })
          }
        })

      },
      selectProxy(scopeRow){
        const selected  = this.proxyDataList[scopeRow.$index].selected
        if(selected=='Y'){
          this.proxyDataList[scopeRow.$index].selected = 'N'
        }else{
          this.proxyDataList[scopeRow.$index].selected = 'Y'
        }
      },
      saveJob(){
        this.$refs['jobEditForm'].validate((valid) => {
          if (valid) {
            if(this.view_type=='new'){
              this.saveNewJob()
            }else if(this.view_type=='edit'){
              this.updateJob()
            }
          } else {
            return false;
          }
        });
      },
      saveNewJob(){
        const checkAllProxyServers = []

        this.proxyDataList.forEach(proxyData=>{
          if(proxyData.selected=='Y'){
            checkAllProxyServers.push(proxyData.proxy_server_id)
          }
        })

        this.jobEditForm.proxyServerList = checkAllProxyServers

        //设置是否有效
        if (this.jobEditForm.is_valid_cn === '1'){
          this.jobEditForm.is_valid = 1
        } else if (this.jobEditForm.is_valid_cn === '0') {
          this.jobEditForm.is_valid = 0
        }
        this.BaseRequest({
          url: "crawler/jobMg/saveNewJob",
          method: 'post',
          data: this.jobEditForm
        }).then(response => {
          this.Message.success("保存成功")
          this.$router.push({
            name: "jobList"
          });
        })
      },
      updateJob(){
        const checkAllProxyServers = []

        this.proxyDataList.forEach(proxyData=>{
          if(proxyData.selected=='Y'){
            checkAllProxyServers.push(proxyData.proxy_server_id)
          }
        })

        this.jobEditForm.proxyServerList = checkAllProxyServers
        //设置是否有效
        if (this.jobEditForm.is_valid_cn === '1'){
          this.jobEditForm.is_valid = 1
        } else if (this.jobEditForm.is_valid_cn === '0') {
          this.jobEditForm.is_valid = 0
        }
        this.BaseRequest({
          url: "crawler/jobMg/updateJobInfo",
          method: 'post',
          data: this.jobEditForm
        }).then(response => {
          this.Message.success("更新成功")
          this.$router.push({
            name: "jobList"
          });
        })
      },
      goBack(){
        this.$router.push({
          name: "jobList"
        });
      }
    },
    mounted() {
      this.job_id = this.$route.params.job_id
      this.jobEditForm.user_id = this.$route.params.user_id
      this.view_type = this.$route.params.view_type
      if(this.view_type=='edit'||this.view_type=='view'){
        this.getJobInfo()
      }

      if(this.view_type=='view'){
        this.pageTitle = "查看"
      }else if(this.view_type=='edit'){
        this.pageTitle = "编辑"
      }else if(this.view_type=='new'){
        this.pageTitle = "新增"
      }

      this.getProxList()
    }
  }
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "@/styles/table-page.scss";

  .search-row{
    margin:5px 0 0 0;
  }

  .el-form{
    width:80%;
    margin:20px 10% 0 10%;
  }

  .el-form-item{
    width:50%;
    float: left;
  }

  .job-edit-root{
    height:100%;
    width:100%;
  }

  .job-edit-buttons{
    text-align: right;
    margin:0 40px 0 0;
  }
</style>
