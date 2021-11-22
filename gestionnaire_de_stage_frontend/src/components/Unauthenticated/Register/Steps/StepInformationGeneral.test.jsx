// noinspection DuplicatedCode

import userEvent from "@testing-library/user-event";
import {cleanup, render, screen} from "@testing-library/react";
import StepInformationGeneral, {verification} from "./StepInformationGeneral";


let firstName = "MonPrenom";
let lastName = "MonNom";
let phone = "5556667777";
let email = "test@email.com";
const mockFnPrev = jest.fn();
const mockFnNext = jest.fn();
const mockFnHandleChange = () => jest.fn((e) => {
    e.preventDefault();
});

jest.mock('../../../SharedComponents/Fields/FieldAddress', () => () => 'myAddressComponents');

beforeEach(() => {
    render(
        <StepInformationGeneral prevStep={mockFnPrev} nextStep={mockFnNext} handleChange={mockFnHandleChange}
                                firstName={firstName} lastName={lastName} phone={phone} email={email}/>
    );
});

afterEach(() => {
    cleanup();
    jest.resetAllMocks();
})

test('click next', () => {
    userEvent.click(screen.getByText("Suivant"));

    expect(mockFnNext).toHaveBeenCalled()
});

test('click prev', () => {
    userEvent.click(screen.getByText("Précédent"));

    expect(mockFnPrev).toHaveBeenCalled()
});

test('verification', () => {
    let myBool = verification('monPrenom', 'MonNom', 'dwdaa@email.com', '5555555555');
    expect(myBool).toBeTruthy();
});

test('verification bad postalCode', () => {
    let myBool = verification('monPrenom', 'MonNom', 'dwdaa@email.com', '55555555556');
    expect(myBool).toBeFalsy();
    myBool = verification('monPrenom', 'MonNom', 'dwdaa@email.com', '555555555');
    expect(myBool).toBeFalsy();
});

test('verification bad FirstName', () => {
    let myBool = verification('monP!renom', 'MonNom', 'dwdaa@emailcom', '5555555555');
    expect(myBool).toBeFalsy();
    myBool = verification('monPrenom1', 'MonNom', 'dwdaaATemail.com', '5555555555');
    expect(myBool).toBeFalsy();
});
test('verification bad LastName', () => {
    let myBool = verification('monPrenom', 'MonN@om', 'dwdaa@email.com', '5555555555');
    expect(myBool).toBeFalsy();
    myBool = verification('monPrenom', 'MonN1om', 'dwdaa@email.com', '5555555555');
    expect(myBool).toBeFalsy();
});

test('verification undefined FirstName', () => {
    let myBool = verification(undefined, 'MonNom', 'dwdaa@email.com', '5555555555');
    expect(myBool).toBeFalsy();
});

test('verification undefined LastName', () => {
    let myBool = verification('monPrenom', 'myBigCompagnie', undefined, 'dwdaa@email.com', '5555555555');
    expect(myBool).toBeFalsy();
});

test('verification undefined email', () => {
    let myBool = verification('monPrenom', 'MonNom', undefined, '5555555555');
    expect(myBool).toBeFalsy();
});

test('verification undefined Phone', () => {
    let myBool = verification('monPrenom', 'MonNom', 'dwdaa@email.com', undefined);
    expect(myBool).toBeFalsy();
})
