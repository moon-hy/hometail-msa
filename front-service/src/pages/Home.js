import { Box, Button, Typography } from "@mui/material"
import { useEffect, useState } from "react"
import { useDispatch, useSelector } from "react-redux"
import FadeCarousel from "../components/FadeCarousel"
import { IngredientCarousel } from "../components/Ingredient/IngredienCarousel"
import SlideCarousel from "../components/SlideCarousel"
import { API } from "../services"
import { login, logout } from "../services/auth/AuthService"
import { getIngredientList } from "../services/IngredientService"

export default function HomePage() {
    const { authenticated, nickname } = useSelector(state => state.auth)
    
    return (
        <>     
        <IngredientCarousel n={5} />
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