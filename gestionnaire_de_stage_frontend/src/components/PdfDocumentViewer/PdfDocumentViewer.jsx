import {Document, Page, pdfjs} from 'react-pdf';

import {useEffect, useState} from "react";

export default function PdfDocumentViewer({file}) {
    const [numPages, setNumPages] = useState(null);
    const [pageNumber, setPageNumber] = useState(1);

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
            <button className={"btn mb-2"}>Show</button>
            <Document
                file={file}
                onLoadSuccess={onDocumentLoad}
                onLoadError={alert}
            >
                <Page pageNumber={pageNumber}></Page>
            </Document>
            <p>Page {pageNumber} of {numPages}</p>
            <div className={"d-flex"}>
                <button type="button" className={"btn btn-primary"} id="previousBtn"
                        onClick={(e) => goToPreviousPage(e)}>Previous
                </button>
                <button type="button" className={"btn btn-primary "} onClick={(e) => goToNextPage(e)} id="nextBtn">Next</button>
            </div>
        </div>
    )
}
