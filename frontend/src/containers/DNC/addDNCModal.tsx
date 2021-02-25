import React, { useEffect, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { teamMemberRequest } from "../../actions/teamMembersAction";
import { addDNCRequest, dncRequest, clearAddDNCData } from "../../actions/dncAction";
import { RootState } from "../../reducers";
import { auth } from "../../utils/auth";
import ModalWrapper from "../../components/ModalWrapper";

interface propType {
    show: Boolean;
    handleClose: () => void;
}
interface initialStatePro {
    email: String,
    mobile: String,
    user_id: String | Number,
    remark: String
}
interface initialErrorStatePro {
    err_email: String,
    err_mobile: String,
    err_user_id: String,
}
const initialError: initialErrorStatePro = {
    err_email: "",
    err_mobile: "",
    err_user_id: "",
}
const initialState: initialStatePro = {
    email: "",
    mobile: "",
    user_id: "",
    remark: "",
}
const validatedForm = (dncState: initialStatePro, key: String = ""): initialErrorStatePro => {
    const error: initialErrorStatePro = {
        err_email: "",
        err_mobile: "",
        err_user_id: "",
    };
    if ((!key || key === "email") && !dncState["email"]) { error["err_email"] = "Email Require!" }
    if ((!key || key === "mobile") && !dncState["mobile"]) { error["err_mobile"] = "Mobile Require!" }
    if ((!key || key === "user_id") && !dncState["user_id"]) { error["err_user_id"] = "List Require!" }
    return error
}

function AddDNCModal(props: propType) {
    const [state, setState] = useState(initialState);
    const [error, setError] = useState(initialError);

    const handleChange = (name: string, value: string | number) => {
        const newState = { ...state, [name]: value }
        const errorObj: initialErrorStatePro = validatedForm(newState, name);
        if (JSON.stringify(error) !== JSON.stringify(errorObj)) {
            setError({ ...errorObj });
        }
        setState({
            ...state,
            [name]: value
        })
    }

    const { teamMembers: { result: users } } = useSelector((state: RootState) => state.teamMember.toJS());
    const { addDNC: { success: dncSuccess, error: dncError } } = useSelector((state: RootState) => state.dnc.toJS());

    const dispatch = useDispatch();

    useEffect(() => {
        if (!Object.entries(users).length) {
            let user: any = auth.getUserInfo();
            dispatch(teamMemberRequest({ userid: user["userid"] }));
        }
    }, []);

    useEffect(() => {
        if (dncSuccess || dncError) {
            setTimeout(() => {
                let user: any = auth.getUserInfo();
                dispatch(dncRequest({ userid: user["userid"] }));
                dispatch(clearAddDNCData());
                props.handleClose();
            }, 2000);
        }
    }, [JSON.stringify(dncSuccess), JSON.stringify(dncError)]);

    const submitAddDNCForm = () => {
        const errorObj: initialErrorStatePro = validatedForm(state);
        if ((!errorObj["err_email"] || !errorObj["err_mobile"]) && !errorObj["err_user_id"]) {
            dispatch(addDNCRequest({ ...state }));
        } else {
            setError(errorObj);
        }
    };

    const footer = (
        <>
            <Button
                onClick={() => submitAddDNCForm()}
                variant="primary"
            >
                CREATE
                </Button>
            <Button
                variant="danger"
                onClick={() => props.handleClose()}
            >
                CANCEL
                </Button>
        </>
    );
    const body = (
        <>
            {!!dncSuccess && <span className="text-success">{dncSuccess || ""}</span>}
            {!!dncError && <span className="text-danger">{dncError || ""}</span>}
            <Form>
                <Form.Group
                    controlId="formBasicEmail"
                >
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        required
                        as="input"
                        type="email"
                        name="email"
                        onChange={({ target: { value, name } }) =>
                            handleChange(name, value)
                        }
                    />
                    {!!error["err_email"] && <span className="text-danger">{error["err_email"] || ""}</span>}
                </Form.Group>

                <Form.Group controlId="formBasicMobile">
                    <Form.Label>Mobile</Form.Label>
                    <Form.Control
                        required
                        name="mobile"
                        type="text"
                        onChange={({ target: { value, name } }) =>
                            handleChange(name, value)
                        }
                    />
                    {!!error["err_mobile"] && <span className="text-danger">{error["err_mobile"] || ""}</span>}
                </Form.Group>

                <Form.Group controlId="formBasicRemark">
                    <Form.Label>Remark</Form.Label>
                    <Form.Control
                        required
                        name="remark"
                        type="text"
                        onChange={({ target: { value, name } }) =>
                            handleChange(name, value)
                        }
                    />
                </Form.Group>

                <Form.Group controlId="controlSelect1">
                    <Form.Label>List</Form.Label>
                    <Form.Control
                        name="user_id"
                        as="select"
                        onChange={({ target: { name, value } }) => handleChange(name, value)
                        }
                    >
                        <option value="">Select</option>
                        {!!(users && Object.entries(users).length) && users.map((user: any) => (
                            <>
                                <option value={user["user_id"]}>{user["name"] || ""}</option>
                            </>
                        ))}
                    </Form.Control>
                    {!!error["err_user_id"] && <span className="text-danger">{error["err_user_id"] || ""}</span>}
                </Form.Group>

            </Form>

        </>
    );

    return (<>
        <ModalWrapper
            show={props.show}
            handleClose={props.handleClose}
            title={"Create DNC Contact"}
            body={body}
            footer={footer}
        />
    </>
    )
}

export default AddDNCModal;