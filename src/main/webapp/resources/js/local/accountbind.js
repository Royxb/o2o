/**
 * 
 */
$(function(){
	//绑定账号的controller url
	var bindUrl = "/o2o/local/bindlocalauth";
	//从地址栏的URL里获取userType
	//usertype= 1则为前端展示系统,其余为店家管理系统
	var usertype = getQueryString('usertype');
});