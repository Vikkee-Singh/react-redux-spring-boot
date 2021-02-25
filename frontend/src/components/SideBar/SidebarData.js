import React from "react";
import * as FaIcons from "react-icons/fa";
import * as AiIcons from "react-icons/ai";
// import * as IoIcons from "react-icons/io";
import * as MdIcons from "react-icons/md";
import * as BsIcons from "react-icons/bs";

export const SidebarData = [
  {
    title: "Dashboard",
    path: "/",
    icon: <AiIcons.AiFillDashboard />,
    cName: "nav-text",
  },
  {
    title: " Member & Team",
    path: "/member",
    icon: <AiIcons.AiOutlineTeam />,
    cName: "nav-text",
  },
  {
    title: "Report",
    path: "/report",
    icon: <BsIcons.BsFileEarmarkText />,
    cName: "nav-text",
  },
  {
    title: "Dnc",
    path: "/dnc",
    icon: <MdIcons.MdBlock />,
    cName: "nav-text",
  },
  {
    title: "Tasks",
    path: "/task",
    icon: <FaIcons.FaTasks />,
    cName: "nav-text",
  },
  // {
  //   title: "List & Contacts",
  //   path: "/contact",
  //   icon: <MdIcons.MdContactPhone />,
  //   cName: "nav-text",
  // },
];
