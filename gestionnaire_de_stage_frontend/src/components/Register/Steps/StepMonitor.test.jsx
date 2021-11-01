import {cleanup, render, screen} from "@testing-library/react";
import StepMonitor, {service} from "./StepMonitor";
import userEvent from "@testing-library/user-event";

let code = "postalll";
let addd = "mon address";
let comp = "name";
let city = "ville";
const prev = jest.fn();
const next = jest.fn();
const updateType = jest.fn();
const handle = () => jest.fn((e) => {
    e.preventDefault();
    console.log(e.target.value)
    comp = e.target.value;
});

jest.mock('../../Fields/FieldAddress', () => () => 'myAddressComponents');
beforeEach(() => {
    render(
        <StepMonitor prevStep={prev} nextStep={next} handleChange={handle} updateUserType={updateType} codePostal={code}
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

    expect(next).not.toHaveBeenCalled()
    userEvent.click(screen.getByText("Suivant"));

    expect(next).toHaveBeenCalled()
    expect(updateType).toHaveBeenCalled()
});
it('click prev', () => {
    expect(prev).not.toHaveBeenCalled()
    userEvent.click(screen.getByText("Précédent"));

    expect(prev).toHaveBeenCalled()
});

