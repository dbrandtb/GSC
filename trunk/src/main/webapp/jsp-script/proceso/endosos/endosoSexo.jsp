<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
///////////////////////
////// variables //////
var _1_urlGuardar  = '<s:url namespace="/endosos" action="guardarEndosoSexo" />';

//Obtenemos el contenido en formato JSON de la propiedad solicitada:
var _1_smap1       = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
var _1_slist1      = <s:property value="%{convertToJSON('slist1')}" escapeHtml="false" />;
var _1_storeFeeder = <s:property value="%{convertToJSON('slist1')}" escapeHtml="false" />;

var _1_store;
var _1_grid;
var _1_form;


debug('_1_smap1',_1_smap1);
debug('_1_slist1',_1_slist1);
debug('_1_storeFeeder',_1_storeFeeder);

////// variables //////
///////////////////////

///////////////////////
////// funciones //////
function _1_confirmar()
{
    debug('_1_confirmar');
    var valido=true;
    
    ////////////////////////////
    ////// validar campos //////
    if(valido)
    {
        valido=_1_form.isValid();
        if(!valido)
            datosIncompletos();
    }
    ////// validar campos //////
    ////////////////////////////
    
    ///////////////////////
    ////// confirmar //////
    if(valido)
    {
        var json={};
        json['smap1']  = _1_smap1;
        json['smap2']  = _1_form.getValues();
        json['slist1'] = [];
        _1_store.each(function(record)
        {
            json['slist1'].push(
            {
                nmsituac  : record.get('nmsituac')
                ,cdperson : record.get('cdperson')
            });
        });
        debug(json);
        _1_form.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _1_urlGuardar
            ,jsonData : json
            ,success  : function(response)
            {
                _1_form.setLoading(false);
                json=Ext.decode(response.responseText);
                if(json.success==true)
                {
                    mensajeCorrecto('Guardar endoso',json.mensaje);
                    //////////////////////////////
                    ////// usa codigo padre //////
                    marendNavegacion(2);
                    ////// usa codigo padre //////
                    //////////////////////////////
                }
                else
                {
                    mensajeError(json.error);
                }
            }
            ,failure  : function()
            {
                _1_form.setLoading(false);
                errorComunicacion();
            }
        });
    }
    ////// confirmar //////
    ///////////////////////
}

function _1_validar()
{
    debug('validar');
    _1_store.each(function(record)
    {
        var n=record.get('fenacimi');
        var nmsituac=record.get('nmsituac');
        var o;
        var mas=_1_smap1.masedad=='si';
        for(var i=0;i<_1_slist1.length;i++)
        {
            if(_1_slist1[i].nmsituac==nmsituac)
                o=new Date(_1_slist1[i].fenacimi);
        }
        var oms=o.getTime();
        var nms=n.getTime();
        if(mas)
        {
            debug('revisar no menor');
            debug('nueva fecha',nms,'fecha original',oms);
            if(n<o)
            {
                record.set('fenacimi',o);
                mensajeWarning('La fecha no puede ser menor');
            }
        }
        else
        {
            debug('revisar no mayor');
            debug('nueva fecha',nms,'fecha original',oms);
            if(n>o)
            {
                record.set('fenacimi',o);
                mensajeWarning('La fecha no puede ser mayor');
            }
        }
    });
}
////// funciones //////
///////////////////////

Ext.onReady(function()
{
    /////////////////////
    ////// modelos //////
    Ext.define('_1_Modelo',
    {
        extend  : 'Ext.data.Model'
        ,fields : [ <s:property value="imap1.modelo" /> ]
    });
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    _1_store=Ext.create('Ext.data.Store',
    {
        model      : '_1_Modelo'
        ,autoLoad  : true
        ,proxy     :
        {
            type    : 'memory'
            ,reader : 'json'
            ,data   : _1_storeFeeder
        }
    });
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    Ext.define('_1_Grid',
    {
        extend         : 'Ext.grid.Panel'
        ,initComponent : function()
        {
            debug('_1_Grid initComponent');
            Ext.apply(this,
            {
                title    : 'Asegurados'
                ,icon    : '${ctx}/resources/fam3icons/icons/user.png'
                ,store   : _1_store
                ,columns :
                          [ <s:property value="imap1.columnas" /> ]
                ,plugins : [ Ext.create('Ext.grid.plugin.CellEditing',{ clicksToEdit : 1 }) ]
            });
            this.callParent();
        }
    });
    
    Ext.define('_1_Form',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_1_Form initComponent');
            Ext.apply(this,
            {
                title        : 'Datos del endoso'
                ,items       :
                [
                    {
                        xtype       : 'datefield'
                        ,format     : 'd/m/Y'
                        ,fieldLabel : 'Fecha'
                        ,allowBlank : false
                        ,value      : new Date()
                        ,name       : 'fecha_endoso'
                    }
                ]
                ,defaults    : 
                {
                    style : 'margin : 5px;'
                }
                ,buttonAlign : 'center'
                ,buttons     :
                              [
                                  {
                                      text     : 'Confirmar endoso'
                                      ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                                      ,handler : _1_confirmar
                                  }
                              ]
            });
            this.callParent();
        }
    });
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    _1_editor=Ext.create('Ext.form.DateField',
    {
        format     : 'd/m/Y'
        ,listeners :
        {
            blur : _1_validar
        }
    });
    
    _1_grid=new _1_Grid();
    _1_form=new _1_Form();
    
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_1_divPri'
        ,defaults :
        {
            style : 'margin : 5px;'
        }
        ,items    :
        [
            _1_grid
            ,_1_form
        ]
    });
    ////// contenido //////
    ///////////////////////
});
</script>
<div id="_1_divPri"></div>