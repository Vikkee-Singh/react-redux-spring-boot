import { all, call, put, takeLatest } from "redux-saga/effects";
import { registerReqConst } from "../constants/registerConstants";
import {
  registerSucess,
  registerReject
} from "../actions/registerAction";
import request, { pararmeter } from "../utils/request";
import { BaseAction } from "../reducers";
import { API_ENDPOINTS } from "../appConfig";

export function* registerReqFunction(params: BaseAction) {
  const { name, number, password, username } = params.payload;
  // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.register}`;
  // Set Request Parameters
  const requestParam: pararmeter = {
    method: "post",
    url: requestURL,
    headers: {
      "Content-Type": "application/json"
    },
    data: { name, number, password, username, parent: 50021 }
  };

  try {
    const response = yield call(request, requestParam);

    if (response.status >= 200 && response.status < 300) {
      yield put(registerSucess({ success: response.data }));
    }
  } catch (err) {
    yield put(registerReject("User Already Exists! Please choose another Username"));
  }
}

export function* watchRegisterUser() {
  yield takeLatest(registerReqConst, registerReqFunction);
}

// single entry point to start all Sagas at once
export default function* defaultSaga() {
  yield all([call(watchRegisterUser)]);
}
