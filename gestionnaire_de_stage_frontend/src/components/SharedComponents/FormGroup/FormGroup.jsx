import React from "react";
import PropTypes from "prop-types";

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
        <div className="form-group row mb-3">
            {React.Children.map(children, (child, index) => {
                return (<div className={`col-md-${12 / count}`} key={index}>
                    {child}
                </div>)
            })}
        </div>
    </>
}

FormGroup.propTypes = {
    repartition: PropTypes.arrayOf(PropTypes.number),
    children: PropTypes.node
}
