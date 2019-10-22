/**
 * Created by SongCQ on 2017/8/1.
 */

var ajax_support_obj = ajax_support.createNew();
var isView = false;

$(document).ready(function(){


    $("#proxy_service_edit_btn").click(function(){
        ajax_support_obj.sendAjaxRequest("/crawler/proxyServer/listProxyServers.do",null,"listProxyData");
    });

    $("#save_job_btn").click(function(){
        var paramObj = page_support.createNew().check_param_from_url();
        if(paramObj==null){
            save_new_job();
            return;
        }
        var view_type = paramObj["type"];
        if(view_type!=null&&view_type=='edit'){
            update_save_job();
        }else{
            save_new_job();
        }
    });

    initSelectData();
    viewOrEdit();

});

function viewOrEdit(){
    var paramObj = page_support.createNew().check_param_from_url();
    var job_id = paramObj["job_id"];
    var view_type = paramObj["type"];
    if(job_id==null||job_id==''){
        return ;
    }

    $("body").append("<input type='hidden' id='job_id' value='"+job_id+"'/>");

    if(view_type=='view'){
        $("input").attr("disabled","disabled");
        $("select").attr("disabled","disabled");
        $("button[id!='back_list']").attr("disabled","disabled");
        isView = true;
    }

    ajax_support_obj.sendAjaxRequest("/crawler/jobMg/getCrawlAndProxy.do",{"job_id":job_id},"setParamVal");
}

function setParamVal(dataJson){
    console.log(JSON.stringify(dataJson));
    if(ajax_support_obj.ajax_result_success(dataJson)){
        var resultData = ajax_support_obj.get_result_data(dataJson);
        var crawlData = resultData["crawlData"];
        var allProxyServerList = resultData["allProxyServerList"];

        var job_name = crawlData['job_name'];
        var is_valid = crawlData['is_valid'];
        var max_page_num = crawlData['max_page_num'];
        var start_urls = crawlData['start_urls'];
        var page_life_cycle = crawlData['page_life_cycle'];
        var entry_page_id = crawlData['entry_page_id'];
        var max_depth = crawlData['max_depth'];
        var crawl_src_type_id = crawlData['crawl_src_type_id'];
        var data_store_id = crawlData['data_store_id'];
        var job_schedule_id = crawlData['job_schedule_id'];
        var host_id = crawlData['host_id'];

        $("#job_name").val(job_name);
        $("#is_valid").find("option[value='"+is_valid+"']").attr("selected",true);
        $("#max_page_num").val(max_page_num);
        $("#start_urls").val(start_urls);
        $("#page_life_cycle").val(page_life_cycle);
        $("#entry_page_id").find("option[value='"+entry_page_id+"']").attr("selected",true);
        $("#max_depth").val(max_depth);
        $("#crawl_src_type_id").find("option[value='"+crawl_src_type_id+"']").attr("selected",true);
        $("#data_store_id").find("option[value='"+data_store_id+"']").attr("selected",true);
        $("#job_schedule_id").find("option[value='"+job_schedule_id+"']").attr("selected",true);
        $("#host_id").find("option[value='"+host_id+"']").attr("selected",true);

        if(allProxyServerList!=null&&allProxyServerList.length>0){
            $.each(allProxyServerList,function(i,proxyServer){
                var proxy_server_id = proxyServer["proxy_server_id"];
                var proxy_server_name = proxyServer["proxy_server_name"];
                var proxy_server_ip = proxyServer["proxy_server_ip"];
                var proxy_user_name = proxyServer["proxy_user_name"];
                var proxy_user_password = proxyServer["proxy_user_password"];
                makeProxyServerTable(proxy_server_id,proxy_server_name,proxy_server_ip,proxy_user_name,proxy_user_password);
            });
        }
    }
}

function initSelectData(){
    ajax_support_obj.sendAjaxRequest("/crawler/pageMg/listCrawlerPage.do",null,"initStartPage");//起始页面
    ajax_support_obj.sendAjaxRequest("/crawler/jobMg/crawlSrcType.do",null,"initCrawSrcType");//数据源类型
    ajax_support_obj.sendAjaxRequest("/crawler/jobMg/dataStore.do",null,"initDataStore");//数据存储
    ajax_support_obj.sendAjaxRequest("/crawler/jobMg/jobScheduleList.do",null,"initSchedule");//任务调度
    ajax_support_obj.sendAjaxRequest("/crawler/jobMg/jobHostList.do",null,"initjobHost");//采集服务器
}

