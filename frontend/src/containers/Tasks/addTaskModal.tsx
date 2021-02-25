import React, { useEffect, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { teamMemberRequest } from "../../actions/teamMembersAction";
import { addTaskRequest, taskRequest, clearAddTaskData } from "../../actions/taskAction";
import { RootState } from "../../reducers";
import { auth } from "../../utils/auth";
import ModalWrapper from "../../components/ModalWrapper";

interface propType {
    show: Boolean;
    handleClose: () => void;
}
interface initialStatePro {
    name: String,
    description: String,
    user_id: String | Number,
    files: any
}
interface initialErrorStatePro {
    err_name: String,
    err_user_id: String,
    err_file: String
}
const initialError: initialErrorStatePro = {
    err_name: "",
    err_user_id: "",
    err_file: ""
}
const initialState: initialStatePro = {
    name: "",
    description: "",
    user_id: "",
    files: null
}
const validatedForm = (dncState: initialStatePro, key: String = ""): initialErrorStatePro => {
    const error: initialErrorStatePro = {
        err_name: "",
        err_user_id: "",
        err_file: ""
    };
    if ((!key || key === "name") && !dncState["name"]) { error["err_name"] = "name Require!" }

    if ((!key || key === "user_id") && !dncState["user_id"]) { error["err_user_id"] = "List Require!" }

    if ((!key || key === "files") && !dncState["files"]) { error["err_file"] = "Plese select a CSV file to upload" }
    return error
}

function AddTaskModal(props: propType) {
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
    const { addTask: { success: taskSuccess, error: taskError } } = useSelector((state: RootState) => state.tasks.toJS());

    const dispatch = useDispatch();

    useEffect(() => {
        if (!Object.entries(users).length) {
            let user: any = auth.getUserInfo();
            dispatch(teamMemberRequest({ userid: user["userid"] }));
        }
    }, []);

    useEffect(() => {
        if (taskSuccess || taskError) {
            setTimeout(() => {
                let user: any = auth.getUserInfo();
                dispatch(taskRequest({ userid: user["userid"] }));
                dispatch(clearAddTaskData());
                props.handleClose();
            }, 2000);
        }
    }, [JSON.stringify(taskSuccess), JSON.stringify(taskError)]);

    const submitAddTaskForm = () => {
        const errorObj: initialErrorStatePro = validatedForm(state);

        if ((!errorObj["err_name"]) && !errorObj["err_user_id"] && !errorObj["err_file"]) {
            dispatch(addTaskRequest(state));
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
    const handleUploadFile = (evt: any) => {
        const files = evt.target.files;
        // console.log(files[0].type);
        if (files[0] && (files[0].type === "" || files[0].type === "text/csv" || files[0].type === "text/plain")) {
            setState({ ...state, files });
            setError({ ...error, err_file: "" });
        } else {
            setError({ ...error, err_file: "Text and csv file only allow to add!" })
        }
    }
    const body = (
        <>
            {!!taskSuccess && <span className="text-success">{taskSuccess || ""}</span>}
            {!!taskError && <span className="text-danger">{taskError || ""}</span>}
            <Form>
                <Form.Group
                    controlId="formBasicEmail"
                >
                    <Form.Label>Name</Form.Label>
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
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        required
                        name="description"
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

                <Form.Group controlId="formBasicFile">
                    <Form.File id="formcheck-api-regular">
                        <Form.File.Label>
                            Upload File
                        </Form.File.Label>
                        <Form.File.Input
                            onChange={handleUploadFile}
                        />
                    </Form.File>
                    {!!error["err_file"] && <span className="text-danger">{error["err_file"] || ""}</span>}
                </Form.Group>
            </Form>
        </>
    );

    return (<>
        <ModalWrapper
            show={props.show}
            handleClose={props.handleClose}
            title={"Create New Task"}
            body={body}
            footer={footer}
        />
    </>
    )
}

export default AddTaskModal;