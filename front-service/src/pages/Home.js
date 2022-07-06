import { API } from "../services";

export default function HomePage() {
    return (
        <div>
            Home Page
            {
                API.auth.check()
                ? <p>Logged in</p>
                : <p>Need to Log in</p>
            }
        </div>
    )
}