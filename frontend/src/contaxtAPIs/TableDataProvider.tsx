import React, { Component } from 'react';
import TableDataContext, { initialState } from "./TableDataContext"

class TableDataProvider extends Component {
    state = initialState;
    render() {
        return (
            <TableDataContext.Provider
                value={{
                    clickAction: this.state.clickAction,
                    handleChangeAction: (clickAction: string) => this.setState({ clickAction })
                }}
            >
                {this.props.children}
            </TableDataContext.Provider>
        );
    }
}

export default TableDataProvider;
