const header = new Headers()
header.append('content-type', 'application/json')

async function getData(v) {
    const response = await fetch("http://localhost:4000/posts", {
        method: 'POST',
        mode: 'cors', // no-cors, *cors, same-origin
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(v)
    });
    const data = await response.json();

    // store the data into our books variable
    console.log(data)
}

function Password({prevStep, handleChange, values}) {

    const Previous = e => {
        e.preventDefault();
        prevStep();
    }

    function Continue(e) {
        e.preventDefault();//https://postman-echo.com/get?foo1=bar1&foo2=bar2
        // fetch('https://localhost:4000',{
        //     method: 'GET',
        //     headers: {"Content-Type": "application/json"},
        //     // body: JSON.stringify(values)
        // }).then(() =>{
        //       console.log("ajoute")
        //  })
        getData(values).then()
    }

    return (<div>
            <div className="form-group row">
                <div className="col-md-12">
                    <label>Mot de passe</label>
                    <div className="input-group">
                        <input name="pwd" placeholder="Votre mot de passe" className="form-control" type="password"
                               value={values.password} onChange={handleChange('password')}/>
                    </div>
                </div>

                {/*TODO REVOIR LA CONFIRMATION DU MDP*/}
                {/*<div className="col-md-6">*/}
                {/*    <label>Confirmez votre mot de passe</label>*/}
                {/*    <div className="input-group">*/}
                {/*        <input name="pwd2" placeholder="Confirmez votre mot de passe" className="form-control" type="text"*/}
                {/*               value={values.pwd2} onChange={handleChange('pwd2')}/>*/}
                {/*    </div>*/}
                {/*</div>*/}
                <div className="form-group text-center">
                    <label/>
                    <div>
                        <button className="btn btn-primary" type={"button"} onClick={Previous}>Precedent</button>
                        <button className="btn btn-primary" type={"button"} onClick={Continue}>Suivant</button>
                    </div>
                </div>
            </div>
        </div>
    )
}


export default Password;
