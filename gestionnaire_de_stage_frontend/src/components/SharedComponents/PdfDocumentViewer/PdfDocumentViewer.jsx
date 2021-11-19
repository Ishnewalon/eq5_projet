import {Document, Page, pdfjs} from 'react-pdf';

import {useEffect, useState} from "react";
import {downloadFile} from "../../../utility";
import {FiDownload} from "react-icons/all";

export default function PdfDocumentViewer({file, fileName, showContract = false}) {
    const [numPages, setNumPages] = useState(null);
    const [pageNumber, setPageNumber] = useState(1);
    const [show, setShow] = useState(showContract);

    useEffect(() => {
        // noinspection JSUnresolvedVariable
        pdfjs.GlobalWorkerOptions.workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.min.js`;
    }, []);

    const onDocumentLoad = ({numPages}) => {
        setNumPages(numPages);
    }

    const goToNextPage = (e) => {
        e.preventDefault();
        if (pageNumber < numPages) {
            setPageNumber(pageNumber + 1);
        }
    }

    const goToPreviousPage = (e) => {
        e.preventDefault();
        if (pageNumber > 1) {
            setPageNumber(pageNumber - 1);
        }
    }

    return (
        <div className={"d-flex justify-content-center align-items-center flex-column"}>
            <div className="btn-group my-3">
                <button className={"btn btn-primary"}
                        onClick={() => setShow(!show)}>{(show ? 'Cacher' : 'Montrer') + ' pdf'}</button>
                <button className='btn btn-primary'
                        onClick={() => downloadFile(file, fileName)}><FiDownload/></button>
            </div>
            {
                show ?
                    <div>
                        <div>
                            <Document
                                file={file}
                                onLoadSuccess={onDocumentLoad}
                                onLoadError={alert}

                            >
                                <Page pageNumber={pageNumber}/>
                            </Document>
                            <p className={'text-center mt-2 border border-white p-2'}>{numPages > 0 ? `Page ${pageNumber} de ${numPages}` : 'Aucune pages'}</p>
                            <div className={"d-flex justify-content-between"}>
                                <button
                                    type="button"
                                    className={"btn btn-primary"}
                                    id="previousBtn"
                                    onClick={(e) => goToPreviousPage(e)}>Précédent
                                </button>
                                <button type="button" className={"btn btn-primary "} onClick={(e) => goToNextPage(e)}
                                        id="nextBtn">Prochain
                                </button>
                            </div>
                        </div>
                    </div>
                    : <></>
            }
        </div>
    )
}
