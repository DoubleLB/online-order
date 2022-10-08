<template>
	<view >
		<u-sticky offset-top="1050" style="margin-left: auto; margin-right: 120rpx; width: 0rpx;height: 0rpx;">
			<u-button style="width: 100rpx;height: 100rpx;" type="primary" shape="circle" :ripple="true" @click="toAdd"><u-icon name="plus" color="#ffffff" size="38"></u-icon></u-button>
		</u-sticky>
		<u-card  v-for="cls in clsList" :title="cls.cls_name" :body-style="{padding:'6rpx 30rpx 30rpx'}" :head-style="{padding:'30rpx 30rpx 6rpx',fontWeight:'bold'}"  :head-border-bottom="false">
			<template #body >
				<view style="display: flex;flex-direction: row;align-items: center;">
					<view style="flex:auto;font: size 38rpx;">
						<!-- {{cls.cls_name}} -->
					</view>
					<view @click="toEdit(cls)"><u-icon name="edit-pen" style="font-size:40rpx;color:#BBBBBB;"></u-icon></view>
				</view>
			</template>
				
		</u-card>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				clsList: [],
			}
		},
		onLoad() {
			

		},
		onShow(){			
			this.getClsList();
		},
		onReady(){
			
		},
		methods: {
			async getClsList() {
				this.clsList = await this.$u.get('/admin/cls');
			},
			toAdd(){
				this.$u.route('/pages/cls/cls-add');
			},
			toEdit(cls){
				const clsStr = encodeURIComponent(JSON.stringify(cls));
				this.$u.route({
					url:'/pages/cls/cls-edit',					
					params:{
						cls:clsStr
					}
				});
			}
		}
	}
</script>

<style scoped lang="scss">

</style>
Z