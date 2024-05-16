<%--
  Created by IntelliJ IDEA.
  User: Garuspik
  Date: 05.05.2024
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.urise.model.*" %>
<%@ page import="com.urise.util.DateUtil" %>
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
        <input type="hidden" name="uuid" required value="${resume.uuid}">
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
            <jsp:useBean id="typeSection" type="com.urise.model.SectionType"/>
            <h2><a>${typeSection.title}</a></h2>
            <c:choose>
                <c:when test="${typeSection=='OBJECTIVE' || typeSection=='PERSONAL'}">
                    <c:choose>
                        <c:when test="${section==null}">
                            <input type='text' name='${typeSection}' size=100 value="">
                        </c:when>
                        <c:when test="${section!=null}">
                            <input type='text' name='${typeSection}' size=100 value='${section.data}'>
                        </c:when>
                    </c:choose>
                </c:when>

                <c:when test="${typeSection=='QUALIFICATIONS' || typeSection=='ACHIEVEMENT'}">
                    <c:choose>
                        <c:when test="${section==null}">
                             <textarea name='${typeSection}' cols=93
                                       rows=10><%=String.join("\n", "")%></textarea>
                        </c:when>
                        <c:when test="${section!=null}">
                             <textarea name='${typeSection}' cols=93
                                       rows=10><%=String.join("\n", ((ListSection) resume.getSection(typeSection)).getData())%></textarea>
                        </c:when>
                    </c:choose>
                </c:when>
                <c:when test="${typeSection=='EXPERIENCE' || typeSection=='EDUCATION'}">
                    <c:choose>
                        <c:when test="${section!=null}">
                            <c:forEach var="company"
                                       items="<%=((CompanySection) resume.getSection(typeSection)).getData()%>"
                                       varStatus="counter">
                                <dt>Компания:</dt>
                                <dd><input type="text" required name='${typeSection}' size=100 value="${company.name}">
                                </dd>
                                <input type="hidden" required name="${typeSection}companyName" size=100
                                       value="${company.name}">
                                <dt>Сайт:</dt>
                                <dd><input type="text" name='${typeSection}' size=100 value="${company.webSite}"></dd>
                                <input type="hidden" name="${typeSection}webSite" size=100 value="${company.webSite}">
                                </dd>
                                <br>

                                <c:forEach var="periods" items="${company.periodList}">
                                    <jsp:useBean id="periods" type="com.urise.model.Company.Period"/>
                                    <dl>
                                        <dt>Дата начала:</dt>
                                        <dd>
                                            <input type="text" name="${typeSection}${counter.index}firstDate" size=15
                                                   value="<%=DateUtil.of(periods.getFirstDate().getYear(),periods.getFirstDate().getMonth())%>">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Дата окончания:</dt>
                                        <dd>
                                            <input type="text" name="${typeSection}${counter.index}secondDate" size=15
                                                   value="<%=DateUtil.of(periods.getSecondDate().getYear(),periods.getSecondDate().getMonth())%>">
                                    </dl>
                                    <dl>
                                        <dt>Должность:</dt>
                                        <dd><input type="text" name='${typeSection}${counter.index}title' size=50
                                                   value="${periods.title}">
                                    </dl>
                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><textarea name="${typeSection}${counter.index}description" rows=5
                                                      cols=75>${periods.description}</textarea></dd>
                                    </dl>
                                </c:forEach>
                            </c:forEach>
                        </c:when>
                        <c:when test="${section==null}">
                            <dt>Компания:</dt>
                            <dd><input type="text" required name='${typeSection}' size=100 value="">
                            </dd>
                            <input type="hidden" required name="${typeSection}companyName" size=100
                                   value="">
                            <dt>Сайт:</dt>
                            <dd><input type="text" name='${typeSection}' size=100 value=""></dd>
                            <input type="hidden" name="${typeSection}webSite" size=100 value="">
                            </dd>
                            <br>
                            <dl>
                                <dt>Дата начала:</dt>
                                <dd>
                                    <input type="text" name="${typeSection}0firstDate" size=15
                                           value="">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Дата окончания:</dt>
                                <dd>
                                    <input type="text" name="${typeSection}0secondDate" size=15
                                           value="">
                            </dl>
                            <dl>
                                <dt>Должность:</dt>
                                <dd><input type="text" name='${typeSection}0title' size=50
                                           value="">
                            </dl>
                            <dl>
                                <dt>Описание:</dt>
                                <dd><textarea name="${typeSection}0description" rows=5
                                              cols=75>${periods.description}</textarea></dd>
                            </dl>
                        </c:when>
                    </c:choose>
                </c:when>
            </c:choose>
        </c:forEach>
        <input type="hidden" name="newResume" value="${newResume}">
        <button type="submit" for="newResume">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
