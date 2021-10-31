import {cleanup, render, screen} from "@testing-library/react";
import StepMonitor, {service} from "./StepMonitor";
import userEvent from "@testing-library/user-event";

let code = "dawdwa";
let addd = "ddddd";
let comp = "dadwdawdawdad";
let city = "dawdawd";
const prev = jest.fn();
const next = jest.fn();
const handle = () => jest.fn((e) => {
    e.preventDefault();
    console.log(e.target.value)
    comp = e.target.value;
});
const updatee = jest.fn();

jest.mock('../../Fields/FieldAddress', () => () => 'myAddressComponents');
beforeEach(() => {
    render(
        <StepMonitor prevStep={prev} nextStep={next} handleChange={handle} updateUserType={updatee} codePostal={code}
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
    userEvent.type(screen.getByTestId("companyName"), "test");
    expect(screen.getByTestId("companyName")).toHaveValue("test");

    expect(cityInput).toBeInTheDocument();
    expect(postalCodeInput).toBeInTheDocument();
    expect(cityInput.value).toBe(city);
    expect(postalCodeInput.value).toBe(code);
    expect(btnNext).toBeInTheDocument();
    expect(btnPrev).toBeInTheDocument();
    expect(screen.getByText("myAddressComponents")).toBeInTheDocument()
    // fireEvent.click(btnPrev);
    expect(prev).toHaveBeenCalled()
    // expect(prev).toHaveBeenCalled()
    // expect(handle).toHaveBeenCalled()
    // expect(updatee).toHaveBeenCalled()
});