function initStartPage(dataResult){
    // console.info(JSON.stringify(dataResult));
    if(initSelectOptions("entry_page_id",dataResult)){
        var dataList = ajax_support_obj.get_result_data(dataResult);
        $.each(dataList,function(i,dataObj){
            var page_id = dataObj['page_id'];
            var page_name = dataObj['page_name'];
            $("#entry_page_id").append("<option value='"+page_id+"'>"+page_name+"</option>");
        });
    }
}

function initCrawSrcType(dataResult){
    // console.info(JSON.stringify(dataResult));
    if(initSelectOptions("crawl_src_type_id",dataResult)){
        var dataList = ajax_support_obj.get_result_data(dataResult);
        $.each(dataList,function(i,dataObj){
            var crawl_src_type_id = dataObj['crawl_src_type_id'];
            var crawl_src_type_name = dataObj['crawl_src_type_name'];
            $("#crawl_src_type_id").append("<option value='"+crawl_src_type_id+"'>"+crawl_src_type_name+"</option>");
        });
    }
}

function initDataStore(dataResult){
    // console.info(JSON.stringify(dataResult));
    if(initSelectOptions("data_store_id",dataResult)){
        var dataList = ajax_support_obj.get_result_data(dataResult);
        $.each(dataList,function(i,dataObj){
            var data_store_id = dataObj['data_store_id'];
            var data_store_type = dataObj['data_store_type'];
            $("#data_store_id").append("<option value='"+data_store_id+"'>"+data_store_type+"</option>");
        });
    }
}

function initSchedule(dataResult){
    console.info(JSON.stringify(dataResult));
    if(initSelectOptions("job_schedule_id",dataResult)){
        var dataList = ajax_support_obj.get_result_data(dataResult);
        $.each(dataList,function(i,dataObj){
            var job_schedule_id = dataObj['job_schedule_id'];
            var job_schedule_type = dataObj['job_schedule_type'];
            $("#job_schedule_id").append("<option value='"+job_schedule_id+"'>"+job_schedule_type+"</option>");
        });
    }
}

function initjobHost(dataResult){
    console.info(JSON.stringify(dataResult));
    if(initSelectOptions("host_id",dataResult)){
        var dataList = ajax_support_obj.get_result_data(dataResult);
        $.each(dataList,function(i,dataObj){
            var host_id = dataObj['host_id'];
            var host_name = dataObj['host_name'];
            $("#host_id").append("<option value='"+host_id+"'>"+host_name+"</option>");
        });
    }
}

function initSelectOptions(selectId,dataResult){
    var $selectObj = $("#"+selectId);
    $selectObj.append("<option value=''>请选择</option>");
    if(ajax_support_obj.ajax_result_success(dataResult)){
        return true;
    }else
        false;
}

function listProxyData(resultData){
    // console.info(JSON.stringify(resultData));
    var ajax_support_new = ajax_support.createNew();
    if(ajax_support_new.ajax_result_success(resultData)){
        var dataList = ajax_support_new.get_result_data(resultData);
        var modal_support_obj = modal_support.createNew();
        var table_tiltes = ["服务器编号","服务器名称","服务器IP","用户名","口令","操作"];
        var param_names = ["proxy_server_id","proxy_server_name","proxy_server_ip","proxy_user_name","proxy_user_password"];

        var operationArray = new Array();
        var select_proxy_operation = workbench_table.createNew()
            .operationsByName("选择", ["proxy_server_id","proxy_server_name","proxy_server_ip","proxy_user_name","proxy_user_password"], "selectProxy(this)");
        operationArray.push(select_proxy_operation);

        if(dataList!=null&&dataList.length>0){
            modal_support_obj.makeTableEdit("代理服务器列表",null,table_tiltes,param_names,dataList,operationArray);

        }else{
            modal_support_obj.makeTableEdit("代理服务器列表",null,table_tiltes,param_names,new Array(),operationArray);
        }


    }

}

