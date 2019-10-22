<template>
  <WorkMain :headerItems="['角色管理','角色列表']">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-button @click="openAddModal" type="primary">新增</el-button>
      </el-col>
    </el-row>

    <el-row class="table-row">
      <el-col :span="24">
        <el-table
          :data="userDataList"
          style="width: 100%">
          <el-table-column
            prop="user_role_id"
            label="角色ID">
          </el-table-column>
          <el-table-column
            prop="user_role_name"
            label="角色名称">
          </el-table-column>
          <el-table-column
            label="操作">
            <template slot-scope="scope">
              <el-button type="text" @click="openRoleFunction(scope.row)" size="mini">功能</el-button>
              <el-button type="text" @click="openEditModal(scope.row)" size="mini">编辑</el-button>
              <el-button type="text" @click="delRole(scope.row)" size="mini">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <!-- 分页 refreshData:点击页码上一页下一页时调用的方法、pageCount:总页数-->
    <WorkTablePager @refreshData="getTableData"
                    :pageCount="totalPage">
    </WorkTablePager>

    <!-- 新增、编辑 弹窗-->
    <el-dialog :title="modalPageTitle" :visible.sync="showModalPage" >
      <el-form class="modal-form" label-width="20%" :model="formData">
        <el-form-item label="角色编号" >
          <el-input :disabled="isEditModal" v-model="formData.user_role_id" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item label="角色名称" >
          <el-input v-model="formData.user_role_name" auto-complete="off" ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeModal">取 消</el-button>
        <el-button type="primary" @click="submitDataForm()">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 角色、功能关联关系菜单 -->
    <el-dialog title="角色功能" :visible.sync="showRoleFunction" >
      <el-transfer :titles="['未选功能', '已选功能']" class="role-transfer" v-model="menuCheckedForRole" :data="roleFunctionData"></el-transfer>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeRoleMenus">取 消</el-button>
        <el-button type="primary" @click="subRoleMenus()">确 定</el-button>
      </div>

    </el-dialog>
  </WorkMain>
</template>

<script>
import WorkTablePager from '@/models/public/WorkTablePager'
import WorkMain from '@/models/public/WorkMain'
import { required } from 'vuelidate/lib/validators'
import MenuTurnner from '@/models/public/utils/menuTurner'

export default {
  name: 'RoleMain',

  data () {
    return {
      userDataList: [],
      termForSearch: '',
      tableDataUrl: 'sys/role/rolePageData',
      currPageNum: 1,
      totalPage: 1,
      showModalPage: false,
      showRoleFunction: false,
      isEditModal: false,
      edit_role_id: null,
      formData: {
        user_role_id: null,
        user_role_name: null
      },
      roleFunctionData: [],
      menuCheckedForRole: []
    }
  },
  validations: {
    formData: {
      user_role_name: {
        required
      }
    }
  },
  computed: {
    modalPageTitle () {
      if (this.formData.user_role_id !== null) {
        return '修改角色'
      } else {
        return '新增角色'
      }
    }
  },
  components: {
    WorkTablePager,
    WorkMain
  },
  methods: {
    getTableData: function (pageNum) {
      if (pageNum && pageNum !== '') {
        this.currPageNum = pageNum
      } else {
        pageNum = this.currPageNum
      }
      const $this = this
      this.BaseRequest({
        url: this.tableDataUrl,
        method: 'get',
        params: {currPage: pageNum, pageSize: 10, search: this.termForSearch || ''}
      }).then((reponse) => {
        $this.totalPage = reponse.totalPage
        $this.refreshTableList(reponse.dataList)
      })
    },
    refreshTableList: function (dataList) {
      this.userDataList = dataList
    },
    delRole: function (row) {
      this.$confirm('确定删除角色【' + row.user_role_name + '】？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        dangerouslyUseHTMLString: true,
        type: 'warning'
      }).then(() => {
        this.BaseRequest({
          url: 'sys/role/deleteRole',
          method: 'get',
          params: {'user_role_id': row.user_role_id}
        }).then(() => {
          this.Message.success('删除成功')
          this.getTableData()
        })
      }).catch(() => {
      })
    },
    openAddModal: function () {
      this.clearData()
      this.showModalPage = true
      this.isEditModal = true
    },
    openEditModal: function (row) {
      this.formData.user_role_id = row.user_role_id
      this.formData.user_role_name = row.user_role_name
      this.showModalPage = true
      this.isEditModal = true
    },
    clearData: function () {
      this.formData.user_role_id = null
      this.formData.user_role_name = null
    },
    closeModal: function () {
      this.showModalPage = false
      this.isEditModal = false
    },
    closeRoleMenus: function () {
      this.showRoleFunction = false
    },
    openRoleFunction: function (row) {
      this.showRoleFunction = true
      this.edit_role_id = row.user_role_id
      const $this = this
      this.BaseRequest({
        url: 'sys/roleMenu/getMenuByRole',
        method: 'get',
        params: {'user_role_id': row.user_role_id}
      }).then((response) => {
        $this.menuCheckedForRole = []
        if (response != null && response.length > 0) {
          response.forEach(menuData => {
            $this.menuCheckedForRole.push(menuData.module_id)
          })
        }
      })
    },
    submitDataForm: function () {
      if (this.checkInputNull()) {
        return
      }
      if (this.formData.user_role_id !== null) {
        this.BaseRequest({
          url: 'sys/role/updateSaveRole',
          method: 'post',
          data: this.formData
        }).then(() => {
          this.Message.success('保存成功')
          this.getTableData()
          this.closeModal()
        })
      } else {
        this.BaseRequest({
          url: 'sys/role/saveNewRole',
          method: 'post',
          data: this.formData
        }).then(() => {
          this.Message.success('保存成功')
          this.getTableData()
          this.closeModal()
        })
      }
    },
    subRoleMenus () {
      this.BaseRequest({
        url: 'sys/roleMenu/saveMenusForRole',
        method: 'post',
        data: {'user_role_id': ''+this.edit_role_id, 'menus': this.menuCheckedForRole}
      }).then(() => {
        this.Message.success('保存成功')
        this.closeRoleMenus()
      })
    },
    checkInputNull () {
      const checkResult = this.$v.$invalid
      if (checkResult) {
        this.$notify({
          dangerouslyUseHTMLString: true,
          message: '<span style="font-size:15px;color:red;font-weight: bold">以下参数不允许为空</span><br>角色编号<br>角色名称'
        })
      }
      return checkResult
    }
  },
  mounted: function () {
    this.userDataList = []
    this.getTableData(1)
    const $this = this
    this.BaseRequest({
      url: 'sys/menu/listMenuByPage',
      method: 'GET',
      params: {'currPage': '1', 'pageSize': '200'}
    }).then(response => {
      response.dataList.forEach(menuData => {
        $this.roleFunctionData.push({'key': menuData.module_id, 'label': menuData.module_name})
      })
    })
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
    width:99%;
    height:$tableRowHeight;
    overflow: auto;
  }

  .pager-row{
    height:$pagerRowHeight;
  }

  .role-transfer{
    text-align: left;
  }
</style>
