
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
	
///////// Inicia items de automovilesFormPanel
	var folio = new Ext.form.TextField({
		id: 'id-textfield-folio',
		fieldLabel: 'Folio',
        name: 'automovil.folio',
	    width: 140,
		disabled: true,
		value: _folio
	});
	
	var fecha = new Ext.form.DateField({
		id: 'id-datefield-fecha',
        fieldLabel: 'Fecha',
        name: 'automovil.fecha',
        format: 'd/m/Y',
        allowBlank: false,
        width: 100,
        disabled: true
    });

	var poliza = new Ext.form.TextField({
		id: 'id-textfield-poliza',
		fieldLabel: 'P&oacute;liza',
		name: 'automovil.poliza',
		width: 140,
		disabled: true,
		value: _poliza
	});
	
	var certificado = new Ext.form.TextField({
		id: 'id-textfield-certificado',
		fieldLabel: 'Certificado',
		name: 'automovil.certificado',
		width: 140,
		disabled: true,
		value: _certificado
	});
	
	var aseguradora = new Ext.form.TextField({
		id: 'id-textfield-aseguradora',
		fieldLabel: 'Aseguradora',
		name: 'automovil.aseguradora',
		width: 140,
		disabled: true,
		value: _aseguradora
	});
	
	var persona1Asegurado = new Ext.form.TextField({
		id: 'id-textfield-persona1Asegurado',
		fieldLabel: 'Asegurado',
		name: 'automovil.asegurado',
		width: 370,
		disabled: true,
		value: _asegurado
	});
	
	var persona2ReportadoPor = new Ext.form.TextField({
		id: 'id-textfield-persona2ReportadoPor',
		fieldLabel: 'Reportado por',
		name: 'automovil.reportadoPor',
		width: 370,
		value: _reportadoPor
	});
	
	var persona3Conductor = new Ext.form.TextField({
		id: 'id-textfield-persona3Conductor',
		fieldLabel: 'Conductor',
		name: 'automovil.conductor',
		width: 370,
		value: _conductor
	});
	
	var persona1Telefono = new Ext.form.NumberField({
		id: 'id-textfield-persona1Telefono',
		fieldLabel: 'Tel&eacute;fono',
		name: 'automovil.telefono1',
		allowNegative: false,
		allowDecimals: false,
		decimalPrecision: 0,
		disabled: true,
		width: 140
	});
	
	var persona2Telefono = new Ext.form.NumberField({
		id: 'id-textfield-persona2Telefono',
		fieldLabel: 'Tel&eacute;fono',
		name: 'automovil.telefono2',
		allowNegative: false,
		allowDecimals: false,
		decimalPrecision: 0,
		width: 140
	});
	
	var persona3Telefono = new Ext.form.NumberField({
		id: 'id-textfield-persona3Telefono',
		fieldLabel: 'Tel&eacute;fono',
		name: 'automovil.telefono3',
		allowNegative: false,
		allowDecimals: false,
		decimalPrecision: 0,
		width: 140
	});
	
	var marca = new Ext.form.TextField({
		id: 'id-textfield-marca',
		fieldLabel: 'Marca',
		name: 'automovil.marca',
		width: 140,
		disabled: true,
		value: _marca
	});
	
	var modelo = new Ext.form.TextField({
		id: 'id-textfield-modelo',
		fieldLabel: 'Modelo',
		name: 'automovil.modelo',
		width: 140,
		disabled: true,
		value: _modelo
	});
	
	var numeroMotor = new Ext.form.TextField({
		id: 'id-textfield-numeroMotor',
		fieldLabel: 'N&uacute;mero de Motor',
		name: 'automovil.numeroMotor',
		width: 140,
		disabled: true,
		value: _numeroMotor
	});
	
	var numeroSerie = new Ext.form.TextField({
		id: 'id-textfield-numeroSerie',
		fieldLabel: 'N&uacute;mero de Serie',
		name: 'automovil.numeroSerie',
		width: 140,
		disabled: true,
		value: _numeroSerie
	});
	
	var numeroPlacas = new Ext.form.TextField({
		id: 'id-textfield-numeroPlacas',
		fieldLabel: 'N&uacute;mero de Placas',
		name: 'automovil.numeroPlacas',
		width: 140,
		disabled: true,
		value: _numeroPlacas
	});

	var color = new Ext.form.TextField({
		id: 'id-textfield-color',
		fieldLabel: 'Color',
		name: 'automovil.color',
		width: 140,
		disabled: true,
		value: _color
	});
	
	var descripcionLugarVehiculo = new Ext.form.TextField({
		id: 'id-textarea-descripcionLugarVehiculo',
		fieldLabel: 'Lugar donde se encuentra el veh&iacute;culo',
		name: 'automovil.lugarVehiculo',
		width: 600,
		height: 60,
		value: _lugarVehiculo
	});
	
	var colonia = new Ext.form.TextField({
		id: 'id-textfield-colonia',
		fieldLabel: 'Colonia',
		name: 'automovil.colonia',
		width: 140,
		value: _colonia
	});
	
	var delegacion = new Ext.form.TextField({
		id: 'id-textfield-delegacion',
		fieldLabel: 'Delegaci&oacute;n',
		name: 'automovil.delegacion',
		width: 140,
		value: _delegacion
	});

	var descripcionAccidente = new Ext.form.TextField({
		id: 'id-textarea-descripcionAccidente',
		fieldLabel: 'C&oacute;mo ocurri&oacute; el accidente',
		name: 'automovil.descripcionAccidente',
		width: 600,
		height: 60,
		value: _descripcionAccidente
	});
	
	var fechaAccidente = new Ext.form.DateField({
		id: 'id-datefield-fechaAccidente',
        fieldLabel: 'Fecha',
        name: 'automovil.fechaAccidente',
        format: 'd/m/Y',
        width: 100
    });
    
	var horaAccidente = new Ext.form.TextField({
		id: 'id-textfield-horaAccidente',
		fieldLabel: 'Hora',
		name: 'automovil.horaAccidente',
		width: 100,
		value: _horaAccidente
	});
	
	var persona4Tercero = new Ext.form.TextField({
		id: 'id-textfield-persona4Tercero',
		fieldLabel: 'Tercero',
		name: 'automovil.tercero',
		width: 280,
		value: _tercero
	});
	
	var taller = new Ext.form.TextField({
		id: 'id-textfield-taller',
		fieldLabel: 'Taller',
		name: 'automovil.taller',
		width: 259,
		value: _taller
	});
	
	var persona5PersonalAseguradora = new Ext.form.TextField({
		id: 'id-textfield-persona5PersonalAseguradora',
		fieldLabel: 'Se report&oacute; a personal de la aseguradora',
		name: 'automovil.reportoAPersonal',
		labelWidth: 150,
		width: 280,
		value: _reportoAPersonal
	});
	
	var fechaPersona5 = new Ext.form.DateField({
		id: 'id-datefield-fechaPersona5',
        fieldLabel: 'Fecha',
        name: 'automovil.fechaReportada',
        format: 'd/m/Y',
        width: 100
    });
    
	var horaPersona5 = new Ext.form.TextField({
		id: 'id-textfield-horaPersona5',
		fieldLabel: 'Hora',
		name: 'automovil.horaReportada',
		width: 100,
		value: _horaReportada
	});
	
	var numeroReporte = new Ext.form.TextField({
		id: 'id-textfield-numeroReporte',
		fieldLabel: 'N&uacute;mero de Reporte de la aseguradora',
		name: 'automovil.numeroReporte',
		labelWidth: 150,
		width: 280,
		value: _numeroReporte
	});
	
	var fechaNumeroReporte = new Ext.form.DateField({
		id: 'id-datefield-fechaNumeroReporte',
        fieldLabel: 'Fecha',
        name: 'automovil.fechaReporte',
        format: 'd/m/Y',
        width: 100
    });
    
	var horaNumeroReporte = new Ext.form.TextField({
		id: 'id-textfield-horaNumeroReporte',
		fieldLabel: 'Hora',
		name: 'automovil.horaReporte',
		width: 100,
		value: _horaReporte
	});

    if ( _TIPO_OPERACION == "I" ) {
		Ext.getCmp('id-datefield-fecha').setValue( fechaHoy );
    } else if ( _TIPO_OPERACION == "U" ) {
    	_fecha = _fecha.substring(8,10) + "/" + _fecha.substring(5,7) + "/"  + _fecha.substring(0,4);
    	Ext.getCmp('id-datefield-fecha').setValue( _fecha );
    }
	if ( !Ext.isEmpty(_telefono1))
		Ext.getCmp('id-textfield-persona1Telefono').setValue(_telefono1);
	if ( !Ext.isEmpty(_telefono2))
		Ext.getCmp('id-textfield-persona2Telefono').setValue(_telefono2);
	if ( !Ext.isEmpty(_telefono3))
		Ext.getCmp('id-textfield-persona3Telefono').setValue(_telefono3);
	if ( !Ext.isEmpty(_fechaAccidente)) {
		_fechaAccidente = _fechaAccidente.substring(8,10) + "/" + _fechaAccidente.substring(5,7) + "/"  + _fechaAccidente.substring(0,4);
		Ext.getCmp('id-datefield-fechaAccidente').setValue(_fechaAccidente);
	}
	if ( !Ext.isEmpty(_fechaReportada)) {
		_fechaReportada = _fechaReportada.substring(8,10) + "/" + _fechaReportada.substring(5,7) + "/"  + _fechaReportada.substring(0,4);
		Ext.getCmp('id-datefield-fechaPersona5').setValue(_fechaReportada);
	}
	if ( !Ext.isEmpty(_fechaReporte)) {
		_fechaReporte = _fechaReporte.substring(8,10) + "/" + _fechaReporte.substring(5,7) + "/"  + _fechaReporte.substring(0,4);
		Ext.getCmp('id-datefield-fechaNumeroReporte').setValue(_fechaReporte);
	}
			
