<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script>
////// urls //////
////// urls //////

////// variables //////
var _p59_storeAseguradosNuevos,
    _p59_windowAsegurado;
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
////// componentes dinamicos //////

Ext.onReady(function()
{
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    _p59_storeAseguradosNuevos = Ext.create('Ext.data.Store',
    {
        type    : 'memory'
        ,data   : []
        ,fields :
        [
            'NMSITUAC', 'DSPARENT', 'DSNOMBRE', 'DSSEXO', { type : 'date', name : 'FENACIMI', dateFormat : 'd/m/Y' }
        ]
        ,cargar : function()
        {
            var me = this;
            me.removeAll();
            
            if(Math.random()>0.5)
            {
                me.add(
                {
                    NMSITUAC  : '5'
                    ,DSPARENT : 'CONYUGE'
                    ,DSNOMBRE : 'TEST '+(new Date()).getTime()
                    ,DSSEXO   : 'MUJER'
                    ,FENACIMI : '06/11/1989'
                });
            }
            
            if(Math.random()>0.5)
            {
                me.add(
                {
                    NMSITUAC  : '6'
                    ,DSPARENT : 'DEPENDIENTE'
                    ,DSNOMBRE : 'TEST '+(new Date()).getTime()
                    ,DSSEXO   : 'HOMBRE'
                    ,FENACIMI : '01/01/2000'
                });
            }
            
            if(Math.random()>0.5)
            {
                me.add(
                {
                    NMSITUAC  : '7'
                    ,DSPARENT : 'DEPENDIENTE'
                    ,DSNOMBRE : 'TEST '+(new Date()).getTime()
                    ,DSSEXO   : 'MUJER'
                    ,FENACIMI : '02/02/2010'
                });
            }
        }
    });
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        itemId    : '_p59_panelpri'
        ,renderTo : '_p59_divpri'
        ,title    : 'ENDOSO DE ALTA DE ASEGURADOS'
        ,border   : 0
        ,defaults : { style : 'margin : 5px;' }
        ,items    :
        [
            {
                xtype     : 'panel'
                ,layout   : 'hbox'
                ,border   : 0
                ,defaults : { style : 'margin : 5px;' }
                ,items    :
                [
                    {
                        xtype       : 'datefield'
                        ,fieldLabel : 'Fecha de efecto'
                        ,format     : 'd/m/Y'
                        ,name       : 'FEEFECTO'
                        ,allowBlank : false
                    }
                    ,{
                        xtype    : 'button'
                        ,text    : 'Confirmar fecha'
                        ,itemId  : '_p59_botonConfirmarFecha'
                        ,icon    : '${icons}accept.png'
                        ,handler : _p59_confirmarClic
                    }
                ]
            }
            ,{
                xtype       : 'grid'
                ,title      : 'ASEGURADOS NUEVOS'
                ,itemId     : '_p59_gridAseguradosNuevos'
                ,width      : 800
                ,height     : 300
                ,autoScroll : true
                ,hidden     : true
                ,tbar       :
                [
                    {
                        text     : 'Agregar'
                        ,icon    : '${icons}add.png'
                        ,handler : _p59_agregarClic
                    }
                ]
                ,store      : _p59_storeAseguradosNuevos
                ,columns    :
                [
                    {
                        text          : 'NO.'
                        ,dataIndex    : 'NMSITUAC'
                        ,width        : 50
                        ,menuDisabled : true
                        ,sortable     : false
                    }
                    ,{
                        text       : 'PARENTESCO'
                        ,dataIndex : 'DSPARENT'
                        ,width     : 100
                    }
                    ,{
                        text       : 'NOMBRE'
                        ,dataIndex : 'DSNOMBRE'
                        ,width     : 300
                    }
                    ,{
                        text       : 'SEXO'
                        ,dataIndex : 'DSSEXO'
                        ,width     : 100
                    }
                    ,{
                        xtype       : 'datecolumn'
                        ,text       : 'FECHA DE<br/>NACIMIENTO'
                        ,dataIndex  : 'FENACIMI'
                        ,width      : 100
                        ,format     : 'd/m/Y'
                    }
                    ,{
                        xtype         : 'actioncolumn'
                        ,menuDisabled : true
                        ,sortable     : false
                        ,width        : 100
                        ,items        :
                        [
                            {
                                tooltip  : 'Editar'
                                ,icon    : '${icons}pencil.png'
                                ,handler : function(me,row,col,item,e,record)
                                {
                                    _fieldById('_p59_formAsegurado').mostrarParaEditar(record);
                                }
                            }
                            ,{
                                tooltip : 'Domicilio'
                                ,icon   : '${icons}report_key.png'
                            }
                            ,{
                                tooltip : 'Exclusiones'
                                ,icon   : '${icons}lock.png'
                            }
                            ,{
                                tooltip  : 'Quitar'
                                ,icon    : '${icons}delete.png'
                                ,handler : function(me,row,col,item,e,record)
                                {
                                    _p59_storeAseguradosNuevos.remove(record);
                                }
                            }
                        ]
                    }
                ]
            }
            ,{
                xtype     : 'form'
                ,title    : 'DATOS DE ASEGURADO'
                ,itemId   : '_p59_formAsegurado'
                ,border   : 0
                ,hidden   : true
                ,defaults : { style : 'margin : 5px;' }
                ,items    :
                [
                    {
                        xtype   : 'fieldset'
                        ,title  : '<span style="font:bold 14px Calibri;">DATOS DE PERSONA</span>'
                        ,layout :
                        {
                            type     : 'table'
                            ,columns : 2
                        }
                        ,defaults : { style : 'margin : 5px;' }
                        ,items    :
                        [
                            {
                                xtype       : 'textfield'
                                ,fieldLabel : 'NOMBRE'
                                ,allowBlank : false
                            }
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'SEGUNDO NOMBRE'
                            }
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'APELLIDO PATERNO'
                                ,allowBlank : false
                            }
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'APELLIDO MATERNO'
                                ,allowBlank : false
                            }
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'SEXO'
                                ,allowBlank : false
                            }
                        ]
                    }
                    ,{
                        xtype   : 'fieldset'
                        ,title  : '<span style="font:bold 14px Calibri;">DATOS ADICIONALES</span>'
                        ,layout :
                        {
                            type     : 'table'
                            ,columns : 2
                        }
                        ,defaults : { style : 'margin : 5px;' }
                        ,items    :
                        [
                            {
                                xtype       : 'textfield'
                                ,fieldLabel : 'CLIENTE VIP'
                            }
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'CORREO ELECTR\u00d3NICO'
                            }
                        ]
                    }
                    ,{
                        xtype   : 'fieldset'
                        ,title  : '<span style="font:bold 14px Calibri;">DATOS RELACIONADOS A LA P\u00d3LIZA</span>'
                        ,layout :
                        {
                            type     : 'table'
                            ,columns : 2
                        }
                        ,defaults : { style : 'margin : 5px;' }
                        ,items    :
                        [
                            {
                                xtype       : 'textfield'
                                ,fieldLabel : 'PARENTESCO'
                                ,allowBlank : false
                            }
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'EXTRAPRIMA OCUPACI\U00D3N'
                            }
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'EXTRAPRIMA SOBREPESO'
                            }
                        ]
                    }
                ]
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Guardar y continuar'
                        ,icon    : '${icons}disk.png'
                        ,handler : _p59_formAseguradosGuardarClic
                    }
                    ,{
                        text     : 'Cancelar'
                        ,icon    : '${icons}cancel.png'
                        ,handler : function(me)
                        {
                            me.up('form').cerrar();
                        }
                    }
                ]
                ,mostrarParaNuevo : function()
                {
                    debug('_p59_formAsegurado.mostrarParaNuevo args:',arguments);
                    var me = this;
                    me.getForm().reset();
                    _fieldById('_p59_gridAseguradosNuevos').hide();
                    me.show();
                }
                ,mostrarParaEditar : function(datos)
                {
                    debug('_p59_formAsegurado.mostrarParaEditar args:',arguments);
                    var me = this;
                    me.getForm().reset();
                    _fieldById('_p59_gridAseguradosNuevos').hide();
                    me.show();
                }
                ,cerrar : function()
                {
                    debug('_p59_formAsegurado.cerrar');
                    var me = this;
                    me.hide();
                    _fieldById('_p59_gridAseguradosNuevos').show();
                }
            }
        ]
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text     : 'Tarificar'
                ,icon    : '${icons}key.png'
                ,handler : _p59_tarificarClic
            },{
                text  : 'Cancelar endoso'
                ,icon : '${icons}cancel.png'
                ,handler : _p59_cancelarClic
            }
            ,{
                text     : '- simular carga -'
                ,icon    : '${icons}bug.png'
                ,handler : _p59_cargar
            }
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    mensajeWarning('Por favor confirme la fecha de efecto para continuar con la captura de asegurados');
    ////// loaders //////
});

