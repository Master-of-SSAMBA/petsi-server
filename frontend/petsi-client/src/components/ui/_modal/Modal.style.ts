import styled from "styled-components";

export const ModalBackdrop = styled.div`
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    left: 50%;
    transform: translate(-50%, 0);
    display: flex;
    justify-content: center;
    align-items: center;
    background: rgba(0, 0, 0, 0.5);
    z-index: 100;
`;

export const Container = styled.div`
    display: flex;
    flex-direction: column;
    width: 82%;
    max-width: 390px;
    height: 160px;

    box-shadow: var(--box-shadow-default);
    border: 1px solid var(--color-black);
    border-radius: 5px;
    background-color: var(--color-beige);
    padding: 0 7%;
    justify-content: center;
    align-items: center;
`;

export const Title = styled.div`
    font-size: var(--font-title-h4);
    font-weight: 600;
`;

export const Subtitle = styled.div`
    font-size: var(--font-body-h5);
    color: var(--color-gray);
    margin-top: 0.5rem;
    font-weight: 400;
`;

export const BtnContainer = styled.div<{ $btnCount: number }>`
    margin-top: 1.5rem;
    display: flex;
    width: 100%;
    justify-content: ${({ $btnCount }) =>
        $btnCount === 1 ? "center" : "space-between"};
`;

export const Btn = styled.div<{ $color?: string }>`
    display: flex;
    width: calc(50% - 5px);
    height: 2.5rem;
    justify-content: center;
    align-items: center;
    border: 1px solid var(--color-black);
    border-radius: 5px;
    box-shadow: var(--box-shadow-default);
    background-color: ${(props) => `var(--color-${props.$color})`};
    font-size: var(--font-title-h4);
    font-weight: 600;
`;
