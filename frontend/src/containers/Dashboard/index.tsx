import React, { useEffect, useMemo, useState } from "react";
import Button from "react-bootstrap/esm/Button";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../reducers";
import { cleanSuccssResult } from "../../actions/registerAction";
import { dashboardRequest, membersRequest } from "../../actions/dashboardAction";
import { MDBIcon } from "mdbreact";
import { auth } from "../../utils/auth";
import Login from "../Login";
import Register from "../Register";
import "./dashboard.scss";
import List from "../../components/List";
import { Chart } from "react-google-charts";

const column = [
  {
    name: "",
    key: "",
    // default: () => (<div style={{
    //   backgroundColor: "red"
    // }}>Hello</div>)
    // render: () => (<CgIcons.CgProfile />)
    render: () => (<div style={{ height: '3vw', width: '2vw' }}> <MDBIcon className="custom-icon" icon="user-circle" /> </div>)
  },
  { name: "Name", key: "agentName" },
  // { name: "Mobile", key: "agentNumber" },

  { name: "Total Calls", key: "total_call", render: (data: any) => (<span>{data["total_call"] || 0}</span>) },
  { name: "Today Call", key: "today_call", render: (data: any) => (<span>{data["today_call"] || 0}</span>) },
  { name: "from date", key: "created_date" },
  // { name: "Average Call Duration", key: "average_call_dur", render: (data: any) => (<span>{data["average_call_dur"] || 0}</span>) },
];

const lastCallcolumn = [
  { name: "Call By", key: "call_by" },
  { name: "Customer Contact", key: "customer contact" },
  { name: "Call To", key: "call_to" },
  { name: "Date", key: "created_date" },
  { name: "Time", key: "created_time" },
];

const dummyTableData = [
  {
    "user_id": 50023,
    "username": "vikkeesingh10@gmail.com",
    "name": "vikkee",
    "mobile": "767688768",
    "email": "vikkeesingh10@gmail.com",
    "pincode": 0,
    "credits_available": 0,
    "credits_used": 0,
    "userId": 50023,
    "creditsAvailable": 0,
    "creditsUsed": 0
  },
  {
    "user_id": 50024,
    "username": "vikkeesingh11@gmail.com",
    "name": "vikkee singh",
    "mobile": "767688768",
    "email": "vikkeesingh11@gmail.com",
    "pincode": 0,
    "credits_available": 0,
    "credits_used": 0,
    "userId": 50024,
    "creditsAvailable": 0,
    "creditsUsed": 0
  }
];

