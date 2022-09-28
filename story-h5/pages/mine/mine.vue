<template>
	<view>
		<view class="content-item">
			<image src="/static/index/bg4.jpg" style="width: 100%;border-radius: 20rpx;"></image>
			<view class="user-info">
				<view>
					<image src="/static/index/5.jpg" style="height: 80px;width: 80px;border-radius: 50%;"></image>
				</view>
				<view class="name">
					<text style="font-weight: bold;font-size: 40rpx;margin:30rpx 0rpx 0rpx 53rpx;">Hello ! User</text>
				</view>
				
			</view>
			<text class="t1" @click="toQuite">退出</text>
		</view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		//点击导航栏右侧的回调函数
		async onNavigationBarButtonTap(options){
			console.log(options);
			
			//访问服务端，判断当前用户是否为已登录的管理员
			const obj = await this.$u.get('/public/is-admin');		
			console.log("测试isAdmin：",obj.isAdmin);
			const isAdmin = obj.isAdmin;
			// console.log("测试isAdmin.data：",isAdmin.data);
			//是，则跳转到管理员主界面，否则跳转至管理员登录页
			if(isAdmin){
				this.$u.route('/pages/adminhome/adminhome');
			}else{
				this.$u.route('/pages/adminlogin/adminlogin');
			}		
		},
		methods: {
			toQuite(){
				
				uni.removeStorageSync("Token"); //清除本地缓存中的令牌
				this.$u.toast("退出成功");
				this.$u.route({
					url: '/pages/login/login',
					type: 'reLaunch' //所有页面都出路由栈，新页面进栈
				});
			}
		}
	}
</script>

<style scoped lang="scss">
	.container {
		width: 100%;
	}
	
	.profile {
		width: 100%;
	}
	
	.user-info {
		position: absolute;
		top: 100px;
		left: 30px;
		display: flex;
		align-items: center;
	
	}
	
	.user-info .name {
		display: flex;
	}
	.t1{
		position: absolute;
		top: 10rpx;
		right: 25rpx;
		color: #919293;
	}
	
	.content-item {
		display: flex;
		margin: 10px;
		flex-direction: column;
		border-radius: 4px;
		background-color: #FFFFFF;
		justify-content: space-around;
	}
	
	.content-item .title {
		padding: 15px;
		color: #5A5B5C;
		font-size: 16px;
		font-weight: bold;
	}
	
	.content-show {
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		align-items: center;
	}
	
	.money-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 20px;
	}
	
	.service-item {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 20px;
	}
	
	.service-item .service-text {
		font-size: 12px;
		color: #919293;
	}
	
	.order-tab {
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 15px;
	}
	
	.order-title {
		font-size: 14px;
		padding-top: 10px;
		color: #919293;
		padding-bottom: 8px;
	}
</style>
