/**
 * 
 */
$(function() {
	// 无限滚动
	$(document).on(
			"pageInit",
			"#page-infinite-scroll-bottom",
			function(e, id, page) {
				var loading = false;
				// 每次加载添加多少条目
				var itemsPerLoad = 20;
				// 最多可加载的条目
				var maxItems = 100;
				var lastIndex = $('.list-container li').length;
				function addItems(number, lastIndex) {
					// 生成新条目的HTML
					var html = '';
					$.getJSON('/o2o/frontend/listproductsbyshop?shopId=1&pageIndex=1&pageSize=10', function(data) {
						if (data.success) {
							// 获取当前查询条件下商品的总数
							maxItems = data.count;
							// for (var i = lastIndex + 1; i <= lastIndex +
							// number; i++) {
							// html += '<li class="item-content"><div
							// class="item-inner"><div
							// class="item-title">新条目</div></div></li>';
							// }
							data.productList.map(function(item, index) {
								html += ''
										+ '<div class="card" data-product-id='
										+ item.productId
										+ '>'
										+ '<div class="card-header">'
										+ item.productName
										+ '</div>'
										+ '<div class="card-content">'
										+ '<div class="list-block media-list">'
										+ '<ul>'
										+ '<li class="item-content">'
										+ '<div class="item-media">'
										+ '<img src="'
										+ item.imgAddr
										+ '" width="44">'
										+ '</div>'
										+ '<div class="item-inner">'
										+ '<div class="item-subtitle">'
										+ item.productDesc
										+ '</div>'
										+ '</div>'
										+ '</li>'
										+ '</ul>'
										+ '</div>'
										+ '</div>'
										+ '<div class="card-footer">'
										+ '<p class="color-gray">'
										+ new Date(item.lastEditTime)
												.Format("yyyy-MM-dd")
										+ '更新</p>'
										+ '<span>点击查看</span>'
										+ '</div>' + '</div>';
							});
						}
					});
					// 添加新条目
					$('.infinite-scroll .list-container').append(html);
				}
				$(page).on('infinite', function() {
					// 如果正在加载，则退出
					if (loading)
						return;
					// 设置flag
					loading = true;
					// 模拟1s的加载过程
					setTimeout(function() {
						// 重置加载flag
						loading = false;
						if (lastIndex >= maxItems) {
							// 加载完毕，则注销无限加载事件，以防不必要的加载
							$.detachInfiniteScroll($('.infinite-scroll'));
							// 删除加载提示符
							$('.infinite-scroll-preloader').remove();
							return;
						}
						addItems(itemsPerLoad, lastIndex);
						// 更新最后加载的序号
						lastIndex = $('.list-container li').length;
						$.refreshScroller();
					}, 1000);
				});
			});

	$.init();
});
