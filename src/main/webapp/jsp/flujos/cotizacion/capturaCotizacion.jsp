<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<style type="text/css">    
       
        .alinear {
        margin-left: auto; margin-right: auto; 
        }
                
    </style>
<title>Array Grid Example</title>
    <script type="text/javascript">
    var _CREGRESAR = '<s:property value="#session.CREGRESAR" />';   
    
    var _URL_REGRESO_DE_RESULTADOS = "<s:url action='capturaCotizacion' namespace='/flujocotizacion'/>";

    <%@ include file="/resources/jsp-script/flujos/cotizacion/capturaCotizacion-script.jsp"%>    
    </script>
    <%@ include file="/jsp/flujos/cotizacion/equipo/equipoEspecial.jsp"%> 
</head>
<body>
    <div>
        <table style="width: 98%; border: 0px solid;">
            <tr>
                <td align="left"><div id="items" /></div></td>
            </tr>
           
            <tr>
                <td align="left"></td>
            </tr>
           
            <tr>
                <td align="left"><div>&nbsp;</div></td>
            </tr>
            <tr>
                <td align="left"><div id="forma" /></div></td>
            </tr>
        </table>
    </div>
   
</body>
</html>