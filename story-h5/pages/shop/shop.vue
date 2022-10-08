<template>
	<view >
		<u-sticky offset-top="1050" style="margin-left: auto; margin-right: 120rpx; width: 0rpx;height: 0rpx;">
			<u-button style="width: 100rpx;height: 100rpx;" type="primary" shape="circle" :ripple="true" @click="toAdd"><u-icon name="plus" color="#ffffff" size="38"></u-icon></u-button>
		</u-sticky>
		<u-card  v-for="shop in shopList" :title="shop.shop_name" :body-style="{padding:'6rpx 30rpx 30rpx'}" :head-style="{padding:'30rpx 30rpx 6rpx',fontWeight:'bold'}"  :head-border-bottom="false">
			<template #body >
				<view style="display: flex;flex-direction: row;align-items: center;">
					<view style="flex:auto;" @click="toAOrder(shop)">
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
			},
			
			//带参跳转
			toAOrder(shop){
				console.log(shop);
				
				//将对象转为json字符串
				const jsonStr = JSON.stringify(shop);
				console.log(jsonStr);
				
				//对字符串进行字符串编码
				const str = encodeURIComponent(jsonStr);
				console.log(str);
				
				this.$u.route({
					url:'pages/shop/shop_order',
					params:{
						shop:str
					}
				});
			}
		}
	}
</script>

<style scoped lang="scss">

</style>
