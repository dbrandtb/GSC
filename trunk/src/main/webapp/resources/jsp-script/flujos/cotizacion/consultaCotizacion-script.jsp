<script type="text/javascript">
    var _ACTION_OBTIENE_TVALOSIT3 = "<s:url action='obtieneTVALOSIT3' namespace='/flujocotizacion'/>";
    var _ACTION_OBTIENE_COBERTURAS = "<s:url action='obtieneCoberturas' namespace='/flujocotizacion'/>";
    var _ACTION_COMPRAR_COTIZACIONES = "<s:url action='comprarCotizaciones' namespace='/flujocotizacion'/>";
    var _ACTION_CONSULTA_COTIZACIONES_COMPRAR = "<s:url action='consultaCotizacionesComprar' namespace='/flujocotizacion'/>";
    var _ACTION_OBTIENE_TVALOSIT_COTIZA = "<s:url action='obtieneTvalositCotiza' namespace='/flujocotizacion'/>";
</script>

<script type="text/javascript">
Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
	
	var itemsPerPage=20;
	var psm;
	var prow;
	var prec;
	
	var busqueda = new Ext.form.TextField({
		fieldLabel:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
		labelSeparator:'',	
		hidden:true			
	});
	var storeAseguradora = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: '<s:url namespace="/flujocotizacion" action="obtieneAseguradoras" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaAseguradoras',
            id: 'listaAseguradoras'
	        },[
           {name: 'codigo', type: 'string',mapping:'value'},
           {name: 'descripcion', type: 'string',mapping:'label'}
        ]),
		remoteSort: true
    });
    storeAseguradora.load();

    var numeroCotizacion = new Ext.form.TextField({
		fieldLabel: 'N&uacute;mero de Cotizaci&oacute;n',
		name: 'numerocotizacion',
		id: 'txtNumeroCotizacion',
		labelSeparator:':',
		width: 150
    });

	var comboAseguradora =new Ext.form.ComboBox({
							id:'id-combo-aseguradora-consulta-cotizacion',
    	                    //tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeAseguradora,
						    displayField:'descripcion',
						    valueField: 'codigo',
					    	typeAhead: true,
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione Aseguradora...',
					    	selectOnFocus:true,
					    	forceSelection:true,
					    	width:250,
						    fieldLabel: 'Aseguradora',
					    	name:"descripcionAseguradoraForm",
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
					                var valor = record.get('codigo');
									Ext.getCmp('id-hidden-codigo-combo-aseguradora').setValue(valor);
									
							}
			});

	var storeProducto = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
			url: '<s:url namespace="/flujocotizacion" action="obtieneProductos" includeParams="none"/>'
        }),
        reader: new Ext.data.JsonReader({
            root: 'listaProductos',
            id: 'listaProductos'
	        },[
           {name: 'codigo', type: 'string',mapping:'value'},
           {name: 'descripcion', type: 'string',mapping:'label'}
        ]),
		remoteSort: true
    });
    storeProducto.load();

	var comboProducto =new Ext.form.ComboBox({
							id:'id-combo-producto-consulta-cotizacion',
    	                    //tpl: '<tpl for="."><div ext:qtip="{value}. {nick}" class="x-combo-list-item">{value}</div></tpl>',
							store: storeProducto,
						    displayField:'descripcion',
						    valueField: 'codigo',
					    	typeAhead: true,
					    	mode: 'local',
					    	triggerAction: 'all',
						    emptyText:'Seleccione Producto...',
					    	selectOnFocus:true,
					    	forceSelection:true,
					    	width:250,
						    fieldLabel: 'Producto',
					    	name:"descripcionProductoForm",
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
					                var valor = record.get('codigo');
									Ext.getCmp('id-hidden-codigo-combo-producto').setValue(valor);
							}
			});	

	
   function selecGridChkBox(sm, row, rec){
     psm  = sm;
     prow = row;
     prec = rec;	
   }
	
   var sm2 = new Ext.grid.CheckboxSelectionModel({
    	listeners: {       
			rowselect: function(sm, row, rec) {
				Ext.getCmp('detalleInciso').on('click',function(){
	             selecGridChkBox(sm, row, rec);		
			 	});
			}
		}
	});
   
    sm2.singleSelect = true;

	var cm = new Ext.grid.ColumnModel([
	sm2,{
		header:"Cotizaci&oacute;n",
		dataIndex:'nmsolici',
		width:100,
		sortable:true
	},{
		header:"Aseguradora",
		dataIndex:'aseguradora',
		width:250,
		sortable:true
	},{
		header:"Producto",
		dataIndex:'producto',
		width:250,
		sortable:true
	},{
		header:"Fecha",
		dataIndex:'fecha',
		width:100,
		sortable:true//,
		//renderer: Ext.util.Format.dateRenderer('d/m/Y')
	}]);
	cm.defaultSortable = true;

	var url = "<s:url action='/flujocotizacion/buscarCotizacion.action' />";
	var store=new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({
			url:url
		}),
		reader: new Ext.data.JsonReader({
			root:'listaConsultaCotizacion',
			totalProperty:'totalCount',
			id:'consultaCotizacionReader',
			successProperty : '@success'
		},[
		{name:'aseguradora',		type:'string', 		mapping:'descripcionAseguradora'},
		{name:'codigoAseguradora',	type:'string', 		mapping:'codigoAseguradora'},		
		{name:'producto', 			type:'string', 		mapping:'descripcionProducto'},
		{name:'codigoProducto',		type:'string', 		mapping:'codigoProducto'},		
		{name:'fecha', 				type:'string', 		mapping:'fecha'},
		{name:'cdcia', 				type:'string', 		mapping:'cdcia'},
		{name:'estado',				type:'string', 		mapping:'estado'},
		{name:'nmpoliza', 			type:'string', 		mapping:'nmpoliza'},
		{name:'nmsituac', 			type:'string', 		mapping:'nmsituac'},
		{name:'nmsuplem', 			type:'string', 		mapping:'nmsuplem'},
		{name:'nmsolici', 			type:'int', 		mapping:'nmsolici'},
		{name:'fevencim', 			type:'string', 		mapping:'fevencim'},
		{name:'cdtipsit', 			type:'string', 		mapping:'cdtipsit'}
		]),
		baseParams:{
			aseguradoraForm:'',
			productoForm:''		
		}
	});
	
	var grid2;
	var claveDeConfiguracion;
	var claveDeSeccion;
	
	grid2 = new Ext.grid.GridPanel({
		id:'id-grid-consulta-cotizacion',
		store:store,
		title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
		stripeRows: true,
		cm:cm,
		width: 750,
		buttons:[
				{
				id: 'detalleInciso',
				text:'Detalle',
            	tooltip:'Detalle',
            	handler:function(){
						if(Ext.getCmp('id-grid-consulta-cotizacion').getSelectionModel().hasSelection()){
	            	         var urlDetalleCotizacion = 
								"?cdunieco=" + psm.getSelected().get('codigoAseguradora') +
								"&cdramo=" + psm.getSelected().get('codigoProducto') +
								"&estado=" + psm.getSelected().get('estado') +
								"&nmpoliza=" + psm.getSelected().get('nmpoliza') +
								"&nmsituac=" + psm.getSelected().get('nmsituac') +
								"&nmsuplem=" + psm.getSelected().get('nmsuplem') +
								"&dsunieco=" + psm.getSelected().get('aseguradora') +
								"&cdcia=" + psm.getSelected().get('cdcia') +
								"&cdtipsit=" + psm.getSelected().get('cdtipsit');
							window.location.href = _CONTEXT + '/flujocotizacion/detalleConsultaCotizacion.action' + urlDetalleCotizacion;
						} else {
							Ext.Msg.alert('Aviso', 'Selecciona un registro para realizar esta operaci&oacute;n');
						}
					}
            	},{
            	text:'Eliminar',
            	tooltip:'Elimina las filas seleccionadas',
            	handler:function(){
	            	if(Ext.getCmp('id-grid-consulta-cotizacion').getSelectionModel().hasSelection()){
	            	
	            	/////////////////////////////////
	            	confirmaPassword(eliminarCotizacion);
	            	/////////////////////////////////
            		}else{
            			Ext.Msg.alert('Aviso', 'Selecciona un registro para realizar esta operaci&oacute;n');
	            	}
            	}
            	}],
		height:250,
		width: 750,
        buttonAlign:'center',        
        sm:sm2,
		bbar: new Ext.PagingToolbar({
			pageSize:20,
			store: store,									            
			displayInfo: true,
			displayMsg:'<span style="color:white;">Mostrando registros {0} - {1} de {2}</span>',
    	    emptyMsg:'<span style="color:white;">No hay registros para visualizar</span>',
    		beforePageText: 'P&aacute;gina',
    		afterPageText: 'de {0}'
		})        		
	});

	
