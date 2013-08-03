// Funcion de Editar una Tarea del Checklist

function editar (record) {

		//Este combo debe recibir como parametro la seccion pero para
		// mostrarla sin posibilidad de edición ni que se pueda desplegar.
		var dsSecciones = new Ext.data.Store({
			     autoLoad: true,
			     proxy: new Ext.data.HttpProxy({
			     url: _ACTION_OBTENER_SECCIONES,
			     method: 'POST'
		       	}),
		  		 reader: new Ext.data.JsonReader({root: 'seccionesComboBox',id: 'cdSeccion'},
		  		 [{name: 'cdSeccion', type: 'string',mapping:'cdSeccion'},
		  		  {name: 'dsSeccion', type: 'string',mapping:'dsSeccion'}
		  		 ])
		  		 });
		  		 
		  var dsEstados = new Ext.data.Store({
			     autoLoad: true,
			     proxy: new Ext.data.HttpProxy({
			     url: _ACTION_OBTENER_ESTADOS,
			     method: 'POST'
		       	}),
		  		reader: new Ext.data.JsonReader({root: 'estadosComboBox',id: 'cdEstado'},
		  		 [{name: 'cdEstado', type: 'string',mapping:'cdEstado'},
		  		  {name: 'dsEstado', type: 'string',mapping:'dsEstado'}
		  		 ])
		  		 });

        //se define la forma en que se van a leer los datos que envie la action
        var _jsonFormReader = new Ext.data.JsonReader( {
            root : 'obtieneTareaVO',
            totalProperty: 'total',
            successProperty : 'success'
        },
         [ 
            {name : 'codigoTarea',type : 'string',mapping : 'cdTarea'},
            {name : 'codigoSeccion',type : 'string',mapping : 'cdSeccion'},
            {name : 'codigoSeccionH',type : 'string',mapping : 'cdSeccion'},
            {name : 'codigoTareaPadre',type : 'string',mapping : 'cdTareapadre'},
            {name : 'codigoEstado',type : 'string',mapping : 'cdEstado'},
            {name : 'url',type : 'string',mapping : 'dsUrl'},
            {name : 'copio',type : 'string',mapping : 'cdCopia'},
            {name : 'ayuda',type : 'string',mapping : 'dsAyuda'}
          ]
        );
		//se define el formulario
        var formPanel = new Ext.FormPanel( {
            labelWidth : 100,
			//action a invocar al hacer al cargar(load) del formulario
            url : _ACTION_GET_TAREA_CHECKLIST,
            
            bodyStyle:'background: white',
			labelAlign:'right',
            bodyStyle : 'padding:5px 5px 0',
            width : 450,
			//se setea el reader que va a usar el form cuando se cargue
            reader : _jsonFormReader,
            defaultType : 'textfield',
			//se definen los campos del formulario
            items : [ 
            		new Ext.form.Hidden ({name: 'codigoSeccion'}),
                    new Ext.form.ComboBox({
                                    tpl: '<tpl for="."><div ext:qtip="{cdSeccion}. {dsSeccion}" class="x-combo-list-item">{dsSeccion}</div></tpl>',
                                    id:'windowEditarComboDsSeccionId',	                                
	                                fieldLabel: getLabelFromMap('windowEditarComboDsSeccionId',helpMap,'Secciones'),
   		                            tooltip: getToolTipFromMap('windowEditarComboDsSeccionId',helpMap,'Elija  Secci&oacute;n'), 
   		       						hasHelpIcon:getHelpIconFromMap('windowEditarComboDsSeccionId',helpMap),
									Ayuda:getHelpTextFromMap('windowEditarComboDsSeccionId',helpMap),
                                    store: dsSecciones,
                                    displayField:'dsSeccion',
                                    valueField:'cdSeccion',
                                    hiddenName: 'codigoSeccionH',
                                    typeAhead: true,
                                    mode: 'local',
                                    triggerAction: 'all',
                                    //fieldLabel: "Secciones",
                                    width: 250,
                                    emptyText:'Seleccione Seccion...',
                                    selectOnFocus:true,
                                    forceSelection:true,
                                    allowBlank:false,
                   		 			blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
                                    readOnly: true,
                                    disabled: true
                                    }),
                    new Ext.form.TextField(
                    {
                    id: 'descrTarea', 
				    fieldLabel: getLabelFromMap('descrTarea',helpMap,'Tarea'),
                    tooltip: getToolTipFromMap('descrTarea',helpMap, 'Tarea del Checklist'), 
                    hasHelpIcon:getHelpIconFromMap('descrTarea',helpMap),
					Ayuda:getHelpTextFromMap('descrTarea',helpMap),
                    //fieldLabel: 'Tarea', 
                    //name: 'descrTarea',
                    name: 'descrTarea',
                    value:record.get('dsTarea'),
                    width: 250,
                    readOnly: false,
                    allowBlank:false,
                    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido')
                    }
                    ),
			    	new Ext.form.Hidden(
			    	{
			    	fieldLabel : 'Tarea',
			    	name : 'codigoTarea',
			    	allowBlank : false
			    	}
			    	),
			    	new Ext.form.Hidden(
			    	{
			    	name : 'codigoTareaPadre'
			    	}
			    	),
				    new Ext.form.TextField(
				    {
                    id: 'windowEditarCodigoTareaPadreId', 
				    fieldLabel: getLabelFromMap('windowEditarCodigoTareaPadreId',helpMap,'Tarea Padre'),
                    tooltip: getToolTipFromMap('windowEditarCodigoTareaPadreId',helpMap,'Tarea Padre del Checklist'),                    				    
					hasHelpIcon:getHelpIconFromMap('windowEditarCodigoTareaPadreId',helpMap),
					Ayuda:getHelpTextFromMap('windowEditarCodigoTareaPadreId',helpMap),
				    //fieldLabel : 'Tarea Padre',
				    name : 'codigoTareaPadre',
				    //value: record.get('dsTareaPadre'),
				    //allowBlank : true, 
				    width: 250,
				    //readOnly: true
				    disabled:true
				    }
				    ),
                    new Ext.form.ComboBox({
                                    tpl: '<tpl for="."><div ext:qtip="{cdEstado}. {dsEstado}" class="x-combo-list-item">{dsEstado}</div></tpl>',
 	                                id:'windowEdicionComboDsEstadosId',
     	                            fieldLabel: getLabelFromMap('windowEdicionComboDsEstadosId',helpMap,'Estado'),
         	                        tooltip: getToolTipFromMap('windowEdicionComboDsEstadosId',helpMap, 'Estado de la tarea'),
									hasHelpIcon:getHelpIconFromMap('windowEdicionComboDsEstadosId',helpMap),
									Ayuda:getHelpTextFromMap('windowEdicionComboDsEstadosId',helpMap),
                                    store: dsEstados,
                                    //anchor:'100%',
                                    displayField:'dsEstado',
                                    valueField:'cdEstado',
                                    hiddenName: 'codigoEstado',
                                    typeAhead: true,
                                    mode: 'local',
                                    triggerAction: 'all',
                                    //fieldLabel: "Estado:",
                                    width: 250,
                                    emptyText:'Seleccione Estado...',
                                    selectOnFocus:true,
                                    forceSelection:true,
                                    allowBlank:false,
                   					 blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido')
                                    }),
		           	new Ext.form.TextField(
		           	{
		           	id:'windowEditarUrl',
                	fieldLabel: getLabelFromMap('windowEditarUrl',helpMap,'URL'),
      				tooltip:getLabelFromMap('windowEditarUrl',helpMap,'URL'),
					hasHelpIcon:getHelpIconFromMap('windowEditarUrl',helpMap),
					Ayuda:getHelpTextFromMap('windowEditarUrl',helpMap),
		           	//fieldLabel : 'URL',
		           	name : 'url',
		           	allowBlank : true, 
		           	width: 250
		           	}
		           	),
		           	new Ext.form.Checkbox({
				        id:'windowEditarCopio',
                    	fieldLabel: getLabelFromMap('windowEditarCopio',helpMap,'Se copia a otro cliente?'),
      			    	tooltip:getLabelFromMap('windowEditarCopio',helpMap, 'Copia a otro cliente'),
						hasHelpIcon:getHelpIconFromMap('windowEditarCopio',helpMap),
						Ayuda:getHelpTextFromMap('windowEditarCopio',helpMap),
				        //fieldLabel: '¿Se copia a otro cliente?',
				        name:'copio',
				        width: 250
				            }),
		           	new Ext.form.TextArea(
		           	{
	           			id:'windowEditarAyuda',
	                    fieldLabel: getLabelFromMap('windowEditarAyuda',helpMap,'Comentarios'),
	      			    tooltip:getLabelFromMap('windowEditarAyuda',helpMap, 'Comentarios'),
						hasHelpIcon:getHelpIconFromMap('windowEditarAyuda',helpMap),
						Ayuda:getHelpTextFromMap('windowEditarAyuda',helpMap),
	           			//fieldLabel : 'Comentarios',
	           			name : 'ayuda',
	           			allowBlank : true, 
	           			width: 250,
	           			emptyText: 'Comentario [opcional]...'
		           	}
		           	)
		            ]
		        });

//Windows donde se van a visualizar la pantalla

        var window = new Ext.Window({
        	id:'windowEditarId',
        	title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('windowEditarId', helpMap,'Configurar Tarea')+'</span>',
        	//title: 'Configurar Tarea',
        	width: 500,
        	height:350,
        	minWidth: 300,
        	modal: true,
        	minHeight: 100,
        	bodyStyle:'background: white',
        	layout: 'fit',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
				id: 'windowEditarIdButtonsGuardarId', 
				text: getLabelFromMap('windowEditarIdButtonsGuardarId', helpMap,'Guardar'),                
				tooltip: getToolTipFromMap('windowEditarIdButtonsGuardarId', helpMap, 'Guarda una tarea'), 
                //text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_TAREA_CHECKLIST,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso', action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();                                     
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),action.result.errorMessages[0]);
                            },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : 'guardando datos ...'
                        });
                    } else {
                        waitMsg : getLabelFromMap('400022', helpMap,'Guardando Actualizaci&oacute;n de datos...')
                    }
                }
            }, {
                id: 'windowEditarIdButtonsCancelarId',
                text: getLabelFromMap('windowEditarIdButtonsCancelarId', helpMap,'Cancelar'),                
				tooltip: getToolTipFromMap('windowEditarIdButtonsCancelarId', helpMap, 'Cancela el ingreso de una tarea'), 
                //text : 'Cancelar',
                handler : function() {
                    window.close();
                }
            }]
    	});



    	window.show();
        //se carga el formulario con los datos de la estructura a editar
        formPanel.form.load( {
            params:{
                seccion: record.get('cdSeccion'),
                tarea: record.get('cdTarea')
            },
            callback:function(){dsEstados.load({callback:function(){dsSecciones.load();}});},
            success:function(){
            	formPanel.findById('windowEditarCodigoTareaPadreId').setValue(record.get('dsTareapadre'));
            },
			waitMsg : 'leyendo datos...'
        });

    };
