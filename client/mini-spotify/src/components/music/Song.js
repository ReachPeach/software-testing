import React from "react";

export class Song extends React.Component {
    render() {
        return (
            <div className="SongItem">
                <div className="SongTitle">{this.props.name}</div>
                <ul>
                    {this.props.authorList.map((author) => (
                        <li>
                            author.name
                        </li>
                    ))}
                </ul>
            </div>
        );
    }
}