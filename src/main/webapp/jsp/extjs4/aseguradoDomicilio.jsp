<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var inputCdunieco = '<s:property value="smap1.pv_cdunieco" />';
var inputCdramo   = '<s:property value="smap1.pv_cdramo" />';
var inputEstado   = '<s:property value="smap1.pv_estado" />';
var inputNmpoliza = '<s:property value="smap1.pv_nmpoliza" />';
var inputNmsituac = '<s:property value="smap1.pv_nmsituac" />';
var inputCdperson = '<s:property value="smap1.pv_cdperson" />';
var urlRegresar   = '<s:url namespace="/" action="editarAsegurados" />';
var urlCargar     = '<s:url namespace="/" action="cargarPantallaDomicilio" />';
var urlGuardar    = '<s:url namespace="/" action="guardarPantallaDomicilio" />';
var formPanel;
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
    Ext.define('Modelo1',{
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
    formPanel=Ext.create('Ext.form.Panel',
    {
        renderTo    : 'maindiv',
        buttonAlign : 'center',
        url         : urlGuardar,
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
                        fieldLabel : 'Asegurado',
                        xtype      : 'textfield',
                        readOnly   : true,
                        name       : 'smap1.asegurado'
                    },
                    {
                        fieldLabel : 'RFC',
                        xtype      : 'textfield',
                        readOnly   : true,
                        name       : 'smap1.rfc'
                    },
                    {
                        fieldLabel : 'Tel&eacute;fono',
                        xtype      : 'textfield',
                        readOnly   : false,
                        name       : 'smap1.telefono'
                    }
                ]
            }),
            Ext.create('Ext.panel.Panel',{
                title           : 'Datos adicionales',
                collapsible     : true,
                titleCollapse   : true,
                style           : 'margin:5px;',
                maxHeight       : 200,
                defaults        :
                {
                    style : 'margin:5px;'
                },
                layout          :
                {
                    type    : 'table',
                    columns : 2
                },
                <s:property value="item2" />
            }),
            Ext.create('Ext.panel.Panel',{
                title           : 'Direcci&oacute;n',
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
                    {
                        fieldLabel     : 'Calle',
                        xtype          : 'textfield',
                        name           : 'smap1.calle',
                        allowBlank     : false
                    },
                    {
                        fieldLabel     : 'Interior',
                        xtype          : 'textfield',
                        name           : 'smap1.interior',
                        allowBlank     : true
                    },
                    {
                        fieldLabel     : 'Exterior',
                        xtype          : 'textfield',
                        name           : 'smap1.exterior',
                        allowBlank     : false
                    },
                    {
                        fieldLabel     : 'Colonia',
                        xtype          : 'textfield',
                        name           : 'smap1.colonia',
                        allowBlank     : false
                    },
                    {
                        fieldLabel     : 'Delegaci&oacute;n / Municipio',
                        xtype          : 'textfield',
                        name           : 'smap1.delegacion',
                        allowBlank     : false
                    },
                    {
                        fieldLabel     : 'Estado / Ciudad',
                        xtype          : 'textfield',
                        name           : 'smap1.ciudad',
                        allowBlank     : false
                    }
                ]
            }),
        ],
        buttons:
        [
            {
                text:'Regresar',
                icon: 'resources/extjs4/resources/ext-theme-neptune/images/toolbar/scroll-left.png',
                handler:function()
                {
                    Ext.create('Ext.form.Panel').submit(
                    {
                        url : urlRegresar,
                        standardSubmit:true,
                        params:
                        {
                            'map1.cdunieco' : inputCdunieco,
                            'map1.cdramo'   : inputCdramo,
                            'map1.estado'   : inputEstado,
                            'map1.nmpoliza' : inputNmpoliza
                        }
                    });
                }
            },
            {
                text:'Guardar cambios',
                icon: 'resources/fam3icons/icons/accept.png',
                handler:function()
                {
                    if(this.up().up().getForm().isValid())
                    {
                        this.up().up().setLoading(true);
                        this.up().up().getForm().submit(
                        {
                            params:
                            {
                                'smap1.pv_cdunieco' : inputCdunieco,
                                'smap1.pv_cdramo'   : inputCdramo,
                                'smap1.pv_estado'   : inputEstado,
                                'smap1.pv_nmpoliza' : inputNmpoliza,
                                'smap1.pv_nmsituac' : inputNmsituac,
                                'smap1.pv_cdperson' : inputCdperson
                            },
                            success:function(response,opts)
                            {
                                formPanel.setLoading(false);
                                var json=Ext.decode(opts.response.responseText);
                                if(json.success==true)
                                {
                                    Ext.Msg.show({
                                        title:'Datos guardados',
                                        msg: 'Se han guardado los datos',
                                        buttons: Ext.Msg.OK
                                    });
                                }
                                else
                                {
                                    Ext.Msg.show({
                                        title:'Error',
                                        msg: 'Error al guardar la informaci&oacute;n',
                                        buttons: Ext.Msg.OK,
                                        icon: Ext.Msg.ERROR
                                    });
                                }
                            },
                            failure:function(response,opts)
                            {
                                formPanel.setLoading(false);
                                Ext.Msg.show({
                                    title:'Error',
                                    msg: 'Error al guardar la informaci&oacute;n',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.ERROR
                                });
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
        ]
    });
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////
    
    //////////////////////
    ////// cargador //////
    /*//////////////////*/
    Ext.define('LoaderForm',
    {
        extend:'Modelo1',
        proxy:
        {
            extraParams:
            {
                'smap1.pv_cdunieco' : inputCdunieco,
                'smap1.pv_cdramo'   : inputCdramo,
                'smap1.pv_estado'   : inputEstado,
                'smap1.pv_nmpoliza' : inputNmpoliza,
                'smap1.pv_nmsituac' : inputNmsituac,
                'smap1.pv_cdperson' : inputCdperson
            },
            type:'ajax',
            url : urlCargar,
            reader:
            {
                type:'json'
            }
        }
    });

    var loaderForm=Ext.ModelManager.getModel('LoaderForm');
    loaderForm.load(123, {
        success: function(resp) {
            //console.log(resp);
            formPanel.loadRecord(resp);
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
    /*//////////////////*/
    ////// cargador //////
    //////////////////////

});
</script>
    </head>
    <body>
        <div id="maindiv" height="500"></div>
    </body>
</html>