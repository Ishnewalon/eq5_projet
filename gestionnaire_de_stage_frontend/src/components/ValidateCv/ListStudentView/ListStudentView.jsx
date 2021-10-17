import React, {Component} from 'react';
import './ListStudentView.css'


export default class ListStudentView extends Component{

    render() {
        const {student} = this.props;
        return <div className="shadow-lg rounded-top p-3 mt-5 border-left border-right border-light">
            <div className={'d-flex align-items-center flex-column'}>
                <h2 className={'d-inline-block text-dark display-6 fst-italic'}>
                    <span className={"badge bg-secondary"}>{student.firstName}</span></h2>
                {/*<div className={'row mb-0'}>*/}
                {/*    <span className={"col badge bg-dark rounded-pill px-2 py-1"}>{student.}</span>*/}
                {/*    <span className="ms-2 col badge bg-success px-2 py-1">{offer.salary + '$/h'}</span>*/}
                {/*</div>*/}
                {/*<p>Addresse : {student.}</p>*/}
                {/*<p className={'mb-0'}>Description: <br/> {offer.description}</p>*/}
            </div>
        </div>
    }
}
