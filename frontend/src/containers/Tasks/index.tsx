import React, { useEffect, useMemo, useState } from "react";
import { Button, OverlayTrigger, Tooltip } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { taskRequest, deleteTaskRequest, clearDeleteTaskData, changeTaskStatus } from "../../actions/taskAction";
import List from "../../components/List";
import ButtonGroups from "../../components/ButtonGroups";
import { RootState } from "../../reducers";
import { auth } from "../../utils/auth";
import "./tasks.scss";
import * as BsIcons from "react-icons/bs";
import * as AiIcons from "react-icons/ai";
import * as FcIcons from "react-icons/fc";
import * as MdIcons from "react-icons/md";
import AddTaskModal from "./addTaskModal"
import ModalWrapper from "../../components/ModalWrapper";
import Search from "../../components/Search";
import { searchFromArrayBykey } from "../../utils/common";

function Tasks() {
  const [show, setShow] = useState(false);
  const [showConfirm, setShowConfirm] = useState(false);
  const [dataToDelete, setDataToDelete] = useState({ task_id: null });
  const [tableData, setTableData] = useState([]);

  const { showSideBar } = useSelector((state: RootState) => state.auth.toJS());

  const { tasks, deleteTask } = useSelector((state: RootState) => state.tasks.toJS());

  const dispatch = useDispatch();
  useEffect(() => {
    let user: any = auth.getUserInfo();
    dispatch(taskRequest({ userid: user["userid"] }));
  }, []);

  useEffect(() => {
    setTimeout(() => {
      let user: any = auth.getUserInfo();
      if (showConfirm) handleConfTaskModal();
      dispatch(taskRequest({ userid: user["userid"] }));
      dispatch(clearDeleteTaskData());
    }, 3000);
  }, [JSON.stringify(deleteTask["success"])]);

  useEffect(() => {
    if (JSON.stringify(tableData) !== JSON.stringify(tasks["result"])) {
      setTableData(tasks["result"]);
    }
  }, [JSON.stringify(tasks["result"]), tasks["isFetching"]]);

  const handleCloseModal = () => { setShow(!show) }

  const setdatatodelete = (data: any) => {
    setDataToDelete({
      task_id: data ? data["task_id"] : null
    })
  };

  const handleConfTaskModal = () => {
    if (showConfirm) setdatatodelete(null);
    setShowConfirm(!showConfirm)
  }

  const deleteTaskFun = (data: any) => {
    handleConfTaskModal();
    setdatatodelete(data);
  };

  const confirDelete = () => {
    dispatch(deleteTaskRequest({
      task_id: dataToDelete ? dataToDelete["task_id"] : null
    }));
  }

  const handleChangeTaskStatus = (status: number, task_id: number) => {
    let user: any = auth.getUserInfo();
    dispatch(
      changeTaskStatus({
        status,
        task_id,
        userid: user["userid"]
      })
    )
  }

  const column = [
    {
      name: "",
      key: "",
      default: () => (<div style={{
        backgroundColor: "red"
      }}>Hello</div>)
    },
    {
      name: "Name",
      render: (data: any) => (
        <OverlayTrigger
          overlay={
            <Tooltip
              id="tooltip-disabled"
            >
              {data["description"] || ""}
            </Tooltip>}>
          <span className="d-inline-block">
            {data["name"] || ""}
          </span>
        </OverlayTrigger>
      )
    },
    { name: "Start Date", key: "created_at" },
    {
      name: "Status",
      render: (data: any) => (
        <>
          {(data["status"] === 0) && <span>{"Reddy"}</span>}
          {(data["status"] === 1) && <span>{"Running"}</span>}
          {(data["status"] === 2) && <span>{"Completed"}</span>}
          {(data["status"] === 3) && <span>{"Pushed"}</span>}
        </>
      )
    },
    { name: "Remaining Calls", key: "remaining_call" },
    { name: "Total Calls", key: "total_call" },
    {
      name: "Action",
      key: "",
      render: (data: any) => (
        <>
          <Button
            variant="light"
            onClick={
              () => {
                let status = !!(data["status"] === 3 || data["status"] === 0) ? 1 : 3;
                if (data["status"] === 2) status = 1;
                handleChangeTaskStatus(status, data["task_id"])
              }
            }
            style={{ marginRight: "5px" }}
          >
            {!!(data["status"] === 3 || data["status"] === 0 || data["status"] === 2) ? <FcIcons.FcStart /> : <AiIcons.AiOutlineStop />}
          </Button>
          <Button
            variant="light"
            style={{ color: `${!!(data["status"] === 2) ? "green" : "block"}`, marginRight: "5px" }}
            onClick={() => handleChangeTaskStatus(2, data["task_id"])}
            disabled={!!(data["status"] === 2)}
          >
            <MdIcons.MdDoneAll />
          </Button>
          <Button
            variant="danger"
            onClick={() => deleteTaskFun(data)}
          >
            <AiIcons.AiFillDelete />
          </Button>
        </>
      )
    },
  ];

  const taskList = useMemo(() => (
    <List
      colums={column}
      rows={(tableData && Object.entries(tableData).length) ? tableData : []}
    />
  ), [JSON.stringify(tableData)]);

  const showAddTaskModal = (
    <AddTaskModal
      show={show}
      handleClose={() => handleCloseModal()}
    />
  )

  const confirm_footer = (
    <>
      <Button
        onClick={() => confirDelete()}
        variant="danger"
      >
        DELETE
      </Button>
      <Button
        variant="text-muted"
        onClick={() => handleConfTaskModal()}
      >
        CANCEL
      </Button>
    </>
  );

  const handleTextChange = (text: string) => {
    let data: any = searchFromArrayBykey(text, tasks["result"]);
    if (Object.entries(tasks["result"]).length) {
      setTableData(data);
    }
  }

  const showConfirmDeleteTaskModal = useMemo(() => {
    return (<>
      <ModalWrapper
        show={showConfirm}
        handleClose={handleConfTaskModal}
        title={"Delete Task"}
        body={
          <>
            {!!deleteTask["success"] &&
              <div
                style={{ textAlign: "center" }}
                className="text-success"
              >
                {deleteTask["success"] || ""}
              </div>
            }
            <div>
              Are you sure, Want to delete the task!
            </div>
          </>
        }
        footer={confirm_footer}
      />
    </>
    );
  }, [showConfirm, JSON.stringify(deleteTask["success"])]);

  return (
    <>
      <div className={showSideBar ? "childContainerWithSidebar" : "childContainerWithOutSidebar"}>
        <div className="card">
          <section className="memLisrtTitle">
            <div>TaskList</div>
            <Button onClick={() => handleCloseModal()}>
              <BsIcons.BsFillPlusSquareFill />
              <span> ADD TASK </span>
            </Button>
          </section>
          <section className="memLisrtfilter">
            <ButtonGroups />
            <div style={{ display: "flex" }}>
              <label style={{ marginRight: "10px" }}>Search</label>
              <Search handleChange={handleTextChange} />
            </div>
          </section>
          {taskList}
          {showAddTaskModal}
          {showConfirmDeleteTaskModal}
        </div>
      </div>
    </>
  );
}

export default Tasks;

