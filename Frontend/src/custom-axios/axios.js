import axios from "axios";

const instance = axios.create({
    baseURL: 'http://localhost:9090/api',
    header: {
        "Access-Control-Allow-Origin" : '*',
        "Access-Control-Allow-Methods" : "GET,PUT,POST,DELETE,PATCH,OPTIONS"
    },
    withCredentials: false
})

export default instance;