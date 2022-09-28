<template>
	<view class="shopmeal">
		<u-collapse :accordion="false">
			<u-collapse-item :name="shop.shop_id" :title="shop.shop_name" v-for="(shop, index) in shopList"
				:key="shop.shop_id">
				<view style="margin:20rpx 20rpx;">
					<view 
						v-for="(meal, idx) in shop.mealList" 
						style="margin: 20rpx 0;display: flex;">					
							<u-checkbox 
								style="flex: auto;"
								v-model="meal.checked" 							
								:key="shop.shop_id+'-'+meal.meal_id"
								:name="meal.meal_id"
								@change="onMealChange($event,shop.shop_id,meal)"
								>{{meal.meal_name}}</u-checkbox>						
							
							<u-icon 
								@click="recommend($event,meal)" 
								:index="shop.shop_id+':'+meal.meal_id" 
								v-if="meal.checked" 
								:name="meal.sm_recommend ? 'star-fill' : 'star'" 
								:color="meal.sm_recommend ? '#fa3545' : ''" 
								size="40" 
								style="margin-right: 70rpx;"></u-icon>
								
							<u-number-box 
								:index="shop.shop_id+':'+meal.meal_id"
								@blur="onStoreChange"  @minus="onStoreChange" @plus="onStoreChange"
								v-if="meal.checked" 
								v-model="meal.sm_store"></u-number-box>
					</view>
					
				</view>
			</u-collapse-item>
		</u-collapse>
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
			this.init();
		},
		methods: {
			async init() {
				this.shopList =  await this.$u.get("admin/shopmeal/full");
			},
			
			/*
				推荐/取消推荐
			*/
			async recommend(shopIdAndmealId,meal){
				console.log("shopIdAndmealId========>",shopIdAndmealId)
				
				//访问服务端操作店长的推荐和取消，操作成功后在修改界面中店长的推荐展示
				//定义向服务端传递的参数
				const params = {
					shop_id:shopIdAndmealId.substring(0,shopIdAndmealId.indexOf(':')),
					meal_id:shopIdAndmealId.substring(shopIdAndmealId.indexOf(':')+1),
					sm_recommend:!meal.sm_recommend
				};
				
				console.log("推荐提交参数=======>",params);
				try{
					await this.$u.put('admin/shopmeal/recommend',params);
					this.$u.toast(!meal.sm_recommend ? '成功推荐' : '取消推荐');
					meal.sm_recommend = !meal.sm_recommend;
				}catch{
					this.$u.toast('操作失败');
				}
					
			}, 
			
			/*
				上架/取消餐品
			*/
			async onMealChange({value,name},shop_id,meal){
				console.log("checkboxchange=====>",value,name,shop_id,meal.checked);
				//定义向服务端传递的参数
				const params = {
					shop_id,
					meal_id:name,
					opr:value ? 'add' : 'del'
				};
				
				const oldChecked = meal.checked;
				try{
					await this.$u.put('admin/shopmeal',params);
					this.$u.toast(value ? '上架成功' : '下架成功');
				}catch(err){
					this.$u.toast(value ? '上架失败' : '下架失败');
					meal.checked = oldChecked;
				}
			},
			
			/*
				库存改变
			*/
			async onStoreChange({value,index}){
				console.log("======>",value,index);
				const arr = index.split(':');
				const params = {
					sm_store:value,
					shop_id:arr[0],
					meal_id:arr[1]
				};
				
				await this.$u.put('admin/shopmeal/store',params);
			}

		}
	}
</script>

<style scoped lang="scss">
	.shopmeal {
		padding: 10rpx 20rpx;
	}

	.collapse-item {

		padding-bottom: 10px;
	}
</style>
