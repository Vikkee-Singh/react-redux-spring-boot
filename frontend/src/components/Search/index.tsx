import React, { useEffect, useState } from "react";
import { Form } from "react-bootstrap";

interface Search {
    handleChange: (text: string) => void
}

function Search(props: Search) {
    const [value, setValue] = useState("");

    const handleOnChange = (value: string) => {
        setValue(value);
    };

    useEffect(() => {
        const timeoutId = setTimeout(() => {
            props.handleChange(value);
        }, 1000);
        return () => clearTimeout(timeoutId);
    }, [value]);


    return (
        <>
            <Form.Control
                type="text"
                value={value}
                onChange={({ target: { value } }) => handleOnChange(value)}
            />
        </>
    )
}
export default Search;
