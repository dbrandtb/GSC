<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
///////////////////////
////// overrides //////
/*///////////////////*/
Ext.define('App.overrides.view.Table',
{
    override: 'Ext.view.Table',
    getRecord: function (node) {
        node = this.getNode(node);
        if (node) {
            return this.dataSource.data.get(node.getAttribute('data-recordId'));
        }
    },
    indexInStore: function (node) {
        node = this.getNode(node, true);
        if (!node && node !== 0) {
            return -1;
        }
        return this.dataSource.indexOf(this.getRecord(node));
    }
});
/*///////////////////*/
////// overrides //////
///////////////////////
	
//////////////////////////
////// variables    //////
/*//////////////////////*/
var panDocInputNmpoliza  = '<s:property value="params.nmpoliza" />';
var panDocInputCdunieco  = '<s:property value="params.cdunieco" />';
var panDocInputCdramo    = '<s:property value="params.cdramo" />';
var panDocInputEstado    = '<s:property value="params.estado" />';
var panDocInputNmsuplem  = '<s:property value="params.nmsuplem" />';
var panDocInputNtramite  = '<s:property value="params.ntramite" />';
var panDocInputNmsolici  = '<s:property value="params.nmsolici" />';
var panDocInputTipoMov   = '<s:property value="params.tipomov" />';
var _nmTramite = '<s:property value="params.nmTramite" />';
var _tipoPago = '<s:property value="params.cdTipoPago" />';
var _tipoAtencion = '<s:property value="params.cdTipoAtencion" />';

var panDocStoreDoc;
var panDocUrlCargar      = '<s:url namespace="/documentos" action="cargaDocumentosSiniestro" />';
var panDocGridDocu;
var panDocContexto       = '${ctx}';
var panDocUrlUploadDoc   = '<s:url namespace="/" action="subirArchivo" />';
var panDocUrlUploadPro   = '<s:url namespace="/" action="subirArchivoMostrarBarra" />';
var panDocUrlDownload    = '<s:url namespace ="/documentos" action="descargaDoc" />';
var panDocUrlViewDoc     = '<s:url namespace ="/documentos" action="descargaDocInline" />';
var venDocUrlImpConrec   = '<s:url namespace ="/documentos" action="generarContrarecibo" />';

