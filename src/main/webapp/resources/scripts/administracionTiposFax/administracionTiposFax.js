Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";

 var swObliga = new Ext.grid.CheckColumn({
      id:'swObligaId',
      header: getLabelFromMap('swObligaId',helpMap,'Obligatorio'),
      tooltip: getToolTipFromMap('swObligaId', helpMap,'Obligatorio'),
      hasHelpIcon:getHelpIconFromMap('swObligaId',helpMap),								 
      Ayuda: getHelpTextFromMap('swObligaId',helpMap),
      
      //header: "No Requerida",
      dataIndex: 'swObliga',
      align: 'center',
      sortable: true,
      width: 80
    });


    /********* Comienza el form ********************************/
    var el_form = new Ext.FormPanel ({
    		id: 'el_form',
    		//el:'formBusqueda',
            renderTo: 'formBusqueda',
	  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_form', helpMap,'Administraci&oacute;n Tipos de Archivos')+'</span>',
            url : _ACTION_BUSCAR_ATRIBUTOS_FAX,
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            buttonAlign: "center",
            labelAlign: 'right',
            waitMsgTarget : true,
            width:500,

			 items:[{
			 	layout:'form',
			 	border: false,
			        items:[{
						   layout:'column',
						   //frame:true,
						   bodyStyle:'background: white',
			        	   html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
						   items:[{
			     		        layout:'form',
							    border:false,
							    columnWidth: 1,
						   		items:[        			
					                   {
					                   xtype: 'textfield', 
					                   fieldLabel: getLabelFromMap('dsArchivo', helpMap,'Tipo de Archivo'), 
					 			       tooltip: getToolTipFromMap('dsArchivo', helpMap, 'Tipo de Archivo'),  
                                       hasHelpIcon:getHelpIconFromMap('dsArchivo',helpMap),								 
                                       Ayuda: getHelpTextFromMap('dsArchivo',helpMap),
					 			       
					                   id: 'dsArchivo', 
					                   name: 'dsArchivo',
					                   width:250,
					                   disabled:true
						                }
						               ]
				               }],
				            
			                
			           buttonAlign: 'center',
			           bodyStyle:'background: white'
				
			           /*buttons: [
			                     {
			  					   text:getLabelFromMap('busquedaAdmTipoFaxButtonBuscar', helpMap,'Buscar'),
								   tooltip:getToolTipFromMap('busquedaAdmTipoFaxButtonBuscar', helpMap,'Busca Tipo de Archivos'),
			                      handler: function () {
			                                      if (el_form.form.isValid()) {
			                                          if (grilla != null) {
			                                              reloadGrid();
			                                          }else {
			                                              createGrid();
			                                          		}
			                                      							}		
			                        					}
			                     },
			                     {
			  					  text:getLabelFromMap('cancelarAdmTipoFaxButtonBuscar', helpMap,'Cancelar'),
								  tooltip:getToolTipFromMap('cancelarAdmTipoFaxButtonBuscar', helpMap,'Cancela la b&uacute;squeda'),
			                     handler: function() {el_form.getForm().reset();}}
			                   ]*/
          }]
 	}]

            //se definen los campos del formulario
    });
    /********* Fin del form ************************************/

	/********* Comienzo del grid *****************************/
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
	    			header: getLabelFromMap('cdtipoarId',helpMap,'Atributo'),
			        tooltip: getToolTipFromMap('cdtipoarId', helpMap, 'Atributo'),	
                    hasHelpIcon:getHelpIconFromMap('cdtipoarId',helpMap),								 
                    Ayuda: getHelpTextFromMap('cdtipoarId',helpMap),
		           	dataIndex: 'cdtipoar',
		           	id:'cdtipoarId',
		           	sortable: true,
		           	width: 60
	    		},
	    		{
				   	header: "",
		           	dataIndex: 'dsAtribu',
		           	sortable: true,
		           	width: 90
	        	},
	        	{ 
	    			header: getLabelFromMap('cmFormato',helpMap,'Formato'),
			        tooltip: getToolTipFromMap('cmFormato', helpMap, 'Formato'),
                    hasHelpIcon:getHelpIconFromMap('cmFormato',helpMap),								 
                    Ayuda: getHelpTextFromMap('cmFormato',helpMap),
			        
	    			dataIndex: 'formato',
	    			sortable: true,
		           	width: 90
	    		},
	    		{
				   	header: getLabelFromMap('cmNmLmin',helpMap,'M&iacute;nimo'),
			        tooltip: getToolTipFromMap('cmNmLmin', helpMap, 'M&iacute;nimo'),	
                    hasHelpIcon:getHelpIconFromMap('cmNmLmin',helpMap),								 
                    Ayuda: getHelpTextFromMap('cmNmLmin',helpMap),
			        
		           	dataIndex: 'nmLmin',
		           	id:'nmLminIden',
		           	sortable: true,
		           	width: 50
	        	},
	        	{
				   	header: getLabelFromMap('cmNmLmax',helpMap,'M&aacute;ximo'),
			        tooltip: getToolTipFromMap('cmDsBloqueSeccions', helpMap, 'M&aacute;ximo'),	
                    hasHelpIcon:getHelpIconFromMap('cmDsBloqueSeccions',helpMap),								 
                    Ayuda: getHelpTextFromMap('cmDsBloqueSeccions',helpMap),
		           	dataIndex: 'nmLmax',
		           	sortable: true,
		           	width: 50
	        	},
	        	{
				   	header: getLabelFromMap('cmDsTabla',helpMap,'Tabla'),
			        tooltip: getToolTipFromMap('cmDsTabla', helpMap, 'Tabla'),
                    hasHelpIcon:getHelpIconFromMap('cmDsTabla',helpMap),								 
                    Ayuda: getHelpTextFromMap('cmDsTabla',helpMap),
			        
		           	dataIndex: 'dsTabla',
		           	sortable: true,
		           	width: 120
	        	},
	        	swObliga,	        	
	    		{
				   	header: getLabelFromMap('cmStatus',helpMap,'Estado'),
			        tooltip: getToolTipFromMap('cmStatus', helpMap, 'Estado'),
                    hasHelpIcon:getHelpIconFromMap('cmStatus',helpMap),								 
                    Ayuda: getHelpTextFromMap('cmStatus',helpMap),
		           	dataIndex: 'status',
		           	sortable: true,
		           	width: 100
	        	},
	        	{
	    			dataIndex: 'cdatribu',
	    			id:'cdatribuId',
	    			hidden: true
	    		},
	    		{
		           	dataIndex: 'swFormat',
		           	hidden: true
	        	},
	        	{
	    			dataIndex: 'otTabVal',
	    			hidden: true
	    		}
	           	]);

		//Crea el Store
		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_ATRIBUTOS_FAX,
						waitMsg: 'Espere por favor....'
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'MAtributosFaxList',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
			        {name: 'cdtipoar', type: 'string', mapping: 'cdtipoar'},
                    {name: 'dsArchivo', type: 'string', mapping: 'dsArchivo'},			        
			        {name: 'cdatribu', type: 'string', mapping: 'cdatribu'},
			        {name: 'dsAtribu', type: 'string', mapping: 'dsAtribu'},
			        {name: 'swFormat',  type: 'string',  mapping:'swFormat'},
			        {name: 'nmLmin',  type: 'string',  mapping:'nmLmin'},
			        {name: 'nmLmax',  type: 'string',  mapping:'nmLmax'},
			        {name: 'dsTabla',  type: 'string',  mapping:'dsTabla'},
			        {name: 'swObliga',  type: 'string',  mapping:'swObliga'},
			        {name: 'otTabVal',  type: 'string',  mapping:'otTabVal'},
			        {name: 'status',  type: 'string',  mapping:'status'},
			        {name: 'formato',  type: 'string',  mapping:'formato'}
					])
		        });
		        
				return store;
		 	}
		//Fin Crea el Store
	var grilla;

	function createGrid(){
		grilla= new Ext.grid.GridPanel({
			id: 'grilla',
	        el:'gridSecciones',
	        store:crearGridStore(),
	        buttonAlign:'center',
	        bodyStyle:'background: white',
			border:true,
			plugins: [swObliga],
			title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
		    cm: cm,
	        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
			buttons:[
	        		 {
	      			  text:getLabelFromMap('gridAdmTipFaxButtonAgregar', helpMap,'Agregar'),
            		  tooltip:getToolTipFromMap('gridAdmTipFaxButtonAgregar', helpMap,'Agrega un nuevo Archivo'),			        			
	            	  handler:function(){insertarOActualizarAtributos(null);
	            		}
	            	 },
	            	 {text:getLabelFromMap('gridAdmTipFaxButtonEditar', helpMap,'Editar'),
	           		  tooltip:getToolTipFromMap('gridAdmTipFaxButtonEditar', helpMap,'Edita los Atributos de un Archivo'),			        			
	            		handler:function(){
		            			if(getSelectedRecord()!=null){
		                			insertarOActualizarAtributos(getSelectedRecord());
		                		}
		                		else{
		                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
		                		}
	                	}
	            	 },
	                 {
	      				text:getLabelFromMap('gridSeccionesButtonBorrar', helpMap,'Eliminar'),
	           			tooltip:getToolTipFromMap('gridSeccionesButtonBorrar', helpMap,'Elimina un Archivo'),			        			
	                	handler:function(){
	                			if(getSelectedRecord() != null){
	                					borrar(getSelectedRecord());
	                			}
	                			else{
	                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	                			}
	                		}
	            	},
	            	
	            		            	
	            	{
	      				text:getLabelFromMap('gridSeccionesButtonExportar', helpMap,'Exportar'),
	           			tooltip:getToolTipFromMap('gridSeccionesButtonExportar', helpMap,'Exporta el contenido del grid'),			        			
	                	handler:function(){
                                 var url = _ACTION_EXPORTAR_ATRIBUTOS_FAX +'?cdtipoar='+CDTIPOAR;
               	 	          showExportDialog( url );
                              }
	            	},
	            	{
                    text:getLabelFromMap('confTipEndBtnBack',helpMap,'Regresar'),
                    tooltip: getToolTipFromMap('confTipEndBtnBack',helpMap,'Regresa a la pantalla anterior'),
                    handler:function (){
                    window.location = _IR_A_ADMINISTRACION_TIPO_ARCHIVO;}
	                }
	            	/*{
	      				text:getLabelFromMap('gridSeccionesButtonRegresar', helpMap,'Regresar'),
	           			tooltip:getToolTipFromMap('gridSeccionesButtonRegresar', helpMap,'Regresa a la pantalla anterior')		        			
	            	}*/
	            	],
	    	width:500,
	    	frame:true,
			height:320,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			stripeRows: true,
			collapsible: true,
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: store,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	    grilla.render()
	}

	/********* Fin del grid **********************************/

	//Funcion para obtener el registro seleccionado en la grilla
    function getSelectedRecord(){
             var m = grilla.getSelections();
             if (m.length == 1 ) {
                return m[0];
             }
        }

    function agregar(){
    }
    
	//Borra el registro seleccionado
	function borrar(record){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
         		if (btn == "yes"){
         			var _params = {
         					cdtipoar: record.get('cdtipoar'),
                		 	cdatribu: record.get('cdatribu')
         					
         			};
         			execConnection (_ACTION_BORRAR_ATRIBUTOS_FAX, _params, cbkBorrar);
         			reloadGrid();
				}
		})

  	}
  	
	function cbkBorrar (_success, _message) {
		if (!_success) {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
		}else {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function() {reloadGrid();});
		}
	}
    //Muestra los componentes en pantalla
	el_form.render();
	createGrid();
	Ext.getCmp("dsArchivo").setValue(DSARCHIVO);
	reloadGrid();
	//Fin Muestra los componentes en pantalla    

});

