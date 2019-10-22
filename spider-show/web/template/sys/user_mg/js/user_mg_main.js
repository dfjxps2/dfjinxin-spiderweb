
var modal_support_alter = modal_support.createNew();
var ajax_support_obj = ajax_support.createNew();
var paging_data_obj = paging_data.createNew();

var edit_modal_name = null;

$(document).ready(function(){
    // init_modal_alter();
    //初始化查询条件区域
    var tableSearchCreater = workbench_table.tableSearchCreater();

    var userIdElement = tableSearchCreater.searchElementObject("user_id","请输入用户ID");
    var userNmElement = tableSearchCreater.searchElementObject("user_name","请输入用户名");
    var searchElements = new Array();
    searchElements.push(userIdElement);
    searchElements.push(userNmElement);

    var searchInfoObj = tableSearchCreater.searchInfoObject("/sys/user/listUserPage.do",searchElements,true,true,"add_user()");
    tableSearchCreater.createSearch(searchInfoObj);

    paging_data_obj.make_paging_data("/sys/user/listUserPage.do");

    // $("#add_user_btn").click(function(){
    //     add_user();
    // });
});

function page_callback(dataList){
    var $user_table = $("#user_table").find("tbody");
    $user_table.empty();
    var columnNames = ["user_id","user_name","user_type","reg_date","user_status","last_login_time"];

    var table_support = workbench_table.createNew();

    var operationArray = new Array();
    var view_operation = table_support.operationsByName("查看", ["user_id"], "viewUser(this)");
    var edit_operation = table_support.operationsByName("编辑", ["user_id"], "editUser(this)");
    var delete_operation = table_support.operationsByName("删除", ["user_id"], "deleteUser(this)");
    operationArray.push(view_operation);
    operationArray.push(edit_operation);
    operationArray.push(delete_operation);

    table_support.makeTable("user_table",columnNames,dataList,operationArray);

}

function add_user(){
    // var userId_modal = modal_support.getModalEditObject("user_id","用户ID","text");
    console.log("add user is running");
    modal_support_alter.makeEditModalByColumn("新增用户",makeUserInfoColumn(),addUserSave);

}

function addUserSave(saveColumn){
    var paramArray = saveParams(saveColumn);
    ajax_support_obj.sendAjaxRequest("/sys/user/saveNewUser.do",paramArray,"saveCallBack");
}


function viewUser(viewUserObj){
    edit_modal_name = modal_support_alter.makeEditModalByColumn("查看用户",makeUserInfoColumn("view"));
    ajax_support_obj.sendAjaxRequest("/sys/user/getUserByUserId.do",{"user_id":$(viewUserObj).attr("user_id")},"editUserInfo");
}

function editUser(viewUserObj){
    edit_modal_name = modal_support_alter.makeEditModalByColumn("编辑用户",makeUserInfoColumn("edit"),editUserSave);
    ajax_support_obj.sendAjaxRequest("/sys/user/getUserByUserId.do",{"user_id":$(viewUserObj).attr("user_id")},"editUserInfo");
}

function editUserSave(saveColumn){
    var paramArray = saveParams(saveColumn);
    ajax_support_obj.sendAjaxRequest("/sys/user/updateSaveUser.do",paramArray,"saveCallBack");
}

function deleteUser(viewUserObj){
    modal_support_alter.make_alter("确定删除用户？",sendDelRequest,{"user_id":$(viewUserObj).attr("user_id")},true)
}

function sendDelRequest(userObj){
    ajax_support_obj.sendAjaxRequest("/sys/user/delUserByUserId.do",{"user_id":$(userObj).attr("user_id")},"saveCallBack");
}

function makeUserInfoColumn(showType){
    var columnsArray = new Array();
    var userObj = null;
    if(showType!=null&&showType=='view'){
        var userId_modal = modal_support.getModalEditObject("user_id","用户ID","text",true,null,true);
        var userName_modal = modal_support.getModalEditObject("user_name","用户名","text",true,null,true);
        var user_type_options = [
            {"options_value":1,"options_text":"管理员"},
            {"options_value":2,"options_text":"业务管理员"},
            {"options_value":3,"options_text":"操作员"}
        ];

        var user_status_options = [
            {"options_value":1,"options_text":"正常"},
            {"options_value":2,"options_text":"停用"},
            {"options_value":3,"options_text":"冻结"},
            {"options_value":3,"options_text":"注销"}
        ];
        var userType_modal = modal_support.getModalEditObject("user_type","用户类型","select",true,null,true,user_type_options);
        var userStatus_modal = modal_support.getModalEditObject("user_status","用户状态","select",true,null,true,user_status_options);

        columnsArray.push(userId_modal);
        columnsArray.push(userName_modal);
        columnsArray.push(userType_modal);
        columnsArray.push(userStatus_modal);
        return columnsArray;
    }else if(showType!=null&&showType=='edit'){
        var userId_modal = modal_support.getModalEditObject("user_id","用户ID","text",true,null,true);
        columnsArray.push(userId_modal);
    }
    var userName_modal = modal_support.getModalEditObject("user_name","用户名","text",true,null,false);
    var user_type_options = [
        {"options_value":1,"options_text":"管理员"},
        {"options_value":2,"options_text":"业务管理员"},
        {"options_value":3,"options_text":"操作员"}
    ];

    var user_status_options = [
        {"options_value":1,"options_text":"正常"},
        {"options_value":2,"options_text":"停用"},
        {"options_value":3,"options_text":"冻结"},
        {"options_value":3,"options_text":"注销"}
    ];
    var userType_modal = modal_support.getModalEditObject("user_type","用户类型","select",true,null,false,user_type_options);
    var userStatus_modal = modal_support.getModalEditObject("user_status","用户状态","select",true,null,false,user_status_options);

    columnsArray.push(userName_modal);
    columnsArray.push(userType_modal);
    columnsArray.push(userStatus_modal);

    console.log(JSON.stringify(columnsArray));

    return columnsArray;
}


function saveParams(saveColumn){
    var columnObj = new Object();
    // console.log(JSON.stringify(saveColumn));
    $.each(saveColumn,function(i,columnData){
        var column_id_val = columnData["column_id"];
        var column_val = columnData["column_val"];
        columnObj[column_id_val] = column_val;
    });
    console.log(JSON.stringify(columnObj));
    return columnObj;
}

function saveCallBack(saveResult){
    if(ajax_support_obj.ajax_result_success(saveResult)){
        modal_support_alter.make_alter("保存成功");
        modal_support_alter.closeEditModalByColumn();
        paging_data_obj.make_paging_data("/sys/user/listUserPage.do");
    }else{
        modal_support_alter.make_alter("保存失败");
    }
}

function editUserInfo(userObj){
    if(ajax_support_obj.ajax_result_success(userObj)){
        var userResultData = ajax_support_obj.get_result_data(userObj);

        var user_id = userResultData["user_id"];
        var user_name = userResultData["user_name"];
        var user_type = userResultData["user_type"];
        var reg_date = userResultData["reg_date"];
        var user_status = userResultData["user_status"];
        var last_login_time = userResultData["last_login_time"];

        var $edit_modal = $("#"+edit_modal_name);

        $edit_modal.find("input[id='user_id']").val(user_id);
        $edit_modal.find("input[id='user_name']").val(user_name);
        $edit_modal.find("select[id='user_type']").val(user_type);
        $edit_modal.find("select[id='user_status']").val(user_status);
        // $("#"+edit_modal_name).find("input[reg_date]").val(reg_date);
        // $("#"+edit_modal_name).find("input[last_login_time]").val(last_login_time);
    }

}
