<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<script>

////// variables //////

var _p12_smap    = <s:property value='smapJson'                    escapeHtml='false' />;
var _p12_smap2   = <s:property value='smap2Json'                   escapeHtml='false' />;
var _p12_smap3   = <s:property value='smap3Json'                   escapeHtml='false' />;
var _p12_slist1  = <s:property value='slist1Json'                  escapeHtml='false' />;//facturas(REEMBOLSO)/siniestros(P DIRECTO)
var _p12_slist2  = <s:property value='slist2Json'                  escapeHtml='false' />;//copago/deducible (TODOS)
var _p12_slist3  = <s:property value='slist3Json'                  escapeHtml='false' />;//proveedores (REEMBOLSO)
var _p12_llist1  = <s:property value='llist1Json'                  escapeHtml='false' />;//lista de lista de conceptos (TODOS)
var _p12_lhosp   = <s:property value='lhospJson'                   escapeHtml='false' />;//lista de totales hopitalizacion
var _p12_lpdir   = <s:property value='lpdirJson'                   escapeHtml='false' />;//lista de datos pdirecto
var _p12_lprem   = <s:property value='lpremJson'                   escapeHtml='false' />;//lista de datos preembolso
var _p12_listaWS = <s:property value='listaImportesWebServiceJson' escapeHtml='false' />;

var _p12_penalTotal = <s:property value='datosPenalizacionJson' escapeHtml='false' />; //Informacion de penalizacion

debug('penalizacion:'    , _p12_penalTotal);
debug('_p12_smap:'    , _p12_smap);
debug('_p12_smap2:'   , _p12_smap2);
debug('_p12_smap3:'   , _p12_smap3);
debug('_p12_slist1:'  , _p12_slist1);
debug('_p12_slist2:'  , _p12_slist2);
debug('_p12_slist3:'  , _p12_slist3);
debug('_p12_llist1:'  , _p12_llist1);
debug('_p12_lhosp:'   , _p12_lhosp);
debug('_p12_lpdir:'   , _p12_lpdir);
debug('_p12_lprem:'   , _p12_lprem);
debug('_p12_listaWS:' , _p12_listaWS);

var _p12_urlObtenerFacturasTramite   = '<s:url namespace="/siniestros"  action="obtenerFacturasTramite"   />';
var _p12_urlObtenerSiniestrosTramite = '<s:url namespace="/siniestros"  action="obtenerSiniestrosTramite" />';
var _p12_urlObtenerDatosProveedor    = '<s:url namespace="/siniestros"  action="obtenerDatosProveedor"    />';
var _p12_urlObtenerConceptosCalculo  = '<s:url namespace="/siniestros"  action="obtenerConceptosCalculo"  />';
var _p12_urlAutorizaConceptos        = '<s:url namespace="/siniestros"  action="autorizaConcepto"         />';
var _p12_urlMesaControl              = '<s:url namespace="/mesacontrol" action="mcdinamica"               />';
var _p12_urlGuardar                  = '<s:url namespace="/siniestros"  action="guardarCalculos"          />';

var _p12_formTramite;
var _p12_formFactura;
var _p12_formProveedor;
var _p12_formSiniestro;
var _p12_panelCalculo;
var _p12_paneles = [];
var _p12_formAutoriza;
var _p12_windowAutoriza;
var _p12_itemsRechazo = [ <s:property value="imap.rechazoitems" /> ];
    _p12_itemsRechazo[2]['width']  = 500;
    _p12_itemsRechazo[2]['height'] = 150;
var _p12_formRechazo;
var _p12_windowRechazo;

////// variables //////

