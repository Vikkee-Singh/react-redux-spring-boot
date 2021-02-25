import { all, fork } from "redux-saga/effects";
import loginSaga from "./loginSaga";
import registerSaga from "./registerSaga";
import dashboardSaga from "./dashboardSaga";
import reportSaga from "./reportSaga";
import teamMemSaga from "./teamMemSaga";
import dncSaga from "./dncSaga";
import taskSaga from "./taskSaga";
import packsSaga from "./packsSaga";


// Individual exports for testing
export default function* rootSaga() {
  // See example in containers/HomePage/saga.js
  yield all([
    fork(loginSaga),
    fork(registerSaga),
    fork(reportSaga),
    fork(dashboardSaga),
    fork(teamMemSaga),
    fork(dncSaga),
    fork(taskSaga),
    fork(packsSaga),
  ]);
}
