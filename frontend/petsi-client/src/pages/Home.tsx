import { headerState } from "@/stores/useNavigationStore";
import { useEffect } from "react";
import { NavLink } from "react-router-dom";
import { useSetRecoilState } from "recoil";

const Home = () => {
    const setHeaderState = useSetRecoilState(headerState);

    useEffect(() => {
        setHeaderState({
            left: "",
            right: "",
            text: "",
            isVisible: false,
        });
    }, [setHeaderState]);

    return (
        <>
            <h1>Home</h1>
            <NavLink to="/login">로그인 페이지로 이동하기</NavLink>
            <NavLink to="/account">계좌 페이지로 이동하기</NavLink>
        </>
    );
};

export default Home;
