import { useContext, useState } from "react"
import { useNavigate } from "react-router-dom"
import { API } from "../../services"
import { AuthContext } from "../../context/AuthContextProvider"

export default function LoginPage() {

    const { setAuthenticated } = useContext(AuthContext)

    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [warning, setWarning] = useState(false)
    const navigate = useNavigate()


    // const email = "moraramee@gmail.com"
    // const password = "asdfqwer12!@"
    
    const handleEmailChange = (e) => {
        setEmail(e.target.value)
    }

    const handlePasswordChange = (e) => {
        setPassword(e.target.value)
    }

    const handleEnter = (e) => {
        if (e.key === 'Enter') handleLoginClick()
    }

    const handleLoginClick = async () => {

        if (email.length === 0) {
            setWarning("이메일을 입력하세요.")
        } else if (password.length === 0) {
            setWarning("비밀번호를 입력하세요.")
        } else {
            setWarning(false)
            await API.auth
                .login(email, password)
                .then(response => {
                    setAuthenticated(true)
                    navigate('/')
                })
                .catch(error => {
                    console.log(error)
                    setWarning("입력한 이메일과 비밀번호를 다시 확인해주세요.")
                })
        }
    }

    return (
        <>
            <span>Login Page.</span>
            <div className="input-container">
                <input onChange={handleEmailChange} type="text" placeholder="이메일" />
                <input onChange={handlePasswordChange} type="password" placeholder="비밀번호" />
                <button type="submit" onClick={handleLoginClick}>Join</button>
                <div>
                    {warning}
                </div>
            </div>
        </>
    )
}