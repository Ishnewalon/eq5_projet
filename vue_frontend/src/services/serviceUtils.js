
export const urlBackend = 'http://localhost:8181'
export const methods = {
    POST: 'POST',
    GET: 'GET',
    PUT: 'PUT'
}
export const requestInit = (method, body) => {
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
    if (method === methods.POST || method === methods.PUT)
        value['body'] = JSON.stringify(body)
    return value
}

