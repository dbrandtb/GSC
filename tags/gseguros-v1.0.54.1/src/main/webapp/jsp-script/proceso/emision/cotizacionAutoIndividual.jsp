<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.green
{
    border-left  : 2px solid green;
    border-right : 2px solid green;
}
.red
{
    border-left  : 2px solid red;
    border-right : 2px solid red;
}
.conTimeout>tbody>tr::after
{
    content     : url('${ctx}/resources/fam3icons/icons/clock.png');
    margin-left : 5px;
}
</style>
<script>
////// overrides //////


Ext.override(Ext.form.TextField,
{
    initComponent:function()
    {
        if(Ext.isEmpty(this.sinOverride)||this.sinOverride==false)
        {
            Ext.apply(this,
            {
                labelWidth : 250
            });
        }
        if(<s:property value='%{getSmap1().containsKey("debug")}' />&&!Ext.isEmpty(this.cdatribu))
        {
            this.on(
            {
                afterrender : function(me)
                {
                    me.el.on(
                    {
                        mouseover : function()
                        {
                            me.ventana=Ext.create('Ext.panel.Panel',
                            {
                                floating : true
                                ,frame   : true
                                ,html    : me.cdatribu+':'+me.getValue()
                            });
                            me.ventana.show();
                            me.ventana.setY(me.getY());
                            me.ventana.setX(me.getX());
                        }
                        ,mouseout : function()
                        {
                            me.ventana.destroy();
                        }
                    });
                }
            });
        }
        return this.callParent();
    }
});
////// overrides //////

////// urls //////
var _p28_urlCargarCduniecoAgenteAuto          = '<s:url namespace="/emision"         action="cargarCduniecoAgenteAuto"                       />';
var _p28_urlCotizar                           = '<s:url namespace="/emision"         action="cotizar"                                        />';
var _p28_urlRecuperarCliente                  = '<s:url namespace="/"                action="buscarPersonasRepetidas"                        />';
var _p28_urlCargarRetroactividadSuplemento    = '<s:url namespace="/emision"         action="cargarRetroactividadSuplemento"                 />';
var _p28_urlCargarSumaAseguradaRamo5          = '<s:url namespace="/emision"         action="cargarSumaAseguradaRamo5"                       />';
var _p28_urlCargar                            = '<s:url namespace="/emision"         action="cargarCotizacion"                               />';
var _p28_urlDatosComplementarios              = '<s:url namespace="/emision"         action="emisionAutoIndividual"                          />';
var _p28_urlCargarParametros                  = '<s:url namespace="/emision"         action="obtenerParametrosCotizacion"                    />';
var _p28_urlCoberturas                        = '<s:url namespace="/flujocotizacion" action="obtenerCoberturas4"                             />';
var _p28_urlComprar                           = '<s:url namespace="/flujocotizacion" action="comprarCotizacion4"                             />';
var _p28_urlViewDoc                           = '<s:url namespace ="/documentos"     action="descargaDocInline"                              />';
var _p28_urlEnviarCorreo                      = '<s:url namespace="/general"         action="enviaCorreo"                                    />';
var _p28_urlDetalleCotizacion                 = '<s:url namespace="/"                action="detalleCotizacion"                              />';
var _p28_urlGuardarConfig                     = '<s:url namespace="/emision"         action="guardarConfigCotizacion"                        />';
var _p28_urlCargarConfig                      = '<s:url namespace="/emision"         action="cargarConfigCotizacion"                         />';
var _p28_urlRecuperacionSimple                = '<s:url namespace="/emision"         action="recuperacionSimple"                             />';
var _p28_urlCargarParamerizacionCoberturas    = '<s:url namespace="/emision"         action="cargarParamerizacionConfiguracionCoberturas"    />';
var _p28_urlValidarTractocamionRamo5          = '<s:url namespace="/emision"         action="cargarValidacionTractocamionRamo5"              />';
var _p28_urlCargarObligatorioCamionRamo5      = '<s:url namespace="/emision"         action="cargarObligatorioTractocamionRamo5"             />';
var _p28_urlCargarDetalleNegocioRamo5         = '<s:url namespace="/emision"         action="cargarDetalleNegocioRamo5"                      />';
var _p28_urlCargarParamerizacionCoberturasRol = '<s:url namespace="/emision"         action="cargarParamerizacionConfiguracionCoberturasRol" />';
var _p28_urlCargarTipoCambioWS                = '<s:url namespace="/emision"         action="cargarTipoCambioWS"                             />';
var _p28_urlRecuperacion                      = '<s:url namespace="/recuperacion"    action="recuperar"                                      />';
var _p28_urlImprimirCotiza = '<s:text name="ruta.servidor.reports" />';
var _p28_reportsServerUser = '<s:text name="pass.servidor.reports" />';
////// urls //////

////// variables //////
var _p28_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p28_smap1:',_p28_smap1);

var _p28_reporteCotizacion = '<s:text name='%{"rdf.cotizacion.nombre."+smap1.cdtipsit.toUpperCase()}' />';
debug('_p28_reporteCotizacion:',_p28_reporteCotizacion);

var _p28_formFields   = [<s:property value="imap.formFields"   />];
var _p28_panel2Items  = [<s:property value="imap.panel2Items"  />];
var _p28_panel3Items  = [<s:property value="imap.panel3Items"  />];
var _p28_panel4Items  = [<s:property value="imap.panel4Items"  />];

var _p28_recordClienteRecuperado = null;
var _p28_storeCoberturas         = null;
var _p28_windowCoberturas        = null;
var _p28_selectedCdperpag        = null;
var _p28_selectedCdplan          = null;
var _p28_selectedDsplan          = null;
var _p28_selectedNmsituac        = null;
var _p28_precioDolarDia          = null;
var plazoenanios;
////// variables //////


