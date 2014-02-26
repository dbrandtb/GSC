<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">
            var _CONTEXT = '${ctx}';
            
            var esHospitalario = <s:property value="esHospitalario" />;
            var loadForm = <s:property value='loadForm' escapeHtml='false'/>;
            //var _URL_HeaderCalculos	= '<s:url namespace="/siniestros" action="obtenHeaderCalculos" />';
            var _URL_ListaCalculos		= '<s:url namespace="/siniestros" action="loadListaCalculos" />';
            
	Ext.onReady(function() {

            	Ext.selection.CheckboxModel.override({
            		mode: 'SINGLE',
            		allowDeselect: true
            	});
            	
            	////////////////////////////////////////////////
            	/////////// 		MODELO				////////
            	////////////////////////////////////////////////
                
                Ext.define('modeloCalculos',{
                    extend: 'Ext.data.Model',
                    fields: [{type:'string',    name:'cpt'},
                             {type:'string',    name:'cantidad'},
                             {type:'string',    name:'arancel'},
                             {type:'string',    name:'subtotalArancel'},
                             {type:'string',    name:'descuento'},
                             {type:'string',    name:'subtotalDescuento'},
                             {type:'string',    name:'porcentajeCopago'},
                             {type:'string',    name:'copago'},
                             {type:'string',    name:'copagoAplicado'},
                             {type:'string',    name:'subtotal'},
                             {type:'string',    name:'isr'},
                             {type:'string',    name:'cedular'},
                             {type:'string',    name:'subtotalImpuestos'},
                             {type:'string',    name:'iva'},
                             {type:'string',    name:'total'},
                             {type:'string',    name:'facturado'},
                             {type:'string',    name:'autorizado'},
                             {type:'string',    name:'valorUtilizar'}
            				]
                });
            	
            	////////////////////////////////////////////////
            	/////////// 		STORE				////////
            	////////////////////////////////////////////////
            	
                
                var storeCalculos = Ext.create('Ext.data.JsonStore', {
                	model:'modeloCalculos',
                    proxy: {
                        type: 'ajax',
                        url: _URL_ListaCalculos,
                        reader: {
                            type: 'json',
                            root: 'loadList'
                        }
                    }
                });
                
            	if(!esHospitalario) storeCalculos.load();
                    
            	
            	var gridCalculos = Ext.create('Ext.grid.Panel',{
            		id             : 'gridCalculosId'
            		//,title         : 'Hist&oacute;rico de Reclamaciones'
            		,autoScroll: true
            		,store         :  storeCalculos
            		,selType: 'checkboxmodel'
            		,collapsible   : true
            		,titleCollapse : true
            		,style         : 'margin:5px'
            		,minHeight: 200
            		,maxHeight: 300
            		,columns       :[{ header     : 'CPT/HCPC' ,dataIndex : 'cpt' ,flex: 1 },
            		                 { header     : 'Cantidad' ,dataIndex : 'cantidad' ,flex: 1 },
            		                 { header     : 'Arancel' ,dataIndex : 'arancel' ,flex: 1 },
            		                 { header     : 'Subtotal Arancel' ,dataIndex : 'subtotalArancel' ,flex: 1 },
            		                 { header     : 'Descuento' ,dataIndex : 'descuento' ,flex: 1 },
            		                 { header     : 'Subtotal Descuento' ,dataIndex : 'subtotalDescuento' ,flex: 1 },
            		                 { header     : 'Copago %' ,dataIndex : 'porcentajeCopago' ,flex: 1 },
            		                 { header     : 'Copago $' ,dataIndex : 'copago' ,flex: 1 },
            		                 { header     : 'Copago Aplicado' ,dataIndex : 'copagoAplicado' ,flex: 1 },
            		                 { header     : 'Subtotal' ,dataIndex : 'subtotal' ,flex: 1 },
            		                 { header     : 'ISR' ,dataIndex : 'isr' ,flex: 1 },
            		                 { header     : 'Cedular' ,dataIndex : 'cedular' ,flex: 1 },
            		                 { header     : 'Subtotal Impuestos' ,dataIndex : 'subtotalImpuestos' ,flex: 1 },
            		                 { header     : 'IVA' ,dataIndex : 'iva' ,flex: 1 },
            		                 { header     : 'Total' ,dataIndex : 'total' ,flex: 1 },
            		                 { header     : 'Facturado' ,dataIndex : 'facturado' ,flex: 1 },
            		                 { header     : 'Autorizado' ,dataIndex : 'autorizado' ,flex: 1 },
            		                 { header     : 'Valor Utilizar' ,dataIndex : 'valorUtilizar' ,flex: 1 }
            		 ],
            		 bbar     :	{
            			 displayInfo : true,
            			 store       : storeCalculos,
            			 xtype       : 'pagingtoolbar'
            		 }
            	});
            	
            	
            	Ext.grid.PropertyColumnModel.prototype.nameText = 'Concepto';
            	
            	var gridCalculos2 = Ext.create('Ext.grid.property.Grid', {
            	    width: 300,
            	    sortableColumns: false,
            	    listeners: { 'beforeedit': function (e) { return false; } },
            	    source: loadForm/*{
            	        "<strong>Asegurado:</strong>": loadForm.asegurado,
            	        "<strong>Deducible:</strong>": loadForm.deducible,
            	        "<strong>% Copago:</strong>": loadForm.copago,
            	        "<strong>IVA Proveedor:</strong>": 0.01,
            	        "":"",
            	        "<strong>Importe:</strong>": "A test object",
            	        "<strong>Descuento:</strong>": 34,
            	        "<strong>Subtotal:</strong>":343,
            	        "<strong>Deducible:</strong>":343,
            	        "<strong>Subtotal:</strong>":343,
            	        "<strong>Copago:</strong>":343,
            	        "<strong>IVA:</strong>":343,
            	        "<strong>Total:</strong>":343
            	    }*/
            	});
            	
            	
            /*################################################################################################################################################
            ###################################################### 			 PANTALLA 	PRINCIPAL 		 #####################################################
            ##################################################################################################################################################*/

            if(esHospitalario){
            	Ext.create('Ext.form.Panel',{
            		border: false
            		,title: 'Calculos de Siniestro'
            		,renderTo : 'maindivCalculos'
            		,bodyStyle:'padding:5px;'
            		,layout: {type:'hbox', pack: 'center'}
            		,items: [gridCalculos2]		
            	});
            }else{
            	
            	Ext.define('modeloHeaderCalculos',{
            				extend  : 'Ext.data.Model'
            				,fields : [{type:'string',    name:'proveedor'},
                                       {type:'string',    name:'isrProveedor'},
                                       {type:'string',    name:'impuestoCedular'},
                                       {type:'string',    name:'iva'}
                      				 ]
            			});
            	
            	var panelCalculos = Ext.create('Ext.form.Panel',{
            		border: false
            		,title: 'Calculos de Siniestro'
            		,renderTo : 'maindivCalculos'
            		,bodyStyle:'padding:5px;'
            		,items: [
            		         {
            		        	 layout: { type: 'table',columns : 2 },
            		        	 border: false,
            		        	 defaults: { style : 'margin:5px;'},
            		        	 items: [{
                 	 				xtype       : 'textfield', 
                		 			fieldLabel : 'Proveedor',
                		 			name: 'proveedor',
                		 			readOnly   : true
                			 	},{
                		 			xtype       : 'textfield', 
                		 			fieldLabel : 'ISR Proveedor',
                		 			name : 'isrProveedor',
                		 			readOnly   : true
                			 	},{
                		 			xtype       : 'textfield', 
                		 			fieldLabel : 'Ipuesto Cedular',
                		 			name : 'impuestoCedular',
                		 			readOnly   : true
                			 	},{
                		 			xtype       : 'textfield', 
                		 			fieldLabel : 'IVA',
                		 			name : 'iva',
                		 			readOnly   : true
                			 	}]
            		         },
            		         gridCalculos]
            	});
            	panelCalculos.loadRecord(new modeloHeaderCalculos(loadForm));
            	
            }
            	
            });            
            
            

</script>

<div id="maindivCalculos" style="height:400px;"></div>
