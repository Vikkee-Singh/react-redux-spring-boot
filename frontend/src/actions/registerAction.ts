import {
  registerReqConst,
  registerSucConst,
  registerFailConst,
  cleanSuccssConst
} from "../constants/registerConstants";

export function registerRequest(payload: any) {
  return {
    type: registerReqConst,
    payload
  };
}

export function registerSucess(payload: any) {
  return {
    type: registerSucConst,
    payload,
  };
}

export function registerReject(payload: any) {
  return {
    type: registerFailConst,
    payload,
  };
}

export function cleanSuccssResult() {
  return { type: cleanSuccssConst };
}