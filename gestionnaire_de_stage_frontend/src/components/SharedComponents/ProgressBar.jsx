import React, {cloneElement} from "react";
import PropTypes from "prop-types";

export function ProgressBar(props) {
    const {currentStep, totalSteps} = props;
    let list = [];
    for (let i = 0; i < totalSteps; i++) {
        let position = i / totalSteps;
        list.push(
            <div className="ps-step"
                 style={{left: position + "%", transitionDuration: "300ms", transform: "translateX(-50%) scale(1)"}}>
                <div className={"ps-indexedStep" + (currentStep >= i ? " accomplished" : "")}>{i + 1}</div>
            </div>
        )
    }
    return (
        <div className="d-flex justify-content-center mb-3">
            <div className="w-75 position-relative">
                <div className="ps-progressBar">
                    {list.map((step, index) =>
                        cloneElement(step, {key: index}))}
                    <div className="ps-progression" style={{width: getPercentage(currentStep, totalSteps) + "%"}}/>
                </div>
            </div>
        </div>
    );
}

ProgressBar.propTypes = {
    currentStep: PropTypes.number,
    totalSteps: PropTypes.number,
};
const getPercentage = (currentStep, total) => {
    if (currentStep === 0)
        return 0
    else
        return (currentStep / (total - 1)) * 100
};
