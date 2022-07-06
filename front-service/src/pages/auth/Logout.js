import { useContext, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { AuthContext } from "../../context/AuthContextProvider"
import {API} from "../../services"
export default function LogoutPage() {

    const { setAuthenticated } = useContext(AuthContext)
    const navigate = useNavigate()
    
    useEffect(() => {
        API.auth.logout()
        setAuthenticated(false)
        navigate("/")
    }, [])
}