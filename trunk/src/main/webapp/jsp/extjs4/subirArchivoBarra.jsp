<%-- 
    Document   : subirArchivoBarra
    Created on : 5/09/2013, 09:50:09 AM
    Author     : Jair
--%>
<%@ include file="/taglibs.jsp"%>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <script>
            var urlObtenerProgreso='<s:url namespace="/" action="jsonObtenerProgreso" />';
            var uploadKey='<s:property value="uploadKey" />';
            var p;
            function checarProgreso(){
                Ext.Ajax.request({
                    url: urlObtenerProgreso,
                    params:{uploadKey:uploadKey},
                    success:function(response,opts)
                    {
                        var jsonResp = Ext.decode(response.responseText);
                        p.updateProgress(jsonResp.progreso,jsonResp.progresoTexto,true);
                        if(jsonResp.progreso==1)
                        {
                            //console.log("archivo arriba");
                        }
                        else
                        {
                            setTimeout(checarProgreso,500);
                        }
                    },
                    failure:checarProgreso
                });
            }
            Ext.onReady(function(){
                p=Ext.create('Ext.ProgressBar',{
                    renderTo:'maindiv',
                    width:100
                });
                checarProgreso();
            });
        </script>
    </head>
    <body style="margin:0;padding: 0;">
        <div id="maindiv"></div>
    </body>
</html>
