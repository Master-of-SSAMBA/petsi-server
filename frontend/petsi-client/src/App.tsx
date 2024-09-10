import { createBrowserRouter, RouterProvider, Outlet } from "react-router-dom";
import Home from "@/pages/Home";
import Login from "@/pages/login/Login";
import Account from "@/pages/Account";
import HeaderNav from "@/components/header-navigation/HeaderNav";
import BottomNav from "@/components/bottom-navigation/BottomNav";
import { RecoilRoot } from "recoil";

// 레이아웃 컴포넌트
const Layout = () => {
    return (
        <div className="App-container">
            <HeaderNav />
            <div className="Content-container">
                <Outlet />
            </div>
            <BottomNav />
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
                    path: "/account",
                    element: <Account />,
                },
            ],
        },
    ]);

    return (
        <RecoilRoot>
            <RouterProvider router={router} />;
        </RecoilRoot>
    );
};

export default App;
