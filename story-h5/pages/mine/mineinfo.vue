<template>
	<view class="mineinfo-edit">
		<u-form :model="form" ref="uForm">
			<u-form-item label="ID:" >
				<u-input v-model="form.u_id" disabled="true"/>
			</u-form-item>
			<u-form-item label="名字" prop="u_nickname">
				<u-input v-model="form.u_nickname" />
			</u-form-item>
			<u-form-item label="头像">
				<u-image width="35%" height="195rpx" borderRadius="50%" :src="(this.form.u_avatar == null ? avatarUrl : imgPath())"></u-image>
				<u-upload ref="uUpload" :file-list="fileList" :form-data="form" :action="action" :auto-upload="false" :header="header"
					@on-success="onUploadUserPicSuccess" :max-count="1">
				</u-upload>
			</u-form-item>

			<u-form-item label="性别" prop="u_sex">
				<u-input v-model="form.u_sex" type="select" @click="sexListShow = true" />
				<u-select v-model="sexListShow" :list="sexList" @confirm="onsexListConfirm" value-name="value"
					label-name="label"></u-select>
			</u-form-item>
			<u-form-item label="密码" prop="u_pwd">
				<u-input v-model="form.u_pwd" type="password"/>
			</u-form-item>
			<u-form-item label="注册时间"  labelWidth="140rpx" >
					<u-input v-model="form.u_register" disabled="true" />
			</u-form-item>
			

		</u-form>

		<u-button @click="execSubmit" type="primary" style="margin: 60rpx 30rpx;" :ripple="true" shape="circle">提交
		</u-button>
		
		
		<!-- 提示框 -->
		<u-modal @confirm="toQuite" v-model="confirmShow" :showCancelButton="true" :content="confirmContent"></u-modal>
		<text class="quite" @click="open">退出登录</text>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				avatarUrl: "../../static/uni-center/logo.png",
				form: {
					u_sex:'',
					u_nickname:'',
					u_avatar:'',
					u_pwd:'',
					u_register:'',
				},
				action: this.$u.apiBaseUrl + '/user/user/edit', //上传地址				

				header:{
					'Token':uni.getStorageSync('Token'),
					},//给请求头添加Token

				//分类选择器的列表数据
				sexList: [{
						value: 1,
						label: '男'
					},
					{
						value: 2,
						label: '女'
					}
				],
				sexListShow: false, //分类选择器是否可见

				//校验规则
				rules: {
					u_nickname: [{
						required: true,
						message: '请输入姓名',
						// 可以单个或者同时写两个触发验证方式
						trigger: 'blur,change'
					}],
					u_sex: [{
						required: true,
						message: '请选择分类',
						// 可以单个或者同时写两个触发验证方式
						trigger: ['blur,change'],
					}],
					u_pwd:[{
						required: true,
						max:10,
						min:1,
						message:'请输入密码（小于10位）',
						trigger:['blur,change']
					}]
				},

				//模态框信息
				confirmShow: false,//是否可见
				confirmContent: '真的要退出吗',//内容

			};
		},
		
		//必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		
		//生命周期函数onLoad的参数就是路由跳转时传递的参数
		onLoad(params) {
			//获取传递的json字符串
			this.form = JSON.parse(decodeURIComponent(params.user));
			console.log(this.form);
		},

		methods: {
			
			//打开模态框
			open(){
				this.confirmShow = true;
			},
			
			//获取图片
			imgPath() {
				return this.$u.apiBaseUrl + '/public/img-user/' + this.form.u_avatar + '?t_=' + new Date().getTime();
			},

			//选择器确认信息
			onsexListConfirm(selectedArr) {
				console.log("test selectedArr", selectedArr);
				this.form.u_sex = selectedArr[0].label;
			},

			//图片上传成功执行
			onUploadUserPicSuccess(result) {
				console.log("测试result", result);

				if (result.success) {
					this.$u.toast('更新成功');
					
					//跳转后刷新页面
					 this.$u.route({url:'/pages/mine/mine',type:'tab'})
					
				} else {
					this.$u.toast(result.message);
				}

			},
			
			//提交
			async execSubmit() {

				this.$refs.uForm.validate(async valid => {

					if (valid) {
						console.log('验证通过', this.$refs.uUpload.lists);
						if (!this.$refs.uUpload.lists || !this.$refs.uUpload.lists.length) {
							//没有选择图片提交地址
							await this.$u.post('user/user/edit-no-img', this.form);
							this.$u.toast('更新成功!');
							
							//跳转页面在更新
							this.$u.route({url:'/pages/mine/mine',type:'tab'})
							// uni.reLaunch({
							// 	url: "/pages/mine/mine"
							// });
							//  //跳转后刷新页面
							//  uni.switchTab({
							//       url: '/pages/mine/mine',
							// 	  type:'tab',
							// 	  success(){
							// 		 uni.reLaunch({
							// 		 	url: "/pages/mine/mine"
							// 		 });
							// 	  }
							// });
							
						} else {
							this.$refs.uUpload.upload();
						}

					}
				});
			},
			
			//退出登录
			toQuite(){
				uni.removeStorageSync("Token"); //清除本地缓存中的令牌
				this.$u.toast("退出成功");
				this.$u.route({
					url: '/pages/login/login',
					type: 'reLaunch' //所有页面都出路由栈，新页面进栈
				});
			},

		}
		
	}
</script>

<style lang="scss" scoped>
	.mineinfo-edit {
		padding: 20rpx 30rpx;
	}
	
	.quite{
		position: absolute;
		color: #999999;
		top: 95%;left: 82%;
	}
	
</style>
