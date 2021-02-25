import * as constants from "../constants/packsConstants";

export function packsRequest() {
  return { type: constants.packsReqConst };
}

export function packsSucess(payload: any) {
  return {
    type: constants.packsSucConst,
    payload,
  };
}

export function packsReject(payload: any) {
  return {
    type: constants.packsFailConst,
    payload,
  };
}
