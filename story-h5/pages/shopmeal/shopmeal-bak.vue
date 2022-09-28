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
				//加载门店清单
				const shopList = await this.$u.get('admin/shopmeal/shop');

				//加载所有的餐品，并将餐品集合清单，赋值给shop.mealList
				const mealList = await this.$u.get('admin/shopmeal/meal-all');

				shopList.forEach(shop => {
					//依据原数组中的元素产生新的数组
					const newArr = mealList.map(item => ({
						meal_id: item.meal_id,
						meal_name: item.meal_name,
						sm_store:0,
						sm_recommend:false
					}));
					shop.mealList = newArr;
				});

				this.shopList = shopList;

				//根据shopList中的shopId，访问服务端获取各门店的餐品数据
				const shopIds = this.shopList.map(item => item.shop_id);
				console.log("测试shopIds", shopIds);

				//访问服务端获取各门店的餐品数据
				const shopMealList = await this.$u.get('admin/shopmeal', {shopIds});

				/*
				解决门店商家餐品的选中问题
				*/
				//循环门店，再在每个门店下找餐品列表，
				//接下来循环餐品列表，看每个门店的每个餐品是否在门店上架餐品列表
				this.shopList.forEach(shop => {
					shop.mealList.forEach(meal => {
						// const checked = shopMealList.some(sm => sm.shop_id == shop.shop_id && sm.meal_id == meal.meal_id); //判断 返回true或者false
						const sm = shopMealList.find(sm => sm.shop_id == shop.shop_id && sm.meal_id == meal.meal_id); //寻找 返回true或者false
						
						if(sm){//如果餐品上架，1.应该赋值餐品库存量  2.店长推荐recommend
							meal.checked = true;
							meal.sm_store = sm.sm_store;
							meal.sm_recommend = sm.sm_recommend;
						}

					});
				});
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
				// console.log("======>",value,index);
				const arr = index.split(':');
				const params = {
					sm_store:value,
					shop_id:arr[0],
					meal_id:arr[1]
				};
				
				await this.$u.put('admin/shopmeal/store',params);
			}

			// async onChange(shop_id) {
			// 	console.log(shop_id);

			// 	//向服务端发请求，获取门店餐品信息
			// 	const list = await this.$u.get('admin/shopmeal/meal/'+shop_id);

			// 	const shop =  this.shopList.find(shop => shop.shop_id = shop_id);
			// 	shop.mealList = list;
			// }

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
