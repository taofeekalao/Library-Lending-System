import { createBrowserRouter } from "react-router-dom";

import { Applayout } from "./components/layouts/AppLayout";

import NoMatch from "./pages/NoMatch";
import Member from "./pages/MemberPage";
import BookPage from "./pages/BookPage";
import DashboardPage from "@/pages/DashboardPage";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <Applayout />,
        children: [
            {
                path: "",
                element: <DashboardPage />,
            },
            {
                path: "member",
                element: <Member />,
            },
            {
                path: "book",
                element: <BookPage />,
            }
        ],
    },
    {
        path: "*",
        element: <NoMatch />,
    },
], {
    basename: global.basename
})
