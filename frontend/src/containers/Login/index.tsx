import React, { useEffect, useState } from "react";
import { RootState } from "../../reducers";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/esm/Alert";
import { useSelector, useDispatch } from "react-redux";
import { loginRequest } from "../../actions/loginAction";
import { auth } from "../../utils/auth";

interface credentials {
  email: string;
  password: string;
}

const initialValue: credentials = {
  email: "",
  password: "",
};

function Login() {
  const dispatch = useDispatch();
  const { userData } = useSelector((state: RootState) => state.auth.toJS());

  const [validated, setValidated] = useState(false);
  const [state, setState] = useState(initialValue);
  const [success, setSuccess] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    if (userData["result"] && userData["result"]["message"] && userData["result"]["message"] === "Success" && !auth.isLogedIn() && success !== "Successfully Logedin!") {
      setSuccess("Successfully Logedin!");
      setError("");
    }
  }, [JSON.stringify(userData["result"])]);

  useEffect(() => {
    if (userData["error"] && error !== userData["error"]) {
      setError(userData["error"]);
      setSuccess("");
    }
  }, [JSON.stringify(userData["error"])]);

  const handleChange = (name: string, value: string) => {
    setState({ ...state, [name]: value });
  };
  const submitLoginForm = (event: any) => {
    const form = event.currentTarget;
    event.preventDefault();
    event.stopPropagation();
    if (form.checkValidity() === false) setValidated(true);
    else dispatch(loginRequest({ ...state }));
  };

  return (
    <div className="wrapper">
      {!!success && <Alert variant="primary">{success}</Alert>}
      {!!error && <Alert variant="danger">{error}</Alert>}

      <Form noValidate validated={validated} onSubmit={submitLoginForm} id="login">
        <h3>Login</h3>
        <Form.Group controlId="formBasicEmail">
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
        </Form.Group>

        <Form.Group controlId="formBasicPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            required
            name="password"
            type="password"
            onChange={({ target: { value, name } }) =>
              handleChange(name, value)
            }
          />
        </Form.Group>

        <Form.Group
          controlId="formBasicPassword"
          style={{ display: "flex", justifyContent: "center" }}
        >
          <Button variant="primary" type="submit" className="btn btn-info waves-effect">
            LOGIN
          </Button>
        </Form.Group>
      </Form>
    </div>
  );
}

export default Login;