Ext.onReady(function()
{
    //INICIO DE THOUSAND SEPARATOR 
    Ext.util.Format.thousandSeparator=",";
    Ext.define("Ext.override.ThousandSeparatorNumberField", {
        override: "Ext.form.field.Number",
        
        /**
        * @cfg {Boolean} useThousandSeparator
        */
        useThousandSeparator: false,
        
        /**
         * @inheritdoc
         */
        toRawNumber: function (value) {
            
            debug("VIL >>> Cadena(value): " + value);
            var cadena = String(value).replace(this.decimalSeparator,'.').replace(new RegExp(Ext.util.Format.thousandSeparator, "g"), '');
            debug("VIL >>> Cadena: " + cadena);
            
            return cadena;
        },
        
        /**
         * @inheritdoc
         */
        getErrors: function (value) {
            if (!this.useThousandSeparator) return this.callParent(arguments);
            
            var me = this,
                errors = Ext.form.field.Text.prototype.getErrors.apply(me, arguments),
                format = Ext.String.format,
                num;

            value = Ext.isDefined(value) ? value : this.processRawValue(this.getRawValue());

            if (value.length < 1) { // if it's blank and textfield didn't flag it then it's valid
                return errors;
            }

            value = me.toRawNumber(value);

            if (isNaN(value.replace(Ext.util.Format.thousandSeparator,''))) {
                errors.push(format(me.nanText, value));
            }

            num = me.parseValue(value);

            if (me.minValue === 0 && num < 0) {
                errors.push(this.negativeText);
            }
            else if (num < me.minValue) {
                errors.push(format(me.minText, me.minValue));
            }

            if (num > me.maxValue) {
                errors.push(format(me.maxText, me.maxValue));
            }

            return errors;
        },
        
        /**
         * @inheritdoc
         */
         valueToRaw: function (value) {
            if (!this.useThousandSeparator) return this.callParent(arguments);
            
            var me = this;
            var format = "000,000";
            
            for (var i = 0; i < me.decimalPrecision; i++) {
                if (i == 0) format += ".";
                format += "0";
            }
            
            value = me.parseValue(Ext.util.Format.number(value, format));
            value = me.fixPrecision(value);
            value = Ext.isNumber(value) ? value : parseFloat(me.toRawNumber(value));
            value = isNaN(value) ? '' : String(Ext.util.Format.number(value, format)).replace('.', me.decimalSeparator);
            debug("VILS >>> Cadena(value) 1: " + value);
            return value;
        },
        
        /**
         * @inheritdoc
         */
        getSubmitValue: function () {
            if (!this.useThousandSeparator)
                return this.callParent(arguments);
            var me = this,
                value = me.callParent();

            if (true||!me.submitLocaleSeparator) {
                value = me.toRawNumber(value);
            }
            debug("Cadena(value) 2: " + value);
            return value;
        },
        
        /**
         * @inheritdoc
         */
        setMinValue: function (value) {
            if (!this.useThousandSeparator)
                return this.callParent(arguments);
            var me = this,
                allowed;

            me.minValue = Ext.Number.from(value, Number.NEGATIVE_INFINITY);
            me.toggleSpinners();

            // Build regexes for masking and stripping based on the configured options
            if (me.disableKeyFilter !== true) {
                allowed = me.baseChars + '';

                if (me.allowExponential) {
                    allowed += me.decimalSeparator + 'e+-';
                }
                else {
                    allowed += Ext.util.Format.thousandSeparator;
                    if (me.allowDecimals) {
                        allowed += me.decimalSeparator;
                    }
                    if (me.minValue < 0) {
                        allowed += '-';
                    }
                }

                allowed = Ext.String.escapeRegex(allowed);
                me.maskRe = new RegExp('[' + allowed + ']');
                if (me.autoStripChars) {
                    me.stripCharsRe = new RegExp('[^' + allowed + ']', 'gi');
                }
            }
        },
        
        /**
         * @private
         */
        parseValue: function (value) {
            if (!this.useThousandSeparator)
                return this.callParent(arguments);
            value = parseFloat(this.toRawNumber(value));
            return isNaN(value) ? null : value;
        }
    }); //FIN THOUSAND SEPARATOR
    
     
    _grabarEvento('COTIZACION','ACCCOTIZA',null,null,_p28_smap1.cdramo);

    Ext.Ajax.timeout = 3*60*1000;
 
    ////// modelos //////
    Ext.define('_p28_modeloRecuperado',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
             'NOMBRECLI'
            ,'DIRECCIONCLI'
        ]
    });
    
    Ext.define('_p28_formModel',
    {
        extend  : 'Ext.data.Model'
        ,fields : _p28_formFields
    });
    
    Ext.define('ModeloDetalleCotizacion',
    {
        extend : 'Ext.data.Model'
        ,fields :
        [
            'Codigo_Garantia'
            ,{
                name : 'Importe'
                ,type : 'float'
            }
            ,'Nombre_garantia'
            ,'cdtipcon'
            ,'nmsituac'
            ,'orden'
            ,'parentesco'
            ,'orden_parentesco'
        ]
    });
    ////// modelos //////
    
    ////// stores //////
    _p28_storeCoberturas=Ext.create('Ext.data.Store',
    {
        autoLoad : false
        ,model   : 'RowCobertura'
        ,sorters    :
        [
            {
                sorterFn : function(o1,o2)
                {
                    debug('sorting:',o1,o2);
                    if (o1.get('orden') === o2.get('orden'))
                    {
                        return 0;
                    }
                    return o1.get('orden')-0 < o2.get('orden')-0 ? -1 : 1;
                }
            }
        ]
        ,proxy   :
        {
            type    : 'ajax'
            ,url    : _p28_urlCoberturas
            ,reader :
            {
                type  : 'json'
                ,root : 'listaCoberturas'
            }
        }
    });
    ////// stores //////
    
    ////// componentes //////
    _p28_windowCoberturas = new Ext.Window(
    {
        plain        : true
        ,width       : 500
        ,height      : 400
        ,modal       : true
        ,autoScroll  : true
        ,title       : 'Coberturas'
        ,layout      : 'fit'
        ,bodyStyle   : 'padding:5px;'
        ,buttonAlign : 'center'
        ,closeAction : 'hide'
        ,closable    : true
        ,items       :
        [
            Ext.create('Ext.grid.Panel',
            {
                itemId       : '_p28_gridCoberturas'
                ,title       : 'Sin plan'
                ,store       : _p28_storeCoberturas
                ,height      : 300
                ,selType     : 'cellmodel'
                ,buttonAlign : 'center'
                ,columns     :
                [
                    {
                        dataIndex : 'dsGarant'
                        ,text : 'Cobertura'
                        ,flex : 3
                    }
                    ,{
                        dataIndex : 'sumaAsegurada'
                        ,text : 'Suma asegurada'
                        ,flex : 1
                    }
                    /*,{
                        dataIndex : 'deducible'
                        ,text : 'Deducible'
                        ,flex : 1
                    }*/
                ]
            })
        ] 
        ,buttons     :
        [
            {
                text     : 'Regresar'
                ,icon    : '${ctx}/resources/fam3icons/icons/arrow_left.png'
                ,handler : function()
                {
                    this.up().up().hide();
                }
            }
        ]
    });
    
    _p28_panel4Items.push(
    {
        xtype    : 'button'
        ,text    : 'Guardar configuraci&oacute;n'
        ,style   : 'margin:5px;margin-left:265px;'
        ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
        ,handler : _p28_guardarConfig
    });
    ////// componentes //////
    
    ////// contenido //////
    var _p28_formOcultoItems = [];
    <s:if test='%{getImap().get("panel5Items")!=null}'>
        var items = [<s:property value="imap.panel5Items" />];
        for(var i=0;i<items.length;i++)
        {
            _p28_formOcultoItems.push(items[i]);
        }
    </s:if>
    <s:if test='%{getImap().get("panel6Items")!=null}'>
        var items = [<s:property value="imap.panel6Items" />];
        for(var i=0;i<items.length;i++)
        {
            _p28_formOcultoItems.push(items[i]);
        }
    </s:if>
   
    var _p28_panel1Items =
    [
        {
            layout  :
            {
                type     : 'table'
                ,columns : 2
            }
            ,border : 0
            ,items  :
            [
                {
                    xtype        : 'numberfield'
                    ,itemId      : '_p28_nmpolizaItem'
                    ,fieldLabel  : 'FOLIO'
                    ,name        : 'nmpoliza'
                    ,style       : 'margin : 15px;'
                    ,sinOverride : true
                    ,labelWidth  : 170
                    ,style       : 'margin:5px;margin-left:15px;'
                    ,listeners   :
                    {
                        change : _p28_nmpolizaChange
                    }
                 }
                ,{
                    xtype    : 'button'
                    ,itemId  : '_p28_botonCargar'
                    ,text    : 'BUSCAR'
                    ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                    ,handler : _p28_cargar
                 }      
              ]
         }
    ];
    
    
    <s:if test='%{getImap().get("panel1Items")!=null}'>
        var aux = [<s:property value='imap.panel1Items' />];
        for(var i=0;i<aux.length;i++)
        {
            aux[i].style='margin-left:15px;';
            _p28_panel1Items.push(aux[i]);
        }
    </s:if>
    
    var tatripolItems=
    [
        <s:if test='%{!"0".equals(getSmap1().get("tatripolItemsLength"))}' >
            <s:property value="imap.tatripolItems" />
        </s:if>
    ];
    
    _p28_panel1Items.push(
    {
        xtype   : 'fieldset'
        ,itemId : '_p28_fieldsetVehiculo'
        ,width  : 435
        ,title  : '<span style="font:bold 14px Calibri;">VEH&Iacute;CULO</span>'
        ,items  : _p28_panel2Items
    }
    ,{
        xtype  : 'fieldset'
        ,width : 435
        ,title : '<span style="font:bold 14px Calibri;">CLIENTE</span>'
        ,items : _p28_panel3Items
    }
    ,{
        xtype   : 'fieldset'
        ,itemId : '_p28_fieldsetTatripol'
        ,width  : 435
        ,title  : '<span style="font:bold 14px Calibri;">DATOS ADICIONALES DE P&Oacute;LIZA</span>'
        ,hidden : _p28_smap1.cdramo+'x'=='5x'
                  ?(
                      _p28_smap1.cdtipsit+'x'!='CRx'||_p28_smap1.cdsisrol!='SUSCRIAUTO'
                  )
                  :false
        ,items  : tatripolItems
    }
    ,{
        xtype       : 'datefield'
        ,itemId     : '_p28_feiniItem'
        ,name       : 'feini'
        ,fieldLabel : 'INICIO DE VIGENCIA'
        ,value      : new Date()
        ,style      : 'margin-left:15px;'
        //,readOnly   : _p28_smap1.cdramo+'x'=='5x'&&_p28_smap1.cdsisrol!='SUSCRIAUTO'
    }
    ,{
        xtype       : 'datefield'
        ,itemId     : '_p28_fefinItem'
        ,name       : 'fefin'
        ,fieldLabel : 'FIN DE VIGENCIA'
        ,value      : Ext.Date.add(new Date(),Ext.Date.YEAR,1)
        ,minValue   : Ext.Date.add(new Date(),Ext.Date.DAY,1)
        ,style      : 'margin-left:15px;'
    });
    
    Ext.create('Ext.panel.Panel',
    {
        itemId    : '_p28_panelpri'
        ,border   : 0
        ,renderTo : '_p28_divpri'
        ,defaults : { style : 'margin : 5px;' }
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                itemId      : '_p28_form'
                ,border     : 0
                ,defaults   : { style : 'margin : 5px;' }
                ,formOculto : Ext.create('Ext.form.Panel',{ items : _p28_formOcultoItems })
                ,layout     :
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
                        ,items : _p28_panel1Items
                    }
                    ,{
                        xtype   : 'fieldset'
                        ,itemId : '_p28_panel4Fieldset'
                        ,title  : '<span style="font:bold 14px Calibri;">COBERTURAS</span>'
                        ,items  : _p28_panel4Items
                    }
                ]
            })
            ,Ext.create('Ext.panel.Panel',
            {
                itemId       : '_p28_botonera'
                ,buttonAlign : 'center'
                ,border      : 0
                ,buttons     :
                [
                    {
                        itemId   : '_p28_cotizarButton'
                        ,text    : 'Cotizar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/calculator.png'
                        ,handler : function(){_p28_cotizar();}
                    }
                    ,{
                        itemId   : '_p28_limpiarButton'
                        ,text    : 'Limpiar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                        ,handler : _p28_limpiar
                    }
                ]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    
    //fechas
    _fieldByName('feini').on(
    {
        change : function(me,val)
        {
            debug('val:',val);
            var fefin = _fieldByName('fefin');
            fefin.setValue(Ext.Date.add(val,Ext.Date.YEAR,1));
            fefin.setMinValue(Ext.Date.add(val,Ext.Date.DAY,1));
            if(!Ext.isEmpty(plazoenanios))
            {
            	fefin.setMaxValue(Ext.Date.add(val,Ext.Date.YEAR,plazoenanios));
            }
            fefin.isValid();
        }
    });
    //fechas
	
	//ramo 5
	if(_p28_smap1.cdramo+'x'=='5x')
	{
	    //fechas
	    _fieldByName('feini').on(
        {
            change : _p28_calculaVigencia
        });
    
        _fieldByName('fefin').on(
        {
            change : _p28_calculaVigencia
        });
    
        _p28_calculaVigencia();
        //fechas
    
        var agente    = _fieldByLabel('AGENTE');
        var clave     = _fieldByName('parametros.pv_otvalor06');
        var marca     = _fieldByName('parametros.pv_otvalor07');
        var submarca  = _fieldByName('parametros.pv_otvalor08');
        var modelo    = _fieldByName('parametros.pv_otvalor09');
        var version   = _fieldByName('parametros.pv_otvalor10');
        var tipoValor = _fieldByLabel('TIPO VALOR');
        var sumaAsegu = _fieldLikeLabel('VALOR VEH');
	    var combcl    = _fieldLikeLabel('CLIENTE NUEVO');
	    
	    //agente
	    if(_p28_smap1.cdsisrol=='EJECUTIVOCUENTA')
	    {
	        agente.setValue(_p28_smap1.cdagente);
	        agente.setReadOnly(true);
	        _p28_ramo5AgenteSelect(agente,_p28_smap1.cdagente);
	    }
	    else if(_p28_smap1.cdsisrol=='PROMOTORAUTO'
            ||_p28_smap1.cdsisrol=='SUSCRIAUTO')
        {
            agente.on(
            {
                'select' : _p28_ramo5AgenteSelect
            });
        }
        //agente
        
        //cliente nuevo
        combcl.on(
        {
            change : _p28_ramo5ClienteChange
        });
        
        combcl.getStore().on('load',function()
        {
            debug('combo cliente nuevo store load');
            combcl.setValue('S');
        });
        combcl.setValue('S');
        //cliente nuevo
        
            //version
            version.anidado = true;
            version.heredar = function()
            {
                version.getStore().load(
                {
                    params :
                    {
                        'params.submarca' : submarca.getValue()
                        ,'params.modelo'  : modelo.getValue()
                    }
                });
            };
            
            submarca.on(
            {
                select : function(){ version.reset(); }
            });
            
            modelo.on(
            {
                select : function()
                {
                    version.getStore().load(
                    {
                        params :
                        {
                            'params.submarca' : submarca.getValue()
                            ,'params.modelo'  : modelo.getValue()
                        }
                    });
                }
                ,change : function()
                {
                    tipoValor.setValue('');
                }
            });
            //version
            
            //clave
            clave.on(
            {
                select : function(combo,records){ _p28_herenciaDescendiente(clave,marca,submarca,modelo,version,records[0]); }
            });
            
            modelo.on(
            {
                select : function() { _p28_herenciaAscendente(clave,marca,submarca,modelo,version); }
            });
            
            version.on(
            {
                select : function() { _p28_herenciaAscendente(clave,marca,submarca,modelo,version); }
            });
            //clave
                
        /*
        //Cambia Atributos campo Fecha de nacimiento dependiendo del seguro de vida
            _fieldByLabel('SEGURO DE VIDA').on(
                            {
                                select : _p28_atributoNacimientoContratante
                            });
        */
        
        //tipovalor
        _fieldLikeLabel('FECHA DE FACTURA').hide();
            tipoValor.on(
            {
                select : function()
                {
                    var valor = tipoValor.getValue();
                    if(_p28_smap1.cdramo+'x'=='5x'&&valor-0==3)
                    {
                        var modelo = _fieldByLabel('MODELO').getValue()-0;
                        var anioAc = new Date().getFullYear()-0;
                        if(anioAc-modelo>1)
                        {
                            mensajeWarning('Solo se permite para modelos del a&ntilde;o actual o anterior');
                            tipoValor.setValue('');
                        }
                        _p28_cargarRangoValorRamo5();
                    }
                    else
                    {
                        _p28_cargarRangoValorRamo5();
                    }
     /* ------------------------------- VALOR FACTURA ------------------- */
                 
                   var valor = tipoValor.getValue();
                    if(valor-0==3)/* 3 = VALOR FACTURA*/
                    {
                        var numeroDiasFechaFacturacion;
                        
                         Ext.Ajax.request(
                                 { 
                                     url     : _p28_urlRecuperacion
                                 
                                     ,params :
                                     {
                                         'params.consulta' :'RECUPERAR_DIAS_FECHA_FACTURACION'
                                        ,'params.cdtipsit' : _p28_smap1.cdtipsit
                                         
                                        }
                                        ,success : function(response)
                                        {                                   
                                            try
                                            {
                                                var json = Ext.decode(response.responseText);
                                                debug('### load status:',json);
                                                if(json.success==true)
                                                {
                                                    numeroDiasFechaFacturacion = json.params.dias;
                                                    var hoy = new  Date();
                                                    var limite = Ext.Date.add(hoy, Ext.Date.DAY,-1*(numeroDiasFechaFacturacion));
                                                    
                                                    _fieldLikeLabel('FECHA DE FACTURA').setMinValue(limite);
                                                    
                                                }
                                                else
                                                {
                                                    mensajeError(json.message);
                                                }
                                            }
                                            catch(e)
                                            {
                                                manejaException(e,ck);
                                            }
                                        }
                                        ,failure : function()
                                        {
                                            errorComunicacion(null,'Error al dias de factura');
                                        }
                                    });
                            
                            
                        var hoy = new  Date();
                        var limite = Ext.Date.add(hoy, Ext.Date.DAY,numeroDiasFechaFacturacion);
                	
                            _fieldLikeLabel('FECHA DE FACTURA').setMinValue(limite);
                            _fieldLikeLabel('FECHA DE FACTURA').setMaxValue(hoy);
                            _fieldLikeLabel('FECHA DE FACTURA').show();
                            _fieldLikeLabel('FECHA DE FACTURA').allowBlank=false;
                        }
            else
                    {
                            _fieldLikeLabel('FECHA DE FACTURA').hide();
                            _fieldLikeLabel('FECHA DE FACTURA').allowBlank=true;
                        }
                    
                    }
            });
            //tipovalor
        
        //sumaAsegurada
            sumaAsegu.on(
            {
                change : function(){ _p28_limitarCoberturasDependientesSumasegRamo5(); }
            });
        //sumaAsegurada
        
        //parametrizacion coberturas
        _fieldByLabel('NEGOCIO').on(
        {
            change : function(){ _p28_cargarParametrizacionCoberturas(); }
        });
        _fieldByLabel('TIPO PERSONA').on(
        {
            change : function(){ _p28_cargarParametrizacionCoberturas(); }
        });
        _fieldByLabel('TIPO SERVICIO').on(
        {
            change : function(me,val)
            {
                if(me.findRecord('key',val)!=false)
                {
                    _p28_cargarParametrizacionCoberturas();
                    _fieldLikeLabel('CLAVE').store.proxy.extraParams['params.servicio']=val;
                }
            }
        });
        //parametrizacion coberturas
        
        //uso
        var usoCmp = _fieldByLabel('TIPO USO');
            debug('@CUSTOM uso:',usoCmp);
            usoCmp.anidado = true;
            usoCmp.heredar = function(remoto,micallback)
            {
                var negocioCmp  = _fieldByLabel('NEGOCIO');
                var servicioCmp = _fieldByLabel('TIPO SERVICIO');
                var negocioVal  = negocioCmp.getValue();
                var servicioVal = servicioCmp.getValue();
                debug('negocioVal:'  , negocioVal);
                debug('servicioVal:' , servicioVal);
                if(!Ext.isEmpty(negocioVal)
                    &&!Ext.isEmpty(servicioVal)
                    )
                {
                    _fieldByLabel('TIPO USO').getStore().load(
                    {
                        params :
                        {
                            'params.cdnegocio' : negocioVal
                            ,'params.servicio' : servicioVal
                        }
                        ,callback : function()
                        {
                            var me  = _fieldByLabel('TIPO USO');
                            var val = me.getValue();
                            var den = false;
                            me.getStore().each(function(record)
                            {
                                if(record.get('key')==val)
                                {
                                    den = true;
                                }
                            });
                            if(!den)
                            {
                                me.clearValue();
                            }
                            
                        _fieldLikeLabel('CLAVE').store.proxy.extraParams['params.uso']=me.getValue();
                            
                            if(!Ext.isEmpty(micallback))
                            {
                                micallback(_fieldByLabel('TIPO USO'));
                            }
                        }
                    });
                }
                else
                {
                    if(!Ext.isEmpty(micallback))
                    {
                        micallback(_fieldByLabel('TIPO USO'));
                    }
                }
            };

            
        _fieldByLabel('NEGOCIO').on(
        {
            change : function(me,val)
            {
                if(me.findRecord('key',val)!=false)
                {
                    _fieldByLabel('TIPO USO').heredar(true);
                }
            }
        });
            _fieldByLabel('TIPO SERVICIO').on(
                    {
                        change : function(me,val)
                        {
                            if(me.findRecord('key',val)!=false)
                            {
                                _fieldByLabel('TIPO USO').heredar(true);
                            }
                        }
                    });
            
            usoCmp.on(
                    {
                        change : function(me,val)
                        {
                var claveCmp = _fieldLikeLabel('CLAVE');
                var modelo   = _fieldByLabel('MODELO').getValue(); 
                                claveCmp.store.proxy.extraParams['params.uso']=val;
                                if(!Ext.isEmpty(claveCmp.getValue())&&!Ext.isEmpty(modelo))
                                 {
                                    var fs = _fieldById('_p28_fieldsetVehiculo');
                                    for(var i in fs.items.items)
                                    {
                                        try
                                        {
                                            fs.items.items[i].setValue();
                                            fs.items.items[i].clearValue();
                                        }
                                        catch(e)
                                        {
                                            debugError(e);
                                        }
                                    }
                                }
                            }
        });
                    //uso
     
        
        //camion
        if(_p28_smap1.cdtipsit+'x'=='CRx')
        {
            _fieldLikeLabel('CLAVE').on(
            {
                select : function(){ _p28_recuperaObligatorioCamionRamo5(); }
            });
            _fieldLikeLabel('VALOR VEH').on(
            {
                change : function(){ _p28_recuperaObligatorioCamionRamo5(); }
            });
            _fieldLikeLabel('LIZA TRACTOCAMI').allowBlank=false;
            _fieldLikeLabel('LIZA TRACTOCAMI').on(
            {
                blur : function(me)
                {
                    var val=me.getValue();
                    if(!Ext.isEmpty(val)&&val+'x'!='x')
                    {
                        me.setLoading(true);
                        Ext.Ajax.request(
                        {
                            url     : _p28_urlValidarTractocamionRamo5
                            ,params :
                            {
                                'smap1.poliza' : val
                                ,'smap1.rfc'   : Ext.isEmpty(_p28_recordClienteRecuperado) ? '' : _p28_recordClienteRecuperado.raw.RFCCLI
                            }
                            ,success : function(response)
                            {
                                me.setLoading(false);
                                var json = Ext.decode(response.responseText);
                                debug('### tractocamion:',json);
                                if(!json.exito)
                                {
                                    mensajeWarning(json.respuesta);
                                    me.setValue('');
                                }
                            }
                            ,failure : function()
                            {
                                me.setLoading(false);
                                me.setValue('');
                                errorComunicacion();
                            }
                        });
                    }
                }
            });
        }
        //camion
        
        //negocio
         if(('|TV|TL|'.lastIndexOf('|'+_p28_smap1.cdtipsit+'|')==-1))
        {
            _fieldByLabel('NEGOCIO').on(
            {
                change : function(me,val)
                {
                    if(me.findRecord('key',val)!=false)
                    {
                        marca.getStore().load(
                        {
                            params :
                            {
                                'params.cdnegocio' : val
                            }
                        });
                        if(_p28_smap1.cdtipsit+'x'=='CRx'||_p28_smap1.cdtipsit+'x'=='PCx')
                        {
                            _fieldByLabel('CARGA').getStore().load(
                            {
                                params :
                                {
                                    'params.negocio' : _fieldByLabel('NEGOCIO').getValue()
                                }
                            });
                        }
                        
                        var negoCmp = _fieldByLabel('NEGOCIO');
                        var negoVal = negoCmp.getValue();
                        negoCmp.setLoading(true);
                        Ext.Ajax.request(
                        {
                            url     : _p28_urlCargarDetalleNegocioRamo5
                            ,params :
                            {
                                'smap1.negocio' : negoVal,
                                'smap1.cdramo'  : _p28_smap1.cdramo,
                                'smap1.cdtipsit': _p28_smap1.cdtipsit
                            }
                            ,success : function(response)
                            {
                                negoCmp.setLoading(false);
                                var json = Ext.decode(response.responseText);
                                debug('### detalle negocio:',json);
                                
                                plazoenanios = Number(json.smap1.LIMITE_SUPERIOR);
//                              _fieldByName('FESOLICI').setMaxValue(Ext.Date.add(new Date(),Ext.Date.YEAR, Number(json.smap1.LIMITE_SUPERIOR)));
                                _fieldByName('fefin').setMaxValue(Ext.Date.add(new Date(),Ext.Date.YEAR,plazoenanios));
                                if(Number(json.smap1.MULTIANUAL) != 0) {
                                    _fieldByName('fefin').validator=function(val)
                                    {
                                        var feiniVal = Ext.Date.format(_fieldByName('feini').getValue(),'d/m/Y');
                                        debug('feiniVal:',feiniVal);
                                        var fefinVal=[];
                                        for(var i=1; i <= Number(json.smap1.MULTIANUAL); i++)
                                        {
                                            debug('mas anios:',i);
                                            fefinVal.push(Ext.Date.format(Ext.Date.add(Ext.Date.parse(feiniVal,'d/m/Y'),Ext.Date.YEAR,i),'d/m/Y'));
                                        }
                                        debug('validar contra:',fefinVal);
                                        var valido = true;
                                        if(!Ext.Array.contains(fefinVal,val))
                                        {
                                            valido = 'Solo se permite:';
                                            for(var i in fefinVal)
                                            {
                                                valido = valido + ' ' + fefinVal[i];
                                                if(fefinVal.length>1&&i<fefinVal.length-1)
                                                {
                                                    valido = valido + ',';
                                                }
                                            }
                                        }
                                        return valido;
                                    }
                                }
                                _fieldByName('fefin').isValid();
                            }
                            ,failure : function()
                            {
                                negoCmp.setLoading(false);
                                errorComunicacion();
                            }
                        });
                    }
                }
            });
        }
        //negocio
        
        //asistencia eua y canada
        if(_p28_smap1.cdtipsit+'x'=='ARx'||_p28_smap1.cdtipsit+'x'=='PPx')
        {
            var canadaCmp = _fieldLikeLabel('CANAD');
            debug('@CUSTOM canada:',canadaCmp);
            canadaCmp.anidado = true;
            canadaCmp.heredar = function(remoto,micallback)
            {
                var me        = _fieldLikeLabel('CANAD');
                var postalCmp = _fieldLikeLabel('CIRCULACI');
                var postalVal = postalCmp.getValue();
                if((postalVal+'x').length==6)
                {
                    me.setLoading(true);
                    Ext.Ajax.request(
                    {
                        url     : _p28_urlRecuperacionSimple
                        ,params :
                        {
                            'smap1.procedimiento' : 'VERIFICAR_CODIGO_POSTAL_FRONTERIZO'
                            ,'smap1.cdpostal'     : postalVal
                        }
                        ,success : function(response)
                        {
                            me.setLoading(false);
                            var json=Ext.decode(response.responseText);
                            debug('### canada:',json);
                            if(json.exito)
                            {
                                if(json.smap1.fronterizo+'x'=='Sx')
                                {
                                    me.setValue('S');
                                }
                                else
                                {
                                    me.setValue('N');
                                }
                            }
                            else
                            {
                                mensajeError(json.respuesta);
                            }
                            if(!Ext.isEmpty(micallback))
                            {
                                micallback(_fieldLikeLabel('CANAD'));
                            }
                        }
                        ,failure : function()
                        {
                            me.setLoading(false);
                            errorComunicacion();
                        }
                    });
                }
                else
                {
                    if(!Ext.isEmpty(micallback))
                    {
                        micallback(_fieldLikeLabel('CANAD'));
                    }
                }
            }
        
            var postalCmp = _fieldLikeLabel('CIRCULACI');
            postalCmp.on(
            {
                change : function()
                {
                    _fieldLikeLabel('CANAD').heredar();
                }
            });
        }
        //asistencia eua y canada
    }
    //ramo 5
    
    //codigo dinamico recuperado de la base de datos
    <s:property value="smap1.customCode" escapeHtml="false" />
    
     Ext.apply(_fieldByName('parametros.pv_otvalor13'),
        {
         useThousandSeparator: true,
        });
    Ext.apply(_fieldByName('parametros.pv_otvalor27'),
        {
         useThousandSeparator: true,
        });
    Ext.apply(_fieldByName('parametros.pv_otvalor29'),
        {
         useThousandSeparator: true,
        }); 
    ////// custom //////
    
    ////// loaders //////
    _p28_cargarConfig();
    
    if('|AR|CR|PC|PP|'.lastIndexOf('|'+_p28_smap1.cdtipsit+'|')!=-1)
    {
        debug('@CUSTOM valor max');
        var valorCmp = _fieldLikeLabel('VALOR VEH');
        Ext.Ajax.request(
        {
            url     : _p28_urlRecuperacionSimple
            ,params :
            {
                'smap1.procedimiento' : 'RECUPERAR_VALOR_MAXIMO_SITUACION_POR_ROL'
                ,'smap1.cdtipsit'     : _p28_smap1.cdtipsit
            }
            ,success : function(response)
            {
                var json = Ext.decode(response.responseText);
                debug('### valor maximo por rol:',json);
                if(json.exito)
                {
                    valorCmp.maximoTotal = json.smap1.VALOR;
                    valorCmp.validator = function(val)
                    {
                        var me = _fieldLikeLabel('VALOR VEH');
                        debug('validar contra ',me.maximoTotal);
                        if(Number(val)>Number(me.maximoTotal))
                        {
                            if(_p28_smap1.cdsisrol =='EJECUTIVOCUENTA'){
                                return 'Favor de acudir a Mesa de Control para realizar la cotización';
                            }else{
                                return 'El valor m&aacute;ximo es '+me.maximoTotal;
                            }
                        }
                        return true;
                    };
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
    }
    ////// loaders //////
});

////// funciones //////
function _p28_cotizar(sinTarificar)
{
    debug('>_p28_cotizar sintarifa:',sinTarificar,'DUMMY');
    
    var panelpri = _fieldById('_p28_panelpri');
    var form     = _fieldById('_p28_form');
    var valido   = form.isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
    if(valido)
    {
        valido = _fieldByName('nmpoliza').sucio==false;
        if(!valido)
        {
            _fieldByName('nmpoliza').semaforo = true;
            _fieldByName('nmpoliza').setValue('');
            _fieldByName('nmpoliza').semaforo = false;
            valido = true;
        }
    }
    
    if(valido)
    {
        //copiar paneles a oculto
        var arr = Ext.ComponentQuery.query('#_p28_gridTarifas');
        if(arr.length>0)
        {
            var formDescuentoActual = _fieldById('_p28_formDescuento');
            var formCesion          = _fieldById('_p28_formCesion');
            var recordPaneles       = new _p28_formModel(formDescuentoActual.getValues());
            var itemsCesion         = Ext.ComponentQuery.query('[fieldLabel]',formCesion);
            for(var i=0;i<itemsCesion.length;i++)
            {
                recordPaneles.set(itemsCesion[i].getName(),itemsCesion[i].getValue());
            }
            form.formOculto.loadRecord(recordPaneles);
            debug('form.formOculto.getValues():',form.formOculto.getValues());
        }
        
        var smap = _p28_smap1;
        _p28_smap1['cdpersonCli'] = Ext.isEmpty(_p28_recordClienteRecuperado) ? '' : _p28_recordClienteRecuperado.raw.CLAVECLI;
        _p28_smap1['cdideperCli'] = Ext.isEmpty(_p28_recordClienteRecuperado) ? '' : _p28_recordClienteRecuperado.raw.CDIDEPER;
        _p28_smap1['notarificar'] = !Ext.isEmpty(sinTarificar)&&sinTarificar==true?'si':'no';//Se utiliza para no retarid
        
        
        var agenteCmp=_fieldByLabel('AGENTE',_fieldById('_p28_form'),true);
        if(Ext.isEmpty(agenteCmp))
        {
            smap.cdagenteAux='';
        }
        else
        {
            smap.cdagenteAux=agenteCmp.getValue();
        }
    
        var json=
        {
            slist1 :
            [
                form.getValues()
            ]
            ,smap1 : smap
        };
        
       //json.slist1[0]["parametros.pv_otvalor13"] = json.slist1[0]["parametros.pv_otvalor13"].replace(",","");
       //json.slist1[0]["parametros.pv_otvalor27"] = json.slist1[0]["parametros.pv_otvalor27"].replace(",","");
       //json.slist1[0]["parametros.pv_otvalor29"] = json.slist1[0]["parametros.pv_otvalor29"].replace(",","");
               
        var valuesFormOculto = form.formOculto.getValues();
        for(var att in valuesFormOculto)
        {
            json.slist1[0][att]=valuesFormOculto[att];
            debug('Agregado a cotizacion:',att,':',valuesFormOculto[att]);
        }
        debug('json a enviar para cotizar:',json);
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p28_urlCotizar
            ,jsonData : json
            ,success  : function(response)
            {
                _p28_bloquear(true);
                panelpri.setLoading(false);
                json=Ext.decode(response.responseText);
                debug('### cotizar:',json);
                if(json.success==true)
                {
                	if(!Ext.isEmpty(json.smap1.msnPantalla))
                	{
                		mensajeCorrecto('Aviso',json.smap1.msnPantalla);
                	}
                	
                    debug(Ext.decode(json.smap1.fields));
                    debug(Ext.decode(json.smap1.columnas));
                    debug(json.slist2);
                    
                    _fieldByName('nmpoliza').semaforo=true;
                    _fieldByName('nmpoliza').setValue(json.smap1.nmpoliza);
                    _fieldByName('nmpoliza').semaforo=false;
                    
                    if(Ext.isEmpty(sinTarificar)||false==sinTarificar)
                    {
                        _grabarEvento('COTIZACION'
                                      ,'COTIZA'
                                      ,_p28_smap1.ntramite
                                      ,_p28_smap1.cdunieco
                                      ,_p28_smap1.cdramo
                                      ,'W'
                                      ,json.smap1.nmpoliza
                                      ,json.smap1.nmpoliza
                                      ,'buscar'
                                      );
                    }
                    
                    Ext.define('_p28_modeloTarifa',
                    {
                        extend  : 'Ext.data.Model'
                        ,fields : Ext.decode(json.smap1.fields)
                    });
                    
                    var itemsDescuento =
                    [
                        {
                            xtype  : 'displayfield'
                            ,value : '¿Desea usar su descuento de agente?'
                        }
                        ,{
                            xtype  : 'displayfield'
                            ,value : 'Si desea aplicar un DESCUENTO seleccione el porcentaje mayor a 0%'
                        }
                        ,{
                            xtype  : 'displayfield'
                            ,value : 'Si desea aplicar un RECARGO seleccione el porcentaje menor a 0%'
                        }
                    ];
                    
                    <s:if test='%{getImap().get("panel5Items")!=null}'>
                        var itemsaux = [<s:property value="imap.panel5Items" />];
                        for(var ii=0;ii<itemsaux.length;ii++)
                        {
                            debug('itemsaux[ii]:',itemsaux[ii]);
                            itemsDescuento.push(itemsaux[ii]);
                        }
                    </s:if>
                    
                    var itemsComision =
                    [
                        {
                            xtype  : 'displayfield'
                            ,value : 'Indique el porcentaje de comisi&oacute;n que desea ceder'
                        }
                    ];
                    
                    <s:if test='%{getImap().get("panel6Items")!=null}'>
                        var itemsaux = [<s:property value="imap.panel6Items" />];
                        for(var ii=0;ii<itemsaux.length;ii++)
                        {
                            itemsaux[ii].minValue=0;
                            itemsaux[ii].maxValue=100;
                            itemsComision.push(itemsaux[ii]);
                        }
                    </s:if>
                    debug('itemsComision:',itemsComision);
                    
                    var arr = Ext.ComponentQuery.query('#_p28_gridTarifas');
                    if(arr.length>0)
                    {
                        _fieldById('_p28_formCesion').destroy();
                        panelpri.remove(arr[arr.length-1],true);
                    }
                    
                    var _p28_formDescuento = Ext.create('Ext.form.Panel',
                    {
                        itemId        : '_p28_formDescuento'
                        ,border       : 0
                        ,defaults     : { style : 'margin:5px;' }
                        ,style        : 'margin-left:535px;'
                        ,width        : 450
                        ,windowCesion : Ext.create('Ext.window.Window',
                        {
                            title        : 'CESI&Oacute;N DE COMISI&Oacute;N'
                            ,autoScroll  : true
                            ,closeAction : 'hide'
                            ,modal       : true
                            ,items       :
                            [
                                Ext.create('Ext.form.Panel',
                                {
                                    itemId       : '_p28_formCesion'
                                    ,border      : 0
                                    ,defaults    : { style : 'margin:5px;' }
                                    ,items       : itemsComision
                                    ,buttonAlign : 'center'
                                    ,buttons     :
                                    [
                                        {
                                            itemId   : '_p28_botonAplicarCesion'
                                            ,text    : 'Aplicar'
                                            ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                            ,handler : function(me)
                                            {
                                                if(me.up('form').getForm().isValid())
                                                {
                                                    me.up('window').hide();
                                                    _p28_cotizar(true);
                                                }
                                                else
                                                {
                                                    mensajeWarning('Favor de verificar los datos');
                                                }
                                            }
                                        }
                                    ]
                                })
                            ]
                        })
                        ,items       :
                        [
                            {
                                xtype  : 'fieldset'
                                ,title : '<span style="font:bold 14px Calibri;">DESCUENTO DE AGENTE</span>'
                                ,items : itemsDescuento
                            }
                        ]
                        ,buttonAlign : 'right'
                        ,buttons     :
                        [
                            {
                                itemId   : '_p28_botonAplicarDescuento'
                                ,text    : 'Aplicar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                ,handler : function(me)
                                {
                                    if(me.up('form').getForm().isValid())
                                    {
                                        _p28_cotizar();
                                    }
                                    else
                                    {
                                        mensajeWarning('Favor de verificar los datos');
                                    }
                                }
                            }
                        ]
                    });
                    
                    _p28_formDescuento.loadRecord(new _p28_formModel(form.formOculto.getValues()));
                    _fieldById('_p28_formCesion').loadRecord(new _p28_formModel(form.formOculto.getValues()));
                    
                    //bloquear descuento
                    var arrDesc = Ext.ComponentQuery.query('[fieldLabel]',_p28_formDescuento);
                    var disabledDesc = false;
                    for(var i=0;i<arrDesc.length;i++)
                    {
                        if(arrDesc[i].getValue()-0!=0)
                        {
                            arrDesc[i].setReadOnly(true);
                            disabledDesc = true;
                        }
                    }
                    _fieldById('_p28_botonAplicarDescuento').setDisabled(disabledDesc);
                    
                    //bloquear comision
                    var arrComi      = Ext.ComponentQuery.query('[fieldLabel]',_fieldById('_p28_formCesion'));
                    var disabledComi = false;
                    for(var i=0;i<arrComi.length;i++)
                    {
                        if(arrComi[i].getValue()-0!=0)
                        {
                            arrComi[i].setReadOnly(true);
                            disabledComi = true;
                        }
                    }
                    _fieldById('_p28_botonAplicarCesion').setDisabled(disabledComi);
                    
                    var gridTarifas=Ext.create('Ext.panel.Panel',
                    {
                        itemId : '_p28_gridTarifas'
                        ,items :
                        [
                            Ext.create('Ext.grid.Panel',
                            {
                                title             : 'Resultados'
                                ,border           : 0
                                ,store            : Ext.create('Ext.data.Store',
                                {
                                    model : '_p28_modeloTarifa'
                                    ,data : json.slist2
                                })
                                ,columns          : Ext.decode(json.smap1.columnas)
                                ,selType          : 'cellmodel'
                                ,minHeight        : 100
                                ,enableColumnMove : false
                                ,listeners        :
                                {
                                    select : _p28_tarifaSelect
                                }
                            })
                            ,_p28_formDescuento
                            ,Ext.create('Ext.panel.Panel',
                            {
                                defaults : { style : 'margin:5px;' }
                                ,border  : 0
                                ,tbar    :
                                [
                                    '->'
                                    ,{
                                        itemId    : '_p28_botonDetalles'
                                        ,text     : 'Detalles'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/text_list_numbers.png'
                                        ,disabled : true
                                        ,handler  : _p28_detalles
                                        ,hidden   : _p28_smap1.cdsisrol!='SUSCRIAUTO'
                                    }
                                    ,{
                                        itemId    : '_p28_botonCoberturas'
                                        ,text     : 'Coberturas'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/table.png'
                                        ,disabled : true
                                        ,handler  : _p28_coberturas
                                    }
                                    ,{
                                        itemId   : '_p28_botonEditar'
                                        ,text    : 'Editar'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
                                        ,handler : _p28_editar
                                    }
                                    ,{
                                        itemId   : '_p28_botonClonar'
                                        ,text    : 'Duplicar'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/control_repeat_blue.png'
                                        ,handler : _p28_clonar
                                    }
                                    ,{
                                        itemId   : '_p28_botonNueva'
                                        ,text    : 'Nueva'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                                        ,handler : _p28_nueva
                                    }
                                ]
                                ,bbar    :
                                [
                                    '->'
                                    ,{
                                        itemId    : '_p28_botonEnviar'
                                        ,xtype    : 'button'
                                        ,text     : 'Enviar'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/email.png'
                                        ,disabled : true
                                        ,handler  : _p28_enviar
                                    }
                                    ,{
                                        itemId    : '_p28_botonImprimir'
                                        ,xtype    : 'button'
                                        ,text     : 'Imprimir'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/printer.png'
                                        ,disabled : true
                                        ,handler  : _p28_imprimir
                                    }
                                    ,{
                                        itemId   : '_p28_botonCesion'
                                        ,xtype   : 'button'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/page_white_star.png'
                                        ,text    : 'Cesi&oacute;n de comisi&oacute;n'
                                        ,handler : _p28_cesionClic
                                    }
                                    ,{
                                        itemId    : '_p28_botonComprar'
                                        ,xtype    : 'button'
                                        ,text     : 'Confirmar cotizaci&oacute;n'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/book_next.png'
                                        ,disabled : true
                                        ,handler  : _p28_comprar
                                    }
                                ]
                            })
                        ]
                    });
                    
                    var panelPri = _fieldById('_p28_panelpri');
                    
                    panelPri.add(gridTarifas);
                    panelPri.doLayout();
                    
                    if(_p28_smap1.cdramo+'x'=='5x'&&arrDesc.length>0)
                    {
                        _p28_formDescuento.setLoading(true);
                        Ext.Ajax.request(
                        {
                            url     : _p28_urlRecuperacionSimple
                            ,params :
                            {
                                'smap1.procedimiento' : 'RECUPERAR_DESCUENTO_RECARGO_RAMO_5'
                                ,'smap1.cdtipsit'     : _p28_smap1.cdtipsit
                                ,'smap1.cdagente'     : _fieldByLabel('AGENTE').getValue()
                                ,'smap1.negocio'      : _fieldByLabel('NEGOCIO').getValue()
                                ,'smap1.tipocot'      : 'I'
                                ,'smap1.cdsisrol'     : _p28_smap1.cdsisrol
                                ,'smap1.cdusuari'     : _p28_smap1.cdusuari
                            }
                            ,success : function(response)
                            {
                                _p28_formDescuento.setLoading(false);
                                var json = Ext.decode(response.responseText);
                                debug('### cargar rango descuento ramo 5:',json);
                                if(json.exito)
                                {
                                    for(var i=0;i<arrDesc.length;i++)
                                    {
                                        arrDesc[i].minValue=100*(json.smap1.min-0);
                                        arrDesc[i].maxValue=100*(json.smap1.max-0);
                                        arrDesc[i].isValid();
                                        debug('min:',arrDesc[i].minValue);
                                        debug('max:',arrDesc[i].maxValue);
                                        arrDesc[i].setReadOnly(false);
                                    }
                                }
                                else
                                {
                                    for(var i=0;i<arrDesc.length;i++)
                                    {
                                        arrDesc[i].minValue=0;
                                        arrDesc[i].maxValue=0;
                                        arrDesc[i].setValue(0);
                                        arrDesc[i].isValid();
                                        arrDesc[i].setReadOnly(true);
                                    }
                                    mensajeError(json.respuesta);
                                }
                            }
                            ,failure : function()
                            {
                                _p28_formDescuento.setLoading(false);
                                for(var i=0;i<arrDesc.length;i++)
                                {
                                    arrDesc[i].minValue=0;
                                    arrDesc[i].maxValue=0;
                                    arrDesc[i].setValue(0);
                                    arrDesc[i].isValid();
                                    arrDesc[i].setReadOnly(true);
                                }
                                errorComunicacion();
                            }
                        });
                    }
                    
                    try {
                       gridTarifas.down('button[disabled=false]').focus(false, 1000);
                    } catch(e) {
                        debug(e);
                    }
                }
                else
                {
                    _p28_bloquear(false);
                    mensajeError('Error al cotizar:<br/>'+json.error);
                }
            }
            ,failure  : function(response)
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p28_cotizar');
}

function _p28_bloquear(b)
{
    debug('>_p28_bloquear:',b);
    var comps=Ext.ComponentQuery.query('[fieldLabel]',_fieldById('_p28_form'));
    
    for(var i=0;i<comps.length;i++)
    {
        comps[i].setReadOnly(b);
    }
    
    _fieldById('_p28_botonera').setDisabled(b);
    _fieldById('_p28_botonCargar').setDisabled(b);
    
    if(b)
    {
    }
    else
    {
        try {
            _fieldByName('nmpoliza').focus();
        } catch(e) {
            debug(e);
        }
    }
    
    if(_p28_smap1.cdramo+'x'=='5x'&&_p28_smap1.cdsisrol=='EJECUTIVOCUENTA')
    {
        var agente = _fieldByLabel('AGENTE');
        agente.setValue(_p28_smap1.cdagente);
        agente.setReadOnly(true);
        _p28_ramo5AgenteSelect(agente,_p28_smap1.cdagente);
    }
    debug('<_p28_bloquear');
}

function _p28_ramo5AgenteSelect(comp,records)
{
    cdagente = typeof records == 'string' ? records : records[0].get('key');
    debug('>_p28_ramo5AgenteSelect cdagente:',cdagente);
    Ext.Ajax.request(
    {
        url     : _p28_urlCargarCduniecoAgenteAuto
        ,params :
        {
            'smap1.cdagente' : cdagente
        }
        ,success : function(response)
        {
            var json=Ext.decode(response.responseText);
            debug('#### obtener cdunieco agente response:',json);
            if(json.exito)
            {
                    _p28_smap1.cdunieco=json.smap1.cdunieco;
                debug('_p28_smap1:',_p28_smap1);
                Ext.Ajax.request(
                {
                    url     : _p28_urlCargarRetroactividadSuplemento
                    ,params :
                    {
                        'smap1.cdunieco'  : _p28_smap1.cdunieco
                        ,'smap1.cdramo'   : _p28_smap1.cdramo
                        ,'smap1.cdtipsup' : 1
                        ,'smap1.cdusuari' : _p28_smap1.cdusuari
                        ,'smap1.cdtipsit' : _p28_smap1.cdtipsit
                        ,'smap1.tipocot'  : 'I'
                    }
                    ,success : function(response)
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### obtener retroactividad:',json);
                        if(json.exito)
                        {
                            var feini = _fieldByName('feini');
                            var fefin = _fieldByName('fefin');
                            
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
            else
            {
                _fieldByLabel('AGENTE').reset();
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            _fieldByLabel('AGENTE').reset();
            errorComunicacion();
        }
    });
    
    _fieldByLabel('NEGOCIO').getStore().load(
    {
        params :
        {
            'params.cdagente' : cdagente
        }
    });
    
    debug('<_p28_ramo5AgenteSelect');
}

function _p28_ramo5ClienteChange(combcl)
{
    debug('>_p28_ramo5ClienteChange value:',combcl.getValue());
    
    var nombre  = _fieldLikeLabel('NOMBRE CLIENTE');
    var tipoper = _fieldByLabel('TIPO PERSONA');
    var codpos  = _fieldLikeLabel('CP CIRCULACI');
    var fenacim = _fieldLikeLabel('FECHA DE NAC');
    
	    
	    //cliente nuevo
	    if(combcl.getValue()=='S')
	    {
	        nombre.reset();
        tipoper.reset();
        codpos.reset();
        fenacim.reset();
        
        nombre.setReadOnly(false);
        tipoper.setReadOnly(false);
        codpos.setReadOnly(false);
        fenacim.setReadOnly(false);
        
	        _p28_recordClienteRecuperado=null;
	    }
	    //recuperar cliente
	    else if(combcl.getValue()=='N' && ( Ext.isEmpty(combcl.semaforo)||combcl.semaforo==false ) )
	    {
	        nombre.reset();
        tipoper.reset();
        codpos.reset();
        fenacim.reset();
        
        nombre.setReadOnly(true);
        tipoper.setReadOnly(true);
        codpos.setReadOnly(true);
        fenacim.setReadOnly(true);
	        
	        var ventana=Ext.create('Ext.window.Window',
	        {
	            title      : 'Recuperar cliente'
	            ,modal     : true
	            ,width     : 600
	            ,height    : 400
	            ,items     :
	            [
	                {
	                    layout    : 'hbox'
	                    ,defaults : { style : 'margin : 5px;' }
	                    ,items    :
	                    [
	                        {
	                            xtype       : 'textfield'
	                            ,name       : '_p28_recuperaRfc'
	                            ,fieldLabel : 'RFC'
	                            ,minLength  : 9
	                            ,maxLength  : 13
	                        }
	                        ,{
	                            xtype    : 'button'
	                            ,text    : 'Buscar'
	                            ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
	                            ,handler : function(button)
	                            {
	                                debug('recuperar cliente buscar');
	                                var rfc=_fieldByName('_p28_recuperaRfc').getValue();
	                                var valido=true;
	                                if(valido)
	                                {
	                                    valido = !Ext.isEmpty(rfc)
	                                             &&rfc.length>8
	                                             &&rfc.length<14;
	                                    if(!valido)
	                                    {
	                                        mensajeWarning('Introduza un RFC v&aacute;lido');
	                                    }
	                                }
	                                
	                                if(valido)
	                                {
	                                    button.up('window').down('grid').getStore().load(
	                                    {
	                                        params :
	                                        {
	                                            'map1.pv_rfc_i'       : rfc
	                                            ,'map1.cdtipsit'      : _p28_smap1.cdtipsit
	                                            ,'map1.pv_cdtipsit_i' : _p28_smap1.cdtipsit
	                                            ,'map1.pv_cdunieco_i' : _p28_smap1.cdunieco
	                                            ,'map1.pv_cdramo_i'   : _p28_smap1.cdramo
	                                            ,'map1.pv_estado_i'   : 'W'
	                                            ,'map1.pv_nmpoliza_i' : _fieldByName('nmpoliza').getValue()
	                                        }
	                                    });
	                                }
	                            }
	                        }
	                    ]
	                }
	                ,Ext.create('Ext.grid.Panel',
	                {
	                    title    : 'Resultados'
	                    ,columns :
	                    [
	                        {
	                            xtype    : 'actioncolumn'
	                            ,width   : 30
	                            ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
	                            ,handler : function(view,row,col,item,e,record)
	                            {
	                                debug('recuperar cliente handler record:',record);
	                                _p28_recordClienteRecuperado=record;
	                                nombre.setValue(record.raw.NOMBRECLI);
                                tipoper.setValue(record.raw.TIPOPERSONA);
                                codpos.setValue(record.raw.CODPOSTAL);
                                fenacim.setValue(record.raw.FENACIMICLI);
	                                ventana.destroy();
	                            }
	                        }
	                        ,{
	                            text       : 'Nombre'
	                            ,dataIndex : 'NOMBRECLI'
	                            ,width     : 200
	                        }
	                        ,{
	                            text       : 'Direcci&oacute;n'
	                            ,dataIndex : 'DIRECCIONCLI'
	                            ,flex      : 1
	                        }
	                    ]
	                    ,store : Ext.create('Ext.data.Store',
	                    {
	                        model     : '_p28_modeloRecuperado'
	                        ,autoLoad : false
	                        ,proxy    :
	                        {
	                            type    : 'ajax'
	                            ,url    : _p28_urlRecuperarCliente
	                            ,timeout: 240000
	                            ,reader :
	                            {
	                                type  : 'json'
	                                ,root : 'slist1'
	                            }
	                        }
	                    })
	                })
	            ]
	            ,listeners :
	            {
	                close : function()
	                {
	                    combcl.setValue('S');
	                }
	            }
	        }).show();
	        centrarVentanaInterna(ventana);
	    }
    debug('<_p28_ramo5ClienteChange');
}

function _p28_limpiar()
{
    debug('>_p28_limpiar');
    _fieldByName('nmpoliza').semaforo=true;
    _fieldById('_p28_form').getForm().reset();
    _fieldById('_p28_form').formOculto.getForm().reset();
    _fieldByName('nmpoliza').semaforo=false;
    
    _p28_cargarConfig();
    
    if(_p28_smap1.cdramo+'x'=='5x')
    {
        _p28_calculaVigencia();
        
        _fieldLikeLabel('CLIENTE NUEVO').setValue('S');    
        
        if(_p28_smap1.cdsisrol=='EJECUTIVOCUENTA')
        {
            var agente = _fieldByLabel('AGENTE');
            agente.setValue(_p28_smap1.cdagente);
            agente.setReadOnly(true);
            _p28_ramo5AgenteSelect(agente,_p28_smap1.cdagente);
        }
        
            _fieldLikeLabel('VALOR VEH').minValue=0;
            _fieldLikeLabel('VALOR VEH').maxValue=9999999;
    }
    
    debug('<_p28_limpiar');
}

function _p28_calculaVigencia(comp,val)
{
    debug('>_p28_calculaVigencia');
    var feini = _fieldByName('feini');
    var fefin = _fieldByName('fefin');
    
    var itemVigencia=_fieldByLabel('VIGENCIA');
    itemVigencia.hide();
    
    if(feini.isValid()&&fefin.isValid())
    {
        var milisDif = Ext.Date.getElapsed(feini.getValue(),fefin.getValue());
        var diasDif  = (milisDif/1000/60/60/24).toFixed(0);
        debug('milisDif:',milisDif,'diasDif:',diasDif);
        itemVigencia.setValue(diasDif);
    }
    debug('<_p28_calculaVigencia');
}

function _p28_editar()
{
    debug('>_p28_editar');
    var panelPri    = _fieldById('_p28_panelpri');
    var gridTarifas = _fieldById('_p28_gridTarifas');
    
    _fieldById('_p28_formCesion').destroy();
    panelPri.remove(gridTarifas,true);
    panelPri.doLayout();
    
    _p28_bloquear(false);
    debug('<_p28_editar');
}

function _p28_nueva()
{
    debug('>_p28_nueva');
    _p28_editar();
    _p28_limpiar();
    debug('<_p28_nueva');
}

function _p28_herenciaDescendiente(clave,marca,submarca,modelo,version,record)
{
    debug('>_p28_herenciaDescendiente');
    //var record = clave.findRecord('key',clave.getValue());
    debug('record:',record);
    var splitted=record.get('value').split(' - ');
    debug('splitted:',splitted);
    var clavev    = splitted[0];
    var marcav    = splitted[1];
    var submarcav = splitted[2];
    var modelov   = splitted[3];
    var versionv  = splitted[4];
    
    marca.setValue(marca.findRecord('value',marcav));
    submarca.heredar(true,function()
    {
        submarca.setValue(submarca.findRecord('value',submarcav));
        modelo.heredar(true,function()
        {
            modelo.setValue(modelo.findRecord('value',modelov));
            version.getStore().load(
            {
                params :
                {
                    'params.submarca' : submarca.getValue()
                    ,'params.modelo'  : modelo.getValue()
                }
                ,callback : function()
                {
                    version.setValue(version.findRecord('value',versionv));
                    _p28_cargarSumaAseguradaRamo5(clave,modelo);
                }
            });
        });
    });
    
    debug('<_p28_herenciaDescendiente');
}

function _p28_herenciaAscendente(clave,marca,submarca,modelo,version,callback)
{
    debug('>_p28_herenciaAscendente');
    var versionval = version.getValue();
    
    if(!Ext.isEmpty(versionval))
    {
        var versiondes = version.findRecord('key',versionval).get('value');
        clave.getStore().load(
        {
            params :
            {
                'params.cadena' : versiondes
            }
            ,callback : function()
            {
                var valor = versionval
                            +' - '
                            +marca.findRecord('key',marca.getValue()).get('value')
                            +' - '
                            +submarca.findRecord('key',submarca.getValue()).get('value')
                            +' - '
                            +modelo.findRecord('key',modelo.getValue()).get('value')
                            +' - '
                            +version.findRecord('key',versionval).get('value');
                debug('>valor:',valor);
                clave.setValue(clave.findRecord('value',valor));
                _p28_cargarSumaAseguradaRamo5(clave,modelo,callback);
            }
        });
    }
    
    debug('<_p28_herenciaAscendente');
}

function _p28_cargarSumaAseguradaRamo5(clave,modelo,callback)
{
    debug('>_p28_cargarSumaAseguradaRamo5');
    var form=_fieldById('_p28_form');
    form.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p28_urlCargarSumaAseguradaRamo5
        ,params  :
        {
            'smap1.cdtipsit'  : _p28_smap1.cdtipsit
            ,'smap1.clave'    : clave.getValue()
            ,'smap1.modelo'   : modelo.getValue()
            ,'smap1.cdsisrol' : _p28_smap1.cdsisrol
        }
        ,success : function(response)
        {
            form.setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('### cargar suma asegurada:',json);
            if(json.exito)
            {
                if(!Ext.isEmpty(json.respuesta))
                {
                 //comentado porque rafa no quiere avisos
                 //mensajeWarning(json.respuesta);
                }
                var sumaseg = _fieldByName('parametros.pv_otvalor13');
                sumaseg.setValue(json.smap1.sumaseg);
                sumaseg.valorCargado=json.smap1.sumaseg;
                try
                {
                    _fieldByLabel('RESPALDO VALOR').setValue(json.smap1.sumaseg);
                }
                catch(e)
                {
                    debugError('error al guardar respaldo valor',e);
                }
                _p28_cargarRangoValorRamo5(callback);
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            form.setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p28_cargarSumaAseguradaRamo5');



}

function _p28_cargar(boton)
{
    var nmpoliza = _fieldByName('nmpoliza').getValue();
    var valido   = !Ext.isEmpty(nmpoliza);
    if(!valido)
    {
        mensajeWarning('Introduce un n&uacute;mero v&aacute;lido');
    }
    
    if(valido)
    {
        var panelpri = _fieldById('_p28_panelpri');
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p28_urlCargar
            ,params  :
            {
                'smap1.nmpoliza'  : nmpoliza
                ,'smap1.cdramo'   : _p28_smap1.cdramo
                ,'smap1.cdunieco' : _p28_smap1.cdunieco
                ,'smap1.cdtipsit' : _p28_smap1.cdtipsit
            }
            ,success : function(response)
            {
             panelpri.setLoading(false);
             var json=Ext.decode(response.responseText);
    
    debug('### cargar cotizacion:',json);
    if(json.success)
    {
        var maestra  = json.slist1[0].ESTADO=='M';
        var fesolici    = Ext.Date.parse(json.smap1.FESOLICI,'d/m/Y');
        var fechaHoy    = Ext.Date.clearTime(new Date());
        var fechaLimite = Ext.Date.add(fechaHoy,Ext.Date.DAY,-1*(json.smap1.diasValidos-0));
        var vencida     = fesolici<fechaLimite;
        debug('fesolici='    , fesolici);
        debug('fechaHoy='    , fechaHoy);
        debug('fechaLimite=' , fechaLimite);
        debug('vencida='     , vencida , '.');

        _p28_limpiar();

        var iniVig = Ext.Date.parse(json.smap1.FEEFECTO,'d/m/Y').getTime();
        var finVig = Ext.Date.parse(json.smap1.FEPROREN,'d/m/Y').getTime();
        var milDif = finVig-iniVig;
        var diaDif = milDif/(1000*60*60*24);
        debug('diaDif:',diaDif);
      
        /*if(!maestra&&!vencida)
        {
            _fieldByName('feini').setValue(Ext.Date.parse(json.smap1.FEEFECTO,'d/m/Y'));
        }*/
        
        _fieldByName('feini').setValue(new Date());
        _fieldByName('fefin').setValue
        (
            Ext.Date.add
            (
                _fieldByName('feini').getValue()
                ,Ext.Date.DAY
                ,diaDif
            )
        );
        
        
        if(maestra)
        {
            _fieldByName('nmpoliza').setValue('');
            mensajeWarning('Se va a duplicar la p&oacute;liza emitida '+json.slist1[0].NMPOLIZA);
        }
        else if(vencida)
        {
            _fieldByName('nmpoliza').setValue('');
            mensajeWarning('La cotizaci&oacute;n ha vencido y solo puede duplicarse');
        }
        else
        {
            _fieldByName('nmpoliza').semaforo=true;
            _fieldByName('nmpoliza').setValue(nmpoliza);
            _fieldByName('nmpoliza').semaforo=false;
        }
        var primerInciso = new _p28_formModel(json.slist1[0]);
        if(_p28_smap1.cdramo=='5')
        {
            primerInciso.set('parametros.pv_otvalor14','S');
        }
        debug('primerInciso:',primerInciso);
        //leer elementos anidados
        var form      = _fieldById('_p28_form');
        var formItems = Ext.ComponentQuery.query('[fieldLabel]',form);
        debug('formItems:' , formItems);
        var numBlurs  = 0;
        for(var i=0;i<formItems.length;i++)
        {
            var item=formItems[i];
            if(item.anidado == true)
            {
                var numBlursSeguidos = 1;
                debug('contando blur:',item);
                for(var j=i+1;j<formItems.length;j++)
                {
                    if(formItems[j].anidado == true)
                    {
                        numBlursSeguidos=numBlursSeguidos+1;
                    }
                }
                if(numBlursSeguidos>numBlurs)
                {
                    numBlurs=numBlursSeguidos;
                }
            }
        }
        debug('numBlurs:',numBlurs);
        var i=0;
        var renderiza=function()
        {
            debug('renderiza',i);
            form.loadRecord(primerInciso);
            form.formOculto.loadRecord(primerInciso);
            debug('form oculto values:',form.formOculto.getValues());
            if(i<numBlurs)
            {
                i=i+1;
                for(var j=0;j<formItems.length;j++)
                {
                    var iItem  = formItems[j]; 
                    var iItem2 = formItems[j+1];
                    debug('iItem2:',iItem2,'store:',iItem2?iItem2.store:'iItem2 no');
                    if(iItem2&&iItem2.anidado==true)
                    {
                        debug('tiene blur y lo hacemos heredar',formItems[j]);
                        iItem2.heredar(true);
                    }
                }
                setTimeout(renderiza,1000);
            }
            else
            {
                panelpri.setLoading(false);
                
                var itemsTatripol = Ext.ComponentQuery.query('[name]',_fieldById('_p28_fieldsetTatripol'));
                debug('itemsTatripol:',itemsTatripol);
                for(var j in itemsTatripol)
                {
                    var tatri=itemsTatripol[j];
                    tatri.setValue(json.smap1[tatri.name]);
                }
                
                if(_p28_smap1.cdramo=='5')
                {
                    var clave    = _fieldByName('parametros.pv_otvalor06');
                    var marca    = _fieldByName('parametros.pv_otvalor07');
                    var submarca = _fieldByName('parametros.pv_otvalor08');
                    var modelo   = _fieldByName('parametros.pv_otvalor09');
                                var version  = _fieldByName('parametros.pv_otvalor10');
                    
                    _p28_herenciaAscendente(clave,marca,submarca,modelo,version,function()
                    {
                        form.loadRecord(primerInciso);
                        if(!Ext.isEmpty(primerInciso.raw.CLAVECLI))
                        {
                            if(maestra&&false)
                            {
                                _fieldLikeLabel('NOMBRE CLIENTE').setValue('');
                            }
                            else
                            {
                                _p28_recordClienteRecuperado = new _p28_modeloRecuperado(primerInciso.raw);
                                debug('_p28_recordClienteRecuperado:',_p28_recordClienteRecuperado);
                            
                                            var combcl      = _fieldLikeLabel('CLIENTE NUEVO');
                                
                                combcl.semaforo = true;
                                combcl.setValue('N');
                                combcl.semaforo = false;
                            }
                        }
                        
                        if(maestra||vencida)
                        {
                            _fieldById('_p28_form').formOculto.getForm().reset();
                        }
                        
                                    if(Ext.isEmpty(json.smap1.NTRAMITE)&&!vencida)
                        {
                            try
                            {
                                            _fieldLikeLabel('VALOR VEH').valorCargado=_fieldByLabel('RESPALDO VALOR').getValue();
                                _p28_cargarRangoValorRamo5(function()
                                {
                                    _p28_cotizar(!maestra&&!vencida);
                                });
                                
                            
                            }
                            catch(e)
                            {
                                debugError('error al parchar respaldo valor respaldo',e);
                            }
                        }
                        else if(vencida)
                        {
                            _p28_cotizar(!maestra&&!vencida);
                        }
                                    else
                        {
                            centrarVentanaInterna(Ext.create('Ext.window.Window',
                            {
                                title      : 'P&oacute;liza en emisi&oacute;n'
                                ,modal     : true
                                ,bodyStyle : 'padding:5px;'
                                ,closable  : false
                                ,html      : 'La cotizaci&oacute;n se encuentra en proceso de emisi&oacute;n'
                                ,buttonAlign : 'center'
                                ,buttons   :
                                [
                                    {
                                        text     : 'Complementar'
                                        ,handler : function()
                                        {
                                            var swExiper = (!Ext.isEmpty(_p28_recordClienteRecuperado)
                                                && Ext.isEmpty(_p28_recordClienteRecuperado.raw.CLAVECLI)
                                                && !Ext.isEmpty(_p28_recordClienteRecuperado.raw.CDIDEPER))? 'N' : 'S' ;
                                            Ext.create('Ext.form.Panel').submit(
                                            {
                                                url             : _p28_urlDatosComplementarios
                                                ,standardSubmit : true
                                                ,params         :
                                                {
                                                    'smap1.cdunieco'  : json.smap1.CDUNIECO
                                                    ,'smap1.cdramo'   : json.smap1.cdramo
                                                    ,'smap1.cdtipsit' : json.smap1.cdtipsit
                                                    ,'smap1.estado'   : 'W'
                                                    ,'smap1.nmpoliza' : json.smap1.nmpoliza
                                                    ,'smap1.ntramite' : json.smap1.NTRAMITE
                                                    ,'smap1.swexiper' : swExiper 
                                                }
                                            });
                                        }
                                    }
                                    ,{
                                        text     : 'Duplicar'
                                        ,handler : function(bot)
                                        {
                                            bot.up('window').close();
                                            _fieldByName('nmpoliza').setValue('');
                                            _fieldById('_p28_form').formOculto.getForm().reset();
                                        }
                                    }
                                ]
                            }).show());
                        }
                    });
                }
            }
        };
        panelpri.setLoading(true);
        if(_p28_smap1.cdramo+'x'=='5x'
            &&
            (
                            _p28_smap1.cdsisrol=='SUSCRIAUTO'
                            ||_p28_smap1.cdsisrol=='PROMOTORAUTO'
            ))
        {
            var agente  = _fieldByName('parametros.pv_otvalor01');
            var negocio = _fieldByLabel('NEGOCIO');
            agente.getStore().load(
            {
                params :
                {
                    'params.agente' : primerInciso.get('parametros.pv_otvalor01')
                }
                ,callback : function()
                {
                    agente.setValue(agente.findRecord('key',primerInciso.get('parametros.pv_otvalor01')));
                    _p28_ramo5AgenteSelect(agente,agente.getValue());
                    renderiza();
                }
            });
        }
        else
        {
            renderiza();
        }
    }
    else
    {
        mensajeError(json.error);
    }   
}
            ,failure : function()
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
}

function _p28_cargarRangoValorRamo5(callback)
{
    debug('>_p28_cargarRangoValorRamo5');
    var tipovalor = _fieldByLabel('TIPO VALOR');
    var valor     = _fieldLikeLabel('VALOR VEH');
    
    var tipovalorval = tipovalor.getValue();
    var valorval     = valor.getValue();
    var valorCargado = valor.valorCargado;
    
    var valido = !Ext.isEmpty(tipovalorval)&&!Ext.isEmpty(valorval)&&!Ext.isEmpty(valorCargado);
    
    if(valido)
    {
        var panelpri = _fieldById('_p28_panelpri');
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p28_urlCargarParametros
            ,params  :
            {
                'smap1.parametro' : 'RANGO_VALOR'
                ,'smap1.cdramo'   : _p28_smap1.cdramo
                ,'smap1.cdtipsit' : _p28_smap1.cdtipsit
                ,'smap1.clave4'   : tipovalorval
                ,'smap1.clave5'   : _p28_smap1.cdsisrol
            }
            ,success : function(response)
            {
                panelpri.setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('### obtener rango valor:',json);
                if(json.exito)
                {
                    valor.validator=function(value){
                        var r = true;
                        valormin = valorCargado*(1+(json.smap1.P1VALOR-0));
                        valormax = valorCargado*(1+(json.smap1.P2VALOR-0));
                        debug('valormin:',valormin);
                        debug('valormax:',valormax);
                        var me = _fieldLikeLabel('VALOR VEH');

                        if(_p28_smap1.cdsisrol =='EJECUTIVOCUENTA'){
                                if(Number(value)>Number(me.maximoTotal)){
                                    valor.setMinValue(me.maximoTotal);
                                    valor.setMaxValue(me.maximoTotal);
                                    valor.setMinValue(false);
                                    valor.setMaxValue(false);
                                    r='Favor de acudir a Mesa de Control para realizar la cotización.';
                                }else if((Number(valormin)>= Number(me.maximoTotal)) && (Number(valormax)>= Number(me.maximoTotal))){
                                    valor.setMinValue(me.maximoTotal);
                                    valor.setMaxValue(me.maximoTotal);
                            }else{
                                valor.setMaxValue(valormax);
                                valor.setMinValue(valormin);
                            }
                        }else{
                            valor.setMinValue(valormin);
                            valor.setMaxValue(valormax);
                        }
                       return r;
                    }
                    valor.isValid();
                    _p28_cargarParametrizacionCoberturas(callback);
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            } 
            ,failure : function()
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    debug('<_p28_cargarRangoValorRamo5');
}

function _p28_limitarCoberturasDependientesSumasegRamo5()
{
    var sumaAsegu = _fieldLikeLabel('VALOR VEH');
    var suma      = sumaAsegu.getValue();
    
    if(!Ext.isEmpty(suma))
    {
        Ext.Ajax.request(
        {
            url      : _p28_urlCargarParametros
            ,params  :
            {
                'smap1.parametro' : 'RANGO_COBERTURAS_DEPENDIENTES'
                ,'smap1.cdramo'   : _p28_smap1.cdramo
                ,'smap1.cdtipsit' : _p28_smap1.cdtipsit
                ,'smap1.clave4'   : _p28_smap1.cdsisrol
            }
            ,success : function(response)
            {
                var json=Ext.decode(response.responseText);
                debug('### obtener rangos coberturas dependientes:',json);
                if(json.exito)
                {
                    var equipoEspecial = _fieldByLabel('SUMA ASEGURADA EQUIPO ESPECIAL');
                        var min            = suma*(1+(json.smap1.P1VALOR-0));
                        var max            = suma*(1+(json.smap1.P2VALOR-0));
                        debug('min:',min,'max:',max);
                        equipoEspecial.setMinValue(min);
                        equipoEspecial.setMaxValue(max);
                        equipoEspecial.isValid();
                    
                        var adaptaciones = _fieldByLabel('SUMA ASEGURADA ADAPTACIONES Y CONVERSIONES');
                        min              = suma*(1+(json.smap1.P3VALOR-0));
                        max              = suma*(1+(json.smap1.P4VALOR-0));
                        debug('min:',min,'max:',max);
                        adaptaciones.setMinValue(min);
                        adaptaciones.setMaxValue(max);
                        adaptaciones.isValid();
                    }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : errorComunicacion
        });
    }
}


function _p28_nmpolizaChange(me)
{
    var sem = me.semaforo;
    if(Ext.isEmpty(sem)||sem==false)
    {
        me.sucio = true;
    }
    else
    {
        me.sucio = false;
    }
}

function _p28_clonar()
{
    debug('>_p28_clonar');
    _fieldById('_p28_form').formOculto.getForm().reset();
    _p28_editar();
    _fieldByName('nmpoliza').setValue('');
    debug('<_p28_clonar');
}

function _p28_coberturas()
{
    _p28_storeCoberturas.load(
    {
        params :
        {
            jsonCober_unieco   : _p28_smap1.cdunieco
            ,jsonCober_cdramo  : _p28_smap1.cdramo
            ,jsonCober_estado  : 'W'
            ,jsonCober_nmpoiza : _fieldByName('nmpoliza').getValue()
            ,jsonCober_cdplan  : _p28_selectedCdplan
            ,jsonCober_cdcia   : '20'
            ,jsonCober_situa   : _p28_selectedNmsituac
        }
    });
    _fieldById('_p28_gridCoberturas').setTitle('Plan ' + _p28_selectedDsplan);
    _p28_windowCoberturas.show();
    centrarVentanaInterna(_p28_windowCoberturas);
}

function _p28_tarifaSelect(selModel, record, row, column, eOpts)
{
    var gridTarifas = _fieldById('_p28_gridTarifas').down('grid');
    debug('column:',column);
    if(column>0)
    {
        column = (column * 2) -1;
    }
    debug('( column * 2 )-1:',column);
    var columnName=gridTarifas.columns[column].dataIndex;
    debug('record',record);
    debug('columnName',columnName);
    if(columnName=='DSPERPAG')
    {
        debug('DSPERPAG');
        _fieldById('_p28_botonCoberturas').setDisabled(true);
        _fieldById('_p28_botonComprar').setDisabled(true);
        _fieldById('_p28_botonImprimir').setDisabled(true);
        _fieldById('_p28_botonEnviar').setDisabled(true);
        _fieldById('_p28_botonDetalles').setDisabled(true);
    }
    else
    {
        // M N P R I M A X
        //0 1 2 3 4 5 6 7
        _p28_selectedCdperpag = record.get("CDPERPAG");
        _p28_selectedCdplan   = columnName.substr(7);
        _p28_selectedDsplan   = record.get("DSPLAN"+_p28_selectedCdplan);
        _p28_selectedNmsituac = record.get("NMSITUAC");
        debug('_p28_selectedCdperpag' , _p28_selectedCdperpag);
        debug('_p28_selectedCdplan'   , _p28_selectedCdplan);
        debug('_p28_selectedDsplan'   , _p28_selectedDsplan);
        debug('_p28_selectedNmsituac' , _p28_selectedNmsituac);
        
        _fieldById('_p28_botonCoberturas').setDisabled(false);
        _fieldById('_p28_botonComprar').setDisabled(false);
        _fieldById('_p28_botonImprimir').setDisabled(false);
        _fieldById('_p28_botonEnviar').setDisabled(false);
        _fieldById('_p28_botonDetalles').setDisabled(false);
    }
}

function _p28_comprar()
{
    debug('comprar');
    var panelPri = _fieldById('_p28_panelpri');
    panelPri.setLoading(true);
    var nombreTitular = '';
    
    Ext.Ajax.request(
    {
        url      : _p28_urlComprar
        ,params  :
        {
            comprarNmpoliza        : _fieldByName('nmpoliza').getValue()
            ,comprarCdplan         : _p28_selectedCdplan
            ,comprarCdperpag       : _p28_selectedCdperpag
            ,comprarCdramo         : _p28_smap1.cdramo
            ,comprarCdciaaguradora : '20'
            ,comprarCdunieco       : _p28_smap1.cdunieco
            ,cdtipsit              : _p28_smap1.cdtipsit
            ,'smap1.fechaInicio'   : Ext.Date.format(_fieldByName('feini').getValue(),'d/m/Y')
            ,'smap1.fechaFin'      : Ext.Date.format(_fieldByName('fefin').getValue(),'d/m/Y')
            ,'smap1.ntramite'      : _p28_smap1.ntramite
            ,'smap1.cdpersonCli'   : Ext.isEmpty(_p28_recordClienteRecuperado) ? '' : _p28_recordClienteRecuperado.raw.CLAVECLI
            ,'smap1.cdideperCli'   : Ext.isEmpty(_p28_recordClienteRecuperado) ? '' : _p28_recordClienteRecuperado.raw.CDIDEPER
            ,'smap1.cdagenteExt'   : _p28_smap1.cdramo+'x'=='5x' ? _fieldByLabel('AGENTE').getValue() : ''
        }
        ,success : function(response,opts)
        {
            panelPri.setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('### Comprar:',json);
            if (json.exito)
            {
                centrarVentanaInterna(Ext.Msg.show(
               {
                   title    : 'Tr&aacute;mite generado'
                   ,msg     : 'La cotizaci&oacute;n '+_fieldByName('nmpoliza').getValue()+' se guard&oacute; y no podr&aacute; ser modificada posteriormente'
                   ,buttons : Ext.Msg.OK
                   ,fn      : function()
                   {
                       var swExiper = (!Ext.isEmpty(_p28_recordClienteRecuperado) && Ext.isEmpty(_p28_recordClienteRecuperado.raw.CLAVECLI) && !Ext.isEmpty(_p28_recordClienteRecuperado.raw.CDIDEPER))? 'N' : 'S' ;
                       Ext.create('Ext.form.Panel').submit(
                       {
                           url             : _p28_urlDatosComplementarios
                           ,standardSubmit : true
                           ,params         :
                           {
                               'smap1.cdunieco'  : _p28_smap1.cdunieco
                               ,'smap1.cdramo'   : _p28_smap1.cdramo
                               ,'smap1.cdtipsit' : _p28_smap1.cdtipsit
                               ,'smap1.estado'   : 'W'
                               ,'smap1.nmpoliza' : _fieldByName('nmpoliza').getValue()
                               ,'smap1.ntramite' : json.smap1.ntramite
                               ,'smap1.swexiper' : swExiper
                           }
                       });
                   }
                }));                
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            panelPri.setLoading(false);
            errorComunicacion();
        }
    });
}

function _p28_imprimir()
{
    debug('>_p28_imprimir');
    var me = this;
    var urlRequestImpCotiza = _p28_urlImprimirCotiza
            + '?p_unieco='      + _p28_smap1.cdunieco
            + '&p_ramo='        + _p28_smap1.cdramo
            + '&p_subramo='     + _p28_smap1.cdtipsit
            + '&p_estado=W'
            + '&p_poliza='      + _fieldByName('nmpoliza').getValue()
            + '&p_suplem=0'
            + '&p_cdplan='      + _p28_selectedCdplan
            + '&p_plan='        + _p28_selectedCdplan
            + '&p_perpag='      + _p28_selectedCdperpag
            + '&p_ntramite='    + _p28_smap1.ntramite
            + '&p_cdusuari='    + _p28_smap1.cdusuari
            + '&destype=cache'
            + "&desformat=PDF"
            + "&userid="        + _p28_reportsServerUser
            + "&ACCESSIBLE=YES"
            + "&report="        + _p28_reporteCotizacion
            + "&paramform=no";
    debug('urlRequestImpCotiza:',urlRequestImpCotiza);
    var numRand = Math.floor((Math.random() * 100000) + 1);
    debug(numRand);
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title          : 'Cotizaci&oacute;n'
        ,width         : 700
        ,height        : 500
        ,collapsible   : true
        ,titleCollapse : true
        ,html : '<iframe innerframe="'
                + numRand
                + '" frameborder="0" width="100" height="100"'
                + 'src="'
                + _p28_urlViewDoc
                + "?contentType=application/pdf&url="
                + encodeURIComponent(urlRequestImpCotiza)
                + "\">"
                + '</iframe>'
        ,listeners :
        {
            resize : function(win,width,height,opt)
            {
                debug(width,height);
                $('[innerframe="'+ numRand+ '"]').attr(
                {
                    'width'   : width - 20
                    ,'height' : height - 60
                });
            }
        }
    }).show());
    debug('<_p28_imprimir');
}

function _p28_enviar()
{
    debug('>_p28_enviar');
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title        : 'Enviar cotizaci&oacute;n'
        ,width       : 550
        ,modal       : true
        ,height      : 150
        ,buttonAlign : 'center'
        ,bodyPadding : 5
        ,items       :
        [
            {
                xtype       : 'textfield'
                ,itemId     : '_p28_idInputCorreos'
                ,fieldLabel : 'Correo(s)'
                ,emptyText  : 'Correo(s) separados por ;'
                ,labelWidth : 100
                ,allowBlank : false
                ,blankText  : 'Introducir correo(s) separados por ;'
                ,width      : 500
            }
        ]
        ,buttons :
        [
            {
                text     : 'Enviar'
                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                ,handler : function()
                {
                    var me = this;
                    if (_fieldById('_p28_idInputCorreos').getValue().length > 0
                            &&_fieldById('_p28_idInputCorreos').getValue() != 'Correo(s) separados por ;')
                    {
                        debug('Se va a enviar cotizacion');
                        me.up().up().setLoading(true);
                        Ext.Ajax.request(
                        {
                            url : _p28_urlEnviarCorreo
                            ,params :
                            {
                                to          : _fieldById('_p28_idInputCorreos').getValue()
                                ,urlArchivo : _p28_urlImprimirCotiza
                                             + '?p_unieco='      + _p28_smap1.cdunieco
                                             + '&p_ramo='        + _p28_smap1.cdramo
                                             + '&p_subramo='     + _p28_smap1.cdtipsit
                                             + '&p_estado=W'
                                             + '&p_poliza='      + _fieldByName('nmpoliza').getValue()
                                             + '&p_suplem=0'
                                             + '&p_cdplan='      + _p28_selectedCdplan
                                             + '&p_plan='        + _p28_selectedCdplan
                                             + '&p_perpag='      + _p28_selectedCdperpag
                                             + '&p_ntramite='    + _p28_smap1.ntramite
                                             + '&p_cdusuari='    + _p28_smap1.cdusuari
                                             + '&destype=cache'
                                             + "&desformat=PDF"
                                             + "&userid="        + _p28_reportsServerUser
                                             + "&ACCESSIBLE=YES"
                                             + "&report="        + _p28_reporteCotizacion
                                             + "&paramform=no",
                                nombreArchivo : 'cotizacion_'+Ext.Date.format(new Date(),'Y-d-m_g_i_s_u')+'.pdf'
                            },
                            callback : function(options,success,response)
                            {
                                me.up().up().setLoading(false);
                                if (success)
                                {
                                    var json = Ext.decode(response.responseText);
                                    if (json.success == true)
                                    {
                                        me.up().up().destroy();
                                        centrarVentanaInterna(Ext.Msg.show(
                                        {
                                            title : 'Correo enviado'
                                            ,msg : 'El correo ha sido enviado'
                                            ,buttons : Ext.Msg.OK
                                        }));
                                    }
                                    else
                                    {
                                        mensajeError('Error al enviar');
                                    }
                                }
                                else
                                {
                                    errorComunicacion();
                                }
                            }
                        });
                    }
                    else
                    {
                        mensajeWarning('Introduzca al menos un correo');
                    }
                }
            }
            ,{
                text     : 'Cancelar'
                ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                ,handler : function()
                {
                    this.up().up().destroy();
                }
            }
        ]
    }).show());
    _fieldById('_p28_idInputCorreos').focus();
    debug('<_p28_enviar');
}

function _p28_detalles()
{
    debug('>_p28_detalles');
    Ext.Ajax.request(
    {
        url      : _p28_urlDetalleCotizacion
        ,params  :
        {
            'panel1.pv_cdunieco_i'  : _p28_smap1.cdunieco
            ,'panel1.pv_cdramo_i'   : _p28_smap1.cdramo
            ,'panel1.pv_estado_i'   : 'W'
            ,'panel1.pv_nmpoliza_i' : _fieldByName('nmpoliza').getValue()
            ,'panel1.pv_cdperpag_i' : _p28_selectedCdperpag
            ,'panel1.pv_cdplan_i'   : _p28_selectedCdplan
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### detalles:',json);
            if (json.success)
            {
                var orden = 0;
                var parentescoAnterior = 'qwerty';
                for ( var i = 0; i < json.slist1.length; i++)
                {
                    if (json.slist1[i].parentesco != parentescoAnterior)
                    {
                        orden++;
                        parentescoAnterior = json.slist1[i].parentesco;
                    }
                    json.slist1[i].orden_parentesco = orden+ '_'+ json.slist1[i].parentesco;
                }
                debug(json);
                centrarVentanaInterna(Ext.create('Ext.window.Window',
                {
                    title       : 'Detalles de cotizaci&oacute;n'
                    ,width      : 600
                    ,autoScroll : true
                    ,modal      : true
                    ,items      :
                    [
                        Ext.create('Ext.grid.Panel',
                        {
                            store    : Ext.create('Ext.data.Store',
                            {
                                model       : 'ModeloDetalleCotizacion'
                                ,groupField : 'orden_parentesco'
                                ,sorters    :
                                [
                                    {
                                        sorterFn : function(o1,o2)
                                        {
                                            debug('sorting:',o1,o2);
                                            if (o1.get('orden') === o2.get('orden'))
                                            {
                                                return 0;
                                            }
                                            return o1.get('orden')-0 < o2.get('orden')-0 ? -1 : 1;
                                        }
                                    }
                                ]
                                ,proxy      :
                                {
                                    type    : 'memory'
                                    ,reader : 'json'
                                }
                                ,data : json.slist1
                            })
                            ,columns :
                            [
                                {
                                    header           : 'Nombre de la cobertura'
                                    ,dataIndex       : 'Nombre_garantia'
                                    ,flex            : 3
                                    ,summaryType     : 'count'
                                    ,summaryRenderer : function(value)
                                    {
                                        return Ext.String.format('Total de {0} cobertura{1}',value,value !== 1 ? 's': '');
                                    }
                                }
                                ,{
                                    header       : 'Importe por cobertura'
                                    ,dataIndex   : 'Importe'
                                    ,flex        : 1
                                    ,renderer    : Ext.util.Format.usMoney
                                    ,align       : 'right'
                                    ,summaryType : 'sum'
                                } 
                            ]
                            ,features :
                            [
                                {
                                    groupHeaderTpl :
                                    [
                                        '{name:this.formatName}'
                                        ,{
                                            formatName : function(name)
                                            {
                                                return name.split("_")[1];
                                            }
                                        }
                                    ]
                                ,ftype          : 'groupingsummary'
                                ,startCollapsed : false
                                }
                            ]
                        })
                        ,Ext.create('Ext.toolbar.Toolbar',
                        {
                            buttonAlign : 'right'
                            ,items      :
                            [
                                '->'
                                ,Ext.create('Ext.form.Label',
                                {
                                    style          : 'color:white;'
                                    ,initComponent : function()
                                    {
                                        var sum = 0;
                                        for ( var i = 0; i < json.slist1.length; i++)
                                        {
                                            sum += parseFloat(json.slist1[i].Importe);
                                        }
                                        this.setText('Total: '+ Ext.util.Format.usMoney(sum));
                                        this.callParent();
                                    }
                                })
                            ]
                        })
                    ]
                }).show());
            }
            else
            {
                mensajeError('Error al obtener detalle');
            }
        }
        ,failure : errorComunicacion
    });
    debug('<_p28_detalles');
}

function _p28_guardarConfig()
{
    debug('>_p28_guardarConfig');
    var params =
    {
        'smap1.cdramo'    : _p28_smap1.cdramo
        ,'smap1.cdtipsit' : _p28_smap1.cdtipsit
        ,'smap1.cdusuari' : _p28_smap1.cdusuari
    };
    var items  = Ext.ComponentQuery.query('[fieldLabel]',_fieldById('_p28_panel4Fieldset'));
    var valido = true;
    for(var i=0;i<items.length;i++)
    {
        valido = valido && items[i].isValid();
        params['smap1.valor'+(items[i].name.slice(-2))]=items[i].getValue();
    }
    debug('params:',params);
    
    if(!valido)
    {
        datosIncompletos();
    }
    
    if(valido)
    {
        var panelpri = _fieldById('_p28_panelpri');
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p28_urlGuardarConfig
            ,params  : params
            ,success : function(response)
            {
                panelpri.setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('### guardar config:',json);
                if(json.exito)
                {
                    mensajeCorrecto('Configuraci&oacute;n guardada',json.respuesta);
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : function()
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p28_guardarConfig');
}

function _p28_cesionClic()
{
    debug('>_p28_cesionClic');
    _fieldById('_p28_formDescuento').windowCesion.show();
    centrarVentanaInterna(_fieldById('_p28_formDescuento').windowCesion);
    debug('<_p28_cesionClic');
}

function _p28_cargarParametrizacionCoberturas(callback)
{
    debug('>_p28_cargarParametrizacionCoberturas callback:',!Ext.isEmpty(callback),'DUMMY');
    
    var _f1_negocio      = _fieldByLabel('NEGOCIO').getValue();
    var _f1_tipoServicio = _fieldByLabel('TIPO SERVICIO').getValue();
    var _f1_modelo       = _fieldByLabel('MODELO').getValue();
    var _f1_tipoPersona  = _fieldByLabel('TIPO PERSONA').getValue();
    var _f1_submarca     = _fieldByLabel('SUBMARCA').getValue();
    var _f1_clavegs      = _fieldLikeLabel('AUTO').getValue();
    
    var valido = !Ext.isEmpty(_f1_negocio)
                 &&!Ext.isEmpty(_f1_tipoServicio)
                 &&!Ext.isEmpty(_f1_modelo)
                 &&!Ext.isEmpty(_f1_tipoPersona)
                 &&!Ext.isEmpty(_f1_submarca)
                 &&!Ext.isEmpty(_f1_clavegs);
                 
    if(valido)
    {
        var _f1_panelpri = _fieldById('_p28_panelpri');
        _f1_panelpri.setLoading(true);
        var params =
        {
            'smap1.cdtipsit'      : _p28_smap1.cdtipsit
            ,'smap1.cdsisrol'     : _p28_smap1.cdsisrol
            ,'smap1.negocio'      : _f1_negocio
            ,'smap1.tipoServicio' : _f1_tipoServicio
            ,'smap1.modelo'       : _f1_modelo
            ,'smap1.tipoPersona'  : _f1_tipoPersona
            ,'smap1.submarca'     : _f1_submarca
            ,'smap1.clavegs'      : _f1_clavegs
        };
        Ext.Ajax.request(
        {
            url      : _p28_urlCargarParamerizacionCoberturas
            ,params  : params
            ,success : function(response)
            {
                _f1_panelpri.setLoading(false);
                var _f1_json=Ext.decode(response.responseText);
                debug('### parametrizacion:',_f1_json);
                if(_f1_json.exito)
                {
                    for(var i=0;i<_f1_json.slist1.length;i++)
                    {
                        var item = _fieldByName('parametros.pv_otvalor'+(('00'+_f1_json.slist1[i].cdatribu).slice(-2)));
                        if(_f1_json.slist1[i].aplica+'x'=='1x')
                        {
                            if(<s:property value='%{getSmap1().containsKey("debug")}' />)
                            {
                                item.setReadOnly(false);
                                item.addCls('green');
                                item.removeCls('red');
                            }
                            else
                            {
                                item.show();
                            }
                            var minimo = _f1_json.slist1[i].minimo;
                            var maximo = _f1_json.slist1[i].maximo;
                            if(!Ext.isEmpty(minimo)&&!Ext.isEmpty(maximo))
                            {
                                item.minValue = minimo;
                                item.maxValue = maximo;
                                if(item.xtype=='combobox')
                                {
                                    item.validator=function(value)
                                    {
                                        var valido=true;
                                        if(value+'x'!='x')
                                        {
                                            var value=this.getStore().findRecord('value',value).get('key');
                                            if(Number(value)<Number(this.minValue))
                                            {
                                                valido = 'El valor m&iacute;nimo es '+this.minValue;
                                            }
                                            else if(Number(value)>Number(this.maxValue))
                                            {
                                                valido = 'El valor m&aacute;ximo es '+this.maxValue;
                                            }
                                        }
                                        return valido;
                                    }
                                    if(!item.isValid())
                                    {
                                        item.reset();
                                    }
                                    debug('item=',item.fieldLabel);
                                    debug('minimo=',minimo,'maximo=',maximo);
                                    item.store.filterBy(function(record)
                                    {
                                        debug('filtrando record=',record);
                                        var key=record.get('key')-0;
                                        debug('quitando key=',key,key>=minimo&&key<=maximo,'.');
                                        return key>=minimo&&key<=maximo;
                                    });
                                    item.on(
                                    {
                                        expand : function(me)
                                        {
                                            var minimo = me.minValue;
                                            var maximo = me.maxValue;
                                            me.store.filterBy(function(record)
                                            {
                                                debug('filtrando record=',record);
                                                var key=record.get('key')-0;
                                                debug('quitando key=',key,key>=minimo&&key<=maximo,'.');
                                                return key>=minimo&&key<=maximo;
                                            });
                                        }
                                    });
                                }
                            }
                            item.isValid();
                        }
                        else
                        {
                            if(<s:property value='%{getSmap1().containsKey("debug")}' />)
                            {
                                item.setReadOnly(true);
                                item.addCls('red');
                                item.removeCls('green');
                            }
                            else
                            {
                                item.hide();
                            }
                            item.setValue(_f1_json.slist1[i].valor);
                        }
                    }
                    if(<s:property value='%{getSmap1().containsKey("debug")}' />)
                    {
                        var aux1 = '';
                        for(var i in params)
                        {
                            aux1 = aux1+i+':'+params[i]+'\n';
                        }
                        var aux2 = '';
                        for(var i=0;i<_f1_json.slist1.length;i++)
                        {
                            aux2 = aux2
                                   +_f1_json.slist1[i].cdatribu+' - '
                                   +'aplica ('+_f1_json.slist1[i].aplica
                                   +') valor ('+_f1_json.slist1[i].valor
                                   +') minimo ('+_f1_json.slist1[i].minimo
                                   +') maximo ('+_f1_json.slist1[i].maximo
                                   +')\n';
                        }
                        centrarVentanaInterna(Ext.create('Ext.window.Window',
                        {
                            title   : '[DEBUG] PARAMETRIZACION DE COBERTURAS'
                            ,items  :
                            [
                                {
                                    xtype   : 'textarea'
                                    ,width  : 400
                                    ,height : 150
                                    ,value  : aux1
                                }
                                ,{
                                    xtype   : 'textarea'
                                    ,width  : 400
                                    ,height : 450
                                    ,value  : aux2
                                }
                            ]
                        }).show());
                    }
                    if(!Ext.isEmpty(callback))
                    {
                        callback();
                    }
                    
                    if(_p28_smap1.cdtipsit+'x'=='ARx'||_p28_smap1.cdtipsit+'x'=='PPx')
                    {
                        _fieldLikeLabel('CANAD').heredar();
                    }
                }
                else
                {
                    mensajeError(_f1_json.respuesta);
                }
            }
            ,failure : function()
            {
                _f1_panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p28_cargarParametrizacionCoberturas');
}

function _p28_cargarConfig()
{
    debug('>_p28_cargarConfig');
    Ext.Ajax.request(
    {
        url     : _p28_urlCargarConfig
        ,params :
        {
            'smap1.cdramo'    : _p28_smap1.cdramo
            ,'smap1.cdtipsit' : _p28_smap1.cdtipsit
            ,'smap1.cdusuari' : _p28_smap1.cdusuari
        }
        ,success : function(response)
        {
            var json = Ext.decode(response.responseText);
            debug('### config:',json);
            if(json.exito)
            {
                for(var prop in json.smap1)
                {
                    var cmp = _fieldByName(prop);
                        if(!Ext.isEmpty(cmp.heredar))
                        {
                            cmp.forceSelection=false;
                        }
                        cmp.setValue(json.smap1[prop]);
                        if(!Ext.isEmpty(cmp.heredar))
                        {
                            cmp.heredar(true,function(me)
                            {
                                me.forceSelection=true;
                                if(me.findRecord('key',me.getValue())==false)
                                {
                                    me.reset();
                                }
                            });
                    }
                }
                _p28_inicializarTatripol();
                _p28_cargarParamerizacionCoberturasRol();
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
    debug('<_p28_cargarConfig');
}

function _p28_recuperaObligatorioCamionRamo5()
{
    debug('>_p28_recuperaObligatorioCamionRamo5');
    _fieldLikeLabel('TRACTO').setLoading(true);
    Ext.Ajax.request(
    {
        url     : _p28_urlCargarObligatorioCamionRamo5
        ,params :
        {
            'smap1.clave' : _fieldLikeLabel('CLAVE').getValue()
        }
        ,success : function(response)
        {
            _fieldLikeLabel('TRACTO').setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('### obligatorio camion:',json);
            _fieldLikeLabel('TRACTO').allowBlank=json.smap1.tipo-0!=13;
            _fieldLikeLabel('TRACTO').isValid();
        }
        ,failure : function()
        {
            _fieldLikeLabel('TRACTO').setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p28_recuperaObligatorioCamionRamo5');
}

function _p28_cargarParamerizacionCoberturasRol()
{
    debug('>_p28_cargarParamerizacionCoberturasRol<');
    var params=
    {
        'smap1.cdtipsit'  : _p28_smap1.cdtipsit
        ,'smap1.cdsisrol' : _p28_smap1.cdsisrol
    };
    Ext.Ajax.request(
    {
        url      : _p28_urlCargarParamerizacionCoberturasRol
        ,params  : params
        ,success : function(response)
        {
            var json=Ext.decode(response.responseText);
            debug('### configuracion por rol:',json);
            if(json.exito)
            {
                for(var i=0;i<json.slist1.length;i++)
                {
                    var item = _fieldByName('parametros.pv_otvalor'+(('00'+json.slist1[i].cdatribu).slice(-2)));
                    if(json.slist1[i].aplica+'x'=='1x')
                    {
                        if(<s:property value='%{getSmap1().containsKey("debug")}' />)
                        {
                            item.setReadOnly(false);
                            item.addCls('green');
                            item.removeCls('red');
                        }
                        else
                        {
                            item.show();
                        }
                        item.isValid();
                    }
                    else
                    {
                        if(<s:property value='%{getSmap1().containsKey("debug")}' />)
                        {
                            item.setReadOnly(true);
                            item.addCls('red');
                            item.removeCls('green');
                        }
                        else
                        {
                            item.hide();
                        }
                        item.setValue(json.slist1[i].valor);
                    }
                }
                if(<s:property value='%{getSmap1().containsKey("debug")}' />)
                {
                    var aux1 = '';
                    for(var i in params)
                    {
                        aux1 = aux1+i+':'+params[i]+'\n';
                    }
                    var aux2 = '';
                    for(var i=0;i<json.slist1.length;i++)
                    {
                        aux2 = aux2
                               +json.slist1[i].cdatribu+' - '
                               +'aplica ('+json.slist1[i].aplica
                               +') valor ('+json.slist1[i].valor
                               +')\n';
                    }
                    centrarVentanaInterna(Ext.create('Ext.window.Window',
                    {
                        title   : '[DEBUG] PARAMETRIZACION DE COBERTURAS POR ROL'
                        ,items  :
                        [
                            {
                                xtype   : 'textarea'
                                ,width  : 400
                                ,height : 70
                                ,value  : aux1
                            }
                            ,{
                                xtype   : 'textarea'
                                ,width  : 400
                                ,height : 450
                                ,value  : aux2
                            }
                        ]
                    }).show());
                }
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
}

function _p28_inicializarTatripol(itemsTatripol)
{

    if(Ext.isEmpty(itemsTatripol))
    {
        itemsTatripol = Ext.ComponentQuery.query('[name]',_fieldById('_p28_fieldsetTatripol'));
        debug('itemsTatripol:',itemsTatripol);
    }

    for(var i in itemsTatripol)
    {
        var tatriItem = itemsTatripol[i];
        
        if(_p28_smap1.cdramo+'x'=='5x' && tatriItem.fieldLabel=='TIPO CAMBIO')
        {
            if(Ext.isEmpty(_p28_precioDolarDia))
            {
                tatriItem.setLoading(true);
                Ext.Ajax.request(
                {
                    url      : _p28_urlCargarTipoCambioWS
                    ,success : function(response)
                    {
                        var me=_fieldByLabel('TIPO CAMBIO',_fieldById('_p28_fieldsetTatripol'));
                        me.setLoading(false);
                        var json=Ext.decode(response.responseText);
                        debug('### dolar:',json);
                        _p28_precioDolarDia=json.smap1.dolar;
                        me.setValue(_p28_precioDolarDia);
                    }
                    ,failure : function()
                    {
                        _fieldByLabel('TIPO CAMBIO',_fieldById('_p28_fieldsetTatripol')).setLoading(false);
                        errorComunicacion();
                    }
                });
            }
            else
            {
                tatriItem.setValue(_p28_precioDolarDia);
            }
            tatriItem.hide();
        }
        else if(_p28_smap1.cdramo+'x'=='5x' && tatriItem.fieldLabel=='MONEDA')
        {
            tatriItem.select('1');
        }
    }
}

function _p28_atributoNacimientoContratante(combo)
{
    var val = 'S';
    
    if(combo != 'S')
    {
        val = combo.getValue();
    }
    
    debug('_p28_atributoNacimientoContratante val:',val,'.');
    
    if(val == 'S')
        {
            if(!Ext.isEmpty(_fieldLikeLabel('FECHA DE NACIMIENTO DEL CONTRATANTE',null,true)))
            {
                _fieldLikeLabel('FECHA DE NACIMIENTO DEL CONTRATANTE').allowBlank=false;
            }

        }
    else
        {
           if(!Ext.isEmpty(_fieldLikeLabel('FECHA DE NACIMIENTO DEL CONTRATANTE',null,true)))
           {
                _fieldLikeLabel('FECHA DE NACIMIENTO DEL CONTRATANTE').allowBlank=true;
           }

        }
}

function _p28_recuperarClienteTramite()
{
    /*
     * Cuando se tiene cdpercli se recupera para ramo 5 cuando exista el combo de cliente nuevo
     * inicio
     */
    if(!Ext.isEmpty(_p28_smap1.cdpercli))
    {
        var ckCdpercli = 'Recuperando cliente de tr\u00e1mite';
        try
        {
            var comboCliente = _fieldLikeLabel('CLIENTE NUEVO',null,true);
            debug('combo cliente nuevo:',comboCliente);
            if(!Ext.isEmpty(comboCliente))
            {
                debug('Entro a recuperar cliente con cdperson:',_p28_smap1.cdpercli);
                _mask(ckCdpercli);
                Ext.Ajax.request(
                {
                    url      : _p28_urlRecuperarCliente
                    ,params  :
                    {
                        'map1.pv_cdperson_i'  : _p28_smap1.cdpercli
                        ,'map1.soloBD'        : 'S'
                        ,'map1.pv_rfc_i'      : ''//rfc
                        ,'map1.cdtipsit'      : _p28_smap1.cdtipsit
                        ,'map1.pv_cdtipsit_i' : _p28_smap1.cdtipsit
                        ,'map1.pv_cdunieco_i' : _p28_smap1.cdunieco
                        ,'map1.pv_cdramo_i'   : _p28_smap1.cdramo
                        ,'map1.pv_estado_i'   : 'W'
                        ,'map1.pv_nmpoliza_i' : _fieldByName('nmpoliza').getValue()
                    }
                    ,success : function(response)
                    {
                        _unmask();
                        var ck = 'Decodificando respuesta al recuperar cliente de tr\u00e1mite';
                        try
                        {
                            var json = Ext.decode(response.responseText);
                            debug('### recuperacion cliente tramite:',json);
                            
                            comboCliente.semaforo = true;
                            comboCliente.setValue('N');
                            comboCliente.setReadOnly(true);
                            comboCliente.semaforo = false;
                            
                            _p28_recordClienteRecuperado = new _p28_modeloRecuperado(json.slist1[0]);
                            debug('_p28_recordClienteRecuperado:',_p28_recordClienteRecuperado);
                            
                            var nombreCliCmp    = _fieldLikeLabel('NOMBRE CLIENTE',null,true)
                                ,tipoPerCmp     = _fieldLikeLabel('TIPO PERSONA',null,true)
                                ,codPosCliCmp   = _fieldLikeLabel('CP CIRCULACI',null,true)
                                ,feNacimiCliCmp = _fieldLikeLabel('FECHA DE NACIMIENTO',null,true);
                            
                            if(!Ext.isEmpty(nombreCliCmp))
                            {
                                nombreCliCmp.setValue(_p28_recordClienteRecuperado.get('NOMBRECLI'));
                                nombreCliCmp.setReadOnly(true);
                            }
                            
                            if(!Ext.isEmpty(tipoPerCmp))
                            {
                                tipoPerCmp.setValue(_p28_recordClienteRecuperado.raw.TIPOPERSONA);
                                tipoPerCmp.setReadOnly(true);
                            }
                            
                            if(!Ext.isEmpty(codPosCliCmp))
                            {
                                codPosCliCmp.setValue(_p28_recordClienteRecuperado.raw.CODPOSTAL);
                                codPosCliCmp.setReadOnly(true);
                            }
                            
                            if(!Ext.isEmpty(feNacimiCliCmp))
                            {
                                feNacimiCliCmp.setValue(_p28_recordClienteRecuperado.raw.FENACIMICLI);
                                feNacimiCliCmp.setReadOnly(true);
                            }
                            
                        }
                        catch(e)
                        {
                            manejaException(e,ck);
                        }
                    }
                    ,failure : function()
                    {
                        _unmask();
                        errorComunicacion(null,'Error al recuperar cliente de tr\u00e1mite');
                    }
                });
            }
        }
        catch(e)
        {
            manejaException(e,ckCdpercli);
        }
    }
    /*
     * Cuando se tiene cdpercli se recupera para ramo 5 cuando exista el combo de cliente nuevo
     * fin
     */
}

/*
 * Este metodo busca en los otvalor del tramite el numero de la ultima cotizacion
 * realizada para recuperarla automaticamente en pantalla
 */
function _p28_recuperarCotizacionDeTramite()
{
    if(!Ext.isEmpty(_p28_flujo))
    {
        var mask, ck = 'Recuperando cotizaci\u00f3n de tr\u00e1mite';
        
        try
        {
            mask = _maskLocal(ck);
            Ext.Ajax.request(
            {
                url      : _p28_urlRecuperarOtvalorTramiteXDsatribu
                ,params  :
                {
                    'params.ntramite'  : _p28_flujo.ntramite
                    ,'params.dsatribu' : 'COTIZACI%N%TR%MITE'
                }
                ,success : function(response)
                {
                    mask.close();
                    var ck = 'Decodificando respuesta al recuperar cotizaci\u00f3n de tr\u00e1mite';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### cotizacion de tramite:',json);
                        if(json.success===true)
                        {
                            if(!Ext.isEmpty(json.params.otvalor))
                            {
                                _fieldByName('nmpoliza').setValue(json.params.otvalor);
                                _p28_cargar();
                            }
                        }
                        else
                        {
                            mensajeError(json.message);
                        }
                    }
                    catch(e)
                    {
                        manejaException(e,ck);
                    }
                }
                ,failure : function(response)
                {
                    mask.close();
                    errorComunicacion(null,'Error al recuperar cotizaci\u00f3n de tr\u00e1mite');
                }
            });
        }
        catch(e)
        {
            manejaException(e,ck,mask);
        }
    }
}

function _p28_actualizarCotizacionTramite(callback)
{
    var ck = 'Registrando cotizaci\u00f3n de tr\u00e1mite';
	try
	{
	    _mask(ck);
	    Ext.Ajax.request(
	    {
	        url      : _p28_urlActualizarOtvalorTramiteXDsatribu
	        ,params  :
	        {
	            'params.ntramite'  : _p28_flujo.ntramite
	            ,'params.dsatribu' : 'COTIZACI%N%TR%MITE%'
	            ,'params.otvalor'  : _fieldByName('nmpoliza').getValue()
	            ,'params.accion'   : 'U'
	        }
	        ,success : function(response)
	        {
	            _unmask();
	            var ck = 'Decodificando respuesta al guardar estatus de tr\u00e1mite';
	            try
	            {
	                var json = Ext.decode(response.responseText);
	                debug('### guardar estatus de tramite:',json);
	                if(json.success===true)
	                {
	                    var ck = 'Guardando detalle de cotiazci\u00f3n de tr\u00e1mite';
	                    try
	                    {
	                        _mask(ck);
	                        Ext.Ajax.request(
	                        {
	                            url      : _p28_urlDetalleTramite
	                            ,params  :
	                            {
	                                'smap1.ntramite'  : _p28_flujo.ntramite
	                                ,'smap1.status'   : _p28_flujo.status
	                                ,'smap1.dscoment' : 'Se guard\u00f3 la cotizaci\u00f3n '+_fieldByName('nmpoliza').getValue()
	                                ,'smap1.swagente' : 'S'
	                            }
	                            ,success : function(response)
	                            {
	                                _unmask();
	                                var ck = 'Decodificando respuesta al guardar detalle de cotizaci\u00f3n de tr\u00e1mite';
	                                try
	                                {
	                                    var jsonDetalle = Ext.decode(response.responseText);
	                                    debug('### guardar detalle cotizacion tramite:',jsonDetalle);
	                                    if(!Ext.isEmpty(callback))
	                                    {
	                                        callback();
	                                    }
	                                }
	                                catch(e)
	                                {
	                                    manejaException(e,ck);
	                                }
	                            }
	                            ,failure : function()
	                            {
	                                _unmask();
	                                errorComunicacion(null,'Error al guardar detalle de cotizaci\u00f3n de tr\u00e1mite');
	                            }
	                        });
	                    }
	                    catch(e)
	                    {
	                        _unmask();
	                        manejaException(e,ck);
	                    }
	                }
	                else
	                {
	                    mensajeError(json.message);
	                }
	            }
	            catch(e)
	            {
	                manejaException(e,ck);
	            }
	        }
	        ,failure : function()
	        {
	            _unmask();
	            errorComunicacion(null,'Error al guardar estatus de tr\u00e1mite');
	        }
	    });
	}
	catch(e)
	{
	    _unmask();
	    manejaException(e,ck);
	}
}

function _p28_actualizarSwexiperTramite(callback)
{
    var ck = 'Registrando estado de cliente de tr\u00e1mite';
    try
    {
        var swExiper = (
            !Ext.isEmpty(_p28_recordClienteRecuperado)
            && Ext.isEmpty(_p28_recordClienteRecuperado.raw.CLAVECLI)
            && !Ext.isEmpty(_p28_recordClienteRecuperado.raw.CDIDEPER)
            ) ? 'N' : 'S' ;
    
        _mask(ck);
        Ext.Ajax.request(
        {
            url      : _p28_urlActualizarOtvalorTramiteXDsatribu
            ,params  :
            {
                'params.ntramite'  : _p28_flujo.ntramite
                ,'params.dsatribu' : 'SWEXIPER'
                ,'params.otvalor'  : swExiper
                ,'params.accion'   : 'U'
            }
            ,success : function(response)
            {
                _unmask();
                var ck = 'Decodificando respuesta al guardar estado de cliente de tr\u00e1mite';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### guardar estatus de cliente de tramite:',json);
                    if(json.success===true)
                    {
                        if(!Ext.isEmpty(callback))
                        {
                            callback();
                        }
                    }
                    else
                    {
                        mensajeError(json.message);
                    }
                }
                catch(e)
                {
                    manejaException(e,ck);
                }
            }
            ,failure : function()
            {
                _unmask();
                errorComunicacion(null,'Error al guardar estado de cliente de tr\u00e1mite');
            }
        });
    }
    catch(e)
    {
        _unmask();
        manejaException(e,ck);
    }
}

/*
 * Esta funcion se usa para recuperar una poliza de SIGS para renovacion en ICE
 * solo debe llamarse si: es por flujo, y si no hay una cotizacion anterior
 * si se recupera cotizacion por _p28_recuperarCotizacionDeTramite entonces
 * esta funcion no debe recuperar nada porque seria doble recuperacion, es decir
 * la condicion que tenga _p28_recuperarCotizacionDeTramite debe estar negada aqui
 */
function _p28_recuperarPolizaSIGS()
{
    if(!Ext.isEmpty(_p28_flujo))
    {
        debug('_p28_recuperarPolizaSIGS');
        
        var mask, ck = 'Recuperando cotizaci\u00f3n de tr\u00e1mite para revisar renovaci\u00f3n';
        
        try
        {
            mask = _maskLocal(ck);
            Ext.Ajax.request(
            {
                url      : _p28_urlRecuperarOtvalorTramiteXDsatribu
                ,params  :
                {
                    'params.ntramite'  : _p28_flujo.ntramite
                    ,'params.dsatribu' : 'COTIZACI%N%TR%MITE'
                }
                ,success : function(response)
                {
                    mask.close();
                    var ck = 'Decodificando respuesta al recuperar cotizaci\u00f3n de tr\u00e1mite';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### cotizacion de tramite:',json);
                        if(json.success===true)
                        {
                            if(Ext.isEmpty(json.params.otvalor)) // si no se ha cotizado antes, verificamos si hay renovacion
                            {
                                ck = 'Revisando p\u00f3liza de renovaci\u00f3n';
                                
                                mask = _maskLocal(ck);
                                
                                Ext.Ajax.request(
                                {
                                    url      : _p28_urlRecuperarDatosTramiteValidacion
                                    ,params  : _flujoToParams(_p28_flujo)
                                    ,success : function(response)
                                    {
                                        mask.close();
                                        
                                        var ck = 'Decodificando respuesta al recuperar datos para revisar renovaci\u00f3n';
                                        
                                        try
                                        {
                                            var jsonDatTram = Ext.decode(response.responseText);
                                            debug('### jsonDatTram:',jsonDatTram,'.');
                                            
                                            if(jsonDatTram.success === true)
                                            {
                                                if(!Ext.isEmpty(jsonDatTram.datosTramite.TRAMITE.RENPOLIEX))
                                                {
                                                    var renuniext  = jsonDatTram.datosTramite.TRAMITE.RENUNIEXT
                                                        ,renramo   = jsonDatTram.datosTramite.TRAMITE.RENRAMO
                                                        ,renpoliex = jsonDatTram.datosTramite.TRAMITE.RENPOLIEX;
                                                        
                                                    debug('se encontraron datos para renovar:',renuniext,renramo,renpoliex,'.');
                                                    
                                                    ck = 'Seteando valores para renovaci\u00f3n';
                                                    
                                                    _fieldByName('sucursal').setValue(jsonDatTram.datosTramite.TRAMITE.RENUNIEXT);
                                                    _fieldByName('ramo').setValue(jsonDatTram.datosTramite.TRAMITE.RENRAMO);
                                                    _fieldByName('poliza').setValue(jsonDatTram.datosTramite.TRAMITE.RENPOLIEX);
                                                    
                                                    _p28_cargarPoliza();
                                                }
                                            }
                                            else
                                            {
                                                mensajeError(jsonDatTram.message);
                                            }
                                        }
                                        catch(e)
                                        {
                                            manejaException(e,ck,mask);
                                        }
                                        
                                    }
                                    ,failure : function()
                                    {
                                        mask.close();
                                        errorComunicacion(null,'Error al recuperar datos de tr\u00e1mite para revisar renovaci\u00f3n');
                                    }
                                });
                            }
                        }
                    }
                    catch(e)
                    {
                        manejaException(e,ck,mask);
                    }
                }
                ,failure : function()
                {
                    mask.close();
                    errorComunicacion(null,'Error al recuperar cotizaci\u00f3n de tr\u00e1mite para revisar renovaci\u00f3n');
                }
            });
        }
        catch(e)
        {
            manejaException(e,ck,mask);
        }
    }
}

function mensajeCorrectoTarifas(titulo,mensaje,funcion)
{//VILS
    
    if(!Ext.isEmpty(funcion))
    {
        var tmpMensajeEmergente=Ext.Msg.show({
            title    : titulo
            ,icon: 'x-message-box-ok' 
            ,msg     : mensaje
            ,buttons : Ext.Msg.OK
            ,fn      : funcion 
        });
    }
    else
    {
        var tmpMensajeEmergente=Ext.Msg.show({
            title    : titulo
            ,icon: 'x-message-box-ok' 
            ,msg     : mensaje
            ,buttons : Ext.Msg.OK 
        });
    }
    centrarVentanaTarifas(tmpMensajeEmergente);
}


function centrarVentanaTarifas(ventana)
{
    try {
        ventana.setPosition(225,1200);
    } catch(e) {
        debug(e);
    }
    return ventana;
}
////// funciones //////
</script>
</head>
<body><div id="_p28_divpri" style="height: 1700px;"></div></body>
</html>