////// funciones //////
function _p59_confirmarClic(me)
{
    debug('>_p59_confirmarClic args:',arguments);
    
    if(!_fieldByName('FEEFECTO').isValid())
    {
        return datosIncompletos();
    }
    
    _p59_bloquearFecha();
}

function _p59_bloquearFecha()
{
    debug('_p59_bloquearFecha');
    
    _fieldByName('FEEFECTO').setReadOnly(true);
    _fieldById('_p59_botonConfirmarFecha').hide();
    _fieldById('_p59_gridAseguradosNuevos').show();
}

function _p59_agregarClic(me)
{
    debug('>_p59_agregarClic args:',arguments);
    _fieldById('_p59_formAsegurado').mostrarParaNuevo();
}

function _p59_formAseguradosGuardarClic(me)
{
    debug('_p59_formAseguradosGuardarClic args:',arguments);
    
    var mask, ck = 'Guardando datos';
    try
    {
        var form = me.up('form');
        if(!form.isValid())
        {
            throw 'Favor de revisar los datos';
        }
        
        mensajeCorrecto(
            'Datos guardados'
            ,'Datos guardados'
            ,function()
            {
                form.cerrar();
                _p59_storeAseguradosNuevos.cargar();
            }
        );
    }
    catch(e)
    {
        manejaException(e,ck,mask);
    }
}

