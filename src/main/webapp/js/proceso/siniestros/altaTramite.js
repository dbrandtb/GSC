Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {
	
	Ext.selection.CheckboxModel.override( {
	    mode: 'SINGLE',
	    allowDeselect: true
	});

	//Models:
	Ext.define('modelFactCtrl', {
	    extend:'Ext.data.Model',
	    fields:['noFactura','fechaFactura','tipoServicio','tipoServicioName','proveedor','proveedorName','importe']
	});
	
	Ext.define('modelListadoProvMedico',{
	    extend: 'Ext.data.Model',
	    fields: [
            {type:'string', name:'cdpresta'},
            {type:'string', name:'nombre'},
            {type:'string', name:'cdespeci'},
            {type:'string',name:'descesp'}
	    ]
	});
	
	Ext.define('modelListadoPoliza',{
	    extend: 'Ext.data.Model',
	    fields: [	{type:'string',    name:'cdramo'},				{type:'string',    name:'cdunieco'},				{type:'string',    name:'estado'},
					{type:'string',    name:'nmpoliza'},			{type:'string',    name:'nmsituac'},				{type:'string',    name:'mtoBase'},
					{type:'string',    name:'feinicio'},			{type:'string',    name:'fefinal'},					{type:'string',    name:'dssucursal'},
					{type:'string',    name:'dsramo'},				{type:'string',    name:'estatus'},					{type:'string',    name:'dsestatus'},
					{type:'string',    name:'nmsolici'},			{type:'string',    name:'nmsuplem'},				{type:'string',    name:'cdtipsit'},
					{type:'string',    name:'dsestatus'},			{type:'string',    name:'vigenciaPoliza'},			{type:'string',    name:'faltaAsegurado'},
					{type:'string',    name:'fcancelacionAfiliado'},{type:'string',    name:'desEstatusCliente'},		{type:'string',    name:'numPoliza'}]
	});
	
	Ext.define('modelListAsegPagDirecto',{
	    extend: 'Ext.data.Model',
	    fields: [
	                {type:'string',    name:'modUnieco'},		{type:'string',    name:'modEstado'},		    {type:'string',    name:'modRamo'},
	                {type:'string',    name:'modNmsituac'},		{type:'string',    name:'modPolizaAfectada'},	{type:'string',    name:'modCdpersondesc'},
	                {type:'string',    name:'modNmsolici'},		{type:'string',    name:'modNmsuplem'},		    {type:'string',    name:'modCdtipsit'},
	                {type:'string',    name:'modNmautserv'},	{type:'string',    name:'modFechaOcurrencia'},	{type:'string',    name:'modCdperson'},
	                {type:'string',    name:'modnumPoliza'}
	            ]
	});
	

	//Stores:
	
	oficinaReceptora = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url:_URL_CATALOGOS,
            extraParams : {catalogo:_CATALOGO_OFICINA_RECEP},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
	
	oficinaEmisora = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:true,
        proxy:
        {
            type: 'ajax',
            url:_URL_CATALOGOS,
            extraParams : {catalogo:_CATALOGO_OFICINA_RECEP},
            reader:
            {
                type: 'json',
                root: 'lista'
            }
        }
    });
    
	var storeFactCtrl=new Ext.data.Store(
	{
	    autoDestroy: true,
	    model: 'modelFactCtrl'
	});

	var storeListAsegPagDirecto=new Ext.data.Store(
	{
	    autoDestroy: true,						model: 'modelListAsegPagDirecto'
	});
	
	var storeTipoAtencion = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoAtencion},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoAtencion.load();
	
	var storeTipoAtencion2 = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoAtencion},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoAtencion2.load();
	var storeAsegurados = Ext.create('Ext.data.Store', {
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
	
	var storeAsegurados2 = Ext.create('Ext.data.Store', {
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
			url: _URL_CATALOGOS,
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
            url : _URL_CATALOGOS,
            extraParams:{
                catalogo         : _CATALOGO_PROVEEDORES,
                catalogoGenerico : true
            },
            reader: {
                type: 'json',
                root: 'listaGenerica'
            }
        }
    });

    var storeListadoPoliza = new Ext.data.Store(
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
    
    
    var cmbOficinaReceptora = Ext.create('Ext.form.field.ComboBox',
	{
	    fieldLabel : 'Oficina receptora',				id:'cmbOficReceptora',			name      : 'cmbOficReceptora',
	    allowBlank : false,							editable   : true,				displayField : 'value',
	    labelWidth : 250,		   					 emptyText:'Seleccione...',		width		 : 500,
	    valueField   : 'key',						forceSelection : true,			queryMode      :'local',
	    store : oficinaReceptora
	});
   
    var cmbOficinaEmisora = Ext.create('Ext.form.field.ComboBox',
	{
	    fieldLabel : 'Oficina emisora',				id:'cmbOficEmisora',			name      : 'cmbOficEmisora',
	    allowBlank : false,							editable   : true,				displayField : 'value',
	    labelWidth : 250,		   					 emptyText:'Seleccione...',		width		 : 500,
	    valueField   : 'key',						forceSelection : true,			queryMode      :'local',
	    store : oficinaEmisora
	});
    
    var comboTipoAte= Ext.create('Ext.form.ComboBox',
    {
        id:'cmbTipoAtencion',			   name:'cmbTipoAtencion',		        fieldLabel: 'Tipo atenci&oacute;n',				queryMode:'local',
        displayField: 'value',	   valueField: 'key',					editable:false,
        labelWidth : 250,		   emptyText:'Seleccione...',			width		 : 500,						store: storeTipoAtencion
    });

    

    var tipoPago= Ext.create('Ext.form.ComboBox',
    		{
    	id:'cmbTipoPago',			   name:'cmbTipoPago',		        fieldLabel: 'Tipo pago',				queryMode:'local',
    	displayField: 'value',	   valueField: 'key',					allowBlank:false,						editable:false,
    	labelWidth : 250,		   width		 : 500,					emptyText:'Seleccione...',			store: storeTipoPago,
    	listeners : {
    		
    		change:function(e){
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
    				Ext.getCmp('idnombreBeneficiarioProv').setValue('');
    			    Ext.getCmp('idnombreAsegurado').setValue('');
    				
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
    				Ext.getCmp('idnombreBeneficiarioProv').setValue('');
    			    Ext.getCmp('idnombreAsegurado').setValue('');
    			}
    		}
    	}
	});

    
    aseguradoAfectado = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'Asegurado afectado',      displayField : 'value',         name:'cmbAseguradoAfectado',
    	id:'cmbAseguradoAfectado',              labelWidth: 250,            width:500,                      valueField   : 'key',
    	forceSelection : true,                 matchFieldWidth: false,     queryMode :'remote',            queryParam: 'params.cdperson',
    	minChars  : 2,                          store : storeAsegurados,    triggerAction: 'all',			hideTrigger:true,
    	listeners : {
            'select' : function(combo, record) {
                    obtieneCDPerson = this.getValue();
                    Ext.getCmp('idnombreAsegurado').setValue(aseguradoAfectado.rawValue);
                   
                    var params = {
                            'params.cdperson' : obtieneCDPerson
                    };
                    
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
                    
                    modPolizasAltaTramite.show();
                }
            }
    });
    
    var cmbBeneficiario= Ext.create('Ext.form.ComboBox',
	{
        id:'cmbBeneficiario',       name:'cmbBeneficiario',        	fieldLabel: 'Beneficiario',         queryMode:'remote',
        displayField: 'value',    	valueField: 'key',        		editable:true,					 	labelWidth : 250,
        width		 : 500,			forceSelection : true,          matchFieldWidth: false,     		queryParam: 'params.cdperson',
    	minChars  : 2, 				store : storeAsegurados2,    	triggerAction: 'all',				hideTrigger:true,
    	listeners : {
			'select' : function(combo, record) {
				Ext.getCmp('idnombreBeneficiarioProv').setValue(cmbBeneficiario.rawValue);
			}
    	}
    });
    
    
    cmbProveedor = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'Proveedor',		displayField : 'nombre',		name:'cmbProveedor',
    	id:'cmbProveedor',				labelWidth: 250,				width:500,					valueField   : 'cdpresta',
    	forceSelection : true,			matchFieldWidth: false,			queryMode :'remote',		queryParam: 'params.cdpresta',
    	minChars  : 2,					store : storeProveedor,			triggerAction: 'all',		hideTrigger:true,
    	listeners : {
			'select' : function(combo, record) {
				Ext.getCmp('idnombreBeneficiarioProv').setValue(cmbProveedor.rawValue);
			}
    	}
    	
    	
    });

    // PANEL PARA EL PAGO POR REEMBOLSO
    var panelModificacionInsercion= Ext.create('Ext.form.Panel',{
        border  : 0
        ,bodyStyle:'padding:5px;'
        ,items :
        [   
            {	
            	xtype      : 'textfield',			name       : 'noFactInterno',		    	fieldLabel : 'No. Factura',	    		labelWidth: 170,	    		allowBlank:false	
            },
            {
            	name: 'fechaFactInterno',	    	fieldLabel: 'Fecha Factura',    	        xtype: 'datefield',		    	        format: 'd/m/Y',
            	editable: true,						allowBlank:false,			    			labelWidth : 170,			   	        value:new Date()
            },
            {
            	xtype: 'combo',		                name:'tipoServicioInterno',	                valueField: 'key',		                displayField: 'value',
                fieldLabel: 'Tipo de servicio',     store: storeTipoAtencion2,	                queryMode:'local',		                allowBlank:false,
                editable:false,		                width: 500,					                labelWidth : 170,		                emptyText:'Seleccione...'
            },
            {
            	xtype       : 'combo',            	name        :'proveedorInterno',           	fieldLabel  : 'Proveedor',            	displayField: 'nombre',
            	valueField  : 'cdpresta',          	allowBlank  : false,		            	minChars  : 2,			            	width       : 500,
                forceSelection : true,              matchFieldWidth: false,		                queryMode   :'remote',	                queryParam  : 'params.cdpresta',
                store       : storeProveedor,	    triggerAction  : 'all',		                labelWidth  : 170,		                emptyText   : 'Seleccione...',
                editable    : true,                hideTrigger:true
            },
            {   
            	xtype      : 'numberfield',	        name       : 'importeInterno',		    	fieldLabel : 'Importe',			    	allowBlank:false,
		    	allowDecimals :true,		    	decimalSeparator :'.',				    	minValue: 0,			                width:500,                labelWidth: 170
    		}
        ]
    });
    /*PANTALLA EMERGENTE PARA LA INSERCION Y MODIFICACION DE LOS DATOS DEL GRID PARA EL PAGO POR REEMBOLSO*/
    var ventanaGrid= Ext.create('Ext.window.Window', {
           title: 'NUEVAS FACTURAS',
           closeAction: 'hide',
           modal: true,
           resizable: false,
           height: 230,
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
        					 	proveedor: datos.proveedorInterno,
        					 	importe: datos.importeInterno,
        					 	proveedorName: panelModificacionInsercion.query('combo[name=proveedorInterno]')[0].rawValue,
        					 	tipoServicioName: panelModificacionInsercion.query('combo[name=tipoServicioInterno]')[0].rawValue
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
 		title: 'Alta de facturas',
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
    
    
    /* PANEL PARA EL PAGO DIRECTO */
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
                format		: 'd/m/Y'
            },{
            	xtype: 'combo',
            	name:'cmbAseguradoAfect',
            	fieldLabel : 'Asegurado',
        	    displayField : 'value',
        	    valueField   : 'key',
        	    allowBlank: false,
        	    width:500,
        	    minChars  : 2,
        	    forceSelection : true,
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

   /*PANTALLA EMERGENTE PARA EL PAGO DIRECTO */
   var ventanaAgregarAsegurado= Ext.create('Ext.window.Window', {
        title: 'Asegurados',
        //height: 200,
        closeAction: 'hide',
        modal: true, 
        resizable: false,
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
                        	modCdpersondesc: panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].rawValue,
                        	modnumPoliza:Ext.getCmp('idNumPolizaInt').getValue()
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
                        header: 'N&uacute;mero P&oacute;liza',		dataIndex: 'modnumPoliza',				width:200
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
    
    /* PANEL PARA LA BUSQUEDA DE LA INFORMACIÓN DEL ASEGURADO PARA LA BUSQUEDA DE LAS POLIZAS */
    gridPolizasAltaTramite= Ext.create('Ext.grid.Panel',
	{
	    id            : 'polizaGridAltaTramite',
	    store         : storeListadoPoliza,
	    //collapsible   : true,
	    //titleCollapse : true,
	    selType	      : 'checkboxmodel',
	    width         : 700,
	    height		  : 200,
	    columns       :
	    [
	        
	        
	        {
	             header     : 'N&uacute;mero de P&oacute;liza',		dataIndex : 'numPoliza',		width	 	: 200
	        },
	        /*{
	             header     : 'N&uacute;mero de P&oacute;liza',		dataIndex : 'nmpoliza',		width	 	: 150
	        },*/
	        {
	        	 header     : 'Estatus p&oacute;liza ',							dataIndex : 'dsestatus',	width	 	: 100
	        },
	        {
	             header     : 'Vigencia p&oacute;liza <br/> Fecha inicio \t\t  |  \t\t Fecha fin  ',						dataIndex : 'vigenciaPoliza',		width	    : 200
	        },
	        {
	             header     : 'Fecha alta <br/> asegurado',		dataIndex : 'faltaAsegurado',		width	    : 100
	        },
	        {
	             header     : 'Fecha cancelaci&oacute;n <br/> asegurado',						dataIndex : 'fcancelacionAfiliado',		width	    : 150
	        },
	        {
	             header     : 'Estatus<br/> asegurado',						dataIndex : 'desEstatusCliente',		width	    : 100
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
                	//1.- Validamos que el asegurado este vigente
                	if(record.get('desEstatusCliente')=="Vigente")
            		{
                		var valorFechaOcurrencia;
                		if(Ext.getCmp('cmbTipoPago').getValue() == "1")
            			{
                			var valorFechaOcu = panelListadoAsegurado.query('datefield[name=dtfechaOcurrencias]')[0].rawValue;
                			valorFechaOcurrencia = new Date(valorFechaOcu.substring(6,10)+"/"+valorFechaOcu.substring(3,5)+"/"+valorFechaOcu.substring(0,2));
            			}else{
            				valorFechaOcurrencia = Ext.getCmp('dtFechaOcurrencia').getValue();
            			}
                		
                		var valorFechaInicial = new Date(record.get('feinicio').substring(6,10)+"/"+record.get('feinicio').substring(3,5)+"/"+record.get('feinicio').substring(0,2));
                		var valorFechaFinal =   new Date(record.get('fefinal').substring(6,10)+"/"+record.get('fefinal').substring(3,5)+"/"+record.get('fefinal').substring(0,2));
                		var valorFechaAltaAsegurado = new Date(record.get('faltaAsegurado').substring(6,10)+"/"+record.get('faltaAsegurado').substring(3,5)+"/"+record.get('faltaAsegurado').substring(0,2));
                		
                		if( (valorFechaOcurrencia <= valorFechaFinal) && (valorFechaOcurrencia >= valorFechaInicial)){
                    		if( valorFechaOcurrencia >= valorFechaAltaAsegurado )
                			{
                    			//cumple la condición la fecha de ocurrencia es menor igual a la fecha de alta de tramite
                    			Ext.getCmp('idUnieco').setValue(record.get('cdunieco'));
            					Ext.getCmp('idEstado').setValue(record.get('estado'));
            					Ext.getCmp('idcdRamo').setValue(record.get('cdramo'));
            					Ext.getCmp('idNmSituac').setValue(record.get('nmsituac'));
            					Ext.getCmp('polizaAfectada').setValue(record.get('nmpoliza'));
            					Ext.getCmp('idNmsolici').setValue(record.get('nmsolici'));
            					Ext.getCmp('idNmsuplem').setValue(record.get('nmsuplem'));
            					Ext.getCmp('idCdtipsit').setValue(record.get('cdtipsit'));
            					Ext.getCmp('idNumPolizaInt').setValue(record.get('numPoliza'));
            					modPolizasAltaTramite.hide();
                			}else{
                				// No se cumple la condición la fecha de ocurrencia es mayor a la fecha de alta de tramite
                				Ext.Msg.show({
        	                    	title:'Error',
        	                    	msg: 'La fecha de ocurrencia es mayor a la fecha de alta del asegurado',
        	                    	buttons: Ext.Msg.OK,
        	                    	icon: Ext.Msg.ERROR
        	                	});
                				modPolizasAltaTramite.hide();
                    			limpiarRegistros();
                    			if(Ext.getCmp('cmbTipoPago').getValue() == "1")
                    			{
                    				panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].setValue('');
                    			}else{
                    				Ext.getCmp('cmbAseguradoAfectado').setValue('');
                    			}
            					
                			}
                		}else{
                			// La fecha de ocurrencia no se encuentra en el rango de la poliza vigente
                			Ext.Msg.show({
    	                    	title:'Error',
    	                    	msg: 'La fecha de ocurrencia no se encuentra en el rango de la p&oacute;liza vigente',
    	                    	buttons: Ext.Msg.OK,
    	                    	icon: Ext.Msg.ERROR
    	                	});
                			modPolizasAltaTramite.hide();
                			limpiarRegistros();
                			if(Ext.getCmp('cmbTipoPago').getValue() == "1")
                			{
                				panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].setValue('');
                			}else{
                				Ext.getCmp('cmbAseguradoAfectado').setValue('');
                			}
                		}
            		}else{
            			// El asegurado no se encuentra vigente
            			Ext.Msg.show({
	                    	title:'Error',
	                    	msg: 'El asegurado de la p&oacute;liza seleccionado no se encuentra vigente',
	                    	buttons: Ext.Msg.OK,
	                    	icon: Ext.Msg.ERROR
	                	});
            			modPolizasAltaTramite.hide();
            			limpiarRegistros();
            			if(Ext.getCmp('cmbTipoPago').getValue() == "1")
            			{
            				panelListadoAsegurado.query('combo[name=cmbAseguradoAfect]')[0].setValue('');
            			}else{
            				Ext.getCmp('cmbAseguradoAfectado').setValue('');
            			}
            		}
                }
            }

	});
    gridPolizasAltaTramite.store.sort([
        {
        	property    : 'nmpoliza',			direction   : 'DESC'
        }
    ]);
    
    
    var modPolizasAltaTramite = Ext.create('Ext.window.Window',
	{
	    title        : 'Listado de P&oacute;liza'
	    ,modal       : true
	    ,resizable   : false
	    ,buttonAlign : 'center'
	    ,closable    : false
	    ,width		 : 710
	    ,minHeight 	 : 100 
	    ,maxheight   : 400
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
					    xtype       : 'textfield',			fieldLabel : 'numPolizaInt'			,id       : 'idNumPolizaInt',	name       : 'numPolizaInt'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'Asegurado'			,id       : 'idnombreAsegurado',	name       : 'idnombreAsegurado'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'Proveedor/Beneficiario'			,id       : 'idnombreBeneficiarioProv',	name       : 'idnombreBeneficiarioProv'
					    ,labelWidth: 170,	hidden:true
					},
					{
					    xtype       : 'textfield',			fieldLabel : 'NumTramite'			,id       : 'idNumTramite',	name       : 'idNumTramite'
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
		            cmbOficinaReceptora
		            ,
		            cmbOficinaEmisora
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
                        width		 : 500,
                        maxValue   :  new Date()
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
    	            text: 'Generar Tr&aacute;mite',
            		handler: function() {
            			
            			var form = this.up('form').getForm();
            			if (form.isValid())
    	                {
            				//aqui realizamos el llamado al guardado
            				var respuesta=true;
            				//Llenamos los campos dependiendo si es pago por reembolso
            				if( Ext.getCmp('cmbTipoPago').getValue() == "1") // Pago Directo
        					{
            					var obtener = [];
                				storeListAsegPagDirecto.each(function(record) {
                					console.log("VALORES DEl STORE LISTA ASEGURADO");
                					console.log(record);
                					
                                    obtener.push(record.data);
                                });
                				
                				console.log("VALOR LENGTH");
                				console.log(obtener.length);
                				
                				if(obtener.length == 1){
                					Ext.getCmp('idUnieco').setValue(obtener[0].modUnieco);
                					Ext.getCmp('idEstado').setValue(obtener[0].modEstado);
                					Ext.getCmp('idcdRamo').setValue(obtener[0].modRamo);
                					Ext.getCmp('idNmSituac').setValue(obtener[0].modNmsituac);
                					Ext.getCmp('polizaAfectada').setValue(obtener[0].modPolizaAfectada);
                					Ext.getCmp('idNmsolici').setValue(obtener[0].modNmsolici);
                					Ext.getCmp('idNmsuplem').setValue(obtener[0].modNmsuplem);
                					Ext.getCmp('idCdtipsit').setValue(obtener[0].modCdtipsit);
                					
                					storeAsegurados.load({
        								params:{
        				                    'params.cdperson':obtener[0].modCdperson
        								}
        			    			});
                					
                					Ext.getCmp('cmbAseguradoAfectado').setValue(obtener[0].modCdperson);
                					Ext.getCmp('dtFechaOcurrencia').setValue(obtener[0].modFechaOcurrencia);
                				}
        					}else{
        						
        						var obtener = [];
        						storeFactCtrl.each(function(record) {
                                    obtener.push(record.data);
                                });
                				if(obtener.length == 1){
                					Ext.getCmp('cmbProveedor').setValue(obtener[0].proveedor);
            	    				Ext.getCmp('txtNoFactura').setValue(obtener[0].noFactura);
            	    				Ext.getCmp('txtImporte').setValue(obtener[0].importe);
            	    				Ext.getCmp('dtFechaFactura').setValue(obtener[0].fechaFactura);
            	    				Ext.getCmp('cmbTipoAtencion').setValue(obtener[0].tipoServicio);
        	    				}else{
        	    					Ext.getCmp('dtFechaFactura').setValue('');
        	    					Ext.getCmp('cmbTipoAtencion').setValue(obtener[0].tipoServicio);
        	    				}
        					}
            				
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
            				
            				console.log("VALORES AL ENVAR AL GUARDADO");
            				console.log(submitValues);
            				
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
    						                msg: 'Error en el guardado del alta de tr&aacute;mite',
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
    
    
    //console.log(valorAction);
    
    if(valorAction == null)
	{
    	Ext.getCmp('cmbTipoPago').setValue('1');
	}else{
		
			
			storeProveedor.load();
			Ext.Ajax.request(
			{
			    url     : _URL_CONSULTA_ALTA_TRAMITE
			    ,params:{
					'params.ntramite': valorAction.ntramite
                }
			    ,success : function (response)
			    {
			    	if(Ext.decode(response.responseText).listaMesaControl != null)
		    		{
			    		var json=Ext.decode(response.responseText).listaMesaControl[0];
			    		console.log("VALOR DEL JSON DE LA MESA DE CONTROL");
			    		console.log(json);
			    		
			    		//<<<
			    		//ASIGNACION DE VALORES GENERALES PARA PAGO DIRECTO Y REEMBOLSO
			    		Ext.getCmp('idNumTramite').setValue(valorAction.ntramite);
			    		Ext.getCmp('txtEstado').setValue('PENDIENTE');
			    		Ext.getCmp('cmbOficReceptora').setValue(json.cdsucdocmc);
			    		Ext.getCmp('cmbOficEmisora').setValue(json.cdsucadmmc);
			    		Ext.getCmp('dtFechaRecepcion').setValue(json.ferecepcmc);
			    		Ext.getCmp('cmbTipoAtencion').setValue(json.otvalor07mc);
			    		Ext.getCmp('cmbTipoPago').setValue(json.otvalor02mc);
			    		
			    		if(Ext.getCmp('cmbTipoPago').getValue() =="1"){
			    			Ext.getCmp('cmbProveedor').setValue(json.otvalor11mc);
			    			Ext.getCmp('txtNoFactura').setValue(json.otvalor08mc);
			    			Ext.getCmp('txtImporte').setValue(json.otvalor03mc);
			    			Ext.getCmp('dtFechaFactura').setValue(json.otvalor06mc);
			    			
			    		}else{
			    			Ext.getCmp('dtFechaOcurrencia').setValue(json.otvalor10mc);
			    			Ext.getCmp('idnombreBeneficiarioProv').setValue(json.otvalor15mc);
			    			Ext.getCmp('idnombreAsegurado').setValue(json.nombremc);
			    			
			    			console.log(Ext.getCmp('cmbAseguradoAfectado').getValue());
			    			storeAsegurados.load({
								params:{
				                    'params.cdperson':json.otvalor09mc
								}
			    			});
			    			
			    			Ext.getCmp('cmbAseguradoAfectado').setValue(json.otvalor09mc);
			    			
			    			storeAsegurados2.load({
								params:{
				                    'params.cdperson':json.otvalor04mc
								}
			    			});
			    			
			    			Ext.getCmp('cmbBeneficiario').setValue(json.otvalor04mc);
			    			
			    			//Ext.getCmp('cmbBeneficiario').setValue(json.otvalor04mc);
			    			
			    			
			    			/*storeAsegurados.load({
								params:{
				                    'params.cdperson':json.otvalor09mc
								}
			    			});
			    			
			    			
			    			
			    			storeAsegurados.load({
								params:{
				                    'params.cdperson':json.otvalor04mc
								}
			    			});
			    			
			    			console.log(json.otvalor04mc);*/
			    			
			    		}
			    		
			    		
			    		/*COMO SEGUNDO PUNTO ES OBTENER LA INFORMACION DE LOS GRIDS*/
			    		Ext.Ajax.request(
	    				{
	    				    url     : _URL_CONSULTA_GRID_ALTA_TRAMITE
	    				    ,params:{
	    						'params.ntramite': valorAction.ntramite
	    	                }
	    				    ,success : function (response)
	    				    {
	    	                    console.log("VALOR DE RESPUESTA _URL_CONSULTA_GRID_ALTA_TRAMITE");
	    	                    //console.log(Ext.decode(response.responseText));
	    	                    if(Ext.decode(response.responseText).listaAltaTramite != null)
	    			    		{
	    				    		//console.log(Ext.decode(response.responseText).listaAltaTramite.length);
	    				    		var json=Ext.decode(response.responseText).listaAltaTramite;
	    				    		console.log("VALOR DE JSON");
	    				    		console.log(json);
	    				    		
	    				    		if(Ext.getCmp('cmbTipoPago').getValue() =="1"){
	    				    			// PAGO DIRECTO
	    				    			var nombreProveedor= json[0].cdpresta +" "+ json[0].dspresta;
	    				    			Ext.getCmp('idnombreBeneficiarioProv').setValue(nombreProveedor);
	    				    			for(var i = 0; i < json.length; i++){
		    				    			var rec = new modelListAsegPagDirecto({
		    		                        	modUnieco: json[i].cdunieco,
		    		                        	modEstado: json[i].estado,
		    		                        	modRamo: json[i].cdramo,
		    		                        	modNmsituac: json[i].nmsituac,
		    		                        	modPolizaAfectada: json[i].nmpoliza,
		    		                        	modNmsolici: json[i].nmsolici,
		    		                        	modNmsuplem: json[i].nmsuplem,
		    		                        	modCdtipsit: json[i].cdtipsit,
		    		                        	modNmautserv: "",
		    		                        	modFechaOcurrencia: json[i].feocurre,
		    		                        	modCdperson: json[i].cdperson,
		    		                        	modCdpersondesc: json[i].nombreAsegurado,
		    		                        	modnumPoliza:json[i].nmpoliex // --> Se tiene que cambiar
		    		                        });

		    				    			//console.log("VALORES OBTENIDOS");
		    				    			console.log(rec);
		    		                        storeListAsegPagDirecto.add(rec);
		    		                        //limpiarRegistros();
		    				    		}
	    				    		}else{
	    				    			
	    				    			for(var i = 0; i < json.length; i++){
	    				    				var rec = new modelFactCtrl({
							 				  	noFactura: json[i].nfactura,
											 	fechaFactura: json[i].ffactura,
											 	tipoServicio: json[i].cdtipser,
											 	proveedor: json[i].cdpresta,
											 	importe: json[i].ptimport,
											 	proveedorName: json[i].dspresta,
											 	tipoServicioName: json[i].dstipser
		    		                        });

		    				    			//console.log("VALORES OBTENIDOS");
		    				    			//console.log(rec);
		    				    			storeFactCtrl.add(rec);
		    		                        //limpiarRegistros();
		    				    		}
	    				    			/*	
					        	 		});
					                	
					                	storeFactCtrl.add(rec);
					                	ventanaGrid.close();
					                	panelModificacionInsercion.getForm().reset();*/
	    				    		}
	    				    		
	    				    		
	    				    		
	    				    		
	    				    		
	    				    		/**/
	    			    		}
	    				    },
	    				    failure : function ()
	    				    {
	    				        me.up().up().setLoading(false);
	    				        Ext.Msg.show({
	    				            title:'Error',
	    				            msg: 'Error de comunicaci&oacute;n',
	    				            buttons: Ext.Msg.OK,
	    				            icon: Ext.Msg.ERROR
	    				        });
	    				    }
	    				});
		    		}
			    },
			    failure : function ()
			    {
			        me.up().up().setLoading(false);
			        Ext.Msg.show({
			            title:'Error',
			            msg: 'Error de comunicaci&oacute;n',
			            buttons: Ext.Msg.OK,
			            icon: Ext.Msg.ERROR
			        });
			    }
			});
			//Ext.getCmp('cmbTipoPago').setValue('2');
	}
    
    
    
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
		Ext.getCmp('idNumPolizaInt').setValue('');
    	return true;
	}
});