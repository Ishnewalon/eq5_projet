import PropTypes from "prop-types";
import myIllustration from "../../../assets/images/EmptyListImg.svg";
import style from "./MessageNothingToShow.module.css";

export default function MessageNothingToShow(props) {
    const {message} = props;
    return <>
        <div className={`d-flex align-items-center justify-content-center ${style.nothingToShow}`}>
            <h3 className={style.title}>
                {message}
            </h3>
            <img src={myIllustration} alt="dawdadad" className={style.image2}/>
        </div>
    </>
}

MessageNothingToShow.propTypes = {
    message: PropTypes.string
};
