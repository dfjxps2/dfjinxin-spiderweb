<template>
  <WorkMain :headerItems="['资源管理','信息存储','创建实体表']">
    <el-form :model="this" ref="formData">
    <el-row>
      <el-col :span="24">
        <el-form-item label-width="0" style="text-align: right">
          <el-button v-if="fieldList.length >0" type="success" @click="save" size="small">创建表</el-button>
          <el-button v-if="fieldList.length >0" type="info" @click="showSql" size="small">查看sql</el-button>
          <el-button v-if="fieldList.length >0" type="success" @click="addRow" size="small">新增列</el-button>
          <el-button type="primary" @click="goBack" size="small">放弃</el-button>
        </el-form-item>
      </el-col>
      <el-col :span="16">
        <el-form-item label-width="60px" label="表名" prop="formData.table_name">
          <el-select STYLE="width: 100%" v-model="formData.table_name" placeholder="请选择采集表" @change="linkTableName" size="small">
            <el-option :key="r.data_file" v-for="r in tableList" :label="r.data_file" :value="r.data_file"></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="5">
        <el-form-item label-width="0">
          <el-checkbox label="强制建表" v-model="formData.is_drop"></el-checkbox>
        </el-form-item>
        <el-form-item label-width="0">
          <el-checkbox label="动态表" v-model="formData.is_dynamic"></el-checkbox>
        </el-form-item>
      </el-col>
      <el-col :span="3">
        <el-form-item label-width="0">
          <el-checkbox label="原表新增" v-model="formData.is_original"></el-checkbox>
        </el-form-item>
      </el-col>
      <el-col :span="24">
        <el-table
          :data="fieldList"
          ref="table"
          header-row-class-name="table-header-style"
          row-class-name="mini-font-size" stripe
          style="width: 100%;">
          <el-table-column width="40" align="left">
            <template slot-scope="scope">
              <div @click="sortData(scope.$index, -1)" class="updown"><span><i class="el-icon-caret-top"></i></span></div>
              <div @click="sortData(scope.$index, 1)" class="updown"><span><i class="el-icon-caret-bottom"></i></span></div>
            </template>
          </el-table-column>
          <el-table-column align="left" width="150" label="字段名" prop="field_name">
            <template slot-scope="scope">
              <el-form-item :prop="'fieldList.' + scope.$index + '.field_name'" label-width="0" style="width:100%;">
                <el-input v-model="scope.row.field_name" size="small" style="width:100%;"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="left" width="150" label="数据类型">
            <template slot-scope="scope">
              <el-form-item :prop="'fieldList.' + scope.$index + '.data_type'" label-width="0" style="width:100%;">
                <el-select v-model="scope.row.data_type" size="small" style="width:100%;" placeholder="请选择数据类型">
                  <el-option :key="v" v-for="v in dataTypes" :label="v" :value="v"></el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="left" width="100" label="数据长度">
            <template slot-scope="scope">
              <el-form-item :prop="'fieldList.' + scope.$index + '.data_size'" label-width="0" style="width:100%;">
                <el-input v-model="scope.row.data_size" size="small" style="width:100%;"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="left" width="100" label="数据精度">
            <template slot-scope="scope">
              <el-form-item :prop="'fieldList.' + scope.$index + '.data_scale'" label-width="0" style="width:100%;">
                <el-input v-model="scope.row.data_scale" size="small" style="width:100%;"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="left" width="60" label="可空">
            <template slot-scope="scope">
              <el-form-item :prop="'fieldList.' + scope.$index + '.nullable'" label-width="0" style="width:100%;">
                <el-checkbox  v-model="scope.row.nullable" @change="scope.row.primary_key = false"></el-checkbox>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="left" width="120" label="默认值">
            <template slot-scope="scope">
              <el-form-item :prop="'fieldList.' + scope.$index + '.data_default'" label-width="0" style="width:100%;">
                <el-input v-model="scope.row.data_default" size="small" style="width:100%;"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="left" width="80" label="主键">
            <template slot-scope="scope">
              <el-form-item :prop="'fieldList.' + scope.$index + '.primary_key'" label-width="0" style="width:100%;">
                <el-checkbox v-model="scope.row.primary_key" @change="scope.row.nullable = false"></el-checkbox>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    </el-form>
  </WorkMain>

