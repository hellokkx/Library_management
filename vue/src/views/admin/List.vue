<template>
  <div style="margin-top: 10px;margin-right: 20px;margin-left:10px">
    <!--搜索表单-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="请输入用户名" v-model="params.username"></el-input>
      <el-input style="margin-left:5px; width: 240px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
      <el-input style="margin-left:5px; width: 240px" placeholder="请输入邮箱" v-model="params.email"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load">
        <i class=" el-icon-search"></i>
        <span>搜索</span>
      </el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset">
        <i class=" el-icon-refresh"></i>
        <span>重置</span>
      </el-button>
    </div>
    <!--stripe显示斑马纹-->
    <el-table :data="tableData"stripe>
      <el-table-column prop="id" label="编号" width="70"></el-table-column>
      <el-table-column prop="username" label="管理员"></el-table-column>
      <el-table-column prop="phone" label="联系方式"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间"></el-table-column>

      <el-table-column label="操作" width="250">
        <template v-slot="scope">
          <!--          scope.row 就是当前行数据-->
          <el-button type="primary" @click="$router.push('/editAdmin?id='+scope.row.id)">编辑</el-button>

          <el-popconfirm
            style="margin-left: 10px"
            title="您确定删除这行数据吗？"
            @confirm="del(scope.row.id)"
        >
          <el-button slot="reference" type="danger">删除</el-button>
        </el-popconfirm>

          <el-button style="margin-left: 10px" type="warning" @click="handlechangePass(scope.row)">修改密码</el-button>


        </template>
      </el-table-column>

    </el-table>

    <div style="margin-top: 20px">
      <el-pagination
          background
          :current_page="params.pageNum"
          :page-size="params.pageSize"
          layout="prev, pager, next"
          @current-change="handleCurrentChange"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="修改密码" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" :inline="true" label-width="100px" :rules="rules" ref="formRef">
        <el-form-item label="新密码" prop="newPass">
          <el-input v-model="form.newPass" autocomplete="off" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="savePass">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
// @ is an alias to /src
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: 'AdminList',
  data(){
    return{
      admin:Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
      tableData:[],
      total:0,
      form:{},
      dialogFormVisible:false,
      params:{
        pageNum:1,
        pageSize:10,
        username:'',
        phone:'',
        email:''
      },
      rules:{
        newPass:[
          {required:true,message:"请输入新密码",trigger:'blur'},
          {min:3, max:10,message:"长度在3-10个字符内",trigger:'blur'},
        ],
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    handlechangePass(row){
      this.form=JSON.parse(JSON.stringify(row))
      this.dialogFormVisible=true
    },
    savePass(){
      this.$refs['formRef'].validate((valid)=>{
        if(valid){
          request.put('/admin/password',this.form).then(res=>{
            if(res.code === '200'){
              if(this.form.id===this.admin.id){//当前修改的用户id等于当前登录的管理员id，修改成功之后重新登录
                this.$notify.success("修改成功,请重新登录")
                Cookies.remove('admin')
                this.$router.push('/login')
              }
              else{
                this.$notify.success("修改成功")
                this.load()
                this.dialogFormVisible=false
              }
            }
            else{
              this.$notify.error("修改失败")
            }
          })
        }
      })
    },
    load(){
      request.get('/admin/page',{
        params: this.params
      }).then(res=>{
        if(res.code === '200'){
          this.tableData=res.data.list
          this.total=res.data.total
        }
      })
    },
    reset(){  //重置
      this.params={
        pageNum:1,
        pageSize:10,
        username:'',
        phone:'',
        email:''
      }
      this.load()
    },
    handleCurrentChange(pageNum){
      //点击分页按钮触发分页
      this.params.pageNum=pageNum
      this.load()
    },
    del(id){
      request.delete("/admin/delete/"+id).then(res =>{
        if(res.code==='200'){
          this.$notify.success('删除成功')
          this.load()
        }
        else{
          this.$notify.error(res.msg)
        }
      })
    }

  }
}
</script>

<style scoped>

</style>