Ext.onReady(function()
{
	////// modelos //////
	Ext.define('_p12_Tramite',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="imap.tramiteFields" /> ]
	});
	Ext.define('_p12_Factura',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="imap.facturaFields" /> ]
	});
	Ext.define('_p12_Siniestro',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value="imap.siniestroFields" /> ]
    });
	Ext.define('_p12_Proveedor',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="imap.proveedorFields" /> ]
	});
	Ext.define('_p12_Concepto',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="imap.conceptoFields" /> ]
	});
	////// modelos //////
	
	////// stores //////
	<%--
	_p12_storeFacturas = Ext.create('Ext.data.Store',
    {
        autoLoad : false
        ,model   : '_p12_Factura'
        ,proxy   :
        {
            reader :
            {
            	type  : 'json'
            	,root : 'slist1'
            }
            ,type  : 'ajax'
            ,url   : _p12_urlObtenerFacturasTramite
        }
    });
    --%>
	<%--_p12_storeSiniestros = Ext.create('Ext.data.Store',
    {
        autoLoad : false
        ,model   : '_p12_Siniestro'
        ,proxy   :
        {
            reader :
            {
                type  : 'json'
                ,root : 'slist1'
            }
            ,type  : 'ajax'
            ,url   : _p12_urlObtenerSiniestrosTramite
        }
    });--%>
	<%--
	_p12_storeConceptos = Ext.create('Ext.data.Store',
    {
        autoLoad : false
        ,model   : '_p12_Concepto'
        ,proxy   :
        {
            reader :
            {
                type  : 'json'
                ,root : 'slist1'
            }
            ,type  : 'ajax'
            ,url   : _p12_urlObtenerConceptosCalculo
        }
    });
    --%>
	////// stores //////
	
	////// componentes //////
	var totalglobal = 0.0;
    
	//PAGO DIRECTO
    if(_p12_smap.OTVALOR02=='1')
    {
        debug('PAGO DIRECTO');
        var indice;
        for(indice = 0;indice<_p12_slist1.length;indice++)
        {
            debug('Iterando siniestros n '+(indice+1));
            var panelSiniestro = Ext.create('Ext.form.Panel',
            {
                layout    :
                {
                	type     : 'table'
                	,columns : 2
                }
                ,border   : 0
	            ,defaults :
	            {
	                style : 'margin : 5px;'
	            }
                ,items    :
                [
                    {
                    	xtype       : 'displayfield'
                    	,fieldLabel : 'N&Uacute;MERO DE SINIESTRO'
                    	,name       : 'NMSINIES'
                    }
                    ,{
                    	xtype       : 'displayfield'
                    	,fieldLabel : 'SUCURSAL DE LA P&Oacute;LIZA'
                    	,name       : 'CDUNIECO'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,fieldLabel : 'P&Oacute;LIZA'
                        ,name       : 'NMPOLIZA'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,fieldLabel : 'CLAVE ASEGURADO'
                        ,name       : 'CDPERSON'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,fieldLabel : 'ASEGURADO'
                        ,name       : 'NOMBRE'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,fieldLabel : 'FECHA DE OCURRENCIA'
                        ,name       : 'FEOCURRE'
                        ,valueToRaw : function(value)
                        {
                        	return Ext.Date.format(value,'d M Y');
                        }
                    }
                ]
            });
            panelSiniestro.loadRecord(new _p12_Siniestro(_p12_slist1[indice]));
            if(_p12_smap2.CDGARANT=='18HO'||_p12_smap2.CDGARANT=='18MA')
            {
            	debug('HOSPITAL');
            	var causaSiniestro = _p12_penalTotal[indice].causaSiniestro;
            	var importe   = (_p12_lhosp[indice].PRECIO*1.0);
            	var descuento = _p12_lhosp[indice].DESCPRECIO*1.0;
            	debug('importe Inicial'   ,_p12_lhosp[indice].PTIMPORT*1.0);
            	debug('importe'   , importe);
            	debug('descuento' , descuento);
            	var subttDesc = importe - descuento;
            	debug('subttDesc' , subttDesc);
            	var deducible = 0;
            	var sDeducible = _p12_slist2[indice].DEDUCIBLE;
            	
            	if(causaSiniestro !="2"){
            		if(
                			!(!sDeducible
                			||sDeducible.toLowerCase()=='na'
                			||sDeducible.toLowerCase()=='no')
                			)
                	{
                		deducible = sDeducible.replace(',','')*1.0;
                	}
            	}
            	
            	var subttDedu = subttDesc - deducible;
            	
            	if(causaSiniestro !="2"){
            		var copagoPesos       = _p12_penalTotal[indice].copagoPesos;
            		var copagoPorcentajes = _p12_penalTotal[indice].copagoPorcentajes;
            		
            		var copagoaplica = (copagoPesos*1.0) + (subttDedu*(copagoPorcentajes/100.0));
            	}else{
                	var copagoaplica = 0.0;
            	}
            	
            	var iva       = _p12_lhosp[indice].IVA*1.0;
            	var baseIva = _p12_lhosp[indice].BASEIVA*1.0;
            	var total = subttDedu - copagoaplica + iva ;
            	debug('subttDedu',subttDedu);
            	debug('subttDesc',subttDesc);
            	debug('deducible',deducible);
            	debug('subttDedu',subttDedu);
            	debug('copago',copago);
            	debug('tipcopag',tipcopag);
            	debug('copagoaplica',copagoaplica);
            	debug('total',total);
            	totalglobal = totalglobal + total;
            	var panelCuentas = Ext.create('Ext.panel.Panel',
            	{
            		title     : 'Resumen'
            		,border   : 1
            		,defaults :
            		{
            			style : 'margin : 5px;'
            		}
            	    ,items    :
            	    [
            	        {
            	        	xtype       : 'displayfield'
            	        	,labelWidth : 200
            	        	,fieldLabel : 'Importe'
            	        	,value      : importe
            	        	,valueToRaw : function(value)
                            {
                                return Ext.util.Format.usMoney(value);
                            }
            	        }
            	        ,{
                            xtype       : 'displayfield'
                            ,labelWidth : 200
                            ,fieldLabel : 'Descuento'
                            ,value      : descuento
                            ,valueToRaw : function(value)
                            {
                                return Ext.util.Format.usMoney(value);
                            }
                        }
            	        ,{
                            xtype       : 'displayfield'
                            ,labelWidth : 200
                            ,fieldLabel : 'Subtotal'
                            ,value      : subttDesc
                            ,valueToRaw : function(value)
                            {
                                return Ext.util.Format.usMoney(value);
                            }
                        }
            	        ,{
                            xtype       : 'displayfield'
                            ,labelWidth : 200
                            ,fieldLabel : 'Deducible'
                            ,value      : deducible
                            ,valueToRaw : function(value)
                            {
                                return Ext.util.Format.usMoney(value);
                            }
                        }
            	        ,{
                            xtype       : 'displayfield'
                            ,labelWidth : 200
                            ,fieldLabel : 'Subtotal'
                            ,value      : subttDedu
                            ,valueToRaw : function(value)
                            {
                                return Ext.util.Format.usMoney(value);
                            }
                        }
            	        ,{
                            xtype       : 'displayfield'
                            ,labelWidth : 200
                            ,fieldLabel : 'Copago' //  PAGO DIRECRO
                            ,value      : copagoaplica
                            ,valueToRaw : function(value)
                            {
                                return Ext.util.Format.usMoney(value);
                            }
                        }
                        ,{
                            xtype       : 'displayfield'
                            ,labelWidth : 200
                            ,fieldLabel : 'Base IVA'
                            ,value      : baseIva
                            ,valueToRaw : function(value)
                            {
                                return Ext.util.Format.usMoney(value);
                            }
                        },{
                            xtype       : 'displayfield'
                            ,labelWidth : 200
                            ,fieldLabel : 'IVA' //IVA PAGO DIRECTO HOSPI
                            ,value      : iva
                            ,valueToRaw : function(value)
                            {
                                return Ext.util.Format.usMoney(value);
                            }
                        }
            	        ,{
                            xtype       : 'displayfield'
                            ,labelWidth : 200
                            ,fieldLabel : 'Total'
                            ,value      : total
                            ,valueToRaw : function(value)
                            {
                                return Ext.util.Format.usMoney(value);
                            }
                        }
            	    ]
            	});
            	var panelIterado = Ext.create('Ext.panel.Panel',
                {
                    title           : 'SINIESTRO'
                    ,collapsible    : true
                    ,titleCollapse  : true
                    ,collapsed      : true
                    ,defaults       :
                    {
                        style : 'margin : 5px;'
                    }
                    ,items          :
                    [
                        panelSiniestro
                        ,panelCuentas
                    ]
                });
            	_p12_paneles.push(panelIterado);
            }
            else
            {
            	debug('PAGO DIRECTO');
            	totalglobal = totalglobal + _p12_lpdir[indice].total*1.0;
            	var gridCuentas = Ext.create('Ext.grid.Panel',
            	{
            		store    : Ext.create('Ext.data.Store',
            		{
            			autoLoad : true
            			,model   : '_p12_Concepto'
            			,proxy   :
            			{
            				type    : 'memory'
            				,reader : 'json'
            				,data   : _p12_llist1[indice]
            			}
            		})
            		,height  : 180
            		,columns :
            		[
            		    {
            		    	header     : 'Tipo concepto'
            		    	,dataIndex : 'DESCRIPC' 
            		    	,flex      : 1
            		    }
            		    ,{
            		    	header     : 'Concepto'
            		    	,dataIndex : 'OTVALOR'
            		    	,flex      : 1
            		    }
            		    ,{
                            header     : 'Cantidad'
                            ,dataIndex : 'CANTIDAD'
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Importe arancel'
                            ,dataIndex : 'IMP_ARANCEL'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Subtotal arancel'
                            ,dataIndex : 'SUBTTARANCEL'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Descuento'
                            ,dataIndex : 'DESTOAPLICA'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Subtotal descuento'
                            ,dataIndex : 'SUBTTDESCUENTO'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Copago'
                            ,dataIndex : 'COPAGOAPLICA'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Subtotal copago'
                            ,dataIndex : 'SUBTTCOPAGO'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'ISR'
                            ,dataIndex : 'ISRAPLICA'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Impuesto cedular'
                            ,dataIndex : 'CEDUAPLICA'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Subtotal impuestos'
                            ,dataIndex : 'SUBTTIMPUESTOS'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'IVA'
                            ,dataIndex : 'IVAAPLICA'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Importe autom&aacute;tico'
                            ,dataIndex : 'PTIMPORTAUTO'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Importe facturado'
                            ,dataIndex : 'PTIMPORT'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Autorizaci&oacute;n<br/>m&eacute;dica'
                            ,dataIndex : 'AUTMEDIC'
                            ,renderer  : function(v)
                            {
                            	var r ='N/A';
                            	if(v=='S')
                            	{
                            		r='SI';
                            	}
                            	else if(v=='N')
                                {
                                    r='NO';
                                }
                            	return r;
                            }
                            ,flex      : 1
                        }
            		    ,{
                            header     : 'Valor usado'
                            ,dataIndex : 'VALORUSADO'
                            ,renderer  : Ext.util.Format.usMoney
                            ,flex      : 1
                        }
            		    ,{
            		    	header     : 'Observaciones<br/>M&eacute;dicas'
            		    	,dataIndex : 'COMMENME'
            		    	,flex      : 1
            		    }
            		]
	            	,viewConfig :
	                {
	                    listeners :
	                    {
	                        refresh : function(dataview)
	                        {
	                            Ext.each(dataview.panel.columns, function(column)
	                            {
	                                column.autoSize();
	                            });
	                        }
	                    }
	                }
            	    ,listeners :
            	    {
            	    	itemclick : _p12_mostrarWindowAutoriza
            	    }
            	});
	            var panelTotales = Ext.create('Ext.form.Panel',
	            {
	                layout    :
	                {
	                    type     : 'table'
	                    ,columns : 2
	                }
	                ,border   : 0
	                ,width    : 800
	                ,defaults :
	                {
	                    style : 'margin : 5px;'
	                }
	                ,items    :
	                [
	                    {
	                        xtype       : 'displayfield'
	                        ,fieldLabel : 'TOTAL'
	                        ,labelWidth : 200
	                        ,value      : _p12_lpdir[indice].total
	                        ,valueToRaw : function(value)
	                        {
	                        	return Ext.util.Format.usMoney(value);
	                        }
	                    }
	                ]
	            });
	            var panelIterado = Ext.create('Ext.panel.Panel',
	            {
	            	title          : 'SINIESTRO'
	            	,collapsible   : true
	            	,titleCollapse : true
	            	,collapsed     : true
	            	,defaults      :
	                {
	                    style : 'margin : 5px;'
	                }
	                ,items         :
	                [
	                    panelSiniestro
	                    ,gridCuentas
	                    ,panelTotales
	                ]
	            });
	            _p12_paneles.push(panelIterado);
            }
        }
    }
    else
    {
    	//PAGO POR REEMBOLSO
        debug('REEMBOLSO');
        var indice;
        for(indice = 0;indice<_p12_slist1.length;indice++)
        {
            debug('Iterando facturas n '+(indice+1));
            var panelFactura = Ext.create('Ext.form.Panel',
            {
                layout    :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,border   : 0
                ,defaults :
                {
                    style : 'margin : 5px;'
                }
                ,items    :
                [
                    {
                        xtype       : 'displayfield'
                        ,fieldLabel : 'N&Uacute;MERO DE FACTURA'
                        ,name       : 'NFACTURA'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,fieldLabel : 'FECHA'
                        ,name       : 'FFACTURA'
                        ,valueToRaw : function(v)
                        {
                        	return Ext.Date.format(v,'d M Y');
                        }
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,fieldLabel : 'COBERTURA'
                        ,name       : 'DSGARANT'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,fieldLabel : 'SUBCOBERTURA'
                        ,name       : 'DSSUBGAR'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,fieldLabel : 'DESCUENTO %'
                        ,name       : 'DESCPORC'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,fieldLabel : 'DESCUENTO $'
                        ,name       : 'DESCNUME'
                       	,valueToRaw : function(value)
                        {
                            return Ext.util.Format.usMoney(value);
                        }
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,fieldLabel : 'SERVICIO'
                        ,name       : 'DESCSERVICIO'
                        ,colspan    : 2
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,fieldLabel : 'PROVEEDOR'
                        ,name       : 'NOMBREPROVEEDOR'
                        ,colspan    : 2
                    }
                ]
            });
            panelFactura.loadRecord(new _p12_Factura(_p12_slist1[indice]));
            var gridCuentas = Ext.create('Ext.grid.Panel',
            {
                store    : Ext.create('Ext.data.Store',
                {
                    autoLoad : true
                    ,model   : '_p12_Concepto'
                    ,proxy   :
                    {
                        type    : 'memory'
                        ,reader : 'json'
                        ,data   : _p12_llist1[indice]
                    }
                })
                ,height  : 180
                ,columns :
                [
                    {
                        header     : 'Tipo concepto'
                        ,dataIndex : 'DESCRIPC' 
                        ,flex      : 1
                    }
                    ,{
                        header     : 'Concepto'
                        ,dataIndex : 'OTVALOR'
                        ,flex      : 1
                    }
                    ,{
                        header     : 'Importe neto'
                        ,dataIndex : 'PTIMPORT'
                        ,renderer  : Ext.util.Format.usMoney
                        ,flex      : 1
                    }
                    ,{
                        header     : 'Ajuste'
                        ,dataIndex : 'PTIMPORT_AJUSTADO'
                        ,renderer  : Ext.util.Format.usMoney
                        ,flex      : 1
                    }
                    ,{
                        header     : 'Subtotal'
                        ,dataIndex : 'SUBTOTAL'
                        ,renderer  : Ext.util.Format.usMoney
                        ,flex      : 1
                    }
                ]
                ,viewConfig :
                {
                    listeners :
                    {
                        refresh : function(dataview)
                        {
                            Ext.each(dataview.panel.columns, function(column)
                            {
                                column.autoSize();
                            });
                        }
                    }
                }
            });
            debug('indice,_p12_lprem[indice]',indice,_p12_lprem[indice]);
            
            var ptimpoajus  = _p12_lprem[indice].SUBTOTAL*1.0;
            var destopor    = _p12_slist1[indice].DESCPORC*1.0;
            var destoimp    = _p12_slist1[indice].DESCNUME*1.0;
            var destoaplica = (ptimpoajus*(_p12_slist1[indice].DESCPORC/100.0)) + destoimp;
            var subttdesc   = ptimpoajus-destoaplica;
            var sDeducible  = _p12_slist2[indice].DEDUCIBLE;
            var deducible   = 0;
            var _facturaIndividual = _p12_slist1[indice];
            
            
            if(
                    !(!sDeducible
                    ||sDeducible.toLowerCase()=='na'
                    ||sDeducible.toLowerCase()=='no')
                    )
            {
                deducible = sDeducible.replace(',','')*1.0;
                
                if(_facturaIndividual.CDGARANT=='18HO'||_facturaIndividual.CDGARANT=='18MA')
               	{
                	if(causaSiniestro ="2"){
                		deducible = 0;
                	}
               	}
            }
            
            
            var subttdeduc  = subttdesc-deducible;
            _p12_slist2[indice].COPAGOAUX = _p12_slist2[indice].COPAGO;
            _p12_slist2[indice].COPAGO = 0;
            if(
                !(!_p12_slist2[indice].COPAGOAUX
                ||_p12_slist2[indice].COPAGOAUX.toLowerCase()=='na'
                ||_p12_slist2[indice].COPAGOAUX.toLowerCase()=='no')    
            )
            {
                _p12_slist2[indice].COPAGO = _p12_slist2[indice].COPAGOAUX.replace(",","");
            }
            var copago      = _p12_slist2[indice].COPAGO*1.0;
            var tipcopag    = _p12_slist2[indice].TIPOCOPAGO;
            var causaSiniestro = _p12_penalTotal[indice].causaSiniestro;
            //var _facturaIndividual = _p12_slist1[indice];
            
            if(_facturaIndividual.CDGARANT=='18HO'||_facturaIndividual.CDGARANT=='18MA')
           	{
            	if(causaSiniestro !="2"){
            		var copagoPesos       = _p12_penalTotal[indice].copagoPesos;
            		var copagoPorcentajes = _p12_penalTotal[indice].copagoPorcentajes;
            		var copagoaplica 	  = (copagoPesos*1.0) + (subttdeduc*(copagoPorcentajes/100.0));
            	}else{
            		var copagoaplica = 0.0;
            	}
           	}else{
           		if(tipcopag=='$'){
                    var copagoaplica = copago;
                }
                else if(tipcopag=='%'){
                    var copagoaplica = subttdeduc*(copago/100.0);
                }
                else{
                    var copagoaplica = 0.0;
                }
           	}
            var total = subttdeduc - copagoaplica;
            _p12_slist1[indice]['TOTALFACTURA']=total;
            totalglobal = totalglobal + total;
            var panelTotales = Ext.create('Ext.panel.Panel',
            {
            	defaults :
            	{
            		style : 'margin : 5px;'
            	}
                ,border  : 0
                ,layout  :
                {
                	type     : 'table'
                	,columns : 2
                }
                ,items   :
                [
                    {
                    	xtype       : 'displayfield'
                    	,labelWidth : 200
                    	,fieldLabel : 'Total neto'
                    	,value      : _p12_lprem[indice].TOTALNETO
                    	,valueToRaw : function(value)
                        {
                            return Ext.util.Format.usMoney(value);
                        }
                    }
                    ,{
                    	xtype       : 'displayfield'
                    	,labelWidth : 200
                    	,fieldLabel : 'Subtotal ajustado'
                    	,value      : _p12_lprem[indice].SUBTOTAL
                    	,valueToRaw : function(value)
                        {
                            return Ext.util.Format.usMoney(value);
                        }
                    }
                    ,{
                    	xtype : 'label'
                    }
                    ,{
                    	xtype       : 'displayfield'
                    	,labelWidth : 200
                    	,value      : destoaplica
                    	,fieldLabel : 'Ajuste'
                    	,valueToRaw : function(value)
                        {
                            return Ext.util.Format.usMoney(value);
                        }
                    }
                    ,{
                    	xtype : 'label'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,labelWidth : 200
                        ,value      : subttdesc
                        ,fieldLabel : 'Total autorizado'
                        ,valueToRaw : function(value)
                        {
                            return Ext.util.Format.usMoney(value);
                        }
                    }
                    ,{
                        xtype : 'label'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,labelWidth : 200
                        ,value      : deducible
                        ,fieldLabel : 'Deducible'
                        ,valueToRaw : function(value)
                        {
                            return Ext.util.Format.usMoney(value);
                        }
                    }
                    ,{
                        xtype : 'label'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,labelWidth : 200
                        ,value      : subttdeduc
                        ,fieldLabel : 'Subtotal despu&eacute;s deducible'
                        ,valueToRaw : function(value)
                        {
                            return Ext.util.Format.usMoney(value);
                        }
                    }
                    ,{
                        xtype : 'label'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,labelWidth : 200
                        ,value      : copagoaplica
                        ,fieldLabel : 'Copago'  // PAGO POR REEMBOLSO
                        ,valueToRaw : function(value)
                        {
                            return Ext.util.Format.usMoney(value);
                        }
                    }
                    ,{
                        xtype : 'label'
                    }
                    ,{
                        xtype       : 'displayfield'
                        ,labelWidth : 200
                        ,value      : total
                        ,fieldLabel : 'Total a pagar'
                        ,valueToRaw : function(value)
                        {
                            return Ext.util.Format.usMoney(value);
                        }
                    }
                    
                ]
            });
            var panelIterado = Ext.create('Ext.panel.Panel',
            {
                title          : 'FACTURA'
                ,collapsible   : true
                ,titleCollapse : true
                ,collapsed     : true
                ,defaults      :
                {
                    style : 'margin : 5px;'
                }
                ,items         :
                [
                    panelFactura
                    ,gridCuentas
                    ,panelTotales
                ]
            });
            _p12_paneles.push(panelIterado);
        }
    }
	
	
	
    _p12_paneles.push(Ext.create('Ext.form.Panel',
    {
    	title     : 'TOTAL'
    	,defaults :
    	{
    		style : 'margin : 5px;'
    	}
        ,layout : 'hbox'
    	,items    :
    	[
    	    {
    	    	xtype       : 'displayfield'
    	    	,labelWidth : 200
    	    	,fieldLabel : 'TOTAL DEL TR&Aacute;MITE'
    	    	,value      : totalglobal
    	    	,valueToRaw : function(value)
                {
                    return Ext.util.Format.usMoney(value);
                }
    	    }
    	    ,{
    	    	xtype    : 'button'
    	    	,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
    	    	,text    : 'Aceptar y guardar'
    	    	,handler : _p12_guardar_click
    	    }
    	]
    }));
    
    Ext.define('_p12_FormRechazo',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_p12_FormRechazo initComponent');
            Ext.apply(this,
            {
                border  : 0
                ,items  : _p12_itemsRechazo
            });
            this.callParent();
        }
    });
    
    Ext.define('_p12_WindowRechazo',
    {
        extend         : 'Ext.window.Window'
        ,initComponent : function()
        {
            debug('_p12_WindowRechazo initComponent');
            Ext.apply(this,
            {
                title        : 'Rechazo de tr&aacute;mite'
                ,width       : 600
                ,height      : 350
                ,autoScroll  : true
                ,closeAction : 'hide'
                ,modal       : true
                ,defaults    : { style : 'margin : 5px; ' }
                ,items       : _p12_formRechazo
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Rechazar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
                        ,handler : _p12_rechazoSiniestro
                    }
                ]
            });
            this.callParent();
        }
    });
	////// componentes //////
	
	////// contenido //////
	_p12_formRechazo   = new _p12_FormRechazo();
	_p12_windowRechazo = new _p12_WindowRechazo();
	
	_p12_formAutoriza = Ext.create('Ext.form.Panel',
	{
		items : [ <s:property value="imap.autorizaItems" /> ] 
	});
	
	_p12_windowAutoriza = Ext.create('Ext.window.Window',
	{
		title        : 'Autorizar concepto'
		,width       : 600
		,height      : 400
		,closeAction : 'hide'
		,items       : _p12_formAutoriza
		,modal       : true
		,buttonAlign : 'center'
		,buttons     :
		[
		    {
		    	text     : 'Guardar'
		    	,icon    : '${ctx}/resources/fam3icons/icons/key.png'
		    	,handler : _p12_autorizarConcepto
		    }
		]
	});
	
	_p12_formTramite = Ext.create('Ext.form.Panel',
	{
		title   : 'TR&Aacute;MITE'
		,layout :
		{
			type     : 'table'
			,columns : 2
		}
		,items  : [ <s:property value="imap.tramiteItems" /> ]
	});
	
	_p12_formFactura = Ext.create('Ext.form.Panel',
    {
        title   : 'FACTURA'
        ,layout :
        {
            type     : 'table'
            ,columns : 2
        }
	    ,hidden : _p12_smap.PAGODIRECTO=='N'
        ,items  : [ <s:property value="imap.facturaItems" /> ]
    });
	
	_p12_formSiniestro = Ext.create('Ext.form.Panel',
    {
        title   : 'SINIESTRO'
        ,layout :
        {
            type     : 'table'
            ,columns : 2
        }
        ,hidden : _p12_smap.PAGODIRECTO=='S'
        ,items  : [ <s:property value="imap.siniestroItems" /> ]
    });
	
	<%--
	_p12_gridFacturas = Ext.create('Ext.grid.Panel',
	{
		title      : 'Facturas'
		,width     : 450
		,minHeight : 180
		,store     : _p12_storeFacturas
		,selType   : 'checkboxmodel'
		,columns   : [ <s:property value="imap.facturaColumns" /> ]
	    ,viewConfig :
	    {
	        listeners :
	        {
	            refresh : function(dataview)
	            {
	                Ext.each(dataview.panel.columns, function(column)
	                {
	                	column.autoSize();
	                });
	            }
	        }
	    }
	});--%>
	<%--
	_p12_gridSiniestros = Ext.create('Ext.grid.Panel',
    {
        title      : 'Siniestros'
        ,width     : 450
        ,minHeight : 180
        ,store     : _p12_storeSiniestros
        ,selType   : 'checkboxmodel'
        ,style     : 'margin-left : 5px;'
        ,columns   : [ <s:property value="imap.siniestroColumns" /> ]
        ,viewConfig :
        {
            listeners :
            {
                refresh : function(dataview)
                {
                    Ext.each(dataview.panel.columns, function(column)
                    {
                        column.autoSize();
                    });
                }
            }
        }
    });--%>
	
	_p12_formProveedor = Ext.create('Ext.form.Panel'
	,{
		title   : 'PROVEEDOR'
		,layout :
	    {
	        type     : 'table'
	        ,columns : 2
	    }
	    ,hidden : _p12_smap.PAGODIRECTO=='N'
	    ,items  : [ <s:property value="imap.proveedorItems" /> ]
	});
	
	<%--
	_p12_gridConceptos = Ext.create('Ext.grid.Panel',
    {
        title      : 'Conceptos'
        ,minHeight : 200
        ,store     : _p12_storeConceptos
        ,columns   : [ <s:property value="imap.conceptoColumns" /> ]
        ,viewConfig :
        {
            listeners :
            {
                refresh : function(dataview)
                {
                    Ext.each(dataview.panel.columns, function(column)
                    {
                        column.autoSize();
                    });
                }
            }
        }
    });
    --%>
	
	_p12_panelCalculo = Ext.create('Ext.panel.Panel',
	{
		title     : 'C&Aacute;LCULO'
		,defaults :
		{
			style : 'margin : 5px;'
		}
		,items    : _p12_paneles
	});
	
	Ext.create('Ext.panel.Panel',
	{
		defaults  : { style : 'margin : 5px;' }
	    ,renderTo : '_p12_divpri'
	    ,items    :
	    [
	        {
	        	xtype     : 'panel'
	        	,title    : 'C&Aacute;LCULOS DEL TR&Aacute;MITE'
	        	,items :
	        	[
			        {
			        	xtype   : 'panel'
			        	,style  : 'margin : 5px;'
			        	,border : 0
			        	,icon   : '${ctx}/resources/fam3icons/icons/error.png'
			        	,title  : 'Al acceder a esta pesta&ntilde;a los c&aacute;lculos anteriores se eliminan y debe guardarlos de nuevo'
			        }
	        	]
	        }
	        ,_p12_formTramite
	        ,_p12_formFactura
	        ,_p12_formProveedor
	        ,_p12_formSiniestro
	        ,_p12_panelCalculo
	        /*
	        ,Ext.create('Ext.panel.Panel',
	        {
	        	border  : 0
	        	,layout :
	        	{
	        		type     : 'table'
	        		,columns : 2
	        	}
	            ,items  :
	        	[
	        	    _p12_gridFacturas
	        	    ,_p12_gridSiniestros
	        	]
	        })
	        ,_p12_panelCalculo*/
	    ]
	});
	////// contenido //////
	
	////// loader //////
	_p12_formRechazo.items.items[1].on('select',function(combo, records, eOpts)
    {
		_p12_formRechazo.items.items[2].setValue(records[0].get('value'));
    });
	
	_p12_formTramite.loadRecord(new _p12_Tramite(_p12_smap));
	_p12_formFactura.loadRecord(new _p12_Factura(_p12_smap2));
	_p12_formProveedor.loadRecord(new _p12_Proveedor(_p12_smap3));
	_p12_formSiniestro.loadRecord(new _p12_Siniestro(_p12_smap2));
	/*_p12_storeFacturas.load(
	{
		params : { 'smap.ntramite' : _p12_smap.NTRAMITE }
	});
	_p12_storeSiniestros.load(
    {
        params : { 'smap.ntramite' : _p12_smap.NTRAMITE }
    });*/
	////// loader //////
});

