interface StateMapItem {
    title: string;
    subtitle?: string;
    btn: number[];
}

type StateMap = {
    [key: string]: StateMapItem;
};

// ButtonMap 인터페이스 정의
interface ButtonMapItem {
    color: string;
    label: string;
}

type ButtonMap = {
    [key: number]: ButtonMapItem;
};

export const stateMap: StateMap = {
    PictureSaveWarning: {
        title: "인증 내역을 저장하시겠어요?",
        subtitle: "저장된 인증 내역은 수정 및 삭제가 불가능합니다",
        btn: [0, 1],
    },
    PictureSaved: {
        title: "인증 내역이 저장되었습니다.",
        btn: [1],
    },
};

export const buttonMap: ButtonMap = {
    0: { color: "lightgray", label: "취소" },
    1: { color: "pink", label: "확인" },
    2: { color: "yellow", label: "변경" },
};
