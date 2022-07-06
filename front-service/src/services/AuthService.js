import client from "../utils/Axios"

async function login(email, password) {
    const data = {
        email,
        password
    }

    const response = await client.post('/auth-service/login', data)
    .then(response => {
        localStorage.setItem('access_token', response.data.data.access_token)
    }).catch(error => {
        throw error
    })

    return response
}

async function logout() {
    localStorage.removeItem('access_token')
}

async function signup(email, password, nickname) {
    const data = {
        email,
        password,
        nickname
    }

    const response = await client.post('/auth-service/signup', data)
    .then(response => {
        console.log(response.data)
    }).catch(error => {
        throw error
    })
    
    return response
}

function check() {
    return localStorage.getItem('access_token')
}

export const auth = {
    login,
    signup,
    check,
    logout,
}