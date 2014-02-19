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
/*///////////////////*/
////// overrides //////
///////////////////////
	
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
var panDocInputTipoMov   = '<s:property value="smap1.tipomov" />';
var panDocStoreDoc;
var panDocUrlCargar      = '<s:url namespace="/documentos" action="ventanaDocumentosPolizaLoad" />';
var panDocGridDocu;
var panDocContexto       = '${ctx}';
var panDocUrlUploadDoc   = '<s:url namespace="/" action="subirArchivo" />';
var panDocUrlUploadPro   = '<s:url namespace="/" action="subirArchivoMostrarBarra" />';
var panDocUrlDownload    = '<s:url namespace ="/documentos" action="descargaDoc" />';
var panDocUrlViewDoc     = '<s:url namespace ="/documentos" action="descargaDocInline" />';
var venDocUrlImpConrec   = '<s:url namespace ="/documentos" action="generarContrarecibo" />';

var _URLhabilitaSigRec   = '<s:url namespace ="/documentos" action="habilitaSigRec" />';

var panDocSmap1 = <s:property value='%{getSmap1().toString().replace("=",":\'").replace(",","\',").replace("}","\'}")}' />;

debug('panDocSmap1:',panDocSmap1);

var panDocStoreConfigDocs;
var urlComboDocumentos = '<s:url namespace="/siniestros" action="loadListaDocumentos" />';
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
    
    Ext.define('modeloConfigDocumentos',
    {
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
    
    panDocStoreConfigDocs = Ext.create('Ext.data.JsonStore',
    {
        model  :'modeloConfigDocumentos',
        proxy :
        {
            type   : 'ajax',
            url    : urlComboDocumentos,
            reader : 
            {
                type : 'json',
                root : 'loadList'
            }
        }
    });
    
    panDocStoreConfigDocs.load(
    {
        params:
        {
            'params.pv_cdtippag_i'  : panDocSmap1.cdtippag,
            'params.pv_cdtipate_i'  : panDocSmap1.cdtipate,
            'params.pv_nmtramite_i' : panDocSmap1.ntramite
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
        ,onContrareciboClick : function(button,e)
        {
        	var window=button.up().up();
        	debug('contrarecibo');
        	/*var docConRec=[];
        	panDocStoreDoc.each(function(record)
            {
                if(record.get('selected')==true)
                {
                    docConRec.push
                    ({
                    	pv_ntramite_i  : record.get('ntramite')
                    	,pv_cddocume_i : record.get('cddocume')
                    	,pv_dsdocume_i : record.get('dsdocume')
                    });
                }
            });
        	var jsonParams={};
        	jsonParams['slist1']=docConRec;
        	debug(jsonParams);*/
        	window.setLoading(true);
        	Ext.Ajax.request
        	({
        		url       : venDocUrlImpConrec
        		//,jsonData : Ext.encode(jsonParams)
        		,params   :
        		{
        			'smap1.ntramite' : panDocInputNtramite
        		}
        		,success  : function(response)
        		{
        			var json=Ext.decode(response.responseText);
        			if(json.success==true)
        			{
        				window.setLoading(false);
        				var numRand=Math.floor((Math.random()*100000)+1);
        	            debug(numRand);
        	            var windowVerDocu=Ext.create('Ext.window.Window',
        	            {
        	                title          : 'Contrarecibo'
        	                ,width         : 700
        	                ,height        : 500
        	                ,collapsible   : true
        	                ,titleCollapse : true
        	                ,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
        	                                 +'src="'+panDocUrlViewDoc+'?idPoliza='+panDocInputNtramite+'&filename='+json.uploadKey+'">'
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
        			}
        			else
        			{
        				window.setLoading(false);
        				Ext.Msg.show({
                            title:'Error',
                            msg: 'Error al generar contrarecibo',
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.ERROR
                        });
        			}
        		}
        		,failure  : function()
        		{
        			window.setLoading(false);
        			Ext.Msg.show({
                        title:'Error',
                        msg: 'Error de comunicaci&oacute;n',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
        		}
        	});
        }
        ,onAddClick : function(button,e)
        {
            var windowAgregarDocu=Ext.create('Ext.window.Window',
            {
                id           : 'panDocWinPopupAddDoc'
                ,title       : 'Agregar documento'//+panDocInputNmpoliza
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
                            ,{
                                xtype          :'combobox',
                                fieldLabel     : 'Asociar a Documento',
                                hiddenName     : 'docuAsociado',
                                emptyText      :'Seleccione...',
                                queryMode      :'local',
                                allowBlank     :true,
                                typeAhead      :true,
                                store          : panDocStoreConfigDocs,
                                forceSelection : true,
                                editable       : true,
                                valueField     : 'id',
                                displayField   : 'nombre',
                                triggerAction  : 'all'
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
            centrarVentanaInterna(windowAgregarDocu);
        }
        ,initComponent   : function()
        {
            debug('initComponent');
            Ext.apply(this,
            {
                columns :
                [
                    /*{
                    	width      : 30
                    	,dataIndex : 'selected'
                    	,xtype     : 'checkcolumn'
                    	,listeners :
                    	{
                    		checkchange : function( me, rowIndex, checked, eOpts )
	                    	{
	                    		debug('checkchange');
	                    		var haySelected=false;
	                            panDocStoreDoc.each(function(record)
	                            {
	                                if(record.get('selected')==true)
	                                {
	                                    haySelected=true;
	                                }
	                            });
	                            if(haySelected==true)
	                            {
	                                Ext.getCmp('venDocMenuSupBotGenConrec').show();
	                            }
	                            else
	                            {
	                            	Ext.getCmp('venDocMenuSupBotGenConrec').hide();
	                            }
	                    	}
                    	}
                    }
                    ,*/{
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
                            },
			                </s:if>
                            {
                            	xtype    : 'button'
                            	,id      : 'venDocMenuSupBotGenConrec'
	                            ,text    : 'Generar contrarecibo'
	                            ,icon    : '${ctx}/resources/fam3icons/icons/page_attach.png'
	                            ,handler : this.onContrareciboClick
	                            ,hidden  : panDocSmap1.cdtiptra!='1' && panDocSmap1.cdtiptra!='15'
                            },
                            '-',
                            { xtype: 'tbspacer', width: 50 }, 
                            {
                            	xtype    : 'button'
                            	,id      : 'habilitaRec'
	                            ,text    : 'Habilitar siguiente Recibo'
	                            ,icon    : '${ctx}/resources/fam3icons/icons/table_go.png'
	                            ,hidden  : panDocSmap1.cdtiptra!='1' && panDocSmap1.cdtiptra!='15'
	                            ,handler : function (button, evt){
	                            	var window=button.up().up();
	                            	
	                            	window.setLoading(true);
	                            	Ext.Ajax.request({
	                            		url       : _URLhabilitaSigRec
	                            		//,jsonData : Ext.encode(jsonParams)
	                            		,params   : {
	                            			'smap1.pv_cdunieco_i' : panDocInputCdunieco,
	                            			'smap1.pv_cdramo_i'   : panDocInputCdramo,
	                            			'smap1.pv_estado_i'   : panDocInputEstado,
	                            			'smap1.pv_nmpoliza_i' : panDocInputNmpoliza,
	                            			
	                            		}
	                            		,success  : function(response){
	                            			window.setLoading(false);
	                            			
	                            			var json=Ext.decode(response.responseText);
	                            			
	                            			if(json.success==true)
	                            			{
	                            				panDocStoreDoc.load();
	                            				Ext.Msg.show({
	                                                title:'Aviso',
	                                                msg: json.progresoTexto,
	                                                buttons: Ext.Msg.OK,
	                                                icon:'x-message-box-ok'  
	                                            });
	                            			}
	                            			else
	                            			{
	                            				Ext.Msg.show({
	                                                title:'Error',
	                                                msg: json.progresoTexto,
	                                                buttons: Ext.Msg.OK,
	                                                icon: Ext.Msg.ERROR
	                                            });
	                            			}
	                            		}
	                            		,failure  : function()
	                            		{
	                            			window.setLoading(false);
	                            			Ext.Msg.show({
	                                            title:'Error',
	                                            msg: 'Error de comunicaci&oacute;n',
	                                            buttons: Ext.Msg.OK,
	                                            icon: Ext.Msg.ERROR
	                                        });
	                            		}
	                            	});
	                            }
                            }
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