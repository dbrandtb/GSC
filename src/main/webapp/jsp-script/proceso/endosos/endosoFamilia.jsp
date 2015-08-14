<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p48_urlRecuperacion             = '<s:url namespace="/recuperacion" action="recuperar"                         />';
var _p48_urlRecuperarItemsFormulario = '<s:url namespace="/endosos"      action="recuperarComponentesAltaAsegurado" />';
////// urls //////

////// variables //////
var _p48_params = <s:property value="%{convertToJSON('params')}" escapeHtml="false" />;
debug('_p48_params:',_p48_params);

var _p48_store;
var _p48_storeMov;
var _p48_nfamilia = 0;
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var _p48_itemsPoliza  = [ <s:property value="items.itemsPoliza"  escapeHtml="false" /> ];
var _p48_fieldsInciso = [ <s:property value="items.fieldsInciso" escapeHtml="false" /> ];
var _p48_colsInciso   = [ <s:property value="items.colsInciso"   escapeHtml="false" /> ];
var _p48_colsMovimi   = [
                            {
                                xtype    : 'actioncolumn'
                                ,width   : 50
                                ,items   :
                                [
                                    {
                                        icon     : '${icons}arrow_undo.png'
                                        ,tooltip : 'Deshacer'
                                        ,handler : _p48_deshacerMov
                                    }
                                    ,{
                                        icon     : '${icons}pencil.png'
                                        ,tooltip : 'Editar'
                                        ,handler : _p48_editarAsegurado
                                    }
                                ]
                            }
                            ,{
                                text       : 'MOV.'
                                ,width     : 70
                                ,dataIndex : 'MOV'
                                ,renderer  : function(v)
                                {
                                    if(v=='-')
                                    {
                                        return '<span style="font-size:10px;">Quitar</span>';
                                    }
                                    if(v=='+')
                                    {
                                        return '<span style="font-size:10px;">Agregar</span>';
                                    }
                                    return v;
                                }
                            }
                            ,<s:property value="items.colsInciso"   escapeHtml="false" />
                        ];
