import React, { useEffect, useMemo, useState } from "react";
import { Button } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { teamMemberRequest } from "../../actions/teamMembersAction";
import List from "../../components/List";
import ButtonGroups from "../../components/ButtonGroups";
import { RootState } from "../../reducers";
import { auth } from "../../utils/auth";
import "./teamMember.scss";
import * as BsIcons from "react-icons/bs";
import Search from "../../components/Search";
import { searchFromArrayBykey } from "../../utils/common";
import AddTeamMemModal from "./addTeamMemModal";

const column = [
  {
    name: "",
    key: "",
    default: () => (<div style={{
      backgroundColor: "red"
    }}>Hello</div>)
  },
  { name: "Name", key: "name" },
  { name: "Email", key: "email" },
  { name: "Mobile", key: "mobile" },
  { name: "Team", key: "team" },
  { name: "From date", key: "created_date" },
];

function TeamMember() {
  const [show, setShow] = useState(false);

  const [tableData, setTableData] = useState([]);

  const { showSideBar } = useSelector((state: RootState) => state.auth.toJS());

  const { teamMembers } = useSelector((state: RootState) => state.teamMember.toJS());

  useEffect(() => {
    if (JSON.stringify(tableData) !== JSON.stringify(teamMembers["result"])) {
      setTableData(teamMembers["result"]);
    }
  }, [JSON.stringify(teamMembers["result"]), teamMembers["isFetching"]]);

  const dispatch = useDispatch();
  useEffect(() => {
    let user: any = auth.getUserInfo();
    dispatch(teamMemberRequest({ userid: user["userid"] }));
  }, []);

  const memberList = useMemo(() => {
    return (
      <>
        <List
          colums={column}
          rows={(tableData && Object.entries(tableData).length) ? tableData : []}
        />
      </>
    )
  }, [JSON.stringify(tableData)]);

  const handleTextChange = (text: string = "") => {
    let data: any = searchFromArrayBykey(text, teamMembers["result"]);
    if (Object.entries(teamMembers["result"]).length) {
      setTableData(data);
    }
  }

  const handleCloseModal = () => setShow(!show);

  const showAddTaskModal = (
    <AddTeamMemModal
      show={show}
      handleClose={() => handleCloseModal()}
    />
  )

  return (
    <>
      <div className={showSideBar ? "childContainerWithSidebar" : "childContainerWithOutSidebar"}>
        <div className="card">
          <section className="memLisrtTitle">
            <div>Member List</div>
            <Button onClick={() => handleCloseModal()}>
              <BsIcons.BsFillPlusSquareFill />
              <span> CREATE MEMBER </span>
            </Button>
          </section>
          <section className="memLisrtfilter">
            <ButtonGroups />
            <div style={{ display: "flex" }}>
              <label style={{ marginRight: "10px" }}>Search</label>
              <Search handleChange={handleTextChange} />
            </div>
          </section>
          {memberList}
          {showAddTaskModal}
        </div>
      </div>
    </>
  );
}

export default TeamMember;

