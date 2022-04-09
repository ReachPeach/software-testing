import React, {useEffect, useState} from "react";

import "./App.css";


export default function AuthorItem() {
    const queryParams = new URLSearchParams(window.location.search);
    const [songs, songsDispatch] = useState([]);

    const id = parseInt(queryParams.get("id"))
    useEffect(async () => {
        songsDispatch(await (await fetch("http://localhost:4000" + "/author?id=" + id, {
            credentials: "include"
        })).json());
    }, []);

    const name = songs.length !== 0
        ? songs.find(song => song.authors.find((author) => author.id === id)).authors.find((author) => author.id === id).name
        : undefined
    return (
        <div className="author-item">
            {name !== undefined ? (
                <div>
                    <h2 className="author-name">{name}</h2>
                    <ol className="song-names">
                        {songs.map((song) => {
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