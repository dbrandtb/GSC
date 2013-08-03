// Funcion de Editar Eemento Estructura
function editar(record) {

    var dsVinculosPadre = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_VINCULOS_PADRE_EDITAR
        }),
        reader: new Ext.data.JsonReader({
        root: 'comboVinculoPadreEditar',
        id: 'cdElemento'
        },[
       {name: 'cdElemento', type: 'string',mapping:'codigo'},
       {name: 'dsElemen', type: 'string',mapping:'descripcion'}
    ])
    });


    
    var dsTiposNivel = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_TIPOS_NIVEL
        }),
        reader: new Ext.data.JsonReader({
        root: 'tiposNivelComboBox',
        id: 'tiposNivelComboBox'
        },[
       {name: 'idTipoNivel', type: 'string',mapping:'codigo'},
       {name: 'textoTipoNivel', type: 'string',mapping:'descripcion'}
    ])
    });


        //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {

            //indica el arreglo a leer
            root : 'MEstructuraList',

            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',

            //indica el resultado de la respuesta de la action
            successProperty : 'success'

        }, [ {
            name : 'nombre',
           	mapping : 'nombre',           
 			type : 'string'
        }, {
            name : 'vinculoPadre',
            mapping : 'vinculoPadre',
            type : 'string'
        }, {
            name : 'dsPadre',
            mapping : 'dsPadre',
            type : 'string'
        }, {
            name : 'tipoNivel',
            mapping : 'codigoTipoNivel',
 			type : 'string'
        }, {
            name : 'posicion',
            mapping : 'posicion',
 			type : 'string'
        }, {
            name : 'nomina',
            mapping : 'nomina',
 			type : 'string'
        }, {
            name : 'codigoCliente',
            mapping : 'codigoPersona',
			type : 'string'
        }, {
        	name: 'codigoPersona',
        	mapping: 'dsNombre',
        	type: 'string'
        }
        ]);

		//se define el formulario
        var formPanel = new Ext.FormPanel ( {

            labelWidth : 100,

			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_ELEMENTO_ESTRUCTURA,

 			baseParams : {
                 codigoElemento: record.get('codigoElemento'),
                 codigoEstructura: record.get('codigoEstructura')
             },

            bodyStyle : ('padding:0 5px 5px 0','background: white'),
            
            labelAlign: 'right',

            width : 350,
            frame:true,
            waitMsgTarget : true,
            reader : _jsonFormReader,

            defaults : {width : 200 },

            defaultType : 'textfield',

			//se definen los campos del formulario
            items : [
            new Ext.form.TextField({
				id:'elemEstEdidNombreId',
				//disabled:true,
				//readOnly:true,
                fieldLabel : getLabelFromMap('elemEstEdidNombreId', helpMap,'Nombre') ,
				tooltip: getToolTipFromMap('elemEstEdidNombreId', helpMap,'Nombre del Elemento') ,
		        hasHelpIcon:getHelpIconFromMap('elemEstEdidNombreId',helpMap),
				Ayuda:getHelpTextFromMap('elemEstEdidNombreId',helpMap),
                name : 'nombre',
				maxlength: 40,
				blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                allowBlank : false

            }),

			new Ext.form.ComboBox({
				id:'cmbElemEstVinPadId',
				tpl: '<tpl for="."><div ext:qtip="{cdElemento}. {dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
				store: dsVinculosPadre,
				forceSelection:true,
				displayField:'dsElemen',
				valueField:'cdElemento',
				hiddenName: 'vinculoPadre',
				typeAhead: true,
				mode: 'local',
				triggerAction: 'all',
				fieldLabel: getLabelFromMap('cmbElemEstVinPadId', helpMap,'V&iacute;nculo-Padre'),
				tooltip: getToolTipFromMap('cmbElemEstVinPadId', helpMap, 'V&iacute;nculo-Padre del Elemento'),
				hasHelpIcon:getHelpIconFromMap('cmbElemEstVinPadId',helpMap),
				Ayuda:getHelpTextFromMap('cmbElemEstVinPadId',helpMap),
				width: 300,
				emptyText:getLabelFromMap('400002',helpMap,'Seleccione Vinculo Padre...'),
				selectOnFocus:true
			}),
            
            /*
            new Ext.form.TextField( {
            	id:'txtElemEstVinPadId',
            	disabled:false,
                fieldLabel: getLabelFromMap('txtElemEstVinPadId', helpMap,'V&iacute;nculo Padre'),
                tooltip: getToolTipFromMap('txtElemEstVinPadId', helpMap,'V&iacute;nculo Padre del Elemento'),
		        hasHelpIcon:getHelpIconFromMap('txtElemEstVinPadId',helpMap),
				helpText:getHelpTextFromMap('txtElemEstVinPadId',helpMap),
                name : 'dsPadre',
                
                allowBlank : true,
               	        		
                width: 200
              }),
              new Ext.form.Hidden( {
            	id:'vincPadreId',
                name : 'vinculoPadre'                
              }),*/
              
                                
                new Ext.form.ComboBox({
                				id:'cmbEditElemEstTipoNivelId',
                                tpl: '<tpl for="."><div ext:qtip="{idTipoNivel}. {textoTipoNivel}" class="x-combo-list-item">{textoTipoNivel}</div></tpl>',
                                store: dsTiposNivel,
                                forceSelection:true,
                                displayField:'textoTipoNivel',
                                valueField:'idTipoNivel',
                                hiddenName: 'tipoNivel',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                            	fieldLabel: getLabelFromMap('cmbEditElemEstTipoNivelId', helpMap,'Tipo Nivel'),
                            	tooltip: getToolTipFromMap('cmbEditElemEstTipoNivelId', helpMap, 'Tipo de Nivel del Elemento'),
						        hasHelpIcon:getHelpIconFromMap('cmbEditElemEstTipoNivelId',helpMap),
								Ayuda:getHelpTextFromMap('cmbEditElemEstTipoNivelId',helpMap),
                                width: 300,
                                emptyText:getLabelFromMap('400002',helpMap,'Seleccione Tipo de Nivel...'),
                                selectOnFocus:true
                                }),
                                
                new Ext.form.NumberField( {
          		id:'txtEditElemEstPosicioId',
                fieldLabel: getLabelFromMap('txtEditElemEstPosicioId', helpMap, 'Orden en los reportes'),
                tooltip: getToolTipFromMap('txtEditElemEstPosicioId', helpMap, 'Indica el ordenamiento que usan los niveles en los reportes'),
		        hasHelpIcon:getHelpIconFromMap('txtEditElemEstPosicioId',helpMap),
				Ayuda:getHelpTextFromMap('txtEditElemEstPosicioId',helpMap),
                name : 'posicion',
                allowBlank : true,
                width: 200

            }), 
            new Ext.form.Checkbox({
        		id: 'elemEditEstCheckBoxId',
        		fieldLabel: getLabelFromMap('elemEditEstCheckBoxId', helpMap,'¿Usa Descuento por n&oacute;mina?'),
        		tooltip: getToolTipFromMap('elemEditEstCheckBoxId', helpMap,'Indica si el nivel utiliza el descuento por n&oacute;mina como instrumento de pago'),
		        hasHelpIcon:getHelpIconFromMap('elemEditEstCheckBoxId',helpMap),
				Ayuda:getHelpTextFromMap('elemEditEstCheckBoxId',helpMap),        			
        		name:'nomina'

              }),


              new Ext.form.TextField( {
            	id:'txtElemEstClienteId',
            	disabled:true,
            	readOnly:true,
                fieldLabel: getLabelFromMap('txtElemEstClienteId', helpMap,'Cliente'),
                tooltip: getToolTipFromMap('txtElemEstClienteId', helpMap,'Cliente'),
		        hasHelpIcon:getHelpIconFromMap('txtElemEstClienteId',helpMap),
				Ayuda:getHelpTextFromMap('txtElemEstClienteId',helpMap),
                name : 'codigoPersona',
                width: 200

            })
                    
            ]

        });

