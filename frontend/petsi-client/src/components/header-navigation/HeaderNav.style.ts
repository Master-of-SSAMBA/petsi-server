import { styled } from "styled-components";

const NavContainer = styled.div`
    display: flex;
    width: 100%;
    padding: 2px 0px;
    justify-content: space-around;
    align-items: center;
    margin-top: 20px;
`;

const NavIcon = styled.img`
    width: 2.2857rem;
    height: 2.2857rem;
`;

const NavText = styled.h4`
    text-align: center;
`;

const PlaceholderDiv = styled.div`
    width: 2.2857rem;
    height: 2.2857rem;
`;

export { NavContainer, NavIcon, PlaceholderDiv, NavText };