var _p48_itemsEndoso = [ <s:property value="items.itemsEndoso"    escapeHtml="false" /> ];
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// modelos //////
    Ext.define('_p48_modelo',
    {
        extend  : 'Ext.data.Model'
        ,fields : _p48_fieldsInciso
    });
    ////// modelos //////
    
    ////// stores //////
    _p48_store = Ext.create('Ext.data.Store',
    {
        autoLoad : false
        ,model   : '_p48_modelo'
        ,proxy   :
        {
            type         : 'ajax'
            ,url         : _p48_urlRecuperacion
            ,extraParams :
            {
                'params.consulta'  : 'RECUPERAR_INCISOS_POLIZA_GRUPO_FAMILIA'
                ,'params.cdunieco' : _p48_params.CDUNIECO
                ,'params.cdramo'   : _p48_params.CDRAMO
                ,'params.estado'   : _p48_params.ESTADO
                ,'params.nmpoliza' : _p48_params.NMPOLIZA
            }
            ,reader :
            {
                type             : 'json'
                ,root            : 'list'
                ,successProperty : 'success'
                ,messageProperty : 'message'
            }
        }
    });
    _p48_cargarStore();
    
    _p48_storeMov = Ext.create('Ext.data.Store',
    {
        autoLoad : false
        ,model   : '_p48_modelo'
        ,proxy   :
        {
            type         : 'ajax'
            ,url         : _p48_urlRecuperacion
            ,extraParams :
            {
                'params.consulta'  : 'RECUPERAR_MOVIMIENTOS_ENDOSO_ALTA_BAJA_ASEGURADO'
                ,'params.cdunieco' : _p48_params.CDUNIECO
                ,'params.cdramo'   : _p48_params.CDRAMO
                ,'params.estado'   : _p48_params.ESTADO
                ,'params.nmpoliza' : _p48_params.NMPOLIZA
                ,'params.nmsuplem' : _p48_params.nmsuplem_endoso
            }
            ,reader :
            {
                type             : 'json'
                ,root            : 'list'
                ,successProperty : 'success'
                ,messageProperty : 'message'
            }
        }
    });
    if(!Ext.isEmpty(_p48_params.nmsuplem_endoso))
    {
        _p48_cargarStoreMov();
    }
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p48_divpri'
        ,defaults : { style : 'margin:5px;' }
        ,border   : 0
        ,items    :
        [
            Ext.create('Ext.panel.Panel',
            {
                title     : 'DATOS DE P\u00D3LIZA'
                ,defaults : { style : 'margin:5px;' }
                ,layout   : 'hbox'
                ,items    : _p48_itemsPoliza
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title     : 'ASEGURADOS'
                ,itemId   : '_p48_gridAsegurados'
                ,columns  : _p48_colsInciso
                ,store    : _p48_store
                ,height   : 200
                ,selModel :
                {
                    selType        : 'checkboxmodel'
                    ,allowDeselect : true
                    ,mode          : 'SINGLE'
                }
                ,tbar     :
                [
                    {
                        text     : 'Agregar dependiente'
                        ,icon    : '${icons}add.png'
                        ,hidden  : _p48_params.operacion!='alta'
                        ,handler : _p48_agregarDepClic
                    }
                    ,{
                        text     : 'Agregar familia'
                        ,icon    : '${icons}add.png'
                        ,hidden  : _p48_params.operacion!='alta'||Ext.isEmpty(_p48_params.TIPOFLOT)||_p48_params.TIPOFLOT=='I'
                        ,handler : _p48_agregarFamClic
                    }
                    ,{
                        text     : 'Quitar'
                        ,icon    : '${icons}delete.png'
                        ,hidden  : _p48_params.operacion!='baja'
                        ,handler : _p48_quitarAseguradoClic
                    }
                ]
            })
            ,Ext.create('Ext.grid.Panel',
            {
                title    : 'MOVIMIENTOS'
                ,itemId  : '_p48_gridMovimientos'
                ,columns : _p48_colsMovimi
                ,store   : _p48_storeMov
                ,height  : 200
            })
            ,Ext.create('Ext.form.Panel',
            {
                title        : 'DATOS DEL ENDOSO'
                ,defaults    : { style : 'margin : 5px;' }
                ,items       : _p48_itemsEndoso
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text  : 'Confirmar'
                        ,icon : '${icons}key.png'
                    }
                ]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
function _p48_cargarStore()
{
    _p48_store.load(function(records,op,success)
    {
        if(!success)
        {
            mensajeError('Error al recuperar asegurados: '+op.getError());
        }
    });
}

function _p48_cargarStoreMov()
{
    _p48_storeMov.load(function(records,op,success)
    {
        if(!success)
        {
            mensajeError('Error al recuperar movimientos: '+op.getError());
        }
    });
}

function _p48_quitarAseguradoClic(me)
{
    debug('>_p48_quitarAseguradoClic');
    var ck = 'Quitando asegurado';
    try
    {
        var gridAsegurados = _fieldById('_p48_gridAsegurados');
        if(gridAsegurados.getSelectionModel().getSelection().length==0)
        {
            throw 'Seleccione un asegurado';
        }
        
        var record = gridAsegurados.getSelectionModel().getSelection()[0];
        debug('record:',record);
        
        _p48_storeMov.each(function(record2)
        {
            if(Number(record2.get('NMSITUAC'))==Number(record.get('NMSITUAC')))
            {
                throw 'Este inciso ya se encuentra en los movimientos';
            }
        });
        
        if(record.get('CVE_PARENTESCO')=='T')
        {
            _p48_store.each(function(rec)
            {
                if(Number(rec.get('NMSITAUX'))==Number(record.get('NMSITAUX'))
                   &&Number(rec.get('NMSITUAC'))!=Number(record.get('NMSITUAC'))
                )
                {
                    debugError('Aun hay NMSITAUX:',record.get('NMSITAUX'),'rec.data:',rec.data);
                    throw 'No se puede quitar un titular si aun hay familiares';
                }
            });
        }
        
        _p48_store.remove(record);
        record.set('MOV','-');
        _p48_storeMov.add(record);
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _p48_deshacerMov(v,row,col,item,e,record)
{
    debug('_p48_deshacerMov record.raw:',record.raw);
    var ck = 'Revirtiendo movimiento';
    try
    {
        if(record.get('MOV')=='-')
        {
            //deshacer "QUITAR"
            if(record.get('CVE_PARENTESCO')!='T')
            {
                var hayFamilia = false;
                _p48_store.each(function(rec)
                {
                    if(Number(rec.get('NMSITAUX'))==Number(record.get('NMSITAUX')))
                    {
                        hayFamilia = true;
                    }
                });
                if(!hayFamilia)
                {
                    throw 'No se puede regresar un dependiente, sin el titular';
                }
            }
            
            _p48_store.add(record);
            _p48_storeMov.remove(record);
        }
        else if(record.get('MOV')=='+')
        {
            if(record.get('AUX1')=='DEP')
            {
                _p48_storeMov.remove(record);
            }
        }
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _p48_validarEstadoBotonCancelar()
{
    debug('_p48_validarEstadoBotonCancelar');
    try
    {
        var bot = _fieldById('_p48_botonCancelar');
        if(Ext.isEmpty(_p48_params.nmsuplem_endoso))
        {
            bot.hide();
        }
        else
        {
            bot.show();
        }
    }
    catch(e)
    {
        debugError(e);
        mensajeError('Error al validar estado de endoso');
    }
}

function _p48_agregarDepClic()
{
    debug('_p48_agregarDepClic');
    var ck = 'Agregando dependiente';
    try
    {
        var grid = _fieldById('_p48_gridAsegurados');
        if(grid.getSelectionModel().getSelection().length==0
            ||grid.getSelectionModel().getSelection()[0].get('CVE_PARENTESCO')!='T'
        )
        {
            throw 'Seleccione un titular';
        }
        
        var record = grid.getSelectionModel().getSelection()[0];
        debug('record:',record);
        
        _p48_obtenerComponentes('DEP',record.get('CDTIPSIT'),function(mpersona,tatrisit,tatrirol,validacion)
        {
            centrarVentanaInterna(Ext.create('Ext.window.Window',
            {
                title  : 'AGREGAR DEPENDIENTE'
                ,modal : true
                ,items :
                [
                    Ext.create('Ext.form.Panel',
                    {
                        defaults : { style : 'margin:5px;' }
                        ,autoScroll : true
                        ,border  : 0
                        ,width   : 600
                        ,height  : 400
                        ,items   :
                        [
                            {
                                xtype  : 'fieldset'
                                ,title : '<span style="font:bold 14px Calibri;">DATOS DE PERSONA</span>'
                                ,items : mpersona
                                ,layout  :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                }
                            }
                            ,{
                                xtype  : 'fieldset'
                                ,title : '<span style="font:bold 14px Calibri;">DATOS DE P\u00D3LIZA</span>'
                                ,items : tatrisit
                                ,layout  :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                }
                            }
                            ,{
                                xtype  : 'fieldset'
                                ,title : '<span style="font:bold 14px Calibri;">DATOS ADICIONALES</span>'
                                ,items : tatrirol
                                ,layout  :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                }
                            }
                        ]
                        ,listeners   :
                        {
                            afterrender : function(me)
                            {
                                var nmsitauxVal = record.get('NMSITAUX');
                                var cdgrupoVal  = record.get('CDGRUPO');
                                var nmsituacVal = record.get('NMSITUAC');
                                var dsgrupoVal  = record.get('DSGRUPO');
                                if(Ext.isEmpty(dsgrupoVal))
                                {
                                    dsgrupoVal = record.get('DES_NOMBRE_GRUPO');
                                }
                                
                                var nmsitauxCmp   = me.down('[name=NMSITAUX]');
                                var cdgrupoCmp    = me.down('[name=CDGRUPO]');
                                var nmsituaextCmp = me.down('[name=NMSITUAEXT]');
                                var dsgrupoCmp    = me.down('[name=DSGRUPO]');
                                
                                nmsitauxCmp.setValue(nmsitauxVal);
                                cdgrupoCmp.setValue(cdgrupoVal);
                                nmsituaextCmp.setValue(nmsituacVal);
                                dsgrupoCmp.setValue(dsgrupoVal);
                            }
                        }
                        ,buttonAlign : 'center'
                        ,buttons     :
                        [
                            {
                                text     : 'Aceptar'
                                ,icon    : '${icons}accept.png'
                                ,handler : function(me)
                                {
                                    var ck = 'Guardando asegurado';
                                    try
                                    {
                                        var form = me.up('form');
                                        if(!form.isValid())
                                        {
                                            throw 'Favor de verificar datos';
                                        }
                                        
                                        var valores = form.getValues();
                                        debug('valores:',valores);
                                        
                                        ck = 'Ejecutando validaci\u00F3 din\u00E1mica';
                                        validacion(valores);
                                        
                                        var dependiente = new _p48_modelo(valores);
                                        dependiente.set('AUX1','DEP');
                                        dependiente.set('MOV','+');
                                        debug('dependiente.raw:',dependiente.raw,'dependiente.data:',dependiente.data);
                                        _p48_storeMov.add(dependiente);
                                        form.up('window').destroy();
                                    }
                                    catch(e)
                                    {
                                        manejaException(e,ck);
                                    }
                                }
                            }
                        ]
                    })
                ]
            }).show());
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _p48_agregarFamClic()
{
    debug('_p48_agregarFamClic');
    var ck = 'Agregando familia';
    try
    {
        _p48_obtenerComponentes('FAM',_p48_store.getAt(0).get('CDTIPSIT'),function(mpersona,tatrisit,tatrirol,validacion)
        {
            centrarVentanaInterna(Ext.create('Ext.window.Window',
            {
                title  : 'AGREGAR FAMILIA'
                ,modal : true
                ,items :
                [
                    Ext.create('Ext.panel.Panel',
                    {
                        width     : 900
                        ,height   : 500
                        ,autoScroll : true
                        ,border   : 0
                        ,defaults : { style : 'margin:5px;' }
                        ,items    :
                        [
                            _p48_crearFormulario(mpersona,tatrisit,tatrirol)
                        ]
                        ,tbar        :
                        [
                            {
                                text     : 'Agregar otro'
                                ,icon    : '${icons}add.png'
                                ,handler : function(me)
                                {
                                    _p48_obtenerComponentes('FAM',_p48_store.getAt(0).get('CDTIPSIT'),function(mpersona,tatrisit,tatrirol,validacion)
                                    {
                                        var form = _p48_crearFormulario(mpersona,tatrisit,tatrirol);
                                        me.up('panel').add(form);
                                    });
                                }
                            }
                        ]
                        ,listeners :
                        {
                            afterrender : function(me)
                            {
                                var ck = 'Manejando consecutivo de familia';
                                try
                                {
                                    _p48_nfamilia = _p48_nfamilia+1;
                                    me.familia    = _p48_nfamilia;
                                    me.down('form').down('[name=NMSITAUX]').setValue('NUEVA-'+me.familia);
                                }
                                catch(e)
                                {
                                    manejaException(e,ck);
                                }                                    
                            }
                        }
                        ,buttonAlign : 'center'
                        ,buttons     :
                        [
                            {
                                text     : 'Aceptar'
                                ,icon    : '${icons}accept.png'
                                ,handler : function(me)
                                {
                                    var ck = 'Revisando datos';
                                    try
                                    {
                                        var panel = me.up('panel');
                                        var forms = Ext.ComponentQuery.query('form',panel);
                                        debug('forms:',forms);
                                        
                                        var errores = [];
                                        for(var i in forms)
                                        {
                                            var form = forms[i];
                                            if(!form.isValid())
                                            {
                                                throw 'Favor de revisar datos';
                                            }
                                        }
                                        
                                        var records = [];
                                        for(var i in forms)
                                        {
                                            var form = forms[i];
                                            records.push(new _p48_modelo(form.getValues()));
                                        }
                                        debug('records:',records);
                                    }
                                    catch(e)
                                    {
                                        manejaException(e,ck);
                                    }
                                }
                            }
                        ]
                    })
                ]
            }).show());
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _p48_crearFormulario(mpersona,tatrisit,tatrirol)
{
    debug('>_p48_crearFormulario');
    return Ext.create('Ext.form.Panel',
    {
        title     : 'ASEGURADO'
        ,defaults : { style : 'margin:5px;' }
        ,width    : 850
        ,items    :
        [
            {
                xtype   : 'fieldset'
                ,title  : '<span style="font:bold 14px Calibri;">DATOS DE PERSONA</span>'
                ,items  : mpersona
                ,layout :
                {
                    type     : 'table'
                    ,columns : 3
                }
            }
            ,{
                xtype   : 'fieldset'
                ,title  : '<span style="font:bold 14px Calibri;">DATOS DE P\u00D3LIZA</span>'
                ,items  : tatrisit
                ,layout :
                {
                    type     : 'table'
                    ,columns : 3
                }
            }
            ,{
                xtype   : 'fieldset'
                ,title  : '<span style="font:bold 14px Calibri;">DATOS ADICIONALES</span>'
                ,items  : tatrirol
                ,layout :
                {
                    type     : 'table'
                    ,columns : 3
                }
            }
        ]
    });
}

function _p48_obtenerComponentes(depFam,cdtipsit,callback)
{
    var ck = 'Recuperando componentes';
    try
    {
        if(Ext.isEmpty(cdtipsit))
        {
            throw 'Falta cdtipsit';
        }
        if(Ext.isEmpty(callback))
        {
            throw 'Falta callback';
        }
    
        Ext.Ajax.request(
        {
            url      : _p48_urlRecuperarItemsFormulario
            ,params  :
            {
                'params.cdramo'    : _p48_params.CDRAMO
                ,'params.cdtipsit' : cdtipsit
                ,'params.depFam'   : depFam
            }
            ,success : function(response)
            {
                var ck = 'Decodificando formularios';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### elementos:',json);
                    if(json.success==true)
                    {
                        var mpersona    = Ext.decode(json.params.mpersona);
                        var tatrisit    = Ext.decode(json.params.tatrisit);
                        var tatrirol    = Ext.decode(json.params.tatrirol);
                        var botonValDep = Ext.decode(json.params.validacion);
                        debug('mpersona:'    , mpersona);
                        debug('tatrisit:'    , tatrisit);
                        debug('tatrirol:'    , tatrirol);
                        debug('botonValDep:' , botonValDep.handler);
                        callback(mpersona,tatrisit,tatrirol,botonValDep.handler);
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
                errorComunicacion(null,'Error al construir formularios');
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _p48_editarAsegurado(v,row,col,item,e,record)
{
    debug('_p48_editarAsegurado record.data:',record.data);
}

/*
function(valores)
{
    if(valores.CVE_PARENTESCO=='T')
    {
        throw 'No se puede agregar otro titular';
    }
    
    if(valores.CVE_PARENTESCO=='C')
    {
        _p48_store.each(function(rec)
        {
            if(Number(rec.get('NMSITAUX'))==Number(valores.NMSITAUX)
               &&rec.get('CVE_PARENTESCO')=='C'
            )
            {
                throw 'Ya hay un(a) c\u00F3nyugue en la familia, en los asegurados';
            }
        });
    
        _p48_storeMov.each(function(rec)
        {
            if(Number(rec.get('NMSITAUX'))==Number(valores.NMSITAUX)
               &&rec.get('CVE_PARENTESCO')=='C'
            )
            {
                throw 'Ya hay un(a) c\u00F3nyugue en la familia, en los movimientos';
            }
        });
    }
    
    valores['OTVALOR03']        = valores.CVE_PARENTESCO;
    valores['DES_NOMBRE_GRUPO'] = valores.DSGRUPO;
    if(valores.CVE_PARENTESCO=='T')
    {
        valores['DES_PARENTESCO'] = 'TITULAR';
    }
    else if(valores.CVE_PARENTESCO=='C')
    {
        valores['DES_PARENTESCO'] = 'C\u00D3NYUGUE';
    }
    else if(valores.CVE_PARENTESCO=='P')
    {
        valores['DES_PARENTESCO'] = 'PADRES';
    }
    else if(valores.CVE_PARENTESCO=='H')
    {
        valores['DES_PARENTESCO'] = 'HIJO';
    }
    else if(valores.CVE_PARENTESCO=='D')
    {
        valores['DES_PARENTESCO'] = 'DEPENDIENTE';
    }
    debug('final valores:',valores);
}
*/
////// funciones //////
</script>
</head>
<body>
<div id="_p48_divpri" style="height:500px;border:1px solid #999999"></div>
</body>
</html>