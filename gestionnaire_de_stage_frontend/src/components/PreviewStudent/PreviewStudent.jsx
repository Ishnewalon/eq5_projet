import './PreviewStudent.css';
import PropTypes from "prop-types";
import CurriculumDto from "../../models/CurriculumDto";
import {toast} from "../../utility";
import PreviewOffer from "../PreviewOffer/PreviewOffer";
import {AiOutlineFile} from "react-icons/all";

export default function PreviewStudent(dto) {

    PreviewStudent.propTypes = {
        student: PropTypes.instanceOf(CurriculumDto).isRequired
    }

    const openFile = () => {
        const decodedChars = atob(dto.file);
        const byteNums = new Array(decodedChars.length);
        for (let i = 0; i < decodedChars.length; i++)
            byteNums[i] = decodedChars.charCodeAt(i);
        let contentType = 'application/pdf';

        if(dto.fileName.endsWith('docx'))
            contentType = 'application/vnd.openxmlformats-officedocument.wordprocessingml.document';

        const blob = new Blob([new Uint8Array(byteNums), {type: contentType}]);

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
                    <div className={'d-flex justify-content-center align-items-center badge bg-primary text-wrap h2'}>
                        <AiOutlineFile />
                        <a onClick={openFile} target={'_blank'} className={'ms-2 text-white'}>{dto.fileName}</a>
                    </div>
                </div>
            </div>
            <div className="col-12 col-sm-6">
                {PreviewOffer(dto.offerDTO)}
            </div>
        </div>
    </div>
}
