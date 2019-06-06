/**
 * 
 */
$(function() {
	// 登录验证的controller url
	var changeUrl = "/o2o/local/changelocalpwd";
	// 从地址栏的URL里获取usertype
	// usertype= 1则为前端展示系统,其余为店家管理系统
	var usertype = getQueryString('usertype');
	// 登录次数,累积登录三次失败之后自动弹出验证码要求输入
	var loginCount = 0;

	$('#submit').click(function() {
		// 获取输入的账号
		var username = $('#username').val();
		// 获取输入的密码
		var password = $('#password').val();
		// 获取输入的密码
		var newPassword = $('#newPassword').val();
		// 获取输入的确认密码
		var confirmPassword = $('#confirmPassword').val();
		if (newPassword != confirmPassword) {
			$.toast('两次输入的新密码不一致!');
			return;
		}
		// 添加表单数据
		var formData = new FormData();
		formData.append('username', username);
		formData.append('password', password);
		formData.append('newPassword', newPassword);
		// 获取输入的验证码
		var verifyCodeActual = $('#j_kaptcha').val();
		// 进行验证码验证
		if (!verifyCodeActual) {
			$.toast('请输入验证码!');
			return;
		}
		formData.append('verifyCodeActual',verifyCodeActual);
		// 将参数post到后台去修改密码
		$.ajax({
			url : changeUrl,
			type : "post",
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast("修改成功!");
					if (usertype == 1) {
						// 若用户是在前端展示系统页面则自动回退到前端展示系统首页
						window.location.href = '/o2o/frontend/index';
					} else {
						// 若用户是在店家管理系统页面则自动回退到店家管理系统首页
						window.location.href = '/o2o/shopadmin/shoplist';
					}
				} else {
					$.toast("修改失败! " + data.errMsg);
					$('#kaptcha_img').click();
				}
			}
		});
	});
	
	$("#back").click(function(){
		window.location.href = '/o2o/shopadmin/shoplist';
	});
});
