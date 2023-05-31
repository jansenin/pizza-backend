package by.fpmibsu.pizza_site.controller;

import by.fpmibsu.pizza_site.dao.TransactionFactoryImpl;
import by.fpmibsu.pizza_site.exception.TransactionException;
import by.fpmibsu.pizza_site.service.ServiceFactory;
import by.fpmibsu.pizza_site.service.ServiceFactoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MainServlet extends HttpServlet {
    public void init() {

    }

    public ServiceFactory getFactory() throws TransactionException {
        return new ServiceFactoryImpl(new TransactionFactoryImpl());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        try {
            writer.println("<h2>Hello from Servlet</h2>");
        } finally {
            writer.close();
        }
    }

//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        process(request, response);
//    }
//
//    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//
//    }
}
