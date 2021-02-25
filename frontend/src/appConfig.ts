import { report } from "process";


let BASE_URL: string;
if (process.env.NODE_ENV === "development") {
    BASE_URL = "http://localhost:8088";
} else if (process.env.NODE_ENV === "production") {
    BASE_URL = "http://app.mydialers.com";
} else {
    BASE_URL = "http://localhost:8088";
}

const API_PREFIX: string = `${BASE_URL}`;
export const API_ENDPOINTS = {
    register: `${API_PREFIX}/app/register`,
    login: `${API_PREFIX}/login`,
    dashboard: `${API_PREFIX}/app/dashboard`,
    member: `${API_PREFIX}/app/user`,
    report: `${API_PREFIX}/app/report`,
    dncList: `${API_PREFIX}/app/dnclist`,
    addDnc: `${API_PREFIX}/app/dnc`,
    taskList: `${API_PREFIX}/app/tasks`,
    agent: `${API_PREFIX}/app/agent`,
    packList: `${API_PREFIX}/app/packs`,
}