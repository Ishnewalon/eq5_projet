export const urlBackend = 'http://localhost:8181'


export async function registerStudent(etudiant) {
    const response = await fetch(`${urlBackend}/student/signup`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(etudiant)
    })
    return await response.json();
}