import axios from "axios";
import client from "../utils/Axios";


async function getIngredientList(params) {
    const response = await axios.get(
            `http://localhost:8180/ingredient-service/ingredients`,
            {
                params: {
                    "category": params?.category
                }
            }
        )
        .then(response => response.data)
        .catch(error => {throw error})
    return response
}

async function getCategoryList() {
    const response = await axios.get("http://localhost:8180/ingredient-service/categories")
        .then(response => response.data)
        .catch(error => {throw error})
    return response
}

export const ingredient = {
    getIngredientList,
    getCategoryList,
}