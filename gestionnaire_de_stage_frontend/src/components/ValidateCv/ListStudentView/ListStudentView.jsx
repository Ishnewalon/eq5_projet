import React, {Component} from 'react';
import './ListStudentView.css'
import {downloadCV} from "../../../services/curriculum-service";
import {toast} from "../../../utility";


export default class ListStudentView extends Component {

    constructor(props) {
        super(props);

        this.state = {
            url: "",
            filename: "default.pdf"
        }
    }

    dl = () => {

        const {id, student} = this.props.cv;
        const {firstName, lastName} = student;
        downloadCV(id).then(blob => {
            let url = URL.createObjectURL(blob);

            let filename = firstName + "_" + lastName + "_" + id + ".pdf";

            this.setState({url, filename});
            const a = document.createElement('a')
            a.href = url
            a.download = filename;
            a.click();
            URL.revokeObjectURL(url)
            toast.fire({title: 'Téléchargement en cours'}).then()
        });
    }

    render() {
        const {student} = this.props.cv;
        return <div className="shadow-lg rounded-top p-3 mt-3 border-left border-right">
            <div className={'d-flex align-items-center flex-column'}>
                <h3 className={'d-inline-block text-dark'}>
                    <span className={"badge rounded-pill"}>
                         {student.firstName} {student.lastName}
                    </span>
                </h3>
                <a onClick={this.dl} target={'_blank'}>Télécharger Cv</a>
            </div>
        </div>
    }
}
