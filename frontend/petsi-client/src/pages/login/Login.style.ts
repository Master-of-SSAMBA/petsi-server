import styled from "styled-components";

export const Layout = styled.div`
    display: flex;
    width: 85%;
    margin: 0 auto;
    flex-direction: column;
    padding-top: 10%;
    gap: 2rem;
`;

export const TitleMsg = styled.span`
    font-size: var(--font-title-h3);
    font-weight: 500;
`;

export const InputSection = styled.div`
    text-align: end;
`;

export const BtnSection = styled.div`
    display: flex;
    flex-direction: column;
    gap: 1rem;
`;

export const FindPassword = styled.p`
    margin-top: 1rem;
`;
