<%--
  Created by IntelliJ IDEA.
  User: Garuspik
  Date: 05.05.2024
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.urise.model.*" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <c:forEach var="typeSection" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(typeSection)}"/>
            <c:if test="${not empty section}">
                <h2><a>${typeSection.title}</a></h2>
                <jsp:useBean id="section" type="com.urise.model.Section"/>
                <c:choose>
                    <c:when test="${typeSection=='OBJECTIVE' || typeSection=='PERSONAL'}">
                        <input type='text' name='${typeSection}' size=100 value='<%=((TextSection)section).getData()%>'>
                    </c:when>

                    <c:when test="${typeSection=='QUALIFICATIONS' || typeSection=='ACHIEVEMENT'}">

                   <textarea name='${typeSection}' cols=93
                             rows=10><%=String.join("\n", ((ListSection) section).getData())%></textarea>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>
        <button type="submit"  value="send request">Сохранить</button>
        <button type ="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
