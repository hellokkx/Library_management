<template>
  <div style="margin-top: 10px;margin-right: 20px;margin-left:10px">
    <!--搜索表单-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="请输入分类名称" v-model="params.name"></el-input>
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
    <el-table :data="tableData" stripe row-key="id" default-expand-all>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column prop="createtime" label="创建时间"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间"></el-table-column>

      <el-table-column label="操作" width="250" align="left" header-align="50px">
        <template v-slot="scope">
          <!--          scope.row 就是当前行数据-->
          <el-button type="primary" @click="$router.push('/editCategory?id='+scope.row.id)">编辑</el-button>

          <el-popconfirm
            style="margin-left: 5px"
            title="您确定删除这行数据吗？"
            @confirm="del(scope.row.id)"
        >
          <el-button slot="reference" type="danger">删除</el-button>
        </el-popconfirm>
          <el-button type="success" v-if="!scope.row.pid" @click="handleAdd(scope.row)" style="margin-left:5px">添加二级分类</el-button>


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


    <el-dialog title="添加二级分类" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" :inline="true" label-width="100px" :rules="rules" ref="formRef">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    

  </div>
</template>

<script>
// @ is an alias to /src
import request from "@/utils/request";
import Cookies from "js-cookie";

export default {
  name: 'CategoryList',
  data(){
    return{
      admin:Cookies.get('admin')?JSON.parse(Cookies.get('admin')):{},
      tableData:[],
      total:0,
      dialogFormVisible:false,
      form:{},
      pid:null,
      params:{
        pageNum:1,
        pageSize:3,
        name:'',
      },
      rules:{
        name:[
          {required:true,message:"请输入分类名称",trigger:'blur'},
        ],
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    
    load(){
      request.get('/category/page',{
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
        pageSize:3,
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
      request.delete("/category/delete/"+id).then(res =>{
        if(res.code==='200'){
          this.$notify.success('删除成功')
          this.load()
        }
        else{
          this.$notify.error(res.msg)
        }
      })
    },
    handleAdd(row){
      //将当前行的id作为二级分类的pid
      this.pid=row.id
      this.dialogFormVisible=true
    },
    save() {
      this.$refs['formRef'].validate((valid) => {
        if (valid) {
          //给二级分类赋值pid
          this.form.pid=this.pid
          request.post('/category/save', this.form).then(res => {
            if (res.code === '200') {
              this.$notify.success('新增二级分类成功');
              this.form={}
              this.dialogFormVisible=false
              this.load()
            } else {
              this.$notify.error(res.msg)
            }
          })
        }
      })
    },

  }
}
</script>

<style scoped>

</style>