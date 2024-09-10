import styled from "styled-components";

export const InputContainer = styled.div`
  position: relative;
  margin-bottom: 20px;
  width: 100%;
  max-width: 300px;
`;

export const InputField = styled.input`
  width: 100%;
  padding: 10px;
  border: 1px solid var(--color-black);
  border-radius: 5px;
  font-size: 16px;
  outline: none;
  transition: border-color 0.3s;

  &:focus {
    border-color: var(--color-black);;
  }

  &:focus + label,
  &:not(:placeholder-shown) + label {
    transform: translateY(-18px) translateX(-5px) scale(0.8);
    background-color: white;
    padding: 0 5px;
  }
`;

export const InputLabel = styled.label`
  position: absolute;
  left: 10px;
  top: 10px;
  color: var(--color-black);
  font-size: 16px;
  transition: all 0.3s;
  pointer-events: none;
  background-color: transparent;
`;