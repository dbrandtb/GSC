Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
var datosgrid;
var storeFactCtrl;
var storeListAsegPagDirecto;


Ext.define('modelFactCtrl',
{
    extend:'Ext.data.Model',
    fields:['noFactura','fechaFactura','tipoServicio','tipoServicioName','proveedor','proveedorName','importe']
});

storeFactCtrl=new Ext.data.Store(
{
    autoDestroy: true,
    model: 'modelFactCtrl'
});


Ext.define('modelListadoProvMedico',{
    extend: 'Ext.data.Model',
    fields: [
             	{type:'string',    name:'cdpresta'},		{type:'string',    name:'nombre'},		{type:'string',    name:'cdespeci'},		{type:'string',    name:'descesp'}
			]
});

Ext.define('modelListadoPoliza',{
    extend: 'Ext.data.Model',
    fields: [	{type:'string',    name:'cdramo'},				{type:'string',    name:'cdunieco'},				{type:'string',    name:'estado'},
				{type:'string',    name:'nmpoliza'},			{type:'string',    name:'nmsituac'},				{type:'string',    name:'mtoBase'},
				{type:'string',    name:'feinicio'},			{type:'string',    name:'fefinal'},					{type:'string',    name:'dssucursal'},
				{type:'string',    name:'dsramo'},				{type:'string',    name:'estatus'},					{type:'string',    name:'dsestatus'},
				{type:'string',    name:'nmsolici'},			{type:'string',    name:'nmsuplem'},				{type:'string',    name:'cdtipsit'}			]
});


Ext.define('modelListAsegPagDirecto',{
    extend: 'Ext.data.Model',
    fields: [
                {type:'string',    name:'modUnieco'},		{type:'string',    name:'modEstado'},		    {type:'string',    name:'modRamo'},
                {type:'string',    name:'modNmsituac'},		{type:'string',    name:'modPolizaAfectada'},	{type:'string',    name:'modCdpersondesc'},
                {type:'string',    name:'modNmsolici'},		{type:'string',    name:'modNmsuplem'},		    {type:'string',    name:'modCdtipsit'},
                {type:'string',    name:'modNmautserv'},	{type:'string',    name:'modFechaOcurrencia'},	{type:'string',    name:'modCdperson'}
                
            ]
});

storeListAsegPagDirecto=new Ext.data.Store(
{
    autoDestroy: true,						model: 'modelListAsegPagDirecto'
});

