import React, { useState, useContext } from "react";
import { Button, Toast } from "react-bootstrap";
import TableDataContext from "../../contaxtAPIs/TableDataContext";

interface propType {
    copyToClickBoard?: () => void;
}

function ButtonGroups({ copyToClickBoard }: propType) {
    const { clickAction, handleChangeAction } = useContext(TableDataContext);

    const copyTableText = (action: string) => handleChangeAction(action);

    return (
        <div>
            <Button
                className="memListButton"
                variant="dark"
                size="sm"
                onClick={() => copyTableText("copy")}
            >
                Copy
            </Button>
            <Button
                className="memListButton"
                variant="light"
                size="sm"
                onClick={() => copyTableText("csv")}
            >
                csv
            </Button>
            <Button
                className="memListButton"
                variant="success"
                size="sm"
                onClick={() => copyTableText("xls")}
            >
                Excel
            </Button>
            <Button
                className="memListButton"
                variant="danger"
                size="sm"
                onClick={() => copyTableText("pdf")}
            >
                PDF
            </Button>
            <Button
                className="memListButton"
                variant="info"
                size="sm"
                onClick={() => copyTableText("print")}
            >
                Print
            </Button>
        </div>
    )
}

export default ButtonGroups;
