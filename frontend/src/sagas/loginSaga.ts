import { all, call, delay, put, takeLatest } from "redux-saga/effects";
import { API_ENDPOINTS } from "../appConfig";
import { loginReqConst } from "../constants/loginConstants";
import request, { pararmeter } from "../utils/request";
import { loginSucess, loginReject } from "../actions/loginAction";
import { auth } from "../utils/auth";
import { BaseAction } from "../reducers";

export function* loginReqFunction(params: BaseAction) {
  const { email, password } = params.payload;
  // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.login}`;
  // Set Request Parameters
  const requestParam: pararmeter = {
    method: "post",
    url: requestURL,
    headers: {
      "Content-Type": "application/json"
    },
    data: { password, username: email }
  };

  if (email === "demo@demo.com" && password === "demo") {
    yield put(loginSucess({
      "message": "Success",
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1MDAyMTt2aWtrZWVzaW5naDMwQGdtYWlsLmNvbSIsInNjb3BlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpc3MiOiJodHRwOi8vdmlvbDguY29tIiwiaWF0IjoxNjA5NTY4MzA4LCJleHAiOjE2MDk1ODYzMDh9.hG0zhTLtjo-C0Zj6cO8lNqz-CPlWBiCHsw3Gs5xtJrw",
      "userid": "50021",
      "role": [
        "ROLE_USER"
      ]
    }));
    yield put(delay(1000));
    yield all([
      call(auth.setToken, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1MDAyMTt2aWtrZWVzaW5naDMwQGdtYWlsLmNvbSIsInNjb3BlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpc3MiOiJodHRwOi8vdmlvbDguY29tIiwiaWF0IjoxNjA5NTY4MzA4LCJleHAiOjE2MDk1ODYzMDh9.hG0zhTLtjo-C0Zj6cO8lNqz-CPlWBiCHsw3Gs5xtJrw"),
      call(auth.setUserInfo, {
        "userid": "50021",
        "role": ["ROLE_USER"]
      })
    ]);
  } else {
    try {
      const response = yield call(request, requestParam);

      if (response.status >= 200 && response.status < 300) {

        yield all([
          call(auth.setToken, response.data.token),
          call(auth.setUserInfo, {
            "userid": response.data.userid,
            "role": response.data.role
          })
        ]);
        yield put(loginSucess(response.data));
        window.location.reload();
      }
    } catch (err) {
      yield put(loginReject(err.message));
    }
  }


}

export function* watchLoginUser() {
  yield takeLatest(loginReqConst, loginReqFunction);
}

// single entry point to start all Sagas at once
export default function* defaultSaga() {
  yield all([call(watchLoginUser)]);
}
