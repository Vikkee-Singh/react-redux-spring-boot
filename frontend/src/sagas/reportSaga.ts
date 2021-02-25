import { all, call, put, takeLatest } from "redux-saga/effects";
import { API_ENDPOINTS } from "../appConfig";
import { reportCallReqConst } from "../constants/reportConstants";
import request, { pararmeter } from "../utils/request";
import { reportCallSucess, reportCallReject } from "../actions/reportAction";
import { BaseAction } from "../reducers";
import { auth } from "../utils/auth";
import { sortByKey } from "../utils/common";

export function* reportsReqFun(params: BaseAction) {
  const { userid } = params.payload;
  // // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.report}/${userid}`;
  // Set Request Parameters
  const requestParam: pararmeter = {
    method: "get",
    url: requestURL,
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": `Bearer ${auth.getToken()}`
    },
  };

  try {
    const response = yield call(request, requestParam);
    if (response.status >= 200 && response.status < 300) {
      let result = sortByKey(response.data, "attemptId")
      yield put(reportCallSucess(result));
    } else {
      throw new Error("No error!");
    }
  } catch (err) {
    yield put(reportCallReject(err.message));
  }
}

export function* watchReportsReqs() {
  yield takeLatest(reportCallReqConst, reportsReqFun);
}

// single entry point to start all Sagas at once
export default function* defaultSaga() {
  yield all([call(watchReportsReqs)]);
}
