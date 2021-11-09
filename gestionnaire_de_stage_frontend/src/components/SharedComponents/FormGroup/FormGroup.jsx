import React from "react";

export function FormGroup(props) {
    const children = props.children;
    const {repartition} = props;

    let count = React.Children.count(children);

    if (repartition)
        return <>
            <div className="form-group row">
                {React.Children.map(children, (child, index) => {
                    return (<div className={`col-md-${repartition[index]}`} key={index}>
                        {child}
                    </div>)
                })}
            </div>
        </>
    return <>
        <div className="form-group row">
            {React.Children.map(children, (child, index) => {
                return (<div className={`col-md-${12 / count}`} key={index}>
                    {child}
                </div>)
            })}
        </div>
    </>
}
