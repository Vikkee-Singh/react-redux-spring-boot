import {
  loginReqConst,
  loginSucConst,
  loginFailConst,
  handleShowSidebarConst
} from "../constants/loginConstants";

export function loginRequest(payload: any) {
  return {
    type: loginReqConst,
    payload
  };
}

export function loginSucess(payload: any) {
  return {
    type: loginSucConst,
    payload,
  };
}

export function loginReject(payload: any) {
  return {
    type: loginFailConst,
    payload,
  };
}

export function handleShowSideBar() {
  return {
    type: handleShowSidebarConst,
  };
}
