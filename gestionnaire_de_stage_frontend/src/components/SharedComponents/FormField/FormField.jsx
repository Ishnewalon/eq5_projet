import React, {Children, cloneElement} from "react";
import PropTypes from "prop-types";


export function FormField(props) {
    const {children, htmlFor} = props;
    let childList = Children.toArray(children) || [];

    let input = childList.find(children => children.type === "input" || children.type === "textarea" || children.type === "select" || children.type === "button" || children.type === "radio");
    if (!input)
        throw new Error("FormField must have either an input, textarea, select, button or radio");
    let label = childList.find(p => p.type === "label")
    let span = childList.find(p => p.type === "span")
    let inputProps = {
        className: `${input.props.className ? input.props.className + " " : ""}${input.type === 'select' ? "form-select" : "form-control"}${span ? " mb-1" : ""}`,
    }
    let labelProps;
    if (label) {
        labelProps = {
            className: `${label.props.className ? input.props.className + " " : ""}label`
        };
        if (htmlFor) {
            inputProps.id = htmlFor
            labelProps.htmlFor = htmlFor
        }
    }
    return <>
        <div className="form-floating">
            {cloneElement(input, inputProps)}
            {label ?
                cloneElement(label, labelProps) : null}
        </div>
        {span ? cloneElement(span, {className: "text-danger mb-3"}) : null}
    </>

}

FormField.propTypes = {
    htmlFor: PropTypes.string,
    children: PropTypes.node
}
