import { all, call, put, takeLatest } from "redux-saga/effects";
import { API_ENDPOINTS } from "../appConfig";
import { packsReqConst } from "../constants/packsConstants";
import request, { pararmeter } from "../utils/request";
import { packsSucess, packsReject } from "../actions/packsAction";
import { auth } from "../utils/auth";

export function* packsReqFun() {
  // // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.packList}`;
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
    console.log("response +++++", response.data);

    if (response.status >= 200 && response.status < 300) {
      yield put(packsSucess(response.data));
    } else {
      throw new Error("No error!");
    }
  } catch (err) {
    yield put(packsReject(err.message));
  }
}

export function* watchPacksReqs() {
  yield takeLatest(packsReqConst, packsReqFun);
}

// single entry point to start all Sagas at once
export default function* defaultSaga() {
  yield all([call(watchPacksReqs)]);
}