</template>

<script>
  import WorkMain from '@/models/public/WorkMain'

  export default {
    name: "DataFieldSql",
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
        newFildList:[],
        dataTypes:['varchar','datetime','decimal','int','bigint','blob','bool','char','date','mediumtext','text','timestamp'],
        formData:{
          is_original:false,
          is_drop:false,
          is_dynamic:true,
          table_name:''
        }
      }
    },
    methods:{
      save(){
        this.$refs['formData'].validate((valid) => {
          if (valid) {
            if (this.formData.is_original === true) {
              this.saveSubs("crawler/pageMg/createTable","执行成功")
            }else{
              this.saveSub("crawler/pageMg/createTable","执行成功")
            }
          } else {
            return false;
          }
        });
      },
      saveSub(url, msg){
        const $this = this
        let dataList = Object.assign({},this.formData)
        dataList.dataField = this.fieldList
        this.BaseRequest({
          url: url,
          method: 'post',
          data: dataList
        }).then(response => {
          if(response)
            this.$message({showClose: true,message:response,type:'error'})
          else
            this.Message.success(msg)
        })
      },
      saveSubs(url, msg){
        const $this = this
        let dataList = Object.assign({},this.formData)
        dataList.dataField = this.newFildList
        this.BaseRequest({
          url: url,
          method: 'post',
          data: dataList
        }).then(response => {
          if(response)
            this.$message({showClose: true,message:response,type:'error'})
          else
            this.Message.success(msg)
        })
      },
      showSql(){
        this.$refs['formData'].validate((valid) => {
          if (valid) {
            this.showSqlSub("crawler/pageMg/createTableSql")
          } else {
            return false;
          }
        });
      },
      showSqlSub(url){
        const $this = this
        let dataList = Object.assign({},this.formData)
        if (this.formData.is_original === true){
          dataList.dataField = this.newFildList
        } else {
          dataList.dataField = this.fieldList
        }
        this.BaseRequest({
          url: url,
          method: 'post',
          data: dataList
        }).then(response => {
          this.$alert(this.formatSql(response),'查看sql',{
            dangerouslyUseHTMLString: true
          });
        })
      },
      formatSql(str){
        str = decodeURIComponent(str)
        str = str.replace(/\n/g,"<br>").replace(/\s/g,"&nbsp;");
        return str
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
            noadd:0
          }
        }).then(response => {
          if(response){
            $this.fieldList = response.map((x, i) => {
              return {
                field_name: x.field_name,
                data_type: x.field_datatype == 0 ? 'varchar' : 'decimal',
                data_size: x.field_datatype == 0 ? 255 : 16,
                data_scale:x.field_datatype == 0 ? null : 2,
                data_default:null,
                nullable:i==0?false:true,
                primary_key:i==0?true:false,
                primary_index:false
              }
            })
          }
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
      },
      addRow(){
        let newObj = {
          field_name: 'col'+(this.fieldList.length + 1),
          data_type: 'varchar',
          data_size: 255,
          data_scale:null,
          data_default:null,
          nullable:true,
          primary_key:false,
          primary_index:false
        };
        let addObj = Object.assign({},newObj)
        this.fieldList.push(addObj)
        this.newFildList.push(addObj)
      },
      sortData(index, n){
        if(index+n>= this.fieldList.length || index+n<0)
          return

        let a = this.fieldList[index]
        let b = this.fieldList[index + n]
        let tmp = a
        this.$set(this.fieldList, index, b)
        this.$set(this.fieldList, index + n, tmp)
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
  .updown{cursor:pointer;}
</style>
