import { all, call, put, takeLatest } from "redux-saga/effects";
import { API_ENDPOINTS } from "../appConfig";
import { dashBoardReqConst, membersReqConst } from "../constants/dashboardConstants";
import request, { pararmeter } from "../utils/request";
import {
  dashboardSucess, dashboardReject, membersSucess,
  membersReject
} from "../actions/dashboardAction";
import { BaseAction } from "../reducers";
import { auth } from "../utils/auth";

export function* dashboardReqFun(params: BaseAction) {
  const { userid } = params.payload;
  // // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.dashboard}/${userid}`;
  // Set Request Parameters
  const requestParam: pararmeter = {
    method: "get",
    url: requestURL,
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${auth.getToken()}`
    },
  };

  try {
    const response = yield call(request, requestParam);
    if (response.status >= 200 && response.status < 300) {
      yield put(dashboardSucess(response.data));
    } else {
      throw new Error("No error!");
    }
  } catch (err) {
    yield put(dashboardReject(err.message));
  }
}

export function* getMemberdReqFun() {
  // const { userid } = params.payload;
  // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.agent}/list`;
  // Set Request Parameters
  const requestParam: pararmeter = {
    method: "get",
    url: requestURL,
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${auth.getToken()}`
    },
  };

  try {
    const response = yield call(request, requestParam);
    if (response.status >= 200 && response.status < 300) {
      yield put(membersSucess(response.data));
    } else {
      throw new Error("No error!");
    }
  } catch (err) {
    yield put(membersReject(err.message));
  }
}


export function* watchDashboardReqs() {
  yield takeLatest(membersReqConst, getMemberdReqFun);
  yield takeLatest(dashBoardReqConst, dashboardReqFun);
}

// single entry point to start all Sagas at once
export default function* defaultSaga() {
  yield all([call(watchDashboardReqs)]);
}
