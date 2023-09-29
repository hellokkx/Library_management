<template>
  <div>
    <div style="padding: 20px">
      <el-form :inline="true" :model="form" style="width: 70%" label-width="200">
        <el-form-item label="姓名">
          <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" placeholder="请输入年龄"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="form.sex" placeholder="请输入性别"></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="form.phone" placeholder="请输入电话"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" placeholder="请输入地址"></el-input>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AddUser',
  data(){
    return{
      form:{}
    }
  }
}
</script>


<!--el-form不显示-->


