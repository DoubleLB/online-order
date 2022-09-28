<template>
	<view >
		<u-sticky style="padding:10rpx 40rpx;">
			<u-button type="error" shape="circle" :ripple="true" @click="toAdd"  >新增</u-button>
		</u-sticky>
		<u-card  v-for="shop in shopList" :title="shop.shop_name" :body-style="{padding:'6rpx 30rpx 30rpx'}" :head-style="{padding:'30rpx 30rpx 6rpx',fontWeight:'bold'}"  :head-border-bottom="false">
			<template #body >
				<view style="display: flex;flex-direction: row;align-items: center;">
					<view style="flex:auto;">
						<view style="color:#777777;font-size:20rpx;margin-bottom:8rpx;" >
							{{shop.shop_addr}}
						</view>	
						<view style="color:#777777;font-size:20rpx;" >
							营业时间：{{shop.shop_open}}
						</view>
					</view>
					<view @click="toEdit(shop)"><u-icon name="edit-pen" style="font-size:40rpx;color:#BBBBBB;"></u-icon></view>
				</view>
			</template>
				
		</u-card>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				shopList: [],
			}
		},
		onLoad() {
			

		},
		onShow(){			
			this.getShopList();
		},
		onReady(){
			
		},
		methods: {
			async getShopList() {
				this.shopList = await this.$u.get('/admin/shop');
			},
			toAdd(){
				this.$u.route('/pages/shop/shop-add');
			},
			toEdit(shop){
				const shopStr = encodeURIComponent(JSON.stringify(shop));
				this.$u.route({
					url:'/pages/shop/shop-edit',					
					params:{
						shop:shopStr
					}
				});
			}
		}
	}
</script>

<style scoped lang="scss">

</style>
