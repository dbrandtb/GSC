<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// variables //////
var _p26_urlBuscar             = '<s:url namespace="/endosos" action="buscarHistoricoPolizasGrupoPersona" />';
var _p26_urlObtenerFamilias    = '<s:url namespace="/endosos" action="cargarFamiliasPoliza"               />';
var _p26_urlObtenerIntegrantes = '<s:url namespace="/endosos" action="cargarIntegrantesFamilia"           />';

var _p26_storePolizas;
////// variables //////

Ext.onReady(function()
{
    ////// modelos //////
    Ext.define('_p26_modeloHistorico',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value="imap.historicoFields" /> ]
    });
    
    Ext.define('_p26_modeloFamilia',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value="imap.familiaFields" /> ]
    });
    
    Ext.define('_p26_modeloIntegrante',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value="imap.integranteFields" /> ]
    });
    ////// modelos //////
    
    ////// stores //////
    _p26_storePolizas = Ext.create('Ext.data.Store',
    {
        model : '_p26_modeloHistorico'
    });
    
    _p26_storeFamilias = Ext.create('Ext.data.Store',
    {
        model : '_p26_modeloFamilia'
    });
    
    _p26_storeIntegrantes = Ext.create('Ext.data.Store',
    {
        model : '_p26_modeloIntegrante'
    });
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p26_divpri'
        ,itemId   : '_p26_panelPri'
        ,defaults : { style : 'margin : 5px;' }
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                title        : 'B&uacute;squeda'
                ,itemId      : '_p26_formBusqueda'
                //,collapsible : true
                ,defaults    : { style : 'margin : 5px;' }
                ,border      : 0
                ,items       :
                [
                    {
                        xtype       : 'textfield'
                        ,fieldLabel : 'N&uacute;mero de p&oacute;liza'
                        ,name       : 'nmpoliex'
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : 'RFC'
                        ,name       : 'rfc'
                    }
                    ,{
                        xtype       : 'numberfield'
                        ,fieldLabel : 'Clave de asegurado'
                        ,name       : 'cdperson'
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : 'Nombre'
                        ,name       : 'nombre'
                    }
                ]
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text  : 'Tipo de b&uacute;squeda'
                        ,icon : '${ctx}/resources/fam3icons/icons/cog.png'
                        ,menu :
                        {
                            xtype  : 'menu'
                            ,items :
                            [
                                {
                                    text     : 'Por p&oacute;liza'
                                    ,handler : function(){ _p26_mostrarControlesFiltro('nmpoliex'); }
                                }
                                ,{
                                    text     : 'Por RFC'
                                    ,handler : function(){ _p26_mostrarControlesFiltro('rfc'); }
                                }
                                ,{
                                    text     : 'Por clave de asegurado'
                                    ,handler : function(){ _p26_mostrarControlesFiltro('cdperson'); }
                                }
                                ,{
                                    text     : 'Por nombre'
                                    ,handler : function(){ _p26_mostrarControlesFiltro('nombre'); }
                                }
                            ]
                        }
                    }
                    ,{
                        text     : 'Buscar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                        ,handler : _p26_buscar
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title        : 'Hist&oacute;rico de movimientos'
                ,itemId      : '_p26_gridPolizas'
                ,collapsible : true
                ,selModel    :
                {
                    selType    : 'checkboxmodel'
                    ,mode      : 'SINGLE'
                    ,listeners :
                    {
                        selectionchange : _p26_historicoSelect
                    }
                }
                ,height     : 200
                ,columns    : [ <s:property value="imap.historicoColumns" /> ]
                ,store      : _p26_storePolizas
                ,viewConfig : viewConfigAutoSize
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title        : 'Familias'
                ,itemId      : '_p26_gridFamilias'
                ,collapsible : true
                ,selModel    :
                {
                    selType    : 'checkboxmodel'
                    ,mode      : 'SINGLE'
                    ,listeners :
                    {
                        selectionchange : _p26_familiaSelect
                    }
                }
                ,height     : 200
                ,columns    : [ <s:property value="imap.familiaColumns" /> ]
                ,store      : _p26_storeFamilias
                ,viewConfig : viewConfigAutoSize
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title        : 'Integrantes'
                ,itemId      : '_p26_gridIntegrantes'
                ,collapsible : true
                ,selType     : 'checkboxmodel'
                ,height      : 200
                ,columns     : [ <s:property value="imap.integranteColumns" /> ]
                ,store       : _p26_storeIntegrantes
                ,viewConfig  : viewConfigAutoSize
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    _p26_mostrarControlesFiltro('nmpoliex');
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
function _p26_mostrarControlesFiltro(name)
{
    debug('>_p26_mostrarControlesFiltro name:',name);
    var names = [ 'nmpoliex' , 'rfc' , 'cdperson' , 'nombre' ];
    var form  = _fieldById('_p26_formBusqueda');
    Ext.Array.each(names,function(iname)
    {
        if(iname==name)
        {
            _fieldByName(iname,form).show();
        }
        else
        {
            _fieldByName(iname,form).hide();
        }
    });
    debug('<_p26_mostrarControlesFiltro'); 
}

function _p26_buscar(me)
{
    debug('>_p26_buscar');
    var form = me.up().up();
    
    _p26_navegacion(1);
    
    form.setLoading(true);
    Ext.Ajax.request(
    {
        url       : _p26_urlBuscar
        ,jsonData :
        {
            'smap1' : form.getValues()
        }
        ,success  : function(response)
        {
            form.setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('### buscar response:',json);
            if(json.exito)
            {
                if(json.slist1.length==0)
                {
                    mensajeWarning('Sin resultados');
                }
                else
                {
                    for(var i=0;i<json.slist1.length;i++)
                    {
                        _p26_storePolizas.add(new _p26_modeloHistorico(json.slist1[i]));
                    }
                }
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure  : function()
        {
            form.setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p26_buscar');
}

function _p26_historicoSelect(me,selected)
{
    debug('_p26_historicoSelect selected:',selected);
    if(selected.length>0)
    {
        _p26_navegacion(2);
        
        var record = selected[0];
        Ext.Ajax.request(
        {
            url     : _p26_urlObtenerFamilias
            ,params :
            {
                'smap1.cdunieco'  : record.get('cdunieco')
                ,'smap1.cdramo'   : record.get('cdramo')
                ,'smap1.estado'   : record.get('estado')
                ,'smap1.nmpoliza' : record.get('nmpoliza')
                ,'smap1.nmsuplem' : record.get('nmsuplem')
            }
            ,success : function(response)
            {
                var json=Ext.decode(response.responseText);
                debug('### obtener familias:',json);
                if(json.exito)
                {
                    for(var i=0;i<json.slist1.length;i++)
                    {
                        _p26_storeFamilias.add(new _p26_modeloFamilia(json.slist1[i]));
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
}

function _p26_familiaSelect(me,selected)
{
    debug('_p26_familiaSelect selected:',selected);
    if(selected.length>0)
    {
        _p26_navegacion(3);
    
        var record = selected[0];
        Ext.Ajax.request(
        {
            url     : _p26_urlObtenerIntegrantes
            ,params :
            {
                'smap1.cdunieco'  : record.get('cdunieco')
                ,'smap1.cdramo'   : record.get('cdramo')
                ,'smap1.estado'   : record.get('estado')
                ,'smap1.nmpoliza' : record.get('nmpoliza')
                ,'smap1.nmsuplem' : record.get('nmsuplem')
                ,'smap1.nmsitaux' : record.get('familia')
            }
            ,success : function(response)
            {
                var json=Ext.decode(response.responseText);
                debug('### obtener integrantes:',json);
                if(json.exito)
                {
                    for(var i=0;i<json.slist1.length;i++)
                    {
                        _p26_storeIntegrantes.add(new _p26_modeloIntegrante(json.slist1[i]));
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
}

function _p26_navegacion(nivel)
{
    debug('>_p26_navegacion nivel:',nivel);
    if(nivel==1)
    {
        _p26_storePolizas.removeAll();
        _p26_storeFamilias.removeAll();
        _p26_storeIntegrantes.removeAll();
       
        //_fieldById('_p26_formBusqueda').expand();
        //_fieldById('_p26_gridPolizas').expand();
        //_fieldById('_p26_gridFamilias').collapse();
        //_fieldById('_p26_gridIntegrantes').collapse();
    }
    else if(nivel==2)
    {
        _p26_storeFamilias.removeAll();
        _p26_storeIntegrantes.removeAll();
        
        //_fieldById('_p26_formBusqueda').collapse();
        //_fieldById('_p26_gridPolizas').expand();
        //_fieldById('_p26_gridFamilias').expand();
        //_fieldById('_p26_gridIntegrantes').collapse();
    }
    else if(nivel==3)
    {
        _p26_storeIntegrantes.removeAll();
        
        //_fieldById('_p26_formBusqueda').collapse();
        //_fieldById('_p26_gridPolizas').collapse();
        //_fieldById('_p26_gridFamilias').expand();
        //_fieldById('_p26_gridIntegrantes').expand();
    }
    debug('<_p26_navegacion');
}
////// funciones //////
</script>
</head>
<body><div id="_p26_divpri" style="height:1000px;"></div></body>
</html>