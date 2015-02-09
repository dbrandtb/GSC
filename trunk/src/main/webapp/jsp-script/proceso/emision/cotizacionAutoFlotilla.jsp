<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// overrides //////
////// overrides //////

////// urls //////
var _p30_urlCargarSumaAseguradaRamo5       = '<s:url namespace="/emision"         action="cargarSumaAseguradaRamo5"       />';
var _p30_urlCargarCduniecoAgenteAuto       = '<s:url namespace="/emision"         action="cargarCduniecoAgenteAuto"       />';
var _p30_urlCargarRetroactividadSuplemento = '<s:url namespace="/emision"         action="cargarRetroactividadSuplemento" />';
var _p30_urlCargarParametros               = '<s:url namespace="/emision"         action="obtenerParametrosCotizacion"    />';
var _p30_urlRecuperarCliente               = '<s:url namespace="/"                action="buscarPersonasRepetidas"        />';
var _p30_urlCargarCatalogo                 = '<s:url namespace="/catalogos"       action="obtieneCatalogo"                />';
var _p30_urlCotizar                        = '<s:url namespace="/emision"         action="cotizarAutosFlotilla"           />';
var _p30_urlCargaMasiva                    = '<s:url namespace="/emision"         action="procesarCargaMasivaFlotilla"    />';
var _p30_urlCargar                         = '<s:url namespace="/emision"         action="cargarCotizacionAutoFlotilla"   />';
var _p30_urlRecuperacionSimple             = '<s:url namespace="/emision"         action="recuperacionSimple"             />';
var _p30_urlRecuperacionSimpleLista        = '<s:url namespace="/emision"         action="recuperacionSimpleLista"        />';
var _p30_urlComprar                        = '<s:url namespace="/flujocotizacion" action="comprarCotizacion4"             />';
var _p30_urlDatosComplementarios           = '<s:url namespace="/emision"         action="emisionAutoFlotilla"            />';
var _p30_urlCargarDetalleNegocioRamo5      = '<s:url namespace="/emision"         action="cargarDetalleNegocioRamo5"      />';
var _p30_urlCargarTipoCambioWS             = '<s:url namespace="/emision"         action="cargarTipoCambioWS"             />';
var _p30_urlNada                           = '<s:url namespace="/emision"         action="webServiceNada"                 />';
////// urls //////

////// variables //////
var _p30_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p30_smap1:',_p30_smap1);
var _p28_smap1 =
{
    cdtipsit : _p30_smap1.cdtipsit
};
debug('_p28_smap1:',_p28_smap1);

var _p30_store                   = null;
var _p30_selectedRecord          = null;
var _p30_recordClienteRecuperado = null;
var _p30_selectedTarifa          = null;
var _p30_ventanaCdtipsit         = null;
var _p30_semaforoBorrar          = false;
var _p30_precioDolarDia          = null;

var _p30_storeCdtipsit       = null;
var _p30_storeMarcasRamo5    = null;
var _p30_storeSubmarcasRamo5 = null;
var _p30_storeVersionesRamo5 = null;
var _p30_storeUsosRamo5      = null;
var _p30_storePlanesRamo5    = null;
var _p30_storeCargasRamo5    = null;
////// variables //////

////// dinamicos //////
var _p30_gridColsConf =
[
    <s:if test='%{getImap().get("gridCols")!=null}'>
        <s:property value="imap.gridCols" />
    </s:if>
];
var _p30_gridCols =
[
    {
        dataIndex     : 'nmsituac'
        ,width        : 30
        ,menuDisabled : true
    }
    ,{
        sortable      : false
        ,menuDisabled : true
        ,dataIndex    : 'personalizado'
        ,width        : 30
        ,renderer     : function(v)
        {
            var r='';
            if(v+'x'=='six')
            {
                r='<img src="${ctx}/resources/fam3icons/icons/tag_blue_edit.png" />';
            }
            return r;
        }
    }
];
for(var i=0;i<_p30_gridColsConf.length;i++)
{
    _p30_gridCols.push(_p30_gridColsConf[i]);
}
for(var i=0;i<_p30_gridCols.length;i++)
{
    if(!Ext.isEmpty(_p30_gridCols[i].editor)&&_p30_gridCols[i].editor.readOnly)
    {
        _p30_gridCols[i].editor='';
    }
}
_p30_gridCols.push(
{
    xtype         : 'actioncolumn'
    ,menuDisabled : true
    ,sortable     : false
    ,width        : 50
    ,items        :
    [
        {
            tooltip  : 'Configurar paquete'
            ,icon    : '${ctx}/resources/fam3icons/icons/cog.png'
            ,iconBkp : '${ctx}/resources/fam3icons/icons/cog.png'
            ,handler : _p30_gridBotonConfigClic
        }
        ,{
            tooltip  : 'Eliminar'
            ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
            ,iconBkp : '${ctx}/resources/fam3icons/icons/delete.png'
            ,handler : _p30_gridBotonEliminarClic
        }
    ]
}
);

var _p30_panel1ItemsConf =
[
    <s:if test='%{getImap().get("panel1Items")!=null}'>
        <s:property value="imap.panel1Items" />
    </s:if>
];

var _p30_panel3ItemsConf =
[
    <s:if test='%{getImap().get("panel3Items")!=null}'>
        <s:property value="imap.panel3Items" />
    </s:if>
];

var _p30_panel5ItemsConf =
[
    <s:if test='%{getImap().get("panel5Items")!=null}'>
        <s:property value="imap.panel5Items" />
    </s:if>
];

var _p30_panel6ItemsConf =
[
    <s:if test='%{getImap().get("panel6Items")!=null}'>
        <s:property value="imap.panel6Items" />
    </s:if>
];

var _p30_paneles  = [];
<s:iterator value="imap">
    <s:if test='%{key.substring(0,"paneldin_".length()).equals("paneldin_")}'>
        _p30_paneles['<s:property value='%{key.substring("paneldin_".length())}' />']=Ext.create('Ext.window.Window',
        {
            title        : ''
            ,modal       : true
            ,closeAction : 'hide'
            ,maxHeight   : 600
            ,width       : 850
            ,autoScroll  : true
            ,callback    : false
            ,valores     : false
            ,items       :
            [
                Ext.create('Ext.form.Panel',
                {
                    itemId    : '_p30_form_panel_<s:property value='%{key.substring("paneldin_".length())}' />'
                    ,defaults : { style : 'margin:5px;' }
                    ,border   : 0
                    ,layout   :
                    {
                        type     : 'table'
                        ,columns : 3
                    }
                    ,items       : [ <s:property value="value" /> ]
                    ,buttonAlign : 'center'
                    ,buttons     :
                    [
                        {
                            text     : 'Aceptar'
                            ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                            ,handler : function(me){ me.up('window').callback(); }
                        }
                        ,{
                            text     : 'Cancelar'
                            ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                            ,handler : function(me){ me.up('window').hide();}
                        }
                    ]
                })
            ]
        });
    </s:if>
    <s:set var="contador" value="#contador+1" />
</s:iterator>

var _p30_gridTbarItems =
[
    {
        text     : 'Agregar'
        ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
        ,handler : function(){_p30_agregarAuto();}
    }
    ,'->'
];

var _f1_botones =[];
for(var i in _p30_smap1)
{
    if(i.slice(0,6)=='boton_')
    {
        _f1_botones.push(
        {
            text      : i.split('_')[1]
            ,icon     : '${ctx}/resources/fam3icons/icons/cog.png'
            ,cdtipsit : _p30_smap1[i]
            ,handler  : function(me){_p30_configuracionPanelDinClic(me.cdtipsit,me.text);}
        });
    }
}
if(_f1_botones.length>1)
{
    for(var i=0;i<_f1_botones.length-1;i++)
    {
        for(var j=i+1;j<_f1_botones.length;j++)
        {
            if(_f1_botones[j].text<_f1_botones[i].text)
            {
                var _f1_aux=_f1_botones[j];
                _f1_botones[j]=_f1_botones[i];
                _f1_botones[i]=_f1_aux;
            }
        }
    }
    for(var i=0;i<_f1_botones.length;i++)
    {
        _p30_gridTbarItems.push(_f1_botones[i]);
    }
}
else if(_f1_botones.length==1)
{
    _p30_gridTbarItems.push(_f1_botones[0]);
}

var _p30_editorCdtipsit=<s:property value="imap.cdtipsitItem" />;

var _p30_situaciones    = _p30_smap1.situacionesCSV.split(',');
debug('_p30_situaciones:',_p30_situaciones);

var _p30_tatrisitFullForms    = [];
var _p30_tatrisitParcialForms = [];
var _p30_tatrisitAutoWindows  = [];
<s:iterator value="imap">
    <s:if test='%{key.substring(0,"tatrisit_full_items_".length()).equals("tatrisit_full_items_")}'>
        _p30_tatrisitFullForms['<s:property value='%{key.substring("tatrisit_full_items_".length())}' />']=
        Ext.create('Ext.form.Panel',
        {
            items : [ <s:property value="value" /> ]
        });
    </s:if>
    <s:if test='%{key.substring(0,"tatrisit_parcial_items_".length()).equals("tatrisit_parcial_items_")}'>
        _p30_tatrisitParcialForms['<s:property value='%{key.substring("tatrisit_parcial_items_".length())}' />']=
        {
            xtype        : 'form'
            ,itemId      : '_p30_tatrisitParcialForm<s:property value='%{key.substring("tatrisit_parcial_items_".length())}' />'
            ,layout      : 'hbox'
            ,autoScroll  : true
            ,border      : 0
            ,hidden      : true
            ,items       : [ <s:property value="value" /> ]
            ,buttonAlign : 'center'
            ,buttons     :
            [
                {
                    text     : 'Aceptar'
                    ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                    ,handler : function(bot) { _p30_editarAutoAceptar(bot); }
                }
                ,{
                    text     : 'B&uacute;squeda de veh&iacute;culo'
                    ,icon    : '${ctx}/resources/fam3icons/icons/car.png'
                    ,handler : function(bot) { _p30_editarAutoAceptar(bot,_p30_editarAutoBuscar); }
                }
            ]
        };
    </s:if>
    <s:if test='%{key.substring(0,"tatrisit_auto_items_".length()).equals("tatrisit_auto_items_")}'>
        _p30_tatrisitAutoWindows['<s:property value='%{key.substring("tatrisit_auto_items_".length())}' />']=
        Ext.create('Ext.window.Window',
        {
            modal        : true
            ,itemId      : '_p30_tatrisitAutoWindow<s:property value='%{key.substring("tatrisit_auto_items_".length())}' />'
            ,closeAction : 'hide'
            ,noMostrar   : false
            ,title       : 'B&Uacute;SQUEDA VEH&Iacute;CULO'
            ,items :
            [
                Ext.create('Ext.form.Panel',
                {
                    itemId      : '_p30_tatrisitAutoForm<s:property value='%{key.substring("tatrisit_auto_items_".length())}' />'
                    ,defaults   : { style:'margin:5px;' }
                    ,items      : [ <s:property value="value" /> ]
                    ,border     : 0
                    ,micallback : function(form)
                    {
                        mensajeWarning('Sin funci&oacute;n de retorno');
                    }
                    ,buttonAlign : 'center'
                    ,buttons     :
                    [
                        {
                            text     : 'Aceptar'
                            ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                            ,handler : _p30_tatrisitWindowAutoAceptarClic
                        }
                    ]
                })
            ]
        });
    </s:if>
</s:iterator>
debug('_p30_tatrisitFullForms:'    , _p30_tatrisitFullForms);
debug('_p30_tatrisitParcialForms:' , _p30_tatrisitParcialForms);
debug('_p30_tatrisitAutoWindows:'  , _p30_tatrisitAutoWindows);
////// dinamicos //////

