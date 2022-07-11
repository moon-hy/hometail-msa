export const validateEmail = (string) => {

    return string.match(
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    )
}

export const validatePassword = (string) => {
    return string.match(
        /^(?=.*[0-9])(?=.*[A-z])(?=.*[!@#$%^&*()_+{}:"<>?/,./;'[\]|]).{8,20}$/
    )
}

export const validateNickname = (string) => {
    return string.match(
        /^[가-힣A-z0-9]{2,16}$/
    )
}

export const validateEquals = (string1, string2) => {
    return string1 === string2
}