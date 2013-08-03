var helpMap = new Map();

Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
    var itemsPerPage=10;


	/********* Comienza el form ********************************/
	var el_form = new Ext.FormPanel ({
		id:'el_formConsAlertId',
		renderTo: 'formulario',
		url : _ACTION_BUSCAR_ALERTAS,
		title: '<span style="color:black;font-size:12px;align=left;">'+getLabelFromMap('13',helpMap,'Consulta de Alertas')+'</span>',
	    labelWidth : 80,
	    frame : true,
        bodyStyle:'background: white',
        width : 500,
        height: 208,
        labelAlign:'right',
        waitMsgTarget : true,
		items:[
				{
					html: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
		            layout: 'column',
		            layoutConfig: {columns: 2},
					items: [
	            			{
	            			layout: 'form',
	            			columnWidth: .50,
	            			items: [
					                  {xtype: 'textfield', id: 'dsUsuario', 
					                  fieldLabel: getLabelFromMap('ctaAlerDsUsuario',helpMap,'Usuario'),
					                  tooltip: getToolTipFromMap('ctaAlerDsUsuario',helpMap,'Usuario'),
					                  //allowBlank : false, 
					                  name: 'dsUsuario'}
	            					]
	            			},
	            			{
	            			layout: 'form',
	            			columnWidth: .50,
	            			items: [
	            						{xtype: 'textfield',
	            						fieldLabel: getLabelFromMap('ctaAlerDsRamo',helpMap,'Producto'),
	            						tooltip: getToolTipFromMap('ctaAlerDsRamo',helpMap,'Producto'),
	            						 name: 'dsRamo', id: 'dsRamo'}
	            					]
	            			},
	            			{
	            			layout: 'form',
	            			columnWidth: .50,
	            			items: [
					                    {xtype: 'textfield', id: 'dsCliente', 
					                    fieldLabel: getLabelFromMap('ctaAlerDsCliente',helpMap,'Cliente'),
					                    tooltip: getToolTipFromMap('ctaAlerDsCliente',helpMap,'Cliente'),
					                    name: 'dsCliente'}
	            					]
	            			},
	            			{
	            			layout: 'form',
	            			columnWidth: .50, //Para 2 columnas
	            			items: [
	            						{xtype: 'textfield',
	            						 fieldLabel: getLabelFromMap('ctaAlerDsAseguradora',helpMap,'Aseguradora'),
	            						 tooltip: getToolTipFromMap('ctaAlerDsAseguradora',helpMap,'Aseguradora'),
	            						 align: 'rigth',id: 'dsAseguradora', name: 'dsAseguradora', 
	            						 //allowBlank : false, 
	            						 anchor: 'auto'}
	            					]
	            			},
		        			{
		        				layout: 'form',
		        				columnWidth: .50,
		        				items: [
		        						{xtype: 'textfield',
		        						 fieldLabel: getLabelFromMap('ctaAlerDsProceso',helpMap,'Proceso'),
		        						 tooltip: getToolTipFromMap('ctaAlerDsProceso',helpMap,'Proceso'),
		        						 name: 'dsProceso', 
		        						 id: 'dsProceso', 
		        						 anchor: 'auto'}
		        					   ]
		        			},
		        			{
		        			layout: 'form',
		        			columnWidth: .50,
		        			items: [
		        						{xtype: 'textfield', 
		        						fieldLabel: getLabelFromMap('ctaAlerDsRol',helpMap,'Rol'),
		        						tooltip: getToolTipFromMap('ctaAlerDsRol',helpMap,'Rol'), 
		        						name: 'dsRol', id: 'dsRol',
		        						anchor: 'auto'}
		        					]
		        			},
		        			{
				   				html: '<span class="x-form-item" style="font-weight:bold"></span><br>'
				   			}
							],
 	    			buttonAlign: 'center',
					buttons: [
       						{
       						id:'btnFindConsAlerId',
       						text: getLabelFromMap('btnFindConsAlerId',helpMap,'Buscar'), 
       						tooltip: getToolTipFromMap('btnFindConsAlerId',helpMap,'Busca consulta de alertas'),
       						handler: function () {
    														if (el_form.form.isValid()) {
    															if (grilla != null) {
    																reloadGrid();
    															}else {
    																createGrid();
    															}
    														}
        									}
       						},
       						{
       						id:'btnCancelConsAlertId',
       						text: getLabelFromMap('btnCancelConsAlertId',helpMap,'Cancelar'), 
       						tooltip: getToolTipFromMap('btnCancelConsAlertId',helpMap,'Cancela operaci&oacute;n') ,
       						handler: function() {
       							el_form.getForm().reset();
       							}
       						}
       					]
			}]
	});		
		
		
		
		
		
		
