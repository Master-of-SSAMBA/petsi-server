import { useState, useEffect } from "react";
import { headerState } from "@/stores/useNavigationStore";
import { useSetRecoilState } from "recoil";
import Input from "@/components/ui/_input/Input";
import Button from "@/components/ui/_button/Button";
import { AppPaths } from "@/interfaces/AppPaths";
import * as St from "./Login.style";

const FindPassword = () => {
    const [email, setEmail] = useState("");
    const setHeaderState = useSetRecoilState(headerState);

    useEffect(() => {
        setHeaderState({
            left: "backArrow",
            right: "empty",
            text: "비밀번호 찾기",
            isVisible: true,
        });
    }, [setHeaderState]);

    const handleEmailChange = (val: string) => setEmail(val);

    return (
        <St.Layout>
            <St.TitleMsg>
                가입하신 이메일을 <br />
                입력해주세요
            </St.TitleMsg>
            <St.InputSection>
                <Input
                    type="text"
                    value={email}
                    onChange={handleEmailChange}
                    label="이메일"
                    shadow={false}
                />
            </St.InputSection>
            <St.BtnSection>
                <Button color="yellow" text="이메일 전송" shadow={true} />
                <Button
                    color="lightgray"
                    text="로그인하러 가기"
                    to={AppPaths.LOGIN}
                    shadow={true}
                />
            </St.BtnSection>
        </St.Layout>
    );
};

export default FindPassword;
