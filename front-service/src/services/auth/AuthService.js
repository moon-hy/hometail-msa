import { store } from "../../app/store"
import client from "../../utils/Axios"

const { dispatch } = store

export function login(email, password) {
    const data = {
        email, password
    }

    // const response = client.post('/auth-service/login', data)
    localStorage.setItem('access_token', 'TEST')
    dispatch({type: "authenticate"})
}

export function logout() {

    // const response = client.delete('/auth-service/logout')
    localStorage.removeItem('access_token')
    dispatch({type: "logout"})
}