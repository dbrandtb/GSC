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
    // var para los grids
    var storeDatosFijos;

    
    var selIndexVariables;
    var selIndexValidaciones;
    var selectedId;
    var afuera=0;
 	var temporal=-1;
 //*************** grid variables temporales***********   
    function DatosFijos(){        		        				
 			url='datosFijos/ListaDatosFijos.action'; 			 		 			
 			storeDatosFijos = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaDatosFijos',   
            	totalProperty: 'totalCount',
            	id: 'readerListaDatosFijos'
            	         	
	        	},[
	        		{name: 'descripcionBloque',  type: 'string',  mapping:'descripcionBloque'},
	        		{name: 'descripcionCampo',  type: 'string',  mapping:'descripcionCampo'},
	        		{name: 'codigoExpresion',  type: 'string',  mapping:'codigoExpresion'}
	        		            
				]),
			//autoLoad:true,
			remoteSort: true
    	});
    	storeDatosFijos.setDefaultSort('descripcionBloque', 'desc');
    	//storeVariables.load();
		return storeDatosFijos;
 	}
 	
 	function toggleDetails(btn, pressed){
       	var view = grid.getView();
       	view.showPreview = pressed;
       	view.refresh();
    	}
		
		var cm = new Ext.grid.ColumnModel([
		    new Ext.grid.RowNumberer(),
			{header: "Bloque", 	   dataIndex:'descripcionBloque',	width: 250, sortable:true,id:'descripcionBloque'},		    
		    {header: "Campo",   dataIndex:'descripcionCampo',	width: 250, sortable:true}
		   
		   	                	
        ]);
        var grid1;
        var selectedId;
 		//var afuera;
 		//var temporal=-1;
 		
 	
	gridDatosFijos= new Ext.grid.GridPanel({
		store:DatosFijos(),
		id:'grid-DatosFijos',
		border:true,
		//baseCls:' background:white ',
		cm: cm,
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {							
        	rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			selectedId = storeDatosFijos.data.items[row].id;
	        	 			//var sel = Ext.getCmp('grid-lista1').getSelectionModel().getSelected();
	        	 			//selIndexVariables = storeVariables.indexOf(rec);
                            		 //alert("row"+row);
                            		 
	        	 			Ext.getCmp('eliminar-DatosFijos').on('click',function(){                            		
                            		//DeleteDemouser(storeVariables,selectedId,sel,listaValoresForm);
                                                                                                   
                                 });
                            afuera=row;     
                            Ext.getCmp('editar-DatosFijos').on('click',function(){
                            		
                            		if(afuera!=temporal){
                            			temporal=afuera;
                            			//alert("row"+row);
                            			var codigoExpresion = storeDatosFijos.getAt(row).get('codigoExpresion');
                            			//alert(codigoExpresion);
                            			if(codigoExpresion!=null && codigoExpresion!="" && codigoExpresion!="0" && codigoExpresion!="undefined"){ 
                            				ExpresionesVentana2(codigoExpresion, "EXPRESION", storeDatosFijos, '0', row);
					            		}else{
	            								var connect = new Ext.data.Connection();
											    connect.request ({
													url:'atributosVariables/ObtenerCodigoExpresion.action',
													callback: function (options, success, response) {				   
														codigoExpresion = Ext.util.JSON.decode(response.responseText).codigoExpresion;
														ExpresionesVentana2(codigoExpresion, "EXPRESION", storeDatosFijos, '0', row);
													}
										   		});
					            		}
                            			//ExpresionesDatosFijos (storeDatosFijos,rec,temporal);
                            		}                                                                     
                                 });
                                 temporal=-1;
	                   	 }
	               	}
		}),
		tbar:[{
            text:'Agregar',
            tooltip:'Agregar',
            iconCls:'add',
            handler: function() {                    	
				     	var connect = new Ext.data.Connection();
						    connect.request ({
								url:'atributosVariables/ObtenerCodigoExpresion.action',
								callback: function (options, success, response) {				   
									var codigoDeExpresion = Ext.util.JSON.decode(response.responseText).codigoExpresion;
									ExpresionesVentana2(codigoDeExpresion, "EXPRESION", storeDatosFijos, '0');
								}
					   		});
				     }
        },'-',{
            text:'Eliminar',
            id:'eliminar-DatosFijos',
            tooltip:'Eliminar',
            iconCls:'remove'
            
            
        },'-',{
            text:'Editar',
            id:"editar-DatosFijos",
            tooltip:'Editar',
            iconCls:'option'
           
        }],      							        	    	    
    	width:600,
        height:290,
    	frame:true,     
		title:'Datos Fijos',
		bodyStyle:'padding:5px',
		//renderTo:document.body,
		
	
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:25,
			store: storeDatosFijos,									            
			displayInfo: true,
			displayMsg: 'Mostrando registros {0} - {1} de {1}',
			emptyMsg: "No hay resultados que mostrar"      		    	
			})        							        				        							 
		}); 	
	

	//storeTarificacion.load(); 
 //********************************************************     
    var tab2 = new Ext.FormPanel({
        labelAlign: 'top',
        id: 'tab2-form',
        title: 'Valores por defecto campos fijos',
        bodyStyle:'padding:5px',
        width: 670,
        autoScroll:true,
        heigth:400,
        items: [gridDatosFijos]
    });

    tab2.render('centerDatosFijos');
});
</script>



