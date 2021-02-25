import React, { useEffect, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { teamMemberRequest, addMemRequest, clearAddMemData } from "../../actions/teamMembersAction";
import { RootState } from "../../reducers";
import { auth } from "../../utils/auth";
import ModalWrapper from "../../components/ModalWrapper";

interface propType {
    show: Boolean;
    handleClose: () => void;
}
interface initialStatePro {
    name: String,
    username: String,
    password: String,
    number: String,
    addAsMember: String,
}
interface initialErrorStatePro {
    err_name: String,
    err_username: String,
    err_password: String,
    err_number: String,
}
const initialError: initialErrorStatePro = {
    err_name: "",
    err_username: "",
    err_password: "",
    err_number: "",
}
const initialState: initialStatePro = {
    name: "",
    username: "",
    password: "",
    number: "",
    addAsMember: "",
}
const validatedForm = (dncState: initialStatePro, key: String = ""): initialErrorStatePro => {
    const error: initialErrorStatePro = {
        err_name: "",
        err_username: "",
        err_password: "",
        err_number: "",
    };
    if ((!key || key === "name") && !dncState["name"]) { error["err_name"] = "Name Require!" }

    if ((!key || key === "username") && !dncState["username"]) { error["err_username"] = "Email Require!" }

    if ((!key || key === "password") && !dncState["password"]) { error["err_password"] = "Password Require!" }

    if ((!key || key === "number") && !dncState["number"]) { error["err_number"] = "Number Require!" }

    return error
}

function AddTeamMemModal(props: propType) {
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

    const { teamMembers: { result: users }, addMember: { success: addMemSccess, error: errAddMemError } } = useSelector((state: RootState) => state.teamMember.toJS());

    const dispatch = useDispatch();

    useEffect(() => {
        if (!Object.entries(users).length) {
            let user: any = auth.getUserInfo();
            dispatch(teamMemberRequest({ userid: user["userid"] }));
        }
    }, []);

    useEffect(() => {
        if (addMemSccess) {
            setTimeout(() => {
                let user: any = auth.getUserInfo();
                dispatch(teamMemberRequest({ userid: user["userid"] }));
                dispatch(clearAddMemData());
                props.handleClose();
            }, 2000);
        }
    }, [JSON.stringify(addMemSccess)]);

    const submitAddTaskForm = () => {
        const errorObj: initialErrorStatePro = validatedForm(state);

        if ((!errorObj["err_name"]) && !errorObj["err_username"] && !errorObj["err_password"] && !errorObj["err_number"]) {
            dispatch(addMemRequest(state));
        } else {
            setError(errorObj);
        }
    };

    const footer = (
        <>
            <Button
                onClick={() => submitAddTaskForm()}
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
            {!!addMemSccess && <span className="text-success">{addMemSccess || ""}</span>}
            {!!errAddMemError && <span className="text-danger">{errAddMemError || ""}</span>}
            <Form>
                <Form.Group
                    controlId="formBasicEmail"
                >
                    <Form.Label>Full Name</Form.Label>
                    <Form.Control
                        required
                        as="input"
                        type="text"
                        name="name"
                        onChange={({ target: { value, name } }) =>
                            handleChange(name, value)
                        }
                    />
                    {!!error["err_name"] && <span className="text-danger">{error["err_name"] || ""}</span>}
                </Form.Group>

                <Form.Group controlId="formBasicDescription">
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                        required
                        name="username"
                        type="text"
                        onChange={({ target: { value, name } }) =>
                            handleChange(name, value)
                        }
                    />
                    {!!error["err_username"] && <span className="text-danger">{error["err_username"] || ""}</span>}
                </Form.Group>

                <Form.Group controlId="formBasicDescription">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        required
                        name="password"
                        type="password"
                        onChange={({ target: { value, name } }) =>
                            handleChange(name, value)
                        }
                    />
                    {!!error["err_password"] && <span className="text-danger">{error["err_password"] || ""}</span>}
                </Form.Group>

                <Form.Group controlId="formBasicDescription">
                    <Form.Label>Phone Number</Form.Label>
                    <Form.Control
                        required
                        name="number"
                        type="text"
                        onChange={({ target: { value, name } }) =>
                            handleChange(name, value)
                        }
                    />
                    {!!error["err_number"] && <span className="text-danger">{error["err_number"] || ""}</span>}
                </Form.Group>


                <Form.Group controlId="controlSelect1">
                    {/* <Form.Label>Role</Form.Label> */}
                    <Form.Check
                        type={"checkbox"}
                        id={`default-checkbox`}
                        name="addAsMember"
                        label="Add as User"
                        onChange={(evt: any) =>
                            handleChange(evt.target["name"], evt.target["checked"])
                        }
                    />
                </Form.Group>


            </Form>
        </>
    );

    return (<>
        <ModalWrapper
            show={props.show}
            handleClose={props.handleClose}
            title={"Create Member"}
            body={body}
            footer={footer}
        />
    </>
    )
}

export default AddTeamMemModal;