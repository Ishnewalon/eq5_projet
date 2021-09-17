import {Component} from "react";

export class TemplateCustomBS extends Component {
    render() {
        return <div className="row">
            <div className="col-6">
                <h1>Header 1</h1>
                <h2>Header 2</h2>
                <h3>Header 3</h3>
                <h4>Header 4</h4>
                <h5>Header 5</h5>
                <h6>Header 6</h6>
                <button className="btn btn-primary">Button</button>
                {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
                <a className="link" href="#">link lol</a>
            </div>
            <div className="col-6 bg-dark rounded shadow my-2 p-2">
                <h1>Header 1</h1>
                <h2>Header 2</h2>
                <h3>Header 3</h3>
                <h4>Header 4</h4>
                <h5>Header 5</h5>
                <h6>Header 6</h6>
                <button className="btn btn-primary">Button</button>
                {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
                <a className="link" href="#">link lol</a>
            </div>
        </div>;
    }
}
