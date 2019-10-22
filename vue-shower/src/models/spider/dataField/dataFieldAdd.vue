<template>
  <WorkMain :headerItems="['资源管理','信息存储','信息字段编辑']">
    <el-form :model="this" ref="formData">
    <el-row>
      <el-col :span="17">
        <el-form-item label-width="100px" label="表名" prop="formData.table_name">
          <el-select STYLE="width: 100%" v-model="formData.table_name" placeholder="请选择采集表" @change="linkTableName">
            <el-option :key="r.data_file" v-for="r in tableList" :label="r.data_file" :value="r.data_file"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label-width="150px" label="表中文名" prop="formData.table_name_cn">
          <el-input :disabled="view_type=='view'" v-model="formData.table_name_cn" ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-table
          :data="fieldList"
          ref="table"
          header-row-class-name="table-header-style"
          row-class-name="mini-font-size" stripe
          style="width: 100%;">
          <el-table-column align="left" width="150" label="字段序号" prop="field_id"></el-table-column>
          <el-table-column align="left" width="250" label="字段名" prop="field_name"></el-table-column>
          <el-table-column align="left" width="300" label="字段中文名">
            <template slot-scope="scope">
              <el-form-item :prop="'fieldList.' + scope.$index + '.field_name_cn'" label-width="0" style="width:100%;">
                <el-input v-model="scope.row.field_name_cn" size="small" style="width:100%;"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
      <el-col :span="24">
        <el-form-item label-width="0" style="text-align: right">
          <el-button v-if="view_type=='new' && fieldList.length >0" type="success" @click="save" size="small">保存</el-button>
          <el-button type="primary" @click="goBack" size="small">放弃</el-button>
        </el-form-item>
      </el-col>
    </el-row>
    </el-form>
  </WorkMain>

</template>

<script>
  import WorkMain from '@/models/public/WorkMain'

  export default {
    name: "DataFieldAdd",
    describe:"信息新增页面",
    components: {
      WorkMain
    },
    data(){
      return {
        view_type: 'view',//view edit new
        pageTitle:'',
        tableList:[],
        fieldList:[],
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
      save(){
        this.$refs['formData'].validate((valid) => {
          if (valid) {
            this.saveSub("crawler/pageMg/newSaveDataField","保存成功")
          } else {
            return false;
          }
        });
      },
      saveSub(url, msg){
        const $this = this
        let dataList = $this.fieldList.map(x=>{
          return {
            table_name : $this.formData.table_name,
            table_name_cn : $this.formData.table_name_cn,
            field_id : x.field_id,
            field_name : x.field_name,
            field_name_cn : x.field_name_cn
          }
        })
        this.BaseRequest({
          url: url,
          method: 'post',
          data: dataList
        }).then(response => {
          this.Message.success(msg)
          this.$router.push({
            name: "dataFieldList"
          });
        })
      },
      goBack(){
        this.$router.push({
          name: "dataFieldList"
        });
      },
      getTableList(){
        const $this = this
        $this.BaseRequest({
          url: "crawler/pageMg/listCrawlerPage",
          method: 'get',
          params: {
          }
        }).then(response => {
          if(response){
            $this.tableList = response.filter(x => {return x.data_file!='' })
          }

        })
      },
      getFieldList(row){
        const $this = this
        this.BaseRequest({
              url: "crawler/pageMg/listAllTableField",
              method: 'get',
              params: {
                job_id:row.job_id,
                page_id:row.page_id,
                user_id:row.user_id,
                noadd:1
              }
        }).then(response => {
          if(response){
            $this.fieldList = response.map(x => {
              x.field_name_cn = ''
              return x
            })
          }
          console.log($this.fieldList)
        })
      },
      linkTableName(val){
        const $this = this
        $this.tableList.forEach(x=>{
          if(x.data_file == val){
            $this.getFieldList(x)
            return true
          }
        })
      }
    },
    mounted() {
      this.view_type = this.$route.query.view_type

      this.pageTitle = "查看"
      this.getTableList()
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
  .el-form-item{margin-bottom:0;}
  .el-table td, .el-table th{padding:8px 0;}
</style>
