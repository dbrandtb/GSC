<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
//////////////////////////
////// variables    //////
/*//////////////////////*/
var panDocInputNmpoliza  = '<s:property value="smap1.nmpoliza" />';
var panDocInputCdunieco  = '<s:property value="smap1.cdunieco" />';
var panDocInputCdramo    = '<s:property value="smap1.cdramo" />';
var panDocInputEstado    = '<s:property value="smap1.estado" />';
var panDocInputNmsuplem  = '<s:property value="smap1.nmsuplem" />';
var panDocInputNtramite  = '<s:property value="smap1.ntramite" />';
var panDocInputNmsolici  = '<s:property value="smap1.nmsolici" />';
var panDocStoreDoc;
var panDocUrlCargar      = '<s:url namespace="/documentos" action="ventanaDocumentosPolizaLoad" />';
var panDocGridDocu;
var panDocContexto       = '${ctx}';
var panDocUrlUploadDoc   = '<s:url namespace="/" action="subirArchivo" />';
var panDocUrlUploadPro   = '<s:url namespace="/" action="subirArchivoMostrarBarra" />';
var panDocUrlDownload    = '<s:url namespace ="/documentos" action="descargaDoc" />';
var panDocUrlViewDoc     = '<s:url namespace ="/documentos" action="descargaDocInline" />';
/*//////////////////////*/
////// variables    //////
//////////////////////////

