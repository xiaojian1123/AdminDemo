import Vue from 'vue';
import router from './router';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Ajax from './assets/js/ajax';
import './assets/js/comm';
import App from './App';
import Vuex from 'vuex';
import store from './vuex/store';

Vue.config.productionTip = false;
Vue.use(ElementUI, {size: 'small'});
Vue.use(Vuex);
Vue.prototype.$ajax = Ajax;
Vue.prototype.$utils = {
  utcFormat: function (__utc, __formatStr) {
    return Boolean(__utc) ? new Date(__utc).format(Boolean(__formatStr) ? __formatStr : 'yyyy-MM-dd hh:mm:ss') : '';
  }
};

new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>',
  store,
  render: h => h(App)
});
