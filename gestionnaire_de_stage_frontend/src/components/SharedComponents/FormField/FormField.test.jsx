// noinspection JSCheckFunctionSignatures

import {render, screen} from "@testing-library/react";
import {FormField} from "./FormField";

beforeAll(() => {
    jest.spyOn(global.console, 'error').mockImplementation(() => {
    });
});

afterAll(() => {
    global.console.error.mockRestore();
});

test('render', () => {
    render(
        <FormField htmlFor="test">
            <label>test</label>
            <input className="text-secondary" type="text" id="test" name="test" value="test" onChange={() => {
            }}/>
        </FormField>
    );

    expect(screen.getByText('test')).toBeInTheDocument();
    expect(screen.getByLabelText('test')).toBeInTheDocument();
    expect(screen.getByLabelText('test')).toHaveValue('test');
    expect(screen.getByLabelText('test')).toHaveAttribute('name', 'test');
    expect(screen.getByLabelText('test')).toHaveAttribute('class', 'text-secondary form-control mb-1');
    expect(screen.getByText('test')).toHaveAttribute('class', 'label text-white');
})

test('render without label', () => {
    render(
        <FormField htmlFor="test">
            <input data-testid="testId" className="text-secondary" type="text" id="test" name="test" value="test"
                   onChange={() => {
                   }}/>
        </FormField>
    );

    expect(screen.getByTestId('testId')).toBeInTheDocument();
    expect(screen.getByTestId('testId')).toHaveValue('test');
    expect(screen.getByTestId('testId')).toHaveAttribute('name', 'test');
    expect(screen.getByTestId('testId')).toHaveAttribute('class', 'text-secondary form-control mb-1');
})

test('no input', () => {
    expect(() => render(
        <FormField htmlFor="test">
            <label>test</label>
        </FormField>
    )).toThrow('FormField must have either an input, textarea, select, button or radio');
})
