import React, {Component} from 'react'
import {Link} from "react-router-dom";


class Navbar extends Component {
    render() {
        return (
            <nav className="nav-wrapper grey darken-3">
                <div className="container">
                    <div className="left">
                        <Link to="/" className="brand-logo">Home</Link>
                    </div>
                </div>
            </nav>
        )
    }
}

export default Navbar