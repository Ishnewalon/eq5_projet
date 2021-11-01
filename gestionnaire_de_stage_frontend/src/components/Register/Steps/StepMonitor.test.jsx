import {cleanup, render, screen} from "@testing-library/react";
import StepMonitor, {service} from "./StepMonitor";
import userEvent from "@testing-library/user-event";

let code = "postalll";
let addd = "mon address";
let comp = "name";
let city = "ville";
const mockFnPrev = jest.fn();
const mockFnNext = jest.fn();
const mockFnUpdateType = jest.fn();
const mockFnHandleChange = () => jest.fn((e) => {
    e.preventDefault();
    comp = e.target.value;
});

jest.mock('../../Fields/FieldAddress', () => () => 'myAddressComponents');

beforeEach(() => {
    render(
        <StepMonitor prevStep={mockFnPrev} nextStep={mockFnNext} handleChange={mockFnHandleChange} updateUserType={mockFnUpdateType} codePostal={code}
                     companyName={comp} city={city} address={addd}/>
    );
});
afterEach(() => {
    cleanup();
    jest.resetAllMocks();
})
it('loads and displays StepMonitor', () => {
    expect(screen.getByTestId("companyName")).toBeInTheDocument();
    expect(screen.getByTestId("input-city")).toBeInTheDocument();
    expect(screen.getByTestId("codePostal")).toBeInTheDocument();
    expect(screen.getByText("Suivant")).toBeInTheDocument();
    expect(screen.getByText("Précédent")).toBeInTheDocument();
    expect(screen.getByText("myAddressComponents")).toBeInTheDocument()
    expect(screen.getByTestId("input-city")).toHaveValue(city);
    expect(screen.getByTestId("codePostal")).toHaveValue(code);

});
it('click next', () => {
    service.verification = jest.fn(() => {
        return true;
    });

    expect(mockFnNext).not.toHaveBeenCalled()
    userEvent.click(screen.getByText("Suivant"));

    expect(mockFnNext).toHaveBeenCalled()
    expect(mockFnUpdateType).toHaveBeenCalled()
});
it('click prev', () => {
    expect(mockFnPrev).not.toHaveBeenCalled()
    userEvent.click(screen.getByText("Précédent"));

    expect(mockFnPrev).toHaveBeenCalled()
});

