import { useState } from "react";
import Input from "@/components/ui/_input/Input";
import * as St from "./Login.style"

interface LoginProps {}

const Login = ({}: LoginProps) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const handleEmailChange = (val: string) => setEmail(val);
    const handlePwChange = (val: string) => setPassword(val);

    return (
        <>
            <St.Message>Welcome to Petsi !</St.Message>
            <Input
                type="text"
                value={email}
                onChange={handleEmailChange}
                label="이메일"
            />
            <Input
                type="password"
                value={password}
                onChange={handlePwChange}
                label="비밀번호"
            />
        </>
    );
};

export default Login;