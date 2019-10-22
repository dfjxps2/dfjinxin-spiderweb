/**
 * Created by SongCQ on 2017/9/25.
 */
var zTreeObj;
var eachPageNum = 8;
var refreshFlag = false;
$(document).ready(function(){

    $("#more_search").hide();

    $("#more_search_btn").click(function(){
        // var is_show = $("#more_search").attr("disabled");
        var is_show = $('#more_search').is(':visible');
        if(is_show){
            $("#more_search").hide();
        }else{
            $("#more_search").show();
        }
    });

    $("#doSearchBtn").click(function(){
        refreshFlag = true;
        doSearch();
    });

    getJobCatData();

    initStyle();


});

function initStyle(){
    var outerHeight = $(".search_input_div").outerHeight();
    var pagingFooterHeight = 80;
    var dataTab = 50;
    var pageHeight = $(document).height();
    $("#searchResultTabContent").height(pageHeight-pagingFooterHeight-outerHeight-dataTab);
    $(".paging_area").css("width","80%");
}

function getJobCatData(){
    ajax_support.createNew().sendAjaxRequest("/search/pageSearch/listJobAndTypes.do",null,"initTree");
}

function initTree(dataList){
    var ajaxSupportTmp = ajax_support.createNew();
    var treeNodes = new Array();
    if(ajaxSupportTmp.ajax_result_success(dataList)){
        var responseData = ajaxSupportTmp.get_result_data(dataList);
        $.each(responseData,function(i,catData){
            var job_cat_id= catData["id"];
            var super_cat_id= catData["super_id"];
            var job_cat_name= catData["name"];
            treeNodes.push({"id":job_cat_id, "pId":super_cat_id, "name":job_cat_name});
        });
    }

    var setting = {
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        view: {
            // fontCss : {color:"#ff0011",fontSize:20,fontWeight:"bold"}
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "s", "N": "s" }
        }
    };

    zTreeObj = $.fn.zTree.init($("#job_cat_tree"), setting, treeNodes);

}

function searchPaging(pagingObj){
    var curr_page = $(pagingObj).attr("curr-page");
    var paging_node = $(pagingObj).attr("paging_node");
    $("#paging_ul_"+paging_node).attr("now_page_num",curr_page);
    var startsNum = eachPageNum*(curr_page-1);
    refreshFlag = false;
    doSearch();
}

function doSearch(){
    zTreeObj.refresh();
    var allCheckNodes = zTreeObj.getCheckedNodes(true);
    if(allCheckNodes!=null&&allCheckNodes.length>0){
        var sendNodeArray = new Array();
        var nodeInfoArray = new Array();
        var pagingsData = new Object();
        $.each(allCheckNodes,function(i,checkedNode){
            if(checkedNode!=null&&!checkedNode["isParent"]&&checkedNode["children"]==null){
                sendNodeArray.push(checkedNode["id"]);
                nodeInfoArray.push(checkedNode);
                var pagingUlObj = $("#paging_ul_"+checkedNode["id"]);
                var paging_node = pagingUlObj.attr("now_page_num");
                if(paging_node!=null){}
                else paging_node = 0;
                pagingsData[checkedNode["id"]] = paging_node;
            }
        });

        var
            jobSearchBean = new Object();
        jobSearchBean.jobIds = JSON.stringify(sendNodeArray);
        jobSearchBean.pagingMap = pagingsData;
        jobSearchBean.rows = eachPageNum;
        jobSearchBean.searchContent = $("#search_content").val();
        jobSearchBean.url = $("#url").val();
        jobSearchBean.jobStartDate = $("#jobStartDate").val();
        jobSearchBean.jobEndData = $("#jobEndData").val();
        jobSearchBean.pageId = $("#pageId").val();

        if(sendNodeArray!=null&&sendNodeArray.length>0){
            var $search_result_tab = $("#search_result_tab");
            var $searchResultTabContent = $("#searchResultTabContent");
            $search_result_tab.empty();
            $searchResultTabContent.empty();
            $.each(nodeInfoArray,function(i,checkNodeInfo){
                var checkNodeId = checkNodeInfo["id"];
                var checkNodeName = checkNodeInfo["name"];
                if(i==0){
                    $search_result_tab.append("<li node_id='"+checkNodeId+"' id='check_node_li_"+checkNodeId+"' num_found='100' now_page_num='1' class='tabLi active'><a href='#check_node_"+checkNodeId+"' data-toggle='tab'>"+checkNodeName+"</a></li>");
                    $searchResultTabContent.append("<div istabdiv='Y' class='tab-pane active' node_id='"+checkNodeId+"' id='check_node_"+checkNodeId+"'></div>");

                }else{
                    $search_result_tab.append("<li node_id='"+checkNodeId+"' id='check_node_li_"+checkNodeId+"' class='tabLi' now_page_num='1' num_found='99'><a href='#check_node_"+checkNodeId+"' data-toggle='tab'>"+checkNodeName+"</a></li>");
                    $searchResultTabContent.append("<div istabdiv='Y' class='tab-pane fade' node_id='"+checkNodeId+"' id='check_node_"+checkNodeId+"'></div>");

                }
            });

            $(".tabLi").click(function(){
                var node_id = $(this).attr("node_id");
                $(".paging[id!='paging_ul_"+node_id+"']").hide();
                $(".paging[id='paging_ul_"+node_id+"']").show();
            });
        }

        ajax_support.createNew().sendAjaxRequest("/search/pageSearch/doSearch.do",jobSearchBean,"searchCallBack");

        // ajax_support.createNew().sendJsonAjaxRequest("/search/pageSearch/doSearch.do?"+JSON.stringify(jobSearchBean),"searchCallBack");
    }
}

