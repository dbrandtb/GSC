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
<title>B�squeda de conjuntos de Pantalla</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
    <jsp:include page="/resources/jsp-script/configurador/configworkflow/variablesDefinePasosPantalla.jsp" flush="true" />
    
    var _CONTEXT = "${ctx}";
    var _ACTION_PAGING = "<s:url action='paging' namespace='/confalfa'/>";
    var _ACTION_EXPORT = "<s:url action='export' namespace='/confalfa'/>";
</script>
<script type="text/javascript" src="${ctx}/resources/scripts/configworkflow/definePasosPantallas.js"></script>

</head>
<body>

<div id="pantalla"></div>
</body>
</html>