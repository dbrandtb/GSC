<%@ page language="java" contentType="application/json" pageEncoding="utf-8"%>
<%@ include file="/taglibs.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<s:if test="%{#request.cmd =='get'}">
	<s:property value="listaArchivos" />
</s:if>

<s:elseif test="%{#request.cmd =='rename' }">
	<s:property value="resultadoRename" />
</s:elseif>

<s:elseif test="%{#request.cmd =='newdir' }">
	<s:property value="resultadoNewDir" />
</s:elseif>

<s:elseif test="%{#request.cmd =='delete' }">
	<s:property value="resultadoDelete" />
</s:elseif>
<s:elseif test="%{#request.cmd =='upload' }">
	<% response.setContentType("text/html"); %>
	<s:property value="resultadoUpload" />
</s:elseif>