import {API} from "../../services"
export default function SignupPage() {

    const email = "moraramee@gmail.com"
    const password = "asdfqwer12!@"
    const nickname = "MORARAMEE"

    return (
        <div>
            <span>Signup Page.</span>
            <button type="button" onClick={() => API.auth.signup(email, password, nickname)}>Sign up</button>
        </div>
    )
}