<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var _CONTEXT = '${ctx}';
	var paramsEntrada          = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
	var guarda_Vigencia_Poliza = '<s:url namespace="/endosos" action=" guardarEndosoAmpliacionVigencia"       />';
	
	debug('paramsEntrada  -->:',paramsEntrada);
	
	Ext.onReady(function() {
		
		Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
		
		var panelInicialPral = Ext.create('Ext.form.Panel', {
		    title: 'Apliaci&oacute;n Vigencia',
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
		    	{	xtype		: 'datefield', 	fieldLabel	: 'Fecha Original',			name	: 'feEfecto',			hidden		: true,
					format		:  'm/d/Y', 	editable	: true,						value	: paramsEntrada.FEEFECTO
				},
				{	xtype		: 'datefield',	fieldLabel	: 'Fecha Minimo',			name	: 'feMin',				hidden		: true,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEEFECTO
				},
				{	xtype		: 'datefield',	fieldLabel	: 'Fecha Maximo',			name	: 'feMax',				hidden		: true,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEEFECTO
				},
				{
					xtype		: 'datefield',	fieldLabel	: 'Fecha Efecto',			name	: 'feIngreso',			labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEEFECTO,	allowBlank	: false
					,readOnly  	: true
					
				},
		    	{	xtype		: 'datefield',	fieldLabel	: 'Fecha Fin',				name	: 'feFin',				labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEPROREN, readOnly  	: true
				},
				{	xtype		: 'datefield',	fieldLabel	: 'Fecha Ampliaci&oacute;n Vigencia', name	: 'feAmpliacion', labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true, 					value   : paramsEntrada.FEPROREN,			allowBlank	: false,
					colspan		:2, minValue : new Date(),
					minText:'La fecha de Ampliaci&oacute;n Vigencia debe ser mayor a Fecha Fin'
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
                        //1.- Verificamos la información de las fechas
						
						var feAmpli  = new Date(panelInicialPral.down('[name="feAmpliacion"]').getValue());
						var feProren = new Date(panelInicialPral.down('[name="feFin"]').getValue());
						paramsEntrada.FEINIVAL = Ext.Date.format(panelInicialPral.down('[name="feIngreso"]').getValue(),'d/m/Y');
						paramsEntrada.FEPROREN = Ext.Date.format(panelInicialPral.down('[name="feAmpliacion"]').getValue(),'d/m/Y');
						if(feAmpli > feProren){
						    //Exito
							var submitValues={};
	        				submitValues['smap1']= paramsEntrada;
	        				Ext.Ajax.request( {
	   						    url: guarda_Vigencia_Poliza,
	   						    jsonData: Ext.encode(submitValues),
	   						    success:function(response,opts){
	   						    	 panelInicialPral.setLoading(false);
	   						         var jsonResp = Ext.decode(response.responseText);
	   						         
	   						         var callbackRemesa = function()
	   						         {
	   						             marendNavegacion(2);
	   						         };
	   						         
	   						      	 mensajeCorrecto("Endoso",jsonResp.respuesta,function()
	   						      	 {
	   						      	     _generarRemesaClic(
	   						      	         true
	   						      	         ,paramsEntrada.CDUNIECO
	   						      	         ,paramsEntrada.CDRAMO
	   						      	         ,paramsEntrada.ESTADO
	   						      	         ,paramsEntrada.NMPOLIZA
	   						      	         ,callbackRemesa
	   						      	     );
	   						      	 });
	   						    },
	   						    failure:function(response,opts){
	   						        panelInicialPral.setLoading(false);
	   						        Ext.Msg.show({
	   						            title:'Error',
	   						            msg: 'Error de comunicaci&oacute;n',
	   						            buttons: Ext.Msg.OK,
	   						            icon: Ext.Msg.ERROR
	   						        });
	   						    }
	   						});
						    
						    
						}else{
						    //Error
							centrarVentanaInterna(Ext.Msg.show({
		                        title:'Error',
		                        msg: 'La fecha de Ampliaci&oacute;n de Vigencia debe ser mayor a la Fecha Fin',
		                        buttons: Ext.Msg.OK,
		                        icon: Ext.Msg.WARNING
		                    }));
						}
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
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
<div id="maindivHist" style="height:1000px;"></div>