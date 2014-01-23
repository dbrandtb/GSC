<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
///////////////////////
////// variables //////
/*
_5_smap1:
    cdrfc=MAVA900817001,
    cdperson=511965,
    fenacimi=1990-08-17T00:00:00,
    sexo=H,
    Apellido_Materno=MAT,
    nombre=NOMBRE1,
    nombrecompleto=NOMBRE1  PAT MAT,
    nmsituac=1,
    segundo_nombre=null,
    Parentesco=T,
    CDTIPSIT=SL,
    NTRAMITE=615,
    CDUNIECO=1006,
    CDRAMO=2,
    NMSUPLEM=245673812540000005,
    NMPOLIZA=14,
    swexiper=S,
    NMPOLIEX=1006213000014000000,
    nacional=001,
    activo=true,
    NSUPLOGI=8,
    ESTADO=M,
    cdrol=2,
    tpersona=F,
    Apellido_Paterno=PAT
*/
var _5_smap1 = <s:property value='%{getSmap1().toString().replace("=",":\'").replace(",","\',").replace("}","\'}")}' />;

var _5_urlLoadMdomicil = '<s:url namespace="/"        action="cargarPantallaDomicilio" />';
var _5_urlGuardar      = '<s:url namespace="/endosos" action="guardarEndosoDomicilioFull" />';

var _5_panelLectura;
var _5_formDomicil;
var _5_panelEndoso;
var _5_panelTatriper;
var _5_fieldFechaEndoso;
var _5_panelPri;

debug('_5_smap1:',_5_smap1);
//////variables //////
//////////////////////

