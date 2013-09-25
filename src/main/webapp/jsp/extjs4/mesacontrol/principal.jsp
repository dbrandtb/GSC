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
    var mesConUrlLoadTareas = '<s:url namespace="/mesacontrol" action="loadTareas" />';
    var mesConUrlDocu       = '<s:url namespace="/documentos"  action="ventanaDocumentosPoliza" />';
    var mesConUrlDatCom     = '<s:url namespace="/"            action="datosComplementarios" />';
    var mesConUrlInitManual = '<s:url namespace="/mesacontrol" action="obtenerValoresDefectoInsercionManual" />';
    var mesConStoreTareas;
    var mesConGridTareas;
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
        sorters:[{sorterFn:function(o1,o2){return o1.get('ntramite')<o2.get('ntramite')}}],
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
        ,height        : 400
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
                        ,width     : 70 
                    }
                    ,
                    {
                    	header     : 'Sucursal'
                    	,dataIndex : "cdsucdoc"
                    	,width     : 70
                    }
                    ,{
                    	header     : 'Subramo'
                    	,dataIndex : "cdsubram"
                    	,width     : 70
                    }
                    ,{
                        header     : 'Estado<br/>de p&oacute;liza'
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
                    	,width     : 95
                    	,renderer  : Ext.util.Format.dateRenderer('d M Y')
                    }
                    ,{
                        header     : 'Fecha<br/>de estatus'
                        ,dataIndex : 'fecstatu'
                        ,width     : 95
                        ,renderer  : Ext.util.Format.dateRenderer('d M Y')
                    }
                    ,{
                        header     : 'P&oacute;liza<br/>/Tr&aacute;mite'
                        ,dataIndex : 'nmpoliza'
                        ,width     : 80
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
                    			res='Pendiente';
                    		}
                    		else if(value=='3')
                    		{
                    			res='Confirmado';
                    		}
                    		return res;
                    	}
                    }
                    /*
                    ,{
                        header     : 'Observaciones'
                        ,dataIndex : 'comments'
                        ,width     : 130
                    }
                    */
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
                                ,tooltip : 'Datos complementarios'
                                ,handler : this.onComplementariosClick
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
            	,loader      :
            	{
            		url       : mesConUrlDocu
            		,params   :
            		{
            			'smap1.nmpoliza'  : record.get('nmsolici')
            			,'smap1.cdunieco' : record.get('cdunieco')
            			,'smap1.cdramo'   : record.get('cdramo')
            			,'smap1.estado'   : record.get('estado')
            			,'smap1.nmsuplem' : '0'
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
	            Ext.create('Ext.form.Panel').submit(
	            {
	            	url             : mesConUrlDatCom
	            	,standardSubmit : true
	            	,params         :
	            	{
	            		cdunieco  : record.get('cdunieco')
	            		,cdramo   : record.get('cdramo')
	            		,estado   : record.get('estado')
	            		,nmpoliza : record.get('nmpoliza')
	            	}
	            });
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
        	Ext.Ajax.request(
        	{
        		url      : mesConUrlInitManual
        		,success : function(response)
        		{
        			var json=Ext.decode(response.responseText);
        			if(json.success==true)
        			{
        				
        			}
        			else
        			{
        				Ext.Msg.show({
        	                title: 'Error',
        	                msg: 'Error al cargar',
        	                buttons: Ext.Msg.OK,
        	                icon: Ext.Msg.ERROR
        	            });
        			}
        		}
        	    ,failure : function()
        	    {
        	    	Ext.Msg.show({
                        title: 'Error',
                        msg: 'Error de comunicaci&oacute;n',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
        	    }
        	});
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