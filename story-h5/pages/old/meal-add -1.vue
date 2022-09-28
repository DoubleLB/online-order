<template>
	<view class="meal-add">
		<u-form :model="form" ref="uForm">
			<u-form-item label="名称">
				<u-input v-model="form.meal_name" />
			</u-form-item>
			<u-form-item>
				<!-- 
					在vue中ref用于给组件或者html标签起一个引用名字
					action 上传的地址，这个地址必须写全路径
					auto-upload 是否自动上传
				 -->
				<u-upload ref="uUpload" @on-success="onUploadMealPicSuccess" @on-remove="onRemoveMealPic"
					:action="action" :auto-upload="false" :max-count="1"></u-upload>
				<u-button @click="uploadImg">确定</u-button>
			</u-form-item>

			<u-form-item label="分类">
				<u-input v-model="form.cls_name" type="select" @click="clsListShow = true" />
				<u-select v-model="clsListShow" :list="clsList" @confirm="onClsListConfirm" value-name="cls_id"
					label-name="cls_name"></u-select>
			</u-form-item>
			<u-form-item label="价格">
				<u-input v-model="form.meal_price" type="number" />
			</u-form-item>
			<u-form-item label="描述">
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
					cls_id: '', //分类编号
					meal_pic: '' //图片路径
				},
				action: this.$u.apiBaseUrl + '/public/upload', //上传地址
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

			};
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
			uploadImg() {
				this.$refs.uUpload.upload();
			},
			onUploadMealPicSuccess(result) {
				console.log("上传成功返回数据：", result);
				this.form.meal_pic = result.data;
			},
			async execAdd() {
				if (!this.form.meal_pic) {
					this.$u.toast('请确认图片再提交');
					return;
				}
				try {
					await this.$u.post('/admin/meal', this.form);
					this.$u.toast('新增成功');
					this.$u.route('/pages/meal/meal');
				} catch (err) {
					this.$u.toast('提交失败！');
				}

			}
		}
	}
</script>

<style lang="scss" scoped>
	.meal-add {
		padding: 20rpx 30rpx;
	}
</style>
