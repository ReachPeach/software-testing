import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";

import "./App.css";


export default function SongList() {
    const [library, libraryDispatch] = useState([]);

    useEffect(() => {
        async function fetchData() {
            await libraryDispatch(await (await fetch("http://localhost:4000/", {
                credentials: "include"
            })).json());
        }
        fetchData()
    }, []);

    return (
        <div className="SongList">
            <ol>
                {library.slice(0, 15).map((song) => {
                    const {id, name, authors} = song;
                    return (
                        <li className="Song" key={id}>
                            {name}
                            <ol className="AuthorList">
                                {authors.slice(0, 5).map((author) => {
                                    const {id, name} = author
                                    return (
                                        <li className="Author" key={id}>
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