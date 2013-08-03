<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/vTypes/date.js"></script>

<jsp:include page="/resources/scripts/jsp/utilities/tablaApoyo5Claves/AgregarClaves.jsp" flush="true" />
<jsp:include page="/resources/scripts/jsp/utilities/tablaApoyo5Claves/AgregarAtributo.jsp" flush="true" />

<!-- SOURCE CODE -->
<script type="text/javascript">
    
TablasDeApoyo= function(dataStoreTabla5Claves, edita) {
var cmClaves;
var storeClaves;
var gridEditableClaves;
var Plant;
var idFilaClaveAnterior;

var cmAtributos;
var storeAtributos;
var gridEditableAtributos;

var formPanelEditable;
var nombreEditable;
var descripcionEditable;
var numEditable;
var selIndex;
       
var windowConsulta;
 // shorthand alias
var fm = Ext.form;
var PlantGrid1;
var colm;
var grid;
var dataStore;
var store;

var contadorDatoClave=0;

function creaVentanaDeConsulta(numEditaTabla){
/*********************************************************        
Ventana de consulta y asociacion de valores
*********************************************************/
 	
/******************************************
Grid editable de claves   
*******************************************/

    // the column model has information about grid columns
    // dataIndex maps the column to the specific data field in
    // the data store (created below)
    
    function formatDate(value){
        return value ? value.dateFormat('d-M-y') : '';
    };
    
    cmClaves = new Ext.grid.ColumnModel([{
           id:'descripcionClave1',
           header: "Clave1 deshabilitada",
           dataIndex: 'descripcionClave1',
           width: 100,
           editor: new fm.TextField({
               allowBlank: false
           })
        },{
           header: "Clave2 deshabilitada",
           dataIndex: 'descripcionClave2',
           width: 100,
           editor: new fm.TextField({
               allowBlank: false
           })
        },{
           header: "Clave3 deshabilitada",
           dataIndex: 'descripcionClave3',
           width: 100,
           editor: new fm.TextField({
               allowBlank: false
           })
        },{
           header: "Clave4 deshabilitada",
           dataIndex: 'descripcionClave4',
           width: 100,
           editor: new fm.TextField({
               allowBlank: false
           })
        },{
           header: "Clave5 deshabilitada",
           dataIndex: 'descripcionClave5',
           width: 100,
           editor: new fm.TextField({
               allowBlank: false
           })
        },{
           header: "Fecha Desde",
           dataIndex: 'fechaDesde',
           width: 100,
           renderer: formatDate,
           editor: new fm.DateField({
               allowBlank: false,
               id: 'startdt',
		       vtype: 'daterange',
			   endDateField: 'enddt',
    		   format:'d/m/Y'
           })           
        },{
           header: "Fecha Hasta",
           dataIndex: 'fechaHasta',
           width: 100,
           renderer: formatDate,
           editor: new fm.DateField({
               allowBlank: false,               
			   id: 'enddt',
    	       vtype: 'daterange',
			   startDateField: 'startdt',
			   format:'d/m/Y'
           })           
        },{
           header: "Identificador",
           dataIndex: 'identificador',
           width: 100,
           hidden:true
        },{
           header: "Identificador Temporal",
           dataIndex: 'identificadorTemporal',
           width: 100,
           hidden:true
        }
    ]);
	
    // by default columns are sortable
    cmClaves.defaultSortable = false;

    // this could be inline, but we want to define the Plant record
    // type so we can add records dynamically
    Plant = Ext.data.Record.create([

           {name: 'identificador', type: 'string'},
           {name: 'identificadorTemporal', type: 'string'},
           {name: 'descripcionClave1', type: 'string'},
           {name: 'descripcionClave2', type: 'string'},          
           {name: 'descripcionClave3', type: 'string'},   
           {name: 'descripcionClave4', type: 'string'},
           {name: 'descripcionClave5', type: 'string'},
           {name: 'fechaDesde', type:'date', dateFormat: 'd/m/Y'},
           {name: 'fechaHasta', type:'date', dateFormat: 'd/m/Y'}         
      ]);

    // create the Data Store
    //var numeroEditable= Ext.getCmp('num-editable').getValue();
    storeClaves = new Ext.data.Store({
	        proxy: new Ext.data.HttpProxy({
	        url:'<s:url namespace="tablaCincoClaves" action="ListaClavesEditable" includeParams="none"/>'
			//url: '../tablaCincoClaves/ListaClavesEditable.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaClavesEditable',
            id: 'comboPadre'
	        }, Plant),
	        baseParams:{numEditable: numEditaTabla}
	        //,sortInfo:{field:'descripcionClave1', direction:'ASC'}
    });

    // create the editor grid
    gridEditableClaves = new Ext.grid.EditorGridPanel({
    	id:'grid-editable-claves',
        store: storeClaves,
        cm: cmClaves,
        autoScroll:true,
        width:500,
        height:200,
        autoExpandColumn:'descripcionClave1',
        title:'<s:text name="productos.tabla5claves.subtitulo.claves"/>',
        collapsible:true,
        frame:true,
        //plugins:checkColumn,
        clicksToEdit:1,
		viewConfig: {
	        forceFit: true
	    },
	    sm: new Ext.grid.RowSelectionModel({
			singleSelect: true,
			listeners: {							
		        	rowselect: function(gridConOtroAlias, row, rec) {	
			        		//selectedId=row.getSelected();                    		                    	                        	                     	                        
			        		//alert(selectedId);
			        		var sel = Ext.getCmp('grid-editable-claves').getSelectionModel().getSelected();
					        selIndex = storeClaves.indexOf(sel);
					        //alert(selIndex);
			            	
                           	Ext.getCmp('eliminar-claves').on('click',function(){
                           			//var url4deliteEditableKeys='../tablaCincoClaves/BorrarClaveEditable.action';
									//DeleteInDataStoreFromAction(formPanelEditable,url4deliteEditableKeys,storeClaves);                                                                          									        	 					 			        	 					 			        	 					 
	        	 					Ext.MessageBox.confirm("Mensaje","Esta seguro que desea eliminar este elemento?",function(btn){									   			
	        	 					 		if(btn == 'yes'){ 
									   			storeClaves.remove(rec);
									   			contadorDatoClave--;
									   		}
									 });
																		   
                           	});
                           	if(selIndex!=idFilaClaveAnterior){
                           				
			                           	//Ext.getCmp('panel-editable-grids').getForm().loadRecord(rec);
			                           	var identificadorRecord= rec.get('identificador');
			                           	var identificadorTemporalRecord= rec.get('identificadorTemporal');
			                           	if((identificadorTemporalRecord=="" || identificadorTemporalRecord=="Undefinded") && (identificadorRecord=="" || identificadorRecord=="Undefinded")){
			                           		identificadorRecord=-1;
			                           	}
			                    /*       	var url4reloadEditableAttributes='<s:url namespace="tablaCincoClaves" action="ListaAtributosEditable" includeParams="none"/>'
            	               		   	//var url4reloadEditableAttributes='/tablaCincoClaves/ListaAtributosEditable.action';
    		    	                   	
    		    	                   	//Ext.getCmp('identificador-hidden').setValue(identificadorRecord);
        		    	               	//alert(Ext.getCmp('identificador-hidden').getValue());
            		    	           	//Ext.getCmp('identificador-temporal-hidden').setValue(identificadorTemporalRecord);
        		            	       	//alert(Ext.getCmp('identificador-temporal-hidden').getValue());
        		            	       	
        		            	       	url4reloadEditableAttributes+="?codigoClaveEditable="+identificadorRecord;										
            		               		url4reloadEditableAttributes+="&&codigoClaveEditableTemporal="+identificadorTemporalRecord;
										//alert(url4reloadEditableAttributes);                		        	   
                		        	   	Ext.getCmp('panel-editable-grids').getForm().submit({
                   		       						url:url4reloadEditableAttributes,			      
										            waitTitle:'<s:text name="ventana.tabla5claves.proceso.mensaje.titulo"/>',
					    							waitMsg:'<s:text name="ventana.tabla5claves.proceso.mensaje"/>',
									        	    failure: function(form, action) {
														    Ext.MessageBox.alert('Error', 'Datos no agregados');
														    //wind.close();
													},
													success: function(form, action) {
														    //Ext.MessageBox.alert('Status', 'Datos agregados');						   
														    //wind.close();
													    	storeAtributos.load();
													    	storeAtributos.rejectChanges();
													}
								        });
								        
								*/           
									
									var params=	"codigoClaveEditable="+identificadorRecord+
												"&&numEditable="+num.getValue()+
  												"&&codigoClaveEditableTemporal="+identificadorTemporalRecord+
  												"&&numeroFila="+ row;
  												
  									var conne = new Ext.data.Connection();
				            		conne.request ({
				            				url:'<s:url namespace="tablaCincoClaves" action="ListaAtributosEditable" includeParams="none"/>',
											//url: 'tablaCincoClaves/InsertarTabla.action',
											method: 'POST',
											successProperty : '@success',
											params : params,
				    	       				callback: function (options, success, response) {
	                       						//alert(success);
	                       						//alert("*"+Ext.util.JSON.decode(response.responseText).success);
	                       						if (success) {
		        	                  					storeAtributos.load();
													    storeAtributos.rejectChanges();
            	           						} else {
		                       							Ext.MessageBox.alert('Error', 'Datos no agregados');        	                							
    	                	      				}
       		               					},
					            	   		waitTitle:'<s:text name="ventana.tabla5claves.proceso.mensaje.titulo"/>',
					    					waitMsg:'<s:text name="ventana.tabla5claves.proceso.mensaje"/>'
		    		       			});			
									
									     
	                        }
                           	idFilaClaveAnterior=selIndex;
				   	 }
   		   	}
		}),
        tbar: [{
	            text: '<s:text name="productos.tabla5claves.btn.agregarClave"/>',
	            tooltip:'Agregar una fila de claves',
	            iconCls:'add',
    	        handler : function(){        	        
        	        
        	        var numFilaDatoClave= Ext.getCmp('grid-editable-claves').getStore().getTotalCount();            			
            		var numeroDatoClaveCount= Ext.getCmp('grid-editable-claves').getStore().getCount();
            		//alert("Count"+numeroDatoClaveCount);
            		var numeroDatoClave=numeroDatoClaveCount+1;            		    
            		    contadorDatoClave++;	        	 		
	        	 		//alert("numeroDatoClave"+numeroDatoClave);
            		   	//alert("contadorDatoClave"+contadorDatoClave);
            		    if(numeroDatoClave == 1){
            		    	var p = new Plant({
        	        			identificador:'',
        	        			identificadorTemporal:'',
            	        		descripcionClave1: '',
                	    		descripcionClave2: '',
	                    		descripcionClave3: '',
    	                		descripcionClave4: '',
        	            		descripcionClave5: '',
        	            		fechaDesde: (new Date()).clearTime(),
        	            		fechaHasta: (new Date()).clearTime()
            	   	 		});            	        	
                				gridEditableClaves.stopEditing();
	                			storeClaves.insert(numFilaDatoClave, p);
    	            			gridEditableClaves.startEditing(numFilaDatoClave,0);
            		    }else{            		    		
            		    		//saveModifidedRecords(false);
            		    		var p = new Plant({
        	        			identificador:'',
        	        			identificadorTemporal:'',
            	        		descripcionClave1: '',
                	    		descripcionClave2: '',
	                    		descripcionClave3: '',
    	                		descripcionClave4: '',
        	            		descripcionClave5: '',
        	            		fechaDesde: (new Date()).clearTime(),
        	            		fechaHasta: (new Date()).clearTime()
            	   	 		});            	        	
                				gridEditableClaves.stopEditing();            		    		
	                			storeClaves.insert(numeroDatoClaveCount, p);
    	            			gridEditableClaves.startEditing(numeroDatoClaveCount,0);
            		    }       	                	                	                	        
        	    }
        	},'-',{
   			    	id:'eliminar-claves',
	    	        text:'<s:text name="productos.tabla5claves.btn.eliminar"/>',
			    	tooltip:'Eliminar la fila seleccionada',
   					iconCls:'remove'
        }]
    });				
			
	
    // trigger the data store load
    storeClaves.load();
/*********************************************
Grid Editable de Atributos
*********************************************/    

    // the column model has information about grid columns
    // dataIndex maps the column to the specific data field in
    // the data store (created below)
    cmAtributos = new Ext.grid.ColumnModel([{
           id:'value',
           header: "Clave de Atributo",
           dataIndex: 'value',
           width: 100          
        },{
           header: "Valor de Atributo",
           dataIndex: 'nick',
           width: 100,
           editor: new fm.TextField({
               allowBlank: false
           })
        }
    ]);
	
    // by default columns are sortable
    cmAtributos.defaultSortable = false;
    
    // create the Data Store
	storeAtributos = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({        	
        	url:'<s:url namespace="tablaCincoClaves" action="ListaAtributosEditable" includeParams="none"/>'
//			url: '/tablaCincoClaves/ListaAtributosEditable.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaAtributosEditable',
            id: 'comboPadre'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'},
           {name: 'nick', type: 'string',mapping:'nick'}        
        ]),
        //baseParams:{numEditable: numEditaTabla},
		remoteSort: false
    });
	//StoreAtributos.load(); 

    // create the editor grid
    gridEditableAtributos = new Ext.grid.EditorGridPanel({
    	id:'grid-editable-atributos',
        store: storeAtributos,
        cm: cmAtributos,
        autoScroll:true,
        width:500,
        height:200,
        autoExpandColumn:'value',
        title:'<s:text name="productos.tabla5claves.subtitulo.atributos"/>',
        collapsible:true,
        frame:true,
        //plugins:checkColumn,
        clicksToEdit:1,
		viewConfig: {
	        forceFit: true
	    }
	    //,sm: new Ext.grid.RowSelectionModel({
		//	singleSelect: true			
		//})
    });

    // trigger the data store load
    //storeAtributos.load();
