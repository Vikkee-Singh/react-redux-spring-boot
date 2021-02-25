import {
  reportCallReqConst,
  reportCallSucConst,
  reportCallFailConst
} from "../constants/reportConstants";

export function reportCallRequest(payload: any) {

  return {
    type: reportCallReqConst,
    payload
  };
}

export function reportCallSucess(payload: any) {
  return {
    type: reportCallSucConst,
    payload,
  };
}

export function reportCallReject(payload: any) {
  return {
    type: reportCallFailConst,
    payload,
  };
}
