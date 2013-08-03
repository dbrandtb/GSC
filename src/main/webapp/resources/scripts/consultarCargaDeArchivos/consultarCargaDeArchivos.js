Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";


    
 var fechaCnclcnDe=	new Ext.form.DateField({
	name: 'fechaCnclcnDe',
	id: 'fechaCnclcnDeId',
	fieldLabel: getLabelFromMap('fechaCnclcnDeId',helpMap,'Fecha Desde'),
	tooltip: getToolTipFromMap('fechaCnclcnDeId',helpMap,'Fecha Desde'),
	hasHelpIcon:getHelpIconFromMap('fechaCnclcnDeId',helpMap),
    Ayuda: getHelpTextFromMap('fechaCnclcnDeId',helpMap),
	width:110,
	format: 'd/m/Y'
	//altFormats : 'm/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g'
});

var fechaCnclcnA = new Ext.form.DateField({
	name: 'fechaCnclcnA',
	id: 'fechaCnclcnAId',
	fieldLabel: getLabelFromMap('fechaCnclcnAId',helpMap,'Fecha Hasta'),
	tooltip: getToolTipFromMap('fechaCnclcnAId',helpMap,'Fecha Hasta'),
	hasHelpIcon:getHelpIconFromMap('fechaCnclcnAId',helpMap),
    Ayuda: getHelpTextFromMap('fechaCnclcnAId',helpMap),
    width:110,
	format: 'd/m/Y'
	//altFormats : 'm/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g'
});

var	storeComboCargaArchivos2 = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_TIPO_ARCHIVO}),
			reader: new Ext.data.JsonReader(
				{
					root:'comboCargaArchivos',
					totalProperty: 'totalCount',
					id: 'descripcion',
					successProperty : '@success'
				},
				[
					{name: 'codigo',mapping:'codigo', type: 'string'},
					{name: 'descripcion',mapping:'descripcion', type: 'string'},
					{name: 'directorioAsociado',mapping:'directorioAsociado', type: 'string', hidden: true} 
				]
				),
				//remoteSort: true,
				sortInfo: {field :'descripcion', direction :'ASC'}
		})
		
var codigoDirectorio;

var opts2 = {
allowSameDay: true,
invalidText: String.format('Debe ingresar esta fecha')
};

