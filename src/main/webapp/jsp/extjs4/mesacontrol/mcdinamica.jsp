<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
.x-action-col-icon
{
    margin-right: 4px;
}
</style>
<script>
///////////////////////
////// variables //////
/*///////////////////*/
var mcdinInput     = [];
var mcdinSesion    = [];
var mcdinUrlNuevo  = '<s:url namespace="/mesacontrol" action="guardarTramiteDinamico" />';
var mcdinUrlCargar = '<s:url namespace="/mesacontrol" action="loadTareasDinamico"     />';
var _4_urlReload   = '<s:url namespace="/mesacontrol" action="mcdinamica"             />';

var _4_smap1 = <s:property value='%{getSmap1().toString().replace("=",":\'").replace(",","\',").replace("}","\'}")}' />;
debug('_4_smap1:',_4_smap1);

mcdinInput['cdunieco'] = '<s:property value="smap2.pv_cdunieco_i" />';
mcdinInput['ntramite'] = '<s:property value="smap2.pv_ntramite_i" />';
mcdinInput['cdramo']   = '<s:property value="smap2.pv_cdramo_i"   />';
mcdinInput['nmpoliza'] = '<s:property value="smap2.pv_nmpoliza_i" />';
mcdinInput['estado']   = '<s:property value="smap2.pv_estado_i"   />';
mcdinInput['cdagente'] = '<s:property value="smap2.pv_cdagente_i" />';
mcdinInput['status']   = '<s:property value="smap2.pv_status_i"   />';
mcdinInput['cdtipsit'] = '<s:property value="smap2.pv_cdtipsit_i" />';
mcdinInput['fedesde']  = '<s:property value="smap2.pv_fedesde_i"  />';
mcdinInput['fehasta']  = '<s:property value="smap2.pv_fehasta_i"  />';
mcdinInput['tiptra']   = '<s:property value="smap2.pv_cdtiptra_i" />';
debug('mcdinInput: ',mcdinInput);

mcdinSesion['username'] = '<s:property value="username" />';
mcdinSesion['rol'] = '<s:property value="rol" />';
debug('mcdinSesion: ',mcdinSesion);

var mcdinGrid;
var mcdinStore;
var mcdinFiltro;
var mcdinFormNuevo;

var _4_botones=
{
	xtype         : 'actioncolumn'
	,hidden       : false
	,menuDisabled : true
	,sortable     : false
	,items:
	[
	    <s:property value="imap1.actionColumns" />
	]
};

var _4_botonesGrid =
[
	<s:if test='%{getSmap1().get("editable")!=null&&getSmap1().get("editable").length()>0}'>
	{
	    text     : 'Agregar tr&aacute;mite'
	    ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
	    ,handler : function()
	    {
	        mcdinFormNuevo.show();
	    }
	}
	</s:if>
];
/*///////////////////*/
////// variables //////
///////////////////////

<s:if test='%{getSmap2().get("pv_cdtiptra_i").equalsIgnoreCase("1")}'>
    <%@ include file="/jsp-script/proceso/endosos/scriptMesaEmision.jsp"%>
</s:if>
<s:elseif test='%{getSmap2().get("pv_cdtiptra_i").equalsIgnoreCase("15")}'>
    <%@ include file="/jsp-script/proceso/endosos/scriptMesaAutorizacionEndosos.jsp"%>
</s:elseif>
<s:elseif test='%{getSmap2().get("pv_cdtiptra_i").equalsIgnoreCase("16")}'>
	<%@ include file="/jsp-script/proceso/siniestros/scriptMesaSiniestros.jsp"%>
</s:elseif>

_4_botones.width = (_4_botones.items.length*20)+10;

///////////////////////
////// funciones //////
/*///////////////////*/
function _4_cambiarTiptra(cdtiptra)
{
	var editable='';
	if(cdtiptra=='1')
	{
		editable='x';
	}
	Ext.create('Ext.form.Panel').submit(
	{
		url     : _4_urlReload
		,params :
		{
			'smap1.cdramo'         : _4_smap1.cdramo
			,'smap1.cdtipsit'      : _4_smap1.cdtipsit
			,'smap1.gridTitle'     : _4_smap1.gridTitle
			,'smap2.pv_cdtiptra_i' : cdtiptra
			,'smap1.editable'      : editable
		}
	    ,standardSubmit : true
	});
}
/*///////////////////*/
////// funciones //////
///////////////////////

