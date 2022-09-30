<template>
	<view class="order">
		<view style="margin:20rpx 0rpx 50rpx;padding-bottom:10rpx;border-bottom:1px solid #f5f5f5;" v-for="order in orderList" :key="order.order_submit">
			<view style="margin-bottom:26rpx;font-size:30rpx;font-weight: bold;color:#303133;display:flex;justify-content: space-between;align-items: center;">
				<text>{{order.shop_name}}</text>
				<text style="font-size:20rpx;color:#3c78d8;" v-if="order.order_status == 1">已提交</text>
				<text style="font-size:20rpx;color:#fa3534;" v-if="order.order_status == 2">已取消</text>
				<text style="font-size:20rpx;color:#44e244;" v-if="order.order_status == 3">已完成</text>
			</view>
			<view>
				<view v-for="meal in order.mealAndNumList" style="margin:20rpx 0rpx;height:120rpx;display: flex;flex-direction: row;align-items: center;">
					<view style="width:20%;height:100%;border-radius: 20rpx;margin-right:20rpx;">
						<u-image border-radius="20rpx" width="100%" height="100%" :src="$u.apiBaseUrl+'/public/img/'+meal.meal_pic"></u-image>
					</view>
					<view style="flex:auto;height:100%;padding:10rpx 0rpx;display: flex;flex-direction: column;justify-content: space-between;">
						<text style="font-weight: bold;color:#666666;">{{meal.meal_name}}</text>
						<text style="font-weight: bold;color:#666666;">￥{{meal.meal_price}}</text>
					</view>
					<view style="height:100%;padding:10rpx 0rpx;display: flex;align-items: flex-end;color:#c0c4cc;">
						<text>×</text>
						<text>{{meal.od_num}}</text>
					</view>
				</view>
			</view>
			<view style="display: flex;flex-direction: row;">
				<view style="font-size: 8rpx;color: #999999;">{{order.order_submit}}</view> 
				<view style="font-weight: bold;margin-left: auto;">
				总额:￥{{order.order_amount}}
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orderList: [],
			};
		},
		
		onLoad(){
			
		},
		
		//只要页面从隐藏 转换到 显示，即执行
		onShow() {			
				 this.getOrderList();
		},
		
		methods: {
			async getOrderList(){
				this.orderList = await this.$u.get("/user/order");
			}
		}
	}
</script>

<style scope lang="scss">
.order{
	padding:10rpx 20rpx;
}
</style>
