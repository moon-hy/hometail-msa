import { useContext } from "react"
import { useNavigate } from "react-router-dom"
import { API } from "../../services"
import { AuthContext } from "../../context/AuthContextProvider"

export default function LoginPage() {

    const { setAuthenticated } = useContext(AuthContext)

    const email = "moraramee@gmail.com"
    const password = "asdfqwer12!@"
    
    const navigate = useNavigate()
    const login = (email, password) => {
        API.auth.login(email, password)
        .then(() => {
            setAuthenticated(true)
            navigate('/')
        })
    }

    return (
        <div>
            <span>Login Page.</span>
            <button type="button" onClick={() => login(email, password)}>Join</button>
        </div>
    )
}