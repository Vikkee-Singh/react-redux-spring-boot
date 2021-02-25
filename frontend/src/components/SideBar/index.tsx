import React, { useState } from "react";
import { Link, useHistory, withRouter } from "react-router-dom";
import { SidebarData } from "./SidebarData.js";
import "./sidebar.scss";
import Clock from '../Clock';
import { Button } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { handleShowSideBar } from "../../actions/loginAction";
import { RootState } from "../../reducers";
import callLogo from '../../assets/images/firstlogo.png';
import ProfileDropdown from "../ProfileDropdown";
import SubscribtionModal from "../SubscribtionModal";

function SideBar() {
  const [showSubModal, setShowSubModal] = useState(false);
  const dispatch = useDispatch();
  let { location: { pathname } } = useHistory();
  const { showSideBar } = useSelector((state: RootState) => state.auth.toJS());

  const openSubsModal = () => setShowSubModal(!showSubModal);

  return (
    <>
      <div className="side-wrapper">
        <nav id="sidebar">
          <div className="sidebar-header">
            <img src={callLogo} alt="" height="32" width="32" />
            <span className={showSideBar ? "showTextCollepse" : "hideTextCollepse"}>
              <strong>{`My Dialer`}</strong>
            </span>
          </div>

          <ul className="list-unstyled components">
            {!!SidebarData && SidebarData.length && SidebarData.map((sidebar, index) => (<li
              key={`${sidebar.title}${index}`}>
              <Link className={!!(pathname === sidebar.path) ? "active" : "inactive"} to={sidebar.path}>
                {sidebar.icon}
                <span className={showSideBar ? "showTextCollepse" : "hideTextCollepse"}>
                  {sidebar.title || ""}
                </span>
              </Link>
            </li>
            ))}

          </ul>
        </nav>

        <div className={showSideBar ? "content" : "expendContent"}>

          <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <div className="container-fluid">
              <div className="left">
                <Button
                  onClick={() => dispatch(handleShowSideBar())}
                  type="button"
                  id="sidebarCollapse"
                  className="btn btn-info"
                >
                  <i className="fa fa-bars"></i>
                </Button>
                {/* Subscribe now button */}
                <Button
                  // style={{ marginRight: "10px" }}
                  onClick={() => openSubsModal()}
                >
                  <span>Subscribe Now</span>
                </Button>
                <Clock />
              </div>

              <ProfileDropdown />

              <SubscribtionModal show={showSubModal} handleClose={openSubsModal} />

            </div>
          </nav>

        </div>
      </div>
    </>
  );
}

export default withRouter(SideBar);