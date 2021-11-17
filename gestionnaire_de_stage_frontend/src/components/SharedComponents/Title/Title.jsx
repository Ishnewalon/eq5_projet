import React from "react";

export function Title(props) {
    return React.createElement(
        "h1",
        {className: "title text-center mb-3"},
        props.children
    );

}
