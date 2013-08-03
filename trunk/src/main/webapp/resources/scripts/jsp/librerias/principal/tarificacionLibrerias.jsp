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

    var storeTarificacion;

    

    var selectedId;
    var afuera;
 	var temporal=-1;
 
 //********************************************************     
 //********************grid concepto de tarificacion********************
     function tarificacion(){        		        				
 			url='librerias/CargaReglaNegocio.action'+'?numeroGrid='+'5' 			 		 			
 			storeTarificacion = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaReglaNegocioTarificacion',   
            	totalProperty: 'totalCount',
            	id: 'nombreCabecera',
            	successProperty : '@success'
            	         	
	        	},[
	        		{name: 'nombreCabecera',  type: 'string',  mapping:'nombre'},
	        		{name: 'descripcionCabecera',  type: 'string',  mapping:'descripcion'},
	        		{name: 'descripcionTipo',  type: 'string',  mapping:'descripcionTipo'},
	        		{name: 'codigoExpresion',  type: 'string',  mapping:'codigoExpresion'}
	        		            
				]),
			//autoLoad:true,
			remoteSort: true
    	});
    	storeTarificacion.setDefaultSort('descripcionCabecera', 'desc');
    	//storeTarificacion.load();
		return storeTarificacion;
 	}
 	
 	function toggleDetails(btn, pressed){
       	var view = grid.getView();
       	view.showPreview = pressed;
       	view.refresh();
    	}
		
		var cmTarificacion = new Ext.grid.ColumnModel([
		    new Ext.grid.RowNumberer(),
			{header: "Nombre", 	   dataIndex:'nombreCabecera',	width: 120, sortable:true,id:'nombreCabecera'},		    
		    {header: "Descripción",   dataIndex:'descripcionCabecera',	width: 120, sortable:true},
		    {header: "Tipo",   dataIndex:'descripcionTipo',	width: 120, sortable:true}
		   
		   	                	
        ]);
        var grid5;
        var selectedId;
 		
 	function createGridTarificacion(){
	grid5= new Ext.grid.GridPanel({
		store:tarificacion(),
		id:'grid-tarificacion',
		border:true,
		//baseCls:' background:white ',
		cm: cmTarificacion,
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {							
        	rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			selectedId = storeTarificacion.data.items[row].id;
	        	 			//var sel = Ext.getCmp('grid-lista5').getSelectionModel().getSelected();
	        	 			//selIndexValidaciones = storeTarificacion.indexOf(sel);
	        	 			Ext.getCmp('eliminar-tarificacion').on('click',function(){                            		
                            		//DeleteDemouser(storeTarificacion,selectedId,sel,listaValoresForm);
                                                                                                       
                                 });
                                 
                            afuera=row;     
                            Ext.getCmp('editar-tarificacion').on('click',function(){
                            		
                            		if(afuera!=temporal){
                            			temporal=afuera;
                            			//alert("temporal"+temporal);
                            			var codigoExpresion = storeTarificacion.getAt(row).get('codigoExpresion');
                            			//alert(codigoExpresion);
                            			if(codigoExpresion!=null && codigoExpresion!="" && codigoExpresion!="0" && codigoExpresion!="undefined"){ 
                            				ExpresionesVentana2(codigoExpresion, "EXPRESION", storeTarificacion, '2', row);
					            		}else{
	            								var connect = new Ext.data.Connection();
											    connect.request ({
													url:'atributosVariables/ObtenerCodigoExpresion.action',
													callback: function (options, success, response) {				   
														codigoExpresion = Ext.util.JSON.decode(response.responseText).codigoExpresion;
														ExpresionesVentana2(codigoExpresion, "EXPRESION", storeTarificacion, '2', row);
													}
										   		});
					            		}
                            			//ExpresionesVentanaTarificaciones(storeTarificacion,rec,temporal);
                            		}                                                                     
                                 });
                                 temporal=-1;         
                                 
                            /*Ext.getCmp('editar-grid').on('click',function(){                            		
                            		 ExpresionesVentanaTarificaciones(storeTarificacion,rec,row);
                                                                                                                
                                 });*/     
	                   	 }
	               	}
		}),
		tbar:[{
            text:'<s:text name="librerias.btn.agregar"/>',
            tooltip:'Agregar tarificación',
            iconCls:'add',
            handler: function() {                    	
				     	var connect = new Ext.data.Connection();
						    connect.request ({
								url:'atributosVariables/ObtenerCodigoExpresion.action',
								callback: function (options, success, response) {				   
									var codigoDeExpresion = Ext.util.JSON.decode(response.responseText).codigoExpresion;
									ExpresionesVentana2(codigoDeExpresion, "EXPRESION", storeTarificacion, '2');
								}
					   		});
				     }
        },'-',{
            text:'<s:text name="librerias.btn.eliminar"/>',
            id:'eliminar-tarificacion',
            tooltip:'Eliminar tarificación',
            iconCls:'remove'
            
        },'-',{
            text:'<s:text name="librerias.btn.editar"/>',
            id:'editar-tarificacion',
            tooltip:'Editar tarificación',
            iconCls:'option'
        }],      							        	    	    
    	width:600,
        height:370,
    	frame:true,     
		//title:'Concepto De Tarificación',
		bodyStyle:'padding:5px',
		//renderTo:document.body,
		
	
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:20,
			store: storeTarificacion,									            
			displayInfo: true,
			displayMsg: 'Mostrando registros {0} - {1} de {2}',
			emptyMsg: "No hay resultados"
			})        							        				        							 
		}); 	
	}
	
	createGridTarificacion();
	//storeTarificacion.load(); 
 //********************************************************     
    var tab2 = new Ext.FormPanel({
        labelAlign: 'top',
        id: 'tab2-form5',
        title: '<s:text name="librerias.titulo.conceptoTarif"/>',
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
                //title:'Conceptos de Tarificación',
                layout:'form',
                width:610,
                items: [grid5]
		}]
    });

    tab2.render('libreriasTarificacion');
});
</script>



