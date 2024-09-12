import styled from "styled-components";

interface ContainerProps {
    $color: string;
    $shadow: boolean;
    children?: React.ReactNode;
}

export const Container = styled.div<ContainerProps>`
    display: flex;
    justify-content: center;
    align-items: center;
    width: 90%;
    border: 1px solid var(--color-black);
    border-radius: 5px;
    background-color: ${(props) => `var(--color-${props.$color})`};
    box-shadow: ${(props) =>
        props.$shadow ? "var(--box-shadow-default)" : "none"};
    padding: 2rem;
`;
