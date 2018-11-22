package com.aversi.controller;

import com.aversi.DAO.DrugDAO;
import com.aversi.DAO.UserDAO;
import com.aversi.model.Drug;
import com.aversi.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "main", value = "/main.do")
public class Main extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        HttpSession session = request.getSession();
        //default value to avoid unnecessary errors
        String redirect = "main.jsp";
        Integer status = (Integer) session.getAttribute("status");

        // check session first
        if (status == null || status < 0 ) {
            requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        } else {
            // if its pharmacist
            redirect = (status == 1) ? "edit.jsp" : "main.jsp";
        }

        // display drugs with or without search word
        displayDrugs(request, response, redirect);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doGet(req, resp);

        if (req.getParameter("user_submit") != null) {
            try {
                validateUserRegistration(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void displayDrugs(HttpServletRequest request, HttpServletResponse response, String redirect) throws ServletException, IOException {
        RequestDispatcher requestDispatcher;// nothing fancy, if search word is not found, display all drugs
        if (request.getParameter("drug_search_name") == null) {
            try {
                List<Drug> drugs = DrugDAO.getDrugs();
                request.setAttribute("drugs", drugs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            requestDispatcher = request.getRequestDispatcher(redirect);
        } else {
            try {
                String searchWord = request.getParameter("drug_search_name");
                List<Drug> drugs = DrugDAO.getDrugsByNameOrDescription(searchWord);
                request.setAttribute("drugs", drugs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            requestDispatcher = request.getRequestDispatcher("search.jsp");
        }

        requestDispatcher.forward(request, response);
    }

    private void validateUserRegistration(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        HttpSession session = req.getSession();
        String userName = req.getParameter("user_name");
        String password = req.getParameter("user_pass");
        String id = req.getParameter("user_id");
        String firstName = req.getParameter("user_first_name");
        String lastName = req.getParameter("user_last_name");
        int age = Integer.parseInt(req.getParameter("user_age"));

        if (validateId(id)) {
            User user = new User(userName, password);
            user.setID(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAge(age);
            UserDAO.createUser(user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("status", user.getUserType().getValue());
            // request dispatcher not working here
            resp.sendRedirect("main.do");
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("register.jsp");
            requestDispatcher.forward(req, resp);
            req.setAttribute("errMessage", "id is incorrect or already exists");
        }

    }

    private boolean validateId(String id) throws SQLException {
        if (id.trim().length() != 11)
            return false;

        return UserDAO.validateById(id);
    }
}
