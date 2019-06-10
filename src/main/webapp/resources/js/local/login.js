/**
 * 
 */
$(function(){
	// 登录验证的controller url
	var loginUrl = "/o2o/local/logincheck";
	// 从地址栏的URL里获取usertype
	// usertype= 1则为前端展示系统,其余为店家管理系统
	var usertype = getQueryString('usertype');
	//登录次数,累积登录三次失败之后自动弹出验证码要求输入
	var loginCount = 0;
	
	$('#submit').click(function(){
		// 获取输入的账号
		var username = $('#username').val();
		// 获取输入的密码
		var password = $('#pwd').val();
		// 获取输入的验证码
		var verifyCodeActual = $('#j_kaptcha').val();
		//是否需要验证码验证,默认为false,即不需要验证
		var needVerify = false;
		
		if (loginCount >= 3){
			//进行验证码验证
			if(!verifyCodeActual){
				$.toast('请输入验证码!');
				return;
			} else {
				needVerify = true;
			}
		}
		// 请求登录
		$.ajax({
			url : loginUrl,
			async : false,
			cache : false,
			type : "post",
			dataType : "json",
			data : {
				username : username,
				password : password,
				verifyCodeActual : verifyCodeActual,
				//是否需要验证码验证
				needVerify : needVerify
			},
			success : function(data){
				if(data.success){
					$.toast("登录成功!");
					if (usertype == 1) {
						//若用户是在前端展示系统页面则自动回退到前端展示系统首页
						window.location.href = '/o2o/frontend/index';
					} else {
						//若用户是在店家管理系统页面则自动回退到店家管理系统首页
						window.location.href = '/o2o/shopadmin/shoplist';
					}
				} else {
					$.toast("登录失败! " + data.errMsg);
					loginCount++;
					if (loginCount >=3){
						//登录失败三次,需要验证码验证
						$("#verifyPart").show();
					}
					$('#kaptcha_img').click();
				}
			}
		});
	});
	
});