Ext.onReady(function()
{

    Ext.Ajax.timeout = 60000;

    ////// modelos //////
    Ext.define('_p30_modelo',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
             'parametros.pv_otvalor01','parametros.pv_otvalor02','parametros.pv_otvalor03','parametros.pv_otvalor04','parametros.pv_otvalor05'
            ,'parametros.pv_otvalor06','parametros.pv_otvalor07','parametros.pv_otvalor08','parametros.pv_otvalor09','parametros.pv_otvalor10'
            ,'parametros.pv_otvalor11','parametros.pv_otvalor12','parametros.pv_otvalor13','parametros.pv_otvalor14','parametros.pv_otvalor15'
            ,'parametros.pv_otvalor16','parametros.pv_otvalor17','parametros.pv_otvalor18','parametros.pv_otvalor19','parametros.pv_otvalor20'
            ,'parametros.pv_otvalor21','parametros.pv_otvalor22','parametros.pv_otvalor23','parametros.pv_otvalor24','parametros.pv_otvalor25'
            ,'parametros.pv_otvalor26','parametros.pv_otvalor27','parametros.pv_otvalor28','parametros.pv_otvalor29','parametros.pv_otvalor30'
            ,'parametros.pv_otvalor31','parametros.pv_otvalor32','parametros.pv_otvalor33','parametros.pv_otvalor34','parametros.pv_otvalor35'
            ,'parametros.pv_otvalor36','parametros.pv_otvalor37','parametros.pv_otvalor38','parametros.pv_otvalor39','parametros.pv_otvalor40'
            ,'parametros.pv_otvalor41','parametros.pv_otvalor42','parametros.pv_otvalor43','parametros.pv_otvalor44','parametros.pv_otvalor45'
            ,'parametros.pv_otvalor46','parametros.pv_otvalor47','parametros.pv_otvalor48','parametros.pv_otvalor49','parametros.pv_otvalor50'
            ,'parametros.pv_otvalor51','parametros.pv_otvalor52','parametros.pv_otvalor53','parametros.pv_otvalor54','parametros.pv_otvalor55'
            ,'parametros.pv_otvalor56','parametros.pv_otvalor57','parametros.pv_otvalor58','parametros.pv_otvalor59','parametros.pv_otvalor60'
            ,'parametros.pv_otvalor61','parametros.pv_otvalor62','parametros.pv_otvalor63','parametros.pv_otvalor64','parametros.pv_otvalor65'
            ,'parametros.pv_otvalor66','parametros.pv_otvalor67','parametros.pv_otvalor68','parametros.pv_otvalor69','parametros.pv_otvalor70'
            ,'parametros.pv_otvalor71','parametros.pv_otvalor72','parametros.pv_otvalor73','parametros.pv_otvalor74','parametros.pv_otvalor75'
            ,'parametros.pv_otvalor76','parametros.pv_otvalor77','parametros.pv_otvalor78','parametros.pv_otvalor79','parametros.pv_otvalor80'
            ,'parametros.pv_otvalor81','parametros.pv_otvalor82','parametros.pv_otvalor83','parametros.pv_otvalor84','parametros.pv_otvalor85'
            ,'parametros.pv_otvalor86','parametros.pv_otvalor87','parametros.pv_otvalor88','parametros.pv_otvalor89','parametros.pv_otvalor90'
            ,'parametros.pv_otvalor91','parametros.pv_otvalor92','parametros.pv_otvalor93','parametros.pv_otvalor94','parametros.pv_otvalor95'
            ,'parametros.pv_otvalor96','parametros.pv_otvalor97','parametros.pv_otvalor98','parametros.pv_otvalor99'
            ,'cdplan','cdtipsit','personalizado',{name:'nmsituac',type:'int'}
        ]
    });
    
    Ext.define('_p30_modeloRecuperado',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'NOMBRECLI'
            ,'DIRECCIONCLI'
        ]
    });
    
    Ext.define('_p30_modeloTarifa',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'CDPERPAG'
            ,'DSPERPAG'
            ,'PRIMA'
        ]
    });
    
    Ext.define('_p30_modeloDetalleCotizacion',
    {
        extend : 'Ext.data.Model'
        ,fields :
        [
            'COBERTURA'
            ,{
                name : 'PRIMA'
                ,type : 'float'
            }
            ,'TITULO'
        ]
    });
    
    Ext.define('_p30_modeloCoberturaCotizacion',
    {
        extend : 'Ext.data.Model'
        ,fields :
        [
            'COBERTURA'
            ,'SUMASEG'
            ,'TITULO'
        ]
    });
    ////// modelos //////
    
    ////// stores //////
    _p30_store = Ext.create('Ext.data.Store',
    {
        model : '_p30_modelo'
    });
    
    _p30_storeSubmarcasRamo5 = Ext.create('Ext.data.Store',
    {
        model     : 'Generic'
        ,storeId  : '_p30_storeSubmarcasRamo5'
        ,cargado  : false
        ,autoLoad : _p30_smap1.cdramo+'x'=='5x'
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p30_urlCargarCatalogo
            ,extraParams :
            {
                'catalogo' : 'RAMO_5_SUBMARCAS'
            }
            ,reader :
            {
                type  : 'json'
                ,root : 'lista'
            }
        }
        ,listeners :
        {
            load : function()
            {
                this.cargado=true;
                _fieldById('_p30_grid').getView().refresh();
                _fieldById('_p30_panelStoreSubmarcas').destroy();
            }
        }
    });
    
    _p30_storeVersionesRamo5 = Ext.create('Ext.data.Store',
    {
        model     : 'Generic'
        ,storeId  : '_p30_storeVersionesRamo5'
        ,cargado  : false
        ,autoLoad : _p30_smap1.cdramo+'x'=='5x'
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p30_urlCargarCatalogo
            ,extraParams :
            {
                'catalogo' : 'RAMO_5_VERSIONES'
            }
            ,reader :
            {
                type  : 'json'
                ,root : 'lista'
            }
        }
        ,listeners :
        {
            load : function()
            {
                this.cargado=true;
                _fieldById('_p30_grid').getView().refresh();
                _fieldById('_p30_panelStoreVersiones').destroy();
            }
        }
    });
    
    _p30_storeUsosRamo5 = Ext.create('Ext.data.Store',
    {
        model     : 'Generic'
        ,storeId  : '_p30_storeUsosRamo5'
        ,cargado  : false
        ,autoLoad : _p30_smap1.cdramo+'x'=='5x'
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p30_urlCargarCatalogo
            ,extraParams :
            {
                'catalogo' : 'RAMO_5_TIPOS_USO'
            }
            ,reader :
            {
                type  : 'json'
                ,root : 'lista'
            }
        }
        ,listeners :
        {
            load : function()
            {
                this.cargado=true;
                _fieldById('_p30_grid').getView().refresh();
                _fieldById('_p30_panelStoreUsos').destroy();
            }
        }
    });
    
    _p30_storeMarcasRamo5 = Ext.create('Ext.data.Store',
    {
        model     : 'Generic'
        ,storeId  : '_p30_storeMarcasRamo5'
        ,cargado  : false
        ,autoLoad : _p30_smap1.cdramo+'x'=='5x'
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p30_urlCargarCatalogo
            ,extraParams :
            {
                'catalogo' : 'RAMO_5_MARCAS'
            }
            ,reader :
            {
                type  : 'json'
                ,root : 'lista'
            }
        }
        ,listeners :
        {
            load : function()
            {
                this.cargado=true;
                _fieldById('_p30_grid').getView().refresh();
                _fieldById('_p30_panelStoreMarcas').destroy();
            }
        }
    });
    
    _p30_storePlanesRamo5 = Ext.create('Ext.data.Store',
    {
        model     : 'Generic'
        ,storeId  : '_p30_storePlanesRamo5'
        ,autoLoad : _p30_smap1.cdramo+'x'=='5x'
        ,cargado  : false
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p30_urlCargarCatalogo
            ,extraParams :
            {
                'catalogo'         : 'PLANES_X_PRODUCTO'
                ,'params.cdramo'   : '5'
                ,'params.cdtipsit' : ''
            }
            ,reader :
            {
                type  : 'json'
                ,root : 'lista'
            }
        }
        ,listeners :
        {
            load : function()
            {
                this.cargado=true;
                _fieldById('_p30_grid').getView().refresh();
                _fieldById('_p30_panelStorePlanes').destroy();
            }
        }
    });
    
    _p30_storeCargasRamo5 = Ext.create('Ext.data.Store',
    {
        model     : 'Generic'
        ,storeId  : '_p30_storeCargasRamo5'
        ,autoLoad : _p30_smap1.cdramo+'x'=='5x'
        ,cargado  : false
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p30_urlCargarCatalogo
            ,extraParams :
            {
                'catalogo'         : 'RAMO_5_TIPOS_CARGA'
                ,'params.cdramo'   : '5'
                ,'params.cdtipsit' : 'AR'
            }
            ,reader :
            {
                type  : 'json'
                ,root : 'lista'
            }
        }
        ,listeners :
        {
            load : function()
            {
                this.cargado=true;
                _fieldById('_p30_grid').getView().refresh();
                _fieldById('_p30_panelStoreCargas').destroy();
            }
        }
    });
    
    _p30_storeCdtipsit = Ext.create('Ext.data.Store',
    {
        model     : 'Generic'
        ,storeId  : '_p30_storeCdtipsit'
        ,cargado  : false
        ,autoLoad : true
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p30_urlCargarCatalogo
            ,extraParams :
            {
                'catalogo'         : 'TIPSIT'
                ,'params.idPadre'  : _p30_smap1.cdramo
            }
            ,reader :
            {
                type  : 'json'
                ,root : 'lista'
            }
        }
        ,listeners :
        {
            load : function(store,records)
            {
                this.cargado=true;
                _fieldById('_p30_grid').getView().refresh();
                _fieldById('_p30_panelStoreCdtipsit').destroy();
                debug('### cdtipsit:',records);
            }
        }
    });
    ////// stores //////
    
    ////// componentes //////
    var _p30_panel1Items =
    [
        {
            xtype   : 'fieldset'
            ,border : 0
            ,items  :
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
                            ,fieldLabel  : 'FOLIO'
                            ,name        : 'nmpoliza'
                            ,style       : 'margin:5px;'
                            ,listeners   :
                            {
                                change : _p30_nmpolizaChange
                            }
                        }
                        ,{
                            xtype    : 'button'
                            ,itemId  : '_p30_botonCargar'
                            ,text    : 'BUSCAR'
                            ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                            ,handler : function(){_p30_cargarClic();}
                        }
                    ]
                }
            ]
        }
        ,{
            xtype   : 'fieldset'
            ,itemId : '_p30_panel3Fieldset'
            ,title  : '<span style="font:bold 14px Calibri;">CLIENTE</span>'
            ,items  : _p30_panel3ItemsConf
        }
    ];
    for(var i=0;i<_p30_panel1ItemsConf.length;i++)
    {
        _p30_panel1Items[0].items.push(_p30_panel1ItemsConf[i]);
    }
    _p30_panel1Items[0].items.push(
    {
        xtype       : 'datefield'
        ,name       : 'feini'
        ,fieldLabel : 'INICIO DE VIGENCIA'
        ,value      : new Date()
        ,style      : 'margin:5px;'
        ,readOnly   : _p30_smap1.cdramo+'x'=='5x'&&_p30_smap1.cdsisrol!='SUSCRIAUTO'
    }
    ,{
        xtype       : 'datefield'
        ,name       : 'fefin'
        ,fieldLabel : 'FIN DE VIGENCIA'
        ,value      : Ext.Date.add(new Date(),Ext.Date.YEAR,1)
        ,minValue   : Ext.Date.add(new Date(),Ext.Date.DAY,1)
        ,style      : 'margin:5px;'
    });
    
    var _p30_formOcultoItems = [];
    <s:if test='%{getImap().get("panel5Items")!=null}'>
        var items = [<s:property value="imap.panel5Items" />];
        for(var i=0;i<items.length;i++)
        {
            _p30_formOcultoItems.push(items[i]);
        }
    </s:if>
    <s:if test='%{getImap().get("panel6Items")!=null}'>
        var items = [<s:property value="imap.panel6Items" />];
        for(var i=0;i<items.length;i++)
        {
            _p30_formOcultoItems.push(items[i]);
        }
    </s:if>
    debug('_p30_formOcultoItems:',_p30_formOcultoItems);
    ////// componentes //////
    
    ////// contenido //////
    var itemspri=
    [
        Ext.create('Ext.form.Panel',
        {
            itemId      : '_p30_form'
            ,title      : 'DATOS GENERALES'
            ,formOculto : Ext.create('Ext.form.Panel',{ items : _p30_formOcultoItems })
            ,defaults   : { style : 'margin:5px;' }
            ,layout     :
            {
                type     : 'table'
                ,columns : 2
                ,tdAttrs : {valign:'top'}
            }
            ,items    : _p30_panel1Items
        })
        ,Ext.create('Ext.grid.Panel',
        {
            itemId      : '_p30_grid'
            ,title      : 'INCISOS'
            ,tbar       : _p30_gridTbarItems
            ,bbar       :
            [
                '->'
                ,{
                    xtype   : 'form'
                    ,hidden : _p30_smap1.tipoflot+'x'!='Fx'
                    ,layout : 'hbox'
                    ,items  :
                    [
                        {
                            xtype       : 'checkbox'
                            ,boxLabel   : '<span style="color:white;">Tomar configuraci&oacute;n de carga masiva</span>'
                            ,name       : 'smap1.tomarMasiva'
                            ,inputValue : 'S'
                            ,style      : 'background:#223772;'
                        }
                        ,{
                            xtype         : 'filefield'
                            ,buttonOnly   : true
                            ,style        : 'margin:0px;'
                            ,name         : 'excel'
                            ,style        : 'background:#223772;'
                            ,buttonConfig :
                            {
                                text  : 'Carga masiva...'
                                ,icon : '${ctx}/resources/fam3icons/icons/book_next.png'
                            }
                            ,listeners :
                            {
                                change : function(me)
                                {
                                    var indexofPeriod = me.getValue().lastIndexOf("."),
                                    uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                    debug('uploadedExtension:',uploadedExtension);
                                    var valido=Ext.Array.contains(['xls','xlsx'], uploadedExtension);
                                    if(!valido)
                                    {
                                        mensajeWarning('Solo se permiten hojas de c&aacute;lculo');
                                        me.reset();
                                    }
                                    if(valido&&_p30_smap1.cdramo+'x'=='5x')
                                    {
                                        valido = !Ext.isEmpty(_fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getValue());
                                        if(!valido)
                                        {
                                            mensajeWarning('Seleccione el negocio');
                                            me.reset();
                                        }
                                    }
                                    
                                    if(valido)
                                    {
                                        var panelpri = _fieldById('_p30_panelpri');
                                        panelpri.setLoading(true);
                                        me.up('form').submit(
                                        {
                                            url     : _p30_urlCargaMasiva
                                            ,params :
                                            {
                                                'smap1.cdramo'    : _p30_smap1.cdramo
                                                ,'smap1.cdtipsit' : _p30_smap1.cdtipsit
                                            }
                                            ,success : function(form,action)
                                            {
                                                panelpri.setLoading(false);
                                                var json = Ext.decode(action.response.responseText);
                                                debug('### excel:',json);
                                                if(json.exito)
                                                {
                                                    var mrecords = [];
                                                    
                                                    for(var i in json.slist1)
                                                    {
                                                        var record=new _p30_modelo(json.slist1[i]);
                                                        _p30_store.add(record);
                                                        mrecords.push(record);
                                                        debug('record.data:',record.data);
                                                    }
                                                    
                                                    if(_p30_smap1.cdramo+'x'=='5x')
                                                    {
                                                        //recuperar
                                                        var len = json.slist1.length;
                                                        panelpri.setLoading(true);
                                                        var recupera = function(i)
                                                        {
                                                            var record       = mrecords[i];
                                                            var cdtipsit     = record.get('cdtipsit');
                                                            if(cdtipsit+'x'=='ARx'
                                                                ||cdtipsit+'x'=='CRx'
                                                                ||cdtipsit+'x'=='PCx'
                                                                ||cdtipsit+'x'=='PPx'
                                                            )
                                                            {
                                                                var form         = _fieldById('_p30_tatrisitAutoForm'+cdtipsit);
                                                                var claveCmp     = form.down('[fieldLabel*=CLAVE]');
                                                                var claveName    = claveCmp.name;
                                                                var marcaName    = form.down('[fieldLabel=MARCA]').name;
                                                                var submarcaName = form.down('[fieldLabel=SUBMARCA]').name;
                                                                var modeloName   = form.down('[fieldLabel=MODELO]').name;
                                                                var versionName  = form.down('[fieldLabel*=VERSI]').name;
                                                                
                                                                claveCmp.getStore().load(
                                                                {
                                                                    params    :
                                                                    {
                                                                        'params.cadena'    : record.get(claveName)
                                                                        ,'params.cdtipsit' : cdtipsit
                                                                    }
                                                                    ,callback : function(records,op)
                                                                    {
                                                                        var index=claveCmp.getStore().findBy(function(irecord)
                                                                        {
                                                                            var splited=irecord.get('value').split(' - ');
                                                                            if(splited[0]==record.get(claveName)
                                                                                &&splited[3]==record.get(modeloName)
                                                                                )
                                                                            {
                                                                                return true;
                                                                            }
                                                                        });
                                                                        var encontrado = claveCmp.getStore().getAt(index);
                                                                        var splited    = encontrado.get('value').split(' - ');
                                                                        var marca      = _p30_storeMarcasRamo5   .getAt(_p30_storeMarcasRamo5   .find('value',splited[1],0,false,false,true)).get('key');
                                                                        var submarca   = _p30_storeSubmarcasRamo5.getAt(_p30_storeSubmarcasRamo5.find('value',splited[2],0,false,false,true)).get('key');
                                                                        var version    = _p30_storeVersionesRamo5.getAt(_p30_storeVersionesRamo5.find('value',splited[4],0,false,false,true)).get('key');
                                                                        record.set(marcaName    , marca);
                                                                        record.set(submarcaName , submarca);
                                                                        record.set(versionName  , version);
                                                                        i=i+1;
                                                                        if(i<len)
                                                                        {
                                                                            recupera(i);
                                                                        }
                                                                        else
                                                                        {
                                                                            panelpri.setLoading(false);
                                                                            _p30_numerarIncisos();
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                            else
                                                            {
                                                                i=i+1;
                                                                if(i<len)
                                                                {
                                                                    recupera(i);
                                                                }
                                                                else
                                                                {
                                                                    panelpri.setLoading(false);
                                                                    _p30_numerarIncisos();
                                                                }
                                                            }
                                                        };
                                                        recupera(0);
                                                    }
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
                                }
                            }
                        }
                    ]
                }
            ]
            ,columns    : _p30_gridCols
            ,height     : 300
            ,store      : _p30_store
            ,selModel   :
            {
                selType        : 'checkboxmodel'
                ,allowDeselect : true
                ,mode          : 'SINGLE'
                ,listeners     :
                {
                    selectionchange : function(me,selected,eOpts)
                    {
                        if(selected.length==1)
                        {
                            _p30_selectedRecord=selected[0];
                            _p30_editarAuto();
                        }
                        else
                        {
                            for(var i in _p30_situaciones)
                            {
                                _fieldById('_p30_tatrisitParcialForm'+_p30_situaciones[i]).hide();
                            }
                        }
                    }
                }
            }
        })
    ];
        
    for(var i in _p30_tatrisitParcialForms)
    {
        itemspri.push(_p30_tatrisitParcialForms[i]);
    }
        
    itemspri.push(
            Ext.create('Ext.panel.Panel',
            {
                itemId       : '_p30_botonera'
                ,buttonAlign : 'center'
                ,border      : 0
                ,buttons     :
                [
                    {
                        itemId   : '_p30_cotizarButton'
                        ,text    : 'Cotizar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/calculator.png'
                        ,handler : function(){_p30_cotizar();}
                    }
                    ,{
                        itemId   : '_p30_limpiarButton'
                        ,text    : 'Limpiar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                        ,handler : function(){_p30_limpiar();}
                    }
                ]
            })
    );
    
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p30_divpri'
        ,itemId   : '_p30_panelpri'
        ,border   : 0
        ,defaults : { style : 'margin:5px;' }
        ,items    : itemspri
    });
    
    _p30_ventanaCdtipsit = Ext.create('Ext.window.Window',
    {
        title        : 'ELEGIR TIPO DE VEH&Iacute;CULO'
        ,modal       : true
        ,closeAction : 'hide'
        ,items       : _p30_editorCdtipsit
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text     : 'Aceptar'
                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                ,handler : function(bot)
                {
                    if(!Ext.isEmpty(_p30_editorCdtipsit.getValue()))
                    {
                        _p30_selectedRecord.set('cdtipsit',_p30_editorCdtipsit.getValue());
                        bot.up('window').hide();
                        _p30_editarAuto();
                    }
                    else
                    {
                        mensajeWarning('Seleccione un tipo de veh&iacute;culo');
                    }
                }
            }
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
            fefin.setMinValue(Ext.Date.add(val,Ext.Date.DAY,1));
            fefin.isValid();
        }
    });
    //fechas
    
    //ramo 5
    if(_p30_smap1.cdramo+'x'=='5x')
    {
        //fechas
        _fieldByName('feini').on(
        {
            change : function(){_p30_calculaVigencia();}
        });
    
        _fieldByName('fefin').on(
        {
            change : function(){_p30_calculaVigencia();}
        });
        
        _p30_calculaVigencia();
        
        //agente
        if(_p30_smap1.cdsisrol=='EJECUTIVOCUENTA')
        {
            _fieldByLabel('AGENTE',_fieldById('_p30_form')).setValue(_p30_smap1.cdagente);
            _fieldByLabel('AGENTE',_fieldById('_p30_form')).setReadOnly(true);
            _p30_ramo5AgenteSelect(_fieldByLabel('AGENTE',_fieldById('_p30_form')),_p30_smap1.cdagente);
        }
        else if(_p30_smap1.cdsisrol=='PROMOTORAUTO'
            ||_p30_smap1.cdsisrol=='SUSCRIAUTO')
        {
            _fieldByLabel('AGENTE',_fieldById('_p30_form')).on(
            {
                'select' : _p30_ramo5AgenteSelect
            });
        }
        //agente
        
        //negocio
        if(_p30_smap1.cdsisrol=='EJECUTIVOCUENTA')
        {
            _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getStore().load(
            {
                params :
                {
                    'params.cdagente' : _p30_smap1.cdagente
                }
            });
        }
        else
        {
            debug('@CUSTOM negocio:',_fieldByLabel('NEGOCIO',_fieldById('_p30_form')),'.');
            _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).anidado = true;
            _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).heredar = function(remoto,callback)
            {
                _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getStore().load(
                {
                    params :
                    {
                        'params.cdagente' : _fieldByLabel('AGENTE',_fieldById('_p30_form')).getValue()
                    }
                    ,callback : function()
                    {
                        if(!Ext.isEmpty(callback))
                        {
                            callback(_fieldByLabel('NEGOCIO',_fieldById('_p30_form')));
                        }
                    }
                });
            }
            _fieldByLabel('AGENTE',_fieldById('_p30_form')).on(
            {
                select : function(){ _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).heredar(); }
            });
        }
        //negocio
        
        //grid
        debug('@CUSTOM grid:_p30_grid removeall on negocio selecet');
        _fieldById('_p30_grid').heredar = function()
        {
            _fieldById('_p30_grid').getStore().removeAll();
        }
        
        _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).on(
        {
            select : function(){ _fieldById('_p30_grid').heredar(); }
        });
        //grid
        
        //paneles tconvalsit
        debug('@CUSTOM tconvalsit:tconvalsit load on negocio select');
        _fieldById('_p30_grid').heredarPanelesTconvalsit = function()
        {
            for(var cdtipsit in _p30_paneles)
            {
                Ext.Ajax.request(
                {
                    url     : _p30_urlRecuperacionSimpleLista
                    ,params :
                    {
                        'smap1.procedimiento' : 'RECUPERAR_CONFIGURACION_VALOSIT_FLOTILLAS'
                        ,'smap1.cdramo'       : _p30_smap1.cdramo
                        ,'smap1.cdtipsit'     : cdtipsit
                        ,'smap1.negocio'      : _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getValue()
                    }
                    ,success : function(response)
                    {
                        var json=Ext.decode(response.responseText);
                        debug('### config:',json);
                        if(json.exito)
                        {
                            _p30_paneles[json.smap1.cdtipsit].valores    = {};
                            _p30_paneles[json.smap1.cdtipsit].valoresBkp = {};
                            for(var i in json.slist1)
                            {
                                _p30_paneles[json.smap1.cdtipsit].valores['parametros.pv_otvalor'+(('00'+json.slist1[i].CDATRIBU).slice(-2))]    = json.slist1[i].VALOR;
                                _p30_paneles[json.smap1.cdtipsit].valoresBkp['parametros.pv_otvalor'+(('00'+json.slist1[i].CDATRIBU).slice(-2))] = json.slist1[i].VALOR;
                            }
                            debug('valores:',_p30_paneles[json.smap1.cdtipsit].valores);
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
        
        _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).on(
        {
            select : function(){ _fieldById('_p30_grid').heredarPanelesTconvalsit(); }
        });
        //paneles tconvalsit
        
        //cliente nuevo
        _fieldLikeLabel('CLIENTE NUEVO',_fieldById('_p30_form')).on(
        {
            change : _p30_ramo5ClienteChange
        });
        _fieldLikeLabel('CLIENTE NUEVO',_fieldById('_p30_form')).select('S');
        
        //tipo situacion
        debug('@CUSTOM cdtipsit:',_p30_editorCdtipsit,'.');
        _p30_editorCdtipsit.anidado = true;
        _p30_editorCdtipsit.heredar = function()
        {
            _p30_editorCdtipsit.getStore().load(
            {
                params :
                {
                    'params.negocio' : _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getValue()
                }
            });
        };
        _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).on(
        {
            change : function(me,val)
            {
                if(me.findRecord('key',val)!=false)
                {
                    _p30_editorCdtipsit.heredar();
                }
            }
        });
        //tipo situacion
        
        //loaders panels
        Ext.create('Ext.panel.Panel',
        {
            itemId    : '_p30_panelStoreMarcas'
            ,floating : true
            ,html     : 'Cargando marcas...'
            ,width    : 200
            ,height   : 30
            ,frame    : true
        }).showAt(770,50);
        
        Ext.create('Ext.panel.Panel',
        {
            itemId    : '_p30_panelStoreSubmarcas'
            ,floating : true
            ,html     : 'Cargando submarcas...'
            ,width    : 200
            ,height   : 30
            ,frame    : true
        }).showAt(770,90);
        
        Ext.create('Ext.panel.Panel',
        {
            itemId    : '_p30_panelStoreUsos'
            ,floating : true
            ,html     : 'Cargando usos...'
            ,width    : 200
            ,height   : 30
            ,frame    : true
        }).showAt(770,130);
        
        Ext.create('Ext.panel.Panel',
        {
            itemId    : '_p30_panelStoreVersiones'
            ,floating : true
            ,html     : 'Cargando versiones...'
            ,width    : 200
            ,height   : 30
            ,frame    : true
        }).showAt(770,170);
        
        Ext.create('Ext.panel.Panel',
        {
            itemId    : '_p30_panelStorePlanes'
            ,floating : true
            ,html     : 'Cargando planes...'
            ,width    : 200
            ,height   : 30
            ,frame    : true
        }).showAt(770,210);
        
        Ext.create('Ext.panel.Panel',
        {
            itemId    : '_p30_panelStoreCdtipsit'
            ,floating : true
            ,html     : 'Cargando situaciones...'
            ,width    : 200
            ,height   : 30
            ,frame    : true
        }).showAt(770,250);
        
        Ext.create('Ext.panel.Panel',
        {
            itemId    : '_p30_panelStoreCargas'
            ,floating : true
            ,html     : 'Cargando tipos de carga...'
            ,width    : 200
            ,height   : 30
            ,frame    : true
        }).showAt(770,290);
        //loaders panels
        
        //herencia situaciones
        for(var i in _p30_situaciones)
        {
            var cdtipsit = _p30_situaciones[i];
            
            //uso
            if('|AR|CR|PC|PP|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var tipoUsoCmp = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=TIPO USO]');
                debug('@CUSTOM tipouso:',tipoUsoCmp,'.');
                tipoUsoCmp.anidado = true;
                tipoUsoCmp.heredar = function(remoto,callback)
                {
                    var record      = _p30_selectedRecord;
                    var cdtipsit    = record.get('cdtipsit');
                    var me          = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=TIPO USO]');
                    var servicioVal = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=TIPO SERVICIO]').getValue();
                    var negocioVal  = _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getValue();
                    if(!Ext.isEmpty(negocioVal)&&!Ext.isEmpty(servicioVal))
                    {
                        me.getStore().load(
                        {
                            params :
                            {
                                'params.cdtipsit'   : cdtipsit
                                ,'params.cdnegocio' : negocioVal
                                ,'params.servicio'  : servicioVal
                            }
                            ,callback : function()
                            {
                                if(!Ext.isEmpty(callback))
                                {
                                    callback
                                    (
                                        _fieldById('_p30_tatrisitParcialForm'+_p30_selectedRecord.get('cdtipsit'))
                                            .down('[fieldLabel=TIPO USO]')
                                    );
                                }
                            } 
                        });
                    }
                    else
                    {
                        me.getStore().removeAll();
                        if(!Ext.isEmpty(callback))
                        {
                            callback(me);
                        }
                    }
                };
                
                _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=TIPO SERVICIO]').on(
                {
                    select : function()
                    {
                        var record      = _p30_selectedRecord;
                        var cdtipsit    = record.get('cdtipsit');
                        _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=TIPO USO]').heredar(true);
                    }
                });
            }
            //uso
            
            //plan
            if('|AR|CR|PC|PP|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var planCmp=_fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=PAQUETE]');
                debug('@CUSTOM plan:',planCmp,'.');
                planCmp.anidado = true;
                planCmp.heredar = function(remoto,callback)
                {
                    var record     = _p30_selectedRecord;
                    var cdtipsit   = record.get('cdtipsit');
                    var me         = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=PAQUETE]');
                    if(Ext.isEmpty(me))
                    {
                        me = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=PAQUETE (Seleccionar veh&iacute;culo primero)]');
                    }
                    var modeloName = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel=MODELO]').getName();
                    var claveName  = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel*=CLAVE]').getName();
                    debug('heredar:'    , me);
                    debug('modeloName:' , modeloName);
                    debug('claveName:'  , claveName);
                    
                    var modeloVal   = record.get(modeloName);
                    var claveVal    = record.get(claveName);
                    var negocioVal  = _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getValue();
                    var servicioVal = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=TIPO SERVICIO]').getValue();
                    debug('modeloVal:'   , modeloVal);
                    debug('claveVal:'    , claveVal);
                    debug('negocioVal:'  , negocioVal);
                    debug('servicioVal:' , servicioVal);
                    
                    if(Ext.isEmpty(modeloVal)||Ext.isEmpty(claveVal)||Ext.isEmpty(negocioVal)||Ext.isEmpty(servicioVal))
                    {
                        me.allowBlank=true;
                        me.setFieldLabel('PAQUETE (Seleccionar veh&iacute;culo primero)');
                        me.getStore().removeAll();
                        if(!Ext.isEmpty(callback))
                        {
                            callback(me);
                        }
                    }
                    else
                    {
                        me.allowBlank=false;
                        me.setFieldLabel('PAQUETE');
                        me.getStore().load(
                        {
                            params :
                            {
                                'params.cdtipsit'  : cdtipsit
                                ,'params.negocio'  : negocioVal
                                ,'params.modelo'   : modeloVal
                                ,'params.clavegs'  : claveVal
                                ,'params.servicio' : servicioVal
                            }
                            ,callback : function()
                            {
                                if(!Ext.isEmpty(callback))
                                {
                                    var me = _fieldById('_p30_tatrisitParcialForm'+_p30_selectedRecord.get('cdtipsit'))
                                        .down('[fieldLabel=PAQUETE]');
                                    if(Ext.isEmpty(me))
                                    {
                                        me = _fieldById('_p30_tatrisitParcialForm'+_p30_selectedRecord.get('cdtipsit'))
                                            .down('[fieldLabel=PAQUETE (Seleccionar veh&iacute;culo primero)]');
                                    }
                                    callback(me);
                                }
                            } 
                        });
                    }
                }
                
                _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=TIPO SERVICIO]').on(
                {
                    select : function(me)
                    {
                        var record   = _p30_selectedRecord;
                        var cdtipsit = record.get('cdtipsit');
                        
                        var planCmp = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=PAQUETE]');
                        if(Ext.isEmpty(planCmp))
                        {
                            planCmp = _fieldById('_p30_tatrisitParcialForm'+cdtipsit)
                                .down('[fieldLabel=PAQUETE (Seleccionar veh&iacute;culo primero)]');
                        }
                        planCmp.heredar(true);
                    }
                });
            }
            //plan
            
            //marca
            if('|AR|CR|PC|PP|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var marcaCmp=_fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=MARCA]');
                debug('@CUSTOM marca:',marcaCmp,'.');
                marcaCmp.anidado = true;
                marcaCmp.heredar = function(remoto,callback)
                {
                    var record     = _p30_selectedRecord;
                    var cdtipsit   = record.get('cdtipsit');
                    var me         = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=MARCA]');
                    var negocioVal = _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getValue();
                    if(!Ext.isEmpty(negocioVal))
                    {
                        me.getStore().load(
                        {
                            params    :
                            {
                                'params.cdtipsit'   : cdtipsit
                                ,'params.cdnegocio' : negocioVal
                            }
                            ,callback : function()
                            {
                                if(!Ext.isEmpty(callback))
                                {
                                    callback
                                    (
                                        _fieldById('_p30_tatrisitAutoForm'+cdtipsit)
                                            .down('[fieldLabel=MARCA]')
                                    );
                                }
                            }
                        });
                    }
                    else
                    {
                        me.getStore().removeAll();
                        if(!Ext.isEmpty(callback))
                        {
                            callback(me);
                        }
                    }
                };
            }
            //marca
            
            //modelo
            if('|AR|CR|PC|PP|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var modeloCmp=_fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=MODELO]');
                debug('@CUSTOM modelo:',modeloCmp,'.');
                modeloCmp.anidado = true;
                modeloCmp.heredar = function(remoto,callback)
                {
                    var record      = _p30_selectedRecord;
                    var cdtipsit    = record.get('cdtipsit');
                    var me          = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=MODELO]');
                    var submarcaVal = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=SUBMARCA]').getValue();
                    if(!Ext.isEmpty(submarcaVal))
                    {
                        me.getStore().load(
                        {
                            params    :
                            {
                                'params.cdtipsit' : cdtipsit
                                ,'params.idPadre' : submarcaVal
                            }
                            ,callback : function()
                            {
                                if(!Ext.isEmpty(callback))
                                {
                                    callback
                                    (
                                        _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit'))
                                            .down('[fieldLabel=MODELO]')
                                    );
                                }
                            }
                        });
                    }
                    else
                    {
                        me.getStore().removeAll();
                        if(!Ext.isEmpty(callback))
                        {
                            callback(me);
                        }
                    }
                };
                
                modeloCmp.on(
                {
                    select : function(me)
                    {
                        var record   = _p30_selectedRecord;
                        var cdtipsit = record.get('cdtipsit');
                        
                        var tipovalorName = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel=TIPO VALOR]').name;
                        var tipovalorVal  = record.get(tipovalorName)-0;
                        var anioAc        = new Date().getFullYear()-0;
                        var modeloVal     = me.getValue()-0;
                        
                        debug('tipovalorVal:' , tipovalorVal);
                        debug('modeloVal:'    , modeloVal);
                        
                        if(tipovalorVal==3&&anioAc-modeloVal>1)
                        {
                            mensajeWarning('Para valor de factura solo se permiten modelos del a&ntilde;o actual o anterior');
                            me.setValue('');
                        }
                    }
                });
                
                _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=SUBMARCA]').on(
                {
                    select : function()
                    {
                        _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit')).down('[fieldLabel=MODELO]').heredar(true);
                    }
                });
            }
            else if('|AF|PU|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                Ext.Ajax.request(
                {
                    url : _p30_urlCargarParametros
                    ,params  :
                    {
                        'smap1.parametro' : 'RANGO_ANIO_MODELO'
                        ,'smap1.cdramo'   : _p30_smap1.cdramo
                        ,'smap1.cdtipsit' : cdtipsit
                    }
                    ,success : function(response)
                    {
                        var json=Ext.decode(response.responseText);
                        debug('### obtener rango años response:',json);
                        if(json.exito)
                        {
                            var modeloCmp = _fieldById('_p30_tatrisitAutoForm'+json.smap1.cdtipsit).down('[fieldLabel=MODELO]');
                            modeloCmp.limiteInferior = json.smap1.P1VALOR-0;
                            modeloCmp.limiteSuperior = json.smap1.P2VALOR-0;
                            
                            modeloCmp.validator=function(value)
                            {
                                var record   = _p30_selectedRecord;
                                var cdtipsit = record.get('cdtipsit');
                                var me       = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=MODELO]');
                                
                                var r = true;
                                var anioActual = new Date().getFullYear()-0;
                                var max = anioActual+me.limiteSuperior;
                                var min = anioActual+me.limiteInferior;
                                debug('anioActual:',anioActual);
                                debug('max:',max);
                                debug('min:',min);
                                debug('value:',value);
                                if(value<min||value>max)
                                {
                                    r='El modelo debe estar en el rango '+min+'-'+max;
                                }
                                return r;
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
            //modelo
            
            //version
            if('|AR|CR|PC|PP|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var versionCmp=_fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel*=VERSI]');
                debug('@CUSTOM version:',versionCmp,'.');
                versionCmp.anidado = true;
                versionCmp.heredar = function(remoto,callback)
                {
                    var record      = _p30_selectedRecord;
                    var cdtipsit    = record.get('cdtipsit');
                    var me          = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel*=VERSI]');
                    var submarcaVal = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=SUBMARCA]').getValue();
                    var modeloVal   = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=MODELO]').getValue();
                    if(!Ext.isEmpty(submarcaVal)
                        &&!Ext.isEmpty(modeloVal))
                    {
                        me.getStore().load(
                        {
                            params    :
                            {
                                'params.cdtipsit'  : cdtipsit
                                ,'params.submarca' : submarcaVal
                                ,'params.modelo'   : modeloVal
                            }
                            ,callback : function()
                            {
                                if(!Ext.isEmpty(callback))
                                {
                                    callback
                                    (
                                        _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit'))
                                            .down('[fieldLabel*=VERSI]')
                                    );
                                }
                            }
                        });
                    }
                    else
                    {
                        me.getStore().removeAll();
                        if(!Ext.isEmpty(callback))
                        {
                            callback(me);
                        }
                    }
                };
                
                _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=SUBMARCA]').on(
                {
                    select : function()
                    {
                        _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit')).down('[fieldLabel*=VERSI]').heredar(true);
                    }
                });
                
                _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=MODELO]').on(
                {
                    select : function()
                    {
                        _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit')).down('[fieldLabel*=VERSI]').heredar(true);
                    }
                });
            }
            //version
            
            //clave
            if('|AR|CR|PC|PP|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var claveCmp=_fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel*=CLAVE]');
                debug('@CUSTOM clave:',claveCmp,'.');
                
                claveCmp.getStore().getProxy().extraParams['params.cdtipsit']=cdtipsit;
                
                claveCmp.anidado = true;
                claveCmp.heredar = function(remoto,callback)
                {
                    var record     = _p30_selectedRecord;
                    var cdtipsit   = record.get('cdtipsit');
                    var me         = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel*=CLAVE]');
                    var modeloVal  = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=MODELO]').getValue();
                    var versionVal = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel*=VERSI]').getValue();
                    if(!Ext.isEmpty(modeloVal)
                        &&!Ext.isEmpty(versionVal))
                    {
                        me.getStore().load(
                        {
                            params    :
                            {
                                'params.cadena'    : versionVal
                                ,'params.cdtipsit' : cdtipsit
                            }
                            ,callback : function()
                            {
                                var record      = _p30_selectedRecord;
                                var cdtipsit    = record.get('cdtipsit');
                                var form        = _fieldById('_p30_tatrisitAutoForm'+cdtipsit);
                                var me          = form.down('[fieldLabel*=CLAVE]');
                                var marcaCmp    = form.down('[fieldLabel=MARCA]');
                                var submarcaCmp = form.down('[fieldLabel=SUBMARCA]');
                                var modeloCmp   = form.down('[fieldLabel=MODELO]');
                                var versionCmp  = form.down('[fieldLabel*=VERSI]');
                                
                                var marcaDisplay    = marcaCmp.findRecord('key'    , marcaCmp.getValue()).get('value');
                                var submarcaDisplay = submarcaCmp.findRecord('key' , submarcaCmp.getValue()).get('value');
                                var modeloDisplay   = modeloCmp.findRecord('key'   , modeloCmp.getValue()).get('value');
                                var versionDisplay  = versionCmp.findRecord('key'  , versionCmp.getValue()).get('value');
                                
                                var meDisplay = versionCmp.getValue()+' - '+marcaDisplay+' - '+submarcaDisplay+' - '
                                    +modeloDisplay+' - '+versionDisplay;
                                debug('meDisplay:',meDisplay);
                                me.setValue(me.findRecordByDisplay(meDisplay));
                                
                                if(!Ext.isEmpty(callback))
                                {
                                    callback(me);
                                }
                            }
                        });
                    }
                    else
                    {
                        me.getStore().removeAll();
                        if(!Ext.isEmpty(callback))
                        {
                            callback(me);
                        }
                    }
                };
                
                _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=MODELO]').on(
                {
                    select : function()
                    {
                        _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit')).down('[fieldLabel*=CLAVE]').heredar(true);
                    }
                });
                
                _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel*=VERSI]').on(
                {
                    select : function()
                    {
                        _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit')).down('[fieldLabel*=CLAVE]').heredar(true);
                    }
                });
            }
            //clave
            
            //valor
            if('|AR|CR|PC|PP|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var valorCmp = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel*=VALOR VEH]');
                debug('@CUSTOM valor:',valorCmp);
                valorCmp.heredar = function(conservar)
                {
                    debug('>recuperar conservar:',conservar,'.');
                    var record     = _p30_selectedRecord;
                    var cdtipsit   = record.get('cdtipsit');
                    var form       = _fieldById('_p30_tatrisitAutoForm'+cdtipsit);
                    var me         = form.down('[fieldLabel*=VALOR VEH]');
                    var versionVal = form.down('[fieldLabel*=VERSI]').getValue();
                    var modeloVal  = form.down('[fieldLabel=MODELO]').getValue();
                    
                    var tipovalorName = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel=TIPO VALOR]').name;
                    var tipovalorVal  = record.get(tipovalorName);
                    
                    debug('versionVal:'    , versionVal);
                    debug('modeloVal:'     , modeloVal);
                    debug('tipovalorName:' , tipovalorName);
                    debug('tipovalorVal:'  , tipovalorVal);
                    
                    if(!Ext.isEmpty(versionVal)&&!Ext.isEmpty(modeloVal)&&!Ext.isEmpty(tipovalorVal))
                    {
                        me.setLoading(true);
                        Ext.Ajax.request(
                        {
                            url      : _p30_urlCargarSumaAseguradaRamo5
                            ,params  :
                            {
                                'smap1.cdtipsit'  : cdtipsit
                                ,'smap1.clave'    : versionVal
                                ,'smap1.modelo'   : modeloVal
                                ,'smap1.cdsisrol' : _p30_smap1.cdsisrol
                            }
                            ,success : function(response)
                            {
                                me.setLoading(false);
                                var json = Ext.decode(response.responseText);
                                debug('### cargar suma asegurada:',json);
                                if(json.exito)
                                {
                                    if(!Ext.isEmpty(json.respuesta))
                                    {
                                        mensajeWarning(json.respuesta);
                                    }
                                    if(!conservar||Ext.isEmpty(me.getValue()))
                                    {
                                        me.setValue(json.smap1.sumaseg);
                                    }
                                    me.valorCargado=json.smap1.sumaseg;
                                    
                                    me.setLoading(true);
                                    Ext.Ajax.request(
                                    {
                                        url      : _p30_urlCargarParametros
                                        ,params  :
                                        {
                                            'smap1.parametro' : 'RANGO_VALOR'
                                            ,'smap1.cdramo'   : _p30_smap1.cdramo
                                            ,'smap1.cdtipsit' : cdtipsit
                                            ,'smap1.clave4'   : tipovalorVal
                                            ,'smap1.clave5'   : _p30_smap1.cdsisrol
                                        }
                                        ,success : function(response)
                                        {
                                            me.setLoading(false);
                                            var json = Ext.decode(response.responseText);
                                            debug('### obtener rango valor:',json);
                                            if(json.exito)
                                            {
                                                var valormin = me.valorCargado-0*(1+(json.smap1.P1VALOR-0));
                                                var valormax = me.valorCargado-0*(1+(json.smap1.P2VALOR-0));
                                                me.setMinValue(valormin);
                                                me.setMaxValue(valormax);
                                                me.isValid();
                                                debug('valor:',me.valorCargado);
                                                debug('valormin:',valormin);
                                                debug('valormax:',valormax);
                                            }
                                            else
                                            {
                                                mensajeError(json.respuesta);
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
                                    mensajeError(json.respuesta);
                                }
                            }
                            ,failure : function()
                            {
                                me.setLoading(false);
                                errorComunicacion();
                            }
                        });
                    }
                };
                
                _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=MODELO]').on(
                {
                    select : function()
                    {
                        _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit')).down('[fieldLabel*=VALOR VEH]').heredar(false);
                    }
                });
                
                _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel*=VERSI]').on(
                {
                    select : function()
                    {
                        _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit')).down('[fieldLabel*=VALOR VEH]').heredar(false);
                    }
                });
            }
            else if('|AF|PU|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var sumaCmp = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=SUMA ASEGURADA]');
                debug('@CUSTOM suma asegurada front.:',sumaCmp);
                sumaCmp.anidado = true;
                sumaCmp.heredar = function()
                {
                    var record        = _p30_selectedRecord;
                    var cdtipsit      = record.get('cdtipsit');
                    var me            = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=SUMA ASEGURADA]');
                    var tipovalorName = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel*=VALOR A COTIZAR]').name;
                    var tipovalorVal  = record.get(tipovalorName)-0;
                    
                    debug('tipovalorName:' , tipovalorName,record.data);
                    debug('tipovalorVal:'  , tipovalorVal);
                    
                    me.setReadOnly(tipovalorVal==2);
                }
                
                //trigger tipo valor
                var tipovalorCmp = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel*=VALOR A COTIZAR]');
                debug('@CUSTOM valor a cotizar front. trigger suma aseg.:',tipovalorCmp);
                tipovalorCmp.on(
                {
                    select : function(me)
                    {
                        var record   = _p30_selectedRecord;
                        var cdtipsit = record.get('cdtipsit');
                        var sumaName = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel=SUMA ASEGURADA]').name;
                        var sumaVal  = record.get(sumaName);
                        
                        debug('sumaName:',sumaName,record.data); 
                        debug('sumaVal:',sumaVal);
                        
                        if(!Ext.isEmpty(sumaVal))
                        {
                            record.set(sumaName,'');
                            mensajeWarning('Debe actualizar la suma asegurada del veh&iacute;culo');
                        }
                    }
                });
                //trigger tipo valor
                
                //trigger tipo vehiculo
                var tipovehCmp = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel*=TIPO VEH]');
                debug('@CUSTOM tipo veh. front. trigger suma aseg.:',tipovehCmp);
                tipovehCmp.on(
                {
                    select : function(me)
                    {
                        var record   = _p30_selectedRecord;
                        var cdtipsit = record.get('cdtipsit');
                        var sumaName = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel=SUMA ASEGURADA]').name;
                        var sumaVal  = record.get(sumaName);
                        
                        debug('sumaName:',sumaName,record.data); 
                        debug('sumaVal:',sumaVal);
                        
                        if(!Ext.isEmpty(sumaVal))
                        {
                            record.set(sumaName,'');
                            mensajeWarning('Debe actualizar la suma asegurada del veh&iacute;culo');
                        }
                    }
                });
                //trigger tipo vehiculo
                
                //trigger cd postal
                var postalCmp = _fieldLikeLabel('CIRCULACI',_fieldById('_p30_form'));
                if(Ext.isEmpty(postalCmp.triggerSumaAseg)||postalCmp.triggerSumaAseg!=true)
                {
                    debug('@CUSTOM cd postal trigger suma aseg.:',postalCmp);
                    postalCmp.triggerSumaAseg=true;
                    postalCmp.on(
                    {
                        change : function()
                        {
                            var sumas = [];
                            var sumaName = _p30_tatrisitFullForms['AF'].down('[fieldLabel=SUMA ASEGURADA]').name;
                            debug('sumaName:',sumaName);
                            _p30_store.each(function(record)
                            {
                                var cdtipsit=record.get('cdtipsit');
                                if('|AF|PU|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
                                {
                                    if(!Ext.isEmpty(record.get(sumaName)))
                                    {
                                        record.set(sumaName,'');
                                        sumas.push(record.get('nmsituac'));
                                    }
                                }
                            });
                            if(sumas.length>0)
                            {
                                mensajeWarning('Debe actualizar la suma asegurada de los '
                                    +'veh&iacute;culos fronterizos en los siguientes incisos: '+sumas.join(','));
                            }
                        }
                    });
                }
                //trigger cd postal
            }
            //valor
            
            //seleccionar auto
            if('|AR|CR|PC|PP|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var form = _fieldById('_p30_tatrisitAutoForm'+cdtipsit);
                debug('@CUSTOM form buscarAutoDesc:',form);
                
                form.buscarAutoDesc = function()
                {
                    debug('>buscarAutoDesc');
                    
                    var record   = _p30_selectedRecord;
                    var cdtipsit = record.get('cdtipsit');
                    var form     = _fieldById('_p30_tatrisitAutoForm'+cdtipsit);
                    
                    var claveCmp    = form.down('[fieldLabel*=CLAVE]');
                    var marcaCmp    = form.down('[fieldLabel=MARCA]');
                    var submarcaCmp = form.down('[fieldLabel=SUBMARCA]');
                    var modeloCmp   = form.down('[fieldLabel=MODELO]');
                    var versionCmp  = form.down('[fieldLabel*=VERSI]');
                    debug('form:'        , form);
                    debug('claveCmp:'    , claveCmp);
                    debug('marcaCmp:'    , marcaCmp);
                    debug('submarcaCmp:' , submarcaCmp);
                    debug('modeloCmp:'   , modeloCmp);
                    debug('versionCmp:'  , versionCmp);
                    
                    var claveVal = claveCmp.getValue();
                    if(!Ext.isEmpty(claveVal))
                    {
                        var record=claveCmp.findRecord('key',claveVal);
                        if(record!=false)
                        {
                            var claveDisplay      = record.get('value');
                            var claveDisplaySplit = claveDisplay.split(' - ');
                            var marcaDisplay      = claveDisplaySplit[1];
                            var submarcaDisplay   = claveDisplaySplit[2];
                            var modeloDisplay     = claveDisplaySplit[3];
                            var versionDisplay    = claveDisplaySplit[4];
                            
                            var tipovalorName = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel=TIPO VALOR]').name;
                            var tipovalorVal  = _p30_selectedRecord.get(tipovalorName)-0;
                            var anioAc        = new Date().getFullYear()-0;
                            debug('tipovalorVal:'  , tipovalorVal);
                            debug('modeloDisplay:' , modeloDisplay);
                            
                            if(tipovalorVal==3&&anioAc-(modeloDisplay-0)>1)
                            {
                                mensajeWarning('Para valor de factura solo se permiten modelos del a&ntilde;o actual o anterior');
                            }
                            else
                            {
                                form.setLoading(true);
                            
                                marcaCmp.setValue(marcaCmp.findRecordByDisplay(marcaDisplay));
                            
                                submarcaCmp.auxDisplay = submarcaDisplay;
                                modeloCmp.auxDisplay   = modeloDisplay;
                                versionCmp.auxDisplay  = versionDisplay;
                            
                                submarcaCmp.heredar(true,function(cmp)
                                {
                                    cmp.setValue(cmp.findRecordByDisplay(cmp.auxDisplay));
                                    _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit'))
                                        .down('[fieldLabel=MODELO]').heredar(true,function(cmp)
                                    {
                                        cmp.setValue(cmp.auxDisplay);
                                        _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit'))
                                            .down('[fieldLabel*=VERSI]').heredar(true,function(cmp)
                                        {
                                            cmp.setValue(cmp.findRecordByDisplay(cmp.auxDisplay));
                                            _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit')).setLoading(false);
                                            _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit'))
                                                .down('[fieldLabel*=VALOR VEH]').heredar(false);
                                        });
                                    });
                                });
                            }
                        }
                    }
                };
                
                form.down('[fieldLabel*=CLAVE]').on(
                {
                    select : function()
                    {
                        _fieldById('_p30_tatrisitAutoForm'+_p30_selectedRecord.get('cdtipsit')).buscarAutoDesc();
                    }
                });
            }
            //seleccionar auto
            
            //tipo valor
            if('|AR|CR|PC|PP|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var tipovalorCmp = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=TIPO VALOR]');
                debug('@CUSTOM tipovalor:',tipovalorCmp);
                tipovalorCmp.on(
                {
                    select : function(me)
                    {
                        var record   = _p30_selectedRecord;
                        var cdtipsit = record.get('cdtipsit');
                        var form     = _p30_tatrisitParcialForms[cdtipsit];
                        var meVal    = me.getValue()-0;
                        
                        var modeloName = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel=MODELO]').name;
                        var modeloVal  = record.get(modeloName);
                        if(!Ext.isEmpty(modeloVal))
                        {
                            var valorName  = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel*=VALOR VEH]').name;
                            var valorVal   = record.get(valorName);
                            var valorReset = false;
                            if(!Ext.isEmpty(valorVal))
                            {
                                valorReset=true;
                                record.set(valorName,'');
                            }
                            
                            var anioAc      = new Date().getFullYear()-0;
                            var modeloReset = false;
                            
                            debug('meVal'     , meVal);
                            debug('anioAc'    , anioAc);
                            debug('modeloVal' , modeloVal);
                            
                            if(meVal==3&&anioAc-(modeloVal-0)>1)
                            {
                                modeloReset=true;
                                record.set(modeloName , '');
                                record.set('cdplan'   , '');
                                //form.down('[name=cdplan]').heredar();
                            }
                            
                            if(valorReset&&modeloReset)
                            {
                                mensajeWarning('Para valor de factura solo se permiten modelos del a&ntilde;o '
                                    +'actual o anterior, favor de actualizar el modelo y el valor del veh&iacute;culo');
                            }
                            else if(valorReset)
                            {
                                mensajeWarning('Debe actualizar el valor del veh&iacute;culo');
                            }
                            else if(modeloReset)
                            {
                                mensajeWarning('Para valor de factura solo se permiten modelos del a&ntilde;o '
                                    +'actual o anterior, favor de actualizar el modelo');
                            }
                        }
                    }
                });
            }
            //tipo valor
            
            //tipo cambio
            if('|AF|PU|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var dolarCmp = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel*=TIPO DE CAMBIO]');
                debug('@CUSTOM dolarCmp:',dolarCmp,'.');
                dolarCmp.anidado = true;
                dolarCmp.heredar = function(remoto,callback)
                {
                    var record   = _p30_selectedRecord;
                    var cdtipsit = record.get('cdtipsit');
                    var form     = _fieldById('_p30_tatrisitAutoForm'+cdtipsit);
                    var me       = form.down('[fieldLabel*=TIPO DE CAMBIO]');
                    var serieCmp = form.down('[fieldLabel*=MERO DE SERIE]');
                    
                    me.hide();
                    
                    if(Ext.isEmpty(_p30_precioDolarDia))
                    {
                        serieCmp.setLoading(true);
                        Ext.Ajax.request(
                        {
                            url      : _p30_urlCargarTipoCambioWS
                            ,success : function(response)
                            {
                                serieCmp.setLoading(false);
                                var json=Ext.decode(response.responseText);
                                debug('### dolar:',json);
                                _p30_precioDolarDia=json.smap1.dolar;
                                me.setValue(_p30_precioDolarDia);
                            }
                            ,failure : function()
                            {
                                serieCmp.setLoading(false);
                                errorComunicacion();
                            }
                        });
                    }
                    else
                    {
                        me.setValue(_p30_precioDolarDia);
                    }
                };
            }
            //tipo cambio
            
            //serie
            if('|AF|PU|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var serieCmp = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel*=MERO DE SERIE]');
                debug('@CUSTOM serie:',serieCmp);
                serieCmp.anidado = true;
                serieCmp.heredar = function()
                {
                    var record    = _p30_selectedRecord;
                    var cdtipsit  = record.get('cdtipsit');
                    var window    = _p30_tatrisitAutoWindows[cdtipsit];
                    var postalCmp = _fieldLikeLabel('CIRCULACI',_fieldById('_p30_form'));
                    
                    if(!postalCmp.isValid())
                    {
                        window.noMostrar=true;
                        mensajeWarning('Debe seleccionar el c&oacute;digo postal primero',function()
                        {
                            _fieldLikeLabel('CIRCULACI',_fieldById('_p30_form')).focus();
                        });
                    }
                };
                
                serieCmp.recuperar = function()
                {
                    var record     = _p30_selectedRecord;
                    var cdtipsit   = record.get('cdtipsit');
                    var form       = _fieldById('_p30_tatrisitAutoForm'+cdtipsit);
                    var me         = form.down('[fieldLabel*=MERO DE SERIE]');
                    var meVal      = me.getValue();
                    var marcaCmp   = form.down('[fieldLabel=MARCA]');
                    var modeloCmp  = form.down('[fieldLabel=MODELO]');
                    var versionCmp = form.down('[fieldLabel*=DESCRIPCI]');
                    var sumaCmp    = form.down('[fieldLabel=SUMA ASEGURADA]');
                    var suma2Cmp   = form.down('[fieldLabel=RESPALDO VALOR NADA]');
                    
                    var precioDolar = form.down('[fieldLabel*=TIPO DE CAMBIO]').getValue()-0;
                    var postalVal   = _fieldLikeLabel('CIRCULACI',_fieldById('_p30_form')).getValue();
                    
                    var tipovehName = _p30_tatrisitFullForms[cdtipsit].down('[fieldLabel*=TIPO VEH]').name;
                    var tipovehVal  = record.get(tipovehName);
                    debug('tipovehName:' , tipovehName,record.data);
                    debug('tipovehVal:'  , tipovehVal);
                    
                    if(!Ext.isEmpty(meVal)&&meVal.length==17)
                    {
                        me.setLoading(true);
                        Ext.Ajax.request(
                        {
                            url : _p30_urlNada
                            ,params :
                            {
                                'smap1.vim'       : meVal
                                ,'smap1.cdramo'   : _p30_smap1.cdramo
                                ,'smap1.cdtipsit' : cdtipsit
                                ,'smap1.tipoveh'  : tipovehVal
                                ,'smap1.codpos'   : postalVal
                            }
                            ,success : function(response)
                            {
                                me.setLoading(false);
                                var json=Ext.decode(response.responseText);
                                debug('### nada:',json);
                                if(json.success)
                                {
                                    marcaCmp.setValue(json.smap1.AUTO_MARCA);
                                    modeloCmp.setValue(json.smap1.AUTO_ANIO);
                                    versionCmp.setValue(json.smap1.AUTO_DESCRIPCION);
                                    sumaCmp.setMinValue
                                    (
                                        (
                                            (
                                                (
                                                    json.smap1.AUTO_PRECIO-0
                                                )*precioDolar
                                            )
                                            *
                                            (
                                                1-
                                                (
                                                    json.smap1.FACTOR_MIN-0
                                                )
                                            )
                                        ).toFixed(2)
                                    );
                                    sumaCmp.setMaxValue
                                    (
                                        (
                                            (
                                                (
                                                    json.smap1.AUTO_PRECIO-0
                                                )*precioDolar
                                            )
                                            *
                                            (
                                                1+
                                                (
                                                    json.smap1.FACTOR_MAX-0
                                                )
                                            )
                                        ).toFixed(2)
                                    );
                                    sumaCmp.setValue
                                    (
                                        (
                                            (
                                                json.smap1.AUTO_PRECIO-0
                                            )*precioDolar
                                        ).toFixed(2)
                                    );
                                    suma2Cmp.setValue
                                    (
                                        (
                                            (
                                                json.smap1.AUTO_PRECIO-0
                                            )*precioDolar
                                        ).toFixed(2)
                                    );
                                }
                                else
                                {
                                    mensajeError(json.error);
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
                        marcaCmp.setValue('');
                        modeloCmp.setValue('');
                        versionCmp.setValue('');
                        sumaCmp.setValue('');
                        suma2Cmp.setValue('');
                    }
                };
                
                serieCmp.on(
                {
                    change : function(me)
                    {
                        me.recuperar();
                    }
                });
            }
            //serie
            
            //respaldo nada
            if('|AF|PU|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var respNadaCmp = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=RESPALDO VALOR NADA]');
                debug('@CUSTOM respaldo nada:',respNadaCmp);
                respNadaCmp.anidado = true;
                respNadaCmp.heredar = function()
                {
                    var record    = _p30_selectedRecord;
                    var cdtipsit  = record.get('cdtipsit');
                    var me        = _fieldById('_p30_tatrisitAutoForm'+cdtipsit).down('[fieldLabel=RESPALDO VALOR NADA]');
                    me.hide();
                };
            }
            //respaldo nada
            
            //carga
            if('|CR|PC|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
            {
                var cargaCmp = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=CARGA]');
                debug('@CUSTOM carga:',cargaCmp,'.');
                cargaCmp.anidado = true;
                cargaCmp.heredar = function(remoto,callback)
                {
                    var record      = _p30_selectedRecord;
                    var cdtipsit    = record.get('cdtipsit');
                    var me          = _fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=CARGA]');
                    var negocioVal  = _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getValue();
                    if(!Ext.isEmpty(negocioVal))
                    {
                        me.getStore().load(
                        {
                            params :
                            {
                                'params.negocio' : negocioVal
                            }
                            ,callback : function()
                            {
                                if(!Ext.isEmpty(callback))
                                {
                                    callback
                                    (
                                        _fieldById('_p30_tatrisitParcialForm'+_p30_selectedRecord.get('cdtipsit'))
                                            .down('[fieldLabel=CARGA]')
                                    );
                                }
                            } 
                        });
                    }
                    else
                    {
                        me.getStore().removeAll();
                        if(!Ext.isEmpty(callback))
                        {
                            callback(me);
                        }
                    }
                };
            }
            //carga
        }
        //herencia situaciones
    }
    //ramo 5
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
function _p30_calculaVigencia()
{
    debug('>_p30_calculaVigencia');
    var feini = _fieldByName('feini');
    var fefin = _fieldByName('fefin');
    
    var itemVigencia=_fieldByLabel('VIGENCIA',_fieldById('_p30_form'));
    itemVigencia.hide();
    
    if(feini.isValid()&&fefin.isValid())
    {
        var milisDif = Ext.Date.getElapsed(feini.getValue(),fefin.getValue());
        var diasDif  = (milisDif/1000/60/60/24).toFixed(0);
        debug('milisDif:',milisDif,'diasDif:',diasDif);
        itemVigencia.setValue(diasDif);
    }
    debug('<_p30_calculaVigencia');
}

