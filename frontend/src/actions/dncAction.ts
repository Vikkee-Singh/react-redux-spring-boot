import * as constants from "../constants/dncConstants";

export function dncRequest(payload: any) {
  return {
    type: constants.dncReqConst,
    payload
  };
}

export function dncSucess(payload: any) {
  return {
    type: constants.dncSucConst,
    payload,
  };
}

export function dncReject(payload: any) {
  return {
    type: constants.dncFailConst,
    payload,
  };
}

export function addDNCRequest(payload: any) {
  return {
    type: constants.adddncReqConst,
    payload
  };
}

export function addDNCSucess(payload: any) {
  return {
    type: constants.adddncSucConst,
    payload,
  };
}

export function addDNCReject(payload: any) {
  return {
    type: constants.adddncFailConst,
    payload,
  };
}

export function clearAddDNCData() {
  return { type: constants.clearAddDNCConst };
}


export function deleteDNCRequest(payload: any) {
  return {
    type: constants.deletedncReqConst,
    payload
  };
}

export function deleteDNCSucess(payload: any) {
  return {
    type: constants.deletedncSucConst,
    payload,
  };
}

export function deleteDNCReject(payload: any) {
  return {
    type: constants.deletedncFailConst,
    payload,
  };
}

export function clearDeleteDNCData() {
  return { type: constants.clearDeleteDNCConst };
}
