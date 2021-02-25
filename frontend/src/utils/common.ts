import jsPDF from 'jspdf'
import autoTable from 'jspdf-autotable'

export interface defaultStateIN {
  request: Object | null;
  result: Object | null;
  error: string | null;
  isFetching: boolean;
}

export const defaultState: defaultStateIN = {
  request: {},
  result: {},
  error: null,
  isFetching: false,
};

export function sortByKey(array: Array<object>, key: string) {
  return array.sort(function (a: any, b: any) {
    var x = a[key]; var y = b[key];
    return ((x < y) ? -1 : ((x > y) ? 1 : 0));
  });
}

export function searchFromArrayBykey(nameKey: string, myArray: Array<any>): any {
  const updatedData = [];
  for (var i = 0; i < myArray.length; i++) {
    if ((myArray[i].hasOwnProperty("name") && myArray[i].name.toLowerCase().includes(nameKey.toLowerCase())) || (myArray[i].hasOwnProperty("email") && myArray[i].email.toLowerCase().includes(nameKey.toLowerCase())) || (myArray[i].hasOwnProperty("mobile") && myArray[i].mobile.toLowerCase().includes(nameKey.toLowerCase()))) {
      updatedData.push(myArray[i]);
    }
  }
  return updatedData;
}

interface pdfData {
  data: Array<any>,
  headers: Array<any>,
  fileName: string
}
export function pdf({ data, headers, fileName }: pdfData): void {
  const doc = new jsPDF("portrait", "pt", "A4");
  doc.setFontSize(15);
  doc.text("My Dialer", 40, 40);
  autoTable(doc,
    {
      startY: 50,
      head: headers,
      body: data
    }
  );
  doc.save(fileName);
}
