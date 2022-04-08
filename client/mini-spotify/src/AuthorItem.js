import React from "react";
import axios from "axios";
import {useParams} from "react-router-dom";

import "./App.css";

import {AppContext} from "./AppContext";

export const AuthorItem = () => {
    const {id} = useParams();

    const {
        appData,
        appDispatch,
    } = React.useContext(AppContext);

    const songNames = appData.library.filter((songInfo) => songInfo.authors.some((author) => author.id == id));
    const name = songNames.length !== 0
        ? songNames.find(song => song.authors.find((author) => author.id == id)).authors.find((author) => author.id == id).name
        : undefined
    return (
        <div className="author-item">
            {name !== undefined ? (
                <div>
                    <h2 className="author-name">{name}</h2>
                    <ol className="song-names">
                        {songNames.map((song) => {
                                const {id, name, authors} = song;
                                return (
                                    <li key={id} className="Song-name">
                                        {name}
                                    </li>
                                )
                            }
                        )}
                    </ol>
                </div>
            ) : (
                <h3 className="missing-author">Author with id={id} doesn't exist!</h3>
            )}
        </div>
    );
};