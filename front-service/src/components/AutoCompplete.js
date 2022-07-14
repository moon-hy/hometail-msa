import { Autocomplete, Checkbox, TextField } from "@mui/material";

export default function AutoComplete( {data} ) {

    return (
        <Autocomplete
            options={data}
            getOptionLabel={option => option.name}
            style={{ width: 300 }}
            renderInput={params => (
                <TextField
                {...params}
                label="Combo box"
                variant="outlined"
                fullWidth
                >
                </TextField>
        )}
        />
    )
}