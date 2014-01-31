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
var _10_smap1 = <s:property value='%{getSmap1().toString().replace("=",":\'").replace(",","\',").replace("}","\'}")}' />;

var _10_formLectura;
var _10_formAgente;
var _10_panelPri;
var _10_panelEndoso;
var _10_fieldFechaEndoso;
var _10_storeAgentes;
var _10_gridAgentes;

var _10_urlGuardar     = '<s:url namespace="/endosos" action="guardarEndosoAgente"       />';
var _10_urlLoadAgentes = '<s:url namespace="/endosos" action="cargarAgentesEndosoAgente" />';

debug('_10_smap1:',_10_smap1);
////// variables //////
///////////////////////

Ext.onReady(function()
{
	///////////////////////
	////// overrides //////
	Ext.override(Ext.form.field.ComboBox,
	{
		initComponent : function()
		{
			debug('Ext.form.field.ComboBox initComponent');
			Ext.apply(this,
			{
				forceSelection : 'true'
			});
			return this.callParent();
		}
	});
	////// overrides //////
	///////////////////////
	
    /////////////////////
    ////// modelos //////
    Ext.define('_10_ModeloAgente',
    {
    	extend  : 'Ext.data.Model'
    	,fields : [ <s:property value="imap1.fieldsModelo" /> ]
    });
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    _10_storeAgentes=Ext.create('Ext.data.Store',
    {
		autoLoad : false
    	,model   : '_10_ModeloAgente'
        ,proxy   :
        {
            type    : 'ajax'
            ,reader : 'json'
            ,data   : []
        }
    });
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    Ext.define('_10_GridAgentes',
    {
    	extend         : 'Ext.grid.Panel'
    	,initComponent : function()
    	{
    		debug('_10_GridAgentes initComponent');
    		Ext.apply(this,
    		{
    			title    : 'Agente(s)'
    			,store   : _10_storeAgentes
    			,columns : [ <s:property value="imap1.columnsGrid" /> ]
    		});
    		this.callParent();
    	}
    });
    
    Ext.define('_10_PanelEndoso',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_10_PanelEndoso initComponent');
            Ext.apply(this,
            {
                title     : 'Datos del endoso'
                ,defaults :
                {
                    style : 'margin : 5px;'
                }
                ,items    :
                [
                    _10_fieldFechaEndoso
                ]
            });
            this.callParent();
        }
    });
    
    Ext.define('_10_FormAgente',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_10_FormAgente initComponent');
            Ext.apply(this,
            {
                title      : 'Agente nuevo'
                ,defaults  :
                {
                    style : 'margin : 5px;'
                }
                ,layout    :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,items     :
                [
                     <s:property value="imap1.comboAgentes" />
                ]
                ,listeners :
                {
                    afterrender : function(me)
                    {
                    }
                }
            });
            this.callParent();
        }
    });
    
    Ext.define('_10_FormLectura',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_10_FormLectura initComponent');
            Ext.apply(this,
            {
                title      : 'Datos de la p&oacute;liza'
                ,defaults  :
                {
                    style : 'margin : 5px;'
                }
                ,layout    :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,listeners :
                {
                    afterrender : heredarPanel
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
    _10_fieldFechaEndoso=Ext.create('Ext.form.field.Date',
    {
        format      : 'd/m/Y'
        ,fieldLabel : 'Fecha'
        ,allowBlank : false
        ,value      : new Date()
        ,name       : 'fecha_endoso'
    });
    _10_panelEndoso = new _10_PanelEndoso();
    _10_formAgente  = new _10_FormAgente();
    _10_formLectura = new _10_FormLectura();
    _10_gridAgentes = new _10_GridAgentes();
    
    _10_panelPri=Ext.create('Ext.panel.Panel',
    {
        renderTo     : '_10_divPri'
        ,defaults    :
        {
            style : 'margin : 5px;'
        }
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text      : 'Confirmar endoso'
                ,icon     : '${ctx}/resources/fam3icons/icons/key.png'
                ,handler  : _10_confirmar
            }
        ]
        ,items       :
        [
            _10_formLectura
            ,_10_gridAgentes
            ,_10_formAgente
            ,_10_panelEndoso
        ]
    });
    ////// contenido //////
    ///////////////////////
    
    ////////////////////
    ////// loader //////
    _10_panelPri.setLoading(true);
    Ext.Ajax.request(
    {
    	url      : _10_urlLoadAgentes
    	,params  :
    	{
    		'smap1.cdunieco'  : _10_smap1.CDUNIECO
    		,'smap1.cdramo'   : _10_smap1.CDRAMO
    		,'smap1.estado'   : _10_smap1.ESTADO
    		,'smap1.nmpoliza' : _10_smap1.NMPOLIZA
    		,'smap1.nmsuplem' : _10_smap1.NMSUPLEM
    	}
    	,success : function (response)
    	{
    		_10_panelPri.setLoading(false);
    		var json=Ext.decode(response.responseText);
    		debug('agentes cargados:',json);
    		if(json.success==true)
    		{
    			/*
    			a.cdunieco, a.cdramo, a.estado, a.nmpoliza, a.cdagente, a.nmsuplem, a.status, a.cdtipoag, porredau, a.porparti,nombre
    			*/
    			_10_storeAgentes.add(json.slist1);
    		}
    		else
    		{
    			mensajeError(json.error);
    			//////////////////////////////////
                ////// usa codigo del padre //////
                /*//////////////////////////////*/
                marendNavegacion(4);
                /*//////////////////////////////*/
                ////// usa codigo del padre //////
                //////////////////////////////////
    		}
    	}
        ,failure : function()
        {
        	_10_panelPri.setLoading(false);
        	errorComunicacion();
        }
    });
    ////// loader //////
    ////////////////////
});

///////////////////////
////// funciones //////
function _10_confirmar()
{
    debug('_10_confirmar');
    
    var valido=true;
    
    if(valido)
    {
        valido=_10_formAgente.isValid()&&_10_panelEndoso.isValid();
        if(!valido)
        {
            datosIncompletos();
        }
    }
    
    if(valido)
    {
        /*
        a.cdunieco, a.cdramo, a.estado, a.nmpoliza, a.cdagente, a.nmsuplem, a.status, a.cdtipoag, porredau, a.porparti,nombre,cdsucurs,nmcuadro
        */
        var json=
        {
            smap1  : _10_smap1
            ,smap2 :
            {
                fecha_endoso : Ext.Date.format(_10_fieldFechaEndoso.getValue(),'d/m/Y')
                ,agente      : _10_formAgente.items.items[0].getValue()
                ,nmcuadro    : _10_storeAgentes.getAt(0).get('NMCUADRO')
                ,cdsucurs    : _10_storeAgentes.getAt(0).get('CDSUCURS')
            }
        }
        debug('datos que se enviaran:',json);
        _10_panelPri.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _10_urlGuardar
            ,jsonData : json
            ,success  : function(response)
            {
                _10_panelPri.setLoading(false);
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
                _10_panelPri.setLoading(false);
                errorComunicacion();
            }
        });
    }
};
////// funciones //////
///////////////////////
</script>
<div id="_10_divPri" style="height:1000px;"></div>