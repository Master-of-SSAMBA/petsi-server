import { createBrowserRouter, RouterProvider, Outlet } from "react-router-dom";
import { RecoilRoot } from "recoil";
import Home from "@/pages/Home";
import Login from "@/pages/login/Login";
import SignUp from "@/pages/signup/SignUp";
import Account from "@/pages/Account";
import FindPassword from "@/pages/login/FindPassword";
import HeaderNav from "@/components/header-navigation/HeaderNav";
// import BottomNav from "@/components/bottom-navigation/BottomNav";

// 레이아웃 컴포넌트
const Layout = () => {
    return (
        <div className="App-container">
            <HeaderNav />
            <div className="Content-container">
                <Outlet />
            </div>
            {/* <BottomNav /> */}
        </div>
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
