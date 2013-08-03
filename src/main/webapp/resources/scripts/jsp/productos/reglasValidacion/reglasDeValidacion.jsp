<%@ include file="/taglibs.jsp"%>
<!-- LIBRARIES -->
<!-- SOURCE CODE -->
<jsp:include page="/resources/scripts/jsp/utilities/reglasValidacion/configurarReglasValidacion.jsp" flush="true" />


<script type="text/javascript">
   Ext.onReady(function(){

    Ext.QuickTips.init();
	Ext.QuickTips.enable();
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';

    var bd = Ext.getBody();
    // var para los grids
    var storeReglasValidacion;

    
    var selIndexVariables;
    var selIndexValidaciones;
    var selectedId;
    var afuera;
 	var temporal=-1;
 //*************** grid reglas de validacion***********   
    function ReglasValidacion(){        		        				
 			url='reglaValidacion/CargaListasReglasDeValidacion.action?lista=reglasDeValidacion'; 			 		 			
 			storeReglasValidacion = new Ext.data.Store({
    			proxy: new Ext.data.HttpProxy({
				url: url
        		}),
        		reader: new Ext.data.JsonReader({
            	root:'listaReglasValidacion',   
            	totalProperty: 'totalCount',
            	id: 'readerlistaReglasValidacion'
            	         	
	        	},[
	        		{name: 'codigoBloque',  type: 'string',  mapping:'codigoBloque'},
	        		{name: 'descripcionBloque',  type: 'string',  mapping:'descripcionBloque'},
	        		{name: 'codigoValidacion',  type: 'string',  mapping:'codigoValidacion'},
	        		{name: 'descripcionValidacion',  type: 'string',  mapping:'descripcionValidacion'},
	        		{name: 'codigoCondicion',  type: 'string',  mapping:'codigoCondicion'},
	        		{name: 'descripcionCondicion',  type: 'string',  mapping:'descripcionCondicion'},
	        		{name: 'secuencia',  type: 'string',  mapping:'secuencia'}
	        		            
				]),
			//autoLoad:true,
			remoteSort: true
    	});
    	storeReglasValidacion.setDefaultSort('descripcionBloque', 'desc');
    	//storeVariables.load();
		return storeReglasValidacion;
 	}
 	
 	function toggleDetails(btn, pressed){
       	var view = grid.getView();
       	view.showPreview = pressed;
       	view.refresh();
    	}
		
		var cm = new Ext.grid.ColumnModel([
		    new Ext.grid.RowNumberer(),
			{header: "Clave", 	   dataIndex:'codigoBloque',	width: 150, sortable:true,id:'codigoBloque'},		    
		    {header: "Descripción",   dataIndex:'descripcionBloque',	width: 150, sortable:true},
		    {header: "Secuencia",   dataIndex:'secuencia',	width: 150, sortable:true},
		    {header: "Validación",   dataIndex:'descripcionValidacion',	width: 150, sortable:true},
		    {header: "condición",   dataIndex:'descripcionCondicion',	width: 150, sortable:true}
		   
		   	                	
        ]);
        var grid1;
        var selectedId;
 		//var afuera;
 		//var temporal=-1;
 		
 	
	gridReglasValidacion= new Ext.grid.GridPanel({
		store:ReglasValidacion(),
		id:'grid-ReglasValidacion',
		border:true,
		//baseCls:' background:white ',
		cm: cm,
		sm: new Ext.grid.RowSelectionModel({
		singleSelect: true,
		listeners: {							
        	rowselect: function(sm, row, rec) {	                    		                    	                        	                     	                        
	        	 			selectedId = storeReglasValidacion.data.items[row].id;
	        	 			//var sel = Ext.getCmp('grid-lista1').getSelectionModel().getSelected();
	        	 			//selIndexVariables = storeVariables.indexOf(rec);
                            		 //alert("row"+row);
                            		 
                            afuera=row;     
	        	 			Ext.getCmp('eliminar-ReglasValidacion').on('click',function(){                            		
                            		if(afuera!=temporal){
                            			temporal=afuera;                           			
                            			var rec = storeReglasValidacion.getAt(temporal);
    									var eliminaCodigoBloque= rec.get('codigoBloque');                          
    									var eliminaSecuencia=rec.get('secuencia');
                            			Ext.MessageBox.confirm('Mensaje','Esta seguro que desea eliminar este elemento?', function(btn) {
           									if(btn == 'yes'){ 
           			     
			     									var conn = new Ext.data.Connection();
                 									conn.request ({	
				    				 					url:'reglaValidacion/EliminarReglaValidacion.action?codigoBloque='+eliminaCodigoBloque+'&secuencia='+eliminaSecuencia,
				     									waitTitle:'<s:text name="ventana.configReglasValidacion.proceso.mensaje.titulo"/>',
					            						waitMsg:'<s:text name="ventana.configReglasValidacion.proceso.mensaje"/>',
			     	 									callback: function (options, success, response) {			     	 		
                            	 							if (Ext.util.JSON.decode(response.responseText).success == false) {
                              	 								Ext.Msg.alert('Status', 'Error al eliminar el elemento');                               
                             								} else {
                                 								Ext.Msg.alert('Status', 'Elemento eliminado');
                                 								storeReglasValidacion.load();
                               								}
                           								}
        	 										});         	 
        									}
       									});
                                    }                                                               
                                 });
                                 temporal=-1;
                            Ext.getCmp('editar-ReglasValidacion').on('click',function(){
                            		
                            		if(afuera!=temporal){
                            			temporal=afuera;
                            			//alert("temporal"+temporal);
                            			configuraReglasValidacion (storeReglasValidacion,temporal);
                            		}                                                                     
                                 });
                                 temporal=-1;
	                   	 }
	               	}
		}),
		tbar:[{
            text:'Agregar',
            tooltip:'Agregar regla de validación',
            iconCls:'add',
            handler: function() {                    	
				     	configuraReglasValidacion(storeReglasValidacion);
				     }
        },'-',{
            text:'Eliminar',
            id:'eliminar-ReglasValidacion',
            tooltip:'Eliminar regla de validación',
            iconCls:'remove'
            
            
        },'-',{
            text:'Editar',
            id:'editar-ReglasValidacion',
            tooltip:'Editar regla de validación',
            iconCls:'option'
           
        }],      							        	    	    
    	width:600,
        height:290,
    	frame:true,     
		title:'<s:text name="productos.configReglasValidacion.titulo"/>',
		bodyStyle:'padding:5px',
		//renderTo:document.body,
		
	
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:25,
			store: storeReglasValidacion,									            
			displayInfo: true,
			displayMsg: 'Displaying rows {0} - {1} of {1}',
			emptyMsg: "No rows to display"      		    	
			})        							        				        							 
		}); 	
	

	//storeTarificacion.load(); 
 //********************************************************     
    var tabReglasValidacion = new Ext.FormPanel({
        //labelAlign: 'top',
        id: 'tabReglasValidacion-form',
        //title: 'Valores por defecto campos fijos',
        bodyStyle:'padding:5px',
        width: 620,
        autoScroll:true,
        heigth:400,
        items: [gridReglasValidacion]
    });

    tabReglasValidacion.render('centerReglasValidacion');
});
</script>



