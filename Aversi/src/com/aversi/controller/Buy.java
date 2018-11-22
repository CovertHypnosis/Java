package com.aversi.controller;

import com.aversi.DAO.DrugDAO;
import com.aversi.DAO.PurchaseDAO;
import com.aversi.model.Drug;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "buy", value = "/buy")
public class Buy extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // todo: maybe make this synchronized
        List<Drug> drugs = null;
        try {
            drugs = DrugDAO.getDrugs();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int qty = 0;
        for (Drug drug : drugs) {
            // if field is left empty
            if (request.getParameter(drug.getName() + "_quantity") == "") continue;


            // parse value if it's set, without try-catch server is constantly displaying error page

            try {
                qty = Integer.parseInt(request.getParameter(drug.getName() + "_quantity"));
            } catch (NumberFormatException e) {
                System.out.println("Entered here " + qty);
            }


            // if user is fucking with us
            if (qty > drug.getQuantity())
                response.sendRedirect("main.do");
            if (qty > 0) {
                try {
                    PurchaseDAO.sell((String) request.getSession().getAttribute("username"), drug.getName(), qty);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        response.sendRedirect("main.do");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}