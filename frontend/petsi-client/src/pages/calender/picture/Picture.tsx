import { headerState } from "@/stores/useNavigationStore";
import { useEffect } from "react";
import { useSetRecoilState } from "recoil";
import Container from "@/components/ui/_container/Container";
import styled from "styled-components";
import { AppPaths } from "@/interfaces/AppPaths";

const Layout = styled.div`
    display: flex;
    width: 100%;
    align-items: center;
    justify-content: center;
`;
const Picture = () => {
    const setHeaderState = useSetRecoilState(headerState);

    useEffect(() => {
        setHeaderState({
            left: "backArrow",
            right: "alertActive",
            text: "매일 인증하기",
            isVisible: true,
        });
    }, [setHeaderState]);

    return (
        <Layout>
            <Container color="white" shadow={true} path={AppPaths.LOGIN}>
                Hello <br /> hello hello
            </Container>
        </Layout>
    );
};

export default Picture;
