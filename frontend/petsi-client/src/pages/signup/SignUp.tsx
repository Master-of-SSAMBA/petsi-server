import { headerState } from "@/stores/useNavigationStore";
import { useEffect } from "react";
import { useSetRecoilState } from "recoil";

const SignUp = () => {
    const setHeaderState = useSetRecoilState(headerState);

    useEffect(() => {
        setHeaderState({
            left: "backArrow",
            right: "empty",
            text: "회원가입",
            isVisible: true,
        })
    }, [setHeaderState]);
    return (
        <h1>SignUp.tsx</h1>
    )
};

export default SignUp;
