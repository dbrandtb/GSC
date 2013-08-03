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

    var storeCondiciones;

    

    var selectedId;
    var afuera;
 	var temporal=-1;
 
 //********************************************************   
 
 //********************grid condiciones********************
     function condiciones(){        		        				
 			url='librerias/CargaReglaNegocio.action'+'?numeroGrid='+'3' 			 		 			
 			storeCondiciones = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaReglaNegocioCondiciones',   
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
    	storeCondiciones.setDefaultSort('descripcionCabecera', 'desc');
    	//storeCondiciones.load();
		return storeCondiciones;
 	}
 	
 	function toggleDetails(btn, pressed){
       	var view = grid.getView();
       	view.showPreview = pressed;
       	view.refresh();
    	}
		
		var cmCondiciones = new Ext.grid.ColumnModel([
		    new Ext.grid.RowNumberer(),
			{header: "Nombre", 	   dataIndex:'nombreCabecera',	width: 120, sortable:true,id:'nombreCabecera'},		    
		    {header: "Descripción",   dataIndex:'descripcionCabecera',	width: 120, sortable:true}
		   
		   	                	
        ]);
        var grid3;
        var selectedId;
 		
 	function createGridCondiciones(){
	grid3= new Ext.grid.GridPanel({
		store:condiciones(),
		id:'grid-condiciones',
		border:true,
		//baseCls:' background:white ',
		cm: cmCondiciones,
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {							
        	rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			selectedId = storeCondiciones.data.items[row].id;
	        	 			//var sel = Ext.getCmp('grid-lista3').getSelectionModel().getSelected();
	        	 			//selIndexValidaciones = storeCondiciones.indexOf(sel);
	        	 			Ext.getCmp('eliminar-condiciones').on('click',function(){                            		
                            		//DeleteDemouser(storeCondiciones,selectedId,sel,listaValoresForm);
                                                                                                       
                                 });
                            
                            afuera=row;     
                            Ext.getCmp('editar-condiciones').on('click',function(){
                            		
                            		if(afuera!=temporal){
                            			temporal=afuera;
                            			//alert("temporal"+temporal);
                            			var codigoExpresion = storeCondiciones.getAt(row).get('codigoExpresion');
                            			if(codigoExpresion!=null && codigoExpresion!="" && codigoExpresion!="0" && codigoExpresion!="undefined"){ 
                            				ExpresionesVentana2(codigoExpresion, "EXPRESION", storeCondiciones, '3', row);
					            		}else{
	            								var connect = new Ext.data.Connection();
											    connect.request ({
													url:'atributosVariables/ObtenerCodigoExpresion.action',
													callback: function (options, success, response) {				   
														codigoExpresion = Ext.util.JSON.decode(response.responseText).codigoExpresion;
														ExpresionesVentana2(codigoExpresion, "EXPRESION", storeCondiciones, '3', row);
													}
										   		});
					            		}
                            			//ExpresionesVentanaCondiciones(storeCondiciones,rec,temporal);
                            		}                                                                     
                                 });
                                 temporal=-1;    
                            /*     
                            Ext.getCmp('editar-grid').on('click',function(){                            		
                            		 ExpresionesVentanaCondiciones(storeCondiciones,rec,row);
                                                                                                             
                                 });*/     
	                   	 }
	               	}
		}),
		tbar:[{
            text:'<s:text name="librerias.btn.agregar"/>',
            tooltip:'Agregar condición',
            iconCls:'add',
            handler: function() {                    	
				     	var connect = new Ext.data.Connection();
					    connect.request ({
							url:'atributosVariables/ObtenerCodigoExpresion.action',
							callback: function (options, success, response) {				   
								codigoExpresion = Ext.util.JSON.decode(response.responseText).codigoExpresion;
								ExpresionesVentana2(codigoExpresion, "EXPRESION", storeCondiciones, '3');
							}
				   		});
				     }
        },'-',{
            text:'<s:text name="librerias.btn.eliminar"/>',
            id:'eliminar-condiciones',
            tooltip:'Eliminar condición',
            iconCls:'remove'
            
        },'-',{
            text:'<s:text name="librerias.btn.editar"/>',
            id:"editar-condiciones",
            tooltip:'Editar condición',
            iconCls:'option'
        }],      							        	    	    
    	width:600,
        height:370,
    	frame:true,     
		//title:'Condiciones',
		bodyStyle:'padding:5px',
		//renderTo:document.body,
		
	
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:20,
			store: storeCondiciones,									            
			displayInfo: true,
			displayMsg: 'Mostrando registros {0} - {1} de {2}',
			emptyMsg: "No hay resultados"
			})        							        				        							 
		}); 	
	}
	
	createGridCondiciones();
	//storeCondiciones.load();
 //********************************************************     
    var tab2 = new Ext.FormPanel({
        labelAlign: 'top',
        id: 'tab2-form3',
        title: '<s:text name="librerias.titulo.condicion"/>',
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
                //title:'Condiciones',
                layout:'form',
                width:610,
                items: [grid3]
		}]
    });

    tab2.render('libreriasCondiciones');
});
</script>



