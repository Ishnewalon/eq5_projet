import React from "react";

export function Table(props) {
    const {children, className} = props;
    let tableRows = [];
    let tableHeader;
    if (!React.Children.toArray(children).find((child) => child.type.name === "TableHeader"))
        throw new Error("Table must have a TableHeader");

    React.Children.toArray(children).forEach(child => {
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


export function TableHeader(props) {
    const {children} = props;
    return <tr>
        {
            React.Children.map(children, (child, index) => {
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
