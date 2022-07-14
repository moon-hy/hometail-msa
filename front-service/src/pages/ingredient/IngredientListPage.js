import { Box, Grid, Typography } from '@mui/material'
import { useLocation } from 'react-router-dom'
import { useIngredientListState } from '../../utils/Hooks'

export default function IngredientListPage(props) {
    const location = useLocation()
    const category = location.state.category
    const ingredients = useIngredientListState({ category: category.id })
    console.log(ingredients)
    return (
        <Typography>
            {/* {ingredients} */}
        </Typography>
    )
}