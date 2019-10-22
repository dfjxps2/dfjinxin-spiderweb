<template>
  <WorkMain :headerItems="['资源管理','代理服务器管理']">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-input placeholder="请输入代理名称" v-model="proxy_server_name" style="width:180px" ></el-input>
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
            prop="proxy_server_id" width="120"
            align="left"
            label="代理服务器编号">
          </el-table-column>
          <el-table-column
            prop="proxy_server_name"
            align="left" width="180"
            label="代理服务器名称">
          </el-table-column>
          <el-table-column width="120"
            prop="proxy_server_ip"
            align="left"
            label="代理服务器IP">
          </el-table-column>
          <el-table-column
            prop="proxy_user_name" width="150"
            align="left"
            label="代理服务器用户名">
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
    describe:"代理服务器管理页面",
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
        proxy_server_name: ''
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
          url: "/crawler/proxyServer/pagingProxyServers",
          method: 'get',
          params: {
            currPage: pageNum,
            pageSize: this.eachPageNum,
            proxy_server_name:this.proxy_server_name,
          }
        }).then(response => {
          $this.dataList = response.dataList
          $this.totalPage = response.totalPage
        })

      },
      viewEdit(row,viewType){
        if(row == null){
          row = {proxy_server_id:0}
        }
        this.$router.push({
          name: "proxyServerEdit",
          params: {
            "proxy_server_id": row.proxy_server_id,
            "view_type": viewType
          }
        });
      },
      delPage(row){
        this.$confirm('确定删除该代理服务器？', '提示', {
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
            url:'crawler/proxyServer/delServer',
            method:'get',
            params:{
              "proxy_server_id": row.proxy_server_id
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
