<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">

var _CONTEXT = '${ctx}';

//Catalogo Tipos de pago a utilizar:
var _PAGO_DIRECTO = '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@DIRECTO.codigo" />';
var _REEMBOLSO    = '<s:property value="@mx.com.gseguros.portal.general.util.TipoPago@REEMBOLSO.codigo" />';


var _URL_LoadFacturas =  '<s:url namespace="/siniestros" action="loadListaFacturasTramite" />';
var _URL_GuardaFactura =  '<s:url namespace="/siniestros" action="guardaFacturaTramite" />';
var _URL_ActualizaFactura =  '<s:url namespace="/siniestros" action="actualizaFacturaTramite" />';
var _URL_EliminaFactura =  '<s:url namespace="/siniestros" action="eliminaFacturaTramite" />';

var _URL_LoadConceptos =  '<s:url namespace="/siniestros" action="obtenerMsinival" />';
var _URL_GuardaConcepto =  '<s:url namespace="/siniestros" action="guardarMsinival" />';

var _UrlAjustesMedicos =  '<s:url namespace="/siniestros" action="includes/ajustesMedicos" />';

var _URL_CATALOGOS = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
var _CATALOGO_TipoAtencion = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_ATENCION_SINIESTROS"/>';
var _CATALOGO_PROVEEDORES  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@PROVEEDORES"/>';
var _CATALOGO_COBERTURAS  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@COBERTURAS"/>';
var _CATALOGO_SUBCOBERTURAS  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@SUBCOBERTURAS"/>';

var _CATALOGO_TipoConcepto  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_CONCEPTO_SINIESTROS"/>';
var _CATALOGO_ConceptosMedicos  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@CODIGOS_MEDICOS"/>';
var _CATALOGO_TipoMoneda   = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_MONEDA"/>';

var _Operacion;
var _Nmordina;

//jtezva
var _revAdm_urlMesaControl = '<s:url namespace="/mesacontrol" action="mcdinamica" />';
var _revAdm_itemsRechazo   = [ <s:property value="imap.itemsCancelar" /> ];
    _revAdm_itemsRechazo[2]['width']  = 500;
    _revAdm_itemsRechazo[2]['height'] = 150;
var _revAdm_formRechazo;
var _revAdm_windowRechazo;
var panelEdicionFacturas;

