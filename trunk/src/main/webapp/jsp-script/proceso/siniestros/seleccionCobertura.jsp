<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consulta Clausulas</title>
<script type="text/javascript">

////// variables //////
var _selCobParams = <s:property value='%{getParams().toString().replace("=",":\'").replace(",","\',").replace("}","\'}")}' />;

var _selCobForm;

var _selCobUrlSave   = '<s:url namespace="/siniestros" action="guardarSeleccionCobertura" />';
var _selCobUrlAvanza = '<s:url namespace="/siniestros" action="afiliadosAfectados"        />';

debug('_selCobParams:',_selCobParams);
////// variables //////

Ext.onReady(function()
{
	
	////// modelos //////
	////// modelos //////
	
	////// stores //////
	////// stores //////
	
	////// componentes //////
	////// componentes //////
	
	////// contenido //////
	_selCobForm = Ext.create('Ext.form.Panel',
	{
		title        : 'Detalle de cobertura'
		,buttonAlign : 'center'
		,renderTo    : 'selcobdivpri'
		,items       : [ <s:property value="item" /> ]
	    ,listeners   : { afterrender : heredarPanel }
	    ,buttons     :
	    [
	        {
	        	icon     : '${ctx}/resources/fam3icons/icons/disk.png'
	        	,text    : 'Guardar'
	        	,handler : _selCobGuardar
	        }
	    ]
	});
	
	_selCobForm.items.items[2].on('blur',function()
	{
		var comboCoberturas = _selCobForm.items.items[3];
		comboCoberturas.getStore().load(
		{
			params :
			{
				'params.cdramo'  : _selCobForm.items.items[1].getValue()
				,'params.cdtipsit' : _selCobForm.items.items[2].getValue()
			}
		});
	});
	
	_selCobForm.items.items[3].on('focus',function()
    {
        var comboCoberturas = _selCobForm.items.items[3];
        comboCoberturas.getStore().load(
        {
            params :
            {
                'params.cdramo'  : _selCobForm.items.items[1].getValue()
                ,'params.cdtipsit' : _selCobForm.items.items[2].getValue()
            }
        });
    });
	////// contenido //////
	
});

////// funciones //////
function _selCobGuardar()
{
	var valido = true;
	
	if(valido)
	{
		valido = _selCobForm.isValid();
		if(!valido)
		{
			datosIncompletos();
		}
	}
	
	if(valido)
	{
		_selCobForm.setLoading(true);
		
		var json = _selCobForm.getValues();
		json['ntramite'] = _selCobParams.ntramite;
		
		Ext.Ajax.request(
		{
			url       : _selCobUrlSave
			,jsonData :
			{
				params : json
			}
		    ,success  : function(response)
		    {
		    	_selCobForm.setLoading(false);
		    	json = Ext.decode(response.responseText);
		    	debug('respuesta:',json);
		    	mensajeCorrecto(json.mensaje,json.mensaje,_selCobAvanza);
		    }
		    ,failure  : function(response)
		    {
                _selCobForm.setLoading(false);
		    	json = Ext.decode(response.responseText);
                debug('respuesta:',json);
                mensajeError(json.mensaje);
		    }
		});
	}
}

function _selCobAvanza()
{
	var params =
	{
		'params.ntramite' : _selCobParams.ntramite
	}
	Ext.create('Ext.form.Panel').submit(
	{
		url             : _selCobUrlAvanza
		,standardSubmit : true
		,params         : params
	});
}
////// funciones //////

</script>        
<%--<script type="text/javascript" src="${ctx}/js/proceso/siniestros/seleccionCobertura.js"></script>--%>
</head>
<body>
<div id="selcobdivpri" style="height:600px;"></div>
</body>
</html>