<template>
  <div style="width:500px">
    <div style="margin-left: 30px">
      <h3 style="margin-left:10px; margin-bottom: 30px">编辑管理员</h3>
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
      <el-button type="primary" @click="save" style="margin-right: 10px">修改</el-button>
      <el-button @click="back" style="margin-left: 20px">取消</el-button>
    </div>
  </div>
</template>

<script>


import request from "@/utils/request";
export default {
  name: 'EditAdmin',
  data(){
    return{
      form:{},
    }
  },
  created() {
    const id=this.$route.query.id
    request.get("/admin/"+id).then(res=>{
      this.form=res.data
    })
  },
  methods:{
    save(){
      request.put('/admin/update',this.form).then(res =>{
        if(res.code==='200'){
          this.$notify.success('更新成功');
          this.$router.push("/adminList")
        }
        else{
          this.$notify.error(res.msg)
        }

      })
    },
    back(){
      this.$router.push("/adminList")
    }
  }
}
</script>


<!--el-form不显示-->


