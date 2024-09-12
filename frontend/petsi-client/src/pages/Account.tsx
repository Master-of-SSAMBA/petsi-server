import { headerState } from "@/stores/useNavigationStore";
import { useEffect } from "react";
import { useSetRecoilState } from "recoil";

const Account = () => {
    const setHeaderState = useSetRecoilState(headerState);

    useEffect(() => {
        setHeaderState({
            left: "backArrow",
            right: "alertActive",
            text: "나의 계좌",
            isVisible: true,
        });
    }, [setHeaderState]);

    return <h1>계좌 화면</h1>;
};

export default Account;
