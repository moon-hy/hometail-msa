import { combineReducers } from "@reduxjs/toolkit";
import authReducer from "./auth";
import loadingReducer from "./loading";

const rootReducer = combineReducers({
    auth: authReducer,
    loading: loadingReducer
})

export default rootReducer