Ext.onReady(function() {
	
	var storeFacturas;
	var storeConceptos;
	
	var gridFacturas;
	var gridConceptos;
	
	var panelPrincipal;
	
	Ext.define('modelFacturas',{
        extend: 'Ext.data.Model',
        fields: [{type:'string',    name:'NFACTURA'},
                 {type:'string',    name:'FFACTURA'},
                 {type:'string',    name:'CDTIPSER'},
                 {type:'string',    name:'DESCRIPC'},
                 {type:'string',    name:'CDPRESTA'},
                 {type:'string',    name:'PTIMPORT'},
                 {type:'string',    name:'CDGARANT'},
                 {type:'string',    name:'DSGARANT'},
                 {type:'string',    name:'DESCPORC'},
                 {type:'string',    name:'DESCNUME'},
                 {type:'string',    name:'CDCONVAL'},
                 {type:'string',    name:'COPAGO'},
                 {type:'string',    name:'NOM_PRESTA'},
                 {type:'string',    name:'DSSUBGAR'},
                 {type:'string',    name:'AUTRECLA'},
                 {type:'string',    name:'AUTMEDIC'},
                 {type:'string',    name:'COMMENME'},
                 {type:'string',    name:'DEDUCIBLE'},
                 {type:'string',    name:'COMMENAR'},
                 {type:'string',    name:'CDMONEDA'},
                 {type:'string',    name:'DESTIPOMONEDA'},
                 {type:'string',    name:'TASACAMB'},
                 {type:'string',    name:'PTIMPORTA'},
                 {type:'string',    name:'DCTONUEX'}
				]
    });
	
	Ext.define('modelConceptos',{
        extend: 'Ext.data.Model',
        fields: ["CDUNIECO","CDRAMO","ESTADO","NMPOLIZA","NMSUPLEM",
         		"NMSITUAC","AAAPERTU","STATUS","NMSINIES","NFACTURA",
        		"CDGARANT","CDCONVAL","CDCONCEP","IDCONCEP","CDCAPITA",
        		"DSGARANT","DSSUBGAR","DESIDCONCEP","DESCONCEP","TOTAJUSMED","SUBTAJUSTADO",
        		"NMORDINA",{name:"FEMOVIMI", type: "date", dateFormat: "d/m/Y"},"CDMONEDA","PTPRECIO","CANTIDAD",
        		"DESTOPOR","DESTOIMP","PTIMPORT","PTRECOBR","NMANNO",
        		"NMAPUNTE","USERREGI",{name:"FEREGIST", type: "date", dateFormat: "d/m/Y"},"PTPCIOEX","DCTOIMEX","PTIMPOEX"]
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
	
	//jtezva
	Ext.define('_revAdm_FormRechazo',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_revAdm_FormRechazo initComponent');
            Ext.apply(this,
            {
                border  : 0
                ,items  : _revAdm_itemsRechazo
            });
            this.callParent();
        }
    });
	
	//jtezva
	Ext.define('_revAdm_WindowRechazo',
    {
        extend         : 'Ext.window.Window'
        ,initComponent : function()
        {
            debug('_revAdm_WindowRechazo initComponent');
            Ext.apply(this,
            {
                title        : 'Rechazo de tr&aacute;mite'
                ,width       : 600
                ,height      : 350
                ,autoScroll  : true
                ,closeAction : 'hide'
                ,modal       : true
                ,defaults    : { style : 'margin : 5px; ' }
                ,items       : _revAdm_formRechazo
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Rechazar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
                        ,handler : _revAdm_rechazoSiniestro
                    }
                ]
            });
            this.callParent();
        }
    });
	
	storeFacturas=new Ext.data.Store(
	{
	    autoDestroy: true,
	    model: 'modelFacturas',
	    proxy: {
            type: 'ajax',
            url: _URL_LoadFacturas,
            reader: {
                type: 'json',
                root: 'loadList'
            }
        }
	});
	storeFacturas.load({
    	params: {
    		'params.ntramite' : _NTRAMITE,
    		'params.cdunieco'  : _CDUNIECO,
    		'params.cdramo'    : _CDRAMO,
    		'params.estado'    : _ESTADO,
    		'params.nmpoliza'  : _NMPOLIZA,
    		'params.nmsituac'  : _NMSITUAC,
    		'params.nmsuplem'  : _NMSUPLEM,
    		'params.status'    : _STATUS,
    		'params.aaapertu'  : _AAAPERTU,
    		'params.nmsinies'  : _NMSINIES
    	}
    });

	storeConceptos=new Ext.data.Store(
			{
			    autoDestroy: true,
			    model: 'modelConceptos',
			    proxy: {
		            type: 'ajax',
		            url: _URL_LoadConceptos,
		            reader: {
		                type: 'json',
		                root: 'loadList'
		            }
		        }
			});
	
	var storeTipoAtencion = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoAtencion},//Cat.TipoAtencionSiniestros
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoAtencion.load();
	
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
	storeProveedor.load();

	var storeCoberturas = Ext.create('Ext.data.Store',{
        model: 'Generic',
        autoLoad: true,
        proxy: {
            type: 'ajax',
            url: _URL_CATALOGOS,
            reader: {
                type: 'json',
                root: 'lista'
            },
            extraParams: {
                'catalogo' : _CATALOGO_COBERTURAS,
                'params.cdramo' : _CDRAMO,
                'params.cdtipsit' : 'SL'
            }
        }
	});
	storeCoberturas.load();
	
	var storeTipoMoneda = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoMoneda},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
    storeTipoMoneda.load();
    
	var storeSubcoberturas = Ext.create('Ext.data.Store',{
        model: 'Generic',
        autoLoad: false,
        proxy: {
            type: 'ajax',
            url: _URL_CATALOGOS,
            reader: {
                type: 'json',
                root: 'lista'
            },
            extraParams: {
                catalogo: _CATALOGO_SUBCOBERTURAS
            }
        }
	});

	var storeSubcoberturasC = Ext.create('Ext.data.Store',{
        model: 'Generic',
        autoLoad: false,
        proxy: {
            type: 'ajax',
            url: _URL_CATALOGOS,
            reader: {
                type: 'json',
                root: 'lista'
            },
            extraParams: {
                catalogo: _CATALOGO_SUBCOBERTURAS
            }
        }
	});
	
	var storeTipoConcepto = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoConcepto},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoConcepto.load();
	
	var storeConceptosCatalogo = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_ConceptosMedicos},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	
	
	panelEdicionFacturas= Ext.create('Ext.form.Panel',{
        border  : 0,
        width: 400,
        url: _URL_GuardaFactura
        ,bodyStyle:'padding:5px;'
        ,items :
        [   
             {
		        xtype      : 'textfield'
		    	,fieldLabel : 'No. Factura'
	    		,allowBlank:false
		    	,name       : 'params.nfactura'
		    	,labelWidth: 170
    		},            
    		{
    	        name: 'params.fefactura',
    	        fieldLabel: 'Fecha Factura',
    	        xtype: 'datefield',
    	        format: 'd/m/Y',
    	        editable: true,
    	        allowBlank:false,
    	        value:new Date(),
    	        labelWidth: 170
    	    },{
            	xtype: 'combo',
                name:'params.cdtipser',
                valueField: 'key',
                displayField: 'value',
                fieldLabel: 'Tipo de servicio',
                store: storeTipoAtencion,
                queryMode:'local',
                allowBlank:false,
                labelWidth: 170,
                editable:false
            },{
            	xtype       : 'combo',
            	name        : 'params.cdpresta',
            	fieldLabel  : 'Proveedor',
            	displayField: 'nombre',
            	valueField  : 'cdpresta',
            	allowBlank  : false,
                forceSelection : true,
                matchFieldWidth: false,
                queryMode   :'remote',
                queryParam  : 'params.cdpresta',
                store       : storeProveedor,
                triggerAction  : 'all',
                //editable    : false,
                minChars  : 2,
                hideTrigger:true,
                triggerAction: 'all',
                labelWidth: 170
            },{
            	xtype       : 'combo',
            	name        : 'params.cdgarant',
            	fieldLabel  : 'Cobertura',
            	displayField: 'value',
            	valueField  : 'key',
            	allowBlank  : true,
                forceSelection : true,
                matchFieldWidth: false,
                queryMode   :'remote',
                labelWidth: 170,
                store       : storeCoberturas,
                triggerAction  : 'all',
                listeners: {
                	select: function (combo, records, opts){
                		var cdGarant =  records[0].get('key');
                		storeSubcoberturas.load({
                			params: {
                				'params.cdgarant' : cdGarant
                			}
                		});
                	}
                },
                editable    : false
            },{
            	xtype       : 'combo',
            	name        : 'params.cdconval',
            	fieldLabel  : 'Sub Cobertura',
            	displayField: 'value',
            	valueField  : 'key',
            	allowBlank  : true,
                forceSelection : true,
                matchFieldWidth: false,
                queryMode   :'local',
                store       : storeSubcoberturas,
                triggerAction  : 'all',
                labelWidth: 170,
                editable    : false
            },{
            	xtype       : 'combo',
            	name        : 'params.tipoMoneda',
            	fieldLabel  : 'Tipo moneda',
            	displayField: 'value',
            	valueField  : 'key',
            	allowBlank  : true,
                forceSelection : true,
                matchFieldWidth: false,
                queryMode   :'local',
                labelWidth: 170,
                store       : storeTipoMoneda,
                triggerAction  : 'all',
                editable    : false,
                listeners : {
    				change:function(e){
    					if(e.getValue()!='001')
						{
    						panelEdicionFacturas.query('numberfield[name=params.ptimport]')[0].show();
    						panelEdicionFacturas.query('numberfield[name=params.ptimporta]')[0].show();
    						panelEdicionFacturas.query('numberfield[name=params.tasacamb]')[0].show();
    						panelEdicionFacturas.query('numberfield[name=params.dctonuex]')[0].show();
						
						}else{
							panelEdicionFacturas.query('numberfield[name=params.ptimport]')[0].show();
							panelEdicionFacturas.query('numberfield[name=params.ptimporta]')[0].hide();
    						panelEdicionFacturas.query('numberfield[name=params.tasacamb]')[0].hide();
    						panelEdicionFacturas.query('numberfield[name=params.dctonuex]')[0].hide();
						}
    	    		}
    	        }
                
            },{
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Importe'
                ,allowBlank:false
                ,allowDecimals: true
                ,decimalSeparator: '.'
                ,minValue: 0
                ,labelWidth: 170
		    	,name       : 'params.ptimport'
    		},{
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Importe moneda extranjera'
	    		,labelWidth: 170
                ,allowBlank:false
                ,allowDecimals: true
                ,decimalSeparator: '.'
                ,minValue: 0
		    	,name       : 'params.ptimporta'
		    	,listeners : {
    				change:function(e){
    					panelEdicionFacturas.down('[name="params.ptimport"]').setValue(+panelEdicionFacturas.down('[name="params.tasacamb"]').getValue() * +panelEdicionFacturas.down('[name="params.ptimporta"]').getValue() );
    	    		}
    	        }
    		},{
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Tasa Cambio'
                ,allowBlank:false
                ,allowDecimals: true
                ,decimalSeparator: '.'
               	,labelWidth: 170
                ,minValue: 0
		    	,name       : 'params.tasacamb'
		    	,listeners : {
    				change:function(e){
    					var cantidad = e.getValue();
    					if(+ cantidad > 0)
   						{
    						panelEdicionFacturas.down('[name="params.ptimport"]').setValue(+panelEdicionFacturas.down('[name="params.tasacamb"]').getValue() * +panelEdicionFacturas.down('[name="params.ptimporta"]').getValue() );
    				 		panelEdicionFacturas.down('[name="params.descnume"]').setValue(+panelEdicionFacturas.down('[name="params.tasacamb"]').getValue() * +panelEdicionFacturas.down('[name="params.dctonuex"]').getValue() );
   						}else{
   							panelEdicionFacturas.down('[name="params.ptimport"]').setValue('0');
   					 		panelEdicionFacturas.down('[name="params.descnume"]').setValue('0');
   						}
    	    		}
    	        }
    		},{
		        	xtype      : 'numberfield'
			    	,fieldLabel : 'Descuento %'
	                ,allowBlank:false
	                ,allowDecimals: true
	                ,decimalSeparator: '.'
	                ,minValue: 0
	                ,labelWidth: 170
			    	,name       : 'params.descporc'
	    	},{
		        xtype      : 'numberfield'
			    	,fieldLabel : 'Descuento Importe'
	                ,allowBlank:false
	                ,allowDecimals: true
	                ,decimalSeparator: '.'
	                ,minValue: 0
	                ,labelWidth: 170
			    	,name       : 'params.descnume'
	    	},{
		        xtype      : 'numberfield'
			    	,fieldLabel : 'Descuento Importe Ext'
	                ,allowBlank:false
	                ,allowDecimals: true
	                ,decimalSeparator: '.'
	                ,minValue: 0
	                ,labelWidth: 170
			    	,name       : 'params.dctonuex'
		    		,listeners : {
	    				change:function(e){
	    					panelEdicionFacturas.down('[name="params.descnume"]').setValue(+panelEdicionFacturas.down('[name="params.tasacamb"]').getValue() * +panelEdicionFacturas.down('[name="params.dctonuex"]').getValue() );
	    	    		}
	    	        }
	    	}
	    	
	    	<s:property value='%{"," + imap.itemsEdicion}' />
	    	
        ]
    });
	for(var i=13;i<panelEdicionFacturas.items.items.length;i++)
	{
		panelEdicionFacturas.items.items[i].labelWidth=166;
	}
	
    var panelEdicionConceptos = Ext.create('Ext.form.Panel',{
        border  : 0
        ,bodyStyle:'padding:5px;'
        ,url: _URL_GuardaConcepto
        ,items :
        [   
            {
        	xtype       : 'combo',
        	name        : 'params.cdconval',
        	fieldLabel  : 'Sub Cobertura',
        	labelWidth: 150,
        	displayField: 'value',
        	valueField  : 'key',
        	allowBlank  : false,
            forceSelection : true,
            matchFieldWidth: false,
            queryMode   :'local',
            store       : storeSubcoberturasC,
            triggerAction  : 'all',
            editable    : false
        },{
        	xtype: 'combo',
            name:'params.idconcep',
            labelWidth: 150,
            valueField: 'key',
            displayField: 'value',
            fieldLabel: 'Tipo de Concepto',
            store: storeTipoConcepto,
            queryMode:'local',
            allowBlank:false,
            editable:false,
            listeners:{
            	select: function (combo, records, opts){
            		var cdTipo =  records[0].get('key');
            		storeConceptosCatalogo.proxy.extraParams=
            		{
            			'params.idPadre' : cdTipo
            			,catalogo        : _CATALOGO_ConceptosMedicos
            		};
            		/*storeConceptosCatalogo.load({
            			params: {
            				'params.idPadre' : cdTipo
            			}
            		});*/
            	}
            }
        },{
        	xtype: 'combo',
            name:'params.cdconcep',
            labelWidth: 150,
            valueField: 'key',
            displayField: 'value',
            fieldLabel: 'Concepto',
            store: storeConceptosCatalogo,
            queryMode:'remote',
            allowBlank:false,
            editable:true,
            forceSelection: true
            ,queryParam  : 'params.descripc'
            ,hideTrigger : true
            ,minChars    : 3
        },{
	        xtype      : 'numberfield'
	    	,fieldLabel : 'Tasa Cambio'
            ,allowDecimals: true
            ,decimalSeparator: '.'
            ,minValue: 0
            ,labelWidth: 150
	    	,name       : 'params.tasacamb1'
    		,listeners : {
   				change:function(e){
   					var cantidad = e.getValue();
   					if(+ cantidad > 0)
  						{
   						panelEdicionConceptos.down('[name="params.ptprecio"]').setValue(+panelEdicionConceptos.down('[name="params.tasacamb1"]').getValue() * +panelEdicionConceptos.down('[name="params.ptpcioex"]').getValue() );
   						panelEdicionConceptos.down('[name="params.destoimp"]').setValue(+panelEdicionConceptos.down('[name="params.tasacamb1"]').getValue() * +panelEdicionConceptos.down('[name="params.dctoimex"]').getValue() );
   						panelEdicionConceptos.down('[name="params.ptimport"]').setValue(+panelEdicionConceptos.down('[name="params.tasacamb1"]').getValue() * +panelEdicionConceptos.down('[name="params.ptimpoex"]').getValue() );
  						}else{
  							panelEdicionConceptos.down('[name="params.ptprecio"]').setValue('0');
  							panelEdicionConceptos.down('[name="params.destoimp"]').setValue('0');
  							panelEdicionConceptos.down('[name="params.ptimport"]').setValue('0');
  						}
   	    		}
   	        }
   		},
           {
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Precio'
	    		,labelWidth: 150
	    		,allowBlank:false
	    		,allowDecimals: true
	               ,decimalSeparator: '.'
	               ,minValue: 0
		    	,name       : 'params.ptprecio'
		    	,listeners: {
		    		change: calculaImporteConcepto
		    	}
   		},
   		{
	        xtype      : 'numberfield'
	    	,fieldLabel : 'Precio Ext'
    		,labelWidth: 150
    		,allowBlank:false
    		,allowDecimals: true
               ,decimalSeparator: '.'
               ,minValue: 0
	    	,name       : 'params.ptpcioex'
	    	/*,listeners: {
	    		change: calculaImporteConcepto
	    	}*/
    		,listeners : {
  				change:function(e){
  						panelEdicionConceptos.down('[name="params.ptprecio"]').setValue(+panelEdicionConceptos.down('[name="params.tasacamb1"]').getValue() * +panelEdicionConceptos.down('[name="params.ptpcioex"]').getValue() );
  						calculaImporteConcepto();
  	    		}
   	        }
		}
            ,{
		       		 xtype      : 'numberfield'
			    	,fieldLabel : 'Cantidad'
		    		,labelWidth: 150
		    		,allowBlank:false
	                ,minValue: 0
			    	,name       : 'params.cantidad'
			    	,listeners: {
				    		change: calculaImporteConcepto
				    }
	    	}
	         ,
            {
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Descuento (%)'
	    		,labelWidth: 150
                ,minValue: 0
	    		,allowBlank:false
		    	,name       : 'params.destopor'
		    	,listeners: {
			    		change: calculaImporteConcepto
			    }
    		},
            {
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Descuento Importe'
	    		,labelWidth: 150
	    		,allowBlank:false
	    		,allowDecimals: true
                ,decimalSeparator: '.'
                ,minValue: 0
		    	,name       : 'params.destoimp'
		    	,listeners: {
			    		change: calculaImporteConcepto
			    }
    		},
    		{
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Descuento Importe Ext'
	    		,labelWidth: 150
	    		,allowBlank:false
	    		,allowDecimals: true
                ,decimalSeparator: '.'
                ,minValue: 0
		    	,name       : 'params.dctoimex'
	    		,listeners : {
	  				change:function(e){
	  						panelEdicionConceptos.down('[name="params.destoimp"]').setValue(+panelEdicionConceptos.down('[name="params.tasacamb1"]').getValue() * +panelEdicionConceptos.down('[name="params.dctoimex"]').getValue() );
	  						calculaImporteConcepto();
	  	    		}
	   	        }
    			/*,listeners: {
			    		change: calculaImporteConcepto
			    }*/
    		}
            ,
            {
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Subtotal factura Ext'
	    		,labelWidth: 150
	    		,allowBlank:false
	    		,allowDecimals: true
                ,decimalSeparator: '.'
                ,readOnly: true
                ,minValue: 0
		    	,name       : 'params.ptimpoex'
    		},
            {
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Subtotal factura'
	    		,labelWidth: 150
	    		,allowBlank:false
	    		,allowDecimals: true
                ,decimalSeparator: '.'
                ,readOnly: true
                ,minValue: 0
		    	,name       : 'params.ptimport'
    		}
        ]
    });
    
    function calculaImporteConcepto(){
    	
    	var cantidad = panelEdicionConceptos.down('[name="params.cantidad"]').getValue()*1;
    	
    	var importe = panelEdicionConceptos.down('[name="params.ptprecio"]').getValue()*1;
    	var destopor = panelEdicionConceptos.down('[name="params.destopor"]').getValue()*1;
    	var destoimp = panelEdicionConceptos.down('[name="params.destoimp"]').getValue()*1;
    	panelEdicionConceptos.down('[name="params.ptimport"]').setValue(((cantidad*importe) *(1-(destopor/100)))-destoimp);
    	
    	var importeEx = panelEdicionConceptos.down('[name="params.ptpcioex"]').getValue()*1;
    	var destoporEx = panelEdicionConceptos.down('[name="params.destopor"]').getValue()*1;
    	var destoimpEx = panelEdicionConceptos.down('[name="params.dctoimex"]').getValue()*1;
    	
    	panelEdicionConceptos.down('[name="params.ptimpoex"]').setValue(((cantidad*importeEx) *(1-(destoporEx/100)))-destoimpEx);
    }

    
    //jtezva
    _revAdm_formRechazo   = new _revAdm_FormRechazo();
    _revAdm_windowRechazo = new _revAdm_WindowRechazo();

