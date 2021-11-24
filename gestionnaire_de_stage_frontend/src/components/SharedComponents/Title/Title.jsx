import React from "react";
import PropTypes from "prop-types";

export function Title(props) {
    const {header, children} = props;
    return React.createElement(
        header,
        {className: "text-light title text-center mb-3"},
        children
    );
}

Title.propTypes = {
    header: PropTypes.string,
    h2: PropTypes.bool,
    children: PropTypes.node.isRequired
};
Title.defaultProps = {
    header: "h1"
};
