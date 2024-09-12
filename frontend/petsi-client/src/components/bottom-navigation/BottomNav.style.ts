import styled from "styled-components";

const NavContainer = styled.div`
    position: fixed;
    top: 714px;
    display: flex;
    width: 374px;
    height: 122px;
    justify-content: space-around;
    align-items: center;
    z-index: 1000;
    border-top: 1px solid var(--Schemes-Scrim, #000);
`;

const NavItem = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`;

const ItemIcon = styled.img``;

const ItemTitle = styled.h6`
    margin: 0;
`;

export { NavContainer, NavItem, ItemIcon, ItemTitle };
