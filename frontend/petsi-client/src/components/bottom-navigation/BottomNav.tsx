import { NavLink } from "react-router-dom";
import * as St from "./BottomNav.style";
import Home from "@/assets/icons/Icon-home.svg";
import Receipt from "@/assets/icons/Icon-receipt.svg";
import Money from "@/assets/icons/Icon-money.svg";
import Calendar from "@/assets/icons/Icon-calendar.svg";
import User from "@/assets/icons/Icon-user.svg";
import { useRecoilValue } from "recoil";
import { bottomState } from "@/stores/useNavigationStore";

const BottomNav = () => {
    const { isVisible } = useRecoilValue(bottomState); // Recoil 상태 가져오기

    if (!isVisible) return null;

    return (
        <St.NavContainer>
            <NavLink to="/">
                <St.NavItem>
                    <St.ItemIcon src={Home} />
                    <St.ItemTitle>홈</St.ItemTitle>
                </St.NavItem>
            </NavLink>
            <NavLink to="#">
                <St.NavItem>
                    <St.ItemIcon src={Receipt} />
                    <St.ItemTitle>소비분석</St.ItemTitle>
                </St.NavItem>
            </NavLink>
            <NavLink to="/account">
                <St.NavItem>
                    <St.ItemIcon src={Money} />
                    <St.ItemTitle>계좌</St.ItemTitle>
                </St.NavItem>
            </NavLink>
            <NavLink to="#">
                <St.NavItem>
                    <St.ItemIcon src={Calendar} />
                    <St.ItemTitle>캘린더</St.ItemTitle>
                </St.NavItem>
            </NavLink>
            <NavLink to="#">
                <St.NavItem>
                    <St.ItemIcon src={User} />
                    <St.ItemTitle>마이페이지</St.ItemTitle>
                </St.NavItem>
            </NavLink>
        </St.NavContainer>
    );
};

export default BottomNav;
