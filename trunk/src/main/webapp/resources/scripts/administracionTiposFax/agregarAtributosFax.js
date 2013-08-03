function insertarOActualizarAtributos (_record) {
    //alert(_record.get("status"));
    var seccion_reg = new Ext.data.JsonReader({
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
		);
	var formato_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_FORMATO}),
						reader: new Ext.data.JsonReader({
								root: 'comboFormatosArchivos'
								//id: 'codigo',
								//successProperty: '@success'
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
            		  ])//descripcion
                }); 
				
				
/*************/
				
	var tablas_store  = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_TABLAS}),
						reader: new Ext.data.JsonReader({
								root: 'comboTablasArchivos', 
							//	id: 'codigo',
								successProperty: '@success'
							}, [
								{name: 'codigo', type: 'string', mapping: 'codigo'},
								{name: 'descripcion', type: 'string', mapping: 'descripcion'}
							])//,
						//remoteSort: true
				});
				

				

	var form_edit = new Ext.FormPanel ({
        url : _ACTION_GET_ATRIBUTOS_FAX_REG,
        frame : true,
        width : 600,
        autoHeight: true,
        bodyStyle:'background: white',
        waitMsgTarget : true,
        layout: 'table',
        layoutConfig: { columns: 3, columnWidth: .33},
        border: false,
        buttonAlign: "center",
        labelAlign: 'right',
        //reader: seccion_reg, 
        		items:[
               	  {            		
           			layout: 'form',
           			labelWidth: 60,
           			colspan:2,            		
           			items:[
		                {
		                xtype: 'textfield', 
		               	fieldLabel: getLabelFromMap('dsarchivoId', helpMap,'Atributo'),
						tooltip: getToolTipFromMap('dsarchivoId', helpMap, 'Descripci&oacute;n del Atributo'), 	
						hasHelpIcon:getHelpIconFromMap('dsarchivoId',helpMap),								 
                        Ayuda: getHelpTextFromMap('dsarchivoId',helpMap),		                    	
		                name:'dsarchivo',
		                allowBlank:false,
		                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		                width:226,
		                id: 'dsarchivoId' 
		                }
		             ]
		           },
	               {            		
          			layout: 'form',
          			labelWidth: 82,  
          		
          			items:[
		                {
		                xtype: 'combo', 
		                id: 'cmbFormatoId', 
		                tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			            store: formato_store, 
			            fieldLabel: getLabelFromMap('cmbFormatoId',helpMap,'Formato'),
		                tooltip: getToolTipFromMap('cmbFormatoId',helpMap,'Seleccione Formato'),	
		                hasHelpIcon:getHelpIconFromMap('cmbFormatoId',helpMap),								 
                        Ayuda: getHelpTextFromMap('cmbFormatoId',helpMap),			          		
			            displayField:'descripcion', 
			            valueField:'codigo', 
			            hiddenName: 'swFormat',
			            typeAhead: true,
			            mode: 'local', 
			            triggerAction: 'all', 
			            width: 130,
			           // bodyStyle:'padding:3px;',
			            allowBlank:false,
			            blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
			            emptyText:'Seleccione Formato...',
			            selectOnFocus:true,
			            forceSelection:true
			           	}
		             ]
			         },
			         {
	            		layout: 'form',
	            		labelWidth: 60, 
	            		items:[
			                {
			                xtype: 'textfield', 
			               	fieldLabel: getLabelFromMap('nmLminId', helpMap,'M&iacute;nimo'), 
							tooltip: getToolTipFromMap('nmLminId', helpMap, 'M&iacute;nimo'), 
							hasHelpIcon:getHelpIconFromMap('nmLminId',helpMap),								 
                            Ayuda: getHelpTextFromMap('nmLminId',helpMap),				                    	
			                name: 'nmLmin',
			                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
			                width:75,
		                    maxLength:2,
			                id: 'nmLminId'
			                }
			                ]
		             },
		             {            		
           			layout: 'form',
           			labelWidth: 40,           			            			            
           			items:[
		               {
		                xtype: 'textfield', 
		               	fieldLabel: getLabelFromMap('nmLmaxId', helpMap,'M&aacute;ximo'), 
						tooltip: getToolTipFromMap('nmLmaxId', helpMap, 'M&aacute;ximo'), 
						hasHelpIcon:getHelpIconFromMap('nmLmaxId',helpMap),								 
                        Ayuda: getHelpTextFromMap('nmLmaxId',helpMap),				                    	
		                name: 'nmLmax',
		                allowBlank:false,
		                maxLength:2,
		                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		                width:78,
		                id: 'nmLmaxId'
		                }
		                ]
		             },
		             {
            		layout: 'form'		    	
			    	},
		            {
            		layout: 'form',
            		labelWidth: 60,
            		colspan:2,
			    	items:[ 
		                {
		                xtype: 'combo', 
		                id: 'cmbTablaId', 
		                tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
			            store: tablas_store, 
			            fieldLabel: getLabelFromMap('cmbTablaId',helpMap,'Tabla'),
		                tooltip: getToolTipFromMap('cmbTablaId',helpMap,'Seleccione Tabla'),	
		                hasHelpIcon:getHelpIconFromMap('cmbTablaId',helpMap),								 
                        Ayuda: getHelpTextFromMap('cmbTablaId',helpMap),				          		
			            displayField:'descripcion', 
			            valueField:'codigo', 
			            hiddenName: 'otTabVal', 
			            typeAhead: true,
			            mode: 'local', 
			            triggerAction: 'all', 
			            width: 250,
			            //allowBlank:false,
			            blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
			            emptyText:'Seleccione Status...',
			            selectOnFocus:true,
			            forceSelection:true
			           	}
		                ]
		             },
		             {
            		layout: 'form',
            		labelWidth: 200,
			    	items:[{
			    		xtype: 'checkbox', 
		               	fieldLabel: getLabelFromMap('swObliga', helpMap,'Obligatorio'), 
						tooltip: getToolTipFromMap('swObliga', helpMap, 'Obligatorio'),
						 hasHelpIcon:getHelpIconFromMap('swObliga',helpMap),								 
                        Ayuda: getHelpTextFromMap('swObliga',helpMap),					                    	
		                name: 'swObliga',
		                width:30,
		                allowBlank:false,
		                blankText: getMsgBlankTextFromMap('400055', helpMap, 'Este campo es requerido'),
		                id: 'swObliga'
			    	} ]
			    	},
		             {		                
           			  layout: 'form',  
           			  labelWidth: 60,  
           			  colspan:3,        			            		
           			  items: [
           				      {
            				   xtype: 'combo',
            				   tpl: '<tpl for="."><div ext:qtip="{codigo}" class="x-combo-list-item">{descripcion}</div></tpl>',
 	                           id:'acCmbStatusId',
	                           fieldLabel: getLabelFromMap('acCmbStatusId',helpMap,'Status'),
  	            			   tooltip:getToolTipFromMap('acCmbStatusId',helpMap,'Seleccione Status'),
  	            			   hasHelpIcon:getHelpIconFromMap('acCmbStatusId',helpMap),								 
                               Ayuda: getHelpTextFromMap('acCmbStatusId',helpMap),		
                               store: storeStatus,
    	                       displayField:'descripcion',
    	                       valueField:'codigo',
    	                       hiddenName: 'status',
    	                       typeAhead: true,
    	                       mode: 'local',
    	                       triggerAction: 'all',
    	                       width: 120,
    	                       allowBlank: false, 
	                           emptyText:'Seleccione Estatus...',
	                           selectOnFocus:true,
	                           forceSelection:true
					          }
           				     ]		                
		                },
			           	{
			           	xtype: 'hidden', 
                		id: 'cdtipoarId', 
                		name: 'cdtipoar',
                		value: CDTIPOAR
			           	},
			           	{
			           	xtype: 'hidden', 
                		id: 'cdatribuId', 
                		name: 'cdatribu'
                		//value: DSARCHIVO
			           	}			           	
        		]
        	
    });
    
    
    
    
	var titulo;
	//titulo = '<span style="color:black;font-size:12px;">'+getLabelFromMap('95', helpMap,'Atributos de Archivo')+'</span>';
	if(_record == undefined ){
		titulo=getLabelFromMap('AgrAtriArchivo', helpMap,'Agregar Atributos de Archivo');
	  }else{
		titulo=getLabelFromMap('EditAtriArchivo', helpMap,'Editar Atributos de Archivo');
	  }


  function guardarAtributosFax(_code){
  	var _url;
  	if(_code==0){
  	   
  		_url= _ACTION_GUARDAR_ATRIBUTOS_FAX
  	}else
  	{
  		_url= _ACTION_ACTUALIZAR_ATRIBUTOS_FAX
  	}
    
    //alert(form_edit.findById("cdtipoarId").getValue());
    var conn = new Ext.data.Connection();
	                conn.request({
				    	url: _url,
				    	method: 'POST',
				    	params: {//cdtipoar, cdatribu, dsAtribu, swFormat, nmLmax, nmLmin, swObliga, otTabVal, status);
				    				
				    			    cdtipoar:form_edit.findById("cdtipoarId").getValue(),
				    			    cdatribu:(_record)?_record.get('cdatribu'):form_edit.findById("cdatribuId").getValue(),				    			    
				    			   	dsAtribu:Ext.getCmp("dsarchivoId").getValue(),
				    				nmLmax:form_edit.findById("nmLmaxId").getValue(),
				    			    //Ext.getCmp('nmLmaxId'),  
						    		nmLmin:form_edit.findById("nmLminId").getValue(),
						    		
						    		//swFormat:_record.get('formato'),			    		    	  
				    				swFormat:(_record)?_record.get("swFormat"):Ext.getCmp("cmbFormatoId").getValue(),
						    	    otTabVal:form_edit.findById("cmbTablaId").getValue(),
						    		status:form_edit.findById("acCmbStatusId").getValue(),
						    		
						    		swObliga:Ext.getCmp("swObliga").getValue()
						    		//_record.get('swObligaId')
						    		
				    			},
	 						waitMsg : getLabelFromMap('400021', helpMap,'Espere por favor ...'),
                   		     callback: function (options, success, response) {
                   		     
      //  if (Ext.util.JSON.decode(response.responseText).success == false) {
            //Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), 'No se pudo guardar los datos');
        	
        	
              if (Ext.util.JSON.decode(response.responseText).success == false) 
                              {
                               Ext.Msg.alert('Error', Ext.util.JSON.decode(response.responseText).errorMessages[0]);
        	
             //Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('40006', helpMap,'No se pudo guardar'));
        }else {
              Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400068', helpMap,'Los datos se guardaron con Exito'),function(){reloadGrid()});
              
            // Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con exito',function(){reloadGrid()});
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
                	/* if (form_edit.form.isValid()) {
                      	guardarYAgregar = true;
                        guardarTareaCatBo();*/
                	
                	if (form_edit.form.isValid()){
                	var code=0;
                	if(_record){code=1};
                   guardarAtributosFax(code);}
                   
                   
                   
                   else{
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                   } 
                   
                 //  code=code+1;
                   /*
                    if (form_edit.form.isValid()) {
                        form_edit.form.submit( {
                            url : _ACTION_GUARDAR_ATRIBUTOS_FAX,
                            success : function(from, action) {
                                Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), action.result.actionMessages[0], function(){reloadGrid();});
                                window.close();
                            },
                            failure : function(form, action) {
                                Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), action.result.errorMessages[0]);
                           },
                            waitMsg : getLabelFromMap('400027', helpMap,'Guardando datos...')
                        });
                    } else {
                       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
               */ }
            },
            {
				text:getLabelFromMap('edtSeccionButtonCancelar', helpMap,'Cancelar'),
				tooltip:getToolTipFromMap('edtSeccionButtonCancelar', helpMap, 'Cancela la operaci&oacute;n de guardar'),
                handler : function() {
                    window.close();
                }
            }]
	});
	
	//tablas_store.load();
	if (_record != null){
		
		 formato_store.load({
    			//params:{cdTabla:'CTIPOARC'},
    			callback:function(){
    					Ext.getCmp("cmbFormatoId").setValue(_record.get("swFormat"));
    					}    			
    		}); 
     storeStatus.load({
    			//params:{cdTabla:'CTIPOARC'},
    			callback:function(){
    					//Ext.getCmp("cmbTablaId").setValue(_record.get("dsTabla"));
    					Ext.getCmp("acCmbStatusId").setValue(_record.get("status"));
    					}    			
    		});  
   	 tablas_store.load({
    			//params:{cdTabla:'CTIPOARC'},
    			callback:function(){
    					//Ext.getCmp("acCmbStatusId").setValue(_record.get("status"));
    				//alert(_record.get("dsTabla"));
    					Ext.getCmp("cmbTablaId").setValue(_record.get("otTabVal"));
    					}    			
    		});
		
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
                
                              	form_edit.findById('cmbFormatoId').setValue('LOCO');
                              }
                             }})
                   
                   
                   } //else {
                       //form_edit.findById('idCdBloque').setDisabled(false);
                   //}
               //}
       });
} else{tablas_store.load();formato_store.load(); storeStatus.load();}
	
    //formato_store.load();
    //tablas_store.load();
   // storeStatus.load();
    window.show();
    //form_edit.submit();    
    
   if(_record){
       Ext.getCmp("dsarchivoId").setValue(_record.get("dsAtribu"));	
       Ext.getCmp("nmLminId").setValue(_record.get("nmLmin"));	
       Ext.getCmp("nmLmaxId").setValue(_record.get("nmLmax"));	
       Ext.getCmp("swObliga").setValue(_record.get("swObligaId"));	
       	
      // }
       
   /*    formato_store.load({
    			//params:{cdTabla:'CTIPOARC'},
    			callback:function(){
    					Ext.getCmp("cmbFormatoId").setValue(_record.get("formato"));
    					}    			
    		}); 
     storeStatus.load({
    			//params:{cdTabla:'CTIPOARC'},
    			callback:function(){
    					//Ext.getCmp("cmbTablaId").setValue(_record.get("dsTabla"));
    					Ext.getCmp("acCmbStatusId").setValue(_record.get("status"));
    					}    			
    		});  
   	 tablas_store.load({
    			//params:{cdTabla:'CTIPOARC'},
    			callback:function(){
    					//Ext.getCmp("acCmbStatusId").setValue(_record.get("status"));
    				alert(_record.get("dsTabla"));
    					Ext.getCmp("cmbTablaId").setValue(_record.get("dsTabla"));
    					}    			
    		});*/
    		  	
    		}
}


