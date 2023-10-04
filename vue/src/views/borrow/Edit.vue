<template>
  <div style="width:100%">
    <div style="margin-left: 30px">
      <h3 style="margin-left:10px; margin-bottom: 30px">编辑分类</h3>
      <div>

        <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px" style="width: 100%">

          <el-form-item label="ISBN" prop="bookno">
            <el-select v-model="form.bookno" clearable filterable placeholder="请选择" @change="selBook">
              <el-option
                  v-for="item in books"
                  :key="item.id"
                  :label="item.bookno"
                  :value="item.bookno">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="图书名称" prop="bookname">
            <el-input v-model="form.bookname" placeholder="请输入名称" style="width: 200px;margin-left: 10px" disabled></el-input>
          </el-form-item>

          <el-form-item label="用户id" prop="userid">
            <el-select v-model="form.userid" clearable filterable placeholder="请选择" @change="selUser">
              <el-option
                  v-for="item in users"
                  :key="item.id"
                  :label="item.username"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="用户名" prop="username" style="margin-top: 10px">
            <el-input v-model="form.username" placeholder="请输入用户名" style="width: 200px;margin-left: 10px" disabled></el-input>
          </el-form-item>

          <el-form-item label="用户联系方式" prop="userphone" style="margin-top: 10px">
            <el-input v-model="form.userphone" placeholder="请输入用户联系方式" style="width: 200px;margin-left: 10px" disabled></el-input>
          </el-form-item>


        </el-form>

      </div>
    </div>

    <div style="margin-left: 80px;margin-top: 20px">
      <el-button type="primary" @click="save" style="margin-right: 10px">修改</el-button>
      <el-button @click="back" style="margin-left: 20px">取消</el-button>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";
export default {
  name: 'EditBook',
  data(){
    return{
      form:{},
      books:[],
      users:[],
      rules: {
        name: [
          {required: true, message: '请输入图书名称', trigger: 'blur'},
        ],
      }
    }
  },
  created() {
    request.get('/book/list').then(res=>{
      this.books=res.data
    })

    request.get('/user/list').then(res=>{
      this.users=res.data
    })
  },
  methods:{
    save(){
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          request.post('/borrow/update', this.form).then(res => {
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
    back(){
      this.$router.push("/borrowList")
    },
    reset() {
      this.form={}
    },
    selBook(){
      const book = this.books.find(v => v.bookno === this.form.bookno)
      this.form.bookname=book.name
    },
    selUser(){
      const user = this.users.find(v => v.id === this.form.userid)
      this.form.username=user.name
      this.form.userphone=user.phone
    }
  }
}
</script>


<!--el-form不显示-->


