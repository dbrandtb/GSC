<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var marmesconurlcata       = '<s:url namespace="/flujocotizacion"   action="cargarCatalogos" />';
var marmesconurlramos      = '<s:url namespace="/"                  action="obtenerRamos" />';
var marmesconUrlLoadTipsit = '<s:url namespace="/"                  action="obtenerTipsit" />';
var marmesconurlAgentes    = '<s:url namespace="/mesacontrol"       action="obtieneAgentes" />';
var marmesconurlcargar     = '<s:url namespace="/mesacontrol"       action="loadTareasSuper" />';
var marmesconStoreTramites;
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
Ext.define('Ramo',
{
    extend  : 'Ext.data.Model'
    ,fields :
    [
        "cdramo"
        ,"dsramo"
    ]
});

Ext.define('Tipsit',{
    extend:'Ext.data.Model',
    fields:
    [
        "CDTIPSIT"
        ,"DSTIPSIT"
    ]
});

Ext.define('MarMesConTarea',
{
	extend  : 'Ext.data.Model'
	,fields :
	[
    	"NTRAMITE" 
	    ,"CDUNIECO" 
	    ,"CDRAMO" 
	    ,"ESTADO" 
	    ,"NMPOLIZA" 
	    ,"NMSOLICI" 
	    ,"CDSUCADM" 
	    ,"CDSUCDOC" 
	    ,"CDSUBRAM" 
	    ,"CDTIPTRA"
	    ,{
    		name        : 'FERECEPC'
    		,type       : 'date'
    		,dateFormat : 'd/m/Y'
    	}
	    ,"CDAGENTE" 
	    ,"NOMBRE_AGENTE" 
	    ,"REFERENCIA" 
	    ,"NOMBRE" 
	    ,{
            name        : 'FECSTATU'
            ,type       : 'date'
            ,dateFormat : 'd/m/Y'
        }
	    ,"STATUS" 
	    ,"COMMENTS" 
	    ,"CDTIPSIT"
	]
});
/*/////////////////*/
////// modelos //////
/////////////////////

