import * as constants from "../constants/tasksConstants";

export function taskRequest(payload: any) {
  return {
    type: constants.taskReqConst,
    payload
  };
}

export function taskSucess(payload: any) {
  return {
    type: constants.taskSucConst,
    payload,
  };
}

export function taskReject(payload: any) {
  return {
    type: constants.taskFailConst,
    payload,
  };
}

export function addTaskRequest(payload: any) {
  return {
    type: constants.addTaskReqConst,
    payload
  };
}

export function addTaskSucess(payload: any) {
  return {
    type: constants.addTaskSucConst,
    payload,
  };
}

export function addTaskReject(payload: any) {
  return {
    type: constants.addTaskFailConst,
    payload,
  };
}

export function clearAddTaskData() {
  return { type: constants.clearAddTaskConst };
}


export function deleteTaskRequest(payload: any) {
  return {
    type: constants.deleteTaskReqConst,
    payload
  };
}

export function deleteTaskSucess(payload: any) {
  return {
    type: constants.deleteTaskSucConst,
    payload,
  };
}

export function deleteTaskReject(payload: any) {
  return {
    type: constants.deleteTaskFailConst,
    payload,
  };
}

export function clearDeleteTaskData() {
  return { type: constants.clearDeleteTaskConst };
}

export function changeTaskStatus(payload: any) {
  return {
    type: constants.changeTaskStatus,
    payload
  };
}
