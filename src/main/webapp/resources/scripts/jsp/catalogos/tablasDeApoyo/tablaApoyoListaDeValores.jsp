<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/atributosVariables/listasDeValores/listasDeValores.jsp" flush="true" />
<jsp:include page="/resources/scripts/jsp/productos/tablaApoyo5Claves/tablaApoyo5Claves.jsp" flush="true" />
	
<script type="text/javascript">
   Ext.onReady(function(){

    Ext.QuickTips.init();
	Ext.QuickTips.enable();
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';


    var bd = Ext.getBody();
    // var para los grids
    var storeListaDeValores;

    
    var selIndexVariables;
    var selIndexValidaciones;
    var selectedId;
    var afuera;
 	var temporal=-1;
 //*************** grid Lista de Valores***********   
    function ListaDeValores(){        		        				
 			var urlListaDeValores='<s:url namespace='atributosVariables' action='ListaValoresAtributos'/>'; 			 		 			
 			storeListaDeValores = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
					url: urlListaDeValores
        		}),
		        reader: new Ext.data.JsonReader({
        		    root: 'valoresDeAtributos',
        		    totalProperty: 'totalCount',
		            id: 'valoresDeAtributosGrid',
		            successProperty : '@success'
	    		},[
		           {name: 'codigoTabla', type: 'string',mapping:'cdTabla'},
        		   {name: 'descripcionTabla', type: 'string',mapping:'dsTabla'},
		           {name: 'numeroTabla', type: 'string',mapping:'numeroTabla'}    
        		]),
				remoteSort: true
    		});
			return storeListaDeValores;
 	}
 	
 	function toggleDetails(btn, pressed){
       	var view = grid.getView();
       	view.showPreview = pressed;
       	view.refresh();
    	}
		
		var cm = new Ext.grid.ColumnModel([
		    new Ext.grid.RowNumberer(),
			{header: "Codigo Tabla", 	   dataIndex:'codigoTabla',	width: 250, sortable:true,id:'codigoTabla'},		    
		    {header: "Descripcion Tabla",   dataIndex:'descripcionTabla',	width: 250, sortable:true}
		   
		   	                	
        ]);
       
 		
 	
	var gridListaDeValores= new Ext.grid.GridPanel({
		store: ListaDeValores(),
		id:'grid-ListaDeValores',
		border:true,
		//baseCls:' background:white ',
		cm: cm,
		enableColLock: false,
		loadMask: true,
		
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,		
		listeners: {							
        	rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			selectedId = storeListaDeValores.data.items[row].id;
	        	 					 
	        	 			Ext.getCmp('eliminar-ListaDeValores').on('click',function(){                            		
                            		//DeleteDemouser(storeVariables,selectedId,sel,listaValoresForm);
                                 });
                                 
                            afuera=row;     
                            Ext.getCmp('editar-ListaDeValores').on('click',function(){                            		
                            		if(afuera!=temporal){
                            			temporal=afuera;
                            			//alert("temporal"+temporal);
                            			var variable = "edita";
                            			creaListasDeValores(storeListaDeValores,variable,temporal);
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
				     	creaListasDeValores(storeListaDeValores);
				     }
        },'-',{
            text:'Eliminar',
            id:'eliminar-ListaDeValores',
            tooltip:'Eliminar',
            iconCls:'remove'
            
            
        },'-',{
            text:'Editar',
            id:"editar-ListaDeValores",
            tooltip:'Editar',
            iconCls:'option'
           
        }],      							        	    	    
    	width:600,
        height:290,
    	frame:true,     
		//title:'Lista De Valores',
		bodyStyle:'padding:5px',
		//renderTo:document.body,
		
	
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:20,
			store: storeListaDeValores,									            
			displayInfo: true,
			displayMsg: 'Mostrando registros {0} - {1} de {2}',
			emptyMsg: "No hay resultados"
			})        							        				        							 
		}); 	

 var tabListaDeValores = new Ext.FormPanel({
        labelAlign: 'top',
        id: 'tabs-ListaDeValores',
        title:'Lista De Valores',
        bodyStyle:'padding:5px',
        width: 670,
        autoScroll:false,
        heigth:400,
        items: [{
                
                layout:'form',
                width:610,
                items: [gridListaDeValores]
           
        }]
    });
    tabListaDeValores.render('tablaApoyoListaDeValores');
});
</script>