////// funciones //////
function _p12_mostrarWindowAutoriza(grid,record)
{
	debug('_p12_mostrarWindowAutoriza record:',record.raw);
	_p12_formAutoriza.getForm().reset();
	_p12_formAutoriza.loadRecord(new _p12_Concepto(record.raw));
	_p12_windowAutoriza.show();
	centrarVentanaInterna(_p12_windowAutoriza);
}

function _p12_rechazoSiniestro()
{
	debug('_p12_rechazoSiniestro');
	var valido = _p12_formRechazo.isValid();
	if(!valido)
	{
		datosIncompletos();
	}
	
	if(valido)
	{
		_p12_windowRechazo.setLoading(true);
		var json =
		{
			params : _p12_formAutoriza.getValues()
		};
		json.params['cancela']       = 'si';
		json.params['cdmotivo']      = _p12_formRechazo.items.items[0].getValue();
		json.params['commenrechazo'] = _p12_formRechazo.items.items[2].getValue();
		json.params['ntramite']      = _p12_smap['NTRAMITE'];
		debug('datos a enviar:',json);
	    Ext.Ajax.request(
	    {
	        url       : _p12_urlAutorizaConceptos
	        ,jsonData : json
	        ,success  : function(response)
	        {
	        	_p12_windowRechazo.setLoading(false);
	            json = Ext.decode(response.responseText);
	            debug('respuesta:',json);
	            if(json.success==true)
	            {
	                mensajeCorrecto('Datos guardados',json.mensaje);
	                Ext.create('Ext.form.Panel').submit(
	                {
	                    url             : _p12_urlMesaControl
	                	,standardSubmit : true
	                    ,params         :
	                    {
	                        'smap1.gridTitle'      : 'Siniestros'
	                        ,'smap2.pv_cdtiptra_i' : 16
	                    }
	                });
	            }
	            else
	            {
	                mensajeError(json.mensaje);
	            }
	        }
	        ,failure  : function()
	        {
	        	_p12_windowRechazo.setLoading(false);
	            errorComunicacion();
	        }
	    });
	}
}

