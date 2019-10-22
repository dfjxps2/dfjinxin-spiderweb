<template>
  <WorkMain :headerItems="['资源管理','离线页面查看']">
    <el-row style="height: 100%;" :gutter="20">
      <el-col class="offline-page-tree" :span="4">
        <el-tree ref="jobTree"
          :props="treeProps"
          :data="treeData"
          show-checkbox
        >
        </el-tree>
      </el-col>

      <el-col class="offline-page-context" :span="20">
        <el-row>
          <el-col :span="24">
            <el-form ref="detailSearch" label-width="80px">
              <el-form-item label="搜索">
                <el-col :span="9">
                  <el-input placeholder="请输入检索内容" v-model="searchContext.search_content" ></el-input>
                </el-col>
                <el-col class="line" :span="2">&nbsp;</el-col>
                <el-col :span="9">
                  <el-button @click="doSearch" type="success">查询</el-button>
                  <el-button @click="showDetailSeach = !showDetailSeach" type="success">{{showDetailSeach?'收起高级搜索':'高级搜索'}}</el-button>
                </el-col>
              </el-form-item>
            </el-form>

            <el-form v-if="showDetailSeach" ref="detailSearch" label-width="80px">
              <el-form-item label="URL" >
                <el-col :span="9">
                  <el-input  v-model="searchContext.url" ></el-input>
                </el-col>
              </el-form-item>
              <el-form-item label="抓取时间">
                <el-col :span="4">
                  <el-date-picker type="date" placeholder="抓取时间" v-model="searchContext.jobStartDate" style="width: 100%;"></el-date-picker>
                </el-col>
                <el-col class="line" :span="1">至</el-col>
                <el-col :span="4">
                  <el-date-picker placeholder="选择时间" v-model="searchContext.jobEndData" style="width: 100%;"></el-date-picker>
                </el-col>
              </el-form-item>
              <el-form-item a label="页面编号" >
                <el-col :span="9">
                  <el-input  v-model="searchContext.pageId" ></el-input>
                </el-col>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>

        <el-row class="search-result-area">
          <el-col :span="24">
            <el-tabs v-model="jobTabOrder" type="border-card" >
              <!--<el-tab-pane v-for="(resultDatas,key) in searchResultDatas" :label="treeObjTmp[key]?treeObjTmp[key].label:'未知'" >-->
              <el-tab-pane v-for="jobId in searchContext.jobIdList" :label="treeObjTmp[jobId]?treeObjTmp[jobId].label:'未知'" >
                <el-table
                  :data="searchResultDatas[jobId]"
                  header-row-class-name="table-header-style"
                  row-class-name="mini-font-size" stripe
                  row-style="height:20px"
                  style="width: 100%;">
                  <el-table-column width="80"
                    align="left"
                    label="页面编号">
                    <template slot-scope="scope">
                      <p @click=showDetail(jobId,scope.row._version_)>{{scope.row.page_id}}</p>
                    </template>
                  </el-table-column>
                  <el-table-column
                    prop="url" width="80"
                    :show-overflow-tooltip="true"
                    align="left"
                    label="URL">
                  </el-table-column>
                  <el-table-column
                    prop="crawl_time"
                    :show-overflow-tooltip="true"
                    align="left" width="180"
                    label="抓取时间">
                  </el-table-column>
                  <el-table-column
                    prop="page_content"
                    :show-overflow-tooltip="true"
                    align="left"
                    label="页面内容">
                  </el-table-column>
                  <el-table-column
                    :show-overflow-tooltip="true"
                    prop="page_source" width="80"
                    label="页面源码">
                  </el-table-column>
                </el-table>

                <!-- 分页 refreshData:点击页码上一页下一页时调用的方法、pageCount:总页数-->
                <WorkTablePager @refreshData="refreshJobDatas"
                                :pageCount="searchResultPageInfo[jobId].totalPage">
                </WorkTablePager>

              </el-tab-pane>
            </el-tabs>
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
    name: "OfflinePage",
    describe:"采集管理页面",
    components: {
      WorkTablePager,
      WorkMain
    },
    data(){
      return {
        currPageNum: 1,
        eachPageNum: 10,
        totalPage: 1,
        jobTabOrder:'',
        treeProps: {
          label: 'label',
          children: 'children'
        },
        treeData:[],
        treeObjTmp:{},
        showDetailSeach:false,
        searchContext:{
          jobIdList:[],
          pagingMap:{},
          rows:null,
          search_content:null,
          jobStartDate:null,
          jobEndData:null,
          pageId:null
        },
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
        this.searchContext.rows = this.eachPageNum
        this.searchContext.jobIdList = []

        const allCheckNodes = this.$refs.jobTree.getCheckedNodes()


        if(allCheckNodes){
          allCheckNodes.forEach(checkNOde=>{
            if(checkNOde['children']&&checkNOde['children'].length>0){

            }else{
              this.searchContext.jobIdList.push(checkNOde['id'])
              // this.searchContext.jobIdList.push("8")

              this.searchContext.pagingMap[checkNOde['id']] = 0
              this.searchContext.pagingMap["8"] = 0
            }
          })
        }

        this.BaseRequest({
          url: "search/pageSearch/doSearch",
          method: 'post',
          data: this.searchContext
        }).then(response => {
          if(response){
            console.log(response)
            this.searchResultPageInfo = {}
            this.searchResultDatas = {}
            this.searchContext.jobIdList.forEach(searchJobId=>{
              console.log(response[searchJobId])
              if(response[searchJobId]){
                const jobResultDatas = response[searchJobId]
                const totalDataCount = response[searchJobId+'_num_found']
                const totalPage =  Math.ceil(totalDataCount/this.eachPageNum);

                this.searchResultPageInfo[searchJobId] = {'totalPage':totalPage,'currPageNum':1}
                this.searchResultDatas[searchJobId] = jobResultDatas

              }
            })
          }
        })
      },

      getTreeData(){
        // search/pageSearch/listJobAndTypes
        const loading = this.$loading({
          lock: true,
          text: '查询中',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });

        this.BaseRequest({
          url: "search/pageSearch/listJobAndTypes",
          method: 'get'
        }).then(response => {
          loading.close()
          if(response){
            const treeArray = []
            const treeObjTmp = {}

            response.forEach(jobAndTypesData=>{
              const idVal = jobAndTypesData.id
              const superId = jobAndTypesData.super_id
              const name = jobAndTypesData.name?jobAndTypesData.name:idVal
              if(superId){
                if(superId==idVal){//根节点
                  if(treeObjTmp[superId]){
                    treeObjTmp[superId].id = idVal
                    treeObjTmp[superId].label = name
                  }else{
                    const rootNode = {'label':name,'id':idVal,'children':[]}
                    treeObjTmp[idVal] = rootNode
                    treeArray.push(rootNode)
                  }
                }else{
                  const sonNODE = {'label':name,'id':idVal,'children':[]}
                  if(treeObjTmp[superId]){
                    treeObjTmp[superId].children.push(sonNODE)
                    treeObjTmp[idVal] = sonNODE
                  }else{
                    const rootNode = {'label':"",'id':"",'children':[]}
                    rootNode.children.push(sonNODE)
                    treeObjTmp[superId] = rootNode
                    treeArray.push(rootNode)
                  }
                }
              }else{//跟节点
                if(treeObjTmp[superId]){
                  treeObjTmp[superId].id = idVal
                  treeObjTmp[superId].label = name
                }else{
                  const rootNode = {'label':name,'id':idVal,'children':[]}
                  treeObjTmp[idVal] = rootNode
                  treeArray.push(rootNode)
                }
              }
            })
            this.treeData = treeArray
            this.treeObjTmp = treeObjTmp
            // console.log(treeArray)
          }
        })
      },
      showDetail(jobId,version){
        this.$router.push({
          name: "offlinePageDetail",
          query: {
            "jobId": jobId,
            "version": version
          }
        });
      },
      refreshJobDatas(pageNum){
        const searchContext = {
          jobIdList:[],
          pagingMap:{},
          rows:this.eachPageNum,
          search_content:this.searchContext.search_content,
          jobStartDate:this.searchContext.jobStartDate,
          jobEndData:this.searchContext.jobEndData,
          pageId:this.searchContext.pageId
        }
        const jobId = this.searchContext.jobIdList[this.jobTabOrder]
        searchContext.jobIdList.push(jobId)
        searchContext.pagingMap[jobId] = pageNum

        this.BaseRequest({
          url: "search/pageSearch/doSearch",
          method: 'post',
          data: searchContext
        }).then(response => {
          if(response){
            const searchResultDatasTMp = this.searchResultDatas
            this.searchResultDatas = null
            const jobResultDatas = response[jobId]
            const totalDataCount = response[jobId+'_num_found']
            const totalPage =  Math.ceil(totalDataCount/this.eachPageNum);
            searchResultDatasTMp[jobId] = jobResultDatas
            this.searchResultDatas = searchResultDatasTMp
            this.searchResultPageInfo[jobId] = {'totalPage':totalPage,'currPageNum':pageNum}
          }
        })
      }
    },
    mounted() {
      this.getTreeData()
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

  .offline-page-tree{
    height:100%;
    margin:20px 0 0 0;
    overflow: auto;
    border-right: 1px solid black;
  }

  .offline-page-context{
    height:100%;
    overflow: auto;
  }

  .search-result-area{

  }

</style>
