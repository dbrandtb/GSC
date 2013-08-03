Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";


//VALORES DE INGRESO DE LA BUSQUEDA

var desAtrArch = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('txtFldDesAtrArchAdmArch',helpMap,'Tipo de Archivo'),
        tooltip:getToolTipFromMap('txtFldDesAtrArchAdmArch',helpMap,'Tipo de Archivo a buscar'),
        id: 'desAtrArchId', 
        name: 'desAtrArch',
        allowBlank: true,
        anchor: '80%'
    });


	var formAdmAtrArch = new Ext.FormPanel({
		    id:'formAdmAtrArch',	
			el:'formBusqueda',
            title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Consulta de Administraci&oacute;n Atributos de Archivo')+'</span>',
            iconCls:'logo',
            bodyStyle:'background: white',
            buttonAlign: "center",
            labelAlign: 'right',
            frame:true,   
		    url: _ACTION_BUSCAR_ATRIBUTOS_ARCHIVOS,
		    width: 600,
            height:192,
		    items: [{
       		         layout:'form',
				     border: false,		   
       				 items:[
                     {
        		      bodyStyle:'background: white',
		              labelWidth: 100,
                      layout: 'form',				     
                      title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
				      frame:true,		
  		       	      baseCls: '',
		       	      buttonAlign: "center",
        		        items: [{
        		        	      layout:'column',
		 				          border:false,
		 				          columnWidth: .1,
        		    		      items:[ {
						      	           columnWidth:.9,
                				           layout: 'form',
		                		           border: false,
        		        		           items:[       		        				
        		        			  	          desAtrArch
                                                 ]
								          },
								          {
								           columnWidth:.1,
                				           layout: 'form'
                				          },{
				   				           html: '<span class="x-form-item" style="font-weight:bold"></span><br>'
				   				          }                				          
                				        ]
                			   }],
                		buttons:[{
        						   text:getLabelFromMap('admAtrArchButtonBuscar',helpMap,'Buscar'),
        						   tooltip: getToolTipFromMap('admAtrArchButtonBuscar',helpMap,'Busca Atributos'),
        						   handler: function() {
				               	   if (formAdmAtrArch.form.isValid()) {
                                         if (grid2!=null) {
                                                reloadGrid();
                                               } else {
                                                createGrid();
                                               }
                						} else{
                							Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
										}
									}
        						  },{
        							text:getLabelFromMap('admAtrArchButtonCancelar',helpMap,'Cancelar'),
        							tooltip: getToolTipFromMap('admAtrArchButtonCancelar',helpMap,'Cancelar busqueda de Archivo'),                              
        							handler: function() {
        								formAdmAtrArch.form.reset();
        							    }
        					        }
        					     ]
        			  	  }]
        		  }]	            
        
    });   

// Definicion de las columnas de la grilla

  var cm = new Ext.grid.ColumnModel([
          {
            header: getLabelFromMap('cmdsAtributoAtrc',helpMap,'Atributo'),
            tooltip: getToolTipFromMap('cmdsAtributoAtrDeFax',helpMap,'Atributo'),
            dataIndex: 'cdAtribu',
            width: 80,
            sortable: true
          },
         {
           header: getLabelFromMap('cmdsAtributoAtrc',helpMap,'Tipo de Archivo'),
           tooltip: getToolTipFromMap('cmdsAtributoAtrc',helpMap,'Tipo de Archivo'),
           dataIndex: 'cdTipoar',
           width: 110,
           sortable: true
         },
         {
           header: getLabelFromMap('cmdsAtributoAtrc',helpMap,'Formato'),
           tooltip: getToolTipFromMap('cmdsAtributoAtrc',helpMap,'Formato'),
           dataIndex: 'swFormat',
           width: 80,
           sortable: true
         },
         {
          header: getLabelFromMap('cmdsAtributoAtrc',helpMap,'M&iacute;nimo'),
          tooltip: getToolTipFromMap('cmdsAtributoAtrc',helpMap,'M&iacute;nimo'),
          dataIndex: 'nmLmin',
          width: 70,
          sortable: true
         },
        {
         header: getLabelFromMap('cmdsAtributoAtrc',helpMap,'M&aacute;ximo'),
         tooltip: getToolTipFromMap('cmdsAtributoAtrc',helpMap,'M&iacute;ximo'),
         dataIndex: 'nmLmax',
         width: 70,
         sortable: true
        },
        {
         header: getLabelFromMap('cmdsAtributoAtrc',helpMap,'Obligatorio'),
         tooltip: getToolTipFromMap('cmdsAtributoAtrc',helpMap,'Obligatorio'),
         dataIndex: 'swObliga',
         width: 90,
         sortable: true,
         align: 'center'
        },
       {
         header: getLabelFromMap('cmStatusAtrDeFax',helpMap,'Status'),
         tooltip: getToolTipFromMap('cmStatusAtrDeFax',helpMap,'Status'),
         dataIndex: 'status',
         width: 80,
         sortable: true,
         align: 'center'
       },
       {
         hidden:true, 
         id: 'dsAtribuId', 
         dataIndex: 'dsAtribu'  
       },          
       {
        hidden:true,
        id:'dsArchivoId',
        dataIndex:'dsArchivo'
       }, 
       {
        hidden:true,
        id:  'otTabvalId',
        dataIndex:'otTabval'
       },
       {
        hidden:true,
        id:  'dsTablaId',
        dataIndex:'dsTabla'
       }
	]);
        
        
