<%@ include file="/taglibs.jsp"%>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var pantallaValositMainContent;
var pantallaValositUrlLoad       = '<s:url namespace="/"           action="pantallaValositLoad" />';
var pantallaValositUrlSave       = '<s:url namespace="/endosos"    action="guardarEndosoValositBasico" />';
var pantallaValositUrlSaveSimple = '<s:url namespace="/endosos"    action="guardarEndosoValositBasicoSimple" />';
var pantallaValositUrlDoc        = '<s:url namespace="/documentos" action="ventanaDocumentosPoliza" />';

var pantallaValositInput                 = [];
    pantallaValositInput['cdunieco']     = '<s:property value="smap1.cdunieco" />';
    pantallaValositInput['cdramo']       = '<s:property value="smap1.cdramo" />';
    pantallaValositInput['estado']       = '<s:property value="smap1.estado" />';
    pantallaValositInput['nmpoliza']     = '<s:property value="smap1.nmpoliza" />';
    pantallaValositInput['cdtipsit']     = '<s:property value="smap1.cdtipsit" />';
    pantallaValositInput['nmsituac']     = '<s:property value="smap1.nmsituac" />';
    pantallaValositInput['ntramite']     = '<s:property value="smap1.ntramite" />';
    pantallaValositInput['nmsuplem']     = '<s:property value="smap1.nmsuplem" />';
    pantallaValositInput['endososimple'] = <s:property value="endosoSimple" />;
    pantallaValositInput['fechainicio']  = pantallaValositInput['endososimple'] ? '<s:property value="mensaje" />' : new Date();
    
var pantallaValositEsTitular         = false;
debug('input',pantallaValositInput);
/*///////////////////*/
////// variables //////
///////////////////////

///////////////////////
////// funciones //////
/*///////////////////*/
function endvalbasSumit(form,confirmar)
{
	if(form.isValid())
    {
        var titularvalido=true;
        var mensajeTitularValido='';
        debug(form.getValues());
        if(form.getValues()['parametros.pv_otvalor16'])
        {
            if(pantallaValositEsTitular==true)
            {
                if(form.getValues()['parametros.pv_otvalor16']!='T')
                {
                    titularvalido=false;
                    mensajeTitularValido='No se puede quitar el titular';
                }
            }
            else
            {
                if(form.getValues()['parametros.pv_otvalor16']=='T')
                {
                    titularvalido=false;
                    mensajeTitularValido='Ya existe un titular';
                }
            }
        }
        if(titularvalido==true)
        {
            form.setLoading(true);
            form.submit({
                params:
                {
                    'smap1.cdunieco'   : pantallaValositInput['cdunieco']
                    ,'smap1.cdramo'    : pantallaValositInput['cdramo']
                    ,'smap1.estado'    : pantallaValositInput['estado']
                    ,'smap1.nmpoliza'  : pantallaValositInput['nmpoliza']
                    ,'smap1.cdtipsit'  : pantallaValositInput['cdtipsit']
                    ,'smap1.nmsituac'  : pantallaValositInput['nmsituac']
                    ,'smap1.confirmar' : confirmar
                },
                success:function(action,response)
                {
                    debug(response);
                    form.setLoading(false);
                    var json=Ext.decode(response.response.responseText);
                    debug(json);
                    Ext.Msg.show(
                    {
                        title   : 'Endoso generado',
                        msg     : json.mensaje,
                        buttons : Ext.Msg.OK
                    });
                    if(confirmar=='si')
                    {
                    	//////////////////////////////////
                        ////// usa codigo del padre //////
                        /*//////////////////////////////*/
                        marendNavegacion(2);
                        /*//////////////////////////////*/
                        ////// usa codigo del padre //////
                        //////////////////////////////////
                    }
                },
                failure:function(action,response)
                {
                    form.setLoading(false);
                	var json=Ext.decode(response.response.responseText);
                    mensajeError(json.error);
                }
            });
        }
        else
        {
            Ext.Msg.show({
                title:'Error',
                icon: Ext.Msg.WARNING,
                msg: mensajeTitularValido,
                buttons: Ext.Msg.OK
            });
        }
    }
    else
    {
        Ext.Msg.show({
            title:'Error',
            icon: Ext.Msg.WARNING,
            msg: 'Favor de llenar los campos requeridos',
            buttons: Ext.Msg.OK
        });
    }
}
/*///////////////////*/
////// funciones //////
///////////////////////

