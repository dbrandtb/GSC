function insertarOActualizar(record) {

	// console.log(cdNotificacion);
	/*
	 * var codigoTipoArchivo = new Ext.form.Hidden({ id: 'cdtipoarId',
	 * name:'cdtipoar', value: (record)?record.get('cdTipoar'):"" });
	 */

	var formWindowInsertar = new Ext.FormPanel({

				// title:'<span class="x-form-item"
				// style="color:black;font-size:12px;font:Arial,Helvetica,
				// Sans-serif;">Cat&aacute;logo Sistema Externo</span></br>',
				title : '<span style="color:black;font-size:12px;">'+ getLabelFromMap('115', helpMap,'Cat&aacute;logo Sistema Externo') + '</span>',
				iconCls : 'logo',
				bodyStyle : 'background: white',
				buttonAlign : "center",
				labelAlign : 'center',
				frame : true,
				url : _ACTION_GUARDAR_TCATAEXT,
				width : 600,
				//autoHeight:true,
				height : 300,
				layout : 'table',
				frame: true,
				// labelAlign: 'top',
				layoutConfig : {columns : 2,width :290
				},
				items : [{
					layout : 'form',
					labelAlign : 'top',
					labelWidth : 40,
					colspan : 2,

					items : [{
						xtype : 'textfield',
						fieldLabel : getLabelFromMap('agrConfTipEndTxtCod',helpMap, 'Clave 01'),
						tooltip : getToolTipFromMap('agrConfTipEndTxtCod',helpMap, 'Clave 01'),
						id : 'clave1Id',
						
						allowBlank : false,
				
					
						name : 'clave1',
						disabled : false,
						width : 80
					}]
				}

				,

				{

					layout : 'form',
					labelAlign : 'top',
					labelWidth : 40,
					colspan : 2,
					items : [{
						xtype : 'textfield',
						fieldLabel : getLabelFromMap('agrConfTipEndTxtCod',helpMap, 'Clave 02'),
						tooltip : getToolTipFromMap('agrConfTipEndTxtCod',helpMap, 'Clve 02'),
						id : 'clave2Id',
						name : 'clave2',
						disabled : false,
						// anchor: '100%'
						width : 80
					}]

				}, {
					layout : 'form',
					labelAlign : 'top',
					//labelWidth : 40,
					// colspan:2,
					width: 100,
					items : [{

						xtype : 'textfield',
						fieldLabel : getLabelFromMap('agrConfTipEndTxtCod',helpMap, 'Clave 03'),
						tooltip : getToolTipFromMap('agrConfTipEndTxtCod',helpMap, 'Clave 03'),
						id : 'clave3Id',
						name : 'clave3',
						disabled : false,
						// anchor: '100%'
						width : 80
					}]

				},

				{

					layout : 'form',
					//labelAlign: 'top',
					labelWidth : 60,
					width: 310,
					// colspan:2,
					items : [{
						xtype : 'textfield',
						fieldLabel : getLabelFromMap('agrConfTipEndTxtCod',helpMap, 'OTVALOR'),
						tooltip : getToolTipFromMap('agrConfTipEndTxtCod',helpMap, 'OTVALOR'),
						id : 'otValorId',
						allign:'right',
						allowBlank : false,
						name : 'otValor',
						disabled : false,
						// anchor: '100%'
						width : 120
					}]
				}, {
					layout : 'form',
					labelAlign : 'top',
					colspan : 2,
					labelWidth : 40,

					items : [{

						xtype : 'textfield',
						fieldLabel : getLabelFromMap('agrConfTipEndTxtCod',helpMap, 'Clave 04'),
						tooltip : getToolTipFromMap('agrConfTipEndTxtCod',helpMap, 'Clave 04'),
						id : 'clave4Id',
						name : 'clave4',
						disabled : false,
						// anchor: '100%'
						width : 80
					}]
				}, {
					layout : 'form',
					labelAlign : 'top',
					colspan : 2,
					labelWidth : 40,

					items : [{

						xtype : 'textfield',
						fieldLabel : getLabelFromMap('agrConfTipEndTxtCod',helpMap, 'Clave 05'),
						tooltip : getToolTipFromMap('agrConfTipEndTxtCod',helpMap, 'Clve 05'),
						id : 'clave5Id',
						name : 'clave5',
						disabled : false,
						// anchor: '100%'
						width : 80
					}]
				}

				]

			});

			
			function vacio(q) { //la variable q contiene el valor del texbox
				
				 alert(q);

for ( i = 0; i < q.length; i++ ) { //la funcion q.length devuelve el tamaño de la palabra contenia por el textbox

if ( q.charAt(i) != " " ) {//la funcion q.charAt nos deuelve el caracter contenido en la posicion i de la variable q

return true

}

}

return false;

}

//valida que el campo no este vacio y no tenga solo espacios en blanco

function valida() {
	 var F= Ext.getCmp("clave1Id").getValue();
	 var otvalor=Ext.getCmp("otValorId").getValue();
	 
	//  alert('chartAt: '+F.charAt(0));
//if( (vacio(F) == false) ) {
 if( (F.charAt(0) == " ")||(otvalor.charAt(0) == " ") ) {

	return false

} else {


return true

}

}
			
			
			
			
	var window = new Ext.Window({

		title : '<span style="color:black;font-size:12px;">'
				+ getLabelFromMap('115', helpMap, 'Insertar / Actualizar')
				+ '</span>',
		// labelAlign: 'center',
		width : 450,
		height : 350,
		id : 'windowId',
		minWidth : 300,
		minHeight : 100,
		modal : true,
		layout : 'fit',
		plain : true,
		bodyStyle : 'padding:5px;',
		buttonAlign : 'center',
		items : formWindowInsertar,
		buttons : [{
			text : getLabelFromMap('agrConfTipEndBtnSave', helpMap, 'Guardar'),
			tooltip : getToolTipFromMap('agrConfTipEndBtnSave', helpMap,'Guardar'),
			disabled : false,
			handler : function() {			
				
								
				if (formWindowInsertar.form.isValid()) {
					if (valida()){guardarDatos();
					}
					else 
					{	
					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'),getLabelFromMap('400122', helpMap,'No puede ingresar caracteres en blanco al inicio del dato'));
				}
				
				} else {
					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
				}

			}
		}, {
			text : getLabelFromMap('agrConfTipEndBtnBack', helpMap, 'Regresar'),
			tooltip : getToolTipFromMap('agrConfTipEndBtnBack', helpMap,'Regresa a la pantalla anterior'),
			handler : function() {
				window.close();
			}
		}]
	});
	
	
	var idABuscar;
	for(i=1;i<=_COLUMNA;i++)
	{
	idABuscar="clave"+i+"Id";	
	Ext.getCmp(idABuscar).allowBlank=false;
	}
	
	for(i=5;i>_COLUMNA;i--)
	{
	idABuscar="clave"+i+"Id";
	Ext.getCmp(idABuscar).setDisabled(true);
	}

	window.show();

	var op;
	op = "Agregar";
	if (record) {
		// Ext.getCmp("descrArchId").setDisabled(true);
		Ext.getCmp("clave1Id").setValue(record.get("dsClave1"));
		Ext.getCmp("clave2Id").setValue(record.get("dsClave2"));
		Ext.getCmp("clave3Id").setValue(record.get("dsClave3"));
		Ext.getCmp("clave4Id").setValue(record.get("dsClave4"));
		Ext.getCmp("clave5Id").setValue(record.get("dsClave5"));
		Ext.getCmp("otValorId").setValue(record.get("dsOtValor"));
		Ext.getCmp("clave1Id").setDisabled(true);
		Ext.getCmp("clave2Id").setDisabled(true);
		Ext.getCmp("clave3Id").setDisabled(true);
		Ext.getCmp("clave4Id").setDisabled(true);
		Ext.getCmp("clave5Id").setDisabled(true);
		op = "Editar";
		//Ext.getCmp("otValorId").setDisabled(true);

	}

	// ************************************************************************************************************************
	function guardarDatos() {
		
		
	
		
		// vacio(Ext.getCmp("clave1Id").getValue());
			
		// alert(Ext.getCmp("comboTipoArchivoId").getValue()+" -
		// "+Ext.getCmp("descrArchId").getValue()+" -
		// "+Ext.getCmp("cdtipoarId").getValue());
		
		var _params = {
			cdPais : _CODPAIS,// Ext.getCmp("descrArchId").getValue(),//record.get("codPaisId"),
			cdSistem : _CODSISTEMA,// Ext.getCmp("descrArchId").getValue(),
			otClave : Ext.getCmp("clave1Id").getValue(), // record?record.get('cdTipoar'):"",
			otValor : Ext.getCmp("otValorId").getValue(),
			otClave2 : Ext.getCmp("clave2Id").getValue(),
			otClave3 : Ext.getCmp("clave3Id").getValue(),
			otClave4 : Ext.getCmp("clave4Id").getValue(),
			otClave5 : Ext.getCmp("clave5Id").getValue(),
			cdTabla : _CDTABLA,
			operacion: op
			// Ext.getCmp("descrArchId").getValue(), //record.get("dsOtValor")

		};
		
		startMask(formWindowInsertar.id, "Guardando datos...");
		execConnection(_ACTION_GUARDAR_TCATAEXT, _params, cbkGuardarDatos);
	}

	function cbkGuardarDatos(_success, _message, _response) {
		endMask();
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Error'),_message);
		} else {
			// Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),
			// _message,
			// function(){Ext.getCmp('windowId').close();reloadGrid()});
			Ext.Msg.alert(getLabelFromMap('400000', helpMap, 'Aviso'),'Los datos se guardaron con exito', function() {reloadGrid()});
			window.close();
		}
	}
	// ***********************************************************************************************************************
}