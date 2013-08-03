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
    
         .inputText{
            font-family: tahoma,arial,helvetica; font-size: 12px; 
   
        }     
          .optiontransferselect{
            font-family: tahoma,arial,helvetica; font-size: 12px; 
            border: 1px solid #000000; margin-left: auto; margin-right: auto;
            width: 250px;
        } 
        
         .botonTransferSelect{
         font-family: tahoma,arial,helvetica; font-size: 12px; 
         width: 40px;
         height: 25px;
         } 
          
    </style>
<title>Configurador Pantallas</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript"
    src="${ctx}/resources/extjs/adapter/ext/ext-base.js"></script>
<script type="text/javascript"
    src="${ctx}/resources/extjs/ext-all.js"></script>


<link rel="stylesheet" type="text/css"
    href="${ctx}/resources/css/screenBuilder/main.css" />

<!-- Estilos para extJs sin Modificar-->
<link rel="stylesheet" type="text/css"
    href="${ctx}/resources/extjs/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"
    href="${ctx}/resources/extjs/resources/css/xtheme-gray.css" />

<jsp:include page="/resources/jsp-script/configurador/pantallas/copiarConjunto-script.jsp" flush="true" />
    
<script type="text/javascript">
        var _CONTEXT = "${ctx}";
       
    </script>

</head>
<body>


</body>
</html>