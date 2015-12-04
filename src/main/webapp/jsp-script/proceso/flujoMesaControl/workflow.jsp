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
.estado .CDESTADOMC, .labelE
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
.pantalla .CDPANTMC, .labelP
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
.componente .CDCOMPMC, .labelC
{
    position    : absolute;
    left        : 50%;
    margin-left : -100px;
    border      : 0px solid red;
    top         : 15px;
    width       : 200px;
    text-align  : center;
}
.proceso .image
{
    width            : 65px;
    height           : 35px;
    background-image : url('${flujoimg}proceso.png');
}
.proceso .CDPROCMC, .labelO
{
    position    : absolute;
    left        : 50%;
    margin-left : -100px;
    border      : 0px solid red;
    top         : 10px;
    width       : 200px;
    text-align  : center;
}
.validacion .image
{
    width            : 50px;
    height           : 50px;
    background-image : url('${flujoimg}validacion.png');
}
.labelV
{
    position    : absolute;
    left        : 50%;
    margin-left : -100px;
    border      : 0px solid red;
    top         : 20px;
    width       : 200px;
    text-align  : center;
}
.revision .image
{
    width            : 45px;
    height           : 50px;
    background-image : url('${flujoimg}revision.png');
}
.labelR
{
    position    : absolute;
    left        : 50%;
    margin-left : -100px;
    border      : 0px solid red;
    top         : 20px;
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
.entidadO
{
    position         : absolute;
    width            : 65px;
    height           : 35px;
    border           : 0px solid red;
    background-image : url('${flujoimg}proceso.png');
}
.entidadV
{
    position         : absolute;
    width            : 50px;
    height           : 50px;
    border           : 0px solid red;
    background-image : url('${flujoimg}validacion.png');
}
.entidadR
{
    position         : absolute;
    width            : 45px;
    height           : 50px;
    border           : 0px solid red;
    background-image : url('${flujoimg}revision.png');
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
.catedit
{
    position : absolute;
    top      : 0;
    left     : 0;
}
</style>
<script type="text/javascript" src="${ctx}/resources/jsPlumb/jsPlumb-2.0.4.js?${now}"></script>
<script>
////// iframe //////
var stop = false;
if(inIframe())
{
    try
    {
        stop = true;
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
var _p52_urlRegistrarEntidad    = '<s:url namespace="/flujomesacontrol" action="registrarEntidad"    />';
var _p52_urlBorrarEntidad       = '<s:url namespace="/flujomesacontrol" action="borrarEntidad"       />';
var _p52_urlRegistrarConnection = '<s:url namespace="/flujomesacontrol" action="registrarConnection" />';
var _p52_urlBorrarConnection    = '<s:url namespace="/flujomesacontrol" action="borrarConnection"    />';
var _p52_urlGuardarCoords       = '<s:url namespace="/flujomesacontrol" action="guardarCoordenadas"  />';
////// urls //////

////// variables //////
var estadoTpl;
var pantallaTpl;
var componenteTpl;
var procesoTpl;
var validacionTpl;
var revisionTpl;

var _p52_panelGrids;
var _p52_gridTramites;
var _p52_gridProcesos;
var _p52_panelDibujo;
var _p52_panelCanvas;
var _p52_catalogoEstados;
var _p52_panelEstado;
var _p52_catalogoPantallas;
var _p52_catalogoComponentes;
var _p52_catalogoProcesos;
var _p52_catalogoValidaciones;
var _p52_catalogoRevisiones;
var _p52_formValidacion;
var _p52_panelRevision;
var _p52_panelAccion;
var _p52_catalogoIconos;

var toolkit;

var epProps = [];

var _p52_formTtipflumc;
var _p52_formTflujomc;
var _p52_selectedFlujo;
var _p52_formEstado;
var _p52_formComponente;
var _p52_formPantalla;
var _p52_formProceso;
////// variables //////

////// overrides //////
Ext.define('App.overrides.view.Table',
{
    override: 'Ext.view.Table',
    getRecord: function (node) {
        node = this.getNode(node);
        if (node) {
            //var recordIndex = node.getAttribute('data-recordIndex');
            //if (recordIndex) {
            //    recordIndex = parseInt(recordIndex, 10);
            //    if (recordIndex > -1) {
            //        // The index is the index in the original Store, not in a GroupStore
            //        // The Grouping Feature increments the index to skip over unrendered records in collapsed groups
            //        return this.store.data.getAt(recordIndex);
            //    }
            //}
            return this.dataSource.data.get(node.getAttribute('data-recordId'));
        }
    },
    indexInStore: function (node) {
        node = this.getNode(node, true);
        if (!node && node !== 0) {
            return -1;
        }
        //var recordIndex = node.getAttribute('data-recordIndex');
        //if (recordIndex) {
        //    return parseInt(recordIndex, 10);
        //}
        return this.dataSource.indexOf(this.getRecord(node));
    }
});
////// overrides //////

////// componentes dinamicos //////
////// componentes dinamicos //////

Ext.onReady(function()
{
    if(stop)
    {
        return;
    }
    ////// requires //////
    ////// requires //////
    
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    estadoTpl = new Ext.Template(
    [
         '<div id="E{CDESTADOMC}" class="catEntidad estado" draggable="true" ondragstart="_p52_dragstart(event);" descrip="{DSESTADOMC}">'
        ,'    <table width="90" border="0">'
        ,'        <tr>'
        ,'            <td align="center"><div class="image"></div><div class="CDESTADOMC">{CDESTADOMC}</div></td>'
        ,'        </tr>'
        ,'        <tr>'
        ,'            <td align="center"><a class="catedit" href="#" onclick="_p52_editCatClic(\'E\',\'E{CDESTADOMC}\'); return false;" ><img src="${icons}pencil.png" /></a>{DSESTADOMC}</td>'
        ,'        </tr>'
        ,'    </table>'
        ,'</div>'
    ]);
    
    pantallaTpl = new Ext.Template(
    [
         '<div id="P{CDPANTMC}" class="catEntidad pantalla" draggable="true" ondragstart="_p52_dragstart(event);" descrip="{DSPANTMC}">'
        ,'    <table width="90" border="0">'
        ,'        <tr>'
        ,'            <td align="center"><div class="image"></div><div class="CDPANTMC">{CDPANTMC}</div></td>'
        ,'        </tr>'
        ,'        <tr>'
        ,'            <td align="center"><a class="catedit" href="#" onclick="_p52_editCatClic(\'P\',\'P{CDPANTMC}\'); return false;" ><img src="${icons}pencil.png" /></a>{DSPANTMC}</td>'
        ,'        </tr>'
        ,'    </table>'
        ,'</div>'
    ]);
    
    componenteTpl = new Ext.Template(
    [
         '<div id="C{CDCOMPMC}" class="catEntidad componente" draggable="true" ondragstart="_p52_dragstart(event);" descrip="{DSCOMPMC}">'
        ,'    <table width="90" border="0">'
        ,'        <tr>'
        ,'            <td align="center"><div class="image"></div><div class="CDCOMPMC">{CDCOMPMC}</div></td>'
        ,'        </tr>'
        ,'        <tr>'
        ,'            <td align="center"><a class="catedit" href="#" onclick="_p52_editCatClic(\'C\',\'C{CDCOMPMC}\'); return false;" ><img src="${icons}pencil.png" /></a>{DSCOMPMC}</td>'
        ,'        </tr>'
        ,'    </table>'
        ,'</div>'
    ]);
    
    procesoTpl = new Ext.Template(
    [
         '<div id="O{CDPROCMC}" class="catEntidad proceso" draggable="true" ondragstart="_p52_dragstart(event);" descrip="{DSPROCMC}">'
        ,'    <table width="90" border="0">'
        ,'        <tr>'
        ,'            <td align="center"><div class="image"></div><div class="CDPROCMC">{CDPROCMC}</div></td>'
        ,'        </tr>'
        ,'        <tr>'
        ,'            <td align="center"><a class="catedit" href="#" onclick="_p52_editCatClic(\'O\',\'O{CDPROCMC}\'); return false;" ><img src="${icons}pencil.png" /></a>{DSPROCMC}</td>'
        ,'        </tr>'
        ,'    </table>'
        ,'</div>'
    ]);
    
    validacionTpl = new Ext.Template(
    [
         '<div id="V0" class="catEntidad validacion" draggable="true" ondragstart="_p52_dragstart(event);" descrip="{dsvalidacion}">'
        ,'    <table width="90" border="0">'
        ,'        <tr>'
        ,'            <td align="center"><div class="image"></div></td>'
        ,'        </tr>'
        ,'        <tr>'
        ,'            <td align="center">{dsvalidacion}</td>'
        ,'        </tr>'
        ,'    </table>'
        ,'</div>'
    ]);
    
    revisionTpl = new Ext.Template(
    [
         '<div id="R0" class="catEntidad revision" draggable="true" ondragstart="_p52_dragstart(event);" descrip="{dsrevision}">'
        ,'    <table width="90" border="0">'
        ,'        <tr>'
        ,'            <td align="center"><div class="image"></div></td>'
        ,'        </tr>'
        ,'        <tr>'
        ,'            <td align="center">{dsrevision}</td>'
        ,'        </tr>'
        ,'    </table>'
        ,'</div>'
    ]);
    
    iconoTpl = new Ext.Template(
    [
         '<div class="radioicono">'
        ,'    <table width="80" border="0">'
        ,'        <tr>'
        ,'            <td align="center"><img src="${icons}{cdicono}.png" /></td>'
        ,'        </tr>'
        ,'        <tr>'
        ,'            <td align="center"><input type="radio" name="iconoaccion" />{dsicono}</td>'
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
    
    epProps['O'] =
    {
        anchor     : [ 'Perimeter' , { shape : 'Rectangle' } ]
        ,isSource  : true
        ,isTarget  : true
    };
    
    epProps['V'] =
    {
        anchor     : [ 'Perimeter' , { shape : 'Diamond' } ]
        ,isSource  : true
        ,isTarget  : true
    };
    
    epProps['R'] =
    {
        anchor     : [ 'Perimeter' , { shape : 'Rectangle' } ]
        ,isSource  : true
        ,isTarget  : true
    };
    
    _p52_formTtipflumc = Ext.create('Ext.window.Window',
    {
        title        : 'TR\u00C1MITE'
        ,modal       : true
        ,closeAction : 'hide'
        ,items       :
        [
            Ext.create('Ext.form.Panel',
            {
                defaults : { style : 'margin:5px;' }
                ,border  : 0
                ,items   :
                [
                    <s:property value="items.ttipfluFormItems" escapeHtml="false" />
                    ,{
                        xtype       : 'fieldcontainer'
                        ,fieldLabel : 'PROPIEDADES'
                        ,items      :
                        [
                            {
                                xtype       : 'checkbox'
                                ,boxLabel   : 'REQUIERE P\u00d3LIZA'
                                ,name       : 'SWREQPOL'
                                ,inputValue : 'S'
                            }
                            ,{
                                xtype       : 'checkbox'
                                ,boxLabel   : 'M\u00DALTIPLES P\u00d3LIZAS'
                                ,name       : 'SWMULTPOL'
                                ,inputValue : 'S'
                                ,align      : 'right'
                            }
                        ]
                    }
                ]
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text  : 'Guardar'
                        ,icon : '${icons}disk.png'
                    }
                ]
            })
        ]
        ,showNew : function()
        {
            var me = this;
            me.down('form').getForm().reset();
            me.down('[name=ACCION]').setValue('I');
            centrarVentanaInterna(me.show());
        }
        ,showEdit : function(record)
        {
            var me = this;
            me.down('form').getForm().loadRecord(record);
            me.down('[name=ACCION]').setValue('U');
            centrarVentanaInterna(me.show());
        }
    });
    
    _p52_formTflujomc = Ext.create('Ext.window.Window',
    {
        title        : 'PROCESO'
        ,modal       : true
        ,closeAction : 'hide'
        ,items       :
        [
            Ext.create('Ext.form.Panel',
            {
                defaults : { style : 'margin:5px;' }
                ,border  : 0
                ,items   :
                [
                    {
                        xtype       : 'textfield'
                        ,fieldLabel : '_ACCION'
                        ,name       : 'ACCION'
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : '_CDTIPFLU'
                        ,name       : 'CDTIPFLU'
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : '_CDFLUJOMC'
                        ,name       : 'CDFLUJOMC'
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : 'NOMBRE'
                        ,name       : 'DSFLUJOMC'
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'checkbox'
                        ,boxLabel   : 'VISIBLE'
                        ,fieldLabel : 'PROPIEDADES'
                        ,name       : 'SWFINAL'
                        ,inputValue : 'S'
                    }
                ]
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text  : 'Guardar'
                        ,icon : '${icons}disk.png'
                    }
                ]
            })
        ]
        ,showNew : function()
        {
            var ck = 'Mostrando formulario';
            try
            {
                var sel = _p52_gridTramites.getSelectionModel().getSelection();
                if(sel.length==0)
                {
                    throw 'Debe seleccionar un tr\u00e1mite';
                }
                
                var me = this;
                me.down('form').getForm().reset();
                me.down('[name=ACCION]').setValue('I');
                me.down('[name=CDTIPFLU]').setValue(sel[0].get('CDTIPFLU'));
                centrarVentanaInterna(me.show());
            }
            catch(e)
            {
                manejaException(e,ck);
            }
        }
        ,showEdit : function(record)
        {
            var me = this;
            me.down('form').getForm().loadRecord(record);
            me.down('[name=ACCION]').setValue('U');
            centrarVentanaInterna(me.show());
        }
    });
    
    _p52_formEstado = Ext.create('Ext.window.Window',
    {
        title        : 'STATUS'
        ,modal       : true
        ,closeAction : 'hide'
        ,items       :
        [
            Ext.create('Ext.form.Panel',
            {
                defaults : { style : 'margin:5px;' }
                ,border  : 0
                ,items   :
                [
                    {
                        xtype       : 'textfield'
                        ,fieldLabel : '_ACCION'
                        ,name       : 'ACCION'
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : '_CDESTADOMC'
                        ,name       : 'CDESTADOMC'
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : 'NOMBRE'
                        ,name       : 'DSESTADOMC'
                        ,allowBlank : false
                    }
                ]
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text  : 'Guardar'
                        ,icon : '${icons}disk.png'
                    }
                ]
            })
        ]
        ,showNew : function()
        {
            var ck = 'Mostrando formulario';
            try
            {
                var me = this;
                me.down('form').getForm().reset();
                me.down('[name=ACCION]').setValue('I');
                centrarVentanaInterna(me.show());
            }
            catch(e)
            {
                manejaException(e,ck);
            }
        }
        ,showEdit : function(data)
        {
            var me = this;
            me.down('form').getForm().loadRecord(data);
            me.down('[name=ACCION]').setValue('U');
            centrarVentanaInterna(me.show());
        }
    });
    
    _p52_formComponente = Ext.create('Ext.window.Window',
    {
        title        : 'COMPONENTE'
        ,modal       : true
        ,closeAction : 'hide'
        ,items       :
        [
            Ext.create('Ext.form.Panel',
            {
                defaults : { style : 'margin:5px;' }
                ,border  : 0
                ,items   :
                [
                    {
                        xtype       : 'textfield'
                        ,fieldLabel : '_ACCION'
                        ,name       : 'ACCION'
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : '_CDCOMPMC'
                        ,name       : 'CDCOMPMC'
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : 'NOMBRE'
                        ,name       : 'DSCOMPMC'
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : 'CLASE'
                        ,name       : 'NOMCOMP'
                        ,allowBlank : false
                    }
                ]
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text  : 'Guardar'
                        ,icon : '${icons}disk.png'
                    }
                ]
            })
        ]
        ,showNew : function()
        {
            var ck = 'Mostrando formulario';
            try
            {
                var me = this;
                me.down('form').getForm().reset();
                me.down('[name=ACCION]').setValue('I');
                centrarVentanaInterna(me.show());
            }
            catch(e)
            {
                manejaException(e,ck);
            }
        }
        ,showEdit : function(data)
        {
            var me = this;
            me.down('form').getForm().loadRecord(data);
            me.down('[name=ACCION]').setValue('U');
            centrarVentanaInterna(me.show());
        }
    });
    
    _p52_formPantalla = Ext.create('Ext.window.Window',
    {
        title        : 'PANTALLA'
        ,modal       : true
        ,closeAction : 'hide'
        ,items       :
        [
            Ext.create('Ext.form.Panel',
            {
                defaults : { style : 'margin:5px;' }
                ,border  : 0
                ,items   :
                [
                    {
                        xtype       : 'textfield'
                        ,fieldLabel : '_ACCION'
                        ,name       : 'ACCION'
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : '_CDPANTMC'
                        ,name       : 'CDPANTMC'
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : 'NOMBRE'
                        ,name       : 'DSPANTMC'
                        ,width      : 500
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : 'URL'
                        ,name       : 'URLPANTMC'
                        ,width      : 500
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'fieldcontainer'
                        ,fieldLabel : 'PROPIEDADES'
                        ,items      :
                        [
                            {
                                xtype       : 'checkbox'
                                ,boxLabel   : 'EXTERNA'
                                ,name       : 'SWEXTERNA'
                                ,inputValue : 'S'
                            }
                        ]
                    }
                ]
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text  : 'Guardar'
                        ,icon : '${icons}disk.png'
                    }
                ]
            })
        ]
        ,showNew : function()
        {
            var ck = 'Mostrando formulario';
            try
            {
                var me = this;
                me.down('form').getForm().reset();
                me.down('[name=ACCION]').setValue('I');
                centrarVentanaInterna(me.show());
            }
            catch(e)
            {
                manejaException(e,ck);
            }
        }
        ,showEdit : function(data)
        {
            var me = this;
            me.down('form').getForm().loadRecord(data);
            me.down('[name=ACCION]').setValue('U');
            centrarVentanaInterna(me.show());
        }
    });
    
    _p52_formProceso = Ext.create('Ext.window.Window',
    {
        title        : 'PROCESO'
        ,modal       : true
        ,closeAction : 'hide'
        ,items       :
        [
            Ext.create('Ext.form.Panel',
            {
                defaults : { style : 'margin:5px;' }
                ,border  : 0
                ,items   :
                [
                    {
                        xtype       : 'textfield'
                        ,fieldLabel : '_ACCION'
                        ,name       : 'ACCION'
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : '_CDPROCMC'
                        ,name       : 'CDPROCMC'
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : 'NOMBRE'
                        ,name       : 'DSPROCMC'
                        ,width      : 500
                        ,allowBlank : false
                    }
                    ,{
                        xtype       : 'textfield'
                        ,fieldLabel : 'URL'
                        ,name       : 'URLPROCMC'
                        ,width      : 500
                        ,allowBlank : false
                    }
                ]
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text  : 'Guardar'
                        ,icon : '${icons}disk.png'
                    }
                ]
            })
        ]
        ,showNew : function()
        {
            var ck = 'Mostrando formulario';
            try
            {
                var me = this;
                me.down('form').getForm().reset();
                me.down('[name=ACCION]').setValue('I');
                centrarVentanaInterna(me.show());
            }
            catch(e)
            {
                manejaException(e,ck);
            }
        }
        ,showEdit : function(data)
        {
            var me = this;
            me.down('form').getForm().loadRecord(data);
            me.down('[name=ACCION]').setValue('U');
            centrarVentanaInterna(me.show());
        }
    });
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
                                xtype    : 'button'
                                ,text    : 'Agregar'
                                ,icon    : '${icons}add.png'
                                ,handler : function(){ _p52_formTtipflumc.showNew(); }
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
                                ,handler : function(me,row,col,item,e,record)
                                {
                                    _p52_formTtipflumc.showEdit(record);
                                }
                            }
                        ]
                        ,store : Ext.create('Ext.data.Store',
                        {
                            autoLoad : true
                            ,fields  :
                            [
                                'CDTIPFLU'
                                ,'DSTIPFLU'
                                ,'CDTIPTRA'
                                ,'SWMULTPOL'
                                ,'SWREQPOL'
                            ]
                            ,proxy   :
                            {
                                type    : 'memory'
                                ,reader : 'json'
                                ,data   :
                                [
                                    {
                                        CDTIPFLU   : 1
                                        ,DSTIPFLU  : 'POLIZA NUEVA'
                                        ,CDTIPTRA  : 2
                                        ,SWMULTPOL : 'S'
                                        ,SWREQPOL  : 'N'
                                    }
                                    ,{
                                        CDTIPFLU   : 2
                                        ,DSTIPFLU  : 'CAMBIO DE CONTRATANTE'
                                        ,CDTIPTRA  : 2
                                        ,SWMULTPOL : 'N'
                                        ,SWREQPOL  : 'S'
                                    }
                                ]
                            }
                        })
                        ,listeners :
                        {
                            select : function(me,record)
                            {
                                _p52_gridProcesos.store.removeAll();
                                _p52_gridProcesos.store.add(
                                    {
                                        CDTIPFLU   : 1
                                        ,CDFLUJOMC : 1
                                        ,DSFLUJOMC : 'EMISION SALUD VITAL'
                                        ,SWFINAL   : 'S'
                                    }
                                    ,{
                                        CDTIPFLU   : 1
                                        ,CDFLUJOMC : 2
                                        ,DSFLUJOMC : 'EMISION SALUD COLECTIVO'
                                        ,SWFINAL   : 'N'
                                    }
                                );
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
                                xtype    : 'button'
                                ,text    : 'Agregar'
                                ,icon    : '${icons}add.png'
                                ,handler : function(me){ _p52_formTflujomc.showNew(); }
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
                                ,width   : 50
                                ,items   :
                                [
                                    {
                                        icon    : '${icons}pencil.png'
                                        ,tooltip : 'Editar'
                                        ,handler : function(view,row,col,item,e,record)
                                        {
                                            _p52_formTflujomc.showEdit(record);
                                        }
                                    }
                                    ,{
                                        icon    : '${icons}chart_line.png'
                                        ,tooltip : 'Modelar'
                                        ,handler : function(view,row,col,item,e,record)
                                        {
                                            _p52_selectedFlujo = record;
                                            _p52_panelDibujo.setTitle(record.get('DSFLUJOMC'));
                                            _p52_navega(2);
                                        }
                                    }
                                ]
                            }
                        ]
                        ,store : Ext.create('Ext.data.Store',
                        {
                            autoLoad : true
                            ,fields  :
                            [
                                'CDTIPFLU'
                                ,'CDFLUJOMC'
                                ,'DSFLUJOMC'
                                ,'SWFINAL'
                            ]
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
                ,title      : ''
                ,titleAlign : 'center'
                ,region     : 'center'
                ,hidden     : true
                ,layout     : 'border'
                ,border     : 0
                ,tools      :
                [{
                    type     : 'gear'
                    ,tooltip : 'Editar'
                    ,handler : function()
                    {
                        var ck = 'Editando proceso';
                        try
                        {
                            _p52_formTflujomc.showEdit(_p52_selectedFlujo);
                        }
                        catch(e)
                        {
                            manejaException(e,ck);
                        }
                    }
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
                                ,tools :
                                [{
                                    type      : 'collapse'
                                    ,tooltip  : 'Agregar'
                                    ,callback : function(panel)
                                    {
                                        _p52_editCatClic('E');
                                    }
                                }]
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
                                ,tools :
                                [{
                                    type      : 'collapse'
                                    ,tooltip  : 'Agregar'
                                    ,callback : function(panel)
                                    {
                                        _p52_editCatClic('P');
                                    }
                                }]
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
                                ,tools :
                                [{
                                    type      : 'collapse'
                                    ,tooltip  : 'Agregar'
                                    ,callback : function(panel)
                                    {
                                        _p52_editCatClic('C');
                                    }
                                }]
                            }
                            ,{
                                title       : 'PROCESOS'
                                ,itemId     : '_p52_catalogoProcesos'
                                ,defaults   : { style : 'margin : 5px;' }
                                ,autoScroll : true
                                ,layout     :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                    ,tdAttrs : { valign : 'top' }
                                }
                                ,tools :
                                [{
                                    type      : 'collapse'
                                    ,tooltip  : 'Agregar'
                                    ,callback : function(panel)
                                    {
                                        _p52_editCatClic('O');
                                    }
                                }]
                            }
                            ,{
                                title       : 'VALIDACIONES'
                                ,itemId     : '_p52_catalogoValidaciones'
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
                                title       : 'REVISIONES'
                                ,itemId     : '_p52_catalogoRevisiones'
                                ,defaults   : { style : 'margin : 5px;' }
                                ,autoScroll : true
                                ,layout     :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                    ,tdAttrs : { valign : 'top' }
                                }
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
                                        xtype     : 'form'
                                        ,defaults : { style : 'margin:5px;' }
                                        ,border   : 0
                                        ,items    :
                                        [
                                            {
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_ACCION'
                                                ,name       : 'ACCION'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_CDTIPFLU'
                                                ,name       : 'CDTIPFLU'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_CDFLUJOMC'
                                                ,name       : 'CDFLUJOMC'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_CDESTADO'
                                                ,name       : 'CDESTADO'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_WEBID'
                                                ,name       : 'WEBID'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_XPOS'
                                                ,name       : 'XPOS'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_YPOS'
                                                ,name       : 'YPOS'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo m\u00e1ximo en horas'
                                                ,minValue   : 0
                                                ,maxValue   : 40
                                                ,increment  : 1
                                                ,name       : 'TIMEMAXH'
                                                ,labelAlign : 'top'
                                            }
                                            ,{
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo m\u00e1ximo en minutos'
                                                ,minValue   : 0
                                                ,maxValue   : 60
                                                ,increment  : 5
                                                ,name       : 'TIMEMAXM'
                                                ,labelAlign : 'top'
                                            }
                                            ,{
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo primer alerta en horas'
                                                ,minValue   : 0
                                                ,maxValue   : 40
                                                ,increment  : 1
                                                ,name       : 'TIMEWRN1H'
                                                ,labelAlign : 'top'
                                            }
                                            ,{
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo primer alerta en minutos'
                                                ,minValue   : 0
                                                ,maxValue   : 60
                                                ,increment  : 5
                                                ,name       : 'TIMEWRN1M'
                                                ,labelAlign : 'top'
                                            }
                                            ,{
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo segunda alerta en horas'
                                                ,minValue   : 0
                                                ,maxValue   : 40
                                                ,increment  : 1
                                                ,name       : 'TIMEWRN2H'
                                                ,labelAlign : 'top'
                                            }
                                            ,{
                                                xtype       : 'slider'
                                                ,fieldLabel : 'Tiempo segunda alerta en minutos'
                                                ,minValue   : 00
                                                ,maxValue   : 60
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
                                                ,fieldLabel  : 'Tipo de asignaci\u00f3n'
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
                                                    }
                                                    ,{
                                                        boxLabel    : 'Carrusel'
                                                        ,name       : 'CDTIPASIG'
                                                        ,inputValue : 3
                                                    }
                                                    ,{
                                                        boxLabel    : 'Carga'
                                                        ,name       : 'CDTIPASIG'
                                                        ,inputValue : '4'
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
                                                text       : 'TRAB.'
                                                ,xtype     : 'checkcolumn'
                                                ,dataIndex : 'SWTRABAJO'
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
                                                ,{ name : 'SWTRABAJO' , type : 'boolean' }
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
                            ,Ext.create('Ext.form.Panel',
                            {
                                itemId       : '_p52_formValidacion'
                                ,title       : 'VALIDACI\u00D3N'
                                ,defaults    : { style : 'margin:5px;' }
                                ,buttonAlign : 'center'
                                ,hidden      : true
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
                                ,items :
                                [
                                    {
                                        xtype       : 'textfield'
                                        ,fieldLabel : '_ACCION'
                                        ,name       : 'ACCION'
                                        ,allowBlank : false
                                    }
                                    ,{
                                        xtype       : 'textfield'
                                        ,fieldLabel : '_CDTIPFLU'
                                        ,name       : 'CDTIPFLU'
                                        ,allowBlank : false
                                    }
                                    ,{
                                        xtype       : 'textfield'
                                        ,fieldLabel : '_CDFLUJOMC'
                                        ,name       : 'CDFLUJOMC'
                                        ,allowBlank : false
                                    }
                                    ,{
                                        xtype       : 'textfield'
                                        ,fieldLabel : '_CDVALIDA'
                                        ,name       : 'CDVALIDA'
                                        ,allowBlank : false
                                    }
                                    ,{
                                        xtype       : 'textfield'
                                        ,fieldLabel : '_WEBID'
                                        ,name       : 'WEBID'
                                        ,allowBlank : false
                                    }
                                    ,{
                                        xtype       : 'textfield'
                                        ,fieldLabel : '_XPOS'
                                        ,name       : 'XPOS'
                                        ,allowBlank : false
                                    }
                                    ,{
                                        xtype       : 'textfield'
                                        ,fieldLabel : '_YPOS'
                                        ,name       : 'YPOS'
                                        ,allowBlank : false
                                    }
                                    ,{
                                        xtype       : 'textfield'
                                        ,fieldLabel : 'NOMBRE'
                                        ,labelAlign : 'top'
                                        ,name       : 'DSVALIDA'
                                        ,allowBlank : false
                                    }
                                    ,{
                                        xtype       : 'textfield'
                                        ,fieldLabel : 'EXPRESI\u00D3N'
                                        ,labelAlign : 'top'
                                        ,name       : 'CDEXPRES'
                                        ,allowBlank : false
                                    }
                                ]
                            })
                            ,Ext.create('Ext.panel.Panel',
                            {
                                itemId       : '_p52_panelRevision'
                                ,title       : 'REVISI\u00D3N'
                                ,hidden      : true
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
                                ,items :
                                [
                                    {
                                        xtype     : 'form'
                                        ,border   : 0
                                        ,defaults : { style : 'margin:5px;' }
                                        ,items    :
                                        [
                                            {
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_ACCION'
                                                ,name       : 'ACCION'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_CDTIPFLU'
                                                ,name       : 'CDTIPFLU'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_CDFLUJOMC'
                                                ,name       : 'CDFLUJOMC'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_CDREVISI'
                                                ,name       : 'CDESTADO'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_WEBID'
                                                ,name       : 'WEBID'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_XPOS'
                                                ,name       : 'XPOS'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_YPOS'
                                                ,name       : 'YPOS'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : 'Nombre'
                                                ,labelAlign : 'top'
                                                ,name       : 'DSREVISI'
                                                ,allowBlank : false
                                            }
                                        ]
                                    }
                                    ,Ext.create('Ext.grid.Panel',
                                    {
                                        itemId    : '_p52_gridRevDoc'
                                        ,title    : 'DOCUMENTOS'
                                        ,height   : 220
                                        ,features :
                                        [{
                                            ftype           : 'groupingsummary'
                                            ,startCollapsed : true
                                            ,groupHeaderTpl :
                                            [
                                                '{name}'
                                            ]
                                        }]
                                        ,columns :
                                        [
                                            {
                                                text       : 'DOCUMENTO'
                                                ,dataIndex : 'DSDOCUME'
                                                ,flex      : 1
                                            }
                                            ,{
                                                text       : 'OBLIGA.'
                                                ,xtype     : 'checkcolumn'
                                                ,dataIndex : 'SWOBLIGA'
                                                ,width     : 70
                                            }
                                        ]
                                        ,store : Ext.create('Ext.data.Store',
                                        {
                                            autoLoad    : true
                                            ,groupField : 'DSTIPTRA'
                                            ,fields     :
                                            [
                                                'CDDOCUME'
                                                ,'DSDOCUME'
                                                ,'CDTIPTRA'
                                                ,'DSTIPTRA'
                                                ,{ name : 'SWOBLIGA'  , type : 'boolean' }
                                            ]
                                            ,proxy   :
                                            {
                                                type    : 'memory'
                                                ,reader : 'json'
                                                ,data   :
                                                [
                                                    {
                                                        DSTIPTRA  : 'EMISION'
                                                        ,DSDOCUME : 'CRENDENCIAL ELECTOR'
                                                    }
                                                    ,{
                                                        DSTIPTRA  : 'EMISION'
                                                        ,DSDOCUME : 'SOLICITUD DE COTIZACION'
                                                    }
                                                    ,{
                                                        DSTIPTRA  : 'EMISION'
                                                        ,DSDOCUME : 'CURP'
                                                    }
                                                    ,{
                                                        DSTIPTRA  : 'EMISION'
                                                        ,DSDOCUME : 'RFC'
                                                    }
                                                    ,{
                                                        DSTIPTRA  : 'SINIESTROS'
                                                        ,DSDOCUME : 'INFORME MEDICO'
                                                    }
                                                    ,{
                                                        DSTIPTRA  : 'SINIESTROS'
                                                        ,DSDOCUME : 'CERTIFICADO ESTUDIOS'
                                                    }
                                                    ,{
                                                        DSTIPTRA  : 'SINIESTROS'
                                                        ,DSDOCUME : 'AUTORIZACION SERVICIOS'
                                                    }
                                                    ,{
                                                        DSTIPTRA  : 'SINIESTROS'
                                                        ,DSDOCUME : 'FACTURA'
                                                    }
                                                    ,{
                                                        DSTIPTRA  : 'SINIESTROS'
                                                        ,DSDOCUME : 'ORDEN DE SERVICIO'
                                                    }
                                                ]
                                            }
                                        })
                                    })
                                ]
                            })
                            ,Ext.create('Ext.panel.Panel',
                            {
                                itemId       : '_p52_panelAccion'
                                ,title       : 'ACCI\u00D3N'
                                ,hidden      : true
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
                                ,items :
                                [
                                    {
                                        xtype     : 'form'
                                        ,border   : 0
                                        ,defaults : { style : 'margin:5px;' }
                                        ,items    :
                                        [
                                            {
		                                        xtype       : 'textfield'
		                                        ,fieldLabel : '_ACCION'
		                                        ,name       : 'ACCION'
		                                        ,allowBlank : false
		                                    }
		                                    ,{
		                                        xtype       : 'textfield'
		                                        ,fieldLabel : '_CDTIPFLU'
		                                        ,name       : 'CDTIPFLU'
		                                        ,allowBlank : false
		                                    }
		                                    ,{
		                                        xtype       : 'textfield'
		                                        ,fieldLabel : '_CDFLUJOMC'
		                                        ,name       : 'CDFLUJOMC'
		                                        ,allowBlank : false
		                                    }
		                                    ,{
		                                        xtype       : 'textfield'
		                                        ,fieldLabel : '_CDACCION'
		                                        ,name       : 'CDACCION'
		                                        ,allowBlank : false
		                                    }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_IDORIGEN'
                                                ,name       : 'IDORIGEN'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : '_IDDESTIN'
                                                ,name       : 'IDDESTIN'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : 'NOMBRE'
                                                ,labelAlign : 'top'
                                                ,name       : 'DSACCION'
                                                ,allowBlank : false
                                            }
                                            ,{
                                                xtype       : 'textfield'
                                                ,fieldLabel : 'VALOR'
                                                ,labelAlign : 'top'
                                                ,name       : 'CDVALOR'
                                            }
                                        ]
                                    }
                                    ,Ext.create('Ext.grid.Panel',
                                    {
                                        itemId   : '_p52_gridAccRol'
                                        ,title   : 'PERMISOS'
                                        ,height  : 220
                                        ,columns :
                                        [
                                            {
                                                text       : 'ROL'
                                                ,dataIndex : 'DSSISROL'
                                                ,width     : 200
                                            }
                                            ,{
                                                xtype      : 'checkcolumn'
                                                ,dataIndex : 'SWPERMISO'
                                                ,flex      : 1
                                            }
                                        ]
                                        ,store : Ext.create('Ext.data.Store',
                                        {
                                            autoLoad : true
                                            ,fields  :
                                            [
                                                'CDSISROL'
                                                ,'DSSISROL'
                                                ,{ name : 'SWPERMISO' , type : 'boolean' }
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
                                    ,Ext.create('Ext.panel.Panel',
                                    {
                                        itemId      : '_p52_catalogoIconos'
                                        ,title      : 'ICONO'
                                        ,height     : 200
                                        ,defaults   : { style : 'margin : 5px;' }
                                        ,autoScroll : true
                                        ,border     : 0
                                        ,layout     :
                                        {
                                            type     : 'table'
                                            ,columns : 3
                                            ,tdAttrs : { valign : 'top' }
                                        }
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
                        text     : 'Guardar coordenadas'
                        ,icon    : '${icons}disk.png'
                        ,handler : function(me)
                        {
                            _p52_guardarCoords();
                        }
                    }
                    ,{
                        text     : 'Guardar coordenadas y regresar'
                        ,icon    : '${icons}disk.png'
                        ,handler : function(me)
                        {
                            _p52_guardarCoords(function()
                            {
                                _p52_navega(1);
                            });
                        }
                    }
                    ,{
                        text  : 'Salir sin guardar coordenadas'
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
    _p52_panelGrids           = _fieldById('_p52_panelGrids');
    _p52_gridTramites         = _fieldById('_p52_gridTramites');
    _p52_gridProcesos         = _fieldById('_p52_gridProcesos');
    _p52_panelDibujo          = _fieldById('_p52_panelDibujo');
    _p52_catalogoEstados      = _fieldById('_p52_catalogoEstados');
    _p52_panelCanvas          = _fieldById('_p52_panelCanvas');
    _p52_panelEstado          = _fieldById('_p52_panelEstado');
    _p52_catalogoPantallas    = _fieldById('_p52_catalogoPantallas');
    _p52_catalogoComponentes  = _fieldById('_p52_catalogoComponentes');
    _p52_catalogoProcesos     = _fieldById('_p52_catalogoProcesos');
    _p52_catalogoValidaciones = _fieldById('_p52_catalogoValidaciones');
    _p52_catalogoRevisiones   = _fieldById('_p52_catalogoRevisiones');
    _p52_formValidacion       = _fieldById('_p52_formValidacion');
    _p52_panelRevision        = _fieldById('_p52_panelRevision');
    _p52_panelAccion          = _fieldById('_p52_panelAccion');
    _p52_catalogoIconos       = _fieldById('_p52_catalogoIconos');
    ////// custom //////
    
    ////// loaders //////
    _p52_cargarEstados();
    _p52_cargarPantallas();
    _p52_cargarComponentes();
    _p52_cargarProcesos();
    _p52_cargarValidaciones();
    _p52_cargarRevisiones();
    _p52_cargarIconos();
    
    //_p52_navega(2);
    
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
        
        toolkit.bind('dblclick',function(con)
        {
            debug('dblclick con:',con);
            _p52_editEndpoint(con,'A');
        });
        
        toolkit.bind('connection',function(con)
        {
            debug('connection con:',con,'.');
            _p52_registrarConnection(con);
        });
        
        toolkit.bind('connectionDetached',function(con)
        {
            debug('connectionDetached con:',con,'.');
            _p52_borrarConnection(con);
        });
        
        toolkit.bind('connectionMoved',function(con)
        {
            debug('connectionMoved con:',con,'.');
            _p52_borrarConnection(
            {
                sourceId  : con.originalSourceId
                ,targetId : con.originalTargetId
            });
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
        ,itemId : 'E100'
        ,data   :
        {
            CDESTADOMC  : 100
            ,DSESTADOMC : 'NUEVO'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : estadoTpl
        ,border : 0
        ,itemId : 'E101'
        ,data   :
        {
            CDESTADOMC  : 101
            ,DSESTADOMC : 'EN REVISION'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : estadoTpl
        ,border : 0
        ,itemId : 'E99'
        ,data   :
        {
            CDESTADOMC  : 999
            ,DSESTADOMC : 'CONFIRMADO'
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
	        ,itemId : 'P45'
	        ,data   :
	        {
	            CDPANTMC   : 45
	            ,DSPANTMC  : 'DATOS COMPLEMENTARIOS'
	            ,URLPANTMC : '/emision/complementarios.action'
	            ,SWEXTERNA : 'N'
	        }
	    }
	    ,{
	        xtype   : 'panel'
	        ,tpl    : pantallaTpl
	        ,border : 0
            ,itemId : 'P86'
	        ,data   :
	        {
	            CDPANTMC  : 86
	            ,DSPANTMC : 'COTIZACION AUTO INDIVIDUAL'
	            ,URLPANTMC : '/emision/cotizacionAutoIndividual.action'
	            ,SWEXTERNA : 'N'
	        }
	    }
	    ,{
	        xtype   : 'panel'
	        ,tpl    : pantallaTpl
	        ,border : 0
            ,itemId : 'P102'
	        ,data   :
	        {
	            CDPANTMC   : 102
	            ,DSPANTMC  : 'COTIZACION SALUD INDIVIDUAL'
	            ,URLPANTMC : '/emision/cotizacion.action'
	            ,SWEXTERNA : 'N'
	        }
	    }
	    ,{
	        xtype   : 'panel'
	        ,tpl    : pantallaTpl
	        ,border : 0
            ,itemId : 'P103'
	        ,data   :
	        {
	            CDPANTMC   : 103
	            ,DSPANTMC  : 'SISA'
	            ,URLPANTMC : 'http://sisa.com/pantalla1.jsp'
	            ,SWEXTERNA : 'S'
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
	        ,itemId : 'C1'
	        ,data   :
	        {
	            CDCOMPMC  : 1
	            ,DSCOMPMC : 'VENTANA DE DOCUMENTOS'
	            ,NOMCOMP  : 'VENTANA_DOCUMENTOS'
	        }
	    }
	    ,{
	        xtype   : 'panel'
	        ,tpl    : componenteTpl
	        ,border : 0
            ,itemId : 'C2'
	        ,data   :
	        {
	            CDCOMPMC  : 2
	            ,DSCOMPMC : 'VENTANA DE HISTORIAL'
                ,NOMCOMP  : 'VENTANA_HISTORIAL'
	        }
	    }
	    ,{
	        xtype   : 'panel'
	        ,tpl    : componenteTpl
	        ,border : 0
            ,itemId : 'C3'
	        ,data   :
	        {
	            CDCOMPMC  : 3
	            ,DSCOMPMC : 'VISTA PREVIA TARIFA'
	            ,NOMCOMP  : 'VP_TARIFA'
	        }
	    }
    );
}

function _p52_cargarProcesos()
{
    debug('_p52_cargarProcesos');
    _p52_catalogoProcesos.removeAll();
    _p52_catalogoProcesos.add(
	    {
	        xtype   : 'panel'
	        ,tpl    : procesoTpl
	        ,border : 0
	        ,itemId : 'O1'
	        ,data   :
	        {
	            CDPROCMC   : 1
	            ,DSPROCMC  : 'EMISION'
	            ,URLPROCMC : '/EMISION/PROCESOEMISION.ACTION'
	        }
	    }
	    ,{
	        xtype   : 'panel'
	        ,tpl    : procesoTpl
	        ,border : 0
            ,itemId : 'O2'
	        ,data   :
	        {
	            CDPROCMC   : 2
	            ,DSPROCMC  : 'WS SALUD'
                ,URLPROCMC : '/EMISION/EJECUTAWSSALUD.ACTION'
	        }
	    }
	    ,{
	        xtype   : 'panel'
	        ,tpl    : procesoTpl
	        ,border : 0
            ,itemId : 'O3'
	        ,data   :
	        {
	            CDPROCMC   : 3
	            ,DSPROCMC  : 'WS AUTOS'
                ,URLPROCMC : '/EMISION/EJECUTAWSSALUD.ACTION'
	        }
	    }
    );
}

function _p52_cargarValidaciones()
{
    debug('_p52_cargarValidaciones');
    _p52_catalogoValidaciones.removeAll();
    _p52_catalogoValidaciones.add(
    {
        xtype   : 'panel'
        ,tpl    : validacionTpl
        ,border : 0
        ,data   :
        {
            cdvalidacion  : 0
            ,dsvalidacion : 'Nueva validaci\u00f3n'
        }
    });
}

function _p52_cargarRevisiones()
{
    debug('_p52_cargarRevisiones');
    _p52_catalogoRevisiones.removeAll();
    _p52_catalogoRevisiones.add(
    {
        xtype   : 'panel'
        ,tpl    : revisionTpl
        ,border : 0
        ,data   :
        {
            cdrevision  : 0
            ,dsrevision : 'Nueva revisi\u00f3n'
        }
    });
}

function _p52_cargarIconos()
{
    debug('_p52_cargarIconos');
    _p52_catalogoIconos.removeAll();
    _p52_catalogoIconos.add(
    {
        xtype   : 'panel'
        ,tpl    : iconoTpl
        ,border : 0
        ,data   :
        {
            cdicono  : 'disk'
            ,dsicono : 'DISK'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : iconoTpl
        ,border : 0
        ,data   :
        {
            cdicono  : 'add'
            ,dsicono : 'ADD'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : iconoTpl
        ,border : 0
        ,data   :
        {
            cdicono  : 'cancel'
            ,dsicono : 'CANCEL'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : iconoTpl
        ,border : 0
        ,data   :
        {
            cdicono  : 'disk'
            ,dsicono : 'DISK'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : iconoTpl
        ,border : 0
        ,data   :
        {
            cdicono  : 'delete'
            ,dsicono : 'DELETE'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : iconoTpl
        ,border : 0
        ,data   :
        {
            cdicono  : 'arrow_left'
            ,dsicono : 'ARROW_LEFT'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : iconoTpl
        ,border : 0
        ,data   :
        {
            cdicono  : 'arrow_up'
            ,dsicono : 'ARROW_UP'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : iconoTpl
        ,border : 0
        ,data   :
        {
            cdicono  : 'delete'
            ,dsicono : 'DELETE'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : iconoTpl
        ,border : 0
        ,data   :
        {
            cdicono  : 'arrow_left'
            ,dsicono : 'ARROW_LEFT'
        }
    }
    ,{
        xtype   : 'panel'
        ,tpl    : iconoTpl
        ,border : 0
        ,data   :
        {
            cdicono  : 'arrow_up'
            ,dsicono : 'ARROW_UP'
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
        _p52_registrarEntidad(tipo,clave,id,x,y,function(json)
        {
            $('#canvasdiv').append('<div id="'+id+'" tipo="'+tipo+'" class="entidad entidad'+tipo+'" style="top:'+y+'px;left:'+x+'px;" title="'+descrip+'"><a href="#" onclick="_p52_addEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="plus"></a><a href="#" onclick="_p52_editEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="edit"></a><a class="remove" href="#" onclick="_p52_removeEndpoint(\''+id+'\',\''+tipo+'\');return false;"></a><div class="labelE">'+clave+' - '+descrip+'</div></div>');
            _p52_addEndpoint(id,tipo);
        });
    }
    else if(tipo=='P')
    {
        _p52_registrarEntidad(tipo,clave,id,x,y,function(json)
        {
            $('#canvasdiv').append('<div id="'+id+'" tipo="'+tipo+'" class="entidad entidad'+tipo+'" style="top:'+y+'px;left:'+x+'px;" title="'+descrip+'"><a href="#" onclick="_p52_addEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="plus"></a><a class="remove" href="#" onclick="_p52_removeEndpoint(\''+id+'\',\''+tipo+'\');return false;"></a><div class="labelP">'+clave+' - '+descrip+'</div></div>');
            _p52_addEndpoint(id,tipo);
        });
    }
    else if(tipo=='C')
    {
        _p52_registrarEntidad(tipo,clave,id,x,y,function(json)
        {
            $('#canvasdiv').append('<div id="'+id+'" tipo="'+tipo+'" class="entidad entidad'+tipo+'" style="top:'+y+'px;left:'+x+'px;" title="'+descrip+'"><a href="#" onclick="_p52_addEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="plus"></a><a class="remove" href="#" onclick="_p52_removeEndpoint(\''+id+'\',\''+tipo+'\');return false;"></a><div class="labelC">'+clave+' - '+descrip+'</div></div>');
            _p52_addEndpoint(id,tipo);
        });
    }
    else if(tipo=='O')
    {
        _p52_registrarEntidad(tipo,clave,id,x,y,function(json)
        {
            $('#canvasdiv').append('<div id="'+id+'" tipo="'+tipo+'" class="entidad entidad'+tipo+'" style="top:'+y+'px;left:'+x+'px;" title="'+descrip+'"><a href="#" onclick="_p52_addEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="plus"></a><a class="remove" href="#" onclick="_p52_removeEndpoint(\''+id+'\',\''+tipo+'\');return false;"></a><div class="labelO">'+clave+' - '+descrip+'</div></div>');
            _p52_addEndpoint(id,tipo);
        });
    }
    else if(tipo=='V')
    {
        _p52_registrarEntidad(tipo,clave,id,x,y,function(json)
        {
            $('#canvasdiv').append('<div id="'+id+'" tipo="'+tipo+'" class="entidad entidad'+tipo+'" style="top:'+y+'px;left:'+x+'px;" title="'+descrip+'"><a href="#" onclick="_p52_addEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="plus"></a><a href="#" onclick="_p52_editEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="edit"></a><a class="remove" href="#" onclick="_p52_removeEndpoint(\''+id+'\',\''+tipo+'\');return false;"></a><div class="labelV">'+descrip+'</div></div>');
            _p52_addEndpoint(id,tipo);
        });
    }
    else if(tipo=='R')
    {
        _p52_registrarEntidad(tipo,clave,id,x,y,function(json)
        {
            $('#canvasdiv').append('<div id="'+id+'" tipo="'+tipo+'" class="entidad entidad'+tipo+'" style="top:'+y+'px;left:'+x+'px;" title="'+descrip+'"><a href="#" onclick="_p52_addEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="plus"></a><a href="#" onclick="_p52_editEndpoint(\''+id+'\',\''+tipo+'\');return false;" class="edit"></a><a class="remove" href="#" onclick="_p52_removeEndpoint(\''+id+'\',\''+tipo+'\');return false;"></a><div class="labelR">'+descrip+'</div></div>');
            _p52_addEndpoint(id,tipo);
        });
    }
}

function _p52_generaId()
{
    return (new Date()).getTime()+'_'+(Math.floor(Math.random()*10000));
}

function _p52_addEndpoint(id,tipo)
{
    debug('_p52_addEndpoint id,tipo:',id,tipo);
    var ep = toolkit.addEndpoint(id,epProps[tipo]);
    toolkit.draggable(id);
    return ep;
}

function _p52_editEndpoint(id,tipo)
{
    debug('_p52_editEndpoint id,tipo:',id,tipo);
    if(tipo=='E')
    {
        _p52_panelCanvas.disable();
        _p52_panelEstado.show();
        _p52_formValidacion.hide();
        _p52_panelRevision.hide();
        _p52_panelAccion.hide();
    }
    else if(tipo=='V')
    {
        _p52_panelCanvas.disable();
        _p52_panelEstado.hide();
        _p52_formValidacion.show();
        _p52_panelRevision.hide();
        _p52_panelAccion.hide();
    }
    else if(tipo=='R')
    {
        _p52_panelCanvas.disable();
        _p52_panelEstado.hide();
        _p52_formValidacion.hide();
        _p52_panelRevision.show();
        _p52_panelAccion.hide();
    }
    else if(tipo=='A')
    {
        _p52_panelCanvas.disable();
        _p52_panelEstado.hide();
        _p52_formValidacion.hide();
        _p52_panelRevision.hide();
        _p52_panelAccion.show();
    }
}

function _p52_removeEndpoint(id,tipo)
{
    debug('_p52_removeEndpoint id,tipo:',id,tipo,'.');
    var ck = 'Borrando entidad';
    try
    {
        _setLoading(true,_p52_panelDibujo);
        Ext.Ajax.request(
        {
            url      : _p52_urlBorrarEntidad
            ,params  :
            {
                'params.cdtipflu'   : _p52_selectedFlujo.get('CDTIPFLU')
                ,'params.cdflujomc' : _p52_selectedFlujo.get('CDFLUJOMC')
                ,'params.tipo'      : tipo
                ,'params.webid'     : id
            }
            ,success : function(response)
            {
                _setLoading(false,_p52_panelDibujo);
                var ck = 'Decodificando respuesta al borrar entidad';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### -entidad:',json);
                    if(json.success==true)
                    {
                        toolkit.remove(id);
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
                _setLoading(false,_p52_panelDibujo);
                errorComunicacion(null,'Error al borrar entidad');
            }
        });
    }
    catch(e)
    {
        _setLoading(false,_p52_panelDibujo);
        manejaException(e,ck);
    }
}

function _p52_editCatClic(tipo,id)
{
    debug('_p52_editCatClic tipo,id:',tipo,id,'.');
    
    if(tipo=='E')
    {
        if(Ext.isEmpty(id))
        {
            _p52_formEstado.showNew();
        }
        else
        {
            debug('recuperando:',_fieldById(id).initialConfig.data,'.');
            var rec =
            {
                datos    : _fieldById(id).initialConfig.data
                ,getData : function()
                {
                    return this.datos;
                }
            };
            _p52_formEstado.showEdit(rec);
        }
    }
    else if(tipo=='P')
    {
        if(Ext.isEmpty(id))
        {
            _p52_formPantalla.showNew();
        }
        else
        {
            debug('recuperando:',_fieldById(id).initialConfig.data,'.');
            var rec =
            {
                datos    : _fieldById(id).initialConfig.data
                ,getData : function()
                {
                    return this.datos;
                }
            };
            _p52_formPantalla.showEdit(rec);
        }
    }
    else if(tipo=='C')
    {
        if(Ext.isEmpty(id))
        {
            _p52_formComponente.showNew();
        }
        else
        {
            debug('recuperando:',_fieldById(id).initialConfig.data,'.');
            var rec =
            {
                datos    : _fieldById(id).initialConfig.data
                ,getData : function()
                {
                    return this.datos;
                }
            };
            _p52_formComponente.showEdit(rec);
        }
    }
    else if(tipo=='O')
    {
        if(Ext.isEmpty(id))
        {
            _p52_formProceso.showNew();
        }
        else
        {
            debug('recuperando:',_fieldById(id).initialConfig.data,'.');
            var rec =
            {
                datos    : _fieldById(id).initialConfig.data
                ,getData : function()
                {
                    return this.datos;
                }
            };
            _p52_formProceso.showEdit(rec);
        }
    }
    
}

function _p52_registrarEntidad(tipo,clave,id,x,y,callback)
{
    var ck = 'Regitrando entidad';
    try
    {
        _setLoading(true,_p52_panelDibujo);
        Ext.Ajax.request(
        {
            url     : _p52_urlRegistrarEntidad
            ,params :
            {
                'params.cdtipflu'   : _p52_selectedFlujo.get('CDTIPFLU')
                ,'params.cdflujomc' : _p52_selectedFlujo.get('CDFLUJOMC')
                ,'params.tipo'      : tipo
                ,'params.clave'     : clave
                ,'params.webid'     : id
                ,'params.xpos'      : x
                ,'params.ypos'      : y
            }
            ,success : function(response)
            {
                _setLoading(false,_p52_panelDibujo);
                var ck = 'Decodificando respuesta al registrar entidad';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### +entidad:',json);
                    if(json.success==true)
                    {
                        callback(json);
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
                _setLoading(false,_p52_panelDibujo);
                errorComunicacion(null,'Error al registrar entidad');
            }
        });
    }
    catch(e)
    {
        _setLoading(false,_p52_panelDibujo);
        manejaException(e,ck);
    }
}

function _p52_registrarConnection(con)
{
    debug('_p52_registrarConnection con:',con,'.');
    var ck = 'Registrando conexi\u00f3n';
    try
    {
        _setLoading(true,_p52_panelDibujo);
        Ext.Ajax.request(
        {
            url      : _p52_urlRegistrarConnection
            ,params  :
            {
                'params.cdtipflu'   : _p52_selectedFlujo.get('CDTIPFLU')
                ,'params.cdflujomc' : _p52_selectedFlujo.get('CDFLUJOMC')
                ,'params.idorigen'  : con.sourceId
                ,'params.iddestin'  : con.targetId
            }
            ,success : function(response)
            {
                _setLoading(false,_p52_panelDibujo);
                var ck = 'Decodificando respuesta al registrar conexi\u00f3n';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### +conex:',json);
                    if(json.success==true)
                    {
                    }
                    else
                    {
                        toolkit.detach(con);
                        mensajeError(json.message);
                    }
                }
                catch(e)
                {
                    toolkit.detach(con);
                    manejaException(e,ck);
                }
            }
            ,failure : function()
            {
                toolkit.detach(con);
                _setLoading(false,_p52_panelDibujo);
                errorComunicacion(null,'Error al registrar conexi\u00f3n');
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _p52_borrarConnection(con)
{
    debug('_p52_borrarConnection con:',con,'.');
    var ck = 'Borrando conexi\u00f3n';
    try
    {
        _setLoading(true,_p52_panelDibujo);
        Ext.Ajax.request(
        {
            url      : _p52_urlBorrarConnection
            ,params  :
            {
                'params.cdtipflu'   : _p52_selectedFlujo.get('CDTIPFLU')
                ,'params.cdflujomc' : _p52_selectedFlujo.get('CDFLUJOMC')
                ,'params.idorigen'  : con.sourceId
                ,'params.iddestin'  : con.targetId
            }
            ,success : function(response)
            {
                _setLoading(false,_p52_panelDibujo);
                var ck = 'Decodificando respuesta al borrar conexi\u00f3n';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### +conex:',json);
                    if(json.success==true)
                    {
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
                _setLoading(false,_p52_panelDibujo);
                errorComunicacion(null,'Error al borrar conexi\u00f3n');
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck);
    }
}

function _p52_guardarCoords(callback)
{
    debug('_p52_guardarCoords callback?',!Ext.isEmpty(callback));
    var ck = 'Guardando coordenadas';
    try
    {
        var jsonData =
        {
            params :
            {
                cdtipflu   : _p52_selectedFlujo.get('CDTIPFLU')
                ,cdflujomc : _p52_selectedFlujo.get('CDFLUJOMC')
            }
            ,list : []
        };
        
        ck       = 'Recopilando coordenadas';
        var divs = $('.entidad');
        debug('divs:',divs);
        
        for(var i=0;i<divs.length;i++)
        {
            var divi = divs[i];
            jsonData.list.push(
            {
                webid : divi.id
                ,xpos : divi.offsetLeft
                ,ypos : divi.offsetTop
                ,tipo : $(divi).attr('tipo')
            });
        }
        
        debug('jsonData:',jsonData);
        
        _setLoading(true,_p52_panelDibujo);
        Ext.Ajax.request(
        {
            url       : _p52_urlGuardarCoords
            ,jsonData : jsonData
            ,success  : function(response)
            {
                _setLoading(false,_p52_panelDibujo);
                var ck = 'Decodificando respuesta al guardar coordenadas';
                try
                {
                    var json = Ext.decode(response.responseText);
                    debug('### guardar coords:',json);
                    if(json.success==true)
                    {
                        mensajeCorrecto('Coordenadas guardadas','Coordenadas guardadas',callback);
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
                _setLoading(false,_p52_panelDibujo);
                errorComunicacion(null,'Error al guardar coordenadas');
            }
        });
    }
    catch(e)
    {
        _setLoading(false,_p52_panelDibujo);
        manejaException(e,ck);
    }
}
////// funciones //////

</script>
</head>
<body>
<div id="_p52_divpri" style="height:710px;border:1px solid #CCCCCC;"></div>
</body>
</html>