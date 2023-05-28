// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'

// 导入ElementUI组件库
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

// 导入路由表
import router from './router/index';

// 全局注册
Vue.use(ElementUI); // 导入ElementUI 

// 引入自定义Axios
import http from './request/request'

Vue.prototype.http = http; // 设置http属性，指向自定义的Axios

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router: router,
  components: { App },
  template: '<App/>'
})