function searchCallBack(searchResult){
    var ajax_support_tmp = ajax_support.createNew();
    if(ajax_support_tmp.ajax_result_success(searchResult)){
        var realResult = ajax_support_tmp.get_result_data(searchResult);
        var allCheckNodesTab = $("div[istabdiv='Y']");
        allCheckNodesTab.empty();
        if(refreshFlag)
            $("#paging_area").empty();
        var firstNode = "";
        $.each(allCheckNodesTab,function(i,checkNodeTab){
            var real_node_id = $(checkNodeTab).attr("node_id");
            if(i==0)
                firstNode = real_node_id;
            var node_search_data = realResult[real_node_id];
            if(node_search_data!=null&&node_search_data.length>0){
                var node_num_found = realResult[real_node_id+"_num_found"];
                $("#check_node_li_"+real_node_id).attr("num_found",node_num_found);
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
                        "<td version='"+version+"' job_id='"+real_node_id+"' style='cursor: pointer;color:blue;' onclick='showDetail(this)' >" +
                        "<span style='margin-right:15px;'>"+page_id+"</span>" +
                        "<span class='glyphicon glyphicon-eye-open'></span>"+
                        "</td></td></tr>");
                    $table_jq.append("<tr class='result_table_tr'><td>URL:</td><td>"+url+"</td></td></tr>");
                    $table_jq.append("<tr class='result_table_tr'><td>抓取时间:</td><td>"+crawl_time+"</td></td></tr>");
                    $table_jq.append("<tr class='result_table_tr'><td>页面内容:</td><td>"+JSON.stringify(page_content)+"</td></td></tr>");
                    $table_jq.append("<tr class='result_table_tr'><td>页面源码:</td>" +
                        "<td><textarea style='color:#400080'>"+page_source+"</textarea></td></td></tr>");
                    $("#check_node_"+real_node_id).append($table_jq);
                });

                if(refreshFlag){
                    $("#paging_area").append("<ul id='paging_ul_"+real_node_id+"' class='paging'></ul>");
                    var page_num =  Math.ceil(node_num_found/eachPageNum);
                    for(var tt = 0;tt<page_num;tt++){
                        var r_page_num = tt+1;
                        $("#paging_ul_"+real_node_id).append("<li><a class='paging_li' paging_node='"+real_node_id+"' curr-page='"+r_page_num+"' onclick='searchPaging(this)' href='#'>"+r_page_num+"</a></li>");
                    }
                    $(".paging[id!='paging_ul_"+firstNode+"']").hide();
                }
            }else{

                $("#check_node_"+real_node_id).append("未找到结果....");
            }
        });


        $('#search_result_tab li:eq(0) a').tab('show');

    }
}

function getCheckedNode(nodeData){
    if(nodeData["isParent"]){
        var sonNodes = nodeData["children"];
        var nodeArray = new Array();
        $.each(sonNodes,function(i,sonNode){
            if(sonNode["isParent"]){
                var sonNodeArrayTmp = getCheckedNode(sonNode);
                $.each(sonNodeArrayTmp,function(s,sonNodeTmp){
                    nodeArray.push(sonNodeTmp);//D
                })
            }else{
                nodeArray.push(sonNode);//D
            }
        });
        return nodeArray;
    }else{
        var nodeArray = new Array();
        var nodeId = nodeData["id"];
        nodeArray.push(nodeId);
        return nodeArray;//D
    }
}

function showDetail(clickObj){
    var job_id = $(clickObj).attr("job_id");
    var version = $(clickObj).attr("version");
    page_support.createNew().forward_new_page("/template/crawler/search_manage/search_detail.html?job_id="+job_id+"&version="+version);
}