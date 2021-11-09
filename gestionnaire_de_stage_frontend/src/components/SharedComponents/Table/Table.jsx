import React from "react";

export function Table(props) {
    const {children, className} = props;
    return <table
        className={"table table-light table-striped table-borderless text-center rounded-3 shadow-lg " + className}>
        {children}
    </table>
}
