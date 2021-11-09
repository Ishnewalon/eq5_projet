import React from "react";

export function InputGroup({children}) {

    return React.Children.map(children, (child) => {
        if (child.type === "input" || child.type === "textarea" || child.type === "select" || child.type === "button") {
            return <div className="input-group">
                {React.cloneElement(child, {
                    className: `${child.props.className ? child.props.className : ""} form-control`
                })}
            </div>
        }
        return child
    });

}
