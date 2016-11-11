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
var _p48_urlConfirmarEndoso          = '<s:url namespace="/endosos"      action="confirmarEndosoFamilias"           />';
var _p48_urlMovimientos              = '<s:url namespace="/movimientos"  action="ejecutar"                          />';
var _p48_urlMovimientosSMD           = '<s:url namespace="/movimientos"  action="ejecutarSMD"                       />';
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
var _p48_itemsEndoso = [ <s:property value="items.itemsEndoso" escapeHtml="false" /> ];
var _p48_comboGrupos = <s:property value="items.comboGrupos" escapeHtml="false" />;
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
        ,itemId   : '_p48_panelpri'
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
                    ,'->'
                    ,{
                        xtype      : 'textfield'
                        ,listeners :
                        {
                            change : function(me,fil)
                            {
                                if(Ext.isEmpty(fil))
							    {
							        _p48_store.clearFilter();
							    }
							    else
							    {
	                                fil = fil.toUpperCase().replace(/ /g,'');
							        debug('filtro:',fil);
							        _p48_store.filterBy(function(record)
							        {
							            var incluido = false;
							            for(var clave in record.raw)
							            {
							                var valor=(String(record.raw[clave])).toUpperCase().replace(/ /g,'');
							                if(valor.lastIndexOf(fil)!=-1)
							                {
							                    incluido=true;
							                    break;
							                }
							            }
							            return incluido;
							        });
							    }
                            }
                        }
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
                ,itemId      : '_p48_formEndoso'
                ,defaults    : { style : 'margin : 5px;' }
                ,items       : _p48_itemsEndoso
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Confirmar'
                        ,icon    : '${icons}key.png'
                        ,handler : function(me)
                        {
                            Ext.MessageBox.confirm('Confirmar', '¿Desea confirmar el endoso?', function(btn)
                            {
                                if(btn === 'yes')
                                {
                                    var ck = 'Confirmando endoso';
                                    try
                                    {
                                        if(_p48_storeMov.getCount()==0)
                                        {
                                            throw 'No hay cambios';
                                        }
                                        
                                        if(!_fieldById('_p48_formEndoso').isValid())
                                        {
                                            throw 'Revisar datos del endoso';
                                        }
                                        
                                        var datos =
                                        {
                                            params : 
                                            {
                                                cdunieco              : _p48_params.CDUNIECO
                                                ,cdramo               : _p48_params.CDRAMO
                                                ,estado               : _p48_params.ESTADO
                                                ,nmpoliza             : _p48_params.NMPOLIZA
                                                ,cdtipsup             : _p48_params.cdtipsup
                                                ,nmsuplem             : _p48_params.nmsuplem_endoso
                                                ,nsuplogi             : _p48_params.nsuplogi
                                                ,fecha                : Ext.Date.format(_fieldByName('FEFECHA').getValue(),'d/m/Y')
                                                ,cdtipsitPrimerInciso : _p48_store.getAt(0).get('CDTIPSIT')
                                                ,nmsolici             : _p48_params.NMSOLICI
                                            }
                                            ,list : []
                                        };
                                        
                                        _p48_storeMov.each(function(record)
                                        {
                                            datos.list.push({nmsituac:record.get('NMSITUAC')});
                                        });
                                        debug('datos para confirmar:',datos);
                                        
                                        _setLoading(true,_fieldById('_p48_panelpri'));
                                        Ext.Ajax.request(
                                        {
                                            url       : _p48_urlConfirmarEndoso
                                            ,jsonData : datos
                                            ,success  : function(response)
                                            {
                                                _setLoading(false,_fieldById('_p48_panelpri'));
                                                var ck = 'Decodificando respuesta al confirmar endoso';
                                                try
                                                {
                                                    var json = Ext.decode(response.responseText);
                                                    debug('### confirmar:',json);
                                                    if(json.success)
                                                    {
                                                        var callbackRemesa = function()
                                                        {
                                                            marendNavegacion(2);
                                                        };
                                                        mensajeCorrecto('Endoso generado',json.message,function()
                                                        {
                                                            _generarRemesaClic(
                                                                true
                                                                ,_p48_params.CDUNIECO
                                                                ,_p48_params.CDRAMO
                                                                ,_p48_params.ESTADO
                                                                ,_p48_params.NMPOLIZA
                                                                ,callbackRemesa
                                                            );
                                                        });
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
                                                _setLoading(false,_fieldById('_p48_panelpri'));
                                                errorComunicacion();
                                            }
                                        });
                                    }
                                    catch(e)
                                    {
                                        manejaException(e,ck);
                                    }
                                }
                            });
                        } 
                    }
                    ,{
                        text     : 'Cancelar y borrar endoso'
                        ,itemId  : '_p48_botonCancelar'
                        ,icon    : '${icons}cancel.png'
                        ,hidden  : true
                        ,handler : _p48_cancelarEndosoClic
                    }
                ]
                ,listeners :
                {
                    afterrender : function(me)
                    {
                        _p48_validarEstadoBotonCancelar();
                        var ck = 'Limitando fecha de endoso';
                        try
                        {
                            me.down('[name=FEFECHA]').minValue = Ext.Date.parse(_p48_params.FEEFECTO,'d/m/Y');
                            me.down('[name=FEFECHA]').maxValue = Ext.Date.parse(_p48_params.FEPROREN,'d/m/Y');
                        }
                        catch(e)
                        {
                            manejaException(e);
                        }
                    }
                }
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
        
        var recordsQueSeQuitan = [];
        
        if(record.get('CVE_PARENTESCO')=='T')
        {
            var familia = Number(record.get('NMSITAUX'));
            _p48_store.each(function(rec)
            {
                if(Number(rec.get('NMSITAUX'))==familia)//pertenece a la familia
                {
                    var yaSeQuito = false;
                    for(var i=0;i<_p48_storeMov.getCount();i++)
                    {
                        if(Number(_p48_storeMov.getAt(i).get('NMSITUAC'))==Number(rec.get('NMSITUAC')))//buscar si ya se encuentra en movimientos
                        {
                            yaSeQuito = true;
                            break;
                        }
                    }
                    if(yaSeQuito==false)
                    {
                        recordsQueSeQuitan.push(rec);
                        rec.set('MOV','-');
                    }
                }
            });
        }
        else
        {
            record.set('MOV','-');
            recordsQueSeQuitan.push(record);
        }
        debug('recordsQueSeQuitan:',recordsQueSeQuitan);
        
        var quitados = 0;
        _setLoading(true,'_p48_gridAsegurados');
        for(var i in recordsQueSeQuitan)
        {
            var datos           = parseaFechas(recordsQueSeQuitan[i].data);
            datos['FEPROREN']   = _p48_params.FEPROREN;
            datos['cdtipsup']   = _p48_params.cdtipsup;
            datos['movimiento'] = 'PASO_QUITAR_ASEGURADO';
            datos['sleep']      = i*300;
            debug('datos:',datos);
            Ext.Ajax.request(
            {
                url       : _p48_urlMovimientosSMD
                ,jsonData : { params : datos }
                ,success  : function(response)
                {
                    var ck = 'Decodificando respuesta al quitar asegurado';
                    try
                    {
                        quitados = quitados + 1;
                        if(quitados==recordsQueSeQuitan.length)
                        {
                            _setLoading(false,'_p48_gridAsegurados');
                        }
                        var json = Ext.decode(response.responseText);
                        debug('### quitar:',json);
                        if(json.success==true)
                        {
                            if(quitados==recordsQueSeQuitan.length)
                            {
                                _p48_params.nmsuplem_endoso = json.params.nmsuplem_endoso;
                                _p48_params.nsuplogi        = json.params.nsuplogi;
                                debug('_p48_params:',_p48_params);
                                _p48_storeMov.proxy.extraParams['params.nmsuplem'] = _p48_params.nmsuplem_endoso;
                                _p48_cargarStoreMov();
                                mensajeCorrecto('Movimiento guardado','Se ha guardado el movimiento');
                                
                                _p48_validarEstadoBotonCancelar();
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
                    quitados = quitados + 1;
                    if(quitados==recordsQueSeQuitan.length)
                    {
                        _setLoading(false,'_p48_gridAsegurados');
                    }
                    errorComunicacion(null,'Error al quitar asegurado');
                }
            });
        }
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
            //buscamos si hay titular de la famlia en los movimientos
            var elTitularEstaEnMovimientos = false;
            for(var i=0;i<_p48_storeMov.getCount();i++)
            {
                var rec = _p48_storeMov.getAt(i);
                if(Number(rec.get('NMSITAUX'))==Number(record.get('NMSITAUX'))
                    &&rec.get('CVE_PARENTESCO')=='T'
                )
                {
                    elTitularEstaEnMovimientos = true;
                    break;
                }
            }
            debug('elTitularEstaEnMovimientos:',elTitularEstaEnMovimientos);
            var recordsParaDeshacerMov = [];
            if(elTitularEstaEnMovimientos)
            {
                var familia = Number(record.get('NMSITAUX'));
                _p48_storeMov.each(function(rec)
                {
                    if(Number(rec.get('NMSITAUX'))==familia)
                    {
                        recordsParaDeshacerMov.push(rec);
                    }
                });
            }
            else
            {
                recordsParaDeshacerMov.push(record);
            }
            
            debug('recordsParaDeshacerMov:',recordsParaDeshacerMov);
            
            var revertidos = 0;
            _setLoading(true,'_p48_panelpri');
            for(var i=0;i<recordsParaDeshacerMov.length;i++)
            {
                var datos = parseaFechas(recordsParaDeshacerMov[i].data);
                datos['movimiento']      = 'DESHACER_PASO_ASEGURADO';
                datos['nmsuplem_endoso'] = _p48_params.nmsuplem_endoso;
                datos['cdtipsup']        = _p48_params.cdtipsup;
                Ext.Ajax.request(
                {
                    url       : _p48_urlMovimientosSMD
                    ,jsonData : { params : datos }
                    ,success  : function(response)
                    {
                        var ck = 'Decodificando respuesta al revertir movimiento';
                        try
                        {
                            revertidos = revertidos + 1;
                            if(revertidos==recordsParaDeshacerMov.length)
                            {
                                _setLoading(false,'_p48_panelpri');
                            }
                            var json = Ext.decode(response.responseText);
	                        debug('### revertir:',json);
	                        if(json.success==true)
	                        {
	                            if(revertidos==recordsParaDeshacerMov.length)
	                            {
	                                mensajeCorrecto('Movimiento(s) revertido(s)','Se ha(n) revertido el(los) movimiento(s)');
	                                _p48_cargarStoreMov();
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
                    ,failure  : function()
                    {
                        revertidos = revertidos + 1;
                        if(revertidos==recordsParaDeshacerMov.length)
                        {
                            _setLoading(false,'_p48_panelpri');
                        }
                        errorComunicacion(null,'Error al revertir movimiento');
                    }
                });
            }
        }
        else if(record.get('MOV')=='+')
        {
            if(record.get('AUX1')=='DEP')
            {
                _p48_storeMov.remove(record);
            }
            else if(record.get('AUX1')=='FAM')
            {
                centrarVentanaInterna(Ext.create('Ext.window.Window',
                {
                    title        : 'Quitar asegurado/familia'
                    ,modal       : true
                    ,html        : '<div style="padding:5px;">¿Desea quitar el asegurado o la familia?</div>'
                    ,buttonAlign : 'center'
                    ,buttons     :
                    [
                        {
                            text     : 'Asegurado'
                            ,icon    : '${icons}user_delete.png'
                            ,handler : function(me)
                            {
                                var ck = 'Revirtiendo asegurado';
                                try
                                {
                                    var paren = record.get('CVE_PARENTESCO');
                                    debug('validando parenteso:',paren);
                                    if(paren=='T')
                                    {
                                        throw 'No se puede quitar el titular';
                                    }
                                    _p48_storeMov.remove(record);
                                    me.up('window').destroy();
                                }
                                catch(e)
                                {
                                    manejaException(e,ck);
                                }
                            }
                        }
                        ,{
                            text  : 'Familia'
                            ,icon : '${icons}group_delete.png'
                            ,handler : function(me)
                            {
                                var ck = 'Revirtiendo familia';
                                try
                                {
                                    var familia = record.get('NMSITAUX');
                                    debug('familia a quitar:',familia);
                                    for(var i=_p48_storeMov.getCount()-1;i>=0;i--)
                                    {
                                        if(_p48_storeMov.getAt(i).get('NMSITAUX')==familia)
                                        {
                                            _p48_storeMov.remove(_p48_storeMov.getAt(i));
                                        }
                                    }
                                    me.up('window').destroy();
                                }
                                catch(e)
                                {
                                    manejaException(e,ck);
                                }
                            }
                        }
                    ]
                }).show());
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
                        defaults    : { style : 'margin:5px;' }
                        ,autoScroll : true
                        ,border     : 0
                        ,width      : 600
                        ,height     : 400
                        ,items      :
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
                                        valores['CDTIPSIT'] = record.get('CDTIPSIT');
                                        debug('valores:',valores);
                                        
                                        ck = 'Ejecutando validaci\u00F3 din\u00E1mica';
                                        validacion(valores,false);
                                        
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
                                        form.down('[fieldLabel=FAMILIA]')
                                            .setValue(me.up('panel').down('form').down('[fieldLabel=FAMILIA]').getValue());
                                    },me.up('window'));
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
                                    Ext.MessageBox.confirm('Confirmar', '¿Termin\u00F3 de agregar todos los asegurados de la familia?', function(btn)
                                    {
                                        if(btn === 'yes')
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
		                                        
		                                        var cdtipsit = _p48_store.getAt(0).get('CDTIPSIT');
		                                        
		                                        var records = [];
		                                        for(var i in forms)
		                                        {
		                                            var form         = forms[i];
		                                            var vals         = form.getValues();
		                                            vals['CDTIPSIT'] = cdtipsit;
		                                            records.push(new _p48_modelo(vals));
		                                        }
		                                        debug('records:',records);
		                                        
		                                        var maxGrupo = 0;
		                                        _p48_store.each(function(record)
		                                        {
		                                            if(Number(record.get('CDGRUPO'))>maxGrupo)
		                                            {
		                                                maxGrupo = Number(record.get('CDGRUPO'));
		                                            }
		                                        });
		                                        debug('maxGrupo:',maxGrupo);
		                                        
		                                        for(var i in records)
		                                        {
		                                            var cdgrupo = Number(records[i].get('CDGRUPO'));
		                                            if(cdgrupo==0||cdgrupo>maxGrupo)
		                                            {
		                                                throw 'El grupo no es v\u00E1lido para '+
		                                                    (
		                                                        [
		                                                            records[i].get('DSNOMBRE')
		                                                            ,records[i].get('DSNOMBRE1')
		                                                            ,records[i].get('DSAPELLIDO')
		                                                            ,records[i].get('DSAPELLIDO1')
		                                                        ].join(' ')
		                                                    );
		                                            }
		                                        }
		                                        
		                                        ck = 'Ejecutando validaci\u00F3n din\u00E1mica';
		                                        validacion(records);
		                                        
		                                        for(var i in records)
		                                        {
		                                            records[i].set('MOV'  , '+');
		                                            records[i].set('AUX1' , 'FAM');
		                                            _p48_storeMov.add(records[i]);
		                                        }
		                                        
		                                        me.up('window').destroy();
		                                    }
		                                    catch(e)
		                                    {
		                                        manejaException(e,ck);
		                                    }
		                                }
		                            });
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

function _p48_obtenerComponentes(depFam,cdtipsit,callback,cmpLoading)
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
        
        _setLoading(true,cmpLoading||_fieldById('_p48_gridAsegurados'));
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
                _setLoading(false,cmpLoading||_fieldById('_p48_gridAsegurados'));
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
                _setLoading(false,cmpLoading||_fieldById('_p48_gridAsegurados'));
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
    var ck = 'Validando registro';
    try
    {
        if(record.get('AUX1')=='DEP'||record.get('AUX1')=='FAM')
        {
            _p48_obtenerComponentes('DEP',record.get('CDTIPSIT'),function(mpersona,tatrisit,tatrirol,validacion)
	        {
	            centrarVentanaInterna(Ext.create('Ext.window.Window',
	            {
	                title  : 'EDITAR ASEGURADO'
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
	                                me.down('[name=CVE_PARENTESCO]').setReadOnly(true);
	                                me.down('[name=DSGRUPO]').allowBlank = true;
	                                me.down('[name=DSGRUPO]').hide();
	                                me.down('[name=NMSITUAEXT]').allowBlank = true;
                                    me.down('[name=NMSITUAEXT]').hide();
	                                _cargarForm(me,record.data);
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
	                                        
	                                        ck = 'Ejecutando validaci\u00F3n din\u00E1mica';
	                                        validacion(valores,true);
	                                        
	                                        for(var att in valores)
	                                        {
	                                            record.set(att,valores[att]);
	                                        }
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
    }
    catch(e)
    {
        manejaException(e);
    }
}

function _p48_cancelarEndosoClic(button)
{
    Ext.MessageBox.confirm('Confirmar', '¿Desea cancelar y borrar los cambios del endoso?', function(btn)
    {
        if(btn === 'yes')
        {
            _setLoading(true,'_p48_panelpri');
            Ext.Ajax.request(
            {
                url      : _p48_urlMovimientos
                ,params  :
                {
                    'params.movimiento' : 'SACAENDOSO'
                    ,'params.cdunieco'  : _p48_params.CDUNIECO
                    ,'params.cdramo'    : _p48_params.CDRAMO
                    ,'params.estado'    : _p48_params.ESTADO
                    ,'params.nmpoliza'  : _p48_params.NMPOLIZA
                    ,'params.nsuplogi'  : _p48_params.nsuplogi
                    ,'params.nmsuplem'  : _p48_params.nmsuplem_endoso
                }
                ,success : function(response)
                {
                    _setLoading(false,'_p48_panelpri');
                    var ck = 'Decodificando respuesta de cancelar endoso';
                    try
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### cancelar:',json);
                        if(json.success==true)
                        {
                            mensajeCorrecto('Endoso revertido','Endoso revertido');
                            _setLoading(true,'_p48_panelpri');
                            marendNavegacion(2);
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
                    _setLoading(false,'_p48_panelpri');
                    errorComunicacion(null,'Error al cancelar endoso');
                }
            });
        }
    });
}

function _p48_rendererGrupos(val)
{
    debug('_p48_rendererGrupos val:',val);
    //voy a buscar el valor val en el store del combo de grupos
    //cuando no este cargando, regreso "cargando...", y agrego listener, (una sola vez)
    //cuando si este cargado, regreso el valor, o "error"
    if(_p48_comboGrupos.getStore().getCount()>0)
    {
        var recordGrupo = _p48_comboGrupos.findRecordByValue(val);
        if(recordGrupo!=false)
        {
            val = recordGrupo.get('value');
        }
        else
        {
            val = '-Error-';
        }
    }
    else
    {
        debug('_p48_rendererGrupos else');
        val = 'Cargando...';
        if(Ext.isEmpty(_p48_comboGrupos.getStore().tieneListener))
        {
            _p48_comboGrupos.getStore().tieneListener = true;
            _p48_comboGrupos.getStore().on(
            {
                load : function(me,records,success)
                {
                    if(success)
                    {
                        _fieldById('_p48_gridAsegurados').getView().refresh();
                    }
                }
            });
        }
    }
    debug('_p48_rendererGrupos return:',val);
    return val;
}

/*
function(valores,editar)
{
    debug('validacion DEP valores:',valores,'editar:',editar,'.');
    
    if(Ext.isEmpty(valores))
    {
        throw 'No hay valores';
    }
    
    if(Ext.isEmpty(editar))
    {
        throw 'No hay flag de editar';
    }
    
    if(valores.CVE_PARENTESCO=='T'&&!editar)
    {
        throw 'No se puede agregar otro titular';
    }
    
    if(valores.CVE_PARENTESCO=='C'&&!editar)
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
        valores['DES_PARENTESCO'] = 'C\u00D3NYUGE';
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

function(records)
{
    var nTit = 0;
    var nCon = 0;
    
    for(var i in records)
    {
        if(records[i].get('CVE_PARENTESCO')=='T')
        {
            nTit = nTit + 1;
        }
        else if(records[i].get('CVE_PARENTESCO')=='C')
        {
            nCon = nCon + 1;
        }
    }
    if(nTit==0)
    {
        throw 'Hace falta un titular';
    }
    else if(nTit>1)
    {
        throw 'Solo debe haber un titular';
    }
    if(nCon>1)
    {
        throw 'Solo puede haber un(a) c\u00F3nyuge';
    }
    
    for(var i in records)
    {
        var record  = records[i];
        var valores = record.raw;
        
        valores['OTVALOR03'] = valores.CVE_PARENTESCO;
        
	    if(valores.CVE_PARENTESCO=='T')
	    {
	        valores['DES_PARENTESCO'] = 'TITULAR';
	    }
	    else if(valores.CVE_PARENTESCO=='C')
	    {
	        valores['DES_PARENTESCO'] = 'C\u00D3NYUGE';
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
	    
	    record.set('OTVALOR03'      , valores['OTVALOR03']);
	    record.set('DES_PARENTESCO' , valores['DES_PARENTESCO']);
    }
    debug('final records:',records);
}
*/
////// funciones //////
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
</head>
<body>
<div id="_p48_divpri" style="height:500px;border:1px solid #999999"></div>
</body>
</html>