function _p30_configuracionPanelDinClic(cdtipsit,titulo)
{
    debug('>_p30_configuracionPanelDinClic:',cdtipsit,titulo);
    var panel = _p30_paneles[cdtipsit];
    panel.setTitle(titulo);
    if(panel.valores!=false)
    {
        panel.down('form').loadRecord(new _p30_modelo(panel.valores));
    }
    else
    {
        panel.down('form').getForm().reset();
    }
    panel.callback=function()
    {
        var form = panel.down('form');
        var valido = form.isValid();
        if(!valido)
        {
            datosIncompletos();
        }
        
        if(valido)
        {
            panel.valores=form.getValues();
            panel.hide();
            debug('panel:',panel);
        }
    }
    centrarVentanaInterna(panel.show());
    debug('<_p30_configuracionPanelDinClic');
}

function _p30_agregarAuto()
{
    debug('>_p30_agregarAuto');
    
    var valido=true;
    if(valido&&_p30_smap1.cdramo+'x'=='5x')
    {
        valido=!Ext.isEmpty(_fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getValue());
        if(!valido)
        {
            mensajeWarning('Seleccione el negocio');
        }
    }
    
    if(valido)
    {
        var record=new _p30_modelo();
        _p30_store.add(record);
        _p30_numerarIncisos();
        _fieldById('_p30_grid').getSelectionModel().select(record);
    }
    
    debug('<_p30_agregarAuto');
}

