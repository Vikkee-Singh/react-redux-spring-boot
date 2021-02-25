import React, { useEffect, useState } from "react";
import { RootState } from "../../reducers";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";
import { useSelector, useDispatch } from "react-redux";
import { registerRequest, cleanSuccssResult } from "../../actions/registerAction";

interface credentials {
  username: string;
  name: string;
  number: string;
  password: string;
  re_enter_password: string
}

const initialValue: credentials = {
  username: "",
  name: "",
  number: "",
  password: "",
  re_enter_password: ""
};

function Register() {
  const dispatch = useDispatch();
  const { registerData } = useSelector((state: RootState) => state.auth.toJS());

  const registrationSubmit = () => {
    dispatch(registerRequest({ ...state }));
  };

  const [validated, setValidated] = useState(false);
  const [state, setState] = useState(initialValue);
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    if (!success && registerData["result"] && registerData["result"]["success"] && success !== registerData["result"]["success"] && registerData["result"]["success"] === "User Created Successfully") {
      setState({ ...initialValue });
      setSuccess("User Created Successfully");
    }
  }, [JSON.stringify(registerData["result"])]);

  useEffect(() => {
    if (!error && registerData["error"] && error !== registerData["error"] && registerData["error"] === "User Already Exists! Please choose another Username") {
      setError(registerData["error"]);
      setTimeout(function () {
        dispatch(cleanSuccssResult());
        setError("");
      }, 3000);
    }
  }, [JSON.stringify(registerData["error"])]);

  const handleChange = (name: string, value: string) => {
    setState({ ...state, [name]: value });
  };
  const submitRegisterForm = (event: any) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();
    if (form.checkValidity() === false && state.password === state.re_enter_password) {
      setValidated(true);
    } else {
      registrationSubmit();
    }
  };

  return (
    <div className="wrapper move">
      {!!success && <Alert variant="primary">{success}</Alert>}
      {!!error && <Alert variant="danger">{error}</Alert>}

      <Form noValidate validated={validated} onSubmit={submitRegisterForm} id="register">
        <h3>Register</h3>
        <Form.Group controlId="formBasicEmail">
          <Form.Label>Email / User Name</Form.Label>
          <Form.Control
            required
            as="input"
            type="email"
            name="username"
            onChange={({ target: { value, name } }) =>
              handleChange(name, value)
            }
          />
        </Form.Group>

        <Form.Group controlId="formBasicPassword">
          <Form.Label>Re-enter Password</Form.Label>
          <Form.Control
            required
            name="password"
            type="password"
            onChange={({ target: { value, name } }) =>
              handleChange(name, value)
            }
          />
        </Form.Group>

        <Form.Group controlId="formBasicPassword">
          <Form.Label>Re-enter Password</Form.Label>
          <Form.Control
            required
            name="re_enter_password"
            type="password"
            onChange={({ target: { value, name } }) =>
              handleChange(name, value)
            }
          />
        </Form.Group>

        <Form.Group controlId="formBasicPassword">
          <Form.Label>Full Name</Form.Label>
          <Form.Control
            required
            name="name"
            type="text"
            onChange={({ target: { value, name } }) =>
              handleChange(name, value)
            }
          />
        </Form.Group>

        <Form.Group controlId="formBasicPassword">
          <Form.Label>Contact</Form.Label>
          <Form.Control
            required
            name="number"
            type="number"
            onChange={({ target: { value, name } }) =>
              handleChange(name, value)
            }
          />
        </Form.Group>

        <Form.Group
          controlId="formBasicPassword"
          style={{ display: "flex", justifyContent: "center" }}
        >
          <Button variant="primary" type="submit" className="btn btn-danger waves-effect">
            REGISTER
            </Button>
        </Form.Group>
      </Form>
    </div>
  );
}

export default Register;
