<template>
	<view class="meal-edit">
		<u-form :model="form" ref="uForm">
			<u-form-item label="名称" prop="meal_name">
				<u-input v-model="form.meal_name" />
			</u-form-item>
			<u-form-item label="图片">
				<u-image width="35%" height="195rpx" :src="imgPath()"></u-image>
				<u-upload ref="uUpload" :file-list="fileList" :form-data="form" :action="action" :auto-upload="false" :header="header"
					@on-success="onUploadMealPicSuccess" @on-remove="onRemoveMealPic" :max-count="1">
				</u-upload>
			</u-form-item>

			<u-form-item label="分类" prop="cls_name">
				<u-input v-model="form.cls_name" type="select" @click="clsListShow = true" />
				<u-select v-model="clsListShow" :list="clsList" @confirm="onClsListConfirm" value-name="cls_id"
					label-name="cls_name"></u-select>
			</u-form-item>
			<u-form-item label="价格" prop="meal_price">
				<u-input v-model="form.meal_price" type="number" />
			</u-form-item>
			<u-form-item label="描述" prop="meal_desc">
				<u-input v-model="form.meal_desc" type="textarea" />
			</u-form-item>

		</u-form>

		<u-button @click="execEdit" type="primary" style="margin: 60rpx 30rpx;" :ripple="true" shape="circle">提交
		</u-button>
		<!-- 提示框 -->
		<u-modal @confirm="execDelMeal" v-model="confirmShow" :showCancelButton="true" :content="confirmContent"></u-modal>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					meal_id:'',
					meal_name: '',
					meal_price: '',
					meal_desc: '',
					cls_name: '', //分类名称
					cls_id: '', //分类编号
					meal_pic: '',
				},
				action: this.$u.apiBaseUrl + '/admin/meal/edit', //上传地址
				
				header:{
					'Token':uni.getStorageSync('Token'),
					 // 'content-type': 'application/x-www-form-urlencoded'
					},

				//分类选择器的列表数据
				clsList: [{
						cls_id: 1,
						cls_name: '分类1'
					},
					{
						cls_id: 2,
						cls_name: '分类2'
					},
					{
						cls_id: 3,
						cls_name: '分类3'
					}
				],
				clsListShow: false, //分类选择器是否可见

				//校验规则
				rules: {
					meal_name: [{
						required: true,
						message: '请输入餐品名称',
						// 可以单个或者同时写两个触发验证方式
						trigger: 'blur,change'
					}],
					meal_price: [{
							required: true,
							message: '请输入餐品价格',
							// 可以单个或者同时写两个触发验证方式
							trigger: 'blur,change',
							type: 'number'
						},
						{
							min: 1,
							message: '最少价格应不低于1元',
							trigger: 'blur,change',
							type: 'number'
						},
						{
							max: 100,
							message: '最大价格不高于100元',
							trigger: 'blur,change',
							type: 'number'
						}
					],
					meal_desc: [{
						max: 20,
						message: '描述不多于20个字',
						// 可以单个或者同时写两个触发验证方式
						trigger: 'blur'
					}],
					cls_name: [{
						required: true,
						message: '请选择分类',
						// 可以单个或者同时写两个触发验证方式
						trigger: ['blur,change'],
					}]
				},

				confirmShow: false,
				confirmContent: '111',

			};
		},
		//必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		//生命周期函数onLoad的参数就是路由跳转时传递的参数
		onLoad(params) {
			//获取传递的json字符串
			this.form = JSON.parse(decodeURIComponent(params.meal));
			console.log(this.form);

			this.getClsList();
		},
		onNavigationBarButtonTap(options) {
			console.log(options);
			//点击删除按钮
			this.confirmContent = "您确定删除该餐品吗?"
			this.confirmShow = true;

		},

		methods: {
			imgPath() {
				return this.$u.apiBaseUrl + '/public/img/' + this.form.meal_pic + '?t_=' + new Date().getTime();
			},

			async getClsList() {
				this.clsList = await this.$u.get('/public/cls');
				//根据当前的cls_id从clsList中找到相应的cls_name
				const theCls = this.clsList.find(cls => cls.cls_id == this.form.cls_id); //寻找符合条件的分类
				this.form.cls_name = theCls.cls_name;
			},

			onClsListConfirm(selectedArr) {
				console.log("", selectedArr);
				this.form.cls_name = selectedArr[0].label;
				this.form.cls_id = selectedArr[0].value;
			},

			// onRemoveMealPic() {
			// 	 this.form.meal_pic = '';
			// },

			onUploadMealPicSuccess(result) {
				console.log("测试result", result);

				if (result.success) {
					this.$u.toast('编辑成功');
					this.$u.route('/pages/meal/meal')
				} else {
					this.$u.toast(result.message);
				}

			},
			async execEdit() {

				this.form.meal_price = Number(this.form.meal_price); //转换价格为数字类型

				this.$refs.uForm.validate(async valid => {

					if (valid) {
						console.log('验证通过', this.$refs.uUpload.lists);
						if (!this.$refs.uUpload.lists || !this.$refs.uUpload.lists.length) {
							//没有选择图片提交地址
							await this.$u.post('admin/meal/edit-no-img', this.form);
							this.$u.toast('编辑成功!');
							this.$u.route('/pages/meal/meal');
						} else {
							this.$refs.uUpload.upload();
						}

					}
				});
			},
			async execDelMeal(){
				await this.$u.delete("/admin/meal/"+this.form.meal_id);
				this.$u.toast('删除成功');
				this.$u.route('/pages/meal/meal');
			}
		}
		
	}
</script>

<style lang="scss" scoped>
	.meal-edit {
		padding: 20rpx 30rpx;
	}
</style>
