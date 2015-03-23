<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var _CONTEXT = '${ctx}';
	var asegAlterno          = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
	var guarda_Aseg_alterno  = '<s:url namespace="/endosos" action=" guardarEndosoAseguradoAlterno"       />';
	
	debug('asegAlterno :',asegAlterno);
	
	Ext.onReady(function() {
		
		var panelInicialPral = Ext.create('Ext.form.Panel', {
		    title: 'Alta Asegurado',
		    renderTo  : 'maindivHist',
		    bodyPadding: 5,
		    defaultType: 'textfield',
		    items: [
		    	{
			        fieldLabel: 'Asegurado Alterno',
			        name: 'asegAlt',
			        labelWidth: 150,
			        allowBlank: false
		    	}
	    	]
			,buttonAlign:'center'
			,buttons: [{
				text: 'Alta Asegurado'
				,icon:_CONTEXT+'/resources/fam3icons/icons/accept.png'
				,buttonAlign : 'center',
				handler: function() {
					var formPanel = this.up().up();
					if (formPanel.form.isValid()) {
                        // Realizamos el proceso de guardado
						var submitValues={};
        				asegAlterno.OTVALOR02 = panelInicialPral.down('[name="asegAlt"]').getValue();
        				submitValues['smap1']= asegAlterno;
        				
        				Ext.Ajax.request(
   						{
   						    url: guarda_Aseg_alterno,
   						    jsonData: Ext.encode(submitValues),
   						    success:function(response,opts){
   						    	 panelInicialPral.setLoading(false);
   						         var jsonResp = Ext.decode(response.responseText);
	   						     centrarVentanaInterna(Ext.Msg.show({
	 					            title:'Endoso',
	 					            msg: jsonResp.mensaje,
	 					            buttons: Ext.Msg.OK//,
	 					            //icon: Ext.Msg.
	 					        }));
   						    },
   						    failure:function(response,opts)
   						    {
   						        panelInicialPrincipal.setLoading(false);
   						        Ext.Msg.show({
   						            title:'Error',
   						            msg: 'Error de comunicaci&oacute;n',
   						            buttons: Ext.Msg.OK,
   						            icon: Ext.Msg.ERROR
   						        });
   						    }
   						});
        				
					}else {
						Ext.Msg.show({
							title: 'Aviso',
							msg: 'Complete la informaci&oacute;n requerida',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.WARNING
						});
					}
				}
			},{
				text: 'Cancelar',
				icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
				buttonAlign : 'center',
				handler: function() {
					windowLoader.close();
				}
			}]
		});
            	
    });
</script>
<div id="maindivHist" style="height:1000px;"></div>