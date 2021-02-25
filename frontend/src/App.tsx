import React, { useEffect, useMemo } from "react";
import { useSelector } from "react-redux";
import { Switch, Route } from "react-router-dom";
import "./App.scss";
import SideBar from "./components/SideBar";
import Dashboard from "./containers/Dashboard";
import DNC from "./containers/DNC";
import Report from "./containers/Report";
import TeamMember from "./containers/TeamMember";
import Tasks from "./containers/Tasks";
import { RootState } from "./reducers";
import { auth } from "./utils/auth";
import { useHistory } from "react-router-dom";

function App() {
  const { userData } = useSelector((state: RootState) => state.auth.toJS());
  let history = useHistory();

  useEffect(() => {
    if (!auth.isLogedIn()) history.push("/");
  }, [JSON.stringify(userData["result"])]);

  return useMemo(() => (
    <>
      {!!auth.isLogedIn() && <SideBar />}
      <Switch>
        <Route path="/" exact component={Dashboard} />
        <Route path="/member" exact component={TeamMember} />
        <Route path="/report" exact component={Report} />
        <Route path="/dnc" exact component={DNC} />
        <Route path="/task" exact component={Tasks} />
      </Switch>
    </>
  ), [userData["result"]])
}

export default App;
