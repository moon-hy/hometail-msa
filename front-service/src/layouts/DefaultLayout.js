import { Box, Container } from "@mui/material";
import SearchAppBar from "../components/Navigation";

function DefaultLayout({ children }) {

    return (
        <>
        <SearchAppBar/>
        <Box sx={{ 
            maxWidth: 900, 
            marginTop: 3,
            marginX: 'auto',
        }}>
            { children }
        </Box>
        </>
    )
}

export default DefaultLayout