function _p30_gridBotonConfigClic(view,row,col,item,e,record)
{
    debug('>_p30_gridBotonConfigClic:',record);
    var cdtipsit = record.get('cdtipsit');
    
    var valido = !Ext.isEmpty(cdtipsit);
    if(!valido)
    {
        mensajeWarning('Debe seleccionar el tipo de veh&iacute;culo');
    }
    
    if(valido)
    {
        var cdtipsitPanel = _p30_smap1['destino_'+cdtipsit];
        debug('cdtipsit:',cdtipsit,'cdtipsitPanel:',cdtipsitPanel);
        var panel = _p30_paneles[cdtipsitPanel];
        panel.setTitle('CONFIGURACI&Oacute;N DE PAQUETE');
        var form  = panel.down('form');
        if(record.get('personalizado')=='si')
        {
            form.loadRecord(record);
        }
        else if(panel.valores!=false)
        {
            form.loadRecord(new _p30_modelo(panel.valores));
        }
        else
        {
            form.getForm().reset();
        }
        panel.callback=function()
        {
            var values = form.getValues();
            for(var prop in values)
            {
                record.set(prop,values[prop]);
            }
            record.set('personalizado','si');
            debug('record:',record);
            panel.hide();
        }
        centrarVentanaInterna(panel.show());
    }
    debug('<_p30_gridBotonConfigClic');
}

