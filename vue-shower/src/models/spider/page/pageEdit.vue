<template>
  <WorkMain :headerItems="['页面管理','页面编辑']">
    <div class="job-edit-root">
      <el-form :model="formData" :rules="validateRules" ref="formData" label-width="150px">
        <!--<el-form-item label="页面编号" prop="page_id">
          <el-input :disabled="view_type=='view' || view_type=='edit'" v-model.number="formData.page_id" ></el-input>
        </el-form-item>-->
        <el-form-item label="页面名称" prop="page_name">
          <el-input :disabled="view_type=='view'" v-model="formData.page_name" ></el-input>
        </el-form-item>
        <el-form-item a label="采集任务" prop="job_id">
          <el-select :disabled="view_type=='view' || view_type=='edit'" STYLE="width: 100%" v-model="formData.job_id" placeholder="请选择采集任务">
            <el-option :key="r.job_id" v-for="r in jobList" :label="r.job_name" :value="r.job_id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item a label="用户" prop="user_id">
          <el-select :disabled="view_type=='view' || view_type=='edit'" STYLE="width: 100%" v-model="formData.user_id" placeholder="请选择用户">
            <el-option :key="r.user_id" v-for="r in userList" :label="r.user_name" :value="r.user_id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="页面类型"  prop="page_type">
          <el-select :disabled="view_type=='view'" v-model="formData.page_type_cn" style="width:100%;" placeholder="请选择页面类型">
            <el-option  label="静态页面" value="0"></el-option>
            <el-option  label="动态页面" value="1"></el-option>
            <el-option  label="JSON页面" value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据类型"  prop="data_format">
          <el-select :disabled="view_type=='view'" v-model="formData.data_format_cn" style="width:100%;" placeholder="请选择数据类型">
            <el-option label="TABLE" value="0"></el-option>
            <el-option label="JSON" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否多页"  prop="is_multi_page">
          <el-select :disabled="view_type=='view'" v-model="formData.is_multi_page_cn" style="width:100%;" placeholder="是否多页">
            <el-option label="否" value="0"></el-option>
            <el-option label="是" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="翻页元素">
          <el-input :disabled="view_type=='view'" v-model="formData.paginate_element" ></el-input>
        </el-form-item>
        <el-form-item label="加载完成标志元素">
          <el-input :disabled="view_type=='view'" v-model="formData.load_indicator" ></el-input>
        </el-form-item>
        <el-form-item label="翻页间隔时间">
          <el-input :disabled="view_type=='view'" v-model.number="formData.page_interval" ></el-input>
        </el-form-item>
        <el-form-item label="最大页数">
          <el-input :disabled="view_type=='view'" v-model.number="formData.max_page_num" ></el-input>
        </el-form-item>
        <el-form-item label="保存页面源码">
          <el-input :disabled="view_type=='view'" v-model="formData.save_page_source" ></el-input>
        </el-form-item>
        <el-form-item label="数据文件">
          <el-input :disabled="view_type=='view'" v-model="formData.data_file" ></el-input>
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
          page_id:0,
          job_id:0,
          user_id:0,
          page_name:'',
          page_type: '',
          page_type_cn: '0',
          data_format: '',
          data_format_cn: '0',
          is_multi_page:'',
          is_multi_page_cn:'0',
          paginate_element:'',
          load_indicator:'',
          page_interval:0,
          max_page_num:0,
          save_page_source:0,
          data_file:''
        },
        validateRules:{
          page_id: [
            { required: true, message: '请输入页面编号', trigger: 'blur' },
            { type: 'number', message: '必须为数字'}
          ],
          job_id: [
            { required: true, message: '请选择采集任务', trigger: 'blur' }
          ],
          user_id: [
            { required: true, message: '请选择用户', trigger: 'blur' }
          ],
          page_name: [
            { required: true, message: '请输入页面名称', trigger: 'blur' }
          ],
          page_interval: [
            { required: false, message: '可为空', trigger: 'blur' },
            { type: 'number', message: '必须为数字'}
          ],
          max_page_num: [
            { required: false, message: '可为空', trigger: 'blur' },
            { type: 'number', message: '必须为数字'}
          ],
          save_page_source: [
            { required: false, message: '可为空', trigger: 'blur' },
            { type: 'number', message: '必须为数字'}
          ]
        }
      }
    },
    methods:{
      getInfo(){
        this.BaseRequest({
          url: "crawler/pageMg/craPageData",
          method: 'get',
          params: {
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
        //设置页面类型
        if (this.formData.page_type_cn === '0'){
          this.formData.page_type = 0
        } else if (this.formData.page_type_cn === '1') {
          this.formData.page_type = 1
        }else if (this.formData.page_type_cn === '2') {
          this.formData.page_type = 2
        }
        //设置数据类型
        if (this.formData.data_format_cn === '0'){
          this.formData.data_format = 0
        } else if (this.formData.data_format_cn === '1') {
          this.formData.data_format = 1
        }
        //设置是否多页
        if (this.formData.is_multi_page_cn === '0'){
          this.formData.is_multi_page = 0
        } else if (this.formData.is_multi_page_cn === '1') {
          this.formData.is_multi_page = 1
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
      getUserList(){
        const $this = this
        $this.BaseRequest({
          url: "sys/user/listAllUser",
          method: 'get',
          params: {
          }
        }).then(response => {
          $this.userList = response
        })
      },
      getJobList(){
        const $this = this
        $this.BaseRequest({
          url: "crawler/jobMg/listAllCraw",
          method: 'get',
          params: {
          }
        }).then(response => {
          $this.jobList = response
        })
      },
      goBack(){
        this.$router.push({
          name: "pageList"
        });
      }
    },
    mounted() {
      this.formData.page_id = this.$route.params.page_id
      this.formData.job_id = this.$route.params.job_id
      this.formData.user_id = this.$route.params.user_id
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
      this.getUserList()
      this.getJobList()
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
