// Funcion de Editar un Plan

function editar(record) {

        var dsTiposSituacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPOS_SITUACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'tiposSituacionComboBox',
            id: 'cdTipSit'
            },[
           {name: 'codSituacion', type: 'string',mapping:'cdTipSit'},
           {name: 'descripcionSituacion', type: 'string',mapping:'dsTipSit'}
        ])
        });


        var dsTiposCobertura = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TIPOS_COBERTURAS
            }),
            reader: new Ext.data.JsonReader({
            root: 'tiposCoberturaComboBox',
            id: 'cdGarant'
            },[
           {name: 'codGarantia', type: 'string',mapping:'cdGarant'},
           {name: 'descripcionGarantia', type: 'string',mapping:'dsGarant'}
        ])
        });


        //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {

            //indica el arreglo a leer
            root : 'recordList',

            //indica la cantidad de registro que debe leerse
            totalProperty: 'total',

            //indica el resultado de la respuesta de la action
            successProperty : 'success'

        }, [ {
            name : 'cdRamo',
            mapping : 'cdRamo',
            type : 'string'
        }, {
            name : 'cdPlan',
            type : 'string',
            mapping : 'cdPlan'
        }, {
            name : 'cdTipSit',
            type : 'string',
            mapping : 'cdTipSit'
        }, {
            name : 'cdGarant',
            type : 'string',
            mapping : 'cdGarant'
        }, {
            name : 'swOblig',
            type : 'string',
            mapping : 'swOblig'
        }]);
		//se define el formulario
        var formPanel = new Ext.FormPanel( {
            labelWidth : 100,
			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_DETALLEPLAN,
            baseParams : {
                cdPlan: record.get('cdPlan'),
                cdRamo: record.get('cdRamo'),
                cdTipSit: record.get('cdTipSit'),
                cdGarant: record.get('cdGarant')
                                
            },
            frame : true,
            width : 350,
			//se setea el reader que va a usar el form cuando se cargue
            reader : _jsonFormReader,
            defaults : {
                width : 230
            },
            labelAlign:'right',
            defaultType : 'textfield',
            bodyStyle:'background:white',
			//se definen los campos del formulario
            items : [ 
            {
            	id:'winEditProductoId',
            	fieldLabel: getLabelFromMap('winEditProductoId',helpMap,'Producto'),
            	tooltip: getToolTipFromMap('winEditProductoId',helpMap, 'Producto del Plan'),
            	name: 'desProducto',
            	disabled: true,
            	value: DESCRIPCION_PRODUCTO,
            	//anchor: '100%'
            	width:240
            },
            {
            	id:'winEditPlanId',
            	fieldLabel: getLabelFromMap('winEditPlanId',helpMap,'Plan'),
            	tooltip: getToolTipFromMap('winEditPlanId',helpMap, 'Plan'),
            	name: 'desPlan',
            	disabled: true,
            	value: DESCRIPCION_PLAN,
            	//anchor: '100%'
            	width:240
            },
             new Ext.form.Hidden ({
                name : 'cdRamo',
                disabled: true
            }), new Ext.form.Hidden ({
                name : 'cdPlan',
                disabled: true
            }),          new Ext.form.ComboBox({
            			  id:'wndCmbEditPlanSitId',
                          tpl: '<tpl for="."><div ext:qtip="{codSituacion}. {descripcionSituacion}" class="x-combo-list-item">{descripcionSituacion}</div></tpl>',
                          disabled: true,
                          //anchor:'100%',
                          width:210,
                          name: 'codSituacion',
                          store: dsTiposSituacion,
                          displayField:'descripcionSituacion',
                          valueField:'codSituacion',
                          hiddenName: 'cdTipSit',
                          typeAhead: true,
                          mode: 'local',
                          triggerAction: 'all',
                          fieldLabel: getLabelFromMap('wndCmbEditPlanSitId',helpMap,'Riesgo'),
                          tooltip: getToolTipFromMap('wndCmbEditPlanSitId',helpMap, 'Riesgo'),
                          emptyText:'Seleccione Tipo de Situacion...',
                          selectOnFocus:true,
                          forceSelection:true
                          }),
                       new Ext.form.ComboBox({
                       	  id:'wndCmbEditPlanGarId',
                          tpl: '<tpl for="."><div ext:qtip="{codGarantia}. {descripcionGarantia}" class="x-combo-list-item">{descripcionGarantia}</div></tpl>',
                          disabled: true,
                          store: dsTiposCobertura,
                          //anchor:'100%',
                          width:210,
                          editable: true,
                          displayField:'descripcionGarantia',
                          valueField:'codGarantia',
                          hiddenName: 'cdGarant',
                          typeAhead: true,
                          mode: 'local',
                          triggerAction: 'all',
                          name: "codigoGarantia",
                          fieldLabel: getLabelFromMap('wndCmbEditPlanGarId',helpMap,'Tipo de Garant&iacute;a'),
                          tooltip: getToolTipFromMap('wndCmbEditPlanGarId',helpMap, 'Tipo de Garant&iacute;a del Plan'),
                          width: 300,
                          emptyText:'Seleccione Tipo de Garantia...',
                          selectOnFocus:true,
                      //    forceSelection:true
                          }),

                    new Ext.form.Checkbox({
                       	    id:'wndChkBxEditPlanObligId',
                            fieldLabel: getLabelFromMap('wndChkBxEditPlanObligId',helpMap,'Obligatorio?'),
                            tooltip: getToolTipFromMap('wndChkBxEditPlanObligId',helpMap, 'Si el detalle de plan es obligatorio'),
							style: 'marginTop: 5px',
                            name:'swOblig'
                      })

                    ]

        });



//Windows donde se van a visualizar la pantalla
    
        var window = new Ext.Window({
        	title: getLabelFromMap('60',helpMap,'Editar Detalle de Plan'),
        	width: 500,
        	height:250,
        	layout: 'fit',
        	plain:true,
        	buttonAlign:'center',
        	modal: true,
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {

                text : getLabelFromMap('winBtnESavePlanDetId',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('winBtnESavePlanDetId',helpMap, 'Guarda actualización'),

                disabled : false,

                handler : function() {

                    if (formPanel.form.isValid()) {


                        formPanel.form.submit( {

                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_DETALLEPLAN,

                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();                                     
                            },

                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),'Problemas al Guardar' + action.result.errorMessages[0]);
                            },

                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualiacion de datos...')

                        });

                    } else {

                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informacion requerida'));

                    }

                }

            }, {

                text : getLabelFromMap('winBtnECancelPlanDetId',helpMap,'Cancelar'),
                tooltip: getToolTipFromMap('winBtnECancelPlanDetId',helpMap, 'Cancela la Edición'),

                handler : function() {
                    window.close();
                }

            }]

    	});



    	window.show();
        formPanel.form.load( {
            params:{
              	codigoSituacion: record.get('cdTipSit'),
                cdRamo: record.get('cdRamo'),
                cdTipSit: record.get('cdTipSit'),
                cdGarant: record.get('cdGarant'),
                codGarantia: record.get('cdGarant'),
                swOblig: record.get('swOblig')
            },
    				waitTitle : getLabelFromMap('400021',helpMap,'Espere...'),
                    waitMsg : getLabelFromMap('400028', helpMap,'Leyendo datos...')
        });

		dsTiposCobertura.load({callback:function(){dsTiposSituacion.load();}});
    };

