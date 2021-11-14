// noinspection JSCheckFunctionSignatures,DuplicatedCode

import {cleanup, render, screen} from "@testing-library/react";
import StepCegep from "./StepCegep";
import userEvent from "@testing-library/user-event";
import {UserType} from "../../../enums/UserTypes";

let matricule = 12345;
const mockFnPrev = jest.fn();
const mockFnNext = jest.fn();
const mockFnUpdateType = jest.fn();
const mockFnHandleChange = () => jest.fn((e) => {
    e.preventDefault();
});
let myRender = (matricule) =>
    render(
        <StepCegep prevStep={mockFnPrev} nextStep={mockFnNext} updateUserType={mockFnUpdateType}
                   handleChange={mockFnHandleChange} matricule={matricule}/>
    );
beforeEach(() => {
    matricule = 12345;
});

afterEach(() => {
    cleanup();
    jest.resetAllMocks();
})

test('loads and displays StepCegep', () => {
    myRender(matricule);

    expect(screen.getByLabelText("Matricule")).toBeInTheDocument();
    expect(screen.getByTestId("input-matricule")).toBeInTheDocument();
    expect(screen.getByText("Suivant")).toBeInTheDocument();
    expect(screen.getByText("Précédent")).toBeInTheDocument();
    expect(screen.getByTestId("input-matricule")).toHaveValue(matricule);
});

test('click next but nothing happen', () => {
    matricule = "";
    myRender(matricule);

    userEvent.click(screen.getByText("Suivant"));

    expect(mockFnNext).not.toHaveBeenCalled()
    expect(mockFnUpdateType).not.toHaveBeenCalled()
});
test('click next Supervisor', () => {
    myRender(matricule);

    userEvent.click(screen.getByText("Suivant"));

    expect(mockFnNext).toHaveBeenCalled()
    expect(mockFnUpdateType).toHaveBeenCalledWith(UserType.SUPERVISOR)
});
test('click next Student', () => {
    matricule = 1234567;
    myRender(matricule);


    userEvent.click(screen.getByText("Suivant"));

    expect(mockFnNext).toHaveBeenCalled()
    expect(mockFnUpdateType).toHaveBeenCalledWith(UserType.STUDENT)
});

test('click prev', () => {
    myRender(matricule);

    userEvent.click(screen.getByText("Précédent"));

    expect(mockFnPrev).toHaveBeenCalled()
});


