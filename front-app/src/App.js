import './App.css';
import {BrowserRouter, Route, Switch} from "react-router-dom";
import React, {Component} from "react";
import Home from "./components/home/Home";
import Navbar from "./components/navbar/Navbar";

class App extends Component {
    render() {
        return(
          <BrowserRouter>
            <div className="App">
              <Navbar/>
              <Switch>
                <Route exact path='/' component={Home}/>
              </Switch>
            </div>

          </BrowserRouter>
        );
    }
}

export default App;
