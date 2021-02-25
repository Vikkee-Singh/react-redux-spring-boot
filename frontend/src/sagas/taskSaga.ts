import { all, call, put, takeLatest } from "redux-saga/effects";
import { API_ENDPOINTS } from "../appConfig";
import {
  taskReqConst, addTaskReqConst,
  deleteTaskReqConst, changeTaskStatus
} from "../constants/tasksConstants";
import request, { pararmeter } from "../utils/request";
import {
  taskSucess, taskReject, addTaskSucess, addTaskReject,
  deleteTaskSucess, deleteTaskReject, taskRequest
} from "../actions/taskAction";
import { BaseAction } from "../reducers";
import { auth } from "../utils/auth";

export function* getTasksSaga(params: BaseAction) {
  const { userid } = params.payload;
  // // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.taskList}/${userid}`;
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
      yield put(taskSucess(response.data));
    } else {
      throw new Error("No error!");
    }
  } catch (err) {

    yield put(taskReject(err.message));
  }
}

export function* addTaskSaga(params: BaseAction) {
  const { name, description, user_id, files } = params.payload;
  const bodyFormData = new FormData();
  bodyFormData.append("file", files[0]);
  bodyFormData.append("task", JSON.stringify({ name, description, user_id }));
  // // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.taskList}`;
  // Set Request Parameters
  const requestParam: pararmeter = {
    method: "post",
    url: requestURL,
    headers: {
      "Content-Type": "multipart/form-data",
      // "Accept": "application/form",
      "Authorization": `Bearer ${auth.getToken()}`
    },
    data: bodyFormData
  };

  console.log("requestParam", requestParam);

  try {
    const response = yield call(request, requestParam);

    if (response.status >= 200 && response.status < 300) {
      yield put(addTaskSucess(response.data));
    } else {
      throw new Error("No error!");
    }
  } catch (err) {
    console.log("Error ", err.message);

    yield put(addTaskReject(err.message));
  }

}

export function* deleteTaskSaga(params: BaseAction) {
  const { task_id } = params.payload;
  // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.taskList}/${task_id}`;
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
      yield put(deleteTaskSucess(response.data));
    } else {
      throw new Error("No error!");
    }
  } catch (err) {
    yield put(deleteTaskReject(err.message));
  }
}

export function* changeTaskStatusSaga(params: BaseAction) {
  const { status, task_id, userid } = params.payload;
  // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.taskList}/${task_id}`;
  // Set Request Parameters
  const requestParam: pararmeter = {
    method: "put",
    url: requestURL,
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": `Bearer ${auth.getToken()}`
    },
    data: status
  };

  try {
    const response = yield call(request, requestParam);
    console.log("Response", response);

    if (response.status >= 200 && response.status < 300) {
      yield put(taskRequest({ userid }));
    } else {
      throw new Error("No error!");
    }

  } catch (err) {
    alert("A Task is allready running");
    console.log("err ====> ", err.message);
  }
}

export function* watchdncReqs() {
  yield takeLatest(taskReqConst, getTasksSaga);
}
export function* watchAddTaskReqs() {
  yield takeLatest(addTaskReqConst, addTaskSaga);
}
export function* watchDeleteTaskReqs() {
  yield takeLatest(deleteTaskReqConst, deleteTaskSaga);
}
export function* watchChangeTaskStatusReqs() {
  yield takeLatest(changeTaskStatus, changeTaskStatusSaga);
}
// single entry point to start all Sagas at once
export default function* defaultSaga() {
  yield all([
    call(watchdncReqs),
    call(watchAddTaskReqs),
    call(watchDeleteTaskReqs),
    call(watchChangeTaskStatusReqs)
  ]);
}

