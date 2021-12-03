import {Document, Page, pdfjs} from 'react-pdf';

import {useEffect, useState} from "react";
import {downloadFile} from "../../../utility";
import {BiLeftArrowAlt, BiRightArrowAlt, FiDownload} from "react-icons/all";
import style from "./PdfDocumentViewer.module.css";

export default function PdfDocumentViewer({file, fileName, showContract = false, message = 'Visionnement de pdf'}) {
    const [numPages, setNumPages] = useState(null);
    const [pageNumber, setPageNumber] = useState(1);
    const [show, setShow] = useState(showContract);

    useEffect(() => {
        pdfjs["GlobalWorkerOptions"].workerSrc = `//cdnjs.cloudflare.com/ajax/libs/pdf.js/${pdfjs.version}/pdf.worker.min.js`;
    }, []);

    const onDocumentLoad = ({numPages}) =>
        setNumPages(numPages)

    const goToNextPage = (e) => {
        e.preventDefault();
        if (pageNumber < numPages)
            setPageNumber(pageNumber + 1);
    }

    const goToPreviousPage = (e) => {
        e.preventDefault();
        if (pageNumber > 1)
            setPageNumber(pageNumber - 1);
    }

    return (
        <div className={"card d-flex justify-content-center align-items-center flex-column shadow-sm"}>
            <div className='card-header w-100 text-center'>
                <h3 className='card-title'>{message}</h3>
                <div className="btn-group my-3">
                    <button className={"btn btn-primary"}
                            onClick={() => setShow(!show)}>{(show ? 'Cacher' : 'Montrer')}</button>
                    <button className='btn btn-primary'
                            onClick={() => downloadFile(file, fileName)}><FiDownload/></button>
                </div>
            </div>
            {
                show ?
                    <div className='p-3'>
                        <div className="text-center">
                            <div className="d-flex">
                                <button disabled={pageNumber === 1} className="link-button disabled"
                                        onClick={e => goToPreviousPage(e)}>
                                    <BiLeftArrowAlt size={40} title="Précédent"/>
                                </button>
                                <Document
                                    file={file}
                                    onLoadSuccess={onDocumentLoad}
                                    onLoadError={alert}
                                    loading={<div className="text-center">Chargement...</div>}
                                    noData={<div className="text-center">Aucun document</div>}
                                    className={"shadow"}>
                                    {
                                        Array.from(new Array(numPages), (el, index) => (
                                            <div key={`page_${index + 1}`}
                                                 className={pageNumber === index + 1 ? "" : style.pageHide}>
                                                <Page pageNumber={index + 1} style={style.page}/>
                                            </div>))
                                    }
                                </Document>
                                <button disabled={pageNumber === numPages} className={"link-button"}
                                        onClick={e => goToNextPage(e)}>
                                    <BiRightArrowAlt size={40} title="Suivant"/>
                                </button>
                            </div>
                            <span className={'mt-2 p-2 bold'}>
                                {numPages > 0 ? `Page ${pageNumber} de ${numPages}` : 'Aucune pages'}
                            </span>
                        </div>
                    </div>
                    : <></>
            }
        </div>
    )
}
