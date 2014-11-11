<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p29_urlPantallaCliente                = '<s:url namespace="/catalogos" action="includes/personasLoader"           />';
var _p29_urlCotizacionAutoIndividual       = '<s:url namespace="/emision"   action="cotizacionAutoIndividual"          />';
var _p29_urlCargarDatosComplementarios     = '<s:url namespace="/emision"   action="cargarDatosComplementariosAutoInd" />';
var _p29_urlCargarTvalosit                 = '<s:url namespace="/emision"   action="cargarValoresSituacion"            />';
var _p29_urlCargarRetroactividadSuplemento = '<s:url namespace="/emision"   action="cargarRetroactividadSuplemento"    />';
var _p29_urlMovimientoMpoliper             = '<s:url namespace="/emision"   action="movimientoMpoliper"                />';
////// urls //////

////// variables //////
var _p29_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p29_smap1:',_p29_smap1);

var _p29_polizaAdicionalesItems = null;
var _p29_adicionalesItems       = null;
var _p22_parentCallback         = false;
////// variables //////

Ext.onReady(function()
{
	////// modelos //////
	Ext.define('_p29_polizaModelo',
	{
	    extend  : 'Ext.data.Model'
	    ,fields :
	    [
	        {
	            type        : 'date'
	            ,dateFormat : 'd/m/Y'
	            ,name       : 'feini'
	        }
	        ,{
                type        : 'date'
                ,dateFormat : 'd/m/Y'
                ,name       : 'fefin'
            }
	        ,<s:property value="imap.polizaFields" />
	        ,<s:property value="imap.agenteFields" />
	        <s:if test='%{getImap().get("polizaAdicionalesFields")!=null}'>
                ,<s:property value="imap.polizaAdicionalesFields" />
            </s:if>
	    ]
	});
	////// modelos //////
	
	////// stores //////
	////// stores //////
	
	////// componentes //////
	_p29_polizaAdicionalesItems =
	[
	    <s:property value="imap.agenteItems" />
	    ,{
	        xtype       : 'datefield'
	        ,format     : 'd/m/Y'
	        ,fieldLabel : 'INICIO DE VIGENCIA'
	        ,name       : 'feini'
	        ,listeners  : { change : _p29_fefinChange }
	        ,style      : 'margin:5px;'
	    }
	    ,{
	        xtype       : 'datefield'
	        ,format     : 'd/m/Y'
	        ,fieldLabel : 'FIN DE VIGENCIA'
	        ,name       : 'fefin'
	        ,readOnly   : true
            ,style      : 'margin:5px;'
	    }
	];
	<s:if test='%{getImap().get("polizaAdicionalesItems")!=null}'>
	    var aux=[<s:property value="imap.polizaAdicionalesItems" />];
	    for(var i=0;i<_p29_polizaAdicionalesItems.length;i++)
	    {
	        aux.push(_p29_polizaAdicionalesItems[i]);
	    }
	    _p29_polizaAdicionalesItems = aux;
	</s:if>
	
	_p29_adicionalesItems = [];
	<s:if test='%{getImap().get("adicionalesItems")!=null}'>
	    _p29_adicionalesItems = [<s:property value="imap.adicionalesItems" />];
	</s:if>
	////// componentes //////
	
	////// contenido //////
	Ext.create('Ext.panel.Panel',
	{
	    itemId    : '_p29_panelpri'
        ,renderTo : '_p29_divpri'
	    ,defaults : { style : 'margin:5px;' }
	    ,items    :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	            itemId    : '_p29_polizaForm'
	            ,title    : 'DATOS DE P&Oacute;LIZA'
	            ,defaults : { style : 'margin:5px;' }
	            ,layout   :
	            {
	                type     : 'table'
	                ,columns : 2
	                ,tdAttrs : { valign : 'top' }
	            }
	            ,items    :
	            [
	                {
	                    xtype  : 'fieldset'
	                    ,title : '<span style="font:bold 14px Calibri;">DATOS GENERALES</span>'
	                    ,items : [<s:property value="imap.polizaItems" />]
	                }
	                ,{
	                    xtype  : 'fieldset'
	                    ,title : '<span style="font:bold 14px Calibri;">DATOS ADICIONALES</span>'
	                    ,items : _p29_polizaAdicionalesItems
	                }
	            ]
	        })
	        ,Ext.create('Ext.form.Panel',
	        {
	            itemId    : '_p29_adicionalesForm'
	            ,title    : 'DATOS ADICIONALES DE INCISO'
	            ,defaults : { style : 'margin:5px;' }
	            ,layout   :
	            {
	                type     : 'table'
	                ,columns : 2
	            }
	            ,items    : _p29_adicionalesItems
	        })
	        ,Ext.create('Ext.panel.Panel',
	        {
	            itemId      : '_p29_clientePanel'
	            ,title      : 'CLIENTE'
	            ,height     : 300
	            ,autoScroll : true
	            ,loader     :
	            {
	                url       : _p29_urlPantallaCliente
	                ,scripts  : true
	                ,autoLoad : false
	            }
	        })
	        ,Ext.create('Ext.panel.Panel',
	        {
	            itemId       : '_p29_panelBotones'
	            ,border      : 0
	            ,buttonAlign : 'center'
	            ,buttons     :
	            [
	                {
	                    itemId   : '_p29_botonEmitir'
	                    ,text    : 'Emitir'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
	                    ,handler : _p29_emitirClic
	                }
	                ,{
	                    text     : 'Nueva'
	                    ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
	                    ,handler : _p29_nuevaClic
	                }
	            ]
	        })
	    ]
	});
	////// contenido //////
	
	////// custom //////
	_fieldByName('porparti').setMaxValue(99);
	////// custom //////
	
	////// loaders //////
	Ext.Ajax.request(
	{
	    url     : _p29_urlCargarDatosComplementarios
	    ,params :
	    {
	        'smap1.cdunieco'  : _p29_smap1.cdunieco
	        ,'smap1.cdramo'   : _p29_smap1.cdramo
	        ,'smap1.estado'   : _p29_smap1.estado
	        ,'smap1.nmpoliza' : _p29_smap1.nmpoliza
	    }
	    ,success : function(response)
	    {
	        var json = Ext.decode(response.responseText);
	        debug('### cargar datos complementarios:',json);
	        if(json.exito)
	        {
	            var form   = _fieldById('_p29_polizaForm');
	            var record = new _p29_polizaModelo(json.smap1);
	            debug('record:',record);
	            form.loadRecord(record);
	            
	            _fieldById('_p29_clientePanel').loader.load(
	            {
	                params:
	                {
	                    'smap1.cdperson' : json.smap1.cdperson
	                }
	            });
	            
	            _p22_parentCallback = _p29_personaSaved;
	        }
	        else
	        {
	            mensajeError(json.respuesta);
	        }
	    }
	    ,failure : errorComunicacion
	});
	
	Ext.Ajax.request(
	{
	    url     : _p29_urlCargarTvalosit
	    ,params :
	    {
	        'smap1.cdunieco'  : _p29_smap1.cdunieco
	        ,'smap1.cdramo'   : _p29_smap1.cdramo
	        ,'smap1.estado'   : _p29_smap1.estado
	        ,'smap1.nmpoliza' : _p29_smap1.nmpoliza
	        ,'smap1.nmsituac' : '1'
	    }
	    ,success : function(response)
	    {
	        var json=Ext.decode(response.responseText);
	        debug('### tvalosit:',json);
	        if(json.exito)
	        {
	            var form = _fieldById('_p29_adicionalesForm');
	            for(var i in json.smap1)
	            {
	                var item = _fieldByName(i,form,true);
	                if(item)
	                {
	                    item.setValue(json.smap1[i]);
	                }
	            }

	            _p29_loadCallback();
	        }
	        else
	        {
	            mensajeError(json.respuesta);
	        }
	    }
	    ,failure : errorComunicacion
	});
	////// loaders //////
});

