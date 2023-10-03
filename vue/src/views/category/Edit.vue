<template>
  <div style="width:500px">
    <div style="margin-left: 30px">
      <h3 style="margin-left:10px; margin-bottom: 30px">编辑分类</h3>
      <div>
        <el-form :inline="true" :model="form" :rules="rules" ref="ruleForm" label-width="100px">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入名称" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>


          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" placeholder="请输入备注" style="width: 200px;margin-left: 10px"></el-input>
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
  name: 'EditCategory',
  data(){
    return{
      form:{},
      rules: {
        name: [
          {required: true, message: '请输入分类名称', trigger: 'blur'},
        ],
      },
    }
  },
  created() {
    const id=this.$route.query.id
    request.get("/category/"+id).then(res=>{
      this.form=res.data
    })
  },
  methods:{
    save(){
      request.put('/category/update',this.form).then(res =>{
        if(res.code==='200'){
          this.$notify.success('更新成功');
          this.$router.push("/categoryList")
        }
        else{
          this.$notify.error(res.msg)
        }

      })
    },
    back(){
      this.$router.push("/categoryList")
    }
  }
}
</script>


<!--el-form不显示-->


