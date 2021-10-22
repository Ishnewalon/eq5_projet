import React, {useEffect, useState} from 'react';
import PreviewStudent from "../PreviewStudent/PreviewStudent";
import getAllApplicants from '../../services/offer-application-service';
import PropTypes from 'prop-types';
export default function ViewAppliedStudents(email) {

    const [students, setStudents] = useState([]);

    const loadAllData = () => async () => await getAllApplicants(email).then(v => setStudents(v))

    useEffect(loadAllData(), []);

    ViewAppliedStudents.propTypes = {
        email: PropTypes.string.isRequired
    }

    return <div className=''>
        <div className={'d-flex justify-content-center align-items-center'}>
            <h2 className="text-center">Applicants</h2>
            <h2 className={'ms-2'}>
                <span className={`badge bg-secondary`}>{students.length}</span>
            </h2>
        </div>
            {students.length > 0 ?
                students.map((student, index) => <div key={index}>{PreviewStudent(student)}</div>)
             : (
                <div className={"d-flex justify-content-center align-items-center"}>
                    <p className={"text-center border border-white rounded p-2 mt-3"}>Aucun applicant à voir pour le moment</p>
                </div>
            )}
    </div>;
}
