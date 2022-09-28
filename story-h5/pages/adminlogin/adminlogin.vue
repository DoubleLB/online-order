<template>
	<view class="login">
		<!-- <view style="width:95%;display: flex; flex-direction;justify-content:flex-end;margin-top:20rpx;">
			<view><u-button :plain="true" size="mini" style="width:200rpx;" :ripple="true"  type="error">新用户注册</u-button></view>
		</view> -->
		
		<view style="width:300rpx;margin-top:15%;margin-bottom:50rpx;">
			<u-image width="100%" height="300rpx" src="/static/login/login.svg"></u-image>
		</view>
		
		<view style="color:#777888;font-size:60rpx;font-weight: bold;letter-spacing: 8rpx;margin-top:0rpx;margin-bottom:50rpx;">
			<text>管理员登录</text>
		</view>
		
		<u-form :model="form" ref="uForm" style="width:90%;background-color: #FFFFFF;padding:0 20rpx;">
			<u-form-item prop="a_id">
				<u-input v-model="form.a_id"  placeholder="请输入账号" />
			</u-form-item>
			<u-form-item prop="a_pwd">
				<u-input v-model="form.a_pwd" type="password" placeholder="请输入密码"  />
			</u-form-item>
<!-- 			<u-form-item>
				<u-input  placeholder="请输入图形验证码"   />
				<u-image  width="180rpx" height="90rpx" mode="aspectFit" src=""></u-image>
			</u-form-item> -->
			
		</u-form>
		<u-button @click="login" style="width:40%;margin-top:70rpx;" :ripple="true" type="primary">登录</u-button>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form:{
					a_id:'',
					a_pwd:''
				},
				rules:{
					a_id:[{
						required: true,
						message: '请输入管理员账号',
						trigger: 'blur,change'
					}],
					a_pwd:[{
						required: true,
						message: '请输入管理员密码',
						trigger: 'blur,change'
					}]
				}
				
			}
		},
		onLoad(){
			
		},
		//必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		
		methods: {
			async login(){
				const token = await this.$u.post('/public/admin/login',this.form);
				 console.log("拿到的token----------->",token); //可以拿到
				//登陆成功
				uni.setStorageSync("Token",token);
				this.$u.toast('登陆成功！');
				this.$u.route({
					url:'pages/adminhome/adminhome',
					type:'redirect'
				})
			}
		},
		
	}
</script>

<style>
	.login{
		display: flex;
		flex-direction: column;
		align-items: center;
	}
</style>