//////////////////////////
////// funciones    //////
/*//////////////////////*/
function panDocSubido()
{
    Ext.getCmp('panDocWinPopupAddDoc').destroy();
    panDocStoreDoc.load();
}
/*//////////////////////*/
////// funciones    //////
//////////////////////////
Ext.onReady(function()
{
    //////////////////////////
    ////// modelos      //////
    /*//////////////////////*/
    Ext.define('Documento',
    {
        extend    : 'Ext.data.Model'
        ,fields   :
        [
            'nmsolici'
            ,'cddocume'
            ,'dsdocume'
            ,{name:'feinici',type:'date',dateFormat:'d/m/Y'}
            ,'liga'
            ,'ntramite'
        ]
    });
    /*//////////////////////*/
    ////// modelos      //////
    //////////////////////////
    
    //////////////////////////
    ////// stores       //////
    /*//////////////////////*/
    panDocStoreDoc=Ext.create('Ext.data.Store',
    {
        model       : 'Documento'
        ,autoLoad   : true
        ,proxy      :
        {
            url          : panDocUrlCargar
            ,extraParams : 
            {
                'smap1.pv_nmpoliza_i'  : panDocInputNmpoliza
                ,'smap1.pv_cdunieco_i' : panDocInputCdunieco
                ,'smap1.pv_cdramo_i'   : panDocInputCdramo
                ,'smap1.pv_estado_i'   : panDocInputEstado
                ,'smap1.pv_ntramite_i' : panDocInputNtramite
            }
            ,type        : 'ajax'
            ,reader      :
            {
                type  : 'json'
                ,root : 'slist1'
            }
        }
    });
    /*//////////////////////*/
    ////// stores       //////
    //////////////////////////
    
    //////////////////////////
    ////// componentes  //////
    /*//////////////////////*/
    Ext.define('PanDocGridDocu',
    {
        extend         : 'Ext.grid.Panel'
        ,store         : panDocStoreDoc
        //,autoScroll  : true
        //,title         : 'Documentos'
        //,collapsible   : true
        //,titleCollapse : true
        ,onAddClick : function(button,e)
        {
            Ext.create('Ext.window.Window',
            {
                id           : 'panDocWinPopupAddDoc'
                ,title       : 'Agregar documento a poliza'//+panDocInputNmpoliza
                ,closable    : false
                ,modal       : true
                ,width       : 500
                ,maxHeight   : 400
                ,bodyPadding : 5
                ,items       :
                [
                    Ext.create('Ext.form.Panel',
                    {
                        border       : 0
                        ,url         : panDocUrlUploadDoc
                        ,buttonAlign : 'center'
                        ,items       :
                        [
                            {
                                xtype       : 'textfield'
                                ,value      : panDocInputNtramite
                                ,readOnly   : true
                                ,fieldLabel : 'N&uacute;mero de tramite'
                                ,name       : 'smap1.ntramite'
                                ,hidden     : true
                            }
                            ,{
                                xtype       : 'datefield'
                                ,format     : 'd/m/Y'
                                ,name       : 'smap1.fecha'
                                ,value      : new Date()
                                ,fieldLabel : 'Fecha'
                            }
                            ,{
                            	xtype       : 'textfield'
                            	,fieldLabel : 'Descripci&oacute;n'
                            	,name       : 'smap1.descripcion'
                           		,width      : 450
                            }
                            ,{
                                xtype       : 'filefield'
                                ,fieldLabel : 'Documento'
                                ,buttonText : 'Examinar...'
                                ,buttonOnly : false
                                ,width      : 450
                                ,name       : 'file'
                                ,cAccept    : ['jpg','png','gif','zip','pdf','rar','jpeg','doc','docx','xls','xlsx','ppt','pptx']
                                ,listeners  :
                                {
                                    change : function(me)
                                    {
                                        var indexofPeriod = me.getValue().lastIndexOf("."),
                                        uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod);
                                        if (!Ext.Array.contains(this.cAccept, uploadedExtension))
                                        {
                                            Ext.MessageBox.show(
                                            {
                                                title   : 'Error de tipo de archivo',
                                                msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
                                                buttons : Ext.Msg.OK,
                                                icon    : Ext.Msg.WARNING
                                            });
                                            me.reset();
                                            Ext.getCmp('panDocBotGuaDoc').setDisabled(true);
                                        }
                                        else
                                        {
                                            Ext.getCmp('panDocBotGuaDoc').setDisabled(false);
                                        }
                                    }
                                }
                            }
                            ,Ext.create('Ext.panel.Panel',
                            {
                                html    :'<iframe id="panDocIframeUploadDoc" name="panDocIframeUploadDoc"></iframe>'
                                ,hidden : true
                            })
                            ,Ext.create('Ext.panel.Panel',
                            {
                                border  : 0
                                ,html   :'<iframe id="panDocIframeUploadPro" name="panDocIframeUploadPro" width="100%" height="30" src="'+panDocUrlUploadPro+'" frameborder="0"></iframe>'
                                ,hidden : false
                            })
                        ]
                        ,buttons     :
                        [
                            {
                                id        : 'panDocBotGuaDoc'
                                ,text     : 'Agregar'
                                ,icon     : panDocContexto+'/resources/fam3icons/icons/disk.png'
                                ,disabled : true
                                ,handler  : function (button,e)
                                {
                                    debug(button.up().up().getForm().getValues());
                                    button.setDisabled(true);
                                    Ext.getCmp('panDocBotCanDoc').setDisabled(true);
                                    Ext.create('Ext.form.Panel').submit(
                                    {
                                        url             : panDocUrlUploadPro
                                        ,standardSubmit : true
                                        ,target         : 'panDocIframeUploadPro'
                                        ,params         :
                                        {
                                        	uploadKey : '1'
                                        }
                                    });
                                    button.up().up().getForm().submit(
                                    {
                                        standardSubmit : true
                                        ,target        : 'panDocIframeUploadDoc'
                                        ,params        :
                                       	{
                                        	'smap1.cdunieco'  : panDocInputCdunieco
                                        	,'smap1.cdramo'   : panDocInputCdramo
                                        	,'smap1.estado'   : panDocInputEstado
                                        	,'smap1.nmsuplem' : panDocInputNmsuplem
                                        	,'smap1.nmpoliza' : panDocInputNmpoliza
                                        	,'smap1.nmsolici' : panDocInputNmsolici
                                       	}
                                    });
                                }
                            }
                            ,{
                            	id       : 'panDocBotCanDoc'
                                ,text    : 'Cancelar'
                                ,icon    : panDocContexto+'/resources/fam3icons/icons/cancel.png'
                                ,handler : function (button,e)
                                {
                                    button.up().up().up().destroy();
                                }
                            }
                        ]
                    })
                ]
            }).show();
        }
        ,initComponent   : function()
        {
            debug('initComponent');
            Ext.apply(this,
            {
                columns :
                [
                    {
                        header     : 'Descripci&oacute;n'
                        ,dataIndex : 'dsdocume'
                        ,flex      : 2
                    }
                    ,{
                        header     : 'Fecha'
                        ,dataIndex : 'feinici'
                        ,flex      : 1
                        ,renderer  : Ext.util.Format.dateRenderer('d M Y')
                    }
                    ,{
                    	dataIndex     : 'liga'
                    	,width        : 30
                    	,renderer     : function(value)
                    	{
                    		debug(value);
                    		var res='';
                            var splited=value.split('#_#');//nombre#_#descripcion
                            debug(splited);
                            var nom=splited[0];
                            var desc=splited[1];
                            if(nom&&nom.length>4)
                            {
                                var http=nom.substr(0,4);
                                if(true||http!='http')
                                {
                                    res='<img src="${ctx}/resources/fam3icons/icons/eye.png" data-qtip="Abrir en línea" style="cursor:pointer;" />';
                                }
                            }
                            return res;
                    	}
                    }
                    ,{
                        dataIndex     : 'liga'
                        ,width        : 30
                        ,renderer     : function(value)
                        {
                            debug(value);
                            var res='';
                            var splited=value.split('#_#');//nombre#_#descripcion
                            debug(splited);
                            var nom=splited[0];
                            var desc=splited[1];
                            if(nom&&nom.length>4)
                            {
                                var http=nom.substr(0,4);
                                if(http!='http')
                                {
                                    res='<img src="${ctx}/resources/fam3icons/icons/page_white_put.png" data-qtip="Descargar" style="cursor:pointer;" />';
                                }
                            }
                            return res;
                        }
                    }
                    /*
                    ,{
                        header        : 'Acciones'
                        ,xtype        : 'actioncolumn'
                        //,dataIndex    : 'cddocume'
                        ,menuDisabled : true
                        ,width        : 80
                        ,items:
                        [
                            {
                                icon     : panDocContexto+'/resources/fam3icons/icons/eye.png'
                                ,tooltip : 'Abrir en l&iacute;nea' 
                                ,handler : this.onViewClick
                            }
                            ,
                            {
                                icon     : panDocContexto+'/resources/fam3icons/icons/page_white_put.png'
                                ,tooltip : 'Descargar' 
                                ,handler : this.onDownloadClick
                            }
                        ]
                    }
                    */
                ]
                <s:if test='!smap1.containsKey("readOnly")'>
                ,dockedItems :
                [
                    {
                        xtype  : 'toolbar'
                        ,dock  : 'top'
                        ,items :
                        [
                            {
                                xtype    : 'button'
                                ,text    : 'Agregar'
                                ,icon    : panDocContexto+'/resources/fam3icons/icons/add.png'
                                ,handler : this.onAddClick
                            }
                        ]
                    }
                ]
                </s:if>
                ,listeners:
                {
                	cellclick : function(grid, td,
                            cellIndex, record, tr,
                            rowIndex, e, eOpts)
                	{
                		debug( cellIndex+'x', rowIndex+'y' , record );
                		if(cellIndex==2)//ver
                		{
                			debug($(td).find('img').length);
                			if($(td).find('img').length>0)//si hay accion
                			{
                				var nom=record.get('cddocume');
                				var salida=false;
                                if(nom&&nom.length>4)
                                {
                                    var http=nom.substr(0,4);
                                    if(http=='http')
                                    {
                                    	salida=true;
                                        window.open(nom,'_blank','width=800,height=600');
                                    }
                                }
                                if(!salida)
                                {
                                    this.onViewClick(grid,rowIndex,cellIndex);
                                }
                			}
                		}
                		else if(cellIndex==3)//descargar
                		{
                			debug($(td).find('img').length);
                			if($(td).find('img').length>0)//si hay accion
               				{
                				this.onDownloadClick(grid,rowIndex,cellIndex);
               				}
                		}
                	}
                }
            });
            this.callParent();
        }
        , onDownloadClick : function(grid,rowIndex,colIndex)
        {
        	debug(rowIndex,colIndex);
        	var record=grid.getStore().getAt(rowIndex);
        	Ext.create('Ext.form.Panel').submit(
            {
                url              : panDocUrlDownload
                , standardSubmit : true
                , target         : '_blank'
                , params         :
                {
                    idPoliza  : record.get('ntramite')
                    ,filename : record.get('cddocume') 
                }
            });
        }
        ,onViewClick:function(grid,rowIndex,colIndex)
        {
        	debug(rowIndex,colIndex);
        	var record=grid.getStore().getAt(rowIndex);
        	window.open(panDocUrlViewDoc+'?idPoliza='+record.get('ntramite')+'&filename='+record.get('cddocume'),'_blank','width=800,height=600');
        }
    });
    /*//////////////////////*/
    ////// componentes  //////
    //////////////////////////
    
    //////////////////////////
    ////// contenido    //////
    /*//////////////////////*/
    panDocGridDocu=new PanDocGridDocu();
    panDocGridDocu.render('pan_doc_maindiv');
    /*//////////////////////*/
    ////// contenido    //////
    //////////////////////////
    
    //////////////////////////
    ////// cargador     //////
    /*//////////////////////*/
    /*//////////////////////*/
    ////// cargador     //////
    //////////////////////////
});
</script>
<div id="pan_doc_maindiv" style="height:400px;"></div>