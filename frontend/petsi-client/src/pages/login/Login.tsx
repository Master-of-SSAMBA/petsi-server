import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { bottomState, headerState } from "@/stores/useNavigationStore";
import { useSetRecoilState } from "recoil";
import Input from "@/components/ui/_input/Input";
import Button from "@/components/ui/_button/Button";
import { AppPaths } from "@/interfaces/AppPaths";
import * as St from "./Login.style";

interface LoginProps {}

const Login = ({}: LoginProps) => {
    const setHeaderState = useSetRecoilState(headerState);
    const setBottomState = useSetRecoilState(bottomState);
    const navigate = useNavigate();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
        setHeaderState({
            left: "backArrow",
            right: "empty",
            text: "로그인",
            isVisible: true,
        });
        setBottomState({
            isVisible: false,
        });
    }, [setHeaderState, setBottomState]);

    const handleEmailChange = (val: string) => setEmail(val);
    const handlePwChange = (val: string) => setPassword(val);

    return (
        <St.Layout>
            <St.TitleMsg>Welcome to Petsi !</St.TitleMsg>
            <St.InputSection>
                <Input
                    type="text"
                    value={email}
                    onChange={handleEmailChange}
                    label="이메일"
                    shadow={false}
                />
                <Input
                    type="password"
                    value={password}
                    onChange={handlePwChange}
                    label="비밀번호"
                    shadow={false}
                />
                <St.FindPassword
                    onClick={() => {
                        navigate(AppPaths.FIND_PASSWORD);
                    }}
                >
                    비밀번호를 잊으셨나요?
                </St.FindPassword>
            </St.InputSection>
            <St.BtnSection>
                <Button color="yellow" text="로그인" shadow={true}/>
                <Button
                    color="lightgray"
                    text="회원가입"
                    to={AppPaths.SIGNUP}
                    shadow={true}
                />
            </St.BtnSection>
        </St.Layout>
    );
};

export default Login;
