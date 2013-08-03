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


<script type="text/javascript">
    <jsp:include page="/resources/jsp-script/configurador/configworkflow/variablesPs2.jsp" flush="true" />
    
    var _CONTEXT = "${ctx}";
    
    var _ACTION_CONFIG = "configuraWorkflowPasos.action";
    var _ACTION_VISTAC = "configuraWorkflowVistaPrevia.action";
    var _ACTION_OPCION = "configuraWorkflowOpciones.action";
    var _ACTION_HOME   = "configuraWorkflow.action";
    
    var PASOS_PROCESOID = '<s:property value="#session.PASOS_PROCESOID" />';
    var PASOS_DSPROCESO = '<s:property value="#session.PASOS_DSPROCESO" />';
    var PASOS_DSPROCESOFLUJO = '<s:property value="#session.PASOS_DSPROCESOFLUJO" />';
    var PASOS_DSELEMEN = '<s:property value="#session.PASOS_DSELEMEN" />';
    var PASOS_DSRAMO = '<s:property value="#session.PASOS_DSRAMO" />';
    var PASOS_DSUNIECO = '<s:property value="#session.PASOS_DSUNIECO" />';
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/util/PagingToolbar.js"></script>
<script type="text/javascript" src="${ctx}/resources/scripts/configworkflow/configWorkflowPs2.js"></script>

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