function selectProxy(selectRowObj){
    var selected_mark = $(selectRowObj).attr("selected_mark");
    if(selected_mark!=null&&selected_mark=='Y'){
        $(selectRowObj).removeAttr("selected_mark");
        $(selectRowObj).html("选择");
    }else{
        $(selectRowObj).attr("selected_mark","Y");
        $(selectRowObj).html("取消");
        var proxy_server_id = $(selectRowObj).attr("proxy_server_id");
        var proxy_server_name = $(selectRowObj).attr("proxy_server_name");
        var proxy_server_ip = $(selectRowObj).attr("proxy_server_ip");
        var proxy_user_name = $(selectRowObj).attr("proxy_user_name");
        var proxy_user_password = $(selectRowObj).attr("proxy_user_password");

        makeProxyServerTable(proxy_server_id,proxy_server_name,proxy_server_ip,proxy_user_name,proxy_user_password);
    }
}

function makeProxyServerTable(proxy_server_id,proxy_server_name,proxy_server_ip,proxy_user_name,proxy_user_password){
    var optionStr = "<td onclick='remove_proxy_server(this)' class='workbench_table_operation'>删除</td>";

    if(isView){
        optionStr = "<td></td>";
    }

    $("#selected_proxy_server_table").find("tbody").append("<tr id='"+proxy_server_id+"'>" +
        "<td>"+proxy_server_id+"</td>" +
        "<td>"+proxy_server_name+"</td>" +
        "<td>"+proxy_server_ip+"</td>" +
        "<td>"+proxy_user_name+"</td>" +
        "<td>"+proxy_user_password+"</td>" +
        optionStr +
        "</tr>");


}

function remove_proxy_server(removeServer){
    $(removeServer).parent().remove();
}

function save_new_job(){

    // ajax_json_obj.addJsonData("jobInfo",getParams());
    // ajax_json_obj.addJsonData("proxyServers",getProxyServers());
    // ajax_json_obj.sendJsonAjaxRequest("/crawler/jobMg/saveNewJob.do","callServiceResult");
    var jobInfoParam = getParams();
    jobInfoParam["proxyServers"] = JSON.stringify(getProxyServers());
    ajax_support.createNew().sendAjaxRequest("/crawler/jobMg/saveNewJob.do",jobInfoParam,"callServiceResult");
}

function update_save_job(){
    var jobInfoParam = getParams();
    jobInfoParam["job_id"]=$("#job_id").val();
    // ajax_json_obj.addJsonData("jobInfo",jobInfoParam);
    // ajax_json_obj.addJsonData("proxyServers",getProxyServers());
    // ajax_json_obj.sendJsonAjaxRequest("/crawler/jobMg/updateJobInfo.do","callServiceResult");

    jobInfoParam["proxyServers"] = JSON.stringify(getProxyServers());
    ajax_support.createNew().sendAjaxRequest("/crawler/jobMg/updateJobInfo.do",jobInfoParam,"callServiceResult");

}

function getParams(){
    var paramObj = new Object();
    var allInput = $(".input_style").find("input");
    var allSelect = $(".input_style").find("select");
    if(allInput.length>0){
        $.each(allInput,function(i,inputObj){
            var inputId = $(inputObj).attr("id");
            var inputVal = $(inputObj).val();
            if(inputVal!=null&&inputVal!='')
                paramObj[inputId] = inputVal;
        });
    }

    if(allSelect.length>0){
        $.each(allSelect,function(i,selectObj){
            var selectId = $(selectObj).attr("id");
            var selectVal = $(selectObj).val();
            if(selectVal!=null&&selectVal!='')
                paramObj[selectId] = selectVal;
        });
    }
    return paramObj;
}

function getProxyServers(){
    var serverList = new Array();
    var allSelectedServer = $("#selected_proxy_server_table").find("tbody").find("tr");
    if(allSelectedServer!=null&&allSelectedServer.length>0){
        $.each(allSelectedServer,function(i,serverTr){
            var serverId = $(serverTr).attr("id");
            serverList.push(serverId);

        });
    }
    return serverList;
}

function callServiceResult(saveResult){
    modal_support.createNew().make_alter(saveResult["result_msg"],function(){
        page_support.createNew().forward_new_page("/template/crawler/job_manage/job_manage_main.html");
    },null,false);
}
