<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
.x-action-col-icon {
    height: 16px;
    width: 16px;
    margin-right: 8px;
}
</style>
<script>
    ///////////////////////
    ////// variables //////
    /*///////////////////*/
    var mesConUrlLoadTareas   = '<s:url namespace="/mesacontrol"     action="loadTareas" />';
    var mesConUrlDocu         = '<s:url namespace="/documentos"      action="ventanaDocumentosPoliza" />';
    var mesConUrlDatCom       = '<s:url namespace="/"                action="datosComplementarios" />';
    var mesConUrlInitManual   = '<s:url namespace="/mesacontrol"     action="obtenerValoresDefectoInsercionManual" />';
    var mesConUrlSaveTra      = '<s:url namespace="/mesacontrol"     action="guardarTramiteManual" />';
    var mesConUrlLoadCatalo   = '<s:url namespace="/flujocotizacion" action="cargarCatalogos" />';
    var mesConUrlCotizar      = '<s:url namespace="/"                action="cotizacionVital" />';
    var mesConUrlDetMC        = '<s:url namespace="/mesacontrol"     action="obtenerDetallesTramite" />';
    var mesConUrlFinDetalleMC ='<s:url namespace="/mesacontrol"      action="finalizarDetalleTramiteMC" />';
    var mesConStoreTareas;
    var mesConGridTareas;
    var mesConStoreUniAdmin;
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
    
	debug('ready');
	
    /////////////////////
    ////// modelos //////
    /*/////////////////*/
    Ext.define('Tarea',{
        extend:'Ext.data.Model',
        fields:
        [
			"ntramite","cdunieco","cdramo","estado","nmpoliza",
			"nmsolici","cdsucadm","cdsucdoc","cdsubram","cdtiptra",{name:"ferecepc",type:"date",dateFormat:"d/m/Y"},"cdagente",
			"Nombre_agente","referencia","nombre",{name:"fecstatu",type:"date",dateFormat:"d/m/Y"},"status","comments"
        ]
    });
    
    Ext.define('DetalleMC',{
        extend:'Ext.data.Model',
        fields:
        [
            "NTRAMITE"
            ,"NMORDINA"
            ,"CDTIPTRA"
            ,"CDCLAUSU"
            ,{name:"FECHAINI",type:'date',dateFormat:'d/m/Y'}
            ,{name:"FECHAFIN",type:'date',dateFormat:'d/m/Y'}
            ,"COMMENTS"
            ,"CDUSUARI_INI"
            ,"CDUSUARI_FIN"
        ]
    });
    /*/////////////////*/
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/
    mesConStoreTareas=Ext.create('Ext.data.Store',
    {
        pageSize : 10,
        autoLoad : true,
        model    : 'Tarea',
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
    
    mesConStoreUniAdmin=[];
    
    Ext.Ajax.request(
    {
    	url      : mesConUrlLoadTareas
    	,success : function(response)
    	{
    		var jsonResponse = Ext.decode(response.responseText);
    		debug(jsonResponse);
    		mesConStoreTareas.setProxy(
            {
                type         : 'memory',
                enablePaging : true,
                reader       : 'json',
                data         : jsonResponse.slist1
            });
    		mesConStoreTareas.load();
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
    /*////////////////*/
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    /*/////////////////////*/
    Ext.define('GridTareas',
    {
    	extend         : 'Ext.grid.Panel'
        ,title         : 'Tareas'
        //,width         : 600
        ,height        : 410
        ,buttonAlign   : 'center'
        ,store         : mesConStoreTareas
        ,bbar     :
        {
            displayInfo : true
            ,store      : mesConStoreTareas
            ,xtype       : 'pagingtoolbar'
        }
        ,initComponent : function()
        {
            debug('initComponent');
            Ext.apply(this,
            {
                columns     :
                [
                    {
                        header     : 'Tr&aacute;mite'
                        ,dataIndex : 'ntramite'
                        ,width     : 60 
                    }
                    ,
                    {
                    	header     : 'Sucursal'
                    	,dataIndex : "cdsucdoc"
                    	,width     : 60
                    }
                    ,{
                    	header     : 'Subramo'
                    	,dataIndex : "cdsubram"
                    	,width     : 60
                    }
                    ,{
                        header     : 'Estado<br/>de solicitud'
                        ,dataIndex : 'estado'
                        ,renderer  : function(estado)
                        {
                            var label='';
                            if (estado=='W')
                            {
                                label='Tr&aacute;mite';
                            }
                            else if(estado=='M')
                            {
                                label='P&oacute;liza';
                            }
                            return label;
                        }
                        ,width     : 80
                    }
                    ,{
                    	header     : 'Fecha<br/>de captura'
                    	,dataIndex : 'ferecepc'
                    	,width     : 90
                    	,renderer  : Ext.util.Format.dateRenderer('d M Y')
                    }
                    ,{
                        header     : 'Fecha<br/>de estatus'
                        ,dataIndex : 'fecstatu'
                        ,width     : 90
                        ,renderer  : Ext.util.Format.dateRenderer('d M Y')
                    }
                    ,{
                        header     : 'P&oacute;liza'
                        ,dataIndex : 'nmpoliza'
                        ,width     : 70
                    }
                    ,{
                        header     : 'Cotizaci&oacute;n'
                            ,dataIndex : 'nmsolici'
                            ,width     : 70
                     }
                     ,{
                    	header     : 'Estado'
                    	,dataIndex : 'status'
                    	,width     : 90
                    	,renderer  : function (value)
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
                    			res='RechazadA';
                    		}
                    		else if(value=='5')
                    		{
                    			res='Vo. Bo. Médico';
                    		}
                    		return res;
                    	}
                    }
                    
                    ,{
                        header     : 'Responsable'
                        ,dataIndex : 'Nombre_agente'
                        ,width     : 190
                    }
                    
                    ,{
                        xtype         : 'actioncolumn'
                        ,menuDisabled : true
                        ,header       : 'Acciones'
                        ,width        : 100
                        ,items        :
                        [
                            {
                            	icon     : '${ctx}/resources/fam3icons/icons/page_attach.png'
	                            ,tooltip : 'Ver documentos'
                                ,handler : this.onFolderClick
                            }
                            ,{
                            	icon     : '${ctx}/resources/fam3icons/icons/folder.png'
                                ,tooltip : 'Complementar'
                                ,handler : this.onComplementariosClick
                            }
                            ,{
                            	icon     : '${ctx}/resources/fam3icons/icons/clock.png'
                            	,tooltip : 'Ver detalles del tr&aacute;mite'
                            	,handler : this.onClockClick
                            }
                        ]
                    }
                ]
                ,tbar: [{
                    icon    : '${ctx}/resources/fam3icons/icons/add.png',
                    text    : 'Agregar tr&aacute;mite',
                    scope   : this,
                    handler : this.onAddClick
                }]
            });
            this.callParent();
        }
        ,onClockClick  : function(grid,rowIndex)
        {
        	var record=grid.getStore().getAt(rowIndex);
            debug(record);
        	var window=Ext.create('Ext.window.Window',
        	{
        		title        : 'Detalles del tr&aacute;mite '+record.get('ntramite')
        		,modal       : true
        		,buttonAlign : 'center'
        		,width       : 600
        		,height      : 400
        		
        		,items       :
        		[
        		    Ext.create('Ext.grid.Panel',
        		    {
        		    	height      : 190
        		    	,autoScroll : true
        		    	,store      : new Ext.data.Store(
        		    	{
        		    		model     : 'DetalleMC'
        		    		,autoLoad : true
        		    		,proxy    :
        		    		{
        		    			type         : 'ajax'
        		    			,url         : mesConUrlDetMC
        		    			,extraParams :
        		    			{
        		    				'smap1.pv_ntramite_i' : record.get('ntramite')
        		    			}
        		    	        ,reader      :
        		    	        {
        		    	        	type  : 'json'
        		    	        	,root : 'slist1'
        		    	        }
        		    		}
        		    	})
        		        ,columns : 
        		        [
        		            {
        		            	header     : 'Tr&aacute;mite'
        		            	,dataIndex : 'NTRAMITE'
        		            	,width     : 60
        		            }
        		            ,{
        		            	header     : 'Consecutivo'
        		            	,dataIndex : 'NMORDINA'
        		            	,width     : 80
        		            }
        		            ,{
        		            	header     : 'Fecha de inicio'
        		            	,xtype     : 'datecolumn'
        		            	,dataIndex : 'FECHAINI'
        		            	,format    : 'd M Y'
       		            		,flex      : 1
        		            }
        		            ,{
                                header     : 'Fecha de fin'
                                ,xtype     : 'datecolumn'
                                ,dataIndex : 'FECHAFIN'
                                ,format    : 'd M Y'
                                ,flex      : 1
                            }
        		            ,{
        		            	width         : 30
        		            	,menuDisabled : true
        		            	,dataIndex    : 'FECHAFIN'
        		            	,renderer     : function(value)
        		            	{
        		            		debug(value);
        		            		if(value&&value!=null)
        		            		{
        		            			value='';
        		            		}
        		            		else
        		            		{
        		            			value='<img src="${ctx}/resources/fam3icons/icons/accept.png" style="cursor:pointer;" data-qtip="Finalizar" />';
        		            		}
        		            		return value;
        		            	}
        		            }
        		            ,{
                                width         : 30
                                ,menuDisabled : true
                                ,dataIndex    : 'CDCLAUSU'
                                ,renderer     : function(value)
                                {
                                    debug(value);
                                    if(value&&value!=null&&value.length>0)
                                    {
                                        value='<img src="${ctx}/resources/fam3icons/icons/printer.png" style="cursor:pointer;" data-qtip="Imprimir" />';
                                    }
                                    else
                                    {
                                        value='';
                                    }
                                    return value;
                                }
                            }
        		        ]
        		        ,listeners :
        		        {
        		        	cellclick : function(grid, td,
                                    cellIndex, record, tr,
                                    rowIndex, e, eOpts)
                            {
        		        		debug(record);
        		        		if(cellIndex<4)
        		        		{
        		        		    Ext.getCmp('inputReadDetalleHtmlVisor').setValue(record.get('COMMENTS'));
        		        		}
        		        		else if(cellIndex==4&&$(td).find('img').length>0)
        		        		{
        		        			debug('finalizar');
        		        			Ext.create('Ext.window.Window',
        		        			{
        		        				title        : 'Finalizar detalle'
	                                    ,width       : 600
	                                    ,height      : 400
	                                    ,buttonAlign : 'center'
	                                    ,modal       : true
	                                    ,closable    : false
	                                    ,autoScroll  : true
	                                    ,items       :
	                                    [
	                                        Ext.create('Ext.form.HtmlEditor', {
	                                            id      : 'inputHtmlEditorFinalizarDetalleMesCon'
	                                            ,width  : 570
	                                            ,height : 300
	                                            ,value  : record.get('COMMENTS')
	                                        })
	                                    ]
        		        			    ,buttons     :
        		        			    [
        		        			        {
        		        			        	text     : 'Guardar'
        		        			        	,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                                                ,handler : function()
                                                {
                                                    var win=this.up().up();
                                                    win.setLoading(true);
                                                    Ext.Ajax.request
                                                    ({
                                                    	url      : mesConUrlFinDetalleMC
                                                    	,params  :
                                                    	{
                                                    		'smap1.pv_ntramite_i'  : record.get('NTRAMITE')
                                                    		,'smap1.pv_nmordina_i' : record.get('NMORDINA')
                                                    		,'smap1.pv_comments_i' : Ext.getCmp('inputHtmlEditorFinalizarDetalleMesCon').getValue()
                                                    	}
                                                    	,success : function (response)
                                                        {
                                                    		var json=Ext.decode(response.responseText);
                                                    		if(json.success==true)
                                                    		{
                                                    			win.destroy();
                                                    			window.destroy();
                                                    			Ext.Msg.show({
                                                                    title:'Detalle actualizado',
                                                                    msg: 'Se finaliz&oacute; el detalle',
                                                                    buttons: Ext.Msg.OK
                                                                });
                                                    		}
                                                    		else
                                                    		{
                                                    			win.setLoading(false);
                                                    			Ext.Msg.show({
                                                                    title:'Error',
                                                                    msg: 'Error al finalizar detalle',
                                                                    buttons: Ext.Msg.OK,
                                                                    icon: Ext.Msg.ERROR
                                                                });
                                                    		}
                                                        }
                                                    	,failure : function()
                                                    	{
                                                    		win.setLoading(false);
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
        		        			        ,{
        		        			        	text     : 'Cancelar'
	        		        			        ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
	        		        			        ,handler : function()
	        		        			        {
	        		        			        	this.up().up().destroy();
	        		        			        }
        		        			        }
        		        			    ]
        		        			}).show();
        		        		}
        		        		else if(cellIndex==5&&$(td).find('img').length>0)
        		        		{
        		        			debug("APRETASTE EL BOTON IMPRIMIR PARA EL RECORD:",record);
        		        		}
                            }
        		        }
        		    })
        		    ,Ext.create('Ext.form.HtmlEditor',
        		    {
                        id        : 'inputReadDetalleHtmlVisor'
                        ,width    : 590
                        ,height   : 200
                        ,readOnly : true
                    })
        		]
        	}).show();
        	window.center();
        	Ext.getCmp('inputReadDetalleHtmlVisor').getToolbar().hide();
        }
        ,onFolderClick : function(grid,rowIndex)
        {
            debug(rowIndex);
            var record=grid.getStore().getAt(rowIndex);
            debug(record);
            Ext.create('Ext.window.Window',
            {
            	title        : 'Documentaci&oacute;n'
            	,modal       : true
            	,buttonAlign : 'center'
            	,width       : 600
            	,height      : 400
            	,autoScroll  : true
            	,loader      :
            	{
            		url       : mesConUrlDocu
            		,params   :
            		{
            			'smap1.nmpoliza'  : record.get('nmpoliza')&&record.get('nmpoliza').length>0?record.get('nmpoliza'):'0'
            			,'smap1.cdunieco' : record.get('cdunieco')
            			,'smap1.cdramo'   : record.get('cdramo')
            			,'smap1.estado'   : record.get('estado')
            			,'smap1.nmsuplem' : '0'
            			,'smap1.ntramite' : record.get('ntramite')
            			,'smap1.nmsolici' : record.get('nmsolici')&&record.get('nmsolici').length>0?record.get('nmsolici'):'0'
            		}
            		,scripts  : true
            		,autoLoad : true
            	}
            }).show();
        }
        ,onComplementariosClick : function(grid,rowIndex)
        {
        	debug(rowIndex);
            var record=grid.getStore().getAt(rowIndex);
            debug(record);
            if(record.get('estado')=='W')
            {
            	if(record.get('nmsolici')&&record.get('nmsolici').length>0&&record.get('nmsolici')>0)
            	{
		            Ext.create('Ext.form.Panel').submit(
		            {
		            	url             : mesConUrlDatCom
		            	,standardSubmit : true
		            	,params         :
		            	{
		            		cdunieco  : record.get('cdunieco')
		            		,cdramo   : record.get('cdramo')
		            		,estado   : record.get('estado')
		            		,nmpoliza : record.get('nmsolici')
		            		,'map1.ntramite' : record.get('ntramite')
		            	}
		            });
            	}
            	else
            	{
            		debug('cotizar');
            		Ext.create('Ext.form.Panel').submit(
                    {
                        url             : mesConUrlCotizar
                        ,standardSubmit : true
                        ,params         :
                        {
                            ntramite : record.get('ntramite')
                        }
                    });
            	}
            }
            else
            {
            	var msg=Ext.Msg.show({
                    title: 'Error',
                    msg: 'Esta p&oacute;liza ya fue emitida',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.WARNING
                });
            	msg.setY(50);
            }
        }
        ,onAddClick : function(button)
        {
        	var grid=button.up().up();
        	debug(grid);
        	Ext.create('Ext.window.Window',
        	{
        		title        : 'Nuevo tr&aacute;mite'
        		,width       : 600
        		,maxHeight   : 400
        		,modal       : true
        		,items       :
        		[
        		    Ext.create('Ext.form.Panel',
        		    {
        		    	id          : 'mesConFormTraManual',
        		    	buttonAlign : 'center'
        		    	,url        : mesConUrlSaveTra
        		    	,border     : 0
        		    	,layout     :
        		    	{
        		    		type     : 'table'
        		    		,columns : 2
        		    	}
        		        ,defaults   :
        		        {
        		        	style : 'margin:5px;'
        		        }
        		    	,items      :
        		    	[
        		    	    Ext.create('Ext.form.field.ComboBox',
                            {
                                fieldLabel : 'Tipo de tr&aacute;mite'
                                ,name       : 'smap1.pv_cdtiptra_i'
                                ,allowBlank : false
                                ,editable   : false
                                ,displayField : 'value'
                                ,valueField   : 'key'
                                ,forceSelection : true
                                ,queryMode      :'local'
                                    ,store : Ext.create('Ext.data.Store', {
                                        model:'Generic',
                                        autoLoad:true,
                                        proxy:
                                        {
                                            type: 'ajax',
                                            url:mesConUrlLoadCatalo,
                                            extraParams:{catalogo:'<s:property value="CON_CAT_MESACONTROL_TIP_TRAMI" />'},
                                            reader:
                                            {
                                                type: 'json',
                                                root: 'lista'
                                            }
                                        }
                                    })
                            })
                            ,Ext.create('Ext.form.field.ComboBox',
        		    	    {
        		    	    	fieldLabel : 'Sucursal Administradora'
        		    	    	,name      : 'smap1.pv_cdsucadm_i'
        		    	    	,allowBlank : false
        		    	    	,editable   : false
        		    	    	,displayField : 'value'
        		    	    	,valueField   : 'key'
        		    	    	,forceSelection : true
        		    	    	,queryMode      :'local'
        		    	    	,store : Ext.create('Ext.data.Store', {
                                    model:'Generic',
                                    autoLoad:true,
                                    proxy:
                                    {
                                        type: 'ajax',
                                        url:mesConUrlLoadCatalo,
                                        extraParams:{catalogo:'<s:property value="CON_CAT_MESACONTROL_SUCUR_ADMIN" />'},
                                        reader:
                                        {
                                            type: 'json',
                                            root: 'lista'
                                        }
                                    }
                                })
        		    	    })
        		    	    ,Ext.create('Ext.form.field.ComboBox',
                            {
                                fieldLabel : 'Sucursal Documento'
                                ,name       : 'smap1.pv_cdsucdoc_i'
                                ,allowBlank : false
                                ,editable   : false
                                ,displayField : 'value'
                                ,valueField   : 'key'
                                ,forceSelection : true
                                ,queryMode      :'local'
                                    ,store : Ext.create('Ext.data.Store', {
                                        model:'Generic',
                                        autoLoad:true,
                                        proxy:
                                        {
                                            type: 'ajax',
                                            url:mesConUrlLoadCatalo,
                                            extraParams:{catalogo:'<s:property value="CON_CAT_MESACONTROL_SUCUR_DOCU" />'},
                                            reader:
                                            {
                                                type: 'json',
                                                root: 'lista'
                                            }
                                        }
                                    })
                            })
                            /*
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'Subramo'
                                ,name       : 'smap1.'
                            }
        		    	    */
                            ,{
        		    	    	xtype       : 'textfield'
        		    	    	,fieldLabel : 'P&oacute;liza'
        		    	    	,name       : 'smap1.pv_nmpoliza_i'
        		    	    	,allowBlank : false
        		    	    }
        		    	    ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'Cotizaci&oacute;n'
                                ,name       : 'smap1.pv_nmsolici_i'
                                ,allowBlank : false
                            }
        		    	    ,{
        		    	    	xtype       : 'textfield'
        		    	    	,fieldLabel : 'Agente'
        		    	    	,name       : 'smap1.pv_cdagente_i'
        		    	    	,allowBlank : false
        		    	    }
        		    	    /*
        		    	    ,Ext.create('Ext.form.field.ComboBox',
                            {
                                fieldLabel : 'Forma de recepci&oacute;n'
                            })
                            */
                            ,{
        		    	    	xtype       : 'textfield'
                                ,fieldLabel : 'Referencia'
                                ,name       : 'smap1.pv_referencia_i'
                                ,allowBlank : false
                            }
        		    	    ,{
        		    	    	xtype       : 'textfield'
                                ,fieldLabel : 'Prospecto'
                                ,name       : 'smap1.pv_nombre_i'
                                ,allowBlank : false
        		    	    }
        		    	    ,Ext.create('Ext.form.field.ComboBox',
                            {
                                fieldLabel : 'Estatus'
                                ,name      : 'smap1.pv_status_i'
                                ,allowBlank : false
                                ,editable   : false
                                ,displayField : 'value'
                                ,valueField   : 'key'
                                ,forceSelection : true
                                ,queryMode      :'local'
                                    ,store : Ext.create('Ext.data.Store', {
                                        model:'Generic',
                                        autoLoad:true,
                                        proxy:
                                        {
                                            type: 'ajax',
                                            url:mesConUrlLoadCatalo,
                                            extraParams:{catalogo:'<s:property value="CON_CAT_MESACONTROL_ESTAT_TRAMI" />'},
                                            reader:
                                            {
                                                type: 'json',
                                                root: 'lista'
                                            }
                                        }
                                    })
                            })
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'Observaciones'
                                ,name       : 'smap1.pv_comments_i'
                                ,allowBlank : false
                            }
        		    	]
        		    	,buttons :
        		    	[
        		    	    {
        		    	    	text     : 'Guardar'
        		    	    	,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
        		    	    	,handler : function()
        		    	    	{
        		    	    		var form=Ext.getCmp('mesConFormTraManual');
        		    	    		debug(form);
        		    	    		if(form.isValid())
        		    	    		{
	                                    form.setLoading(true);
	                                    form.submit(
	                                    {
	                                        params :
	                                        {
	                                            'smap1.pv_cdunieco_i' : '1',
	                                            'smap1.pv_cdramo_i'   : '2',
	                                            'smap1.pv_estado_i'   : 'W',
	                                            'smap1.pv_nmsuplem_i' : '0'
	                                        },
	                                        success:function(form2, action){
	                                            form.setLoading(false);
	                                            Ext.Msg.show({
	                                                title:'Cambios guardados',
	                                                msg: 'Se agreg&oacute; un nuevo tr&aacute;mite</br> N&uacute;mero: '+ action.result.msgResult,
	                                                buttons: Ext.Msg.OK,
	                                                fn: function(){
	                                                	Ext.create('Ext.form.Panel').submit({standardSubmit:true});
	                                                }
	                                            });
	                                            
	                                        },
	                                        failure:function(){
	                                            form.setLoading(false);
	                                            Ext.Msg.show({
	                                                title:'Error',
	                                                msg: 'Error de comunicaci&oacute;n',
	                                                buttons: Ext.Msg.OK,
	                                                icon: Ext.Msg.ERROR
	                                            });
	                                        }
	                                    });
        		    	    		}
        		    	    		else
        		    	    		{
        		    	    			Ext.Msg.show({
                                            title:'Error',
                                            msg: 'Favor de introducir los campos requeridos',
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.Msg.WARNING
                                        });
        		    	    		}
        		    	    	}
        		    	    }
        		    	]
        		    })
        		]
        	}).show();
        }
    });
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    mesConGridTareas = new GridTareas();
    mesConGridTareas.render('mesconprimain');
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////
    
    //////////////////////
    ////// cargador //////
    /*//////////////////*
    Ext.define('LoaderForm',
    {
        extend:'Modelo1',
        proxy:
        {
            extraParams:{
            },
            type:'ajax',
            url : urlCargar,
            reader:{
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
<div id="mesconprimain" style="height:600px;"></div>
</body>
</html>