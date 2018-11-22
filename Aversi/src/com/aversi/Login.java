package com.aversi;

import com.aversi.DAO.UserDAO;
import com.aversi.model.User;
import com.aversi.model.UserType;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("user_name");
        String password = request.getParameter("user_password");
        RequestDispatcher requestDispatcher;


        try {
            if (UserDAO.validate(userName, password) < 0) {
                // check if user passed the validation or not
                request.setAttribute("errMessage", "username or password incorrect");
                requestDispatcher = request.getRequestDispatcher("index.jsp");
                requestDispatcher.forward(request, response);
            } else {
                // if all passed proceed
                User loginUser = UserDAO.getUserByName(userName);
                HttpSession session = request.getSession();
                session.setAttribute("username", userName);
                session.setAttribute("status", loginUser.getUserType().getValue());
                response.sendRedirect("main.do");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
