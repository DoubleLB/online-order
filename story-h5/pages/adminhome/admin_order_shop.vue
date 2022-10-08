<template>
	<view class="admin-shop">

		<view class="admin-shop-list" v-for="shop in shopList" :key="shop.shop_id">

			<view @click="toAOrder(shop)" class="vd1">
				<view style="font-weight: bold;font-size: 30rpx;display:flex;flex-direction: raw;margin: 20rpx 0 5rpx 0 ;">
					<view style="margin-right: 30rpx;font-size: 38rpx;font-weight: bold;">{{shop.shop_name}}</view>
					<view style="margin:10rpx 1rpx 0 auto; width: 200rpx;"><u-icon name="map" color="#2979ff" size="28"></u-icon>{{shop.shop_addr}}</view>
				</view>
				<u-line color="green" hair-line="true" margin="15rpx 0rpx 10rpx 0rpx" />

			</view>
		</view>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				shopList: []
			}
		},
		onLoad() {
			this.getShopList();
		},
		methods: {
			async getShopList() {
				this.shopList = await this.$u.get('/admin/shop'); //这是一个耗时的ajax请求,await表是一直阻塞，直到获取响应数据
			},
			
			//带参跳转
			toAOrder(shop){
				console.log(shop);
				
				//将对象转为json字符串
				const jsonStr = JSON.stringify(shop);
				console.log(jsonStr);
				
				//对字符串进行字符串编码
				const str = encodeURIComponent(jsonStr);
				console.log(str);
				
				this.$u.route({
					url:'pages/adminhome/admin_order',
					params:{
						shop:str
					}
				});
			}
		}

	}
</script>

<style>
	.admin-shop-list {
		margin: 10rpx 40rpx 20rpx 40rpx;
		display: flex;
		flex-direction: raw;

		border: 4px rgba(222, 222, 222, .3) solid;
		border-radius: 40rpx;
		box-shadow: 10px 10px 5px #d9d9d9;
	}
	.vd1{
		display:flex;
		flex-direction: column;
		justify-content:center;
		margin:10rpx 30rpx;
		flex: auto;
	}
</style>
