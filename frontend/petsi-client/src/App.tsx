import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Login from "./pages/Login";
import Main from "./pages/Home";
import Account from "./pages/Account";

function App() {
    const router = createBrowserRouter([
        {
            path: "/",
            element: <Main />,
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
}

export default App;
