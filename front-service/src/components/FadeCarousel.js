import { Button, Paper } from "@mui/material"
import Carousel from "react-material-ui-carousel"

export default function FadeCarousel() {
    const items = [{
        title: 'First Title',
        description: 'This is the first description.'
    },
    {
        title: 'Second Title',
        description: 'Second Description'
    }]
    return (
        <Carousel indicators={false} height={100} sx={{
            width: '100%'
        }}>
            {
                items.map((item, index) => 
                    <Item key={index} item={item} />
                )
            }
        </Carousel>
    )
}

function Item(props) {
    return (
        <Paper>
            <h2>{props.item.title}</h2>
            <p>{props.item.description}</p>
        </Paper>
    )
}