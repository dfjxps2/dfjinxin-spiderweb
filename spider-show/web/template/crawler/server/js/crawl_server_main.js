/**
 * Created by SongCQ on 2017/8/4.
 */

$(document).ready(function(){
    var paging_data_support = paging_data.createNew();

    var tableSearchCreater = workbench_table.tableSearchCreater();


    var jobNameElement = tableSearchCreater.searchElementObject("host_name","请输入主机名称");
    var searchElements = new Array();
    searchElements.push(jobNameElement);

    var searchInfoObj = tableSearchCreater.searchInfoObject("/crawler/server/pagingServer.do",searchElements,true,true,"init_host_input()");
    tableSearchCreater.createSearch(searchInfoObj);

    paging_data_support.make_paging_data("/crawler/server/pagingServer.do");
});

function page_callback(dataList){
    console.info("dataList "+JSON.stringify(dataList));

    var $user_table = $("#server_list_table").find("tbody");
    $user_table.empty();
    var columnNames = ["host_id","host_name","host_ip","host_status_cn","user_group.user_group_name"];

    var table_support = workbench_table.createNew();

    var operationArray = new Array();
    var view_operation = table_support.operationsByName("查看", ["host_id"], "view_host(this)");
    var edit_operation = table_support.operationsByName("编辑", ["host_id"], "edit_host(this)");
    var delete_operation = table_support.operationsByName("删除", ["host_id"], "deleteHost(this)");
    var listCrawler_operation = table_support.operationsByName("采集列表", ["host_id"], "listCrawler(this)");
    operationArray.push(view_operation);
    operationArray.push(edit_operation);
    operationArray.push(delete_operation);
    operationArray.push(listCrawler_operation);


    table_support.makeTable("server_list_table",columnNames,dataList,operationArray);

}

function init_host_input(host_id){
    var modal_support_tool = modal_support.createNew();

    var host_name_op = modal_support.getModalEditObject("host_name","服务器名称");
    var host_ip_op = modal_support.getModalEditObject("host_ip","服务器地址");
    var host_status_op = modal_support.getModalEditObject("host_status","服务器状态","select",true,null,false,
        [{options_value:1,options_text:"在线"},
            {options_value:0,options_text:"离线"}]);
    var group_id_op = modal_support.getModalEditObject("user_group_id","用户组","select");
    var opArray = new Array();
    if(host_id!=null){
        var host_id_op = modal_support.getModalEditObject("host_id","服务器编号","text",true,host_id,true);
        opArray.push(host_id_op);
    }
    opArray.push(host_name_op);
    opArray.push(host_ip_op);
    opArray.push(host_status_op);
    opArray.push(group_id_op);



    modal_support_tool.makeEditModalByColumn("新增采集服务器",opArray,saveHost);
    ajax_support.createNew().sendAjaxRequest("/sys/group/listUserGroup.do",null,"groupBack");
}

function view_host(clickObj){
    var modal_support_tool = modal_support.createNew();
    var host_id_op = modal_support.getModalEditObject("host_id","服务器编号","text",true,$(clickObj).attr("host_id"),true);
    var host_name_op = modal_support.getModalEditObject("host_name","服务器名称","text",true,null,true);
    var host_ip_op = modal_support.getModalEditObject("host_ip","服务器地址","text",true,null,true);
    var host_status_op = modal_support.getModalEditObject("host_status","服务器状态","select",true,null,true,
        [{options_value:1,options_text:"离线"},
            {options_value:0,options_text:"在线"}]);
    var group_id_op = modal_support.getModalEditObject("user_group_id","用户组","select",true,null,true);
    var opArray = [host_id_op,host_name_op,host_ip_op,host_status_op,group_id_op];

    modal_support_tool.makeEditModalByColumn("查看采集服务器",opArray);
    ajax_support.createNew().sendAjaxRequest("/sys/group/listUserGroup.do",null,"groupBack");
    ajax_support.createNew().sendAjaxRequest("/crawler/server/getServer.do",{"host_id":$(clickObj).attr("host_id")},"paramValInit");
}

function edit_host(clickObj){
    init_host_input($(clickObj).attr("host_id"));
    ajax_support.createNew().sendAjaxRequest("/crawler/server/getServer.do",{"host_id":$(clickObj).attr("host_id")},"paramValInit");
}

function paramValInit(pageData){
    var ajaxSupp = ajax_support.createNew();
    if(ajaxSupp.ajax_result_success(pageData)) {
        var dataJson = ajaxSupp.get_result_data(pageData);
        var host_name = dataJson["host_name"];
        var host_ip = dataJson["host_ip"];
        var host_status = dataJson["host_status"];
        var user_group_id = dataJson["user_group_id"];
        $("#modal_edit_table").find("#host_name").val(host_name);
        $("#modal_edit_table").find("#host_ip").val(host_ip);
        $("#modal_edit_table").find("#host_status").val(host_status);
        $("#modal_edit_table").find("#user_group_id").val(user_group_id);
    }
}

function groupBack(dataResult){
    var ajaxSupp = ajax_support.createNew();
    if(ajaxSupp.ajax_result_success(dataResult)){
        var dataJson = ajaxSupp.get_result_data(dataResult);
        console.info("dataResult  ..."+JSON.stringify(dataJson["dataList"]));
        var dataList = dataJson["dataList"];
        $.each(dataList,function(i,dataObj){
            var user_group_id = dataObj.user_group_id;
            var user_group_name = dataObj.user_group_name;
            $("#user_group_id").append("<option value='"+user_group_id+"'>"+user_group_name+"</option>");
        })
    }
}


function saveHost(hostParams){
    // console.log("save host is running...."+JSON.stringify(checkOutParams(hostParams)));
    var allSaveParam = checkOutParams(hostParams);
    if(allSaveParam["host_id"]!=null){
        ajax_support.createNew().sendAjaxRequest("/crawler/server/updateServer.do",checkOutParams(hostParams),"saveCallBack")

    }else{
        ajax_support.createNew().sendAjaxRequest("/crawler/server/saveNewServer.do",checkOutParams(hostParams),"saveCallBack")
    }


}

function checkOutParams(hostParams){
    var paramObj = new Object();
    $.each(hostParams,function(i,hostParam){
        var column_id = hostParam["column_id"];
        var column_val = hostParam["column_val"];
        paramObj[column_id] = column_val;
    });
    return paramObj;
}

function saveCallBack(callBackResult){
    console.log("save call back is running "+JSON.stringify(callBackResult));
    modal_support.createNew().make_alter(callBackResult["result_msg"],"listPage");

}

function listPage(){
    var paging_data_support = paging_data.createNew();
    paging_data_support.make_paging_data("/crawler/server/pagingServer.do");
    modal_support.createNew().closeEditModalByColumn();
}

function listCrawler(clickObj){
    page_support.createNew().forward_new_page("/template/crawler/job_manage/job_paging_by_host.html?host_id="+$(clickObj).attr("host_id"));
}

function deleteHost(host_id){
    modal_support.createNew().make_alter("确定删除服务器？","confirmDel",{"host_id":$(host_id).attr("host_id")},true);


}

function confirmDel(host_id){
    ajax_support.createNew().sendAjaxRequest("/crawler/server/delServer.do",{"host_id":$(host_id).attr("host_id")},"saveCallBack")
}