Ext.onReady(function()
{
    
    /////////////////////
    ////// modelos //////
    /*/////////////////*/
    Ext.define('EndValBasModelo',
    {
        extend:'Ext.data.Model'
        ,<s:property value="item1" />
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
    Ext.define('PanelValosit',
    {
        extend       : 'Ext.form.Panel'
        ,title       : 'Datos del asegurado'
        ,layout      :
        {
            type     : 'table'
            ,columns : 2
        }
        ,defaults    :
        {
            style : 'margin:5px;'
        }
        ,<s:property value="item2" />
    });
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    pantallaValositMainContent=new PanelValosit();
    Ext.create('Ext.form.Panel',
    {
    	defaults     :
    	{
    		style : 'margin : 5px;'
    	}
        ,border      : 0
        ,renderTo    : 'maindivpantallavalosit'
        ,buttonAlign : 'center'
        ,url         : pantallaValositInput['endososimple'] ? pantallaValositUrlSaveSimple : pantallaValositUrlSave
        ,items       :
        [
            pantallaValositMainContent
            ,Ext.create('Ext.panel.Panel',
            {
            	title     : 'Informaci&oacute;n del endoso'
            	,layout   :
            	{
            		type     : 'table'
            		,columns : 2 
            	}
            	,defaults :
                {
                    style : 'margin : 5px;'
                }
            	,items    :
            	[
            	    {
                        xtype       : 'datefield'
                        ,fieldLabel : 'Fecha de inicio'
                        ,format     : 'd/m/Y'
                        ,value      : pantallaValositInput['fechainicio']
                        ,allowBlank : false
                        ,name       : 'smap1.fecha_endoso'
                        ,readOnly   : pantallaValositInput['endososimple']
                    }
            	]
            })
        ]
        ,buttons     :
        [
            {
                text     : 'Guardar endoso'
                ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                ,handler : function(me)
                {
                    var form=me.up().up();
                    endvalbasSumit(form,'no');
                }
                ,hidden   : true // TODO:Quitar propiedad cuando se arregle duplicidad de PKG_SATELITES.P_MOV_MPOLISIT
            }
            ,{
                text     : 'Confirmar endoso'
                ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                ,handler : function(me)
                {
                    var form=me.up().up();
                    endvalbasSumit(form,'si');
                }
            }
            ,{
                text     : 'Documentos'
                ,icon    : '${ctx}/resources/fam3icons/icons/printer.png'
                ,handler : function()
                {
                    Ext.create('Ext.window.Window',
                    {
                        title        : 'Documentos del tr&aacute;mite '+pantallaValositInput['ntramite']
                        ,modal       : true
                        ,buttonAlign : 'center'
                        ,width       : 600
                        ,height      : 400
                        ,autoScroll  : true
                        ,loader      :
                        {
                            url       : pantallaValositUrlDoc
                            ,params   :
                            {
                                'smap1.nmpoliza'  : pantallaValositInput['nmpoliza']
                                ,'smap1.cdunieco' : pantallaValositInput['cdunieco']
                                ,'smap1.cdramo'   : pantallaValositInput['cdramo']
                                ,'smap1.estado'   : pantallaValositInput['estado']
                                ,'smap1.nmsuplem' : '0'
                                ,'smap1.ntramite' : pantallaValositInput['ntramite']
                                ,'smap1.nmsolici' : ''
                                ,'smap1.tipomov'  : '0'
                            }
                            ,scripts  : true
                            ,autoLoad : true
                        }
                    }).show();
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
        extend:'EndValBasModelo',
        proxy:
        {
            extraParams:
            {
                'smap1.pv_cdunieco_i'  : pantallaValositInput['cdunieco']
                ,'smap1.pv_nmpoliza_i' : pantallaValositInput['nmpoliza']
                ,'smap1.pv_cdramo_i'   : pantallaValositInput['cdramo']
                ,'smap1.pv_estado_i'   : pantallaValositInput['estado']
                ,'smap1.pv_nmsituac_i' : pantallaValositInput['nmsituac']
            },
            type:'ajax',
            url : pantallaValositUrlLoad,
            reader:{
                type:'json'
            }
        }
    });

    var loaderForm=Ext.ModelManager.getModel('LoaderForm');
    loaderForm.load(123, {
        success: function(resp) {
            //console.log(resp);
            pantallaValositMainContent.loadRecord(resp);
            if(resp.get("parametros.pv_otvalor16")=='T')
            {
            	debug('sin cambiar titular');
            	pantallaValositEsTitular=true;
            }
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
<div id="maindivpantallavalosit" style="min-height:150px;"></div>