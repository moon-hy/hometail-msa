import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { TextField, Button, Box, Typography, Container } from "@mui/material"
import { validateEmail, validatePassword } from "../../utils/Validator"
import { useSelector } from "react-redux"

export default function LoginPage() {

    const { authenticated } = useSelector(state => state.auth)

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

        // if (email.length === 0) {
        //     setWarning("이메일을 입력하세요.")
        // } else if (password.length === 0) {
        //     setWarning("비밀번호를 입력하세요.")
        // } else {
        //     setWarning(false)
        //     await API.auth
        //         .login(email, password)
        //         .then(response => {
        //             setAuthenticated(true)
        //             navigate('/')
        //         })
        //         .catch(error => {
        //             console.log(error)
        //             setWarning("입력한 이메일과 비밀번호를 다시 확인해주세요.")
        //         })
        // }
    }

    return (
        <>
            <Container maxWidth="xs">
                <Box
                    sx={{
                        marginTop: 8,
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center'
                    }}>
                    <Typography component="h1" variant="h5">
                        Login
                    </Typography>
                    <TextField 
                        variant="filled"
                        margin="normal"
                        label="Email" 
                        name="email"
                        autoComplete="email"
                        autoFocus
                        onChange={handleEmailChange}
                        error={email.length > 0 && !validateEmail(email)}
                        helperText={!validateEmail(email) && "이메일 형식에 맞게 입력해주세요."}
                        placeholder="이메일"
                        fullWidth
                    />
                    <TextField 
                        variant="filled"
                        margin="normal"
                        label="Password" 
                        onChange={handlePasswordChange}
                        type="password" 
                        placeholder="비밀번호"
                        fullWidth
                    />
                    <Button 
                        type="submit"
                        fullWidth
                        variant="contained" sx={({ mt:3, mb:2 })}
                        onClick={handleLoginClick}>Join</Button>
                    <div>
                        {warning}
                    </div>
                </Box>
            </Container>
        </>
    )
}