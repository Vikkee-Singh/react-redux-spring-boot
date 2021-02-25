import { fromJS } from "immutable";
import { BaseAction } from "./index";
import { defaultState } from "../utils/common";
import * as constants from "../constants/dncConstants";

const initialState = fromJS({
  dnc: defaultState,
  addDNC: {
    request: {},
    isFetching: false,
    success: "",
    error: ""
  },
  deleteDNC: {
    request: {},
    isFetching: false,
    success: "",
    error: ""
  }
});

function dncReducer(state = initialState, action: BaseAction) {
  switch (action.type) {
    // dnc list reducer
    case constants.dncReqConst:
      return state
        .setIn(["dnc", "request"], action.payload || {})
        .setIn(["dnc", "isFetching"], true);
    case constants.dncSucConst:
      return state
        .setIn(["dnc", "result"], action.payload || {})
        .setIn(["dnc", "isFetching"], false);
    case constants.dncFailConst:
      return state
        .setIn(["dnc", "error"], action.payload || "")
        .setIn(["dnc", "isFetching"], false);

    // add dnc reducer
    case constants.adddncReqConst:
      return state
        .setIn(["addDNC", "request"], action.payload || {})
        .setIn(["addDNC", "isFetching"], true);
    case constants.adddncSucConst:
      return state
        .setIn(["addDNC", "success"], action.payload || {})
        .setIn(["addDNC", "isFetching"], false);
    case constants.adddncFailConst:
      return state
        .setIn(["addDNC", "error"], action.payload || "")
        .setIn(["addDNC", "isFetching"], false);
    case constants.clearAddDNCConst:
      return state
        .setIn(["addDNC", "success"], "")
        .setIn(["addDNC", "error"], "");
    // Delete dnc reducer
    case constants.deletedncReqConst:
      return state
        .setIn(["deleteDNC", "request"], action.payload || {})
        .setIn(["deleteDNC", "isFetching"], true);
    case constants.deletedncSucConst:
      return state
        .setIn(["deleteDNC", "success"], action.payload || {})
        .setIn(["deleteDNC", "isFetching"], false);
    case constants.deletedncFailConst:
      return state
        .setIn(["deleteDNC", "error"], action.payload || "")
        .setIn(["deleteDNC", "isFetching"], false);
    case constants.clearDeleteDNCConst:
      return state
        .setIn(["deleteDNC", "success"], "")
        .setIn(["deleteDNC", "error"], "");

    default:
      return state;
  }
}
export default dncReducer;
