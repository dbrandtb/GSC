<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <style type="text/css">    
        #button-grid .x-panel-body {
            border:1px solid #99bbe8;
            border-top:0 none;
        }
        .logo{
            background-image:url(../resources/images/aon/bullet_titulo.gif) !important;
        }
        
    
        .alinear {
        margin-top:20px; margin-left: auto; margin-right: auto; 
        }
                
    </style>
<title>Búsqueda de conjuntos de Pantalla</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript">
    <jsp:include page="/resources/jsp-script/configurador/configworkflow/variablesPs1.jsp" flush="true" />
    
    var _CONTEXT = "${ctx}";
    var _ACTION_CONFIG = "configuraWorkflowPasos.action";
//  var _ACTION_CONFIG = "<s:url action='configuraWorkflowPasos' namespace='/configworkflow'/>";
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/PagingToolbar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/configworkflow/configWorkflowPs1.js"></script>

</head>
<body>
    <table class="headlines" cellspacing="10">
        <tr valign="top">
            <td class="headlines" colspan="2">
                <div id="opcionesForm" />
            </td>
        </tr>
       <tr valign="top">
           <td class="headlines" colspan="2">
                <div id="gridOpciones" />
           </td>
       </tr>
    </table>
</body>
</html>