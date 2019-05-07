/**
 * 因为商品的添加和编辑复用同一个页面，所以需要根据Url中的商品Id来判断
 */
$(function() {
	// 通过Url是否含有productId来判断是添加商品还是编辑
	var productId = getQueryString('productId');
	// 根据productId获取商品信息Url
	var infoUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;
	// 获取当前店铺设定的商品类别列表Url
	var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
	// 商品提交Url，通过标识符来判断是添加还是编辑操作
	var productPostUrl = '/o2o/shopadmin/modifyproduct';
	// 标识符 productId非空则为true即编辑，否则为添加商品
	var isEdit = false;

	// 通过标识符，确定调用的方法
	if (productId) {
		// 为true，则根据productId调用获取product信息的方法
		getProductInfo(productId);
		isEdit = true;
		// 编辑Url
		productPostUrl = '/o2o/shopadmin/modifyproduct';
	} else {
		// 为false，则初始化新增product页面
		getProductCategory();
		// 新增Url
		productPostUrl = '/o2o/shopadmin/addproduct';
	}

	 /**
	 * 编辑页面,根据id获取商品信息
	 */
   function getProductInfo(productId){
       $.getJSON(infoUrl,
	        function(data) {
	            if (data.success) {
	            	// 返回的json信息进行页面赋值
	                var product = data.product;
	                $('#product-name').val(product.productName);
	                $('#product-desc').val(product.productDesc);
	                $('#priority').val(product.priority);
	                $('#normal-price').val(product.normalPrice);
	                $('#promotion-price').val(product.promotionPrice);
	                // 设置商品类别列表及选中的商品类别
	                var optionHtml = '';
	                var optionArr = data.productCategoryList;
	                var optionSelected = product.productCategory.productCategoryId;
	                //生成前端的HTML商品类别列表，并默认选择编辑前的商品类别
	                optionArr.map(function(item, index) {
                           var isSelect = optionSelected === item.productCategoryId ? 'selected' : '';
                           optionHtml += '<option data-value="'
                                   + item.productCategoryId
                                   + '"'
                                   + isSelect
                                   + '>'
                                   + item.productCategoryName
                                   + '</option>';
	                        });
	                $('#product-category').html(optionHtml);
	            }
	        });
   };
	/**
	 * 初始化新增product页面
	 * 
	 * 根据页面原型和数据模型，需要加载该shop对应的productCategory信息(shop信息从服务端session中获取)
	 */
	function getProductCategory() {
		$.getJSON(categoryUrl, function(data) {
			if (data.success) {
				// 设置product_category
				var productCategoryList = data.data;
				var productCategoryTempHtml = '';
				productCategoryList.map(function(item, index) {
					productCategoryTempHtml += '<option data-value="'
							+ item.productCategoryId + '">'
							+ item.productCategoryName + '</option>';
				});
				$('#product-category').html(productCategoryTempHtml);
			} else {
				$.toast(data.errMsg);
			}
		});
	};

	/**
	 * 点击控件的最后一个且图片数量小于6个的时候，生成一个选择框
	 */
	$('.detail-img-div').on('change', '.detail-img:last-child', function() {
		if ($('.detail-img').length < 6) {
			$('#detail-img').append('<input type="file" class="detail-img">');
		}
	});

	/**
	 * 提交按钮的响应时间,分别对商品添加和商品编辑做不同的相应
	 */
	$('#submit').click(
			function() {
				// 创建商品Json对象，并从表单对象中获取对应的属性值
				var product = {};
				product.productName = $('#product-name').val();
				product.productDesc = $('#product-desc').val();
				product.priority = $('#priority').val();
				product.normalPrice = $('#normal-price').val();
				product.promotionPrice = $('#promotion-price').val();
				// 获取商品的特定目录值
				product.productCategory = {
					productCategoryId : $('#product-category').find('option').not(function() {
						return !this.selected;
					}).data('value')
				};
				product.productId = productId;
				// 缩略图 （只有一张），获取缩略图的文件流
				var thumbnail = $('#small-img')[0].files[0]; //productImg
				// 生成表单对象用于接收参数并传递给后台
				var formData = new FormData();
				formData.append('thumbnail', thumbnail)

				// 遍历商品详情图控件，获取里面的文件流
				$('.detail-img').map(
					function(index, item) {
						// 判断该控件是否已经选择了文件
						if ($('.detail-img')[index].files.length > 0) {
							// 将第i个文件流赋值给key为productImgi的表单键值对里
							formData.append('productImg' + index, 
									$('.detail-img')[index].files[0]
							);
						}
					});
				// 将product 转换为json ,添加到formData
				formData.append('productStr', JSON.stringify(product));

				// 获取表单中的验证码
				var verifyCodeActual = $('#j_kaptcha').val();
				if (!verifyCodeActual) {
					$.toast('请输入验证码！');
					return;
				}
				formData.append("verifyCodeActual", verifyCodeActual);

				// 使用ajax异步提交
				$.ajax({
					url : productPostUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if (data.success) {
							$.toast('提交成功！');
							$('#kaptcha_img').click();
						} else {
							$.toast('提交失败！');
							$('#kaptcha_img').click();
						}
					}
				});
			});
});