import React from 'react'

export interface defaultValue {
    clickAction: string
}

export const initialState: defaultValue = {
    clickAction: ""
}

const TableDataContext: React.Context<any> = React.createContext(initialState);

export default TableDataContext