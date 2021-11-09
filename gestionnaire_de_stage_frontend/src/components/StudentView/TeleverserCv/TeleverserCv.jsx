import React, {useState} from "react";
import Dropzone from "react-dropzone";
import {uploadFile} from "../../../services/curriculum-service";
import {useAuth} from "../../../services/use-auth";

export default function TeleverserCv() {
    let auth = useAuth();
    const [fileNames, setFileNames] = useState([]);
    const handleDrop = acceptedFiles => {
        setFileNames(acceptedFiles.map(file => file.name));
        console.log(acceptedFiles)
        uploadFile(acceptedFiles, auth.user.id).then()
    }

    return (
        <div>
            <Dropzone
                onDrop={handleDrop}
                accept="application/pdf"
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
                            <h2 className={"text-center"}>Les fichiers acceptés sont de type <b>.PDF</b>
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
        </div>
    );
}
