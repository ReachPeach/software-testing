import React from "react";
import {BrowserRouter, Redirect, Route, Routes, Switch} from "react-router-dom";
import {createBrowserHistory} from "history";
import {QueryClient, QueryClientProvider} from "react-query";

import "./App.css";
import SongList from "./SongList";
import AuthorItem from "./AuthorItem";


const queryClient = new QueryClient();
const history = createBrowserHistory()

function App() {
    return (
        // <QueryClientProvider client={queryClient}>
            <div className="App">
                <header className="App-header">
                    <h2>Getting started with React testing library</h2>
                </header>

                <div className="App-body">
                    <BrowserRouter>
                        <Routes>
                            <Route path="/author" element={<AuthorItem/>}/>
                            <Route exact path="/" element={<SongList/>}/>
                        </Routes>
                    </BrowserRouter>
                </div>
            </div>
        // </QueryClientProvider>
    );
}

export default App;