function _p12_autorizarConcepto()
{
	debug('_p12_autorizarConcepto');
	
	var valido = _p12_formAutoriza.isValid();
	if(!valido)
	{
		datosIncompletos();
	}
	
	if(valido)
	{
		
		var autoMed = _p12_formAutoriza.down('[name="AUTRECLA"]');
        if(autoMed)
        {
            autoMed = autoMed.getValue();
        }
        var autoRec = _p12_formAutoriza.down('[name="AUTMEDIC"]');
        if(autoRec)
        {
            autoRec = autoRec.getValue();
        }
        debug('autoMed',autoMed,'autoRec',autoRec);
        var cancelar = (autoMed && autoMed =='N') || (autoRec && autoRec == 'N');
        debug('cancelar sin tipopago',cancelar ? 'si' : 'no');
        cancelar = cancelar && _p12_smap['OTVALOR02']=='1';
        debug('cancelar con tipopago',cancelar ? 'si' : 'no');
        if(cancelar)
        {
            //jtezva
            mensajeWarning(
                    'El tr&aacute;mite de pago directo ser&aacute; cancelado debido a que no ha sido autorizada alguno de los conceptos'
                    ,function(){_p12_windowRechazo.show();centrarVentanaInterna(_p12_windowRechazo);}
            );
        }
        else
        {
			_p12_formAutoriza.setLoading(true);
			Ext.Ajax.request(
			{
				url       : _p12_urlAutorizaConceptos
				,jsonData :
				{
					params : _p12_formAutoriza.getValues() 
				}
				,success  : function(response)
				{
					_p12_formAutoriza.setLoading(false);
					var json = Ext.decode(response.responseText);
					debug('respuesta:',json);
					if(json.success==true)
					{
						mensajeCorrecto('Datos guardados',json.mensaje);
						Ext.create('Ext.form.Panel').submit(
						{
							standardSubmit : true
							,params        :
							{
								'params.ntramite'  : _NTRAMITE
								,'params.tipopago' : _TIPOPAGO
							}
						});
					}
					else
					{
						mensajeError(json.mensaje);
					}
				}
				,failure  : function()
				{
					_p12_formAutoriza.setLoading(false);
					errorComunicacion();
				}
			});
        }
	}
}
<%--
function _p12_calcular()
{
	debug('_p12_calcular');
	
	var valido=true;
	
	if(valido)
	{
		valido = _p12_gridFacturas.getSelectionModel().selected.length==1&&_p12_gridSiniestros.getSelectionModel().selected.length==1;
		if(!valido)
		{
			mensajeWarning('Seleccione la factura y el siniestro');
		}
	}
	
	if(valido)
	{
		var panel     = _p12_panelCalculo;
		var factura   = _p12_gridFacturas.getSelectionModel().selected.items[0];
		var siniestro = _p12_gridSiniestros.getSelectionModel().selected.items[0];
		
		panel.setLoading(true);
		Ext.Ajax.request(
		{
			url     : _p12_urlObtenerDatosProveedor
			,params :
			{
				'smap.cdpresta' : factura.get('CDPRESTA')
			}
		    ,success : function(response)
		    {
		    	var datosProveedor = Ext.decode(response.responseText);
		    	debug('respuesta de datos de proveedor: ',datosProveedor);
		    	
		    	valido = datosProveedor.success; 
		    	if(!valido)
		    	{
		    		panel.setLoading(false);
		    		mensajeError('Error al obtener datos del proveedor',respuestaProveedor.mensaje);
		    	}
		    	
		    	if(valido)
		    	{
		    		_p12_storeConceptos.load(
		    		{
		    			params :
		    			{
		    				'smap.ntramite' : _p12_smap.NTRAMITE
		    			}
			    		,callback: function(records, operation, success)
	                    {
			    			if(success)
			    			{
			    				panel.setLoading(false);
			    				datosProveedor = datosProveedor.smap;
			                    _p12_formProveedor.loadRecord(new _p12_Proveedor(datosProveedor));
			    			}
			    			else
			    			{
			    				panel.setLoading(false);
			    				mensajeError('Error al obtener los conceptos');
			    			}
	                    }
		    		});
		    	}
		    	
		    }
		    ,failure : function()
		    {
		    	panel.setLoading(false);
		    	errorComunicacion();
		    }
		});
	}
}
--%>

