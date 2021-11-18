import PropTypes from "prop-types";

export function ContainerBox(props) {
    const {children, className} = props;
    return (
        <div className={"container bg-dark px-3 py-4 rounded-3 shadow-lg " + className}>
            {children}
        </div>
    );
}

ContainerBox.propTypes = {
    children: PropTypes.node,
    className: PropTypes.string
};
ContainerBox.defaultProps = {
    className: ""
};
