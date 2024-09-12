import { createBrowserRouter, RouterProvider, Outlet } from "react-router-dom";
import { RecoilRoot } from "recoil";
import Home from "@/pages/Home";
import Login from "@/pages/login/Login";
import SignUp from "@/pages/signup/SignUp";
import Account from "@/pages/Account";
import FindPassword from "@/pages/login/FindPassword";
import HeaderNav from "@/components/header-navigation/HeaderNav";
import BottomNav from "@/components/bottom-navigation/BottomNav";
import styled from "styled-components";

// 레이아웃 스타일
const AppContainer = styled.div`
    display: flex;
    flex-direction: column;
    height: 844px;
`;

const ContentContainer = styled.div`
    flex: 1;
    overflow-y: auto; /* 스크롤 가능 */
`;

// 레이아웃 컴포넌트
const Layout = () => {
    return (
        <AppContainer>
            <HeaderNav />
            <ContentContainer>
                <Outlet />
                <BottomNav />
            </ContentContainer>
        </AppContainer>
    );
};

const App = () => {
    const router = createBrowserRouter([
        {
            path: "/",
            element: <Layout />,
            children: [
                {
                    path: "/",
                    element: <Home />,
                },
                {
                    path: "/login",
                    element: <Login />,
                },
                {
                    path: "/signup",
                    element: <SignUp />,
                },
                {
                    path: "/find-password",
                    element: <FindPassword />,
                },
                {
                    path: "/account",
                    element: <Account />,
                },
            ],
        },
    ]);

    return (
        <RecoilRoot>
            <RouterProvider router={router} />
        </RecoilRoot>
    );
};

export default App;
