import { Box, Button, Container, TextField, Typography } from "@mui/material"
import { useState } from "react"
import { useSelector } from "react-redux"
import { useNavigate } from "react-router-dom"

import { validateEmail, validatePassword, validateNickname, validateEquals } from "../../utils/Validator"
export default function SignupPage() {

    const { authenticated } = useSelector(state => state.auth)

    const [email, setEmail] = useState("")
    const [emailNotification, setEmailNotification] = useState(false)

    const [nickname, setNickname] = useState("")
    const [nicknameNotification, setNicknameNotification] = useState(false)

    const [password, setPassword] = useState("")
    const [passwordNotification, setPasswordNotification] = useState(false)

    const [passwordConform, setPasswordConfirm] = useState("")
    const [warning, setWarning] = useState(false)
    const navigate = useNavigate()

    // const email = "moraramee@gmail.com"
    // const password = "asdfqwer12!@"
    // const nickname = "MORARAMEE"

    const handleEmailChange = (e) => {
        setEmail(e.target.value)
        if (validateEmail(e.target.value)) {
            setEmailNotification(false)
        } else {
            setEmailNotification(true)
        }
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

    const handlePasswordConfirmChange = (e) => {
        setPasswordConfirm(e.target.value)
        if (password === e.target.value || e.target.value.length === 0) {
            setPasswordNotification(false)
        } else {
            setPasswordNotification(true)
        }
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
        // const data = await API.auth
        //     .signup(email, password, nickname)
        //     .then(() => {
        //         API.auth
        //             .login(email, password)
        //             .then(() => {
        //                 setAuthenticated(true)
        //                 navigate('/')
        //             })
        //             .catch(error => {
        //                 console.log(error)
        //             })
        //         })
        //     .catch(error => {
        //         console.log(error)
        //     })
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
                    Signup
                </Typography>
                <TextField 
                    margin="normal"
                    label="Email"
                    name="email"
                    autoFocus
                    onChange={handleEmailChange} 
                    error={email.length > 0 && !validateEmail(email)}
                    helperText={!validateEmail(email) && "????????? ????????? ?????? ??????????????????."}
                    placeholder="?????????"
                    fullWidth
                />
                <TextField 
                    margin="normal"
                    label="Nickname"
                    name="nickname"
                    onChange={handleNicknameChange}
                    error={nickname.length > 0 && !validateNickname(nickname)}
                    helperText={!validateNickname(nickname) && "???????????? 2~16?????? ??????, ??????, ????????? ??????????????? ?????????."}
                    placeholder="?????????"
                    fullWidth
                />
                <TextField
                    margin="normal"
                    label="Password"
                    name="password"
                    onChange={handlePasswordChange} 
                    error={password.length > 0 && !validatePassword(password)}
                    helperText={!validatePassword(password) && "??????????????? 8~20?????? ????????? ?????? ????????? ??????, ??????, ??????????????? ??????????????? ?????????."}
                    type="password" 
                    placeholder="????????????"
                    fullWidth
                />
                <TextField 
                    margin="normal"
                    label="Password Confirm"
                    name="passwordConfirm"
                    onChange={handlePasswordConfirmChange} 
                    error={!validateEquals(password, passwordConform)}
                    helperText={!validateEquals(password, passwordConform) && "??????????????? ???????????? ????????????."}
                    type="password"
                    placeholder="???????????? ??????"
                    fullWidth
                />
                <Button 
                    type="submit" 
                    onClick={handleSignupClick}
                    fullWidth
                    variant="contained" sx={{ mt:3, mb:2 }}
                    >Sign up</Button>
            </Box>
        </Container>
        </>
    )
}