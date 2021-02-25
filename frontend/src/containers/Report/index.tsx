import React, { useEffect, useMemo } from "react";
import { Button, Form } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { reportCallRequest } from "../../actions/reportAction";
import List from "../../components/List";
import { RootState } from "../../reducers";
import { auth } from "../../utils/auth";
import "./report.scss";

const column = [
  { name: "Agent Mobile", key: "agentNo" },
  { name: "Customer Mobile", key: "customerNo" },
  { name: "Call Time", key: "ansTime" },
  { name: "Call duration", key: "leadTalkDuration" },
];

function Report() {
  const { showSideBar } = useSelector((state: RootState) => state.auth.toJS());

  const { callLogs } = useSelector((state: RootState) => state.report.toJS());

  const dispatch = useDispatch();
  useEffect(() => {
    let user: any = auth.getUserInfo();
    dispatch(reportCallRequest({ userid: user["userid"] }));
  }, []);

  const callLogList = useMemo(() => {
    return (
      <>
        <List
          colums={column}
          rows={
            (callLogs["result"] && Object.entries(callLogs["result"]).length)
              ? callLogs["result"] : []
          }
        />
      </>
    )
  }, [JSON.stringify(callLogs["result"])])

  return (
    <>
      <div className={showSideBar ? "childContainerWithSidebar" : "childContainerWithOutSidebar"}>
        <div className="card">
          <div>Call Logs</div>
          <section className="report-filter-section">
            <div>
              <Form>
                <Form.Group controlId="selectMember">
                  <Form.Label>Select Member</Form.Label>
                  <Form.Control as="select" custom>
                    <option>Select Member</option>
                    <option>Rohit</option>
                  </Form.Control>
                </Form.Group>
              </Form>
            </div>
            <div>
              <Form>
                <Form.Group controlId="startDate">
                  <Form.Label>Start Date</Form.Label>
                  <Form.Control type="date" name="start-date" placeholder="select start date" />
                </Form.Group>
              </Form>
            </div>
            <div>
              <Form>
                <Form.Group controlId="endDate">
                  <Form.Label>End Date</Form.Label>
                  <Form.Control type="date" name="end-date" placeholder="select end date" />
                </Form.Group>
              </Form>
            </div>
            <div className="filterButton">
              <Button>Filter</Button>
            </div>
          </section>
          {callLogList}
        </div>
      </div>
    </>
  );
}

export default Report;