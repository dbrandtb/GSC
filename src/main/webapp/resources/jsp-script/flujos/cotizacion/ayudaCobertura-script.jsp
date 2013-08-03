<%@page import="org.apache.commons.lang.StringEscapeUtils" %>
<%String ayudaLeyenda = StringEscapeUtils.unescapeHtml(""+session.getAttribute("AYUDA_LEYENDA")); %>

//Ext singleton
Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    Ext.form.Field.prototype.msgTarget = "side";

 
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  

/*************************************************************
** FormPanel de Ventana de Ayuda Cobertura
**************************************************************/
  var ayudaCoberturaForm = new Ext.FormPanel({
        id:'ayudaCoberturaForm',
        renderTo:'items',
        layout:'form',
        // url:'flujocotizacion/obtenerParametrosResult.action',
        bodyStyle:'padding:5px 5px 0',
        border:false,
   
       items :[{
                
           <s:if test="%{#session.containsKey('AYUDA')}">
           //html : '<table width=270 ><tr><td align=left bgcolor="#98012e" style="color:white;font-size:11px;"><b><s:property value= "%{#session.get('AYUDA').dsGarant}" /></b></td></tr><tr><td style="font-size:11px; "><s:property value= "%{#session.get('AYUDA').dsAyuda}" /></td></tr></table>',
           html : '<table width=270 ><tr><td align=left bgcolor="#98012e" style="color:white;font-size:11px;"><b><s:property value= "%{#session.get('AYUDA_ENCABEZADO')}" /></b></td></tr><tr><td style="font-size:11px; "><%=ayudaLeyenda%></td></tr></table>',
           </s:if>
           border:false
          
         }]     
      
    });

});