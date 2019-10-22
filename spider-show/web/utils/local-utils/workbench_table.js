/**
 * Created by SongCQ on 2017/7/19.
 */
document.write('<script type="text/javascript" src="'+projectName+'/utils/local-utils/paging_data.js"></script>');

var workbench_table = {
    createNew : function(){
        var table_support = {};

        table_support.makeTable = function(tableName,paramNames,dataList,operations){
            var $table = $("#"+tableName);
            var $user_table = $table.find("tbody");
            $user_table.empty();
            $.each(dataList,function(i,dataObj){
                var $tr = $("<tr></tr>");
                $.each(paramNames,function(pi,paramName){
                    var paramValue = null;
                    if(paramName.split(".").length>1){
                        var paramPart = paramName.split(".");
                        if(dataObj[paramPart[0]]!=null)
                            paramValue = dataObj[paramPart[0]][paramPart[1]];
                        else
                            paramValue = "未知";
                    }else{
                        paramValue = dataObj[paramName];
                    }
                   if(i%2==0){
                       $tr.attr("class","workbench_table_colorful");
                   }
                   $tr.append("<td>"+paramValue+"</td>");
               });
                if(operations!=null){
                    $opeTd = $("<td></td>");
                    $.each(operations,function(i,operationObj){
                        if(i>0){
                            $opeTd.append("<span style='margin-left:5px;'></span>");
                        }
                        if(operationObj.operationType=='function'){
                            $opeTd.append(createOperationByFunction(operationObj,dataObj));
                        }else if(operationObj.operationType=='html'){
                            $opeTd.append(operationObj.operationName);
                        }
                    });
                    // console.log("operation for table html code is "+$opeTd.html());
                    $tr.append($opeTd);

                }
                $user_table.append($tr);
            });

            function createOperationByFunction(operationObj,rowData){
                var $operation = $("<span class='workbench_table_operation' onclick='"+operationObj.clickFunction+"'>"+operationObj.operationName+"</span>");
                var operationParams = operationObj.operationParams;
                $.each(operationParams,function(i,paramObj){
                    $operation.attr(paramObj,rowData[paramObj]);
                });
                return $operation;
            }
        };


        table_support.operationsByHtmlCode = function(operationHtmlCode){
            var operation = {};
            operation.operationName = operationHtmlCode;
            operation.operationType = "html";
            return operation;
        };

        table_support.operationsByName = function(operationName,operationParams,clickFunction){
            var operation = {};
            operation.operationName = operationName;
            operation.operationParams = operationParams;
            operation.clickFunction = clickFunction;
            operation.operationType = "function";
            return operation;
        };

        return table_support;
    },

    tableSearchCreater:function() {
        var tableSearchCreater = {};

        tableSearchCreater.searchInfoObject = function(searchUrl,searchElement,paging,needAddBtn,addFunction,ajaxCallBack){
            var searchInfo = {};
            searchInfo.searchUrl = searchUrl;
            searchInfo.searchElement = searchElement;
            paging!=null&&!paging?searchInfo.paging=false:searchInfo.paging = true;
            needAddBtn!=null&&!needAddBtn?searchInfo.needAddBtn = false:searchInfo.needAddBtn = true;
            needAddBtn?searchInfo.addFunction = addFunction:searchInfo.addFunction = null;
            paging!=null&&!paging?searchInfo.ajaxCallBack = null:searchInfo.ajaxCallBack = ajaxCallBack;
            return searchInfo;
        };

        tableSearchCreater.searchElementObject = function(element_id,elementPlaceHolder,element_type,select_options){
            var searchElement = {};
            searchElement.element_id = element_id;
            searchElement.elementPlaceHolder = elementPlaceHolder;
            element_type!=null?searchElement.element_type = element_type:searchElement.element_type ="text";//text select
            element_type!=null&&element_type=='select'?searchElement.select_options = select_options:searchElement.select_options = null;
            return searchElement;
        };

        tableSearchCreater.selectOptionObject = function(optionValue,optionText){
            var selectOption = {};
            selectOption.optionValue = optionValue;
            selectOption.optionText = optionText;
            return selectOption;
        };

        tableSearchCreater.createSearch = function(searchInfoObject){
            var searchElements = searchInfoObject.searchElement;
            var searchUrl = searchInfoObject.searchUrl;
            var needAddBtn = searchInfoObject.needAddBtn;
            var addFunction = searchInfoObject.addFunction;
            var paging = searchInfoObject.paging;
            var ajaxCallBack = searchInfoObject.ajaxCallBack;

            var noNeedSearch = false;
            if(searchElements!=null&&searchElements.length>0&&searchUrl!=null){}
            else{
                noNeedSearch = true;
                if(!needAddBtn){
                    return;
                }
            }

            var $seach_area = $(".seach_area");
            $seach_area.append(
                    "<div id='seach_div' class=' base_float seach_div'>"+
                        "<div class=' base_float search_condition_area'>"+
                        "</div>"+
                        "<div class=' base_float_right search_btn_area'></div>"+
                    "</div>");

            if(needAddBtn){
                $seach_area.append("<div id='operation_div' class=' base_float_right operation_div'>"+
                    "<button id='table_add_btn' type='button' onclick='"+addFunction+"' class='add_button' >新增</button>"+
                    "</div>");
                // $("#table_add_btn").click(function(){
                //     addFunction();
                // });
            }

            if(noNeedSearch){
                return;
            }

            $(".search_btn_area").append("<button id='search_btn' type='button' class='search_button' >查询</button>");

            $.each(searchElements,function(i,elementObj){
                // <input type="text" class="search_input" id="user_id" placeholder="请输入用户ID" />
                var element_id = elementObj.element_id;
                var elementPlaceHolder = elementObj.elementPlaceHolder;
                var element_type = elementObj.element_type;
                var select_options = elementObj.select_options;

                if(element_type=='text')
                    $(".search_condition_area").append("<input type='text' class='search_input' id='"+element_id+"' placeholder='"+elementPlaceHolder+"' />");
                else if(element_type=='select'){
                    var $selectObj = $("<select class='search_input' id='"+element_id+"'>" +
                        // "<option>请选择</option>" +
                        "</select>");
                    $.each(select_options,function(s,optionObj){
                        var optionValue = optionObj.optionValue;
                        var optionText = optionObj.optionText;
                        $selectObj.append("<option value='"+optionValue+"'>"+optionText+"</option>");
                    });
                    $(".search_condition_area").append($selectObj);
                }
            });

            $("#search_btn").click(function(){
                var $seach_area = $(".seach_area");
                var allInputText = $seach_area.find("input[type='text']");
                var allInputSelect = $seach_area.find("select");

                var paramObj = new Object();

                if(allInputText!=null&&allInputText.length>0){
                    $.each(allInputText,function(q,inputText){
                        var elementId = $(inputText).attr("id");
                        var elementVal = $(inputText).val();
                        if(elementVal!=null&&elementVal!='')
                            paramObj[elementId] = elementVal;
                    });
                }

                if(allInputSelect!=null&&allInputSelect.length>0){
                    $.each(allInputSelect,function(q,selectText){
                        var elementId = $(selectText).attr("id");
                        var elementVal = $(selectText).val();
                        if(elementVal!=null&&elementVal!='')
                            paramObj[elementId] = elementVal;
                    });
                }

                // console.log("查询条件内容"+JSON.stringify(paramObj));
                if(paging){
                    paging_data.createNew().make_paging_data(searchUrl,paramObj);
                }else{
                    ajax_support.sendAjaxRequest(searchUrl,paramObj,ajaxCallBack);
                }
            });

            initTablePageArea();
        }

        return tableSearchCreater;
    },


    makeTableSearch:function(){
        var table_search_support = {};

        table_search_support.bodyHeight = $(document.body).height();
        table_search_support.$seach_area = $(".seach_area");
        table_search_support.$paging_area = $(".paging_area");
        table_search_support.$table_area = $(".table_area");

        table_search_support.initSearchArea = function(){
            var $seach_div = $(".seach_div");
            var $search_condition_area = $(".search_condition_area");

            console.log("search_condition_area val "+$search_condition_area.outerWidth()+"|"
                +$search_condition_area.innerWidth()+"|"+$search_condition_area.width());

            if($search_condition_area.height()>$seach_div.height()){

                var $search_condition_area_width = $search_condition_area.width();
                var search_width = $(".search_input").outerWidth();
                // console.log($search_condition_area_width/(search_width+30));
                var maxInputCount_EachRow = Math.floor($search_condition_area_width/(search_width+30));
                // console.log("maxInputCount_EachRow value is "+maxInputCount_EachRow);
                var allInput = $(".search_input");
                $.each(allInput,function(i,inputObj){
                    if((i+1)>maxInputCount_EachRow){
                        $(inputObj).addClass("search_input_hide");
                    }
                    if((i+1)==maxInputCount_EachRow){
                        $(inputObj).after("<span id='showAllSearchInput' onclick='showAllSearchInput()' " +
                            "class='glyphicon glyphicon-resize-full'><span style='margin-left:10px'>展开</span></span>");

                        $(inputObj).after("<span id='hideSearchInput' onclick='hideSearchInput()' " +
                            "class='glyphicon glyphicon-resize-full'><span style='margin-left:10px'>收起</span></span>");

                        $("#hideSearchInput").hide();
                    }

                });
            }
        };

        table_search_support.initTableArea = function(){
            var $seach_area_height = this.$seach_area.outerHeight();

            var $paging_area_height = this.$paging_area.outerHeight();

            this.$table_area.css("margin-top",$seach_area_height);

            console.log("$seach_area_height "+$seach_area_height+"--$paging_area_height "+$paging_area_height);
            this.$table_area.css("height",this.bodyHeight-$seach_area_height-$paging_area_height-2);
        };

        table_search_support.initPagingArea = function(){
            this.$paging_area.css("margin-top",this.$seach_area.outerHeight()+this.$table_area.outerHeight());
        }

        return table_search_support;
    }
}

/**
 * 初始化 搜索栏 列表 分页 的高度和宽度
 */
$(document).ready(function(){
    initTablePageArea();
    //

});

function initTablePageArea(){
    var table_search_support = workbench_table.makeTableSearch();

    table_search_support.initSearchArea();
    table_search_support.initTableArea();
    table_search_support.initPagingArea();
}

function showAllSearchInput(){
    var $seach_area = $(".seach_area");
    var $search_condition_area = $(".search_condition_area");
    $(".seach_area").css("z-index","10086");
    $(".search_input_hide").removeClass("search_input_hide");

    $seach_area.height($search_condition_area.height());
    $("#showAllSearchInput").hide();
    $("#hideSearchInput").show();
}

function hideSearchInput(){
    $("#showAllSearchInput").remove();
    $("#hideSearchInput").remove();
    var table_search_support = workbench_table.makeTableSearch();
    table_search_support.initSearchArea();
    $(".seach_area").removeAttr("style");

}