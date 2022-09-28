<template>
	<view class="meal-add">
		<u-form :model="form" ref="uForm">
			<u-form-item label="名称" prop="shop_name">
				<u-input v-model="form.shop_name" />
			</u-form-item>			
			<u-form-item label="地址" prop="shop_addr">
				<u-input v-model="form.shop_addr"/>
			</u-form-item>
			<u-form-item label="营业时间" label-width="140rpx" prop="shop_open">
				<u-input v-model="form.shop_open"  />
			</u-form-item>
		</u-form>

		<u-button @click="execAdd" type="primary" style="margin: 60rpx 30rpx;" :ripple="true" shape="circle">提交
		</u-button>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					shop_name: '',
					shop_addr: '',
					shop_open: ''
				},

				//校验规则
				rules: {

					shop_name: [{
						required: true,
						message: '请输入门店名称',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],

					shop_addr: [{
						required: true,
						message: '请输入门店地址',
						trigger: ['change', 'blur'],
					}],

					shop_open: [{
						required: true,
						message: '请输入营业时间',
						trigger: ['change', 'blur'],
					}]
				},

			};
		},
		// 必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		onLoad() {
			
		},
		methods: {
			
			execAdd() {
				
				this.$refs.uForm.validate(async valid => {
					if (valid) {						
						await this.$u.post('/admin/shop',this.form);
						this.$u.toast('新增成功');
						this.$u.route({url:'/pages/shop/shop',type:'back'});
					} 
				});

				

			}
		}
	}
</script>

<style lang="scss" scoped>
	.meal-add {
		padding: 20rpx 30rpx;
	}
</style>
