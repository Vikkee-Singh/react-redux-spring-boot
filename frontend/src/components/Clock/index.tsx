import React, { useEffect, useState } from "react";
import moment from "moment";
const format = "DD-MMMM-YYYY hh:mm:ss A";
function Clock() {
    const [time, setTime] = useState(moment().format(format));

    useEffect(() => { updateTime(); }, []);

    const updateTime = () => setInterval(tick, 1000);

    const tick = () => setTime(moment().format(format));

    return <><span style={{ fontSize: '0.6rem' }}>{time || ""}</span></>
}

export default Clock;
