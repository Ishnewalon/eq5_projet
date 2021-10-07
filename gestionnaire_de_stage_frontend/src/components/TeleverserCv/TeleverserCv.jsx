import './TeleverserCv.css'
import React, {Component} from "react";
import Dropzone from "react-dropzone";


class TeleverserCv extends Component {

    onDrop = acceptedFiles => {
        console.log(acceptedFiles)
    }

    render() {

        return (
            <div className="App">
                <Dropzone
                    onDrop={this.onDrop}
                    accept="application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                    minSize={1024}
                    maxSize={3072000}
                >
                    {({
                          getRootProps,
                          getInputProps,
                          isDragActive,
                          isDragAccept,
                          isDragReject
                      }) => {
                        const additionalClass = isDragAccept
                            ? "accept"
                            : isDragReject
                                ? "reject"
                                : "";
                        return (
                            <div
                                {...getRootProps({
                                    className: `dropzone ${additionalClass}`
                                })}
                            >
                                <input {...getInputProps()} />
                                <span>{isDragActive ? "Déposez votre C.V ici" : "Cliquer moi ou glisser votre C.V. ici"}</span>
                            </div>
                        );
                    }}
                </Dropzone>
            </div>
        );
    }
        // return (
        //     <Dropzone onDrop={this.onDrop} accept="application/pdf, application/vnd.openxmlformats-officedocument.wordprocessingml.document">
        //         {({getRootProps, getInputProps, isDragActive, isDragReject, isDragAccept }) => (
        //             <div {...getRootProps()}>
        //                 <input {...getInputProps()}/>
        //                 {!isDragActive && "Cliquer moi ou glisser votre C.V. ici"}
        //                 {isDragActive && isDragAccept && "Déposez votre C.V ici"}
        //                 {isDragActive && isDragReject && "Ce format de fichier n'est pas accepté"}
        //             </div>
        //         )}
        //     </Dropzone>
        // );
}
export default TeleverserCv