<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="mx.com.aon.portal.model.UserVO"%>
<script language="javascript">

	<%
		UserVO userVO = (UserVO)session.getAttribute("USUARIO");
		if (userVO != null) {%>
			/**
			*	Sobrecarga la clase Ext-JS para soporte el caracter de separación 
			*	de decimales definido para el usuario actual
			*/
			Ext.override(Ext.form.NumberField, {
					decimalSeparator : "<%=userVO.getDecimalSeparator()%>"
			});
			
			/**
			*	Sobrecarga la clase Ext-JS para soporte de formato de fechas
			*	definido para el usuario actual
			*/
			Ext.override(Ext.form.DateField, {
				format: '<%=userVO.getClientFormatDate()%>'
			});
	<%	
		} else {
	%>
			location.href = "http://acw.biosnettcs.com:7778/login/login.html";
	<%
		}
	%>
</script>