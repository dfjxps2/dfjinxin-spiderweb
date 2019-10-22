<template>
  <WorkMain :headerItems="['调度配置','调度新增']">
    <div class="job-edit-root">
      <el-form :model="jobEditForm" :rules="validateRules" ref="jobEditForm" label-width="150px">
        <el-form-item label="任务调度编号" prop="job_schedule_id">
          <el-input  v-model.number="jobEditForm.job_schedule_id"></el-input>
        </el-form-item>
        <el-form-item label="调度类型编号" prop="job_schedule_type">
          <el-input v-model.number="jobEditForm.job_schedule_type"></el-input>
        </el-form-item>
        <el-form-item label="调度类型" prop="param_name">
          <el-input v-model="jobEditForm.param_name"></el-input>
        </el-form-item>
        <el-form-item label="调度参数值" prop="param_value">
          <el-input v-model="jobEditForm.param_value"></el-input>
        </el-form-item>
        <el-form-item v-if="view_type!='view'" label-width="0" style="text-align: right">
          <el-button type="success" @click="save" :size="mini">保存</el-button>
          <el-button type="primary" @click="callOf()" :size="mini">放弃</el-button>
        </el-form-item>
      </el-form>
    </div>
  </WorkMain>

</template>

<script>
import WorkMain from '@/models/public/WorkMain'

export default {
  name: 'jobScheduleEdit',
  describe: '调度配置新增页面',
  components: {
    WorkMain
  },
  data () {
    return {
      visible: false,
      view_type: '', // edit new
      pageTitle: '',
      jobEditForm: {
        job_schedule_id: '',
        job_schedule_type: '',
        param_name: '',
        param_value: ''
      },
      validateRules: {
        job_schedule_id: [
          {required: true, message: '请输入任务调度编号', trigger: 'blur'},
          { type: 'number', message: '必须为数字'}
        ],
        job_schedule_type: [
          {required: true, message: '请输入调度类型编号', trigger: 'blur'},
          { type: 'number', message: '必须为数字'}
        ],
        param_name: [
          {required: true, message: '请输入调度类型', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    save () {
      this.$refs['jobEditForm'].validate((valid) => {
        if (valid) {
          this.saveNewSchedule()
        } else {
          return false
        }
      })
    },
    saveNewSchedule () {
      this.BaseRequest({
        url: 'job/jobSchedule/saveNewSchedule',
        method: 'post',
        params: {
          job_schedule_id: this.jobEditForm.job_schedule_id,
          job_schedule_type: this.jobEditForm.job_schedule_type,
          param_name: this.jobEditForm.param_name,
          param_value: this.jobEditForm.param_value
        }
      }).then(response => {
        this.Message.success('保存成功')
        this.$router.push({
          name: 'jobScheduleList'
        })
      })
    },
    callOf () {
      this.visible = true
      this.$router.push({
        name: 'jobScheduleList'
      })
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
