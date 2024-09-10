import { atom } from "recoil";

export interface HeaderState {
    left: string;
    right: string;
    text: string;
    isVisible: boolean;
}

export const headerState = atom<HeaderState>({
    key: "headerState", // Recoil 상태의 고유 key
    default: {
        left: "",
        right: "",
        text: "",
        isVisible: false,
    },
});
