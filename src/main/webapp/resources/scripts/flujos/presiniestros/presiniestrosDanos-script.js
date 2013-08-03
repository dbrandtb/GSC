
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

///////// Inicia items de danosFormPanel
	var folio = new Ext.form.TextField({
		id: 'id-textfield-folio',
		fieldLabel: 'Folio',
        name: 'dano.folio',
	    width: 140,
		disabled: true,
		value: _folio
	});
	
	var producto = new Ext.form.TextField({
		id: 'id-textfield-producto',
		fieldLabel: 'Producto',
		name: 'dano.producto',
		width: 100,
		disabled: true,
		value: _producto
	});
	
	var fecha = new Ext.form.DateField({
		id: 'id-datefield-fecha',
        fieldLabel: 'Fecha',
        name: 'dano.fecha',
        format: 'd/m/Y',
        allowBlank: false,
        width: 100,
        disabled: true
    });

	var poliza = new Ext.form.TextField({
		id: 'id-textfield-poliza',
		fieldLabel: 'P&oacute;liza',
		name: 'dano.poliza',
		width: 100,
		disabled: true,
		value: _poliza
	});
	
	var inciso = new Ext.form.TextField({
		id: 'id-textfield-inciso',
		fieldLabel: 'Inciso',
		name: 'dano.inciso',
		width: 35,
		disabled: true,
		value: _inciso
	});
	
	var aseguradora = new Ext.form.TextField({
		id: 'id-textfield-aseguradora',
		fieldLabel: 'Aseguradora',
		name: 'dano.aseguradora',
		width: 170,
		disabled: true,
		value: _aseguradora
	});
	
	var ramo = new Ext.form.TextField({
		id: 'id-textfield-ramo',
		fieldLabel: 'Ramo',
		name: 'dano.ramo',
		width: 135,
		disabled: true,
		value: _ramo
	});
	
	var nombreAsegurado = new Ext.form.TextField({
		id: 'id-textfield-nombre-asegurado',
		fieldLabel: 'Nombre del Asegurado',
		name: 'dano.nombreAsegurado',
		width: 200,
		disabled: true,
		value: _nombreAsegurado
	});
	
	var personaRecibeReporte = new Ext.form.TextField({
		id: 'id-textfield-persona-recibe-reporte',
		fieldLabel: 'Quien recibe el reporte',
		name: 'dano.personaRecibeReporte',
		width: 200,
		maxLength: 150,
		value: _personaRecibeReporte
	});
	
	var embarqueBienesDanados = new Ext.form.TextField({
		id: 'id-textfield-embarque-bienes-danados',
		fieldLabel: 'Embarque o bienes da&ntilde;ados',
		name: 'dano.embarqueBienesDanados',
		width: 200,
		height: 60,
		maxLength: 200,
		value: _embarqueBienesDanados
	});
	
	var descripcionDano = new Ext.form.TextField({
		id: 'id-textfield-descripcion-dano',
		fieldLabel: 'Forma en que ocurri&oacute; el da&ntilde;o',
		name: 'dano.descripcionDano',
		width: 550,
		height: 60,
		maxLength: 500,
		value: _descripcionDano
	});

	var fechaDano = new Ext.form.DateField({
		id: 'id-datefield-fecha-dano',
        fieldLabel: 'Fecha de recibido o cuando ocurri&oacute; el da&ntilde;o',
        name: 'dano.fechaDano',
        format: 'd/m/Y',
        allowBlank: false,
        width: 100
    });

	var lugarBienesAfectados = new Ext.form.TextField({
		id: 'id-textfield-lugar-bienes-afectados',
		fieldLabel: 'Lugar donde se encuentran los bienes afectados',
		name: 'dano.lugarBienesAfectados',
		width: 200,
		maxLength: 200,
		value: _lugarBienesAfectados
	});
	
	var personaEntrevista = new Ext.form.TextField({
		id: 'id-textfield-persona-entrevista',
		fieldLabel: 'Con qu&eacute; persona se entrevistan',
		name: 'dano.personaEntrevista',
		labelWidth: 150,
		width: 200,
		maxLength: 150,
		value: _personaEntrevista
	});

	var telefono1 = new Ext.form.NumberField({
		id: 'id-textfield-telefono-persona-entrevisa',
		fieldLabel: 'Tel&eacute;fono',
		name: 'dano.telefono1',
		allowNegative: false,
		allowDecimals: false,
		decimalPrecision: 0,
		width: 140
	});

	var estimacionDano = new Ext.form.NumberField({
		id: 'id-numberfield-estimacion-dano',
		fieldLabel: 'Estimaci&oacute;n del da&ntilde;o',
		name: 'dano.estimacionDano',
		labelWidth: 150,
		allowNegative: false,
		allowDecimals: false,
		decimalPrecision: 0,
		maxLength: 20,
		width: 100
	});

	var personaReporto = new Ext.form.TextField({
		id: 'id-textfield-persona-reporto',
		fieldLabel: 'Qui&eacute;n report&oacute;',
		name: 'dano.personaReporto',
		labelWidth: 150,
		width: 200,
		maxLength: 150,
		value: _personaReporto
	});	
	
	var telefono2 = new Ext.form.NumberField({
		id: 'id-textfield-telefono-persona-reporta',
		fieldLabel: 'Tel&eacute;fono',
		name: 'dano.telefono2',
		allowNegative: false,
		allowDecimals: false,
		decimalPrecision: 0,
		maxLength: 15,
		width: 140
	});	

    if ( _TIPO_OPERACION == "I" ) {
		Ext.getCmp('id-datefield-fecha').setValue( fechaHoy );
    } else if ( _TIPO_OPERACION == "U" ) {
    	Ext.getCmp('id-datefield-fecha').setValue( _fecha );
    }
	if ( !Ext.isEmpty(_telefono1))
		Ext.getCmp('id-textfield-telefono-persona-entrevisa').setValue(_telefono1);
	if ( !Ext.isEmpty(_telefono2))
		Ext.getCmp('id-textfield-telefono-persona-reporta').setValue(_telefono2);
	if ( !Ext.isEmpty(_estimacionDano))
		Ext.getCmp('id-numberfield-estimacion-dano').setValue(_estimacionDano);
	if ( !Ext.isEmpty(_fechaDano)) {
		Ext.getCmp('id-datefield-fecha-dano').setValue(_fechaDano);
	}

