<template>
	<view class="register">
		<view style="margin:50rpx 0rpx 80rpx 0rpx;display: flex;justify-content: center;width:100%;">
			<u-image width="30%" height="200rpx" src="/static/register/welcome.svg"></u-image>
			<view style="font-size:50rpx;font-weight: bold;margin-left:20rpx;">
				<view><text>Hi，</text></view>
				<view><text>很高兴遇见你~</text></view>
			</view>
		</view>
		<u-form :model="form" ref="uForm" style="width:90%;background-color: #FFFFFF;padding:0 20rpx;">
			<u-form-item prop="u_id">
				<u-input v-model="form.u_id" placeholder="请输入账号" />
			</u-form-item>
			<u-form-item prop="u_pwd">
				<u-input type="password" v-model="form.u_pwd" placeholder="请设置密码" />
			</u-form-item>
			<u-form-item prop="u_repwd">
				<u-input type="password" v-model="form.u_repwd" placeholder="请再输入一次密码" />
			</u-form-item>

		</u-form>
		<u-button style="width:40%;margin-top:70rpx;" :ripple="true" @click="register" type="primary">注册</u-button>
		
		<u-toast ref="uToast" />
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					u_id: '',
					u_pwd: '',
					u_repwd: ''
				},


				rules: {
					u_id: [{
						required: true,
						message: '请输入账号',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					u_pwd: [{
						required: true,
						message: '请设置密码',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					u_repwd:[{
						required: true,
						message: '请再输一次密码',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					},{
						// 自定义验证函数，见上说明
						validator: (rule, value, callback) => {
							// 上面有说，返回true表示校验通过，返回false表示不通过
							// this.$u.test.mobile()就是返回true或者false的
							//return this.$u.test.mobile(value);
							return this.form.u_pwd === value;
						},
						message: '两次密码不一致',
						// 触发器可以同时用blur和change
						trigger: ['change','blur'],
					}]

				}

			};
		},
		methods: {
			register() {
				this.$refs.uForm.validate(async  valid=>{
					if(valid){
						const params = {u_id:this.form.u_id,u_pwd:this.form.u_pwd};
						await this.$u.post('/public/register',params)
						this.$u.toast('注册成功，请登录!');
						this.$u.route({url:'/pages/login/login',type:'back'});
					}
				});
				
				
			}
		},
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		}
	}
</script>

<style>
	.register {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
</style>