function _p12_guardar_click()
{
	debug('_p12_guardar_click');
	var ventana = Ext.MessageBox.confirm('Guardar pagos','¿Desea guardar el importe total?',_p12_guardar_confirmar);
	centrarVentanaInterna(ventana);
}

function _p12_guardar_confirmar(boton)
{
	debug('_p12_guardar_confirmar:',boton);
	if(boton=='yes')
	{
		_p12_guardar();
	}
}

function _p12_guardar()
{
	debug('_p12_guardar');
	var valido = _p12_validaAutorizaciones();
	//console.log(valido);
	if(valido.length==0)
	{
		_p12_panelCalculo.setLoading(true);
		var esPagoDirecto = _p12_smap.PAGODIRECTO=='S';
		Ext.Ajax.request(
		{
			url       : _p12_urlGuardar
			,jsonData :
			{
				slist1  : _p12_listaWS
				,slist2 : esPagoDirecto ? [] : _p12_slist1
			}
			,success  : function(response)
			{
				_p12_panelCalculo.setLoading(false);
				var json = Ext.decode(response.responseText);
				debug('respuesta:',json);
				if(json.success)
				{
					mensajeCorrecto('Datos guardados',json.mensaje);
				}
				else
				{
					mensajeError(json.mensaje);
				}
			}
		    ,failure  : function()
		    {
		    	_p12_panelCalculo.setLoading(false);
		    	errorComunicacion();
		    }
		});
	}
	else
	{
		centrarVentanaInterna(Ext.Msg.show({
	           title      : 'Error',
	           msg        : valido,
	           width      : 600,
	           icon    	  : Ext.Msg.ERROR,
	           buttons    : Ext.Msg.OK
	    }));
	}
}

