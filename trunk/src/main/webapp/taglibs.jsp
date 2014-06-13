<%@ page language="java" errorPage="/error.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"               prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/struts-tags"                                   prefix="s" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<c:set var="now" value="<%= new java.util.Date() %>" />
<fmt:formatDate pattern="yyyyMMddHHmm" value="${now}" var="now" />