Ext.onReady(function()
{
	/////////////////////
	////// modelos //////
	Ext.define('_5_modeloDomicil',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="imap1.fieldsDomicil" /> ]
	});
    ////// modelos //////
	/////////////////////
	
	////////////////////
	////// stores //////
    ////// stores //////
	////////////////////
	
	/////////////////////////
	////// componentes //////
	Ext.define('_5_PanelTatriper',
	{
		extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_5_PanelTatriper initComponent');
            Ext.apply(this,
            {
                title   : 'Datos adicionales'
                ,layout :
                {
                	type     : 'table'
                	,columns : 2
                }
                ,items  : [ <s:property value="imap1.itemsTatriper" /> ]
            });
            this.callParent();
        }
	});
	
	Ext.define('_5_PanelEndoso',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_5_PanelEndoso initComponent');
            Ext.apply(this,
            {
                title     : 'Datos del endoso'
                ,defaults :
                {
                	style : 'margin : 5px;'
                }
                ,items    :
                [
                    _5_fieldFechaEndoso
                ]
            });
            this.callParent();
        }
    });
	
	Ext.define('_5_FormDomicil',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_5_FormDomicil initComponent');
            Ext.apply(this,
            {
                title   : 'Domicilio'
                ,model  : '_5_modeloDomicil'
               	,layout :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,items  : [ <s:property value="imap1.itemsDomicil" /> ]
            });
            this.callParent();
        }
    });
	
	Ext.define('_5_PanelLectura',
	{
		extend         : 'Ext.panel.Panel'
		,initComponent : function()
		{
			debug('_5_PanelLectura initComponent');
			Ext.apply(this,
			{
				title   : 'Datos de p&oacute;liza'
				,layout :
                {
                    type     : 'table'
                    ,columns : 2
                }
				,items  : [ <s:property value="imap1.itemsLectura" /> ]
			});
			this.callParent();
		}
	});
    ////// componentes //////
	/////////////////////////
	
	///////////////////////
	////// contenido //////
	_5_fieldFechaEndoso=Ext.create('Ext.form.field.Date',
	{
	    format      : 'd/m/Y'
	    ,fieldLabel : 'Fecha'
	    ,allowBlank : false
	    ,value      : new Date()
	    ,name       : 'fecha_endoso'
	});
	_5_panelLectura  = new _5_PanelLectura();
	_5_formDomicil   = new _5_FormDomicil();
	_5_panelEndoso   = new _5_PanelEndoso();
	_5_panelTatriper = new _5_PanelTatriper();
	
	_5_panelPri=Ext.create('Ext.panel.Panel',
	{
		renderTo  : '_5_divPri'
		,border   : 0
		,defaults :
		{
			style : 'margin : 5px;'
		}
	    ,items    :
	    [
	        _5_panelLectura
	        ,_5_formDomicil
	        ,_5_panelTatriper
	        ,_5_panelEndoso
	    ]
	    ,buttonAlign : 'center'
	    ,buttons     :
	    [
	        {
	        	text     : 'Confirmar endoso'
	        	,icon    : '${ctx}/resources/fam3icons/icons/key.png'
	        	,handler : _5_confirmar
	        }
	    ]
	});
    ////// contenido //////
	///////////////////////
	
	////////////////////
	////// loader //////
	_5_panelPri.setLoading(true);
	Ext.Ajax.request(
	{
		url     : _5_urlLoadMdomicil
		,params :
		{
			'smap1.pv_cdunieco_i'    : _5_smap1.CDUNIECO
            ,'smap1.pv_cdramo_i'     : _5_smap1.CDRAMO
            ,'smap1.pv_estado_i'     : _5_smap1.ESTADO
            ,'smap1.pv_nmpoliza_i'   : _5_smap1.NMPOLIZA
            ,'smap1.pv_nmsituac_i'   : _5_smap1.nmsituac
            ,'smap1.pv_cdperson_i'   : _5_smap1.cdperson
            ,'smap1.pv_cdrol_i'      : _5_smap1.cdrol
            ,'smap1.nombreAsegurado' : ''
            ,'smap1.cdrfc'           : _5_smap1.cdrfc
            ,'smap1.pv_cdtipsit_i'   : _5_smap1.CDTIPSIT
		}
	    ,success : function(response)
	    {
	    	_5_panelPri.setLoading(false);
	    	var json=Ext.decode(response.responseText);
	    	debug('respuesta',json);
	    	_5_formDomicil.loadRecord(new _5_modeloDomicil(json.smap1));
	    	
	    	debug('codpost:',_5_formDomicil.items.items[1]);
	    	debug('colonia:',_5_formDomicil.items.items[4]);
	    	
	    	//cargar colonia
	    	_5_formDomicil.items.items[4].getStore().load(
            {
                params :
                {
                    'params.cp' : _5_formDomicil.items.items[1].getValue()
                }
            });
	    	//cargar colonia
	    	
	    	//establecer cargar colonia al cambiar cod pos
	    	_5_formDomicil.items.items[1].on('blur',function()
	    	{
	    		debug('cod pos change');
	    		_5_formDomicil.items.items[4].getStore().load(
	    		{
	    			params :
	    			{
	    				'params.cp' : _5_formDomicil.items.items[1].getValue()
	    			}
	    		    ,callback : function()
	    		    {
	    		    	var hay=false;
	    		    	_5_formDomicil.items.items[4].getStore().each(function(record)
	    		    	{
	    		    		if(_5_formDomicil.items.items[4].getValue()==record.get('key'))
	    		    		{
	    		    			hay=true;
	    		    		}
	    		    	});
	    		    	if(!hay)
	    		    	{
	    		    		_5_formDomicil.items.items[4].setValue('');
	    		    	}
	    		    }
	    		});
	    	});
	    	//establecer cargar colonia al cambiar cod pos
	    	
	    	/*
	    	LEIDOS DESDE CURSOR:
		    	CDCOLONI: "137617"
		    	CDEDO: "9600030"
		    	CDMUNICI: "9600030003"
		    	CDPERSON: "511999"
		    	CODPOSTAL: "96000"
		    	DSDOMICI: "FLORES"
		    	Municipio: "ACAYUCAN"
		    	NMNUMERO: "ROSAS"
		    	NMNUMINT: "1"
		    	NMORDDOM: "1"
		    	NMTELEFO: "AA"
		    	estado: "VERACRUZ DE IGNACIO DE LA LLAVE"
	    	ENVIADOS AL PL:
		        String nmordom      = null;-
	            String dsdomici     = null;-
	            String nmtelefo     = null;-
	            String cdpostal     = null;-
	            String cdestado     = null;-
	            String cdmunici     = null;-
	            String cdcoloni     = null;-
	            String nmnumext     = null;-
	            String nmnumint     = null;-
	    	*/
	    }
	    ,failure : function()
	    {
	    	_5_panelPri.setLoading(false);
	    	errorComunicacion();
	    }
	});
    ////// loader //////
    ////////////////////
});

//////////////////////
//////funciones //////
function _5_confirmar(boton)
{
	debug('_5_confirmar');
	
	var valido=true;
	
	if(valido)
	{
		valido=_5_formDomicil.isValid()&&_5_panelTatriper.isValid()&&_5_panelEndoso.isValid();
		if(!valido)
		{
			datosIncompletos();
		}
	}
	
	if(valido)
	{
		var json=
		{
			smap1       : _5_smap1
			,smap2      : _5_formDomicil.getValues()
			,smap3      :
			{
				fecha_endoso : Ext.Date.format(_5_fieldFechaEndoso.getValue(),'d/m/Y')
			}
			,parametros : _5_panelTatriper.getValues()
		};
		debug('datos a enviar:',json);
		_5_panelPri.setLoading(true);
		Ext.Ajax.request(
		{
			url       : _5_urlGuardar
			,jsonData : json
			,success  : function(response)
			{
				_5_panelPri.setLoading(false);
				json=Ext.decode(response.responseText);
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
		    	_5_panelPri.setLoading(false);
		    	errorComunicacion();
		    }
		});
	}
}
//////funciones //////
//////////////////////
</script>
<div id="_5_divPri" style="height:1000px;"></div>