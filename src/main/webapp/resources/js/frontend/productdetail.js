/**
 * 
 */
$(function() {
	addItem();
	function addItem() {
		var url = '/o2o/frontend/listproductsbyshop?shopId=1&pageIndex=1&pageSize=10';
		var initUrl= "/o2o/shopadmin/getshopinitinfo";
		$.getJSON(initUrl, function(data) {
			// 数据存在
			if (data.success) {
				var tempHtml = "";
				var tempAreaHtml = "";
				// 迭代店铺分类列表
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				// 迭代区域信息
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#list-block').html(tempHtml);
				$('#list-block').html(tempAreaHtml);
			}
		});
		$.getJSON(url, function(data) {
			if (data.success) {
				var productHtml = '';
				// 遍历头条列表，并拼接出轮播图组
				data.productList.map(function(item, index) {
					productHtml += '' + '<div class="card" data-product-id='
							+ item.productId + '>'
							+ '<div class="card-header">' + item.productName
							+ '</div>' + '<div class="card-content">'
							+ '<div class="list-block media-list">' + '<ul>'
							+ '<li class="item-content">'
							+ '<div class="item-media">' + '<img src="'
							+ item.imgAddr + '" width="44">' + '</div>'
							+ '<div class="item-inner">'
							+ '<div class="item-subtitle">' + item.productDesc
							+ '</div>' + '</div>' + '</li>' + '</ul>'
							+ '</div>' + '</div>' + '<div class="card-footer">'
							+ '<p class="color-gray">'
							+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
							+ '更新</p>' + '<span>点击查看</span>' + '</div>'
							+ '</div>';
				});
				// 将拼接好的类别赋值给前端HTML控件进行展示
				$('#list-block').append(productHtml);
			}
		});
	}
});
