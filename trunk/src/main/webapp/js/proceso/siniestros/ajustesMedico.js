Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
var datosgrid;
var storeIncisos;

Ext.define('modelClau',
{
    extend:'Ext.data.Model',
    fields:['consecutivo','ajusteImporte','observaciones']
});

storeIncisos=new Ext.data.Store(
{
    autoDestroy: true,
    model: 'modelClau'
});

function _amRecardar()
{
	var json =
	{
		'params.cdunieco' : _amParams.cdunieco,
		'params.cdramo'   : _amParams.cdramo,
		'params.estado'   : _amParams.estado,
		'params.nmpoliza' : _amParams.nmpoliza,
		'params.nmsuplem' : _amParams.nmsuplem,
		'params.nmsituac' : _amParams.nmsituac,
		'params.aaapertu' : _amParams.aaapertu,
		'params.status'   : _amParams.status,
		'params.nmsinies' : _amParams.nmsinies,
		'params.nfactura' : _amParams.nfactura,
		'params.cdgarant' : _amParams.cdgarant,
		'params.cdconval' : _amParams.cdconval,
		'params.cdconcep' : _amParams.cdconcep,
		'params.idconcep' : _amParams.idconcep,
		'params.nmordina' : _amParams.nmordina
	};
	debug('datos que se envian al servidor: ',json);
	gridIncisos.setLoading(true);
	Ext.Ajax.request(
	{
		url : _amUrlCargar
		,params : json
		,success : function(response)
		{
			gridIncisos.setLoading(false);
			json = Ext.decode(response.responseText);
			debug('respuesta del servidor: ',json);
			if(json.success == true)
			{
				var total = 0*1;
				for(var i = 0;i<json.loadList.length;i++)
				{
					total = total*1 + json.loadList[i]["PTIMPORT"]*1;
				}
				Ext.getCmp('idTotalAjustado').setValue(total);
				storeIncisos.removeAll();
				storeIncisos.add(json.loadList);
			}
			else
			{
				mensajeError('No se pudieron obtener los ajustes m&eacute;dicos');
			}
		}
	    ,failure : function()
	    {
	    	gridIncisos.setLoading(false);
	    	errorComunicacion();
	    }
	});
}

function _amAgregar(ptimport,comments)
{
	var json =
	{
		'params.cdunieco' : _amParams.cdunieco,
		'params.cdramo'   : _amParams.cdramo,
		'params.estado'   : _amParams.estado,
		'params.nmpoliza' : _amParams.nmpoliza,
		'params.nmsuplem' : _amParams.nmsuplem,
		'params.nmsituac' : _amParams.nmsituac,
		'params.aaapertu' : _amParams.aaapertu,
		'params.status'   : _amParams.status,
		'params.nmsinies' : _amParams.nmsinies,
		'params.nfactura' : _amParams.nfactura,
		'params.cdgarant' : _amParams.cdgarant,
		'params.cdconval' : _amParams.cdconval,
		'params.cdconcep' : _amParams.cdconcep,
		'params.idconcep' : _amParams.idconcep,
		'params.nmordina' : _amParams.nmordina,
		'params.ptimport' : ptimport,
		'params.comments' : comments
	};
	debug('datos que se envian al servidor: ',json);
	ventanaGridAjusteMedico.setLoading(true);
	Ext.Ajax.request(
	{
		url     : _amUrlAgregar
		,params : json
	    ,success : function(response)
	    {
	    	ventanaGridAjusteMedico.setLoading(false);
	    	json = Ext.decode(response.responseText);
	    	if(json.success == true)
	    	{
	    		//recargar el grid
	    		_amRecardar();
	    	}
	    	else
	    	{
	    		mensajeError(json.mensaje);
	    	}
	    }
	    ,failure : function()
	    {
	    	ventanaGridAjusteMedico.setLoading(false);
	    	errorComunicacion();
	    }
	});
}

