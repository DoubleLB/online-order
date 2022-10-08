<template>
	<view class="admin-add">
		<u-form :model="form" ref="uForm">
			<u-form-item label="ID" prop="a_id">
				<u-input v-model="form.a_id" />
			</u-form-item>
			<u-form-item label="名字" prop="a_name">
				<u-input v-model="form.a_name" />
			</u-form-item>			
			<u-form-item label="店铺名" prop="shop_id" labelWidth="140rpx">
				<u-input v-model="form.shop_id" type="select" @click="shopListShow = true" />
				<u-select v-model="shopListShow" :list="shopList" @confirm="onShopListConfirm" value-name="shop_id"
					label-name="shop_name"></u-select>
				<text style="width: 350rpx;text-align: center;">{{form.shop_name}}</text>
			</u-form-item>
			<u-form-item label="密码" label-width="140rpx" prop="a_pwd">
				<u-input v-model="form.a_pwd"  />
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
					a_id:'',
					a_name: '',
					a_pwd: '',
					shop_id: ''
				},
				
				//店铺分类
				shopList:[{
					shop_id:'',
					shop_name:''
				}],
				shopListShow:false,

				//校验规则
				rules: {

					a_id:[{
						required: true,
						message: '请输入ID',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],
					
					a_name: [{
						required: true,
						message: '请输入管理员名字',
						// 可以单个或者同时写两个触发验证方式 
						trigger: ['change', 'blur'],
					}],

					shop_id: [{
						required: true,
						message: '请选择门店',
						trigger: ['change', 'blur'],
					}],

					a_pwd: [{
						required: true,
						message: '请输入密码',
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
			this.getShopList();
		},
		methods: {
			async getShopList() {
				this.shopList = await this.$u.get('/admin/admin/shop');
			},
			onShopListConfirm(selectedArr){
				console.log("test", selectedArr);
				this.form.shop_name = selectedArr[0].label;
				this.form.shop_id = selectedArr[0].value.toString();
			},
			
			execAdd() {
				
				this.$refs.uForm.validate(async valid => {
					if (valid) {						
						await this.$u.post('/admin/admin',this.form);
						this.$u.toast('新增成功');
						this.$u.route({url:'/pages/admin/admin',type:'back'});
					} 
				});

				

			}
		}
	}
</script>

<style lang="scss" scoped>
	.admin-add {
		padding: 20rpx 30rpx;
	}
</style>
