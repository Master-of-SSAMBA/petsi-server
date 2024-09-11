import styled from "styled-components";

interface InputProps {
    shadow: boolean;
}

export const InputContainer = styled.div`
    position: relative;
    width: 100%;
    margin-top: 15px; // 라벨 공간 확보
`;

export const InputField = styled.input<InputProps>`
    width: 100%;
    height: 60px;
    padding: 10px;
    border: 1px solid var(--color-gray);
    border-radius: 5px;
    font-size: var(--font-body-h4);
    outline: none;
    transition: all 0.3s;
    box-shadow: ${(props) => props.shadow ? 'var(--box-shadow-default)' : 'none'};

    &:focus {
        border-color: var(--color-black);
        border: 2px solid var(--color-black);
    }

    &:focus + label,
    &:not(:placeholder-shown) + label {
        top: 1px;
        left: 10px;
        font-size: var(--font-body-h5);
        color: var(--color-black);
    }
`;

export const InputLabel = styled.label`
    position: absolute;
    left: 10px;
    top: 50%;
    transform: translateY(-50%);
    color: var(--color-gray);
    font-size: 16px;
    transition: all 0.3s;
    padding: 0 5px;
    background-color: white;
    pointer-events: none;
`;
