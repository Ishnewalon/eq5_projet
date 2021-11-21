// noinspection JSCheckFunctionSignatures

import {cleanup, render, screen} from "@testing-library/react";
import Login from "./Login";
import userEvent from "@testing-library/user-event";
import {useAuth} from "../../services/use-auth";


jest.mock('../SharedComponents/Fields/FieldEmail', () => () => 'myEmailComponents');
jest.mock('../SharedComponents/Fields/FieldPassword', () => () => 'myPasswordComponents');
jest.mock('../../services/use-auth')

afterEach(() => {
    cleanup();
    jest.resetAllMocks();
})
test('Login component', async () => {
    render(<Login/>);

    expect(await screen.findAllByRole('option')).toHaveLength(4)
    expect(screen.getByText("myEmailComponents")).toBeInTheDocument()
    expect(screen.getByText("myPasswordComponents")).toBeInTheDocument()
});

test('call signIn', () => {
    let mockFnSignIn = jest.fn(() => Promise.resolve())
    useAuth.mockReturnValue({
        isAuthenticated: false,
        user: null,
        signIn: mockFnSignIn
    })
    render(<Login/>);

    userEvent.click(screen.getByText("Connexion"));

    expect(mockFnSignIn).toHaveBeenCalled();
})