function reloadGrid(){
	var _params = {
			        cdtipoar:CDTIPOAR 
			        // dsArchivo: Ext.getCmp('el_form').form.findField('dsArchivo').getValue()
	              };
    reloadComponentStore(Ext.getCmp('grilla'), _params, cbkReload);
}
function cbkReload (_r, _o, _success, _store) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll();
	}
}

/*************  Definición de Plugin para Checkbox en grillas ********************************************/
		Ext.grid.CheckColumn = function(config){
		    Ext.apply(this, config);
		    if(!this.id){
		        this.id = Ext.id();
		    }
		    this.renderer = this.renderer.createDelegate(this);
		};

		Ext.grid.CheckColumn.prototype ={
		    init : function(grid){
		        this.grid = grid;
		        this.grid.on('render', function(){
		            var view = this.grid.getView();
		            view.mainBody.on('mousedown', this.onMouseDown, this);
		        }, this);
		    },
		
		    onMouseDown : function(e, t){
		        if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
		            e.stopEvent();
		            var index = this.grid.getView().findRowIndex(t);
		            var record = this.grid.store.getAt(index);
		            record.set(this.dataIndex, !record.data[this.dataIndex]);

		            /*if (this.dataIndex == 'fgNoRequ') {
		            	if (record.data['fgNoRequ']) record.set('fgComple', false);
		            	if (record.data['fgNoRequ']) record.set('fgPendiente', false);
		            }*/
		            this.grid.getSelectionModel().selectRow(index); //Selecciona la fila completa
		        }
		    },
			onClick: function (e, t) {
			},
		    renderer : function(v, p, record){
		        p.css += ' x-grid3-check-col-td'; 
		        return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
		    }
		};
/*************  Fin Definición de Plugin para Checkbox en grillas ********************************************/