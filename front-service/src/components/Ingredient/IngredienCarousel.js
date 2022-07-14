import { Card, Grid, Typography } from "@mui/material";
import Carousel from "react-material-ui-carousel";
import { useIngredientListState } from "../../utils/Hooks";


export function IngredientCarousel({ n=5 }) {
    const ingredients = useIngredientListState()

    return (
        <IngredientCarouselBase n={n} items={ingredients.slice(0, 20)}/>
    )
}

function IngredientCarouselBase({ n, items }) {

    const parts = []
    for (let i=0; i<items.length; i+=n) {
        parts.push(
            <Grid container spacing={1} key={i.toString()}>
                {
                    items.slice(i, i+n).map((item, index) => 
                    <Grid item key={i+index} xs={12/n}>
                        <IngredientPaper key={i+index} n={n} item={item} />
                    </Grid>
                    )
                }
            </Grid>
        )
    }

    return (
        <Carousel indicators={true} sx={{
            width: '100%',
            height: 'auto',
        }}>
            {
                parts
            }
        </Carousel>
    )
}

function IngredientPaper(props) {
    
    return (
        <Card raised sx={{
            maxHeight: '300px',
            height: `min(${100/props.n}vw, ${900/props.n}px)`,
            margin: 1,
            padding: 1,
        }}>
            <Typography variant="body1" noWrap>{props.item.name}</Typography>
            <Typography>{props.item.id}</Typography>
        </Card>
    )
}