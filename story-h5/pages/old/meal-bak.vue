<template>
	<view class="meal">
		
		<view class="meal-list">
			
			<view class="meal-list-item" v-for="meal in mealList" :kye="meal.meal_id" >
				
				<!-- 左侧图片 -->
				<view style="width:45%;border-radius: 20rpx;margin-right:35rpx;">
					<u-image border-radius="20rpx" width="100%" height="200rpx" :src="meal.meal_pic"></u-image>
				</view>
				
				<!-- 右侧信息 -->
				<view style="display:flex;flex-direction: column;justify-content:center;">
					<text style="font-size:40rpx;font-weight: bold;margin-bottom: 10rpx;">{{meal.meal_name}}</text>
					<view style="font-size:18rpx;color:#888888;margin-bottom: 30rpx;">
						<text style="color:red;font-weight: bold;margin-right:20rpx;">推荐</text>
						<text style="margin-right:16rpx;">销售{{meal.meal_sale}}</text>
						<text>剩余{{meal.meal_store}}</text>
					</view>
					<text style="font-size:38rpx;font-weight: bold;color:red;">￥{{meal.meal_price}}</text>
				</view>
			</view>
			
		</view>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				//餐品清单(数组)
				mealList:[
					{
						meal_id:1,
						meal_pic:'/static/index/1.jpg',//餐品图片
						meal_name:'餐品 1',
						meal_sale:123,
						meal_store:321,
						meal_price:28						
					},
					{
						meal_id:2,
						meal_pic:'/static/index/2.jpg',//餐品图片
						meal_name:'餐品 2',
						meal_sale:123,
						meal_store:321,
						meal_price:28						
					},
					{
						meal_id:3,
						meal_pic:'/static/index/3.jpg',//餐品图片
						meal_name:'餐品 3',
						meal_sale:123,
						meal_store:321,
						meal_price:28						
					},
					{
						meal_id:4,
						meal_pic:'/static/index/4.jpg',//餐品图片
						meal_name:'餐品 4',
						meal_sale:123,
						meal_store:321,
						meal_price:28						
					},
				],
			}
		},
		methods: {
			
		}
	}
</script>

<style>
	.meal-list-item{
		margin: 60rpx 40rpx 60rpx 40rpx;
		
		display:flex; /* 本样式类修饰的元素为flex盒子 */
		flex-direction: row; /*本flex盒子内直接子元素按行排列*/
	}
</style>
