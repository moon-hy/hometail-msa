import axios from "axios";
import { store } from "../app/store";

const { dispatch } = store

const client = axios.create({
    baseURL: 'http://localhost:8000',
    withCredentials: true,
    timeout: 2000,
    headers: {
        'Content-Type': 'application/json'
    },
    validateStatus: function (status) {
        return status < 500
    }
})

client.interceptors.request.use(
    config => {
        const access_token = localStorage.getItem('access_token')
        if (!access_token) {
            return config
        }
        config.headers['Authorization'] = `Bearer ${access_token}`
        return config
    },
    error => {
        console.log(`[Error] utils.Axios.js: client.interceptors.request ${error}`)
        return Promise.reject(error)
    }
)

client.interceptors.response.use(
    response => {
        return response
    },
    error => {
        if (error.response && error.response.status === 401) {
            const request = error.config;
            const response = client.get('/auth-service/reissue')
                .catch(error => {
                    localStorage.removeItem('access_token')
                    dispatch({type: "logout"})
                    return Promise.reject(error)
                })
                
            const access_token = response.data.access_token
            localStorage.removeItem('access_token')
            localStorage.setItem('access_token', access_token)
            request.headers['Authorization'] = `Bearer ${access_token}`
            return client.request(request)
        }
        return Promise.reject(error)
    }
)

export default client