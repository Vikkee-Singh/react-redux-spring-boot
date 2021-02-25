import { all, call, put, takeLatest } from "redux-saga/effects";
import { API_ENDPOINTS } from "../appConfig";
import { teamMembersReqConst, addMemReqConst } from "../constants/teamMembersConstants";
import request, { pararmeter } from "../utils/request";
import {
  teamMemberSucess, teamMemberReject, addMemSucess,
  addMemReject
} from "../actions/teamMembersAction";
import { BaseAction } from "../reducers";
import { auth } from "../utils/auth";

export function* teamMemReqFun(params: BaseAction) {
  const { userid } = params.payload;
  // // Base Url of Get Data Api.
  const requestURL = `${API_ENDPOINTS.member}/${userid}`;
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
      yield put(teamMemberSucess(response.data));
    } else {
      throw new Error("No error!");
    }
  } catch (err) {
    yield put(teamMemberReject(err.message));
  }
}

export function* addMemberReqFun(params: BaseAction) {
  const { name, number, password, username, addAsMember } = params.payload;
  // Set Request Parameters
  const requestParam: pararmeter = {
    method: "post",
    url: `${API_ENDPOINTS.agent}`,
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": `Bearer ${auth.getToken()}`
    },
    data: { agentName: name, agentNumber: number, agentEmail: username }
  };
  let user: any = auth.getUserInfo();
  const addReqAddRegiterParam: pararmeter = {
    method: "post",
    url: `${API_ENDPOINTS.register}`,
    headers: {
      "Content-Type": "application/json",
      "Accept": "application/json",
      "Authorization": `Bearer ${auth.getToken()}`
    },
    data: { name, number, password, username, parent: user["userid"] }
  };

  try {
    console.log("requestParam@@@@@@@@@@", requestParam);
    const response = yield call(request, requestParam);
    console.log("response@@@@@@@@@@", response);

    if (response.status >= 200 && response.status < 300) {
      yield put(addMemSucess(response.data['message']));
    }

    if (addAsMember) {
      var userAdded = yield call(request, addReqAddRegiterParam);
    }

    console.log("userAdded", userAdded);

  } catch (err) {
    console.log(err.message);

    yield put(addMemReject("User Already Exists! Please choose another Username"));
  }
}


export function* watchTeamMemReqs() {
  yield takeLatest(teamMembersReqConst, teamMemReqFun);
}

export function* watchAddMemReqs() {
  yield takeLatest(addMemReqConst, addMemberReqFun);
}

// single entry point to start all Sagas at once
export default function* defaultSaga() {
  yield all([call(watchTeamMemReqs), call(watchAddMemReqs)]);
}