function reloadGrid(){
	var mStore = grid2.store;
	var o = {start: 0};
	mStore.baseParams = mStore.baseParams || {};
	mStore.baseParams['codigoProductoForm']=comboProducto.getValue();
	mStore.baseParams['codigoAseguradoraForm']=comboAseguradora.getValue();
	mStore.baseParams['codigoCotizacionForm']=numeroCotizacion.getValue();
	mStore.reload(
		{
			params:{start:0, limit:itemsPerPage},
			callback: function(r, options, success){				
				if(!success){
					Ext.MessageBox.alert('No se encontraron registros');
                    mStore.removeAll();
				}
			}					
		}
	);
}
	
  
	var filterAreaForm = new Ext.form.FormPanel({
		id:'id-carga-valores',
		title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Búsqueda</span>',
		layout: 'form',
		id:'filterArea',
		bodyStyle:'padding:5px 5px 0',
		buttonAlign:'center',
		labelAlign:'right',
		labelWidth: 200,
		border:false,
		width:760,
		autoHeight : true,
		items:[ {xtype:'hidden', id:'id-hidden-codigo-combo-aseguradora', name:'codigoAseguradoraForm'},
				{xtype:'hidden', id:'id-hidden-codigo-combo-producto', name:'codigoProductoForm'},{
				layout:'form',
				border:false,
				
				items:[{
					bodyStyle:'background:white',
					labelWidth:150,
					layout:'form',
					frame:true,
					baseCls:'',
					buttonAlign:'center',
					items:[	numeroCotizacion,
							comboAseguradora,
							comboProducto
					],
					buttons:[{
						text:'Buscar',
						handler:function(){
							reloadGrid();
						}
						},{
						text:'Cancelar',
							handler:function(){
								comboAseguradora.reset();
								comboProducto.reset();
								numeroCotizacion.reset();
							}
						}]
					}]
			}]
		});						
	
	var resultAreaForm = new Ext.form.FormPanel({
		layout:'form',
		id:'resultArea',
        border: false,
        bodyStyle:'padding:5px 5px 0',
        autoHeight : true,
		width:760,
		items:[grid2]
	});
	
	function toggleDetails(btn, pressed){
		var view = grid2.getView();
		view.showPreview=pressed;
		view.refresh();
	}
	
	
	function eliminarCotizacion(){
		Ext.MessageBox.confirm('Mensaje','Esta seguro que desea eliminar este elemento?', function(btn) {
			if(btn == 'yes'){
	
				var records = Ext.getCmp('id-grid-consulta-cotizacion').getSelectionModel().getSelections();
				var semod = Ext.getCmp('id-grid-consulta-cotizacion').getSelectionModel().getCount();
				     		
				var params = "";
				    			
				for(var i = 0 ; i < semod;i++){
					params += "listaConsultaCotizacion["+i+"].codigoAseguradora=" + records[i].get('codigoAseguradora') + "&&" ;
					params += "listaConsultaCotizacion["+i+"].descripcionAseguradora=" + records[i].get('aseguradora') + "&&" ;
					params += "listaConsultaCotizacion["+i+"].codigoProducto=" + records[i].get('codigoProducto') + "&&" ;
					params += "listaConsultaCotizacion["+i+"].descripcionProducto=" + records[i].get('producto') + "&&" ;
					params += "listaConsultaCotizacion["+i+"].fecha=" + records[i].get('fecha') + "&&" ;
					params += "listaConsultaCotizacion["+i+"].cdcia=" + records[i].get('cdcia') + "&&" ;
					params += "listaConsultaCotizacion["+i+"].estado=" + records[i].get('estado') + "&&" ;
					params += "listaConsultaCotizacion["+i+"].nmpoliza=" + records[i].get('nmpoliza') + "&&" ;
					params += "listaConsultaCotizacion["+i+"].nmsituac=" + records[i].get('nmsituac') + "&&" ;
					params += "listaConsultaCotizacion["+i+"].nmsuplem=" + records[i].get('nmsuplem') + "" ;
				}
				
				var conn = new Ext.data.Connection();
				conn.request ({
					url:'<s:url namespace="/flujocotizacion" action="borrarCotizaciones" includeParams="none"/>',
					method: 'POST',
					successProperty : '@success',
					params : params,
				    callback: function (options, success, response) {
						if (Ext.util.JSON.decode(response.responseText).success == false) {
							Ext.Msg.alert('Error', 'No se pudieron eliminar las cotizaciones');
						} else {
							Ext.Msg.alert('Confirmación', 'Se han eliminado con éxito las cotizaciones');
							Ext.getCmp('id-grid-consulta-cotizacion').getStore().load();
						}
					},
					waitTitle:'<s:text name="ventana.tabla5claves.proceso.mensaje.titulo"/>',
					waitMsg:'<s:text name="ventana.tabla5claves.proceso.mensaje"/>'
				});
				Ext.getCmp('id-grid-consulta-cotizacion').getSelectionModel().clearSelections();
		
		
			}
		});
	}


	var panelPrincipal = new Ext.Panel({
        region: 'north',
        title: 'Consulta de Cotizaciones',
        autoHeight : true,
        width: 760,
        id:'panelPrincipal',
        items: [filterAreaForm, resultAreaForm ] 
    
    });
	panelPrincipal.render('items');
	
	
});
</script>