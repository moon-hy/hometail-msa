import { CssBaseline } from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import React from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import DefaultLayout from './layouts/DefaultLayout';
import LoginPage from './pages/auth/Login';
import LogoutPage from './pages/auth/Logout';
import SignupPage from './pages/auth/Signup';
import HomePage from './pages/Home';

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
  }
})

function App() {

  return (
    <div style={{
      height: '100vh',
      backgroundColor: '#303030'
    }}>
    <ThemeProvider theme={darkTheme}>
      <CssBaseline />
      <BrowserRouter>
        <DefaultLayout>
          <Routes>
            <Route path="/" element={<HomePage/>}></Route>
            <Route path="/login" element={<LoginPage/>}></Route>
            <Route path="/logout" element={<LogoutPage/>}></Route>
            <Route path="/signup" element={<SignupPage/>}></Route>
          </Routes>
        </DefaultLayout>
      </BrowserRouter>
    </ThemeProvider>
    </div>
  )
}

export default App;
