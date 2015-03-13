<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
--%>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var inputCduniecop4        = '<s:property value="smap1.CDUNIECO" />';
var inputCdramop4          = '<s:property value="smap1.CDRAMO" />';
var inputEstadop4          = '<s:property value="smap1.ESTADO" />';
var inputNmpolizap4        = '<s:property value="smap1.NMPOLIZA" />';
var inputCdpersonp4        = '<s:property value="smap1.CDPERSON" />';
var inputCdrfcp4           = '<s:property value="smap1.CDRFC" escapeHtml="false" />';
var inputCdtipsit          = '<s:property value="smap1.CDTIPSIT" />';
var inputNtramite          = '<s:property value="smap1.NTRAMITE" />';
var tipoFlotilla           = '<s:property value="smap1.TIPOFLOT" />';
var inputFechaInicio       = new Date();
var urlRegresarp4          = '<s:url namespace="/"        action="editarAsegurados" />';
var urlCargarp4            = '<s:url namespace="/"        action="cargarPantallaDomicilio" />';
var urlGuardarp4           = '<s:url namespace="/endosos" action="guardarEndosoDomicilioAuto" />';
var enddomUrlDoc           = '<s:url namespace="/documentos" action="ventanaDocumentosPolizaClon" />';
var _ComboColoniasUrl      = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';

var esElContratanteP4   = true;
var formPanelp4;

debug('inputCduniecop4'        , inputCduniecop4);
debug('inputCdramop4'          , inputCdramop4);
debug('inputEstadop4'          , inputEstadop4);
debug('inputNmpolizap4'        , inputNmpolizap4);
debug('inputCdpersonp4'        , inputCdpersonp4);
debug('inputCdrfcp4'           , inputCdrfcp4);
debug('inputCdtipsit'          , inputCdtipsit);
debug('inputNtramite'          , inputNtramite);
/*///////////////////*/
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
/*///////////////////*/

/*///////////////////*/
////// funciones //////
///////////////////////

