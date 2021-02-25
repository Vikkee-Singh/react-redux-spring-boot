import { all, call, put, takeLatest } from "redux-saga/effects";
import { API_ENDPOINTS } from "../appConfig";
import { dncReqConst, adddncReqConst, deletedncReqConst } from "../constants/dncConstants";
import request, { pararmeter } from "../utils/request";
import {
  dncSucess, dncReject, addDNCSucess, addDNCReject, deleteDNCSucess,
  deleteDNCReject
} from "../actions/dncAction";
import { BaseAction } from "../reducers";
import { auth } from "../utils/auth";

export function* dncSaga(params: BaseAction) {
  const { userid } = params.payload;
  // // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.dncList}/${userid}`;
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
      yield put(dncSucess(response.data));
    } else {
      throw new Error("No error!");
    }
  } catch (err) {

    yield put(dncReject(err.message));
  }
}

export function* addDNCSaga(params: BaseAction) {
  const { email, mobile, user_id, remark } = params.payload;
  // // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.addDnc}`;
  // Set Request Parameters
  const requestParam: pararmeter = {
    method: "post",
    url: requestURL,
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": `Bearer ${auth.getToken()}`
    },
    data: { email, mobile, user_id: Number(user_id), remark }
  };

  try {
    const response = yield call(request, requestParam);
    if (response.status >= 200 && response.status < 300) {
      yield put(addDNCSucess(response.data));
    } else {
      throw new Error("No error!");
    }
  } catch (err) {

    yield put(addDNCReject(err.message));
  }
}

export function* deleteDNCSaga(params: BaseAction) {
  const { dnc_id } = params.payload;
  // // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.addDnc}/${dnc_id}`;
  // Set Request Parameters
  const requestParam: pararmeter = {
    method: "delete",
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
      yield put(deleteDNCSucess(response.data));
    } else {
      throw new Error("No error!");
    }
  } catch (err) {

    yield put(deleteDNCReject(err.message));
  }
}

export function* watchdncReqs() {
  yield takeLatest(dncReqConst, dncSaga);
}
export function* watchAddDNCReqs() {
  yield takeLatest(adddncReqConst, addDNCSaga);
}
export function* watchDeleteDNCReqs() {
  yield takeLatest(deletedncReqConst, deleteDNCSaga);
}
// single entry point to start all Sagas at once
export default function* defaultSaga() {
  yield all([
    call(watchdncReqs),
    call(watchAddDNCReqs),
    call(watchDeleteDNCReqs)
  ]);
}
