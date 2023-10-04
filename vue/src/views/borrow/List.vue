<template>
  <div style="margin-top: 10px;margin-right: 20px;margin-left:10px">
    <!--搜索表单-->
    <div style="margin-bottom: 20px">
      <el-input style="width: 240px" placeholder="请输入图书名称" v-model="params.bookname"></el-input>
      <el-input style="width: 240px;margin-left: 5px" placeholder="请输入ISBN" v-model="params.bookno"></el-input>
      <el-input style="width: 240px;margin-left: 5px" placeholder="请输入用户名称" v-model="params.username"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class=" el-icon-search"></i><span>搜索</span>
      </el-button>
      <el-button style="margin-left: 5px" type="warning" @click="reset"><i
          class=" el-icon-refresh"></i><span>重置</span></el-button>
    </div>

    <!--stripe显示斑马纹-->
    <el-table :data="tableData" stripe row-key="id" default-expand-all>
      <el-table-column prop="id" label="编号" width="80px"></el-table-column>
      <el-table-column prop="bookname" label="图书名称"></el-table-column>
      <el-table-column prop="bookno" label="ISBN"></el-table-column>
      <el-table-column prop="userid" label="用户id"></el-table-column>
      <el-table-column prop="username" label="用户名称"></el-table-column>
      <el-table-column prop="userphone" label="用户联系方式"></el-table-column>
      <el-table-column prop="createtime" label="借书时间"></el-table-column>


      <el-table-column label="操作" align="left" header-align="250px">
        <template v-slot="scope">
        <el-popconfirm
            style="margin-left: 5px"
            title="您确定归还该图书吗？"
            @confirm="del(scope.row.id)"
        >
        <el-button slot="reference" type="success" plain>归还图书</el-button>
        </el-popconfirm>
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


  </div>
</template>

<script>
// @ is an alias to /src
import request from "@/utils/request";
import Cookies from "js-cookie";
import {options} from "axios";

export default {
  name: 'BorrowList',
  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 5,
        bookno:'',
        bookname:'',
        username:'',
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    options,

    load() {
      request.get('/borrow/page', {
        params: this.params
      }).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    reset() {  //重置
      this.params = {
        pageNum: 1,
        pageSize: 5,
        bookno:'',
        bookname:'',
        username:'',
      }
      this.load()
    },
    handleCurrentChange(pageNum) {
      //点击分页按钮触发分页
      this.params.pageNum = pageNum
      this.load()
    },
    del(id) {
      request.delete("/borrow/delete/" + id).then(res => {
        if (res.code === '200') {
          this.$notify.success('还书成功')
          this.load()
        } else {
          this.$notify.error(res.msg)
        }
      })

    },

  }
}
</script>

<style scoped>

</style>