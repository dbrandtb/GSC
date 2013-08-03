function agregar(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";			



	var FormPanel = new Ext.FormPanel ({
	        id: 'acFormPanelId',	        
	       // title: '<span style="color:black;font-size:12px;">Alta de Atributos</span>',
	        iconCls:'logo',
	        store:storeAtributos,
	        bodyStyle:'background: white',
	        buttonAlign: "center",
	        labelAlign: 'right',
	        frame:true,
	        width: 590,
	       // height: 150,
	        autoHeight:true,
	        layout: 'table',
            layoutConfig: { columns: 3, columnWidth: .33},
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
					          allowBlank:false,
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
					          allowBlank:false,
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
	                        typeAhead: true,
	                        mode: 'local',
	                        triggerAction: 'all',
	                        width: 160,
	                        allowBlank: false,
	                        emptyText:'Seleccione Formato...',
	                        selectOnFocus:true
	                        //forceSelection:true
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
 *   fin forrm
 */
    
	var window = new Ext.Window ({
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('105',helpMap,'Alta de Atributos')+'</span>',
			width: 605,
			autoHeight: true,
			//bodyStyle : 'padding:5px 5px 0',
        	bodyStyle:'padding:5px;',
        	bodyStyle:'background: white',
        	buttonAlign:'center',
        	items: FormPanel,
            //se definen los botones del formulario
            buttons : [ {

                text : getLabelFromMap('agAtriBtnSave',helpMap,'Guardar'),
				tooltip:getToolTipFromMap('agAtriBtnSave',helpMap,'Guardar'),
                disabled : false,

                handler : function() {
                    if (FormPanel.form.isValid()) {
							guardarAtributos();
							window.close();
					
                      }else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
                      }
                     }
                    },
            	    {
                     text : getLabelFromMap('agAtriBtnBack',helpMap,'Regresar'),
	   			     tooltip:getToolTipFromMap('agAtriBtnBack',helpMap,'Regresa a la pantalla anterior'),
                     handler : function() {
                     window.close();
                		}
                    }
                  ]

	});
    
    window.show();
    storeAtributos.load({
    	params: {
    				cdTabla: 'ATRIARCH'
    			}
    });
    storeStatus.load({
    	params: {
    				cdTabla: 'CATBOSTATUS'
    			}
    });
    
    
    
function guardarAtributos()
{
        var params = {
                     cdAtribu: FormPanel.findById('acTFNomAtriId').getValue(),
                     swFormat: FormPanel.findById('acCmbFormatoId').getValue(),
                     nmLmin: FormPanel.findById('acTFMinId').getValue(),  
                     nmLmax: FormPanel.findById('acTMax').getValue(), 
                     swObliga: FormPanel.findById('acObli').getValue(),
                     cdTipoar: FormPanel.findById('acTFTipoAtriId').getValue(),
                     Status: FormPanel.findById('acCmbStatusId').getValue()
             };
             
        execConnection (_ACTION_AGREGAR_ATRIBUTOS_ARCHIVOS, params, cbkGuardar)
}

function cbkGuardar (success, message) {
    if (success) {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message,function(){reloadGrid()})
    } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), message)
    }
}
}

