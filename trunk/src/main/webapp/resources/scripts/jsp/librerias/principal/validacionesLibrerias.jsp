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

    var storeValidaciones;

    

    var selectedId;
    var afuera;
 	var temporal=-1;
 
//*********************************************************
 
 //********************grid validaciones********************
     function validaciones(){        		        				
 			url='librerias/CargaReglaNegocio.action'+'?numeroGrid='+'2' 			 		 			
 			storeValidaciones = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaReglaNegocioValidaciones',   
            	totalProperty: 'totalCount',
            	id: 'nombreCabecera',
            	successProperty : '@success'
            	         	
	        	},[
	        		{name: 'nombreCabecera',  type: 'string',  mapping:'nombre'},
	        		{name: 'descripcionCabecera',  type: 'string',  mapping:'descripcion'},
	        		{name: 'descripcionTipo',  type: 'string',  mapping:'descripcionTipo'},
	        		{name: 'mensaje',  type: 'string',  mapping:'mensaje'},
	        		{name: 'codigoExpresion',  type: 'string',  mapping:'codigoExpresion'}
	        		            
				]),
			//autoLoad:true,
			remoteSort: true
    	});
    	storeValidaciones.setDefaultSort('descripcionCabecera', 'desc');
    	//storeValidaciones.load();
		return storeValidaciones;
 	}
 	
 	function toggleDetails(btn, pressed){
       	var view = grid.getView();
       	view.showPreview = pressed;
       	view.refresh();
    	}
		
		var cmValidaciones = new Ext.grid.ColumnModel([
		    new Ext.grid.RowNumberer(),
			{header: "Nombre", 	   dataIndex:'nombreCabecera',	width: 120, sortable:true,id:'nombreCabecera'},		    
		    {header: "Descripción",   dataIndex:'descripcionCabecera',	width: 120, sortable:true},
		    {header: "Tipo", 	   dataIndex:'descripcionTipo',	width: 120, sortable:true},		    
		    {header: "Mensaje",   dataIndex:'mensaje',	width: 120, sortable:true}
		   
		   	                	
        ]);
        var grid2;
        var selectedId;
 		
 	function createGridValidaciones(){
	grid2= new Ext.grid.GridPanel({
		store:validaciones(),
		id:'grid-validaciones',
		border:true,
		//baseCls:' background:white ',
		cm: cmValidaciones,
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {							
        	rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			selectedId = storeValidaciones.data.items[row].id;
	        	 			//var sel = Ext.getCmp('grid-lista2').getSelectionModel().getSelected();
	        	 			//selIndexValidaciones = storeValidaciones.indexOf(sel);
	        	 			Ext.getCmp('eliminar-validaciones').on('click',function(){                            		
                            		//DeleteDemouser(storeValidaciones,selectedId,sel,listaValoresForm);
                                                                                                       
                                 });
                            afuera=row;     
                            Ext.getCmp('editar-validaciones').on('click',function(){
                            		
                            		if(afuera!=temporal){
                            			temporal=afuera;
                            			//alert("temporal"+temporal);
                            			var codigoExpresion = storeValidaciones.getAt(row).get('codigoExpresion');
                            			//alert(codigoExpresion);
                            			if(codigoExpresion!=null && codigoExpresion!="" && codigoExpresion!="0" && codigoExpresion!="undefined"){ 
                            				ExpresionesVentana2(codigoExpresion, "EXPRESION", storeValidaciones, '4', row);
					            		}else{
	            								var connect = new Ext.data.Connection();
											    connect.request ({
													url:'atributosVariables/ObtenerCodigoExpresion.action',
													callback: function (options, success, response) {				   
														codigoExpresion = Ext.util.JSON.decode(response.responseText).codigoExpresion;
														ExpresionesVentana2(codigoExpresion, "EXPRESION", storeValidaciones, '4', row);
													}
										   		});
					            		}
                            			//ExpresionesVentanaValidaciones(storeValidaciones,rec,temporal);
                            		}                                                                     
                                 });
                                 temporal=-1;     
                            /*Ext.getCmp('editar-grid').on('click',function(){                            		
                            		 ExpresionesVentanaValidaciones(storeValidaciones,rec,row);
                                                                                                              
                                 });*/     
	                   	 }
	               	}
		}),
		tbar:[{
            text:'<s:text name="librerias.btn.agregar"/>',
            tooltip:'Agregar validación',
            iconCls:'add',
            handler: function() {                    	
				     	var connect = new Ext.data.Connection();
						    connect.request ({
								url:'atributosVariables/ObtenerCodigoExpresion.action',
								callback: function (options, success, response) {				   
									var codigoDeExpresion = Ext.util.JSON.decode(response.responseText).codigoExpresion;
									ExpresionesVentana2(codigoDeExpresion, "EXPRESION", storeValidaciones, '4');
								}
					   		});
				     }
        },'-',{
            text:'<s:text name="librerias.btn.eliminar"/>',
            id:'eliminar-validaciones',
            tooltip:'Eliminar validación',
            iconCls:'remove'
            
        },'-',{
            text:'<s:text name="librerias.btn.editar"/>',
            id:"editar-validaciones",
            tooltip:'Editar validación',
            iconCls:'option'
        }],      							        	    	    
    	width:600,
        height:370,
    	frame:true,     
		//title:'Validaciones',
		bodyStyle:'padding:5px',
		//renderTo:document.body,
		
	
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:20,
			store: storeValidaciones,									            
			displayInfo: true,
			displayMsg: 'Mostrando registros {0} - {1} de {2}',
			emptyMsg: "No hay resultados"
			})        							        				        							 
		}); 	
	}
	
	createGridValidaciones();
	//storeValidaciones.load(); 

 //********************************************************     
    var tab2 = new Ext.FormPanel({
        labelAlign: 'top',
        id: 'tab2-form2',
        title: '<s:text name="librerias.titulo.validacion"/>',
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
                //title:'Validaciones',
                layout:'form',
                width:610,
                items: [grid2]
		}]
    });

    tab2.render('libreriasValidaciones');
});
</script>



