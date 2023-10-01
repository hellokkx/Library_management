<template>
  <div style="width:500px">
    <div style="margin-left: 10px">
      <h3 style="padding-left:20px; margin-bottom: 30px">新增管理员</h3>
      <div>
        <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px">
          <el-form-item label="管理员" prop="username">
            <el-input v-model="form.username" placeholder="请输入管理员名" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>


          <el-form-item label="联系方式" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入联系方式" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="form.email" placeholder="请输入邮箱" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>

        </el-form>
      </div>
    </div>

    <div style="margin-left: 80px;margin-top: 20px">
      <el-button type="primary" plain @click="save" style="margin-right: 10px">提交</el-button>
      <el-button type="info" plain @click="reset" style="margin-left: 20px">重置</el-button>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";

export default {
  name: 'AddAdmin',
  data() {

    const checkPhone= (rule, value, callback) => {
      if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
          callback(new Error('合法的手机号:11位,非"11"或"12"开头'));
      }
      callback()
    };


    return {
      form: {},
      sexoption: [{
        value: '男',
        label: '男'
      }, {
        value: '女',
        label: '女'
      }],
      rules: {
        username: [
          {required: true, message: '请输入管理员姓名', trigger: 'blur'},
        ],
        phone: [
          {validator: checkPhone, trigger: 'blur'}
        ],
      }

    }
  },
  methods: {
    save() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/admin/save', this.form).then(res => {
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
    }
  }
}
</script>


<!--el-form不显示-->


