// 配置路由规则
import Vue from 'vue'
import VueRouter from 'vue-router'

// 全局注册VueRouter组件
Vue.use(VueRouter)

// 1. 定义路由组件.
import index from "../view/index"
import emp from "../view/emp/emp"
import role from "../view/role/role"
import menu from "../view/menu/menu"
import login from "../view/login"

// 2. 定义一些路由 配置映射关系
const routes = [
    {
        path: '/index', component: index, children: [
            { path: '/emp', component: emp },
            { path: '/role', component: role },
            { path: '/menu', component: menu },
        ]
    },
    { path: '/', component: login },
    { path: '/login', component: login },
]

// 3. 创建路由实例并传递 `routes` 配置
export default new VueRouter({
    routes // es6对象简写
})