Ext.onReady(function(){
    
    /////////////////////
    ////// modelos //////
    /*/////////////////*/
    Ext.define('Modelo1p4',{
        extend     : 'Ext.data.Model',
        <s:property value="item1" />
    });
    /*/////////////////*/
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/

    /*////////////////*/
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    /*/////////////////////*/
    
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    formPanelp4=Ext.create('Ext.form.Panel',
    {
        renderTo    : 'maindivp4',
        border      : 0,
        buttonAlign : 'center',
        url         : urlGuardarp4,
        items       :
        [
            Ext.create('Ext.panel.Panel',
            {
                title         : 'Domicilio',
                collapsible   : true,
                titleCollapse : true,
                style         : 'margin:5px;',
                defaults      :
                {
                    style : 'margin : 5px;'
                },
                layout        :
                {
                    type    : 'table',
                    columns : 3
                },
                items:
                [
                    {
                        fieldLabel : 'Contratante',
                        xtype      : 'textfield',
                        readOnly   : true,
                        name       : 'smap1.asegurado'
                    },
                    {
                        fieldLabel : 'RFC',
                        xtype      : 'textfield',
                        readOnly   : true,
                        name       : 'smap1.rfc'
                    }
                ]
            }),
            Ext.create('Ext.panel.Panel',
            {
                title           : 'Direcci&oacute;n',
                itemId          : 'panelDireccionp4',
                collapsible     : true,
                titleCollapse   : true,
                style           : 'margin:5px;',
                defaults        :
                {
                    style : 'margin:5px;'
                },
                layout          :
                {
                    type    : 'table',
                    columns : 2
                },
                items           :
                [
                    /*
                    {
                        fieldLabel     : 'Consecutivo',
                        xtype          : 'numberfield',
                        name           : 'smap1.NMORDDOM',
                        readOnly       : true
                    },
                    {
                        fieldLabel     : 'C&oacute;digo postal',
                        xtype          : 'textfield',
                        name           : 'smap1.CODPOSTAL',
                        readOnly       : true
                    },
                    {
                        xtype          : 'textfield',
                        name           : 'smap1.CDEDO',
                        hidden         : true
                    },
                    {
                        fieldLabel     : 'Estado / Ciudad',
                        xtype          : 'textfield',
                        name           : 'smap1.estado',
                        readOnly       : true
                    },
                    {
                        xtype          : 'textfield',
                        name           : 'smap1.CDMUNICI',
                        hidden         : true
                    },
                    {
                        fieldLabel     : 'Delegaci&oacute;n / Municipio',
                        xtype          : 'textfield',
                        name           : 'smap1.Municipio',
                        readOnly       : true
                    },
                    {
                        fieldLabel     : 'Tel&eacute;fono',
                        xtype          : 'textfield',
                        name           : 'smap1.NMTELEFO',
                        allowBlank     : true,
                        readOnly       : !esElContratanteP4
                    },{
                        xtype:'combo',
                        id:'coloniaId',//id
                        name:'smap1.CDCOLONI',
                        fieldLabel:'Colonia',
                        displayField: 'value',
                        valueField: 'key',
                        readOnly: !esElContratanteP4,
                        store:Ext.create('Ext.data.Store', {
                            model:'Generic',
                            autoLoad:false,
                            proxy:
                            {
                                type: 'ajax',
                                url: _ComboColoniasUrl,
                                reader:
                                {
                                    type: 'json',
                                    root: 'lista'
                                }
                            }
                        }),
                        editable:true,
                        queryMode:'local',
                        style:'margin:5px;',
                        allowBlank:false
                    },
                    {
                        fieldLabel     : 'Calle',
                        xtype          : 'textfield',
                        name           : 'smap1.DSDOMICI',
                        allowBlank     : false,
                        readOnly       : !esElContratanteP4
                    },
                    {
                        fieldLabel     : 'Exterior',
                        xtype          : 'textfield',
                        name           : 'smap1.NMNUMERO',
                        maxLength      : 10,
                        allowBlank     : false,
                        readOnly       : !esElContratanteP4
                    },
                    {
                        fieldLabel     : 'Interior',
                        xtype          : 'textfield',
                        name           : 'smap1.NMNUMINT',
                        maxLength      : 10,
                        allowBlank     : true,
                        readOnly       : !esElContratanteP4
                    }
                    */
                    <s:property value="item3" />
                ]
            })
            ,Ext.create('Ext.panel.Panel',
            {
            	title     : 'Informaci&oacute;n del endoso'
            	,style    : 'margin : 5px;'
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
					{
					    xtype       : 'datefield'
					    ,fieldLabel : 'Fecha de efecto'
					    ,format     : 'd/m/Y'
					    ,value      : inputFechaInicio
					    ,allowBlank : false
					    ,name       : 'smap2.pv_fecha_i'
					    ,readOnly   : false
					}
            	]
            })
        ],
        buttons:
        [
            {
                text:'Generar endoso',
                icon: '${ctx}/resources/fam3icons/icons/key.png',
                handler:function()
                {
                    if(this.up().up().getForm().isValid())
                    {
                        this.up().up().setLoading(true);
                        this.up().up().getForm().submit(
                        {
                            params:
                            {
                                'smap1.pv_cdunieco' : inputCduniecop4,
                                'smap1.pv_cdramo'   : inputCdramop4,
                                'smap1.pv_estado'   : inputEstadop4,
                                'smap1.pv_nmpoliza' : inputNmpolizap4,
                                'smap1.pv_nmsituac' : '1',
                                'smap1.pv_cdperson' : inputCdpersonp4,
                                'smap1.pv_cdrol'    : '1',
                                'smap1.TIPOFLOT'    : tipoFlotilla,
                                'smap2.cdtipsit'    : inputCdtipsit
                            },
                            success:function(response,opts)
                            {
                                formPanelp4.setLoading(false);
                                var json=Ext.decode(opts.response.responseText);
                            	//////////////////////////////////
                                ////// usa codigo del padre //////
                                /*//////////////////////////////*/
//                                marendNavegacion(2);
                                /*//////////////////////////////*/
                                ////// usa codigo del padre //////
                                //////////////////////////////////
                                Ext.Msg.show({
                                    title:'Endoso generado',
                                    msg: json.mensaje,
                                    buttons: Ext.Msg.OK
                                });
                            },
                            failure:function(response,opts)
                            {
                                formPanelp4.setLoading(false);
                            	var json=Ext.decode(opts.response.responseText);
                                mensajeError(json.error);
                            }
                        });
                    }
                    else
                    {
                        Ext.Msg.show({
                            title:'Datos incompletos',
                            msg: 'Favor de llenar los campos requeridos',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.WARNING
                        });
                    }
                }
            }
            /*,{
                text     : 'Documentos'
                ,icon    : '${ctx}/resources/fam3icons/icons/printer.png'
                ,handler : function()
                {
                    Ext.create('Ext.window.Window',
                    {
                        title        : 'Documentos del tr&aacute;mite '+inputNtramite
                        ,modal       : true
                        ,buttonAlign : 'center'
                        ,width       : 600
                        ,height      : 400
                        ,autoScroll  : true
                        ,loader      :
                        {
                            url       : enddomUrlDoc
                            ,params   :
                            {
                                'smap1.nmpoliza'  : inputNmpolizap4
                                ,'smap1.cdunieco' : inputCduniecop4
                                ,'smap1.cdramo'   : inputCdramop4
                                ,'smap1.estado'   : inputEstadop4
                                ,'smap1.nmsuplem' : '0'
                                ,'smap1.ntramite' : inputNtramite
                                ,'smap1.nmsolici' : ''
                                ,'smap1.tipomov'  : '0'
                            }
                            ,scripts  : true
                            ,autoLoad : true
                        }
                    }).show();
                }
            }*/
        ]
    });
    
    _codPosEnd().setReadOnly(true);
    
    _comboEstadoEnd().setReadOnly(true);
    _comboMunicipioEnd().setReadOnly(true);
    _comboColoniasEnd().setReadOnly(true);
    
    
//    if(!_p4_habilitaEdicion)//si es asegurado solo puede leer cp, estado y municipio
//    {
//        _comboEstadoEnd().setReadOnly(true);//estado
//        _comboMunicipioEnd().setReadOnly(true);//municipio
//    }
    
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
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////
    
    //////////////////////
    ////// cargador //////
    /*//////////////////*/
    Ext.define('LoaderFormp4',
    {
        extend:'Modelo1p4',
        proxy:
        {
            extraParams:
            {
                'smap1.pv_cdunieco_i'   : inputCduniecop4,
                'smap1.pv_cdramo_i'     : inputCdramop4,
                'smap1.pv_estado_i'     : inputEstadop4,
                'smap1.pv_nmpoliza_i'   : inputNmpolizap4,
                'smap1.pv_nmsituac_i'   : '1',
                'smap1.pv_cdperson_i'   : inputCdpersonp4,
                'smap1.pv_cdrol_i'      : '1',
                'smap1.nombreAsegurado' : '',
                'smap1.cdrfc'           : inputCdrfcp4,
                'smap1.pv_cdtipsit_i'   : inputCdtipsit,
                'smap1.domGeneral'      : 'S'
            },
            type:'ajax',
            url : urlCargarp4,
            reader:
            {
                type:'json'
            }
        }
    });

    var loaderFormp4=Ext.ModelManager.getModel('LoaderFormp4');
    loaderFormp4.load(123, {
        success: function(resp) {
            debug(resp);
            resp.data['smap1.asegurado'] ='';
            resp.data['smap1.rfc']       =inputCdrfcp4;
            debug(resp);
            formPanelp4.loadRecord(resp);
            
            _comboColoniasEnd().getStore().load(
            {
                params :
                {
                    'params.cp' : resp.data['smap1.CODPOSTAL']
                }
            });
        },
        failure:function()
        {
            Ext.Msg.show({
                title:'Error',
                icon: Ext.Msg.ERROR,
                msg: 'Error al cargar',
                buttons: Ext.Msg.OK
            });
        }
    });
    
    
    _fieldByName('smap1.NMNUMERO').regex = /^[A-Za-z0-9-]*$/;
    _fieldByName('smap1.NMNUMERO').regexText = 'Solo d&iacute;gitos, letras y guiones';
    _fieldByName('smap1.NMNUMINT').regex = /^[A-Za-z0-9-]*$/;
    _fieldByName('smap1.NMNUMINT').regexText = 'Solo d&iacute;gitos, letras y guiones';
    
    Ext.ComponentQuery.query('[name=smap1.NMTELEFO]')[Ext.ComponentQuery.query('[name=smap1.NMTELEFO]').length-1].hide();
    
    /*//////////////////*/
    ////// cargador //////
    //////////////////////
    
    
    function _codPosEnd(){
	    return Ext.ComponentQuery.query('[name=smap1.CODPOSTAL]')[Ext.ComponentQuery.query('[name=smap1.CODPOSTAL]').length-1];
	}
	function _comboEstadoEnd(){
	    return Ext.ComponentQuery.query('[name=smap1.CDEDO]')[Ext.ComponentQuery.query('[name=smap1.CDEDO]').length-1];
	}
	function _comboMunicipioEnd(){
	    return Ext.ComponentQuery.query('[name=smap1.CDMUNICI]')[Ext.ComponentQuery.query('[name=smap1.CDMUNICI]').length-1];
	}
	function _comboColoniasEnd(){
	    return Ext.ComponentQuery.query('[name=smap1.CDCOLONI]')[Ext.ComponentQuery.query('[name=smap1.CDCOLONI]').length-1];
	}

});
</script>
<%--
    </head>
    <body>
--%>
        <div id="maindivp4" style="height:600px;"></div>
<%--
    </body>
</html>
--%>