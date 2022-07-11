import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { logout } from "../../services/auth/AuthService";

export default function LogoutPage() {
    const dispatch = useDispatch()
    const { authenticated } = useSelector(state => state.auth)
    const navigate = useNavigate()

    useEffect(() => {
        logout()
        navigate('/')
    }, [])
}