<template>
	<view class="index">
		<u-swiper :height="380" :list="swiperImages" :title="true" style="margin:0rpx 10rpx;"></u-swiper>

		<view class="shop">
			<u-form-item label="门店">
				<u-input v-model="params.shop_name" type="select" :border="false" @click="shopListShow=true"
					placeholder="请选择门店" />
				<u-select @confirm="onShopListShowConfirm" v-model="shopListShow" :list="shopList" value-name="shop_id"
					label-name="shop_name"></u-select>
			</u-form-item>



		</view>

		<view style="text-align: center;">
			<u-tabs :list="tabList" bg-color="#F5F5F5" bar-width="160" active-color="#D9001B" @change="onTabChange"
				v-model="currentTab"></u-tabs>
		</view>



		<view class="card-list">
			<view v-for="shopMeal in shopMealList" :key="shopMeal.sm_id" class="card">
				<u-image border-radius="10rpx 10rpx 0rpx 0rpx" width="100%" height="300rpx"
					:src="$u.apiBaseUrl+'/public/img/'+shopMeal.meal_pic"></u-image>
				<text
					style="font-weight: bold;color:#777777;font-size: 30rpx;padding:10rpx  0 10rpx 20rpx;">{{shopMeal.meal_name}}</text>

				<view style="color:#777777;font-size: 22rpx;padding:5rpx  0 16rpx 15rpx;">
					<text style="margin-right:16rpx;">库存 {{shopMeal.sm_store}}</text>
					<text style="margin-right:16rpx;">销量 {{shopMeal.sm_sale}}</text>
					<text style="margin-right:16rpx;">点击 {{shopMeal.sm_click}}</text>
					<text v-if="shopMeal.sm_recommend" style="color:red;font-weight:bold;">荐</text>
				</view>


				<view style="padding:0rpx  15rpx 12rpx 12rpx;display:flex;justify-content: space-between;">
					<text style="font-weight: bold;color:#FF9900;font-size: 26rpx;">￥{{shopMeal.meal_price}}</text>

					<text @click="addCart(shopMeal)"
						style="background-color: red; color:white;font-size: 22rpx;padding:5rpx 10rpx;border-radius: 5rpx;">加入购物车</text>

				</view>
			</view>
			<view v-if="shopMealList.length%2" class="card card-empty"></view>
		</view>





	</view>

</template>

<script>
	//导航tab选项对应的含义
	const tabMap = {
		0: 'recommend',
		1: 'todayHot',
		2: 'random'
	};

	export default {
		data() {
			return {
			
				tabList: [{
					name: '店长推荐'
				}, {
					name: '今日热卖'
				}, {
					name: '随便吃点',

				}],
				currentTab: 0,


				shopListShow: false, //门店选择器是否显示
				shopList: [], //门店数据列表

				//定义参数
				params: {
					shop_id: '',
					shop_name: ''
				},
				shopMealList: [],

			};
		},

		//定义计算属性（以属性的方式使用，但是其值从其他属性计算而来）
		computed: {
			//计算属性 opr
			opr() {
				return tabMap[this.currentTab];
			},
			swiperImages() {
				return this.shopMealList.map(sm => ({
					image: this.$u.apiBaseUrl + "/public/img/" + sm.meal_pic,
					title: sm.meal_name + "【" + sm.meal_desc + "】"
				}));
			}

		},

		//页面加载时执行
		async onLoad() {

			//从本地缓存中获取当前门店信息
			const currentShop = uni.getStorageSync("CurrentShop");
			console.log("curentShop=====>", currentShop);
			if (currentShop) {
				this.params.shop_id = currentShop.shop_id;
				this.params.shop_name = currentShop.shop_name;
			}

			await this.getShopList();
			if (!this.params.shop_id) { //是否已经选择门店
				this.shopListShow = true;
			} else {
				this.getShopMeal();
			}
		},

		//只要页面从隐藏转换到显示就执行
		onShow() {
			if (this.params.shop_id) { //是否已经选择门店
				this.getShopMeal();
			}


		},

		methods: {
			//获取门店清单
			async getShopList() {
				this.shopList = await this.$u.get("/public/shop-list");
			},

			//确定门店选择
			onShopListShowConfirm([{
				value,
				label
			}]) {
				this.params.shop_id = value;
				this.params.shop_name = label;

				//选择门店后，将当前选择存储本地缓存
				uni.setStorageSync("CurrentShop", {
					shop_id: value,
					shop_name: label
				});

				this.getShopMeal();
			},

			//tab选项发生变化
			onTabChange(obj) {

				//this.nextTick() 等当前的数据变化已经渲染完毕，在下一个环节执行
				this.$nextTick(() => this.getShopMeal());

			},

			//根据选择门店和tab选项查询餐品数据
			async getShopMeal() {
				const args = {
					...this.params,
					opr: this.opr
				}; //解构
				this.shopMealList = await this.$u.get('/public/shop-meal', args)
			},
			
			//加购物车
			async addCart({shop_id,meal_id}){
				const obj = await this.$u.get("/public/is-user");
				if(obj.isUser){//是已登录的消费者
					await this.$u.post('/user/cart',{shop_id,meal_id});
					this.$u.toast('加购成功');
				}else{
					this.$u.route('/pages/login/login');
				}
			}
			
			
		}

	}
</script>

<style scoped lang="scss">
	.index {
		background-color: #F5F5F5;
	}

	.index>.shop {

		margin: 0rpx 30rpx;
		padding: 0rpx 0rpx 0rpx 0rpx;
	}

	.index>.card-list {
		margin: 20rpx 30rpx;
		display: flex;
		justify-content: space-between;
		flex-wrap: wrap;

	}

	.index>.card-list>.card {
		width: 40%;
		flex: auto;
		margin: 10rpx;
		border-radius: 10rpx;

		background-color: #FFFFFF;
		display: flex;
		flex-direction: column;

	}

	.index>.card-list>.card-empty {
		width: 40%;
		flex: auto;
		margin: 10rpx;
		border-radius: 10rpx;

		background-color: transparent;
		/*背景透明*/
		display: flex;
		flex-direction: column;

	}

	.index>.card-list>.card>.img {}
</style>
