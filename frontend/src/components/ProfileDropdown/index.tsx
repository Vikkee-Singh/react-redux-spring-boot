import React from "react";
import { useDispatch } from "react-redux";
import { loginSucess } from "../../actions/loginAction";
import userLogo from '../../assets/images/user.jpg';
import * as AiIcons from "react-icons/ai";
import * as BsIcons from "react-icons/bs";
import * as CgIcons from "react-icons/cg";
import * as MdIcons from "react-icons/md";
import { Dropdown } from "react-bootstrap";
import { auth } from "../../utils/auth";

function ProfileDropdown() {
  const dispatch = useDispatch();

  const logout = () => {
    auth.clearAppStorage();
    dispatch(loginSucess({ msg: "Loggedout" }));
  }

  return (
    <>
      <Dropdown>
        <Dropdown.Toggle variant="success" id="dropdown-basic">
          <img src={userLogo} alt="" height="32" width="32" />
        </Dropdown.Toggle>

        <Dropdown.Menu>
          <Dropdown.Item className="userProfile">
            <span><CgIcons.CgProfile /></span>
            <span>Profile</span>
          </Dropdown.Item>
          <Dropdown.Item className="userProfile">
            <span><BsIcons.BsChatSquareQuoteFill /></span>
        Costome Status
      </Dropdown.Item>
          <Dropdown.Item className="userProfile">
            <span> <MdIcons.MdSubscriptions /> </span>
            <span>Subscription</span>
          </Dropdown.Item>
          <Dropdown.Item className="userProfile">
            <span> <MdIcons.MdLiveHelp /> </span>
            <span>Help</span>
          </Dropdown.Item>
          <Dropdown.Item className="userProfile" onClick={() => logout()} >
            <span> <AiIcons.AiOutlinePoweroff /> </span>
            <span>Logout</span>
          </Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </>
  )
}

export default ProfileDropdown;
