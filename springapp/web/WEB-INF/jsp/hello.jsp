<%-- 
    Document   : hello
    Created on : Apr 25, 2014, 10:30:36 AM
    Author     : dknottne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <head><title><fmt:message key="title"/></title></head>
    <body>
        <h1><fmt:message key="heading"/></h1>
        <p><fmt:message key="greeting"/> <c:out value="${model.now}"/></p>
        <h3>Products</h3>
        <c:forEach items="${model.products}" var="prod">
            <c:out value="${prod.description}"/> <i>$<c:out value="${prod.price}"/></i><br><br>
        </c:forEach>
        <br>
        <a href="<c:url value="priceincrease.htm"/>">Increase Prices</a>
        <br>
    </body>
</html>
