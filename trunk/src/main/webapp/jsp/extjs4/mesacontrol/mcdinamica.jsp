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
<script type="text/javascript">
///////////////////////
////// variables //////
/*///////////////////*/
var mcdinInput     = [];
var mcdinSesion    = [];
var mcdinUrlNuevo  = '<s:url namespace="/mesacontrol" action="guardarTramiteDinamico"  />';
var mcdinUrlCargar = '<s:url namespace="/mesacontrol" action="loadTareasDinamico"      />';
var _4_urlReload   = '<s:url namespace="/mesacontrol" action="mcdinamica"              />';
var _4_urlReporte  = '<s:url namespace="/reportes"    action="procesoObtencionReporte" />';

//Obtenemos el contenido en formato JSON de la propiedad solicitada:
var _4_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
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
var loadMcdinStore;

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

var _4_statusColumns = [<s:property value="imap1.statusColumns" escapeHtml="false" />];

var _4_botonesGrid =
[
	<s:property value="imap1.gridbuttons" />
];
/*///////////////////*/
////// variables //////
///////////////////////

<s:if test='%{getSmap2().get("pv_cdtiptra_i").equalsIgnoreCase("1")}'>
    <%@ include file="/jsp-script/proceso/endosos/scriptMesaEmision.jsp"%>
</s:if>
<s:elseif test='%{getSmap2().get("pv_cdtiptra_i").equalsIgnoreCase("14")}'>
<%@ include file="/jsp-script/proceso/endosos/scriptMesaAutorizacionServicios.jsp"%>
</s:elseif>
<s:elseif test='%{getSmap2().get("pv_cdtiptra_i").equalsIgnoreCase("15")}'>
    <%@ include file="/jsp-script/proceso/endosos/scriptMesaAutorizacionEndosos.jsp"%>
</s:elseif>
<s:elseif test='%{getSmap2().get("pv_cdtiptra_i").equalsIgnoreCase("16")}'>
	<%@ include file="/jsp-script/proceso/siniestros/scriptMesaSiniestros.jsp"%>
</s:elseif>
<s:elseif test='%{getSmap2().get("pv_cdtiptra_i").equalsIgnoreCase("17")}'>
<%@ include file="/jsp-script/proceso/emision/scriptMesaAutorizacionEmisiones.jsp"%>
</s:elseif>

_4_botones.width = (_4_botones.items.length*20)+10;

///////////////////////
////// funciones //////
/*///////////////////*/
function _4_imagenStatus(record,imagen,texto,estados,funcion,row)
{
	debug('record:'  , record);
	debug('imagen:'  , imagen);
	debug('texto:'   , texto);
	debug('estados:' , estados);
	debug('funcion:' , funcion);
	debug('row:'     , row);
	var indice   = $.inArray(record.get('status'),estados);
	debug('indice: ',indice);
	if(indice>=0)
	{
	    return '<a href="#" onclick="'+funcion+'('+row+'); return false;"><img src="${ctx}/resources/fam3icons/icons/'+imagen+'.png" data-qtip="'+texto+'" /></a>';
	}
	else
	{
		return 'a';
	}
}

