import './PreviewStudent.css';
import {toast} from "../../utility";
import PreviewOffer from "../PreviewOffer/PreviewOffer";
import {AiOutlineFile} from "react-icons/all";

export default function PreviewStudent({dto}) {

    const openFile = () => {
        const decodedChars = atob(dto.file);
        const byteNums = new Array(decodedChars.length);
        for (let i = 0; i < decodedChars.length; i++)
            byteNums[i] = decodedChars.charCodeAt(i);

        // noinspection JSCheckFunctionSignatures
        const blob = new Blob([new Uint8Array(byteNums), {type: 'application/pdf'}]);

        let url = window.URL.createObjectURL(blob);

        const a = document.createElement('a')
        a.href = url
        a.download = dto.fileName;
        a.click();
        URL.revokeObjectURL(url)
        toast.fire({title: 'Téléchargé'}).then()
    }

    return <div className=" p-3 mt-5 border-left border-right border-light">
        <div className={'row'}>
            <div className="col-12 col-sm-6">
                <div className={'d-flex justify-content-center align-items-center flex-column p-3 shadow h-100'}>
                    <h4 className={'p-2 rounded bg-secondary fw-bold text-white'}>{dto.firstName + ', ' + dto.lastName} </h4>
                    <div className={'d-flex align-items-center'}>

                        <button onClick={openFile} className="ms-2 btn btn-primary"><AiOutlineFile/> {dto.fileName}</button>
                    </div>
                </div>
            </div>
            <div className="col-12 col-sm-6">
                <PreviewOffer offer={dto.offerDTO}/>
            </div>
        </div>
    </div>
}
