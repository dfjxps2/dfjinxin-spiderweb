/**
 * Created by SongCQ on 2017/8/8.
 */
$(document).ready(function(){
    var jobId = page_support.createNew().check_param_from_url().job_id;

    var paging_data_support = paging_data.createNew();

    var tableSearchCreater = workbench_table.tableSearchCreater();


    var jobNameElement = tableSearchCreater.searchElementObject("job_name","请输入采集名称");
    var searchElements = new Array();
    searchElements.push(jobNameElement);

    var searchInfoObj = tableSearchCreater.searchInfoObject("/crawler/pageView/listCrawlerPage.do",searchElements,true,false);
    tableSearchCreater.createSearch(searchInfoObj);

    var param_obj = new Object();
    param_obj["job_id"] = jobId;
    paging_data_support.make_paging_data("/crawler/pageView/listCrawlerPage.do",param_obj);
});

function page_callback(dataList){

    var job_list_table = $("#job_list_table").find("tbody");
    job_list_table.empty();
    var columnNames = ["job_id","job_name","page_url","download_time_str"];

    var table_support = workbench_table.createNew();

    var operationArray = new Array();
    var view_operation = table_support.operationsByName("查看", ["job_id"], "view_host(this)");
    operationArray.push(view_operation);

    table_support.makeTable("job_list_table",columnNames,dataList,null);

}