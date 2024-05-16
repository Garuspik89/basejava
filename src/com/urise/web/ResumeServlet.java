package com.urise.web;

import com.Config;
import com.urise.exception.StorageException;
import com.urise.model.*;
import com.urise.storage.Storage;
import com.urise.util.DateUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) {
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        String newResume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                r = storage.get(uuid);
                newResume = "false";
                break;
            case "add":
                r = new Resume();
                newResume = "true";
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("resume", r);
        request.setAttribute("newResume", newResume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        String newResume = request.getParameter("newResume");
        Resume r;
        if (fullName.trim().isEmpty()) {
            response.sendRedirect("resume");
            return;
        }
        if (newResume.equals("false")) {
            r = storage.get(uuid);
        } else {
            r = new Resume(uuid, fullName);
        }
        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }

        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value == null) {
                continue;
            } else if (value.trim().isEmpty()) {
                r.getSections().remove(type);
                continue;
            }
            switch (type) {
                case OBJECTIVE:
                case PERSONAL:
                    r.addSection(type, new TextSection(value));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    r.addSection(type, new ListSection(value.replaceAll("(?m)^\\s*$[\r\n]+", "").split("\\n")));
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Company> companies = new ArrayList<>();
                    String[] companyNames = request.getParameterValues(type.name());
                    String[] webSites = request.getParameterValues(type.name() + "webSite");
                    List<Company.Period> periods = new ArrayList<>();
                    for (int i = 0; i < companyNames.length; i++) {
                        String companyName = companyNames[i];
                        String webSite = webSites[i];
                        String[] titles = request.getParameterValues(type.name() + i + "title");
                        for (int j = 0; j < titles.length; j++) {
                            String[] firstDates = request.getParameterValues(type.name() + j + "firstDate");
                            String[] secondDates = request.getParameterValues(type.name() + j + "secondDate");
                            String[] titlesOfPeriods = request.getParameterValues(type.name() + j + "title");
                            String[] descriptions = request.getParameterValues(type.name() + j + "description");
                            periods.add(new Company.Period(DateUtil.unmarshal(firstDates[j]), DateUtil.unmarshal(secondDates[j]), titlesOfPeriods[j], descriptions[j]));
                        }
                        companies.add(new Company(companyName, webSite, periods));
                    }
                    if (newResume.equals("true")) {
                        r.addSection(type, new CompanySection(companies));
                    } else {
                        r.getSections().remove(type);
                        r.addSection(type, new CompanySection(companies));
                    }

                    break;
            }
        }
        if (newResume.equals("true")) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }
}