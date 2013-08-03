// Funcion de Agregar  Tarea de CheckList
function agregar() {
		
		  var dsSecciones = new Ext.data.Store({
			     proxy: new Ext.data.HttpProxy({
			     url: _ACTION_OBTENER_SECCIONES,
			     method: 'POST'
		       	}),
		  		 reader: new Ext.data.JsonReader({root: 'seccionesComboBox',id: 'cdSeccion'},
		  		 [{name: 'cdSeccion', type: 'string',mapping:'cdSeccion'},
		  		  {name: 'dsSeccion', type: 'string',mapping:'dsSeccion'}
		  		 ])
		  		 });
		
		  var dsTareasPadre = new Ext.data.Store({
			     //autoLoad: true,
			     proxy: new Ext.data.HttpProxy({
			     url: _ACTION_LISTA_TAREAS,
			     method: 'POST'
		       	}),
		  		reader: new Ext.data.JsonReader({root: 'MEstructuraList',id: 'cdTareapadre'},
		  		 [{name: 'cdTareapadre', type: 'string',mapping:'cdTareapadre'},
		  		  {name: 'dsTareapadre', type: 'string',mapping:'dsTareapadre'}
		  		 ])
		  		 });
		  
		  var dsEstados = new Ext.data.Store({
			     //autoLoad: true,
			     proxy: new Ext.data.HttpProxy({
			     url: _ACTION_OBTENER_ESTADOS,
			     method: 'POST'
		       	}),
		  		reader: new Ext.data.JsonReader({root: 'estadosComboBox',id: 'cdEstado'},
		  		 [{name: 'cdEstado', type: 'string',mapping:'cdEstado'},
		  		  {name: 'dsEstado', type: 'string',mapping:'dsEstado'}
		  		 ])
		  		 });
		  	  	
          var formPanel = new Ext.FormPanel( {
	            labelWidth : 100,
	            url : _ACTION_GUARDAR_NUEVO_TAREA_CHECKLIST,
	            frame : true,
	            renderTo: Ext.get('formulario'),
	            bodyStyle : 'padding:5px 5px 0',
	            bodyStyle:'background: white',
	            width : 350,
	            labelAlign:'right',
	            waitMsgTarget : true,
	            defaults : {
	                width : 230
	            },
	            defaultType : 'textfield',
	            //se definen los campos del formulario
	            items : [
                new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{cdSeccion}. {dsSeccion}" class="x-combo-list-item">{dsSeccion}</div></tpl>',
                                id:'windowAgregarComboDsSeccionId',
                                fieldLabel: getLabelFromMap('windowAgregarComboDsSeccionId',helpMap,'Secciones'),
                                tooltip: getToolTipFromMap('windowAgregarComboDsSeccionId',helpMap, 'Secciones'),
                                hasHelpIcon:getHelpIconFromMap('windowAgregarComboDsSeccionId',helpMap),
								Ayuda:getHelpTextFromMap('windowAgregarComboDsSeccionId',helpMap),
                                store: dsSecciones,
                                //anchor:'100%',
                                width:200,
                                displayField:'dsSeccion',
                                valueField:'cdSeccion',
                                hiddenName: 'codigoSeccion',
                                typeAhead: true,
                                mode: 'local', 
				                allowBlank: false, 
				                blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                                triggerAction: 'all',
                                //fieldLabel: "Secciones",
                                width: 100,
                                emptyText:'Seleccione Seccion...',
                                selectOnFocus:true,
                                forceSelection:true,
                                onSelect: function (record) {
                                				this.setValue(record.get('cdSeccion'));
                                				this.collapse();
                                				dsTareasPadre.load({params:{
										            codSeccion: record.get('cdSeccion'),
										            codTarea: ''
										        }});
                                		}
                                }),
                new Ext.form.TextField(
                 {
                  //fieldLabel: 'Taread', 
                  id:'windowAgregarDescrTareaId', 
				  fieldLabel: getLabelFromMap('windowAgregarDescrTareaId',helpMap,'Tarea'),
                  tooltip: getToolTipFromMap('windowAgregarDescrTareaId',helpMap,'Tarea del Checklist'),
				  hasHelpIcon:getHelpIconFromMap('windowAgregarDescrTareaId',helpMap),
				  Ayuda:getHelpTextFromMap('windowAgregarDescrTareaId',helpMap),
                  name: 'descrTarea', 
                  allowBlank: false, 
                  blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                  width: 200
                  }
                  ),
			    new Ext.form.Hidden(
			    {
			    fieldLabel : 'Tarea',
			    name : 'codigoTarea',
			    allowBlank : false,
			    width: 200
			    }
			    ),
			    new Ext.form.ComboBox({
			    	tpl: '<tpl for="."><div ext:qtip="{cdTareapadre}. {dsTareapadre}" class="x-combo-list-item">{dsTareapadre}</div></tpl>',
                    id:'windowAgregarComboDsTareaPadre',
                    fieldLabel: getLabelFromMap('windowAgregarComboDsTareaPadre',helpMap,'Tarea padre'),
                    tooltip: getToolTipFromMap('windowAgregarComboDsTareaPadre',helpMap, 'Tarea padre del Checklist'),
				    hasHelpIcon:getHelpIconFromMap('windowAgregarComboDsTareaPadre',helpMap),
				    Ayuda:getHelpTextFromMap('windowAgregarComboDsTareaPadre',helpMap),
				    store: dsTareasPadre,
                    //anchor:'100%',
                    width:200,
                    displayField:'dsTareapadre',
                    valueField:'cdTareapadre',
                    hiddenName: 'codigoTareaPadre',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    //fieldLabel: "Tarea padre",
                    emptyText:'Seleccione Tarea padre...',
                    selectOnFocus:true,
                    forceSelection:true
			     }),			  
                new Ext.form.ComboBox({
                                tpl: '<tpl for="."><div ext:qtip="{cdEstado}. {dsEstado}" class="x-combo-list-item">{dsEstado}</div></tpl>',
                                id:'windowAgregarComboDsEstado',
                                fieldLabel: getLabelFromMap('windowAgregarComboDsEstado',helpMap,'Estado'),
                    			tooltip:getToolTipFromMap('windowAgregarComboDsEstado',helpMap,'Estado de tareas del Checklist'),
							    hasHelpIcon:getHelpIconFromMap('windowAgregarComboDsEstado',helpMap),
							    Ayuda:getHelpTextFromMap('windowAgregarComboDsEstado',helpMap),
                                store: dsEstados,
                                //anchor:'100%',
                                displayField:'dsEstado',
                                valueField:'cdEstado',
                                hiddenName: 'codigoEstado',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                //fieldLabel: "Estados",
                                width: 200,
                                allowBlank: false, 
                  				blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
                                emptyText:'Seleccione Estado...',
                                selectOnFocus:true,
                                forceSelection:true
                                }),
	           	new Ext.form.TextField(
	           	{
                id:'windowAgregarUrl',
                fieldLabel: getLabelFromMap('windowAgregarUrl',helpMap,'URL'),
      			tooltip:getLabelFromMap('windowAgregarUrl',helpMap, 'URL'),
				hasHelpIcon:getHelpIconFromMap('windowAgregarUrl',helpMap),
				Ayuda:getHelpTextFromMap('windowAgregarUrl',helpMap),
	           	//fieldLabel : 'URL',
	           	name : 'url',
	           	allowBlank : true,
	           	width: 200
	           	}
	           	),
	           	new Ext.form.Checkbox({
                    id:'windowAgregarCopio',
                    fieldLabel: getLabelFromMap('windowAgregarCopio',helpMap,'Se copia a otro cliente?'),
      			    tooltip:getLabelFromMap('windowAgregarCopio',helpMap, 'Copia a otro cliente'),			        
				    hasHelpIcon:getHelpIconFromMap('windowAgregarCopio',helpMap),
				    Ayuda:getHelpTextFromMap('windowAgregarCopio',helpMap),
			        //fieldLabel: '¿Se copia a otro cliente?',
			        name:'copio',
			        readOnly:false,
			        checked :false
			            }),
	           	new Ext.form.TextArea({
	                    id:'windowAgregarAyuda',
	                    fieldLabel: getLabelFromMap('windowAgregarAyuda',helpMap,'Comentarios'),
	      			    tooltip:getLabelFromMap('windowAgregarAyuda',helpMap,'Comentarios'),			
						hasHelpIcon:getHelpIconFromMap('windowAgregarAyuda',helpMap),
						Ayuda:getHelpTextFromMap('windowAgregarAyuda',helpMap),
	           			//fieldLabel : 'Comentarios',
	           			name : 'ayuda',
	           			allowBlank : true,
	           			width: 200,
	           			emptyText: 'Comentario [opcional]...'})
	            ]
	        });

