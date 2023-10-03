import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Layout from '../views/Layout.vue'
import Cookies from "js-cookie";


Vue.use(VueRouter)

const routes = [
    //----------------------1_Login-------------------------------
  {
    path: '/login',
    name: 'Login',
    component: ()=>import('@/views/login/Login.vue'),
  },
    //------------------------1_Layout-------------------------
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:'/home',
    children:[
      //------------------------2_HOME-------------------------
      {path: 'home', name: 'Home', component: HomeView},
      // ---------------------2_User-----------------------------
      {path: 'userList', name: 'UserList', component: () => import('@/views/user/User.vue')},
      {path: 'addUser', name: 'AddUser', component: () => import('@/views/user/AddUser.vue')},
      {path: 'editUser', name: 'EditUser', component: () => import('@/views/user/EditUser.vue')},

      // ---------------------2_Admin-----------------------------
      {path: 'adminList', name: 'AdminList', component: () => import('@/views/admin/List.vue')},
      {path: 'addAdmin', name: 'AddAdmin', component: () => import('@/views/admin/Add.vue')},
      {path: 'editAdmin', name: 'EditAdmin', component: () => import('@/views/admin/Edit.vue')},

      // ---------------------2_Category-----------------------------
      {path: 'categoryList', name: 'CategoryList', component: () => import('@/views/category/List.vue')},
      {path: 'addCategory', name: 'AddCategory', component: () => import('@/views/category/Add.vue')},
      {path: 'editCategory', name: 'EditCategory', component: () => import('@/views/category/Edit.vue')},

      // ---------------------2_Book-----------------------------
      {path: 'bookList', name: 'BookList', component: () => import('@/views/book/List.vue')},
      {path: 'addBook', name: 'AddBook', component: () => import('@/views/book/Add.vue')},
      {path: 'editBook', name: 'EditBook', component: () => import('@/views/book/Edit.vue')},

    ]
  },
  {
    path:"*",
    component:()=>import('@/views/404.vue')
  }

]



const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  if(to.path==='/login') next()
  const admin=Cookies.get("admin")
  if(!admin && to.path!=='/login') return next("/login") //强制退回到登录页面
  next()
})



export default router
