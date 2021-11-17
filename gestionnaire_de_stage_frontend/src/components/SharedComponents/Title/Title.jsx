import React from "react";
import PropTypes from "prop-types";

export function Title(props) {
    return React.createElement(
        "h1",
        {className: "title text-center mb-3"},
        props.children
    );
}
Title.propTypes = {
    children: PropTypes.any.isRequired,
};
