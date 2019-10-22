/**
 * Created by SongCQ on 2017/7/24.
 */

var user_group_id = null;

$(document).ready(function(){
    var page_support_obj = page_support.createNew();
    var params = page_support_obj.check_param_from_url();
    // console.log(JSON.stringify(params));
    user_group_id = params["user_group_id"];
    // var ajax_support_obj = ajax_support.createNew();
    var paging_data_obj = paging_data.createNew();
    paging_data_obj.make_paging_data("/sys/userGroup/listUsersByGroupId.do",params);

    var tableSearchCreater = workbench_table.tableSearchCreater();
    var searchInfoObj = tableSearchCreater.searchInfoObject(null,null,true,true,"add_user_to_group()");
    tableSearchCreater.createSearch(searchInfoObj);

});

function page_callback(dataList){
    console.log(" page callback value is "+dataList);
    var $user_table = $("#user_table").find("tbody");
    $user_table.empty();
    var columnNames = ["user_id","user_name","user_type","reg_date","user_status","last_login_time"];

    var table_support = workbench_table.createNew();

    var operationArray = new Array();
    var view_operation = table_support.operationsByName("查看", ["user_id"], "viewUser(this)");
    operationArray.push(view_operation);

    table_support.makeTable("user_table",columnNames,dataList,operationArray);
}

function viewUser(viewUserObj){
    var modal_support_alter = modal_support.createNew();
    edit_modal_name = modal_support_alter.makeEditModalByColumn("查看用户",makeUserInfoColumn("view"));
    ajax_support.sendAjaxRequest("/sys/user/getUserByUserId.do",{"user_id":$(viewUserObj).attr("user_id")},"editUserInfo");
}

function editUserInfo(userObj){
    if(ajax_support.ajax_result_success(userObj)){
        var userResultData = ajax_support.get_result_data(userObj);

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


function makeUserInfoColumn(showType){
    var columnsArray = new Array();
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
}

function add_user_to_group(){

    var userId_modal = modal_support.getModalEditObject("user_id","用户ID","text",true,null,false);
    var columnsArray = new Array();
    columnsArray.push(userId_modal);

    var modal_support_alter = modal_support.createNew();
    modal_support_alter.makeEditModalByColumn("新增用户组成员",columnsArray,addGroupUserSave);
}

function addGroupUserSave(inputValues){
    var column_id = inputValues[0].column_id;
    var column_val = inputValues[0].column_val;
    
    var saveParam = new Object();

    saveParam[column_id] = column_val;
    saveParam["user_group_id"] = user_group_id;
    console.log("inputValues value is "+JSON.stringify(inputValues));

    ajax_support.sendAjaxRequest("/sys/userGroup/saveUserGroup.do",saveParam,"saveCallBack");
}

function saveCallBack(saveResult){
    if(ajax_support.ajax_result_success(saveResult)){
        modal_support_alter.make_alter("保存成功");
        modal_support_alter.closeEditModalByColumn();
        paging_data.make_paging_data("/sys/userGroup/listUsersByGroupId.do",{"user_group_id":user_group_id});
    }else{
        modal_support_alter.make_alter("保存失败");
    }
}