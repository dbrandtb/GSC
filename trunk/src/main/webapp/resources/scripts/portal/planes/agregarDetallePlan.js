// Funcion de Agregar un Detalle de Plan

function agregar() {
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

        //se define el formulario
        var formPanel = new Ext.FormPanel( {

            labelWidth : 100,

            url : _ACTION_GUARDAR_NUEVO_DETALLEPLAN,

            frame : true,
			bodyStyle:'background:white',
            renderTo: Ext.get('formulario'),
			labelAlign:'right',
            width : 350,

            waitMsgTarget : true,

            defaults : {

                width : 230

            },

            defaultType : 'textfield',

            //se definen los campos del formulario

            items : [ {
            	id:'winAddProductoId',
            	fieldLabel: getLabelFromMap('winAddProductoId',helpMap,'Producto'),
            	tooltip: getToolTipFromMap('winAddProductoId',helpMap,'Producto de planes'),
            	value: DESCRIPCION_PRODUCTO,
            	//anchor: '100%',
            	width:240,
            	disabled: true
            },{
            	id:'winAddPlanId',
            	fieldLabel: getLabelFromMap('winAddPlanId',helpMap,'Plan'),
            	tooltip: getToolTipFromMap('winAddPlanId',helpMap,'Nombre del plan'),
            	value: DESCRIPCION_PLAN,
            	//anchor: '100%',
            	width:240,
            	disabled: true
            },new Ext.form.Hidden({
                name : 'cdRamo',
                disabled : false,
                value: CODIGO_PRODUCTO
            }), new Ext.form.Hidden({
                name : 'cdPlan',
                value: CODIGO_PLAN,
                disabled: false
            }),new Ext.form.ComboBox({
            			  id:'wndCmbAddPlanSitId',
                          tpl: '<tpl for="."><div ext:qtip="{codSituacion}. {descripcionSituacion}" class="x-combo-list-item">{descripcionSituacion}</div></tpl>',
                          //anchor:'100%',
                          width:210,
                          name: 'codSituacion',
                          store: dsTiposSituacion,
                          displayField:'descripcionSituacion',
                          valueField:'codSituacion',
                          hiddenName: 'cdTipSit',
                          typeAhead: true,
                          allowBlank:false,
                          blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                          mode: 'local',
                          triggerAction: 'all',
                          fieldLabel: getLabelFromMap('wndCmbAddPlanSitId',helpMap,'Riesgo'),
                          tooltip: getToolTipFromMap('wndCmbAddPlanSitId',helpMap,'Elija tipo de situaci&oacute;n'),
                          emptyText:'Seleccione Tipo de Situacion...',
                          selectOnFocus:true,
                          forceSelection:true
                          }),
                       new Ext.form.ComboBox({
                       	  id:'wndCmbAddPlanGarId',
                          tpl: '<tpl for="."><div ext:qtip="{codGarantia}. {descripcionGarantia}" class="x-combo-list-item">{descripcionGarantia}</div></tpl>',
                          store: dsTiposCobertura,
                          //anchor:'100%',
                          width:210,
                          displayField:'descripcionGarantia',
                          valueField:'codGarantia',
                          hiddenName: 'cdGarant',
                          typeAhead: true,
                          mode: 'local',
                          triggerAction: 'all',
                          name: "codigoGarantia",
                          fieldLabel: getLabelFromMap('wndCmbAddPlanGarId',helpMap,'Tipo de Garant&iacute;a'),
                          tooltip: getToolTipFromMap('wndCmbAddPlanGarId',helpMap,'Elija tipo de garant&iacute;a'),
                          //width: 300,
                          allowBlank:false,
                            blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                          emptyText:'Seleccione Tipo de Garantia...',
                          selectOnFocus:true,
                          forceSelection:true
                          }),

                    new Ext.form.Checkbox({
                       	    id:'wndChkBxAddPlanObligId',
                            fieldLabel: getLabelFromMap('wndChkBxAddPlanObligId',helpMap,'Obligatorio?'),
                            tooltip: getToolTipFromMap('wndChkBxAddPlanObligId',helpMap,'Si el detalle de plan es obligatorio'),
                            style: 'marginTop: 5px',
                            //allowBlank:false,
                            //blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                            name:'swOblig'
                      })
                ]

        });


//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
        	id:'winAddDetPlanId',
        	title: getLabelFromMap('59',helpMap,'Agregar Detalle de Plan'),        	
        	width: 500,
        	height:250,
        	layout: 'fit',
        	plain:true,
        	buttonAlign:'center',
        	modal: true,
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
            	id:'winBtnSavePlanDetId',
                text : getLabelFromMap('winBtnSavePlanDetId',helpMap,'Guardar'),
                tooltip: getToolTipFromMap('winBtnSavePlanDetId',helpMap, 'Guarda detalle del Plan'),
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_NUEVO_DETALLEPLAN,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert('Error', action.result.errorMessages[0]);
                            },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027',helpMap,'Guardando datos')
                        });
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                }
            }, {
            	id:'winBtnCancelPlanDetId',
                text : getLabelFromMap('winBtnCancelPlanDetId',helpMap,'Cancelar'),
                tooltip: getToolTipFromMap('winBtnCancelPlanDetId',helpMap, 'Cancela el ingreso de datos'),
                handler : function() {
                    window.close();
                }
            }]
    	});

    	window.show();

		dsTiposCobertura.load({callback:function(){dsTiposSituacion.load();}});
};