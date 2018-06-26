package ua.nure.Hladush.SummaryTask4.servlets;


import ua.nure.Hladush.SummaryTask4.Path;
import ua.nure.Hladush.SummaryTask4.db.DBManager;
import ua.nure.Hladush.SummaryTask4.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet("/addflight")
public class AddFlight extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        System.out.println("MyAddFlight#doGet");

        int num = Integer.parseInt(request.getParameter("numF"));
        String whence = request.getParameter("whenceF");
        String dest = request.getParameter("destF");
        //String name = request.getParameter("nameF");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
             date = dateFormat.parse(request.getParameter("departF"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            DBManager.getInstance().addFlight(num, whence, dest,  date);
        } catch (DBException e) {
            e.printStackTrace();
        }

        String page = Path.PAGE_ADD_FLIGHT;
        request.getRequestDispatcher(page).forward(request, response);
    }
}
