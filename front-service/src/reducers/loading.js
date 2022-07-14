const initialState = {
    loading: false
}

export default function loadingReducer(state = initialState, action) {
    switch (action.type) {
        case 'loading': {
            return {
                ...state,
                loading: true
            }
        }
        case 'loaded': {
            return {
                ...state,
                loading: false
            }
        }
        default :
            return state
    }
}