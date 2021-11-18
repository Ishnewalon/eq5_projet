import React, {Children, cloneElement} from "react";
import PropTypes from "prop-types";


export function FormField(props) {
    const {children, htmlFor} = props;
    let childList = Children.toArray(children) || [];

    let input = childList.find(children => children.type === "input" || children.type === "textarea" || children.type === "select" || children.type === "button" || children.type === "radio");
    if (!input)
        throw new Error("FormField must have either an input, textarea, select, button or radio");
    let label = childList.find(p => p.type === "label")

    let inputProps = {
        className: `${input.props.className ? input.props.className : ""} form-control`
    }
    let labelProps = {
        className: `${input.props.className ? input.props.className : ""} label text-white`
    }
    if (htmlFor) {
        inputProps.id = htmlFor
        labelProps.htmlFor = htmlFor
    }

    return <>
        {label ?
            cloneElement(label, labelProps) : null}
        <div className="input-group">
            {cloneElement(input, inputProps)}
        </div>
    </>

}

FormField.propTypes = {
    htmlFor: PropTypes.string,
    children: PropTypes.node
}