Ext.onReady(function() {

	var storeTipoAtencion = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: mesConUrlLoadCatalo,
			extraParams : {catalogo:_CATALOGO_TipoAtencion},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoAtencion.load();
    
	
	storeAsegurados = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTADO_ASEGURADO,
            reader: {
                type: 'json',
                root: 'listaAsegurado'
            }
        }
    });
	
	var storeTipoPago = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: mesConUrlLoadCatalo,
			extraParams : {catalogo:_CATALOGO_TipoPago},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
    storeTipoPago.load();
    
    var storeProveedor = Ext.create('Ext.data.Store', {
        model:'modelListadoProvMedico',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_PROVEEDOR_MEDICO,
            extraParams:{
            	'params.tipoprov' : '16'
            },
            reader: {
                type: 'json',
                root: 'listaProvMedico'
            }
        }
    });

    storeListadoPoliza = new Ext.data.Store(
    {
    	pageSize : 5
        ,model      : 'modelListadoPoliza'
        ,autoLoad  : false
        ,proxy     :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    
    
    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });

    oficinaReceptora= Ext.create('Ext.form.ComboBox',
  	{
    	id:'cmbOficReceptora',	   name:'cmbOficReceptora',     fieldLabel: 'Oficina receptora',		queryMode:'local',		   
    	displayField: 'value',	   valueField: 'key',			allowBlank:false,				        editable:false,
    	labelWidth : 250,		   width		 : 500,			emptyText:'Seleccione...'
   		,store : Ext.create('Ext.data.Store', {
   			model:'Generic',
   			autoLoad:true,
   			proxy:
   			{
   				type: 'ajax',
   				url:mesConUrlLoadCatalo,
   				extraParams : {catalogo:_CATALOGO_OFICINA_RECEP},
   				reader:
   				{
   					type: 'json',
   					root: 'lista'
   				}
   			}
   		})
   	});
    
    oficinaEmisora= Ext.create('Ext.form.ComboBox',
	{
    	id:'cmbOficEmisora',		name:'cmbOficEmisora',		fieldLabel: 'Oficina emisora',			queryMode:'local',
	    displayField: 'value',	    valueField: 'key',			width		 : 500,					    allowBlank:false,					    editable:false,
	    labelWidth : 250,		    emptyText:'Seleccione...'
	    ,store : Ext.create('Ext.data.Store', {
			model:'Generic',
			autoLoad:true,
			proxy:
			{
				type: 'ajax',
				url:mesConUrlLoadCatalo,
				extraParams : {catalogo:_CATALOGO_OFICINA_RECEP},
				//extraParams : {catalogo:'<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@MC_TIPOS_TRAMITE"/>'},
				reader:
				{
					type: 'json',
					root: 'lista'
				}
			}
		})
	    
	});
     
    var comboTipoAte= Ext.create('Ext.form.ComboBox',
    {
        id:'cmbTipoAtencion',			   name:'cmbTipoAtencion',		        fieldLabel: 'Tipo atenci&oacute;n',				queryMode:'local',
        displayField: 'value',	   valueField: 'key',					allowBlank:false,						editable:false,
        labelWidth : 250,		   emptyText:'Seleccione...',			width		 : 500,						store: storeTipoAtencion
    });

    var tipoServicioInterno = Ext.create('Ext.form.ComboBox',
    {
        id:'tipoServicioInterno',			   name:'tipoServicioInterno',		        fieldLabel: 'Tipo de servicio',				queryMode:'local',
        displayField: 'value',	   valueField: 'key',					allowBlank:false,						editable:false,
        labelWidth : 170,		   emptyText:'Seleccione...',			width		 : 500,						store: storeTipoAtencion
    });

    tipoPago= Ext.create('Ext.form.ComboBox',
    		{
    	id:'cmbTipoPago',			   name:'cmbTipoPago',		        fieldLabel: 'Tipo pago',				queryMode:'local',
    	displayField: 'value',	   valueField: 'key',					allowBlank:false,						editable:false,
    	labelWidth : 250,		   width		 : 500,					emptyText:'Seleccione...',			store: storeTipoPago,
    	listeners : {
    		
    		change:function(e){
    		//'select' : function(combo, record) {
    			//closedStatusSelectedID = this.getValue();
    			if(e.getValue() =='1'){
    				//PAGO DIRECTO
    				Ext.getCmp('editorIncisos').hide();
    				Ext.getCmp('cmbBeneficiario').hide();
    				Ext.getCmp('cmbProveedor').show();
    				Ext.getCmp('txtNoFactura').show();
    				Ext.getCmp('txtImporte').show();
    				Ext.getCmp('dtFechaFactura').show();
    				Ext.getCmp('EditorAsegPagDirecto').show();
    				Ext.getCmp('cmbAseguradoAfectado').hide();
    				Ext.getCmp('dtFechaOcurrencia').hide();
    				
    			}else{
    				//PAGO POR REEMBOLSO
    				Ext.getCmp('cmbProveedor').hide();
    				Ext.getCmp('txtNoFactura').hide();
    				Ext.getCmp('txtImporte').hide();
    				Ext.getCmp('dtFechaFactura').hide();
    				Ext.getCmp('editorIncisos').show();
    				Ext.getCmp('cmbBeneficiario').show();
    				Ext.getCmp('EditorAsegPagDirecto').hide();
    				Ext.getCmp('cmbAseguradoAfectado').show();
    				Ext.getCmp('dtFechaOcurrencia').show();
    			}
    		}
    	}
	});

    
    aseguradoAfectado = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'Asegurado afectado',      displayField : 'value',         name:'cmbAseguradoAfectado',
    	id:'cmbAseguradoAfectado',              labelWidth: 250,            width:500,                      valueField   : 'key',
    	forceSelection : false,                 matchFieldWidth: false,     queryMode :'remote',            queryParam: 'params.cdperson',
    	minChars  : 2,                          store : storeAsegurados,    triggerAction: 'all',			hideTrigger:true,
    	listeners : {
            'select' : function(combo, record) {
                    obtieneCDPerson = this.getValue();
                    var params = {
                            'params.cdperson' : obtieneCDPerson
                    };
                    
                    cargaStorePaginadoLocal(storeListadoPoliza, _URL_CONSULTA_LISTADO_POLIZA, 'listaPoliza', params, function(options, success, response){
                        if(success){
                            var jsonResponse = Ext.decode(response.responseText);
                            if(jsonResponse.listaPoliza == null) {
                                Ext.Msg.show({
                                    title: 'Aviso',
                                    msg: 'No se encontraron datos.',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.WARNING
                                });
                                //limpiarRegistros();
            					Ext.getCmp('cmbAseguradoAfectado').setValue('');
                                modPolizasAltaTramite.hide();
                                return;
                            }
                            
                        }else{
                            Ext.Msg.show({
                                title: 'Aviso',
                                msg: 'Error al obtener los datos.',
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.ERROR
                            });
                        }
                    });
                    
                    modPolizasAltaTramite.showAt(200,100);
                }
            }
    });
    
    
    cmbBeneficiario= Ext.create('Ext.form.ComboBox',
	{
        id:'cmbBeneficiario',        name:'cmbBeneficiario',        	fieldLabel: 'Beneficiario',        queryMode:'local',        
        displayField: 'value',    valueField: 'key',        			/*allowBlank:false,*/        				editable:false,        
        labelWidth : 250,         width		 : 500,					emptyText:'Seleccione...'//,       		store: storePenalizacion
        
    });
    
    
    cmbProveedor = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'Proveedor',			/*allowBlank:false,*/				displayField : 'nombre',		name:'cmbProveedor',
    		id:'cmbProveedor',				labelWidth: 250,					valueField   : 'cdpresta',			
    		forceSelection : false,	width:500,		emptyText:'Seleccione...',
    	matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdpresta',	store : storeProveedor,			editable:false,
    	triggerAction: 'all'
    });
    
    cmbProveedorInterno = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'Proveedor',			allowBlank: false,				displayField : 'nombre',		name:'cmbProveedorInterno',
    		id:'cmbProveedorInterno',				labelWidth: 170,					valueField   : 'cdpresta',			
    		forceSelection : false,	width:500,		emptyText:'Seleccione...',
    	matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdpresta',	store : storeProveedor,			editable:false,
    	triggerAction: 'all'
    });
    
    var panelModificacionInsercion= Ext.create('Ext.form.Panel',{
        border  : 0
        ,bodyStyle:'padding:5px;'
        ,items :
        [   
             {
		    	id:'noFactInterno'
		        ,xtype      : 'textfield'
		    	,fieldLabel : 'No. Factura'
	    		,labelWidth: 170
	    		,allowBlank:false
		    	,name       : 'noFactInterno'
    		},            
    		{
    	        id: 'fechaFactInterno',
    	        name: 'fechaFactInterno',
    	        fieldLabel: 'Fecha Factura',
    	        xtype: 'datefield',
    	        format: 'd/m/Y',
    	        editable: true,
    	        allowBlank:false,
    	        labelWidth : 170,
    	        value:new Date()
    	    }
            ,
            tipoServicioInterno
            ,
            cmbProveedorInterno
            ,
            	//proveedor2
            {
		    	id:'importeInterno'
		        ,xtype      : 'numberfield'
		    	,fieldLabel : 'Importe'
	    		,labelWidth: 170
                ,width:500
                ,allowBlank:false
		    	,name       : 'importeInterno'
		    	,allowDecimals :true
		    	,decimalSeparator :'.'
		    	,minValue: 0
    		}
        ]
    });
    /*PANTALLA EMERGENTE PARA LA INSERCION Y MODIFICACION DE LOS DATOS DEL GRID*/
    ventanaGrid= Ext.create('Ext.window.Window', {
         renderTo: document.body,
           title: 'NUEVAS FACTURAS',
           height: 230,
           closeAction: 'hide',
           items:[panelModificacionInsercion],
           
           buttons:[{
                  text: 'Aceptar',
                  icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                  handler: function() {
                        if (panelModificacionInsercion.form.isValid()) {
                        	
                        	var datos=panelModificacionInsercion.form.getValues();
                        	
                        	var rec = new modelFactCtrl({
        	 				  	noFactura: datos.noFactInterno,
        					 	fechaFactura: datos.fechaFactInterno,
        					 	tipoServicio: datos.tipoServicioInterno,
        					 	proveedor: datos.cmbProveedorInterno,
        					 	importe: datos.importeInterno,
        					 	proveedorName: cmbProveedorInterno.rawValue,
        					 	tipoServicioName:tipoServicioInterno.rawValue
		        	 		});
                        	
                        	storeFactCtrl.add(rec);
                        	ventanaGrid.close();
                        	panelModificacionInsercion.getForm().reset();
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
                      panelModificacionInsercion.getForm().reset();
                      ventanaGrid.close();
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
 		title: 'Alta de Tr&aacute;mite',
 		frame: false,

	 	initComponent: function(){
	 		this.cellEditing = new Ext.grid.plugin.CellEditing({
	 		clicksToEdit: 1
	 		});

	 			Ext.apply(this, {
	 			width: 750,
	 			height: 200,
	 			plugins: [this.cellEditing],
	 			store: storeFactCtrl,
	 			columns: 
	 			[
				 	{	
				 		header: 'No. de Factura',			dataIndex: 'noFactura',			flex:2
				 	},
				 	{
				 		header: 'Fecha de Factura',			dataIndex: 'fechaFactura',		flex:2//,			 	renderer: Ext.util.Format.dateRenderer('d M Y')
				 		
				 	},
				 	{
					 	header: 'Tipo de Servicio',			dataIndex: 'tipoServicioName',	 	flex:2	
				 	},
				 	{
					 	header: 'Proveedor',				dataIndex: 'proveedorName',			flex:2
				 	},
				 	{
					 	header: 'Importe', 					dataIndex: 'importe',		 	flex:2,				renderer: Ext.util.Format.usMoney
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
				 	text: 'Agregar Factura',
				 	scope: this,
				 	handler: this.onAddClick
		 		}]
		 	});
 			this.callParent();
	 	},
	 	onAddClick: function(btn, e){
	 		ventanaGrid.animateTarget=btn;
	 		ventanaGrid.show();
		   },
		   
	 	onRemoveClick: function(grid, rowIndex){
	 		var record=this.getStore().getAt(rowIndex);
	 		this.getStore().removeAt(rowIndex);
	 	}
 	});
    gridIncisos=new EditorIncisos();
    mesConStoreUniAdmin=[];
    
    
    
    var panelListadoAsegurado= Ext.create('Ext.form.Panel',{
        border  : 0
        ,startCollapsed : true
        ,bodyStyle:'padding:5px;'
        ,items :
        [   			
            {
                xtype      : 'datefield',
                name       : 'dtfechaOcurrencias',
                fieldLabel : 'Fecha ocurrencia',
                allowBlank: false,
                maxValue   :  new Date(),
                labelWidth : 170,
                format		: 'd/m/Y'
            },{
            	xtype: 'combo',
            	name:'cmbAseguradoAfect',
            	fieldLabel : 'Asegurado',
        	    displayField : 'value',
        	    valueField   : 'key',
        	    allowBlank: false,
        	    width:500,
        	    labelWidth: 170,
        	    forceSelection : false,
        	    matchFieldWidth: false,
        	    queryMode :'remote',
        	    queryParam: 'params.cdperson',
        	    store : storeAsegurados,
        	    triggerAction: 'all',
        	    hideTrigger:true,
        	    listeners : {
        	    	
        	        'select' : function(combo, record) {
        	        	
        	        	var params = {'params.cdperson' : this.getValue()};
                        
                        cargaStorePaginadoLocal(storeListadoPoliza, _URL_CONSULTA_LISTADO_POLIZA, 'listaPoliza', params, function(options, success, response){
                            if(success){
                                var jsonResponse = Ext.decode(response.responseText);
                                if(jsonResponse.listaPoliza == null) {
                                    Ext.Msg.show({
                                        title: 'Aviso',
                                        msg: 'No existen p&oacute;lizas para el asegurado elegido.',
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.WARNING
                                    });
                                    
                                    combo.clearValue();
                                    modPolizasAltaTramite.hide();
                                    return;
                                }
                                
                            }else{
                                Ext.Msg.show({
                                    title: 'Aviso',
                                    msg: 'Error al obtener los datos.',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.ERROR
                                });
                            }
                        });
                        
                        modPolizasAltaTramite.show();
                    }
        	    }
            }
        ]
    });

    ventanaAgregarAsegurado= Ext.create('Ext.window.Window', {
        //renderTo: document.body,
        title: 'Asegurados',
        //height: 200,
        closeAction: 'hide', 
        modal: true, 
        items:[panelListadoAsegurado],
        buttonAlign : 'center',
        buttons:[
            {
                text: 'Aceptar',
                icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                handler: function() {
                    if (panelListadoAsegurado.form.isValid()) {
                    	
                        var datos=panelListadoAsegurado.form.getValues();
                        
                        var rec = new modelListAsegPagDirecto({
                        	modUnieco: Ext.getCmp('idUnieco').getValue(),
                        	modEstado: Ext.getCmp('idEstado').getValue(),
                        	modRamo: Ext.getCmp('idcdRamo').getValue(),
                        	modNmsituac: Ext.getCmp('idNmSituac').getValue(),
                        	modPolizaAfectada: Ext.getCmp('polizaAfectada').getValue(),
                        	modNmsolici: Ext.getCmp('idNmsolici').getValue(),
                        	modNmsuplem: Ext.getCmp('idNmsuplem').getValue(),
                        	modCdtipsit: Ext.getCmp('idCdtipsit').getValue(),
                        	modNmautserv: "",
                        	modFechaOcurrencia: datos.dtfechaOcurrencias,
                        	modCdperson: datos.cmbAseguradoAfect,
                        	modCdpersondesc: panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].rawValue
                        });
                        storeListAsegPagDirecto.add(rec);
                        limpiarRegistros();
                        panelListadoAsegurado.getForm().reset();
                        ventanaAgregarAsegurado.close();
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
                handler: function(){
                    panelListadoAsegurado.getForm().reset();
                    ventanaAgregarAsegurado.close();
                }
            }
        ]
    });
    
    
    Ext.define('EditorAsegPagDirecto', {
        extend: 'Ext.grid.Panel',
        requires: [
            'Ext.selection.CellModel',
            'Ext.grid.*',
            'Ext.data.*',
            'Ext.util.*',
            'Ext.form.*'
        ],
        xtype: 'cell-editing',
        id:'EditorAsegPagDirecto',
        title: 'Asegurados',
        frame: false,

        initComponent: function(){
            this.cellEditing = new Ext.grid.plugin.CellEditing({
            clicksToEdit: 1
            });

                Ext.apply(this, {
                width: 750,
                height: 200,
                plugins: [this.cellEditing],
                store: storeListAsegPagDirecto,
                columns: 
                [
                    {	
                        header: 'Fecha ocurrencia',			dataIndex: 'modFechaOcurrencia',			width:150
                    },
                    {
                        header: 'Asegurado afectado',		dataIndex: 'modCdpersondesc',				width:350
                    },
                    {
                        xtype: 'actioncolumn',				width: 30,					 	sortable: false,	 	menuDisabled: true,
                        items: [{
                            icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
                            tooltip: 'Quitar asegurado',
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
                    text: 'Agregar Asegurado',
                    scope: this,
                    handler: this.onAddClick
                }]
            });
            this.callParent();
        },
        onAddClick: function(btn, e){
        	ventanaAgregarAsegurado.animateTarget=btn;
            ventanaAgregarAsegurado.show();
           },
           
        onRemoveClick: function(grid, rowIndex){
            var record=this.getStore().getAt(rowIndex);
            this.getStore().removeAt(rowIndex);
        }
    });

    gridAsegPagDirecto=new EditorAsegPagDirecto();
    
    
    gridPolizasAltaTramite= Ext.create('Ext.grid.Panel',
	{
	    id            : 'polizaGridAltaTramite',
	    store         : storeListadoPoliza,
	    collapsible   : true,
	    titleCollapse : true,
	    selType	      : 'checkboxmodel',
	    width         : 640,
	    height		  : 200,
	    columns       :
	    [
	        {
	             header     : 'N&uacute;mero de P&oacute;liza',		dataIndex : 'nmpoliza',		width	 	: 150
	        },
	        {
	             header     : 'Estatus',							dataIndex : 'dsestatus',	width	 	: 100
	        },
	        {
	             header     : 'Fecha inicio',						dataIndex : 'feinicio',		width	    : 100
	        },
	        {
	             header     : 'Fecha final',						dataIndex : 'fefinal',		width	    : 100
	        },
	        {
	             header     : 'Producto',							dataIndex : 'dsramo',		width       : 150
	        },
	        {
	             header     : 'Sucursal',							dataIndex : 'dssucursal',	width       : 150
	        },
	        {
	             header     : 'Estado',								dataIndex : 'estado',		width	    : 100
	        },
	        {
	             header     : 'N&uacute;mero de Situaci&oacute;n',	dataIndex : 'nmsituac',		width	    : 150
	         }
		],
	    bbar :
	    {
	        displayInfo : true,
	        store       : storeListadoPoliza,
	        xtype       : 'pagingtoolbar'
	    },
	    listeners: {
                itemclick: function(dv, record, item, index, e){
                	Ext.getCmp('idUnieco').setValue(record.get('cdunieco'));
					Ext.getCmp('idEstado').setValue(record.get('estado'));
					Ext.getCmp('idcdRamo').setValue(record.get('cdramo'));
					Ext.getCmp('idNmSituac').setValue(record.get('nmsituac'));
					Ext.getCmp('polizaAfectada').setValue(record.get('nmpoliza'));
					Ext.getCmp('idNmsolici').setValue(record.get('nmsolici'));
					Ext.getCmp('idNmsuplem').setValue(record.get('nmsuplem'));
					Ext.getCmp('idCdtipsit').setValue(record.get('cdtipsit'));
					modPolizasAltaTramite.hide();
                }
            }

	});
    gridPolizasAltaTramite.store.sort([
        {
        	property    : 'nmpoliza',			direction   : 'DESC'
        }
    ]);
    
    
    modPolizasAltaTramite = Ext.create('Ext.window.Window',
	{
	    title        : 'Listado de P&oacute;liza'
	    ,modal       : true
	    ,buttonAlign : 'center'
	    ,closable : false
	    ,width		 : 650
	    ,minHeight 	 : 100 
	    ,maxheight      : 400
	    ,items       :
	        [
	            gridPolizasAltaTramite
	        ]
	});
    
    var panelInicialPral= Ext.create('Ext.form.Panel',
    	    {
    	        border    : 0
    	        ,title: 'Alta de Tr&aacute;mite'
    	        ,id : 'panelInicialPral'
    	        ,renderTo : 'div_clau'
	        	,bodyPadding: 10
	            ,width: 800
    	        ,items    :
    	        [
					{
					    xtype       : 'textfield',			fieldLabel : 'Unieco'				,id       : 'idUnieco',				name       : 'cdunieco'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'estado'				,id       : 'idEstado',				name       : 'estado'			
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'Ramo'				,id       : 'idcdRamo',					name       : 'cdramo'
					    ,labelWidth: 170,	hidden:true
					},	 			
					{
					    xtype       : 'textfield',			fieldLabel : 'nmsituac'				,id       : 'idNmSituac',			name       : 'nmsituac'
					    ,labelWidth: 170,	hidden:true
					},
					{
					   xtype       : 'textfield',			fieldLabel : 'P&oacute;liza afectada'				,id       : 'polizaAfectada'
					   ,labelWidth: 170,				name:'nmpoliza',	readOnly   : true,	hidden:true
					},
					
//												
					{
					    xtype       : 'textfield',			fieldLabel : 'nmsolici'				,id       : 'idNmsolici',			name       : 'nmsolici'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'nmsuplem'				,id       : 'idNmsuplem',			name       : 'nmsuplem'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'cdtipsit'				,id       : 'idCdtipsit',			name       : 'cdtipsit'
					    ,labelWidth: 170,	hidden:true
					},
    	            {
            			id:'txtContraRecibo'
		                ,xtype      : 'textfield'
		            	,fieldLabel : 'Contra recibo'
		            	,readOnly   : true
		            	,labelWidth : 250
		            	,width		 : 500
		            	,name       : 'txtContraRecibo'
		            },
		            {
		                id: 'txtEstado',
		                name: 'txtEstado',
		                xtype: 'textfield',
		                fieldLabel: 'Estado',
		                readOnly   : true,
		               labelWidth: 250,
		               width		 : 500,
		               value:'PENDIENTE'
		            },
		            
		            oficinaReceptora
		            ,
            		oficinaEmisora
	            	,
	            	{
		            	id:'dtFechaRecepcion'
		                ,xtype      : 'datefield'
		            	,fieldLabel : 'Fecha recepci&oacute;n'
		            	,labelWidth : 250
		            	,width		 : 500
		            	,name       : 'dtFechaRecepcion'
	            		,value		: new Date()
		            	,format		: 'd/m/Y'
		            	,readOnly   : true
		            }
	            	,
	            	comboTipoAte,
	        		tipoPago,
        			{
                        id: 'dtFechaOcurrencia',
                        name: 'dtFechaOcurrencia',
                        fieldLabel: 'Fecha ocurrencia',
                        xtype: 'datefield',
                        format: 'd/m/Y',
                        editable: true,
                        labelWidth : 250,
                        width		 : 500
                    },
            		aseguradoAfectado
	            	,
	            	cmbBeneficiario
	            	,
	            	cmbProveedor
	        	    ,
	        	    {
		            	id:'txtNoFactura'
		                ,xtype      : 'textfield'
		            	,fieldLabel : 'No. Factura'
		            	,labelWidth : 250
		            	,width		: 500
		            	,name       : 'txtNoFactura'
		            }
		            ,
                	{
		            	id:'txtImporte'
		                ,xtype      : 'numberfield'
		            	,fieldLabel : 'Importe'
		            	,labelWidth : 250
		            	,width		: 500
		            	,name       : 'txtImporte'
	            		,allowDecimals :true
	    		    	,decimalSeparator :'.'
	    		    	,minValue: 0
		            },
		            {
                        id: 'dtFechaFactura',
                        name: 'dtFechaFactura',
                        fieldLabel: 'Fecha factura',
                        xtype: 'datefield',
                        format: 'd/m/Y',
                        editable: true,
                        allowBlank:false,
                        labelWidth : 250,
                        width		 : 500,
                        value:new Date()
                    },
                    gridIncisos
                    ,
                    gridAsegPagDirecto
    	        ],
    	        buttonAlign:'center',
    	        buttons: [{
    	            id:'botonCotizar',
    	            icon:_CONTEXT+'/resources/fam3icons/icons/calculator.png',
    	            //text: hayTramiteCargado?'Precaptura':'Cotizar',
    	            text: 'Generar Tr&aacute;mite',
            		handler: function() {
            			
            			var form = this.up('form').getForm();
            			if (form.isValid())
    	                {
            				//aqui realizamos el llamado al guardado
            				var respuesta=true;
            				var submitValues={};
            				var formulario=panelInicialPral.form.getValues();
            				submitValues['params']=formulario;
            				
            				var datosTablas = [];
            				
            				// si es pago directo
            				if( Ext.getCmp('cmbTipoPago').getValue() == "1")
        					{
            					storeListAsegPagDirecto.each(function(record,index){
            						datosTablas.push({
    	            					cdperson:record.get('modCdperson'),
    	            					cdtipsit:record.get('modCdtipsit'),
    	            					estado:record.get('modEstado'),
    	            					fechaOcurrencia:record.get('modFechaOcurrencia'),
    	            					nmsituac:record.get('modNmsituac'),
    	            					nmsolici:record.get('modNmsolici'),
    	            					nmsuplem:record.get('modNmsuplem'),
    	            					polizaAfectada:record.get('modPolizaAfectada'),
    	            					ramo:record.get('modRamo'),
    	            					unieco:record.get('modUnieco')
    	            				});
                				});
        					}else{
        						storeFactCtrl.each(function(record,index){
    	            				datosTablas.push({
    	            					nfactura:record.get('noFactura'),
    	            					ffactura:record.get('fechaFactura'),
    	            					cdtipser:record.get('tipoServicio'),
    	            					cdpresta:record.get('proveedor'),
    	            					ptimport:record.get('importe')
    	            				});
                				});
    						}
            				
            				submitValues['datosTablas']=datosTablas;
            				panelInicialPral.setLoading(true);
            				
            				Ext.Ajax.request(
    						{
    						    url: _URL_GUARDA_ALTA_TRAMITE,
    						    jsonData:Ext.encode(submitValues), // convierte a estructura JSON
    						    
    						    success:function(response,opts){
    						    	panelInicialPral.setLoading(false);
    						        var jsonResp = Ext.decode(response.responseText);
    						        
    						        if(jsonResp.success==true){
    						            Ext.Msg.show({
    						                title:'Guardado',
    						                msg: 'Se gener&oacute; el n&uacute;mero de tr&aacute;mite '+ Ext.decode(response.responseText).msgResult ,
    						                buttons: Ext.Msg.OK,
    						                icon: Ext.Msg.INFO
    						            });
    						            
    						            panelInicialPral.getForm().reset();
    						            storeFactCtrl.removeAll();
    						            windowLoader.close();
    						        }
    						        else{
    						            Ext.Msg.show({
    						                title:'Error',
    						                msg: 'Error al realizar los registros',
    						                buttons: Ext.Msg.OK,
    						                icon: Ext.Msg.ERROR
    						            });
    						            
    						            respuesta= false;
    						        }
    						    },
    						    failure:function(response,opts)
    						    {
    						        panelInicialPrincipal.setLoading(false);
    						        Ext.Msg.show({
    						            title:'Error',
    						            msg: 'Error de comunicaci&oacute;n',
    						            buttons: Ext.Msg.OK,
    						            icon: Ext.Msg.ERROR
    						        });
    						    }
    						});
    	                }
            			else
        				{
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
    	            handler:function()
    	            {
    	            	windowLoader.close();
    	            }
    	        }
    	    ]
    	    });
    
    Ext.getCmp('cmbTipoPago').setValue('1');
    
	function limpiarRegistros()
	{
		Ext.getCmp('idUnieco').setValue('');
		Ext.getCmp('idEstado').setValue('');
		Ext.getCmp('idcdRamo').setValue('');
		Ext.getCmp('idNmSituac').setValue('');
		Ext.getCmp('polizaAfectada').setValue('');
		Ext.getCmp('idNmsolici').setValue('');
		Ext.getCmp('idNmsuplem').setValue('');
		Ext.getCmp('idCdtipsit').setValue('');
		
    	return true;
	}
});