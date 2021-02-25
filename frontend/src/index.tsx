import React from "react";
import ReactDOM from "react-dom";
import { Provider } from "react-redux";
import configureStore from "./store";
import App from "./App";
import { BrowserRouter as Router } from "react-router-dom";
import TableDataProvider from "./contaxtAPIs/TableDataProvider";

const initialState = {};
export const store = configureStore(initialState);

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <Router>
        <TableDataProvider>
          <App />
        </TableDataProvider>
      </Router>
    </Provider>
  </React.StrictMode>,
  document.getElementById("root")
);
