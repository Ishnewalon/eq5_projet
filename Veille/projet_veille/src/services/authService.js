export const urlBackend = 'http://localhost:8181'


export async function registerStudent(etudiant) {
    const response = await fetch(`${urlBackend}/student/signup`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(etudiant)
    })
    return await response.json();
}

export async function registerMonitor(monitor) {
    const response = await fetch(`${urlBackend}/monitor/signup`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(monitor)
    })
    return await response.json();
}

export async function registerSupervisor(supervisor) {
    const response = await fetch(`${urlBackend}/supervisor/signup`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(supervisor)
    })
    return await response.json();
}