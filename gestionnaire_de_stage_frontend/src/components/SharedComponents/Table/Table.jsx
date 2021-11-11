import React from "react";

export function Table(props) {
    const {children, className, thList} = props;
    return (
        <table
            className={"table table-light table-striped table-borderless text-center rounded-3 shadow-lg " + className}>
            <thead>
            <tr>
                {thList.map((fieldName, index) => {
                    return <th key={index} scope="col">{fieldName}</th>
                })}
            </tr>
            </thead>
            <tbody>
            {React.Children.map(children, (child) => {
                if (child.type === "tr") {
                    return <TableRow>
                        {child.props.children}
                    </TableRow>
                }
            })}
            </tbody>
        </table>
    );
}

function TableRow(props) {
    const {children} = props;
    return <tr>
        {React.Children.map(children, (child, index) => {
            if (child.type === 'th') {
                return React.cloneElement(child,
                    {
                        key: {index},
                        className: "",
                        scope: "row"
                    })
            }
            if (child.type === 'td') {
                return React.cloneElement(child,
                    {
                        key: {index},
                        className: ""
                    })
            }
        })}
    </tr>
}
