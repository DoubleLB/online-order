<template>
	<view class="center">
		<view class="logo" @click="goLogin" :hover-class="!login ? 'logo-hover' : ''">
			<image class="logo-img" :src="login ? (this.userInfo.u_avatar == null ? avatarUrl : imgPath()) :avatarUrl"></image>
			<view class="logo-title">
				<text class="uer-name">Hi，{{login ? (this.userInfo.u_nickname == null ? '[用户]' : this.userInfo.u_nickname) : '您未登录'}}</text>
				<text class="go-login navigat-arrow" v-if="!login">&#xe65e;</text>
				<!-- <u-icon name="arrow-right" color=" #8c8c8c" size="38"></u-icon> -->
			</view>
		</view>
		<view class="center-list">
			<view class="center-list-item border-bottom" @click="toMineInfo(userInfo)">
				<text class="list-icon">&#xe60f;</text>
				<text class="list-text">帐号管理</text>
				<text class="navigat-arrow">&#xe65e;</text>
			</view>
			<view class="center-list-item" @click="toHistoryOrder">
				<text class="list-icon">&#xe639;</text>
				<text class="list-text">历史订单</text>
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
				userInfo: {
					
				}
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
		onShow(){
			this.getUserInfoList();
			this.isUser();		
		},
		
		methods: {
			
			//获取用户信息
			async getUserInfoList(){
				this.userInfo = await this.$u.get('/user/user');//这是一个耗时的ajax请求,await表是一直阻塞，直到获取响应数据
			},
			
			//跳转历史订单
			toHistoryOrder(){
				this.$u.route('/pages/order/history-order');
			},
			
			//判断是否登录
			async isUser(){
				const obj = await this.$u.get("/public/is-user");
				this.login = obj.isUser;	
						console.log("this.login",this.login);
			},
			
			imgPath() {
				return this.$u.apiBaseUrl + '/public/img-user/' + this.userInfo.u_avatar + '?t_=' + new Date().getTime();
			},
			
			//跳转登录
			goLogin() {
				if (!this.login) {
					this.$u.route('/pages/login/login')
					console.log("点击前往登录")
				}
			},
			
			//转至个人信息页面
			toMineInfo(user){

				if(this.login){
					console.log("userInfo为  ",user);
					
					//将对象转为json字符串
					const jsonStr = JSON.stringify(user);
					console.log("json字符串  ",jsonStr);
					
					//对字符串进行字符串编码
					const str = encodeURIComponent(jsonStr);
					console.log("字符串编码  ",str);
					
					this.$u.route({
						url:'pages/mine/mineinfo',
						params:{
							user:str
						}
					});
				}else{
					this.$u.toast("您未登录，请登录后查看");
					this.$u.route("/pages/login/login");
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
		height: 340upx;
		padding: 20upx;
		box-sizing: border-box;
		background-color: #96d977;
		flex-direction: row;
		align-items: center;
	}

	.logo-hover {
		opacity: 0.8;
	}

	.logo-img {
		width: 200upx;
		height: 200upx;
		border-radius: 50%;
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
		height: 60rpx;
		line-height: 60rpx;
		font-size: 58rpx;
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