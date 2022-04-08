import React from "react";
import axios from "axios";
import {Link} from "react-router-dom";

import "./App.css";

import {AppContext} from "./AppContext";

export const SongList = () => {
    const {appData, appDispatch} = React.useContext(AppContext);

    return (
        <div>
            <ol>
                {appData.library.slice(0, 15).map((song) => {
                    const {id, name, authors} = song;
                    return (
                        <li>
                            {name}
                            <ol>
                                {authors.slice(0, 5).map((author) => {
                                    const {id, name} = author
                                    return (
                                        <li>
                                            <Link to={`/author/${id}`} data-testid={id}>
                                                {name}
                                            </Link>
                                        </li>
                                    )
                                })}
                            </ol>
                        </li>
                    );
                })}
            </ol>
        </div>
    );
};