/**********************************************
Forma del panel Editable
***********************************************/
      nombreEditable= new Ext.form.TextField({
      				id:'nombre-editable',
                    fieldLabel: '<s:text name="productos.config.tabla5claves.nombre"/>',
                    labelSeparator:'',
                    width:'85'  ,
                    disabled:true,
                    maxLength: '30',
                    maxLengthText: 'El texto debe ser de 30 caracteres',
                    allowBlank:false,
                    blankText : '<s:text name="productos.tabla5claves.valida.nombre.req"/>',
                    name:'nombreEditable' 
   		});		

      descripcionEditable= new Ext.form.TextField({
      				id:'descripcion-editable',	
                    fieldLabel: '<s:text name="productos.config.tabla5claves.descripcion"/>',
                    labelSeparator:'',                    
                    width:'195'  ,
                    maxLength: '60',
                    maxLengthText: 'El texto debe ser de 60 caracteres', 
                    disabled:true,
                    allowBlank:false,
                    blankText : '<s:text name="productos.tabla5claves.valida.descripcion.req"/>',
                    name:'descripcionEditable' 
   		});		

      numEditable= new Ext.form.NumberField({
      				id:'num-editable',
                    fieldLabel: '<s:text name="productos.config.tabla5claves.numero"/>',
                    labelSeparator:'',                    
                    width:'50' ,
                    disabled:true  ,
                    name:'numEditable'
   		});		

    formPanelEditable = new Ext.FormPanel({
		autoScroll:true,
		id:'panel-editable-grids',
        frame:true,
        region:'center',
        bodyStyle:'padding:5px 5px 0',
        url:'<s:url namespace="tablaCincoClaves" action="InsertarValoresTablaEditable" includeParams="none"/>',
//        url:'/tablaCincoClaves/InsertarValoresTablaEditable.action',
        width:500,
        border:false,
        items:[{
	        	layout:'form',
    	    	border:false,
        		title:'<s:text name="productos.tabla5claves.subtitulo.encabezado"/>',
        		collapsible:true,
	        	width: '500',
    	    	items:[{
			        layout:'column',
		    	    border:true,
	    	    	width: '430',
	    	    	items: [{
			        		columnWidth:.25,
    		  		        labelAlign: 'top',
    			    		layout:'form',
        					border:false,
        					items:[nombreEditable]
			        	},{
    			    		columnWidth:.50,
    				        labelAlign: 'top',
        					layout:'form',
        					border:false,
        					items:[descripcionEditable]
		   		    	},{
	    		    		columnWidth:.2,
    				        labelAlign: 'top',
        					layout:'form',
        					border:false,
        					items:[numEditable]
		        		}]
			    }/*,{
			        layout:'column',
		    	    border:false,
	    	    	width: '430',
	    	    	items: [{
			        		columnWidth:.5,
    			    		layout:'form',
        					border:false,
        					items:[{
			    	            xtype:'datefield',
	    			            fieldLabel: '<s:text name="productos.config.tabla5claves.fechaIni"/>',
					    	    name: 'fechaDesde',
		    	    			id: 'startdt',
		        				vtype: 'daterange',
		    	    	        endDateField: 'enddt',
    				            format:'d/m/Y'		    
				        	}]
			        	},{
    			    		columnWidth:.5,
        					layout:'form',
        					border:false,
        					items:[{
    				    		xtype:'datefield',
		    	    	        fieldLabel: '<s:text name="productos.config.tabla5claves.fechaFin"/>',
			    	    		name: 'fechaHasta',
			    		    	id: 'enddt',
    	    					vtype: 'daterange',
						        startDateField: 'startdt',
						        format:'d/m/Y'        	    
				        	}]
		   		    	}]
			    }*/]		    
		    },
        	gridEditableClaves,gridEditableAtributos]     
    });
    
 windowConsulta = new Ext.Window({
            title: '<s:text name="productos.tabla5claves.titulo.consulta"/>',
            closable:true,
            buttonAlign:'center',
            width:560,
            height:500,
            autoScroll:true,
            plain:true,
            layout: 'border',
            modal:true,
			items:[formPanelEditable],
  			buttons:[{
  					text:'<s:text name="productos.tabla5claves.btn.guardarCambios"/>',
  					handler:function(){
  						var recs = storeClaves.getModifiedRecords();
  						var recsAtributos = storeAtributos.getModifiedRecords();
						if(recs.length==0 && recsAtributos.length==0){
							band=false;
							Ext.Msg.alert('Error', 'No ha habido cambios');
						}else{
							saveModifidedRecords(false);
						}			
  					}  					
  				},{
  					text:'<s:text name="productos.tabla5claves.btn.guardarFinalizar"/>',
  					handler:function(){
	  					saveModifidedRecords(true);
	  					
  					}  					
				},{
					text:'<s:text name="productos.tabla5claves.btn.cancelar"/>',
					handler:function(){  
						
							windowConsulta.close();
							limpiarSesion();
							storeClaves.rejectChanges();		
					}
  				}]
        });
        function loadRecordForm(recordClavesForm){
        	formPanelEditable.form.loadRecord(recordClavesForm);
        }
        function saveModifidedRecords(condicion){
        				//alert("count"+storeClaves.getCount())
        				//alert("totalCount"+storeClaves.getTotalCount());
  						var params="numeroFila="+selIndex+"&&";
  						//+"&& codigoClaveEditable="+Ext.getCmp('identificador-hidden').getValue();
  						//params+="&& codigoClaveEditableTemporal="+Ext.getCmp('identificador-temporal-hidden').getValue()+"&&";
  						
  						var recs = storeClaves.getModifiedRecords();  						
  						
  						//alert("recs"+recs.length);
  						//alert("validacion"+ validarClavesObligatorias(recs));
						var valida;
						if(recs.length != 0){
							valida= validarClavesObligatorias(recs);
							//alert("valida del if"+valida);
						}else{
							var record = storeClaves.getAt(selIndex);
							//alert("selindex"+selIndex);
							//alert(record.get('identificador'));
							var lengthGuarda=dataStore.getTotalCount();	    								
							for(var ind=0;ind<lengthGuarda;ind++){
								boleanoG=true;
								banderaGCO=false;
								switch(ind){
									case 0:
										if(record.get('descripcionClave1').length==0)
											banderaGCO=true;
										break;
									case 1:
										if(record.get('descripcionClave2').length==0)
											banderaGCO=true;
										break;
									case 2:
										if(record.get('descripcionClave3').length==0)
											banderaGCO=true;
										break;
									case 3:
										if(record.get('descripcionClave4').length==0)
											banderaGCO=true;
										break;
									case 4:
										if(record.get('descripcionClave5').length==0)
											banderaGCO=true;
										break;
								
								}
							if(banderaGCO){
								ind=lengthGuarda;							
								boleanoG=false;
							}						
							}
							if(!boleanoG){
								Ext.MessageBox.alert('Error', 'Por favor llene todas las columnas requeridas');
								valida=false;
							}else{
							valida=true;
							//alert("valida del else"+valida);
							}
							
						}
						//alert("valida"+valida);
						if (valida) {
							for (var i=0; i<recs.length; i++) {														
								
							params =params +"listaParamsClaves[" + i + "].identificador=" + recs[i].get('identificador') + 
											"&&listaParamsClaves[" + i + "].descripcionClave1=" + recs[i].get('descripcionClave1')+ 
											"&&listaParamsClaves[" + i + "].descripcionClave2=" + recs[i].get('descripcionClave2')+
											"&&listaParamsClaves[" + i + "].descripcionClave3=" + recs[i].get('descripcionClave3')+ 
											"&&listaParamsClaves[" + i + "].descripcionClave4=" + recs[i].get('descripcionClave4')+
											"&&listaParamsClaves[" + i + "].descripcionClave5=" + recs[i].get('descripcionClave5')+ 
											"&&listaParamsClaves[" + i + "].fechaDesde=" + recs[i].get('fechaDesde')+											 
											"&&listaParamsClaves[" + i + "].fechaHasta=" + recs[i].get('fechaHasta')+"&&";
							}
						
						/*if (storeAtributos.getTotalCount()>0){
							for(var i=0; i<storeAtributos.getTotalCount();i++){
								params = params+"listaParamsAtributos["+storeAtributos.getAt(i).get('key')+"].key="+storeAtributos.getAt(i).get('key')+ //seteas identificador
   												"listaParamsAtributos["+storeAtributos.getAt(i).get('key')+"].value="+storeAtributos.getAt(i).get('value')+ //seteas nombre del atributo
												"listaParamsAtributos["+storeAtributos.getAt(i).get('key')+"].nick="+storeAtributos.getAt(i).get('nick')+"&&" //seteas valor del atributo
							}
						}
						*/
						var recsAtributos = storeAtributos.getModifiedRecords();
  						
  						//alert(recsAtributos.length);
						if (recsAtributos.length > 0) {
							for (var j=0; j<recsAtributos.length; j++) {
								params =params +"listaParamsAtributos[" + j + "].key=" + recsAtributos[j].get('key') + 
												"&&listaParamsAtributos[" + j + "].value=" + recsAtributos[j].get('value')+ 
												"&&listaParamsAtributos[" + j + "].nick=" + recsAtributos[j].get('nick')+"&&";
							}
						}
						var band=true; 
						//if(recs.length==0 && recsAtributos.length==0){
							//band=false;
							//Ext.Msg.alert('Error', 'No ha habido cambios');
						//}
						if(band){
							var conn = new Ext.data.Connection();
				            conn.request ({
				            		url:'<s:url namespace="tablaCincoClaves" action="GuardarCambiosClaves" includeParams="none"/>',
									//url: '/tablaCincoClaves/GuardarCambiosClaves.action',
									method: 'POST',
									successProperty : '@success',
									params : params,
				    	       		callback: function (options, success, response) {
                       						if (!success) {
	                       							Ext.Msg.alert('Error', 'No se pudieron guardar los cambios');
                        							band = false;
                       						} else {
		                          					//Ext.Msg.alert('Confirmación', 'Se han guardado con éxito los cambios');
		                          					storeClaves.load();
		                          					if(condicion){
		                          						//guardar en la base
		                          						/*boleanoFechaD=true;
						boleanoFechaH=true;
						if(Ext.getCmp('startdt').length>0){
							Ext.getCmp('startdt').isValid(false);
							boleanoFechaD=false;
						}		
						if(Ext.getCmp('enddt').length>0){
							Ext.getCmp('enddt').isValid(false);
							boleanoFechaH=false;
						}		
						if(boleanoFechaD && boleanoFechaH){
  							saveModifidedRecords();
	  						windowConsulta.close();
  						}*/
  						var params=	"numeroTabla="+numEditable.getValue();
  																					
									var connValores = new Ext.data.Connection();
				            		connValores.request ({
				            				url:'<s:url namespace="tablaCincoClaves" action="InsertarValoresTablaCincoClaves" includeParams="none"/>',
											//url: 'tablaCincoClaves/InsertarTabla.action',
											method: 'POST',
											successProperty : '@success',
											params : params,
				    	       				callback: function (options, success, response) {
	                       						//alert(success);
	                       						//alert("*"+Ext.util.JSON.decode(response.responseText).success);
	                       						if (success) {
		        	                  					Ext.MessageBox.alert('Status', 'Valores agregados');		            	              					
		            	              					windowConsulta.close();
		            	              					limpiarSesion();
            	           						} else {
		                       							Ext.MessageBox.alert('Error', 'Valores no agregados');        	                							
    	                	      				}
       		               					},
					            	   		waitTitle:'<s:text name="ventana.tabla5claves.proceso.mensaje.titulo"/>',
					    					waitMsg:'<s:text name="ventana.tabla5claves.proceso.mensaje"/>'
		    		       			});//aqui termina guardar en la base
		                          					}
    	                      				}
       		               			},
					               	waitTitle:'<s:text name="ventana.tabla5claves.proceso.mensaje.titulo"/>',
					    			waitMsg:'<s:text name="ventana.tabla5claves.proceso.mensaje"/>'
		    		       	});
		    	       	}
			           	if(band){
  							storeClaves.commitChanges();
  							storeAtributos.commitChanges();
  							
  						}
  						}
        }
        
        function limpiarSesion(){
        	var connSesion = new Ext.data.Connection();
				            		connSesion.request ({
				            				url:'<s:url namespace="tablaCincoClaves" action="LimpiarSesionTablaCincoClaves" includeParams="none"/>',											
											method: 'POST',
											successProperty : '@success'
											//params : params,
				    	       				/*callback: function (options, success, response) {
	                       						//alert(success);
	                       						//alert("*"+Ext.util.JSON.decode(response.responseText).success);
	                       						if (success) {
		        	                  					Ext.MessageBox.alert('Status', 'Valores agregados');		            	              					
		            	              					windowConsulta.close();
            	           						} else {
		                       							Ext.MessageBox.alert('Error', 'Valores no agregados');        	                							
    	                	      				}
       		               					}*/
					            	   		//waitTitle:'<s:text name="ventana.tabla5claves.proceso.mensaje.titulo"/>',
					    					//waitMsg:'<s:text name="ventana.tabla5claves.proceso.mensaje"/>'
		    		       			});		                              	                      				
        }
        
  encabezados();      
  windowConsulta.show();      
}  
/***************************************************************************
Ventana de apoyo de 5 claves
****************************************************************************/
 var afuera;
 var temporal=-1;
 var contador=0;
 var selIndexGridclave;
