import { createGlobalStyle } from "styled-components";

const GlobalStyles = createGlobalStyle`
    * {
        box-sizing: border-box;
    }

    a {
        text-decoration: none;
        color: inherit;
    }
    
    ul, ol {
        list-style: none;
    }  

    button {
        all: unset;
        cursor: pointer;
    }

    @font-face {
        font-family: 'Pretendard';
        src: url('/fonts/Pretendard-Light.woff2') format('woff2');
        font-weight: 300; /* Light */
        font-display: swap;
    }

    @font-face {
        font-family: 'Pretendard';
        src: url('/fonts/Pretendard-Medium.woff2') format('woff2');
        font-weight: 500; /* Medium */
        font-display: swap;
    }

    html {
        font-family: 'Pretendard', sans-serif;
    }

    :root {
        /* Color styles */
        --color-white: #FFFFFF;
        --color-beige: #FFF8D2;
        --color-black: #000000;
        --color-pink: #DEBFFF;
        --color-lightgray: #EDEDED;
        --color-red: #E76C6C;
        --color-lime: #EFFFC0;
        --color-gray: #6E6E6E;
        --color-green: #5CC238;
        --color-yellow: #FFE845;

        /* Text-size styles */
        /* base size: --font-title-h4 (16px) */
        --font-title-h1: 2.12rem;
        --font-title-h2: 1.75rem;
        --font-title-h3: 1.5rem;
        --font-title-h4: 1rem;
        --font-title-h5: 0.75rem;
        --font-title-h6: 0.5rem;
        --font-body-h1: 2.12rem;
        --font-body-h2: 1.75rem;
        --font-body-h3: 1.5rem;
        --font-body-h4: 1rem;
        --font-body-h5: 0.75rem;
        --font-body-h6: 0.5rem;

        width: 390px;
        height: 844px;
        margin: auto;
    }

    h1, h2, h3, h4, h5, h6 {
        font-family: 'Pretendard', sans-serif;
        font-weight: 500; /* Medium */
        color: var(--color-green3);
    }

    body, p, span, div, input, textarea {
        font-family: 'Pretendard', sans-serif;
        font-weight: 300; /* Light */
        color: var(--color-green3);
    }

    /* 기본 속성 제거 */
    input, textarea {
        border: none;
        outline: none;
        resize: none;
    }


    h1 {
        font-size: var(--font-title-h1);
    }

    h2 {
        font-size: var(--font-title-h2);
    }

    h3 {
        font-size: var(--font-title-h3);
    }

    h4 {
        font-size: var(--font-title-h4);
    }

    h5 {
        font-size: var(--font-title-h5);
    }

    h6 {
        font-size: var(--font-title-h6);
    }
`;

export default GlobalStyles;
