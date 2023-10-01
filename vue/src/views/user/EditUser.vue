<template>
  <div style="width:500px">
    <div style="margin-left: 30px">
      <h3 style="margin-left:10px; margin-bottom: 30px">编辑用户</h3>
      <div>
        <el-form :inline="true" :model="form" label-width="100px">
          <el-form-item label="会员卡号">
            <el-input v-model="form.username" disabled style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="form.name" placeholder="请输入姓名" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>
          <el-form-item label="年龄">
            <el-input v-model="form.age" placeholder="请输入年龄" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>

          <el-form-item label="性别">
            <el-select v-model="form.sex" placeholder="请选择性别" style="width: 200px;margin-left: 10px">
              <el-option
                  v-for="item in sexoption"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="联系方式">
            <el-input v-model="form.phone" placeholder="请输入电话" style="width: 200px;margin-left: 10px"></el-input>
          </el-form-item>
          <el-form-item label="地址">
            <el-input v-model="form.address" placeholder="请输入地址" style="width: 200px;margin-left: 10px"></el-input>
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
  name: 'EditUser',
  data(){
    return{
      form:{},
      sexoption: [{
        value: '男',
        label: '男'
      }, {
        value: '女',
        label: '女'
      }],
    }
  },
  created() {
    const id=this.$route.query.id
    request.get("/user/"+id).then(res=>{
      this.form=res.data
    })
  },
  methods:{
    save(){
      request.put('/user/update',this.form).then(res =>{
        if(res.code==='200'){
          this.$notify.success('更新成功');
          this.$router.push("/userList")
        }
        else{
          this.$notify.error(res.msg)
        }

      })
    },
    back(){
      this.$router.push("/userList")
    }
  }
}
</script>


<!--el-form不显示-->


