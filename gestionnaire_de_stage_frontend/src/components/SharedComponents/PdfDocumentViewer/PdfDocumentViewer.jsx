import {Document, Page, pdfjs} from 'react-pdf';

import {useEffect, useState} from "react";

export default function PdfDocumentViewer({file, showContract= false}) {
    const [numPages, setNumPages] = useState(null);
    const [pageNumber, setPageNumber] = useState(1);
    const [show, setShow] = useState(showContract);

    useEffect(() => {
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
            <button className={"btn btn-primary text-capitalize mb-2 mt-4"}
                    onClick={() => setShow(!show)}>{(show ? 'cacher' : 'montrer') + ' pdf'}</button>
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
