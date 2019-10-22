var pathName=window.document.location.pathname;
var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
// console.log(projectName);

//全局变量

//后台服务地址
var SERVICE_HOST = 'http://localhost:8080/spider';
// var SERVICE_HOST = 'http://portal-demo:8081/SpiderManage';

var use_jsonp = false;

document.write('<script type="text/javascript" src="'+projectName+'/utils/public-utils/jquery.1.11.3/jquery.min.js"></script>');
document.write('<script type="text/javascript" src="'+projectName+'/utils/public-utils/bootstrap.3.3.7/js/bootstrap.min.js"></script>');
document.write('<script type="text/javascript" src="'+projectName+'/utils/local-utils/page_data_support.js"></script>');
document.write('<script type="text/javascript" src="'+projectName+'/utils/local-utils/page_support.js"></script>');
document.write('<script type="text/javascript" src="'+projectName+'/utils/local-utils/modal_support.js"></script>');
