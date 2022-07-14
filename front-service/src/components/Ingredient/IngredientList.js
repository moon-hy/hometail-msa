import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography } from "@mui/material";
import { useIngredientListState } from "../../utils/Hooks";

export default function IngredientList() {

    const ingredients = useIngredientListState()

    return (
        <TableContainer component={Paper}>
            <Table sx={{ minWidth: 'xs' }}>
                <TableHead>
                    <TableRow>
                        <TableCell>#</TableCell>
                        <TableCell align="right">Name</TableCell>
                        <TableCell align="right">ABV</TableCell>
                        <TableCell align="right">Representation</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {ingredients.map((ingredient, index) => (
                        <TableRow 
                            key={ingredient.name}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                {ingredient.id}
                            </TableCell>
                            <TableCell align="right">{ingredient.name}</TableCell>
                            <TableCell align="right">{ingredient.alcohol_by_volume}</TableCell>
                            <TableCell align="right">{ingredient.representation}</TableCell>

                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}