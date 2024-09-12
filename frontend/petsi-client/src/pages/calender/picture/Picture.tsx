import { headerState } from "@/stores/useNavigationStore";
import { useEffect } from "react";
import { useSetRecoilState } from "recoil";
import Container from "@/components/ui/_container/Container";
import { AppPaths } from "@/interfaces/AppPaths";
import * as St from "./Picture.style";

// import Camera from "@/assets/icons/Icon-camera.svg";
// import Ticket from "@/assets/icons/Icon-ticket.svg";
// import Walk from "@/assets/icons/Icon-walk.svg";

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
        <St.ContinerLayout>
            <Container color="white" shadow={true} path={AppPaths.LOGIN}>
                사진 인증하기
            </Container>
            <Container color="white" shadow={true} path={AppPaths.LOGIN}>
                추가금리 확인하기
            </Container>
            <Container color="yellow" shadow={true} path={AppPaths.LOGIN}>
                인증 기록 모아보기
            </Container>
        </St.ContinerLayout>
    );
};

export default Picture;
