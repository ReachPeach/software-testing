import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";

import "./App.css";
import {SongList} from "./SongList";
import {AuthorItem} from "./AuthorItem";

function App() {
    return (
        <div className="App">
            <header className="App-header">
                <h2>Getting started with React testing library</h2>
            </header>

            <div className="App-body">
                <BrowserRouter>
                    <Routes>
                        <Route path="/author/:id" element={<AuthorItem/>}/>
                        <Route exact path="/" element={<SongList/>}/>
                    </Routes>
                </BrowserRouter>
            </div>
        </div>
    );
}

export default App;