import * as constants from "../constants/teamMembersConstants";

export function teamMemberRequest(payload: any) {
  return {
    type: constants.teamMembersReqConst,
    payload
  };
}

export function teamMemberSucess(payload: any) {
  return {
    type: constants.teamMembersSucConst,
    payload,
  };
}

export function teamMemberReject(payload: any) {
  return {
    type: constants.teamMembersFailConst,
    payload,
  };
}


export function addMemRequest(payload: any) {
  return {
    type: constants.addMemReqConst,
    payload
  };
}

export function addMemSucess(payload: any) {
  return {
    type: constants.addMemSucConst,
    payload,
  };
}

export function addMemReject(payload: any) {
  return {
    type: constants.addMemFailConst,
    payload,
  };
}

export function clearAddMemData() {
  return { type: constants.clearAddMemConst };
}