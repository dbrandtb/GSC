function insertarOActualizarEquivalencia (_record) {

    /*var seccion_reg = new Ext.data.JsonReader({
						root: 'MAtributosFaxList',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
							{name: 'cdtipoar', type: 'string', mapping: 'cdtipoar'},
			        		{name: 'dsarchivo', type: 'string', mapping: 'dsarchivo'},			        		
			        		{name: 'cdatribu', type: 'string', mapping: 'cdatribu'},
			        		{name: 'dsAtribu', type: 'string', mapping: 'dsAtribu'},
			        		{name: 'swFormat',  type: 'string',  mapping:'swFormat'},
			        		{name: 'nmLmin',  type: 'string',  mapping:'nmLmin'},
			        		{name: 'nmLmax',  type: 'string',  mapping:'nmLmax'},
			        		{name: 'dsTabla',  type: 'string',  mapping:'dsTabla'},
			        		{name: 'swObliga',  type: 'string',  mapping:'swObliga'},
			        		//{name: 'otTabVal',  type: 'string',  mapping:'otTabVal'},
			        		{name: 'status',  type: 'string',  mapping:'status'}
						]
		);*/
	
	var storePais  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_PAISES}),
						reader: new Ext.data.JsonReader({
								root: 'comboPaises2'

							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							])
				});
	
	
			
    var storeColumnas = new Ext.data.Store({
                         proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_COLUMNAS}),
                         reader: new Ext.data.JsonReader(
                     	 {root: 'comboColumnas'},
                   		 [{name: 'codigo', type: 'string',mapping: 'codigo'},    
            		     {name: 'descripcion', type: 'string',mapping: 'descripcion'}
            		  ])
                }); 
                
				
	var storeUso  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_USO}),
						reader: new Ext.data.JsonReader({
								root: 'comboUso', 

								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							])
				});
				
	var storeSistemaExterno  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_SISTEMA_EXTERNO}),
						reader: new Ext.data.JsonReader({
								root: 'comboSistemaExterno', 

								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							])
				});
			
				

				

	var form_edit = new Ext.FormPanel ({
        //url : _ACTION_GET_ATRIBUTOS_FAX_REG,
        frame : true,
        width : 600,
        autoHeight: true,
        bodyStyle:'background: white',
        waitMsgTarget : true,
        layout: 'table',
        layoutConfig: { columns: 2},  //una sola columna , columnWidth: .50
        border: false,
        buttonAlign: "center",
        labelAlign: 'right',
        //reader: seccion_reg, 
        		items:[
               	  {            		
           			layout: 'form',
           			labelWidth: 75, 
           			colspan:2, 
           			items:[
		                 {
		                xtype: 'combo', 
		                id: 'cmbPaisId', 
		                tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			            store: storePais, 
			            fieldLabel: getLabelFromMap('cmbPais',helpMap,'Pa&iacute;s'),
		                tooltip: getToolTipFromMap('cmbPais',helpMap,'Pa&iacute;s'),			          		
			            displayField:'descripcion', 
			            valueField:'codigo', 
			            hiddenName: 'cdPais',
			            typeAhead: true,
			            mode: 'local', 
			            triggerAction: 'all', 
			            width: 160,
			            allowBlank:false,
			            blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
			            emptyText:'Seleccione Pais...',
			            selectOnFocus:true,
			            forceSelection:true
			           	}
			           	]
               	  },{            		
           			layout: 'form',
           			labelWidth: 75,
           			colspan:2,            		
           			items:[
			           	{
		                xtype: 'combo', 
		                id: 'cdSistemaId', 
		                tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			            store: storeSistemaExterno, 
			            fieldLabel: getLabelFromMap('cdSistemaAddEdit',helpMap,'C&oacute;digo de Sistema'),
		                tooltip: getToolTipFromMap('cdSistemaAddEdit',helpMap,'C&oacute;digo de Sistema'),			          		
			            displayField:'descripcion', 
			            valueField:'codigo', 
			            hiddenName: 'cdSistemaAddEdit',
			            typeAhead: true,
			            mode: 'local', 
			            triggerAction: 'all', 
			            width: 160,
			            allowBlank:false,
			            blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
			            emptyText:'Seleccione Sistema Externo...',
			            selectOnFocus:true,
			            forceSelection:true
			           	}
			           		
		              ]
               	  },{            		
           			layout: 'form',
           			labelWidth: 75,
           			colspan:2,            		
           			items:[
           				{
		                xtype: 'textfield', 
		               	fieldLabel: getLabelFromMap('nmTabla', helpMap,'C&oacute;digo de Tabla'),
						tooltip: getToolTipFromMap('nmTabla', helpMap, 'C&oacute;digo de Tabla'), 			                    	
		                name:'nmTabla',
		                allowBlank:false,
		                blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
		                width:100,
		                id: 'nmTablaId' 
		                }
		                ]
               	  },{            		
           			layout: 'form',
           			labelWidth: 75,
           			colspan:2,            		
           			items:[
           				{
		                xtype: 'textfield', 
		               	fieldLabel: getLabelFromMap('cdTablaAcwAddEdit', helpMap,'Tabla AON CatWeb'),
						tooltip: getToolTipFromMap('cdTablaAcwAddEdit', helpMap, 'Tabla AON CatWeb'), 			                    	
		                name:'cdTablaAcwAddEdit',
		                //allowBlank:false,
		                blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
		                width:100,
		                id: 'cdTablaAcwId' 
		                }
		                ]
               	  },{            		
           			layout: 'form',
           			labelWidth: 75,
           			colspan:2,            		
           			items:[
           				{
		                xtype: 'textfield', 
		               	fieldLabel: getLabelFromMap('cdTablaExtAddEdit', helpMap,'Sistema Externo'),
						tooltip: getToolTipFromMap('cdTablaExtAddEdit', helpMap, 'Sistema Externo'), 			                    	
		                name:'cdTablaExtAddEdit',
		                //allowBlank:false,
		                blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
		                width:100,
		                id: 'cdTablaExtId' 
		                }
           					
           					
           			   
		             ]
		           },{            		
          			layout: 'form',
          			labelWidth: 75,         			            		
          			items:[
		                {
		                xtype: 'combo', 
		                id: 'cmbIndUsoId', 
		                tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			            store: storeUso, 
			            fieldLabel: getLabelFromMap('indUso',helpMap,'Uso'),
		                tooltip: getToolTipFromMap('indUso',helpMap,'Uso'),			          		
			            displayField:'descripcion', 
			            valueField:'codigo', 
			            hiddenName: 'indUso',
			            typeAhead: true,
			            mode: 'local', 
			            triggerAction: 'all', 
			            width: 140,
			            allowBlank:false,
			            blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
			            emptyText:'Seleccione Uso...',
			            selectOnFocus:true,
			            forceSelection:true
			           	}
		             ]
			         },{            		
          			layout: 'form',
          			labelWidth: 75,         			            		
          			items:[
		                {
		                xtype: 'combo', 
		                id: 'cmbNmColumnaId', 
		                tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			            store: storeColumnas, 
			            fieldLabel: getLabelFromMap('nmColumna',helpMap,'Columnas'),
		                tooltip: getToolTipFromMap('nmColumna',helpMap,'Columnas'),			          		
			            displayField:'descripcion', 
			            valueField:'codigo', 
			            hiddenName: 'nmColumna',
			            typeAhead: true,
			            mode: 'local', 
			            triggerAction: 'all', 
			            width: 150,
			            allowBlank:false,
			            blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
			            emptyText:'Seleccione Columnas...',
			            selectOnFocus:true,
			            forceSelection:true
			           	}
		             ]
			         },
		             {
            		layout: 'form',
            		labelWidth: 75,
            		colspan:2, 
			    	items:[
			    		{
			    		xtype: 'textfield', 
		               	fieldLabel: getLabelFromMap('chkObligatorioAddEdit', helpMap,'Descripci&oacute;n'), 
						tooltip: getToolTipFromMap('chkObligatorioAddEdit', helpMap, 'Descripci&oacute;n'), 			                    	
		                name: 'dsUsoAcwAddEdit',
		                allowBlank:false,
		                width: 450,
		                blankText: getMsgBlankTextFromMap('400000', helpMap, 'Este campo es requerido'),
		                id: 'dsUsoAcwId'
			    	} ]
			    	}		           	
        		]
        	
    });
    
    
    
    
	var titulo;
	titulo = '<span style="color:black;font-size:12px;">'+getLabelFromMap('95', helpMap,'Insertar / Actualizar')+'</span>';
