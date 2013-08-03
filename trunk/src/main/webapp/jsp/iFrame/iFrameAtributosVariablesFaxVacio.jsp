<%@ page import="mx.com.aon.portal.web.tooltip.ToolTipBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>   
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<!-- Estilos para extJs -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="${ctx}/resources/scripts/util/hashMap.js"></script>




<script language="javascript">

	var _CONTEXT = "${ctx}";
   
	var helpMap= new Map();
    <%=session.getAttribute("helpMap")%>
    
     <%=((ToolTipBean)pageContext.getServletContext().getAttribute("toolTipName")).getHelpMap("804")%>
 </script>
 <link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico" />
<!-- Links para extJs -->
<script type="text/javascript" src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="${ctx}/resources/extjs/ext-all.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/ext.js"></script>
<jsp:include page="/resources/scripts/util/ext.jsp" flush="true"></jsp:include>
<script type="text/javascript" src="${ctx}/resources/scripts/util/AON_utils.js"></script>

<link href="${ctx}/resources/css/template_css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/resources/scripts/util/ManagedIFrame.js"></script>

<script language="javascript">

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	
	/*var dinamicFormPanel = new Ext.FormPanel({
				renderTo:'atributosVariablesFax',
		        id: 'dinamicFormPanelId',
		        iconCls:'logo',
		        bodyStyle:'background: white',
		        buttonAlign: "center",
		        labelAlign: 'right',
		        frame:true,
		        autoHeight:true,
		        layout: 'table',
		        autoWidth: true,
	            layoutConfig: {columns: 1, align: 'left'},            
	            items:[
	            {xtype: 'hidden',
	            id:'cdNada'}
	            		 ]
	            
		});*/
			
		//dinamicFormPanel.render();
		var altura = 5;//dinamicFormPanel.body.dom.clientHeight;
		window.parent.document.getElementById('atributosVariablesFax').height = eval(altura) + 2;
		window.parent.parent.setSizeHeight(altura + 580);
		window.parent.mensaje();
		//window.parent.desabilitar();
		window.parent.finMask();
		window.parent.deshabilitarBotonGuarda()
		
	
});
</script>
</head>	
<body><div id="atributosVariablesFax"></div></body>