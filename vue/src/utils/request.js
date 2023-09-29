import axios from 'axios'


const request=axios.create({
    baseURL:'http://localhost:9090', //后台地址
    timeout:5000
})

//request拦截器
//拦截请求
request.interceptors.request.use(
    config=>{
        config.headers['Content-Type']='application/json;charset=utf-8';
        //config.headers['token']=user.token;
        return config
    },
    error => {
        return Promise.reject(error);
    }
);


//response拦截器
request.interceptors.response.use(
    response=>{
        let res=response.data;
        if(typeof res === 'string'){
            res = res? JSON.parse(res) : res
        }
        return res;
    },
    error => {
        console.log('err' + error)
        return Promise.reject(error)
    }

)

export default request




