import { fromJS } from "immutable";
import { BaseAction } from "./index";
import { defaultState } from "../utils/common";
import * as constants from "../constants/teamMembersConstants";

const initialState = fromJS({
  teamMembers: defaultState,
  addMember: {
    request: {},
    isFetching: false,
    success: "",
    error: ""
  },
});

function teamMembersReducer(state = initialState, action: BaseAction) {
  switch (action.type) {

    case constants.teamMembersReqConst:
      return state
        .setIn(["teamMembers", "request"], action.payload || {})
        .setIn(["teamMembers", "isFetching"], true);
    case constants.teamMembersSucConst:
      return state
        .setIn(["teamMembers", "result"], action.payload || {})
        .setIn(["teamMembers", "isFetching"], false);
    case constants.teamMembersFailConst:
      return state
        .setIn(["teamMembers", "error"], action.payload || "")
        .setIn(["teamMembers", "isFetching"], false);


    // add Member reducer
    case constants.addMemReqConst:
      return state
        .setIn(["addMember", "request"], action.payload || {})
        .setIn(["addMember", "isFetching"], true);
    case constants.addMemSucConst:
      return state
        .setIn(["addMember", "success"], action.payload || "")
        .setIn(["addMember", "isFetching"], false);
    case constants.addMemFailConst:
      return state
        .setIn(["addMember", "error"], action.payload || "")
        .setIn(["addMember", "isFetching"], false);
    case constants.clearAddMemConst:
      return state
        .setIn(["addMember", "success"], "")
        .setIn(["addMember", "error"], "");

    default:
      return state;
  }
}
export default teamMembersReducer;
