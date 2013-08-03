<%@ include file="/taglibs.jsp"%>
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/productos/tablaApoyo5Claves/tablaApoyo5Claves.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/resources/scripts/utilities/checkColumnPlugin.js"></script>

<script type="text/javascript">
ExpresionesVentana2 = function(codigoExpresion, codigoExpresionSession, store, encabezado, row ){
//alert('store'+store);
//alert('encabezado'+encabezado);
//alert('row'+row);
var cabecera;
var dsComboV;
var record4editing;
var detailEl;
var win;
var urlEditarExpresion='expresiones/EditarExpresion.action'; 	
var temporal = -1;
//var urlEditarExpresion='librerias/CargaExpresionReglaNegocio.action';
//alert(codigoExpresionSession);
/****************************************
Editar Expresion
****************************************/
	
	if(codigoExpresion!=null && codigoExpresion!='' && codigoExpresion!="undefined"){
		urlEditarExpresion+='?codigoExpresion='+codigoExpresion;					
	}else{
		urlEditarExpresion+='?codigoExpresion=0';					
	}
	
	/*
	if(codigoExpresionSession!=null && codigoExpresionSession!='' && codigoExpresionSession!="undefined"){
		urlEditarExpresion+='?codigoExpresionSession='+codigoExpresionSession;					
	}else{
		urlEditarExpresion+='codigoExpresionSession=EXPRESION';					
	}*/
	editaExpresion();
function editaExpresion(){
		//alert("edit expresion + urlEditarExpresion="+urlEditarExpresion);
		var storeExpresion = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: urlEditarExpresion
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'editarExpresion',   
            	totalProperty: 'totalCount',
            	id: 'editarExpresion'            	         	
	        	},[
	        		{name: 'descripcion',  type: 'string',  mapping:'otExpresion'},
	        		{name: 'switchRecalcular',  type: 'string',  mapping:'switchRecalcular'},
	        		{name: 'codigoExpresion',  type: 'string',  mapping:'codigoExpresion'}	        		            
				]),			
			remoteSort: true
    	});
    	storeExpresion.setDefaultSort('descripcion', 'desc');
    	    	
    	storeExpresion.on('load', function(){   
    						//alert(codigoExpresion);
    						//alert(Ext.getCmp('hidden-codigo-expresio-expresion').getValue());
    						if(storeExpresion.getTotalCount()>0){                        
		                           var rec = storeExpresion.getAt(0);                  
        		                   var swRecalcular= rec.get('switchRecalcular');
                		           Ext.getCmp('forma-expresiones').getForm().loadRecord(rec);
                        		   if(swRecalcular == 'S'){
		                           		Ext.getCmp('switch-recalcular-expresion-simple').setValue(true);
        		                   		Ext.getCmp('switch-recalcular-expresion-simple').setRawValue("S");
                		           }if(swRecalcular == 'N'){
                        		   		Ext.getCmp('switch-recalcular-expresion-simple').setValue(false);
                           				//Ext.getCmp('switch-recalcular-expresion-simple').setRawValue("N");
		                           }
        		                   dsComboV.load();
        		                   //alert(Ext.getCmp('hidden-codigo-expresio-expresion').getValue());
                	        }else{
                	        	Ext.getCmp('forma-expresiones').getForm().reset();
                	        }
		});
        storeExpresion.load();
        urlEditarExpresion='expresiones/EditarExpresion.action'; 
}
/****************************************
Editar Expresion
****************************************/
/****************************************
Switch de Cabeceras
****************************************/
if(encabezado){
	//alert('entro al if de encabezado');
	switch(encabezado){
		case '0':	
			//alert('entro al case 0');
			var storeCampo = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
					url: 'datosFijos/CatalogoCampo.action'
		        }),
    		    reader: new Ext.data.JsonReader({
        		    root: 'catalogoCampo',
		            id: 'readerCatalogoCampo'
	    	    },[
		           {name: 'claveCampo', type: 'string',mapping:'key'},
        		   {name: 'valorCampo', type: 'string',mapping:'value'}    
		        ]),
				remoteSort: true
		    });
			storeCampo.setDefaultSort('claveCampo', 'desc');
			var storeBloque = new Ext.data.Store({
        		proxy: new Ext.data.HttpProxy({
					url: 'datosFijos/CatalogoBloque.action'
        		}),
		        reader: new Ext.data.JsonReader({
        		    root: 'catalogoBloque',
		            id: 'readerCatalogoBloque'
	    		},[
		           {name: 'claveBloque', type: 'string',mapping:'key'},
        		   {name: 'valorBloque', type: 'string',mapping:'value'}    
		        ]),
				remoteSort: true
		    });
		    storeBloque.setDefaultSort('claveBloque', 'desc');
		    storeBloque.load();   
		    var comboBloque = new Ext.form.ComboBox({
    			tpl: '<tpl for="."><div ext:qtip="{claveBloque}" class="x-combo-list-item">{valorBloque}</div></tpl>',
    			store: storeBloque,
	    		id:'combo-bloque-value',
    			displayField:'valorBloque',
    			valueField:'claveBloque',
    			allowBlank:false,
	    		blankText : 'Dato requerido',
    			width:150,
    			typeAhead: true,
    			mode: 'local',
	    		triggerAction: 'all',
    			emptyText:'Selecciona un bloque',
    			selectOnFocus:true,
    			fieldLabel: "Bloque",
	    		forceSelection:true,
    			name:"descripcionBloque",
	          	focusAndSelect : function(record) {
    		     	var index = typeof record === 'number' ? record : this.store.indexOf(record);
        		 		this.select(index, this.isExpanded());
         				this.onSelect(this.store.getAt(record), index, this.isExpanded());
	       			},
	          	onSelect : function(record, index, skipCollapse){
    		     	if(this.fireEvent('beforeselect', this, record, index) !== false){
           				this.setValue(record.data[this.valueField || this.displayField]);
			       		if( !skipCollapse ) {
		            		this.collapse();
        		   		}
		           		this.lastSelectedIndex = index + 1;
        		   		this.fireEvent('select', this, record, index);
		         	}	
		         	//carga el otro combo
        		 	params="claveBloque="+this.getValue();
		         	var conn = new Ext.data.Connection();
					conn.request ({
						url: 'datosFijos/SubeClaveBloque.action',
						method: 'POST',
						successProperty : '@success',
						params : params,
					    callback: function (options, success, response) {
                    		if (Ext.util.JSON.decode(response.responseText).success == false) {
	                				//Ext.Msg.alert('Error', 'No se pudieron guardar los cambios');                        							
                       		} else {
		               				//Ext.Msg.alert('Status', 'Se han guardado con éxito los cambios');
		                    		storeCampo.load();
    	                    }
	    	       		},
	        	      	waitMsg: 'Espere por favor....'
		      		});
				}
			});       

	       var comboCampo = new Ext.form.ComboBox({
    			tpl: '<tpl for="."><div ext:qtip="{claveCampo}" class="x-combo-list-item">{valorCampo}</div></tpl>',
    			store: storeCampo,
    			id:'combo-campo-value',
	    		displayField:'valorCampo',
    			valueField:'claveCampo',
    			allowBlank:false,
    			blankText : 'Dato requerido',
	    		width:150,
    			typeAhead: true,
    			mode: 'local',
    			triggerAction: 'all',
	    		emptyText:'Selecciona un campo',
    			selectOnFocus:true,
    			//forceSelection:true,
    			fieldLabel: "Campo",
	    		name:"descripcionCampo"
			});   
			cabecera = new Ext.form.FormPanel({
            	collapsible:true,
            	margins:'0 0 0 0',
            	frame:true,
            	url:'datosFijos/InsertarDatoFijo.action',
            	id:'cabecera-form',
       			bodyStyle:'padding:5px',
        		width:800,//700
        		height:85,
        		laberAlign:'top',
        		title:'valores por defecto campos fijos',
        		region:'north',
                items: [{xtype:'hidden',id:'hidden-combo-bloque-value',name:'claveBloque'},
						{xtype:'hidden',id:'hidden-combo-campo-value',name:'claveCampo'},
						{
 				    		border: false,
 				    		layout: 'form',
				    		items: [{
        								layout:'column',
        								border: false,
            							items:[{
				                			columnWidth:.5,
                							layout: 'form',
                							border: false,
				                				items: [
				                					comboBloque
				                				]
        								},{
               								columnWidth:.5,
			                				layout: 'form',
			                				border: false,
               									items: [
				                					comboCampo    						
               									]
				    					}]        			
		        			}]
		        }]
	    	});
    		break;
    	case '1':
			function validaVariable(v) {
				if( !Ext.isEmpty(Ext.getCmp('nombre-cabecera')) ) {
					var valor = v.toUpperCase();
					var ext = Ext.getCmp('nombre-cabecera').getValue().toUpperCase();
					if ( valor != ' ' && valor != ''  && valor.search( ext ) != -1 ) {
						return "No se puede incluir la misma variable temporal en Descripci&oacute;n";
					} else {
						return true;
					}
				}else {
					return true;
				}
			}

	    	var nombreAutorizaciones = new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.nombre"/>',
                    id: 'nombre-cabecera',
                    allowBlank:false,
                    blankText : '<s:text name="librerias.cabecera.nombre.req"/>',
                    name: 'nombreCabecera',
                    width:150  
   			});
   	
		   	var descripcionAutorizaciones= new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.descripcion"/>',
                    allowBlank:false,
                    blankText : '<s:text name="librerias.cabecera.descripcion.req"/>',
                    name: 'descripcionCabecera',
                    width:150
		   	});
   	
		   	var nivelAutorizaciones= new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.nivel"/>',
                    allowBlank:false,
                    blankText : '<s:text name="librerias.cabecera.nivel.req"/>',
                    name: 'nivel',
                    width:150     
		   	});
   	
   	
			cabecera = new Ext.form.FormPanel({
            	collapsible:true,
            	margins:'0 0 0 0',
            	frame:true,
            	url:'librerias/InsertaReglaNegocio.action?numeroGrid=4',
            	id:'cabecera-form',
       			bodyStyle:'padding:5px',
        		width:800,//700
        		height:100,
        		title:'<s:text name="librerias.titulo.autorizacion"/>',
        		region:'north',
                items: [{
 				    		border: false,
 				    		layout: 'form',
				    		items: [{
        								layout:'column',
        								border: false,
            							items:[{
				                			columnWidth:.5,
                							layout: 'form',
                							border: false,
				                				items: [
				               						nombreAutorizaciones
			                					]
        								},{
               								columnWidth:.5,
			                				layout: 'form',
			                				border: false,
               									items: [
               										descripcionAutorizaciones
               									]
				    					}]
		        			},{
        								layout:'column',
        								border: false,
            							items:[{
				                			columnWidth:.5,
                							layout: 'form',
                							border: false,
				                				items: [
				                					nivelAutorizaciones
				                				]
        								}]        			
		        			}]
		        }]
		    });
    		break;	
    	case '2':
			function validaVariable(v) {
				if( !Ext.isEmpty(Ext.getCmp('nombre-cabecera')) ) {
					var valor = v.toUpperCase();
					var ext = Ext.getCmp('nombre-cabecera').getValue().toUpperCase();
					if ( valor != ' ' && valor != ''  && valor.search( ext ) != -1 ) {
						return "No se puede incluir la misma variable temporal en Descripci&oacute;n";
					} else {
						return true;
					}
				}else {
					return true;
				}
			}

    		var nombreTarificacion = new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.nombre"/>',
                    id: 'nombre-cabecera',
                    allowBlank:false,
                    name: 'nombreCabecera',
                    blankText : '<s:text name="librerias.cabecera.nombre.req"/>',
                    width:150  
		   	});
   	
		   	var descripcionTarificacion = new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.descripcion"/>',
                    allowBlank:false,
                    name: 'descripcionCabecera',
                    blankText : '<s:text name="librerias.cabecera.descripcion.req"/>',
                    width:150
		   	});
   	
		   	var tipoTarificacionds = new Ext.data.Store({
        			proxy: new Ext.data.HttpProxy({
						url: 'librerias/CargaComboTipo.action'+'?comboTipo='+'tarificacion'
			        }),
			        reader: new Ext.data.JsonReader({
            			root: 'listaTipo',
			            id: 'tipoDeTarificacion'
	        		},[
			           {name: 'claveTipo', type: 'string',mapping:'key'},
			           {name: 'valorTipo', type: 'string',mapping:'value'}    
			        ]),
					remoteSort: true
		    });
	       tipoTarificacionds.setDefaultSort('tipoDeTarificacion', 'desc'); 
       
	       var tipoTarificacion = new Ext.form.ComboBox({
    			tpl: '<tpl for="."><div ext:qtip="{claveTipo}" class="x-combo-list-item">{valorTipo}</div></tpl>',
    			store: tipoTarificacionds,
    			displayField:'valorTipo',
	    		allowBlank:false,
    			blankText : '<s:text name="librerias.cabecera.tipoTarificacion.req"/>',
    			width:150,
    			//maxLength : '30',
	    		//maxLengthText : 'Treinta caracteres maximo',
    			typeAhead: true,
    			mode: 'local',
    			triggerAction: 'all',
	    		emptyText:'<s:text name="librerias.cabecera.select.tipoTarificacion"/>',
    			selectOnFocus:true,
    			fieldLabel: '<s:text name="librerias.cabecera.tipoTarificacion"/>',
    			name:"descripcionTipo",
	          	focusAndSelect : function(record) {
	    	     	var index = typeof record === 'number' ? record : this.store.indexOf(record);
        	 		this.select(index, this.isExpanded());
         			this.onSelect(this.store.getAt(record), index, this.isExpanded());
    	   		},
        	  	onSelect : function(record, index, skipCollapse){
	         		if(this.fireEvent('beforeselect', this, record, index) !== false){
    	    	   		this.setValue(record.data[this.valueField || this.displayField]);
        	   			if( !skipCollapse ) {
            				this.collapse();
		           		}
        		   		this.lastSelectedIndex = index + 1;
		           		this.fireEvent('select', this, record, index);
        		 	}
					var valor=record.get('claveTipo');
					Ext.getCmp('hidden-combo-tipo-tarificacion').setValue(valor);
				}
			});            
		   	tipoTarificacionds.load();
   	
			cabecera = new Ext.form.FormPanel({
            	collapsible:true,
            	margins:'0 0 0 0',
            	frame:true,
            	url:'librerias/InsertaReglaNegocio.action?numeroGrid=5',
            	id:'cabecera-form',
       			bodyStyle:'padding:5px',
        		width:800,//700
        		height:100,
        		title:'<s:text name="librerias.titulo.conceptoTarif"/>',
        		region:'north',
                items: [{xtype:'hidden',id:'hidden-combo-tipo-tarificacion',name:'tipo'},
						{
 				    		border: false,
 				    		layout: 'form',
				    		items: [{
        								layout:'column',
        								border: false,
            							items:[{
				                			columnWidth:.5,
                							layout: 'form',
                							border: false,
				                				items: [
				               						nombreTarificacion
			                					]
        								},{
               								columnWidth:.5,
			                				layout: 'form',
			                				border: false,
               									items: [
               										descripcionTarificacion
               									]
				    					}]
		        			},{
        								layout:'column',
        								border: false,
            							items:[{
				                			columnWidth:.5,
                							layout: 'form',
                							border: false,
				                				items: [
				                					tipoTarificacion
				                				]
        								}]        			
		        			}]
		        }]
	   		 });
    		break;
    	case '3':
			function validaVariable(v) {
				if( !Ext.isEmpty(Ext.getCmp('nombre-cabecera')) ) {
					var valor = v.toUpperCase();
					var ext = Ext.getCmp('nombre-cabecera').getValue().toUpperCase();
					if ( valor != ' ' && valor != ''  && valor.search( ext ) != -1 ) {
						return "No se puede incluir la misma variable temporal en Descripci&oacute;n";
					} else {
						return true;
					}
				}else {
					return true;
				}
			}

	    	var nombreCondiciones = new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.nombre"/>',
                    id: 'nombre-cabecera',
                    allowBlank:false,
                    blankText : '<s:text name="librerias.cabecera.nombre.req"/>',
                    name: 'nombreCabecera',
                    width:150  
	   		});
   	
   			var descripcionCondiciones= new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.descripcion"/>',
                    allowBlank:false,
                    blankText : '<s:text name="librerias.cabecera.descripcion.req"/>',
                    name: 'descripcionCabecera',
                    width:150
		   	});
			cabecera = new Ext.form.FormPanel({
            	collapsible:true,
            	margins:'0 0 0 0',
            	frame:true,
            	url:'librerias/InsertaReglaNegocio.action?numeroGrid=3',
            	id:'cabecera-form',
       			bodyStyle:'padding:5px',
        		width:800,//700
        		height:100,
        		title:'<s:text name="librerias.titulo.condicion"/>',
        		region:'north',
                items: [{
 				    		border: false,
 				    		layout: 'form',
				    		items: [
				            	nombreCondiciones,
				            	descripcionCondiciones
				           ]	
		        }]
		    });
    		break;
    	case '4':
			function validaVariable(v) {
				if( !Ext.isEmpty(Ext.getCmp('nombre-cabecera')) ) {
					var valor = v.toUpperCase();
					var ext = Ext.getCmp('nombre-cabecera').getValue().toUpperCase();
					if ( valor != ' ' && valor != ''  && valor.search( ext ) != -1 ) {
						return "No se puede incluir la misma variable temporal en Descripci&oacute;n";
					} else {
						return true;
					}
				}else {
					return true;
				}
			}

	    	var nombreValidaciones = new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.nombre"/>',
                    id: 'nombre-cabecera',
                    allowBlank:false,
                    name: 'nombreCabecera',
                    blankText : '<s:text name="librerias.cabecera.nombre.req"/>',
                    width:155  
   			});
   	
		   	var descripcionValidaciones= new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.descripcion"/>',
                    allowBlank:false,
                    name: 'descripcionCabecera',
                    blankText : '<s:text name="librerias.cabecera.descripcion.req"/>',
                    width:155
		   	});
   	
		   	var tipods = new Ext.data.Store({
        		proxy: new Ext.data.HttpProxy({
					url: 'librerias/CargaComboTipo.action'+'?comboTipo='+'validaciones'
        		}),
		        reader: new Ext.data.JsonReader({
        		    root: 'listaTipo',
		            id: 'tipoDeValidaciones'
	    		},[
		           {name: 'claveTipo', type: 'string',mapping:'key'},
        		   {name: 'valorTipo', type: 'string',mapping:'value'}    
		        ]),
				remoteSort: true
		    });
		    tipods.setDefaultSort('tipoDeValidaciones', 'desc'); 
       
	        var tipoValidaciones = new Ext.form.ComboBox({
	    		tpl: '<tpl for="."><div ext:qtip="{claveTipo}" class="x-combo-list-item">{valorTipo}</div></tpl>',
    			store: tipods,
    			displayField:'valorTipo',
    			allowBlank:false,
	    		blankText : '<s:text name="librerias.cabecera.tipoValidacion.req"/>',
    			width:155,
    			//maxLength : '30',
    			//maxLengthText : 'Treinta caracteres maximo',
	    		typeAhead: true,
    			mode: 'local',
    			triggerAction: 'all',
    			emptyText:'<s:text name="librerias.cabecera.select.tipoValidacion"/>',
	    		selectOnFocus:true,
    			fieldLabel: '<s:text name="librerias.cabecera.tipoValidacion"/>',
    			name:"descripcionTipo",
		       	focusAndSelect : function(record) {
		         	var index = typeof record === 'number' ? record : this.store.indexOf(record);
        	 		this.select(index, this.isExpanded());
	         		this.onSelect(this.store.getAt(record), index, this.isExpanded());
    	   		},
	          	onSelect : function(record, index, skipCollapse){
    		     	if(this.fireEvent('beforeselect', this, record, index) !== false){
           				this.setValue(record.data[this.valueField || this.displayField]);
		           		if( !skipCollapse ) {
        		    		this.collapse();
		           		}
        		   		this.lastSelectedIndex = index + 1;
           				this.fireEvent('select', this, record, index);
		         	}
					var valor=record.get('claveTipo');
					Ext.getCmp('hidden-combo-tipo-validaciones').setValue(valor);
				}
			});            
   			tipods.load();

		   	var mensajeValidaciones= new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.mensajeValidacion"/>',
                    allowBlank:false,
                    name: 'mensaje',
                    blankText : '<s:text name="librerias.cabecera.mensajeValidacion.req"/>',
                    width:155     
		   	});
		   	
			cabecera = new Ext.form.FormPanel({
            	collapsible:true,
            	margins:'0 0 0 0',
            	frame:true,
            	url:'librerias/InsertaReglaNegocio.action?numeroGrid=2',
            	id:'cabecera-form',
       			bodyStyle:'padding:5px',
        		width:800,//700
        		height:100,
        		title:'<s:text name="librerias.titulo.validacion"/>',
        		region:'north',
                items: [{xtype:'hidden',id:'hidden-combo-tipo-validaciones',name:'tipo'},
						{
 				    		border: false,
 				    		layout: 'form',
				    		items: [{
        								layout:'column',
        								border: false,
            							items:[{
				                			columnWidth:.5,
                							layout: 'form',
                							border: false,
				                				items: [
				               						nombreValidaciones
			                					]
        								},{
               								columnWidth:.5,
			                				layout: 'form',
			                				border: false,
               									items: [
               										descripcionValidaciones
               									]
				    					}]
        		
		        			},{
        								layout:'column',
        								border: false,
            							items:[{
				                			columnWidth:.5,
                							layout: 'form',
                							border: false,
				                				items: [
				                					tipoValidaciones
				                				]
        								},{
               								columnWidth:.5,
			                				layout: 'form',
			                				border: false,
               									items: [
				                					mensajeValidaciones    						
               									]
				    					}]        			
		        			}]
		        }]
		    });
    		break;
    	case '5':
			function validaVariable(v) {
				if( !Ext.isEmpty(Ext.getCmp('nombre-cabecera')) ) {
					var valor = v.toUpperCase();
					var ext = Ext.getCmp('nombre-cabecera').getValue().toUpperCase();
					if ( valor != ' ' && valor != ''  && valor.search( ext ) != -1 ) {
						return "No se puede incluir la misma variable temporal en Descripci&oacute;n";
					} else {
						return true;
					}
				}else {
					return true;
				}
			}

			var nombreVariableTemporal = new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.nombre"/>',
                    id: 'nombre-cabecera',
                    name: 'nombreCabecera',
                    allowBlank:false,
                    blankText : '<s:text name="librerias.cabecera.nombre.req"/>',
                    width:150  
		   	});
   
		   	var descripcionVariableTemporal= new Ext.form.TextField({
                    fieldLabel: '<s:text name="librerias.cabecera.descripcion"/>',
                    name: 'descripcionCabecera',
                    allowBlank:false,
                    blankText : '<s:text name="librerias.cabecera.descripcion.req"/>',
                    width:150
		   	});
   	
			cabecera = new Ext.form.FormPanel({
				collapsible:true,
            	margins:'0 0 0 0',
            	url:'librerias/InsertaReglaNegocio.action?numeroGrid=1',
            	id:'cabecera-form',
            	frame:true,
       			bodyStyle:'padding:5px',
        		width:800,//700
        		height:100,
        		title:'Variables Temporales',
        		region:'north',
                items: [{
 				    		border: false,
 				    		layout: 'form',
				    		items: [
				            	nombreVariableTemporal,
				            	descripcionVariableTemporal
				           ]	
		        }]
		    });
    		break;
	    default:
    		break;	
	}
}
/************************************************************************
variables locales de expresion
************************************************************************/
	var dsComboTabla
	var dsComboColumna;
	var dsComboClave;

	//alert(codigoExpresionBase);
	dsComboTabla = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: '<s:url namespace="expresiones" action="ComboTabla" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaTablaJson',
            id: 'comboAbuelo'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'}      
        ]),
		remoteSort: true
    });
  	dsComboTabla.load();
  	
  	dsComboColumna = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: '<s:url namespace="expresiones" action="ComboColumna" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaColumnaJson',
            id: 'dsComboColumna'
	        },[
           {name: 'key', type: 'string',mapping:'key'},
           {name: 'value', type: 'string',mapping:'value'},
           {name: 'nick', type: 'string',mapping:'nick'}      
        ]),
		remoteSort: true,		
        baseParams: {tabla:'undefined'}
    });
    //dsComboColumna.load();



    dsComboClave = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: '<s:url namespace="expresiones" action="ComboClave" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaClave',
            id: 'comboHijo'
	        },[
           {name: 'clave', type: 'string',mapping:'clave'},
           {name: 'expresion', type: 'string',mapping:'expresion'},             
           {name: 'switchRecalcular', type: 'boolean',mapping:'switchRecalcualar'},
           {name: 'recalcular', type: 'string',mapping:'recalcular'},
           {name: 'idBase', type: 'string',mapping:'idBase'},
           {name: 'codigoSecuencia', type: 'string',mapping:'codigoSecuencia'}          
        ]),
		remoteSort: true,		
        baseParams: {tabla:'undefined',
         			 columna:'undefined',
         			 nombreVariableLocal:'undefined'}
    });
  	//dsComboClave.load();
  	
    dsComboV = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: 'expresiones/ComboVariables.action'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaComboVariables',
            id: 'comboVariables'
	        },[
           {name: 'codigoExpresion', type: 'float',mapping:'codigoExpresion'},
           {name: 'nombre', type: 'string',mapping:'codigoVariable'},
           {name: 'tabla', type: 'string',mapping:'tabla'},
           {name: 'descripcionTabla', type: 'string',mapping:'descripcionTabla'},
           {name: 'columna', type: 'float',mapping:'columna'},
           {name: 'descripcionColumna', type: 'string',mapping:'descripcionColumna'},
           {name: 'switchFormato', type: 'string',mapping:'switchFormato'},
           
        ]),
         baseParams: {codigoExpresion:codigoExpresion},
		remoteSort: true
    });
    dsComboV.load(); 

	var comboVariables =new Ext.form.ComboBox({
    	                    tpl: '<tpl for="."><div ext:qtip="{nombre}. {tabla}" class="x-combo-list-item">{nombre}</div></tpl>',
							store: dsComboV,
						    displayField:'nombre',
						    valueField: 'nombre',
					    	typeAhead: true,
					    	//labelAlign: 'top',
					    	allowBlank:false,
						    mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="productos.valida.expresion.variables"/>',
					    	selectOnFocus:true,
					    	fieldLabel: '<s:text name="productos.config.expresion.variables"/>',
					    	name:"claveSeleccionada",
					    	width:220,
				    		selectFirst : function() {
									this.focusAndSelect(this.store.data.first());
							},
					    	focusAndSelect : function(record) {
									var index = typeof record === 'number' ? record : this.store.indexOf(record);
									this.select(index, this.isExpanded());
									this.onSelect(this.store.getAt(record), index, this.isExpanded());
							},
					    	onSelect : function(record, index, skipCollapse){
									if(this.fireEvent('beforeselect', this, record, index) !== false){
											this.setValue(record.data[this.valueField || this.displayField]);
											if( !skipCollapse ) {
												this.collapse();
											}
											this.lastSelectedIndex = index + 1;
											this.fireEvent('select', this, record, index);
									}
					                    if(comboVariables.getValue()!=null && comboVariables.getValue()!="" && comboVariables.getValue()!="undefined"){
						                        if( Ext.getCmp('id-forma-variables-expresion')){
						                        	recVariableExpresion = dsComboV.getAt(index);
						                        	Ext.getCmp('id-forma-variables-expresion').getForm().loadRecord(recVariableExpresion);
						                        	var claveVariableLocal = recVariableExpresion.get('nombre');
						                        	//alert('claveVariableLocal1 = ' + claveVariableLocal);
						                        	//claveVariableLocal = this.getValue();
						                        	//alert('claveVariableLocal2 = ' + claveVariableLocal);
										    		Ext.getCmp('hidden-codigo-nombre-variable-local').setValue(claveVariableLocal);  
										    		var claveTabla = recVariableExpresion.get('tabla');
										    		var claveColumna = recVariableExpresion.get('columna');										    		
										    		Ext.getCmp('id-combo-tabla-variable-expresion').setValue(claveTabla);
										    		Ext.getCmp('hidden-codigo-columna-variable-expresion').setValue(claveColumna);										    		
										    		dsComboTabla.load();
										    		dsComboColumna.baseParams['tabla'] = claveTabla;
									                dsComboColumna.load({
								                		callback : function(r,options,success) {
									                        if (!success) {
						                                       	dsComboColumna.removeAll();
								                            }
								                        }
									                });
									                dsComboClave.baseParams['tabla'] = claveTabla;                
									                dsComboClave.baseParams['columna'] = claveColumna;
									                dsComboClave.baseParams['nombreVariableLocal'] = claveVariableLocal;
													dsComboClave.load({
								                		callback : function(r,options,success) {
									                        if (!success) {
						        		                        //Ext.MessageBox.alert('Aviso','No se encontraron registros');  
						                                       	dsComboClave.removeAll();
							                                }
								                        }
									                });
						                        }
       				            	       	}else{
		    							    	Ext.MessageBox.alert('alert', 'no ha seleccionado una variable');
		    							    }  
							}
			});

  	var columnModel = new Ext.grid.ColumnModel([
        {id:'clave',header: "Clave", dataIndex: 'clave'},
        {header: "Expresion", dataIndex: 'expresion'},
        {header: "Recalcular", dataIndex: 'switchRecalcular'}
    ]);

    var recalcular= new Ext.form.Checkbox({
   			id:'recalcular-check-variables-expresion',
            name:'recalcular',
            boxLabel: '<s:text name="productos.variableLoc.recalcular"/>',
          	hideLabel:true
    });

   	var expresion= new Ext.form.TextField({fieldLabel:'Expresion',disabled:true,name:'expresion', maxLength :'240'});
	var comboTabla =new Ext.form.ComboBox({
							id:'id-combo-tabla-variable-expresion',
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsComboTabla,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
					    	labelAlign: 'top',
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="productos.valida.variableLoc.tabla"/>',
					    	selectOnFocus:true,
				          	allowBlank:false,
				          	blankText : '<s:text name="productos.valida.variableLoc.tabla.req"/>',
						    fieldLabel: '<s:text name="productos.variableLoc.tabla"/>',
						    listWidth:220,
						    width:220,
					    	name:"descripcionTabla",
					    	selectFirst : function() {
									this.focusAndSelect(this.store.data.first());
							},
					    	focusAndSelect : function(record) {
									var index = typeof record === 'number' ? record : this.store.indexOf(record);
									this.select(index, this.isExpanded());
									this.onSelect(this.store.getAt(record), index, this.isExpanded());
							},
					    	onSelect : function(record, index, skipCollapse){
									if(this.fireEvent('beforeselect', this, record, index) !== false){
											this.setValue(record.data[this.valueField || this.displayField]);
											if( !skipCollapse ) {
												this.collapse();
											}
											this.lastSelectedIndex = index + 1;
											this.fireEvent('select', this, record, index);
									}
									valor = record.get('key');
									Ext.getCmp('hidden-codigo-tabla-variable-expresion').setValue(valor);	
									comboColumna.reset();
									//comboClave.reset();
									dsComboClave.removeAll();									
									dsComboColumna.baseParams['tabla'] = valor;
					                dsComboColumna.load({
                							callback : function(r,options,success) {
							                        if (!success) {
        		    					                    //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
					                                       	dsComboColumna.removeAll();
                    					            }
					                        }
					                });
					               
							}
			});
	
	var comboColumna =new Ext.form.ComboBox({
							id:'id-combo-columna-variable-expresion',
    	                    tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: dsComboColumna,
						    displayField:'value',
						    valueField: 'key',
					    	typeAhead: true,
					    	labelAlign: 'top',
				          	allowBlank:false,
				          	blankText : '<s:text name="productos.valida.variableLoc.columna.req"/>',
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'<s:text name="productos.valida.variableLoc.columna"/>',
					    	selectOnFocus:true,
						    fieldLabel: '<s:text name="productos.variableLoc.columna"/>',
					    	name:"descripcionColumna",
					    	width:220,
					    	selectFirst : function() {
									this.focusAndSelect(this.store.data.first());
							},
					    	focusAndSelect : function(record) {
									var index = typeof record === 'number' ? record : this.store.indexOf(record);
									this.select(index, this.isExpanded());
									this.onSelect(this.store.getAt(record), index, this.isExpanded());
							},
					    	onSelect : function(record, index, skipCollapse){
									if(this.fireEvent('beforeselect', this, record, index) !== false){
											this.setValue(record.data[this.valueField || this.displayField]);
											if( !skipCollapse ) {
												this.collapse();
											}
											this.lastSelectedIndex = index + 1;
											this.fireEvent('select', this, record, index);
									}
									valor = record.get('key');
									Ext.getCmp('hidden-switch-formato-variable-expresion').setValue(record.get('nick'));	
									Ext.getCmp('hidden-codigo-columna-variable-expresion').setValue(valor);		            													
									dsComboClave.baseParams['tabla'] = Ext.getCmp('id-combo-tabla-variable-expresion').getValue();                
						            dsComboClave.baseParams['columna'] = valor;
						            dsComboClave.baseParams['nombreVariableLocal'] = 'undefined';
						            dsComboClave.load({
					                		callback : function(r,options,success) {
		            					            if (!success) {
						       		                        //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                        					               	dsComboClave.removeAll();
						                            }
					                        }
					                });
					                
							}
					    	
			});

    var checkColumn = new Ext.grid.CheckColumn({
       header: "Recalcular",
       dataIndex: 'switchRecalcular',
       width: 70,
       align:'center'
    });
    
	cmClaves = new Ext.grid.ColumnModel([{
           id:'clave',
           header: "Clave",
           dataIndex: 'clave',
           width: 100,
           editor: new Ext.form.TextField({
               allowBlank: false
           })
        },{
           header: "Expresión",
           dataIndex: 'expresion',
           width: 240,
           editor: new Ext.form.TextField({
               allowBlank: false
           })
        },checkColumn
    ]);
 cmClaves.setEditable(0,false);
 gridEditableClaves = new Ext.grid.EditorGridPanel({
    	//id:'grid-editable-claves',
    	id:'gridpanel-editableClaves',
        store: dsComboClave,
        cm: cmClaves,
        autoScroll:true,
        width: 530,//430
        height:100,//autoHeight:true,
        autoExpandColumn:'clave',
        title:'<s:text name="productos.variableLoc.subtitulo.claves"/>',
        collapsible:true,
        frame:true,
        plugins:checkColumn,
        clicksToEdit:1,
		viewConfig: {
	        forceFit: true
	    }
    });
    var formPanelVariablesLocales = new Ext.FormPanel({
    	id:'id-forma-variables-expresion',
        //frame:true,
        //region:'center',
        //layout:'column',        
        //bodyStyle:'padding:5px 5px 0',
        heigth:300,
        width: 550,//450
        frame:true,
        border:false,
        url:'expresiones/AgregarVariable.action',
        items: [{xtype:'hidden', id:'hidden-codigo-tabla-variable-expresion', name:'tabla'},
		        {xtype:'hidden', id:'hidden-codigo-columna-variable-expresion', name:'columna'},
		        {xtype:'hidden', id:'hidden-switch-formato-variable-expresion', name:'switchFormato'},
		        {xtype:'hidden', id:'hidden-codigo-nombre-variable-local', name:'codigoNombreVariableLocal'},
		        {xtype:'hidden', id:'hidden-codigo-edita-variable-expresion', name:'editaVariableLocal'},
		        {
		        	layout:'column',
		        	width: 550,//450
	    	    	border:false,
	        		items:[{
			        		columnWidth:.68,
    			    		layout:'form',
        					border:false,
        					width:380,//280
        					items:[comboVariables]       
			    	    },{
    	    				columnWidth:.32,
	        				layout:'form',
        					border:false,
		        			items:[{
	               		        xtype:'button',
   			   				    text:'<s:text name="productos.config.expresion.btn.eliminarVar"/>',
   							    handler:function(){
   							    		var valorCombo=comboVariables.getValue();
   							    		if(comboVariables.isDirty()){
   	           							       
   	           							    var params="codigoExpresion=" + Ext.getCmp('hidden-codigo-expresio-expresion').getValue();
   	           							    params+= "&&claveSeleccionada="+valorCombo;    	       							    			
       							    		var url4delete='expresiones/EliminarVariable.action';    	    	       							    		
      							    		Ext.MessageBox.confirm('Mensaje','Realmente deseas eliminar el elemento?', function(btn) {
									            if(btn == 'yes'){   
												    tab2.form.load({	
														url: url4delete,
														params:params,
													    waitMsg:"Procesando...",
													    failure: function(form, action) {
														    comboVariables.reset();
													    	dsComboV.load();
													    	comboTabla.reset();
													    	dsComboTabla.load();
													    	comboColumna.reset();
												    		dsComboColumna.baseParams['tabla'] = '';
											                dsComboColumna.load({
										                		callback : function(r,options,success) {
											                        if (!success) {
						        		                               	dsComboColumna.removeAll();
								        		                    }
								                		        }
											                });
											                dsComboClave.baseParams['tabla'] = '';                
											                dsComboClave.baseParams['columna'] = '';
											                dsComboClave.baseParams['nombreVariableLocal'] = '';
															dsComboClave.load({
								        		        		callback : function(r,options,success) {
									            		            if (!success) {
						        		                		        //Ext.MessageBox.alert('Aviso','No se encontraron registros');  
						                                       			dsComboClave.removeAll();
									                                }
										                        }
											                });
													    	Ext.MessageBox.alert('Status', 'La variable se ha eliminado correctamente');
														}/*,
														success: function(form, action) {
														    Ext.MessageBox.alert('Status', 'Elemento borrado');						    
														    dsComboV.load();						    
														}*/
													});         	 
												}
											});
           							    }else{
		    	           				   	Ext.MessageBox.alert('Error', 'No ha seleccionado una variable');
		    	           				}
        	       				}					        		     
				            }]       
			    	}]
			    },{
		        	layout:'column',
		        	width: 550,//450
	    	    	border:false,
	        		items:[{
			        		columnWidth:.68,
    			    		layout:'form',
        					border:false,
        					width:380,//280
        					items:[comboTabla]       
			    	    },{
    	    				columnWidth:.32,
	        				layout:'form',
        					border:false,
		        			items:[{
		        				xtype:'button',
		        				text:'<s:text name="productos.config.variableLoc.btn.5claves"/>',
		        				handler:function(){
		        					TablasDeApoyo(dsComboTabla);
		        					}
		        			}]       
			    	}]
			    },{
		        	layout:'column',
		        	width: 550,//550
		        	border:false,
		        	items:[{
			        		columnWidth:.68,
    			    		layout:'form',
        					border:false,
        					width:380,//280
        					items:[comboColumna]       
			    	    },{
    	    				columnWidth:.32,
	        				layout:'form',
        					border:false,
		        			items:[{
		        				xtype:'button',
		        				text:'Guardar Variable',
		        				handler:function(){
		        					if(formPanelVariablesLocales.form.isValid()){
			        					if(validarClavesObligatoriasExpresion()){
			        						Ext.getCmp('hidden-codigo-nombre-variable-local').setValue(comboVariables.getValue());
			        						//alert(Ext.getCmp('hidden-codigo-nombre-variable-local').getValue());
				        					var params="codigoExpresion=" + Ext.getCmp('hidden-codigo-expresio-expresion').getValue();
				        					var maxlengthRecords=dsComboClave.getTotalCount();
				        					var modifiedRecords = dsComboClave.getModifiedRecords();
							 				for (var i=0; i<maxlengthRecords; i++) {				
							 					var validaModificados = true;					
							 					if(modifiedRecords.length>0){
							 						for(var j=0; j<modifiedRecords.legth;j++){
							 							if(dsComboClave.getAt(i).get('codigoSecuencia') == modifiedRecords[j].get('codigoSecuencia')){
							 								validaModificados = false;
							 								params+="&&listaClave["+i+"].codigoSecuencia="+modifiedRecords[j].get('codigoSecuencia')+
															"&&listaClave["+i+"].clave="+modifiedRecords[j].get('clave')+ 
															"&&listaClave["+i+"].expresion="+modifiedRecords[j].get('expresion')+
															"&&listaClave["+i+"].recalcular="+((modifiedRecords[j].get('switchRecalcular')== true)?'S':'N'); 											
							 							}
							 						}
							 					}
							 					if(validaModificados){
													params+="&&listaClave["+i+"].codigoSecuencia="+dsComboClave.getAt(i).get('codigoSecuencia')+
															"&&listaClave["+i+"].clave="+dsComboClave.getAt(i).get('clave')+ 
															"&&listaClave["+i+"].expresion="+dsComboClave.getAt(i).get('expresion')+
															"&&listaClave["+i+"].recalcular="+((dsComboClave.getAt(i).get('switchRecalcular')== true)?'S':'N'); 											
												}
											}
											//alert(params);
				        					formPanelVariablesLocales.form.submit({			      
									            waitTitle:'<s:text name="ventana.expresiones.proceso.mensaje.titulo"/>',
						            			waitMsg:'<s:text name="ventana.expresiones.proceso.mensaje"/>',
						            			params:params,
									            failure: function(form, action) {
													    Ext.MessageBox.alert('Error Message', Ext.util.JSON.decode(action.response.responseText).mensajeDelAction);
													    //Ext.MessageBox.alert('Error Message', 'failure');
												},
												success: function(form, action) {
					        						dsComboClave.commitChanges();
													dsComboV.load();
												}
									        });
								        }
							        }else{
										Ext.MessageBox.alert('Error', 'Favor de llenar datos requeridos');
									} 
		        				}
		        			}]       
			    	}]
			    },gridEditableClaves
				  		]
    				});
