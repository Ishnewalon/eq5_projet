import React, {useEffect, useState} from "react";
import {getCurrentAndFutureSession} from "../../services/session-service";
import {DepartmentEnum} from "../../enums/Departement";
import {useAuth} from "../../hooks/use-auth";
import {createOffer} from "../../services/offer-service";
import OfferDTO from "../../models/OfferDTO";
import {ContainerBox} from "../SharedComponents/ContainerBox";
import {useForm} from "react-hook-form";
import {regexEmail} from "../../utility";
import {FormGroup} from "../SharedComponents/Form/FormGroup";
import {FieldInput, FieldSelect, FieldTextarea} from "../SharedComponents/Form/FormFields";
import {Column} from "../SharedComponents/Column";


export default function CreateOffer() {
    let auth = useAuth();
    const {register, handleSubmit, formState: {errors}, reset, watch} = useForm({
        mode: "onSubmit",
        reValidateMode: "onChange"
    });
    const [sessions, setSessions] = useState([])

    useEffect(() => {
        getCurrentAndFutureSession().then(sessions => setSessions(sessions.sort((a, b) => a.year - b.year)))
    }, [])

    const addOffer = (data, e) => {
        e.preventDefault();
        let {
            title,
            department,
            description,
            address,
            salary,
            creator_email,
            nbSemaine,
            dateDebut,
            dateFin,
            horaireTravail,
            nbHeureSemaine,
            idSession
        } = data

        if (!creator_email)
            creator_email = auth.user.email;

        let offer = new OfferDTO(title, department, description, address, salary, creator_email, nbSemaine, dateDebut, dateFin, horaireTravail, nbHeureSemaine, parseInt(idSession));
        createOffer(offer).then((b) => {
            if (b === null)
                return
            reset()
        })
    }

    const stageStart = watch("dateDebut", "");

    const checkIfLowerThan = (val, previous) => {
        if (!val || !previous)
            return true;

        let dateEnd = val.split("-").map(n => parseInt(n));
        let dateStart = previous.split("-").map(n => parseInt(n));

        if (dateEnd[0] !== dateStart[0]) {
            return false;
        }
        else if (dateEnd[0] === dateStart[0]) {
            let diff = dateEnd[1] - dateStart[1];
            if (diff >= 3) {
                return true;
            }
            else {
                return false;
            }
        }


        // let nextTime = val.split(":").map(n => parseInt(n));
        // let previousTime = previous.split(":").map(n => parseInt(n));
        //
        // if (nextTime[0] > previousTime[0])
        //     return true;
        // else if (nextTime[0] === previousTime[0])
        //     if (nextTime[1] > previousTime[1])
        //         return true;
    }



    const monitorEmail = (
        <Column col={{sm: 12, md: 6}}>
            <FieldInput label="Email du moniteur"
                        name="creator_email"
                        error={errors.creator_email}
                        type="email"
                        placeholder="Entrez l'email du moniteur"
                        register={register}
                        validation={{
                            required: "Ce champ est obligatoire!",
                            pattern: {
                                value: regexEmail,
                                message: "L'email n'est pas valide!"
                            }
                        }}/>
        </Column>
    )
    return (<ContainerBox className="w-75">
        <form onSubmit={handleSubmit(addOffer)} noValidate>
            <FormGroup>
                <Column col={{md: auth.isManager() ? 6 : 12}}>
                    <FieldInput label="Titre"
                                name="title"
                                error={errors.title}
                                type="text"
                                placeholder="Titre"
                                register={register}
                                validation={{
                                    required: "Ce champ est obligatoire!",
                                }}/>
                </Column>
                {(auth.isManager()) && monitorEmail}
                <Column>
                    <FieldTextarea label="Description"
                                   name="description"
                                   placeholder="Description"
                                   error={errors.description}
                                   register={register}
                                   validation={{
                                       required: "Ce champ est obligatoire!"
                                   }}/>
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 6}}>
                    <FieldSelect label="Session"
                                 name="idSession"
                                 defaultMessage="Choisissez une session"
                                 options={sessions}
                                 fieldValue={'id'}
                                 displayed={['typeSession', 'year']}
                                 register={register}
                                 error={errors.idSession}
                                 validation={{
                                     required: "Ce champ est obligatoire!",
                                 }}/>
                </Column>
                <Column col={{md: 6}}>
                    <FieldSelect label="Département"
                                 name="department"
                                 defaultMessage="Choisissez un département"
                                 options={Object.values(DepartmentEnum)}
                                 fieldValue={0}
                                 displayed={[0]}
                                 register={register}
                                 error={errors.department}
                                 validation={{
                                     required: "Ce champ est obligatoire!",
                                 }}/>
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 6}}>
                    <FieldInput label="Adresse ou le stage se situe"
                                name="address"
                                error={errors.address}
                                type="text"
                                placeholder="Rue, boulevard, avenue.."
                                register={register}
                                validation={{
                                    required: "Ce champ est obligatoire!"
                                }}/>
                </Column>
                <Column col={{md: 6}}>
                    <FieldInput label="Salaire"
                                name="salary"
                                error={errors.salary}
                                type="number"
                                register={register}
                                validation={{
                                    required: "Ce champ est obligatoire!"
                                }}/>
                </Column>
            </FormGroup>
            <FormGroup>
                <Column col={{md: 6, lg: 3}}>
                    <FieldInput label="Date de début"
                                name="dateDebut"
                                error={errors.dateDebut}
                                type="date"
                                placeholder="Date de début"
                                register={register}
                                validation={{
                                    required: "Ce champ est obligatoire!"
                                }}/>
                </Column>
                <Column col={{md: 6, lg: 3}}>
                    <FieldInput label="Date de fin"
                                name="dateFin"
                                error={errors.dateFin}
                                type="date"
                                placeholder="Date de fin"
                                register={register}
                                validation={{
                                    required: "Ce champ est obligatoire!",
                                    validate: val => checkIfLowerThan(val, stageStart) || 'La date de fin de stage doit être au minimum 3 mois après la date de début de stage ' +
                                        'et être dans la même année'
                                }}/>
                </Column>
                <Column col={{lg: 6}}>
                    <FieldInput label="Horaire de travail"
                                name="horaireTravail"
                                error={errors.horaireTravail}
                                type="text"
                                placeholder="Horaire de travail"
                                register={register}
                                validation={{
                                    required: "Ce champ est obligatoire!"
                                }}/>
                </Column>
                <Column col={{lg: 6}}>
                    <FieldInput label="Nombre de semaines"
                                name="nbSemaine"
                                error={errors.nbSemaine}
                                type="text"
                                placeholder="Nombre de semaines"
                                register={register}
                                validation={{
                                    required: "Ce champ est obligatoire!"
                                }}/>
                </Column>
                <Column col={{lg: 6}}>
                    <FieldInput label="Nombre de heures par semaine"
                                name="nbHeureSemaine"
                                error={errors.nbHeureSemaine}
                                type="text"
                                placeholder="Nombre de heures par semaine"
                                register={register}
                                validation={{
                                    required: "Ce champ est obligatoire!"
                                }}/>
                </Column>
            </FormGroup>
            <div className="form-group text-center">
                <button className="btn btn-primary mt-3" type="submit">Ajouter</button>
            </div>
        </form>
    </ContainerBox>);
}

