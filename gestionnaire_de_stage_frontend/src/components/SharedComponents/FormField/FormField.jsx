import React from "react";

export function FormField({children}) {
    let reactPortals = React.Children.toArray(children);
    let input
    if (reactPortals.find(p => p.type === "input" || p.type === "textarea" || p.type === "select" || p.type === "button" || p.type === "radio")) {
        input = reactPortals.find(p => p.type === "input" || p.type === "textarea" || p.type === "select" || p.type === "button" || p.type === "radio");
    } else {
        throw new Error("FormField must have either an input, textarea, select, button or radio");
    }
    let label
    if (reactPortals.find(p => p.type === "label")) {
        label = reactPortals.find(p => p.type === "label");
    }


    return <>
        {label ?
            React.cloneElement(label, {
                className: `${input.props.className ? input.props.className : ""} label`
            }) : null}
        <div className="input-group">
            {React.cloneElement(input, {
                className: `${input.props.className ? input.props.className : ""} form-control`
            })}
        </div>
    </>

}
