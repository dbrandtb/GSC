<%@ include file="/taglibs.jsp"%>

/*************************************************************
** FormPanel de Ventana de vista previa
**************************************************************/       
 
var vistaPreviaFormPanel =  new Ext.form.FormPanel({                          
   id:'vistaPreviaFormPanel',
   border:false,
   layout:'form',
 <%--
  <s:if test="componente!=null">  
    <s:component template="entradaCotizacion.vm" templateDir="templates" theme="components" />
  </s:if>
  
  <s:else>  
    <s:component template="vistaPrevia.vm" templateDir="templates" theme="components" >
        <s:param name="componente" value="''" /> 
    </s:component>  
 </s:else>
 
   ,
--%>
   listeners: {
            beforerender : function() {
             seleccionarComponente();
              }//beforerender
       }
 });//end FormPanel


var windowVistaPrevia = new Ext.Window({
            title: WINDOW_1_VISTA_PREVIA_TITLE,
            plain:true,
            width: 800,
            height:300,
            minWidth: 800,
            minHeight: 300,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal:true,
            closable : false,
            items: vistaPreviaFormPanel,
             buttons: [{
               text: WINDOW_1_VISTA_PREVIA_BOTON_CERRAR,
               handler: function() { 
               windowVistaPrevia.hide(); 
               }
          }]
            
        });
