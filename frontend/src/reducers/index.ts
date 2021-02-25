
import { combineReducers } from "@reduxjs/toolkit";
import authReducer from "./authReducer";
import dashboardReducer from "./dashboardReducer";
import reportReducer from "./reportReducer";
import teamMembersReducer from "./teamMembersReducer";
import dncReducer from "./dncReducer";
import taskReducer from "./taskReducer";
import packsReducer from "./packsReducer";

const combineRedusers = {
  auth: authReducer,
  packs: packsReducer,
  dashboard: dashboardReducer,
  report: reportReducer,
  teamMember: teamMembersReducer,
  dnc: dncReducer,
  tasks: taskReducer,
};

export let rootReducer = combineReducers({
  ...combineRedusers,
});

export default function createReducer(injectedReducers = {}) {
  rootReducer = combineReducers({
    ...combineRedusers,
    ...injectedReducers,
  });

  return rootReducer;
}

export type RootState = ReturnType<typeof rootReducer>;

export interface BaseAction {
  type: string;
  payload?: any;
}
