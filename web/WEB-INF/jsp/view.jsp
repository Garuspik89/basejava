<%@ page import="com.urise.model.TextSection" %>
<%@ page import="com.urise.model.ListSection" %>
<%@ page import="com.urise.model.CompanySection" %>
<%@ page import="com.urise.util.DateUtil" %>
<%@ page import="com.urise.model.Company" %>
<%--

  Created by IntelliJ IDEA.
  User: Garuspik
  Date: 05.05.2024
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>

        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.model.SectionType, com.urise.model.Section>"/>
            <c:set var="typeSection" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.model.Section"/>
        <c:if test="${not empty section.getData()}">
        <c:choose>
        <c:when test="${typeSection=='OBJECTIVE' || typeSection=='PERSONAL'}">
    <table border="0" cellpadding="8" cellspacing="0">
        <tr>
            <td><a name="type.name">${typeSection.title}</a></td>
            <td>
                <%=((TextSection) section).getData()%>
            </td>
        </tr>
    </table>
    </c:when>
    <c:when test="${typeSection=='QUALIFICATIONS' || typeSection=='ACHIEVEMENT'}">
        <table border="0" cellpadding="8" cellspacing="0">
            <tr>
                <td><a name="type.name">${typeSection.title}</a></td>
                <td>
                    <ul>
                        <c:forEach var="list" items="<%=((ListSection) section).getData()%>">
                            <li>${list}</li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </table>
    </c:when>
    <c:when test="${typeSection=='EXPERIENCE' || typeSection=='EDUCATION'}">
        <c:forEach var="company" items="<%=((CompanySection) section).getData()%>">
            <tr>
                <td colspan="2">
                    <h3>${company.name}</h3>
                    <h3>${company.webSite}</h3>
                </td>
            </tr>
            <c:forEach var="period" items="${company.periodList}">
                <jsp:useBean id="period" type="com.urise.model.Company.Period"/>
                <tr>
                    <td width="15%"
                        style="vertical-align: top"><%=DateUtil.of(period.getFirstDate().getYear(), period.getFirstDate().getMonth()) + " - " + DateUtil.of(period.getSecondDate().getYear(), period.getSecondDate().getMonth())%>
                    </td>
                    <td><b>${period.title}</b></td> <td>${period.description}</td>
                </tr>
            </c:forEach>
        </c:forEach>
    </c:when>
    </c:choose>
    </c:if>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
