import {cleanup, render, screen} from "@testing-library/react";
import StepMonitor, {service, verification} from "./StepMonitor";
import userEvent from "@testing-library/user-event";

let myPostalCode = "postalll";
let myAddress = "mon address";
let myCompanyName = "name";
let myCity = "ville";
const mockFnPrev = jest.fn();
const mockFnNext = jest.fn();
const mockFnUpdateType = jest.fn();
const mockFnHandleChange = () => jest.fn((e) => {
    e.preventDefault();
    myCompanyName = e.target.value;
});

jest.mock('../../Fields/FieldAddress', () => () => 'myAddressComponents');

beforeEach(() => {
    render(
        <StepMonitor prevStep={mockFnPrev} nextStep={mockFnNext} handleChange={mockFnHandleChange}
                     updateUserType={mockFnUpdateType} codePostal={myPostalCode}
                     companyName={myCompanyName} city={myCity} address={myAddress}/>
    );
});

afterEach(() => {
    cleanup();
    jest.resetAllMocks();
})

test('loads and displays StepMonitor', () => {
    expect(screen.getByTestId("companyName")).toBeInTheDocument();
    expect(screen.getByTestId("input-city")).toBeInTheDocument();
    expect(screen.getByTestId("codePostal")).toBeInTheDocument();
    expect(screen.getByText("Suivant")).toBeInTheDocument();
    expect(screen.getByText("Précédent")).toBeInTheDocument();
    expect(screen.getByText("myAddressComponents")).toBeInTheDocument()
    expect(screen.getByTestId("input-city")).toHaveValue(myCity);
    expect(screen.getByTestId("codePostal")).toHaveValue(myPostalCode);
    expect(screen.getByTestId("companyName")).toHaveValue(myCompanyName);

});

test('click next', () => {
    service.verification = jest.fn(() => {
        return true;
    });

    expect(mockFnNext).not.toHaveBeenCalled()
    userEvent.click(screen.getByText("Suivant"));

    expect(mockFnNext).toHaveBeenCalled()
    expect(mockFnUpdateType).toHaveBeenCalled()
});

test('click prev', () => {
    expect(mockFnPrev).not.toHaveBeenCalled()
    userEvent.click(screen.getByText("Précédent"));

    expect(mockFnPrev).toHaveBeenCalled()
});

test('verification', () => {
    let myBool = verification('myBigCompagnie', 'mtl', '555 rue blabla', 'H0H0H0');
    expect(myBool).toBeTruthy();
});

test('verification bad postalCode', () => {
    let myBool = verification('myBigCompagnie', 'mtl', '555 rue blabla', 'H0H0H03');
    expect(myBool).toBeFalsy();
    myBool = verification('myBigCompagnie', 'mtl', '555 rue blabla', 'H0H03H');
    expect(myBool).toBeFalsy();
});

test('verification bad companyName', () => {
    let myBool = verification('myBigCompagnie!', 'mtl', '555 rue blabla', 'H0H0H0');
    expect(myBool).toBeFalsy();
});
test('verification bad city', () => {
    let myBool = verification('myBigCompagnie', 'mtl1', '555 rue blabla', 'H0H0H0');
    expect(myBool).toBeFalsy();
});

test('verification undefined CompanyName', () => {
    let myBool = verification(undefined, 'mtl', '555 rue blabla', 'H0H0H0');
    expect(myBool).toBeFalsy();
});

test('verification undefined City', () => {
    let myBool = verification('myBigCompagnie', undefined, '555 rue blabla', 'H0H0H0');
    expect(myBool).toBeFalsy();
});

test('verification undefined Address', () => {
    let myBool = verification('myBigCompagnie', 'mtl', undefined, 'H0H0H0');
    expect(myBool).toBeFalsy();
});

test('verification undefined PostalCode', () => {
    let myBool = verification('myBigCompagnie', 'mtl', '555 rue blabla', undefined);
    expect(myBool).toBeFalsy();
})
