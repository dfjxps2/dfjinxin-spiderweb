/**
 * Created by SongCQ on 2017/8/4.
 */
$(document).ready(function(){
    var paging_data_support = paging_data.createNew();

    var tableSearchCreater = workbench_table.tableSearchCreater();


    var hostnameElement = tableSearchCreater.searchElementObject("host_name","服务器名称");
    var searchElements = new Array();
    searchElements.push(hostnameElement);

    var searchInfoObj = tableSearchCreater.searchInfoObject("/crawler/jobMg/pagingListByHost.do",searchElements,null,false);
    tableSearchCreater.createSearch(searchInfoObj);

    var paramUrl = page_support.createNew().check_param_from_url();
    var host_id = paramUrl["host_id"];
    paging_data_support.make_paging_data("/crawler/jobMg/pagingListByHost.do",{"host_id":host_id});
});

function page_callback(dataList){
    console.info("dataList "+JSON.stringify(dataList));

    var $user_table = $("#job_list_table").find("tbody");
    $user_table.empty();
    var columnNames = ["job_id","job_name","entry_page_id","user.user_name","is_valid_cn","jobStatus.run_status_cn"];

    var table_support = workbench_table.createNew();

    var operationArray = new Array();
    var view_operation = table_support.operationsByName("查看", ["job_id"], "viewJob(this)");
    operationArray.push(view_operation);

    table_support.makeTable("job_list_table",columnNames,dataList,operationArray);

}

function viewJob(viewObj){
    var job_id = $(viewObj).attr("job_id");
    page_support.createNew().forward_new_page("/template/crawler/job_manage/edit_job.html?job_id="+job_id+"&type=view")
}