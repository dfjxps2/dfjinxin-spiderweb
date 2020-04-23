<template>
  <WorkMain :headerItems="['采集页面管理','页面管理','页面字段管理']">
    <el-form :rules="rules" :model="this"  ref="form">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-input placeholder="请输入采集名称" v-model="job_name" style="width:180px" ></el-input>
        <el-button @click="getTableData(1)" >查询</el-button>
        <el-button @click="addRow()" type="primary">新增</el-button>
        <el-button @click="save()" type="primary">保存</el-button>
      </el-col>
      <el-col class="align-right" :span="7">
        <el-button @click="goBack" type="primary">返回页面列表</el-button>
      </el-col>
    </el-row>
    <el-row class="table-page-root-outoptions">
      <el-col :span="24">
        <el-table
          :data="dataList"
          ref="table"
          header-row-class-name="table-header-style"
          row-class-name="mini-font-size" stripe
          style="width: 100%;">
          <el-table-column label="序号" prop="field_id" width="80" fixed align="center"></el-table-column>
          <el-table-column align="center" width="120" label="字段名称">
            <template slot-scope="scope">
              <el-form-item :prop="'dataList.' + scope.$index + '.field_name'" :rules='rules.field_name'>
                <el-input v-model="scope.row.field_name"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="center" width="120" label="上级字段">
            <template slot-scope="scope">
              <el-form-item :prop="'dataList.' + scope.$index + '.parent_field_id'">
                <el-tree-select :checkedKeys="[scope.row.parent_field_id]" :data="fieldList" size="mini" :height="150"></el-tree-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="center" width="120" label="字段类型">
            <template slot-scope="scope">
              <el-form-item :prop="'dataList.' + scope.$index + '.field_datatype'">
                <el-select v-model="scope.row.field_datatype" style="width:100%;" placeholder="请选择字段类型">
                  <el-option :key="key" v-for="(key, value) in dataTypes" :label="value" :value="key"></el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="center" width="120" label="合并元素数据">
            <template slot-scope="scope">
              <el-form-item :prop="'dataList.' + scope.$index + '.combine_field_value'">
                <el-select v-model="scope.row.combine_field_value" style="width:100%;" placeholder="是否合并元素数据">
                  <el-option :key="key" v-for="(key, value) in yesNo" :label="value" :value="key"></el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="center" width="150" label="字段定位(个)">
            <template slot-scope="scope">
              <el-form-item>
                <el-input v-model="scope.row.pageFieldLocate.length" readonly="readonly">
                  <el-button slot="append" icon="el-icon-edit-outline" @click="openLocate(scope.$index,scope.row)"></el-button>
                </el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="100"
            align="left">
            <template slot-scope="scope">
              <el-button type="primary" @click="delField(scope.$index, scope.row)" size="mini" >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    </el-form>
    <!-- 分页 refreshData:点击页码上一页下一页时调用的方法、pageCount:总页数-->
    <WorkTablePager @refreshData="getTableData"
                    :pageCount="totalPage">
    </WorkTablePager>
    <!-- 字段定位 -->
    <el-dialog title="字段定位关系" :close-on-press-escape='false' :show-close='false'	:visible.sync="isLocateEditor" >
      <el-form :model="this" ref="lform" class="modal-form" label-position="right" label-width="0" style="margin:0;" >
        <el-table
          :data="locateList"
          ref="ltable"
          header-row-class-name="table-header-style"
          row-class-name="mini-font-size" stripe
          style="width: 100%;">
          <el-table-column label="定位编号" prop="field_locate_id" width="80"></el-table-column>
          <el-table-column align="left" width="350" label="定位表达式">
            <template slot-scope="scope">
              <el-form-item :prop="'locateList.' + scope.$index + '.field_locate_pattern'">
                <el-input type="textarea" autosize v-model="scope.row.field_locate_pattern"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="left" width="150" label="扩展表达式">
            <template slot-scope="scope">
              <el-form-item :prop="'locateList.' + scope.$index + '.field_ext_pattern'">
                <el-input v-model="scope.row.field_ext_pattern"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <el-row>
        <el-col :span="7">
          <el-button type="primary" @click="addLocate()">新增</el-button>
        </el-col>
        <el-col :span="17">
          <el-button @click="isLocateEditor = false">取消</el-button>
          <el-button type="primary" @click="addLocateList()">确定</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </WorkMain>

</template>

