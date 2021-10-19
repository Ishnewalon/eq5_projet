import React, {Component} from 'react';
import './ListStudentView.css'


export default class ListStudentView extends Component{

    render() {
        const {student} = this.props;
        return <div className="shadow-lg rounded-top p-3 mt-5 border-left border-right border-light">
            <div className={'d-flex align-items-center flex-column'}>
                <h2 className={'d-inline-block text-dark display-6 fst-italic'}>
                    <span className={"badge bg-secondary"}>{student.lastName}</span></h2>
            </div>
        </div>
    }
}
