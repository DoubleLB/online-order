<template>
	<view class="cls-add">
		<u-form :model="form" ref="uForm">
			<u-form-item label="类名" prop="cls_name">
				<u-input v-model="form.cls_name" />
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
					cls_name: ''
					
				},

				//校验规则
				rules: {

					cls_name: [{
						required: true,
						message: '请输入种类名',
						// 可以单个或者同时写两个触发验证方式 
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
						await this.$u.post('/admin/cls',this.form);
						this.$u.toast('新增成功');
						this.$u.route({url:'/pages/cls/cls',type:'back'});
					} 
				});

				

			}
		}
	}
</script>

<style lang="scss" scoped>
	.cls-add {
		padding: 20rpx 30rpx;
	}
</style>