/*		
		
	var el_form = new Ext.FormPanel ({

    width : 500,
    labelAlign:'right',            
    waitMsgTarget : true,
    items:[
    	   {
		 	layout: 'form',
		  	border:'false',  
			items:[
					{
					align:'left',
				    layout: 'column',
				    frame: true,
				    bodyStyle:'background: white',
				    layoutConfig: {columns: 2, align: 'left'},
					defaults: {labelWidth: 90}, 
		            
			        	}	
			        	]
					}
				]
 
	});
*/
	

/********* Comienzo del grid *****************************/
		//Definición Column Model
	    var cm = new Ext.grid.ColumnModel([
	    		{	    			
	    			dataIndex: 'codigoConfAlerta',
	    			hidden: true,
	    			header: 'Código Config'
	    		},
	    		{
	    			dataIndex: 'cdIdUnico',
	    			hidden: true,
	    			header: 'Id Alerta'
	    		},
				{	
					id:'cmGridConfigANombreId',
		           	header: getLabelFromMap('cmGridConfigANombreId',helpMap,'Cliente'),
		           	tooltip:getToolTipFromMap('cmGridConfigANombreId',helpMap),
		           	dataIndex: 'dsNombre',
	           		sortable: true,
		           	width: 80
	        	},{
		           	id:'cmGridConfigAProcesoId',
		           	header: getLabelFromMap('cmGridConfigAProcesoId',helpMap,'Proceso'),
		           	tooltip:getToolTipFromMap('cmGridConfigAProcesoId',helpMap),
		           	dataIndex: 'dsProceso',
	           		sortable: true,
		           	width: 80
	           	},{
		           	id:'cmGridConfigAUsuarioId',
		           	header: getLabelFromMap('cmGridConfigAUsuarioId',helpMap,'Usuario'),
		           	tooltip:getToolTipFromMap('cmGridConfigAUsuarioId',helpMap),
		           	dataIndex: 'dsUsuario',
	           		sortable: true,
		           	width: 80
	           	},{
		           	id:'cmGridConfigAAsegId',
		           	header: getLabelFromMap('cmGridConfigAAsegId',helpMap,'Aseguradora'),
		           	tooltip:getToolTipFromMap('cmGridConfigAAsegId',helpMap),
	           		sortable: true,
		           	dataIndex: 'dsUniEco',
		           	width: 80
	           	},
	           	{
	           		id:'cmGridConfigAPolId',
		           	header: getLabelFromMap('cmGridConfigAPolId',helpMap,'P&oacute;liza'),
		           	tooltip:getToolTipFromMap('cmGridConfigAPolId',helpMap),
	           		sortable: true,
	           		dataIndex: 'nmPoliza',
		           	width: 80
	           	},
	           	{
	           		id:'cmGridConfigAReciboId',
		           	header: getLabelFromMap('cmGridConfigAReciboId',helpMap,'Recibo'),
		           	tooltip:getToolTipFromMap('cmGridConfigAReciboId',helpMap),
	           		sortable: true,
	           		dataIndex: 'nmRecibo',
		           	width: 80
	           	},
	           	{
	           		id:'cmGridConfigAUEId',
		           	header: getLabelFromMap('cmGridConfigAUEId',helpMap,'Ult. Envio'),
		           	tooltip:getToolTipFromMap('cmGridConfigAUEId',helpMap),
	           		sortable: true,
	           		dataIndex: 'feUltimoEvento',
		           	width: 80
	           	},
	           	{
	           		id:'cmGridConfigASEId',
		           	header: getLabelFromMap('cmGridConfigASEId',helpMap,'Sig. Envio'),
		           	tooltip:getToolTipFromMap('cmGridConfigASEId',helpMap),
	           		sortable: true,
	           		dataIndex: 'feSiguienteEnvio',
		           	width: 80
		           	},{
	           		id:'cmGridConfigARolId',
		           	header: getLabelFromMap('cmGridConfigARolId',helpMap,'Rol'),
		           	tooltip:getToolTipFromMap('cmGridConfigARolId',helpMap),
	           		sortable: true,
	           		dataIndex: 'dsIsRol',
		           	width: 80
		           	},{
	           		id:'cmGridConfigARamoId',
		           	header: getLabelFromMap('cmGridConfigARamoId',helpMap,'Producto'),
		           	tooltip:getToolTipFromMap('cmGridConfigARamoId',helpMap),
	           		sortable: true,
	           		dataIndex: 'dsRamo',
		           	width: 80
		        }
	           	]);

		function crearGridStore(){
		 			store = new Ext.data.Store({
		    			proxy: new Ext.data.HttpProxy({
						url: _ACTION_BUSCAR_ALERTAS,
						waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')
		                }),
		                reader: new Ext.data.JsonReader({
		            	root:'MAlertasUsuario',
		            	totalProperty: 'totalCount',
			            successProperty : '@success'
			        },[
			        {name: 'cdIdUnico', type: 'string', mapping: 'cdIdUnico'},
			        {name: 'dsNombre',  type: 'string',  mapping:'dsNombre'},
			        {name: 'dsProceso',  type: 'string',  mapping:'dsProceso'},
			        {name: 'dsUsuario',  type: 'string',  mapping:'dsUsuario'},
			        {name: 'cdUniEco',  type: 'string',  mapping:'cdUniEco'},
			        {name: 'dsUniEco',  type: 'string',  mapping:'dsUniEco'},
			        {name: 'nmPoliza',  type: 'string',  mapping:'nmPoliza'},
			        {name: 'nmRecibo',  type: 'string',  mapping:'nmRecibo'},
			        {name: 'feUltimoEvento',  type: 'string',  mapping:'feUltimoEvento'},
			        {name: 'feSiguienteEnvio',  type: 'string',  mapping:'feSiguienteEnvio'},
			        {name: 'codigoConfAlerta', type: 'string', mapping: 'codigoConfAlerta'},
			        {name: 'dsIsRol', type: 'string', mapping: 'dsIsRol'},
			        {name: 'dsRamo', type: 'string', mapping: 'dsRamo'}
					])
		        });
				return store;
		 	}
	var grilla;
	
	function createGrid(){
		grilla= new Ext.grid.GridPanel({
		    id: 'grid2',
	        el:'gridConfiguraAlertas',
	        store:crearGridStore(),
	        title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
			border:true,
	        cm: cm,
	        buttonAlign:'center',
	        bodyStyle:'background: white',
	        stripeRows: true,
	        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
	        collapsible: true,
			buttons:[
		        		{	id:'gridBtnAddConfAlId',
		        			text:getLabelFromMap('gridBtnAddConfAlId',helpMap,'Agregar'),
							tooltip:getToolTipFromMap('gridBtnAddConfAlId',helpMap,'Agrega consulta de alerta'),
		            		handler:function(){agregar()}
		            	},
		            	{
		            		id:'gridBtnEditConfAlId',
		        			text:getLabelFromMap('gridBtnEditConfAlId',helpMap,'Editar'),
							tooltip:getToolTipFromMap('gridBtnEditConfAlId',helpMap,'Edita una consulta de alerta'),
		            		handler:function(){
			            			if(getSelectedRecord() != null){
			                			editar(getSelectedRecord());
			                		}
			                		else{
			                			Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe Seleccione un registro para realizar esta operaci&oacute;n'));
			                		}
		                	}
		            	},
		                  { 
		                  text:getLabelFromMap('gridBtnDelConfAlId',helpMap,'Eliminar'),
		                  tooltip:getToolTipFromMap('gridBtnDelConfAlId',helpMap, 'Elimina Alertas de Usuarios'),
		                    handler:function(){		
									if ((getSelectedKey(grilla, "cdIdUnico") != "")&&(getSelectedKey(grilla, "codigoConfAlerta") != "")) {
			                        		borrar(getSelectedRecord(grilla));
			                        } else {
			                        		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
			                                }
		                   			}
			              }
		            	/*,
		            	{
		            		id:'gridBtnBackConfAlId',
		        			text:getLabelFromMap('gridBtnBackConfAlId',helpMap,'Regresar'),
							tooltip:getToolTipFromMap('gridBtnBackConfAlId',helpMap)
		            	}*/
	            	],
	    	width:500,
	    	frame:true,
			height:578,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			bbar: new Ext.PagingToolbar({
					pageSize: itemsPerPage,
					store: store,
					displayInfo: true,
	                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
					emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
			    })
			});
	
	    grilla.render()

	}
	
