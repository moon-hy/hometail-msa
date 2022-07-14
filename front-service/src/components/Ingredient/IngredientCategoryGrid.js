import { Button, Card, Grid, Typography } from "@mui/material";
import { API } from "../../services";
import { useCategoryListState } from "../../utils/Hooks";
import { NavLink, Link } from "react-router-dom"

export default function IngredientCategoryGrid({ n=4 }) {
    const [ categories, links ] = useCategoryListState()

    const handleCategoryOnClick = (e) => {
        alert(e)
    }

    return (
        <Grid container spacing={1} justifyContent="center">
        {
                categories.map((category, index) => 
                    <Grid key={category.id} item xs={12/categories.length} >
                        <Link 
                            to='/ingredients/list' 
                            state={{category: category}}
                        >
                            <Card key={category.name} raised sx={{
                                height: `min(${100/n}vw, ${900/n}px)`,
                                margin: 1,
                                padding: 1,
                            }}
                            // onClick={() => handleCategoryOnClick(category.id)}
                            >
                                <Typography>
                                    {category.name}
                                </Typography>
                            </Card>
                        </Link>
                    </Grid>
                )
        }
        </Grid>
    )
}