/*************************************
Grid 1
**************************************/
//var fm = Ext.form;

var myData = [
        ["N","Numerico"],
        ["A","Alfanumerico"]
        ];
var comboStore = new Ext.data.SimpleStore({
        fields: [
           {name: 'key'},
           {name: 'value'}           
        ]
    });   	
    comboStore.loadData(myData);
     colm = new Ext.grid.ColumnModel([{
           id:'descripcionClave',
           header: "Claves",
           dataIndex: 'descripcionClave',
           width: 100,           
           editor: new Ext.form.TextField({
           		allowBlank: false,
           		blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.descripcion.req"/>',
           		maxLength: '30',
                    maxLengthText: 'El texto debe ser de 30 caracteres'
           })
        },{
           header: "Formato",
           dataIndex: 'descripcionFormatoClave',
           width: 100,
           editor: new Ext.form.ComboBox({          										
				typeAhead: true,
				triggerAction: 'all',
				tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
				displayField:'value',
				//valueField: 'key',
				typeAhead: true,
				mode: 'local',					    	
				store: comboStore,
				forceSelection:true,
				emptyText:'<s:text name="productos.tabla5claves.atributosVar.select.formato"/>',
				selectOnFocus:true,
				allowBlank:false,
				blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.formato.req"/>',    	       
				hideLabel:true
            })		    
        },{
           header: "Mínimo",
           dataIndex: 'minimoClave',
           width: 90,
           editor: new Ext.form.NumberField({   
           		allowBlank: false,
        		blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.minimo.req"/>'       		
            })
        },{
           header: "Máximo",
           dataIndex: 'maximoClave',
           width: 90,           
           editor: new Ext.form.NumberField({   
           		allowBlank: false,
        		blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.maximo.req"/>'       		
            })
           
        }        
    ]);
    
    
    colm.defaultSortable = false;
    
    PlantGrid1 = Ext.data.Record.create([

           		//{name: 'numeroTabla',  		type: 'string',  mapping:'numeroTabla'},
	        	//{name: 'numeroClave',  		type: 'string',  mapping:'numeroClave'},
        		{name: 'descripcionClave',  type: 'string',  mapping:'descripcion'},
        		{name: 'descripcionFormatoClave',  	type: 'string',  mapping:'descripcionFormato'},        		
        		{name: 'maximoClave',  		type: 'string',  mapping:'maximo'},
        		{name: 'minimoClave',  		type: 'string',  mapping:'minimo'}                      
      ]);

    
    dataStore = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url:'<s:url namespace="tablaCincoClaves" action="ListaCincoClaves" includeParams="none"/>'   
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaClaves',
            id: 'descripcion'
            }, PlantGrid1)
     
    });
    grid = new Ext.grid.EditorGridPanel({
			id:'grid-clave',
        	store: dataStore,
        	cm: colm,
        	autoScroll:true,
        	width:450,
        	height:190,
        	autoExpandColumn:'descripcionClave',
        	collapsible:true,
        	frame:true,
        	title:'<s:text name="productos.tabla5claves.subtitulo.claves"/>',
        	clicksToEdit:1,
        	viewConfig: {
				forceFit: true
        	},
        	sm: new Ext.grid.RowSelectionModel({
			singleSelect: true,
			listeners: {							
        		rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			//selectedId = dataStore.data.items[row].id;
	        	 			var selGrid = Ext.getCmp('grid-clave').getSelectionModel().getSelected();
	        	 			var selIndexGrid = dataStore.indexOf(selGrid);
							//afuera=row;
							
	        	 			Ext.getCmp('eliminar-clave').on('click',function(){ 
	        	 					 var numData=dataStore.getTotalCount();	
	        	 					 var numelimina = numData + 1;        	 					 			        	 					 		
	        	 					 if(selIndexGrid >= numData){
	        	 					 	Ext.MessageBox.confirm("Mensaje","Esta seguro que desea eliminar este elemento?",function(btn){
									   			//alert("numelimina"+numelimina);
	        	 					 		if(btn == 'yes'){ 
									   			dataStore.remove(rec);
									   			contador--;
									   		}
									 	});
	        	 					 }else{
	        	 					 	Ext.MessageBox.alert('Error', 'No se pueden eliminar las claves asociadas');
	        	 					 }                                      
                                 });                                                           	        	 					                                                                                  
                 }
     	     }                                                         							
		}),  
		tbar:[{
            text:'<s:text name="productos.tabla5claves.btn.agregar"/>',
            tooltip:'Agrega una clave',            
            iconCls:'add',            
            handler:function(){         	            		            	
	        	 		//alert("contador"+contador);
            			var numFila= Ext.getCmp('grid-clave').getStore().getTotalCount();            			
            		    var num=numFila+contador;            		    
            		    contador++;	        	 		
	        	 		//alert("num"+num);
            		   
            		    if(contador == 1){
            		    	selIndexGridclave=Ext.util.JSON.encode(numFila);
            		    	var p = new PlantGrid1({
            	        		descripcionClave: '',
                	    		descripcionFormatoClave: '',
	                    		maximoClave: '',
    	                		minimoClave: ''
            	    		});            	        	
                				grid.stopEditing();
	                			dataStore.insert(numFila, p);
    	            			grid.startEditing(numFila,0);
            		    }else if(num<5){
            		    		selIndexGridclave=Ext.util.JSON.encode(num);
            		    		var p = new PlantGrid1({
            	        		descripcionClave: '',
                	    		descripcionFormatoClave: '',
	                    		maximoClave: '',
    	                		minimoClave: ''
            	    		});            	        	
                				grid.stopEditing();
	                			dataStore.insert(num, p);
    	            			grid.startEditing(num,0);
            		    }else {
            		    	Ext.MessageBox.alert('Error', 'Esta tabla solo puede crecer hasta 5 campos');
            		    }							            			           			
			}
            
        },'-',{
            text:'<s:text name="productos.tabla5claves.btn.eliminar"/>',
            id:'eliminar-clave',
            tooltip:'Elimina la clave seleccionada',
            iconCls:'remove'
            
        }/*,'-',{
            text:'<s:text name="productos.tabla5claves.btn.editar"/>',
            id:"editar-clave",
            tooltip:'Edita la clave seleccionada',
            iconCls:'option'
        }*/] 
    	});

		grid.addListener({
			
			'beforeedit':{
				fn: function(event){
					 var selGridclaveBefore = Ext.getCmp('grid-clave').getSelectionModel().getSelected();
					 var selIndexGridclaveBefore = dataStore.indexOf(selGridclaveBefore);
					 var numeroDeDatos= Ext.getCmp('grid-clave').getStore().getTotalCount();   
					 //alert("numeroDeDatos"+numeroDeDatos);
					 //alert("selGridclaveBefore"+selIndexGridclaveBefore);
					 if(selIndexGridclaveBefore == -1){
					 	selIndexGridclave=selIndexGridclave;
					 }else{					 
					 	if(selIndexGridclaveBefore < numeroDeDatos){					 	 					 	 
					 	 
					 		 selIndexGridclave=Ext.util.JSON.encode(selIndexGridclaveBefore);
					 	}else{
					 	 	selIndexGridclave=Ext.util.JSON.encode(numeroDeDatos);				 	
					 	}
					 }	
				}
				,scope:this
			},						
			'validateedit':{
				fn: function(event){					
					//var selGridclave = Ext.getCmp('grid-clave').getSelectionModel().getSelected();	       			       			
	       			//var selIndexGridclave = dataStore.indexOf(selGridclave);
    				var numeroDeDatos= Ext.getCmp('grid-clave').getStore().getTotalCount();    		    		
    				//alert("numeroDeDatosVal"+numeroDeDatos);
    				//alert("selIndexGridclaveVal"+selIndexGridclave);
    				if(selIndexGridclave < numeroDeDatos){
		        		//Ext.MessageBox.alert('Error', 'No se pueden editar las claves asociadas');		        			        		
		        		//Ext.getCmp('grid-clave').stopEditing();
		        		return false		        				        	
	    			}else{	    			
	    				return true
	    			}	    			    				 	
				}
				,scope:this
			}		
		});//end grid.addListener				
 
    dataStore.load();

