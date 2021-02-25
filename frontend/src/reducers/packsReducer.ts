import { fromJS } from "immutable";
import { BaseAction } from "./index";
import { defaultState } from "../utils/common";
import * as constants from "../constants/packsConstants";

const initialState = fromJS({
  packs: defaultState,
});

function packsReducer(state = initialState, action: BaseAction) {
  switch (action.type) {
    // packs list reducer
    case constants.packsReqConst:
      return state
        .setIn(["packs", "isFetching"], true);
    case constants.packsSucConst:
      return state
        .setIn(["packs", "result"], action.payload || {})
        .setIn(["packs", "isFetching"], false);
    case constants.packsFailConst:
      return state
        .setIn(["packs", "error"], action.payload || "")
        .setIn(["packs", "isFetching"], false);

    default:
      return state;
  }
}
export default packsReducer;
