<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<script>

////// variables //////

var _p12_smap   = <s:property value='smapJson'   escapeHtml='false' />;
var _p12_smap2  = <s:property value='smap2Json'  escapeHtml='false' />;
var _p12_smap3  = <s:property value='smap3Json'  escapeHtml='false' />;
var _p12_slist1 = <s:property value='slist1Json' escapeHtml='false' />;//facturas(REEMBOLSO)/siniestros(P DIRECTO)
var _p12_slist2 = <s:property value='slist2Json' escapeHtml='false' />;//copago/deducible (TODOS)
var _p12_slist3 = <s:property value='slist3Json' escapeHtml='false' />;//proveedores (REEMBOLSO)
var _p12_llist1 = <s:property value='llist1Json' escapeHtml='false' />;//lista de lista de conceptos (TODOS)
var _p12_lhosp  = <s:property value='lhospJson'  escapeHtml='false' />;//lista de totales hopitalizacion
var _p12_lpdir  = <s:property value='lpdirJson'  escapeHtml='false' />;//lista de datos pdirecto
var _p12_lprem  = <s:property value='lpremJson'  escapeHtml='false' />;//lista de datos preembolso
debug('_p12_smap:'   , _p12_smap);
debug('_p12_smap2:'  , _p12_smap2);
debug('_p12_smap3:'  , _p12_smap3);
debug('_p12_slist1:' , _p12_slist1);
debug('_p12_slist2:' , _p12_slist2);
debug('_p12_slist3:' , _p12_slist3);
debug('_p12_llist1:' , _p12_llist1);
debug('_p12_lhosp:'  , _p12_lhosp);
debug('_p12_lpdir:'  , _p12_lpdir);
debug('_p12_lprem:'  , _p12_lprem);

var _p12_urlObtenerFacturasTramite   = '<s:url namespace="/siniestros" action="obtenerFacturasTramite"   />';
var _p12_urlObtenerSiniestrosTramite = '<s:url namespace="/siniestros" action="obtenerSiniestrosTramite" />';
var _p12_urlObtenerDatosProveedor    = '<s:url namespace="/siniestros" action="obtenerDatosProveedor"    />';
var _p12_urlObtenerConceptosCalculo  = '<s:url namespace="/siniestros" action="obtenerConceptosCalculo"  />';

var _p12_formTramite;
var _p12_formFactura;
var _p12_formProveedor;
var _p12_formSiniestro;
//var _p12_gridFacturas;
//var _p12_storeFacturas;
//var _p12_gridSiniestros;
//var _p12_storeSiniestros;
var _p12_panelCalculo;
//var _p12_gridConceptos;
var _p12_paneles = [];
//var _p12_storeConceptos;

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
            	var importe   = _p12_lhosp[indice].PTIMPORT*1.0;
            	var descuento = _p12_lhosp[indice].DESTO*1.0;
            	var iva       = _p12_lhosp[indice].IVA*1.0;
            	debug('importe'   , importe);
            	debug('descuento' , descuento);
            	debug('iva'       , iva);
            	var subttDesc = importe - descuento;
            	var deducible = _p12_slist2[indice].DEDUCIBLE*1.0;
            	var subttDedu = subttDesc - deducible;
            	var copago    = _p12_slist2[indice].COPAGO*1.0;
            	var tipcopag  = _p12_slist2[indice].TIPCOPAG;
            	if(tipcopag=='I')
            	{
            		var copagoaplica = copago;
            	}
            	else if(tipcopag=='P')
            	{
            		var copagoaplica = subttDedu*(copago/100.0);
            	}
            	else
            	{
            		var copagoaplica = 0.0;
            	}
            	var total = subttDedu - ( copagoaplica + iva );
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
                            ,fieldLabel : 'Copago'
                            ,value      : copagoaplica
                            ,valueToRaw : function(value)
                            {
                                return Ext.util.Format.usMoney(value);
                            }
                        }
            	        ,{
                            xtype       : 'displayfield'
                            ,labelWidth : 200
                            ,fieldLabel : 'IVA'
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
                        ,dataIndex : 'PTIMPORTAJUSTADO'
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
            var ptimpoajus  = _p12_lprem[indice].SUBTOTAL*1.0;
            var destopor    = _p12_slist1[indice].DESCPORC*1.0;
            var destoimp    = _p12_slist1[indice].DESCNUME*1.0;
            var destoaplica = (ptimpoajus*(_p12_slist1[indice].DESCPORC/100.0)) + destoimp;
            var subttdesc   = ptimpoajus-destoaplica;
            var deducible   = _p12_slist2[indice].DEDUCIBLE;
            var subttdeduc  = subttdesc-deducible;
            var copago      = _p12_slist2[indice].COPAGO*1.0;
            var tipcopag    = _p12_slist2[indice].TIPCOPAG;
            if(tipcopag=='I')
            {
                var copagoaplica = copago;
            }
            else if(tipcopag=='P')
            {
                var copagoaplica = subttdeduc*(copago/100.0);
            }
            else
            {
                var copagoaplica = 0.0;
            }
            var total = subttdeduc - copagoaplica;
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
                        ,fieldLabel : 'Copago'
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
    	]
    }));
	////// componentes //////
	
	////// contenido //////
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
	        _p12_formTramite
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

////// funciones //////

</script>
<div id="_p12_divpri" style="height:1500px;"></div>