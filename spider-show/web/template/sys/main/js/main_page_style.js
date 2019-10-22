/**
 * Created by SongCQ on 2017/7/7.
 */
var has_show_menu_page = false;

$(document).ready(function(){
    init_main_page_style();
});

function init_main_page_style(){
    menu_div_style();
    title_div_style();
    main_div_style();
    $(".son_menu_area").hide();
}

/**
 * 动态计算菜单栏div总体高度,页面高度-logo高度-退出按钮高度
 * 动态计算菜单栏div总体高度,页面高度-logo高度 modify by SongChaoqun 退出按钮放到菜单一起 去掉60px退出区域
 */
function menu_div_style(){
    var page_height4main_page = $(document.body).height();
    var logoHeight = 85;//logo高度
    // var signOutHeight = 60;//退出按钮高度
    var menu_margintop = 36;//菜单上边距高度
    var menu_area_div_height = page_height4main_page-logoHeight-menu_margintop;
    $(".menu_area_div").height(menu_area_div_height);//85:logo高度 70退出按钮高度
}

function title_div_style(){
    //title宽度为整体宽度减去菜单宽度 高度与logo高度相同85
    var page_width4main_page = $(document.body).width();
    var menu_div_width = $("#menu_div").width();
    // console.log(menu_div_width);

    var title_div_width = page_width4main_page-menu_div_width;
    $("#title_div").width(title_div_width);
    // console.log("page_width4main_page value is "+page_width4main_page+"||menu_div_width value is "+menu_div_width);
    // $("#title_div").height(85);
    $("#title_div").height(0);
    $("#title_space_div").width(title_div_width-200);
    $("#title_space_div").height('100%');
    $("#title_user_div").width(198);
    $("#title_user_div").height('100%');
    // $("#title_div").hide();

}

function main_div_style(){
    //宽度为整体宽度减去菜单宽度，高度为整体高度减去title高度
    var page_width= $(document.body).width();
    var page_height = $(document.body).height();

    var title_div_height = $("#title_div").height();
    var menu_div_width = $("#menu_area_div").width();
    $("#main_div").width(page_width-menu_div_width);
    $("#main_div").height(page_height - title_div_height);
}

/**
 * 根据一级菜单个数动态计算每个一级菜单占用多少高度
 * @param first_menu_length
 */
function sum_split_li_height(first_menu_length){
    var signOutHeight = 60;//退出按钮高度
    var menu_area_div = $("#menu_area_div").height();
    var real_menu_height = menu_area_div - signOutHeight;
    var each_root_menu_area = real_menu_height/first_menu_length;
    var root_menu_show = each_root_menu_area*0.4;
    var root_menu_split = each_root_menu_area*0.6;
    var minHeightChecked = false;
    if(root_menu_show>30){
        root_menu_show = 30;
        minHeightChecked = true;
    }
    if(root_menu_split>78){
        root_menu_split = 78;
        minHeightChecked = true;
    }
    if(minHeightChecked){
        real_menu_height = first_menu_length*(root_menu_show+root_menu_split);
        if(menu_area_div>real_menu_height){
            var sign_out_margintop = menu_area_div - real_menu_height -signOutHeight;
            $(".sign_out_li").css("margin-top",sign_out_margintop);
        }
    }

    // console.log("menu_area_div "+menu_area_div+"--real_menu_height"+real_menu_height+"--root_menu_show"+root_menu_show+"--"+root_menu_split);
    $(".menu_img_li").height(root_menu_show);
    $(".menu_img").height(root_menu_show);
    $(".root_menu_font").height(root_menu_show);
    $(".root_menu_font").css("line-height",root_menu_show+"px");
    $(".split_label").height(root_menu_split);


}

