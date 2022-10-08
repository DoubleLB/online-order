<template>
	<view class="password">
		<u-form :model="form" ref="uForm">
			<u-form-item label="新密码" labelWidth="140rpx">
				<u-input v-model="form.a_pwd" type="password"/>
			</u-form-item>
		</u-form>
		<u-button @click="updatePassword" type="primary" style="margin: 60rpx 30rpx;" :ripple="true" shape="circle">提交</u-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				form:{
					a_pwd:''
				}
			}
		},
		methods: {
			
			async updatePassword() {
				try {
					await this.$u.post('/admin/admin/pwd', this.form.a_pwd);
					this.$u.toast('修改成功');
					this.$u.route('/pages/adminlogin/adminlogin');
				} catch (err) {
					this.$u.toast('提交失败！');
				}
			
			}
		}
	}
</script>

<style lang="scss" scoped>
	.password {
		padding: 20rpx 30rpx;
	}
</style>
