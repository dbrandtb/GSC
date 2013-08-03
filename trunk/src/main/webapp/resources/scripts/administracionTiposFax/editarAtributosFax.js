function editar (record) {
    var seccion_reg = new Ext.data.JsonReader({
						root: 'MAtributosFaxList',                      
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
							{name: 'cdtipoar', type: 'string', mapping: 'cdtipoar'},
			        		{name: 'dsarchivo', type: 'string', mapping: 'dsarchivo'},
			        		
			        		{name: 'cdatribu', type: 'string', mapping: 'cdatribu'},
			        		{name: 'dsatribu', type: 'string', mapping: 'dsAtribu'},
			        		{name: 'swFormat',  type: 'string',  mapping:'swFormat'},
			        		{name: 'nmLmin',  type: 'string',  mapping:'nmLmin'},
			        		{name: 'nmLmax',  type: 'string',  mapping:'nmLmax'},
			        		{name: 'dsTabla',  type: 'string',  mapping:'dsTabla'},
			        		{name: 'swObliga',  type: 'string',  mapping:'swObliga'},
			        		{name: 'otTabVal',  type: 'string',  mapping:'otTabVal'},
			        		{name: 'status',  type: 'string',  mapping:'status'}
						]
		);

	var formato_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_FORMATO}),
						reader: new Ext.data.JsonReader({
								root: 'comboFormatosArchivos', 
							//	id: 'codigo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							])//,
						//remoteSort: true
					
				});
				
/*   se agrego */				
     var storeStatus = new Ext.data.Store({
                         proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_STATUS}),
                         reader: new Ext.data.JsonReader(
                     	 {root: 'comboTareaEstatus'},
                   		 [{name: 'codigo', type: 'string'},    //status
            		     {name: 'descripcion', type: 'string'}
            		  ])
                }); 
				
				