var _URL_ListaDocumentos =      '<s:url namespace="/siniestros" action="loadListaDocumentos" />';

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
            ,{name:'selected',type:'boolean'}
            ,'tipmov'
            ,'nmsuplem'
            ,'orden'
            ,'nsuplogi'
        ]
    });
    
    Ext.define('modeloConfigDocumentos',{
        extend: 'Ext.data.Model',
        fields: [{type:'string',    name:'id'},
                 {type:'boolean',   name:'listo'},
                 {type:'string',    name:'nombre'},
                 {type:'string',    name:'obligatorio'}
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
        //,groupField : 'orden'
        ,groupers   :
        [
            {
            	property  : 'orden'
            	,sorterFn : function(recordA,recordB)
            	{
            		var nmsuplemA=recordA.get('nsuplogi')*1;
            		var nmsuplemB=recordB.get('nsuplogi')*1;
            		var r=0;
            		if(nmsuplemA>nmsuplemB)
            			r=-1;
            		else if(nmsuplemA==nmsuplemB)
            			r=0;
            		else
            			r=1;
            		//debug(nmsuplemA,nmsuplemB,r);
            		return r;
            	}
            }
        ]
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
    
    
    var storeConfigDocs = Ext.create('Ext.data.JsonStore', {
    	model:'modeloConfigDocumentos',
        proxy: {
            type: 'ajax',
            url: _URL_ListaDocumentos,
            reader: {
                type: 'json',
                root: 'loadList'
            }
        }
    });
    storeConfigDocs.load({
    	params: {
    		'params.pv_cdtippag_i' : _tipoPago,
    		'params.pv_cdtipate_i' : _tipoAtencion,
    		'params.pv_nmtramite_i' : _nmTramite
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
        ,height        : 300
        ,onAddClick : function(button,e)
        {
            var windowAgregarDocu=Ext.create('Ext.window.Window',
            {
                id           : 'panDocWinPopupAddDoc'
                ,title       : 'Agregar documento a siniestro'//+panDocInputNmpoliza
                ,closable    : false
                ,modal       : true
                ,width       : 500
                //,height   : 700
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
                                ,readOnly   : true
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
                            },
                            {
            					xtype:'combobox',
                    			fieldLabel: 'Asociar a Documento',
                    			hiddenName: 'docuAsociado',
                    			emptyText:'Seleccione...',
                    			queryMode:'local',
                    			allowBlank:false,
                    			typeAhead:true,
                    			store: storeConfigDocs,
                    			forceSelection: true,
                				valueField: 'id',
                				displayField: 'nombre',
                				triggerAction: 'all'

            				}
                            ,{
                                xtype       : 'filefield'
                                ,fieldLabel : 'Archivo'
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
                                        uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
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
                                        	,'smap1.tipomov'  : panDocInputTipoMov
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
            windowAgregarDocu.center();
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
                    
                ]
	            ,features: [{
	                groupHeaderTpl:
	                    [
	                        '{name:this.formatName}',
	                        {
	                            formatName:function(name)
	                            {
	                            	var splited=name.split("#_#");
	                                return splited[1]+(splited[2]*1>0?(' ('+splited[2]+')'):'');
	                            }
	                        }
	                    ],
	                ftype:'groupingsummary',
	                startCollapsed :true
	            }]
                ,dockedItems :
                [
                    {
                        xtype  : 'toolbar'
                        ,dock  : 'top'
                        ,items :
                        [
			                <s:if test='!smap1.containsKey("readOnly")'>
                            {
                                xtype    : 'button'
                                ,text    : 'Agregar'
                                ,icon    : panDocContexto+'/resources/fam3icons/icons/add.png'
                                ,handler : this.onAddClick
                            }
			                </s:if>
                            
                        ]
                    }
                ]
                ,listeners:
                {
                	cellclick : function(grid, td,
                            cellIndex, record, tr,
                            rowIndex, e, eOpts)
                	{
                		debug( cellIndex+'x', rowIndex+'y' , record.raw );
                		if(cellIndex==2)//ver
                		{
                			debug($(td).find('img').length);
                			if($(td).find('img').length>0)//si hay accion
                			{
                				var nom=record.get('cddocume');
                				debug(nom);
                				var salida=false;
                                if(nom&&nom.length>4)
                                {
                                    var http=nom.substr(0,4);
                                    if(http=='http')
                                    {
                                    	salida=true;
                                    	var numRand=Math.floor((Math.random()*100000)+1);
                                        debug('numRand b: ',numRand);
                                    	var windowVerDocu=Ext.create('Ext.window.Window',
                                        {
                                            title          : record.get('dsdocume')
                                            ,width         : 700
                                            ,height        : 500
                                            ,collapsible   : true
                                            ,titleCollapse : true
                                            ,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
                                                             +'src="'+nom+'">'
                                                             +'</iframe>'
                                            ,listeners     :
                                            {
                                                resize : function(win,width,height,opt){
                                                    debug(width,height);
                                                    $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
                                                }
                                            }
                                        }).show();
                                        windowVerDocu.center();
                                        //window.open(,'_blank','width=800,height=600');
                                    }
                                }
                                if(!salida)
                                {
                                    this.onViewClick(record);
                                }
                			}
                		}
                		else if(cellIndex==3)//descargar
                		{
                			debug($(td).find('img').length);
                			if($(td).find('img').length>0)//si hay accion
               				{
                				this.onDownloadClick(record);
               				}
                		}
                	}
                }
            });
            this.callParent();
        }
        , onDownloadClick : function(record)
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
        ,onViewClick:function(record)
        {
        	var numRand=Math.floor((Math.random()*100000)+1);
        	debug('numRand a: ',numRand);
        	var windowVerDocu=Ext.create('Ext.window.Window',
        	{
        		title          : record.get('dsdocume')
        		,width         : 700
        		,height        : 500
        		,collapsible   : true
        		,titleCollapse : true
        		,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
        		                 +'src="'+panDocUrlViewDoc+'?idPoliza='+record.get('ntramite')+'&filename='+record.get('cddocume')+'">'
        		                 +'</iframe>'
        		,listeners     :
        		{
        			resize : function(win,width,height,opt){
                        debug(width,height);
                        $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
                    }
        		}
        	}).show();
        	windowVerDocu.center();
        	//window.open(,'_blank','width=800,height=600');
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
    //Ext.getCmp('venDocMenuSupBotGenConrec').hide();
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