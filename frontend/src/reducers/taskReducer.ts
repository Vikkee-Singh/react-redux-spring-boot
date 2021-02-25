import { fromJS } from "immutable";
import { BaseAction } from "./index";
import { defaultState } from "../utils/common";
import * as constants from "../constants/tasksConstants";

const initialState = fromJS({
  tasks: defaultState,
  addTask: {
    request: {},
    isFetching: false,
    success: "",
    error: ""
  },
  deleteTask: {
    request: {},
    isFetching: false,
    success: "",
    error: ""
  }
});

function dncReducer(state = initialState, action: BaseAction) {
  switch (action.type) {
    // dnc list reducer
    case constants.taskReqConst:
      return state
        .setIn(["tasks", "request"], action.payload || {})
        .setIn(["tasks", "isFetching"], true);
    case constants.taskSucConst:
      return state
        .setIn(["tasks", "result"], action.payload || {})
        .setIn(["tasks", "isFetching"], false);
    case constants.taskFailConst:
      return state
        .setIn(["tasks", "error"], action.payload || "")
        .setIn(["tasks", "isFetching"], false);

    // add Task reducer

    case constants.addTaskReqConst:
      return state
        // .setIn(["addTask", "request"], action.payload || {})
        .setIn(["addTask", "isFetching"], true);

    case constants.addTaskSucConst:
      return state
        .setIn(["addTask", "success"], action.payload || "")
        .setIn(["addTask", "isFetching"], false);

    case constants.addTaskFailConst:
      return state
        .setIn(["addTask", "error"], action.payload || "")
        .setIn(["addTask", "isFetching"], false);

    case constants.clearAddTaskConst:
      return state
        .setIn(["addTask", "success"], "")
        .setIn(["addTask", "error"], "");

    // Delete Task reducer

    case constants.deleteTaskReqConst:
      return state
        .setIn(["deleteTask", "request"], action.payload || {})
        .setIn(["deleteTask", "isFetching"], true);
    case constants.deleteTaskSucConst:
      return state
        .setIn(["deleteTask", "success"], action.payload || {})
        .setIn(["deleteTask", "isFetching"], false);
    case constants.deleteTaskFailConst:
      return state
        .setIn(["deleteTask", "error"], action.payload || "")
        .setIn(["deleteTask", "isFetching"], false);
    case constants.clearDeleteTaskConst:
      return state
        .setIn(["deleteTask", "success"], "")
        .setIn(["deleteTask", "error"], "");

    default:
      return state;
  }
}
export default dncReducer;
