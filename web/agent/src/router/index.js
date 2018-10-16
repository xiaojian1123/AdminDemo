import Vue from 'vue'
import Router from 'vue-router'
import Login from '../components/login/Index';
import Index from '../components/index/Index';
import Home from '../components/index/Home';
import Operator from '../components/index/operator/operator/Index';
import OperatorRole from '../components/index/operator/role/Index';
import OperatorLog from '../components/index/operator/log/Index';

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: Login
    },
    {
      path:'/index',
      component:Index,
      children:[
        {
          path:'home',
          component:Home,
          meta:{
            id:0
          }
        },
        {
          path:'operator/operator',
          component:Operator,
          meta:{
            id:'1-1'
          }
        },
        {
          path:'operator/role',
          component:OperatorRole,
          meta:{
            id:'1-2'
          }
        },
        {
          path:'operator/log',
          component:OperatorLog,
          meta:{
            id:'1-3'
          }
        }
      ]
    }
  ]
})
