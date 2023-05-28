// 封装axios
import Vue from 'vue'
import axios from 'axios'
import VueAxios from 'vue-axios'

import { Message } from 'element-ui'

// 全局注册
Vue.use(VueAxios, axios)

// 创建axios实例对象
const http = axios.create({
    baseURL: 'http://localhost:8080/',
    timeout: 1000 * 3, // 超时时间
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    } // 设置请求头中的的内容
});

// 添加请求拦截器
http.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器
http.interceptors.response.use(function (response) {
    debugger;
    console.log(response)
    // 对响应数据做点什么
    if(response.data.code != 200){ // 错误相应处理
        Message.error(response.data.msg);
    }
    return response;
  }, function (error) {
    // 对响应错误做点什么
    if(response.data.code != 200){ // 错误相应处理
        Message.error(response.data.msg);
    }
    return Promise.reject(error);
  });

// 将该实例导出
export default http;