function createGrid(){
       grid2= new Ext.grid.GridPanel({
        	  id: 'grid2',
              el:'gridBusqueda',
              store:storeGrillaAtributos,
              title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
              reader:jsonGrillaAtributos,
              border:true,
              cm: cm,
              clicksToEdit:1,
	          successProperty: 'success',
              buttonAlign: "center",
              buttons:[
                       {
                         text:getLabelFromMap('gridAtrArchBottonAgregar',helpMap,'Agregar'),
                         tooltip: getToolTipFromMap('gridAtrArchBottonAgregar',helpMap,'Agregar'),
                         handler:function(){agregar()}                                                  ////////// modificado ////////////////
                        // reloadGrid()}
                        },
                      {
                        text:getLabelFromMap('gridAtrarchBottonEditar',helpMap,'Editar'),
                        tooltip: getToolTipFromMap('gridAtrarchBottonEditar',helpMap,'Editar'),
                        handler:function()
                        {
                        //if (getSelectedRecord(grid2) != null) {
                        
						if ((getSelectedKey(grid2, "cdTipoar") != "")&&(getSelectedKey(grid2, "cdAtribu") != "")) 
						 {       
                        		editar(getSelectedRecord(grid2));
                        	//	alert(1);
               			 }else {
               					Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                           }
                	     }
                      },
                       {
                         text:getLabelFromMap('gridAtrArchBottonBorrar',helpMap,'Eliminar'),
                         tooltip: getToolTipFromMap('gridAtrArchBottonBorrar',helpMap,'Eliminar Atributos'),
                         handler:function()
                         {
						  if ((getSelectedKey(grid2, "cdTipoar") != "")&&(getSelectedKey(grid2, "cdAtribu") != "")) {
                        		borrar(getSelectedRecord(grid2));
                          } else {
                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                                }
                        }
                      },
                      {
 	                  text:getLabelFromMap('gridAtrArchBottonExportar', helpMap,'Exportar'),
           			  tooltip:getToolTipFromMap('gridAtrArchBottonExportar', helpMap,'Exportar'),           		
                      handler:function(){
                        var url = _ACTION_EXPORT_ATRIBUTOS_ARCHIVOS;
                	 	showExportDialog( url );
                        }
                      },
                     {
                      text:getLabelFromMap('gridAtrArchBottonRegresar',helpMap,'Regresar'),
                      tooltip: getToolTipFromMap('gridAtrArchBottonRegresar',helpMap,'Volver a la Pantalla Anterior')
                     }
                    ],
             width:600,
             frame:true,
             height:320,
             sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			 stripeRows: true,
			 collapsible: true,
			 bbar: new Ext.PagingToolbar({
					pageSize:20,
					store: storeGrillaAtributos,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })          
        });
   grid2.render()
}
                  
                  
formAdmAtrArch.render();
createGrid();


function borrar(record) {
        if(record.get('cdTipoar') != "" && record.get('cdAtribu') != "")
        {
            Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'),
            getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'), function(btn)
            {
                if (btn == "yes")
                {
                    var _params = {
                              cdTipoar: record.get('cdTipoar'),
                              cdAtribu: record.get('cdAtribu')
                    };
                    execConnection(_ACTION_BORRAR_ATRIBUTOS_ARCHIVOS, _params, cbkConnection);               
                }
            })
        }else{
                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
        }};
 
 


function cbkConnection (_success, _message) {
	if (!_success) {
	 	    Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	     }else {
		  Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid()});
	    }};     
	    
	    
	     
 //});

function reloadGrid(){
	var _params = {
       		dsArchivo: Ext.getCmp('desAtrArchId').getValue()
	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
};

function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]/*"Error"*/);
	}};







//////////////////////////////

 });








