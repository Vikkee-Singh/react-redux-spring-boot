import {
  dashBoardReqConst,
  dashBoardSucConst,
  dashBoardFailConst,
  membersReqConst,
  membersSucConst,
  membersFailConst
} from "../constants/dashboardConstants";

export function dashboardRequest(payload: any) {
  return {
    type: dashBoardReqConst,
    payload
  };
}

export function dashboardSucess(payload: any) {
  return {
    type: dashBoardSucConst,
    payload,
  };
}

export function dashboardReject(payload: any) {
  return {
    type: dashBoardFailConst,
    payload,
  };
}

export function membersRequest(payload: any) {
  return {
    type: membersReqConst,
    payload
  };
}

export function membersSucess(payload: any) {
  return {
    type: membersSucConst,
    payload,
  };
}

export function membersReject(payload: any) {
  return {
    type: membersFailConst,
    payload,
  };
}
