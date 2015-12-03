<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
#canvasdiv
{
    width    : 2000px;
    height   : 2000px;
    position : absolute;
}
.catEntidad
{
    border : 0px solid red;
}
.estado .image
{
    width            : 50px;
    height           : 50px;
    background-image : url('${flujoimg}estado.png');
}
.estado .cdestado, .labelE
{
    position    : absolute;
    left        : 50%;
    margin-left : -100px;
    border      : 0px solid red;
    top         : 20px;
    width       : 200px;
    text-align  : center;
}
.pantalla .image
{
    width            : 45px;
    height           : 60px;
    background-image : url('${flujoimg}pantalla.png');
}
.pantalla .cdpantalla, .labelP
{
    position    : absolute;
    left        : 50%;
    margin-left : -100px;
    border      : 0px solid red;
    top         : 25px;
    width       : 200px;
    text-align  : center;
}
.componente .image
{
    width            : 60px;
    height           : 40px;
    background-image : url('${flujoimg}componente.png');
}
.componente .cdcomponente, .labelC
{
    position    : absolute;
    left        : 50%;
    margin-left : -100px;
    border      : 0px solid red;
    top         : 15px;
    width       : 200px;
    text-align  : center;
}
.entidad:hover
{
    border : 0px solid blue;
}
.entidadE
{
    position         : absolute;
    width            : 50px;
    height           : 50px;
    border           : 0px solid red;
    background-image : url('${flujoimg}estado.png');
}
.entidadP
{
    position         : absolute;
    width            : 45px;
    height           : 60px;
    border           : 0px solid red;
    background-image : url('${flujoimg}pantalla.png');
}
.entidadC
{
    position         : absolute;
    width            : 60px;
    height           : 40px;
    border           : 0px solid red;
    background-image : url('${flujoimg}componente.png');
}
.plus
{
    position         : absolute;
    top              : 0;
    left             : 0;
    margin-top       : -32px;
    margin-left      : -16px;
    width            : 16px;
    height           : 16px;
    border           : 0px solid green;
    background-image : url('${icons}add.png')
}
.edit
{
    position         : absolute;
    top              : 0;
    left             : 50%;
    margin-top       : -32px;
    margin-left      : -8px;
    width            : 16px;
    height           : 16px;
    border           : 0px solid green;
    background-image : url('${icons}pencil.png')
}
.remove
{
    position         : absolute;
    top              : 0;
    right            : 0;
    margin-top       : -32px;
    margin-right     : -16px;
    width            : 16px;
    height           : 16px;
    border           : 0px solid green;
    background-image : url('${icons}delete.png')
}
</style>
<script type="text/javascript" src="${ctx}/resources/jsPlumb/jsPlumb-2.0.4.js?${now}"></script>
<script>
////// iframe //////
if(inIframe())
{
    try
    {
        window.top.location = window.location;
    }
    catch(e)
    {
        alert('Error');
        window.location='error';
    }
}
////// iframe //////

////// urls //////
////// urls //////

////// variables //////
var estadoTpl;
var pantallaTpl;

var _p52_panelGrids;
var _p52_gridTramites;
var _p52_gridProcesos;
var _p52_panelDibujo;
var _p52_panelCanvas;
var _p52_catalogoEstados;
var _p52_panelEstado;
var _p52_catalogoPantallas;
var _p52_catalogoComponentes;

var toolkit;