function Dashboard() {
  const dispatch = useDispatch();
  const [state, setState] = useState("login");
  const handleUserAccess = (state: string) => setState(state);

  const { registerData, showSideBar, userData } = useSelector((state: RootState) => state.auth.toJS());
  const { dashboard, members } = useSelector((state: RootState) => state.dashboard.toJS());

  useEffect(() => {
    if (auth.isLogedIn()) {
      if (userData["result"] && Object.entries(userData["result"]).length) {
        dispatch(dashboardRequest({ userid: userData["result"]["userid"] }));
        dispatch(membersRequest({ userid: userData["result"]["userid"] }));
      } else if (auth.getUserInfo()) {
        let user: any = auth.getUserInfo();
        dispatch(dashboardRequest({ userid: user["userid"] }));
        dispatch(membersRequest({ userid: user["userid"] }));
      }
    }
  }, [JSON.stringify(userData["result"])]);

  useEffect(() => {
    if (registerData["result"] && registerData["result"]["success"] && registerData["result"]["success"] === "User Created Successfully") {
      if (state === "register") {
        setTimeout(() => {
          setState("login");
          dispatch(cleanSuccssResult());
        }, 3000);
      }
    }
  }, [JSON.stringify(registerData["result"])]);

  const activeMember = useMemo(() => {
    return (
      <>
        <div className="header"><strong>Active</strong> Members</div>
        <List
          colums={column}
          rows={(members["result"] && Object.entries(members["result"]).length) ? members["result"] : dummyTableData}
        />
      </>
    )
  }, [JSON.stringify(members["result"])])

  const lastCallByMember = useMemo(() => {
    return (
      <>
        <div className="header"><strong>Last Call</strong> By Member</div>
        <List
          colums={lastCallcolumn}
          rows={[]}
        />
      </>
    )
  }, [JSON.stringify(members["result"])])

  return (
    <>
      {!!!auth.isLogedIn() && <div className="loginCard">
        {!!(state === "login") && (
          <>
            <Login />
            <div className="rgstr-btn splits">
              <p> Don't have an account? </p>
              <Button id="round-btn" onClick={() => handleUserAccess("register")}>Register</Button>
            </div>
          </>
        )}
        {!!(state === "register") && (
          <>
            <div className="login-btn splits">
              <p> Already an user? </p>
              <Button id="round-btn" onClick={() => handleUserAccess("login")}>Login</Button>
            </div>
            <Register />
          </>
        )}
      </div>}
      {!!auth.isLogedIn() && <div className={showSideBar ? "childContainerWithSidebar" : "childContainerWithOutSidebar"}>
        <div className="dashboardDetails">
          <div className="recordBox">
            <div>Total Calls</div>
            <div className="number">{dashboard["result"] && dashboard["result"]["totalCalls"] && dashboard["result"]["totalCalls"] || 0}</div>
            <div className="columnChart">
              <Chart
                width="40vh"
                height="10vw"
                chartType="ColumnChart"
                data={[['', ''], ['', 4], ['', 13], ['', 10], ['', 5], ['', 7], ['', 9], ['', 15], ['', 8], ['', 3], ['', 1],
                ]}
                options={{ colors: ['#ffc107'] }}
              />
            </div>
          </div>
          <div className="recordBox">
            <div>Total Members</div>
            <div className="number">
              {dashboard["result"] && dashboard["result"]["totalUsers"] && dashboard["result"]["totalUsers"] || 0}
            </div>
            <div className="barChart"><Chart
              chartType="LineChart"
              width="40vh"
              height="10vw"
              data={[['', ''], [0, 0], [1, 2], [2, 0], [3, 2], [4, 1], [5, 2], [6, 0], [7, 2],
              ]}
              options={{ colors: ['#17a2b8'] }}
            /></div>
          </div>
          <div className="recordBox">
            <div>Total Contacts</div>
            <div className="number">
              {dashboard["result"] && dashboard["result"]["connectedCalls"] && dashboard["result"]["connectedCalls"] || 0}
            </div>
            <Chart
              chartType="PieChart"
              width="40vh"
              height="10vw"
              data={[
                ['Language', 'Speakers (in millions)'], ['', 5.85], ['', 1.66], ['', 0.316], ['', 0.0791],
              ]}
              options={{
                legend: 'none',
                pieSliceText: 'label',
              }}
            />
          </div>
        </div>

        <section style={{ display: "flex" }}>
          <div className="memberList">
            {activeMember}
          </div>
          <div className="report">
            <div className="recordBox">
              <div><strong>Call Report</strong></div>
              <div className="repotListheader">
                <div>Today</div>
                <div>Today</div>
                <div>Today</div>
              </div>
              <div className="repotListvalue">
                <div>0</div>
                <div>0</div>
                <div>0</div>
              </div>
            </div>
            <div className="recordBox">
              <div><strong>Contact Report</strong></div>
              <div className="repotListheader">
                <div>Today</div>
                <div>Today</div>
                <div>Today</div>
              </div>
            </div>
            <div className="recordBox">
              <div><strong>List Report</strong></div>
              <div className="repotListheader">
                <div>Today</div>
                <div>Today</div>
                <div>Today</div>
              </div>
            </div>
          </div>
        </section>


        <section style={{ display: "flex", justifyContent: "space-between" }}>

          <div className="report">
            <div className="recordBox">
              <div className="header"><strong>Today Call</strong> Status</div>

            </div>
          </div>

          <div className="memberList">
            {lastCallByMember}
          </div>
        </section>

      </div>
      }
    </>
  );
}

export default Dashboard;
