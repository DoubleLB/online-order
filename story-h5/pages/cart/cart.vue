<template>
	<view class="cart">
		<u-sticky>
			<view class="shop">
				<u-form-item label="门店">
					<u-input v-model="params.shop_name" type="select" :border="false" @click="shopListShow=true"
						placeholder="请选择门店" />
					<u-select @confirm="onShopListShowConfirm" v-model="shopListShow" :list="shopList"
						value-name="shop_id" label-name="shop_name"></u-select>
				</u-form-item>
			</view>
		</u-sticky>

		<view style="padding:10rpx 0rpx;flex:1;">


			<view v-for="cart in cartList" :key="cart.cart_id"
				style="margin:50rpx 0rpx;height:150rpx;display: flex;flex-direction: row;align-items: center;">
				<u-checkbox @change="onChange" shape="circle" size="40" active-color="red" v-model="cart.checked" :name="cart.cart_id">
				</u-checkbox>
				<!-- 左侧图片 -->
				<view style="width:24%;height:100%;border-radius: 20rpx;margin-right:20rpx;">
					<u-image border-radius="20rpx" width="100%" height="100%" :src="$u.apiBaseUrl+'/public/img/'+cart.meal_pic"></u-image>
				</view>
				<view
					style="flex:auto;height:100%;padding:10rpx 0rpx;display: flex;flex-direction: column;justify-content: space-between;">
					<text style="font-weight: bold;color:#666666;">{{cart.meal_name}}</text>
					<text style="font-size:35rpx;font-weight: bold;color:#fa3534;">￥{{cart.meal_price}}</text>
				</view>
				<view style="height:100%;padding:10rpx 0rpx;display: flex;align-items: flex-end;">
					<text style="color: #fa3534;font-size: 10rpx;margin-right: 30rpx;" @click="removeCart(cart.cart_id)">删除</text>
					<u-number-box  
						bg-color="#f4f4f5" color="#303133" :input-width="60" :input-height="40"
						v-model="cart.cart_num"
						:index="cart.cart_id"
						:min=1
						@minus="onCartNumChange"
						@plus="onCartNumChange"
						@blur="onCartNumChange"
						></u-number-box>
				</view>
			</view>

		</view>
		<view
			style="display: flex;justify-content: space-between; align-items: center; 
				position:fixed;bottom:98rpx;left:10rpx;right:10rpx;background-color: white;padding:10rpx 0rpx;
				border-top:1rpx solid #EEEEEE;box-sizing: border-box;"
			>
			<u-checkbox shape="circle" size="40" active-color="red" v-model="allChecked" @change="onAllChange" style="flex:1;">全选
			</u-checkbox>
			<view>
				<text style="vertical-align: middle;">已选{{totalCount}}份,合计:</text>
				<text style="vertical-align: middle;font-size:36rpx;font-weight: bold;color:#fa3534;">￥{{totalMoney}}</text>
			</view>
			<view style="width:120rpx;margin-left:30rpx;">
				<u-button @click="balance" type="error">结算</u-button>
			</view>
		</view>


	</view>
</template>

<script>
	export default {
		data() {
			return {
				shopListShow: false, //门店选择器是否显示
				shopList: [], //门店数据列表
				
				//购物车列表
				cartList:[],
				allChecked:false,

				//定义参数
				params: {
					shop_id: '', //当前选中门店编号
					shop_name: '', //当前选中门店名称
					
				},
			};
		},
		
		computed:{
			totalMoney(){
				let money = 0;
				this.cartList.forEach(cart=>{
					if(cart.checked)money+=cart.meal_price*cart.cart_num;
				});
				return money;
			},
			totalCount(){
				let count = 0;
				this.cartList.filter(cart=>cart.checked).forEach(cart=>count+=cart.cart_num);
				return count;
			}
		},
		
		//页面加载时执行
		async onLoad() {
			
		},

		//只要页面从隐藏 转换到 显示，即执行
		async onShow() {
			//从本地缓存中获取当前门店信息
			const currentShop = uni.getStorageSync("CurrentShop");
			//console.log("currentShop====>",currentShop);
			if (currentShop) {
				this.params.shop_id = currentShop.shop_id;
				this.params.shop_name = currentShop.shop_name;
			}
			await this.getShopList();
			if (!this.params.shop_id) { //是否选择门店
				this.shopListShow = true;
			}
			
			if (this.params.shop_id) { //是否选择门店				
				 this.getCartList();
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

				//选择门店后，将当前选择存储于本地缓存
				uni.setStorageSync("CurrentShop", {
					shop_id: value,
					shop_name: label
				});

				this.getCartList();

			},

			//获取购物车列表清单
			async getCartList(){
				this.cartList = await this.$u.get('/user/cart',this.params);
				this.allChecked = false;
			},
			
			//勾选单个选项
			onChange(){//为了使allChecked变为true，使得全选改变
				this.$nextTick(()=>{
					this.allChecked=this.cartList.every(cart=>cart.checked);
					//every()数组的方法
					//判断数组中的元素是否符合某个条件
					//如果数组中的所有元素都符合条件 返回true
					//相反只要有一个不符合就返回false
				})
				
				
			},
			
			//全选
			onAllChange({value}){
				this.cartList.forEach(cart=>cart.checked=value);
			},
			
			//购物车数量变化
			async onCartNumChange({value,index}){
				// console.log({value,index});//{value 数量,index 位置}
				await this.$u.put('/user/cart/num',{cart_id:index,cart_num:value});
			},
			
			//结算
			async balance(){
				//获得 选中 购物车餐品的id
				const cartIds = this.cartList.filter(cart=>cart.checked).map(cart=>cart.cart_id);
				if(!cartIds || !cartIds.length){
					this.$u.toast('请选择餐品');
					return;
				}
				await this.$u.post("/user/order/add/"+this.params.shop_id,cartIds);
				this.$u.toast('结算成功');
				
				uni.switchTab({
				     url: '/pages/order/order'
				   })
				// this.$u.route('pages/order/order');
			},
			
			//删除购物车商品
			removeCart(rmc){
				this.$u.post('/user/cart/remove',{cart_id:rmc});
				this.$u.toast('删除成功');
				
				uni.reLaunch({
					url: '/pages/cart/cart'
				});


			}

			
		}
	}
</script>

<style scoped lang="scss">
	.cart {
		padding: 10rpx 20rpx 80rpx;
		
	}

	.cart .shop {
		background-color: #FFFFFF;
		
	}
</style>