////// funciones //////
function _p29_fefinChange(me,newVal,oldVal)
{
    debug('>_p29_fefinChange:',newVal,oldVal,'DUMMY');
    debug('<_p29_fefinChange');
}

function _p29_nuevaClic()
{
    _fieldById('_p29_panelpri').setLoading(true);
    Ext.create('Ext.form.Panel').submit(
    {
        url     : _p29_urlCotizacionAutoIndividual
        ,params :
        {
            'smap1.cdramo'    : _p29_smap1.cdramo
            ,'smap1.cdtipsit' : _p29_smap1.cdtipsit
        }
        ,standardSubmit : true
    });
}

function _p29_loadCallback()
{
    var vigen = _fieldByLabel('VIGENCIA');
    vigen.hide();
    var feini = _fieldByName('feini');
    var fefin = _fieldByName('fefin');
    feini.on(
    {
        change : function(me,val)
        {
            try
            {
                fefin.setValue(Ext.Date.add(val,Ext.Date.DAY,vigen.getValue()))
            }
            catch(e)
            {
                debug(e);
            }
        }
    });
    
    Ext.Ajax.request(
    {
        url     : _p29_urlCargarRetroactividadSuplemento
        ,params :
        {
            'smap1.cdunieco'  : _p29_smap1.cdunieco
            ,'smap1.cdramo'   : _p29_smap1.cdramo
            ,'smap1.cdtipsup' : 1
            ,'smap1.cdusuari' : _p29_smap1.cdusuari
            ,'smap1.cdtipsit' : _p29_smap1.cdtipsit
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### obtener retroactividad:',json);
            if(json.exito)
            {
                feini.setMinValue(Ext.Date.add(new Date(),Ext.Date.DAY,(json.smap1.retroac-0)*-1));
                feini.setMaxValue(Ext.Date.add(new Date(),Ext.Date.DAY,json.smap1.diferi-0));
                feini.isValid();
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : errorComunicacion
    });
}

function _p29_personaSaved()
{
    debug('>_p29_personaSaved');
    Ext.Ajax.request(
    {
        url     : _p29_urlMovimientoMpoliper
        ,params :
        {
            'smap1.cdunieco'  : _p29_smap1.cdunieco
            ,'smap1.cdramo'   : _p29_smap1.cdramo
            ,'smap1.estado'   : _p29_smap1.estado
            ,'smap1.nmpoliza' : _p29_smap1.nmpoliza
            ,'smap1.nmsituac' : '0'
            ,'smap1.cdrol'    : '1'
            ,'smap1.cdperson' : _p22_fieldCdperson().getValue()
            ,'smap1.nmsuplem' : '0'
            ,'smap1.status'   : 'V'
            ,'smap1.nmorddom' : '1'
            ,'smap1.accion'   : 'I'
            ,'smap1.swexiper' : 'S'
        }
        ,success : function(response)
        {
            var json=Ext.decode(response.responseText);
            debug('### mpoliper:',json);
            if(json.exito==false)
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : errorComunicacion
    });
    debug('<_p29_personaSaved');
}

function _p29_emitirClic()
{
    debug('>_p29_emitirClic');
    
    var form1  = _fieldById('_p29_polizaForm');
    var form2  = _fieldById('_p29_adicionalesForm');
    var valido = form1.isValid()&&form2.isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
    if(valido)
    {
        valido = !Ext.isEmpty(_p22_fieldCdperson().getValue());
        if(!valido)
        {
            mensajeWarning('Debe buscar o agregar el cliente');
        }
    }
    
    if(valido)
    {
        mensajeCorrecto('P&oacute;liza emitida','P&oacute;liza emitida');
    }
    
    debug('<_p29_emitirClic');
}
////// funciones //////
</script>
</head>
<body><div id="_p29_divpri" style="height:1000px;"></div></body>
</html>