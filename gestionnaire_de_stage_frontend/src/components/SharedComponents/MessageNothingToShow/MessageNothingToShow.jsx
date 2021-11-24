import PropTypes from "prop-types";
import myIllustration from "../../../assets/images/dawdawda.svg";

export default function MessageNothingToShow(props) {

    const {message} = props;
    return <>
        {/*Bonne<span className="color-emphasis-1"> nouvelle!</span><br/>*/}
        {/*Aucune offre <span className="color-emphasis-1">à valider.</span>*/}
        <div className="d-flex messageEmpty align-items-center justify-content-center">
            <h3 className="mega montserrat bold text-left">
                {message}
            </h3>
            <img src={myIllustration} alt="dawdadad" className="no-select image2 center"/>
        </div>
    </>
}

MessageNothingToShow.propTypes = {
    message: PropTypes.string
};
