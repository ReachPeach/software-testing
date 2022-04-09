import React from "react";

export const AppContext = React.createContext({});

export const AppProvider = ({children}) => {
    const reducer = (state, action) => {
        switch (action.type) {
            case "LOAD_LIBRARY":
                return {...state, todoList: action.library};
            case "LOAD_AUTHOR":
                return {...state, activeAuthor: action.author};
            default:
                return state;
        }
    };

    const [appData, appDispatch] = React.useReducer(reducer, {
        library: [],
        activeAuthor: {name: `Anonymous`},
    });

    return (
        <AppContext.Provider value={{appData, appDispatch}}>
            {children}
        </AppContext.Provider>
    );
};