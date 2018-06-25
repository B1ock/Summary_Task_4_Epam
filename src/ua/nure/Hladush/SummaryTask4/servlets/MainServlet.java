package ua.nure.Hladush.SummaryTask4.servlets;


import ua.nure.Hladush.SummaryTask4.Path;
import sun.net.httpserver.HttpServerImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/mainservlet")
public class MainServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        System.out.println("MyMainServlet#doGet");

        String page = Path.PAGE_ADMIN_MENU;
        request.getRequestDispatcher(page).forward(request, response);
    }




}
