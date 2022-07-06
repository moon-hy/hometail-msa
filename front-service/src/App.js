import { Route, Routes, BrowserRouter } from "react-router-dom"

import "./styles/Container.css"
import HomePage from "./pages/Home.js";
import AboutPage from "./pages/About.js";
import LoginPage from "./pages/auth/Login";
import SignupPage from "./pages/auth/Signup";
import LogoutPage from "./pages/auth/Logout";
import DefaultLayout from "./layouts/DefaultLayout";
import AuthContextProvider from "./context/AuthContextProvider";
import ProfilePage from "./pages/auth/Profile";

export default function App() {

    return (
        <BrowserRouter>
            <AuthContextProvider>
                <DefaultLayout>
                    <Routes>
                        <Route path="/" element={<HomePage />} />
                        <Route path="/about" element={<AboutPage />} />
                        <Route path="/login" element={<LoginPage />} />
                        <Route path="/signup" element={<SignupPage />} />
                        <Route path="/logout" element={<LogoutPage />} />
                        <Route path="/profile" element={<ProfilePage />} />
                    </Routes>
                </DefaultLayout>
            </AuthContextProvider>
        </BrowserRouter>
    )
}
