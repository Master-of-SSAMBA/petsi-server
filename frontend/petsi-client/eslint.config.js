import eslint from "@eslint/js";
import tseslint from "@typescript-eslint/eslint-plugin";
import tseslintParser from "@typescript-eslint/parser";
import reactPlugin from "eslint-plugin-react";
import reactHooksPlugin from "eslint-plugin-react-hooks";
import prettierPlugin from "eslint-plugin-prettier";
import prettierConfig from "eslint-config-prettier";
import globals from "globals";

export default [
    // eslint.configs.recommended,
    prettierConfig,
    {
        files: ["src/**/*.{js,jsx,ts,tsx}"], // src 폴더 내부 파일만 검사
        plugins: {
            "@typescript-eslint": tseslint,
            react: reactPlugin,
            "react-hooks": reactHooksPlugin,
            prettier: prettierPlugin,
        },
        languageOptions: {
            parser: tseslintParser,
            globals: {
                ...globals.node,
                ...globals.browser,
            },
            parserOptions: {
                projectService: true, // TypeScript 프로젝트 서비스 활성화
                ecmaVersion: "latest",
                sourceType: "module",
                ecmaFeatures: {
                    jsx: true,
                },
                project: "./tsconfig.json",  // tsconfig.json 파일 경로 지정
            },
        },
        rules: {
            // React related rules
            "react/react-in-jsx-scope": "off",
            "react/prop-types": "off",
            "react-hooks/rules-of-hooks": "error",
            "react-hooks/exhaustive-deps": "warn",
            "react/jsx-no-bind": ["warn", { allowArrowFunctions: true }],
            "react/jsx-boolean-value": ["error", "always"],
            "react/jsx-no-useless-fragment": "warn",

            // TypeScript related rules
            "@typescript-eslint/explicit-function-return-type": "off",
            "@typescript-eslint/explicit-module-boundary-types": "off",
            "@typescript-eslint/no-explicit-any": "warn",
            "@typescript-eslint/no-unused-vars": [
                "error",
                { argsIgnorePattern: "^_" },
            ],
            "@typescript-eslint/consistent-type-definitions": [
                "error",
                "interface",
            ],
            "@typescript-eslint/no-non-null-assertion": "error",
            "@typescript-eslint/no-inferrable-types": "error", 
            "@typescript-eslint/strict-boolean-expressions": "warn", 

            // Prettier
            "prettier/prettier": "error",

            // General best practices
            "no-console": "warn",
            "no-debugger": "error",
            "no-duplicate-imports": "error",
            "prefer-const": "error",
            eqeqeq: ["error", "always"],
        },
        settings: {
            react: {
                version: "detect",
            },
        },
    },
];
