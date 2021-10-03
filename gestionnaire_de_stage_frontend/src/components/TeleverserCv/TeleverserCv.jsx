import './TeleverserCv.css'
import React, {Component} from "react";
import Dropzone from "react-dropzone";


class TeleverserCv extends Component {

    onDrop = acceptedFiles => {
        console.log(acceptedFiles)
    }

    render() {

        return (
            <Dropzone onDrop={this.onDrop} >
                {({getRootProps, getInputProps, isDragActive}) => (
                    <div {...getRootProps()}>
                        <input {...getInputProps()}/>
                        {isDragActive ? "Déposez votre C.V ici" : "Cliquer moi ou glisser votre C.V. ici"}


                    </div>
                )}
            </Dropzone>
        );
    }
}
export default TeleverserCv