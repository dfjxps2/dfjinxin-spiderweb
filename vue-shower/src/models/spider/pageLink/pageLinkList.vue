<template>
  <WorkMain :headerItems="['采集页面管理','页面管理','页面链接管理']">
    <el-form :rules="rules" :model="this"  ref="form">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-button @click="addRow()" type="info">新增</el-button>
        <el-button @click="save()" type="info">保存</el-button>
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
          <el-table-column label="序号"  type="index" width="60" fixed align="center"></el-table-column>
          <el-table-column align="left" width="450" label="链接定位表达式">
            <template slot-scope="scope">
              <el-form-item :prop="'dataList.' + scope.$index + '.link_locate_pattern'">
                <el-input type="textarea" autosize v-model="scope.row.link_locate_pattern"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="left" width="120" label="链接扩展表达式">
            <template slot-scope="scope">
              <el-form-item :prop="'dataList.' + scope.$index + '.link_ext_pattern'">
                <el-input v-model="scope.row.link_ext_pattern"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="left" width="120" label="下一页面编号">
            <template slot-scope="scope">
              <el-form-item :prop="'dataList.' + scope.$index + '.next_page_id'">
                <el-input v-model="scope.row.next_page_id"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column
            label="操作" width="100"
            align="left"
          >
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
  </WorkMain>

</template>

<script>
  import WorkTablePager from '@/models/public/WorkTablePager'
  import WorkMain from '@/models/public/WorkMain'
  import ElTreeSelect from '@/models/public/ElTreeSelect.vue'

  export default {
    name: "PageLinkList",
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
        dataTypes:{
          "字符串":0,
          "数字":1
        },
        yesNo:{
          "否":0,
          "是":1
        },
        formData:{
          link_id:0,
          page_id:0,
          job_id:0,
          user_id:0,
          next_page_id:2,
          link_locate_pattern:'',
          link_ext_pattern:'',
        },
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
          url: "crawler/pageMg/listPageLink",
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
            if(x.link_locate_pattern){
              x.link_locate_pattern = decodeURIComponent(x.link_locate_pattern)
            }
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
        /*let x = this.dataList.splice(i, 1)
        if(row.field_id){
          this.saveList.del.push(x)
        }*/
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
            url:'crawler/pageMg/deletePageLink',
            method:'get',
            params:{
              'link_id':row.link_id,
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
        const $this = this
        $this.dataList.forEach(x=>{
          if(x.field_id=='')
            $this.saveList.add.push(x)
          else
            $this.saveList.edit.push(x)
        })
        this.subSave(this.saveList,'crawler/pageMg/saveAllLinks')
      },
      subCheck(){
        let checkRow = true
        let checkResult = this.$refs["form"].validate((valid,model)=>{
          if(!valid)
            checkRow = false
        })||true
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
</style>
