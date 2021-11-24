import PropTypes from "prop-types";
import myIllustration from "../../../assets/images/dawdawda.svg";
import style from "./MessageNothingToShow.module.css";

export default function MessageNothingToShow(props) {
    const {message} = props;
    return <>
        {/*Bonne<span className="color-emphasis-1"> nouvelle!</span><br/>*/}
        {/*Aucune offre <span className="color-emphasis-1">à valider.</span>*/}
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
