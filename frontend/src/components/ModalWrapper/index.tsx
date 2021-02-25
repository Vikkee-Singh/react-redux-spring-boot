import React from "react";
import { Modal } from "react-bootstrap";
const { Title, Header, Body, Footer } = Modal;
interface modal {
    show: Boolean,
    handleClose: () => void,
    title: String | null;
    body: JSX.Element[] | JSX.Element | null;
    footer: JSX.Element[] | JSX.Element | null;
    size?: 'sm' | 'lg' | 'xl';
}

function ModalWrapper(props: modal) {
    const { title, body, footer, show, handleClose, ...rest } = props;
    return (<>
        <Modal
            show={show}
            onHide={() => handleClose()}
            backdrop={false}
            keyboard={false}
            // size={size || "lg"}
            {...rest}
        >
            <Header closeButton>{title && <Title>{title}</Title>}</Header>

            <Body> {body && <>{body}</>}</Body>

            {footer && <Footer>{footer} </Footer>}

        </Modal>
    </>
    )
}
export default ModalWrapper;