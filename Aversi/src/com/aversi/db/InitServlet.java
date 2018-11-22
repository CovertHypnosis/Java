package com.aversi.db;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

@WebServlet(name = "init", value="/initServlet", loadOnStartup=1)
public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public InitServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) {
        try {
            DatabaseConnectionFactory.getConFactory().init();
        } catch (IOException e) {
            config.getServletContext().log(e.getLocalizedMessage(), e);
        }
    }
}
