import { useEffect, useState } from "react"
import { API } from "../services"

export function useIngredientListState(params) {
    const [ ingredients, setIngredients ] = useState([])
    const [ links, setLinks ] = useState([])

    useEffect(() => {
        (async function() {
            const data = await API.ingredient.getIngredientList(params)
            if (data) {
                console.log(data)
                setIngredients(data.ingredients)
                setLinks(data.links)
            }
        })()
    }, [])

    return ingredients
}

export function useCategoryListState() {
    const [ categories, setCategories ] = useState([])
    const [ links, setLinks ] = useState([])

    useEffect(() => {
        (async function() {
            const data = await API.ingredient.getCategoryList()
            if (data) {
                setCategories(data.categories)
                setLinks(data._links)
            }
        })()
    }, [])

    return [ categories, links ]
}