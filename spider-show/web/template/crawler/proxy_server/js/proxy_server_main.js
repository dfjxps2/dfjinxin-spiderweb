/**
 * Created by SongCQ on 2017/8/7.
 */
$(document).ready(function(){
    var paging_data_support = paging_data.createNew();

    var tableSearchCreater = workbench_table.tableSearchCreater();


    var jobNameElement = tableSearchCreater.searchElementObject("host_name","请输入主机名称");
    var searchElements = new Array();
    searchElements.push(jobNameElement);

    var searchInfoObj = tableSearchCreater.searchInfoObject("/crawler/proxyServer/pagingProxyServers.do",searchElements,true,true,"init_host_input()");
    tableSearchCreater.createSearch(searchInfoObj);

    paging_data_support.make_paging_data("/crawler/proxyServer/pagingProxyServers.do");
});

function page_callback(dataList){
    console.info("dataList "+JSON.stringify(dataList));

    var $user_table = $("#server_list_table").find("tbody");
    $user_table.empty();
    var columnNames = ["proxy_server_id","proxy_server_name","proxy_server_ip","proxy_user_name"];

    var table_support = workbench_table.createNew();

    var operationArray = new Array();
    var view_operation = table_support.operationsByName("查看", ["proxy_server_id"], "view_server(this)");
    var edit_operation = table_support.operationsByName("编辑", ["proxy_server_id"], "edit_server(this)");
    var delete_operation = table_support.operationsByName("删除", ["proxy_server_id"], "delete_server(this)");
    operationArray.push(view_operation);
    operationArray.push(edit_operation);
    operationArray.push(delete_operation);

    table_support.makeTable("server_list_table",columnNames,dataList,operationArray);

}

function view_server(clickObj){
    var modal_support_tool = modal_support.createNew();
    var proxy_server_id = modal_support.getModalEditObject("proxy_server_id","代理服务器编号","text",true,$(clickObj).attr("proxy_server_id"),true);
    var proxy_server_name = modal_support.getModalEditObject("proxy_server_name","代理服务器名称","text",true,null,true);
    var proxy_server_ip = modal_support.getModalEditObject("proxy_server_ip","代理服务器地址","text",true,null,true);
    var proxy_user_name = modal_support.getModalEditObject("proxy_user_name","服务器用户名","text",true,null,true);
    var proxy_user_password = modal_support.getModalEditObject("proxy_user_password","服务器密码","text",true,null,true);
    var opArray = [proxy_server_id,proxy_server_name,proxy_server_ip,proxy_user_name,proxy_user_password];

    modal_support_tool.makeEditModalByColumn("查看代理服务器",opArray);
    ajax_support.createNew().sendAjaxRequest("/crawler/proxyServer/proxyServer.do",{"proxy_server_id":$(clickObj).attr("proxy_server_id")},"paramValInit");
}

function edit_server(clickObj){
    init_host_input($(clickObj).attr("proxy_server_id"));
    ajax_support.createNew().sendAjaxRequest("/crawler/proxyServer/proxyServer.do",{"proxy_server_id":$(clickObj).attr("proxy_server_id")},"paramValInit");
}

function paramValInit(pageData){
    var ajaxSupp = ajax_support.createNew();
    if(ajaxSupp.ajax_result_success(pageData)) {
        var dataJson = ajaxSupp.get_result_data(pageData);
        var proxy_server_id = dataJson["proxy_server_id"];
        var proxy_server_name = dataJson["proxy_server_name"];
        var proxy_server_ip = dataJson["proxy_server_ip"];
        var proxy_user_name = dataJson["proxy_user_name"];
        var proxy_user_password = dataJson["proxy_user_password"];
        $("#modal_edit_table").find("#proxy_server_id").val(proxy_server_id);
        $("#modal_edit_table").find("#proxy_server_name").val(proxy_server_name);
        $("#modal_edit_table").find("#proxy_server_ip").val(proxy_server_ip);
        $("#modal_edit_table").find("#proxy_user_name").val(proxy_user_name);
        $("#modal_edit_table").find("#proxy_user_password").val(proxy_user_password);
    }
}

function init_host_input(proxy_server_id){
    var modal_support_tool = modal_support.createNew();

    var proxy_server_name = modal_support.getModalEditObject("proxy_server_name","代理服务器名称","text");
    var proxy_server_ip = modal_support.getModalEditObject("proxy_server_ip","代理服务器地址","text");
    var proxy_user_name = modal_support.getModalEditObject("proxy_user_name","服务器用户名","text");
    var proxy_user_password = modal_support.getModalEditObject("proxy_user_password","服务器密码","text");

    var opArray = new Array();

    opArray.push(proxy_server_name);
    opArray.push(proxy_server_ip);
    opArray.push(proxy_user_name);
    opArray.push(proxy_user_password);
    if(proxy_server_id!=null){
        var proxy_server_id = modal_support.getModalEditObject("proxy_server_id","代理服务器编号","text",true,proxy_server_id,true);
        opArray.unshift(proxy_server_id);
        modal_support_tool.makeEditModalByColumn("编辑采集服务器",opArray,saveProxyServer);
    }else{
        modal_support_tool.makeEditModalByColumn("新增采集服务器",opArray,saveProxyServer);
    }

}

function saveProxyServer(hostParams){
    var allSaveParam = checkOutParams(hostParams);
    if(allSaveParam["proxy_server_id"]!=null){
        ajax_support.createNew().sendAjaxRequest("/crawler/proxyServer/updateServer.do",checkOutParams(hostParams),"saveCallBack")

    }else{
        ajax_support.createNew().sendAjaxRequest("/crawler/proxyServer/saveNewServer.do",checkOutParams(hostParams),"saveCallBack")
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
    paging_data_support.make_paging_data("/crawler/proxyServer/pagingProxyServers.do");
    modal_support.createNew().closeEditModalByColumn();
}

function delete_server(proxy_server_id){
    modal_support.createNew().make_alter("确定删除服务器？","confirmDel",{"proxy_server_id":$(proxy_server_id).attr("proxy_server_id")},true);
}

function confirmDel(proxy_server_id){
    ajax_support.createNew().sendAjaxRequest("/crawler/proxyServer/delServer.do",{"proxy_server_id":$(proxy_server_id).attr("proxy_server_id")},"saveCallBack")
}