//Windows donde se van a visualizar la pantalla
        var window = new Ext.Window({
           	id:'windowAgregarId',
        	title: '<span style="color:black;font-size:14px;">'+getLabelFromMap('windowAgregarId', helpMap,'Configurar Tarea')+'</span>',
            //title: 'Configurar Tarea',
        	width: 500,
        	height:350,
        	modal: true,
        	layout: 'fit',
        	bodyStyle:'background: white',
        	plain:true,
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',        	
        	items: formPanel,
            //se definen los botones del formulario
            buttons : [ {
				id: 'windowAgregarIdButtonsGuardarId', 
				text: getLabelFromMap('windowAgregarIdButtonsGuardarId', helpMap,'Guardar'),
				tooltip: getToolTipFromMap('windowAgregarIdButtonsGuardarId', helpMap, 'Guarda una tarea'),
				//text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_NUEVO_TAREA_CHECKLIST,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                            },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                }
            }, {
            	id: 'windowAgregarIdButtonsCancelarId', 
            	text: getLabelFromMap('windowAgregarIdButtonsCancelarId', helpMap,'Cancelar'),
				tooltip: getToolTipFromMap('windowAgregarIdButtonsCancelarId', helpMap, 'Cancela el ingreso de una tarea'),
                //text : 'Cancelar',
                handler : function() {
                    window.close();
                }
            }]
    	});
    	
		
    	window.show();
        dsTareasPadre.load({
        		params:{codSeccion: 1,codTarea: 1},
        		callback:function(){dsSecciones.load({callback:function(){dsEstados.load();}});}
        	});

};