var comboTipoArchivo2 = new Ext.form.ComboBox({
	    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	    store:storeComboCargaArchivos2,
	    id:'tipoArchivo2',
	    name: 'tipoArchivo2',
	    displayField:'descripcion',
	    valueField:'codigo',
	    hiddenName: 'codigoh',
	    typeAhead: true,
	    //anchor:'97%',
	    width:150,
	    allowBlank : false,
	    mode: 'local',
	    emptyText:'Seleccione Tipo Archivo',
	    triggerAction: 'all',
	    fieldLabel: getLabelFromMap('tipoArchivo2',helpMap,'Tipo de Archivo'),
	    tooltip:getToolTipFromMap('tipoArchivo2',helpMap,'Tipo de Archivo'),
	    hasHelpIcon:getHelpIconFromMap('tipoArchivo2',helpMap),
        Ayuda: getHelpTextFromMap('tipoArchivo2',helpMap),
	    labelAlign:'right',
	    selectOnFocus:true,
	    forceSelection:true,
	    onSelect: function(record){
	    				this.setValue(record.get("codigo"));
		                this.collapse();
		                codigoDirectorio=record.get("directorioAsociado");
		                //alert('direc ' + record.get("codigo"));
		                //alert('descripcion ' + record.get("descripcion"));
		                //alert('directorioAsociado ' + record.get("directorioAsociado"));
		                //alert('codigoDirectorio'+ codigoDirectorio);
		                
	    }
	    
       });	



    /********* Comienza el form ********************************/
    var el_form = new Ext.FormPanel ({
    		id: 'el_formAdmTiposFaxId',
    		//el:'formBusqueda',
            renderTo: 'formBusqueda',
	  		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_formAdmTiposFaxId', helpMap,'Carga de Archivos')+'</span>',
            //url : _ACTION_BUSCAR_ATRIBUTOS_FAX,
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
			     		        //width:500,
							    border:false,
							    columnWidth: 1,
						   		items:[        			
					                   comboTipoArchivo2,
						                fechaCnclcnDe,
						                fechaCnclcnA
						               ]
				               }],
				            
			                
			           buttonAlign: 'center',
			           bodyStyle:'background: white',
				
			           buttons: [
			                     {
			  					   text:getLabelFromMap('busquedaCargaArchivosButtonBuscar', helpMap,'Buscar'),
								   tooltip:getToolTipFromMap('busquedaCargaArchivosButtonBuscar', helpMap,'Busca Tipo de Archivos'),
			                      handler: function () {
			     
			                                      if (el_form.form.isValid() ){
			                                      	  if (fechaCnclcnDe.getValue() != "" && fechaCnclcnA.getValue() != "") {
			                                         	 if (fechaCnclcnDe.getValue() > fechaCnclcnA.getValue()) {
			                                         		Ext.MessageBox.alert(getLabelFromMap('400010', helpMap,'Error'), getLabelFromMap('400083', helpMap, 'La fecha de inicio debe ser menor o igual que la de fin'));
			                                         		return;
														 }
		                                         	 }
			                                    
			                                           if (grilla != null) {
			                                              
			                                              reloadGrid();
			                                          }else {
			                                          		
			                                              createGrid();
			                                          		}
			                       
			                                      							}		
			                        					}
			                     },
			                     {
			  					  text:getLabelFromMap('busquedaCargaArchivosButtonCancelar', helpMap,'Cancelar'),
								  tooltip:getToolTipFromMap('busquedaCargaArchivosButtonCancelar', helpMap,'Cancela la b&uacute;squeda'),
			                     handler: function() {el_form.getForm().reset();}
			                     }
			                     
			                   ]
          }]
 	}]

            //se definen los campos del formulario
    });
    /********* Fin del form ************************************/
    
    storeComboCargaArchivos2.load();

	/********* Comienzo del grid *****************************/
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{
				   	dataIndex: 'nombre',
				   	header: getLabelFromMap('cmNombreArchivo',helpMap,'Nombre Archivo'),
			        tooltip: getToolTipFromMap('cmNombreArchivo', helpMap, 'Nombre Archivo'),		           	           	
		           	sortable: true,
		           	width: 400
	        	},{				   			           	
		           	dataIndex: 'directorio',
		           	hidden: true
	        	},{
				    dataIndex: 'ultimaModificacion',
	        		header: getLabelFromMap('cmUltimaModificacion',helpMap,'&Uacute;ltima Modificaci&oacute;n'),
			        tooltip: getToolTipFromMap('cmUltimaModificacion', helpMap, ' &Uacute;ltima Modificaci&oacute;n'),		           		           	
		           	sortable: true,
		            //format:'d/m/Y',
		           	//altFormats : 'm/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d|Y-m-d H:i:s.g',
		           	renderer: function(val) {
		           		try{
		           			return Ext.util.Format.date(val,'d/m/Y');
            			   }
              			catch(e)
              			{	return val;
              			}
                	},
		           	width: 150
	        	}
	           	]);

		//Crea el Store
		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_ARCHIVOS,
						waitMsg: 'Espere por favor....'
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'MCargaArchivosList',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
			        {name: 'nombre', type: 'string', mapping: 'nombre'},
			        {name: 'directorio', type: 'string', mapping: 'directorio'},
			        {name: 'ultimaModificacion',  type: 'string',  mapping:'ultimaModificacion'}
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
	     	title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',			
	        cm: cm,
	        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
			buttons:[
	        		{
	      				text:getLabelFromMap('gridCargaArchivosButtonBuscar', helpMap,'Agregar'),
	           			tooltip:getToolTipFromMap('gridCargaArchivosButtonBuscar', helpMap,'Agrega un nuevo Archivo'),			        			
	            		handler:function(){anexarArchivoDigitalizado();
	            		}
	            	},
	            	{
	      				text:getLabelFromMap('gridCargaArchivosButtonVerLog', helpMap,'Ver Log'),
	           			tooltip:getToolTipFromMap('gridCargaArchivosButtonVerLog', helpMap,'Ver Log seleccionado'),			        			
	            		handler:function(){
	            		if(getSelectedRecord() != null){
	                					bajar(getSelectedRecord());
	                			}
	                	else{
	                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	                		}
		            			
	                	}
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
					pageSize:20,
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
    
    function bajar(record){
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400082', helpMap,'Desea descargar el archivo seleccionado'),function(btn)
			{
         		if (btn == "yes"){
         			var _params = {
         					         					
         			};
         			
         			//alert("Estamos en la bajada del archivo:  " + record.get('nombre'));
         			var submitForm = getNewSubmitForm();

 					createNewFormElement(submitForm, "pathCompletoArchivoBajada", record.get('nombre'));  //escape( record.get('nombre'))
					 //createNewFormElement(submitForm, "nombredelparam2", "somevalue");
					 submitForm.action= _ACTION_BAJAR_ARCHIVOS;
					 //envio sincronico
					 submitForm.submit();
					 
				}
		})

  	}
    
//helper function to create the form
function getNewSubmitForm(){
 var submitForm = document.createElement("FORM");
 document.body.appendChild(submitForm);
 submitForm.method = "POST";
 return submitForm;
}

//helper function to add elements to the form
function createNewFormElement(inputForm, elementName, elementValue){

 var newElement = document.createElement("input");
 newElement.type='hidden';
 newElement.name = elementName;
 newElement.value = elementValue;
 inputForm.appendChild(newElement);
 return newElement;
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
	//Fin Muestra los componentes en pantalla    
	
	function reloadGrid(){
	var _params = {
			directorio: codigoDirectorio,//Ext.getCmp('el_form').form.findField('dsArchivo').getValue(),
			fechaDesde: Ext.getCmp('el_formAdmTiposFaxId').form.findField('fechaCnclcnDe').getRawValue(),
			fechaHasta: Ext.getCmp('el_formAdmTiposFaxId').form.findField('fechaCnclcnA').getRawValue()
	};
    reloadComponentStore(Ext.getCmp('grilla'), _params, cbkReload);
	}
	


});


function cbkReload (_r, _o, _success, _store) {
	if (!_success) {	
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll();
	}
	
}

