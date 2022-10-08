<template>
	<view>
		<u-grid :col="3">
			<u-grid-item @click="$u.route('/pages/shop/shop')">
				<u-icon name="/static/adminhome/shop.png" :size="46"></u-icon>
				<view class="grid-text">门店</view>
			</u-grid-item>
			<u-grid-item @click="$u.route('/pages/cls/cls')">
				<u-icon name="/static/adminhome/cls.png" :size="46"></u-icon>
				<view class="grid-text">分类</view>
			</u-grid-item>
			<u-grid-item @click="$u.route('/pages/meal/meal')">
				<u-icon name="/static/adminhome/meal.png" :size="46"></u-icon>
				<view class="grid-text">所有餐品</view>
			</u-grid-item>
			<u-grid-item @click="$u.route('/pages/shopmeal/shopmeal')">
				<u-icon name="/static/adminhome/shop-meal.png" :size="46"></u-icon>
				<view class="grid-text">门店餐品</view>
			</u-grid-item>
			<u-grid-item @click="$u.route('/pages/adminhome/admin_order_shop')">
				<u-icon name="/static/adminhome/order.png" :size="46"></u-icon>
				<view class="grid-text">订单管理</view>
			</u-grid-item>
			<u-grid-item @click="$u.route('/pages/admin/admin')">
				<u-icon name="/static/adminhome/admin.png" :size="46"></u-icon>
				<view class="grid-text">管理员</view>
			</u-grid-item>
			<u-grid-item @click="$u.route('/pages/adminhome/adminpassword')">
				<u-icon name="/static/adminhome/password.png" :size="46"></u-icon>
				<view class="grid-text">密码修改</view>
			</u-grid-item>
			<u-grid-item @click="adminLogout">
				<u-icon name="/static/adminhome/exit.png" :size="46"></u-icon>
				<view class="grid-text">退出</view>
			</u-grid-item>
		</u-grid>
	</view>
</template>

<script>
	export default {
		data() {
			return {

			}
		},
		methods: {
			adminLogout() {
				uni.removeStorageSync("Token"); //清除本地缓存中的令牌
				this.$u.toast("退出成功");
				this.$u.route({
					url: '/pages/adminlogin/adminlogin',
					type: 'reLaunch' //所有页面都出路由栈，新页面进栈
				});
			}
		}
	}
</script>

<style>

</style>