///////// Inicia items de automovilesFormPanel
	var tituloMainFormPanel = ""; 
	if ( _TIPO_OPERACION == "I" )
		tituloMainFormPanel = '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Alta de Presiniestro</span>';
	else if ( _TIPO_OPERACION == "U" )
		tituloMainFormPanel = '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Edici&oacute;n de Presiniestro</span>';

	var automovilesFormPanel = new Ext.form.FormPanel({
		id: 'id-automoviles-formpanel',
		title: tituloMainFormPanel,
		url: _ACTION_GUARDAR_PRESINIESTRO_AUTOMOVILES,
		layout: 'form',
		bodyStyle: 'padding:5px 5px 0',
		buttonAlign: 'left',
		labelAlign: 'right',
		labelWidth: 100,
		border: false,
		width: 860,
		autoHeight: true,
		items:[{
			layout: 'form',
			border: false,
			items:[{
				id: 'id-form-automoviles-1',
				layout: 'form',
				border: false,
				width: 715,
				items:[{
					layout: 'column',
					border: false,
					items:[{
						columnWidth: .35,
						layout: 'form',
						width: 247,
						border: false,
						items: [{
							id: 'id-form-layout-blank-space',
							html: '&nbsp;',
							border: false,
							hidden: true,
							width: 247
						},{
							layout: 'form',
							id: 'id-form-layout-folio',
							width: 247,
							border: false,
							hidden: true,
							items: [ folio ]
						}]
					},{
						columnWidth: .32,
						layout: 'form',
						width: 234,
						labelAlign: 'left',
						labelWidth: 80,
						border: false,
						items: [{
							html: '&nbsp;',
							border: false,
							width: 234
						}]
					},{
						columnWidth: .32,
						layout: 'form',
						width: 234,
						labelAlign: 'left',
						labelWidth: 80,
						border: false,
						items: [ fecha ]
					}]
				}]

			},{

				layout: 'form', 
				border: false, 
				heigth: '20',
				items: [{
					html: '&nbsp;',
					border: false
				}]

			},{

				id: 'id-form-automoviles-2',
				layout: 'form',
				border: false,
				width: 715,
				items:[{
					layout: 'column',
					border: false,
					items:[{
						columnWidth: .35,
						layout: 'form',
						width: 247,
						border: false,
						items: [ poliza ]
					},{
						columnWidth: .32,
						layout: 'form',
						width: 234,
						labelAlign: 'left',
						labelWidth: 80,
						border: false,
						items: [ certificado ]
					},{
						columnWidth: .32,
						layout: 'form',
						width: 234,
						labelAlign: 'left',
						labelWidth: 80,
						border: false,
						items: [ aseguradora ]
					}]
				}]

			},{

				id: 'id-form-automoviles-3',
				layout: 'form',
				border: false,
				width: 715,
				items:[{
					layout: 'column',
					border: false,
					items:[{
						columnWidth: .67,
						layout: 'form',
						width: 479,
						border: false,
						items: [ persona1Asegurado, persona2ReportadoPor, persona3Conductor ]
					},{
						columnWidth: .32,
						layout: 'form',
						width: 234,
						labelAlign: 'left',
						labelWidth: 80,
						border: false,
						items: [ persona1Telefono, persona2Telefono, persona3Telefono ]
					}]
				}]

			},{

				layout: 'form', 
				border: false, 
				heigth: '20',
				items: [{
					html: '&nbsp;',
					border: false
				}]

			},{

				id: 'id-form-automoviles-4',
				layout: 'form',
				border: false,
				width: 715,
				items:[{
					layout: 'column',
					border: false,
					items:[{
						columnWidth: .35,
						layout: 'form',
						width: 247,
						border: false,
						items: [ marca, numeroSerie ]
					},{
						columnWidth: .32,
						layout: 'form',
						width: 234,
						labelAlign: 'left',
						labelWidth: 80,
						border: false,
						items: [ modelo, numeroPlacas ]
					},{
						columnWidth: .32,
						layout: 'form',
						width: 234,
						labelAlign: 'left',
						labelWidth: 80,
						border: false,
						items: [ numeroMotor, color ]
					}]
				}]

			},{

				layout: 'form', 
				border: false, 
				heigth: '20',
				items: [{
					html: '&nbsp;',
					border: false
				}]

			},{

				id: 'id-form-automoviles-5',
				layout: 'form',
				border: false,
				width: 715,
				items:[ descripcionLugarVehiculo ]
		
			},{

				id: 'id-form-automoviles-6',
				layout: 'form',
				border: false,
				width: 715,
				items:[{
					layout: 'column',
					border: false,
					items:[{
						columnWidth: .35,
						layout: 'form',
						width: 247,
						border: false,
						items: [ colonia ]
					},{
						columnWidth: .32,
						layout: 'form',
						width: 234,
						labelAlign: 'left',
						labelWidth: 80,
						border: false,
						items: [{
							html: '&nbsp;',
							border: false,
							width: 234
						}]
					},{
						columnWidth: .32,
						layout: 'form',
						width: 234,
						labelAlign: 'left',
						labelWidth: 80,
						border: false,
						items: [ delegacion ]
					}]
				}]

			},{

				id: 'id-form-automoviles-7',
				layout: 'form',
				border: false,
				width: 715,
				items:[ descripcionAccidente ]

			},{

				id: 'id-form-automoviles-8',
				layout: 'form',
				border: false,
				width: 715,
				items:[{

					layout: 'column',
					border: false,
					items:[{
						columnWidth: .55,
						layout: 'form',
						width: 393,
						border: false,
						items: [{
							html: '&nbsp;',
							border: false,
							width: 393
						}]
					},{
						columnWidth: .22,
						layout: 'form',
						width: 161,
						labelAlign: 'left',
						labelWidth: 50,
						border: false,
						items: [ fechaAccidente ]
					},{
						columnWidth: .22,
						layout: 'form',
						width: 161,
						labelAlign: 'left',
						labelWidth: 50,
						border: false,
						items: [ horaAccidente ]
					}]
					
				}]

			},{

				layout: 'form', 
				border: false, 
				heigth: '20',
				items: [{
					html: '&nbsp;',
					border: false
				}]

			},{

				id: 'id-form-automoviles-9',
				layout: 'form',
				border: false,
				width: 715,
				items:[{
					layout: 'column',
					border: false,
					items:[{
						columnWidth: .55,
						layout: 'form',
						width: 393,
						border: false,
						items: [ persona4Tercero ]
					},{
						columnWidth: .45,
						layout: 'form',
						width: 322,
						labelAlign: 'left',
						labelWidth: 50,
						border: false,
						items: [ taller ]
					}]
				}]

			},{

				id: 'id-form-automoviles-10',
				layout: 'form',
				border: false,
				width: 715,
				items:[{
					layout: 'column',
					border: false,
					items:[{
						columnWidth: .55,
						layout: 'form',
						width: 393,
						border: false,
						items: [ persona5PersonalAseguradora, numeroReporte ]
					},{
						columnWidth: .22,
						layout: 'form',
						width: 161,
						labelAlign: 'left',
						labelWidth: 50,
						border: false,
						items: [ fechaPersona5, fechaNumeroReporte ]
					},{
						columnWidth: .22,
						layout: 'form',
						width: 161,
						labelAlign: 'left',
						labelWidth: 50,
						border: false,
						items: [ horaPersona5, horaNumeroReporte ]
					}]
				}]
			}]
///////// termina items
		}],
        buttons: [{

			text: 'Guardar',
			id: 'id-button-guardar',
			buttonAlign: 'left',
			handler: function(){

				if ( automovilesFormPanel.form.isValid() ) {
						automovilesFormPanel.form.submit({
							params: {tipoOperacion: _TIPO_OPERACION},
							waitTitle: 'Espere',
							waitMsg: 'Procesando...',
							failure: function(form, action) {
								Ext.MessageBox.alert('Error','Error al guardar presiniestro');
							},
							success: function(form, action) {
								Ext.MessageBox.alert('Aviso','El presiniestro ha sido guardado', function () { window.location = _ACTION_CONSULTA_PRESINIESTROS; });
							}
						});
				} else {
					Ext.MessageBox.alert('Error', 'Favor de llenar los campos requeridos');
				}

			}

		},{

			text: 'Regresar',
			id: 'id-button-regresar',
			buttonAlign: 'left',
			handler: function(){

				window.location = _ACTION_CONSULTA_PRESINIESTROS;

			}
			
		}]
		
	});

	if ( _TIPO_OPERACION == "I" ) {
		Ext.getCmp('id-form-layout-folio').hide();
		Ext.getCmp('id-form-layout-blank-space').show();
	} else if ( _TIPO_OPERACION == "U" ) {
		Ext.getCmp('id-form-layout-folio').show();
		Ext.getCmp('id-form-layout-blank-space').hide();
	}
	
	var panelPrincipal = new Ext.Panel({
        region: 'north',
        title: 'Pre-Siniestros Autom&oacute;viles',
        autoHeight : true,
        width: 720,
        id: 'panelPrincipal',
        items: [ automovilesFormPanel ] 
    });

	panelPrincipal.render('items');
});
