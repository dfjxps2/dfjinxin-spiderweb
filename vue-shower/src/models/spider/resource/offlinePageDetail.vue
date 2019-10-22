<template>
  <WorkMain :headerItems="['资源管理','离线页面查看','离线页面详情']">
    <el-row style="height: 100%;" :gutter="20">
      <el-col class="offline-page-context" :span="24">
        <el-row :gutter="20">
          <el-col class="align-left" :span="17">
            <el-button @click="$router.go(-1)" type="info">返回</el-button>
          </el-col>
        </el-row>

        <el-row class="search-result-area">
          <el-col :span="24">
            <el-table
              :data="searchResultDatas[job_id]"
              header-row-class-name="table-header-style"
              row-class-name="mini-font-size" stripe
              row-style="height:20px"
              style="width: 100%;">
              <el-table-column
                width="80"
                align="left"
                prop="page_id"
                label="编号">
              </el-table-column>
              <el-table-column
                prop="url"
                align="left"
                label="URL">
              </el-table-column>
              <el-table-column
                prop="crawl_time"
                align="left"
                label="抓取时间">
              </el-table-column>
              <el-table-column
                align="left"
                label="Solr Version">
                <template slot-scope="scope">
                  {{version}}
                </template>
              </el-table-column>
              <el-table-column
                prop="page_content"
                align="left"
                label="页面内容">
              </el-table-column>
              <el-table-column
                label="页面源码">
                <template slot-scope="scope">
                  <textarea rows='6' style='color:#400080'>{{scope.row.page_source}}</textarea>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </WorkMain>

</template>

<script>
  import WorkTablePager from '@/models/public/WorkTablePager'
  import WorkMain from '@/models/public/WorkMain'

  export default {
    name: "OfflinePageDetail",
    describe:"采集页面详情",
    components: {
      WorkTablePager,
      WorkMain
    },
    data(){
      return {
        currPageNum: 1,
        eachPageNum: 10,
        totalPage: 1,
        version:'',
        job_id:"",
        searchResultPageInfo:{
          'node_id':{
            'currPageNum': 1,
            'totalPage': 1
          }
        },
        searchResultDatas:{
          'node_id':[]
        }
      }
    },
    methods:{
      doSearch(){
        this.BaseRequest({
          url: "search/pageSearch/searchDetail",
          method: 'get',
          params: {'job_id':this.job_id,'version':this.version}
        }).then(response => {
          if(response){
            // console.log(response)
            this.searchResultPageInfo = {}
            this.searchResultDatas = {}
            if(response[this.job_id]){
              const jobResultDatas = response[this.job_id]
              const totalDataCount = response[this.job_id+'_num_found']
              const totalPage =  Math.ceil(totalDataCount/this.eachPageNum);

              this.searchResultPageInfo[this.job_id] = {'totalPage':totalPage,'currPageNum':1}
              this.searchResultDatas[this.job_id] = jobResultDatas
            }
          }
        })
      }
    },
    mounted() {
      this.version = this.$route.query.version
      this.job_id = this.$route.query.jobId

      this.doSearch()
    }
  }
</script>


<style rel="stylesheet/scss" lang="scss" scoped>
  @import "@/styles/table-page.scss";

  .search-row{
    margin:5px 0 0 0;
    width:100%;
    height:100%;
  }

  .offline-page-context{
    height:100%;
    overflow: auto;
  }


</style>
