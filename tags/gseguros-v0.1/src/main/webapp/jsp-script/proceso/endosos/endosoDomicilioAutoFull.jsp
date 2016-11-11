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
//Obtenemos el contenido en formato JSON de la propiedad solicitada:
var _5_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;

var _5_urlLoadMdomicil = '<s:url namespace="/"        action="cargarPantallaDomicilio" />';
var _5_urlGuardar      = '<s:url namespace="/endosos" action="guardarEndosoDomicilioAutoFull" />';

var _5_panelLectura;
var _5_formDomicil;
var _5_panelEndoso;
//var _5_panelTatriper;
var _5_fieldFechaEndoso;
var _5_panelPri;

var datosIniciales;

debug('_5_smap1:',_5_smap1);


var _p5_urlRecuperacionSimple = '<s:url namespace="/emision" action="recuperacionSimple" />';
var _cdTipSupCambioDomFull = '<s:property value="@mx.com.gseguros.portal.general.util.TipoEndoso@CAMBIO_DOMICILIO_ASEGURADO_TITULAR.cdTipSup" />';

//////variables //////
//////////////////////

Ext.onReady(function()
{
	
	Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
	
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
	/*
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
                ,items  : [ property value="imap1.itemsTatriper" ]
            });
            this.callParent();
        }
	});
    */
	
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
				title      : 'Datos de p&oacute;liza'
				,layout    :
                {
                    type     : 'table'
                    ,columns : 2
                }
				,items     : [ <s:property value="imap1.itemsLectura" /> ]
				,listeners :
				{
					afterrender : function(me){heredarPanel(me);}
				}
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
	    ,fieldLabel : 'Fecha de efecto'
	    ,allowBlank : false
	    ,value      : new Date()
	    ,name       : 'fecha_endoso'
	});
	_5_panelLectura  = new _5_PanelLectura();
	_5_formDomicil   = new _5_FormDomicil();
	_5_panelEndoso   = new _5_PanelEndoso();
	//_5_panelTatriper = new _5_PanelTatriper();
	
	if(!Ext.isEmpty(_5_smap1.CDTIPSIT1) && _5_smap1.CDTIPSIT1 == 'MSC'){
		_fieldByName('CDEDO',_5_formDomicil).getStore().addListener('beforeload',function( store, operation, eOpts){
			_fieldByName('CDEDO',_5_formDomicil).getStore().getProxy().extraParams['params.cdtipsit']='MSC';
		});
		
		_fieldByName('CDMUNICI',_5_formDomicil).getStore().addListener('beforeload',function(store, operation){
			_fieldByName('CDMUNICI',_5_formDomicil).getStore().getProxy().extraParams['params.cdtipsit']='MSC';
		});
		
		_fieldByName('CDEDO',_5_formDomicil).value1 = 'MSC';		
		_fieldByName('CDMUNICI',_5_formDomicil).value1 = 'MSC';
	}
	
	_5_panelPri=Ext.create('Ext.panel.Panel',
	{
		renderTo  : 'divEndDomCP'
		,border   : 0
		,defaults :
		{
			style : 'margin : 5px;'
		}
	    ,items    :
	    [
	        _5_panelLectura
	        ,_5_formDomicil
	        //,_5_panelTatriper
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
            ,'smap1.pv_cdramo_i'      : _5_smap1.CDRAMO
            ,'smap1.pv_estado_i'      : _5_smap1.ESTADO
            ,'smap1.pv_nmpoliza_i'    : _5_smap1.NMPOLIZA
            ,'smap1.pv_nmsituac_i'    : _5_smap1.nmsituac
            ,'smap1.pv_cdperson_i'    : _5_smap1.cdperson
            ,'smap1.pv_cdrol_i'       : _5_smap1.cdrol
            ,'smap1.nombreAsegurado'  : ''
            ,'smap1.cdrfc'            : _5_smap1.cdrfc
            ,'smap1.pv_cdtipsit_i'    : _5_smap1.CDTIPSIT
            ,'smap1.domGeneral'      : 'S'
		}
	    ,success : function(response)
	    {
	    	_5_panelPri.setLoading(false);
	    	var json=Ext.decode(response.responseText);
	    	debug('respuesta',json);
	    	
	    	datosIniciales = json.smap1;
	    	
	    	_5_formDomicil.loadRecord(new _5_modeloDomicil(json.smap1));
	    	
	    	heredarPanel(_5_formDomicil);
	    	
	    	debug('codpost:',_codPosEnd());
	    	debug('colonia:',_comboColoniasEnd());
	    	_comboColoniasEnd().setEditable(true);
	    	
	    	//cargar colonia
	    	_comboColoniasEnd().getStore().load(
            {
                params :
                {
                    'params.cp' : _codPosEnd().getValue()
                }
            });
	    	//cargar colonia
	    	
	    	//establecer cargar colonia al cambiar cod pos
	    	_codPosEnd().on('blur',function()
	    	{
	    		debug('cod pos change');
	    		_comboColoniasEnd().getStore().load(
	    		{
	    			params :
	    			{
	    				'params.cp' : _codPosEnd().getValue()
	    			}
	    		    ,callback : function()
	    		    {
	    		    	var hay=false;
	    		    	_comboColoniasEnd().getStore().each(function(record)
	    		    	{
	    		    		if(_comboColoniasEnd().getValue()==record.get('key'))
	    		    		{
	    		    			hay=true;
	    		    		}
	    		    	});
	    		    	if(!hay)
	    		    	{
	    		    		_comboColoniasEnd().setValue('');
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
	
	_fieldByName('NMNUMERO').regex = /^[A-Za-z0-9-\s]*$/;
    _fieldByName('NMNUMERO').regexText = 'Solo d&iacute;gitos, letras y guiones';
    _fieldByName('NMNUMINT').regex = /^[A-Za-z0-9-\s]*$/;
    _fieldByName('NMNUMINT').regexText = 'Solo d&iacute;gitos, letras y guiones';
    
    
    Ext.Ajax.request(
    {
        url      : _p5_urlRecuperacionSimple
        ,params  :
        {
            'smap1.procedimiento' : 'RECUPERAR_FECHAS_LIMITE_ENDOSO'
            ,'smap1.cdunieco'     : _5_smap1.CDUNIECO
            ,'smap1.cdramo'       : _5_smap1.CDRAMO
            ,'smap1.estado'       : _5_smap1.ESTADO
            ,'smap1.nmpoliza'     : _5_smap1.NMPOLIZA
            ,'smap1.cdtipsup'     : _cdTipSupCambioDomFull
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### fechas:',json);
            if(json.exito)
            {
                _5_fieldFechaEndoso.setMinValue(json.smap1.FECHA_MINIMA);
                _5_fieldFechaEndoso.setMaxValue(json.smap1.FECHA_MAXIMA);
                _5_fieldFechaEndoso.setValue(json.smap1.FECHA_REFERENCIA);
                _5_fieldFechaEndoso.setReadOnly(json.smap1.EDITABLE=='N');
                _5_fieldFechaEndoso.isValid();
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
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
	
	setTimeout(function(){
	
		centrarVentana(Ext.Msg.show({
	        title: 'Confirmar acci&oacute;n',
	        msg: '&iquest;Esta seguro de cambiar este domicilio?',
	        buttons: Ext.Msg.YESNO,
	        fn: function(buttonId, text, opt) {
	        	if(buttonId == 'yes') {
			debug('_5_confirmar');
			
			var valido=true;
			
			if(valido)
			{
				var nuevosDatos = _5_formDomicil.getForm().getValues();
				
				valido = ( datosIniciales.CODPOSTAL != nuevosDatos.CODPOSTAL || datosIniciales.NMNUMERO != nuevosDatos.NMNUMERO || datosIniciales.NMNUMINT != nuevosDatos.NMNUMINT
				||  datosIniciales.CDEDO != nuevosDatos.CDEDO || datosIniciales.CDMUNICI != nuevosDatos.CDMUNICI || datosIniciales.CDCOLONI != nuevosDatos.CDCOLONI || datosIniciales.DSDOMICI != nuevosDatos.DSDOMICI );
				
				if(!valido)
				{
					mensajeWarning('No ha realizado cambios en el domicilio.');
				}
			}
			
			if(valido)
			{
				valido=_5_formDomicil.isValid()&&_5_panelEndoso.isValid();//&&_5_panelTatriper.isValid()
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
						fecha_endoso : Ext.Date.format(_5_fieldFechaEndoso.getValue(),'d/m/Y'),
						cdperson     : _5_smap1.cdperson,
						calle        : datosIniciales.DSDOMICI,
						cp           : datosIniciales.CODPOSTAL,
						numext       : datosIniciales.NMNUMERO,
						numint       : datosIniciales.NMNUMINT,
						cdedo        : datosIniciales.CDEDO,
						cdmunici     : datosIniciales.CDMUNICI,
						cdcoloni     : datosIniciales.CDCOLONI
					}
					//,parametros : _5_panelTatriper.getValues()
				};
				debug('datos a enviar:',json);
				var panelMask = new Ext.LoadMask('divEndDomCP', {msg:"Confirmando..."});
				panelMask.show();
				Ext.Ajax.request(
				{
					url       : _5_urlGuardar
					,jsonData : json
					,success  : function(response)
					{
						panelMask.hide();
						json=Ext.decode(response.responseText);
						if(json.success==true)
						{
							var callbackRemesa = function()
							{
							    //////////////////////////////////
		                        ////// usa codigo del padre //////
		                        /*//////////////////////////////*/
		                        marendNavegacion(2);
		                        /*//////////////////////////////*/
		                        ////// usa codigo del padre //////
		                        //////////////////////////////////
		                    };
		                    
		                    mensajeCorrecto('Endoso generado',json.mensaje,function()
		                    {
		                        _generarRemesaClic(
                                    true
                                    ,_5_smap1.CDUNIECO
                                    ,_5_smap1.CDRAMO
                                    ,_5_smap1.ESTADO
                                    ,_5_smap1.NMPOLIZA
                                    ,callbackRemesa
                                );
		                    });
						}
						else
						{
							mensajeError(json.error);
						}
					}
				    ,failure  : function()
				    {
				    	panelMask.hide();
				    	errorComunicacion();
				    }
				});
			}
		}else{
    		return;
    	}
	},
    icon: Ext.Msg.QUESTION
	}));
	
	},1500);

}


Ext.ComponentQuery.query('[name=NMTELEFO]')[Ext.ComponentQuery.query('[name=NMTELEFO]').length-1].hide();

function _codPosEnd(){
    return Ext.ComponentQuery.query('[name=CODPOSTAL]')[Ext.ComponentQuery.query('[name=CODPOSTAL]').length-1];
}

function _comboColoniasEnd(){
    return Ext.ComponentQuery.query('[name=CDCOLONI]')[Ext.ComponentQuery.query('[name=CDCOLONI]').length-1];
}
//////funciones //////
//////////////////////
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
<div id="divEndDomCP" style="height:1000px;"></div>