var epProps = [];
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// requires //////
    ////// requires //////
    
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    estadoTpl = new Ext.Template(
    [
         '<div id="E{cdestado}" class="catEntidad estado" draggable="true" ondragstart="_p52_dragstart(event);" descrip="{dsestado}">'
        ,'    <table width="90" border="0">'
        ,'        <tr>'
        ,'            <td align="center"><div class="image"></div><div class="cdestado">{cdestado}</div></td>'
        ,'        </tr>'
        ,'        <tr>'
        ,'            <td align="center">{dsestado}</td>'
        ,'        </tr>'
        ,'    </table>'
        ,'</div>'
    ]);
    
    pantallaTpl = new Ext.Template(
    [
         '<div id="P{cdpantalla}" class="catEntidad pantalla" draggable="true" ondragstart="_p52_dragstart(event);" descrip="{dspantalla}">'
        ,'    <table width="90" border="0">'
        ,'        <tr>'
        ,'            <td align="center"><div class="image"></div><div class="cdpantalla">{cdpantalla}</div></td>'
        ,'        </tr>'
        ,'        <tr>'
        ,'            <td align="center">{dspantalla}</td>'
        ,'        </tr>'
        ,'    </table>'
        ,'</div>'
    ]);
    
    componenteTpl = new Ext.Template(
    [
         '<div id="C{cdcomponente}" class="catEntidad componente" draggable="true" ondragstart="_p52_dragstart(event);" descrip="{dscomponente}">'
        ,'    <table width="90" border="0">'
        ,'        <tr>'
        ,'            <td align="center"><div class="image"></div><div class="cdcomponente">{cdcomponente}</div></td>'
        ,'        </tr>'
        ,'        <tr>'
        ,'            <td align="center">{dscomponente}</td>'
        ,'        </tr>'
        ,'    </table>'
        ,'</div>'
    ]);
    
    epProps['E'] =
    {
        anchor     : [ 'Perimeter' , { shape : 'Circle' } ]
        ,isSource  : true
        ,isTarget  : true
    };
    
    epProps['P'] =
    {
        anchor     : [ 'Perimeter' , { shape : 'Rectangle' } ]
        ,isSource  : true
        ,isTarget  : true
    };
    
    epProps['C'] =
    {
        anchor     : [ 'Perimeter' , { shape : 'Rectangle' } ]
        ,isSource  : true
        ,isTarget  : true
    };
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        renderTo : '_p52_divpri'
        ,itemId  : '_p52_panelpri'
        ,title   : 'C O N F I G U R A D O R&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;D E&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;P R O C E S O S'
        ,height  : 700
        ,border  : 0
        ,layout  : 'border'
        ,header  :
        {
            titleAlign     : 'center'
            ,titlePosition : 1
            ,items         :
            [
                {
                    xtype    : 'button'
                    ,text    : 'Regresar'
                    ,icon    : '${icons}arrow_left.png'
                    ,handler : function(){ history.back(); }
                }
                ,{
                    xtype    : 'button'
                    ,text    : 'Recargar'
                    ,icon    : '${icons}control_repeat_blue.png'
                    ,handler : function(){ location.reload(); }
                }
            ]
        }
        ,items      :
        [
            Ext.create('Ext.panel.Panel',
            {
                itemId  : '_p52_panelGrids'
                ,region : 'north'
                ,layout : 'border'
                ,style  : 'margin-top : 5px;'
                ,height : 200
                ,split  : true
                ,items  :
                [
                    Ext.create('Ext.grid.Panel',
                    {
                        itemId       : '_p52_gridTramites'
                        ,title       : 'TR\u00C1MITES'
                        ,region      : 'west'
                        ,width       : 400
                        ,split       : true
                        ,header      :
                        {
                            titlePosition : 0
                            ,items        :
                            [{
                                xtype : 'button'
                                ,text : 'Agregar'
                                ,icon : '${icons}add.png'
                            }]
                        }
                        ,hideHeaders : true
                        ,columns     :
                        [
                            {
                                dataIndex : 'DSTIPFLU'
                                ,flex     : 1
                            }
                            ,{
                                xtype    : 'actioncolumn'
                                ,width   : 30
                                ,icon    : '${icons}pencil.png'
                                ,tooltip : 'Editar'
                            }
                        ]
                        ,store : Ext.create('Ext.data.Store',
                        {
                            autoLoad : true
                            ,fields  : [ 'DSTIPFLU' ]
                            ,proxy   :
                            {
                                type    : 'memory'
                                ,reader : 'json'
                                ,data   :
                                [
                                    { DSTIPFLU : '-poliza nueva' }
                                    ,{ DSTIPFLU : '-cambio de contratante' }
                                    ,{ DSTIPFLU : '-otro' }
                                    ,{ DSTIPFLU : '-otro' }
                                    ,{ DSTIPFLU : '-otro' }
                                    ,{ DSTIPFLU : '-otro' }
                                    ,{ DSTIPFLU : '-otro' }
                                    ,{ DSTIPFLU : '-otro' }
                                    ,{ DSTIPFLU : '-otro' }
                                    ,{ DSTIPFLU : '-otro' }
                                    ,{ DSTIPFLU : '-otro' }
                                    ,{ DSTIPFLU : '-otro' }
                                    ,{ DSTIPFLU : '-otro' }
                                ]
                            }
                        })
                        ,listeners :
                        {
                            select : function(me,record)
                            {
                                _p52_gridProcesos.store.removeAll();
                                _p52_gridProcesos.store.add({ DSFLUJOMC : '-emision salud individual' },{ DSFLUJOMC : '-otro '+(new Date().getTime()) });
                            }
                        }
                    })
                    ,Ext.create('Ext.grid.Panel',
                    {
                        itemId       : '_p52_gridProcesos'
                        ,title       : 'PROCESOS'
                        ,region      : 'center'
                        ,header      :
                        {
                            titlePosition : 0
                            ,items        :
                            [{
                                xtype : 'button'
                                ,text : 'Agregar'
                                ,icon : '${icons}add.png'
                            }]
                        }
                        ,hideHeaders : true
                        ,columns     :
                        [
                            {
                                dataIndex : 'DSFLUJOMC'
                                ,flex     : 1
                            }
                            ,{
                                xtype    : 'actioncolumn'
                                ,width   : 30
                                ,icon    : '${icons}pencil.png'
                                ,tooltip : 'Editar'
                                ,handler : function(view,row,col,item,e,record)
                                {
                                    _p52_navega(2);
                                }
                            }
                        ]
                        ,store : Ext.create('Ext.data.Store',
                        {
                            autoLoad : true
                            ,fields  : [ 'DSFLUJOMC' ]
                            ,proxy   :
                            {
                                type    : 'memory'
                                ,reader : 'json'
                                ,data   : []
                            }
                        })
                    })
                ]
            })
            ,Ext.create('Ext.panel.Panel',
            {
                itemId      : '_p52_panelDibujo'
                ,title      : 'PROCESO DE PRUEBA'
                ,titleAlign : 'center'
                ,region     : 'center'
                ,hidden     : true
                ,layout     : 'border'
                ,border     : 0
                ,tools      :
                [{
                    type     : 'gear'
                    ,tooltip : 'Editar'
                    ,handler : function(){ alert('editar proceso'); }
                }]
                ,items :
                [
                    Ext.create('Ext.panel.Panel',
                    {
                        itemId  : '_p52_accordion'
                        ,width  : 250
                        ,region : 'west'
                        ,style  : 'margin-right:5px;'
                        ,layout :
                        {
                            type           : 'accordion'
                            ,titleCollapse : true
                            ,animate       : true
                        }
                        ,items :
                        [
                            {
                                title       : 'STATUS'
                                ,itemId     : '_p52_catalogoEstados'
                                ,defaults   : { style : 'margin : 5px;' }
                                ,autoScroll : true
                                ,layout     :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                    ,tdAttrs : { valign : 'top' }
                                }
                            }
                            ,{
                                title       : 'PANTALLAS'
                                ,itemId     : '_p52_catalogoPantallas'
                                ,defaults   : { style : 'margin : 5px;' }
                                ,autoScroll : true
                                ,layout     :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                    ,tdAttrs : { valign : 'top' }
                                }
                            }
                            ,{
                                title       : 'COMPONENTES'
                                ,itemId     : '_p52_catalogoComponentes'
                                ,defaults   : { style : 'margin : 5px;' }
                                ,autoScroll : true
                                ,layout     :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                    ,tdAttrs : { valign : 'top' }
                                }
                            }
                            ,{
                                title : 'PROCESOS'
                            }
                            ,{
                                title : 'VALIDACIONES'
                            }
                            ,{
                                title : 'REVISIONES'
                            }
                        ]
                    })
                    ,Ext.create('Ext.panel.Panel',
                    {
                        itemId      : '_p52_propForm'
                        ,width      : 300
                        ,style      : 'margin-left:5px;'
                        ,region     : 'east'
                        ,autoScroll : true
                        ,items      :
                        [
                            Ext.create('Ext.panel.Panel',
                            {
                                itemId       : '_p52_panelEstado'
                                ,title       : 'STATUS'
                                ,hidden      : true
                                ,border      : 0
                                ,buttonAlign : 'center'
                                ,buttons     :
                                [
                                    {
                                        text     : 'Guardar'
                                        ,icon    : '${icons}disk.png'
                                        ,handler : function(me)
                                        {
                                            _p52_panelCanvas.enable();
                                            me.up('panel').hide();
                                        }
                                    }
                                    ,{
                                        text     : 'Cancelar'
                                        ,icon    : '${icons}cancel.png'
                                        ,handler : function(me)
                                        {
                                            _p52_panelCanvas.enable();
                                            me.up('panel').hide();
                                        }
                                    }
                                ]
                                ,items       :
                                [
                                    {
                                        xtype  : 'fieldset'
                                        ,items :
                                        [
                                            {
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo m\u00e1ximo en horas'
                                                ,minValue   : 0
                                                ,maxValue   : 40
                                                ,value      : 0
                                                ,increment  : 1
                                                ,name       : 'TIMEMAXH'
                                                ,labelAlign : 'top'
                                            }
                                            ,{
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo m\u00e1ximo en minutos'
                                                ,minValue   : 30
                                                ,maxValue   : 60
                                                ,value      : 30
                                                ,increment  : 5
                                                ,name       : 'TIMEMAXM'
                                                ,labelAlign : 'top'
                                            }
                                            ,{
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo primer alerta en horas'
                                                ,minValue   : 0
                                                ,maxValue   : 40
                                                ,value      : 0
                                                ,increment  : 1
                                                ,name       : 'TIMEWRN1H'
                                                ,labelAlign : 'top'
                                            }
                                            ,{
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo primer alerta en minutos'
                                                ,minValue   : 30
                                                ,maxValue   : 60
                                                ,value      : 0
                                                ,increment  : 5
                                                ,name       : 'TIMEWRN1M'
                                                ,labelAlign : 'top'
                                            }
                                            ,{
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo segunda alerta en horas'
                                                ,minValue   : 0
                                                ,maxValue   : 40
                                                ,value      : 0
                                                ,increment  : 1
                                                ,name       : 'TIMEWRN2H'
                                                ,labelAlign : 'top'
                                            }
                                            ,{
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo segunda alerta en minutos'
                                                ,minValue   : 30
                                                ,maxValue   : 60
                                                ,value      : 0
                                                ,increment  : 5
                                                ,name       : 'TIMEWRN2M'
                                                ,labelAlign : 'top'
                                            }
                                            ,{
                                                xtype       : 'checkbox'
                                                ,boxLabel   : 'Origen por escalamiento'
                                                ,name       : 'SWESCALA'
                                                ,inputValue : 'S'
                                            }
                                            ,{
                                                xtype        : 'fieldcontainer'
                                                ,fieldLabel  : 'Tipo asignaci\u00f3n'
                                                ,labelAlign  : 'top'
                                                ,defaultType : 'radiofield'
                                                ,defaults    : { flex : 1 }
                                                ,layout      : 'hbox'
                                                ,items       :
                                                [
                                                    {
                                                        boxLabel    : 'Simple'
                                                        ,name       : 'CDTIPASIG'
                                                        ,inputValue : 1
                                                        ,checked    : true
                                                    }
                                                    ,{
                                                        boxLabel    : 'Carrusel'
                                                        ,name       : 'CDTIPASIG'
                                                        ,inputValue : 3
                                                    }
                                                    ,{
                                                        boxLabel    : 'Carga'
                                                        ,name       : 'CDTIPASIG'
                                                        ,inputValue : 'xl'
                                                    }
                                                ]
                                            }
                                        ]
                                    }
                                    ,Ext.create('Ext.grid.Panel',
                                    {
                                        itemId   : '_p52_gridEstRol'
                                        ,title   : 'PERMISOS'
                                        ,height  : 220
                                        ,columns :
                                        [
                                            {
                                                text       : 'ROL'
                                                ,dataIndex : 'DSSISROL'
                                                ,width     : 120
                                            }
                                            ,{
                                                text       : 'VER'
                                                ,xtype     : 'checkcolumn'
                                                ,dataIndex : 'SWVER'
                                                ,width     : 55
                                            }
                                            ,{
                                                text       : 'DEF.'
                                                ,xtype     : 'checkcolumn'
                                                ,dataIndex : 'SWDEFAULT'
                                                ,width     : 55
                                            }
                                            ,{
                                                text       : 'COMPR.'
                                                ,xtype     : 'checkcolumn'
                                                ,dataIndex : 'SWCOMPRA'
                                                ,width     : 55
                                            }
                                            ,{
                                                text       : 'REASI.'
                                                ,xtype     : 'checkcolumn'
                                                ,dataIndex : 'SWREASIG'
                                                ,width     : 55
                                            }
                                        ]
                                        ,store : Ext.create('Ext.data.Store',
                                        {
                                            autoLoad : true
                                            ,fields  :
                                            [
                                                'CDSISROL'
                                                ,'DSSISROL'
                                                ,{ name : 'SWVER'     , type : 'boolean' }
                                                ,{ name : 'SWDEFAULT' , type : 'boolean' }
                                                ,{ name : 'SWCOMPRA'  , type : 'boolean' }
                                                ,{ name : 'SWREASIG'  , type : 'boolean' }
                                                ,'CDROLASIG'
                                            ]
                                            ,proxy   :
                                            {
                                                type    : 'memory'
                                                ,reader : 'json'
                                                ,data   :
                                                [
                                                    {
                                                        DSSISROL : 'AGENTE'
                                                    }
                                                    ,{
                                                        DSSISROL : 'SUSCRIPTOR'
                                                    }
                                                    ,{
                                                        DSSISROL : 'MESA DE CONTROL'
                                                    }
                                                    ,{
                                                        DSSISROL : 'SUPERVISOR'
                                                    }
                                                    ,{
                                                        DSSISROL : 'SUSCRIPTOR AUTO'
                                                    }
                                                    ,{
                                                        DSSISROL : 'PROMOTOR'
                                                    }
                                                ]
                                            }
                                        })
                                    })
                                    ,Ext.create('Ext.grid.Panel',
                                    {
                                        itemId   : '_p52_gridEstAvi'
                                        ,title   : 'AVISOS'
                                        ,height  : 180
                                        ,plugins :
                                        [
                                            Ext.create('Ext.grid.plugin.CellEditing',
                                            {
                                                clicksToEdit: 1
                                            })
                                        ]
                                        ,tools :
                                        [{
                                            type      : 'plus'
                                            ,tooltip  : 'Agregar'
                                            ,callback : function(panel)
                                            {
                                                debug('panel.',panel);
                                                panel.store.add({});
                                            }
                                        }]
                                        ,columns :
                                        [
                                            {
                                                text       : 'CORREO'
                                                ,dataIndex : 'DSMAILAVI'
                                                ,flex      : 1
                                                ,editor    : 'textfield'
                                            }
                                        ]
                                        ,store : Ext.create('Ext.data.Store',
                                        {
                                            autoLoad : true
                                            ,fields  :
                                            [
                                                'CDAVISO'
                                                ,'CDTIPAVI'
                                                ,'DSCOMENT'
                                                ,'SWAUTOAVI'
                                                ,'DSMAILAVI'
                                                ,'CDUSUARIAVI'
                                                ,'CDSISROLAVI'
                                            ]
                                            ,proxy   :
                                            {
                                                type    : 'memory'
                                                ,reader : 'json'
                                                ,data   :
                                                [
                                                ]
                                            }
                                        })
                                    })
                                ]
                            })
                        ]
                    })
                    ,Ext.create('Ext.panel.Panel',
                    {
                        itemId      : '_p52_panelCanvas'
                        ,region     : 'center'
                        ,autoScroll : true
                        ,html       : '<div id="canvasdiv" ondragover="event.preventDefault();" ondrop="_p52_drop(event);"></div>'
                    })
                ]
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text  : 'Guardar'
                        ,icon : '${icons}disk.png'
                    }
                    ,{
                        text     : 'Guardar y regresar'
                        ,icon    : '${icons}disk.png'
                        ,handler : function(me)
                        {
                            _p52_navega(1);
                        }
                    }
                    ,{
                        text  : 'Salir sin guardar'
                        ,icon : '${icons}cancel.png'
                        ,handler : function(me)
                        {
                            _p52_navega(1);
                        }
                    }
                ]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    _p52_panelGrids          = _fieldById('_p52_panelGrids');
    _p52_gridTramites        = _fieldById('_p52_gridTramites');
    _p52_gridProcesos        = _fieldById('_p52_gridProcesos');
    _p52_panelDibujo         = _fieldById('_p52_panelDibujo');
    _p52_catalogoEstados     = _fieldById('_p52_catalogoEstados');
    _p52_panelCanvas         = _fieldById('_p52_panelCanvas');
    _p52_panelEstado         = _fieldById('_p52_panelEstado');
    _p52_catalogoPantallas   = _fieldById('_p52_catalogoPantallas');
    _p52_catalogoComponentes = _fieldById('_p52_catalogoComponentes');
    ////// custom //////
    
    ////// loaders //////
    _p52_cargarEstados();
    _p52_cargarPantallas();
    _p52_cargarComponentes();
    
    _p52_navega(2);
    
    jsPlumb.ready(function()
    {
        
        toolkit = jsPlumb.getInstance(
        {
            Container            : 'canvasdiv'
            ,Endpoint            : ['Dot',{radius:7}]
            ,ConnectionOverlays  : [ [ 'PlainArrow' , { location : 1 } ] ]
            ,Connector           : 'StateMachine'
            ,ReattachConnections : false
        });
    });
    ////// loaders //////
});

