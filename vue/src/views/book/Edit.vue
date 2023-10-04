<template>
  <div style="width:100%">
    <div style="margin-left: 30px">
      <h3 style="margin-left:10px; margin-bottom: 30px">编辑分类</h3>
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
      categories:[],
      rules: {
        name: [
          {required: true, message: '请输入图书名称', trigger: 'blur'},
        ],
      },
    }
  },
  created() {
    request.get('/category/tree').then(res=>{
      this.categories=res.data
    })
    const id=this.$route.query.id
    request.get("/book/"+id).then(res=>{
      this.form=res.data
      if(this.form.category){
        this.form.categories=this.form.category.split(' > ')
      }
    })
  },
  methods:{
    save(){
      request.put('/book/update',this.form).then(res =>{
        if(res.code==='200'){
          this.$notify.success('更新成功');
          this.$router.push("/bookList")
        }
        else{
          this.$notify.error(res.msg)
        }
      })
    },
    back(){
      this.$router.push("/bookList")
    },
  }
}
</script>


<!--el-form不显示-->


