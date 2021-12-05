export const urlBackend = 'http://localhost:8181'
export const methods = {
    POST: 'POST',
    GET: 'GET',
    PUT: 'PUT',
    DELETE: 'DELETE'
}
export const requestInit = (method, body, isString) => {
    let value = {
        method: method,
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    }

    if (body && (method === methods.POST || method === methods.PUT)) {
        if (isString)
            value['body'] = body
        else
            value['body'] = JSON.stringify(body)
    }
    return value
}