function _p12_validaAutorizaciones()
{
	var result = '';
	debug('_p12_validaAutorizaciones');
	var esPagoDirecto = _p12_smap.PAGODIRECTO=='S';
	debug('esPagoDirecto:',esPagoDirecto);
	if(esPagoDirecto)
	{
		var esHospital = _p12_smap2.CDGARANT=='18HO'||_p12_smap2.CDGARANT=='18MA';
		debug('esHospital:',esHospital);
		if(esHospital&&false)
		{
			debug('validando hospitalizacion pago directo');
		}
		else
		{
			debug('validando pago directo');
			var i;
			var factura    = _p12_smap2;
			var siniestros = _p12_slist1;
			var conceptos  = _p12_llist1; 
			for(i=0;i<siniestros.length;i++)
			{
				var siniestroIte = siniestros[i];
				if(siniestroIte.AUTRECLA!='S')
                {
                    result = result + 'Reclamaciones no autoriza el siniestro ' + siniestroIte.NMSINIES + '<br/>';
                }
				if(false && siniestroIte.AUTMEDIC!='S')
				{
					result = result + 'El m&eacute;dico no autoriza el siniestro ' + siniestroIte.NMSINIES + '<br/>';
				}
			}
			for(i=0;i<siniestros.length;i++)
            {
				var siniestroIte = siniestros[i];
				if(factura['AUTRECLA'+siniestroIte.NMSINIES]!='S')
				{
					result = result + 'Reclamaciones no autoriza la factura para el siniestro ' + siniestroIte.NMSINIES + '<br/>';
				}
				if(false && factura['AUTMEDIC'+siniestroIte.NMSINIES]!='S')
				{
					result = result + 'El m&eacute;dico no autoriza la factura para el siniestro ' + siniestroIte.NMSINIES + '<br/>';
				}
            }
			if(!esHospital)
			{
				for(i=0;i<siniestros.length;i++)
	            {
	                var siniestroIte = siniestros[i];
	                var conceptosSiniestro = _p12_llist1[i];
	                var j;
	                for(j=0;j<conceptosSiniestro.length;j++)
	                {
	                	var conceptoSiniestroIte = conceptosSiniestro[j];
	                	/*if(conceptoSiniestroIte.AUTRECLA!='S')
	                    {
	                        result = result + 'Reclamaciones no autoriza el concepto \'' + conceptoSiniestroIte.OTVALOR + '\' del siniestro ' + siniestroIte.NMSINIES + '<br/>';
	                    }*/
	                    if(false && conceptoSiniestroIte.AUTMEDIC!='S')
	                    {
	                        result = result + 'El m&eacute;dico no autoriza el concepto \'' + conceptoSiniestroIte.OTVALOR + '\' del siniestro ' + siniestroIte.NMSINIES + '<br/>';
	                    }
	                }
	            }
			}
		}
	}
	else
	{
		debug('validando reembolso');
		var facturas = _p12_slist1;
		debug('facturas a validar:',facturas);
		var i;
		for(i=0;i<facturas.length;i++)
		{
			var facturaIte = facturas[i];
			
			if(+ facturaIte.PTIMPORT <= 0){
				result = result + 'Verifica el Importe ' + facturaIte.NFACTURA + '<br/>';
			}
			
			if(+facturaIte.DCTONUEX > +facturaIte.DESCNUME){
				result = result + 'Verifica el Descuento ' + facturaIte.NFACTURA + '<br/>';
			}
			
			if(facturaIte.AUTRECLA!='S')
            {
                result = result + 'Reclamaciones no autoriza la factura ' + facturaIte.NFACTURA + '<br/>';
            }
            if(facturaIte.AUTMEDIC!='S')
            {
                result = result + 'El m&eacute;dico no autoriza la factura ' + facturaIte.NFACTURA + '<br/>';
            }
            
            
            
            var conceptos = _p12_llist1[i];
            //console.log(conceptos);
            var j;
            for(j=0;j<conceptos.length;j++)
            {
            	var conceptosInt = conceptos[j];
            	
            	if(+ conceptosInt.PTIMPORT <= 0){
    				result = result + 'Verifica el Importe del concepto ' + conceptosInt.NFACTURA + '<br/>';
    			}
    			
    			if(+conceptosInt.DCTOIMEX > +conceptosInt.DESTOIMP){
    				result = result + 'Verifica el Descuento ' + conceptosInt.NFACTURA + '<br/>';
    			}
    			if(+conceptosInt.PTPCIOEX > +conceptosInt.PTPRECIO){
    				result = result + 'Verifica el Precio ' + conceptosInt.NFACTURA + '<br/>';
    			}
            	
            }
		}
	}
	return result;
}
////// funciones //////

</script>
<div id="_p12_divpri" style="height:2400px;"></div>