<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// variables //////
var _p16_formulario;
var _p16_gridPolizas;
var _p16_urlRegeneraMedicinaPreventiva = '<s:url namespace="/reexpediciondocumentos" action="regeneraMedicinaPreventiva" />';
////// variables //////
Ext.onReady(function()
{
	////// modelos //////
	Ext.define('_p16_modeloPoliza',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="itemMap.fieldsModeloPoliza" />]
	});
	////// modelos //////
	
	////// stores //////
	////// stores //////
	
	////// componentes //////
	////// componentes //////
	
	////// contenido //////
	_p16_formulario = Ext.create('Ext.form.Panel',
	{
		title        : 'B&uacute;squeda'
		,layout      :
		{
			type     : 'table'
			,columns : 2
		}
		,defaults    :
        {
            style : 'margin : 5px;'
        }
		,items       : [ <s:property value="itemMap.itemsDeFormulario" />]
		,buttonAlign : 'center'
		,buttons     :
		[
		    {
		    	text     : 'Limpiar'
		    	,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
		    	,handler : function()
		    	{
		    		this.up().up().getForm().reset();
		    	}
		    }
		    ,{
		    	text     : 'Buscar'
		    	,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
		    	,handler : _p16_buscarPolizas
		    }
		]
	});
	
	_p16_gridPolizas = Ext.create('Ext.grid.Panel',
	{
		title       : 'P&oacute;lizas'
		,store      : Ext.create('Ext.data.Store',
		{
			model : '_p16_modeloPoliza'
		})
		,columns    :
		[
		    <s:property value="itemMap.columnasGridPolizas" />
		    ,{
		    	xtype  : 'actioncolumn'
		    	,items : [ <s:property value="itemMap.botonesGridPolizas" /> ]
		    }
		]
		,minHeight  : 150
		,maxHeight  : 300
		,viewConfig : viewConfigAutoSize
	});
	
	Ext.create('Ext.panel.Panel',
	{
		defaults  :
		{
			style : 'margin : 5px;'
		}
	    ,renderTo : '_p16_divpri'
	    ,border   : 0
	    ,items    :
	    [
	    	_p16_formulario
	    	,_p16_gridPolizas
	    ]
	});
	////// contenido //////
});
////// getters //////
function _p16_getFormulario()
{
	return _p16_formulario;
}

function _p16_getGridPolizas()
{
	return _p16_gridPolizas;
}
////// getters //////

////// funciones //////
function _p16_regenerarMedicinaPreventiva(grid,rowIndex,colIndex)
{
	debug('>_p16_regenerarMedicinaPreventiva row,col: ',rowIndex,colIndex);
	var record  = grid.getStore().getAt(rowIndex);
	debug('record:',record);
	grid.setLoading(true);
	Ext.Ajax.request(
	{
		url       : _p16_urlRegeneraMedicinaPreventiva
		,jsonData :
		{
			stringMap : record.getData()
		}
	    ,success  : function(response)
	    {
	    	grid.setLoading(false);
	    	var jsonResponse = Ext.decode(response.responseText);
	    	if(jsonResponse.success)
	    	{
	    		mensajeCorrecto('Aviso',jsonResponse.mensaje);
	    	}
	    	else
	    	{
	    		mensajeError(jsonResponse.mensaje);
	    	}
	    }
	    ,failure  : function()
	    {
	    	grid.setLoading(false);
	    	errorComunicacion();
	    }
	});
	debug('<_p16_regenerarMedicinaPreventiva');
}
function _p16_buscarPolizas()
{
	debug('>_p16_buscarPolizas');
	var form = _p16_getFormulario();
	var valido = true;
	if(valido)
	{
		valido = form.getForm().isValid();
		if(!valido)
		{
			datosIncompletos();
		}
	}
	if(valido)
	{
		_p16_getGridPolizas().getStore().removeAll();
		consultaDinamica('PKG_CONSULTA.P_OBTIENE_DATOS_REEXPED_DOC',form.getValues(),form,function(lista)
		{
			debug('>callback');
			_p16_getGridPolizas().getStore().loadData(lista);
			debug('<callback');
		});
	}
	debug('<_p16_buscarPolizas');
}
////// funciones //////
</script>
</head>
<body>
<div id='_p16_divpri'></div>
</body>
</html>