/*************************************
Grid 2
**************************************/
var contadorAtributo=0;
var selIndexGridatributo;
var myData2 = [
        ["N","Numerico"],
        ["A","Alfanumerico"]
        ];
var comboStore2 = new Ext.data.SimpleStore({
        fields: [
           {name: 'key'},
           {name: 'value'}           
        ]
    });   	
    comboStore2.loadData(myData2);
    
     var cm = new Ext.grid.ColumnModel([{
           id:'descripcionAtributo',
           header: "Atributos",
           dataIndex: 'descripcionAtributo',
           width: 100,
           editor: new Ext.form.TextField({
           	maxLength: '30',
                    maxLengthText: 'El texto debe ser de 60 caracteres'
           })
        },{
           header: "Formato",
           dataIndex: 'descripcionFormatoAtributo',
           width: 100,
           editor: new Ext.form.ComboBox({          										
				typeAhead: true,
				triggerAction: 'all',
				tpl: '<tpl for="."><div ext:qtip="{key}" class="x-combo-list-item">{value}</div></tpl>',
				displayField:'value',
				//valueField: 'key',
				typeAhead: true,
				mode: 'local',					    	
				store: comboStore,
				forceSelection:true,
				emptyText:'<s:text name="productos.tabla5claves.atributosVar.select.formato"/>',
				selectOnFocus:true,
				allowBlank:false,
				blankText : '<s:text name="productos.tabla5claves.atributosVar.valida.formato.req"/>',    	       
				hideLabel:true
            })
        },{
           header: "Mínimo",
           dataIndex: 'minimoAtributo',
           width: 80,
           editor: new Ext.form.NumberField({          		
            })
        },{
           header: "Máximo",
           dataIndex: 'maximoAtributo',
           width: 80,           
           editor: new Ext.form.NumberField({          		
            })
           
        }
    ]);
    
    
    cm.defaultSortable = false;
   
    PlantGrid2 = Ext.data.Record.create([
           		
	        	{name: 'numeroClave',  		type: 'string',  mapping:'numeroClave'},
        		{name: 'descripcionAtributo',  type: 'string',  mapping:'descripcion'},
        		{name: 'descripcionFormatoAtributo',  	type: 'string',  mapping:'descripcionFormato'},        		
        		{name: 'maximoAtributo',  		type: 'string',  mapping:'maximo'},
        		{name: 'minimoAtributo',  		type: 'string',  mapping:'minimo'}                      
      ]);

    
    store = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url:'<s:url namespace="tablaCincoClaves" action="ListaAtributos" includeParams="none"/>'   
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaAtributos',
            id: 'attributos'
            }, PlantGrid2)
     
    });
    grid2 = new Ext.grid.EditorGridPanel({
			id:'grid-lista-atrib',
        	store: store,
        	cm: cm,
        	autoScroll:true,
        	width:450,
        	height:190,
        	autoExpandColumn:'descripcionAtributo',
        	collapsible:true,
        	frame:true,
        	title:'<s:text name="productos.tabla5claves.subtitulo.atributos"/>',
        	clicksToEdit:1,
        	viewConfig: {
				forceFit: true
        	},
        	sm: new Ext.grid.RowSelectionModel({
			singleSelect: true,
			listeners: {							
        		rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			//selectedId = dataStore.data.items[row].id;
	        	 			var selGrid2 = Ext.getCmp('grid-lista-atrib').getSelectionModel().getSelected();
	        	 			var selIndexGrid2 = store.indexOf(selGrid2);
							//afuera=row;
							
	        	 			Ext.getCmp('eliminar-grid-lista-atributos').on('click',function(){ 
	        	 					 var numDataAtributo=store.getTotalCount();	
	        	 					 var numeliminaAtributo = numDataAtributo + 1;        	 					 			        	 					 		
	        	 					 if(selIndexGrid2 >= numDataAtributo){
	        	 					 	Ext.MessageBox.confirm("Mensaje","Esta seguro que desea eliminar este elemento?",function(btn){
									   			//alert("numeliminaAtributo"+numeliminaAtributo);
	        	 					 		if(btn == 'yes'){ 
									   			store.remove(rec);
									   			contadorAtributo--;
									   		}
									 	});
	        	 					 }else{
	        	 					 	Ext.MessageBox.alert('Error', 'No se pueden eliminar los atributos asociados');
	        	 					 }                         
	        	 					                                            
                                 }); 
                  }
            }                                                            							
		}),
		tbar:[{
            text:'<s:text name="productos.tabla5claves.btn.agregar"/>',
            tooltip:'Agrega un atributo',
            iconCls:'add',                        			
			handler:function(){         	            		            	
	        	 		//alert("contadorAtributo"+contadorAtributo);
            			var numFilaAtributo= Ext.getCmp('grid-lista-atrib').getStore().getTotalCount();            			
            		    var numAtributo=numFilaAtributo+contadorAtributo;            		    
            		    contadorAtributo++;	        	 		
	        	 		//alert("numAtributo"+numAtributo);
            		   
            		    if(contadorAtributo == 1){
            		    	selIndexGridatributo=Ext.util.JSON.encode(numFilaAtributo);
            		    	var p2 = new PlantGrid2({
            	        		descripcionAtributo: '',
                	    		descripcionFormatoAtributo: '',
	                    		maximoAtributo: '',
    	                		minimoAtributo: ''
            	    		});            	        	
                				grid2.stopEditing();
	                			store.insert(numFilaAtributo, p2);
    	            			grid2.startEditing(numFilaAtributo,0);
            		    }else if(numAtributo<25){
            		    		selIndexGridatributo=Ext.util.JSON.encode(numAtributo);
            		    		var p2 = new PlantGrid2({
            	        		descripcionAtributo: '',
                	    		descripcionFormatoAtributo: '',
	                    		maximoAtributo: '',
    	                		minimoAtributo: ''
            	    		});            	        	
                				grid2.stopEditing();
	                			store.insert(numAtributo, p2);
    	            			grid2.startEditing(numAtributo,0);
            		    }else{
            		    	Ext.MessageBox.alert('Error', 'Esta tabla solo puede crecer hasta 25 campos');
            		    }							            			           			
			}
			
            
        },'-',{
            text:'<s:text name="productos.tabla5claves.btn.eliminar"/>',
            id:'eliminar-grid-lista-atributos',
            tooltip:'Elimina el atributo seleccionado',
            iconCls:'remove'
            
        }/*,'-',{
            text:'<s:text name="productos.tabla5claves.btn.editar"/>',
            id:"editar-clave",
            tooltip:'Edita la clave seleccionada',
            iconCls:'option'
        }*/] 
    	});

 	grid2.addListener({
 	
 			'beforeedit':{
				fn: function(event){
					 var selGridatributoBefore = Ext.getCmp('grid-lista-atrib').getSelectionModel().getSelected();
					 var selIndexGridatributoBefore = store.indexOf(selGridatributoBefore);
					 var numeroDeDatosAtributos= Ext.getCmp('grid-lista-atrib').getStore().getTotalCount();   
					 //alert("numeroDeDatosAtributos"+numeroDeDatosAtributos);
					 //alert("selIndexGridatributoBefore"+selIndexGridatributoBefore);
					 if(selIndexGridatributoBefore == -1){
					 	selIndexGridatributo=selIndexGridatributo;
					 }else{					 
					 	if(selIndexGridatributoBefore < numeroDeDatosAtributos){					 	 					 	 
					 	 
					 		 selIndexGridatributo=Ext.util.JSON.encode(selIndexGridatributoBefore);
					 	}else{
					 	 	selIndexGridatributo=Ext.util.JSON.encode(numeroDeDatosAtributos);				 	
					 	}
					 }					 					
				}
				,scope:this
			},			
			'validateedit':{
				fn: function(event){					
    				var numeroDeDatosAtributos= Ext.getCmp('grid-lista-atrib').getStore().getTotalCount();    		    		    				
    				if(selIndexGridatributo < numeroDeDatosAtributos){
		        		//Ext.MessageBox.alert('Error', 'No se pueden editar los atributos asociados');		        			        		        			        	
		        		return false; 
	    			}else{	    				
	    				return true;
	    			}		        				 	
				}
				,scope:this
			}		
		});//end grid.addListener
		
    store.load();	

