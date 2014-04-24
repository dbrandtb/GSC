<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// variables //////
var _p15_form;

var _p15_urlHabilitarRecibos = '<s:url namespace="/endosos" action="habilitarRecibosSubsecuentes" />';
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
	_p15_form = Ext.create('Ext.form.Panel',
	{
        title        : 'Habilitar recibos subsecuentes'
        ,layout      :
        {
            type     : 'table'
            ,columns : 2
        }
        ,defaults    :
        {
            style : 'margin:5px;'
        }
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
            	text     : 'Habilitar recibos'
            	,icon    : '${ctx}/resources/fam3icons/icons/folder_go.png'
            	,handler : _p15_botonHabilitarHandler
            }
        ]
        ,items       :
        [
            {
                xtype       : 'datefield'
                ,itemId     : '_p15_fechaInicio'
                ,name       : 'fechaInicio'
                ,fieldLabel : 'Fecha de inicio'
                ,value      : new Date()
                ,allowBlank : false
            }
            ,{
                xtype       : 'datefield'
                ,itemId     : '_p15_fechaFin'
                ,name       : 'fechaFin'
                ,fieldLabel : 'Fecha de fin'
                ,readOnly   : true
            }
            ,<s:property value="imap1.itemsDeFormulario" />
        ]
	});
	
	Ext.create('Ext.panel.Panel',
	{
		renderTo  : '_p15_divpri'
		,defaults :
        {
            style : 'margin:5px;'
        }
	    ,border   : 0
		,items    :
		[
		    _p15_form
		]
	});
	////// contenido //////
	
	////// eventos //////
	_p15_getFechaInicio().addListener('change',_p15_fechaInicioChange);
	_p15_fechaInicioChange(_p15_getFechaInicio(),new Date(),null);
	////// eventos //////
});

////// getters //////
function _p15_getFechaInicio()
{
	return Ext.ComponentQuery.query('#_p15_fechaInicio')[0];
}

function _p15_getFechaFin()
{
    return Ext.ComponentQuery.query('#_p15_fechaFin')[0];
}
function _p15_getFormulario()
{
	return _p15_form;
}
////// getters //////

////// funciones //////
function _p15_botonHabilitarHandler()
{
	debug('>_p15_botonHabilitarHandler');
	var form = _p15_getFormulario();
	var valido = form.isValid();
	if(!valido)
	{
		datosIncompletos();
	}
	if(valido)
	{
		form.setLoading(true);
		Ext.Ajax.request(
		{
			url       : _p15_urlHabilitarRecibos
			,jsonData :
			{
				smap1 : form.getValues()
			}
		    ,success  : function(response)
		    {
		    	form.setLoading(false);
		    	var response = Ext.decode(response.responseText);
		    	debug('response:',response);
		    	if(response.success)
		    	{
		    		mensajeCorrecto('Recibos habilitados',response.mensaje);
		    	}
		    	else
		    	{
		    		mensajeError(response.mensaje);
		    	}
		    }
		    ,failure  : function()
		    {
		    	form.setLoading(false);
		    	errorComunicacion();
		    }
		});
	}
	debug('<_p15_botonHabilitarHandler');
}
function _p15_fechaInicioChange(comp,newVal,oldVal)
{
	debug('>_p15_fechaInicioChange',newVal,oldVal);
	var fechaFin = Ext.Date.add(newVal,Ext.Date.MONTH,1);
	debug('mas mes:',fechaFin);
	var ultimoDia = Ext.Date.getDaysInMonth(fechaFin);
	debug('ultimo dia:',ultimoDia);
	debug('dias:',fechaFin.getDate());
	fechaFin=Ext.Date.add(fechaFin,Ext.Date.DAY,ultimoDia-fechaFin.getDate());
	_p15_getFechaFin().setValue(fechaFin);
	debug('<_p15_fechaInicioChange',fechaFin);
}
////// funciones //////
</script>
</head>
<body>
<div id="_p15_divpri" style="height:600px;"></div>
</body>
</html>