/**
 * 控制浏览器将当前页面跳转到其他页面
 */
var page_support = {

    createNew: function(){
        var page_support_obj = {};

        page_support_obj.forward_new_page = function(url){
            if(url!=null){
                window.location.href = projectName+url;
            }else{
                alert("跳转url未定义");
            }
        };

        /**
         * 控制浏览器打开新窗口
         */
        page_support_obj.open_new_page = function(url){

        };

        page_support_obj.alter_window = function(url){

        };

        page_support_obj.check_param_from_url = function(){
            //未来换成正则

            var hasParams = window.location.href.split("?");
            if(hasParams.length>0){
                var allParamStr = hasParams[1];
                if(allParamStr==null||allParamStr=='')
                    return null;
                var paramArray = allParamStr.split("&");

                var resultParam = new Object();

                $.each(paramArray,function(i,paramStr){
                    var param = paramStr.split("=");

                    var paramNm = param[0];
                    var paramVal = param[1];
                    resultParam[paramNm] = paramVal;
                });

                console.log("check_param_from_url resultParam "+JSON.stringify(resultParam));


                return resultParam;
            }
        };

        return page_support_obj;
    }

}