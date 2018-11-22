package com.aversi.controller;

import com.aversi.DAO.DrugDAO;
import com.aversi.model.Drug;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "edit", value = "/edit.do")
public class Edit extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // todo: maybe remove this fucking try-s and put them in dao
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("update.jsp");
        String drugName = request.getParameter("drug_name");
        List<Drug> drugs = new ArrayList<>();

        try {
            drugs = DrugDAO.getDrugs();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (drugName != null) {
                Drug drug = DrugDAO.getDrug(drugName);
                request.setAttribute("drugUpdate", drug);
                requestDispatcher.forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // if search is not required, just display details
        request.setAttribute("drugs", drugs);
        requestDispatcher = request.getRequestDispatcher("edit.jsp");
        requestDispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String job = request.getParameter("submit");
        String name = request.getParameter("new_name");
        String description = request.getParameter("desc");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("qt"));
        Drug drug = new Drug(name, price, description, quantity);



        if (job.equals("update")) {
            String origName = request.getParameter("original_name");
            try {
                DrugDAO.updateDrug(drug, origName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (job.equals("register")) {
            try {
                DrugDAO.addDrug(drug);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new ServletException("Wrong operation");
        }

        response.sendRedirect("edit.do");
    }
}
