<template>
	<view class="meal-add">
		<u-form :model="form" ref="uForm">
			<u-form-item label="名称" prop="meal_name">
				<u-input v-model="form.meal_name" />
			</u-form-item>
			<u-form-item label="图片">
				<u-upload ref="uUpload" :form-data="form" @on-success="onUploadMealPicSuccess" :header="header"
					@on-remove="onRemoveMealPic" :action="action" :auto-upload="false" :max-count="1"></u-upload>
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

		<u-button @click="execAdd" type="primary" style="margin: 60rpx 30rpx;" :ripple="true" shape="circle">提交
		</u-button>

	</view>
</template>

<script>
	export default {
		data() {
			return {
				form: {
					meal_name: '',
					meal_price: '',
					meal_desc: '',
					cls_name: '', //分类名称
					cls_id: '' ,//分类编号
					
				},
				action: this.$u.apiBaseUrl + '/admin/meal', //上传地址
				
				header:{
					'Token':uni.getStorageSync('Token'),
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
							trigger: 'blur,change'
						},
						{
							min: 1,
							message: '最少价格应不低于1元',
							trigger: 'change'
						},
						{
							max: 100,
							message: '最大价格不高于100元',
							trigger: 'change'
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
				}

			};
		},
		//必须要在onReady生命周期，因为onLoad生命周期组件可能尚未创建完毕
		onReady() {
			this.$refs.uForm.setRules(this.rules);
		},
		onLoad() {
			this.getClsList();
		},

		methods: {
			async getClsList() {
				this.clsList = await this.$u.get('/public/cls');
			},
			onClsListConfirm(selectedArr) {
				console.log("", selectedArr);
				this.form.cls_name = selectedArr[0].label;
				this.form.cls_id = selectedArr[0].value;
			},

			onRemoveMealPic() {
				this.form.meal_pic = '';
			},
			onUploadMealPicSuccess(result) {
				if (result.success) {
					this.$u.toast('新增成功');
					this.$u.route('/pages/meal/meal')
				} else {
					this.$u.toast(result.message);
				}

			},
			async execAdd() {
				console.log("测试lists",this.$refs.uUpload.lists);
				if (!this.$refs.uUpload.lists || !this.$refs.uUpload.lists.length) {
					this.$u.toast('请选择图片');
					return;
				}
				
				this.$refs.uForm.validate(valid => {
					
					console.log("测试valid",valid);//boolean
					
					if (valid) {
						console.log('验证通过');
						this.$refs.uUpload.upload();
					}
				});

				
			}
		}
	}
</script>

<style lang="scss" scoped>
	.meal-add {
		padding: 20rpx 30rpx;
	}
</style>
