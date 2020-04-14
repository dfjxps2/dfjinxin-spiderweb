<template>
  <WorkMain :headerItems="['资源管理','代理服务器编辑']">
    <div class="job-edit-root">
      <el-form :model="formData" :rules="validateRules" ref="formData" label-width="150px">
        <el-form-item label="代理编号" prop="proxy_server_id">
          <el-input :disabled="true" v-model.number="formData.proxy_server_id" ></el-input>
        </el-form-item>
        <el-form-item label="代理服务器名称" prop="proxy_server_name">
          <el-input :disabled="view_type=='view'" v-model="formData.proxy_server_name" ></el-input>
        </el-form-item>
        <el-form-item label="代理服务器IP" prop="proxy_server_ip">
          <el-input :disabled="view_type=='view'" v-model="formData.proxy_server_ip" ></el-input>
        </el-form-item>
        <el-form-item label="服务器用户名" prop="proxy_user_name">
          <el-input :disabled="view_type=='view'" v-model="formData.proxy_user_name" ></el-input>
        </el-form-item>
        <el-form-item label="服务器密码" prop="proxy_user_password">
          <el-input :disabled="view_type=='view'" v-model="formData.proxy_user_password" ></el-input>
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
        formData:{
          proxy_server_id:0,
          proxy_server_name:'',
          proxy_server_ip:'',
          proxy_user_name:'',
          proxy_user_password:''
        },
        validateRules:{
          proxy_server_name: [
            { required: true, message: '请输入代理服务器名称', trigger: 'blur' }
          ],
          proxy_server_ip: [
            { required: true, message: '请输入代理服务器IP', trigger: 'blur' }
          ],
          proxy_user_name: [
            { required: true, message: '请输入服务器用户名', trigger: 'blur' }
          ],
          proxy_user_password: [
            { required: true, message: '请输入服务器密码', trigger: 'blur' }
          ]
        }
      }
    },
    methods:{
      getInfo(){
        this.BaseRequest({
          url: "crawler/proxyServer/proxyServer",
          method: 'get',
          params: {
            proxy_server_id: this.formData.proxy_server_id
          }
        }).then(response => {
          this.formData = response
        })
      },
      save(){
        this.$refs['formData'].validate((valid) => {
          if (valid) {
            if(this.view_type=='new'){
              this.savePage("crawler/proxyServer/saveNewServer","保存成功")
            }else if(this.view_type=='edit'){
              this.savePage("crawler/proxyServer/updateServer","更新成功")
            }
          } else {
            return false;
          }
        });
      },
      savePage(url, msg){
        if(this.formData.proxy_server_id === ''){
          this.formData.proxy_server_id = 0
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
          name: "proxyServerList"
        });
      }
    },
    mounted() {
      this.formData.proxy_server_id = this.$route.params.proxy_server_id
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
