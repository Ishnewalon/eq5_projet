import React from "react";
import PropTypes from "prop-types";

export function FormGroupV2(props) {
    const children = props.children;
    return <div className="form-group row mb-3 mx-auto">{children}</div>
}

FormGroupV2.propTypes = {
    children: PropTypes.node
}

Col.propTypes = {
    children: PropTypes.node,
    col: PropTypes.shape({
        sm: PropTypes.number,
        md: PropTypes.number,
        lg: PropTypes.number,
        xl: PropTypes.number,
    }),
    default: PropTypes.number
}

function Col(props) {
    const {children, col, default: defaultCol} = props;
    const {sm, md, lg, xl} = col || {sm: defaultCol, md: defaultCol, lg: defaultCol, xl: defaultCol};
    return <div className={`col-sm-${sm} col-md-${md} col-lg-${lg} col-xl-${xl}`}>
        {children}
    </div>
}
