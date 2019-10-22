/**
 * Created by SongCQ on 2017/7/24.
 */

var modal_support_alter = modal_support.createNew();
var edit_modal_name = null;
$(document).ready(function(){
    //初始化查询条件区域
    var tableSearchCreater = workbench_table.tableSearchCreater();

    var searchInfoObj = tableSearchCreater.searchInfoObject(null,null,true,true,"add_role()");
    tableSearchCreater.createSearch(searchInfoObj);

    var pagingDataObj = paging_data.createNew();

    pagingDataObj.make_paging_data("/sys/role/rolePageData.do");

});

function page_callback(dataList){
    console.log(JSON.stringify(dataList));

    var role_table = $("#role_table").find("tbody");
    role_table.empty();
    var columnNames = ["user_role_id","user_role_name"];

    var table_support = workbench_table.createNew();

    var operationArray = new Array();
    var view_operation = table_support.operationsByName("查看", ["user_role_id"], "viewRole(this)");
    var edit_operation = table_support.operationsByName("编辑", ["user_role_id"], "editRole(this)");
    var delete_operation = table_support.operationsByName("删除", ["user_role_id"], "deleteRole(this)");
    var roleMenus_operation = table_support.operationsByName("权限", ["user_role_id"], "roleMenus(this)");
    operationArray.push(view_operation);
    operationArray.push(edit_operation);
    operationArray.push(delete_operation);
    operationArray.push(roleMenus_operation);

    table_support.makeTable("role_table",columnNames,dataList,operationArray);
}


function add_role(){
    // var userId_modal = modal_support.getModalEditObject("user_id","用户ID","text");
    console.log("add user is running");
    modal_support_alter.makeEditModalByColumn("新增角色",makeRoleInfoColumn(),addRoleSave);

}

function viewRole(viewUserObj){
    edit_modal_name = modal_support_alter.makeEditModalByColumn("查看角色",makeRoleInfoColumn("view"));
    ajax_support.sendAjaxRequest("/sys/role/getRoleById.do",{"user_role_id":$(viewUserObj).attr("user_role_id")},"editRoleInfo");
}

function editRole(viewUserObj){
    edit_modal_name = modal_support_alter.makeEditModalByColumn("编辑角色",makeRoleInfoColumn("edit"),editRoleSave);
    ajax_support.sendAjaxRequest("/sys/role/getRoleById.do",{"user_role_id":$(viewUserObj).attr("user_role_id")},"editRoleInfo");
}

function addRoleSave(saveColumn){
    var paramArray = saveParams(saveColumn);
    ajax_support.sendAjaxRequest("/sys/role/saveNewRole.do",paramArray,"saveCallBack");
}

function editRoleSave(saveColumn){
    var paramArray = saveParams(saveColumn);
    ajax_support.sendAjaxRequest("/sys/role/updateSaveRole.do",paramArray,"saveCallBack");
}

function deleteRole(viewUserObj){
    modal_support_alter.make_alter("确定删除角色？<font style='color:red;'>该动作会删除所有关联该角色用户的权限</font>",sendDelRequest,{"user_role_id":$(viewUserObj).attr("user_role_id")})
}

function sendDelRequest(userObj){
    ajax_support.sendAjaxRequest("/sys/role/deleteRole.do",{"user_role_id":$(userObj).attr("user_role_id")},"saveCallBack");
}

function editRoleInfo(roleObj){
    if(ajax_support.ajax_result_success(roleObj)){
        var roleResultData = ajax_support.get_result_data(roleObj);

        var user_role_id = roleResultData["user_role_id"];
        var user_role_name = roleResultData["user_role_name"];

        var $edit_modal = $("#"+edit_modal_name);

        $edit_modal.find("input[id='user_role_id']").val(user_role_id);
        $edit_modal.find("input[id='user_role_name']").val(user_role_name);
    }
}

function saveParams(saveColumn){
    var columnObj = new Object();
    // console.log(JSON.stringify(saveColumn));
    $.each(saveColumn,function(i,columnData){
        var column_id_val = columnData["column_id"];
        var column_val = columnData["column_val"];
        if(column_val!=null&&column_val!=''){
            columnObj[column_id_val] = column_val;
        }
    });
    console.log(JSON.stringify(columnObj));
    return columnObj;
}

function saveCallBack(saveResult){
    if(ajax_support.ajax_result_success(saveResult)){
        modal_support_alter.make_alter("保存成功");
        modal_support_alter.closeEditModalByColumn();
        paging_data.make_paging_data("/sys/role/rolePageData.do");
    }else{
        modal_support_alter.make_alter("保存失败");
    }
}

function makeRoleInfoColumn(showType){
    var columnsArray = new Array();
    if(showType!=null&&showType=='view'){
        var roleId_modal = modal_support.getModalEditObject("user_role_id","角色ID","text",true,null,true);
        var roleName_modal = modal_support.getModalEditObject("user_role_name","角色名称","text",true,null,true);

        columnsArray.push(roleId_modal);
        columnsArray.push(roleName_modal);
        return columnsArray;
    }else if(showType!=null&&showType=='edit'){
        var roleId_modal = modal_support.getModalEditObject("user_role_id","角色ID","text",true,null,true);
        columnsArray.push(roleId_modal);
    }else{
        var roleId_modal = modal_support.getModalEditObject("user_role_id","角色ID","text",true,null,false);
        columnsArray.push(roleId_modal);
    }
    var roleName_modal = modal_support.getModalEditObject("user_role_name","角色名称","text",false,null,false);

    columnsArray.push(roleName_modal);

    return columnsArray;
}

function roleMenus(viewUserObj){
    var user_role_id = $(viewUserObj).attr("user_role_id");
    var page_support_obj = page_support.createNew();
    page_support_obj.forward_new_page("/template/sys/role_mg/role_menus.html?user_role_id="+user_role_id);
}