function _p59_cargar()
{
    debug('_p59_cargar');
    _fieldByName('FEEFECTO').setValue(new Date());
    _p59_bloquearFecha();
    _p59_storeAseguradosNuevos.cargar();
}

function _p59_cancelarClic(me)
{
    debug('_p59_cancelarClic args:',arguments);
    mensajeCorrecto(
        'Endoso revertido'
        ,'Endoso revertido'
        ,function()
        {
            _p59_mesacontrol();
        }
    );
}

function _p59_tarificarClic(me)
{
    debug('_p59_tarificarClic args:',arguments);
    centrarVentanaInterna(Ext.create('Ext.window.Window',
    {
        title  : 'TARIFA PREVIA'
        ,modal : true
        ,items :
        [
            {
                xtype    : 'grid'
                ,border  : 0
                ,width   : 700
                ,height  : 400
                ,columns :
                [
                    {
                        text       : 'COBERTURA'
                        ,width     : 300
                        ,dataIndex : 'DSGARANT'
                    }
                    ,{
                        text       : 'PRIMA'
                        ,width     : 100
                        ,renderer  : Ext.util.Format.usMoney
                        ,dataIndex : 'NMIMPORT'
                    }
                ]
                ,store : Ext.create('Ext.data.Store',
                {
                    fields :
                    [
                        'DSGARANT'
                        ,{
                            name  : 'NMIMPORT'
                            ,type : 'float'
                        }
                    ]
                    ,data :
                    [
                        {
                            DSGARANT  : 'EMERGENCIA EN EL EXTRANJERO'
                            ,NMIMPORT : 429.50
                        }
                        ,{
                            DSGARANT  : 'ASISTENCIA INTERNACIONAL'
                            ,NMIMPORT : 225
                        }
                        ,{
                            DSGARANT  : 'DERECHOS'
                            ,NMIMPORT : 300
                        }
                    ]
                })
            }
        ]
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text     : 'Confirmar'
                ,icon    : '${icons}key.png'
                ,handler : _p59_confirmarEndosoClic
            }
            ,{
                text    : 'Documentos'
                ,hidden : true
                ,icon   : '${icons}printer.png'
            }
        ]
    }).show());
}

function _p59_mesacontrol()
{
    debug('_p59_mesacontrol');
    _mask('Redireccionando...');
    Ext.create('Ext.form.Panel').submit(
    {
        standardSubmit : true
        ,url           : _GLOBAL_COMP_URL_MCFLUJO
    });
}

function _p59_confirmarEndosoClic(me)
{
    debug('_p59_confirmarEndosoClic args:',arguments);
    mensajeCorrecto(
        'Endoso confirmado'
        ,'Se ha confirmado el endoso 1'
        ,function()
        {
            me.up('window').closable = false;
            me.up('window').down('[text=Confirmar]').hide();
            me.up('window').down('[text=Documentos]').show();
        }
    );
}
////// funciones //////
</script>
</head>
<body>
<div id="_p59_divpri" style="height:800px;border:1px solid #CCCCCC"></div>
</body>
</html>