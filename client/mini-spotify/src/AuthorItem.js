import React, {useEffect, useState} from "react";

import "./App.css";
import {useParams} from "react-router-dom";


export default function AuthorItem() {
    const {id} = useParams();

    const [songs, songsDispatch] = useState([]);
    useEffect(() => {
        async function fetchData() {
            songsDispatch(await (await fetch("http://localhost:4000/author?id=" + id, {
                credentials: "include"
            })).json());
        }

        fetchData()
    });
    const name = songs.length !== 0
        ? songs.find(song => song.authors.find((author) => author.id === parseInt(id))).authors.find((author) => author.id === parseInt(id)).name
        : undefined
    return (
        <div className="author-item">
            {name !== undefined ? (
                <div>
                    <h2 className="author-name">{name}</h2>
                    <ol className="song-names">
                        {songs.map((song) => {
                                const {id, name} = song;
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