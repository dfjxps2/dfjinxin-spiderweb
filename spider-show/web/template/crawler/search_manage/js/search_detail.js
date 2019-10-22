
var job_id = null;
var version = null;
$(document).ready(function(){
    var params_from_url = page_support.createNew().check_param_from_url();
    job_id = params_from_url["job_id"];
    version = params_from_url["version"];

    var jobSearchBean = new Object();
    jobSearchBean.version = version;
    jobSearchBean.job_id = job_id;
    ajax_support.createNew().sendAjaxRequest("/search/pageSearch/searchDetail.do",jobSearchBean,"searchCallBack");

});


function searchCallBack(searchResult){
    var ajax_support_tmp = ajax_support.createNew();
    if(ajax_support_tmp.ajax_result_success(searchResult)){
        var realResult = ajax_support_tmp.get_result_data(searchResult);
        var node_search_data = realResult[job_id];
        if(node_search_data!=null&&node_search_data.length>0){
            $.each(node_search_data,function(s,each_node_data){
                var page_id = each_node_data['page_id'];
                var id = each_node_data['id'];
                var url = each_node_data['url'];
                var crawl_time = each_node_data['crawl_time'];
                var page_content = each_node_data['page_content'];
                var page_source = each_node_data['page_source'];
                var version = each_node_data['_version_'];
                page_source = page_source.replace("<","&lt;");
                page_source = page_source.replace(">","&gt;");
                var $table_jq = $("<table class='tab-content-table'></table>");
                $table_jq.append("<tr class='result_table_tr'><td>页面编号:</td>" +
                    "<td>" +
                    "<span style='color:blue'>"+page_id+"</span>" +
                    "</td></td></tr>");
                $table_jq.append("<tr class='result_table_tr'><td>编号:</td><td>"+id+"</td></td></tr>");
                $table_jq.append("<tr class='result_table_tr'><td>URL:</td><td>"+url+"</td></td></tr>");
                $table_jq.append("<tr class='result_table_tr'><td>抓取时间:</td><td>"+crawl_time+"</td></td></tr>");
                $table_jq.append("<tr class='result_table_tr'><td>Solr Version:</td><td>"+version+"</td></td></tr>");
                $table_jq.append("<tr class='result_table_tr'><td>页面内容:</td>" +
                    "<td>"+page_content+"</td></td></tr>");
                $table_jq.append("<tr class='result_table_tr'><td>页面源码:</td>" +
                    "<td><textarea rows='6' style='color:#400080'>"+page_source+"</textarea></td></td></tr>");
                // $("#back_list").before($table_jq);
                $("body").before($table_jq);

            });
        }else{
            $("body").before("未找到结果....");

        }
    }
}