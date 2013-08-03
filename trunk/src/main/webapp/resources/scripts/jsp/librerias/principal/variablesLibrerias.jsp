<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/expresiones/expresiones.jsp" flush="true" />

<script type="text/javascript">
   Ext.onReady(function(){

    Ext.QuickTips.init();
	Ext.QuickTips.enable();
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';

    var bd = Ext.getBody();

    var storeVariables;

    

    var selectedId;
    var afuera;
 	var temporal=-1;
 
 //********************************************************  
 //********************grid autorizaciones********************
    function variables(){        		        				
 			url='librerias/CargaReglaNegocio.action'+'?numeroGrid='+'1' 			 		 			
 			storeVariables = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaReglaNegocioVariables',   
            	totalProperty: 'totalCount',
            	id: 'nombreCabecera',
            	successProperty : '@success'
            	         	
	        	},[
	        		{name: 'nombreCabecera',  type: 'string',  mapping:'nombre'},
	        		{name: 'descripcionCabecera',  type: 'string',  mapping:'descripcion'},
	        		{name: 'codigoExpresion',  type: 'string',  mapping:'codigoExpresion'}
	        		            
				]),
			//autoLoad:true,
			remoteSort: true
    	});
    	storeVariables.setDefaultSort('descripcionCabecera', 'desc');
    	//storeVariables.load();
		return storeVariables;
 	}
 

 	function toggleDetails(btn, pressed){
       	var view = grid.getView();
       	view.showPreview = pressed;
       	view.refresh();
    	}
		
		var cmVariables = new Ext.grid.ColumnModel([
		    new Ext.grid.RowNumberer(),
			{header: "Nombre", 	   dataIndex:'nombreCabecera',	width: 250, sortable:true,id:'nombreCabecera'},		    
		    {header: "Descripción",   dataIndex:'descripcionCabecera',	width: 250, sortable:true}
		   
		   	                	
        ]);
     
 
	var grid1= new Ext.grid.GridPanel({
		store:variables(),
		id:'grid-variables-temporales',
		border:true,
		//baseCls:' background:white ',
		cm: cmVariables,
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {							
        	rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			selectedId = storeVariables.data.items[row].id;
	        	 			//var sel = Ext.getCmp('grid-lista1').getSelectionModel().getSelected();
	        	 			//selIndexVariables = storeVariables.indexOf(rec);
                            		 //alert("row"+row);
                            		 
	        	 			Ext.getCmp('eliminar-variables').on('click',function(){                            		
                            		//DeleteDemouser(storeVariables,selectedId,sel,listaValoresForm);
                                                                                                   
                                 });
                            afuera=row;     
                            Ext.getCmp('editar-variables').on('click',function(){
                            		
                            		if(afuera!=temporal){
                            			temporal=afuera;
                            			//alert("temporal"+temporal);
                            			var codigoExpresion = storeVariables.getAt(row).get('codigoExpresion');
                            			//alert(codigoExpresion);
                            			if(codigoExpresion!=null && codigoExpresion!="" && codigoExpresion!="0" && codigoExpresion!="undefined"){ 
                            				ExpresionesVentana2(codigoExpresion, "EXPRESION", storeVariables, '5', row);
					            		}else{
	            								var connect = new Ext.data.Connection();
											    connect.request ({
													url:'atributosVariables/ObtenerCodigoExpresion.action',
													callback: function (options, success, response) {				   
														codigoExpresion = Ext.util.JSON.decode(response.responseText).codigoExpresion;
														ExpresionesVentana2(codigoExpresion, "EXPRESION", storeVariables, '5', row);
													}
										   		});
					            		}
                            			//ExpresionesVentanaVariablesTemporales(storeVariables,rec,temporal);
                            		}                                                                     
                                 });
                                 temporal=-1;
	                   	 }
	               	}
		}),
		tbar:[{
            text:'<s:text name="librerias.btn.agregar"/>',
            tooltip:'Agregar variable temporal',
            iconCls:'add',
            handler: function() {                    	
				     	var connect = new Ext.data.Connection();
						    connect.request ({
								url:'atributosVariables/ObtenerCodigoExpresion.action',
								callback: function (options, success, response) {				   
									var codigoDeExpresion = Ext.util.JSON.decode(response.responseText).codigoExpresion;
									ExpresionesVentana2(codigoDeExpresion, "EXPRESION", storeVariables, '5');
								}
					   		});
				     }
        },'-',{
            text:'<s:text name="librerias.btn.eliminar"/>',
            id:'eliminar-variables',
            tooltip:'Eliminar variable temporal',
            iconCls:'remove'
            
            
        },'-',{
            text:'<s:text name="librerias.btn.editar"/>',
            id:"editar-variables",
            tooltip:'Editar variable temporal',
            iconCls:'option'
           
        }],      							        	    	    
    	width:600,
        height:290,
    	frame:true,     
		//title:'Variables Temporales',
		bodyStyle:'padding:5px',
		//renderTo:document.body,
		
	
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:20,
			store: storeVariables,									            
			displayInfo: true,
			displayMsg: 'Mostrando registros {0} - {1} de {2}',
			emptyMsg: "No hay resultados"
			})        							        				        							 
		}); 	


 //********************************************************     
    var tab2 = new Ext.FormPanel({
        labelAlign: 'top',
        id: 'tab2-form1',
        title: '<s:text name="librerias.titulo.variablesTemp"/>',
        bodyStyle:'padding:5px',
        width: 670,
        autoScroll:true,
        heigth:400,
        items: [{
            layout:'column',
            border:false,
            items:[{
                columnWidth:.5,
                layout: 'form',
                border:false,
                items: [{
                    xtype:'textfield',
                    fieldLabel: '<s:text name="librerias.cabecera.texto"/>',
                    labelSeparator:'',
                    hidden:true,
                    name: 'first',
                    anchor:'95%'
                }]
            }]
        },{
                //title:'Variables Temporales',
                layout:'form',
                width:610,
                items: [grid1]
		}]
    });

    tab2.render('libreriasVariables');
});
</script>



