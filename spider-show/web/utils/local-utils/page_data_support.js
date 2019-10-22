
// 调用后台接口获取数据的公用方法

var ajax_support = {

    createNew: function(){
        var ajax_support = {};

        ajax_support.DEFUALT_PAGE_SIZE = 10;//默认每页显示条数

        ajax_support.json_data_list = new Array();

        ajax_support.sendAjaxJsonp = function(url,params){
            if(use_jsonp){
                $.ajax({
                    url:SERVICE_HOST+url,
                    type:'post',
                    data:params,
                    xhrFields:{withCredentials:true},
                    dataType:'jsonp',
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    error:function(data){
                        console.log("brower get one error....."+data);
                    }

                });
            }
        };

        ajax_support.sendAjaxRequest = function(url,params,callBackFunction){
            // console.log("sendAjaxRequest is running....");
            if(use_jsonp){
                var realUrl = url + "?web_call_back=" + callBackFunction;
                this.sendAjaxJsonp(realUrl,params);
            }else{
                $.ajax({
                    type : 'POST',
                    // contentType : 'application/json; charset=utf-8',
                    url : SERVICE_HOST+url,
                    data : params,
                    dataType : 'json',
                    success : function(data) {
                        try{
                            data = JSON.parse(data)
                        }catch(e){

                        }
                        // 生成报表
                        if(callBackFunction!=null){
                            window[callBackFunction](data);
                        }

                    },
                    error : function(data) {
                        var modal_support_page_data_tmp = null;
                        if(modal_support!=null)
                            modal_support_page_data_tmp = modal_support.createNew();
                        else{
                            modal_support_page_data_tmp = modal_support;
                        }
                        if(data!=null&&!ajax_support.ajax_result_success(data)){
                            modal_support_page_data_tmp.make_alter(data.result_msg);
                        }else{
                            alert("error");
                        }
                    }

                });
            }
        };

        // ajax_support.sendJsonAjaxRequest = function(url,params,callBackFunction){
        //     var jsonStr = JSON.stringify(params);
        //     var realUrl = url + "?web_call_back=" + callBackFunction+"&isJson=Y";
        //     this.sendAjaxRequestSimple(realUrl,JSON.parse(JSON.stringify(params)));
        // };

        ajax_support.sendJsonAjaxRequest = function(url,callBackFunction){
            if(use_jsonp){
                var realUrl = url + "?web_call_back=" + callBackFunction;
                var sendParam = new Object();
                if(this.json_data_list.length>0){
                    $.each(this.json_data_list,function(i,json_data){
                        var jsonName = json_data["jsonName"];
                        var jsonData = json_data["jsonData"];
                        var jsonStr = JSON.stringify(jsonData);
                        sendParam[jsonName] = jsonStr;
                    });
                    sendParam["isJson"] = "Y";
                    this.sendAjaxRequest(realUrl,sendParam,callBackFunction);
                }
            }else{
                if(this.json_data_list.length>0){
                    var sendParam = new Object();

                    $.each(this.json_data_list,function(i,json_data){
                        var jsonName = json_data["jsonName"];
                        var jsonData = json_data["jsonData"];
                        var jsonStr = JSON.stringify(jsonData);
                        sendParam[jsonName] = jsonData;
                    });
                    this.sendAjaxRequest(realUrl,sendParam,callBackFunction);

                }
            }


        };


        ajax_support.addJsonData = function(jsonName,jsonData){
            this.json_data_list.push({"jsonName":jsonName,"jsonData":jsonData});
        };

        /**
         * 带分页数据
         * @param url 目标后台服务地址
         * @param params 请求参数
         * @param callBackFunction 回调函数
         * @param pageSize 每页数据量
         * @param currPage 当前页码
         */
        ajax_support.sendAjaxRequestByPage = function(url,param,pageSize,currPage,callBackFunction){
            // console.log("sendAjaxRequestByPage is running....");
            if(pageSize==null||pageSize==''){
                pageSize = this.DEFUALT_PAGE_SIZE;
            }
            if(currPage==null||currPage==''){
                currPage = 1;
            }
            // this.sendAjaxRequest()
            if(param==null)
                param = new Object();
            param["pageSize"] = pageSize;
            param["currPage"] = currPage;
            this.sendAjaxRequest(url,param,callBackFunction);
        };

        ajax_support.ajax_result_success = function(result_json){
            if(result_json.result=="SUCCESS"){
                return true;
            }else{
                var modal_support_page_data_tmp = null;
                if(modal_support!=null)
                    modal_support_page_data_tmp = modal_support;
                else{
                    modal_support_page_data_tmp = modal_support.createNew();
                }
                if(result_json.faild_reason == "USER_NOT_LOGIN"){
                    modal_support_page_data_tmp.make_alter(result_json.result_msg,auth_failed);
                }else if(result_json.faild_reason == "USERNM_NOT_FOUND"){
                    modal_support_page_data_tmp.make_alter(result_json.result_msg,auth_failed);
                }

                function auth_failed(){
                    page_support.forward_new_page("/template/sys/login/login.html");
                }

                return false;
            }
        };

        ajax_support.get_result_data = function(result_json){
            return result_json.resultData;
        };

        ajax_support.isRoot = function(){
            return false;
        }

        return ajax_support;
    },

    isRoot : function(){
        return true;
    }

}