/*////////////////////////////////////////////////////////////////
////////////////   DECLARACION DE GRID FACTURAS ////////////
///////////////////////////////////////////////////////////////*/
Ext.define('EditorFacturas', {
		extend: 'Ext.grid.Panel',
 		requires: [
	 	'Ext.selection.CellModel',
	 	'Ext.grid.*',
	 	'Ext.data.*',
	 	'Ext.util.*',
	 	'Ext.form.*'
 	],
		selType: 'checkboxmodel',
		title: 'Facturas en Tr&aacute;mite',
		frame: false,
 		initComponent: function(){
 		this.cellEditing = new Ext.grid.plugin.CellEditing({
 		clicksToEdit: 1
 		});

 			Ext.apply(this, {
 			height: 250,
 			plugins: [this.cellEditing],
 			store: storeFacturas,
 			columns: 
 				[{
 					header : 'No. de Factura',
 					dataIndex : 'NFACTURA',
 					width : 150
 				},{
 					header : 'Fecha de Factura',
 					dataIndex : 'FFACTURA',
 					width : 150
 				// , renderer: Ext.util.Format.dateRenderer('d M Y')

 				},{
 					header : 'Cobertura',
 					dataIndex : 'DSGARANT',
 					width : 150
 				},{
 					header : 'Importe',
 					dataIndex : 'PTIMPORT',
 					width : 150,
 					renderer : Ext.util.Format.usMoney
 				},{
 					header : 'Moneda',
 					dataIndex : 'DESTIPOMONEDA',
 					width : 150,
 					hidden: false
 				},{
 					header : 'Tasa cambio',
 					dataIndex : 'TASACAMB',
 					width : 150,
 					hidden: false,
 					renderer : Ext.util.Format.usMoney
 				},{
 					header : 'Deducible',
 					dataIndex : 'DEDUCIBLE',
 					width : 120
 				},{
 					header : 'Copago',
 					dataIndex : 'COPAGO',
 					width : 120
 				} <s:property value='%{"," + imap.gridColumns}' />
 				,{
 					header : 'Moneda',
 					dataIndex : 'CDMONEDA',
 					width : 150,
 					hidden: true
 				},{
 					header : 'Importe Ext',
 					dataIndex : 'PTIMPORTA',
 					width : 150,
 					hidden: true
 				},{
 					header : 'DESC EXT',
 					dataIndex : 'DCTONUEX',
 					width : 150,
 					hidden: true
 				}/*,{
 					xtype : 'actioncolumn',
 					width : 50,
 					sortable : false,
 					menuDisabled : true,
 					items : [{
 						icon : _CONTEXT+'/resources/fam3icons/icons/pencil.png',
 						tooltip : 'Editar Factura',
 						scope : this,
 						handler : this.onEditClick
 					},{
 						icon : _CONTEXT+'/resources/fam3icons/icons/delete.png',
 						tooltip : 'Eliminar Factura',
 						scope : this,
 						handler : this.onRemoveClick
 					}]
 				} */]
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
 		panelEdicionFacturas.getForm().reset();
 		panelEdicionFacturas.down('[name="params.nfactura"]').setReadOnly(false);
 		panelEdicionFacturas.down('[name="params.cdgarant"]').setReadOnly(false);
 		panelEdicionFacturas.down('[name="params.tipoMoneda"]').setValue('001');
 		//xca<<<<
 		//panelEdicionFacturas.down('[name="params.tipoMoneda"]').setValue('001');
 		panelEdicionFacturas.down('[name="params.tasacamb"]').setValue('0');
 		panelEdicionFacturas.down('[name="params.ptimporta"]').setValue('0');
 		panelEdicionFacturas.down('[name="params.ptimport"]').setValue('0');
 		panelEdicionFacturas.down('[name="params.descnume"]').setValue('0');
 		panelEdicionFacturas.down('[name="params.dctonuex"]').setValue('0');
 		panelEdicionFacturas.down('[name="params.descporc"]').setValue('0');
 		
 		
 		_Operacion = 'I';
 		
 		windowFacturas.setTitle('Agregar Factura');
 		windowFacturas.show();
 		centrarVentana(windowFacturas);
 		
 	},
 	onRemoveClick: function(grid, rowIndex){
 		if(Ext.isEmpty(_TIPOPAGO) || _TIPOPAGO == _PAGO_DIRECTO){
 			centrarVentanaInterna(mensajeWarning('No se pueden eliminar Facturas en Pago Directo.'));
 			return;
 		}
 		
 		var record=grid.getStore().getAt(rowIndex);
 		
 		panelEdicionFacturas.getForm().reset();
 		
 		centrarVentana(Ext.Msg.show({
	        title: 'Aviso',
	        msg: '&iquest;Esta seguro que desea eliminar esta factura?',
	        buttons: Ext.Msg.YESNO,
	        icon: Ext.Msg.QUESTION,
	        fn: function(buttonId, text, opt){
	        	if(buttonId == 'yes'){
	        		
	        		gridFacturas.setLoading(true);
	         		
	         		Ext.Ajax.request({
	        			url: _URL_EliminaFactura,
	        			params: {
	        		    		'params.ntramite' : _NTRAMITE,
	        		    		'params.nfactura' : record.get('NFACTURA')
	        			},
	        			success: function(response) {
	        				var res = Ext.decode(response.responseText);
	        				gridFacturas.setLoading(false);
	        				storeFacturas.reload();
	        				if(res.success){
	        					centrarVentanaInterna(mensajeCorrecto('Aviso','Se ha eliminado con &eacute;xito.'));
	    	    				storeFacturas.reload();
	        				}else {
	        					centrarVentanaInterna(mensajeError('No se pudo eliminar.'));	
	        				}
	        			},
	        			failure: function(){
	        				gridFacturas.setLoading(false);
	        				centrarVentanaInterna(mensajeError('No se pudo eliminar.'));
	        			}
	        		});
	        	}
	        	
	        }
	    }));
 		
 		
 	},
 	onEditClick: function(grid, rowIndex){
 		var record=grid.getStore().getAt(rowIndex);
 		
 		if(Ext.isEmpty(_TIPOPAGO) || _TIPOPAGO == _PAGO_DIRECTO){
 			panelEdicionFacturas.query('numberfield[name=params.ptimporta]')[0].hide();
			panelEdicionFacturas.query('numberfield[name=params.tasacamb]')[0].hide();
			panelEdicionFacturas.query('combo[name=params.tipoMoneda]')[0].hide();
			panelEdicionFacturas.query('numberfield[name=params.dctonuex]')[0].hide();
 		}
 		_Operacion = 'U';
 		debug("Editando...");
 		
 		panelEdicionFacturas.getForm().reset();
 		
 		panelEdicionFacturas.down('[name="params.nfactura"]').setReadOnly(true);
 		panelEdicionFacturas.down('[name="params.cdgarant"]').setReadOnly(true);
 		panelEdicionFacturas.down('[name="params.nfactura"]').setValue(record.get('NFACTURA'));
 		panelEdicionFacturas.down('[name="params.fefactura"]').setValue(record.get('FFACTURA'));
 		panelEdicionFacturas.down('[name="params.cdtipser"]').setValue(record.get('CDTIPSER'));
 		panelEdicionFacturas.down('[name="params.cdpresta"]').setValue(record.get('CDPRESTA'));
 		panelEdicionFacturas.down('[name="params.cdgarant"]').setValue(record.get('CDGARANT'));
 		panelEdicionFacturas.down('[name="params.tipoMoneda"]').setValue(record.get('CDMONEDA'));
 		panelEdicionFacturas.down('[name="params.tasacamb"]').setValue(record.get('TASACAMB') == null || record.get('TASACAMB') == ''? "0":record.get('TASACAMB'));
 		panelEdicionFacturas.down('[name="params.ptimporta"]').setValue(record.get('PTIMPORTA') == null || record.get('PTIMPORTA') == ''? "0":record.get('PTIMPORTA'));
 		panelEdicionFacturas.down('[name="params.dctonuex"]').setValue(record.get('DCTONUEX') == null || record.get('DCTONUEX') == ''? "0":record.get('DCTONUEX'));
 		
 		panelPrincipal.setLoading(true);
 		storeSubcoberturas.load({
			params: {
				'params.cdgarant' : record.get('CDGARANT')
			},
			callback: function (){ 
				panelEdicionFacturas.down('[name="params.cdconval"]').setValue(record.get('CDCONVAL'));
				panelPrincipal.setLoading(false);
				
				windowFacturas.setTitle('Editar Factura');
				windowFacturas.show();
		 		centrarVentana(windowFacturas);
			}
		});
 		panelEdicionFacturas.down('[name="params.ptimport"]').setValue(record.get('PTIMPORT') == null || record.get('PTIMPORT') == ''? "0":record.get('PTIMPORT'));
 		panelEdicionFacturas.down('[name="params.descporc"]').setValue(record.get('DESCPORC') == null || record.get('DESCPORC') == ''? "0":record.get('DESCPORC'));
 		panelEdicionFacturas.down('[name="params.descnume"]').setValue(record.get('DESCNUME') == null || record.get('DESCNUME') == ''? "0":record.get('DESCNUME'));

 		if(!Ext.isEmpty(panelEdicionFacturas.down('[name="params.autrecla"]'))){
 			panelEdicionFacturas.down('[name="params.autrecla"]').setValue(record.get('AUTRECLA'));
 		}
 		if(!Ext.isEmpty(panelEdicionFacturas.down('[name="params.commenar"]'))){
 			panelEdicionFacturas.down('[name="params.commenar"]').setValue(record.get('COMMENAR'));
 		}
 		if(!Ext.isEmpty(panelEdicionFacturas.down('[name="params.autmedic"]'))){
 			panelEdicionFacturas.down('[name="params.autmedic"]').setValue(record.get('AUTMEDIC'));
 		}
 		if(!Ext.isEmpty(panelEdicionFacturas.down('[name="params.commenme"]'))){
 			panelEdicionFacturas.down('[name="params.commenme"]').setValue(record.get('COMMENME'));
 		}
 		
 	},
 	listeners: {
 		select: function (grid, record, index, opts){
 			debug(record.get('NFACTURA'));
 			storeConceptos.load({
 				params: {
 		    		'params.nfactura'  : record.get('NFACTURA'),
 		    		'params.cdunieco'  : _CDUNIECO,
 		    		'params.cdramo'    : _CDRAMO,
 		    		'params.estado'    : _ESTADO,
 		    		'params.nmpoliza'  : _NMPOLIZA,
 		    		'params.nmsituac'  : _NMSITUAC,
 		    		'params.nmsuplem'  : _NMSUPLEM,
 		    		'params.status'    : _STATUS,
 		    		'params.aaapertu'  : _AAAPERTU,
 		    		'params.nmsinies'  : _NMSINIES
 		    	}
 			});
 		}
 	}
	});

gridFacturas=new EditorFacturas();


/*////////////////////////////////////////////////////////////////
////////////////   DECLARACION DE GRID CONCEPTOS  ////////////
///////////////////////////////////////////////////////////////*/
Ext.define('EditorConceptos', {
		extend: 'Ext.grid.Panel',
 	requires: [
	 	'Ext.selection.CellModel',
	 	'Ext.grid.*',
	 	'Ext.data.*',
	 	'Ext.util.*',
	 	'Ext.form.*'
 	],
		title: 'Conceptos en Factura',
		frame: false,

 	initComponent: function(){
 		this.cellEditing = new Ext.grid.plugin.CellEditing({
 		clicksToEdit: 1
 		});

 			Ext.apply(this, {
 			height: 250,
 			plugins: [this.cellEditing],
 			store: storeConceptos,
 			columns: 
 				[{
 					dataIndex : 'NMORDINA',
 					width : 20,
 					hidden: true
 				},{
 					header : 'Factura',
 					dataIndex:  'NFACTURA',
 					hidden: true
 				},{
 					header : 'Tipo Concepto',
 					dataIndex : 'DESIDCONCEP',
 					width : 150
 				},{
 					header : 'Codigo Concepto',
 					dataIndex : 'DESCONCEP',
 					width : 150
 				},{
 					header : 'Subcobertura',
 					dataIndex : 'DSSUBGAR',
 					width : 150
 				},{
 					header : 'Precio',
 					dataIndex : 'PTPRECIO',
 					width : 150,
 					renderer : Ext.util.Format.usMoney
 				},{
 					header : 'Cantidad',
 					dataIndex : 'CANTIDAD',
 					width : 150
 				},{
 					header : 'Descuento (%)',
 					dataIndex : 'DESTOPOR',
 					width : 150
 				},{
 					header : 'Descuento ($)',
 					dataIndex : 'DESTOIMP',
 					width : 150
 				},{
 					header : 'Ajuste M&eacute;dico',
 					dataIndex : 'TOTAJUSMED',
 					width : 150,
 					renderer : Ext.util.Format.usMoney
 				},{
 					header : 'Subtotal Factura',
 					dataIndex : 'PTIMPORT',
 					width : 150,
 					renderer : Ext.util.Format.usMoney
 				},{
 					header : 'Subtotal Ajustado',
 					dataIndex : 'SUBTAJUSTADO',
 					width : 150,
 					renderer : Ext.util.Format.usMoney
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
 	}
	});

gridConceptos=new EditorConceptos();


panelPrincipal = Ext.create('Ext.form.Panel',{
	renderTo: 'maindivAdminData',
	border     : false
	,bodyStyle:'padding:5px;'
	,items      : [
        		gridFacturas,
				gridConceptos
	]
	});
	
function _mostrarVentanaAjustes(grid,rowIndex,colIndex){
    var record = grid.getStore().getAt(rowIndex);
    var recordFactura = gridFacturas.getSelectionModel().getSelection()[0];
    debug("Codigo Garantia: "+recordFactura.get('NFACTURA'));
    
    windowLoader = Ext.create('Ext.window.Window',{
        modal       : true,
        buttonAlign : 'center',
        width       : 650,
        height      : 450,
        autoScroll  : true,
        loader      : {
            url     : _UrlAjustesMedicos,
            params         :
            {
                'params.ntramite'  		: _NTRAMITE
                ,'params.cdunieco' 		: _CDUNIECO
                ,'params.cdramo'   		: _CDRAMO
                ,'params.estado'   		: _ESTADO
                ,'params.nmpoliza' 		: _NMPOLIZA
                ,'params.nmsuplem' 		: _NMSUPLEM
                ,'params.nmsituac' 		: _NMSITUAC
                ,'params.aaapertu' 		: _AAAPERTU
                ,'params.status'   		: _STATUS
                ,'params.nmsinies' 		: _NMSINIES
                ,'params.nfactura'		: recordFactura.get('NFACTURA')
                ,'params.cdgarant' 		: record.get('CDGARANT')
                ,'params.cdconval' 		: record.get('CDCONVAL')
                ,'params.cdconcep' 		: record.get('CDCONCEP')
                ,'params.idconcep' 		: record.get('IDCONCEP')
                ,'params.nmordina' 		: record.get('NMORDINA')
                ,'params.precio'   		: record.get('PTPRECIO')
                ,'params.cantidad'      : record.get('CANTIDAD')
                ,'params.descuentoporc' : record.get('DESTOPOR')
                ,'params.descuentonum'  : record.get('DESTOIMP')
                ,'params.importe'       : record.get('PTIMPORT')
                ,'params.ajusteMedi'    : record.get('TOTAJUSMED')
            },
            scripts  : true,
            loadMask : true,
            autoLoad : true,
            ajaxOptions: {
            	method: 'POST'
            }
        }
	    ,
	    listeners:{
	         close:function(){
	             if(true){
	                 //Actualizamos la información de la consulta del grid inferior
	            	 storeConceptos.reload();
	             }
	         }
	    }
    }).show();
    centrarVentana(windowLoader);
}
        //jtezva
        _revAdm_formRechazo.items.items[1].on('select',function(combo, records, eOpts)
	    {    
        	_revAdm_formRechazo.items.items[2].setValue(records[0].get('value'));
	    });
});

//jtezva
////// funciones //////
function _revAdm_rechazoSiniestro()
{
    debug('_revAdm_rechazoSiniestro');
    
    var valido = _revAdm_formRechazo.isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
    if(valido)
    {
    	var UrlFact;
        if(_Operacion == 'I'){
            UrlFact = _URL_GuardaFactura;
        }else {
            UrlFact = _URL_ActualizaFactura;
        }
        _revAdm_windowRechazo.setLoading(true);
        panelEdicionFacturas.form.submit({    
            url: UrlFact,
            params: {
                'params.ntramite'       : _NTRAMITE,
                'params.cdunieco'       : _CDUNIECO,
                'params.cdramo'         : _CDRAMO,
                'params.estado'         : _ESTADO,
                'params.nmpoliza'       : _NMPOLIZA,
                'params.nmsituac'       : _NMSITUAC,
                'params.nmsuplem'       : _NMSUPLEM,
                'params.status'         : _STATUS,
                'params.aaapertu'       : _AAAPERTU,
                'params.nmsinies'       : _NMSINIES,
                'params.cancelar'       : 'si',
                'params.cdmotivo'       : _revAdm_itemsRechazo[0].getValue(),
                'params.cdsubmotivo'    : _revAdm_itemsRechazo[1].getValue(),
                'params.rechazocomment' : _revAdm_itemsRechazo[2].getValue()
            },
            failure: function(form, action) {
            	_revAdm_windowRechazo.setLoading(false);
            	centrarVentanaInterna(mensajeError("Error al guardar la Factura"));
            },
            success: function(form, action)
            {
            	_revAdm_windowRechazo.setLoading(false);
            	Ext.create('Ext.form.Panel').submit(
                {
                    url             : _revAdm_urlMesaControl
                    ,standardSubmit :true
                    ,params         :
                    {
                        'smap1.gridTitle'      : 'Siniestros'
                        ,'smap2.pv_cdtiptra_i' : 16
                    }
                });
            }
        });
    }
}
////// funciones //////
</script>

<div id="maindivAdminData" style="height:600px;"></div>