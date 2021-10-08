import './TeleverserCv.css'
import React, {useState} from "react";
import Dropzone from "react-dropzone";
import {uploadFile} from "../../services/curriculum-service";

export default function TeleverserCv() {
    const [fileNames, setFileNames] = useState([]);
    const handleDrop = acceptedFiles => {
        setFileNames(acceptedFiles.map(file => file.name));
        console.log(acceptedFiles)
        uploadFile(acceptedFiles, 1).then()
    }

    const goDashboard = () => {
        this.props.history.push('/dashboard')
    }


    return (
        <div>
            <Dropzone
                onDrop={handleDrop}
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
                        <div>
                            <h2 className={"text-center"}>Les fichiers accetpés sont de type <b>.PDF</b> ou <b>.DOCX</b>
                            </h2>
                            <div
                                {...getRootProps({
                                    className: `dropzone ${additionalClass}`
                                })}
                            >
                                <input {...getInputProps()} />
                                <span>
                                        {!isDragActive && "Cliquez moi ou glissez votre C.V. ici"}
                                    {isDragActive && isDragAccept && "Déposez votre C.V ici"}
                                    {isDragActive && isDragReject && "Ce format de fichier n'est pas accepté"}
                                    </span>
                            </div>
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
            <div className={"text-center"}>
                <button>Envoyer</button>
            </div>
        </div>
    );
}
