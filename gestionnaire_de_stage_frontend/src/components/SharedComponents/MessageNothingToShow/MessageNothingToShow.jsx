import PropTypes from "prop-types";

export default function MessageNothingToShow(props) {

    const {message} = props;
    return <div
        className={'bg-secondary d-flex py-3 align-items-center justify-content-center text-white'}>{message}</div>
}

MessageNothingToShow.propTypes = {
    message: PropTypes.string
};
