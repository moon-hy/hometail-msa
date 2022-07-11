import {
    AppBar,
    Button,
    InputBase,
    Toolbar,
    Box,
    Typography,
    Divider,
} from '@mui/material'
import { styled, alpha } from '@mui/material/styles'
import React from 'react'
import SearchIcon from '@mui/icons-material/Search'
import { footerMenu, mainMenu } from './ResponsiveDrawer'
import { useSelector } from 'react-redux'
import { NavLink } from 'react-router-dom'
import ResponsiveDrawer from './ResponsiveDrawer'

const ActiveButton = styled(Button)(({ theme }) => ({
    color: 'lightGray',
    marginX: 0,
    '&.active': {
        color: 'white',
        textDecorationLine: 'underline',
        textUnderlineOffset: '5px',
        fontWeight: 'bold',
    },
    '&:hover': {
        color: 'white',
    },
}))

const Search = styled('div')(({ theme }) => ({
    position: 'relative',
    borderRadius: theme.shape.borderRadius,
    backgroundColor: alpha(theme.palette.common.white, 0.15),
    '&:hover': {
        backgroundColor: alpha(theme.palette.common.white, 0.25),
    },
    marginLeft: 0,
    width: 'auto',
    [theme.breakpoints.up('xs')]: {
        marginLeft: 0,
        width: 'auto',
    },
}))

const SearchIconWrapper = styled('div')(({ theme }) => ({
    padding: theme.spacing(0, 2),
    height: '100%',
    position: 'absolute',
    pointerEvents: 'none',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
}))

const StyledInputBase = styled(InputBase)(({ theme }) => ({
    color: 'inherit',
    '& .MuiInputBase-input': {
        padding: theme.spacing(1, 1, 1, 0),
        // vertical paddign + font size from searchIcon
        paddingLeft: `calc(1em + ${theme.spacing(4)})`,
        transition: theme.transitions.create('width'),
        width: '100%',
        [theme.breakpoints.up('xs')]: {
            width: '12ch',
            '&:focus': {
                width: '20ch',
            },
        },
    },
}))

export default function SearchAppBar() {
    const { authenticated } = useSelector((state) => state.auth)

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <ResponsiveDrawer />
                    <Typography
                        variant="h6"
                        noWrap
                        component={ NavLink }
                        to="/"
                        sx={{
                            textDecoration: 'none',
                            color: 'white',
                            flexGrow: 1,
                            display: { xs: 'block', sm: 'block' },
                        }}
                    >
                        TITLE
                    </Typography>
                    <Box sx={{ display: { xs: 'none', md: 'flex' } }}>
                        {mainMenu.map((item) => (
                            <ActiveButton
                                component={NavLink}
                                to={item.path}
                                key={item.title}
                            >
                                {item.title}
                            </ActiveButton>
                        ))}
                        <Divider
                            orientation="vertical"
                            flexItem
                            sx={{
                                borderColor: 'gray',
                            }}
                        />
                        {footerMenu.map((item) =>
                            item.requireAuth === authenticated ? (
                                <ActiveButton
                                    component={NavLink}
                                    to={item.path}
                                    key={item.title}
                                >
                                    {item.title}
                                </ActiveButton>
                            ) : null
                        )}
                    </Box>
                    <Search>
                        <SearchIconWrapper>
                            <SearchIcon />
                        </SearchIconWrapper>
                        <StyledInputBase
                            placeholder="Search…"
                            inputProps={{ 'aria-label': 'search' }}
                        />
                    </Search>
                </Toolbar>
            </AppBar>
        </Box>
    )
}