/****************************************************************************************
variables locales de expresion
****************************************************************************************/

/****************************************
Expresiones
*****************************************/
	function validaVariable(v) {
		if( !Ext.isEmpty(Ext.getCmp('nombre-cabecera')) ) {
			var valor = v.toUpperCase();
			var ext = Ext.getCmp('nombre-cabecera').getValue().toUpperCase();
			if ( valor != ' ' && valor != ''  && valor.search( ext ) != -1 ) {
				return "No se puede incluir la misma variable temporal en Descripci&oacute;n";
			} else {
				return true;
			}
		}else {
			return true;
		}
	}

    var descripcion= new Ext.form.TextArea({
	                        name: "descripcion",
	                        fieldLabel: '<s:text name="productos.config.expresion.descripcion"/>',
	                        width: '250',
	                        labelSeparator:'',
	                        //allowBlank: true,
	                        maxLength:'2000',
	                        validator: validaVariable
	        });

	function detailsUpdate(text){			
			if(!detailEl){
    			var bd = Ext.getCmp('details-panel').body;
    			bd.update('').setStyle('background','#fff');
    			detailEl = bd.createChild(); //create default empty div
    		}
    		detailEl.hide().update("Details:"+text).slideIn('l', {stopFx:true,duration:.2});
	}

    var tab2 = new Ext.FormPanel({
					//region:'center',
					labelAlign: 'left',
			        frame: true,
			        //margins:'0 0 0 5',
			        url:'expresiones/AgregarExpresion.action',
			        bodyBorder:false,
			        border:false,
			        id:'forma-expresiones',
			        //bodyStyle:'padding:0 0 0',
			        autoHeight:true,//height:120,
			        width: 550,//450
			        items:[ {xtype:'hidden', id:'hidden-expresion-session-expresion',name:'codigoExpresionSession',value:codigoExpresionSession},
			        		{xtype:'hidden', id:'hidden-codigo-expresio-expresion',name:'codigoExpresion'},{
		   				layout:'form',
		   				//margins:'0 5 5 5',
		   				//height:300,
			        	//bodyStyle:'padding:5px 5px 0',
		   				border:false,
		   				frame:false,
		   				title: '<s:text name="productos.expresiones.subtitulo.valorDefecto"/>',
		   				items: [{
					            layout:'column',
					            border:false,
					            items:[{
						                columnWidth:.7,
					    	            layout: 'form',
					        	        border:false,
					            	    labelAlign: 'top',
					                	items: [{layout:'form',border:false,heigth:'10'},descripcion]
						            },{
						                columnWidth:.3,
						                layout: 'form',
						                border:false,
						                items: [{
							                    layout: 'form',
							             		heigth:'10',
						    	         		border:false
						        	        },{
						            	        layout: 'form',
						                		border:false,
						                		items: [{
						                				id:'switch-recalcular-expresion-simple',
								    	                xtype:'checkbox',
							    		                fieldLabel: 'Last Name',
							            		        hideLabel:true,
							                    		boxLabel:'<s:text name="productos.config.expresion.recalcula"/>',
							                    		name:'switchRecalcular'
		        	        						},{
							            		        xtype:'button',
		                							    text:'<s:text name="productos.config.expresion.btn.comprobarSintaxis"/>',
		                							    handler: function () {
		                							    	tab2.form.submit({
		                							    		url: 'expresiones/ValidarExpresion.action',			      
								            					failure: function(form, action) {
												    				Ext.MessageBox.alert('Error Message', Ext.util.JSON.decode(action.response.responseText).mensajeValidacion);
																},
																success: function(form, action) {
												    				//Ext.MessageBox.alert('Confirm', action.result.info);	
												    				//alert("Exito!!");
												    				//Ext.getCmp('hidden-valor-defecto-atributos-variables').setValue("-1");					   
												    				Ext.MessageBox.alert('Mensaje', Ext.util.JSON.decode(action.response.responseText).mensajeValidacion);
																}
							        						});
		                							    }
					        			        }]
					            	    }]
					            }]
					        }]
			    	}]
				});
				

