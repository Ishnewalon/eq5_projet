import React from "react";
import PropTypes from "prop-types";

export function FormGroup(props) {
    const {children, className} = props;
    return <div className={`form-group row mb-2 mx-auto ${className}`}>{children}</div>
}

FormGroup.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string
}
FormGroup.defaultProps = {
    className: ''
}
