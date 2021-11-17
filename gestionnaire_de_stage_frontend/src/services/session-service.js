import {methods, requestInit, urlBackend} from "./serviceUtils";
import {toast, toastErr} from "../utility";

export async function createSession(body) {
    return await fetch(`${urlBackend}/sessions`, requestInit(methods.POST,body)).then(
        response => {
            return response.json().then((body) => {
                if (response.status === 200)
                    toast.fire({title: "Session créée!"})
                if (response.status === 400)
                    toastErr.fire({title: body.message})
            })
        }, err => console.error(err)
    );
}
export async function getCurrentAndFutureSession() {
    return await fetch(`${urlBackend}/sessions`, requestInit(methods.GET)).then(
        response => {
            return response.json().then((body) => {
                if (response.status === 200)
                    return body
                if (response.status === 400)
                    toastErr.fire({title: body.message})
            })
        }, err => console.error(err)
    );
}
