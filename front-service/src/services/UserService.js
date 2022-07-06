import client from "../utils/Axios"

async function profile() {
    const response = await client.get('/user-service/profile')
    .then((r) => {
        console.log(r)
    }).catch((e) => {
        console.log(e)
    })
    return response
}

export const user = {
    profile,
}