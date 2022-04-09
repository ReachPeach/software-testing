import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";

import "./App.css";


export default function SongList() {
    const [library, libraryDispatch] = useState([]);

    useEffect(async () => {
        await libraryDispatch(await (await fetch("http://localhost:4000/", {
            credentials: "include"
        })).json());
    }, []);

    return (
        <div className="SongList">
            <ol>
                {library.slice(0, 15).map((song) => {
                    const {id, name, authors} = song;
                    return (
                        <li className="Song">
                            {name}
                            <ol className="AuthorList">
                                {authors.slice(0, 5).map((author) => {
                                    const {id, name} = author
                                    return (
                                        <li className="Author">
                                            <Link to={`/author?id=${id}`} data-testid={id}>
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