<template>
  <div>
    <!--搜索表单-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="请输入名称"></el-input>
      <el-input style="margin-left:5px; width: 240px" placeholder="请输入联系方式"></el-input>
      <el-button style="margin-left: 5px" type="primary">
        <i class=" el-icon-search"></i>
        <span>搜索</span>
      </el-button>
    </div>
    <!--stripe显示斑马纹-->
    <el-table :data="tableData" stripe>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="age" label="年龄"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="phone" label="联系方式"></el-table-column>
      <el-table-column prop="sex" label="性别"></el-table-column>
    </el-table>

    <!--分页-->
    <div style="margin-top: 20px">
      <el-pagination
          background
          :current-page="params.pageNum"
          :page-size="params.pageSize"
          layout="prev, pager, next"
          :total="total">
      </el-pagination>
    </div>

  </div>

</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

import request from "@/utils/request";

export default {
  name: 'HomeView',
  data(){
    return{
      tableData:[],
      total:0,
      params:{
        pageNum:1,
        pageSize:10,
        name:'',
        phone:'',
      }
    }
  },
  created() {
    this.load();//调用load方法，完成数据的渲染
  },
  methods:{
    load(){
      // fetch('http://localhost:9090/user/list')
      //     .then(res => res.json()).then(res=>{
      //       console.log(res)
      //       this.tableData=res
      //     })

      //get-post-fetch-delete
      request.get('/user/page',{
        params:this.params
      }).then(res=>{

        if(res.code==="200"){
          this.tableData=res.data.list
          this.total=res.data.total
        }
      })
    }
  }

}
</script>