Ext.onReady(function()
{
	
    /////////////////////
    ////// modelos //////
    /*/////////////////*/
    Ext.define('McdinModelo',
    {
    	extend  : 'Ext.data.Model'
    	,fields :
    	[
    	    {
    	    	name  : 'activo'
    	    	,type : 'boolean'
    	    }
    	    ,<s:property value="imap1.modelFields" />
    	]
    });
    /*/////////////////*/
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/
    mcdinStore = Ext.create('Ext.data.Store',
    {
        pageSize : 10,
        autoLoad : true,
        model    : 'McdinModelo',
        //sorters:[{sorterFn:function(o1,o2){return o1.get('ntramite')<o2.get('ntramite')}}],
        proxy    :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
        ,listeners :
        {
            load : function (action,records)
            {
                debug("records",records);
            }
        }
    });
    /*////////////////*/
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    /*/////////////////////*/
    Ext.define('McdinGrid',
    {
    	extend : 'Ext.grid.Panel'
    	,initComponent : function()
    	{
    		debug('initComponent instance of McdinGrid');
    		Ext.apply(this,
    		{
    			title      : '<s:property value="smap1.gridTitle" />'
    			,store     : mcdinStore
    			,minHeight : 200
    			,columns   :
    			[
    			    {
    			    	xtype         : 'checkcolumn'
    			    	,dataIndex    : 'activo'
    			    	,width        : 30
    			    	,menuDisabled : true
    			    	,sortable     : false
    			    }
    			    ,<s:property value="imap1.gridColumns" />
    			    ,_4_botones
    			]
    			,tbar      : _4_botonesGrid
    			,bbar      :
    	        {
    	            displayInfo : true
    	            ,store      : mcdinStore
    	            ,xtype      : 'pagingtoolbar'
    	        }
    		});
    		this.callParent();
    	}
    });
    
    Ext.define('McdinFiltro',
    {
    	extend         : 'Ext.form.Panel'
    	,initComponent : function()
    	{
    		debug('initComponent instance of McdinFiltro');
    		Ext.apply(this,
    		{
		    	title          : 'Filtro'
		    	,icon          : '${ctx}/resources/fam3icons/icons/zoom.png'
		    	,buttonAlign   : 'center'
		    	,collapsible   : true
		    	,url           : _4_urlReload
		    	,minHeight     : 200
		    	,titleCollapse : true
		    	,layout        :
		    	{
		    		type     : 'table'
		    		,columns : 3
		    	}
		    	,defaults      :
		    	{
		    		style : 'margin : 5px;'
		    	}
		    	,items         :
		    	[
		    	    <s:property value="imap1.itemsFiltro" />
		    	]
		    	,tbar          :
		    	[
					{
					    text      : 'Emisi&oacute;n'
					    ,icon     : '${ctx}/resources/fam3icons/icons/star.png'
					    ,disabled : mcdinInput['tiptra']=='1'
					    ,handler  : function()
					    {
					    	_4_cambiarTiptra(1);
					    }
					}
					,{
                        text      : 'Endosos'
                        ,icon     : '${ctx}/resources/fam3icons/icons/overlays.png'
                        ,disabled : mcdinInput['tiptra']=='15'
                        ,handler  : function()
                        {
                        	_4_cambiarTiptra(15);
                        }
                    }
					,{
                        text      : 'Siniestros'
                        ,icon     : '${ctx}/resources/fam3icons/icons/flag_red.png'
                        ,disabled : mcdinInput['tiptra']=='16'
                        ,handler  : function()
                            {
                            	_4_cambiarTiptra(16);
                            }
                    }
		    	]
		    	,buttons       :
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
		    	    		if(this.up().up().isValid())
		    	    		{
			    	    		this.up().up().submit(
			    	    		{
			    	    			standardSubmit : true
			    	    			,params        :
			    	    			{
			    	    				'smap1.cdramo'         : _4_smap1.cdramo
			    	    	            ,'smap1.cdtipsit'      : _4_smap1.cdtipsit
			    	    	            ,'smap1.gridTitle'     : _4_smap1.gridTitle
			    	    	            ,'smap2.pv_cdtiptra_i' : mcdinInput['tiptra']
			    	    			}
			    	    		});
		    	    		}
		    	    		else
		    	    		{
		    	    			Ext.Msg.show(
                                {
                                    title    : 'Error'
                                    ,msg     : 'Favor de introducir los campos requeridos'
                                    ,buttons : Ext.Msg.OK
                                    ,icon    : Ext.Msg.WARNING
                                });
		    	    		}
		    	    	}
		    	    }
		    	]
    		});
    		this.callParent();
    	}
    });
    
    Ext.define('McdinFormNuevo',
    {
    	extend       : 'Ext.window.Window'
        ,title       : 'Agregar tr&aacute;mite'
        ,icon        : '${ctx}/resources/fam3icons/icons/add.png'
        ,width       : 600
        ,modal       : true
        ,height      : 400
        ,closeAction : 'hide'
        ,items       :
        [
            Ext.create('Ext.form.Panel',
            {
                buttonAlign : 'center'
                ,border     : 0
                ,url        : mcdinUrlNuevo
                ,layout     :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,defaults   :
                {
                    style : 'margin : 5px;'
                }
                ,items      :
                [
                    <s:property value="imap1.formItems" />
                ]
                ,buttons    :
                [
                    {
                        text     : 'Guardar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                        ,handler : function()
                        {
                            var form=this.up().up();
                            debug(form.getForm().getValues());
                            if(form.isValid())
                            {
                                form.setLoading(true);
                                form.submit(
                                {
                                    success  : function(form2, action)
                                    {
                                        form.setLoading(false);
                                        Ext.Msg.show(
                                        {
                                            title    : 'Cambios guardados'
                                            ,msg     : 'Se agreg&oacute; un nuevo tr&aacute;mite</br> N&uacute;mero: '+ action.result.msgResult
                                            ,buttons : Ext.Msg.OK
                                            ,fn      : function()
                                            {
                                            	_4_cambiarTiptra(mcdinInput['tiptra']);
                                            }
                                        });    
                                    }
                                    ,failure : function()
                                    {
                                        form.setLoading(false);
                                        Ext.Msg.show(
                                        {
                                            title    : 'Error'
                                            ,msg     : 'Error de comunicaci&oacute;n'
                                            ,buttons : Ext.Msg.OK
                                            ,icon    : Ext.Msg.ERROR
                                        });
                                    }
                                });
                            }
                            else
                            {
                                Ext.Msg.show(
                                {
                                    title    : 'Error'
                                    ,msg     : 'Favor de introducir los campos requeridos'
                                    ,buttons : Ext.Msg.OK
                                    ,icon    : Ext.Msg.WARNING
                                });
                            }
                        }
                    }
                ]
            })
        ]
    });
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    mcdinGrid      = new McdinGrid();
    mcdinFiltro    = new McdinFiltro();
    mcdinFormNuevo = new McdinFormNuevo();
    
    Ext.create('Ext.panel.Panel',
    {
    	border    : 0
    	,renderTo : 'mcdinDivPri'
    	,defaults :
    	{
    		style : 'margin : 5px;'
    	}
    	,items    :
    	[
    	    mcdinFiltro,
    	    mcdinGrid
    	]
    });
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////
    
    //////////////////////
    ////// cargador //////
    /*//////////////////*/
    Ext.Ajax.request(
    {
        url      : mcdinUrlCargar
        ,params  :
        {
            'smap1.pv_cdunieco_i'   : mcdinInput['cdunieco']
             ,'smap1.pv_ntramite_i' : mcdinInput['ntramite']
             ,'smap1.pv_cdramo_i'   : mcdinInput['cdramo']
             ,'smap1.pv_nmpoliza_i' : mcdinInput['nmpoliza']
             ,'smap1.pv_estado_i'   : mcdinInput['estado']
             ,'smap1.pv_cdagente_i' : mcdinInput['cdagente']
             ,'smap1.pv_status_i'   : mcdinInput['status']
             ,'smap1.pv_cdtipsit_i' : mcdinInput['cdtipsit']
             ,'smap1.pv_fedesde_i'  : mcdinInput['fedesde']
             ,'smap1.pv_fehasta_i'  : mcdinInput['fehasta']
             ,'smap1.pv_cdtiptra_i' : mcdinInput['tiptra']
        }
        ,success : function(response)
        {
        	debug('responseText',response.responseText);
            var jsonResponse = Ext.decode(response.responseText);
            debug(jsonResponse);
            mcdinStore.setProxy(
            {
                type         : 'memory',
                enablePaging : true,
                reader       : 'json',
                data         : jsonResponse.olist1
            });
            mcdinStore.load();
        }
        ,failure : function()
        {
            var msg=Ext.Msg.show(
            {
                title   : 'Error',
                icon    : Ext.Msg.ERROR,
                msg     : 'Error de comunicaci&oacute;n',
                buttons : Ext.Msg.OK
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
<div id="mcdinDivPri" style="height:1000px;"></div>
</body>
</html>