////////////////////
////// stores //////
/*////////////////*/
marmesconStoreTramites=Ext.create('Ext.data.Store',
{
	autoLoad  : true
	,model    : 'MarMesConTarea'
	,proxy    :
	{
	    reader : 'json'
	    ,type  : 'memory'
	    ,data  : []
	}
});
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
Ext.create('Ext.panel.Panel',
{
	renderTo  : 'marmesconDivPri'
	,defaults :
	{
		style : 'margin : 5px;'
	}
	,border   : 0
	,items    :
	[
	    Ext.create('Ext.form.Panel',
	    {
	    	title      : 'Filtro'
	    	,frame     : true
	    	,url       : marmesconurlcargar
	    	,layout    :
	    	{
	    		type     : 'table'
	    		,columns : 3
	    	}
	    	,defaults    : 
	    	{
	    		style : 'margin : 5px;'
	    	}
	    	,buttonAlign : 'center'
	    	,buttons     :
	    	[
	    	    {
	    	    	text     : 'Limpiar'
	    	    	,icon    : '${ctx}/resources/fam3icons/icons/control_repeat_blue.png'
	    	    	,handler : function()
	    	    	{
	    	    		this.up().up().getForm().reset();
	    	    	}
	    	    }
	    	    ,{
	    	    	text     : 'Filtrar'
	    	    	,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
	    	    	,handler : function()
	    	    	{
	    	    		debug('buscar');
	    	    		var form =this.up().up();
	    	    		if(form.isValid())
	    	    		{
	    	    			form.setLoading(true);
	    	    			form.submit({
	    	    				success  : function(form2,action)
                                {
	    	    					form.setLoading(false);
                                    debug(action);
                                    var json = Ext.decode(action.response.responseText);
                                    debug(json);
                                    if(json.success==true&&json.slist1&&json.slist1.length>0)
                                    {
                                    	marmesconStoreTramites.removeAll();
                                    	marmesconStoreTramites.add(json.slist1);
                                        debug(marmesconStoreTramites);
                                    }
                                    else
                                    {
                                        Ext.Msg.show(
                                        {
                                            title    : 'Sin resultados'
                                            ,msg     : 'No hay resultados'
                                            ,icon    : Ext.Msg.WARNING
                                            ,buttons : Ext.Msg.OK
                                        });
                                    }
                                }
                                ,failure : function()
                                {
                                	form.setLoading(false);
                                    Ext.Msg.show(
                                    {
                                        title   : 'Error',
                                        icon    : Ext.Msg.ERROR,
                                        msg     : 'Error de comunicaci&oacute;n',
                                        buttons : Ext.Msg.OK
                                    });
                                }
	    	    			});
	    	    		}
	    	    		else
	    	    		{
	    	    			Ext.Msg.show(
                            {
                                title   : 'Datos imcompletos',
                                icon    : Ext.Msg.WARNING,
                                msg     : 'Favor de llenar los campos requeridos',
                                buttons : Ext.Msg.OK
                            });
	    	    		}
	    	    	}
	    	    }
	    	]
	    	,items       :
	    	[
				{
				    xtype           : 'combo'
				    ,id             : 'marmesconFilUnieco'
				    ,fieldLabel     : 'Sucursal'
				    ,name           : 'smap1.pv_cdunieco_i'
				    ,displayField   : 'value'
				    ,valueField     : 'key'
				    ,forceSelection : false
				    ,queryMode      :'local'
				    ,store          : Ext.create('Ext.data.Store',
				    {
				        model     : 'Generic'
				        ,autoLoad : true
				        ,proxy    :
				        {
				            type         : 'ajax'
				            ,url         : marmesconurlcata
				            ,extraParams : {catalogo:'<s:property value="CON_CAT_MESACONTROL_SUCUR_DOCU" />'}
				            ,reader      :
				            {
				                type  : 'json'
				                ,root : 'lista'
				            }
				        }
				    })
				    ,listeners      :
				    {
				        'change' : function()
				        {
				            Ext.getCmp('marmesconFilRamo').getStore().load(
				            {
				                params : {'map1.cdunieco':this.getValue()}
				            });
				        }
				    }
				}
				,{
                    xtype           : 'combo'
                    ,id             : 'marmesconFilRamo'
                    ,fieldLabel     : 'Producto'
                    ,name           : 'smap1.pv_cdramo_i'
                    ,valueField     : 'cdramo'
                    ,displayField   : 'dsramo'
                    ,forceSelection : false
                    ,queryMode      :'local'
                    ,store          : Ext.create('Ext.data.Store',
                    {
                        model     : 'Ramo'
                        ,autoLoad : false
                        ,proxy    :
                        {
                            type    : 'ajax'
                            ,url    : marmesconurlramos
                            ,reader :
                            {
                                type  : 'json'
                                ,root : 'slist1'
                            }
                        }
                    })
                    ,listeners      :
                    {
                        'change' : function()
                        {
                            Ext.getCmp('marmesconFilTipsit').getStore().load(
                            {
                                params : {'map1.cdramo':this.getValue()}
                            });
                        }
                    }
                }
				,{
                    fieldLabel  : 'Modalidad'
                    ,xtype      : 'combo'
                    ,id         : 'marmesconFilTipsit'
                    ,name       : 'smap1.pv_cdtipsit_i'
                    ,valueField   : 'CDTIPSIT'
                    ,displayField : 'DSTIPSIT'
                    ,forceSelection : false
                    ,queryMode      :'local'
                    ,store : Ext.create('Ext.data.Store', {
                        model:'Tipsit',
                        autoLoad : false,
                        proxy:
                        {
                            type: 'ajax',
                            url : marmesconUrlLoadTipsit,
                            reader:
                            {
                                type: 'json',
                                root: 'slist1'
                            }
                        }
                    })
                }
				,{
					xtype           : 'combo'
                    ,id             : 'marmesconFilEstado'
                    ,fieldLabel     : 'Estado'
                    ,name           : 'smap1.pv_estado_i'
                    ,displayField   : 'value'
                    ,valueField     : 'key'
                    ,forceSelection : false
                    ,queryMode      :'local'
                    ,store          : Ext.create('Ext.data.Store',
                    {
                        model     : 'Generic'
                        ,autoLoad : true
                        ,proxy    :
                        {
                            type         : 'ajax'
                            ,url         : marmesconurlcata
                            ,extraParams : {catalogo:'<s:property value="CON_CAT_POL_ESTADO" />'}
                            ,reader      :
                            {
                                type  : 'json'
                                ,root : 'lista'
                            }
                        }
                    })
                }
				,{
					xtype       : 'textfield'
                    ,fieldLabel : 'P&oacute;liza'
                    ,name       : 'smap1.pv_nmpoliza_i'
				}
				,{
					xtype       : 'numberfield'
					,fieldLabel : 'Tr&aacute;mite'
					,name       : 'smap1.pv_ntramite_i'
				}
				,Ext.create('Ext.form.field.ComboBox',
                {
                    fieldLabel : 'Agente'
                    ,name      : 'smap1.pv_cdagente_i'
                    ,displayField : 'value'
                    ,valueField   : 'key'
                    ,forceSelection : false
                    ,matchFieldWidth: false
                    ,hideTrigger : true
                    ,minChars  : 3
                    ,queryMode :'remote'
                    ,queryParam: 'smap1.pv_cdagente_i'
                    ,store : Ext.create('Ext.data.Store', {
                        model:'Generic',
                        autoLoad:false,
                        proxy: {
                            type: 'ajax',
                            url : marmesconurlAgentes,
                            reader: {
                                type: 'json',
                                root: 'lista'
                            }
                        }
                    })
                })
                ,Ext.create('Ext.form.field.ComboBox',
                {
                    fieldLabel      : 'Estatus'
                    ,name           : 'smap1.pv_status_i'
                    ,displayField   : 'value'
                    ,valueField     : 'key'
                    ,forceSelection : false
                    ,queryMode      :'local'
                    ,store          : Ext.create('Ext.data.Store',
                    {
                        model     : 'Generic'
                        ,autoLoad : true
                        ,proxy    :
                        {
                            type         : 'ajax'
                            ,url         : marmesconurlcata
                            ,extraParams : {catalogo:'<s:property value="CON_CAT_MESACONTROL_ESTAT_TRAMI" />'}
                            ,reader      :
                            {
                                type  : 'json'
                                ,root : 'lista'
                            }
                        }
                    })
                })
	    	]
	    })
	    ,Ext.create('Ext.panel.Panel',
        {
            frame      : true
            ,region    : 'center'
            ,id        : 'marmesconLoaderFrame'
            ,loader    :
            {
                autoLoad: false
            }
        })
	    /*
	    ,Ext.create('Ext.grid.Panel',
	    {
            frame    : true
            ,title   : 'Tr&aacute;mites'
            ,height  : 300
            ,store   : marmesconStoreTramites
            ,columns :
            [
                {
                	header     : 'Tr&aacute;mite'
                	,dataIndex : 'NTRAMITE'
                	,flex      : 1
                }
                ,{
                    header     : 'Sucursal'
                    ,dataIndex : 'CDUNIECO'
                    ,flex      : 1
                }
                ,{
                    header     : 'Subramo'
                    ,dataIndex : 'CDRAMO'
                    ,flex      : 1
                }
                ,{
                    header     : 'Fecha<br/>de captura'
                    ,dataIndex : 'FERECEPC'
                    ,flex      : 1
                    ,renderer  : Ext.util.Format.dateRenderer('d M Y')
                }
                ,{
                    header     : 'Fecha<br/>de estatus'
                    ,dataIndex : 'FECSTATU'
                    	,flex      : 1
                    ,renderer  : Ext.util.Format.dateRenderer('d M Y')
                }
                ,{
                    header     : 'P&oacute;liza'
                    ,dataIndex : 'NMPOLIZA'
                    ,flex      : 1
                }
                ,{
                    header     : 'Cotizaci&oacute;n'
                    ,dataIndex : 'NMSOLICI'
                    ,flex      : 1
                 }
                ,{
                	header     : 'Estado'
                	,dataIndex : 'STATUS'
                	,flex      : 1
                	,renderer  : function(value)
                	{
                		var res=value;
                        if(value=='1')
                        {
                            res='En revisión médica';
                        }
                        else if(value=='2')
                        {
                            res='Pendiente';
                        }
                        else if(value=='3')
                        {
                            res='Confirmada';
                        }
                        else if(value=='4')
                        {
                            res='Rechazada';
                        }
                        else if(value=='5')
                        {
                            res='Vo. Bo. Médico';
                        }
                        else if(value=='6')
                        {
                            res='Esperando información cliente';
                        }
                        return res;
                	}
                }
                ,{
                    header     : 'Agente'
                    ,dataIndex : 'NOMBRE_AGENTE'
                    ,width     : 190
                }
            ]
        })
        */
	]
});
/*///////////////////*/
////// contenido //////
///////////////////////

//////////////////////
////// cargador //////
/*//////////////////*/

/*//////////////////*/
////// cargador //////
//////////////////////
});
</script>
</head>
<body>
<div id="marmesconDivPri" style="height:500px;"></div>
</body>
</html>