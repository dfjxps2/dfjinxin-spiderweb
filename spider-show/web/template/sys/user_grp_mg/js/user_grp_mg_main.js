
var modal_support_alter = modal_support.createNew();
var ajax_support = ajax_support.createNew();
var paging_data = paging_data.createNew();

var edit_modal_name = null;

$(document).ready(function(){
    //初始化查询条件区域
    var tableSearchCreater = workbench_table.tableSearchCreater();
    //
    // var userIdElement = tableSearchCreater.searchElementObject("user_id","请输入用户组ID");
    // var userNmElement = tableSearchCreater.searchElementObject("user_name","请输入用户名");
    // var searchElements = new Array();
    // searchElements.push(userIdElement);
    // searchElements.push(userNmElement);
    //
    var searchInfoObj = tableSearchCreater.searchInfoObject(null,null,true,true,"add_group()");
    tableSearchCreater.createSearch(searchInfoObj);

    paging_data.make_paging_data("/sys/group/listUserGroupPage.do");

});

function page_callback(dataList){
    var $user_table = $("#user_table").find("tbody");
    $user_table.empty();
    var columnNames = ["user_group_id","user_group_name","super_user_group_id"];

    var table_support = workbench_table.createNew();

    var operationArray = new Array();
    var view_operation = table_support.operationsByName("查看", ["user_group_id"], "viewGroup(this)");
    var edit_operation = table_support.operationsByName("编辑", ["user_group_id"], "editGroup(this)");
    var delete_operation = table_support.operationsByName("删除", ["user_group_id"], "deleteGroup(this)");
    var team_operation = table_support.operationsByName("成员", ["user_group_id"], "teamList(this)");
    operationArray.push(view_operation);
    operationArray.push(edit_operation);
    operationArray.push(delete_operation);
    operationArray.push(team_operation);

    table_support.makeTable("user_grp_table",columnNames,dataList,operationArray);

}

function add_group(){
    // var userId_modal = modal_support.getModalEditObject("user_id","用户ID","text");
    console.log("add user is running");
    modal_support_alter.makeEditModalByColumn("新增用户组",makeUserInfoColumn(),addGroupSave);

}

function addGroupSave(saveColumn){
    console.log("addGroupSave is running");
    var paramArray = saveParams(saveColumn);
    ajax_support.sendAjaxRequest("/sys/group/saveNewGroup.do",paramArray,"saveCallBack");
}


function viewGroup(viewUserObj){
    edit_modal_name = modal_support_alter.makeEditModalByColumn("查看用户组",makeUserInfoColumn("view"));
    ajax_support.sendAjaxRequest("/sys/group/getUserGroup.do",{"user_group_id":$(viewUserObj).attr("user_group_id")},"editUserGroupInfo");
}

function editGroup(viewUserObj){
    edit_modal_name = modal_support_alter.makeEditModalByColumn("编辑用户组",makeUserInfoColumn("edit"),editGroupSave);
    ajax_support.sendAjaxRequest("/sys/group/getUserGroup.do",{"user_group_id":$(viewUserObj).attr("user_group_id")},"editUserGroupInfo");
}

function editGroupSave(saveColumn){
    var paramArray = saveParams(saveColumn);
    ajax_support.sendAjaxRequest("/sys/group/updateUserGroup.do",paramArray,"saveCallBack");
}

function deleteGroup(viewUserObj){
    modal_support_alter.make_alter("确定删除用户组？",sendDelRequest,{"user_group_id":$(viewUserObj).attr("user_group_id")})
}

function sendDelRequest(userObj){
    ajax_support.sendAjaxRequest("/sys/group/delUserGroup.do",{"user_group_id":$(userObj).attr("user_group_id")},"saveCallBack");
}

function makeUserInfoColumn(showType){
    var columnsArray = new Array();
    var userObj = null;
    if(showType!=null&&showType=='view'){
        var userGroupId_modal = modal_support.getModalEditObject("user_group_id","用户组ID","text",true,null,true);
        var userGroupName_modal = modal_support.getModalEditObject("user_group_name","用户组名","text",true,null,true);
        var superUserGrp_modal = modal_support.getModalEditObject("super_user_group_id","上级用户组","select",true,null,true,null);

        columnsArray.push(userGroupId_modal);
        columnsArray.push(userGroupName_modal);
        columnsArray.push(superUserGrp_modal);
        return columnsArray;
    }else if(showType!=null&&showType=='edit'){
        var userGroupId_modal = modal_support.getModalEditObject("user_group_id","用户组ID","text",true,null,true);
        columnsArray.push(userGroupId_modal);
    }else{
        var userGroupId_modal = modal_support.getModalEditObject("user_group_id","用户组ID","text",true,null,false);
        columnsArray.push(userGroupId_modal);
    }
    var userGroupName_modal = modal_support.getModalEditObject("user_group_name","用户组名","text",false,null,false);

    var superUserGrp_modal = modal_support.getModalEditObject("super_user_group_id","上级用户组","select",true,null,false,null);

    columnsArray.push(userGroupName_modal);
    columnsArray.push(superUserGrp_modal);

    ajax_support.sendAjaxRequest("/sys/group/listSuperGroup.do",null,"getSupperGroupResult");


    return columnsArray;
}

function getSupperGroupResult(dataList){
    var $super_user_group_id = $("#edit_modal").find("select[id='super_user_group_id']");
    if(ajax_support.ajax_result_success(dataList)){
        var result = ajax_support.get_result_data(dataList);
        $.each(result,function(i,dataObj){
            var user_group_id = dataObj['user_group_id'];
            var user_group_name = dataObj['user_group_name'];
            $super_user_group_id.append("<option value='"+user_group_id+"'>"+user_group_name+"</option>");
        });
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
        paging_data.make_paging_data("/sys/group/listUserGroupPage.do");
    }else{
        modal_support_alter.make_alter("保存失败");
    }
}

function editUserGroupInfo(groupObj){
    if(ajax_support.ajax_result_success(groupObj)){
        var groupResultData = ajax_support.get_result_data(groupObj);

        var user_group_id = groupResultData["user_group_id"];
        var user_group_name = groupResultData["user_group_name"];
        var super_user_group_id = groupResultData["super_user_group_id"];

        var $edit_modal = $("#"+edit_modal_name);

        $edit_modal.find("input[id='user_group_id']").val(user_group_id);
        $edit_modal.find("input[id='user_group_name']").val(user_group_name);
        $edit_modal.find("select[id='super_user_group_id']").val(super_user_group_id);
        // $("#"+edit_modal_name).find("input[reg_date]").val(reg_date);
        // $("#"+edit_modal_name).find("input[last_login_time]").val(last_login_time);
    }

}


function teamList(viewUserObj){
    var page_support_tmp = page_support.createNew();
    var groupId = $(viewUserObj).attr("user_group_id");
    page_support_tmp.forward_new_page("/template/sys/user_grp_mg/user_grp_teamer.html?user_group_id="+groupId);
    // edit_modal_name = modal_support_alter.makeEditModalByColumn("用户组成员管理",makeUserInfoColumn("view"));
    // ajax_support.sendAjaxRequest("/sys/group/getUserGroup.do",{"user_group_id":$(viewUserObj).attr("user_group_id")},"editUserGroupInfo");
}
