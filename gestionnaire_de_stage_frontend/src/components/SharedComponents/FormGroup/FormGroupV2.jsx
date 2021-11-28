import React from "react";
import PropTypes from "prop-types";

export function FormGroupV2(props) {
    const {children, className} = props;
    return <div className={`form-group row mb-1 mx-auto ${className}`}>{children}</div>
}

FormGroupV2.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string
}
FormGroupV2.defaultProps = {
    className: ''
}

Column.propTypes = {
    children: PropTypes.node,
    col: PropTypes.shape({
        xs: PropTypes.number,
        sm: PropTypes.number,
        md: PropTypes.number,
        lg: PropTypes.number,
        xl: PropTypes.number,
        xxl: PropTypes.number
    }),
    defaultValue: PropTypes.number
}
Column.defaultProps = {
    defaultValue: 12
}

export function Column(props) {
    const {children, col, defaultValue} = props;
    if (!col)
        return <div className={`col-${defaultValue}`}>{children}</div>

    const {xs, sm, md, lg, xl, xxl} = {
        xs: col && col.xs,
        sm: col && col.sm,
        md: col && col.md,
        lg: col && col.lg,
        xl: col && col.xl,
        xxl: col && col.xxl
    };

    return <div
        className={`${xs ? "col-xs-" + xs : ""}${sm ? " col-sm-" + sm : ""}${md ? " col-md-" + md : ""}${lg ? " col-lg-" + lg : ""}${xl ? " col-xl-" + xl : ""}${xxl ? " col-xxl-" + xxl : ""}`}>
        {children}
    </div>
}
