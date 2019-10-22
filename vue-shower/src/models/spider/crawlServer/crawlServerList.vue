<template>
  <WorkMain :headerItems="['资源管理','采集服务器管理']">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-input placeholder="请输入主机名称" v-model="host_name" style="width:180px" ></el-input>
        <el-button @click="getTableData(1)" type="success">查询</el-button>
        <el-button @click="viewEdit(null,'new')" type="info">新增</el-button>
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
            prop="host_id" width="80"
            align="left"
            label="主机编号">
          </el-table-column>
          <el-table-column
            prop="host_name"
            align="left" width="180"
            label="主机名称">
          </el-table-column>
          <el-table-column width="100"
            prop="host_ip"
            align="left"
            label="主机IP">
          </el-table-column>
          <el-table-column
            prop="host_status" width="80"
            align="left"
            label="在线状态">
          </el-table-column>
          <el-table-column
            label="操作"
            align="left"
          >
            <template slot-scope="scope">
              <el-button type="primary" @click="viewEdit(scope.row,'view')" size="mini" >查看</el-button>
              <el-button type="primary" @click="viewEdit(scope.row,'edit')" size="mini" >编辑</el-button>
              <el-button type="primary" @click="delPage(scope.row)" size="mini" >删除</el-button>
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
    name: "CrawlServerList",
    describe:"采集服务器管理页面",
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
        host_name: ''
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
          url: "/crawler/server/pagingServer",
          method: 'get',
          params: {
            currPage: pageNum,
            pageSize: this.eachPageNum,
            host_name:this.host_name,
          }
        }).then(response => {
          $this.dataList = response.dataList
          $this.totalPage = response.totalPage
        })

      },
      viewEdit(row,viewType){
        if(row == null){
          row = {host_id:0}
        }
        this.$router.push({
          name: "crawlServerEdit",
          params: {
            "host_id": row.host_id,
            "view_type": viewType
          }
        });
      },
      delPage(row){
        this.$confirm('确定删除该主机？', '提示', {
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
            url:'crawler/server/delServer',
            method:'get',
            params:{
              "host_id": row.host_id
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
