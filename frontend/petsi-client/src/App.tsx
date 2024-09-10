import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Home from "@/pages/Home";
import Login from "@/pages/login/Login";
import Account from "@/pages/Account";

const App = () => {
    const router = createBrowserRouter([
        {
            path: "/",
            element: <Home />,
        },
        {
            path: "/login/",
            element: <Login />,
        },
        {
            path: "/account",
            element: <Account />,
        },
    ]);

    return <RouterProvider router={router} />;
};

export default App;
