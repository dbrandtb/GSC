<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var _CONTEXT = '${ctx}';
	var asegAlterno          = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
	var guarda_Aseg_alterno  = '<s:url namespace="/endosos" action=" guardarEndosoAseguradoAlterno"       />';
	var guarda_Vigencia_Poliza = '<s:url namespace="/endosos" action=" guardarEndosoVigenciaPoliza"       />';
	
	debug('asegAlterno :',asegAlterno);
	
	Ext.onReady(function() {
		
		var panelInicialPral = Ext.create('Ext.form.Panel', {
		    title: 'Vigencia P&oacute;liza',
		    renderTo  : 'maindivHist',
		    bodyPadding: 5,
		    defaultType: 'textfield',
		    layout     :
			{
				type     : 'table'
				,columns : 2
			}
			,defaults 	:
			{
				style : 'margin:5px;'
			}
			,
		    items: [
		    	{
					id: 'feInicio'
					,xtype		: 'datefield'
					,fieldLabel	: 'Fecha Efecto'
					,name		: 'feIngreso'
					,format		: 'd/m/Y'
					,editable: true		
					,value		: asegAlterno.FEEFECTO
			    	,listeners:{
			    	    change:function(field,value)
			    	    {
			    	    	panelInicialPral.down('[name="feFin"]').setValue(Ext.Date.add(value, Ext.Date.DAY, asegAlterno.pv_difDate));
			    	    }
			    	}
				},
		    	{
					id: 'feFin'
					,xtype		: 'datefield'
					,fieldLabel	: 'Fecha Fin'
					,name		: 'feFin'
					,format		: 'd/m/Y'
					,editable: true		
					,value		: asegAlterno.FEPROREN
		    		, readOnly  : true
				}
		    	
	    	]
			,buttonAlign:'center'
			,buttons: [{
				text: 'Confirmar endoso'
				,icon:_CONTEXT+'/resources/fam3icons/icons/key.png'
				,buttonAlign : 'center',
				handler: function() {
					var formPanel = this.up().up();
					if (formPanel.form.isValid()) {
                        // Realizamos el proceso de guardado
						var submitValues={};
                        dateIngreso = new Date(panelInicialPral.down('[name="feIngreso"]').getValue());
                        dateFin = new Date( panelInicialPral.down('[name="feFin"]').getValue());
                        convDateIngreso = dateIngreso.getDate() + "/" + (dateIngreso.getMonth() + 1) + "/" + dateIngreso.getFullYear();
                        convDateFin 	= dateFin.getDate() 	+ "/" + (dateFin.getMonth() + 1) 	 + "/" + dateFin.getFullYear();
                        
                        asegAlterno.FEEFECTO = convDateIngreso;
        				asegAlterno.FEPROREN = convDateFin;
        				submitValues['smap1']= asegAlterno;
        				
        				Ext.Ajax.request(
   						{
   						    url: guarda_Vigencia_Poliza,
   						    jsonData: Ext.encode(submitValues),
   						    success:function(response,opts){
   						    	 panelInicialPral.setLoading(false);
   						         var jsonResp = Ext.decode(response.responseText);
   						      	 mensajeCorrecto("Endoso",jsonResp.respuesta,null);
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
			}]
		});
            	
    });
</script>
<div id="maindivHist" style="height:1000px;"></div>