function agregar(codigoEstructura) {

    var dsClientes = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_CLIENTES
        }),
        reader: new Ext.data.JsonReader({
        root: 'clientesComboBox',
        id: 'codigoPersona'
        },[
       {name: 'codigoPersona', type: 'string',mapping:'codigoPersona'},
       {name: 'nombre', type: 'string',mapping:'nombre'}
    ])
    });

    var dsTiposNivel = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_TIPOS_NIVEL
        }),
        reader: new Ext.data.JsonReader({
        root: 'tiposNivelComboBox',
        id: 'id'
        },[
       {name: 'id', type: 'string',mapping:'codigo'},
       {name: 'texto', type: 'string',mapping:'descripcion'}
    ])
    });

    var dsVinculosPadre = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_VINCULOS_PADRE
        }),
        reader: new Ext.data.JsonReader({
        root: 'padresComboBox',
        id: 'cdElemento'
        },[
       {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
       {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
    ])
    });


         var codigoEstructura=codigoEstructura;
                    
        var formPanel = new Ext.FormPanel( {

            labelWidth : 100,

            url : _ACTION_GUARDAR_NUEVO_ELEMENTO_ESTRUCTURA,

            frame : true,

            renderTo: Ext.get('formulario'),

            bodyStyle : 'padding:5px 5px 0',

            width : 350,

            waitMsgTarget : true,

            defaults : {width : 200},

            defaultType : 'textfield',
            
            bodyStyle : ('padding:0 5px 5px 0','background: white'),
            
            labelAlign: 'right',

            //se definen los campos del formulario

            items : [
               new Ext.form.TextField( {
				id:'elemEstAgregNombreId',
                fieldLabel : getLabelFromMap('elemEstAgregNombreId', helpMap,'Nombre') ,
				tooltip: getToolTipFromMap('elemEstAgregNombreId', helpMap,'Nombre del Elemento') ,
				hasHelpIcon:getHelpIconFromMap('elemEstAgregNombreId',helpMap),
				Ayuda:getHelpTextFromMap('elemEstAgregNombreId',helpMap),
                name : 'nombre',
                maxLength:40,
				blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                allowBlank : false,                
                width: 200
            }),
            new Ext.form.ComboBox({
            				id:'cmbElemEstNombreId',
                            tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                            store: dsVinculosPadre,
                            forceSelection:true,
                            displayField:'dsElemen',
                            valueField:'cdElemento',
                            hiddenName: 'vinculoPadre',
                            typeAhead: true,
                            mode: 'local',
                            triggerAction: 'all',
                            fieldLabel: getLabelFromMap('cmbElemEstNombreId', helpMap,'V&iacute;nculo Padre'),
                            tooltip: getToolTipFromMap('cmbElemEstNombreId', helpMap,'V&iacute;nculo Padre del Elemento'),
							hasHelpIcon:getHelpIconFromMap('cmbElemEstNombreId',helpMap),
							Ayuda:getHelpTextFromMap('cmbElemEstNombreId',helpMap),
                            width: 300,
                            emptyText:'Seleccione Vinculo Padre...',
                            selectOnFocus:true
                            }),
          new Ext.form.ComboBox({
          					id:'cmbElemEstTipoNivelId',
                            tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                            store: dsTiposNivel,
                            forceSelection:true,
                            displayField:'texto',
                            valueField:'id',
                            hiddenName: 'tipoNivel',
                            typeAhead: true,
                            mode: 'local',
                            triggerAction: 'all',
                            fieldLabel: getLabelFromMap('cmbElemEstTipoNivelId', helpMap,'Tipo Nivel'),
                            tooltip: getToolTipFromMap('cmbElemEstTipoNivelId', helpMap,'Tipo Nivel del Elemento'),
							hasHelpIcon:getHelpIconFromMap('cmbElemEstTipoNivelId',helpMap),
							Ayuda:getHelpTextFromMap('cmbElemEstTipoNivelId',helpMap),
                            width: 300,
                            emptyText:'Seleccione Tipo de Nivel...',
                            selectOnFocus:true
                            }),
              new Ext.form.NumberField( {
          		id:'txtElemEstPosicioId',
                fieldLabel: getLabelFromMap('txtElemEstPosicioId', helpMap,'Orden en los reportes'),//Posici&oacute;n'),
                tooltip: getToolTipFromMap('txtElemEstPosicioId', helpMap,'Indica el ordenamiento que usan los niveles en los reportes'),// Posici&oacute;n del Elemento'),
				hasHelpIcon:getHelpIconFromMap('txtElemEstPosicioId',helpMap),
				Ayuda:getHelpTextFromMap('txtElemEstPosicioId',helpMap),

                name : 'posicion',

                allowBlank : true,

                width: 200

            }),
              new Ext.form.Checkbox({
        		id: 'elemEstCheckBoxId',
        		fieldLabel: getLabelFromMap('elemEstCheckBoxId', helpMap,'¿Usa Descuento por n&oacute;mina?'),// N&oacute;mina'),
        		tooltip: getToolTipFromMap('elemEstCheckBoxId', helpMap,'Indica si el nivel utiliza el descuento por n&oacute;mina como instrumento de pago'),//N&oacute;mina del Elemento'),
				hasHelpIcon:getHelpIconFromMap('elemEstCheckBoxId',helpMap),
				Ayuda:getHelpTextFromMap('elemEstCheckBoxId',helpMap),
        		name:'nomina'

            }),

            new Ext.form.ComboBox({
            				id:'cmbElemEstClienteId',
                            tpl: '<tpl for="."><div ext:qtip="{codigoPersona}. {nombre}" class="x-combo-list-item">{nombre}</div></tpl>',
                            store: dsClientes,
                            forceSelection:true,
                            displayField:'nombre',
                            valueField:'codigoPersona',
                            hiddenName: 'codigoElemento',
                            typeAhead: true,
                            mode: 'local',
                            triggerAction: 'all',
                            fieldLabel: getLabelFromMap('cmbElemEstClienteId', helpMap,'Cliente'),
                            tooltip: getToolTipFromMap('cmbElemEstClienteId', helpMap,'Cliente'),
							hasHelpIcon:getHelpIconFromMap('cmbElemEstClienteId',helpMap),
							Ayuda:getHelpTextFromMap('cmbElemEstClienteId',helpMap),
                            width: 300,
                            emptyText:'Seleccione Cliente...',
                            selectOnFocus:true
                            }),


            new Ext.form.Hidden( {
				
                disabled:false,
                
                name:'codigoEstructura',
                
        		value: codigoEstructura

            })]
          });

          
          
          
