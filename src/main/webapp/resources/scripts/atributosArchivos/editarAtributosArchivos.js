function editar(record){

//se agrego

    var form_editar_reg = new Ext.data.JsonReader({
						root: 'listaAtributos',
						totalProperty: 'totalCount',
						successProperty: '@success'
						}, [
            			{name: 'cdAtribu', type: 'string'},            			
            			{name: 'cdTipoar', type: 'string'},
            			{name: 'swFormat', type: 'string'},
            			{name: 'nmLmin', type: 'string'},
            			{name: 'nmLmax', type: 'string'},
            			{name: 'swObliga', type: 'string'},
						]
		);

var storeAtributos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_FORMATO}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboFormatos'},
            		[
            			{name: 'codigo', type: 'string'},
            			{name: 'descripcion', type: 'string'},
            		]
            )
        });


 var storeStatus = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_STATUS}),
            reader: new Ext.data.JsonReader(
            		{root: 'comboTareaEstatus'},
            		[{name: 'codigo', type: 'string'},    //status
            		 {name: 'descripcion', type: 'string'}
            		])
        }); 
        
 // hasta aqui       


	var form_edit = new Ext.FormPanel ({
	        id: 'acFormPanelId',	        
	        iconCls:'logo',
	       // store:storeAtributos,
	        url:_ACTION_OBTENER_REG_ATRIBUTOS_ARCHIVOS,         //_ACTION_BUSCAR_ATRIBUTOS_ARCHIVOS,
	        bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,
	        width: 590,
	       // height: 150,
	        autoHeight:true,
            bodyStyle: 'background:white',
	        layout: 'table',
            layoutConfig: { columns: 3, columnWidth: .33},
            reader: form_editar_reg,             
            items:[
            		{
            		//html: '<span class="x-form-item" style="font-weight:bold">Datos Atributos</span>',
            		colspan:3
            		},
            		{            		
           			layout: 'form',
           			labelWidth: 50,           			            		
           			items: [
           				    {
           				  	  xtype: 'textfield',
           					  id: 'acTFNomAtriId',
					          fieldLabel: getLabelFromMap('acTFNomAtriId',helpMap,'Atributo'),
					          tooltip:getToolTipFromMap('acTFNomCliId',helpMap,'Atributo'), 	        
					          name: 'cdAtribu',
					       // disabled: true,
					          width: 100
					        }
           				   ]
            		},
            		{            		
           			layout: 'form',
           			labelWidth: 60,           			            		
           			items: [
           				    {
           				  	  xtype: 'textfield',
           					  id: 'acTFTipoAtriId',
					          fieldLabel: getLabelFromMap('acTFTipoAtriId',helpMap,'Tipo'),
					          tooltip:getToolTipFromMap('acTFTipoAtriId',helpMap,'Tipo Atributo'), 	        
					          name: 'cdTipoar',
					       // disabled: true,
					          width: 100
					        }
           				   ]
            		},
            		{
            		layout: 'form',
            		labelWidth: 60,
            		items:[
           				   {
           				    xtype: 'combo',
           				    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                        id:'acCmbFormatoId',
	                        fieldLabel: getLabelFromMap('acCmbFormatoId',helpMap,'Formato'),
	           		  	    tooltip:getToolTipFromMap('acCmbFormatoId',helpMap,'Formato'),
	                        store: storeAtributos,
	                        displayField:'descripcion',
	                        valueField:'codigo',
                    	    hiddenName: 'codigo',
	                        typeAhead: true,
	                        mode: 'local',
	                        triggerAction: 'all',
	                        width: 180,
	                        allowBlank: false,
	                        emptyText:'Seleccione Formato...',
	                        selectOnFocus:true,
	                        forceSelection:true
	                        }
           				  ]
            		},
//					{
//					  layout: 'form'
//					},
            		{            		
           			  layout: 'form',  
           			  labelWidth: 50,       			            		
           			  items: [
           				      {
           					   xtype:'textfield',
		            		   id: 'acTFMinId',
					           fieldLabel: getLabelFromMap('acTFMinId',helpMap,'M&iacute;nimo'),
					           tooltip:getToolTipFromMap('acTFMinId',helpMap,'M&iacute;nimo'), 	        
					           name: 'nmLmin',
					        // disabled: true,
					           width: 100
					          }
           				     ]
            		},
            		{
            		  layout: 'form',  
            		  labelWidth: 60,          			
           			  items: [
           				      {
           					   xtype:'textfield',
		            		   id: 'acTMax',
					           fieldLabel: getLabelFromMap('acTMax',helpMap,'M&aacute;ximo'),
					           tooltip:getToolTipFromMap('acTMax',helpMap,'M&aacute;ximo'), 	        
					           name: 'nmLmax',
					        // disabled: true,
					           width: 100           					
					          }
           				     ]
            		},
            		{
            		  layout: 'form',  
            		  labelWidth: 70,          			
           			  items: [
           				      {
           					   xtype: 'checkbox',
           					   id:'acObli',
		                       fieldLabel: getLabelFromMap('acObli',helpMap,'Obligatorio'),
		      			       tooltip:getLabelFromMap('acObli',helpMap, 'Obligatorio'),
					           name:'swObliga',
					           checked:false
					          }
           				     ]
            		},
//					{  layout: 'form'              /***    se utiliza para agregar espacio en la primer columna
//					},
            		{            		
           			  layout: 'form',  
           			  labelWidth: 50,  
           			  colspan:3,        			            		
           			  items: [
           				      {
            				   xtype: 'combo',
            				   tpl: '<tpl for="."><div ext:qtip="{codigo}" class="x-combo-list-item">{descripcion}</div></tpl>',
 	                           id:'acCmbStatusId',
	                           fieldLabel: getLabelFromMap('acCmbStatusId',helpMap,'Status'),
  	            			   tooltip:getToolTipFromMap('acCmbStatusId',helpMap,'Status'),
                               store: storeStatus,
    	                       displayField:'descripcion',
    	                       valueField:'codigo',
    	                       hiddenName: 'codigo',
    	                       typeAhead: true,
    	                       mode: 'local',
    	                       triggerAction: 'all',
    	                       width: 150,
    	                       allowBlank: false, 
	                           emptyText:'Seleccione Estatus...',
	                           selectOnFocus:true,
	                           forceSelection:true
					          }
           				     ]
            		}            		
            	  ]
    });
 /*
 *   fin form
 */
    
	var window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('105',helpMap,'Editar Atributos')+'</span>',
			width: 600,
			autoHeight: true,
			//bodyStyle : 'padding:5px 5px 0',
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: form_edit,
            //se definen los botones del formulario
            buttons : [ {

                text : getLabelFromMap('agAtriBtnSave',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('agAtriBtnSave',helpMap,'Guardar'),
                disabled : false,

                handler : function() {
                    if (form_edit.form.isValid()) {
                        form_edit.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_AGREGAR_ATRIBUTOS_ARCHIVOS,
                            
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
                            //waitMsg : 'guardando datos ...'
                        });
                    } else {
                       Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                    }
                  }
                     },
            	    {
                     text : getLabelFromMap('agAtriBtnBack',helpMap,'Regresar'),
	   			     tooltip:getToolTipFromMap('agAtriBtnBack',helpMap,'Regresa a la pantalla anterior'),
                     handler : function() {
                     window.close()
                		}
                    }
                  ]
	});
    

	if (record != null) {
		storeAtributos.load({
			callback: function () {
					storeStatus.load({
							callback: function () {
								        form_edit.form.load ({
								                params: {codAtribu: record.get('cdAtribu')},
								       			//waitTitle: getLabelFromMap('400017',helpMap,'Espere ...'),
								                //waitMsg : getLabelFromMap('400028',helpMap,'Leyendo datos ...'),
								                success: function() {
								                },
								                failure: function() {
								                	Ext.Msg.alert('', 'Error');
								                }
								        });
							}					
					});									
			}
		});
	}

    window.show();
}

