// noinspection JSCheckFunctionSignatures

import {cleanup, render, screen} from "@testing-library/react";
import Choice from "./StepChoices";
import userEvent from "@testing-library/user-event";
import {Step} from "../../../../enums/Steps";

const mockFnNext = jest.fn();
beforeEach(() => {
    render(
        <Choice nextStep={mockFnNext}/>
    );
});

afterEach(() => {
    cleanup();
    jest.resetAllMocks();
})

test('renders without crashing', () => {
   expect(screen.getByTestId('cegep')).toBeInTheDocument()
   expect(screen.getByTestId('company')).toBeInTheDocument()
});

test('click cegep',()=>{
    userEvent.click(screen.getByTestId('cegep'));

    expect(mockFnNext).toHaveBeenCalledWith(Step.CEGEP);
});
test('click company',()=>{
    userEvent.click(screen.getByTestId('company'));

    expect(mockFnNext).toHaveBeenCalledWith(Step.MONITOR);
});