Ext.onReady(function() {

    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });

    var panelAjusteMedico= Ext.create('Ext.form.Panel',{
        border  : 0
        ,bodyStyle:'padding:5px;'
        ,items :
        [   
	         {
		    	id:'idConsecutivo'				,xtype      : 'textfield'	    	,fieldLabel : 'Consecutivo'
	    		,labelWidth: 170	    		,allowBlank:false		    		,width: 300
		    	,name : 'idConsecutivo'
	         },
	         {
		    	id:'idAjusteImporte'	        ,xtype      : 'textfield'	    	,fieldLabel : 'Ajuste de importe'
	    		,labelWidth: 170	    		,allowBlank:false		    		,width: 500
		    	,name : 'idAjusteImporte'
	         },
	         {
		    	id:'idObservaciones'	        ,xtype      : 'textfield'	    	,fieldLabel : 'Observaciones'
	    		,labelWidth: 170	    		,allowBlank:false		    		,width: 500
		    	,name : 'idObservaciones'
	         }
        ]
    });
    /*PANTALLA EMERGENTE PARA LA INSERCION Y MODIFICACION DE LOS DATOS DEL GRID*/
    ventanaGridAjusteMedico= Ext.create('Ext.window.Window', {
         renderTo: document.body,
           title: 'Ajuste M&eacute;dico',
           height: 180,
           closeAction: 'hide',
           items:[panelAjusteMedico],
           
           buttons:[{
                  text: 'Aceptar',
                  icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                  handler: function() {
                        if (panelAjusteMedico.form.isValid()) {
                        	
                        	var datos=panelAjusteMedico.form.getValues();
                        	console.log(datos);
                        	
                        	var rec = new modelClau({
                        		consecutivo: datos.idConsecutivo,
                        		ajusteImporte: datos.idAjusteImporte,
                        		observaciones: datos.idObservaciones
		        	 		});
                        	storeIncisos.add(rec);
                        	panelAjusteMedico.getForm().reset();
                        } else {
                            Ext.Msg.show({
                                   title: 'Aviso',
                                   msg: 'Complete la informaci&oacute;n requerida',
                                   buttons: Ext.Msg.OK,
                                   icon: Ext.Msg.WARNING
                               });
                        }
                    }
              },
            {
                  text: 'Cancelar',
                  icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
                  handler: function() {
                       //Ext.getCmp('btnGuardaRegistro').disable();
                      panelAjusteMedico.getForm().reset();
                      ventanaGridAjusteMedico.close();
                  }
            }
           ]
           });
    
    /*////////////////////////////////////////////////////////////////
    ////////////////   DECLARACION DE EDITOR DE INCISOS  ////////////
    ///////////////////////////////////////////////////////////////*/
    Ext.define('EditorIncisos', {
 		extend: 'Ext.grid.Panel',
	 	requires: [
		 	'Ext.selection.CellModel',
		 	'Ext.grid.*',
		 	'Ext.data.*',
		 	'Ext.util.*',
		 	'Ext.form.*'
	 	],
 		xtype: 'cell-editing',
		id:'editorIncisos',
 		//title: 'Facturas en Tr&aacute;mite',
 		frame: false,

	 	initComponent: function(){
	 		this.cellEditing = new Ext.grid.plugin.CellEditing({
	 		clicksToEdit: 1
	 		});

	 			Ext.apply(this, {
	 			width: 750,
	 			height: 200,
	 			plugins: [this.cellEditing],
	 			store: storeIncisos,
	 			columns: 
	 			[
	 			 	//'consecutivo','ajusteImporte','Observaciones'
				 	{	
				 		header: 'Consecutivo',			dataIndex: 'consecutivo'
				 	},
				 	{
				 		header: 'Ajuste de importe',	dataIndex: 'ajusteImporte',			width: 200		,renderer: Ext.util.Format.usMoney
				 		
				 	},
				 	{
					 	header: 'Observaciones',			dataIndex: 'observaciones',	 	flex:2	
				 	},
				 	{
					 	xtype: 'actioncolumn',
					 	width: 30,
					 	sortable: false,
					 	menuDisabled: true,
					 	items: [{
					 		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
					 		tooltip: 'Quitar inciso',
					 		scope: this,
					 		handler: this.onRemoveClick
				 		}]
				 	}
		 		],
		 		selModel: {
			 		selType: 'cellmodel'
			 	},
		 		tbar: [{
				 	icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
				 	text: 'Agregar ajuste',
				 	scope: this,
				 	handler: this.onAddClick
		 		}]
		 	});
 			this.callParent();
	 	},
	 	getColumnIndexes: function () {
		 	var me, columnIndexes;
		 	me = this;
		 	columnIndexes = [];
		 	Ext.Array.each(me.columns, function (column)
		 	{
		 		if (column.getEditor&&Ext.isDefined(column.getEditor())&&column.getEditor().allowBlank==false) {
		 			columnIndexes.push(column.dataIndex);
			 	} else {
			 		columnIndexes.push(undefined);
			 	}
		 	});
		 	return columnIndexes;
	 	},
	 	validateRow: function (columnIndexes,record, y)
	 	//hace que una celda de columna con allowblank=false tenga el estilo rojito
	 	{
		 	var view = this.getView();
		 	Ext.each(columnIndexes, function (columnIndex, x)
		 	{
		 		if(columnIndex)
			 	{
			 		var cell=view.getCellByPosition({row: y, column: x});
			 		cellValue=record.get(columnIndex);
				 	if(cell.addCls&&((!cellValue)||(cellValue.lenght==0))){
				 		cell.addCls("custom-x-form-invalid-field");
				 	}
			 	}
		 	});
		 	return false;
	 	},
	 	onAddClick: function(){
	 		ventanaGridAjusteMedico.show();
	 	},
	 	onRemoveClick: function(grid, rowIndex){
	 		var record=this.getStore().getAt(rowIndex);
	 		console.log(record);        	
	 		this.getStore().removeAt(rowIndex);
	 	}
 	});
    gridIncisos=new EditorIncisos();
    mesConStoreUniAdmin=[];
    
    
    
    
    
    
    
    Ext.create('Ext.form.Panel',
    	    {
    	        border    : 0
    	        ,title: 'Ajustes M&eacute;dico'
    	        ,renderTo : 'div_clau'
	        	//,bodyPadding: 10
	        	,bodyStyle:'padding:5px;'
	            ,width: 800
    	        ,items    :
    	        [
    	            {
            			id:'idConcepto'
		                ,xtype      : 'textfield'
		            	,fieldLabel : 'Concepto'
		            	,readOnly   : true
		            	,labelWidth : 160
		            	,width: 500
		            	,name       : 'idConcepto'
		            },
		            {
		                id: 'idCPT'
		                ,name: 'idCPT'
		                ,xtype: 'textfield'
		                ,fieldLabel: 'CPT'
		                ,labelWidth: 160
		                ,width: 500
		            },
		            {
		                id: 'idImporteFact'
		                ,name: 'idCPT'
		                ,xtype: 'textfield'
		                ,fieldLabel: 'Importe facturado'
		                ,labelWidth: 160
		                ,width: 500
		            },
                    gridIncisos
                    ,
                    {
        		    	id:'idTotalAjustado'
        		        ,xtype      : 'textfield'
        		    	,fieldLabel : 'Total ajustado'
        	    		,labelWidth: 170
        	    		,allowBlank:false
        	    		,width: 500
        		    	,name       : 'idTotalAjustado'
        	         }
    	        ],
    	        buttonAlign:'center',
    	        buttons: [{
    	            id:'botonGuadar',
    	            icon:_CONTEXT+'/resources/fam3icons/icons/calculator.png',
    	            //text: hayTramiteCargado?'Precaptura':'Cotizar',
    	            text: 'Guardar',
            		handler: function() {
            			
            			var form = this.up('form').getForm();
            			if (form.isValid())
    	                {
            				Ext.Msg.show({
    	                    	title:'Exito',
    	                    	msg: 'Se contemplaron todo',
    	                    	buttons: Ext.Msg.OK,
    	                    	icon: Ext.Msg.WARNING
    	                	});
    	                }
            			else
        				{
            				var incisosRecords = storeIncisos.getRange();
            				console.log(incisosRecords.length);
            				
            				var incisosJson = [];
            				storeIncisos.each(function(record,index){
	                        	if(record.get('nombre')
	                        			&&record.get('nombre').length>0)
                        		{
	                        		nombres++;
                        		}
	                            incisosJson.push({
	                            	noFactura: record.get('noFactura'),
	                            	fechaFactura: record.get('fechaFactura'),
	                            	tipoServicio: record.get('tipoServicio'),
	                            	proveedor: record.get('proveedor'),
	                            	importe: record.get('importe')
	                            });
                            });
            				
            				console.log('---- VALOR DE IncisosJson ---- ');
            				console.log(incisosJson);
            				
            				var submitValues=form.getValues();
                        	submitValues['incisos']=incisosJson;
                        	console.log('---- VALOR DE submitValues ---- ');
            				console.log(submitValues);
            				
            				Ext.Msg.show({
    	                    	title:'Datos incompletos',
    	                    	msg: 'Favor de introducir todos los campos requeridos',
    	                    	buttons: Ext.Msg.OK,
    	                    	icon: Ext.Msg.WARNING
    	                	});
        				}
    	            }
    	        },
    	        {
    	            text:'Cancelar',
    	            icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
    	            id:'botonCancelar',
    	            handler:function()
    	            {}
    	        }
    	    ]
    	    });
    
    _amAgregar('150.5','sin observaciones');
    
});