function build_son_menu(parent_menu_id,son_menu_length){
    // console.log("开始初始化"+parent_menu_id+"下的子菜单,子菜单个数"+son_menu_length);
    if(son_menu_length!=null&&son_menu_length>0){
        var root_menu_height = $("#"+parent_menu_id).height();
        var sonMenuMargin = (root_menu_height-16)/2;
        $(".son_menu").height(root_menu_height);
        $(".son_menu").css("margin-top",sonMenuMargin);
        $(".son_menu").css("margin-bottom",sonMenuMargin);
        // console.log("子菜单参数:"+root_menu_height+"--"+sonMenuMargin)
        $("#"+parent_menu_id+"_sons").height((2*sonMenuMargin+root_menu_height)*son_menu_length);
    }
}

function menu_show(){
    $("#menu_area_div").click(function(){
        console.log("menu_show function is running....");
        var isCollapse = $(".menu_div_collapse").length>0?true:false;
        if(isCollapse&&!has_show_menu_page){//菜单折叠状态
            $("#menu_div").animate({width:'210px'},"slow",function(){
                $("#menu_div").removeClass("menu_div_collapse");
                $("#menu_div").addClass("menu_div_expansion");
                $(".root_menu_font").show();
                $(".sign_out_font").show();
                $(".menu_img").addClass("menu_img_expansion");
                $(".menu_img_li").addClass("expansion_mounse_over");
                $(".menu_img_li").attr("onclick","menu_click(this)");
                $(".sign_out_img").addClass("menu_img_expansion");
                init_main_page_style();

            });
        }
        has_show_menu_page = false;
    });
    $("div[id='logo_div'],div[id='sign_out_div'],div[id='main_div'],div[id='title_div'],iframe[id='main_frame']").click(function(){
        var isCollapse = $(".menu_div_collapse").length>0?true:false;
        if(!isCollapse){
            collapseMenu();
        }
    });

}

function collapseMenu(menu_url){
    console.log("collapseMenu  is running...");
    $("#menu_div").removeClass("menu_div_expansion");
    $("#menu_div").addClass("menu_div_collapse");
    $(".root_menu_font").hide();
    $(".sign_out_font").hide();
    $(".menu_img").removeClass("menu_img_expansion");
    $(".menu_img_li").removeClass("expansion_mounse_over");
    $(".menu_img_li").removeAttr("onclick");
    $(".sign_out_img").removeClass("menu_img_expansion");

    $("#menu_div").animate({width:'100px'},"slow",function(){
        $(".son_menu_area").hide();
        $(".menu_img_li").show(1000);
        $(".split_label").show(1000);
        $(".sign_out_li").show();
        var $show_sons = $(".son_menu_area[show_sons='Y']");
        if($show_sons.length>0){
            $show_sons.attr("show_sons","N");
        }
        init_main_page_style();
        if(menu_url!=null&&menu_url!='')
            $(".main_frame").attr("src",projectName+menu_url);
    });
}

/**
 * 一级菜单点击后方法 隐藏其他一级菜单 显示被点击菜单的下级菜单
 * 如已展开显示2级菜单则显示其他一级菜单，隐藏2级菜单
 * @param clickObj
 */
function menu_click(clickObj){
    console.log("menu_click is running....");
    // $(".menu_img_li").unbind("click").click(function(){
        var menu_id = $(clickObj).attr("id");
        var show_sons = $(clickObj).attr("show_sons");
        var sons_length = $("#"+menu_id+"_sons").length;
        if(sons_length<1){
            return;
        }
        // console.log("show sons value "+show_sons+" menu_id"+menu_id);
        if(show_sons!=null&&show_sons=='Y'){
            $("#"+menu_id+"_sons").hide(1000);
            $(".menu_img_li").show(1000);
            $(".split_label").show(1000);
            $(".sign_out_li").show();

            $(clickObj).attr("show_sons","N");
        }else{
            $("#"+menu_id+"_sons").show(1000);

            $(".split_label").hide(1000);
            $(".menu_img_li[id!='"+menu_id+"'][id!='sign_out_area']").hide();
            $(".sign_out_li").hide();
            $(clickObj).attr("show_sons","Y");
        }
    // });
}