/*************/
				
				
	var tablas_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_TABLAS}),
						reader: new Ext.data.JsonReader({
								root: 'comboTablasArchivos',
								id: 'codigo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							]),
						remoteSort: true
				});
				

	var form_edit = new Ext.FormPanel ({
        labelWidth : 50,
        url : _ACTION_GET_ATRIBUTOS_FAX_REG,
        //frame : true,
        bodyStyle : 'padding:5px 5px 0',
        width : 690,
        autoHeight: true,
        bodyStyle:'background: white',
        waitMsgTarget : true,
        layout: 'table',
        layoutConfig: { columns: 3, columnWidth: .33},
        border: false,
        buttonAlign: "center",
        labelAlign: 'right',
        defaults:{width:200},
        reader: seccion_reg, 
        		items:[
               	  {            		
           			layout: 'form',
           			labelWidth: 45,	            		
           			items:[
		                {
		                xtype: 'textfield', 
		               	fieldLabel: getLabelFromMap('dsatribu', helpMap,'Atributo'), 
						tooltip: getToolTipFromMap('dsatribu', helpMap, 'Atributo'), 	
                        hasHelpIcon:getHelpIconFromMap('dsatribu',helpMap),								 
                        Ayuda: getHelpTextFromMap('dsatribu',helpMap,'h'),
						
		                name: 'dsatribu',
		                //width:180,
		                allowBlank:false,
		                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		                id: 'dsatribu'
		                }
		               ]
		             },
		             {            		
           			layout: 'form',
           			labelWidth: 45,       			            		
           			items:[
		                {
		                xtype: 'textfield', 
		               	fieldLabel: getLabelFromMap('cdatribu', helpMap,'Codigo'), 
						tooltip: getToolTipFromMap('cdatribu', helpMap, 'Codigo'), 
                        hasHelpIcon:getHelpIconFromMap('dsacdatributribu',helpMap),								 
                        Ayuda: getHelpTextFromMap('cdatribu',helpMap,'h'),
						
		                name: 'cdatribu',
		                allowBlank:false,
		                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		                id: 'cdatribu'
		                }
		               ]
		             },
		             {            		
           			layout: 'form',
           			labelWidth: 45,           			            		
           			items:[
		                {
		                xtype: 'combo', 
		                id: 'cmbFormatoId',              //'idDsarchivo', 
		                tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			            store: formato_store, 
			            name: 'combito',
			            fieldLabel: getLabelFromMap('cmbFormatoId',helpMap,'Formato'),
		                tooltip: getToolTipFromMap('cmbFormatoId',helpMap,'Formato'),
                        hasHelpIcon:getHelpIconFromMap('cmbFormatoId',helpMap),								 
                        Ayuda: getHelpTextFromMap('cmbFormatoId',helpMap,'h'),
		                
			            displayField:'descripcion', 
			            valueField:'codigo', 
			            hiddenName: 'swFormat', 
			            typeAhead: true,
			            mode: 'local', 
			            triggerAction: 'all', 
			            width: 140,
			            allowBlank:false,
			         /*   blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
			            emptyText:'Seleccione Formato...',*/
			            selectOnFocus:true,
			            forceSelection:true 
			           	}
			           ]
			         },
			         {
            		layout: 'form',
            		labelWidth: 45, 
            		width:220,
			    	items:[
		                {
		                xtype: 'textfield', 
		               	fieldLabel: getLabelFromMap('nmLmin', helpMap,'M&iacute;nimo'), 
						tooltip: getToolTipFromMap('nmLmin', helpMap, 'M&iacute;nimo'), 	
                        hasHelpIcon:getHelpIconFromMap('nmLmin',helpMap),								 
                        Ayuda: getHelpTextFromMap('nmLmin',helpMap,'h'),
						
		                name: 'nmLmin',
		                allowBlank:false,
		               // blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		                id: 'nmLmin'
		                }
		                ]
		             },
		             {            		
           			layout: 'form',
           			labelWidth: 45,
           			colspan:2,            			            		
           			items:[
		               {
		                xtype: 'textfield', 
		               	fieldLabel: getLabelFromMap('nmLmax', helpMap,'M&aacute;ximo'), 
						tooltip: getToolTipFromMap('nmLmax', helpMap, 'M&aacute;ximo'), 
                        hasHelpIcon:getHelpIconFromMap('nmLmax',helpMap),								 
                        Ayuda: getHelpTextFromMap('nmLmax',helpMap,'h'),
						
		                name: 'nmLmax',
		                allowBlank:false,
		                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		                id: 'nmLmax'
		                }
		                ]
		             },
		             {
            		layout: 'form',
            		labelWidth: 45,
            		colspan:2, 
			    	items:[ 
		                {
		               /* xtype: 'textfield', 
		               	fieldLabel: getLabelFromMap('txtFldTabEdt', helpMap,'Status'), 
						tooltip: getToolTipFromMap('txtFldTabEdt', helpMap, 'Status'), 			                    	
		                name: 'status',
		                allowBlank:false,
		                blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
		                id: 'status'*/
           			  layout: 'form',  
           			  labelWidth: 45,  
           			  colspan:3,        			            		
           			  items: [
           				      {
            				   xtype: 'combo',
            				   tpl: '<tpl for="."><div ext:qtip="{codigo}" class="x-combo-list-item">{descripcion}</div></tpl>',
 	                           id:'acCmbStatusId',
	                           fieldLabel: getLabelFromMap('acCmbStatusId',helpMap,'Status'),
  	            			   tooltip:getToolTipFromMap('acCmbStatusId',helpMap,'Status'),
                               hasHelpIcon:getHelpIconFromMap('acCmbStatusId',helpMap),								 
                               Ayuda: getHelpTextFromMap('acCmbStatusId',helpMap,'h'),
  	            			   
                               store: storeStatus,
    	                       displayField:'descripcion',
    	                       valueField:'codigo',
    	                       hiddenName: 'status',        //'codigo',
    	                       typeAhead: true,
    	                       mode: 'local',
    	                       triggerAction: 'all',
    	                       width: 140,
    	                       allowBlank: false, 
	                           emptyText:'Seleccione Estatus...',
	                           selectOnFocus:true,
	                           forceSelection:true
					          }
           				     ]
		                
		                
		                }
		               ]
		              },
		              {
            		layout: 'form',
            		labelWidth: 70,
			    	items:[{
			    		xtype: 'checkbox', 
		               	fieldLabel: getLabelFromMap('swObliga', helpMap,'Obligatorio'), 
						tooltip: getToolTipFromMap('swObliga', helpMap, 'Obligatorio'), 	
                        hasHelpIcon:getHelpIconFromMap('swObliga',helpMap),								 
                        Ayuda: getHelpTextFromMap('swObliga',helpMap,'h'),
						
		                name: 'swObliga',
		                allowBlank:false,
		            //    blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		                id: 'swObliga'
		                
			    	} ]
			    	},
		            {
            		layout: 'form',
            		labelWidth: 45,
			    	items:[ 
		                {
		                xtype: 'combo', 
		                id: 'idStatus', 
		                //labelWidth: 10, 
		                tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			            store: tablas_store, 
			            fieldLabel: getLabelFromMap('idStatus',helpMap,'Tabla'),
		                tooltip: getToolTipFromMap('idStatus',helpMap,'Tabla'),	
                        hasHelpIcon:getHelpIconFromMap('idStatus',helpMap),								 
                        Ayuda: getHelpTextFromMap('idStatus',helpMap,'h'),
		                
			            displayField:'descripcion', 
			            valueField:'codigo', 
			            hiddenName: 'dsTabla',  //'otTabVal',           // 'dsTabla', 
			            typeAhead: true,
			            mode: 'local', 
			            triggerAction: 'all', 
			            width: 140,
			            allowBlank:false,
			            blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
			            emptyText:'Seleccione Status...',
			            selectOnFocus:true,
			            forceSelection:true 
			            //labelSeparator:''
			           	},
			           	{
			           	xtype: 'hidden', 
                		id: 'cdtipoar', 
                		name: 'cdtipoar'                  //////////////
			           	},
			           	/*{
			           	xtype: 'hidden', 
                		id: 'swFormat', 
                		name: 'swFormat'
			           	},*/
			           	{
			           	xtype: 'hidden', 
                		id: 'otTabVal', 
                		name: 'otTabVal'
			           	}
			           	]
			          }
        		]
        	
    });
    
    
    
    