function _4_cambiarTiptra(cdtiptra)
{
	var editable = '';
	var titulo   = '';
	
	if(cdtiptra=='1')
	{
		editable = 'si';
		titulo   = 'Tareas';
	}
	else if(cdtiptra=='14')
    {
        editable = '';
        titulo   = 'Autorizaciones de servicios';
    }
	else if(cdtiptra=='15')
    {
        editable = '';
        titulo   = 'Endosos en espera';
    }
	else if(cdtiptra=='16')
    {
        editable = '';
        titulo   = 'Reclamaciones en proceso';
    }
	else if(cdtiptra=='17')
    {
        editable = '';
        titulo   = 'Emisiones en espera';
    }
	
	Ext.create('Ext.form.Panel').submit(
	{
		url     : _4_urlReload
		,params :
		{
			'smap1.cdramo'         : _4_smap1.cdramo
			,'smap1.cdtipsit'      : _4_smap1.cdtipsit
			,'smap1.gridTitle'     : titulo
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
                debug("Records Cargados en mcdinStore: ",records);
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
    		var columnas =
    	    [
    	        <s:property value="imap1.gridColumns" />
    	    ];
    		if(_4_statusColumns.length>0)
    		{
    			for(var i=0;i<_4_statusColumns.length;i++)
    			{
    				var col=_4_statusColumns[i];
    				col.flex         = 0;
    				col.width        = 20;
    				col.xtype        = 'actioncolumn';
    				col.text         = '';
    				col.sortable     = false;
    				col.menuDisabled = true;
    				columnas.push(col);
    			}
    		}
    		columnas.push(_4_botones);
    		Ext.apply(this,
    		{
    			title      : '<s:property value="smap1.gridTitle" />'
    			,store     : mcdinStore
    			,minHeight : 200
    			,selType   : 'checkboxmodel'
    			,columns   : columnas
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
		    	    <s:property value="imap1.botonesTramite" />
		    	]
		    	,buttons       :
		    	[
					{
					    text     : 'Reporte'
					    ,icon    : '${ctx}/resources/fam3icons/icons/printer.png'
					    ,hidden  : mcdinInput['tiptra']!='1'
					    ,handler : function()
					    {
					        Ext.MessageBox.confirm('Confirmar', '¿Generar reporte con la sucursal y producto seleccionados?', function(btn)
					        {
					            if(btn === 'yes')
					            {   
					                Ext.create('Ext.form.Panel').submit(
					                {
					                    standardSubmit : true,
					                    url:_4_urlReporte,
					                    params:
					                    {
					                        cdreporte : 'REPEXC007'
					                        ,'params.pv_cdunieco_i' : mcdinFiltro.items.items[0].getValue()
					                        ,'params.pv_cdramo_i'   : mcdinFiltro.items.items[1].getValue()
					                    },
					                    success: function(form, action)
					                    {
					                        
					                    },
					                    failure: function(form, action)
					                    {
					                        switch (action.failureType)
					                        {
					                            case Ext.form.action.Action.CONNECT_FAILURE:
					                                Ext.Msg.alert('Error', 'Error de comunicaci&oacute;n');
					                                break;
					                            case Ext.form.action.Action.SERVER_INVALID:
					                            case Ext.form.action.Action.LOAD_FAILURE:
					                                Ext.Msg.alert('Error', 'Error del servidor, consulte a soporte');
					                                break;
					                       }
					                    }
					                });
					            }
					        });
					    }
					}
					,{
                        text     : 'Limpiar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/control_repeat_blue.png'
                        ,handler : function()
                        {
                            this.up().up().getForm().reset();
                        }
                    }
		    	    ,{
		    	    	text     : 'Buscar'
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
		    	    			mensajeWarning('Favor de introducir los campos requeridos');
		    	    		}
		    	    	}
		    	    }
		    	]
    		});
    		if(this.items[0])
    		{
    			this.items[0].forceSelection=false;
    		}
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
                                mensajeWarning('Favor de introducir los campos requeridos');
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
    if(mcdinFormNuevo
    		&&mcdinFormNuevo.items
    		&&mcdinFormNuevo.items.items[0]
            &&mcdinFormNuevo.items.items[0].items
            &&mcdinFormNuevo.items.items[0].items.items[8])
    {
    	var comboTmp =mcdinFormNuevo.items.items[0].items.items[8];
    	debug('combo tooltip',comboTmp);
    	comboTmp.addListener('afterrender',function()
    	{
		    Ext.create('Ext.tip.ToolTip',
		    {
		        target : comboTmp.getEl()
		        ,html  : 'Usar transferencia cuando es cambio de producto'
		    });    		
    	});
    }
    
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
    loadMcdinStore = function (){
    	var params = {
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
		   };
    	
    	cargaStorePaginadoLocal(mcdinStore, mcdinUrlCargar, 'olist1', params, function (options, success, response){
    		if(success){
                var jsonResponse = Ext.decode(response.responseText);
                
                if(!jsonResponse.success) {
                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
                }
            }else{
                showMessage('Error', 'Error al obtener los datos.', Ext.Msg.OK, Ext.Msg.ERROR);
            }
    	}, mcdinGrid);
    	
    }
    
    loadMcdinStore();
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