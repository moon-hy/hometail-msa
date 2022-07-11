import * as React from 'react';
import { useSelector } from "react-redux"
import { styled } from '@mui/material/styles'
import { 
    Divider,
    Grid,
    List, ListItem, ListItemButton, ListItemIcon, ListItemText,
    IconButton,
    Drawer,
    Box,
} from '@mui/material';
import { Menu, Home, Info, LocalBar, Kitchen, Login, PersonAdd, Logout, AccountCircle } from '@mui/icons-material';
import { NavLink } from 'react-router-dom';

const Footer = styled('div')(({ theme }) => ({
    display: 'flex',
    width: 'inherit',
    position: 'fixed',
    bottom: 0,
    textAlign: 'center',
    paddingBottom: 10,
}))

export const mainMenu = [
    { path: '/', title: 'Home', icon: <Home/> },
    { path: '/about', title: 'About', icon: <Info/> },
    { path: '/cocktails', title: 'Cocktails', icon: <LocalBar/> },
    { path: '/ingredients', title: 'Ingredients', icon: <Kitchen/> }
]

export const footerMenu = [
    { path: '/login', title: 'Login', icon: <Login />, requireAuth: false},
    { path: '/signup', title: 'Signup', icon: <PersonAdd />, requireAuth: false},
    { path: '/logout', title: 'Logout', icon: <Logout />, requireAuth: true},
    { path: '/profile', title: 'Profile', icon: <AccountCircle />, requireAuth: true},
]

const ListItemActiveButton = styled(ListItemButton)(({ theme }) => ({
    '&.active': {
        backgroundColor: 'lightGray',
        color: 'black',
    }
}))

export default function ResponsiveDrawer() {
    const [state, setState] = React.useState(false);
    const { authenticated } = useSelector(state => state.auth)

    const toggleDrawer = (flag) => (event) => {
        if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }
        setState(flag);
    };

    const list = () => (
        <Box
            sx={{ width: 250, height: '100%' }}
            role="presentation"
            onClick={toggleDrawer(false)}
            onKeyDown={toggleDrawer(false)}
            // backgroundColor={'#192544'}/
        >
            <List>
                <ListItem key={"menu"}>
                    <ListItemIcon>
                        <Menu />
                    </ListItemIcon>
                    <ListItemText primary={ 'MENU' }/>
                </ListItem>
            </List>
            <Divider />
            <List>
                {mainMenu.map((item, index) => (
                    <ListItem key={item.title} disablePadding>
                        <ListItemActiveButton component={ NavLink } to={ item.path }>
                            <ListItemIcon sx={{ color: 'inherit' }}>
                                { item.icon }
                            </ListItemIcon>
                            <ListItemText primary={ item.title } />
                        </ListItemActiveButton>
                    </ListItem>
                ))}
            </List>
            <Footer>
            <Grid
                container
                direction="row"
                justifyContent="center"
                alignItems="center"
            >
                {footerMenu.map((item, index) => (
                    item.requireAuth === authenticated ?
                    <Grid item key={item.title} xs={6}>
                        <ListItem disablePadding>
                            <ListItemActiveButton component={ NavLink } to={ item.path }>
                                <ListItemIcon>
                                    { item.icon } { item.title }
                                </ListItemIcon>
                            </ListItemActiveButton>
                        </ListItem>
                    </Grid> : 
                    null
                ))}
            </Grid>
            </Footer>
        </Box>
    );

    return (
        <div>
            <IconButton
                size="large"
                edge="start"
                color="inherit"
                onClick={toggleDrawer(true)}
                sx={{ mr: 2, display:{ xs:'flex', md:'none'} }}
            >
                <Menu />
            </IconButton>
            <Drawer
                anchor="left"
                open={state}
                onClose={toggleDrawer(false)}
            >
                {list("left")}
            </Drawer>
        </div>
    );
}
