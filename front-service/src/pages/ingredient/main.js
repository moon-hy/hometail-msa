import { TableContainer, Table, TableHead, TableRow, TableCell, Paper, Autocomplete, TextField } from "@mui/material";
import AutoComplete from "../../components/AutoCompplete";
import IngredientCategoryGrid from "../../components/Ingredient/IngredientCategoryGrid";
import IngredientList from "../../components/Ingredient/IngredientList";
import { useIngredientListState } from "../../utils/Hooks";

export default function IngrdientPage() {
    const ingredients = useIngredientListState()

    return (
        <>
        <IngredientCategoryGrid />
        <AutoComplete data={ingredients} />
        <IngredientList />
        </>
    )
}