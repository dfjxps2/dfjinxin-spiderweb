
var modal_support_alter = modal_support.createNew();
var ajax_support = ajax_support.createNew();
var paging_data = paging_data.createNew();

var edit_modal_name = null;

$(document).ready(function(){
    
    
	paging_data.make_paging_data("/sys/menu/listMenuByPage.do");//向后台请求菜单列表数据；
});




function page_callback(dataList){  //后台返回菜单列表数据回调该方法；
    var $menu_table = $("#menu_table").find("tbody");
    $menu_table.empty();
    var columnNames = ["module_id","module_name","super_module_id"];
    var table_support = workbench_table.createNew();//创建一个表格生成器
    var operationArray = new Array();
    var view_operation = table_support.operationsByName("查看", ["module_id","module_name","super_module_id"], "viewModule(this)");

    operationArray.push(view_operation);


    table_support.makeTable("menu_table",columnNames,dataList,operationArray);//创建表格内容

}



function makeModuleInfoColumn(showType,module_id,module_name,super_module_id){
    var columnsArray = new Array();
    var moduleObj = null;
    if(showType!=null&&showType=='view'){
        var ModuleId_modal = modal_support.getModalEditObject("module_id","菜单ID","text",true,module_id,true);
        var ModuleName_modal = modal_support.getModalEditObject("module_name","菜单名称","text",true,module_name,true);
        var superModule_modal = modal_support.getModalEditObject("super_module_id","上级菜单","select",true,super_module_id,true,null);

        columnsArray.push(ModuleId_modal);
        columnsArray.push(ModuleName_modal);
        columnsArray.push(superModule_modal);
        return columnsArray;
        
     }   
}
        
function viewModule(viewModuleObj){
	var module_id = $(viewModuleObj).attr("module_id");
	var module_name = $(viewModuleObj).attr("module_name");
	var super_module_id = $(viewModuleObj).attr("super_module_id");
	console.log("module id value is :"+module_id);
    edit_modal_name = modal_support_alter.makeEditModalByColumn("查看",makeModuleInfoColumn("view",module_id,module_name,super_module_id));
    ajax_support.sendAjaxRequest("/sys/menu/getMenuById.do",{"module_id":$(viewModuleObj).attr("module_id")},"makeModuleInfoColumn");
}


