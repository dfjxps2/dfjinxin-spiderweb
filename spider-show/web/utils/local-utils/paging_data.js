/**
 * Created by SongCQ on 2017/7/17.
 */

var paging_data = {

    createNew : function(){
        var paging_data_obj = {};

        /**
         * 请求后台返回分页数据，调用该方法时需要保证调用的JS中实现page_callback函数
         * @param url
         * @param params
         * @param pageSize
         * @param currPage
         */
        paging_data_obj.make_paging_data = function(url,params,pageSize,currPage){
            console.log("ajax_support "+ajax_support);
            var ajax_support_self = null;
            if(ajax_support!=null&&ajax_support.isRoot()!=null&&ajax_support.isRoot()){
                ajax_support_self = ajax_support.createNew();
            }else
                ajax_support_self = ajax_support;
            $(".paging").attr("back_url",url);
            if(params!=null)
                $(".paging").attr("paging_params",JSON.stringify(params));

            ajax_support_self.sendAjaxRequestByPage(url,params,pageSize,currPage,"page_data_callback");
        };

        return paging_data_obj;
    }

}

function page_data_callback(resonse_data){
    if(resonse_data!=null){
        var responseResult = ajax_support.createNew().ajax_result_success(resonse_data);
        if(responseResult){
            var page_data = resonse_data.resultData;

            var currPage = page_data.currPage;
            var pageSize = page_data.pageSize;
            var totalPage = page_data.totalPage;
            var totalNum = page_data.totalNum;
            var dataList = page_data.dataList;

            $paging = $(".paging");
            if($paging.length>0){
                if($paging.find("li").length>0) {
                    $paging.attr("page-size", pageSize);
                    $paging.attr("now-page-no", currPage);
                }else{
                    // $paging.append("<li class='paging_pre_next'><a href='#' aria-label='Previous' onclick='goToPage(this)' class='paging_previous'><span aria-hidden='true'>&laquo;</span></a></li>");
                    $paging.append("<li class='paging_pre'><a href='#' aria-label='Previous' onclick='goToPage(this)'><span aria-hidden='true'>&nbsp;</span></a></li>");
                    for(var i=1;i<(totalPage+1);i++){
                        $paging.append("<li><a class='paging_li' curr-page='"+i+"' onclick='goToPage(this)' href='#'>"+i+"</a></li>");
                    }
                    $paging.append("<li class='paging_next'><a href='#' aria-label='Next' onclick='goToPage(this)'><span aria-hidden='true'>&nbsp;</span></a></li>");

                    // $paging.append("<li class='paging_next'><a href='#' aria-label='Next' onclick='goToPage(this)' class='paging_next'><span aria-hidden='true'>&nbsp;</span></a></li>");
                    $paging.attr("page-size",pageSize);
                    $paging.attr("now-page-no",currPage);
                    $paging.attr("total-page",totalPage);
                }
                page_callback(dataList);
            }else{
                page_callback(responseResult);
            }
        }
    }
}

function goToPage(aObj){
    $paging = $(".paging");
    var url = $paging.attr("back_url");
    var pageSize = $paging.attr("page-size");
    var currPage = $(aObj).attr("curr-page");
    var totalPage = $paging.attr("total-page");
    // console.log("1 currPage value is "+currPage);

    if(currPage!=null&&currPage!=undefined){}
    else{
        var gotoOption = $(aObj).attr("aria-label");
        currPage = parseInt($paging.attr("now-page-no"));
        if(gotoOption=='Previous'){
            currPage = currPage-1;
            console.log("Pre is running");

        }else if(gotoOption=='Next'){
            currPage = currPage+1;
            console.log("Next is running");

        }

        if(currPage<1||currPage>totalPage){
            console.info("翻页按钮超限....");
            return ;
        }
    }
    var paging_params = $paging.attr("paging_params");
    var params = null;
    if(paging_params!=null)
        params = JSON.parse(paging_params);
    // console.log("2 currPage value is "+currPage+"---" );

    paging_data.createNew().make_paging_data(url,params,pageSize,currPage);
    $paging.attr("now-page-no",currPage);

    $(".paging_li_check").removeClass("paging_li_check");
    $("a[curr-page='"+currPage+"']").addClass("paging_li_check");
}