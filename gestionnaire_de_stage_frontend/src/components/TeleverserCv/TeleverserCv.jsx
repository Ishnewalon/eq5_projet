import './TeleverserCv.css'
import React, {useState}  from "react";
import Dropzone from "react-dropzone";


class TeleverserCv extends Component {

    onDrop = acceptedFiles => {
        console.log(acceptedFiles)
    }

    render() {

        return (
            <div>
                <Dropzone
                    onDrop={handleDrop}
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
                                <span>
                                    {isDragActive ? "Déposez votre C.V ici" : "Cliquer moi ou glisser votre C.V. ici"}
                                </span>
                            </div>
                        );
                    }}
                </Dropzone>
                <div className={"text-center"}>
                    <b><h3>Fichiers:</h3></b>
                    <ul>
                        {fileNames.map(fileName => (
                            <li key={fileName}>{fileName}</li>
                        ))}
                    </ul>
                </div>
            </div>
        );

}
