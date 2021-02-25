import React, { useEffect } from "react";
import { Button, ListGroup } from "react-bootstrap";
import ModalWrapper from "../ModalWrapper";
import { packsRequest } from "../../actions/packsAction";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../reducers";
import List from "../List";

interface propType { show: Boolean; handleClose: () => void; }

function SubscribtionModal(props: propType) {
  const dispatch = useDispatch();
  const { packs: { result: packsList } } = useSelector((state: RootState) => state.packs.toJS());

  useEffect(() => {
    if (!Object.entries(packsList).length) {
      dispatch(packsRequest())
    }
  }, [props.show]);

  const column = [
    { name: "Pack", key: "packName" },
    { name: "Agents", key: "agents" },
    { name: "Price(Rs)", render: (data: any) => <span>{data.packPrice + "/-"}</span> },
    {
      name: "Action",
      render: (data: any) => <Button variant="link">Subscribe</Button>,
    },
  ];
  const body = (
    <List
      colums={column}
      rows={(packsList && Object.entries(packsList).length) ? packsList : []}
    />
  );

  const footer = <Button onClick={() => props.handleClose()}>CANCEL</Button>;
  return (
    <>
      <ModalWrapper
        show={props.show}
        handleClose={props.handleClose}
        title={"Subscription plan"}
        body={body}
        footer={footer}
        size="lg"
      />
    </>
  )
}

export default SubscribtionModal;
