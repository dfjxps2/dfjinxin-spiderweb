<template>
  <WorkMain :headerItems="['资源管理','信息存储']">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-input placeholder="请输入表名称" v-model="table_name" style="width:180px" ></el-input>
        <el-button @click="getTableData(1)" >查询</el-button>
        <el-button @click="viewAdd(null,'new')" type="primary">新增</el-button>
        <el-button @click="viewNewTable()" type="primary">创建实体表</el-button>
      </el-col>
    </el-row>
    <el-row class="table-page-root-outoptions">
      <el-col :span="24">
        <el-table
          :data="dataList"
          header-row-class-name="table-header-style"
          row-class-name="mini-font-size" stripe
          style="width: 100%;">
          <el-table-column label="字段序号" prop="field_id" width="100" fixed align="center"></el-table-column>
          <el-table-column label="表名" prop="table_name" align="left" width="150"></el-table-column>
          <el-table-column label="表中文名" prop="table_name_cn" align="left" width="200"></el-table-column>
          <el-table-column label="字段名" prop="field_name" align="left" width="150"></el-table-column>
          <el-table-column label="字段中文名" prop="field_name_cn" align="left" width="200"></el-table-column>
          <el-table-column
            label="操作"
            align="left"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="viewEdit(scope.row,'view')" size="mini" >查看</el-button>
              <el-button type="primary" @click="viewEdit(scope.row,'edit')" size="mini" >编辑</el-button>
              <el-button type="primary" @click="delField(scope.$index, scope.row)" size="mini" >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
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
        table_name: ''
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
          url: "crawler/pageMg/pagingDataField",
          method: 'get',
          params: {
            currPage: pageNum,
            pageSize: this.eachPageNum,
            table_name:this.table_name
          }
        }).then(response => {
          $this.dataList = response.dataList
          $this.totalPage = response.totalPage
        })
      },
      viewAdd(row,viewType){
        this.$router.push({
          name: "dataFieldAdd",
          query: {
            "view_type": viewType
          }
        });
      },
      viewEdit(row,viewType){
        if(row == null){
          row = {table_name:'',field_name:''}
        }
        this.$router.push({
          name: "dataFieldEdit",
          query: {
            "table_name": row.table_name,
            "field_name": row.field_name,
            "view_type": viewType
          }
        });
      },
      delField(i,row){
        this.$confirm('确定删除该字段？', '提示', {
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
            url:'crawler/pageMg/deleteDataField',
            method:'get',
            params:{
              "table_name": row.table_name,
              "field_name": row.field_name
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
      viewNewTable(){
        this.$router.push({
          name: "dataFieldSql"
        });
      }
    },
    mounted() {
      this.getTableData(1)
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
