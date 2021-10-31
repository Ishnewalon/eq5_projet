import {cleanup, render, screen} from "@testing-library/react";
import StepMonitor, {service} from "./StepMonitor";
import userEvent from "@testing-library/user-event";

let code = "postalll";
let addd = "mon address";
let comp = "name";
let city = "ville";
const prev = jest.fn();
const next = jest.fn();
const handle = () => jest.fn((e) => {
    e.preventDefault();
    console.log(e.target.value)
    comp = e.target.value;
});
const updateType = jest.fn();

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
it('loads and displays StepMonitor', async () => {
    service.verification = jest.fn(() => {
        return true;
    });
    let cityInput = screen.getByTestId("input-city");
    let postalCodeInput = screen.getByTestId("codePostal");
    let btnNext = screen.getByText("Suivant");
    let btnPrev = screen.getByText("Précédent");
    expect(screen.getByTestId("companyName")).toBeInTheDocument();

    expect(cityInput).toBeInTheDocument();
    expect(postalCodeInput).toBeInTheDocument();
    expect(cityInput).toHaveValue(city);
    expect(postalCodeInput).toHaveValue(code);
    expect(btnNext).toBeInTheDocument();
    expect(btnPrev).toBeInTheDocument();
    expect(screen.getByText("myAddressComponents")).toBeInTheDocument()
    userEvent.click(btnPrev);
    expect(prev).toHaveBeenCalled()
    userEvent.click(btnNext);
    expect(next).toHaveBeenCalled()
    expect(updateType).toHaveBeenCalled()
});

