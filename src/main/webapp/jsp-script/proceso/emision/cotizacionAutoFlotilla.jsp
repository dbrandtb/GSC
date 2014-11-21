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
////// urls //////

////// variables //////
var _p30_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p30_smap1:',_p30_smap1);

var _p30_windowAuto = null;
var _p30_store      = null;
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
    { xtype : 'rownumberer' }
];
for(var i=0;i<_p30_gridColsConf.length;i++)
{
    _p30_gridCols.push(_p30_gridColsConf[i]);
}
_p30_gridCols.push(
{
    xtype  : 'actioncolumn'
    ,items :
    [
        {
            tooltip  : 'Configurar plan'
            ,icon    : '${ctx}/resources/fam3icons/icons/cog.png'
            ,handler : _p30_gridBotonConfigClic
        }
        ,{
            tooltip  : 'Eliminar'
            ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
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

var _p30_panel2ItemsConf =
[
    <s:if test='%{getImap().get("panel2Items")!=null}'>
        <s:property value="imap.panel2Items" />
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
                            text  : 'Aceptar'
                            ,icon : '${ctx}/resources/fam3icons/icons/accept.png'
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
////// dinamicos //////

Ext.onReady(function()
{
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
            ,'cdplan','cdtipsit'
        ]
	});
	////// modelos //////
	
	////// stores //////
	_p30_store = Ext.create('Ext.data.Store',
	{
	    model : '_p30_modelo'
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
                                /*change : _p28_nmpolizaChange*/
                            }
                        }
                        ,{
                            xtype    : 'button'
                            ,itemId  : '_p30_botonCargar'
                            ,text    : 'BUSCAR'
                            ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                            /*,handler : _p28_cargar*/
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
    }
    ,{
        xtype       : 'datefield'
        ,name       : 'fefin'
        ,fieldLabel : 'FIN DE VIGENCIA'
        ,value      : Ext.Date.add(new Date(),Ext.Date.YEAR,1)
        ,minValue   : Ext.Date.add(new Date(),Ext.Date.DAY,1)
        ,style      : 'margin:5px;'
    });
	////// componentes //////
	
	////// contenido //////
	Ext.create('Ext.panel.Panel',
	{
	    renderTo  : '_p30_divpri'
	    ,border   : 0
	    ,defaults : { style : 'margin:5px;' }
	    ,items    :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	            itemId    : '_p30_form'
	            ,title    : 'DATOS GENERALES'
	            ,defaults : { style : 'margin:5px;' }
	            ,layout   :
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
	            ,columns    : _p30_gridCols
	            ,height     : 350
	            ,viewConfig : viewConfigAutoSize
	            ,store      : _p30_store
	            ,plugins    : Ext.create('Ext.grid.plugin.RowEditing',
	            {
	                clicksToEdit  : 1
	                ,errorSummary : false
                })
	        })
	    ]
	});
	
	_p30_windowAuto = Ext.create('Ext.window.Window',
	{
	    title        : 'B&Uacute;SQUEDA DE VEH&Iacute;CULO'
	    ,closeAction : 'hide'
	    ,modal       : true
	    ,items       :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	            itemId    : '_p30_formAuto'
	            ,border   : 0
	            ,defaults : { style : 'margin:5px;' }
	            ,items    : _p30_panel2ItemsConf
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text  : 'Aceptar'
                        ,icon : '${ctx}/resources/fam3icons/icons/accept.png'
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
	
	centrarVentanaInterna(_p30_windowAuto.show());
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
    
    var itemVigencia=_fieldByLabel('VIGENCIA');
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
    debug('>_p30_configuracionPanelDinClic:',cdtipsit);
    _p30_paneles[cdtipsit].title=titulo;
    centrarVentanaInterna(_p30_paneles[cdtipsit].show());
    debug('<_p30_configuracionPanelDinClic');
}

function _p30_agregarAuto()
{
    debug('>_p30_agregarAuto');
    _p30_store.add(new _p30_modelo());
    debug('<_p30_agregarAuto');
}

function _p30_gridBotonConfigClic(view,row,col,item,e,record)
{
    debug('>_p30_gridBotonConfigClic:',record);
    debug('<_p30_gridBotonConfigClic');
}

function _p30_gridBotonEliminarClic(view,row,col,item,e,record)
{
    debug('>_p30_gridBotonEliminarClic:',record);
    debug('<_p30_gridBotonEliminarClic');
}
////// funciones //////
</script>
</head>
<body><div id="_p30_divpri" style="height:1000px;"</body>
</html>