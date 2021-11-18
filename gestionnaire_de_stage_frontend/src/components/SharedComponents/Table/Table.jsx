import React, {Children} from "react";
import PropTypes from "prop-types";

export function Table(props) {
    const {children, className} = props;
    let tableRows = [];
    let tableHeader;

    let list = Children.toArray(children) || [];
    if (!list.find(child => child.type.name === "TableHeader"))
        throw new Error("Table must have a TableHeader");

    Children.map(children, child => {
        if (child.type.name === "TableHeader")
            tableHeader = child;
        else
            tableRows.push(child);
    });
    return (
        <table
            className={"table table-light table-striped table-borderless text-center rounded-3 shadow-lg " + className}>
            <thead>
            {tableHeader}
            </thead>
            <tbody>
            {tableRows}
            </tbody>
        </table>
    );
}

Table.propTypes = {
    children: PropTypes.array.isRequired,
    className: PropTypes.string
};

export function TableHeader(props) {
    const {children} = props;
    return <tr>
        {
            React.Children.map(children || [], (child, index) => {
                return React.cloneElement(child,
                    {
                        key: {index},
                        className: "",
                        scope: "col"
                    })
            })
        }
    </tr>
}

TableHeader.propTypes = {
    children: PropTypes.array.isRequired,
};

export function TableRow(props) {
    const {children} = props;
    return <tr>
        {React.Children.map(children, child => {
            if (child.type === 'th') {
                return React.cloneElement(child,
                    {
                        className: "",
                        scope: "row"
                    })
            }
            if (child.type === 'td') {
                return React.cloneElement(child,
                    {
                        className: ""
                    })
            }
        })}
    </tr>
}

TableRow.propTypes = {
    children: PropTypes.array.isRequired,
};
