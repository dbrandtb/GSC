<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
///////////////////////
////// variables //////

/*
smap1:
    CDRAMO: "2"
    CDTIPSIT: "SL"
    CDUNIECO: "1006"
    DSCOMENT: ""
    DSTIPSIT: "SALUD VITAL"
    ESTADO: "M"
    FEEMISIO: "22/01/2014"
    FEINIVAL: "22/01/2014"
    NMPOLIEX: "1006213000024000000"
    NMPOLIZA: "24"
    NMSUPLEM: "245668019180000000"
    NSUPLOGI: "1"
    NTRAMITE: "678"
    PRIMA_TOTAL: "12207.37"
*/
//Obtenemos el contenido en formato JSON de la propiedad solicitada:
var _8_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;

var _8_formLectura;
var _8_panelPri;
var _8_panelEndoso;

var _8_urlLoaderLectura = '<s:url namespace="/consultasPoliza" action="consultaDatosPoliza" />';

var _8_urlGuardar = '<s:url namespace="/endosos" action="guardarEndosoReexpedicion" />';

debug('_8_smap1:',_8_smap1);
////// variables //////
///////////////////////

Ext.onReady(function()
{
    /////////////////////
    ////// modelos //////
    Ext.define('_8_ModeloPoliza',
    {
    	extend  : 'Ext.data.Model'
    	,fields : [ <s:property value="imap1.fieldsFormLectura" /> ]
    });
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    Ext.define('_8_PanelEndoso',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_8_PanelEndoso initComponent');
            Ext.apply(this,
            {
                title     : 'Datos del endoso'
                ,layout   :
                {
                	type     : 'table'
                	,columns : 2
                }
                ,defaults :
                {
                    style : 'margin : 5px;'
                }
                ,items    :
                [ <s:property value="imap1.itemsDatosEndoso" /> ]
            });
            this.callParent();
        }
    });
    
    Ext.define('_8_FormLectura',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_8_FormLectura initComponent');
            Ext.apply(this,
            {
                title      : 'Datos de la p&oacute;liza'
                ,model     : '_8_ModeloPoliza'
                ,defaults  :
                {
                    style : 'margin : 5px;'
                }
                ,layout    :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,items     : [ <s:property value="imap1.itemsPanelLectura" /> ]
            });
            this.callParent();
        }
    });
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    _8_fieldFechaEndoso=Ext.create('Ext.form.field.Date',
    {
        format      : 'd/m/Y'
        ,fieldLabel : 'Fecha'
        ,allowBlank : false
        ,value      : new Date()
        ,name       : 'fecha_endoso'
    });
    _8_panelEndoso   = new _8_PanelEndoso();
    _8_formLectura   = new _8_FormLectura();
    
    _8_panelPri=Ext.create('Ext.panel.Panel',
    {
        renderTo     : '_8_divPri'
        ,defaults    :
        {
            style : 'margin : 5px;'
        }
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text     : 'Confirmar endoso'
                ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                ,handler : _8_confirmar
            }
        ]
        ,items       :
        [
            _8_formLectura
            ,_8_panelEndoso
        ]
    });
    ////// contenido //////
    ///////////////////////
    
    ////////////////////
    ////// loader //////
    _8_panelPri.setLoading(true);
    Ext.Ajax.request(
    {
    	url      : _8_urlLoaderLectura
    	,params  :
    	{
    		'params.cdunieco'  : _8_smap1.CDUNIECO
    		,'params.cdramo'   : _8_smap1.CDRAMO
    		,'params.estado'   : _8_smap1.ESTADO
    		,'params.nmpoliza' : _8_smap1.NMPOLIZA
    	}
    	,success : function(response)
    	{
    		_8_panelPri.setLoading(false);
    		var json = Ext.decode(response.responseText);
    		debug('respuesta:',json);
    		if(json.success==true)
    		{
    			_8_formLectura.loadRecord(new _8_ModeloPoliza(json.datosPoliza));
    		}
    	}
        ,failure : function()
        {
        	_8_panelPri.setLoading(false);
        	mensajeError('Error al cargar los datos de la p&oacute;liza');
        }
    });
    ////// loader //////
    ////////////////////
});

///////////////////////
////// funciones //////
function _8_confirmar()
{
    debug('_8_confirmar');
    
    var valido=true;
    
    if(valido)
    {
        valido=_8_panelEndoso.isValid();
        if(!valido)
        {
            datosIncompletos();
        }
    }
    
    var motivo = _8_panelEndoso.items.items[1].getValue();
    var coment = _8_panelEndoso.items.items[3].getValue();
    
    if(valido)
    {
    	valido=motivo&&motivo*1>0;
    	if(!valido)
    	{
    		mensajeWarning('El motivo es requerido');
    	}
    }
    
    if(valido)
    {
    	if(motivo==30)//el 30 es el motivo Otros en TRAZREEXP y en TRAZCANC
    	{
    		if(!coment||coment.length==0)
    		{
    			valido = false;
    		}
    	}
    	if(!valido)
    	{
    		mensajeWarning('Si el motivo es "Otros", se debe especificar en el campo comentarios');
    	}
    }
    
    if(valido)
    {
        var json=
        {
            smap1  : _8_smap1
            ,smap2 : _8_panelEndoso.getValues()
            ,smap3 : _8_formLectura.getValues()
        }
        debug('datos que se enviaran:',json);
        _8_panelPri.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _8_urlGuardar
            ,jsonData : json
            ,success  : function(response)
            {
                _8_panelPri.setLoading(false);
                json=Ext.decode(response.responseText);
                debug('datos recibidos:',json);
                if(json.success==true)
                {
                    mensajeCorrecto('Endoso generado',json.mensaje);
                    
                    //////////////////////////////////
                    ////// usa codigo del padre //////
                    /*//////////////////////////////*/
                    marendNavegacion(2);
                    /*//////////////////////////////*/
                    ////// usa codigo del padre //////
                    //////////////////////////////////
                }
                else
                {
                    mensajeError(json.error);
                }
            }
            ,failure  : function()
            {
                _8_panelPri.setLoading(false);
                errorComunicacion();
            }
        });
    }
};
////// funciones //////
///////////////////////
</script>
<div id="_8_divPri" style="height:1000px;"></div>