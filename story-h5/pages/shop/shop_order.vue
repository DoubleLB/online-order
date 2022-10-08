<template>
	<view class="order">

		<view v-if="this.listcom.length == 0" style="color: #8e8b8d;font-size: 50rpx;margin: 50% auto 0 28%;">
			没有用户下单
		</view>
		
		<view style="margin:20rpx 0rpx 50rpx;padding-bottom:10rpx;border-bottom:1px solid #f5f5f5;" 
			v-for="order in listcom"
			:key="order.order_submit" >		
			
			<view style="margin-bottom:26rpx;font-size:30rpx;font-weight: bold;color:#303133;display:flex;justify-content: space-between;align-items: center;">
				<text>{{order.u_nickname}}</text>
				<text style="font-size:28rpx;color:#999999;" v-if="order.order_status == 0">用户已删除该订单</text>
				<text style="font-size:28rpx;color:#3c78d8;" v-if="order.order_status == 1">已下单</text>
				<text style="font-size:28rpx;color:#fa3534;" v-if="order.order_status == 2">已取消</text>
				<text style="font-size:28rpx;color:#44e244;" v-if="order.order_status == 3">已完成</text>
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
			
			<u-modal @confirm="toFinish" v-model="confirmShow" :showCancelButton="true" :content="confirmContent"></u-modal>
			<text class="cancelOrder" v-if="order.order_status == 1" @click="open(order.order_id)">完成该订单</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				orderFin:'',//待处理ID号
				
				shop:{},
				orderList:[],
				
				//模态框信息
				confirmShow: false,//是否可见
				confirmContent: '是否确认',//内容
			};
		},
		
		computed: {
			listcom() {
				return this.orderList.filter(function(data) {
					//只返回status != 0的
					return data.order_status  == 1
				})
			}
		},
		
		onLoad(params) {
			
			//获取传递的json字符串
			this.shop = JSON.parse(decodeURIComponent(params.shop));
			console.log(this.shop);

			this.getOrderList();
		},
		
		methods: {
			
			async getOrderList(){
						this.orderList = await this.$u.get('admin/order/'+this.shop.shop_id);
			},
			
			toFinish(){
				console.log(this.orderFin)
				this.$u.post('/admin/order/change',{order_id:this.orderFin})
				this.$u.toast('处理订单成功')
				//跳转后刷新页面
				 this.$u.route('/pages/shop/shop')
			},
			
			//打开模态框
			open(pms){
				this.confirmShow = true;
				this.orderFin = pms;
			},
			
		}
	}
</script>

<style scope lang="scss">
	.order{
		padding:10rpx 20rpx;
	}
	.cancelOrder{
		font-size: 24rpx;
		font-weight: 100;
		color: #999999;
		text-decoration:underline;
		display: block;
		margin-left: 590rpx;
		margin-top:0rpx ;
		margin-bottom:0rpx ;
	}
</style>
