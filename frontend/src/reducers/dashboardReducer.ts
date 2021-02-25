import { fromJS } from "immutable";
import { BaseAction } from "./index";
import { defaultState } from "../utils/common";
import {
  dashBoardReqConst,
  dashBoardSucConst,
  dashBoardFailConst,
  membersReqConst,
  membersSucConst,
  membersFailConst
} from "../constants/dashboardConstants";

const initialState = fromJS({
  dashboard: defaultState,
  members: defaultState
});

function dashboardReducer(state = initialState, action: BaseAction) {
  switch (action.type) {

    case dashBoardReqConst:
      return state
        .setIn(["dashboard", "request"], action.payload || {})
        .setIn(["dashboard", "isFetching"], true);
    case dashBoardSucConst:
      return state
        .setIn(["dashboard", "result"], action.payload || {})
        .setIn(["dashboard", "isFetching"], false);
    case dashBoardFailConst:
      return state
        .setIn(["dashboard", "error"], action.payload || "")
        .setIn(["dashboard", "isFetching"], false);

    case membersReqConst:
      return state
        .setIn(["members", "request"], action.payload || {})
        .setIn(["members", "isFetching"], true);
    case membersSucConst:
      return state
        .setIn(["members", "result"], action.payload || {})
        .setIn(["members", "isFetching"], false);
    case membersFailConst:
      return state
        .setIn(["members", "error"], action.payload || "")
        .setIn(["members", "isFetching"], false);
    default:
      return state;
  }
}
export default dashboardReducer;