/**
 * 后台定义,菜单ID：
 * 1:采集管理 2:采集监控 3:离线页面 4:资源管理 5:用户管理
 * @param menu_data
 */
function make_menu(menu_data){
    var resultData = menu_data["resultData"];
    if(resultData.length>0){
        var root_menu_length = 0;
        var son_menu_map = new Object();
        var root_menu_aliase = new Array();
        $.each(resultData,function(i,menu_data){
            var module_id = menu_data["module_id"];
            var super_module_id = menu_data["super_module_id"];
            var module_name =menu_data["module_name"];
            var module_url= menu_data['module_url'];

            if(super_module_id=='0'){//一级菜单
                var li_obj = $("<li></li>");
                var menu_aliase = get_menu_aliase(module_id);
                li_obj.attr("id",menu_aliase);
                li_obj.attr("menu_id",module_id);
                li_obj.attr("class","menu_img_li");

                var root_menu_img_label = $("<label></label>");
                root_menu_img_label.attr("root_menu_type",menu_aliase);
                root_menu_img_label.attr("class","base_float menu_img "+menu_aliase+"_img");

                var root_menu_name_label = $("<label>"+module_name+"</label>");
                root_menu_name_label.addClass("base_float root_menu_font");
                li_obj.append(root_menu_img_label);
                li_obj.append(root_menu_name_label);

                $("#sign_out_area").before(li_obj);
                $("#sign_out_area").before("<li id='"+menu_aliase+"_split' class='split_label'></li>");
                root_menu_length++;
                root_menu_aliase.push(menu_aliase);

            }else{//二级菜单
                var $super_menu_obj = $(".menu_img_li[menu_id='"+super_module_id+"']");
                if($super_menu_obj.length<1){//当前2级菜单所属的1级菜单还没初始化

                }else{
                    var super_menu_aliase = get_menu_aliase(super_module_id);
                    // console.log("2级菜单...."+super_menu_aliase);
                    var $son_menu_li = $("#"+super_menu_aliase+"_sons");
                    var son_menu_length = $son_menu_li.length;
                    // console.log("son_menu_length...."+son_menu_length);
                    if(son_menu_length<1){
                        $son_menu_li = $("<li id='"+super_menu_aliase+"_sons' class='son_menu_area'></li>");
                        $son_menu_li.append(" <label class=' base_float son_menu' menu_url='"+module_url+"'>"+module_name+"</label>");
                        $("#"+super_menu_aliase+"_split").before($son_menu_li);
                    }else{
                        $son_menu_li.append(" <label class=' base_float son_menu' menu_url='"+module_url+"'>"+module_name+"</label>");
                    }

                    if(son_menu_map[super_menu_aliase]!=null){
                        son_menu_map[super_menu_aliase] = son_menu_map[super_menu_aliase]+1;
                    }else{
                        son_menu_map[super_menu_aliase] = 1;
                    }
                }
            }

            if(i==(resultData.length-1)){
                $(".root_menu_font").hide();
                $(".sign_out_font").hide();
                sum_split_li_height(root_menu_length);

                $.each(root_menu_aliase,function(i,aliase_nm){
                    var son_menu_count = son_menu_map[aliase_nm];
                    build_son_menu(aliase_nm,son_menu_count);
                });

                init_main_page_style();

                $(".son_menu").click(function(){
                    var menu_url = $(this).attr("menu_url");
                    show_menu_page(menu_url);
                })
            }
        });

        // console.log($("#menu_list").html());
    }

}

function show_menu_page(menu_url){
    console.log("show_menu_page  "+projectName+menu_url);
    collapseMenu(menu_url);
    has_show_menu_page = true;
}


function get_menu_aliase(menu_id){
    if(menu_id==1){
        return "gather";
    }else if(menu_id==2){
        return "gather_report";
    }else if(menu_id==3){
        return "offline_page";
    }else if(menu_id==4){
        return "resource";
    }else if(menu_id==5){
        return "user_manage";
    }else if(menu_id==6){
        return "auth";
    }
}

