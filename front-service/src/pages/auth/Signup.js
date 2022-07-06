import { useContext, useState } from "react"
import { useNavigate } from "react-router-dom"
import { AuthContext } from "../../context/AuthContextProvider"
import {API} from "../../services"

import "../../styles/style.css"
export default function SignupPage() {

    const { setAuthenticated } = useContext(AuthContext)

    const [email, setEmail] = useState("")
    const [emailNotification, setEmailNotification] = useState(false)

    const [nickname, setNickname] = useState("")
    const [nicknameNotification, setNicknameNotification] = useState(false)

    const [password, setPassword] = useState("")
    const [passwordNotification, setPasswordNotification] = useState(false)

    const [checkPassword, setCheckPassword] = useState("")
    const [warning, setWarning] = useState(false)
    const navigate = useNavigate()

    // const email = "moraramee@gmail.com"
    // const password = "asdfqwer12!@"
    // const nickname = "MORARAMEE"
    const validateEmail = (string) => {
        return string.match(
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        )
    }

    const handleEmailChange = (e) => {
        setEmail(e.target.value)
        if (validateEmail(e.target.value)) {
            setEmailNotification(false)
        } else {
            setEmailNotification(true)
        }
    }

    const validatePassword = (string) => {
        return string.match(
            /^(?=.*[0-9])(?=.*[A-z])(?=.*[!@#$%^&*()_+{}:"<>?/,./;'[\]|]).{8,20}$/
        )
    }

    const handlePasswordChange = (e) => {
        setPassword(e.target.value)
        if (validatePassword(e.target.value)) {
            setPasswordNotification(false)
        } else {
            setPasswordNotification(true)
        }
        console.log(password)
    }

    const handleCheckPasswordChange = (e) => {
        setCheckPassword(e.target.value)
        if (password === e.target.value || e.target.value.length === 0) {
            setPasswordNotification(false)
        } else {
            setPasswordNotification(true)
        }
    }

    const validateNickname = (string) => {
        return string.match(
            /^[가-힣A-z0-9]{2,16}$/
        )
    }

    const handleNicknameChange = (e) => {
        setNickname(e.target.value)
        if (validateNickname(e.target.value)) {
            setNicknameNotification(false)
        } else {
            setNicknameNotification(true)
        }
    }
    
    const handleSignupClick =  async () => {
        const data = await API.auth
            .signup(email, password, nickname)

        await API.auth
            .login(email, password)
            .then(response => {
                setAuthenticated(true)
                navigate('/')
            })
            .catch(error => {
                console.log(error)
            })
    }

    return (
        <>
            <span>Signup Page.</span>
            <div className="input-container">
                <input onChange={handleEmailChange} type="text" placeholder="이메일"/>
                <input onChange={handleNicknameChange} type="text" placeholder="닉네임"/>
                <input onChange={handlePasswordChange} type="password" placeholder="비밀번호"/>
                <input onChange={handleCheckPasswordChange} type="password" placeholder="비밀번호 확인"/>
                <button type="button" onClick={handleSignupClick}>Sign up</button>
            </div>
        </>
    )
}