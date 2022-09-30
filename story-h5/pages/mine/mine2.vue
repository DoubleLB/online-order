<template>
	<view class="center">
		<view class="logo" @click="goLogin" :hover-class="!login ? 'logo-hover' : ''">
			<image class="logo-img" :src="login ? uerInfo.avatarUrl :avatarUrl"></image>
			<view class="logo-title">
				<text class="uer-name">Hi，{{login ? uerInfo.name : '您未登录'}}</text>
				<text class="go-login navigat-arrow" v-if="!login">&#xe65e;</text>
			</view>
		</view>
		<view class="center-list">
			<view class="center-list-item border-bottom">
				<text class="list-icon">&#xe60f;</text>
				<text class="list-text">帐号管理</text>
				<text class="navigat-arrow">&#xe65e;</text>
			</view>
			<view class="center-list-item">
				<text class="list-icon">&#xe639;</text>
				<text class="list-text">新消息通知</text>
				<text class="navigat-arrow">&#xe65e;</text>
			</view>
		</view>
		<view class="center-list">
			<view class="center-list-item border-bottom">
				<text class="list-icon">&#xe60b;</text>
				<text class="list-text">帮助与反馈</text>
				<text class="navigat-arrow">&#xe65e;</text>
			</view>
			<view class="center-list-item">
				<text class="list-icon">&#xe65f;</text>
				<text class="list-text">服务条款及隐私</text>
				<text class="navigat-arrow">&#xe65e;</text>
			</view>
		</view>
		<view class="center-list">
			<view class="center-list-item">
				<text class="list-icon">&#xe614;</text>
				<text class="list-text">关于应用</text>
				<text class="navigat-arrow">&#xe65e;</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				login: false,
				avatarUrl: "../../static/uni-center/logo.png",
				uerInfo: {}
			}
		},
		methods: {
			goLogin() {
				if (!this.login) {
					console.log("点击前往登录")
				}
			}
		}
	}
</script>

<style>
	@font-face {
		font-family: texticons;
		font-weight: normal;
		font-style: normal;
		src: url('https://at.alicdn.com/t/font_984210_5cs13ndgqsn.ttf') format('truetype');
	}

	page,
	view {
		display: flex;
	}

	page {
		background-color: #f8f8f8;
	}

	.center {
		flex-direction: column;
	}

	.logo {
		width: 750upx;
		height: 240upx;
		padding: 20upx;
		box-sizing: border-box;
		background-color: #4cd964;
		flex-direction: row;
		align-items: center;
	}

	.logo-hover {
		opacity: 0.8;
	}

	.logo-img {
		width: 150upx;
		height: 150upx;
		border-radius: 150upx;
	}

	.logo-title {
		height: 150upx;
		flex: 1;
		align-items: center;
		justify-content: space-between;
		flex-direction: row;
		margin-left: 20upx;
	}

	.uer-name {
		height: 60upx;
		line-height: 60upx;
		font-size: 38upx;
		color: #FFFFFF;
	}

	.go-login.navigat-arrow {
		font-size: 38upx;
		color: #FFFFFF;
	}

	.login-title {
		height: 150upx;
		align-items: self-start;
		justify-content: center;
		flex-direction: column;
		margin-left: 20upx;
	}

	.center-list {
		background-color: #FFFFFF;
		margin-top: 20upx;
		width: 750upx;
		flex-direction: column;
	}

	.center-list-item {
		height: 90upx;
		width: 750upx;
		box-sizing: border-box;
		flex-direction: row;
		padding: 0upx 20upx;
	}

	.border-bottom {
		border-bottom-width: 1upx;
		border-color: #c8c7cc;
		border-bottom-style: solid;
	}

	.list-icon {
		width: 40upx;
		height: 90upx;
		line-height: 90upx;
		font-size: 34upx;
		color: #4cd964;
		text-align: center;
		font-family: texticons;
		margin-right: 20upx;
	}

	.list-text {
		height: 90upx;
		line-height: 90upx;
		font-size: 34upx;
		color: #555;
		flex: 1;
		text-align: left;
	}

	.navigat-arrow {
		height: 90upx;
		width: 40upx;
		line-height: 90upx;
		font-size: 34upx;
		color: #555;
		text-align: right;
		font-family: texticons;
	}
</style>


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