//Windows donde se van a visualizar la pantalla
    
        var window = new Ext.Window({
        	id:'wndwElmtEstrEditId',
        	title: getLabelFromMap('wndwElmtEstrEditId', helpMap,'Editar Elementos de Estructura'),
        	width: 500,
        	height:290,
        	modal:true,
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
				id:'wEditElemEstruBtnGuardarId',
                text : getLabelFromMap('wEditElemEstruBtnGuardarId', helpMap, 'Guardar'),
				tooltip: getToolTipFromMap('wEditElemEstruBtnGuardarId', helpMap, 'Guarda la Actualizaci&oacute;n'),

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {

                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_ELEMENTO_ESTRUCTURA,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                //reloadGrid();
                                window.close();                                     
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                           },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizaci&oacute;n de datos...')

                        });

                    } else {
                            Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                           }

                }

            }, {
				id:'wEditElemEstruBtnCancelarId',
                text : getLabelFromMap('wEditElemEstruBtnCancelarId', helpMap, 'Cancelar'),
				tooltip: getToolTipFromMap('wEditElemEstruBtnCancelarId', helpMap, 'Cancela la Edici&oacute;n'),

                handler : function() {
                    window.close();
                }

            }]

    	});

    	window.show();
        //se carga el formulario con los datos de la estructura a editar

        formPanel.form.load( {
    			//	waitTitle : getLabelFromMap('400021', helpMap,'Espere...'),
                //    waitMsg : getLabelFromMap('400028', helpMap,'Leyendo datos...'),
                    success: function(form, action){
    					//console.log(action.result);
        				var _dsVinculosPadre = action.result.data.vinculoPadre;
        				var _dsTipoNivel = action.result.data.tipoNivel;
            			dsVinculosPadre.load({
            				params: {
        						cdEstruc: record.get('codigoEstructura'),
        						cdElemento: record.get('codigoElemento')
        						},
	            			callback: function (r, o, success) {
	                    		if (success) {
	                        		Ext.getCmp('cmbElemEstVinPadId').setValue(_dsVinculosPadre);
	                        		dsTiposNivel.load({
	            							callback: function (r, o, success) {
	                    						if (success) {
	                        							Ext.getCmp('cmbEditElemEstTipoNivelId').setValue(_dsTipoNivel);
	                        							//alert(_dsTipoNivel);
	                        					}
	                						}
            						});
	                        	}
	                		}
            			});
       				}   
        });
/*
        dsTiposNivel.load({
        	success: function(){
        		var _code = dsTiposNivel.reader.jsonData.tiposNivelComboBox[0];
        		Ext.getCmp('cmbEditElemEstTipoNivelId').setValue(_code);
        	},
            callback: function() {
				//alert(1);
                formPanel.form.load( {
                            waitTitle : getLabelFromMap('400021', helpMap,'Espere...'),
                            waitMsg : getLabelFromMap('400028', helpMap,'Leyendo datos...')
                });
            }
        });
         	 dsVinculosPadre.load({

        			   	success: function(){
        					//var _code = dsVinculosPadre.reader.jsonData.tiposNivelComboBox[0];
        					//Ext.getCmp('cmbEditElemEstTipoNivelId').setValue(_code);
        				}
       			});*/
    };
