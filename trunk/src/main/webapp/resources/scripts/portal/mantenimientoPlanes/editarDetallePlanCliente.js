// Funcion de Editar Eemento Estructura
function editar(record) {

       /* var dsTiposSituacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPOS_SITUACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboTipoSituacionProducto',
            id: 'codigo'
            },[
           {name: 'codSituacion', type: 'string',mapping:'codigo'},
           {name: 'descripcionSituacion', type: 'string',mapping:'descripcion'}
        ])
        });
        
        // Se cambió los combos por pedido de oslen 12 - Sept - 2008 15:17 hs
       		var storeSituacionPlan = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_SITUACION_X_PLAN}),
            reader: new Ext.data.JsonReader({
            		root: 'comboSituacionPlan',
            		id: 'codigo'
            	},
            	[
           			{name: 'codigo', type: 'string',mapping:'codigo'},
           			{name: 'descripcion', type: 'string',mapping:'descripcion'}
        		])
        });


        var dsTiposCobertura = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPOS_COBERTURAS
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboTiposGarantia',
            id: 'codigo'
            },[
           {name: 'codGarantia', type: 'string',mapping:'codigo'},
           {name: 'descripcionGarantia', type: 'string',mapping:'descripcion'}
        ])
        });
        
        // Se cambió los combos por pedido de oslen 12 - Sept - 2008 15:17 hs
        var storeCoberturasPlan = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COBERTURAS_X_PLAN
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboCoberturaPlan',
            id: 'codigo'
            },[
           {name: 'codGarantia', type: 'string',mapping:'codigo'},
           {name: 'descripcionGarantia', type: 'string',mapping:'descripcion'}
        ])
        });

        var dsAseguradoras = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_ASEGURADORAS
            }),
            reader: new Ext.data.JsonReader({
            root: 'comboAseguradoraPorProducto',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ])
        });*/

        var _jsonFormReader = new Ext.data.JsonReader( {
            root : 'registro',
            successProperty : '@success'
        }, [ {
            name : 'dsElemen',
            mapping : 'dsElemen',
            type : 'string'
            },{
            name : 'codigoAseguradora',
            mapping : 'cdUniEco',
            type : 'string'
            },
            {
            name : 'desAseguradora',
            mapping : 'dsUniEco',
            type : 'string'
            },
            {
            name : 'dsPlan',
            mapping : 'dsPlan',
            type : 'string'
            },
            {
            name : 'codigoSituacion',
            mapping : 'cdTipSit',
            type : 'string'
            },
            {
            name : 'desSituacion',
            mapping : 'dsTipSit',
            type : 'string'
            },
            {
            name : 'codigoGarantia',
            mapping : 'cdGarant',
            type : 'string'
            },
            {
            name : 'desGarantia',
            mapping : 'dsGarant',
            type : 'string'
            },
            {
            name : 'codigoObligatorio',
            mapping : 'swOblig',
            type : 'string'
            }
        ]);
        
        
        var formPanel2 = new Ext.FormPanel( {
            labelWidth : 100,
            url : _ACTION_GET_PLANCLIENTE,			
            baseParams : {
                codigoProducto: record.get("cdRamo"),
                codigoPlan: record.get("cdPlan"),
                codigoElemento: record.get('cdElemento'),
                codigoCliente: record.get('cdPerson')
            },
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            reader : _jsonFormReader,
            labelAlign:'right',
             bodyStyle:'background: white',
           // reader : test(),
            defaults : {
                width : 230
            },
            defaultType : 'textfield',
          items : [
               new Ext.form.TextField( {
               	id:'txtEditDetPlanProductoId',
                fieldLabel : getLabelFromMap('txtEditDetPlanProductoId',helpMap,'Producto'),
                tooltip : getToolTipFromMap('txtEditDetPlanProductoId',helpMap, 'Producto'),
		        hasHelpIcon:getHelpIconFromMap('txtEditDetPlanProductoId',helpMap),								 
                Ayuda: getHelpTextFromMap('txtEditDetPlanProductoId',helpMap),
                name : 'dsRamo',
                allowBlank : false,
                value: record.get("dsRamo"),
                disabled : true,
                width: 200
            }),
            //new Ext.form.Hidden({name:'codigoProducto'}),
            new Ext.form.TextField( {
               	id:'txtEditDetPlanPlanId',
                fieldLabel : getLabelFromMap('txtEditDetPlanPlanId',helpMap,'Plan') ,
                tooltip : getToolTipFromMap('txtEditDetPlanPlanId',helpMap, 'Plan del Cliente') ,
		        hasHelpIcon:getHelpIconFromMap('txtEditDetPlanPlanId',helpMap),								 
                Ayuda: getHelpTextFromMap('txtEditDetPlanPlanId',helpMap),
                name : 'dsPlan',
                allowBlank : false,
                value: record.get("dsPlan"),
                disabled : true,
                width: 200
            }),
			//new Ext.form.Hidden({name:'codigoPlan'}),
            new Ext.form.TextField( {
            	d:'txtEditDetPlanClienteId',
                fieldLabel: getLabelFromMap('txtEditDetPlanClienteId',helpMap,'Cliente'),
                tooltip: getToolTipFromMap('txtEditDetPlanClienteId',helpMap, 'Cliente'),
		        hasHelpIcon:getHelpIconFromMap('txtEditDetPlanClienteId',helpMap),								 
                Ayuda: getHelpTextFromMap('txtEditDetPlanClienteId',helpMap),
               
                //fieldLabel : 'Cliente' ,
                name : 'dsElemen',
                allowBlank : false,
                disabled : true,
                width: 200
            }),
           new Ext.form.Hidden({name:'codigoAseguradora'}),
           new Ext.form.TextField( {
            	d:'txtEditAseDetPlanClienteId',
                fieldLabel: getLabelFromMap('txtEditAseDetPlanClienteId',helpMap,'Aseguradora'),
                tooltip: getToolTipFromMap('txtEditAseDetPlanClienteId',helpMap, 'Aseguradora'),	
		        hasHelpIcon:getHelpIconFromMap('txtEditAseDetPlanClienteId',helpMap),								 
                Ayuda: getHelpTextFromMap('txtEditAseDetPlanClienteId',helpMap),
                name : 'desAseguradora',
                allowBlank : false,
                disabled : true,
                width: 200
            }),
            new Ext.form.Hidden({name:'codigoSituacion'}),            
            new Ext.form.TextField( {
            	d:'txtEditRiesDetPlanClienteId',
                fieldLabel: getLabelFromMap('txtEditRiesDetPlanClienteId',helpMap,'Riesgo'),
                tooltip: getToolTipFromMap('txtEditRiesDetPlanClienteId',helpMap, 'Riesgo'),
		        hasHelpIcon:getHelpIconFromMap('txtEditRiesDetPlanClienteId',helpMap),								 
                Ayuda: getHelpTextFromMap('txtEditRiesDetPlanClienteId',helpMap),
                name : 'desSituacion',
                allowBlank : false,
                disabled : true,
                width: 200
            }),
            new Ext.form.Hidden({name:'codigoGarantia'}),                        
            new Ext.form.TextField( {
            	d:'txtEditCobDetPlanClienteId',
                fieldLabel: getLabelFromMap('txtEditCobDetPlanClienteId',helpMap,'Tipo de Cobertura'),
                tooltip: getToolTipFromMap('txtEditCobDetPlanClienteId',helpMap, 'Tipo de Cobertura'),	
		        hasHelpIcon:getHelpIconFromMap('txtEditCobDetPlanClienteId',helpMap),								 
                Ayuda: getHelpTextFromMap('txtEditRiesDetPlanClienteId',helpMap),
                name : 'desGarantia',
                allowBlank : false,
                disabled : true,
                width: 200
            }),
            new Ext.form.Checkbox( {
             	id:'cbEditPlanObligId',
                fieldLabel: getLabelFromMap('cbEditPlanObligId',helpMap,'Oblig?.'),
                tooltip: getToolTipFromMap('cbEditPlanObligId',helpMap, 'Tilde si es obligatorio'),	
		        hasHelpIcon:getHelpIconFromMap('cbEditPlanObligId',helpMap),								 
                Ayuda: getHelpTextFromMap('cbEditPlanObligId',helpMap),
                name : 'codigoObligatorio',
                allowBlank : false,
                style: 'marginTop: 5px',
                width: 50
            })
                ]
        });


//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
        	id:'wEditDetPlanCid',
            title: getLabelFromMap('wEditDetPlanCid', helpMap,'Editar producto por Cliente'),        	
        	width: 500,
        	height:275,
        	layout: 'fit',
        	bodyStyle:'background: white',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel2,
            //se definen los botones del formulario
            buttons : [ {
                        text : getLabelFromMap('wAddEditDetPlanId',helpMap,'Guardar'),
				        tooltip : getToolTipFromMap('wAddEditDetPlanId',helpMap, 'Guarda Actualizaci&oacute;n'),
                        disabled : false,
                        handler : function() {

                       if (formPanel2.form.isValid()) {
                        formPanel2.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_PLANCLIENTE,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.Msg.alert('Aviso', action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert('Error', action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualiaci&oacute;n de datos...')

                        });

                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                }

            }, {
                text : getLabelFromMap('wEditCancelDetPlanId',helpMap,'Cancelar'),
				tooltip : getToolTipFromMap('wEditCancelDetPlanId',helpMap, 'Cancela la edici&oacute;n'),
                handler : function() {
                    window.close();
                }

            }]

    	});



    	window.show();


      formPanel2.form.load( {
          params:{
              codigoProducto: record.get('cdRamo'),
              codigoPlan: record.get('cdPlan'),
              codigoCliente: record.get('cdElemento'),
              codigoSituacion: record.get('cdTipSit'),
              codigoGarantia: record.get('cdGarant'),
              codigoObligatorio: record.get('swOblig'),
              codigoAseguradora: record.get('cdUniEco')
          },
		  success: function(form, action) {
		  		var _aseguradora = action.reader.jsonData.registro[0].cdUniEco;
		  		var _tiposituac = action.reader.jsonData.registro[0].cdTipSit;
		  		var _tipocobert = action.reader.jsonData.registro[0].cdGarant;
		  		var _elemento = action.reader.jsonData.registro[0].cdElemento;
		  		var _ramo = action.reader.jsonData.registro[0].cdRamo;
		  		var _plan = action.reader.jsonData.registro[0].cdPlan;
		  	},
          //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
          failure : function(form, action) {
             Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400033', helpMap,'No se encontraron registros'));              
             window.close();
          },

    				waitTitle : getLabelFromMap('400021',helpMap,'Espere...'),
                    waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos...')
        });
    };