/********************************************
forma
*******************************************/	
	var url='<s:url namespace="tablaCincoClaves" action="CatalogoClnatura" includeParams="none"/>';
	//var url='/tablaCincoClaves/CatalogoClnatura.action'; 			 		 			
	var storeClnatura = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: url
       		}),
	       		reader: new Ext.data.JsonReader({
    	       	root:'listaClnatura',   
        	   	totalProperty: 'totalCount',
           		id: 'catalogo'           	         	
	        },[
        		{name: 'key',  type: 'string',  mapping:'key'},
        		{name: 'value',  type: 'string',  mapping:'value'}        		            	        		            
			]),
			autoLoad:true,
			remoteSort: true
   	});
   	storeClnatura.setDefaultSort('catalogo', 'desc');
   	storeClnatura.load();
       var clnatura =new Ext.form.ComboBox({
						    tpl: '<tpl for="."><div ext:qtip="{value}. {key}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeClnatura,
						    displayField:'value',
						    valueField: 'value',
					    	typeAhead: true,
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="productos.tabla5claves.select.naturaleza"/>',
					    	selectOnFocus:true,
					    	fieldLabel: '<s:text name="productos.config.tabla5claves.naturaleza"/>',
					    	allowBlank:false,
    						blankText : '<s:text name="productos.tabla5claves.valida.naturaleza.req"/>',
					    	name:"clnatura"					    	
			});
      var nombre= new Ext.form.TextField({
                    fieldLabel: '<s:text name="productos.config.tabla5claves.nombre"/>',
                    labelSeparator:'',
                    width:'80'  ,
                    allowBlank:false,
                    maxLength: '30',
                    maxLengthText: 'El texto debe ser de 30 caracteres',
    				blankText : '<s:text name="productos.tabla5claves.valida.nombre.req"/>',
                    name:'nombre1' 
   		});		

      var descripcion= new Ext.form.TextField({
      				id:'descripcion-test',
                    fieldLabel: '<s:text name="productos.config.tabla5claves.descripcion"/>',
                    labelSeparator:'',                    
                    width:'196'  ,
                    allowBlank:false,
                    maxLength: '60',
                    maxLengthText: 'El texto debe ser de 60 caracteres',
    				blankText : '<s:text name="productos.tabla5claves.valida.descripcion.req"/>',
                    name:'descripcion1' 
   		});		

      var num= new Ext.form.NumberField({
                    id:'id-num1-tabla-5-claves',
                    fieldLabel: '<s:text name="productos.config.tabla5claves.numero"/>',
                    labelSeparator:'',                    
                    width:'50',
                    disabled:true,
                    name:'num1'
   		});		
   	 var tipoUnico= new Ext.form.Radio({	
   	 						id:'radio-tipo-unico-tabla5Claves',	 				
    		 				boxLabel:'<s:text name="productos.config.tabla5claves.unico"/>',
    		 				labelSeparator:'',                    
    		 				fieldLabel:'<s:text name="productos.config.tabla5claves.tipoAcceso"/>',
    		 				checked:true,
    		 				name:'tipoDeAcceso',
    		 				onClick:function(){
				            	if(this.getValue()){				            					            		
				            		Ext.getCmp('id-radio-tipo-de-acceso').setValue("U");				            	
				            	}
				            }
    		 });
	 var tipoPorTramos= new Ext.form.Radio({		 				
	 						id:'radio-tipo-porTamos-tabla5Claves',
    		 				boxLabel:'<s:text name="productos.config.tabla5claves.tramos"/>',
    		 				hideLabel:true,
    		 				hideParent:true,
    		 				name:'tipoDeAcceso',
    		 				onClick:function(){
				            	if(this.getValue()){				            	
				            		Ext.getCmp('id-radio-tipo-de-acceso').setValue("T");				            	
				            	}
				            }
	 });	 	 	
	 
	 var switchModificar= new Ext.form.Checkbox({
	 						id:'id-modifica-valor-tabla5claves',
							hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            },
				            name:'switchModificacion'
     });	
	 var switchModificarDummy= new Ext.form.Checkbox({
							width:'350',
							hidden:true,
							labelSeparator:'',                    
            				fieldLabel:'<s:text name="productos.config.tabla5claves.modificaValor"/>'
            				
     });
     var switchEnviar= new Ext.form.Checkbox({
     						id:'id-envia-tabla-error-tabla5claves',
       						hideLabel:true,
            				onClick:function(){
				            	if(this.getValue()){				            	
				            		this.setRawValue("S");				            	
				            	}else{      
				            		this.setRawValue("N");      						
				            	}
				            },
				            name:'switchEnviar'
    });
     var switchEnviarDummy= new Ext.form.Checkbox({
     						width:'350',
     						hidden:true,
     						labelSeparator:'',                   
            				fieldLabel:'<s:text name="productos.config.tabla5claves.enviaTabError"/>'
            				
    });

    var formPanel = new Ext.FormPanel({
    	id:'id-form-panel-tabla5claves',
		autoScroll:true,
        frame:true,
        region:'center',
        bodyStyle:'padding:5px 5px 0',
        url:'<s:url namespace="tablaCincoClaves" action="InsertarTabla" includeParams="none"/>',
        //url:'/tablaCincoClaves/InsertarTabla.action?'+params,
        width:500,
        border:false,
        items:[{
        	layout:'form',
        	border:false,
        	title:'<s:text name="productos.tabla5claves.subtitulo.encabezado"/>',
        	collapsible:true,
        	width: '450',
        	items:[	{xtype:'hidden',id:'hidden-numero-tabla-cinco-claves',name:'num1'},
        			{xtype:'hidden',id:'id-radio-tipo-de-acceso',name:'radioTipoAcceso'},
        			{xtype:'hidden',id:'hidden-radio-tipo-de-acceso',name:'hiddenRadioTipoAcceso'},
        			{xtype:'hidden',id:'hidden-tipo-transaccion-tabla-5-claves',name:'tipoTransaccion'},             			     		
        		{
		        layout:'column',
		        border:false,
	    	    width: '430',
    	    	items: [{
		        		columnWidth:.23,
    	  		        labelAlign: 'top',
    		    		layout:'form',
        				border:false,
        				items:[nombre]
		        	},{
    		    		columnWidth:.5,
    			        labelAlign: 'top',
        				layout:'form',
        				border:false,
        				items:[descripcion]
		   		    },{
    		    		columnWidth:.18,
    			        labelAlign: 'top',
        				layout:'form',
        				border:false,
        				items:[num]
		        	}]
		    },{
			    layout:'form',							                
		        border:false,
		        bodyBorder:true, 
	    	    labelAlign:'left',   	    
        		width: 400,
		        items: [{
			            layout:'column',
		    	        border:false,
		        	    labelAlign:'left',
			            items:[{
				                columnWidth:.45,
				                layout: 'form',
				                labelAlign:"left",
			    	            border:false,
			        	        items: [tipoUnico]
				            },{
			               		columnWidth:.55,
				                layout: 'form',
				                border:false,
			    	            items: [tipoPorTramos]
		            	}]
		        }]
		    },{
			    layout:'form',							                
		        border:false,
	    	    bodyBorder:true, 	        	    
        		width: '400',
		        items: [{
			            layout:'column',
			            border:false,
		    	        items:[{
			    	            columnWidth:.6,
			        	        layout: 'form',
			            	    labelAlign:"top",
			                	border:false,
				                items: [switchModificarDummy]
				            },{
			               		columnWidth:.3,
				                layout: 'form',
			    	            border:false,
			        	        items: [switchModificar]
			            }]	
		        }]
	    	},{
			    layout:'form',							                
		        border:false,
	    	    bodyBorder:true, 	        	    
        		width: '400',
		        items: [{
			            layout:'column',
			            border:false,
		    	        items:[{
			    	            columnWidth:.6,
			        	        layout: 'form',
			            	    labelAlign:"top",
			                	border:false,
				                items: [switchEnviarDummy]
				            },{
			               		columnWidth:.3,
				                layout: 'form',
			    	            border:false,
			        	        items: [switchEnviar]
			            }]
		        }]
		    },clnatura]
	    },
	    	grid,grid2]     
    });
 	
    // define window and show it in desktop
 var wind = new Ext.Window({
            title: '<s:text name="productos.tabla5claves.titulo.principal"/>',
            closable:true,
            buttonAlign:'center',
            width:510,
            height:480,
            autoScroll:true,
            plain:true,
            layout: 'border',
            modal:true,
			items:[formPanel],
  			buttons:[{
  					text:'<s:text name="productos.tabla5claves.btn.grabar"/>',
  					handler: function(){
	  						var boolean1=true;
  							var boolean2=true;
  							var boolean3=true;
  							var boolean4=true;
  							var boolean5=true;
		  					if(nombre.getValue().length == 0) {
		  						nombre.markInvalid("Este dato es requerido");
		  						boolean1=false;
		  					}
		  					if(descripcion.getValue().length == 0) {
		  						descripcion.markInvalid("Este dato es requerido");
		  						boolean2=false;
		  					}
		  					if(clnatura.getValue().length == 0) {
		  						clnatura.markInvalid("Este dato es requerido");
		  						boolean3=false;
		  					}
		  					//alert("store"+store.getTotalCount());
		  					//alert("store"+store.getCount());
		  					if(dataStore.getTotalCount() == 0 && dataStore.getCount()==0) {
		  						Ext.MessageBox.alert('Error', 'almenos debe existir una clave');
		  						boolean4=false;
		  					}
		  						//alert("dataStore"+dataStore.getTotalCount());
		  						//alert("dataStore"+dataStore.getCount());
		  					if(store.getTotalCount() == 0 && store.getCount()==0) {
		  						Ext.MessageBox.alert('Error', 'almenos debe existir un atributo');
		  						boolean5=false;
		  					}
  							if(boolean1 && boolean2 && boolean3 && boolean4 && boolean5) {					 		        
															
								var tipoAcceso='';	        
								var modifica='';
								var envia='';
								if(tipoUnico.getValue()){
									tipoAcceso='U';
								}else{
									tipoAcceso='T';
								}
								if(switchModificar.getValue()){
									modifica='S';
								}else{
									modifica='N';
								}
								if(switchEnviar.getValue()){
									envia='S';
								}else{
									envia='N';
								}
								var params=	"clnatura="+clnatura.getValue()+
  											"&&nombre1="+nombre.getValue()+
  											"&&descripcion1="+descripcion.getValue()+
  											"&&num1="+num.getValue()+
  											"&&tipoDeAcceso="+tipoAcceso+  									
  											"&&switchModificacion="+modifica+  									
  											"&&switchEnviar="+envia+"&&";
  								
  								var numeroDeClavesDeLaBase = dataStore.getTotalCount();
  								for(var i = 0 ; i < numeroDeClavesDeLaBase ; i++){
  									//var numclave= i++;
  									params =params +"listaClaves[" + i + "].numeroClave=" + i + 
  													"&&listaClaves[" + i + "].descripcion=" + dataStore.getAt(i).get('descripcionClave')+
  													"&&listaClaves[" + i + "].descripcionFormato=" + dataStore.getAt(i).get('descripcionFormatoClave')+
  													"&&listaClaves[" + i + "].maximo=" + dataStore.getAt(i).get('maximoClave')+
  													"&&listaClaves[" + i + "].minimo=" + dataStore.getAt(i).get('minimoClave')+"&&";
  								}
  								var	recClavesModificadas = Ext.getCmp('grid-clave').getStore().getModifiedRecords();  						
  								var numeroClavesModificadas = recClavesModificadas.length;						  												
								//alert('numeroDeClavesDeLaBase'+numeroDeClavesDeLaBase);
								//alert('numeroClavesModificadas'+numeroClavesModificadas);
								for (var j=0; j<numeroClavesModificadas; j++) {
									var m = j+numeroDeClavesDeLaBase;
									//alert(m);
									//alert('recModificadoDescricpcion'+recClavesModificadas[j].get('descripcionClave'));
									params =params +"listaClaves[" + m + "].numeroClave=" + m + 
													"&&listaClaves[" + m + "].descripcion=" + recClavesModificadas[j].get('descripcionClave')+ 
													"&&listaClaves[" + m + "].descripcionFormato=" + recClavesModificadas[j].get('descripcionFormatoClave')+
													"&&listaClaves[" + m + "].maximo=" + recClavesModificadas[j].get('maximoClave')+ 
													"&&listaClaves[" + m + "].minimo=" + recClavesModificadas[j].get('minimoClave')+"&&";
								}
								var numeroDeAtributosDeLaBase = store.getTotalCount();
  								//se comenta por que no es necesario enviar todos los atributos solo los agregados ya que no se editan los de la base
  								for(var i = 0 ; i < numeroDeAtributosDeLaBase ; i++){
  									//var numclave= i++;
  									params =params +"listaAtributos[" + i + "].numeroClave=" + store.getAt(i).get('numeroClave') + 
  													"&&listaAtributos[" + i + "].descripcion=" + store.getAt(i).get('descripcionAtributo')+
  													"&&listaAtributos[" + i + "].descripcionFormato=" + store.getAt(i).get('descripcionFormatoAtributo')+
  													"&&listaAtributos[" + i + "].maximo=" + store.getAt(i).get('maximoAtributo')+
  													"&&listaAtributos[" + i + "].minimo=" + store.getAt(i).get('minimoAtributo')+"&&";
  								}
								//var recAtributosModificados = store.getModifiedRecords();
  								var recAtributosModificados = Ext.getCmp('grid-lista-atrib').getStore().getModifiedRecords();
  								var numeroAtributosModificados = recAtributosModificados.length;						  												
								for (var j=0; j<numeroAtributosModificados; j++) {
									var m = j+numeroDeAtributosDeLaBase;
									params =params +//"listaAtributos[" + m + "].numeroClave=" + m + 
													"&&listaAtributos[" + m + "].descripcion=" + recAtributosModificados[j].get('descripcionAtributo')+ 
													"&&listaAtributos[" + m + "].descripcionFormato=" + recAtributosModificados[j].get('descripcionFormatoAtributo')+
													"&&listaAtributos[" + m + "].maximo=" + recAtributosModificados[j].get('maximoAtributo')+ 
													"&&listaAtributos[" + m + "].minimo=" + recAtributosModificados[j].get('minimoAtributo')+"&&";
								}
								//alert("PARAMS"+params);	
								
									var conn2 = new Ext.data.Connection();
				            		conn2.request ({
				            				url:'<s:url namespace="tablaCincoClaves" action="InsertarTabla" includeParams="none"/>',
											//url: 'tablaCincoClaves/InsertarTabla.action',
											method: 'POST',
											successProperty : '@success',
											params : params,
				    	       				callback: function (options, success, response) {
	                       						//alert(success);
	                       						//alert("*"+Ext.util.JSON.decode(response.responseText).success);
	                       						if (success) {
		        	                  					Ext.MessageBox.alert('Status', 'Tabla agregada');
		            	              					dataStoreTabla5Claves.load();
						  								store.commitChanges();
  														dataStore.commitChanges();
		            	              					wind.close();
            	           						} else {
		                       							Ext.MessageBox.alert('Error', 'Tabla no agregada');        	                							
    	                	      				}
       		               					},
					            	   		waitTitle:'<s:text name="ventana.tabla5claves.proceso.mensaje.titulo"/>',
					    					waitMsg:'<s:text name="ventana.tabla5claves.proceso.mensaje"/>'
		    		       			});
		    	       			
  							
		  																  						                   	        		 
							}else{
									Ext.MessageBox.alert('Error', 'Favor de llenar datos requeridos');
							}						   
  						}
  					},{
  						text:'<s:text name="productos.tabla5claves.btn.consulta"/>',
  						id:'id-boton-consulta',
  						//disabled :true,
  						handler: function(){
  							if(dataStore.getTotalCount()!=0){
  								//alert("numEditable"+ Ext.getCmp('id-num1-tabla-5-claves').getValue());
  								creaVentanaDeConsulta(Ext.getCmp('id-num1-tabla-5-claves').getValue());  		
  								var nombreTemporal =nombre.getValue();
  								Ext.getCmp('nombre-editable').setValue(nombreTemporal);
  								var descripcionTemporal = Ext.getCmp('descripcion-test');
  								Ext.getCmp('descripcion-editable').setValue(descripcion.getValue());
  								Ext.getCmp('num-editable').setValue(num.getValue());
  							}else{
	  							Ext.MessageBox.alert('Error', 'Debe existir almenos una clave asociada');
  								
  							}				
  					}
  						
	  			},{
  						text:'<s:text name="productos.tabla5claves.btn.cargaAutomatica"/>',
  						handler:function(){  						
  							//windowCargaAutomatica.show();
  						}
  					},{
  						text:'<s:text name="productos.tabla5claves.btn.cancelar"/>',
  						handler:function(){  						
  							wind.close();
  						}
  						
  					}]
        });
	wind.show();
	function encabezados(){    
    	var maxlength=dataStore.getTotalCount();
		for(ind=0;ind<5;ind++){
			if(ind<maxlength){
				var recordVariableColumns=dataStore.getAt(ind);
				//alert(recordVariableColumns);
				var recordDescripcion=recordVariableColumns.get("descripcionClave");
				//alert(recordDescripcion);
				cmClaves.setColumnHeader(ind,recordDescripcion);
			}else{
				cmClaves.setHidden(ind,true);
			}
		}
	}
	
	function validarClavesObligatorias(recordsClavesObligatorias){
    	if(recordsClavesObligatorias.length == 0){
    		boleanoG=false;
    	}else{
	    	var maxlengthGuarda=dataStore.getTotalCount();
	    	var maxlengthRecords=recordsClavesObligatorias.length;
			for(var irec=0;irec<maxlengthRecords;irec++){
				for(var ind=0;ind<maxlengthGuarda;ind++){
						boleanoG=true;
						banderaGCO=false;
						switch(ind){
							case 0:
								if(recordsClavesObligatorias[irec].get('descripcionClave1').length==0)
									banderaGCO=true;
								break;
							case 1:
								if(recordsClavesObligatorias[irec].get('descripcionClave2').length==0)
									banderaGCO=true;
								break;
							case 2:
								if(recordsClavesObligatorias[irec].get('descripcionClave3').length==0)
									banderaGCO=true;
								break;
							case 3:
								if(recordsClavesObligatorias[irec].get('descripcionClave4').length==0)
									banderaGCO=true;
								break;
							case 4:
								if(recordsClavesObligatorias[irec].get('descripcionClave5').length==0)
									banderaGCO=true;
								break;
								
						}
						if(banderaGCO){
							ind=maxlengthGuarda;
							irec=maxlengthRecords;
							boleanoG=false;
						}
						
						/*
						var idClaveObligatoria='descripcionClave'+ind+'';
						alert(idClaveObligatoria);
						var lengthCO=recordsClavesObligatorias[ind].get('"'+idClaveObligatoria+'"').length;
						alert(lengthCO);
						if(recordsClavesObligatorias[ind].get('"'+idClaveObligatoria+'"').length==0){
							ind=maxlengthGuarda;
							irec=maxlengthRecords;
							boleanoG=false;
						}*/
								
				}
			}
			if(!boleanoG){
				Ext.MessageBox.alert('Error', 'Por favor llene todas las columnas requeridas');
			}
		}
		return boleanoG;
				
	}
	//alert(edita);
  	if(edita){
		//alert('entro');
		var rec = dataStoreTabla5Claves.getAt(edita);
		var nmTabla= rec.get('nick');
		//var camaraAction ='EditaTabla5ClavesCabecera.action?num1='+nmTabla;
		//url4Edit5KeysTabHeader='<s:url namespace="/tablaCincoClaves" action="'+camaraAction+'"/>';
		//alert(url4Edit5KeysTabHeader);	
		var storeListaValores = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: 'tablaCincoClaves/EditaTabla5ClavesCabecera.action?num1='+nmTabla
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'editaCabeceraTabla5claves'
	        	},[
	        		{name: 'nombre1',  type: 'string',  mapping:'nombre'},
	        		{name: 'descripcion1',  type: 'string',  mapping:'descripcion'},
	        		{name: 'num1',  type: 'string',  mapping:'numeroTabla'},
	        		{name: 'enviarTablaErrores',  type: 'string',  mapping:'enviarTablaErrores'},
	        		{name: 'modificaValores',  type: 'string',  mapping:'modificaValores'},	        		
	        		{name: 'ottipoac',  type: 'string',  mapping:'ottipoac'},
	        		{name: 'ottipotb',  type: 'string',  mapping:'ottipotb'},	        		
	        		{name: 'clnatura',  type: 'string',  mapping:'dsNatura'}
	        		//{name: 'claveDependencia',  type: 'string',  mapping:'claveDependencia'},
	        		//{name: 'dsNatura',  type: 'string',  mapping:'dsNatura'},
	        		//{name: 'numero',  type: 'string',  mapping:'cdAtribu'}
	        		            
				]),
			
			remoteSort: true
    	});
    	//alert('despues de declarar el store');
    	storeListaValores.on('load', function(){
    				//alert('load');
					if(storeListaValores.getTotalCount()>0){
    						var recLV= storeListaValores.getAt(0);
    						var recLVETE = recLV.get('enviarTablaErrores');
    						var recLVMV = recLV.get('modificaValores');
    						var recT5CT = recLV.get('ottipoac');
    						//alert("tipoAcceso"+recLV.get('ottipoac'));
                           	Ext.getCmp('id-form-panel-tabla5claves').getForm().loadRecord(recLV);  
                           	Ext.getCmp('id-num1-tabla-5-claves').setValue(recLV.get('num1'));                            
                           	
                           	if(recLVETE == "S"){
                           		Ext.getCmp('id-envia-tabla-error-tabla5claves').setValue(true);
                          		Ext.getCmp('id-envia-tabla-error-tabla5claves').setRawValue("S");
                           	}else{
	                           	Ext.getCmp('id-envia-tabla-error-tabla5claves').setValue(false);
                          		Ext.getCmp('id-envia-tabla-error-tabla5claves').setRawValue("N");
                           	}
                           	if(recLVMV == "S"){
                           		Ext.getCmp('id-modifica-valor-tabla5claves').setValue(true);
                          		Ext.getCmp('id-modifica-valor-tabla5claves').setRawValue("S");
                           	}else{
	                           	Ext.getCmp('id-modifica-valor-tabla5claves').setValue(false);
                          		Ext.getCmp('id-modifica-valor-tabla5claves').setRawValue("N");
                           	}
                           	if(recT5CT == "T"){
                           		Ext.getCmp('radio-tipo-porTamos-tabla5Claves').setValue(true);
                           		Ext.getCmp('radio-tipo-unico-tabla5Claves').setValue(false);
                          		Ext.getCmp('hidden-radio-tipo-de-acceso').setValue("T");
                           	}if(recT5CT == "U"){
                           		Ext.getCmp('radio-tipo-unico-tabla5Claves').setValue(true);
                           		Ext.getCmp('radio-tipo-porTamos-tabla5Claves').setValue(false);
                          		Ext.getCmp('hidden-radio-tipo-de-acceso').setValue("U");
                           	}else{
	                           	//Ext.getCmp('radio-tipo-unico-tabla5Claves').setValue(true);
                          		Ext.getCmp('hidden-radio-tipo-de-acceso').setValue("U");
                           	}
                           	//alert('cargando store');
                           	
							mStoreA = Ext.getCmp('grid-lista-atrib').getStore();
							mStoreA.baseParams = mStoreA.baseParams || {};
                       		mStoreA.baseParams['num1'] = nmTabla;
                       		//alert('cargando store');
					        mStoreA.reload({					        	
								callback : function(r,options,success) {
									//alert('callback');
									if (!success) {
        		    					//  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
							            mStoreA.removeAll();
						            }
				                }
							});                           
							mStoreC = Ext.getCmp('grid-clave').getStore();
							mStoreC.baseParams = mStoreC.baseParams || {};
                       		mStoreC.baseParams['num1'] = nmTabla;
                       		//alert('cargando store');
					        mStoreC.reload({					        	
								callback : function(r,options,success) {
									//alert('callback');
									if (!success) {
        		    					//  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
							            mStoreC.removeAll();
						            }
				                }
							});
							
							Ext.getCmp('hidden-numero-tabla-cinco-claves').setValue(recLV.get('num1'));
							Ext.getCmp('hidden-tipo-transaccion-tabla-5-claves').setValue('2');
					//Ext.getCmp('grid-clave').getStore().load();
					//Ext.getCmp('grid-lista-atrib').getStore().load();
					}
        });
        storeListaValores.load();
		//alert('jamas entra al load');
}

};

</script>