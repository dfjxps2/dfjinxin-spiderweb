/**
 * Created by SongCQ on 2017/8/4.
 */

$(document).ready(function(){
    var paging_data_support = paging_data.createNew();

    var tableSearchCreater = workbench_table.tableSearchCreater();


    var jobNameElement = tableSearchCreater.searchElementObject("host_name","采集服务器名称");
    var searchElements = new Array();
    searchElements.push(jobNameElement);

    var searchInfoObj = tableSearchCreater.searchInfoObject("/crawler/server/pagingServer.do",searchElements,true,true,"init_host_input()");
    tableSearchCreater.createSearch(searchInfoObj);

    paging_data_support.make_paging_data("/crawler/server/pagingServer.do");
});

function page_callback(dataList){
    console.info("dataList "+JSON.stringify(dataList));

    var job_list_table = $("#job_list_table").find("tbody");
    job_list_table.empty();
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


    table_support.makeTable("job_list_table",columnNames,dataList,operationArray);

}