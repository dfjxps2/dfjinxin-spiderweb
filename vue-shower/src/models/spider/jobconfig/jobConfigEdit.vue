<template>
  <WorkMain :headerItems="['采集配置','配置编辑']">
    <div class="job-edit-root">
      <el-form :model="jobEditForm" :rules="validateRules" ref="jobEditForm" label-width="150px">
        <el-form-item label="参数名称" prop="param_name">
          <el-input  v-model="jobEditForm.param_name"></el-input>
        </el-form-item>
        <el-form-item label="参数值" prop="param_value">
          <el-input v-model="jobEditForm.param_value"></el-input>
        </el-form-item>
        <el-form-item label="用户名称" prop="user_id">
          <el-select v-model="jobEditForm.user_id" STYLE="width: 100%" placeholder="请选择">
            <el-option
              v-for="item in useAll"
              :key="item.user_id"
              :label="item.user_name"
              :value="item.user_id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="采集任务" prop="job_id">
          <el-select v-model="jobEditForm.job_id" STYLE="width: 100%" placeholder="请选择">
            <el-option
              v-for="item in jobBeans"
              :key="item.job_id"
              :label="item.job_name"
              :value="item.job_id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item v-if="view_type!='view'" label-width="0" style="text-align: right">
          <el-button type="success" @click="save" :size="small">保存</el-button>
          <el-button type="primary" @click="callOf()" :size="small">放弃</el-button>
        </el-form-item>
      </el-form>
    </div>
  </WorkMain>

</template>

<script>
import WorkMain from '@/models/public/WorkMain'

export default {
  name: 'JobConfigEdit',
  describe: '采集配置编辑页面',
  components: {
    WorkMain
  },
  data () {
    return {
      visible: false,
      view_type: '', // edit new
      pageTitle: '',
      useAll: [],
      jobBeans: [],
      jobEditForm: {
        param_name: '',
        param_value: '',
        user_id: '',
        job_id: ''
      },
      validateRules: {
        param_name: [
          {required: true, message: '请输入参数名称', trigger: 'blur'}
        ],
        param_value: [
          {required: true, message: '请输入参数值', trigger: 'blur'}
        ],
        user_id: [
          {required: true, message: '请选择用户名称', trigger: 'blur'}
        ],
        job_id: [
          {required: true, message: '请选择采集任务', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getInfo () {
      this.BaseRequest({
        url: 'crawler/jobConfig/getJonConfig',
        method: 'get',
        params: {
          job_id: this.job_id,
          user_id: this.user_id,
          param_name: this.param_name
        }
      }).then(response => {
        this.jobEditForm = response.crawlerConfig
        if (this.jobEditForm.param_value) {
          this.jobEditForm.param_value = decodeURIComponent(this.jobEditForm.param_value)
        }
        this.useAll = response.userList
        this.jobBeans = response.jobInfoBeans
      })
    },
    getInfo1 () {
      this.BaseRequest({
        url: 'crawler/jobConfig/getJonConfigInfo',
        method: 'get'
      }).then(response => {
        this.useAll = response.userList
        this.jobBeans = response.jobInfoBeans
      })
    },
    save () {
      this.$refs['jobEditForm'].validate((valid) => {
        if (valid) {
          if (this.view_type === 'new') {
            this.saveNewConfig()
          } else if (this.view_type === 'edit') {
            this.updateJob()
          }
        } else {
          return false
        }
      })
    },
    updateJob () {
      this.BaseRequest({
        url: 'crawler/jobConfig/updateConfig',
        method: 'post',
        params: {
          p_name: this.param_name,
          j_id: this.job_id,
          u_id: this.user_id,
          param_name: this.jobEditForm.param_name,
          param_value: this.jobEditForm.param_value,
          user_id: this.jobEditForm.user_id,
          job_id: this.jobEditForm.job_id
        }
      }).then(response => {
        this.Message.success('保存成功')
        this.$router.push({
          name: 'jobConfigList'
        })
      })
    },
    saveNewConfig () {
      this.BaseRequest({
        url: 'crawler/jobConfig/saveNewConfig',
        method: 'post',
        data: this.jobEditForm
      }).then(response => {
        this.Message.success('保存成功')
        this.$router.push({
          name: 'jobConfigList'
        })
      })
    },
    callOf () {
      this.visible = true
      this.$router.push({
        name: 'jobConfigList'
      })
    }
  },
  mounted () {
    this.param_name = this.$route.params.param_name
    this.job_id = this.$route.params.job_id
    this.user_id = this.$route.params.user_id
    this.view_type = this.$route.params.view_type
    if (this.view_type === 'new') {
      this.getInfo1()
    }
    if (this.view_type === 'edit') {
      this.getInfo()
    }
    if (this.view_type === 'edit') {
      this.pageTitle = '编辑'
    } else if (this.view_type === 'new') {
      this.pageTitle = '新增'
    }
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