////// funciones //////

//funcion para revisar si estas en un iframe
//http://stackoverflow.com/questions/326069/how-to-identify-if-a-webpage-is-being-loaded-inside-an-iframe-or-directly-into-t
function inIframe () {
    try {
        return window.self !== window.top;
    } catch (e) {
        return true;
    }
}

function _p52_navega(nivel)
{
    if(nivel==1)
    {
        _p52_panelGrids.show();
        _p52_panelDibujo.hide();
    }
    else if(nivel==2)
    {
        _p52_panelGrids.hide();
        _p52_panelDibujo.show();
    }
}

function _p52_cargarEstados()
{
    debug('_p52_cargarEstados');
    _p52_catalogoEstados.removeAll();
    _p52_catalogoEstados.add(
    {
        xtype   : 'panel'
        ,tpl    : estadoTpl
        ,border : 0
        ,data   :
        {
            cdestado  : 100
            ,dsestado : 'Nuevo'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : estadoTpl
        ,border : 0
        ,data   :
        {
            cdestado  : 101
            ,dsestado : 'En revisión por el supervisor'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : estadoTpl
        ,border : 0
        ,data   :
        {
            cdestado  : 999
            ,dsestado : 'Confirmado'
        }
    }
    );
}

function _p52_cargarPantallas()
{
    debug('_p52_cargarPantallas');
    _p52_catalogoPantallas.removeAll();
    _p52_catalogoPantallas.add(
    {
        xtype   : 'panel'
        ,tpl    : pantallaTpl
        ,border : 0
        ,data   :
        {
            cdpantalla  : 45
            ,dspantalla : 'Datos complementarios'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : pantallaTpl
        ,border : 0
        ,data   :
        {
            cdpantalla  : 86
            ,dspantalla : 'Cotizacion auto individual'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : pantallaTpl
        ,border : 0
        ,data   :
        {
            cdpantalla  : 102
            ,dspantalla : 'Cotizacion salud individual'
        }
    }
    );
}

function _p52_cargarComponentes()
{
    debug('_p52_cargarComponentes');
    _p52_catalogoComponentes.removeAll();
    _p52_catalogoComponentes.add(
    {
        xtype   : 'panel'
        ,tpl    : componenteTpl
        ,border : 0
        ,data   :
        {
            cdcomponente  : 1
            ,dscomponente : 'Ventana de documentos'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : componenteTpl
        ,border : 0
        ,data   :
        {
            cdcomponente  : 2
            ,dscomponente : 'Ventana de historial'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : componenteTpl
        ,border : 0
        ,data   :
        {
            cdcomponente  : 3
            ,dscomponente : 'Vista previa tarifa'
        }
    }
    );
}

function _p52_dragstart(event)
{
    debug('_p52_dragstart event:',event);
    event.dataTransfer.setData('clave'   , event.target.id);
    event.dataTransfer.setData('descrip' , $('#'+event.target.id).attr('descrip'));
    debug('getData:',event.dataTransfer.getData("clave"),event.dataTransfer.getData("descrip"));
}

function _p52_drop(event)
{
    debug('_p52_drop event:',event);
    
    var catId   = event.dataTransfer.getData("clave");
    var descrip = event.dataTransfer.getData("descrip");
    var tipo    = catId.substr(0,1);
    var clave   = catId.substr(1);
    var id      = _p52_generaId();
    var x       = event.layerX;
    var y       = event.layerY;
    
    if(tipo=='E')
    {
        $('#canvasdiv').append('<div id="'+id+'" class="entidad entidad'+tipo+'" style="top:'+y+'px;left:'+x+'px;" title="'+descrip+'"><a href="#" onclick="_p52_addEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="plus"></a><a href="#" onclick="_p52_editEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="edit"></a><a class="remove" href="#" onclick="_p52_removeEndpoint(\''+id+'\');return false;"></a><div class="labelE">'+clave+' - '+descrip+'</div></div>');
    }
    else if(tipo=='P')
    {
        $('#canvasdiv').append('<div id="'+id+'" class="entidad entidad'+tipo+'" style="top:'+y+'px;left:'+x+'px;" title="'+descrip+'"><a href="#" onclick="_p52_addEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="plus"></a><a class="remove" href="#" onclick="_p52_removeEndpoint(\''+id+'\');return false;"></a><div class="labelP">'+clave+' - '+descrip+'</div></div>');
    }
    else if(tipo=='C')
    {
        $('#canvasdiv').append('<div id="'+id+'" class="entidad entidad'+tipo+'" style="top:'+y+'px;left:'+x+'px;" title="'+descrip+'"><a href="#" onclick="_p52_addEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="plus"></a><a class="remove" href="#" onclick="_p52_removeEndpoint(\''+id+'\');return false;"></a><div class="labelC">'+clave+' - '+descrip+'</div></div>');
    }
    
    _p52_addEndpoint(id,tipo);
}

function _p52_generaId()
{
    return (new Date()).getTime()+'_'+(Math.floor(Math.random()*10000));
}

function _p52_addEndpoint(id,tipo)
{
    debug('_p52_addEndpoint id,tipo:',id,tipo);
    toolkit.addEndpoint(id,epProps[tipo]);
    toolkit.draggable(id);
}

function _p52_editEndpoint(id,tipo)
{
    debug('_p52_editEndpoint id,tipo:',id,tipo);
    if(tipo=='E')
    {
        _p52_panelCanvas.disable();
        _p52_panelEstado.show();
    }
}

function _p52_removeEndpoint(id)
{
    debug('_p52_removeEndpoint id:',id);
    toolkit.remove(id);
}
////// funciones //////

</script>
</head>
<body>
<div id="_p52_divpri" style="height:710px;border:1px solid #CCCCCC;"></div>
</body>
</html>