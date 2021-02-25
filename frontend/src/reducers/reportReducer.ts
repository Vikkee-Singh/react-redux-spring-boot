import { fromJS } from "immutable";
import { BaseAction } from "./index";
import { defaultState } from "../utils/common";
import {
  reportCallReqConst,
  reportCallSucConst,
  reportCallFailConst,
} from "../constants/reportConstants";

const initialState = fromJS({
  callLogs: defaultState
});

function reportReducer(state = initialState, action: BaseAction) {
  switch (action.type) {

    case reportCallReqConst:
      return state
        .setIn(["callLogs", "request"], action.payload || {})
        .setIn(["callLogs", "isFetching"], true);
    case reportCallSucConst:
      return state
        .setIn(["callLogs", "result"], action.payload || {})
        .setIn(["callLogs", "isFetching"], false);
    case reportCallFailConst:
      return state
        .setIn(["callLogs", "error"], action.payload || "")
        .setIn(["callLogs", "isFetching"], false);

    default:
      return state;
  }
}
export default reportReducer;
