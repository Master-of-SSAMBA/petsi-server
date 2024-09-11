import styled from "styled-components";

interface BtnContainerProps {
    color: string;
    shadow: boolean;
}

export const BtnContainer = styled.button<BtnContainerProps>`
    display: flex;
    width: 100%;
    height: 50px;
    justify-content: center;
    align-items: center;
    border: 1px solid var(--color-black);
    border-radius: 5px;
    background-color: ${(props) => `var(--color-${props.color})`};
    box-shadow: ${(props) => props.shadow ? 'var(--box-shadow-default)' : 'none'};
`;

export const BtnText = styled.h4``;
