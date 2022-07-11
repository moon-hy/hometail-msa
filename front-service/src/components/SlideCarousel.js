import { Box, Paper } from "@mui/material";
import Slider from "react-slick";

export default function SlideCarousel() {

    const items = [{
        title: 'First Title',
        description: 'This is the first description.'
    },
    {
        title: 'Second Title',
        description: 'Second Description'
    }]

    const settings = {
        dots: true,
        infinite: true,
        speed: 500,
        slidesToShow: 1,
        slidesToScroll: 1,
    }
    
    return (
        <Slider {...settings}>
            {
                items.map((item, index) => {
                    <Item key={index} item={item}/>  
                })
            }
        </Slider>
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