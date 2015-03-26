<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	var _CONTEXT = '${ctx}';
	var asegAlterno          = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
	var guarda_Aseg_alterno  = '<s:url namespace="/endosos" action=" guardarEndosoAseguradoAlterno"       />';
	var guarda_Vigencia_Poliza = '<s:url namespace="/endosos" action=" guardarEndosoVigenciaPoliza"       />';
	
	debug('asegAlterno  -->:',asegAlterno);
	
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
					xtype		: 'datefield'
					,fieldLabel	: 'Fecha Original'
					,name		: 'feEfecto'
					,format		:  'm/d/Y'
					,editable	: true		
					,value		: asegAlterno.FEEFECTO
					,hidden		: true
				},
				{
					xtype		: 'datefield'
					,fieldLabel	: 'Fecha Min'
					,name		: 'feMin'
					,format		: 'd/m/Y'
					,editable	: true		
					,value		: asegAlterno.FEEFECTO
					,hidden		: true
				},
				{
					xtype		: 'datefield'
					,fieldLabel	: 'feMax'
					,name		: 'feMax'
					,format		: 'd/m/Y'
					,editable	: true		
					,value		: asegAlterno.FEEFECTO
					,hidden		: true
				},{
					xtype		: 'datefield'
					,fieldLabel	: 'Fecha Efecto'
					,name		: 'feIngreso'
					,format		: 'd/m/Y'
					,editable	: true		
					,value		: asegAlterno.FEEFECTO
					,listeners:{
			    	    change:function(field,value)
			    	    {
			    	    	fechaFeefecto = new Date(panelInicialPral.down('[name="feEfecto"]').getValue());
			    	    	panelInicialPral.down('[name="feMin"]').setValue(Ext.Date.add(fechaFeefecto, Ext.Date.DAY, -(+asegAlterno.pv_diasMinimo)));
			    	    	panelInicialPral.down('[name="feMax"]').setValue(Ext.Date.add(fechaFeefecto, Ext.Date.DAY, asegAlterno.pv_diasMaximo));
			    	    	panelInicialPral.down('[name="feFin"]').setValue(Ext.Date.add(value, Ext.Date.DAY, asegAlterno.pv_difDate));
			    	    }
			    	}
				},
		    	{
					xtype		: 'datefield'
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
                        //1.- Verificamos la información de las fechas
                        fechaMinVal = new Date(panelInicialPral.down('[name="feMin"]').getValue());
						fechaMaxVal = new Date(panelInicialPral.down('[name="feMax"]').getValue());
						dateEfecto  = new Date(panelInicialPral.down('[name="feIngreso"]').getValue());
						
						var fechaMinValMod = fechaMinVal.getDate() + "/" + (fechaMinVal.getMonth() + 1) + "/" + fechaMinVal.getFullYear();
						var fechaMaxValMod = fechaMaxVal.getDate() + "/" + (fechaMaxVal.getMonth() + 1) + "/" + fechaMaxVal.getFullYear();
						var	fechaEfectoMod = dateEfecto.getDate() + "/" + (dateEfecto.getMonth() + 1) + "/" + dateEfecto.getFullYear();
						
						if(validate_fechaMayorQue(fechaEfectoMod , fechaMinValMod) == 0 && validate_fechaMayorQue(fechaMaxValMod ,fechaEfectoMod) == 0){
						    //Exito
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

		function validate_fechaMayorQue(fechaInicial,fechaFinal)
        {
            valuesStart=fechaInicial.split("/");
            valuesEnd=fechaFinal.split("/");
			 // Verificamos que la fecha no sea posterior a la actual
            var dateStart=new Date(valuesStart[2],(valuesStart[1]-1),valuesStart[0]);
			debug("dateStart -->",dateStart);
            var dateEnd=new Date(valuesEnd[2],(valuesEnd[1]-1),valuesEnd[0]);
            debug("dateEnd -->",dateEnd);
            if(dateStart>=dateEnd)
            {
                return 0;
            }
            return 1;
        }
		
    });
</script>
<div id="maindivHist" style="height:1000px;"></div>