/*	var titulo;
	titulo = '<span style="color:black;font-size:12px;">'+getLabelFromMap('95', helpMap,'Editar Secci&oacute;n')+'</span>';
//	if(record == undefined ){
//		titulo='<span style="color:black;font-size:12px;">'+getLabelFromMap('94', helpMap,'Agregar Secci&oacute;n')+'</span>';
	}*/
	
	var window = new Ext.Window ({
		/*    id:'Editarwin',
		    
            title: getLabelFromMap('Editarwin', helpMap,'Editar Secci&oacute;n'),*/		    
            
		    id:'EditAtriArchivo',
            title: getLabelFromMap('EditAtriArchivo', helpMap,'Atributos de Archivo'),		    
            
			width: 700,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	modal: true,
        	items: form_edit,
            //se definen los botones del formulario
            buttons : [ {
				text:getLabelFromMap('edtSeccionButtonGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edtSeccionButtonGuardar', helpMap,'Guarda la nueva secci&oacute;n creada'),
                //text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (form_edit.form.isValid()) {
                        form_edit.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_GUARDAR_ATRIBUTOS_FAX,
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
				text:getLabelFromMap('edtSeccionButtonCancelar', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('edtSeccionButtonCancelar', helpMap, 'Cancela la operaci&oacute;n de guardar'),
                handler : function() {
                    window.close();
                }
            }]
	});
	if (record != null && record.get('cdtipoar') != "" && record.get('cdatribu') != "") {
        form_edit.findById('cdtipoar').setValue(record.get('cdtipoar'));
        form_edit.findById('cdatribu').setValue(record.get('cdatribu'));
        form_edit.form.load ({
                params: {cdtipoar: record.get('cdtipoar'),
                		 cdatribu: record.get('cdatribu'),
                		 nmLmax: record.get('nmLmax'),
                		 nmLmin: record.get('nmLmin'),
                		 swObliga: record.get('swObliga')
                },
                success: function(form, action) {
                    //var bloqueEditable = form_edit.findById('bloqueEditable').getValue();
                    //if (bloqueEditable == 'false') {
                    //    form_edit.findById('idCdBloque').setDisabled(true);
                    
                    var _swformat= action.reader.jsonData.MAtributosFaxList[0].swFormat;
                    formato_store.load({
                               callback:function(r,o,success){
                               if (success)
                               {
                               	form_edit.findById('cmbFormatoId').setValue(_swformat);
                               }
                              }})
                    
                    
                    } //else {
                        //form_edit.findById('idCdBloque').setDisabled(false);
                    //}
                //}
        });
	} 
	
   // formato_store.load();
    tablas_store.load();
    storeStatus.load();
    
    window.show();
    //form_edit.submit();
}