import React, {useEffect, useState} from "react";
import {getCurrentAndFutureSession} from "../../services/session-service";
import {FormGroup} from "../SharedComponents/FormGroup/FormGroup";
import {DepartmentEnum} from "../../enums/Departement";
import {useAuth} from "../../services/use-auth";
import {createOffer} from "../../services/offer-service";
import OfferDTO from "../../models/OfferDTO";
import {ContainerBox} from "../SharedComponents/ContainerBox/ContainerBox";
import {FormInput} from "../SharedComponents/FormInput/FormInput";
import {useForm} from "react-hook-form";
import {regexEmail} from "../../utility";
import {FormTextarea} from "../SharedComponents/FormTextarea";
import {FormSelect} from "../SharedComponents/FormInput/FormSelect";


export default function CreateOffer() {
    let auth = useAuth();
    const {register, handleSubmit, formState: {errors}, reset} = useForm({
        mode: "onSubmit",
        reValidateMode: "onChange"
    });
    const [sessions, setSessions] = useState([])

    useEffect(() => {
        getCurrentAndFutureSession().then(sessions => setSessions(sessions))
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

    const monitorEmail = (
        <FormInput label="Email du moniteur"
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
    )

    return (<ContainerBox className="w-75">
        <form onSubmit={handleSubmit(addOffer)} noValidate>
            <FormGroup repartition={!auth.isManager() ? [12] : null}>
                <FormInput label="Titre"
                           name="title"
                           error={errors.title}
                           type="text"
                           placeholder="Titre"
                           register={register}
                           validation={{
                               required: "Ce champ est obligatoire!",
                           }}/>
                {(auth.isManager()) ? monitorEmail : null}
            </FormGroup>
            <FormGroup>
                <FormTextarea label="Description"
                              name="description"
                              placeholder="Description"
                              error={errors.description}
                              register={register}
                              validation={{
                                  required: "Ce champ est obligatoire!"
                              }}/>
            </FormGroup>
            <FormGroup>
                <FormSelect label="Session"
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
                <FormSelect label="Département"
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
            </FormGroup>
            <FormGroup>
            </FormGroup>
            <FormGroup>
                <FormInput label="Adresse ou le stage se situe"
                           name="address"
                           error={errors.address}
                           type="text"
                           placeholder="Rue, boulevard, avenue.."
                           register={register}
                           validation={{
                               required: "Ce champ est obligatoire!"
                           }}/>
                <FormInput label="Salaire"
                           name="salary"
                           error={errors.salary}
                           type="number"
                           register={register}
                           validation={{
                               required: "Ce champ est obligatoire!"
                           }}/>
            </FormGroup>
            <FormGroup repartition={[3, 3, 6]}>
                <FormInput label="Date de début"
                           name="dateDebut"
                           error={errors.dateDebut}
                           type="date"
                           placeholder="Date de début"
                           register={register}
                           validation={{
                               required: "Ce champ est obligatoire!"
                           }}/>
                <FormInput label="Date de fin"
                           name="dateFin"
                           error={errors.dateFin}
                           type="date"
                           placeholder="Date de fin"
                           register={register}
                           validation={{
                               required: "Ce champ est obligatoire!"
                           }}/>
                <FormInput label="Horaire de travail"
                           name="horaireTravail"
                           error={errors.horaireTravail}
                           type="text"
                           placeholder="Horaire de travail"
                           register={register}
                           validation={{
                               required: "Ce champ est obligatoire!"
                           }}/>
            </FormGroup>
            <FormGroup>
                <FormInput label="Nombre de semaines"
                           name="nbSemaine"
                           error={errors.nbSemaine}
                           type="text"
                           placeholder="Nombre de semaines"
                           register={register}
                           validation={{
                               required: "Ce champ est obligatoire!"
                           }}/>
                <FormInput label="Nombre de heures par semaine"
                           name="nbHeureSemaine"
                           error={errors.nbHeureSemaine}
                           type="text"
                           placeholder="Nombre de heures par semaine"
                           register={register}
                           validation={{
                               required: "Ce champ est obligatoire!"
                           }}/>
            </FormGroup>
            <div className="form-group text-center">
                <button className="btn btn-primary mt-3" type="submit">Ajouter</button>
            </div>
        </form>
    </ContainerBox>);
}

