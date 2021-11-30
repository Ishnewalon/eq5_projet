import React from "react";
import Dropzone from "react-dropzone";
import {uploadFile} from "../../../../vue_frontend/src/services/curriculum-service";
import {useAuth} from "../../hooks/use-auth";

export default function CurriculumUpload() {
    let auth = useAuth();
    const handleDrop = acceptedFiles => {
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
        </div>
    );
}
