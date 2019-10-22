<template>
  <WorkMain :headerItems="['采集页面管理','页面管理']">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-input placeholder="请输入页面编号" v-model="page_id" style="width:180px" ></el-input>
        <el-input placeholder="请输入采集名称" v-model="job_name" style="width:180px" ></el-input>
        <el-button @click="getTableData(1)" >查询</el-button>
        <el-button @click="viewEdit(null,'new')" type="primary">新增</el-button>
      </el-col>
    </el-row>
    <el-row class="table-page-root-outoptions">
      <el-col :span="24">
        <el-table
          :data="dataList"
          header-row-class-name="table-header-style"
          row-class-name="mini-font-size" stripe
          style="width: 100%;">
          <el-table-column
            prop="job_id" width="80"
            align="center"
            label="采集编号">
          </el-table-column>
          <el-table-column
            prop="job_name"
            align="center" width="180"
            label="采集名称">
          </el-table-column>
          <el-table-column width="100"
            prop="user_name_cn"
            align="center"
            label="用户">
          </el-table-column>
          <el-table-column
            prop="page_id" width="80"
            align="center"
            label="页面编号">
          </el-table-column>
          <el-table-column
            prop="page_name"
            align="center" width="180"
            label="页面名称">
          </el-table-column>
          <el-table-column
            prop="max_page_num"
            align="center" width="120"
            label="最大采集数量">
          </el-table-column>
          <el-table-column
            label="操作"
            align="left"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="viewEdit(scope.row,'edit')" size="mini" >编辑</el-button>
              <el-button type="primary" @click="delPage(scope.row)" size="mini" >删除</el-button>
              <el-button type="primary" @click="viewGo(scope.row,'pageFieldList')" size="mini" >字段列表</el-button>
              <el-button type="primary" @click="viewGo(scope.row,'pageLinkList')" size="mini" >链接列表</el-button>
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

  export default {
    name: "PageList",
    describe:"页面管理页面",
    components: {
      WorkTablePager,
      WorkMain
    },
    data(){
      return {
        dataList: [],
        currPageNum: 1,
        eachPageNum: 10,
        totalPage: 1,
        page_id:'',
        job_id: '',
        job_name: '',
        user_id: '',
        rowStyle:{height:'20px'}
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
          url: "crawler/pageMg/pagingCrawlerPage",
          method: 'get',
          params: {
            currPage: pageNum,
            pageSize: this.eachPageNum,
            job_name:this.job_name,
          }
        }).then(response => {
          $this.dataList = response.dataList
          $this.totalPage = response.totalPage
        })

      },
      viewEdit(row,viewType){
        if(row == null){
          row = {job_id:'',page_id:'',user_id:''}
        }
        this.$router.push({
          name: "pageEdit",
          params: {
            "job_id": row.job_id,
            "page_id": row.page_id,
            "user_id": row.user_id,
            "view_type": viewType
          }
        });
      },
      viewGo(row,viewName){
        this.$router.push({
          name: viewName,
          query: {
            "job_id": row.job_id,
            "page_id": row.page_id,
            "user_id": row.user_id,
          }
        });
      },
      delPage(row){
        if(row==null||row.page_id==null){
          this.Error("请先选择要删除的页面")
          return
        }
        this.$confirm('确定删除该页面？', '提示', {
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
            url:'crawler/pageMg/deleteCrawlerPage',
            method:'get',
            params:{
              "job_id": row.job_id,
              "page_id": row.page_id,
              "user_id": row.user_id,
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
</style>
