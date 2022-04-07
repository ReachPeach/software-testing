import React from 'react';
import {BrowserRouter, Routes, Route} from "react-router-dom";
import './App.css';
import {NavigationBar} from './components/header/Header'
import {Song} from './components/music/Song'
import {Author} from './components/music/Author'
import {Library} from './components/music/Library'

function App() {
    return (
        <div className="App">
            <header className="App-header">
                <NavigationBar/>
                <h2>Getting started with react</h2>
            </header>

            <div className="App-body">
                <Routes>
                    <Route path="/library"
                           element={<Library/>}/>
                </Routes>
                {/*<BrowserRouter>*/}
                {/*    <Route path="/song/:id" component={Song}/>*/}
                {/*    <Route path="/author/:id" component={Author}/>*/}
                {/*    <Route exact path="/" component={Library}/>*/}
                {/*</BrowserRouter>*/}
            </div>
        </div>
    );
}

export default App;
