<template>
	<view class="login">
		<view style="width:95%;display: flex; flex-direction;justify-content:flex-end;margin-top:20rpx;">
			<view><u-button :plain="true" size="mini" style="width:200rpx;" :ripple="true" @click="$u.route('/pages/register/register')" type="error">新用户注册</u-button></view>
		</view>
		<view style="width:300rpx;margin-top:15%;margin-bottom:50rpx;">
			<u-image width="100%" height="300rpx" src="/static/index/6.jpg"></u-image>
		</view>
		
		<!-- <view style="color:#999999;font-size:60rpx;font-weight: bold;letter-spacing: 8rpx;margin-top:40rpx;margin-bottom:50rpx;">
			<text>登录</text>
		</view> -->
		
		<u-form :model="form" ref="uForm" style="width:90%;background-color: #FFFFFF;padding:0 20rpx;">
			<u-form-item>
				<u-input v-model="form.u_id" placeholder="请输入账号" />
			</u-form-item>
			<u-form-item>
				<u-input v-model="form.u_pwd"  placeholder="请输入密码" type="password" />
			</u-form-item>
			<u-form-item>
				<u-input v-model="form.u_valid" placeholder="请输入图形验证码"   />
				<u-image @click="getSrc" width="180rpx" height="90rpx" mode="aspectFit" :src="src"></u-image>
			</u-form-item>
			
		</u-form>
		<u-button style="width:40%;margin-top:70rpx;" :ripple="true" @click="login" type="primary">登录</u-button>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				src:'',//验证码图片访问地址
				form: {
					u_id: '',
					u_pwd: '',
					u_valid:'',
					key:'' //由客户端生成的一个不重复的唯一的key
				},
			}
		},
		methods: {
			//获得验证码图片访问地址
			getSrc(){
				this.form.key = this.$u.guid(20);
				//服务端发到客户端的是一个图片	，服务端存储的是图片上显示的验证码，
				//客户端生成唯一key，在客户端临时存储，并在请求验证码图片时向服务端发送，服务端会将收到的key为键，而验证码为值存入一个Map集合中
				//客户端登录时，同时发送key，服务通过key从map集合取出验证码，进行验证
				this.src = this.$u.apiBaseUrl+'/public/login/verify-code?key='+this.form.key;
			},
			async login(){
				const token = await this.$u.post('/public/user/login',this.form);
				uni.setStorageSync("Token",token);
				this.$u.toast('登录成功！');
				this.$u.route({url:'/pages/index/index',type:'tab'})
			}
		},
		onLoad(){
			this.getSrc();
		}
	}
</script>

<style>
	.login{
		display: flex;
		flex-direction: column;
		align-items: center;
	}
</style>
