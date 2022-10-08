<template>
	<view class="admin-add">
		<u-form :model="form" ref="uForm">
			<u-form :model="form" ref="uForm">
				<u-form-item label="ID" >
					<u-input v-model="form.a_id" disabled="true"/>
				</u-form-item>
				<u-form-item label="名字" >
					<u-input v-model="form.a_name" />
				</u-form-item>			
				<u-form-item label="店铺名" labelWidth="140rpx">
					<u-input v-model="form.shop_id" type="select" @click="shopListShow = true" />
					<u-select v-model="shopListShow" :list="shopList" @confirm="onShopListConfirm" value-name="shop_id"
						label-name="shop_name"></u-select>
					<text style="width: 350rpx;text-align: center;">{{form.shop_name}}</text>
				</u-form-item>
				<u-form-item label="密码" label-width="140rpx">
					<u-input v-model="form.a_pwd" disabled="true"/>
				</u-form-item>
			</u-form>
		</u-form>

		<u-button @click="execEdit" type="primary" style="margin: 60rpx 30rpx;" :ripple="true" shape="circle">提交
		</u-button>
		<!--提示框-->
		<u-modal @confirm="execDelAdmin" v-model="confirmShow" :show-cancel-button="true" :content="confirmContent"></u-modal>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				confirmShow:false,
				confirmContent:'',
				form: {
					
				},

				//店铺分类
				shopList:[{
					shop_id:'',
					shop_name:''
				}],
				shopListShow:false,

			};
		},
		// 必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		onLoad(params) {
			this.form = JSON.parse(decodeURIComponent(params.admin));
			console.log(this.form);
			
			this.getShopList();
		},
		onNavigationBarButtonTap(){
			//删除
			this.confirmContent="您确定删除该管理员吗？"
			this.confirmShow = true;
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
			
			async execEdit() {				
				await this.$u.put('/admin/admin',this.form);
				this.$u.toast('编辑成功');
				this.$u.route({url:'/pages/admin/admin',type:'back'});
				
			},
			
			async execDelAdmin(){
				await this.$u.delete("/admin/admin/"+this.form.a_id);
				this.$u.toast('删除成功');
				this.$u.route({url:'/pages/admin/admin',type:'back'});
			}
		}
	}
</script>

<style lang="scss" scoped>
	.admin-add {
		padding: 20rpx 30rpx;
	}
</style>
