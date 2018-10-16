import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    // 定义状态
    state: {
        device: '',
        routers:''
    },
    mutations:{
        setDevice(state,msg){
            state.device = msg
        },
        PushRouter(state,msg){
            state.routers = msg
        }
    }
})

export default store
