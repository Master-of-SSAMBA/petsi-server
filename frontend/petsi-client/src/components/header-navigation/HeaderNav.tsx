import { NavLink, useNavigate } from "react-router-dom";
import BackArrow from "@/assets/icons/Icon-backarrow.svg";
import AlertActive from "@/assets/icons/Icon-alert-active.svg";
import Setting from "@/assets/icons/Icon-setting.svg";
import * as St from "./HeaderNav.style";
import { useRecoilValue } from "recoil";
import { headerState } from "@/stores/useNavigationStore";

interface RenderItem {
    icon: string;
    path?: string;
    action?: () => void;
}

const HeaderNav = () => {
    const navigate = useNavigate();
    const { left, right, text, isVisible } = useRecoilValue(headerState); // Recoil 상태 가져오기

    if (!isVisible) return null;

    const renderItem = (label: string): RenderItem | null => {
        switch (label) {
            case "backArrow":
                return {
                    icon: BackArrow,
                    action: () => navigate(-1),
                };
            case "alertActive":
                return {
                    icon: AlertActive,
                    path: "/alert",
                };
            case "setting":
                return {
                    icon: Setting,
                    path: "/setting",
                };
            default:
                return null;
        }
    };

    const leftItem = renderItem(left);
    const rightItem = renderItem(right);

    return (
        <St.NavContainer>
            {leftItem?.path ? (
                <NavLink to={leftItem.path}>
                    <St.NavIcon src={leftItem.icon} alt={left} />
                </NavLink>
            ) : (
                leftItem?.action && (
                    <button onClick={leftItem.action}>
                        <St.NavIcon src={leftItem.icon} alt={left} />
                    </button>
                )
            )}

            <h4>{text}</h4>

            {rightItem?.path && (
                <NavLink to={rightItem.path}>
                    <St.NavIcon src={rightItem.icon} alt={right} />
                </NavLink>
            )}
        </St.NavContainer>
    );
};

export default HeaderNav;
