import axios, { Method } from "axios";
import { auth } from "./auth";
import { store } from "../index";
// import { useDispatch } from "react-redux";
import { loginSucess } from "../actions/loginAction";
export interface pararmeter {
  method: Method;
  url: string,
  headers: any;
  data?: any;
}


/**
 * Parses the JSON returned by a network request
 *
 * @param  {object} response A response from a network request
 *
 * @return {object}          The parsed JSON from the request
 */
function errorSent(err: any): any {
  const error: any = new Error();
  error.message = "Network error";

  if (err.response["status"] === 401 && err.response["data"] && err.response["data"]["message"] && err.response["data"]["message"] === "Token Expired") {
    error.message = err.response.data.message; //"Entered credentials are incorrect. Please try again";
    auth.clearAppStorage();
    store.dispatch(loginSucess({ msg: "Loggedout" }))
  }

  throw error;
}

/**
 * Checks if a network request came back fine, and throws an error if not
 *
 * @param  {object} response   A response from a network request
 *
 * @return {object|undefined} Returns either the response, or throws an error
 */
function checkStatus(response: any): any {
  if (response.status >= 200 && response.status < 300) {
    return response;
  } else if (response.status === 204 || response.status === 205) {
    return null;
  }
  const error: any = new Error(response.statusText);
  error.response = response;
  throw error;
}

/**
 * Requests a URL, returning a promise
 *
 * @param  {string} url       The URL we want to request
 * @param  {object} [options] The options we want to pass to "fetch"
 *
 * @return {object}           The response data
 */
export default function request(params: pararmeter): any {
  return axios(params)
    .then(checkStatus)
    .catch(errorSent);
}
