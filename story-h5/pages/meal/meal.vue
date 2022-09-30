<template>
	<view class="meal">
		
		<u-sticky style="padding:20rpx 40rpx;">
			<u-button type="error" shape="circle" :ripple="true" @click="toAdd" >新增</u-button>
		</u-sticky>
		
		<view class="meal-list">
			
			<view class="meal-list-item" v-for="meal in mealList" :kye="meal.meal_id" >
				
				<!-- 左侧图片 -->
				<view style="width:45%;border-radius: 20rpx;margin-right:35rpx;">
					<u-image border-radius="20rpx" width="100%" height="200rpx" :src="$u.apiBaseUrl+'/public/img/'+meal.meal_pic+'?t_='+currentTime()"></u-image>
				</view>
				
				<!-- 中间信息 flex:auto表示本块自缩放适应空间大小-->
				<view style="display:flex;flex-direction: column;justify-content:center;flex: auto;">
					<text style="font-size:40rpx;font-weight: bold;margin-bottom: 10rpx;">{{meal.meal_name}}</text>
					<view style="font-size:18rpx;color:#888888;margin-bottom: 30rpx;">
						<text style="font-size: 30rpx; color: #ff9900;margin-right: 10rpx;">{{meal.cls_name}}</text>
						<text>{{meal.meal_desc}}</text>
					</view>
					<text style="font-size:38rpx;font-weight: bold;color:red;">￥{{meal.meal_price}}</text>
				</view>
				
				<!-- 右侧图标 -->
				<view style="font-size: 50rpx;color: #BBBBBB;" @click="toEdit(meal)"><u-icon name="edit-pen"></u-icon></view>
	
			</view>
			
		</view>
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				//餐品清单(数组)
				mealList:[],
			}
		},
		onLoad(){//onLoad是uniapp的页面生命周期回调方法，表示页面页面刚刚加载
			this.getMealList();
		},
		methods: {
			currentTime(){
				return new Date().getTime();
			},
			
			//定义一个异步方法，访问服务器，获取餐品清单数据
			async getMealList(){
				this.mealList = await this.$u.get('/admin/meal');//这是一个耗时的ajax请求,await表是一直阻塞，直到获取响应数据
			},
			
			//跳转到增加页
			toAdd(){
				console.log('跳转至新增........');
				this.$u.route('/pages/meal/meal-add');//路由跳转
			},
			
			//跳转到编辑页
			toEdit(meal){
				console.log(meal);
				
				//将对象转为json字符串
				const jsonStr = JSON.stringify(meal);
				console.log("json字符串",jsonStr);
				
				//对字符串进行字符串编码
				const str = encodeURIComponent(jsonStr);
				console.log("字符串编码",str);
				
				this.$u.route({
					url:'pages/meal/meal-edit',
					params:{
						meal:str
					}
				});
			}
		}
	}
</script>

<style>
	.meal-list-item{
		margin: 60rpx 40rpx 60rpx 40rpx;
		
		display:flex; /* 本样式类修饰的元素为flex盒子 */
		flex-direction: row; /*本flex盒子内直接子元素按行排列，水平方向为主轴（元素按主轴方向排列）*/
		align-items: center;/*交叉轴对齐方式为居中*/
	}
</style>
