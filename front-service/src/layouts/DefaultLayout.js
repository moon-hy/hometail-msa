import { useContext, useEffect } from "react";
import { NavLink, Link } from "react-router-dom";
import { API } from "../services";
import { AuthContext } from "../context/AuthContextProvider";
import "../styles/DefaultLayout.css"

const defaultNavItems = [
    { path: "/" ,       title: "Home"},
    { path: "/about",   title: "About"},
]

const nonAuthNavItems = [
    { path: "/login" ,  title: "Login"},
    { path: "/signup" , title: "Signup"},
]

const authNavItems = [
    { path: "/profile", title: "Profile"},
    { path: "/logout",  title: "Logout"}
]

export default function DefaultLayout({ children }) {
    const { authenticated, setAuthenticated } = useContext(AuthContext)

    useEffect(() => {
        const data = API.auth.check()
        if (data) {
            setAuthenticated(true)
        }
    }, [authenticated, setAuthenticated])

    const navItems = authenticated
    ? defaultNavItems.concat(authNavItems)
    : defaultNavItems.concat(nonAuthNavItems)

    const navList = navItems.map(
        (item, idx) =>
        <li key={idx}>
            <NavLink to={item.path}>
                {item.title}
            </NavLink>
        </li>
    )

    return (
        <>
        <nav className="nav">
            <Link to="/" className="nav-title">
                Site
            </Link>
            <ul>
                {navList}
            </ul>
        </nav>
        <div className="container">
            {children}
        </div>
        </>
    );

}