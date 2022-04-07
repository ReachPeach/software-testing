import './Header.css'
import React from 'react';

export class NavigationBar extends React.Component {
    render() {
        return (
            <div className="NavigationBar">
                <div className="Navigate">
                    <nav>
                        <a href='/library'>Library</a>
                    </nav>
                </div>
            </div>
        )
    }
}