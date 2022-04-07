import React from "react";

export class Author extends React.Component {
    render() {
        return (
            <div className="AuthorItem">
                <div className="AuthorName">{this.props.name}</div>
                <ul>
                    {this.props.songList.map((song) => (
                        <li>
                            song.name
                        </li>
                    ))}
                </ul>
            </div>
        );
    }
}