/************************************
nueva ventana
***********************************/
	
    // define window and show it in desktop
 var wind;
 if(encabezado){
 //alert('entro al if de store');
 	wind = new Ext.Window({
            title: '<s:text name="productos.expresiones.titulo.principal"/>',
            closable:true,
            buttonAlign:'center',
            width:800,//700
            height:580,
            //border:false,
            plain:true,
            layout: 'border',
            modal:true,
			items:[cabecera,{
                    region:'south',
                    split:true,
                    height: 100,
                    minSize: 50,
                    maxSize: 200,
                    collapsible: true,
                    frame:true,
                    title:'<s:text name="productos.expresiones.subtitulo.detalles"/>',
                    margins:'0 0 0 0',
		            items:[new Ext.Panel({
										id: 'details-panel',
								        margins:'5 0 0 0',
								        border:true,	
								        autoHeigth:true,
							    	    bodyStyle: 'padding-bottom:15px;background:#eee;',
										autoScroll: true,
										html: '<p class="details-info">Al seleccionar uno de los elementos del árbol aquí aparecerá su detalle.<br><br> Al darle doble click al elemento se agregará al campo de descripción </p>'
					    	})]
	                    
	                },{
						region:'west',
						heigth:'400',
						width:'200',
						minSize:'175',
						maxSize:'300',
						border:false,
	        		    collapsible: false,
		                margins:'0 5 0 5',
           			    layout:'accordion',
	                    layoutConfig:{
       				            animate:true
	                    },
       				    items: [{    
						        title: '<s:text name="productos.expresiones.subtitulo.funciones"/>',
						        xtype: 'treepanel',
			    			    width: 200,
						        autoScroll: true,
						        split: true,
			    			    loader: new Ext.tree.TreeLoader({
						               dataUrl:'<s:url value="expresiones/FuncionesArbol.action"/>'
					            }), 
						        root:  new Ext.tree.AsyncTreeNode({
						                text: 'Ext JS', 
						                draggable:false, // disable root node dragging
						                id:'source'
					            }),
						        rootVisible: false,
						        listeners: {
			    				        click: function(n) {
								            //Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');								               
	 										detailsUpdate(n.attributes.descripcion);
							            },
							            dblclick: function(n){
							            	var valor = descripcion.getValue();
							            	if(descripcion.isDirty()){
							            		valor = valor+" ";
							            	}
	 										descripcion.setValue(valor+n.attributes.funcion);
	 									}
						        }
						    },{    
						        title: '<s:text name="productos.expresiones.subtitulo.variableTemporal"/>',
						        xtype: 'treepanel',
						        width: 200,
			    			    autoScroll: true,
						        split: true,
						        loader: new Ext.tree.TreeLoader({
						               dataUrl:'<s:url value="expresiones/VariablesTemporalesArbol.action"/>'
					            }), 
						        root:  new Ext.tree.AsyncTreeNode({
						                text: 'Ext JS', 
						                draggable:false, // disable root node dragging
						                id:'source'
					            }),
						        rootVisible: false,
						        listeners: {
			    				        click: function(n) {
								                //Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');								                
	 											//detailsUpdate(n.attributes.descripcion);
							            },
							            dblclick: function(n){
							            	 var valor= descripcion.getValue();
							            	if(descripcion.isDirty()){
								            	valor=valor+" &";
	 										}else{
	 											valor=valor+"&";	 										
	 										}
	 										descripcion.setValue(valor+n.attributes.funcion); 
	 									}
						        }
						    }]
		
										
				},{layout:'form',region:'center',boder:false,frame:true,items:[tab2,formPanelVariablesLocales]}],
				buttons:[{
  						text:'<s:text name="productos.config.expresion.btn.guardar"/>',
  						handler: function(){//alert(Ext.getCmp('hidden-expresion-session-expresion').getValue());
  							if(descripcion.getValue().length>0) {
					 		        tab2.form.submit({			      
								            waitTitle:'<s:text name="ventana.expresiones.proceso.mensaje.titulo"/>',
					            			waitMsg:'<s:text name="ventana.expresiones.proceso.mensaje"/>',
								            failure: function(form, action) {
												    Ext.MessageBox.alert('Error Message', Ext.util.JSON.decode(action.response.responseText).mensajeDelAction);
												    //Ext.MessageBox.alert('Error Message', 'failure');
											},
											success: function(form, action) {
												    //Ext.MessageBox.alert('Confirm', action.result.info);	
												    //alert("Exito!!");
												    //Ext.getCmp('hidden-valor-defecto-atributos-variables').setValue("-1");	
												    cabecera.form.submit({
												    	waitTitle:'<s:text name="ventana.expresiones.proceso.mensaje.titulo"/>',
								            			waitMsg:'<s:text name="ventana.expresiones.proceso.mensaje"/>',
								            			failure: function(form, action) {
														    Ext.MessageBox.alert('Error Message', 'failure');
														},
														success: function(form, action) {
															store.load();
														    wind.close();
														}
												    });				   
											}
							        });                   
	        		        } else{
	        		        		descripcion.markInvalid('<s:text name="productos.valida.expresion.descripcion"/>');
									Ext.MessageBox.alert('Error', 'Favor de llenar los datos requeridos');
							}     
  						}
  					},{
  						text:'<s:text name="productos.config.expresion.btn.cancelar"/>',
  						handler:function(){ 
  							wind.close();
  						}
  				}]
        });
 }else{
	 wind = new Ext.Window({
            title: '<s:text name="productos.expresiones.titulo.principal"/>',
            closable:true,
            buttonAlign:'center',
            width:700,
            height:580,
            plain:true,
            layout: 'border',
            modal:true,
			items:[{
                    region:'south',
                    split:true,
                    height: 85,
                    minSize: 50,
                    maxSize: 200,
                    collapsible: true,
                    frame:true,
                    title:'<s:text name="productos.expresiones.subtitulo.detalles"/>',
                    margins:'0 0 0 0',
		            items:[new Ext.Panel({
										id: 'details-panel',
								        margins:'5 0 0 0',
								        border:true,	
								        autoHeigth:true,
							    	    bodyStyle: 'padding-bottom:15px;background:#eee;',
										autoScroll: true,
										html: '<p class="details-info">Al seleccionar uno de los elementos del árbol aquí aparecerá su detalle.<br><br> Al darle doble click al elemento se agregará al campo de descripción </p>'
					    	})]
	                    
	                },{
						region:'west',
						heigth:'400',
						width:'200',
						minSize:'175',
						maxSize:'300',
						border:false,
	        		    collapsible: false,
		                margins:'0 5 0 5',
           			    layout:'accordion',
	                    layoutConfig:{
       				            animate:true
	                    },
       				    items: [{    
						        title: '<s:text name="productos.expresiones.subtitulo.funciones"/>',
						        xtype: 'treepanel',
			    			    width: 200,
						        autoScroll: true,
						        split: true,
			    			    loader: new Ext.tree.TreeLoader({
						               dataUrl:'<s:url value="expresiones/FuncionesArbol.action"/>'
					            }), 
						        root:  new Ext.tree.AsyncTreeNode({
						                text: 'Ext JS', 
						                draggable:false, // disable root node dragging
						                id:'source'
					            }),
						        rootVisible: false,
						        listeners: {
			    				        click: function(n) {
								            //Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');								               
	 										detailsUpdate(n.attributes.descripcion);
							            },
							            dblclick: function(n){
							            	var valor = descripcion.getValue();
							            	if(descripcion.isDirty()){
							            		valor = valor+" ";
							            	}
	 										descripcion.setValue(valor+n.attributes.funcion);
	 									}
						        }
						    },{    
						        title: '<s:text name="productos.expresiones.subtitulo.variableTemporal"/>',
						        xtype: 'treepanel',
						        width: 200,
			    			    autoScroll: true,
						        split: true,
						        loader: new Ext.tree.TreeLoader({
						               dataUrl:'<s:url value="expresiones/VariablesTemporalesArbol.action"/>'
					            }), 
						        root:  new Ext.tree.AsyncTreeNode({
						                text: 'Ext JS', 
						                draggable:false, // disable root node dragging
						                id:'source'
					            }),
						        rootVisible: false,
						        listeners: {
			    				        click: function(n) {
								                //Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');								                
	 											//detailsUpdate(n.attributes.descripcion);
							            },
							            dblclick: function(n){
							            	 var valor= descripcion.getValue();
							            	if(descripcion.isDirty()){
								            	valor=valor+" &";
	 										}else{
	 											valor=valor+"&";	 										
	 										}
	 										descripcion.setValue(valor+n.attributes.funcion); 
	 									}
						        }
						    }]
		
										
				},{layout:'form',region:'center',boder:false,frame:true,items:[tab2,formPanelVariablesLocales]}],
				buttons:[{
  						text:'<s:text name="productos.config.expresion.btn.guardar"/>',
  						handler: function(){//alert(Ext.getCmp('hidden-expresion-session-expresion').getValue());
  							if(descripcion.getValue().length>0) {
					 		        tab2.form.submit({			      
								            waitTitle:'<s:text name="ventana.expresiones.proceso.mensaje.titulo"/>',
					            			waitMsg:'<s:text name="ventana.expresiones.proceso.mensaje"/>',
								            failure: function(form, action) {
												    Ext.MessageBox.alert('Error Message', Ext.util.JSON.decode(action.response.responseText).mensajeDelAction);
												    //Ext.MessageBox.alert('Error Message', 'failure');
											},
											success: function(form, action) {
												    //Ext.MessageBox.alert('Confirm', action.result.info);	
												    //alert("Exito!!");
												    //Ext.getCmp('hidden-valor-defecto-atributos-variables').setValue("-1");					   
												    wind.close();
											}
							        });                   
	        		        } else{
	        		        		descripcion.markInvalid('<s:text name="productos.valida.expresion.descripcion"/>');
									Ext.MessageBox.alert('Error', 'Favor de llenar los datos requeridos');
							}     
  						}
  					},{
  						text:'<s:text name="productos.config.expresion.btn.cancelar"/>',
  						handler:function(){ 
  						//alert(Ext.getCmp('hidden-expresion-session-expresion').getValue()); 						
  							wind.close();
  						}
  				}]
        });
 }

        wind.show();
        
   		function validarClavesObligatoriasExpresion(){
	    	var boleanoGE=false;
	    	//alert("validarClavesObligatoriasExpresion + dsComboClave.getTotalCount()="+dsComboClave.getTotalCount());
	    	if(dsComboClave.getTotalCount() != 0){
	    		var validaPrimero = false;
		    	var cuztomizedMessage='Favor de llenar las expresiones de: ';
		    	var maxlengthRecords=dsComboClave.getTotalCount();	    								
				for(var irec=0;irec<maxlengthRecords;irec++){				
						if(dsComboClave.getAt(irec).get('expresion').length==0 ){
							if(validaPrimero){
								cuztomizedMessage+=', ';								
							}
							boleanoGE=true;
							cuztomizedMessage+=dsComboClave.getAt(irec).get("clave") ;
							validaPrimero = true;
						}
				}
				if(boleanoGE){
					Ext.MessageBox.alert('Errors', cuztomizedMessage);
				}
			}
			return !boleanoGE;				
		}
		
	if(row){
		//alert('entro al if de row');
		var record = store.getAt(row);
	    //codigoExpresion= record.get('codigoExpresion');
        Ext.getCmp('cabecera-form').getForm().loadRecord(record);
	}
};
</script>