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

    <div style="margin-top: 20px">
      <el-pagination
          background
          :page-size="5"
          layout="prev, pager, next"
          :total="100">
      </el-pagination>
    </div>

  </div>

</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
    data(){
      return{
        tableData:[
          {name:'王楚钦',age:23,address:'北京市',phone: '131',sex:'男'},
          {name:'王楚钦',age:23,address:'北京市',phone: '136623412',sex:'男'},
          {name:'王楚钦',age:23,address:'北京市',phone: '136623412',sex:'男'},
          {name:'王楚钦',age:23,address:'北京市',phone: '136623412',sex:'男'},
        ]
      }
    }
}
</script>
