<template>
  <div style="width:100%">
    <div style="margin-left: 10px">
      <h3 style="padding-left:20px; margin-bottom: 30px">新增图书</h3>
      <div>
        <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px" style="width: 100%">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入名称" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>


          <el-form-item label="描述" prop="description">
            <el-input type="textarea" v-model="form.description" placeholder="请输入描述" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>

          <el-form-item label="出版日期" prop="publishdate">
            <el-date-picker
                style="width: 95%"
                v-model="form.publishdate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择出版日期">
            </el-date-picker>
          </el-form-item>

          <el-form-item label="作者" prop="author" style="margin-top: 10px">
            <el-input v-model="form.author" placeholder="请输入作者" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>

          <el-form-item label="出版社" prop="publisher" style="margin-top: 10px">
            <el-input v-model="form.publisher" placeholder="请输入出版社" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>

          <el-form-item label="分类" prop="category" style="margin-top: 10px">
            <el-cascader
                :props="{value:'name',label:'name'}"
                v-model="form.categories"
                :options="categories"
                @change="handleChange"
            ></el-cascader>
          </el-form-item>

          <el-form-item label="ISBN" prop="bookno" style="margin-top: 15px">
            <el-input v-model="form.bookno" placeholder="请输入ISBN" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>

          <el-form-item label="图书数量" prop="nums" style="margin-top: 15px;margin-left: 10px">
            <el-input-number v-model="form.nums" :min="1" :max="1000" label="图书数量" style="margin-left: 10px"></el-input-number>
          </el-form-item>

          <el-form-item label="封面" prop="cover" style="margin-top: 15px;margin-left: 60px">
            <el-input v-model="form.cover" placeholder="请输入封面" style="width: 200px;"></el-input>
          </el-form-item>

        </el-form>
      </div>
    </div>

    <div style="margin-left: 80px;margin-top: 20px">
      <el-button type="primary" plain @click="save" style="margin-left: 80px;margin-right: 10px">提交</el-button>
      <el-button type="info" plain @click="reset" style="margin-left: 20px">重置</el-button>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: 'AddBook',
  data() {
    return {
      form: {},
      categories:[],
      sexoption: [{
        value: '男',
        label: '男'
      }, {
        value: '女',
        label: '女'
      }],
      rules: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'},
        ],
        bookno: [
          {required: true, message: '请输入ISBN', trigger: 'blur'},
        ],
        nums: [
          {required: true, message: '请输入图书数量', trigger: 'blur'},
        ],
      }

    }
  },
  created() {
    request.get('/category/tree').then(res=>{
      this.categories=res.data
    })
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/book/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('新增成功');
              this.form={}
            } else {
              this.$notify.error(res.msg)
            }
          })
        }
      })

    },
    reset() {
      this.form={}
    },
    handleChange(val){
      console.log(val)
    },
  }
}
</script>


<!--el-form不显示-->


