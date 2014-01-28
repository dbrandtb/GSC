Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);
var datosgrid;
var storeConceptoAutorizados;
var storeQuirugicoBase;
var storeQuirugico;
var extraParams='';
Ext.onReady(function() {

	Ext.selection.CheckboxModel.override( {
		mode: 'SINGLE',
		allowDeselect: true
	});
	
    // Conversión para el tipo de moneda
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';
    
    
	////////////////////////////////////////////////
	/////////// 		MODELO				////////
	////////////////////////////////////////////////
    Ext.define('modelListadoCobertura',{
        extend: 'Ext.data.Model',
        fields: [
                 	{type:'string',    name:'cdgarant'},		{type:'string',    name:'dsgarant'},		{type:'string',    name:'ptcapita'}
				]
    });
    
	Ext.define('modelListadoAsegurado',{
        extend: 'Ext.data.Model',
        fields: [
                 	{type:'string',    name:'nmautser'},		{type:'string',    name:'nmautant'},		{type:'string',    name:'fesolici'},
					{type:'string',    name:'polizaafectada'},	{type:'string',    name:'cdprovee'},		{type:'string',    name:'nombreProveedor'}
				]
    });
	
	//DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
	Ext.define('modelConceptoAutorizado',
	{
		extend:'Ext.data.Model',
		fields:['medico','cpt','descripcion','precio','cantidad','importe']
	});
	
	//DATOS PARA EL SEGUNDO GRID --> EQUIPO QUIRURGICO BASE
	Ext.define('equipoQuirugicoBase',
	{
		extend:'Ext.data.Model',
		fields:['cpt','descripcion','precio','porcentaje','importe']
	});
	
	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
	Ext.define('equipoQuirurgico',
	{
		extend:'Ext.data.Model',
		fields:['medico','porcentaje','importe']
	});
    
	////////////////////////////////////////////////
	/////////// 		STORE				////////
	////////////////////////////////////////////////
	
    //STORE PARA SELECCIONAR EL TIPO DE AUTORIZACION
    storeTipoAutorizacion= Ext.create('Ext.data.JsonStore', {
    	model:'Generic',
        proxy: {
            type: 'ajax',
            url: _URL_TIPO_AUTORIZACION,
            reader: {
                type: 'json',
                root: 'tiposAutorizacion'
            }
        }
    });
    storeTipoAutorizacion.load();
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
    
    storeSiniestros = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_CAUSA_SINIESTRO,
            reader: {
                type: 'json',
                root: 'listaCausaSiniestro'
            }
        }
    });

    
    /*Store que contiene la información general en el grid*/
    storeListadoAsegurado = new Ext.data.Store(
    {
    	pageSize : 5
        ,model      : 'modelListadoAsegurado'
        ,autoLoad  : false
        ,proxy     :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    
    var storeMedico = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_PROVEEDOR_MEDICO,
            extraParams:{
            	'params.tipoprov' : 'M'
            },
            reader: {
                type: 'json',
                root: 'listaProvMedico'
            }
        }
    });
    
    var storeProveedor = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_PROVEEDOR_MEDICO,
            extraParams:{
            	'params.tipoprov' : 'C'
            },
            reader: {
                type: 'json',
                root: 'listaProvMedico'
            }
        }
    });
    
    var storeTiposICD = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_CPTICD,
            extraParams:{
            	'params.cdtabla' : '2TABLICD'
            },
            reader: {
                type: 'json',
                root: 'listaCPTICD'
            }
        }
    });
    
    var storeTiposCPT = Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_CPTICD,
            extraParams:{
            	'params.cdtabla' : '2TABLCPT'
            },
            reader: {
                type: 'json',
                root: 'listaCPTICD'
            }
        }
    });
    
    //DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
	storeConceptoAutorizados=new Ext.data.Store(
	{
		autoDestroy: true,
		model: 'modelConceptoAutorizado'
	});
	
	//DATOS PARA EL SEGUNDO GRID --> EQUIPO QUIRURGICO BASE
	storeQuirugicoBase=new Ext.data.Store(
	{
		autoDestroy: true,
		model: 'equipoQuirugicoBase'
	});
	
	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
	storeQuirurgico=new Ext.data.Store(
	{
		autoDestroy: true,
		model: 'equipoQuirurgico'
	});
    
    
    /*//////////////////////////////////////////////////////////////////////////////////
     *////////////////////////////		VERIFICAR 	////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////*/
	
	var storeCobertura = Ext.create('Ext.data.Store', {
        model:'modelListadoCobertura',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_COBERTURA,
            reader: {
                type: 'json',
                root: 'listaCoberturaPoliza'
            }
        }
    });
    
    
    var storeSubcobertura= Ext.create('Ext.data.Store', {
        model:'Generic',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_LISTA_SUBCOBERTURA,
            reader: {
                type: 'json',
                root: 'listaSubcobertura'
            }
        }
    });

    
	/**LLAMADO AL JSON**/
	var storePenalizacion = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_TIPOS_PENALIZACION,
			reader: {
				type: 'json',
				root: 'tipoPenalizacion'
			}
		}
	});
	storePenalizacion.load();
	
	//////////////////////////////////////////
	///// DECLARACION DE LOS COMBOS /////////
	////////////////////////////////////////
	
	asegurado = Ext.create('Ext.form.field.ComboBox',
    {
		colspan:2,						fieldLabel : 'Asegurado',			allowBlank: false,				displayField : 'value',
        id:'idAsegurado',				labelWidth: 170,					width:500,						valueField   : 'key',
        forceSelection : false,			matchFieldWidth: false,				queryMode :'remote',			queryParam: 'params.cdperson',
        store : storeAsegurados,		triggerAction: 'all',
        listeners : {
			'select' : function(combo, record) {
					obtieneCDPerson = this.getValue();
					Ext.getCmp('idCobAfectada').reset();
					Ext.getCmp('idSubcobertura').reset();					
					storeSubcobertura.removeAll();
					storeCobertura.removeAll();
					// aqui va la funcion que va a generar carlos para obtener los valores de Unieco,ramo, estado, nmsituac y numero de poliza
					Ext.Ajax.request(
					{
					    url     : _URL_CONSULTA_LISTADO_AUTORIZACION
					    ,params : 
					    {
					        'params.cdperson'  : obtieneCDPerson,
					        'params.tipoAut':'2'
					    }
					    ,success : function (response)
					    {
					        //AQUI VA LA VALIDACION DE LOS CAMPOS QUE ME VA A TRAER, PARA ESTO LO QUE TENEMOS QUE GUARDAR 
					    	
							if(obtieneCDPerson=='510918')
							{
								Ext.getCmp('idUnieco').setValue('1009');
								Ext.getCmp('idEstado').setValue('M');
								Ext.getCmp('idcdRamo').setValue('2');
								Ext.getCmp('idNmSituac').setValue('1');
								Ext.getCmp('polizaAfectada').setValue('55');
							}
							else
							{
								Ext.getCmp('idUnieco').setValue('2009');
								Ext.getCmp('idEstado').setValue('A');
								Ext.getCmp('idcdRamo').setValue('23');
								Ext.getCmp('idNmSituac').setValue('11');
								Ext.getCmp('polizaAfectada').setValue('554');
							}
							storeCobertura.load({
			                    params:{
			                    	'params.cdunieco':Ext.getCmp('idUnieco').getValue(),
					            	'params.estado':Ext.getCmp('idEstado').getValue(),
					            	'params.cdramo':Ext.getCmp('idcdRamo').getValue(),
					            	'params.nmpoliza':Ext.getCmp('polizaAfectada').getValue(),
					            	'params.nmsituac':Ext.getCmp('idNmSituac').getValue()
			                    }
							});
			            	
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
			}
    });
	
	causaSiniestro = Ext.create('Ext.form.field.ComboBox',
    {
		colspan:2,						fieldLabel : 'Causa Siniestro',		allowBlank: false,				displayField : 'value',
		id:'idCausaSiniestro',			labelWidth: 170,					width:500,						valueField   : 'key',
		forceSelection : false,			matchFieldWidth: false,				queryMode :'remote',			queryParam: 'params.cdcausa',
		store : storeSiniestros,		triggerAction: 'all'
	});

    medico = Ext.create('Ext.form.field.ComboBox',
    {
		fieldLabel : 'M&eacute;dico',	allowBlank: false,					displayField : 'value',			id:'idMedico',
		labelWidth: 170,				valueField   : 'key',				forceSelection : false,			matchFieldWidth: false,
		queryMode :'remote',			queryParam: 'params.cdpresta',		store : storeMedico,			triggerAction: 'all'
    });
    
    medicoConAutorizado = Ext.create('Ext.form.field.ComboBox',
    {
		fieldLabel : 'M&eacute;dico',	allowBlank: false,					displayField : 'value',			id:'idmedicoConAutorizado',
		labelWidth: 100,				width:450,							valueField   : 'key',			forceSelection : false,
		matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdpresta',	store : storeMedico,
		triggerAction: 'all',			name:'idmedicoConAutorizado'
	});
    
    medicoEqQuirurg = Ext.create('Ext.form.field.ComboBox',
    {
		fieldLabel : 'M&eacute;dico',	allowBlank: false,					displayField : 'value',			id:'idmedicoEqQuirurg',
		labelWidth: 100,				width:450,							valueField   : 'key',			forceSelection : false,
		matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdpresta',	store : storeMedico,
		triggerAction: 'all',			name:'idmedicoEqQuirurg'
	});
    
    proveedor = Ext.create('Ext.form.field.ComboBox',
    {
    	colspan:2,						fieldLabel : 'Proveedor',			allowBlank: false,				displayField : 'value',
    	id:'idProveedor',				labelWidth: 170,					valueField   : 'key',			forceSelection : false,
    	matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdpresta',	store : storeProveedor,
    	triggerAction: 'all'
    });
    
    /**	VERIFICAR **/
    coberturaAfectada = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel :'Cobertura afectada',	allowBlank: false,				displayField : 'dsgarant',		id:'idCobAfectada',
    	labelWidth: 170,					valueField   : 'cdgarant',		forceSelection : false,			matchFieldWidth: false,
    	queryMode :'remote',				store : storeCobertura,			triggerAction: 'all',
        listeners : {
        	change:function(el) {
        		Ext.getCmp('idSubcobertura').reset();
        		storeSubcobertura.removeAll();
            	storeSubcobertura.load({
                    params:{
                    'params.cdgarant' : el.value
                    }
                });
            }
            }
    });
    /** VERIFICAR **/
    subCobertura = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'Subcobertura',	allowBlank: false,					displayField : 'value',			id:'idSubcobertura',
    	labelWidth: 170,				valueField   : 'key',				forceSelection : false,			matchFieldWidth: false,
    	queryMode :'remote',			store : storeSubcobertura,		triggerAction: 'all'
    });
    
    //storeTiposCPT
    comboICD = Ext.create('Ext.form.field.ComboBox',
    {
    	colspan:2,						fieldLabel : 'ICD',					allowBlank: false,				displayField : 'value',
    	id:'idComboICD',				labelWidth: 170,					valueField   : 'key',			forceSelection : false,
    	matchFieldWidth: false,			queryMode :'remote',				queryParam: 'params.cdsubcob',	store : storeTiposICD,
    	triggerAction: 'all'
    });
    
    cptConAutorizado = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'CPT',				allowBlank: false,					displayField : 'value',			id:'cptConAutorizado',
    	width:450,						valueField   : 'key',				forceSelection : false,			matchFieldWidth: false,
    	queryMode :'remote',			queryParam: 'params.cdsubcob',   	store : storeTiposCPT,			triggerAction: 'all',
    	name:'cptConAutorizado'
	});
    
    cptQuirBase = Ext.create('Ext.form.field.ComboBox',
    {
    	fieldLabel : 'CPT',				allowBlank: false,					displayField : 'value',			id:'cptQuirBase',
    	width:450,						valueField: 'key',					forceSelection : false,			matchFieldWidth: false,
    	queryMode :'remote',			queryParam: 'params.cdsubcob',		store : storeTiposCPT,			triggerAction: 'all',
    	name:'cptQuirBase'
    });
    
	//SUCURSAL
	sucursal= Ext.create('Ext.form.field.ComboBox',
	{
		colspan		:2,					fieldLabel   : 'Sucursal',			id: 'idSucursal',				allowBlank: false
		,editable   : false,			displayField : 'value',				valueField:'key',			    forceSelection : true
		,labelWidth : 170,				queryMode    :'local'
		,store : Ext.create('Ext.data.Store', {
			model:'Generic',
			autoLoad:true,
			proxy:
			{
				type: 'ajax',
				url:mesConUrlLoadCatalo,
				extraParams : {catalogo:_CAT_AUTORIZACION},
				reader:
				{
					type: 'json',
					root: 'lista'
				}
			}
		})
	});	
		
	//PENALIZACION
	penalizacion= Ext.create('Ext.form.ComboBox',
	{
		colspan:2,		    	id:'idPenalizacion',			    fieldLabel: 'Penalizaci&oacute;n',        store: storePenalizacion,				        
		queryMode:'local',      displayField: 'value',	        valueField: 'key',				        allowBlank:false,
		editable:false,	        labelWidth : 170,				emptyText:'Seleccione...'
	});
	   
    
	////////////////////////////////////////////////////////////////////////
	/////////// 		PANEL PARA PEDIR LOS REGISTROS				////////
	///////////////////////////////////////////////////////////////////////
	//DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
	var panelConceptosAutorizados= Ext.create('Ext.form.Panel',{
		border  : 0
		,startCollapsed : true
		,bodyStyle:'padding:5px;'
		,items :
		[   			
		 	medicoConAutorizado,
		 	cptConAutorizado,			
			{
		 		id		: 'desConAutorizado',			xtype      	: 'textfield',			fieldLabel 	: 'Descripci&oacute;n',		labelWidth: 100,
		 		width:450,								name:'desConAutorizado',			allowBlank	: false
			}
		 	,
			{
				id		: 'precioConAutorizado',		xtype      	: 'numberfield',			fieldLabel 	: 'Precio',					labelWidth: 100,
				width	:350,							name		:'precioConAutorizado',
				allowBlank	: false,					allowDecimals :true,          			decimalSeparator :'.',
				listeners:{
					change:function(field,value)
					{
						try
						{
							var importeTotal= Ext.getCmp('precioConAutorizado').getValue() * Ext.getCmp('cantidadConAutorizado').getValue();
							Ext.getCmp('importeConAutorizado').setValue(importeTotal);
						}catch(e){}
					}
				}
			},
			{
				id		: 'cantidadConAutorizado',		xtype      	: 'numberfield',			fieldLabel 	: 'Cantidad',				labelWidth: 100,			width:350,
				name    : 'cantidadConAutorizado',		allowBlank	: false,
				listeners:{
					change:function(field,value)
					{
						try
						{
							var importeTotal= Ext.getCmp('precioConAutorizado').getValue() * Ext.getCmp('cantidadConAutorizado').getValue();
							Ext.getCmp('importeConAutorizado').setValue(importeTotal);
						}catch(e){}
					}
				}
			},
			{
				id		: 'importeConAutorizado',		xtype      	: 'numberfield',			fieldLabel 	: 'Importe',				labelWidth: 100,			width:350,
				name    : 'importeConAutorizado',		allowBlank	: false,					allowDecimals :true,          			decimalSeparator :'.'
			}
		]
	});
	
	//DATOS PARA EL SEGUDO GRID --> EQUIPO QUIRURGICO BASE
	var panelEquipoQuirurgicoBase= Ext.create('Ext.form.Panel',{
		border  : 0
		,bodyStyle:'padding:5px;'
		,items :
		[              
		 	cptQuirBase
			,
			{
			id		: 'descripcionQuirurgico',			xtype      	: 'textfield',			fieldLabel 	: 'Descripci&oacute;n',		labelWidth: 100,
			name    : 'descripcionQuirurgico',			allowBlank	: false,				width:450
			}
			,
			{
			id		: 'precioQuirurgico',				xtype      	: 'numberfield',			fieldLabel 	: 'Precio',					labelWidth: 100,
			name    : 'precioQuirurgico',				allowBlank	: false,					allowDecimals :true,          			decimalSeparator :'.'
			
			}
			,
			{
			id		: 'porcentajeQuirurgico',			xtype      	: 'numberfield',		fieldLabel 	: 'Porcentaje',				labelWidth: 100,
			name    : 'porcentajeQuirurgico',			allowBlank	: false
			}
			,
			{
			id		: 'importeQuirurgico',				xtype      	: 'numberfield',		fieldLabel 	: 'Importe',				labelWidth: 100,
			name    : 'importeQuirurgico',				allowBlank	:false,					allowDecimals :true,          			decimalSeparator :'.'
			}
		]
	});
	
	
	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
	var panelEquipoQuirurgico= Ext.create('Ext.form.Panel',{
		border  : 0
		,bodyStyle:'padding:5px;'
		,items :
		[ 
		 	medicoEqQuirurg,
		 	{
		 		id		: 'porcentajeEqQuirurg',			xtype      	: 'numberfield',	fieldLabel 	: 'Porcentaje',		labelWidth: 100,
		 		name    : 'porcentajeEqQuirurg',			allowBlank	: false
		 	}
		 	,
		 	{
		 		id		: 'importeEqQuirurg',			xtype      	: 'numberfield',		fieldLabel 	: 'Importe',		labelWidth: 100,
		 		name    : 'importeEqQuirurg',			allowBlank	: false,				allowDecimals :true,     		decimalSeparator :'.'
		 	}
		]
	});
	
	////////////////////////////////////////////////////////////////////////////
	/////////// 		VENTANA PARA PEDIR LOS REGISTROS				////////
	///////////////////////////////////////////////////////////////////////////
	//DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
	ventanaConceptosAutorizado= Ext.create('Ext.window.Window', {
		renderTo: document.body,
		title: 'Conceptos Autorizados',
		height: 270,
		closeAction: 'hide',           
		items:[panelConceptosAutorizados],		
		buttons:[
	        {
	        	text: 'Aceptar',
	        	icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
	        	handler: function() {
	        		if (panelConceptosAutorizados.form.isValid()) {			
	        			var datos=panelConceptosAutorizados.form.getValues();
	        			var rec = new modelConceptoAutorizado({
	        					medico: datos.idmedicoConAutorizado,
	        					cpt: datos.cptConAutorizado,
	        					descripcion: datos.desConAutorizado,
	        					precio: datos.precioConAutorizado,
	        					cantidad: datos.cantidadConAutorizado,
	        					importe: datos.importeConAutorizado            	
	    				});
	        			storeConceptoAutorizados.add(rec);
	        			panelConceptosAutorizados.getForm().reset();
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
				panelConceptosAutorizados.getForm().reset();
				ventanaConceptosAutorizado.close();
			}
		}
	]
	});
	
	//DATOS PARA EL SEGUDO GRID --> EQUIPO QUIRURGICO BASE
	
	ventanaEqQuirurgicoBase= Ext.create('Ext.window.Window', {
		renderTo: document.body,
		title: 'Equipo quir&uacute;rgico base',
		height: 270,
		closeAction: 'hide',
		items:[panelEquipoQuirurgicoBase],		
		buttons:[{
			text: 'Aceptar',
			icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
			handler: function() {
				if (panelEquipoQuirurgicoBase.form.isValid()){
					var datos=panelEquipoQuirurgicoBase.form.getValues();
					var rec = new equipoQuirugicoBase({
						cpt: datos.cptQuirBase,
						descripcion: datos.descripcionQuirurgico,
						precio: datos.precioQuirurgico,
						porcentaje: datos.porcentajeQuirurgico,
						importe: datos.importeQuirurgico
					});
					storeQuirugicoBase.add(rec);
					panelEquipoQuirurgicoBase.getForm().reset();
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
				panelEquipoQuirurgicoBase.getForm().reset();
				ventanaEqQuirurgicoBase.close();
			}
		}
	]
	});
	
	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
	
	ventanaEqQuirurgico= Ext.create('Ext.window.Window', {
		renderTo: document.body,
		title: 'Equipo quir&uacute;rgico',
		height: 270,
		closeAction: 'hide',
		items:[panelEquipoQuirurgico],		
		buttons:[{
			text: 'Aceptar',
			icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
			handler: function() {
				if (panelEquipoQuirurgico.form.isValid()){
					var datos=panelEquipoQuirurgico.form.getValues();
					var rec = new equipoQuirurgico({
						medico: datos.idmedicoEqQuirurg,
						porcentaje: datos.porcentajeEqQuirurg,
						importe: datos.importeEqQuirurg
					});
					storeQuirurgico.add(rec);
					panelEquipoQuirurgico.getForm().reset();
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
				panelEquipoQuirurgico.getForm().reset();
				ventanaEqQuirurgico.close();
			}
		}
	]
	});
	
	//////////////////////////////////////////////////////////
	////////// EDITOR PARA MANIPULACION DE LAS TABLAS  ///////
	//////////////////////////////////////////////////////////
	
	//DATOS PARA EL PRIMER GRID --> CONCEPTOS AUTORIZADOS
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
       title: 'Conceptos Autorizados',
       frame: false,
       initComponent: function(){
    	   this.cellEditing = new Ext.grid.plugin.CellEditing({
    		   clicksToEdit: 1
    	   });
    	   Ext.apply(this, {
    		   height: 200,
    		   plugins: [this.cellEditing],
    		   store: storeConceptoAutorizados,
    		   columns: 
    			   [
	    			    {	
	    			    	header: 'M&eacute;dico',			dataIndex: 'medico',			width:250
	    			    },
	    			    {
	    			    	header: 'CPT',						dataIndex: 'cpt',				width:150				 		
	    			    },
	    			    {
	    			    	header: 'Descripci&oacute;n',		dataIndex: 'descripcion',	 	width:250	
	    			    },
	    			    {
	    			    	header: 'Precio',					dataIndex: 'precio',			width:150,				renderer: Ext.util.Format.usMoney
	    			    },
	    			    {
	    			    	header: 'Cantidad', 				dataIndex: 'cantidad',		 	width:150
	    			    },
	    			    {
	    			    	header: 'Importe', 					dataIndex: 'importe',		 	width:150,				renderer: Ext.util.Format.usMoney
	    			    },
	    			    {
	    			    	xtype: 'actioncolumn',				width: 30,					 	sortable: false,	 	menuDisabled: true,
	    			    	items: [{
	    			    		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
	    			    		tooltip: 'Quitar conceptos autorizados',
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
    			    	text: 'Agregar conceptos autorizados',
    			    	scope: this,
    			    	handler: this.onAddClick
    			    }]
    	   });
    	   this.callParent();
	   },
	   onAddClick: function(btn, e){
		   ventanaConceptosAutorizado.animateTarget=btn;
		   ventanaConceptosAutorizado.show();
	   },
	   onRemoveClick: function(grid, rowIndex){
		   var record=this.getStore().getAt(rowIndex);
		   this.getStore().removeAt(rowIndex);
	   }
	});
	
	gridIncisos=new EditorIncisos();
		
		
	
	//DATOS PARA EL SEGUDO GRID --> EQUIPO QUIRURGICO BASE
	Ext.define('EditorIncisos2', {
		extend: 'Ext.grid.Panel',
		requires: [
		           'Ext.selection.CellModel',
		           'Ext.grid.*',
		           'Ext.data.*',
		           'Ext.util.*',
		           'Ext.form.*'
		           ],
       xtype: 'cell-editing',
       title: 'Equipo quir&uacute;rgico base',
       frame: false,
       initComponent: function(){
    	   this.cellEditing = new Ext.grid.plugin.CellEditing({
    		   clicksToEdit: 1
    	   });
	
	   Ext.apply(this, {
		   height: 200,
		   plugins: [this.cellEditing],
		   store: storeQuirugicoBase,
		   columns: 
			   [				 	
			    	{
			    		header: 'CPT',						dataIndex: 'cpt',				width:150				 		
			    	},
			    	{
			    		header: 'Descripci&oacute;n',		dataIndex: 'descripcion',	 	width:250	
			    	},
			    	{
			    		header: 'Precio',					dataIndex: 'precio',			width:150,				renderer: Ext.util.Format.usMoney
			    	},
			    	{
			    		header: 'Porcentaje', 				dataIndex: 'porcentaje',		width:150
			    	},
			    	{
			    		header: 'Importe', 					dataIndex: 'importe',		 	width:150,				renderer: Ext.util.Format.usMoney
			    	},
			    	{
			    		xtype: 'actioncolumn',				width: 30,						sortable: false,		menuDisabled: true,
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
	    		text: 'Agregar equipo quir&uacute;gico',
	    		scope: this,
	    		handler: this.onAddClick
	    	}]
	   });
	   this.callParent();
       },
	
       onAddClick: function(btn, e){
    	   ventanaEqQuirurgicoBase.animateTarget=btn;
    	   ventanaEqQuirurgicoBase.show();
       },
       onRemoveClick: function(grid, rowIndex){
    	   var record=this.getStore().getAt(rowIndex);
    	   this.getStore().removeAt(rowIndex);
       }
});

	
	gridIncisos2=new EditorIncisos2();
	
	
	//DATOS PARA EL TERCER GRID --> EQUIPO QUIRURGICO
	Ext.define('EditorIncisos3', {
		extend: 'Ext.grid.Panel',
		requires: [
		           'Ext.selection.CellModel',
		           'Ext.grid.*',
		           'Ext.data.*',
		           'Ext.util.*',
		           'Ext.form.*'
		           ],
       xtype: 'cell-editing',
       title: 'Equipo quir&uacute;rgico',
       frame: false,
       initComponent: function(){
    	   this.cellEditing = new Ext.grid.plugin.CellEditing({
    		   clicksToEdit: 1
    	   });
	
	   Ext.apply(this, {
		   height: 200,
		   plugins: [this.cellEditing],
		   store: storeQuirurgico,
		   columns: 
			   [				 	
			    	{
			    		header: 'M&eacute;dico',		dataIndex: 'medico',	 		width:250	
			    	},
			    	{
			    		header: 'Porcentaje',			dataIndex: 'porcentaje',		width:150				 		
			    	},
			    	{
			    		header: 'Importe',				dataIndex: 'importe',	 		width:250,					renderer: Ext.util.Format.usMoney
			    	},						
			    	{
			    		xtype: 'actioncolumn',				width: 30,						sortable: false,		menuDisabled: true,
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
		    		text: 'Agregar equipo quir&uacute;gico',
		    		scope: this,
		    		handler: this.onAddClick
		    	}]
	   });
	   this.callParent();
       },
	
       onAddClick: function(btn, e){
    	   ventanaEqQuirurgico.animateTarget=btn;
    	   ventanaEqQuirurgico.show();
       },
       onRemoveClick: function(grid, rowIndex){
    	   var record=this.getStore().getAt(rowIndex);
    	   this.getStore().removeAt(rowIndex);
       }
	});

	gridIncisos3=new EditorIncisos3();

	
	
	
	
	
/*#####################################################################################################################################################
###################################################### 		 DATOS PARA LA BUSQUEDA INICIAL 	#######################################################
#######################################################################################################################################################*/
	
	codigoAsegurado= Ext.create('Ext.form.ComboBox',
	{
		//colspan:2,
		id:'tipoAutorizacion',				fieldLabel: 'Tipo autorizaci&oacute;n',		store: storeTipoAutorizacion,
		queryMode:'local',					displayField: 'value',						valueField: 'key',					allowBlank:false,
		blankText:'Es un dato requerido',	editable:false,								labelWidth : 170,
		width: 400,							emptyText:'Seleccione Autorizaci&oacute;n ...',
		listeners : {
			'select' : function(combo, record) {
					closedStatusSelectedID = this.getValue();
					if(closedStatusSelectedID !=1){
						Ext.getCmp('panelbusqueda').show();
						Ext.getCmp('clausulasGridId').show();						
					}else{
						Ext.getCmp('panelbusqueda').hide();
						Ext.getCmp('clausulasGridId').hide();
					}    					
				}
			}
	});
	
	
	panelbusquedas= Ext.create('Ext.panel.Panel',
	{
		border  : 0,		id     : 'panelbusqueda',				width	: 600,		style  : 'margin:5px'
		,items :
			[
			 Ext.create('Ext.form.field.ComboBox',
			 {
				 fieldLabel 	: 'Nombre/C&oacute;digo asegurado',			allowBlank		: false,		displayField 	: 'value'
				 ,id			: 'idCodigoAsegurado',						labelWidth		: 170,			width		 	: 500
				 ,valueField   	: 'key',									forceSelection 	: false,		matchFieldWidth	: false
				 ,minChars  	: 3,										queryMode 		:'remote'		,queryParam: 'params.cdperson'
				 ,store : storeAsegurados
				 ,triggerAction: 'all'
			 })
			 ]
		,buttonAlign: 'center'
		,buttons : [{
			text: "Buscar"
			,icon:_CONTEXT+'/resources/fam3icons/icons/magnifier.png'
			,handler: function(){
				if (panelClausula.form.isValid()) {
					storeListadoAsegurado.removeAll();
					var params = {
							'params.cdperson' : Ext.getCmp('idCodigoAsegurado').getValue(),
							'params.tipoAut' : Ext.getCmp('tipoAutorizacion').getValue()
					};
					
					storeSiniestros.load();// --> Verificar que no se carga el valor al momento de pasarle el valor para que se visualice correctamente
					storeMedico.load();
					storeProveedor.load();
					storeSubcobertura.load();
					
					storeTiposICD.load();
					
					cargaStorePaginadoLocal(storeListadoAsegurado, _URL_CONSULTA_LISTADO_AUTORIZACION, 'listaAutorizacion', params, function(options, success, response){
						if(success){
							var jsonResponse = Ext.decode(response.responseText);
							if(jsonResponse.listaAutorizacion == null) {								
								Ext.Msg.show({
									title: 'Aviso',
									msg: 'No se encontraron datos.',
									buttons: Ext.Msg.OK,
									icon: Ext.Msg.WARNING
								});
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
				} else {
					Ext.Msg.show({
						title: 'Aviso',
						msg: 'Complete la informaci&oacute;n requerida',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.WARNING
					});
				}
			}	
		}] 
	});
	
	gridDatos= Ext.create('Ext.grid.Panel',
	{
		id             : 'clausulasGridId'
		//,title         : 'Hist&oacute;rico de Reclamaciones'
		,store         :  storeListadoAsegurado
		,collapsible   : true
		,titleCollapse : true
		,style         : 'margin:5px'
		,width   : 600
		,height: 200
		,columns       :
		[
			 {
				 header     : 'N&uacute;mero <br/> Autorizaci&oacute;n'
				 ,dataIndex : 'nmautser'
				 ,width	 	: 100
			 },
			 {
				 header     : 'N&uacute;mero <br/> Autorizaci&oacute;n Anterior'
				 ,dataIndex : 'nmautant'
				 ,width     : 200
			 }
			 ,
			 {
				 header     : 'Fecha Solicitud'
				 ,dataIndex : 'fesolici'
				 ,width     : 100
			 },
			 {
				 header     : 'P&oacute;liza <br/> Afectada'
				 ,dataIndex : 'polizaafectada'
				 ,width	    : 100
			 }
			 ,
			 {
				 header     : 'Clave Proveedor'
				 ,dataIndex : 'cdprovee'
				 ,width	    : 100
			 }
			 ,
			 {
				 header     : 'Nombre del Proveedor'
				 ,dataIndex : 'nombreProveedor'
				 ,width	   : 200
			 }
		 ],
		 bbar     :
		 {
			 displayInfo : true,
			 store       : storeListadoAsegurado,
			 xtype       : 'pagingtoolbar'
		 }
	});
	
	/*OCULTAMOS LA INFORMACION DEL GRID Y DE LA BUSQUEDA*/
	
	Ext.getCmp('panelbusqueda').hide();
	Ext.getCmp('clausulasGridId').hide();
	
	panelClausula= Ext.create('Ext.form.Panel', {
		id: 'panelClausula',
		bodyPadding: 5,
		renderTo: Ext.getBody(),
		defaults 	:
		{	
			style : 'margin:5px;'
		}
		,
		items: [
		        codigoAsegurado 	// <-- contiene los valores de los combo
		        ,panelbusquedas 	// <-- contiene el formulario para la busqueda de acuerdo al codigo del asegurado
		        ,gridDatos 			// <-- Contiene la información de los asegurados
			],
		buttonAlign:'center',
		buttons: [{
			text: "Editar"
			,icon:_CONTEXT+'/resources/fam3icons/icons/application_edit.png'
			,handler: function(){
				idTipoAutorizacion= Ext.getCmp('tipoAutorizacion').getValue();
				if(idTipoAutorizacion !=1)
				{
					if(Ext.getCmp('clausulasGridId').getSelectionModel().hasSelection()){
						var rowSelected = Ext.getCmp('clausulasGridId').getSelectionModel().getSelection()[0];
						var nmautser= rowSelected.get('nmautser');
						Ext.Ajax.request(
								{
									url     : _URL_CONSULTA_AUTORIZACION_ESP
									,params : 
									{
										'params.nmautser'  : nmautser
									}
								,success : function (response)
								{
									var json=Ext.decode(response.responseText).datosAutorizacionEsp;
									
									//Número de autorización
									Ext.getCmp('idNoAutorizacion').setValue(json.nmautser);
									
									//Número de autorizacion Anterior
									Ext.getCmp('idNumeroAnterior').setValue(json.nmautant);
									
									//INFORMACION DEL ASEGURADO
									Ext.getCmp('idAsegurado').setValue(json.cdperson);
										//console.log(json.nombreCliente);
									
									//Fecha Solicitud
									Ext.getCmp('fechaSolicitud').setValue(json.fesolici);
									
									//Fecha Autorización
									Ext.getCmp('fechaAutorizacion').setValue(json.feautori);
									
									//Fecha Afectado
									Ext.getCmp('fechaVencimiento').setValue(json.fevencim);
									
									//Fecha de Ingreso
									Ext.getCmp('fechaIngreso').setValue(json.feingres);
									
									// Póliza Afectada
									Ext.getCmp('polizaAfectada').setValue(json.nmpoliza);									
									
									//Proveedor
									Ext.getCmp('idProveedor').setValue(json.cdprovee);
										//console.log(json.nombreProveedor);
									
									//Médico
									Ext.getCmp('idMedico').setValue(json.cdmedico);
										//console.log(json.nombreMedico);
									
									//Causa Siniestro
									Ext.getCmp('idCausaSiniestro').setValue(json.cdcausa);
										//console.log(json.descCausa);
									
									//Tratamiento
									Ext.getCmp('tratamiento').setValue(json.dstratam);
									
									//Observaciones
									Ext.getCmp('observaciones').setValue(json.dsobserv);
									
									//Nota Interes
									Ext.getCmp('notaInterna').setValue(json.dsnotas);
									
									Ext.getCmp('idCobAfectada').setValue(json.cdgarant);
									
									Ext.getCmp('idUnieco').setValue(json.cdunieco);
									Ext.getCmp('idEstado').setValue(json.estado);
									Ext.getCmp('idcdRamo').setValue(json.cdramo);
									Ext.getCmp('idNmSituac').setValue(json.nmsituac);
									
									
									Ext.getCmp('idSubcobertura').setValue('18HO001');
									
									Ext.getCmp('idComboICD').setValue('A01X');
									
									//CARGO LOS VALORES DE COBERTURA SEGUN LOS DATOS DE ENTRADA
									storeCobertura.load({
					                    params:{
					                    	'params.cdunieco':Ext.getCmp('idUnieco').getValue(),
							            	'params.estado':Ext.getCmp('idEstado').getValue(),
							            	'params.cdramo':Ext.getCmp('idcdRamo').getValue(),
							            	'params.nmpoliza':Ext.getCmp('polizaAfectada').getValue(),
							            	'params.nmsituac':Ext.getCmp('idNmSituac').getValue()
					                    }
									});
									
									storeCobertura.removeAll();
									modificacionClausula.close();
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
					}else {
						//Ext.Msg.alert('Aviso', 'Debe de seleccionar una cl&aacute;usula para realizar la edici&oacute;n');
						Ext.Msg.show({
							title: 'Aviso',
							msg: 'Error al obtener los datos.',
							buttons: Ext.Msg.OK,
							icon: Ext.Msg.ERROR
						});
						
					}
				}
				else
				{
					modificacionClausula.close();
					Ext.getCmp('idNumeroAnterior').hide();
					Ext.getCmp('btnBuscar').hide();
				}
			}
		}
	]
});
	
modificacionClausula = Ext.create('Ext.window.Window',
{
	title        : 'Modo de Autorizaci&oacute;n'
	,modal       : true
	,buttonAlign : 'center'
	,closable : false
	,width		 : 650
	,minHeight 	 : 100 
	,maxheight      : 400
	,items       :
		[
		 	panelClausula
		 ]
}).show();

/*#####################################################################################################################################################
#######################################################################################################################################################
#######################################################################################################################################################*/
	
	
	
	
	
/*################################################################################################################################################
###################################################### 			 PANTALLA 	PRINCIPAL 		 #####################################################
##################################################################################################################################################*/
	Ext.create('Ext.form.Panel',
	{
		border    : 0
		,title: 'Autorizaci&oacuten de Servicios'
		,renderTo : 'div_clau'
		,bodyPadding: 5
		,width: 750
		,layout     :
		{
			type     : 'table'
			,columns : 2
		}
		,defaults 	:
		{
			style : 'margin:5px;'
		}
		,
		items    	:
			[
			 	//se agrupa el No. de autorización , el No. de autorización anterior y el botón de buscar
			 	{
	 				 xtype       : 'textfield',			fieldLabel : 'Unieco'				,id       : 'idUnieco'
					 ,allowBlank : false,				labelWidth: 170
	 			},
	 			{
	 				 xtype       : 'textfield',			fieldLabel : 'estado'				,id       : 'idEstado'
					 ,allowBlank : false,				labelWidth: 170
	 			},
	 			{
	 				 xtype       : 'textfield',			fieldLabel : 'Ramo'				,id       : 'idcdRamo'
					 ,allowBlank : false,				labelWidth: 170
	 			},	 			
	 			{
	 				 xtype       : 'textfield',			fieldLabel : 'nmsituac'				,id       : 'idNmSituac'
					 ,allowBlank : false,				labelWidth: 170
	 			},
			 	
			 	{
			 		colspan:2
			 		,border: false		
			 		,layout     :
			 		{
			 			type     : 'table'
		 				,columns : 3
			 		},				        
			 		items    :
			 			[
			 			 	{
		 			 			xtype       : 'textfield',			fieldLabel : 'No. de autorizaci&oacute;n',				id  : 'idNoAutorizacion',
		 			 			labelWidth	: 170,					readOnly   : true
			 			 	},
			 			 	{
			 			 		xtype       : 'textfield'		    ,fieldLabel : 'No. de autorizaci&oacute;n anterior',	id	: 'idNumeroAnterior',	
			 			 		labelWidth	: 170
			 			 	}
			 			 	,
			 			 	Ext.create('Ext.Button',{
			 			 			text: 'Buscar',
			 			 			id:'btnBuscar',
			 			 			icon : _CONTEXT + '/resources/fam3icons/icons/folder.png'
			 			 			//falta la implementacion del boton, para cuando la busqueda se encuentre
			 			 	})
		 			 	]
			 	},
			 	medico
			 	,
			 	asegurado
			 	,
			 	{
			 		id: 'fechaSolicitud'					,xtype		: 'datefield'								,fieldLabel	: 'Fecha Solicitud',
			 		labelWidth : 170						,id 		: 'fechaSolicitud'							,format		: 'd/m/Y',
			 		editable: true							,value		: new Date()
			 	},
			 	{
			 		id: 'fechaAutorizacion'					,xtype		: 'datefield'								,fieldLabel	: 'Fecha Autorizaci&oacute;n',
			 		labelWidth : 170						,format		: 'd/m/Y',
			 		editable: true,							//,value		: new Date()
			 		listeners:{
			 			change:function(field,value)
			 			{
			 				try
			 				{
			 					Ext.getCmp('fechaVencimiento').setValue(Ext.Date.add(value, Ext.Date.DAY, 15));
			 				}catch(e){}
			 			}
			 		}
			 	},
			 	{
			 		id: 'fechaVencimiento'					,xtype		: 'datefield'								,fieldLabel	: 'Fecha de vencimiento',
			 		labelWidth : 170						,format		: 'd/m/Y',
			 		editable: true							//,value		: new Date()
			 	},
			 	{
			 		id: 'fechaIngreso'						,xtype		: 'datefield'								,fieldLabel	: 'Fecha de Ingreso',
			 		labelWidth : 170						,format		: 'd/m/Y',
			 		editable: true							//,value		: new Date()
			 	},
			 	{
			 		colspan:2
			 		,border: false
			 		,layout     :
			 		{
			 			type     : 'table'
		 				,columns : 3	
			 		},
			 		items    :
			 			[
				 			 {
				 				 xtype       : 'textfield',			fieldLabel : 'P&oacute;liza afectada'				,id       : 'polizaAfectada'
			 					 ,allowBlank : false,				labelWidth: 170
				 			 }
				 			 ,
				 			 Ext.create('Ext.Button', {
				 				 text: 'Ver coberturas',
				 				 icon : _CONTEXT + '/resources/fam3icons/icons/application_view_list.png',
				 				handler: function() {
				 					 Ext.create('Ext.window.Window',
		 							 {
			 						 	title        : 'COBERTURA'
	 						 			//,modal       : true
		 						 		,buttonAlign : 'center'
	 						 			,width       : 800
	 						 			,height      : 400
	 						 			,autoScroll  : true
	 						 			,loader      :
	 						 			{
	 						 				url       : _VER_COBERTURAS
	 						 				,scripts  : true
	 						 				,autoLoad : true
	 						 			},
		 							 }).show();
				 				 }
				 			 })
				 			 ,
				 			 Ext.create('Ext.Button', {
				 				 text: 'Ver historial de reclamaciones',
				 				 icon : _CONTEXT + '/resources/fam3icons/icons/clock.png',
				 				 handler: function() {
				 					 Ext.create('Ext.window.Window',
		 							 {
			 						 	title        : 'Historial de reclamaciones'
	 						 			//,modal       : true
		 						 		,buttonAlign : 'center'
	 						 			,width       : 800
	 						 			,height      : 400
	 						 			,autoScroll  : true
	 						 			,loader      :
	 						 			{
	 						 				url       : _HISTORIAL_RECLAMACIONES									            		
	 						 				,scripts  : true
	 						 				,autoLoad : true
	 						 			},
		 							 }).show();
				 				 }
				 			 })							
			 			 ]
			 	},
			 	sucursal,
			 	coberturaAfectada,
			 	subCobertura,
			 	proveedor,
			 	medico,
			 	{
			 		xtype       : 'textfield'				,fieldLabel : 'Especialidad'				,id       : 'especialidad'
		 			,allowBlank : false						,labelWidth: 170
			 	},
			 	{
			 		xtype       : 'textfield'				,fieldLabel : 'Deducible'					,id       : 'deducible'
		 			,allowBlank : false						,labelWidth: 170
			 	},
			 	{
			 		xtype       : 'textfield'				,fieldLabel : 'Copago'						,id       : 'copago'
		 			,allowBlank : false						,labelWidth: 170
			 	},
			 	penalizacion
			 	,
			 	comboICD
			 	,
			 	{
			 		colspan:2,			xtype : 'numberfield',              id:'sumDisponible',           fieldLabel: 'Suma disponible proveedor',
			 		labelWidth: 170,    allowBlank: false,	                allowDecimals :true,          decimalSeparator :'.',                 allowBlank:false
			 	}
			 	,
			 	causaSiniestro
			 	,
			 	{
			 		colspan:2								,xtype       : 'textareafield'				,fieldLabel : 'Tr&aacute;tamiento'			,id       : 'tratamiento'
		 			,allowBlank : false                     ,labelWidth	 : 170							,width      : 700							,height		: 70
			 	},
			 	{
			 		colspan:2								,xtype       : 'textareafield'				,fieldLabel : 'Observaciones'				,id       : 'observaciones'
		 			,allowBlank : false                     ,labelWidth	 : 170							,width      : 700							,height		: 70
			 	},
			 	{
			 		colspan:2								,xtype       : 'textareafield'				,fieldLabel : 'Notas internas'				,id       : 'notaInterna'
		 			,allowBlank : false						,labelWidth: 170							,width      : 700							,height: 70
			 	},
			 	{
			 		colspan:2,
			 		items    :
			 			[
			 			 gridIncisos
			 			 ]
			 	},
			 	{
			 		colspan:2,
			 		items    :
			 			[
			 			 gridIncisos2
			 			 ]
			 	},
			 	{
			 		colspan:2,
			 		items    :
			 			[
			 			 gridIncisos3
			 			 ]
			 	}
		 	],
		 	buttonAlign:'center',
		 	buttons: [{
			 		text:'Guardar',
			 		icon:_CONTEXT+'/resources/fam3icons/icons/disk.png',
			 		id:'botonLimpiar',
			 		handler:function()
			 		{}
			 	},
			 	{
			 		text:'Autorizar',
			 		icon:_CONTEXT+'/resources/fam3icons/icons/key.png',
			 		id:'Autorizar',
			 		handler:function()
			 		{}
			 	}
			 	,
			 	{
			 		text:'Rechazar',
			 		icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
			 		id:'Rechazar',
			 		handler:function()
			 		{}
			 	}
		 	]		
	});
});