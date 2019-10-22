/**
 * Created by SongCQ on 2017/10/10.
 */

var edit_old_config = new Object();

$(document).ready(function(){
    var paging_data_support = paging_data.createNew();

    var tableSearchCreater = workbench_table.tableSearchCreater();

    var searchElements = new Array();

    var searchInfoObj = tableSearchCreater.searchInfoObject("/crawler/jobConfig/pagingList.do",searchElements,true,true,"add_job_config()");
    tableSearchCreater.createSearch(searchInfoObj);

    paging_data_support.make_paging_data("/crawler/jobConfig/pagingList.do");
});

function page_callback(dataList){
    // console.info("dataList "+JSON.stringify(dataList));

    var $user_table = $("#crawler_config_lst").find("tbody");
    $user_table.empty();
    var columnNames = ["user_info.user_name","job_info.job_name","param_name","param_value"];
    // var columnNames = ["user_id","user_info.user_name","job_id","job_info.job_name","param_name","param_value"];

    var table_support = workbench_table.createNew();

    var operationArray = new Array();
    var view_operation = table_support.operationsByName("查看", ["user_id","job_id","param_name","param_value"], "view_job_config(this)");
    var edit_operation = table_support.operationsByName("编辑", ["user_id","job_id","param_name","param_value"], "edit_job_config(this)");
    var delete_operation = table_support.operationsByName("删除", ["user_id","job_id","param_name","param_value"], "delete_job_config(this)");
    operationArray.push(view_operation);
    operationArray.push(edit_operation);
    operationArray.push(delete_operation);

    table_support.makeTable("crawler_config_lst",columnNames,dataList,operationArray);

}

function add_job_config(){
    modal_support.createNew().makeEditModalByColumn("新增配置",makeConfigInfoColumn(),addJobConfigSave);
    initUserOption();
    initJobOption();
}

function view_job_config(viewObj){
    var job_id = $(viewObj).attr("job_id");
    var user_id = $(viewObj).attr("user_id");
    var param_name = $(viewObj).attr("param_name");
    var param_value = $(viewObj).attr("param_value");
    modal_support.createNew().makeEditModalByColumn("查看配置",makeConfigInfoColumn("view"),addJobConfigSave);
    initUserOption();
    initJobOption();
    $("input[id='param_name']").val(param_name);
    $("input[id='param_value']").val(param_value);
}

function edit_job_config(viewObj){
    var job_id = $(viewObj).attr("job_id");
    var user_id = $(viewObj).attr("user_id");
    var param_name = $(viewObj).attr("param_name");
    var param_value = $(viewObj).attr("param_value");
    edit_old_config["job_id"] = job_id;
    edit_old_config["user_id"] = user_id;
    edit_old_config["param_name"] = param_name;
    edit_old_config["param_value"] = param_value;
    modal_support.createNew().makeEditModalByColumn("编辑配置",makeConfigInfoColumn("edit"),editJobConfigSave);
    initUserOption();
    initJobOption();
    $("input[id='param_name']").val(param_name);
    $("input[id='param_value']").val(param_value);
}

function initUserOption(){
    ajax_support.createNew().sendAjaxRequest("/sys/user/listAllUser.do",null,"userOptionData");
}

function userOptionData(userDataResult){
    if(ajax_support.createNew().ajax_result_success(userDataResult)){
        var userDataList = ajax_support.createNew().get_result_data(userDataResult);
        var $userIdSelect = $("select[id='user_id']");
        $userIdSelect.empty();
        if(userDataList!=null&&userDataList.length>0){
            $.each(userDataList,function(i,userData){
                var user_id = userData["user_id"];
                var user_name = userData["user_name"];
                $userIdSelect.append("<option value='"+user_id+"'>"+user_name+"</option>");
            });
        }
    }
    $("select[id='user_id']").val(edit_old_config['user_id']);
}

function initJobOption(){
    ajax_support.createNew().sendAjaxRequest("/crawler/jobMg/listAllCraw.do",null,"jobOptionData");
}

function jobOptionData(jobDataResult){
    if(ajax_support.createNew().ajax_result_success(jobDataResult)){
        var jobDataList = ajax_support.createNew().get_result_data(jobDataResult);
        var $jobIdSelect = $("select[id='job_id']");
        $jobIdSelect.empty();
        if(jobDataList!=null&&jobDataList.length>0){
            $.each(jobDataList,function(i,jobData){
                var job_id = jobData["job_id"];
                var job_name = jobData["job_name"];
                $jobIdSelect.append("<option value='"+job_id+"'>"+job_name+"</option>");
            });
        }
    }
    $("select[id='job_id']").val(edit_old_config['job_id']);

}


function delete_job_config(viewObj){
    var job_id = $(viewObj).attr("job_id");
    var user_id = $(viewObj).attr("user_id");
    var param_name = $(viewObj).attr("param_name");
    var param_value = $(viewObj).attr("param_value");
    modal_support.createNew().make_alter("确定删除当前采集配置？","confirmDel",
        {"job_id":job_id,"user_id":user_id,"param_name":param_name,"param_value":param_value}
        ,true);
}

function makeConfigInfoColumn(showType){
    var columnsArray = new Array();
    if(showType!=null&&showType=='view'){
        var paramId_modal = modal_support.getModalEditObject("param_name","参数名称","text",true,null,true);
        var paramVal_modal = modal_support.getModalEditObject("param_value","参数值","text",true,null,true);

        var user_modal = modal_support.getModalEditObject("user_id","用户","select",true,null,true,null);
        var job_modal = modal_support.getModalEditObject("job_id","采集任务","select",true,null,true,null);

        columnsArray.push(paramId_modal);
        columnsArray.push(paramVal_modal);
        columnsArray.push(user_modal);
        columnsArray.push(job_modal);
        return columnsArray;
    }
    var paramId_modal = modal_support.getModalEditObject("param_name","参数名称","text",true,null,false);
    var paramVal_modal = modal_support.getModalEditObject("param_value","参数值","text",true,null,false);

    var user_modal = modal_support.getModalEditObject("user_id","用户","select",true,null,false,null);
    var job_modal = modal_support.getModalEditObject("job_id","采集任务","select",true,null,false,null);

    columnsArray.push(paramId_modal);
    columnsArray.push(paramVal_modal);
    columnsArray.push(user_modal);
    columnsArray.push(job_modal);

    return columnsArray;
}


function confirmDel(jobParam){
    ajax_support.createNew().sendAjaxRequest("/crawler/jobConfig/deleConfig.do",jobParam,"delCallBack");
}

function addJobConfigSave(jobConfigParam){
    var saveParamObj = saveParams(jobConfigParam);
    ajax_support.createNew().sendAjaxRequest("/crawler/jobConfig/saveNewConfig.do",saveParamObj,"saveCallBack");
}

function editJobConfigSave(jobConfigParam){
    addJobConfigSave(jobConfigParam);
    confirmDel(edit_old_config);
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
    if(ajax_support.createNew().ajax_result_success(saveResult)){
        modal_support.createNew().make_alter("保存成功");
        modal_support.createNew().closeEditModalByColumn();
        paging_data.createNew().make_paging_data("/crawler/jobConfig/pagingList.do");
    }else{
        modal_support.createNew().make_alter("保存失败");
    }
}

function delCallBack(delResult){
    modal_support.createNew().make_alter(delResult["result_msg"]);
    paging_data.createNew().make_paging_data("/crawler/jobConfig/pagingList.do");
}