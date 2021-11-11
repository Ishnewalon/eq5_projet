import React from "react";

export function FormField({children}) {
    if (React.Children.count(children) !== 2)
        throw new Error("FormField must have exactly two child");

    let reactPortals = React.Children.toArray(children);

    let label = reactPortals[0];
    if (label.type !== "label")
        throw new Error("FormField first child must be a label");


    let input = reactPortals[1];
    if (input.type !== "input" && input.type !== "textarea" && input.type !== "select" && input.type !== "button" && input.type !== "radio")
        throw new Error("FormField second child must be an input");

    return <>
        {React.cloneElement(label, {
            className: `${input.props.className ? input.props.className : ""} label`
        })}
        <div className="input-group">
            {React.cloneElement(input, {
                className: `${input.props.className ? input.props.className : ""} form-control`
            })}
        </div>
    </>

}
