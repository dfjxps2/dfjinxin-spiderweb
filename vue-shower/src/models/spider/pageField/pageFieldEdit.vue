<template>
  <WorkMain :headerItems="['采集页面管理','页面管理','页面字段编辑']">
    <div class="job-edit-root">
      <el-form :model="formData" :rules="validateRules" ref="formData" label-width="150px">
        <el-form-item label="字段名称" prop="field_name">
          <el-input :disabled="view_type=='view'" v-model="formData.field_name" ></el-input>
        </el-form-item>
        <el-form-item label="上级字段名称" prop="parent_field_id">
          <treeselect :disabled="view_type=='view'" v-model="formData.parent_field_id" :options="fieldList" />
        </el-form-item>
        <el-form-item label="数据类型"  prop="field_datatype">
          <el-select :disabled="view_type=='view'" v-model.number="formData.field_datatype.toString()" style="width:100%;" placeholder="请选择数据类型">
            <el-option label="字符串" value="0"></el-option>
            <el-option label="数字" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="合并元素数据"  prop="combine_field_value">
          <el-select :disabled="view_type=='view'" v-model.number="formData.combine_field_value.toString()" style="width:100%;" placeholder="请选择数据类型">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
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
        fieldList:[],
        formData:{
          field_id:0,
          page_id:0,
          job_id:0,
          user_id:0,
          field_name:'',
          field_datatype:'',
          parent_field_id:'',
          combine_field_value:''
        },
        validateRules:{
          field_name: [
            { required: true, message: '请输入字段名称', trigger: 'blur' }
          ]
        }
      }
    },
    methods:{
      getInfo(){
        this.BaseRequest({
          url: "crawler/pageMg/craFieldData",
          method: 'get',
          params: {
            field_id: this.formData.field_id,
            job_id: this.formData.job_id,
            page_id: this.formData.page_id,
            user_id: this.formData.user_id
          }
        }).then(response => {
          this.formData = response
          if(this.formData.paginate_element){
            this.formData.paginate_element = decodeURIComponent(this.formData.paginate_element)
          }
          if(this.formData.load_indicator){
            this.formData.load_indicator = decodeURIComponent(this.formData.load_indicator)
          }
        })
      },
      save(){
        this.$refs['formData'].validate((valid) => {
          if (valid) {
            if(this.view_type=='new'){
              this.savePage("crawler/pageMg/newSaveCrawlerPage","保存成功")
            }else if(this.view_type=='edit'){
              this.savePage("crawler/pageMg/updateCrawlerPage","更新成功")
            }
          } else {
            return false;
          }
        });
      },
      savePage(url, msg){
        if(this.formData.page_id === ''){
          this.formData.page_id = 0
        }
        this.BaseRequest({
          url: url,
          method: 'post',
          data: this.formData
        }).then(response => {
          this.Message.success(msg)
          this.$router.push({
            name: "pageList"
          });
        })
      },
      getFieldList(){
        const $this = this
        $this.BaseRequest({
          url: "crawler/pageMg/treePageField",
          method: 'get',
          params: {
            job_id:this.formData.job_id,
            page_id:this.formData.page_id,
            user_id:this.formData.user_id
          }
        }).then(response => {
          $this.fieldList = response
        })
      },
      updateFieldList(){

      },
      goBack(){
        this.$router.push({
          name: "pageFieldList",
          query: {
            "job_id": this.formData.job_id,
            "page_id": this.formData.page_id,
            "user_id": this.formData.user_id
          }
        });
      }
    },
    mounted() {
      this.formData.field_id = this.$route.query.field_id
      this.formData.page_id = this.$route.query.page_id
      this.formData.job_id = this.$route.query.job_id
      this.formData.user_id = this.$route.query.user_id
      this.view_type = this.$route.query.view_type
      if(!this.formData.field_id && !this.formData.page_id){
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
      this.getFieldList()
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
