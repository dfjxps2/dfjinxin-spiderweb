<template>
  <WorkMain :headerItems="['用户管理','用户列表']">
    <el-row class="search-row" :gutter="20">
      <el-col class="align-left" :span="17">
        <el-input placeholder="请输入待查询用户名" style="width:180px"  v-model="seachUserId"></el-input>
        <el-button @click="getTableData(1)" >查询</el-button>
        <el-button @click="openAddModal" type="primary">新增</el-button>
      </el-col>
    </el-row>

    <el-row class="table-page-root-outoptions">
      <el-col :span="24">
        <el-table
          :data="userDataList"
          header-row-class-name="table-header-style"
          row-class-name="mini-font-size" stripe
          style="width: 100%">
          <el-table-column
            prop="user_name"
            align="left"
            label="用户名称">
          </el-table-column>
          <el-table-column
            prop="user_name_cn"
            align="left"
            label="用户名称">
          </el-table-column>
          <el-table-column
            prop="reg_date"
            align="left"
            label="注册日期">
          </el-table-column>
          <el-table-column
            prop="last_login_time"
            align="left"
            label="最后登入日期">
          </el-table-column>
          <el-table-column
            label="操作"
            align="center">
            <template slot-scope="scope">
              <el-button type="text" @click="openEditModal(scope.row)" size="mini">编辑</el-button>
              <el-button type="text" @click="openAuthModal(scope.row)" size="mini">权限</el-button>
              <el-button type="text" @click="resetPwd(scope.row)" size="mini">重置密码</el-button>
              <el-button type="text" @click="delUser(scope.row)" size="mini">删除</el-button>
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
      <el-form  class="modal-form" label-position="left" label-width="20%" :model="formData">
        <!--
        <el-form-item :size="mini" label="用户登陆名" >
          <el-input   auto-complete="off" ></el-input>
        </el-form-item>
        -->
        <el-form-item size="mini" label="用户名称" >
          <el-input v-model="formData.user_name" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item size="mini" label="用户中文名称" >
          <el-input v-model="formData.user_name_cn" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item size="mini" label="用户类型" >
          <el-select v-model="formData.user_type" style="width:100%;" placeholder="请选择用户类型">
            <el-option label="管理员" value='1'></el-option>
          </el-select>
        </el-form-item>

        <el-form-item size="mini" label="用户状态" >
          <el-select v-model="formData.user_status" style="width:100%;" placeholder="请选择用户状态">
            <el-option label="正常" value='0'></el-option>
            <el-option label="锁定" value='1'></el-option>
            <el-option label="密码过期" value="3"></el-option>
            <el-option label="停用" value='4'></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeModal">取 消</el-button>
        <el-button type="primary" @click="submitDataForm()">确 定</el-button>
      </div>
    </el-dialog>

    <!--用户权限设置-->
    <el-dialog title="用户权限" :visible.sync="showUserAuth" >
      <el-form  class="modal-form" label-position="left" label-width="20%" :model="formData">
        <el-form-item size="mini" label="用户名称" >
          <el-input :disabled="true"  :value="formData.user_name" auto-complete="off" ></el-input>
        </el-form-item>
        <el-form-item size="mini" label="角色" >
          <el-select v-model="user_role_id" style="width:100%;" placeholder="请选择角色">
            <el-option :key="roleData.user_role_id" v-for="roleData in allRoleList" :label="roleData.user_role_name" :value="roleData.user_role_id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeUserAuthModal">取 消</el-button>
        <el-button type="primary" @click="saveUserRole()">确 定</el-button>
      </div>
    </el-dialog>
  </WorkMain>
</template>