<script>
  import WorkTablePager from '@/models/public/WorkTablePager'
  import WorkMain from '@/models/public/WorkMain'
  import ElTreeSelect from '@/models/public/ElTreeSelect.vue'

  export default {
    name: "PageFieldList",
    describe:"页面管理页面",
    components: {
      WorkTablePager,
      WorkMain,
      ElTreeSelect
    },
    data(){
      return {
        dataList: [],
        currPageNum: 1,
        eachPageNum: 10,
        totalPage: 1,
        field_id:'',
        page_id:'',
        job_id: '',
        user_id: '',
        job_name: '',
        fieldList:[],
        saveList:{add:[],edit:[],del:[]},
        rules:{
          field_name:{ type:"string",required:true,message:"必填字段",trigger:"change"},
          field_locate_pattern:{ type:"string",required:true,message:"必填字段",trigger:"change"}
        },
        dataTypes:{
          "字符串":0,
          "数字":1
        },
        yesNo:{
          "否":0,
          "是":1
        },
        formData:{
          field_id:0,
          page_id:0,
          job_id:0,
          user_id:0,
          field_name:'',
          field_datatype:0,
          parent_field_id:0,
          combine_field_value:0,
          pageFieldLocate:[]
        },
        isLocateEditor:false,
        locateIndex:0,
        locateList:[]
      }
    },
    methods:{
      getTableData(pageNum){
        if (pageNum && pageNum !== '') {
          this.currPageNum = pageNum
        } else {
          pageNum = this.currPageNum
        }

        const $this = this
        this.BaseRequest({
          url: "crawler/pageMg/listPageField",
          method: 'get',
          params: {
            currPage: pageNum,
            pageSize: this.eachPageNum,
            job_id:this.job_id,
            page_id:this.page_id,
            user_id:this.user_id,
            job_name:this.job_name
          }
        }).then(response => {
          $this.dataList = response
          $this.dataList.forEach(x=>{
            x.pageFieldLocate.forEach(y =>{
              if(y.field_locate_pattern)
                y.field_locate_pattern = decodeURIComponent(y.field_locate_pattern)
              if(y.field_ext_pattern)
                y.field_ext_pattern = decodeURIComponent(y.field_ext_pattern)
            })
          })
          //$this.totalPage = response.totalPage
        })

      },
      viewEdit(row,viewType){
        if(row == null){
          row = {field_id:'',job_id:this.job_id,page_id:this.page_id,user_id:this.user_id}
        }
        this.$router.push({
          name: "pageFieldEdit",
          query: {
            "field_id": row.field_id,
            "job_id": row.job_id,
            "page_id": row.page_id,
            "user_id": row.user_id,
            "view_type": viewType
          }
        });
      },
      delField(i,row){
        //let x = this.dataList.splice(i)
        this.$confirm('确定删除该任务？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          dangerouslyUseHTMLString:true,
          type: 'warning'
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: '删除中',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });

          this.BaseRequest({
            url:'crawler/pageMg/deletePageField',
            method:'get',
            params:{
              'field_id':row.field_id,
              'page_id':row.page_id,
              'job_id':row.job_id,
              'user_id':row.user_id,
            }
          }).then(response=>{
            this.Message.success("删除成功")
            loading.close()
            this.getTableData(1)
          }).catch(error=>{
            loading.close()
            this.Message.error("删除失败"+error)
          })
        }).catch(() => {
        });
      },


      goBack(){
        this.$router.push({
          name: "pageList"
        });
      },
      getFieldList(){
        const $this = this
        $this.BaseRequest({
          url: "crawler/pageMg/treePageField",
          method: 'get',
          params: {
            job_id:this.job_id,
            page_id:this.page_id,
            user_id:this.user_id
          }
        }).then(response => {
          $this.fieldList = response
        })
      },
      addRow(){
        let addObj = Object.assign({},this.formData)
        addObj.pageFieldLocate = Object.assign({}, this.formData.pageFieldLocate)
        this.dataList.push(addObj)
        //updateFieldList()
      },
      updateFieldList(obj){
        if(this.fieldList.length == 0){
          this.fieldList.push({id:'',label:'',parent:'',children:[]})
        }
      },
      save(){
        if(!this.subCheck()){
          return false
        }
        this.subSave(this.dataList,'crawler/pageMg/saveAllFields')
      },
      subCheck(){
        let checkRow = true
        let checkResult = this.$refs["form"].validate((valid,model)=>{
          if(!valid)
            checkRow = false
        })||true
        if(this.dataList.length<=0){
          this.Message.error("必须填写1个以上的字段");
          return false;
        }
        for(let i = 0; i < this.dataList.length; i++){
          let n = this.dataList[i].pageFieldLocate;
          if(n.length<=0 || n.length == null){
            this.Message.error("字段"+this.dataList[i].field_name+"必须填写1个以上的字段定位");
            return false;
          }
        }
        return checkResult && checkRow
      },
      subSave(sendData,sendUrl){
        const $this = this
        const loading = $this.$loading({
          lock: true,
          text: '保存中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        $this.BaseRequest({
          url:sendUrl,
          method:'post',
          data:sendData
        }).then(response=>{
          loading.close()
          $this.Message.success("保存成功")
          $this.getTableData(1)
          $this.getFieldList()
        }).catch(error=>{
          loading.close()
          $this.Message.success("保存失败:"+error)
        });
      },
      openLocate(i, row){
        this.isLocateEditor = true
        this.locateIndex = i
        this.locateList = Object.assign([],row.pageFieldLocate)
      },
      addLocate(){
        let newObj = {
          field_locate_pattern:'',
          field_ext_pattern:'text',
          field_locate_id:0
        }
        this.locateList.push(newObj)
      },
      addLocateList(){
        this.dataList[this.locateIndex].pageFieldLocate = Object.assign([],this.locateList)
        this.isLocateEditor = false
      }
    },
    mounted() {
      this.page_id = this.$route.query.page_id
      this.job_id = this.$route.query.job_id
      this.user_id = this.$route.query.user_id
      this.formData.job_id = this.job_id
      this.formData.page_id = this.page_id
      this.formData.user_id = this.user_id
      this.getTableData(1)
      this.getFieldList()
    }
  }
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "@/styles/table-page.scss";

  .search-row{
    margin:5px 0 0 0;
  }
  .el-textarea__inner{
    height:40px;
  }
</style>
