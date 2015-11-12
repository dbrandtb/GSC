<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// urls //////
var _p40_urlConfirmarEndoso         = '<s:url namespace="/endosos" action="confirmarEndosoRehabilitacionSalud" />';
var _p40_urlRecuperacionSimpleLista = '<s:url namespace="/emision" action="recuperacionSimpleLista"           />';
////// urls //////

////// variables //////
var _p40_smap1  = <s:property value="%{convertToJSON('smap1')}"  escapeHtml="false" />;
debug('_p40_smap1:',_p40_smap1);

var _p40_storeEndosos = null;
////// variables //////

////// componentes dinamicos //////
var _p40_formLecturaItems   = [ <s:property value="imap.formLecturaItems"   escapeHtml="false" /> ];
var _p40_formEndosoItems    = [ <s:property value="imap.formEndosoItems"    escapeHtml="false" /> ];
var _p40_gridEndososColumns = [ <s:property value="imap.gridEndososColumns" escapeHtml="false" /> ];
var _p40_modeloEndosoFields = [ <s:property value="imap.modeloEndosoFields" escapeHtml="false" /> ];
////// componentes dinamicos //////

Ext.onReady(function()
{
	Ext.Ajax.timeout = 1*60*60*1000; // 1 hora
	
    ////// overrides //////
    ////// overrides //////
    
    ////// modelos //////
    Ext.define('_p40_modeloEndoso',
    {
        extend  : 'Ext.data.Model'
        ,fields : _p40_modeloEndosoFields
    });
    ////// modelos //////
    
    ////// stores //////
    _p40_storeEndosos = Ext.create('Ext.data.Store',
    {
        model     : '_p40_modeloEndoso'
        ,autoLoad : false
        ,proxy    :
        {
            type         : 'ajax'
            ,url         : _p40_urlRecuperacionSimpleLista
            ,extraParams :
            {
                'smap1.procedimiento' : 'RECUPERAR_ENDOSOS_REHABILITABLES'
                ,'smap1.cdunieco'     : _p40_smap1.CDUNIECO
                ,'smap1.cdramo'       : _p40_smap1.CDRAMO
                ,'smap1.estado'       : _p40_smap1.ESTADO
                ,'smap1.nmpoliza'     : _p40_smap1.NMPOLIZA
            }
            ,reader      :
            {
                type             : 'json'
                ,root            : 'slist1'
                ,successProperty : 'exito'
                ,messageProperty : 'respuesta'
            }
        }
    });
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p40_divpri'
        ,itemId   : '_p40_panelpri'
        ,border   : 0
        ,defaults : { style : 'margin:5px;' }
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                itemId  : '_p40_formLectura'
                ,title  : 'Datos de p&oacute;liza'
                ,layout :
                {
                    type     : 'table'
                    ,columns : 3
                }
                ,defaults : { style : 'margin:5px;' }
                ,items  : _p40_formLecturaItems
            })
            ,Ext.create('Ext.grid.Panel',
            {
                itemId     : '_p40_gridEndosos'
                ,title     : 'Endosos rehabilitables'
                ,columns   : _p40_gridEndososColumns
                ,store     : _p40_storeEndosos
                ,minHeight : 150
                ,maxHeight : 400
                ,selModel  :
	            {
	                selType        : 'checkboxmodel'
	                ,allowDeselect : true
	                ,mode          : 'SINGLE'
	                ,listeners     :
	                {
	                    selectionchange : function(me,selected,eOpts)
	                    {
	                        debug('selected:',selected);
	                        if(selected.length==1)
	                        {
	                            _fieldByName('feefecto').setValue(selected[0].get('FEINIVAL'));
	                        }
	                        else
	                        {
	                            _fieldByName('feefecto').setValue('');
	                        }
	                    }
	                }
	            }
            })
            ,Ext.create('Ext.form.Panel',
            {
                itemId  : '_p40_formEndoso'
                ,title  : 'Datos de endoso'
                ,layout :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,defaults : { style : 'margin:5px;' }
                ,items    : _p40_formEndosoItems
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        itemId   : '_p40_botonConfirmar'
                        ,text    : 'Confirmar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                        ,handler : function(me)
                        {
                            if(!me.up('form').getForm().isValid())
                            {
                                datosIncompletos();
                                return;
                            }
                            
                            me.disable();
                            me.setText('Cargando...');
                            var record = _fieldById('_p40_gridEndosos').getSelectionModel().getSelection()[0];
                            Ext.Ajax.request(
                            {
                                url      : _p40_urlConfirmarEndoso
                                ,params  :
                                {
                                    'smap1.cdunieco'  : _p40_smap1.CDUNIECO
                                    ,'smap1.cdramo'   : _p40_smap1.CDRAMO
                                    ,'smap1.estado'   : _p40_smap1.ESTADO
                                    ,'smap1.nmpoliza' : _p40_smap1.NMPOLIZA
                                    ,'smap1.nsuplogi' : record.get('NSUPLOGI')
                                    ,'smap1.cddevcia' : record.get('CDDEVCIA')
                                    ,'smap1.cdgestor' : record.get('CDGESTOR')
                                    ,'smap1.feemisio' : record.raw['FEEMISIO']
                                    ,'smap1.feinival' : record.raw['FEINIVAL']
                                    ,'smap1.fefinval' : record.raw['FEFINVAL']
                                    ,'smap1.feefecto' : record.raw['FEEFECTO']
                                    ,'smap1.feproren' : record.raw['FEPROREN']
                                    ,'smap1.cdmoneda' : record.get('CDMONEDA')
                                    ,'smap1.nmsuplem' : record.get('NMSUPLEM')
                                }
                                ,success : function(response)
                                {
                                    me.enable();
                                    me.setText('Confirmar');
                                    var json = Ext.decode(response.responseText);
                                    debug('### confirmar:',json);
                                    if(json.success)
                                    {
                                        var callbackRemesa = function()
                                        {
                                            marendNavegacion(2);
                                        };
                                        mensajeCorrecto('Endoso generado','Endoso generado',function()
                                        {
                                            _generarRemesaClic(
                                                true
                                                ,_p40_smap1.CDUNIECO
                                                ,_p40_smap1.CDRAMO
                                                ,_p40_smap1.ESTADO
                                                ,_p40_smap1.NMPOLIZA
                                                ,callbackRemesa
                                            );
                                        });
                                    }
                                    else
                                    {
                                        mensajeError(json.respuesta);
                                    }
                                }
                                ,failure : function()
                                {
                                    me.enable();
                                    me.setText('Confirmar');
                                    errorComunicacion();
                                }
                            });
                        }
                    }
                ]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    _p40_storeEndosos.load(function(records,op,success)
    {
        if(success&&!Ext.isEmpty(records))
        {
            
        }
        else if(success)
        {
            mensajeError('No hay endosos rehabilitables');
        }
        else
        {
            mensajeError(op.getError());
        }
    });
    ////// loaders //////
});

////// funciones //////
////// funciones //////
<%@ include file="/jsp-script/proceso/documentos/scriptImpresionRemesaEmisionEndoso.jsp"%>
</script>
</head>
<body><div id="_p40_divpri" style="height:300px;border:1px solid #999999;"></div></body>
</html>