import { useState, createContext } from "react"

const initialAuthContext = {
    authenticated: false,
    setAuthenticated: () => {}
}

export const AuthContext = createContext(initialAuthContext)

const AuthContextProvider = ({ children }) => {
    const prevToken = localStorage.getItem('access_token') || null
    const [authenticated, setAuthenticated] = useState(prevToken != null)

    return (
        <AuthContext.Provider value={{authenticated, setAuthenticated}}>
            {children}
        </AuthContext.Provider>
    )
}

export default AuthContextProvider;