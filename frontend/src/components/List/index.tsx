import React, { useContext, useEffect } from "react";
import { Table } from "react-bootstrap";
import TableDataContext from "../../contaxtAPIs/TableDataContext";
import { pdf } from "../../utils/common";
interface propType {
    colums: Array<any>;
    rows: Array<any>;
}

function List({ colums, rows }: propType) {
    const { clickAction, handleChangeAction } = useContext(TableDataContext);
    const copyToClipboard = () => {
        let el: any = document.getElementById('listTable')
        let body: any = document.body;
        let range: any = null;
        let sel: any = null;
        if (document.createRange && window.getSelection) {
            range = document.createRange();
            sel = window.getSelection();
            sel.removeAllRanges();
            try {
                range.selectNodeContents(el);
                sel.addRange(range);
            } catch (e) {
                range.selectNode(el);
                sel.addRange(range);
            }
            document.execCommand("copy");

        } else if (body.createTextRange) {
            range = body.createTextRange();
            range.moveToElementText(el);
            range.select();
            range.execCommand("Copy");
        }
    }
    const textTableContent = () => {
        let data = "";
        const tableData = [];
        const rows: any = document.querySelectorAll("table tr");
        for (const row of rows) {
            const rowData = [];
            for (const [index, column] of row.querySelectorAll("th, td").entries()) {
                if ((index + 1) % 3 === 0) rowData.push('"' + column.innerText + '"');
                else rowData.push(column.innerText);
            }
            tableData.push(rowData.join(","));
        }
        data += tableData.join("\n");
        return data;
    }
    const downLoadCSV = () => {
        let data = textTableContent();
        const a = document.createElement("a");
        a.href = URL.createObjectURL(new Blob([data], { type: "text/csv" }));
        a.setAttribute("download", "My Dialer.csv");
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    };
    const downLoadExcel = () => {
        let downloadLink;
        let dataType = 'application/vnd.ms-excel';
        let tableSelect: any = document.getElementById("listTable");
        let tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');
        // Specify file name
        let filename: string = "My Dialer.xls";
        // Create download link element
        downloadLink = document.createElement("a");
        document.body.appendChild(downloadLink);
        if (navigator.msSaveOrOpenBlob) {
            let blob = new Blob(['\ufeff', tableHTML], { type: dataType });
            navigator.msSaveOrOpenBlob(blob, filename);
        } else {
            // Create a link to the file
            downloadLink.href = 'data:' + dataType + ', ' + tableHTML;
            // Setting the file name
            downloadLink.download = filename;
            //triggering the function
            downloadLink.click();
        }
    }
    const downLoadPDF = () => {
        const colString = colums.map((col) => col.name)
        const headers: Array<any> = [];
        const tableData: Array<any> = [];
        const rows: any = document.querySelectorAll("table tr");

        rows.forEach((row: any, idx: number) => {
            const rowData = [];
            for (const [index, column] of row.querySelectorAll("th, td").entries()) {
                if ((index + 1) % 3 === 0) rowData.push(column.innerText);
                else rowData.push(column.innerText);
            }
            if (idx === 0) {
                headers.push(rowData.filter(obj => obj !== "Action"));
            } else {
                if (colString.includes("Action")) {
                    rowData.splice(-1, 1);
                    tableData.push(rowData);
                } else {
                    tableData.push(rowData);
                }
            }
        });
        pdf({
            data: tableData,
            headers,
            fileName: "My Dialer.pdf"
        })
    }
    const downLoadPrint = () => {
        let tableSelect: any = document.getElementById("listTable");
        //Get the HTML of div
        const divElements: any = tableSelect.innerHTML;
        var winPrint: any = window.open('', '', 'left=0,top=0,width=800,height=600,toolbar=0,scrollbars=0,status=0');
        winPrint.document.write(`<title>My Dialer</title><br /><br />${divElements}`);
        winPrint.document.close();
        winPrint.focus();
        winPrint.print();
    }
    useEffect(() => {
        switch (clickAction) {
            case "copy":
                copyToClipboard();
                break;
            case "csv":
                downLoadCSV();
                break;
            case "xls":
                downLoadExcel();
                break;
            case "pdf":
                downLoadPDF();
                break;
            case "print":
                downLoadPrint();
                break;
            default:
                break;
        }
        handleChangeAction("")
    }, [JSON.stringify(clickAction)])

    return (
        <>
            <Table hover id="listTable">
                <thead>
                    <tr>
                        {!!(colums && colums.length) && colums.map((col, index) => (
                            <th
                                key={
                                    `${col.name || "#"}${index}`
                                }
                            >
                                {col.name || ""}
                            </th>
                        )
                        )}
                    </tr>
                </thead>
                <tbody>
                    {!!(rows && rows.length) && rows.map((row, index) => (
                        <tr key={`${JSON.stringify(row)}${index}`}>
                            {!!(colums && colums.length) && colums.map((col, idx) => (
                                <td
                                    // colSpan={row.colSpan || 1}
                                    key={
                                        `${index}${col.data || "#"}${idx}`
                                    }
                                >
                                    {!!(row.hasOwnProperty(`${col["key"]}`) && !col.hasOwnProperty('render')) && row[`${col["key"]}`]}

                                    {col.hasOwnProperty('render') &&
                                        <div>{col['render'](row)}</div>
                                    }
                                </td>
                            )
                            )}
                        </tr>
                    ))}
                </tbody>
            </Table>
        </>
    )

}

export default List