///////// Inicia items de automovilesFormPanel
	var tituloMainFormPanel = ""; 
	if ( _TIPO_OPERACION == "I" )
		tituloMainFormPanel = '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Alta de Presiniestro</span>';
	else if ( _TIPO_OPERACION == "U" )
		tituloMainFormPanel = '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Edici&oacute;n de Presiniestro</span>';

	var danosFormPanel = new Ext.form.FormPanel({
		id: 'id-danos-formpanel',
		title: tituloMainFormPanel,
		url: _ACTION_GUARDAR_PRESINIESTRO_DANOS,
		layout: 'form',
		bodyStyle: 'padding:5px 5px 0',
		buttonAlign: 'left',
		labelAlign: 'right',
		labelWidth: 100,
		width: 860,
		border: false,
		autoHeight: true,
		items:[{
			layout: 'form',
			border: false,
			items:[{
				id: 'id-form-danos-1',
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
						items: [ producto ]
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

				id: 'id-form-danos-2',
				layout: 'form',
				border: false,
				width: 715,
				items:[{
					layout: 'column',
					border: false,
					items:[{
						columnWidth: .26,
						layout: 'form',
						width: 185,
						labelAlign: 'left',
						labelWidth: 80,
						border: false,
						items: [ poliza ]
					},{
						columnWidth: .13,
						layout: 'form',
						width: 90,
						labelAlign: 'left',
						labelWidth: 50,
						border: false,
						items: [ inciso ]
					},{
						columnWidth: .35,
						layout: 'form',
						width: 250,
						labelAlign: 'left',
						labelWidth: 70,
						border: false,
						items: [ aseguradora ]
					},{
						columnWidth: .26,
						layout: 'form',
						width: 185,
						labelAlign: 'left',
						labelWidth: 40,
						border: false,
						items: [ ramo ]
					}]
				}]

			},{

				id: 'id-form-danos-3',
				layout: 'form',
				width: 715,
				labelAlign: 'left',
				labelWidth: 150,
				border: false,
				items:[ 
					nombreAsegurado, 
					personaRecibeReporte, 
					embarqueBienesDanados, 
					descripcionDano, 
					fechaDano, 
					lugarBienesAfectados 
				]

			},{

				layout: 'form', 
				border: false, 
				heigth: '20',
				items: [{
					html: '&nbsp;',
					border: false
				}]

			},{

				id: 'id-form-danos-4',
				layout: 'form',
				width: 715,
				labelAlign: 'left',
				labelWidth: 150,
				border: false,
				items:[{
					layout: 'column',
					border: false,
					items:[{
						columnWidth: .50,
						layout: 'form',
						width: 357,
						border: false,
						items: [ personaEntrevista, estimacionDano, personaReporto ]
					},{
						columnWidth: .50,
						layout: 'form',
						width: 357,
						labelAlign: 'left',
						labelWidth: 80,
						border: false,
						items: [ 
							telefono1, 
							{
								layout: 'form', 
								border: false, 
								heigth: '40',
								items: [{
									html: '&nbsp;<br>&nbsp;',
									border: false
								}]
							}, 
							telefono2 
						]
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

				if ( danosFormPanel.form.isValid() ) {
						danosFormPanel.form.submit({
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
        title: 'Pre-Siniestros Da&ntilde;os',
        autoHeight : true,
        width: 720,
        id: 'panelPrincipal',
        items: [ danosFormPanel ] 
    });

	panelPrincipal.render('items');
});
