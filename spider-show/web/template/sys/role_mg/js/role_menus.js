/**
 * Created by SongCQ on 2017/7/24.
 */
var user_role_id = null;
var modal_support_alter = modal_support.createNew();

$(document).ready(function(){
    var page_support_obj = page_support.createNew();
    var params = page_support_obj.check_param_from_url();
    user_role_id = params["user_role_id"];
    var pagingDataObj = paging_data.createNew();
    pagingDataObj.make_paging_data("/sys/roleMenu/pagingMenuByRole.do",params);


    var tableSearchCreater = workbench_table.tableSearchCreater();
    var searchInfoObj = tableSearchCreater.searchInfoObject(null,null,true,true,"add_menu()");
    tableSearchCreater.createSearch(searchInfoObj);
});

function page_callback(dataList){
    console.log(JSON.stringify(dataList));

    var menu_table = $("#menu_table").find("tbody");
    menu_table.empty();
    var columnNames = ["module_id","module_name","super_module_id","module_url"];

    var table_support = workbench_table.createNew();

    var operationArray = new Array();
    var delete_operation = table_support.operationsByName("删除", ["module_id"], "deleteModule(this)");
    // operationArray.push(view_operation);
    operationArray.push(delete_operation);

    table_support.makeTable("menu_table",columnNames,dataList,operationArray);
}

function add_menu(){
    var columnsArray = new Array();
    var moduleId_modal = modal_support.getModalEditObject("module_id","功能ID","select",true,null,false);
    columnsArray.push(moduleId_modal);
    modal_support_alter.makeEditModalByColumn("增加权限",columnsArray,addMenuSave);
    ajax_support.sendAjaxRequest("/sys/roleMenu/getMenuOutRole.do",{"user_role_id":user_role_id},"initMenuListSelect");
}

function initMenuListSelect(ajaxResult){
    console.log("initMenuListSelect "+JSON.stringify(ajaxResult));
    if(ajax_support.ajax_result_success(ajaxResult)) {
        var resultData = ajax_support.get_result_data(ajaxResult);
        var $edit_modal = $("#edit_modal");
        $.each(resultData,function(i,dataObj){
            var module_id = dataObj['module_id'];
            var module_name = dataObj['module_name'];
            $edit_modal.find("select[id='module_id']").append("<option value='"+module_id+"'>"+module_name+"</option>");
        });
    }
}

function addMenuSave(saveColumn){
    var paramArray = saveParams(saveColumn);
    paramArray['user_role_id'] = user_role_id;
    ajax_support.sendAjaxRequest("/sys/roleMenu/saveMenuForRole.do",paramArray,"saveCallBack");
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


function deleteModule(viewUserObj){
    modal_support_alter.make_alter("确定删除该功能？<font style='color:red;'>删除后所有该角色下的用户都将无法使用该功能</font>",
        sendDelRequest,{"module_id":$(viewUserObj).attr("module_id"),"user_role_id":user_role_id});
}

function sendDelRequest(moduleObj){
    ajax_support.sendAjaxRequest("/sys/roleMenu/delMenuFromRole.do",
        {"module_id":$(moduleObj).attr("module_id"),"user_role_id":$(moduleObj).attr("user_role_id")},"saveCallBack");
}

function saveCallBack(saveResult){
    if(ajax_support.ajax_result_success(saveResult)){
        modal_support_alter.make_alter("保存成功");
        modal_support_alter.closeEditModalByColumn();
        paging_data.make_paging_data("/sys/roleMenu/pagingMenuByRole.do",{"user_role_id":user_role_id});
    }else{
        modal_support_alter.make_alter("保存失败");
    }
}