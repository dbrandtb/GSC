<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var _CONTEXT = '${ctx}';
	var asegAlterno          = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
	var guarda_Aseg_alterno  = '<s:url namespace="/endosos" action=" guardarEndosoAseguradoAlterno"       />';
	var guarda_Vigencia_Poliza = '<s:url namespace="/endosos" action=" guardarEndosoVigenciaPoliza"       />';
	
	debug('asegAlterno  -->:',asegAlterno);
	
	Ext.onReady(function() {
		var myMask = new Ext.LoadMask(Ext.getBody(), {msg:"loading..."});
		
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
		    	{	xtype		: 'datefield',	fieldLabel	: 'Fecha Inicio Endoso',	name	: 'feInival',			labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true, 					value   : new Date(),			allowBlank	: false,
					colspan		:2
				},
				{
					xtype		: 'datefield',	fieldLabel	: 'Fecha Efecto',			name	: 'feIngreso',			labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true,						value	: asegAlterno.FEEFECTO,	allowBlank	: false,
					minValue: asegAlterno.pv_fechaMinima ,
					maxValue: asegAlterno.pv_fechaMaxima ,
					listeners:{
			    	    change:function(field,value) {
			    	    	panelInicialPral.down('[name="feFin"]').setValue(Ext.Date.add(value, Ext.Date.DAY, asegAlterno.pv_difDate));
			    	    }
			    	}
				},
		    	{	xtype		: 'datefield',	fieldLabel	: 'Fecha Fin',				name	: 'feFin',				labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true,						value	: asegAlterno.FEPROREN, readOnly  	: true
				}
	    	]
			,buttonAlign:'center'
			,buttons: [{
				text: 'Confirmar endoso'
				,icon:_CONTEXT+'/resources/fam3icons/icons/key.png'
				,buttonAlign : 'center',
				handler: function() {
					var formPanel = this.up().up();
					myMask.show();
					if (formPanel.form.isValid()) {
							var submitValues={};
							asegAlterno.FEEFECTO = Ext.Date.format(panelInicialPral.down('[name="feIngreso"]').getValue(),'d/m/Y');
	        				asegAlterno.FEPROREN = Ext.Date.format(panelInicialPral.down('[name="feFin"]').getValue(),'d/m/Y');
	        				asegAlterno.FEINIVAL = Ext.Date.format(panelInicialPral.down('[name="feInival"]').getValue(),'d/m/Y');
	        				submitValues['smap1']= asegAlterno;
	        				Ext.Ajax.request( {
	   						    url: guarda_Vigencia_Poliza,
	   						    jsonData: Ext.encode(submitValues),
	   						    success:function(response,opts){
	   						    	 myMask.hide();
	   						    	 panelInicialPral.setLoading(false);
	   						         var jsonResp = Ext.decode(response.responseText);
	   						      	 mensajeCorrecto("Endoso",jsonResp.respuesta,null);
	   						    },
	   						    failure:function(response,opts){
	   						    	myMask.hide();
	   						    	Ext.Msg.show({
	   						            title:'Error',
	   						            msg: 'Error de comunicaci&oacute;n',
	   						            buttons: Ext.Msg.OK,
	   						            icon: Ext.Msg.ERROR
	   						        });
	   						    }
	   						});
					}else {
						myMask.hide();
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