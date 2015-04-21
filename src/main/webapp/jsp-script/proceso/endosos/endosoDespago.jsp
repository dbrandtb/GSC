<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var _CONTEXT = '${ctx}';
	var paramsEntrada          = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
	var guarda_Despago = '<s:url namespace="/endosos" action=" guardarEndosoDespago"       />';
	
	debug('paramsEntrada  -->:',paramsEntrada);
	
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
		    	{	xtype		: 'datefield', 	fieldLabel	: 'Fecha Original',			name	: 'feEfecto',			hidden		: true,
					format		:  'm/d/Y', 	editable	: true,						value	: paramsEntrada.FEEFECTO
				},
				{	xtype		: 'datefield',	fieldLabel	: 'Fecha Minimo',			name	: 'feMin',				hidden		: true,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEEFECTO
				},
				{	xtype		: 'datefield',	fieldLabel	: 'Fecha Maximo',			name	: 'feMax',				hidden		: true,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEEFECTO
				},
				{	xtype		: 'datefield',	fieldLabel	: 'Fecha Inicio Endoso',	name	: 'feInival',			labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true, 					value   : new Date(),			allowBlank	: false,
					colspan		:2
				},
				{
					xtype		: 'datefield',	fieldLabel	: 'Fecha Efecto',			name	: 'feIngreso',			labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEEFECTO,	allowBlank	: false,
					listeners:{
			    	    change:function(field,value) {
			    	    	fechaFeefecto = new Date(panelInicialPral.down('[name="feEfecto"]').getValue());
			    	    	panelInicialPral.down('[name="feMin"]').setValue(Ext.Date.add(fechaFeefecto, Ext.Date.DAY, -(+paramsEntrada.pv_diasMinimo)));
			    	    	panelInicialPral.down('[name="feMax"]').setValue(Ext.Date.add(fechaFeefecto, Ext.Date.DAY, paramsEntrada.pv_diasMaximo));
			    	    	panelInicialPral.down('[name="feFin"]').setValue(Ext.Date.add(value, Ext.Date.DAY, paramsEntrada.pv_difDate));
			    	    }
			    	}
				},
		    	{	xtype		: 'datefield',	fieldLabel	: 'Fecha Fin',				name	: 'feFin',				labelWidth	: 150,
					format		: 'd/m/Y',		editable	: true,						value	: paramsEntrada.FEPROREN, readOnly  	: true
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
                        var fechaMinValMod = Ext.Date.format(panelInicialPral.down('[name="feMin"]').getValue(),'d/m/Y');
						var fechaMaxValMod = Ext.Date.format(panelInicialPral.down('[name="feMax"]').getValue(),'d/m/Y');
						var fechaEfectoMod = Ext.Date.format(panelInicialPral.down('[name="feIngreso"]').getValue(),'d/m/Y');
						
						if(validate_fechaMayorQue(fechaEfectoMod , fechaMinValMod) == 0 && validate_fechaMayorQue(fechaMaxValMod ,fechaEfectoMod) == 0){
						    //Exito
							var submitValues={};
							paramsEntrada.FEEFECTO = Ext.Date.format(panelInicialPral.down('[name="feIngreso"]').getValue(),'d/m/Y');
	        				paramsEntrada.FEPROREN = Ext.Date.format(panelInicialPral.down('[name="feFin"]').getValue(),'d/m/Y');
	        				paramsEntrada.FEINIVAL = Ext.Date.format(panelInicialPral.down('[name="feInival"]').getValue(),'d/m/Y');
	        				submitValues['smap1']= paramsEntrada;
	        				Ext.Ajax.request( {
	   						    url: guarda_Despago,
	   						    jsonData: Ext.encode(submitValues),
	   						    success:function(response,opts){
	   						    	 panelInicialPral.setLoading(false);
	   						         var jsonResp = Ext.decode(response.responseText);
	   						      	 mensajeCorrecto("Endoso",jsonResp.respuesta,null);
	   						      	 marendNavegacion(2);
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
		                        msg: 'La fecha efecto esta fuera del rango. Fecha Minimo: '+fechaMinValMod+' Fecha M&aacute;ximo:'+fechaMaxValMod,
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

		function validate_fechaMayorQue(fechaInicial,fechaFinal){
            debug("fechaInicial : "+fechaInicial+" fechaFinal : "+fechaFinal);
			valuesStart = fechaInicial.split("/");
            valuesEnd   = fechaFinal.split("/");
			 // Verificamos que la fecha no sea posterior a la actual
            var dateStart = new Date(valuesStart[2],(valuesStart[1]-1),valuesStart[0]);
			debug("dateStart -->",dateStart);
            var dateEnd = new Date(valuesEnd[2],(valuesEnd[1]-1),valuesEnd[0]);
            debug("dateEnd -->",dateEnd);
            if(dateStart >= dateEnd){
                return 0;
            }
            return 1;
        }
		
    });
</script>
<div id="maindivHist" style="height:1000px;"></div>