import ElementUI from 'element-ui';
import Axios from 'axios';
import Qs from 'qs';

let axios = Axios.create({
    baseURL: 'http://127.0.0.1:8880/',
    headers: {'X-Requested-With': 'XMLHttpRequest'},
    transformRequest: [function (data) {
        //在data当中存在数组的话需要加上{arrayFormat: 'brackets'} 否则提交时数组会显示下标
        data = Qs.stringify(data,{arrayFormat: 'brackets'});
        return data;
    }],
    withCredentials: true
});

export default {
    get: function (__url, __params) {
        return new Promise((__resolve, __reject) => {
            let params = typeof __params === 'object' ? __params : '';
            axios.get(__url, {params: params}).then((__response) => {
                let resultData = __response.data;
                switch (resultData.code) {
                    case 401:
                        ElementUI.Message({
                            message: '登录超时，请重新登录',
                            type: 'error',
                            onClose: function () {
                                window.sessionStorage.removeItem('admin');
                                window.location.reload();
                            }
                        });
                        break;
                    case 500:
                        ElementUI.Message.error(resultData.msg);
                        break;
                    case 200:
                        __resolve({msg: resultData.msg, result: resultData.result});
                        break;
                    case 501:
                        __reject(resultData.msg);
                        break;
                }
            }).catch((__error) => {
                console.error(__error);
                ElementUI.Message.error('请求出现异常，请稍后重试');
            });
        });
    },
    post: function (__url, __params) {
        return new Promise((__resolve, __reject) => {
            axios.post(__url, __params).then((__response) => {
                let resultData = __response.data;
                switch (resultData.code) {
                    case 401:
                        ElementUI.Message({
                            message: '登录超时，请重新登录',
                            type: 'error',
                            onClose: function () {
                                window.sessionStorage.removeItem('admin');
                                window.location.reload();
                            }
                        });
                        break;
                    case 500:
                        ElementUI.Message.error(resultData.msg);
                        break;
                    case 200:
                        __resolve({msg: resultData.msg, result: resultData.result});
                        break;
                    case 501:
                        __reject(resultData.msg);
                        break;
                }
            }).catch((__error) => {
                console.error(__error);
                ElementUI.Message.error('请求出现异常，请稍后重试');
            });
        });
    }
}