//	if(record == undefined ){
//		titulo='<span style="color:black;font-size:12px;">'+getLabelFromMap('94', helpMap,'Agregar Secci&oacute;n')+'</span>';
//	}


  function guardarAtributosFax(_code){
  	var _url;
  	if(_code==0){
  	   
  		_url= _ACTION_GUARDAR_EQUIVALENCIA_CATALOGOS
  	}else
  	{
  		_url= _ACTION_ACTUALIZAR_EQUIVALENCIA_CATALOGOS
  	}
    var conn = new Ext.data.Connection();
	                conn.request({
				    	url: _url,
				    	method: 'POST',
				    	params: {
				    				
				    			    	countryCode: form_edit.findById("cmbPaisId").getValue(),
										cdSistema: form_edit.findById("cdSistemaId").getValue(),
										nmTabla: form_edit.findById("nmTablaId").getValue(),
										cdTablaAcw: form_edit.findById("cdTablaAcwId").getValue(),
										cdTablaExt: form_edit.findById("cdTablaExtId").getValue(),
										indUso: form_edit.findById("cmbIndUsoId").getValue(), 
										nmColumna: form_edit.findById("cmbNmColumnaId").getValue(),
										dsUsoAcw: form_edit.findById("dsUsoAcwId").getValue()
				   
				    			},
	 						waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
                   		     
        if (Ext.util.JSON.decode(response.responseText).success == false) {
        	 Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).actionErrors[0]);
        }else {
             
             Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito',function(){reloadGrid()});
             window.close();
             // function(){reloadGridNivAtencion(cdMatriz);habilitaBotonesTiempo();/*reloadGridResponsables("","");*/ _window.close();}
        }
    }
 })
} 
	
	var window = new Ext.Window ({
			title: titulo,
			width: 600,
			autoHeight: true,
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	modal: true,
        	items: form_edit,
            buttons : [ {
				text:getLabelFromMap('edtSeccionButtonGuardar', helpMap,'Guardar'),
				tooltip:getToolTipFromMap('edtSeccionButtonGuardar', helpMap,'Guarda la nueva secci&oacute;n creada'),
                disabled : false,
                handler : function() {
                	var code=0;
                	if(_record){code=1};
                   //guardarAtributosFax(code);
                   code=code+1;
                   
                    if (form_edit.form.isValid()) {
                    	
                    	if ((form_edit.findById("cdTablaAcwId").getValue() == "" && form_edit.findById("cdTablaExtId").getValue() == ""))
                          Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Ingrese al menos un nombre para el campo Tabla AON Catweb o Sistema Externo'));                    	
                        else guardarAtributosFax(code);                    	
                        /*form_edit.form.submit( {
                            url : _ACTION_GUARDAR_EQUIVALENCIA_CATALOGOS,
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                           },
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });*/
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
	
	
	/*if (_record != null){
       //form_edit.findById('cdtipoar').setValue(record.get('cdtipoar'));
       //form_edit.findById('cdatribu').setValue(record.get('cdatribu'));
       form_edit.form.load ({
               params: {
               		 cdtipoar: _record.get('cdtipoar'),
               		 cdatribu: _record.get('cdatribu')
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
	} */
	if (_record == null)
	{storePais.load();
    	storeColumnas.load();
    	storeUso.load();
    	storeSistemaExterno.load();
	}
    window.show();
    //form_edit.submit();    
    
   if(_record){
       //Ext.getCmp("cdSistemaId").setValue(_record.get("cdSistema"));	
       Ext.getCmp("nmTablaId").setValue(_record.get("nmTabla"));	
       Ext.getCmp("cdTablaAcwId").setValue(_record.get("cdTablaAcw"));	
       Ext.getCmp("cdTablaExtId").setValue(_record.get("cdTablaExt"));	
       Ext.getCmp("dsUsoAcwId").setValue(_record.get("dsUsoAcw"));
       Ext.getCmp("nmTablaId").setDisabled(true);
       Ext.getCmp("cdSistemaId").setDisabled(true);
       
      storeUso.load({
    			callback:function(){
    					Ext.getCmp("cmbIndUsoId").setValue(_record.get("indUso"));
    					storeColumnas.load({
    						callback:function(){
    							Ext.getCmp("cmbNmColumnaId").setValue(_record.get("nmColumna"));
    							storePais.load({
    								callback:function(){
    									Ext.getCmp("cmbPaisId").setValue(_record.get("countryCode"));
    									Ext.getCmp("cmbPaisId").setDisabled(true);
    									storeSistemaExterno.load({
    										callback:function(){
    											Ext.getCmp("cdSistemaId").setValue(_record.get("cdSistema"));
    												}
    											  })
    											}    			
    										});			
    							}    			
    					});
    				}    			
    		}); 
        
      
    		  	
    }
    else Ext.getCmp("nmTablaId").setDisabled(true);
}