/*	
	function reloadGrid(){
	    var mStore = grilla.store;
	    var o = {start: 0};
	    mStore.baseParams = mStore.baseParams || {};
	    mStore.baseParams['dsUsuario'] = el_form.findById('dsUsuario').getValue();
	    mStore.baseParams['dsNombre'] = el_form.findById('dsCliente').getValue();
	    mStore.baseParams['dsProceso'] = el_form.findById('dsProceso').getValue();
	    mStore.baseParams['dsTipRam'] = el_form.findById('dsRamo').getValue();
	    mStore.baseParams['dsUniEco'] = el_form.findById('dsAseguradora').getValue();
	    mStore.baseParams['dsRol'] = el_form.findById('dsRol').getValue();

	    mStore.reload(
	              {
	                  params:{start:0,limit:itemsPerPage},
	                  callback : function(r,options,success) {
	                      if (!success) {
	                         //Ext.MessageBox.alert(getLabelFromMap('400010', helpMap, 'Error'), getLabelFromMap('400013', helpMap, 'Complete la informacion requerida'));
	                         Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400033', helpMap,'No se encontraron registros'));
	                         mStore.removeAll();
	                      }
	                  }
	
	              }
	            );
	}*/

	/********* Fin del grid **********************************/
	
	el_form.render();
	createGrid();

	function getSelectedCodigo(){
              var m = grilla.getSelections();
              var jsonData = "";
              for (var i = 0, len = m.length;i < len; i++) {
                var ss = m[i].get("cdIdUnico");
                if (i == 0) {
                jsonData = jsonData + ss;
                } else {
                  jsonData = jsonData + "," + ss;
               }
              }
              return jsonData;
    }
    
	function getSelectedRecord(){
            var m = grilla.getSelectionModel().getSelections();
            if (m.length == 1 ) {
               return m[0];
            }
       }
       
