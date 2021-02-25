import React, { useEffect, useMemo, useState } from "react";
import { Button } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { dncRequest, deleteDNCRequest, clearDeleteDNCData } from "../../actions/dncAction";
import List from "../../components/List";
import ButtonGroups from "../../components/ButtonGroups";
import { RootState } from "../../reducers";
import { auth } from "../../utils/auth";
import "./dnc.scss";
import * as AiIcons from "react-icons/ai";
import * as BsIcons from "react-icons/bs";
import AddDNCModal from "./addDNCModal";
import ModalWrapper from "../../components/ModalWrapper";
import Search from "../../components/Search";
import { searchFromArrayBykey } from "../../utils/common";

function DNC() {
  const [tableData, setTableData] = useState([]);

  const [show, setShow] = useState(false);

  const [showConfirm, setShowConfirm] = useState(false);

  const [dataToDelete, setDataToDelete] = useState({ dnc_id: null });

  const { showSideBar } = useSelector((state: RootState) => state.auth.toJS());

  const { dnc, deleteDNC } = useSelector((state: RootState) => state.dnc.toJS());

  const dispatch = useDispatch();
  useEffect(() => {
    let user: any = auth.getUserInfo();
    dispatch(dncRequest({ userid: user["userid"] }));
  }, []);

  useEffect(() => {
    setTimeout(() => {
      let user: any = auth.getUserInfo();
      if (showConfirm) handleConfDNCModal();
      dispatch(dncRequest({ userid: user["userid"] }));
      dispatch(clearDeleteDNCData());
    }, 3000);
  }, [JSON.stringify(deleteDNC["success"])]);

  useEffect(() => {
    if (JSON.stringify(tableData) !== JSON.stringify(dnc["result"])) {
      setTableData(dnc["result"]);
    }
  }, [JSON.stringify(dnc["result"]), dnc["isFetching"]]);

  const handleCloseModal = () => { setShow(!show) }

  const setdatatodelete = (data: any) => { setDataToDelete({ dnc_id: data ? data["dnc_id"] : null }) }

  const handleConfDNCModal = () => {
    if (showConfirm) setdatatodelete(null);
    setShowConfirm(!showConfirm)
  }

  const deleteContact = (data: any) => {
    handleConfDNCModal();
    setdatatodelete(data);
  };

  const confirDelete = () => {
    dispatch(deleteDNCRequest({
      dnc_id: dataToDelete ? dataToDelete["dnc_id"] : null
    }));
  }

  const handleTextChange = (text: string) => {
    let data: any = searchFromArrayBykey(text, dnc["result"]);
    if (Object.entries(dnc["result"]).length) {
      setTableData(data);
    }
  }

  const column = [
    {
      name: "",
      key: "",
      default: () => (<div style={{
        backgroundColor: "red"
      }}>Hello</div>)
    },
    { name: "Email", key: "email" },
    { name: "Mobile", key: "mobile" },
    { name: "Remarks", key: "remark" },
    {
      name: "Action",
      key: "",
      render: (data: any) => (
        <Button variant="danger" onClick={() => deleteContact(data)}> <AiIcons.AiFillDelete /></Button>
      )
    },
  ];

  const dncList = useMemo(() => {
    return (
      <>
        <List
          colums={column}
          rows={(tableData && Object.entries(tableData).length) ? tableData : []}
        />
      </>
    )
  }, [tableData]);

  const showAddDNCModal = useMemo(() => {
    return (<>
      <AddDNCModal
        show={show}
        handleClose={handleCloseModal}
      />
    </>
    );
  }, [show]);

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
        onClick={() => handleConfDNCModal()}
      >
        CANCEL
      </Button>
    </>
  );

  const showConfirmDeleteDNCModal = useMemo(() => {
    return (<>
      <ModalWrapper
        show={showConfirm}
        handleClose={handleConfDNCModal}
        title={"Delete DNC Contact"}
        body={
          <>
            {!!deleteDNC["success"] &&
              <div
                style={{ textAlign: "center" }}
                className="text-success"
              >
                {deleteDNC["success"] || ""}
              </div>
            }
            <div>
              Are you sure, Want to delete the contact!
            </div>
          </>
        }
        footer={confirm_footer}
      />
    </>
    );
  }, [showConfirm, JSON.stringify(deleteDNC["success"])]);

  return (
    <>
      <div className={showSideBar ? "childContainerWithSidebar" : "childContainerWithOutSidebar"}>
        <div className="card">
          <section className="memLisrtTitle">
            <div>DNCList</div>
            <Button onClick={() => handleCloseModal()}>
              <BsIcons.BsFillPlusSquareFill />
              <span> ADD DNC CONTACT </span>
            </Button>
          </section>
          <section className="memLisrtfilter">
            <ButtonGroups />
            <div style={{ display: "flex" }}>
              <label style={{ marginRight: "10px" }}>Search</label>
              <Search handleChange={handleTextChange} />
            </div>
          </section>
          {dncList}
          {showAddDNCModal}
          {showConfirmDeleteDNCModal}
        </div>
      </div>
    </>
  );
}

export default DNC;
