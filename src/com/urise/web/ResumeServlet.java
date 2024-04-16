package com.urise.web;

import com.Config;
import com.urise.model.Resume;
import com.urise.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    Storage storage;
    List<Resume> list;

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();

        writer.println("<table>\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<th>UUID</th>\n" +
                "<th>Full Name</th>\n" +
                "</tr>");

        for (Resume r : list) {
            writer.println("<tr>");
            writer.println("<td>" + r.getUuid() + "</td>");
            writer.println("<td>" + r.getFullName() + "</td>");
            writer.println("</tr>");
        }

        writer.println("</tbody\n" +
                "</table>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    @Override
    public void init(ServletConfig config) {
        storage = Config.get().getStorage();
        list = storage.getAllSorted();
    }
}
