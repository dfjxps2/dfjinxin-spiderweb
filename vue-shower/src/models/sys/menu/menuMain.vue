<template>
  <WorkMain :headerItems="['权限管理','功能查看']">

    <el-row class="table-row">
      <el-col :span="24">
        <el-table
          :data="menuDataList"
          style="width: 100%">
          <el-table-column
            prop="module_id"
            align="left"
            label="菜单编号">
          </el-table-column>
          <el-table-column
            prop="module_name"
            align="left"
            label="菜单名称">
          </el-table-column>
          <el-table-column
            prop="super_module_id"
            align="left"
            label="上级菜单编号">
          </el-table-column>
          <el-table-column
            prop="super_module_name"
            align="left"
            label="上级菜单名称"
            :formatter="formatterSuperName"
          >
          </el-table-column>
          <el-table-column
            prop="module_url"
            align="left"
            label="菜单地址">
          </el-table-column>

        </el-table>
      </el-col>
    </el-row>

    <!-- 分页 refreshData:点击页码上一页下一页时调用的方法、pageCount:总页数-->
    <WorkTablePager @refreshData="getTableData"
                    :pageCount="totalPage">
    </WorkTablePager>

  </WorkMain>
</template>1

<script>
  import WorkTablePager from "@/models/public/WorkTablePager"
  import WorkMain from "@/models/public/WorkMain"
  import { required } from 'vuelidate/lib/validators'
  export default {
    name: 'MenuMain',
    data() {
      return {
        menuDataList:[],
        menuDataObjs:{},
        tableDataUrl:'manage/datasource',
        currPageNum:1,
        eachPageNum:10,
        totalPage:1,
        showModalPage:false,
        isEditModal:false
      }
    },
    validations:{
      formData:{
        super_module_id:{
          required
        },
        module_name:{
          required
        }
      }
    },
    computed:{
      formData:function(){
        if(this.isEditModal){
          return this.editFormData
        }else{
          return this.addFormData
        }
      }
    },
    components: {
      WorkTablePager,
      WorkMain
    },
    methods: {
      getTableData:function(pageNum){
        if(pageNum&&pageNum!=''){
          this.currPageNum = pageNum;
        }else{
          pageNum = this.currPageNum;
        }
        const $this = this

        this.BaseRequest({
          url:'/sys/menu/listMenuByPage',
          method:"get",
          params:{currPage:pageNum,pageSize:this.eachPageNum}
        }).then(response=>{
            if(response.dataList!=null){
              response.dataList.forEach(menuObj =>{
                $this.menuDataObjs[menuObj.module_id] = menuObj
              })
            }
            $this.menuDataList = response.dataList
            $this.totalPage = response.totalPage
        })
      },
      formatterSuperName:function(row){
        return this.menuDataObjs[row.super_module_id]!=null?this.menuDataObjs[row.super_module_id].module_name:"无"
      },
      refreshTableList:function(dataList){
        this.dataSourceList = dataList
      }
    },
    mounted:function(){
      this.menuDataList = []
      this.getTableData(1)
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "@/styles/table-page.scss";

  .el-row{
    margin-top:20px;
  }

  $seachRowHeight : 50px;
  $pagerRowHeight : 50px;
  $tableRowHeight : calc(100% - #{$seachRowHeight+$pagerRowHeight+10});
  .search-row{
    height:$seachRowHeight;
  }

  .table-row{
    height:$tableRowHeight;
    overflow: auto;
  }

  .pager-row{
    height:$pagerRowHeight;
  }
</style>
