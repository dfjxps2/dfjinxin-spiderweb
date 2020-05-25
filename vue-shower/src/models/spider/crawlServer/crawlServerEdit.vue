<template>
  <WorkMain :headerItems="['资源管理','采集服务器编辑']">
    <div class="job-edit-root">
      <el-form :model="formData" :rules="validateRules" ref="formData" label-width="150px">
        <el-form-item label="主机编号" prop="host_id">
          <el-input :disabled="true" v-model.number="formData.host_id" ></el-input>
        </el-form-item>
        <el-form-item label="主机名称" prop="host_name">
          <el-input :disabled="view_type=='view'" v-model="formData.host_name" ></el-input>
        </el-form-item>
        <el-form-item label="主机IP" prop="host_ip">
          <el-input :disabled="view_type=='view'" v-model="formData.host_ip" ></el-input>
        </el-form-item>
        <el-form-item label="在线状态"  prop="host_status">
          <el-select :disabled="view_type=='view'" v-model="formData.host_status" style="width:100%;" placeholder="请选择在线状态">
            <!--<el-option label="在线" value="1"></el-option>
            <el-option label="离线" value="0"></el-option>-->
            <el-option v-for="item in hostStatus" :key="item.value" :label="item.name" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label-width="0" style="text-align: right">
          <el-button v-if="view_type!='view'" type="success" @click="save" size="mini">保存</el-button>
          <el-button type="primary" @click="goBack" size="mini">放弃</el-button>
        </el-form-item>
      </el-form>

    </div>



  </WorkMain>

</template>

<script>
  import WorkMain from '@/models/public/WorkMain'

  export default {
    name: "PageEdit",
    describe:"页面编辑页面",
    components: {
      WorkMain
    },
    data(){
      return {
        view_type: 'view',//view edit new
        pageTitle:'',
        userList:[],
        jobList:[],
        formData:{
          host_id:0,
          host_name:'',
          host_ip:'',
          host_status:1,
          user_group_id:0
        },
        hostStatus:[
          {name:'在线',value:1 },
          {name:'离线',value:0 },
        ],
        validateRules:{
          host_name: [
            { required: true, message: '请输入主机名称', trigger: 'blur' }
          ]
        }
      }
    },
    methods:{
      getInfo(){
        this.BaseRequest({
          url: "crawler/server/getServer",
          method: 'get',
          params: {
            host_id: this.formData.host_id
          }
        }).then(response => {
          this.formData = response
        })
      },
      save(){
        this.$refs['formData'].validate((valid) => {
          if (valid) {
            if(this.view_type=='new'){
              this.savePage("crawler/server/saveNewServer","保存成功")
            }else if(this.view_type=='edit'){
              this.savePage("crawler/server/updateServer","更新成功")
            }
          } else {
            return false;
          }
        });
      },
      savePage(url, msg){
        if(this.formData.host_id === ''){
          this.formData.host_id = 0
        }
        this.BaseRequest({
          url: url,
          method: 'post',
          data: this.formData
        }).then(response => {
          this.Message.success(msg)
          this.goBack()
        })
      },
      goBack(){
        this.$router.push({
          name: "crawlServerList"
        });
      }
    },
    mounted() {
      this.formData.host_id = this.$route.params.host_id
      this.view_type = this.$route.params.view_type
      if(this.view_type=='edit'||this.view_type=='view'){
        this.getInfo()
      }

      if(this.view_type=='view'){
        this.pageTitle = "查看"
      }else if(this.view_type=='edit'){
        this.pageTitle = "编辑"
      }else if(this.view_type=='new'){
        this.pageTitle = "新增"
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