//*************

function borrar(record) {
        if(record.get('cdIdUnico') != "" && record.get('codigoConfAlerta') != "")
        {
            Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
            {
                if (btn == "yes")
                {
                    var _params = {
                              cdIdUnico: record.get('cdIdUnico'),
                              codigoConfAlerta: record.get('codigoConfAlerta')
                    };
                    execConnection(_ACTION_BORRAR_ALERTA, _params, cbkConnection);
               }
            })
        }else{
                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
        }
 };

function cbkConnection (_success, _message) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message)
	}else {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), "Registro eliminado", function(){reloadGrid()})
	}
}   

});   //onReady()


function reloadGrid(){
	var _params = {
       		dsUsuario: Ext.getCmp('el_formConsAlertId').form.findField('dsUsuario').getValue(),
            dsNombre: Ext.getCmp('el_formConsAlertId').form.findField('dsCliente').getValue(),
       		dsProceso: Ext.getCmp('el_formConsAlertId').form.findField('dsProceso').getValue(),
            dsTipRam: Ext.getCmp('el_formConsAlertId').form.findField('dsRamo').getValue(),
            dsUniEco: Ext.getCmp('el_formConsAlertId').form.findField('dsAseguradora').getValue(), 
            dsRol: Ext.getCmp('el_formConsAlertId').form.findField('dsRol').getValue()            
 	};
	reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
}

function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}
