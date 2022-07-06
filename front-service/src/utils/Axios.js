import axios from "axios";

const client = axios.create({
    baseURL: 'http://localhost:8000',
    withCredentials: true,
    timeout: 3000,
    headers: {
        'Content-Type': 'application/json'
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
        console.log(`[ERROR] AxiosInstance: interceptors.request ${error}`)
        return Promise.reject(error)
    }
)

client.interceptors.response.use(
    response => {
        return response
    },
    async error => {
        if (error.response && error.response.status === 401) {
            try {
                const request = error.config;
                const data = await client.get('/auth-service/reissue')
                if (data) {
                    const access_token = data.data.access_token
                    localStorage.removeItem('access_token')
                    localStorage.setItem('accesstoken', data.data.access_token)
                    request.headers['Authorization'] = `Bearer ${access_token}`
                    return await client.request(request)
                }
            } catch (error) {
                localStorage.removeItem('access_token')
                console.log(error)
            }
            return Promise.reject(error)
        }
        console.log(`[ERROR] AxiosInstance: interceptors.response ${error}`)
        return Promise.reject(error)
    }
)

export default client