//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
        	id:'wndwElmtEstrAgrgrId',
        	title: getLabelFromMap('wndwElmtEstrAgrgrId', helpMap,'Agregar elemento de Estructura'),
        	width: 500,
        	height: 296,
        	layout: 'fit',
        	modal:true,
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
				id:'wElemEstruBtnGuardarId',
                text : getLabelFromMap('250', helpMap, 'Guardar'),
				tooltip: getToolTipFromMap('250', helpMap,'Guarda un elemento en la estructura.'),
                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {
						guardarDatos(false);
                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }

                }

            },
            {
				id:'wElemEstruBtnGuardarId',
                text : getLabelFromMap('251', helpMap, 'Guardar y Agregar'),
				tooltip: getToolTipFromMap('251', helpMap,'Guarda el elemento actual y permite agregar un elemento nuevo en la estructura'),
                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {
						guardarDatos(true);
						
                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));

                    }

                }

            }, {
				id:'wElemEstruBtnCancelarId',
                text : getLabelFromMap('wElemEstruBtnCancelarId', helpMap, 'Cancelar'),
				tooltip: getToolTipFromMap('wElemEstruBtnCancelarId', helpMap,'Cancela el ingreso'),

                handler : function() {
                    window.close();
                }

            }]

    	});

    	window.show();

        dsClientes.load({
        	callback: function(){
        		dsTiposNivel.load({
        		  callback: function(){
        		  	dsVinculosPadre.load({
        				params: {codigoEstructura: codigoEstructura}
        			});
        		   }
        		});
        	}
        });
        
        

		function guardarDatos(agregarOtro) {
                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_NUEVO_ELEMENTO_ESTRUCTURA,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap, 'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                if (!agregarOtro) {
                                	window.close();
                                } else {
                                	formPanel.getForm().reset();
							        dsVinculosPadre.load({
							        		params: {codigoEstructura: codigoEstructura}
							        });
                                }
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap, 'Error'), action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')

                        });
		}
}