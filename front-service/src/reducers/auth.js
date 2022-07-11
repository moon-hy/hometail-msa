const initialState = {
    authenticated: false,
    id: null,
    role: null,
    nickname: null
}

export default function authReducer(state = initialState, action) {
    switch (action.type) {
        case 'authenticate': {
            return {
                ...state,
                authenticated: true,
                id: 'access_token.id',
                role: 'access_token.role',
                nickname: 'access_tokenn.nickname'
            }
        }
        case 'logout': {
            return {
                ...state,
                authenticated: false,
                id: null,
                role: null,
                nickname: null
            }
        }
        default :
            return state
    }
}