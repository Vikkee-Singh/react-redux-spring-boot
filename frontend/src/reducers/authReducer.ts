import { fromJS } from "immutable";
import { BaseAction } from "./index";
import { defaultState } from "../utils/common";
import {
  loginReqConst,
  loginSucConst,
  loginFailConst,
  handleShowSidebarConst
} from "../constants/loginConstants";
import {
  registerReqConst,
  registerSucConst,
  registerFailConst,
  cleanSuccssConst
} from "../constants/registerConstants";

const initialState = fromJS({
  showSideBar: true,
  userData: defaultState,
  registerData: defaultState
});

function authReducer(state = initialState, action: BaseAction) {
  switch (action.type) {
    case handleShowSidebarConst:
      return state.set("showSideBar", !state.toJS().showSideBar);
    case loginReqConst:
      return state
        .setIn(["userData", "request"], action.payload || {})
        .setIn(["userData", "isFetching"], true);
    case loginSucConst:
      return state
        .setIn(["userData", "result"], action.payload || {})
        .setIn(["userData", "isFetching"], false);
    case loginFailConst:
      return state
        .setIn(["userData", "error"], action.payload || "")
        .setIn(["userData", "isFetching"], false);

    case registerReqConst:
      return state
        .setIn(["registerData", "request"], action.payload || {})
        .setIn(["registerData", "isFetching"], true);
    case registerSucConst:
      return state
        .setIn(["registerData", "result"], action.payload || {})
        .setIn(["registerData", "isFetching"], false);
    case registerFailConst:
      return state
        .setIn(["registerData", "error"], action.payload || "")
        .setIn(["registerData", "isFetching"], false);
    case cleanSuccssConst:
      return state
        .setIn(["registerData", "request"], {})
        .setIn(["registerData", "error"], null)
        .setIn(["registerData", "result"], {});
    default:
      return state;
  }
}
export default authReducer;
