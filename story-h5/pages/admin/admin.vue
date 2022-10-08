<template>
	<view >
		
		<u-sticky  offset-top="1000" style="margin-left: auto; margin-right: 120rpx; width: 0rpx;height: 0rpx;">
			<u-button style="width: 100rpx;height: 100rpx;" type="primary" shape="circle" :ripple="true" @click="toAdd"><u-icon name="plus" color="#ffffff" size="38"></u-icon></u-button>
		</u-sticky>
		
		<u-card  v-for="admin in adminList" :title="admin.a_name" :body-style="{padding:'6rpx 30rpx 30rpx'}" :head-style="{padding:'30rpx 30rpx 6rpx',fontWeight:'bold'}"  :head-border-bottom="false">
			<template #body >
				<view style="display: flex;flex-direction: row;align-items: center;">
					<view style="flex:auto;" >
						<view style="color:#777777;font-size:20rpx;margin-bottom:8rpx;" >
							<text style="font-weight: bold;">所属店铺:</text>{{admin.shop_name}}
						</view>	
						<view style="color:#777777;font-size:20rpx;" >
							<text style="font-weight: bold;">ID:</text>{{admin.a_id}}
						</view>
						<view style="color:#777777;font-size:20rpx;" >
							<text style="font-weight: bold;">Password:</text>{{admin.a_pwd}}
						</view>
					</view>
					<view @click="toEdit(admin)"><u-icon name="edit-pen" style="font-size:40rpx;color:#BBBBBB;"></u-icon></view>
				</view>
			</template>
				
		</u-card>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				adminList: [],
			}
		},
		onLoad() {
			

		},
		onShow(){			
			this.getAdminList();
		},
		onReady(){
			
		},
		methods: {
			
			async getAdminList() {
				this.adminList = await this.$u.get('/admin/admin');
			},
			
			toAdd(){
				this.$u.route('/pages/admin/admin-add');
			},
			
			toEdit(admin){
				const adminStr = encodeURIComponent(JSON.stringify(admin));
				this.$u.route({
					url:'/pages/admin/admin-edit',					
					params:{
						admin:adminStr
					}
				});
			},
			
		}
	}
</script>

<style scoped lang="scss">

</style>
