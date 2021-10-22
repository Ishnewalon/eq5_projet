import React, {Component} from 'react';
import PreviewOffer from "../PreviewOffer/PreviewOffer";

export default class ViewAppliedStudents extends Component{

    constructor(props) {
        super(props);
        this.state = {
            students: []
        }
    }


    render() {
        return <div className='container'>
            <h2 className="text-center">Offres de Stage</h2>
            <ul>
                {this.state.students.map((student, index) => <li key={index}><PreviewStudent studnet={student}/></li>)}
            </ul>
        </div>
    }
}
