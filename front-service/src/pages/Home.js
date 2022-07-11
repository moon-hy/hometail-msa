import { Box, Button, Typography } from "@mui/material"
import { useDispatch, useSelector } from "react-redux"
import FadeCarousel from "../components/FadeCarousel"
import SlideCarousel from "../components/SlideCarousel"
import { login, logout } from "../services/auth/AuthService"

export default function HomePage() {
    const { authenticated, nickname } = useSelector(state => state.auth)

    return (
        <>
        <SlideCarousel/>
        {/* <FadeCarousel/> */}
        <Box
            sx={{
                marginTop: 8,
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center'
            }}>

            <Button onClick={() => {
                login()
            }}>Login Test</Button>
            
            <Button onClick={() => {
                logout()
            }}>Logout Test</Button>
            <Typography>
                auth is :
                {nickname}
            </Typography>
        </Box>
        </>
    )
}