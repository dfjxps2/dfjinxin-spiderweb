<template>
  <WorkMain :headerItems="['资源管理','信息存储','信息字段编辑']">
    <div class="job-edit-root">
      <el-form :model="formData" :rules="validateRules" ref="formData" label-width="150px">
        <el-form-item label="表名" prop="table_name">
          <el-input :disabled="true" v-model="formData.table_name" ></el-input>
        </el-form-item>
        <el-form-item label="表中文名" prop="table_name_cn">
          <el-input :disabled="view_type=='view'" v-model="formData.table_name_cn" ></el-input>
        </el-form-item>
        <el-form-item label="字段名" prop="field_name">
          <el-input :disabled="true" v-model="formData.field_name" ></el-input>
        </el-form-item>
        <el-form-item label="字段中文名" prop="field_name_cn">
          <el-input :disabled="view_type=='view'" v-model="formData.field_name_cn" ></el-input>
        </el-form-item>
        <el-form-item label="字段序号" prop="field_id">
          <el-input :disabled="true" v-model="formData.field_id" ></el-input>
        </el-form-item>

        <el-form-item label-width="0" style="text-align: right">
          <el-button v-if="view_type!='view'" type="success" @click="save" size="small">保存</el-button>
          <el-button type="primary" @click="goBack" size="small">放弃</el-button>
        </el-form-item>
      </el-form>

    </div>



  </WorkMain>

</template>

<script>
  import WorkMain from '@/models/public/WorkMain'
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'

  export default {
    name: "PageFieldEdit",
    describe:"页面编辑页面",
    components: {
      WorkMain,
      Treeselect
    },
    data(){
      return {
        view_type: 'view',//view edit new
        pageTitle:'',
        tableList:[],
        formData:{
          field_id:0,
          field_name:'',
          table_name_cn:'',
          field_name_cn:'',
          table_name:''
        },
        validateRules:{
        }
      }
    },
    methods:{
      getInfo(){
        this.BaseRequest({
          url: "crawler/pageMg/craDataField",
          method: 'get',
          params: {
            table_name: this.formData.table_name,
            field_name: this.formData.field_name
          }
        }).then(response => {
          this.formData = response
        })
      },
      save(){
        this.$refs['formData'].validate((valid) => {
          if (valid) {
            this.saveSub("crawler/pageMg/updateDataField","更新成功")
          } else {
            return false;
          }
        });
      },
      saveSub(url, msg){
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
          name: "dataFieldList"
        });
      }
    },
    mounted() {
      this.formData.table_name = this.$route.query.table_name
      this.formData.field_name = this.$route.query.field_name
      this.view_type = this.$route.query.view_type
      if(!this.formData.table_name && !this.formData.field_name){
        this.view_type=='view'
        return
      }
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
      //this.getFieldList()
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
