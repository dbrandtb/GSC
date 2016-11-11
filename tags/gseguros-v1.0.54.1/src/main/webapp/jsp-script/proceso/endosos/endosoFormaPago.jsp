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
    formapago : 12
    fechaInicio : 10/10/2014
*/
//Obtenemos el contenido en formato JSON de la propiedad solicitada:
var _9_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;

var _9_formLectura;
var _9_formFormaPago;
var _9_panelPri;
var _9_panelEndoso;
var _9_fieldFechaEndoso;

var _9_urlGuardar            = '<s:url namespace="/endosos"         action="guardarEndosoFormaPago" />';
var _9_urlLoaderLectura      = '<s:url namespace="/consultasPoliza" action="consultaDatosPoliza"    />';
var _9_urlRecuperacionSimple = '<s:url namespace="/emision"         action="recuperacionSimple"     />';

debug('_9_smap1:',_9_smap1);
////// variables //////
///////////////////////

Ext.onReady(function()
{
	
	Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
	
    /////////////////////
    ////// modelos //////
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    Ext.define('_9_PanelEndoso',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_9_PanelEndoso initComponent');
            Ext.apply(this,
            {
                title     : 'Datos del endoso'
                ,defaults :
                {
                    style : 'margin : 5px;'
                }
                ,items    :
                [
                    _9_fieldFechaEndoso
                ]
            });
            this.callParent();
        }
    });
    
    Ext.define('_9_FormFormaPago',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_9_FormFormaPago initComponent');
            Ext.apply(this,
            {
                title     : 'Forma de pago'
                ,defaults :
                {
                    style : 'margin : 5px;'
                }
                ,layout   :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,items    :
                [
                    <s:property value="imap1.itemCambioOld" />
                    ,<s:property value="imap1.itemCambioNew" />
                ]
            });
            this.callParent();
        }
    });
    
    Ext.define('_9_FormLectura',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_9_FormLectura initComponent');
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
                    afterrender : function(me){heredarPanel(me);}
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
    _9_fieldFechaEndoso=Ext.create('Ext.form.field.Date',
    {
        format      : 'd/m/Y'
        ,fieldLabel : 'Fecha de efecto'
        ,allowBlank : false
        ,name       : 'fecha_endoso'
        //,readOnly   : true
    });
    _9_panelEndoso   = new _9_PanelEndoso();
    _9_formFormaPago = new _9_FormFormaPago();
    _9_formLectura   = new _9_FormLectura();
    
    _9_panelPri=Ext.create('Ext.panel.Panel',
    {
        renderTo     : '_9_divPri'
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
                ,handler : _9_confirmar
            }
        ]
        ,items       :
        [
            _9_formLectura
            ,_9_formFormaPago
            ,_9_panelEndoso
        ]
    });
    ////// contenido //////
    ///////////////////////
    
    ////////////////////
    ////// loader //////
    _setLoading(true,_9_panelPri);
    Ext.Ajax.request(
    {
        url      : _9_urlLoaderLectura
        ,params  :
        {
            'params.cdunieco'  : _9_smap1.CDUNIECO
            ,'params.cdramo'   : _9_smap1.CDRAMO
            ,'params.estado'   : _9_smap1.ESTADO
            ,'params.nmpoliza' : _9_smap1.NMPOLIZA
        }
        ,success : function(response)
        {
            _setLoading(false,_9_panelPri);
            var json = Ext.decode(response.responseText);
            debug('respuesta:',json);
            if(json.success==true)
            {
            	var comboOriginal  = _9_formFormaPago.items.items[0];
                var comboNuevo     = _9_formFormaPago.items.items[1];
                _9_smap1['perpag'] = json.datosPoliza.cdperpag;
                
                comboOriginal.setValue(_9_smap1['perpag']);
                _9_panelEndoso.items.items[0].setValue(_9_smap1.fechaInicio);
                
                comboNuevo.on('change',function(combo,newVal,oldVal)
                {
                    var perpagOrigin = _9_smap1['perpag'];
                    
                    debug('comparando',perpagOrigin,newVal);
                    
                    if(newVal==perpagOrigin)
                    {
                   		combo.setValue(oldVal);
                        mensajeError('No hay cambio de forma de pago');
                    }
                });
                
            }
        }
        ,failure : function()
        {
            _setLoading(false,_9_panelPri);
            mensajeError('Error al cargar los datos de la p&oacute;liza');
        }
    });
    
    Ext.Ajax.request(
    {
        url      : _9_urlRecuperacionSimple
        ,params  :
        {
            'smap1.procedimiento' : 'RECUPERAR_FECHAS_LIMITE_ENDOSO'
            ,'smap1.cdunieco'     : _9_smap1.CDUNIECO
            ,'smap1.cdramo'       : _9_smap1.CDRAMO
            ,'smap1.estado'       : _9_smap1.ESTADO
            ,'smap1.nmpoliza'     : _9_smap1.NMPOLIZA
            ,'smap1.cdtipsup'     : _9_smap1.cdtipsup
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### fechas:',json);
            if(json.exito)
            {
                _fieldByName('fecha_endoso').setMinValue(json.smap1.FECHA_MINIMA);
                _fieldByName('fecha_endoso').setMaxValue(json.smap1.FECHA_MAXIMA);
                _fieldByName('fecha_endoso').setReadOnly(json.smap1.EDITABLE=='N');
                _fieldByName('fecha_endoso').isValid();
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

///////////////////////
////// funciones //////
function _9_confirmar()
{
    debug('_9_confirmar');
    
    var valido=true;
    
    if(valido)
    {
        valido=_9_formFormaPago.isValid()&&_9_panelEndoso.isValid();
        if(!valido)
        {
            datosIncompletos();
        }
    }
    
    if(valido)
    {
        var json=
        {
            smap1  : _9_smap1
            ,smap2 :
            {
                fecha_endoso : Ext.Date.format(_9_fieldFechaEndoso.getValue(),'d/m/Y')
                ,cdperpag    : _9_formFormaPago.items.items[1].getValue()
            }
        }
        debug('datos que se enviaran:',json);
        
        var panelMask = new Ext.LoadMask('_9_divPri', {msg:"Confirmando..."});
		panelMask.show();
        
        Ext.Ajax.request(
        {
            url       : _9_urlGuardar
            ,jsonData : json
            ,success  : function(response)
            {
                panelMask.hide();
                json=Ext.decode(response.responseText);
                debug('datos recibidos:',json);
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
                        var cadena= json.mensaje;
                        var palabra="guardado";
                    	if (cadena.indexOf(palabra)==-1){
                    		_generarRemesaClic2(
                                    false
                                    ,_9_smap1.CDUNIECO
                                    ,_9_smap1.CDRAMO
                                    ,_9_smap1.ESTADO
                                    ,_9_smap1.NMPOLIZA
                                    ,callbackRemesa
                                );
                    	}else{
                    		_generarRemesaClic(
                                    true
                                    ,_9_smap1.CDUNIECO
                                    ,_9_smap1.CDRAMO
                                    ,_9_smap1.ESTADO
                                    ,_9_smap1.NMPOLIZA
                                    ,callbackRemesa
                                );
                    	}
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
};
////// funciones //////
///////////////////////
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
<div id="_9_divPri" style="height:1000px;"></div>