function _p30_gridBotonEliminarClic(view,row,col,item,e,record)
{
    debug('>_p30_gridBotonEliminarClic:',record);
    _p30_store.remove(record);
    _p30_numerarIncisos();
    _p30_semaforoBorrar=true;
    debug('<_p30_gridBotonEliminarClic');
}

function _p30_ramo5AgenteSelect(comp,records)
{
    var cdagente = typeof records == 'string' ? records : records[0].get('key');
    debug('>_p30_ramo5AgenteSelect cdagente:',cdagente);
    Ext.Ajax.request(
    {
        url     : _p30_urlCargarCduniecoAgenteAuto
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
                _p30_smap1.cdunieco=json.smap1.cdunieco;
                debug('_p30_smap1:',_p30_smap1);
                Ext.Ajax.request(
                {
                    url     : _p30_urlCargarRetroactividadSuplemento
                    ,params :
                    {
                        'smap1.cdunieco'  : _p30_smap1.cdunieco
                        ,'smap1.cdramo'   : _p30_smap1.cdramo
                        ,'smap1.cdtipsup' : 1
                        ,'smap1.cdusuari' : _p30_smap1.cdusuari
                        ,'smap1.cdtipsit' : _p30_smap1.cdtipsit
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
                _fieldByLabel('AGENTE',_fieldById('_p30_form')).reset();
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            _fieldByLabel('AGENTE',_fieldById('_p30_form'))
            errorComunicacion();
        }
    });
    debug('<_p30_ramo5AgenteSelect');
}

function _p30_ramo5ClienteChange()
{
    var combcl  = _fieldLikeLabel('CLIENTE NUEVO',_fieldById('_p30_form'));
    
    debug('>_p30_ramo5ClienteChange value:',combcl.getValue());
    
    var nombre  = _fieldLikeLabel('NOMBRE CLIENTE' , _fieldById('_p30_form'));
    var tipoper = _fieldByLabel('TIPO PERSONA'     , _fieldById('_p30_form'));
    var codpos  = _fieldLikeLabel('CP CIRCULACI'   , _fieldById('_p30_form'));
    
    //cliente nuevo
    if(combcl.getValue()=='S')
    {
        nombre.reset();
        tipoper.reset();
        codpos.reset();
        
        nombre.setReadOnly(false);
        tipoper.setReadOnly(false);
        codpos.setReadOnly(false);
        
        _p30_recordClienteRecuperado=null;
    }
    //recuperar cliente
    else if(combcl.getValue()=='N' && ( Ext.isEmpty(combcl.semaforo)||combcl.semaforo==false ) )
    {
        nombre.reset();
        tipoper.reset();
        codpos.reset();
        
        nombre.setReadOnly(true);
        tipoper.setReadOnly(true);
        codpos.setReadOnly(true);
        
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
                            ,name       : '_p30_recuperaRfc'
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
                                var rfc=_fieldByName('_p30_recuperaRfc').getValue();
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
                                            ,'map1.cdtipsit'      : _p30_smap1.cdtipsit
                                            ,'map1.pv_cdtipsit_i' : _p30_smap1.cdtipsit
                                            ,'map1.pv_cdunieco_i' : _p30_smap1.cdunieco
                                            ,'map1.pv_cdramo_i'   : _p30_smap1.cdramo
                                            ,'map1.pv_estado_i'   : 'W'
                                            ,'map1.pv_nmpoliza_i' : _fieldByName('nmpoliza',_fieldById('_p30_form')).getValue()
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
                                _p30_recordClienteRecuperado=record;
                                nombre.setValue(record.raw.NOMBRECLI);
                                tipoper.setValue(record.raw.TIPOPERSONA);
                                codpos.setValue(record.raw.CODPOSTAL);
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
                        model     : '_p30_modeloRecuperado'
                        ,autoLoad : false
                        ,proxy    :
                        {
                            type    : 'ajax'
                            ,url    : _p30_urlRecuperarCliente
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
    debug('<_p30_ramo5ClienteChange');
}

function _p30_cotizar(sinTarificar)
{
    debug('>_p30_cotizar sinTarificar:',sinTarificar,'.');
    
    var valido = _fieldById('_p30_form').isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
    if(valido)
    {
        var error  = '';
        for(var i=0;i<_f1_botones.length;i++)
        {
            var boton    = _f1_botones[i];
            var cdtipsit = boton.cdtipsit;
            var panel    = _p30_paneles[cdtipsit];
            debug('buscando en panel:',panel);
            if(panel.valores==false)
            {
                valido = false;
                error  = error+'FALTA DEFINIR: '+boton.text+'<br/>';
            }
        }
        if(!valido)
        {
            mensajeWarning(error);
        }
    }
    
    if(valido)
    {
        if(_p30_smap1.cdsisrol=='SUSCRIAUTO')
        {
            valido = _p30_store.getCount()>=1;
            if(!valido)
            {
                mensajeWarning('Debe capturar al menos un inciso');
            }
        }
        else
        {
            valido = _p30_store.getCount()>=5;
            if(!valido)
            {
                mensajeWarning('Debe capturar al menos cinco incisos');
            }
        }
    }
    
    if(valido)
    {
        var error        = '';
        var agregarError = function(cadena,nmsituac)
        {
            valido = false;
            error  = error + 'Inciso ' + nmsituac + ': ' + cadena +'</br>';
        };
        _p30_store.each(function(record)
        {
            var cdtipsit = record.get('cdtipsit');
            var nmsituac = record.get('nmsituac');
            
            if(!Ext.isEmpty(cdtipsit))
            {
                if(Ext.isEmpty(record.get('cdplan')))
                {
                    agregarError('Debe seleccionar el paquete',nmsituac);
                }
                var itemsObliga = Ext.ComponentQuery.query('[swobligaflot=true]',_p30_tatrisitFullForms[cdtipsit]);
                for(var i in itemsObliga)
                {
                    if(Ext.isEmpty(record.get(itemsObliga[i].getName())))
                    {
                        agregarError('Falta definir "'+itemsObliga[i].getFieldLabel()+'"',nmsituac);
                    }
                }
            }
            else
            {
                agregarError('Debe seleccionar el tipo de veh&iacute;culo',nmsituac);
            }
        });
        if(!valido)
        {
            mensajeWarning(error);
        }
    }
    
    if(valido)
    {
        valido = _fieldByName('nmpoliza',_fieldById('_p30_form')).sucio==false;
        if(!valido)
        {
            _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo = true;
            _fieldByName('nmpoliza',_fieldById('_p30_form')).setValue('');
            _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo = false;
            valido = true;
        }
    }
    
    debug('_p30_paneles:',_p30_paneles,'valido:',valido);
    if(valido)
    {
        var form = _fieldById('_p30_form');
        
        //copiar paneles a oculto
        var arr = Ext.ComponentQuery.query('#_p30_gridTarifas');
        if(arr.length>0)
        {
            var formDescuentoActual = _fieldById('_p30_formDescuento');
            var formCesion          = _fieldById('_p30_formCesion');
            var recordPaneles       = new _p30_modelo(formDescuentoActual.getValues());
            var itemsCesion         = Ext.ComponentQuery.query('[fieldLabel]',formCesion);
            for(var i=0;i<itemsCesion.length;i++)
            {
                recordPaneles.set(itemsCesion[i].getName(),itemsCesion[i].getValue());
            }
            form.formOculto.loadRecord(recordPaneles);
            debug('form.formOculto.getValues():',form.formOculto.getValues());
        }
    
        debug('length:',_p30_paneles.length,'type:',typeof _p30_paneles);
        var recordsCdtipsit = [];
        for(var cdtipsitPanel in _p30_paneles)
        {
            var panel      = _p30_paneles[cdtipsitPanel];
            var recordBase = new _p30_modelo(panel.valores);
            recordBase.set('cdtipsit',cdtipsitPanel);
            debug('cdtipsitPanel:',cdtipsitPanel,'recordBase:',recordBase);
            recordsCdtipsit[cdtipsitPanel] = recordBase;
        }
        debug('recordsCdtipsit:',recordsCdtipsit);
        var storeTvalosit = Ext.create('Ext.data.Store',
        {
            model : '_p30_modelo'
        });
        var formValuesAux = form.getValues();
        var formValues    = {};
        for(var prop in formValuesAux)
        {
            if(prop+'x'!='x'
                &&prop.slice(0,5)=='param')
            {
                formValues[prop]=formValuesAux[prop];
            }
        }
        debug('formValues:',formValues);
        var valuesFormOculto = form.formOculto.getValues();
        debug('valuesFormOculto:',valuesFormOculto);
        _p30_store.each(function(record)
        {
            var cdtipsit       = record.get('cdtipsit');
            var cdtipsitPanel  = _p30_smap1['destino_'+cdtipsit];
            var recordBase     = recordsCdtipsit[cdtipsitPanel];
            var recordTvalosit = new _p30_modelo(record.data);
            for(var prop in recordTvalosit.data)
            {
                var valor = recordTvalosit.get(prop);
                var base  = recordBase.get(prop);
                if(valor+'x'=='x'&&base+'x'!='x')
                {
                    recordTvalosit.set(prop,base);
                }
            }
            
            if(_p30_smap1.mapeo=='DIRECTO')
            {
                for(var prop in formValues)
                {
                    recordTvalosit.set(prop,formValues[prop]);
                }
                for(var att in valuesFormOculto)
                {
                    recordTvalosit.set(att,valuesFormOculto[att]);
                }
            }
            else
            {
                var mapeos = _p30_smap1.mapeo.split('#');
                debug('mapeos:',mapeos);
                for(var i in mapeos)
                {
                    var cdtipsitsMapeo = mapeos[i].split('|')[0];
                    var mapeo          = mapeos[i].split('|')[1];
                    debug('cdtipsit:',cdtipsit,'cdtipsitsMapeo:',cdtipsitsMapeo);
                    if((','+cdtipsitsMapeo+',').lastIndexOf(','+cdtipsit+',')!=-1)
                    {
                        debug('coincidente:',cdtipsitsMapeo,'cdtipsit:',cdtipsit)
                        debug('mapeo:',mapeo);
                        if(mapeo=='DIRECTO')
                        {
                            debug('directo');
                            for(var prop in formValues)
                            {
                                recordTvalosit.set(prop,formValues[prop]);
                            }
                            for(var att in valuesFormOculto)
                            {
                                recordTvalosit.set(att,valuesFormOculto[att]);
                            }
                        }
                        else
                        {
                            var atributos = mapeo.split('@');
                            debug('atributos:',atributos);
                            for(var i in atributos)
                            {
                                var atributoIte = atributos[i];
                                var modelo      = atributoIte.split(',')[0];
                                var origen      = atributoIte.split(',')[1];
                                var pantalla    = atributoIte.split(',')[2];
                                
                                modelo   = 'parametros.pv_otvalor'+(('x00'+modelo)  .slice(-2));
                                pantalla = 'parametros.pv_otvalor'+(('x00'+pantalla).slice(-2));
                                
                                debug('modelo:'   , modelo   , '.');
                                debug('origen:'   , origen   , '.');
                                debug('pantalla:' , pantalla , '.');
                                
                                if(origen=='F')
                                {
                                    recordTvalosit.set(modelo,formValues[pantalla]);
                                }
                                else if(origen=='O')
                                {
                                    recordTvalosit.set(modelo,valuesFormOculto[pantalla]);
                                }
                            }
                        }
                    }
                }
            }
            
            storeTvalosit.add(recordTvalosit);
            debug('record:',record.data,'tvalosit:',recordTvalosit.data);
        });
        debug('_p30_store:',_p30_store);
        debug('storeTvalosit:',storeTvalosit);
        
        var json =
        {
            smap1 :
            {
                cdunieco     : _p30_smap1.cdunieco
                ,cdramo      : _p30_smap1.cdramo
                ,estado      : 'W'
                ,nmpoliza    : _fieldByName('nmpoliza',_fieldById('_p30_form')).getValue()
                ,cdtipsit    : _p30_smap1.cdtipsit
                ,cdpersonCli : Ext.isEmpty(_p30_recordClienteRecuperado) ? '' : _p30_recordClienteRecuperado.raw.CLAVECLI
                ,cdideperCli : Ext.isEmpty(_p30_recordClienteRecuperado) ? '' : _p30_recordClienteRecuperado.raw.CDIDEPER
                ,feini       : Ext.Date.format(_fieldByName('feini').getValue(),'d/m/Y')
                ,fefin       : Ext.Date.format(_fieldByName('fefin').getValue(),'d/m/Y')
                ,cdagente    : _fieldByLabel('AGENTE',_fieldById('_p30_form')).getValue()
                ,notarificar : sinTarificar ? 'si' : ''
                ,tipoflot    : _p30_smap1.tipoflot
            }
            ,slist1 : []
            ,slist2 : []
            ,slist3 : []
        };
        
        for(var cdtipsitPanel in recordsCdtipsit)
        {
            json.slist3.push(recordsCdtipsit[cdtipsitPanel].data);
        }
        
        _p30_store.each(function(record)
        {
            json.slist2.push(record.data);
        });
        
        storeTvalosit.each(function(record)
        {
            json.slist1.push(record.data);
        });
        
        debug('>>> json a enviar:',json);
        
        var panelpri = _fieldById('_p30_panelpri');
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p30_urlCotizar
            ,jsonData : json
            ,success  : function(response)
            {
                panelpri.setLoading(false);
                
                _p30_bloquear(true);
                
                json = Ext.decode(response.responseText);
                debug('### cotizar:',json);
                if(json.exito)
                {
                    _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo=true;
                    _fieldByName('nmpoliza',_fieldById('_p30_form')).setValue(json.smap1.nmpoliza);
                    _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo=false;
                    
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
                    debug('itemsDescuento:',itemsDescuento);
                    
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
                    
                    var arr = Ext.ComponentQuery.query('#_p30_gridTarifas');
                    if(arr.length>0)
                    {
                        _fieldById('_p30_formCesion').destroy();
                        panelpri.remove(arr[arr.length-1],true);
                    }
                    
                    var _p30_formDescuento = Ext.create('Ext.form.Panel',
                    {
                        itemId        : '_p30_formDescuento'
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
                                    itemId       : '_p30_formCesion'
                                    ,border      : 0
                                    ,defaults    : { style : 'margin:5px;' }
                                    ,items       : itemsComision
                                    ,buttonAlign : 'center'
                                    ,buttons     :
                                    [
                                        {
                                            itemId   : '_p30_botonAplicarCesion'
                                            ,text    : 'Aplicar'
                                            ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                            ,handler : function(me) { me.up('window').hide(); _p30_cotizar(); }
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
                                itemId   : '_p30_botonAplicarDescuento'
                                ,text    : 'Aplicar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                ,handler : function(){_p30_cotizar();}
                            }
                        ]
                    });
                    
                    _p30_formDescuento.loadRecord(new _p30_modelo(form.formOculto.getValues()));
                    _fieldById('_p30_formCesion').loadRecord(new _p30_modelo(form.formOculto.getValues()));
                    
                    //bloquear descuento
                    var arrDesc = Ext.ComponentQuery.query('[fieldLabel]',_p30_formDescuento);
                    var disabledDesc = false;
                    for(var i=0;i<arrDesc.length;i++)
                    {
                        if(arrDesc[i].getValue()-0!=0)
                        {
                            arrDesc[i].setReadOnly(true);
                            disabledDesc = true;
                        }
                    }
                    _fieldById('_p30_botonAplicarDescuento').setDisabled(disabledDesc);
                    
                    //bloquear comision
                    var arrComi      = Ext.ComponentQuery.query('[fieldLabel]',_fieldById('_p30_formCesion'));
                    var disabledComi = false;
                    for(var i=0;i<arrComi.length;i++)
                    {
                        if(arrComi[i].getValue()-0!=0)
                        {
                            arrComi[i].setReadOnly(true);
                            disabledComi = true;
                        }
                    }
                    _fieldById('_p30_botonAplicarCesion').setDisabled(disabledComi);
                    
                    var gridTarifas=Ext.create('Ext.panel.Panel',
                    {
                        itemId : '_p30_gridTarifas'
                        ,items :
                        [
                            Ext.create('Ext.grid.Panel',
                            {
                                title             : 'Resultados'
                                ,border           : 0
                                ,store            : Ext.create('Ext.data.Store',
                                {
                                    model : '_p30_modeloTarifa'
                                    ,data : json.slist1
                                })
                                ,columns          :
                                [
                                    {
                                        text       : 'FORMA DE PAGO'
                                        ,dataIndex : 'DSPERPAG'
                                        ,flex      : 1
                                    }
                                    ,{
                                        text       : 'PRIMA'
                                        ,dataIndex : 'PRIMA'
                                        ,renderer  : Ext.util.Format.usMoney
                                        ,flex      : 1
                                    }
                                ]
                                ,selType          : 'cellmodel'
                                ,minHeight        : 100
                                ,enableColumnMove : false
                                ,listeners        :
                                {
                                    select : _p30_tarifaSelect
                                }
                            })
                            ,_p30_formDescuento
                            ,Ext.create('Ext.panel.Panel',
                            {
                                defaults : { style : 'margin:5px;' }
                                ,border  : 0
                                ,tbar    :
                                [
                                    '->'
                                    ,{
                                        itemId    : '_p30_botonDetalles'
                                        ,text     : 'Detalles'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/text_list_numbers.png'
                                        ,disabled : true
                                        ,handler  : _p30_detalles
                                    }
                                    ,{
                                        itemId    : '_p30_botonCoberturas'
                                        ,text     : 'Coberturas'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/table.png'
                                        ,disabled : true
                                        ,handler  : _p30_coberturas
                                    }
                                    ,{
                                        itemId   : '_p30_botonEditar'
                                        ,text    : 'Editar'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/pencil.png'
                                        ,handler : _p30_editar
                                    }
                                    ,{
                                        itemId   : '_p30_botonClonar'
                                        ,text    : 'Duplicar'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/control_repeat_blue.png'
                                        ,handler : _p30_clonar
                                    }
                                    ,{
                                        itemId   : '_p30_botonNueva'
                                        ,text    : 'Nueva'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                                        ,handler : _p30_nueva
                                    }
                                ]
                                ,bbar    :
                                [
                                    '->'
                                    ,{
                                        itemId    : '_p30_botonEnviar'
                                        ,xtype    : 'button'
                                        ,text     : 'Enviar'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/email.png'
                                        ,disabled : true
                                        /*,handler  : _p28_enviar*/
                                    }
                                    ,{
                                        itemId    : '_p30_botonImprimir'
                                        ,xtype    : 'button'
                                        ,text     : 'Imprimir'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/printer.png'
                                        ,disabled : true
                                        /*,handler  : _p28_imprimir*/
                                    }
                                    ,{
                                        itemId   : '_p30_botonCesion'
                                        ,xtype   : 'button'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/page_white_star.png'
                                        ,text    : 'Cesi&oacute;n de comisi&oacute;n'
                                        ,handler : _p30_cesionClic
                                    }
                                    ,{
                                        itemId    : '_p30_botonComprar'
                                        ,xtype    : 'button'
                                        ,text     : 'Emitir'
                                        ,icon     : '${ctx}/resources/fam3icons/icons/book_next.png'
                                        ,disabled : true
                                        ,handler  : _p30_comprar
                                    }
                                ]
                            })
                        ]
                    });
                    
                    panelpri.add(gridTarifas);
                    panelpri.doLayout();
                    
                    if(_p30_smap1.cdramo+'x'=='5x'&&arrDesc.length>0)
                    {
                        _p30_formDescuento.setLoading(true);
                        Ext.Ajax.request(
                        {
                            url     : _p30_urlRecuperacionSimple
                            ,params :
                            {
                                'smap1.procedimiento' : 'RECUPERAR_DESCUENTO_RECARGO_RAMO_5'
                                ,'smap1.cdtipsit'     : _p30_smap1.cdtipsit
                                ,'smap1.cdagente'     : _fieldByLabel('AGENTE',_fieldById('_p30_form')).getValue()
                                ,'smap1.negocio'      : _fieldByLabel('NEGOCIO',_fieldById('_p30_form')).getValue()
                                ,'smap1.tipocot'      : _p30_smap1.tipoflot
                                ,'smap1.cdsisrol'     : _p30_smap1.cdsisrol
                                ,'smap1.cdusuari'     : _p30_smap1.cdusuari
                            }
                            ,success : function(response)
                            {
                                _p30_formDescuento.setLoading(false);
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
                                _p30_formDescuento.setLoading(false);
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
                    _p30_bloquear(false);
                    mensajeError(json.respuesta);
                }
            }
            ,failure  : function()
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p30_cotizar');
}

function _p30_nmpolizaChange(me)
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

function _p30_limpiar()
{
    debug('>_p30_limpiar');
    
    _p30_store.removeAll();
    
    _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo=true;
    _fieldById('_p30_form').getForm().reset();
    _fieldById('_p30_form').formOculto.getForm().reset();
    _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo=false;
    
    for(var i in _p30_paneles)
    {
        _p30_paneles[i].valores=_p30_paneles[i].valoresBkp;
    }
    
    if(_p30_smap1.cdramo+'x'=='5x')
    {
        _p30_calculaVigencia();
        _fieldLikeLabel('CLIENTE NUEVO',_fieldById('_p30_form')).setValue('S');    
        
        if(_p30_smap1.cdsisrol=='EJECUTIVOCUENTA')
        {
            var agente = _fieldByLabel('AGENTE',_fieldById('_p30_form'));
            agente.setValue(_p30_smap1.cdagente);
            agente.setReadOnly(true);
            _p30_ramo5AgenteSelect(agente,_p30_smap1.cdagente);
        }
    }
    
    debug('<_p30_limpiar');
}

function _p30_cargarClic()
{
    debug('>_p30_cargarClic');
    
    var panelpri = _fieldById('_p30_panelpri');
    var ck       = '';
    try
    {
        ck='Validando folio';
        var nmpoliza = _fieldByName('nmpoliza',_fieldById('_p30_form')).getValue();
        checkEmpty(nmpoliza,'Introduce un n&uacute;mero v&aacute;lido');
        
        ck='Invocando servicio de recuperacion de cotizacion';
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url     : _p30_urlCargar
            ,params :
            {
                'smap1.cdramo'    : _p30_smap1.cdramo
                ,'smap1.nmpoliza' : nmpoliza
            }
            ,success : function(response)
            {
                panelpri.setLoading(false);
                var ck = '';
                try
                {
                    ck = 'Descodificando respuesta';
                    var json = Ext.decode(response.responseText);
                    debug('### cargar:',json);
                    checkBool(json.exito,json.respuesta);
                    
                    var maestra=json.smap1.ESTADO=='M';
                    _p30_limpiar();
                    if(maestra)
                    {
                        _fieldByName('nmpoliza',_fieldById('_p30_form')).setValue('');
                        mensajeWarning('Se va a duplicar la p&oacute;liza emitida '+json.smap1.NMPOLIZA);
                    }
                    else
                    {
                        _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo=true;
                        _fieldByName('nmpoliza',_fieldById('_p30_form')).setValue(nmpoliza);
                        _fieldByName('nmpoliza',_fieldById('_p30_form')).semaforo=false;
                    }
                    var datosGenerales = new _p30_modelo(json.smap1);
                    var cdtipsitDatos  = datosGenerales.raw['parametros.pv_cdtipsit'];
                    debug('cdtipsitDatos:',cdtipsitDatos);
                    
                    if(_p30_smap1.cdramo+'x'=='5x')
                    {
                        var clienteNuevoName = _p30_tatrisitFullForms[cdtipsitDatos].down('[fieldLabel*=CLIENTE NUEVO]').name;
                        debug('clienteNuevoName:',clienteNuevoName);
                        datosGenerales.set(clienteNuevoName,'S');
                    }
                    
                    ck='Recuperando datos generales';
                    if(_p30_smap1.mapeo=='DIRECTO')
                    {
                        _fieldById('_p30_form').loadRecord(datosGenerales);
                        _fieldById('_p30_form').formOculto.loadRecord(datosGenerales);
                    }
                    else
                    {                            
                        var mapeos = _p30_smap1.mapeo.split('#');
                        debug('mapeos:',mapeos);
                        for(var i in mapeos)
                        {
                            var cdtipsitsMapeo = mapeos[i].split('|')[0];
                            var mapeo          = mapeos[i].split('|')[1];
                            debug('cdtipsitDatos:',cdtipsitDatos,'cdtipsitsMapeo:',cdtipsitsMapeo);
                            if((','+cdtipsitsMapeo+',').lastIndexOf(','+cdtipsitDatos+',')!=-1)
                            {
                                debug('coincidente:',cdtipsitsMapeo,'cdtipsitDatos:',cdtipsitDatos)
                                debug('mapeo:',mapeo);
                                if(mapeo=='DIRECTO')
                                {
                                    debug('directo');
                                    if(_p30_smap1.cdramo+'x'=='5x'&&_p30_smap1.cdsisrol+'x'=='SUSCRIAUTOx')
                                    {
                                        var agenteCmp  = _fieldLikeLabel('AGENTE'  , _fieldById('_p30_form'));
                                        var negocioCmp = _fieldLikeLabel('NEGOCIO' , _fieldById('_p30_form'));
                                        agenteCmp.forceSelection=false;
                                        negocioCmp.forceSelection=false;
                                    }
                                    else if(_p30_smap1.cdramo+'x'=='5x'&&_p30_smap1.cdsisrol+'x'=='PROMOTORAUTOx')
                                    {
                                        var negocioCmp = _fieldLikeLabel('NEGOCIO' , _fieldById('_p30_form'));
                                        negocioCmp.forceSelection=false;
                                    }
                                    _fieldById('_p30_form').loadRecord(datosGenerales);
                                    _fieldById('_p30_form').formOculto.loadRecord(datosGenerales);
                                    if(_p30_smap1.cdramo+'x'=='5x'&&_p30_smap1.cdsisrol+'x'=='SUSCRIAUTOx')
                                    {
                                        var agenteCmp  = _fieldLikeLabel('AGENTE'  , _fieldById('_p30_form'));
                                        agenteCmp.getStore().load(
                                        {
                                            params :
                                            {
                                                'params.agente' : agenteCmp.getValue()
                                            }
                                            ,callback : function()
                                            {
                                                 var agenteCmp  = _fieldLikeLabel('AGENTE' , _fieldById('_p30_form'));
                                                 //agenteCmp.select(agenteCmp.getValue());
                                                 agenteCmp.forceSelection=true;
                                                 var negocioCmp = _fieldLikeLabel('NEGOCIO' , _fieldById('_p30_form'));
                                                 negocioCmp.heredar(true,function(cmp)
                                                 {
                                                     cmp.forceSelection=true;
                                                 });
                                            }
                                        });
                                    }
                                    else if(_p30_smap1.cdramo+'x'=='5x'&&_p30_smap1.cdsisrol+'x'=='PROMOTORAUTOx')
                                    {
                                        var negocioCmp = _fieldLikeLabel('NEGOCIO' , _fieldById('_p30_form'));
                                        negocioCmp.heredar(true,function(cmp)
                                        {
                                            cmp.forceSelection=true;
                                        });
                                    }
                                }
                                else
                                {
                                    var atributos = mapeo.split('@');
                                    debug('atributos:',atributos);
                                    
                                    var recordMapeado = new _p30_modelo();
                                    if(_p30_smap1.cdramo=='5')
                                    {
                                        var clienteNuevoName = _p30_tatrisitFullForms[cdtipsitDatos].down('[fieldLabel*=CLIENTE NUEVO]').name;
                                        debug('clienteNuevoName:',clienteNuevoName);
                                        datosGenerales.set(clienteNuevoName,'S');
                                    }
                                    
                                    for(var i in atributos)
                                    {
                                        var atributoIte = atributos[i];
                                        var modelo      = atributoIte.split(',')[0];
                                        var origen      = atributoIte.split(',')[1];
                                        var pantalla    = atributoIte.split(',')[2];
                                        
                                        modelo   = 'parametros.pv_otvalor'+(('x00'+modelo)  .slice(-2));
                                        pantalla = 'parametros.pv_otvalor'+(('x00'+pantalla).slice(-2));
                                        
                                        debug('modelo:'   , modelo   , '.');
                                        debug('origen:'   , origen   , '.');
                                        debug('pantalla:' , pantalla , '.');
                                        
                                        recordMapeado.set(pantalla,datosGenerales.get(modelo));
                                    }
                                    
                                    debug('recordMapeado:',recordMapeado.data);
                                    if(_p30_smap1.cdramo+'x'=='5x'&&_p30_smap1.cdsisrol+'x'=='SUSCRIAUTOx')
                                    {
                                        var agenteCmp  = _fieldLikeLabel('AGENTE'  , _fieldById('_p30_form'));
                                        var negocioCmp = _fieldLikeLabel('NEGOCIO' , _fieldById('_p30_form'));
                                        agenteCmp.forceSelection=false;
                                        negocioCmp.forceSelection=false;
                                    }
                                    else if(_p30_smap1.cdramo+'x'=='5x'&&_p30_smap1.cdsisrol+'x'=='PROMOTORAUTOx')
                                    {
                                        var negocioCmp = _fieldLikeLabel('NEGOCIO' , _fieldById('_p30_form'));
                                        negocioCmp.forceSelection=false;
                                    }
                                    _fieldById('_p30_form').loadRecord(recordMapeado);
                                    _fieldById('_p30_form').formOculto.loadRecord(recordMapeado);
                                    if(_p30_smap1.cdramo+'x'=='5x'&&_p30_smap1.cdsisrol+'x'=='SUSCRIAUTOx')
                                    {
                                        var agenteCmp  = _fieldLikeLabel('AGENTE'  , _fieldById('_p30_form'));
                                        agenteCmp.getStore().load(
                                        {
                                            params :
                                            {
                                                'params.agente' : agenteCmp.getValue()
                                            }
                                            ,callback : function()
                                            {
                                                 var agenteCmp  = _fieldLikeLabel('AGENTE' , _fieldById('_p30_form'));
                                                 //agenteCmp.select(agenteCmp.getValue());
                                                 agenteCmp.forceSelection=true;
                                                 var negocioCmp = _fieldLikeLabel('NEGOCIO' , _fieldById('_p30_form'));
                                                 negocioCmp.heredar(true,function(cmp)
                                                 {
                                                     cmp.forceSelection=true;
                                                 });
                                            }
                                        });
                                    }
                                    else if(_p30_smap1.cdramo+'x'=='5x'&&_p30_smap1.cdsisrol+'x'=='PROMOTORAUTOx')
                                    {
                                        var negocioCmp = _fieldLikeLabel('NEGOCIO' , _fieldById('_p30_form'));
                                        negocioCmp.heredar(true,function(cmp)
                                        {
                                            cmp.forceSelection=true;
                                        });
                                    }
                                }
                            }
                        }
                    }
                        
                    if(!Ext.isEmpty(json.smap1.CDPERSON))
                    {
                        ck='Recuperando cliente';
                        if(maestra&&false)
                        {
                            _fieldLikeLabel('NOMBRE CLIENTE').setValue('');
                        }
                        else
                        {
                            json.smap1['CLAVECLI']       = json.smap1.CDPERSON;
                            _p30_recordClienteRecuperado = new _p30_modeloRecuperado(json.smap1);
                            
                            debug('_p30_recordClienteRecuperado:',_p30_recordClienteRecuperado);
                            
                            var combcl      = _fieldLikeLabel('CLIENTE NUEVO',_fieldById('_p30_form'));
                            combcl.semaforo = true;
                            combcl.setValue('N');
                            combcl.semaforo = false;
                        }
                    }
                    
                    ck='Recuperando configuracion de incisos';
                    for(var i in json.slist1)
                    {
                        var tconvalsit = json.slist1[i];
                        var cdtipsit   = tconvalsit.CDTIPSIT;
                        _p30_paneles[cdtipsit].valores=tconvalsit;
                    }
                    ck='Recuperando incisos base';
                    for(var i in json.slist2)
                    {
                        _p30_store.add(new _p30_modelo(json.slist2[i]));
                    }
           
                    if(Ext.isEmpty(json.smap1.NTRAMITE))
                    {
                        _p30_cotizar(!maestra);
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
                                        var swExiper = (Ext.isEmpty(json.smap1.CDPERSON) && !Ext.isEmpty(json.smap1.CDIDEPER))? 'N' : 'S' ;
                                        Ext.create('Ext.form.Panel').submit(
                                        {
                                            url             : _p30_urlDatosComplementarios
                                            ,standardSubmit : true
                                            ,params         :
                                            {
                                                'smap1.cdunieco'  : json.smap1.CDUNIECO
                                                ,'smap1.cdramo'   : json.smap1.cdramo
                                                ,'smap1.cdtipsit' : _p30_smap1.cdtipsit
                                                ,'smap1.estado'   : 'W'
                                                ,'smap1.nmpoliza' : json.smap1.nmpoliza
                                                ,'smap1.ntramite' : json.smap1.NTRAMITE
                                                ,'smap1.swexiper' : swExiper
                                                ,'smap1.tipoflot' : json.smap1.TIPOFLOT
                                            }
                                        });
                                    }
                                }
                                ,{
                                    text     : 'Duplicar'
                                    ,handler : function(bot)
                                    {
                                        bot.up('window').destroy();
                                        _fieldByName('nmpoliza',_fieldById('_p30_form')).setValue('');
                                    }
                                }
                            ]
                        }).show());
                    }
                }
                catch(e)
                {
                    manejaException(e,ck);
                }
            }
            ,failure : function()
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck,panelpri);
    }
    
    debug('<_p30_cargarClic');
}

function _p30_tarifaSelect(selModel, record, row, column, eOpts)
{
    var gridTarifas = _fieldById('_p30_gridTarifas').down('grid');
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
        /*_fieldById('_p30_botonImprimir').setDisabled(true);*/
        /*_fieldById('_p30_botonEnviar').setDisabled(true);*/
        _fieldById('_p30_botonDetalles').setDisabled(true);
        _fieldById('_p30_botonCoberturas').setDisabled(true);
        _fieldById('_p30_botonComprar').setDisabled(true);
    }
    else
    {
        // M N P R I M A X
        //0 1 2 3 4 5 6 7
        _p30_selectedTarifa = record;
        debug('_p30_selectedTarifa:',_p30_selectedTarifa);
        
        /*_fieldById('_p30_botonImprimir').setDisabled(false);*/
        /*_fieldById('_p30_botonEnviar').setDisabled(false);*/
        _fieldById('_p30_botonDetalles').setDisabled(false);
        _fieldById('_p30_botonCoberturas').setDisabled(false);
        _fieldById('_p30_botonComprar').setDisabled(false);
    }
}

function _p30_cesionClic()
{
    debug('>_p30_cesionClic');
    _fieldById('_p30_formDescuento').windowCesion.show();
    centrarVentanaInterna(_fieldById('_p30_formDescuento').windowCesion);
    debug('<_p30_cesionClic');
}

function _p30_editar()
{
    debug('>_p30_editar');
    var panelPri    = _fieldById('_p30_panelpri');
    var gridTarifas = _fieldById('_p30_gridTarifas');
    
    _fieldById('_p30_formCesion').destroy();
    panelPri.remove(gridTarifas,true);
    panelPri.doLayout();
    
    _p30_bloquear(false);
    debug('<_p30_editar');
}

function _p30_bloquear(b)
{
    debug('>_p30_bloquear:',b);
    var comps=Ext.ComponentQuery.query('[fieldLabel]',_fieldById('_p30_form'));
    
    for(var i=0;i<comps.length;i++)
    {
        comps[i].setReadOnly(b);
    }
    
    _fieldById('_p30_botonera').setDisabled(b);
    _fieldById('_p30_botonCargar').setDisabled(b);
    
    if(b)
    {
    }
    else
    {
        try {
            _fieldByName('nmpoliza',_fieldById('_p30_form')).focus();
        } catch(e) {
            debug(e);
        }
    }
    
    if(_p30_smap1.cdramo+'x'=='5x'&&_p30_smap1.cdsisrol=='EJECUTIVOCUENTA')
    {
        var agente = _fieldByLabel('AGENTE',_fieldById('_p30_form'));
        agente.setValue(_p30_smap1.cdagente);
        agente.setReadOnly(true);
        _p30_ramo5AgenteSelect(agente,_p30_smap1.cdagente);
    }
    debug('<_p30_bloquear');
}

function _p30_clonar()
{
    debug('>_p30_clonar');
    _fieldById('_p30_form').formOculto.getForm().reset();
    _p30_editar();
    _fieldByName('nmpoliza',_fieldById('_p30_form')).setValue('');
    debug('<_p30_clonar');
}

function _p30_nueva()
{
    debug('>_p30_nueva');
    _p30_editar();
    _p30_limpiar();
    debug('<_p30_nueva');
}

function _p30_detalles()
{
    debug('>_p30_detalles');
    var panelpri = _fieldById('_p30_panelpri');
    panelpri.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p30_urlRecuperacionSimpleLista
        ,params  :
        {
            'smap1.procedimiento' : 'RECUPERAR_DETALLES_COTIZACION_AUTOS_FLOTILLA'
            ,'smap1.cdunieco'     : _p30_smap1.cdunieco
            ,'smap1.cdramo'       : _p30_smap1.cdramo
            ,'smap1.estado'       : 'W'
            ,'smap1.nmpoliza'     : _fieldByName('nmpoliza',_fieldById('_p30_form')).getValue()
            ,'smap1.cdperpag'     : _p30_selectedTarifa.get('CDPERPAG')
        }
        ,success : function(response)
        {
            panelpri.setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('### detalles:',json);
            if(json.exito)
            {
                centrarVentanaInterna(Ext.create('Ext.window.Window',
                {
                    title       : 'Detalles de cotizaci&oacute;n'
                    ,width      : 600
                    ,maxHeight  : 500
                    ,autoScroll : true
                    ,modal      : true
                    ,items      :
                    [
                        Ext.create('Ext.grid.Panel',
                        {
                            store    : Ext.create('Ext.data.Store',
                            {
                                model       : '_p30_modeloDetalleCotizacion'
                                ,groupField : 'TITULO'
                                ,sorters    :
                                [
                                    {
                                        sorterFn : function(o1,o2)
                                        {
                                            debug('sorting:',o1,o2);
                                            if (o1.get('COBERTURA') == o2.get('COBERTURA'))
                                            {
                                                return 0;
                                            }
                                            return o1.get('COBERTURA') < o2.get('COBERTURA') ? -1 : 1;
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
                                    ,dataIndex       : 'COBERTURA'
                                    ,width           : 400
                                    ,summaryType     : 'count'
                                    ,summaryRenderer : function(value)
                                    {
                                        return Ext.String.format('Total de {0} cobertura{1}',value,value !== 1 ? 's': '');
                                    }
                                }
                                ,{
                                    header       : 'Importe por cobertura'
                                    ,dataIndex   : 'PRIMA'
                                    ,width       : 150
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
                                            sum += parseFloat(json.slist1[i].PRIMA);
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
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            panelpri.setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p30_detalles');
}

function _p30_coberturas()
{
    debug('>_p30_coberturas');
    var panelpri = _fieldById('_p30_panelpri');
    panelpri.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p30_urlRecuperacionSimpleLista
        ,params  :
        {
            'smap1.procedimiento' : 'RECUPERAR_DETALLES_COBERTURAS_COTIZACION_AUTOS_FLOTILLA'
            ,'smap1.cdunieco'     : _p30_smap1.cdunieco
            ,'smap1.cdramo'       : _p30_smap1.cdramo
            ,'smap1.estado'       : 'W'
            ,'smap1.nmpoliza'     : _fieldByName('nmpoliza',_fieldById('_p30_form')).getValue()
            ,'smap1.cdperpag'     : _p30_selectedTarifa.get('CDPERPAG')
        }
        ,success : function(response)
        {
            panelpri.setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('### detalles:',json);
            if(json.exito)
            {
                centrarVentanaInterna(Ext.create('Ext.window.Window',
                {
                    title       : 'Coberturas'
                    ,width      : 600
                    ,maxHeight  : 500
                    ,autoScroll : true
                    ,modal      : true
                    ,items      :
                    [
                        Ext.create('Ext.grid.Panel',
                        {
                            store    : Ext.create('Ext.data.Store',
                            {
                                model       : '_p30_modeloCoberturaCotizacion'
                                ,groupField : 'TITULO'
                                ,sorters    :
                                [
                                    {
                                        sorterFn : function(o1,o2)
                                        {
                                            debug('sorting:',o1,o2);
                                            if (o1.get('COBERTURA') == o2.get('COBERTURA'))
                                            {
                                                return 0;
                                            }
                                            return o1.get('COBERTURA') < o2.get('COBERTURA') ? -1 : 1;
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
                                    ,dataIndex       : 'COBERTURA'
                                    ,width           : 400
                                }
                                ,{
                                    header       : 'Suma asegurada'
                                    ,dataIndex   : 'SUMASEG'
                                    ,width       : 150
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
                    ]
                }).show());
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
    debug('<_p30_coberturas');
}

function _p30_comprar()
{
    debug('_p30_comprar');
    var panelPri = _fieldById('_p30_panelpri');
    panelPri.setLoading(true);
    var nombreTitular = '';
    
    Ext.Ajax.request(
    {
        url      : _p30_urlComprar
        ,params  :
        {
            comprarNmpoliza        : _fieldByName('nmpoliza',_fieldById('_p30_form')).getValue()
            ,comprarCdplan         : '*'
            ,comprarCdperpag       : _p30_selectedTarifa.get('CDPERPAG')
            ,comprarCdramo         : _p30_smap1.cdramo
            ,comprarCdciaaguradora : '20'
            ,comprarCdunieco       : _p30_smap1.cdunieco
            ,cdtipsit              : _p30_smap1.cdtipsit
            ,'smap1.fechaInicio'   : Ext.Date.format(_fieldByName('feini').getValue(),'d/m/Y')
            ,'smap1.fechaFin'      : Ext.Date.format(_fieldByName('fefin').getValue(),'d/m/Y')
            ,'smap1.ntramite'      : _p30_smap1.ntramite
            ,'smap1.cdpersonCli'   : Ext.isEmpty(_p30_recordClienteRecuperado) ? '' : _p30_recordClienteRecuperado.raw.CLAVECLI
            ,'smap1.cdideperCli'   : Ext.isEmpty(_p30_recordClienteRecuperado) ? '' : _p30_recordClienteRecuperado.raw.CDIDEPER
            ,'smap1.cdagenteExt'   : _p30_smap1.cdramo+'x'=='5x' ? _fieldByLabel('AGENTE',_fieldById('_p30_form')).getValue() : ''
            ,'smap1.flotilla'      : 'si'
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
                   ,msg     : 'La cotizaci&oacute;n se guard&oacute; para el tr&aacute;mite '
                              + json.smap1.ntramite
                              + '<br/>y no podr&aacute; ser modificada posteriormente'
                   ,buttons : Ext.Msg.OK
                   ,fn      : function()
                   {
                       var swExiper = (!Ext.isEmpty(_p30_recordClienteRecuperado) && Ext.isEmpty(_p30_recordClienteRecuperado.raw.CLAVECLI) && !Ext.isEmpty(_p30_recordClienteRecuperado.raw.CDIDEPER))? 'N' : 'S' ;
                       Ext.create('Ext.form.Panel').submit(
                       {
                           url             : _p30_urlDatosComplementarios
                           ,standardSubmit : true
                           ,params         :
                           {
                               'smap1.cdunieco'  : _p30_smap1.cdunieco
                               ,'smap1.cdramo'   : _p30_smap1.cdramo
                               ,'smap1.cdtipsit' : _p30_smap1.cdtipsit
                               ,'smap1.estado'   : 'W'
                               ,'smap1.nmpoliza' : _fieldByName('nmpoliza',_fieldById('_p30_form')).getValue()
                               ,'smap1.ntramite' : json.smap1.ntramite
                               ,'smap1.swexiper' : swExiper
                               ,'smap1.tipoflot' : _p30_smap1.tipoflot
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

function _p30_numerarIncisos()
{
    var i=1;
    _p30_store.each(function(record)
    {
        record.set('nmsituac',i++);
    });
}

function _p30_editarAuto()
{
    debug('>_p30_editarAuto');
    var record = _p30_selectedRecord;
    debug('_p30_editarAuto:',record.data);
    if(Ext.isEmpty(record.get('cdtipsit')))
    {
        _p30_pedirCdtipsit();
    }
    else
    {
        for(var i in _p30_situaciones)
        {
            _fieldById('_p30_tatrisitParcialForm'+_p30_situaciones[i]).hide();
        }
        if(!_p30_semaforoBorrar)
        {
            var cdtipsit = record.get('cdtipsit');
            var form     = _fieldById('_p30_tatrisitParcialForm'+cdtipsit);
            form.show();
            var combos=Ext.ComponentQuery.query('combo[heredar]',form);
            debug('combos anidados:',combos);
            for(var i in combos)
            {
                combos[i].forceSelection=false;
            }
            form.loadRecord(record);
            heredarPanel(form,true);
            form.items.items[0].focus();
        }
        else
        {
            _p30_semaforoBorrar=false;
        }
    }
}

function _p30_pedirCdtipsit()
{
    debug('>_p30_pedirCdtipsit');
    _p30_editorCdtipsit.reset();
    centrarVentanaInterna(_p30_ventanaCdtipsit.show());
}

function _p30_editarAutoAceptar(bot,callback)
{
    debug('>_p30_editarAutoAceptar');
    var form   = bot.up('form');
    var record = _p30_selectedRecord;
    
    var valido=true;
    
    if(valido)
    {
        valido=form.isValid();
        if(!valido)
        {
            datosIncompletos();
        }
    }
    
    if(valido)
    {
        var values=form.getValues();
        for(var prop in values)
        {
            record.set(prop,values[prop]);
        }
        _fieldById('_p30_grid').getSelectionModel().deselectAll();
        
        if(!Ext.isEmpty(callback))
        {
            callback();
        }
    }
    debug('<_p30_editarAutoAceptar');
}

function _p30_renderer(record,mapeo)
{
    debug('>_p30_renderer',mapeo,record.data);
    
    var label='-situacion-';
    
    if(!Ext.isEmpty(record.get('cdtipsit')))
    {
        label='N/A';
        
        var cdtipsit = record.get('cdtipsit');
        var mapeos   = mapeo.split('#');
        for(var i in mapeos)
        {
            var mapeoIte  = mapeos[i];
            var cdtipsits = mapeoIte.split('|')[0];
            
            if((','+cdtipsits+',').lastIndexOf(','+cdtipsit+',')!=-1)//mapeo correcto
            {
                var name      = mapeoIte.split('|')[1];
                if(!isNaN(name))
                {
                    name='parametros.pv_otvalor'+(('x00'+name).slice(-2));
                }
                var origen = mapeoIte.split('|')[2];
                var valor  = record.get(name);
                debug('name:'   , name   , '.');
                debug('origen:' , origen , '.');
                debug('valor:'  , valor  , '.');
                
                label='';//'-vacio-';
                if(!Ext.isEmpty(valor))
                {
                    if(origen+'x'=='valorx')
                    {
                        label=valor;
                    }
                    else if(origen+'x'=='atributox')
                    {
                        label='';//'-atributo-';
                        var store=_fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[name='+name+']').getStore();
                        if(Ext.isEmpty(store))
                        {
                            debugError('No hay store de atributo:',cdtipsit,name,'.');
                            mensajeError('El atributo mapeado no contiene lista de valores');
                        }
                        else
                        {
                            var index = store.find('key',valor,0,false,false,true);
                            if(index==-1)
                            {
                                label='No encontrado...';
                            }
                            else
                            {
                                label=store.getAt(index).get('value');
                            }
                        }
                    }
                    else
                    {
                        label='Error...';//'-sin store-';
                        var store=Ext.getStore(origen);
                        if(Ext.isEmpty(store))
                        {
                            debugError('No hay store:',origen);
                            mensajeError('No se encuentra la colecci&oacute;n con id "'+origen+'"');
                        }
                        if(!Ext.isEmpty(store)&&store.cargado)
                        {
                            var index = store.find('key',valor,0,false,false,true);
                            if(index==-1)
                            {
                                label='No encontrado...';
                            }
                            else
                            {
                                label=store.getAt(index).get('value');
                            }
                        }
                        else
                        {
                            label = 'Cargando...';
                        }
                    }
                }
            }
        }
    }
    return label;
}

function _p30_editarAutoBuscar()
{
    debug('>_p30_editarAutoBuscar');
    var cdtipsit = _p30_selectedRecord.get('cdtipsit');
    var window   = _p30_tatrisitAutoWindows[cdtipsit];
    var form     = _fieldById('_p30_tatrisitAutoForm'+cdtipsit);
    var record   = _p30_selectedRecord;
    var combos   = Ext.ComponentQuery.query('combo[heredar]',form);
    debug('combos con herencia:',combos);
    for(var i in combos)
    {
        combos[i].forceSelection=false;
    }
    form.loadRecord(record);
    heredarPanel(form,true);
    form.micallback = function(me)
    {
        var record   = _p30_selectedRecord;
        var cdtipsit = record.get('cdtipsit');
        var data     = me.getValues();
        for(var prop in data)
        {
            record.set(prop,data[prop]);
        }
        
        if('|AR|CR|PC|PP|'.lastIndexOf('|'+cdtipsit+'|')!=-1)
        {
            var planVal = record.get('cdplan');
            if(!Ext.isEmpty(planVal))
            {
                var planCmp=_fieldById('_p30_tatrisitParcialForm'+cdtipsit).down('[fieldLabel=PAQUETE]');
                planCmp.heredar(true,function(planCmp)
                {
                    var valido = false;
                    planCmp.getStore().each(function(plan)
                    {
                        if(plan.get('key')==planVal)
                        {
                            valido = true;
                        }
                    });
                    if(!valido)
                    {
                        record.set('cdplan','');
                        mensajeWarning('Debe actualizar el paquete para el inciso');
                        _fieldById('_p30_grid').getSelectionModel().select(record);
                    }
                });
            }
        }
        
        me.up('window').hide();
    };
    if(window.noMostrar==false)
    {
        centrarVentanaInterna(window.show());
    }
    else
    {
        window.noMostrar=false;
    }
    debug('<_p30_editarAutoBuscar');
}

function _p30_tatrisitWindowAutoAceptarClic(bot)
{
    var form = bot.up('form');
    debug('>_p30_tatrisitWindowAutoAceptarClic:',form);
    
    var ck='Validando registro';
    try
    {
        if(!form.isValid())
        {
            throw 'Favor de verificar los datos';
        }
        
        form.micallback(form);
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}
////// funciones //////
</script>
</head>
<body><div id="_p30_divpri" style="height:1000px;"</body>
</html>