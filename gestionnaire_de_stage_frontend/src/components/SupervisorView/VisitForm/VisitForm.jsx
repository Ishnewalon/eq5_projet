


export default function VisitForm(){

    return<div className="container">
            <form>
                <div className="input-group">
                    <label>Name</label>
                    <input type="text" className="form-control"  placeholder=""/>
                </div>
                <div className="form-group">
                    <label htmlFor="exampleFormControlInput1">Email address</label>
                    <input type="email" className="form-control" id="exampleFormControlInput1" placeholder="" />
                </div>
                <div className="input-group">
                    <label>Phone</label>
                    <input type="text" className="form-control"  placeholder=""/>
                </div>
            </form>
        </div>
}
