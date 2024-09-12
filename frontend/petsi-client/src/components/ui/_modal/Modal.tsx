import * as St from "./Modal.style";
// import CheckIcon from "@assets/Modal/CheckIcon.svg?react";
// import AlarmIcon from "@assets/Modal/AlarmIcon.svg?react";

// 별도의 파일로 분리할 수 있는 상수들
import { stateMap, buttonMap } from "./modalConstants";

interface BasicModalProps {
    state: keyof typeof stateMap;
    isOpen: boolean;
    onClose: () => void;
    onButtonClick?: () => void;
    nickname?: string;
}

const Modal = ({ state, isOpen, onClose, onButtonClick }: BasicModalProps) => {
    if (!isOpen) return null;

    const renderTitle = (state: keyof typeof stateMap) => {
        const title = stateMap[state]?.title || <></>;
        return title;
    };

    const renderSubtitle = (state: keyof typeof stateMap) => {
        return stateMap[state]?.subtitle || <></>;
    };

    const handleBtnClick = (index: number) => {
        // 취소 버튼이 아닌 경우
        if (index !== 0 && onButtonClick) {
            onButtonClick();
        }
        onClose();
    };

    const renderBtn = (state: keyof typeof stateMap) => {
        const btnArray = stateMap[state]?.btn || [];
        return btnArray.map((btnType: number, index: number) => {
            const { color, label } = buttonMap[btnType];
            return (
                <St.Btn
                    key={index}
                    $color={color}
                    onClick={() => handleBtnClick(index)}
                >
                    {label}
                </St.Btn>
            );
        });
    };

    const btnArray = stateMap[state]?.btn || [];

    return (
        <St.ModalBackdrop>
            <St.Container>
                <St.Title>{renderTitle(state)}</St.Title>
                <St.Subtitle>{renderSubtitle(state)}</St.Subtitle>
                <St.BtnContainer $btnCount={btnArray.length}>
                    {renderBtn(state)}
                </St.BtnContainer>
            </St.Container>
        </St.ModalBackdrop>
    );
};

export default Modal;
