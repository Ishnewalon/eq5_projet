import React from "react";

export function FormField(props) {
    const {children, myFor} = props;
    let childList = React.Children.toArray(children);
    // noinspection JSUnresolvedVariable
    let input = childList.find(p => p.type === "input" || p.type === "textarea" || p.type === "select" || p.type === "button" || p.type === "radio");
    if (!input)
        throw new Error("FormField must have either an input, textarea, select, button or radio");
    // noinspection JSUnresolvedVariable
    let label = childList.find(p => p.type === "label")

    let inputProps = {
        className: `${input.props.className ? input.props.className : ""} form-control`
    }
    let labelProps = {
        className: `${input.props.className ? input.props.className : ""} label text-white`
    }
    if (myFor) {
        inputProps.id = myFor
        labelProps.htmlFor = myFor
    }

    // noinspection JSCheckFunctionSignatures
    return <>
        {label ?
            React.cloneElement(label, labelProps) : null}
        <div className="input-group">
            {React.cloneElement(input, inputProps)}
        </div>
    </>

}