<script>
  import WorkTablePager from '@/models/public/WorkTablePager'
  import WorkMain from '@/models/public/WorkMain'
  import { required } from 'vuelidate/lib/validators'
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'

  export default {
    name: 'UserMain',
    data () {
      return {
        userDataList: [],
        originList: [],
        seachOriginList: [],
        searchOriginName: '',
        userType: 1,
        seachUserId: null,
        seachOriginId: null,
        tableDataUrl: 'sys/user/listUserPage',
        currPageNum: 1,
        totalPage: 1,
        showModalPage: false,
        isEditModal: false,
        showUserAuth: false,
        showOrigin: false,
        allRoleList: [],
        user_role_id: '',
        formData: {
          user_id:null,
          user_name: null,
          user_name_cn: null,
          user_type: null,
          user_status: null,
        },
        options:[]

      }
    },
    validations: {
      formData: {
        user_name: {
          required
        },
        user_type: {
          required
        },
        user_status: {
          required
        },
      }
    },
    computed: {
      modalPageTitle () {
        if (this.isEditModal) {
          return '修改用户'
        } else { return '新增用户' }
      }
    },
    components: {
      Treeselect,
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
        let seachOriginId = null
        if(this.seachOriginList!=null&&this.seachOriginList.length>0){
          seachOriginId = this.seachOriginList[this.seachOriginList.length-1]
        }
        if(this.seachUserId==null||this.seachUserId==''){
          this.seachUserId = ""
        }
        this.BaseRequest({
          url: this.tableDataUrl,
          method: 'get',
          params: {currPage: pageNum,
            pageSize: 10,
            user_name_cn: this.seachUserId,
            searchOriginName: this.searchOriginName,
            searchOriginId:seachOriginId}
        }).then(reponse => {
          $this.totalPage = reponse.totalPage
          $this.refreshTableList(reponse.dataList)
        })
      },
      refreshTableList: function (dataList) {
        this.userDataList = dataList
      },
      delUser: function (row) {
        this.$confirm('确定删除用户【' + row.user_name + '】？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          dangerouslyUseHTMLString: true,
          type: 'warning'
        }).then(() => {
          this.BaseRequest({
            url: 'sys/user/delUserByUserId',
            method: 'get',
            params: {'user_id': row.user_id}
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
        this.isEditModal = false
      },
      clearData: function () {
        this.formData = {
          user_id: null,
          user_name: null,
          user_name_cn: null,
          user_status: null,
          user_type: null
        }
      },
      openEditModal: function (row) { // user edit
        this.clearData()
        this.showModalPage = true
        this.isEditModal = true
        this.formData.user_name = row.user_name
        this.formData.user_name_cn = row.user_name_cn
        this.formData.user_id = row.user_id
        this.formData.user_status = row.user_status
        this.formData.user_type = row.user_type
      },
      resetPwd(row){
        const userId = row.user_id
        this.$confirm('确定将该用户密码重置？', '提示', {
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
            url:'sys/user/resetPwd',
            method:'get',
            params:{'userId':userId}
          }).then(response=>{
            this.Message.success("重置成功")
            loading.close()
            this.getTableData(1)
          }).catch(error=>{
            console.log(error)
            loading.close()
            this.Message.error("重置失败"+error)
          })
        }).catch(() => {
        });
      },
      submitDataForm: function () { // user submit
        if (this.checkInputNull()) {
          return
        }
        if (this.isEditModal) { // update
          this.BaseRequest({
            url: 'sys/user/updateSaveUser',
            method: 'POST',
            data: this.formData
          }).then(() => {
            this.Message.success('保存成功')
            // add user——origin relation
            this.getTableData()
            this.closeModal()
          })
        } else { // insert
          this.BaseRequest({
            url: 'sys/user/saveNewUser',
            method: 'get',
            params: this.formData
          }).then((response) => {
            this.Message.success('保存成功')
            // add user——origin relation
            this.formData.user_id = response.user_id
            this.getTableData()
            this.closeModal()
          })
        }
      },
      openAuthModal: function (row) { // edit
        const $this = this
        this.showUserAuth = true
        // this.formData = row
        this.formData.user_id = row.user_id
        this.formData.user_name = row.user_name
        this.BaseRequest({
          url: 'sys/userRole/getRoleByUserId',
          method: 'get',
          params: {user_id: this.formData.user_id}
        }).then(response => {
          if (response != null && response.length > 0) {
            // set value for update
            $this.user_role_id = response[0]['user_role_id']
            $this.formData.user_role_id = response[0]['user_role_id']
          }
        })
      },
      saveUserRole: function () { // 权限保存修改
        this.BaseRequest({
          url: 'sys/userRole/updateUserRole',
          method: 'get',
          params: {user_id: this.formData.user_id, user_role_id: this.user_role_id, old_user_role_id: this.formData.user_role_id}
        }).then(() => {
          this.Message.success('保存成功')
          this.showUserAuth = false
        })
      },
      closeModal: function () {
        this.showModalPage = false
        this.isEditModal = false
      },
      closeUserAuthModal: function () {
        this.showUserAuth = false
      },

      checkInputNull () {
        const checkResult = this.$v.$invalid
        if (checkResult) {
          this.$notify({
            dangerouslyUseHTMLString: true,
            message: '<span style="font-size:15px;color:red;font-weight: bold">以下参数不允许为空</span><br>用户名称、所属机构'
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
        url: 'sys/role/rolePageData',
        method: 'get',
        params: {currPage: 1, pageSize: 100}
      }).then(response => {
        $this.allRoleList = response.dataList
      })
    }
  }
</script>

<style lang="css">
  .mini-font-size{
    font-size: 12px !important;
  }
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
  @import "@/styles/table-page.scss";

  .search-row{
    margin:5px 0 0 0;
  }
</style>
