import React, {useEffect, useState} from "react";
import {Song} from "./Song";

export function Library() {
    const [library, setLibrary] = useState([]);

    useEffect(() => {
        fetch("/library/").then(res => {
            if (res.ok) {
                return res.json();
            }
        }).then(jsonRes => {
            setLibrary(jsonRes.library);
        })
    })

    return (
        <div className="Library">
            <div>
                {
                    library.map(
                        libraryItem => <Song name={libraryItem.name}
                                             authorList={libraryItem.authorList}/>)
                }
            </div>
        </div>

    );
}