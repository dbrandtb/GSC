<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
--%>
<style>
.x-action-col-icon {
    height: 16px;
    width: 16px;
    margin-right: 8px;
}
</style>
<script>
	var inputCduniecop2= '<s:property value="map1.cdunieco" />';
	var inputCdramop2=   '<s:property value="map1.cdramo" />';
	var inputEstadop2=   '<s:property value="map1.estado" />';
	var inputNmpolizap2= '<s:property value="map1.nmpoliza" />';
	var _URL_OBTEN_CATALOGO_GENERICOp2='<s:url action="jsonObtenCatalogoGenerico" namespace="/" />';
	var CDATRIBU_ROLp2='<s:property value="cdatribuRol" />';
	var gridPersonasp2;
	var CDATRIBU_SEXOp2='<s:property value="cdatribuSexo" />';
	var storeRolesp2;
	var storeGenerosp2;
	var storePersonasp2;
	var storeTomadorp2;
	var gridPersonasp2;
	var editorRolesp2;
	var editorGenerosp2;
	var urlCargarAseguradosp2='<s:url namespace="/" action="cargarComplementariosAsegurados" />';
	var urlCargarCatalogosp2='<s:url namespace="/flujocotizacion" action="cargarCatalogos" />';
	var urlDatosComplementariosp2='<s:url namespace="/" action="datosComplementarios.action" />';
	var urlGuardarAseguradosp2='<s:url namespace="/" action="guardarComplementariosAsegurados" />';
	var urlCoberturasAseguradop2='<s:url namespace="/" action="editarCoberturas" />';
	var urlGenerarCdPersonp2='<s:url namespace="/" action="generarCdperson" />';
	var urlDomiciliop2      ='<s:url namespace="/" action="pantallaDomicilio" />';
	var urlExclusionp2      ='<s:url namespace="/" action="pantallaExclusion" />';
	var editorFechap2;
	var contextop2='${ctx}';
	var gridTomadorp2;
	var recordTomadorp2;
	var timeoutflagp2;
	
    function rendererRolp2(v)
    {
    	var leyenda='no';
        if(typeof v == 'string')
	    		   //tengo solo el indice
        {
			//window.console&&console.log('string:');
			storeRolesp2.each(function(rec){
				//window.console&&console.log('iterando...',rec.data);
				if(rec.data.key==v)
			    {
					leyenda=rec.data.value;	
			    }
			});
			//window.console&&console.log(leyenda);
        }
		else
		//tengo objeto que puede venir como Generic u otro mas complejo
		{
			//window.console&&console.log('object:');
		    if(v.key&&v.value)
		    //objeto Generic
		    {
		        leyenda=v.value;
		    }
		    else
		    {
		        leyenda=v.data.value;
		    }
		    //window.console&&console.log(leyenda);
		}
        //console.log('return',leyenda);
		return leyenda;
	}
    
    function rendererSexop2(v)
    {
        var leyenda='no';
        if(typeof v == 'string')
                   //tengo solo el indice
        {
        	//window.console&&console.log('string:');
            storeGenerosp2.each(function(rec){
            	//window.console&&console.log('iterando...',rec.data);
                if(rec.data.key==v)
                {
                    leyenda=rec.data.value; 
                }
            });
            //window.console&&console.log(leyenda);
        }
        else
        //tengo objeto que puede venir como Generic u otro mas complejo
        {
        	//window.console&&console.log('object:');
            if(v.key&&v.value)
            //objeto Generic
            {
                leyenda=v.value;
            }
            else
            {
                leyenda=v.data.value;
            }
            //window.console&&console.log(leyenda);
        }
        //console.log('return',leyenda);
        return leyenda;
    }
    
    function editarDespuesValidacionesp2(incisosJson,banderaCoberODomici)
    {
    	var formPanel=Ext.getCmp('form1p2');
        var submitValues=formPanel.getForm().getValues();
        //console.log(submitValues);
        //console.log("###############################");
        submitValues['list1']=incisosJson;
        var map1={
        'pv_cdunieco':inputCdunieco,
        'pv_cdramo':inputCdramo,
        'pv_estado':inputEstado,
        'pv_nmpoliza':inputNmpoliza};
        submitValues['map1']=map1;
        //window.console&&console.log(submitValues);
        //Submit the Ajax request and handle the response
        formPanel.setLoading(true);
        /*Ext.MessageBox.show({
            msg: 'Cotizando...',
            width:300,
            wait:true,
            waitConfig:{interval:100}
        });*/
        Ext.Ajax.request(
        {
            url: urlGuardarAseguradosp2,
            jsonData:Ext.encode(submitValues),
            success:function(response,opts)
            {
                //Ext.MessageBox.hide();
                formPanel.setLoading(false);
                var jsonResp = Ext.decode(response.responseText);
                //window.console&&console.log(jsonResp);
                if(jsonResp.success==true)
                {
                	if(banderaCoberODomici&&banderaCoberODomici==true)
                	{
                		timeoutflagp2=3;
                	}
                	else
                	{
	                	Ext.Msg.show({
	                        title:'Asegurados guardados',
	                        msg: 'Se ha guardado la informaci&oacute;n',
	                        buttons: Ext.Msg.OK
	                    });
	                	expande(1);
                	}
                }
                else
               	{
                	Ext.Msg.show({
                        title:'Error',
                        msg: 'No se pudo guardar',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
                	timeoutflagp2=2;
               	}
            },
            failure:function(response,opts)
            {
                //Ext.MessageBox.hide();
                formPanel.setLoading(false);
                //window.console&&console.log("error");
                Ext.Msg.show({
                    title:'Error',
                    msg: 'Error de comunicaci&oacute;n',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.ERROR
                });
                timeoutflagp2=2;
            }
        });
    }
    
    function validarYGuardar()
    {
    	debug("validarYGuardar flag:1");
    	timeoutflagp2=1;
    	if(Ext.getCmp('form1p2').getForm().isValid())
        {
            var incisosRecords = storePersonasp2.getRange();
            if(incisosRecords&&incisosRecords.length>0)
            {
                var incisosJson = [];
                var completos=true;
                var sinCdperson=0;
                
                storePersonasp2.each(function(record,index)
                {
                    //console.log(record);
                    if(
                        !record.get("nombre")
                        ||record.get("nombre").length==0
                        ||!record.get("Apellido_Paterno")
                        ||record.get("Apellido_Paterno").length==0
                        ||!record.get("Apellido_Paterno")
                        ||record.get("Apellido_Paterno").length==0
                        ||!record.get("Apellido_Materno")
                        ||record.get("Apellido_Materno").length==0
                        ||!record.get("cdrfc")
                        ||record.get("cdrfc").length==0
                        )
                    {
                        //console.log("#incompleto:");
                        //console.log(record);
                        completos=false;                                    
                    }
                    if(!record.get("cdperson")||record.get("cdperson").length==0)
                    {
                        sinCdperson++;
                    }
                    if(record.get('Parentesco')=='T')
                    {
                        incisosJson.push({
                            nmsituac:'0',
                            cdrol:'1',
                            fenacimi: typeof record.get('fenacimi')=='string'?record.get('fenacimi'):Ext.Date.format(record.get('fenacimi'), 'd/m/Y'),
                            sexo:typeof record.get('sexo')=='string'?record.get('sexo'):record.get('sexo').get('key'),
                            cdperson:record.get('cdperson'),
                            nombre: record.get('nombre'),
                            segundo_nombre: record.get('segundo_nombre'),
                            Apellido_Paterno: record.get('Apellido_Paterno'),
                            Apellido_Materno: record.get('Apellido_Materno'),
                            cdrfc:record.get('cdrfc')
                        });
                    }
                    incisosJson.push({
                        nmsituac:record.get('nmsituac'),
                        cdrol:typeof record.get('cdrol')=='string'?record.get('cdrol'):record.get('cdrol').get('key'),
                        fenacimi: typeof record.get('fenacimi')=='string'?record.get('fenacimi'):Ext.Date.format(record.get('fenacimi'), 'd/m/Y'),
                        sexo:typeof record.get('sexo')=='string'?record.get('sexo'):record.get('sexo').get('key'),
                        cdperson:record.get('cdperson'),
                        nombre: record.get('nombre'),
                        segundo_nombre: record.get('segundo_nombre'),
                        Apellido_Paterno: record.get('Apellido_Paterno'),
                        Apellido_Materno: record.get('Apellido_Materno'),
                        cdrfc:record.get('cdrfc')
                    });
                });
                
               //console.log("sin cd person: "+sinCdperson);
                if(completos)
                {
                    if(sinCdperson==0)
                    {
                    	debug("validarYGuardar ->editar");
                        editarDespuesValidacionesp2(incisosJson,true);//manda el submit
                    }
                    else
                    {
                        Ext.getCmp('form1p2').setLoading(true);
                        //mandar a traer los cdperson de las personas asincrono
                        storePersonasp2.each(function(record,index)
                        {
                            //console.log(index);
                            setTimeout(function()
                            {
                            	debug("validarYGuardar -> gecdperson");
                                //console.log("trigger");
                                Ext.Ajax.request(
                                {
                                    url: urlGenerarCdPersonp2,
                                    success:function(response,opts)
                                    {
                                        var jsonResp = Ext.decode(response.responseText);
                                        //console.log("respuesta cdperson",jsonResp);
                                        //window.console&&console.log(jsonResp);
                                        if(jsonResp.success==true)
                                        {
                                            try
                                            {
                                                record.data.cdperson=jsonResp.cdperson;
                                                sinCdperson--;
                                                if(sinCdperson==0)
                                                {
                                                    //procesar submit
                                                    //console.log(storePersonasp2.getRange());
                                                    //storePersonasp2.sync();
                                                    //console.log(storePersonasp2.getRange());
                                                    gridPersonasp2.getView().refresh();
                                                    incisosJson=[];
                                                    storePersonasp2.each(function(record,index)
                                                    {
                                                        if(record.get('Parentesco')=='T')
                                                        {
                                                            incisosJson.push({
                                                                nmsituac:'0',
                                                                cdrol:'1',
                                                                fenacimi: typeof record.get('fenacimi')=='string'?record.get('fenacimi'):Ext.Date.format(record.get('fenacimi'), 'd/m/Y'),
                                                                sexo:typeof record.get('sexo')=='string'?record.get('sexo'):record.get('sexo').get('key'),
                                                                cdperson:record.get('cdperson'),
                                                                nombre: record.get('nombre'),
                                                                segundo_nombre: record.get('segundo_nombre'),
                                                                Apellido_Paterno: record.get('Apellido_Paterno'),
                                                                Apellido_Materno: record.get('Apellido_Materno'),
                                                                cdrfc:record.get('cdrfc')
                                                            });
                                                        }
                                                        incisosJson.push({
                                                            nmsituac:record.get('nmsituac'),
                                                            cdrol:typeof record.get('cdrol')=='string'?record.get('cdrol'):record.get('cdrol').get('key'),
                                                            fenacimi: typeof record.get('fenacimi')=='string'?record.get('fenacimi'):Ext.Date.format(record.get('fenacimi'), 'd/m/Y'),
                                                            sexo:typeof record.get('sexo')=='string'?record.get('sexo'):record.get('sexo').get('key'),
                                                            cdperson:record.get('cdperson'),
                                                            nombre: record.get('nombre'),
                                                            segundo_nombre: record.get('segundo_nombre'),
                                                            Apellido_Paterno: record.get('Apellido_Paterno'),
                                                            Apellido_Materno: record.get('Apellido_Materno'),
                                                            cdrfc:record.get('cdrfc')
                                                        });
                                                    });                
                                                    Ext.getCmp('form1p2').setLoading(false);
                                                    debug("validarYGuardar -> editar");
                                                    editarDespuesValidacionesp2(incisosJson,true);
                                                }
                                            }
                                            catch(e)
                                            {
                                                //console.log(e);
                                                Ext.Msg.show({
                                                    title:'Error',
                                                    msg: 'Error al procesar la informaci&oacute;n',
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.Msg.ERROR
                                                });
                                                debug("validarYGuardar flag:2");
                                                timeoutflagp2=2;
                                            }
                                        }
                                        else
                                        {
                                            Ext.Msg.show({
                                                title:'Error',
                                                msg: 'Error al obtener la informaci&oacute;n',
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.Msg.ERROR
                                            });
                                            debug("validarYGuardar flag:2");
                                            timeoutflagp2=2;
                                        }
                                    },
                                    failure:function(response,opts)
                                    {
                                        Ext.Msg.show({
                                            title:'Error',
                                            msg: 'Error de comunicaci&oacute;n',
                                            buttons: Ext.Msg.OK,
                                            icon: Ext.Msg.ERROR
                                        });
                                        debug("validarYGuardar flag:2");
                                        timeoutflagp2=2;
                                    }
                                })
                            },(index+1)*500);
                        });
                    }
                }
                else
                {
                    Ext.Msg.show({
                        title:'Datos incompletos',
                        msg: 'El nombre, apellidos y RFC son requeridos',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.WARNING
                    });
                    debug("validarYGuardar flag:2");
                    timeoutflagp2=2;
                }
            }
            else
            {
                Ext.Msg.show({
                    title:'Datos incompletos',
                    msg: 'Favor de introducir al menos un asegurado',
                    buttons: Ext.Msg.OK,
                    icon: Ext.Msg.WARNING
                });
                debug("validarYGuardar flag:2");
                timeoutflagp2=2;
            }
        }
    	else
    	{
    		debug("validarYGuardar flag:2");
    		timeoutflagp2=2;
    	}
    }
	
    Ext.onReady(function(){
		
		Ext.define('Modelo1p2',{
			extend:'Ext.data.Model',
			<s:property value="item1" />
		});
		
		storeRolesp2 = new Ext.data.Store({
	        model: 'Generic',
	        autoLoad:true,
	        proxy:
	        {
	            type: 'ajax',
	            url : urlCargarCatalogosp2,
	            extraParams:{catalogo:'<s:property value="CON_CAT_POL_ROL" />'},
	            reader:
	            {
	                type: 'json',
	                root: 'lista'
	            }
	        }
	    })
	    
	    storeGenerosp2 = new Ext.data.Store({
	        model: 'Generic',
	        autoLoad:true,
	        proxy:
	        {
	            type: 'ajax',
	            url : _URL_OBTEN_CATALOGO_GENERICOp2,
	            extraParams:{cdatribu:CDATRIBU_SEXOp2},
	            reader:
	            {
	                type: 'json',
	                root: 'lista'
	            }
	        }
	    });
	    
	    storePersonasp2 =new Ext.data.Store(
   	    {
   	        // destroy the store if the grid is destroyed
   	        //autoDestroy: true,
   	        model: 'Modelo1p2',
   	        autoLoad:true,
   	        proxy:
   	        {
   	        	url:urlCargarAseguradosp2,
   	        	type:'ajax',
   	        	extraParams:
   	        	{
   	        		'map1.pv_cdunieco':inputCduniecop2,
   	        		'map1.pv_cdramo':inputCdramop2,
   	        		'map1.pv_estado':inputEstadop2,
   	        		'map1.pv_nmpoliza':inputNmpolizap2
   	        	},
		   	    reader:
		        {
		            type: 'json',
		            root: 'list1'
		        }
   	        }
   	        ,listeners:
   	        {
   	        	load:function( store, records, successful, eOpts )
	   	        {
	   	        	debug('listener load');
	   	        	var indexTomador;
   	        		store.each(function(record,index)
       			    {
   	        			debug('iterando',record);
   	        			if('1'==(typeof record.get('cdrol')=='string'?record.get('cdrol'):record.get('cdrol').get('key')))
        				{
        				    debug('es tomador en indice '+index);
        				    indexTomador=index;
        				}
   	        			else
        				{
        				    debug('no es tomador');
        				}
       			    });
   	        		var record=store.getAt(indexTomador);
   	        		storeTomadorp2.add(record);
                    storePersonasp2.remove(record);
   	        		debug('checar cual asegurado es el tomador');
   	        		if(!record.get('cdperson')||record.get('cdperson').length==0)
   	        		{
   	        			debug('buscar por parentesco T abajo');
	   	        	    store.each(function(record,index)
		                {
		                    debug('iterando',record);
		                    if(record.get('Parentesco')=='T')
		                    {
		                        debug('es el tomador',record);
		                        record.set('estomador','Si');
		                        recordTomadorp2=record.copy();
		                        debug('se puso en sesion recordTomadorp2',recordTomadorp2);
		                        //gridTomadorp2.setDisabled(true);
		                    }
		                    else
		                    {
		                        debug('no es el tomador');
		                    }
		                });
   	        		}
   	        		else
   	        		{
   	        			var cdpersonTomador=record.get('cdperson');
   	        			debug('buscar por cdperson abajo');
   	        			store.each(function(record,index)
                        {
                            debug('iterando',record);
                            if(record.get('cdperson')==cdpersonTomador)
                            {
                                debug('es el tomador',record);
                                record.set('estomador','Si');
                                recordTomadorp2=record.copy();
                                debug('se puso en sesion recordTomadorp2',recordTomadorp2);
                                //gridTomadorp2.setDisabled(true);
                            }
                            else
                            {
                                debug('no es el tomador');
                            }
                        });
   	        		}
   	        		debug('fin de checar cual asegurado es el tomador');
	   	        }
   	        }   	        
   	    });
	    
	    storeTomadorp2 =new Ext.data.Store(
        {
            model     : 'Modelo1p2'
        });
	    
	    editorRolesp2=Ext.create('Ext.form.ComboBox',
   	    {
   	        store: storeRolesp2,
   	        queryMode:'local',
   	        displayField: 'value',
   	        valueField: 'key',
   	        allowBlank:false,
   	        editable:false
   	    });
	    
	    editorGenerosp2=Ext.create('Ext.form.ComboBox',
   	    {
   	        store: storeGenerosp2,
   	        queryMode:'local',
   	        displayField: 'value',
   	        valueField: 'key',
   	        allowBlank:false,
   	        editable:false
   	    });
	    
	    editorFechap2=Ext.create('Ext.form.field.Date',
        {
	    	format:'d/m/Y',
            allowBlank:false
        });
	    
	    Ext.define('GridTomadorP2',
   	    {
	    	extend         : 'Ext.grid.Panel'
	    	,title         : 'Contratante'
	    	,store         : storeTomadorp2
	        ,<s:property value="item3" />
	        ,frame         : false
	        ,style         : 'margin:5px'
        	,selModel      :
        	{
                selType: 'cellmodel'
            }
        	,requires      :
       		[
                'Ext.selection.CellModel',
                'Ext.grid.*',
                'Ext.data.*',
                'Ext.util.*',
                'Ext.form.*'
            ]
	        ,xtype         : 'cell-editing'
	        ,initComponent : function()
	        {
	        	debug('initComponent');
	        	this.cellEditing = new Ext.grid.plugin.CellEditing({
                    clicksToEdit: 1
                });
	        	Ext.apply(this,
       			{
	        	    //plugins : [this.cellEditing]
	        	    listeners:
                    {
                        // add the validation after render so that validation is not triggered when the record is loaded.
                        afterrender: function (grid)
                        {
                            var view = grid.getView();
                         // validation on record level through "itemupdate" event
                            view.on('itemupdate', function (record, y, node, options) {
                                this.validateRow(this.getColumnIndexes(), record, y, true);
                            }, grid);
                        }
                    }
       			});
	        	this.callParent();
	        }
        	,getColumnIndexes: function () {
                var me, columnIndexes;
                me = this;
                columnIndexes = [];
                Ext.Array.each(me.columns, function (column)
                {
                    // only validate column with editor
                    if (column.getEditor&&Ext.isDefined(column.getEditor())&&column.getEditor().allowBlank==false) {
                        columnIndexes.push(column.dataIndex);
                    } else {
                        columnIndexes.push(undefined);
                    }
                });
                //console.log(columnIndexes);
                return columnIndexes;
            }
            ,validateRow: function (columnIndexes,record, y)
            //hace que una celda de columna con allowblank=false tenga el estilo rojito
            {
                var view = this.getView();
                Ext.each(columnIndexes, function (columnIndex, x)
                {
                    if(columnIndex)
                    {
                        var cell=view.getCellByPosition({row: y, column: x});
                        cellValue=record.get(columnIndex);
                        if((cell.addCls)&&((!cellValue)||(cellValue.lenght==0)))
                        {
                            cell.addCls("custom-x-form-invalid-field");
                        }
                    }
                });
                return false;
            }
   	    });
	    
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ////// Inicio de declaracion de grid                                                                             //////
	    ////// http://docs.sencha.com/extjs/4.2.1/extjs-build/examples/build/KitchenSink/ext-theme-neptune/#cell-editing //////
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    Ext.define('EditorIncisosp2', {
	        extend: 'Ext.grid.Panel',

	        requires: [
	            'Ext.selection.CellModel',
	            'Ext.grid.*',
	            'Ext.data.*',
	            'Ext.util.*',
	            'Ext.form.*'
	        ],
	        xtype: 'cell-editing',

	        title: 'Asegurados',
	        frame: false,
	        //collapsible:true,
	        //titleCollapse:true,
	        style:'margin:5px;',
            //title:'Asegurados',

	        initComponent: function() {
	            this.cellEditing = new Ext.grid.plugin.CellEditing({
	                clicksToEdit: 1
	            });

	            Ext.apply(this, {
	                //width: 750,
	                height: 200,
	                plugins: [this.cellEditing],
	                store: storePersonasp2,
	                <s:property value="item2" />,
	                selModel: {
	                    selType: 'cellmodel'
	                },
	                /*tbar: [{
	                    icon:'resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
	                    text: 'Agregar',
	                    scope: this,
	                    handler: this.onAddClick
	                }],*/
	                /*http://www.sencha.com/forum/showthread.php?141626-Grid-Validation-with-Error-Indication-%28suggestions-needed%29*/
	                //valida las celdas y les pone el estilo rojito
	                listeners:
	                {
	                    // add the validation after render so that validation is not triggered when the record is loaded.
	                    afterrender: function (grid)
	                    {
	                        var view = grid.getView();
	                     // validation on record level through "itemupdate" event
	                        view.on('itemupdate', function (record, y, node, options) {
	                        	if(record.get('estomador'))
	                            {
	                                debug('tomador update:',record);
	                                recordTomadorp2=record.copy();
	                                debug('se puso en sesion recordTomadorp2',recordTomadorp2);
	                                storeTomadorp2.removeAll();
	                                storeTomadorp2.add(recordTomadorp2);
	                            }
                        	    this.validateRow(this.getColumnIndexes(), record, y, true);
	                        }, grid);
	                        
	                    },
	                    beforeedit: function (grid, e, eOpts)
	                    {
	                    	//console.log("beforeedit");
	                    	//console.log("e.column.xtype",e.column.xtype);
	                        return e.column.xtype !== 'actioncolumn';//para que no edite sobre actioncolumn
	                    }
	                }/*http://www.sencha.com/forum/showthread.php?141626-Grid-Validation-with-Error-Indication-%28suggestions-needed%29*/

	            });

	            this.callParent();

	            /*this.on('afterlayout', this.loadStore, this, {
	                delay: 1,
	                single: true
	            })*/
	        },

	        /*http://www.sencha.com/forum/showthread.php?141626-Grid-Validation-with-Error-Indication-%28suggestions-needed%29*/
	        //regresa las columnas con editor que tengan allowBlank=false (requeridas)
	        getColumnIndexes: function () {
	            var me, columnIndexes;
	            me = this;
	            columnIndexes = [];
	            Ext.Array.each(me.columns, function (column)
	            {
	                // only validate column with editor
	                if (column.getEditor&&Ext.isDefined(column.getEditor())&&column.getEditor().allowBlank==false) {
	                    columnIndexes.push(column.dataIndex);
	                } else {
	                    columnIndexes.push(undefined);
	                }
	            });
	            //console.log(columnIndexes);
	            return columnIndexes;
	        },
	        validateRow: function (columnIndexes,record, y)
	        //hace que una celda de columna con allowblank=false tenga el estilo rojito
	        {
	            var view = this.getView();
	            Ext.each(columnIndexes, function (columnIndex, x)
	            {
	                if(columnIndex)
	                {
	                    var cell=view.getCellByPosition({row: y, column: x});
	                    cellValue=record.get(columnIndex);
	                    if((cell.addCls)&&((!cellValue)||(cellValue.lenght==0)))
	                    {
	                        cell.addCls("custom-x-form-invalid-field");
	                    }
	                }
	            });
	            return false;
	        }/*http://www.sencha.com/forum/showthread.php?141626-Grid-Validation-with-Error-Indication-%28suggestions-needed%29*/,

	        /*onAddClick: function(){
	            //window.parent.scrollTo(0,600);
	            // Create a model instance
	            var rec = new Modelo1({
	            	nmsituac:'',
	            	cdrol: new Generic({key:storeRoles.getAt(0).data.key,value:storeRoles.getAt(0).data.value}),
	            	fenacimi: new Date(),
	                sexo: new Generic({key:storeGeneros.getAt(0).data.key,value:storeGeneros.getAt(0).data.value}),
	                cdperson:'',
	                nombre: '',
	                segundo_nombre: '',
	                Apellido_Paterno: '',
	                Apellido_Materno: '',
	                cdrfc:''
	            });

	            this.getStore().insert(0, rec);
	            
	            this.validateRow(this.getColumnIndexes(), this.getStore().getAt(0), 0, true);
	            
	            //para acomodarse en la primer celda para editar
	            this.cellEditing.startEditByPosition({
	                row: 0, 
	                column: 0
	            });
	        },*/
	        
	        onEditarInter:function(grid,rowIndex)
            {
                var me=this;
                debug("interval called");
                if(timeoutflagp2==1)
                {
                    debug("interval: 1");
                    setTimeout(function(){me.onEditarInter(grid,rowIndex)},500);
                }
                else if(timeoutflagp2==3)
                {
                    debug("interval: 3 proceder");
                    me.onEditarSave(grid,rowIndex);
                }
                else
                {
                    debug("finish: "+timeoutflagp2)
                }
            },
	        
	        onEditarClick:function(grid,rowIndex)
	        {
	        	var me=this;
                debug("domicilios.click");
                debug("validarYGuardar");
                validarYGuardar();
                setTimeout(function(){me.onEditarInter(grid,rowIndex)},500);
	        },
	        
	        onEditarSave:function(grid,rowIndex)
	        {
	        	var record=this.getStore().getAt(rowIndex);
	        	if(Ext.getCmp('coberturasAccordionEl'))
	        	{
	        		Ext.getCmp('coberturasAccordionEl').destroy();
	        	}
	            accordion.add(
       			{
       				id:'coberturasAccordionEl'
       				,title:'Editar coberturas de '+record.get('nombre')+' '+(record.get('segundo_nombre')?record.get('segundo_nombre')+' ':' ')+record.get('Apellido_Paterno')+' '+record.get('Apellido_Materno')
       				,cls:'claseTitulo'
       				,loader:
       				{
	                    url : urlCoberturasAseguradop2
	                    ,params:{
	                    	'smap1.pv_cdunieco' : inputCduniecop2,
	                        'smap1.pv_cdramo'   : inputCdramop2,
	                        'smap1.pv_estado'   : inputEstadop2,
	                        'smap1.pv_nmpoliza' : inputNmpolizap2,
	                        'smap1.pv_nmsituac' : record.get('nmsituac'),
	                        'smap1.pv_cdperson' : record.get('cdperson')
	                    }
       					,autoLoad:true
       					,scripts:true
       				}
	       			,listeners:
	                {
	                    expand:function( p, eOpts )
	                    {
	                        window.parent.scrollTo(0,150+p.y);
	                    }
	                }
       			}).expand();
	        	/*
	        	Ext.create('Ext.form.Panel').submit({
                    standardSubmit:true,
                });
	        	*/
	        },
	        
	        onDomiciliosSave:function(grid,rowIndex)
	        {
	        	var record=this.getStore().getAt(rowIndex);
                if(Ext.getCmp('domicilioAccordionEl'))
                {
                    Ext.getCmp('domicilioAccordionEl').destroy();
                }
                accordion.add(
                {
                    id:'domicilioAccordionEl'
                    ,title:'Editar domicilio de '+record.get('nombre')+' '+(record.get('segundo_nombre')?record.get('segundo_nombre')+' ':' ')+record.get('Apellido_Paterno')+' '+record.get('Apellido_Materno')
                    ,cls:'claseTitulo'
                    ,loader:
                    {
                        url : urlDomiciliop2
                        ,params:
                        {
                            'smap1.pv_cdunieco'     : inputCduniecop2,
                            'smap1.pv_cdramo'       : inputCdramop2,
                            'smap1.pv_estado'       : inputEstadop2,
                            'smap1.pv_nmpoliza'     : inputNmpolizap2,
                            'smap1.pv_nmsituac'     : record.get('nmsituac'),
                            'smap1.pv_cdperson'     : record.get('cdperson'),
                            'smap1.pv_cdrol'        : record.get('cdrol'),
                            'smap1.nombreAsegurado' : record.get('nombre')+' '+(record.get('segundo_nombre')?record.get('segundo_nombre')+' ':' ')+record.get('Apellido_Paterno')+' '+record.get('Apellido_Materno'),
                            'smap1.cdrfc'           : record.get('cdrfc'),
                            'smap1.botonCopiar'     : record.get('estomador')!='Si'?1:0
                        }
                        ,autoLoad:true
                        ,scripts:true
                    }
                    ,listeners:
                    {
                        expand:function( p, eOpts )
                        {
                            window.parent.scrollTo(0,150+p.y);
                        }
                    }
                }).expand();
	        },
	        
	        onExclusionSave:function(grid,rowIndex)
            {
                var record=this.getStore().getAt(rowIndex);
                if(Ext.getCmp('exclusionAccordionEl'))
                {
                    Ext.getCmp('exclusionAccordionEl').destroy();
                }
                accordion.add(
                {
                    id:'exclusionAccordionEl'
                    ,title:'Editar exclusiones de '+record.get('nombre')+' '+(record.get('segundo_nombre')?record.get('segundo_nombre')+' ':' ')+record.get('Apellido_Paterno')+' '+record.get('Apellido_Materno')
                    ,cls:'claseTitulo'
                    ,loader:
                    {
                        url : urlExclusionp2
                        ,params:
                        {
                            'smap1.pv_cdunieco'     : inputCduniecop2,
                            'smap1.pv_cdramo'       : inputCdramop2,
                            'smap1.pv_estado'       : inputEstadop2,
                            'smap1.pv_nmpoliza'     : inputNmpolizap2,
                            'smap1.pv_nmsituac'     : record.get('nmsituac'),
                            'smap1.pv_cdperson'     : record.get('cdperson'),
                            'smap1.pv_cdrol'        : record.get('cdrol'),
                            'smap1.nombreAsegurado' : record.get('nombre')+' '+(record.get('segundo_nombre')?record.get('segundo_nombre')+' ':' ')+record.get('Apellido_Paterno')+' '+record.get('Apellido_Materno'),
                            'smap1.cdrfc'           : record.get('cdrfc'),
                            'smap1.botonCopiar'     : record.get('estomador')!='Si'?1:0
                        }
                        ,autoLoad:true
                        ,scripts:true
                    }
                    ,listeners:
                    {
                        expand:function( p, eOpts )
                        {
                            window.parent.scrollTo(0,150+p.y);
                        }
                    }
                }).expand();
            },
	        
	        onExclusionInter:function(grid,rowIndex)
	        {
	        	var me=this;
	        	debug("interval called");
	        	if(timeoutflagp2==1)
        		{
	        		debug("interval: 1");
	        		setTimeout(function(){me.onExclusionInter(grid,rowIndex)},500);
        		}
	        	else if(timeoutflagp2==3)
	        	{
	        		debug("interval: 3 proceder");
	        		me.onExclusionSave(grid,rowIndex);
	        	}
	        	else
        		{
        		    debug("finish: "+timeoutflagp2)
        		}
	        },
	        
	        onDomiciliosInter:function(grid,rowIndex)
            {
                var me=this;
                debug("interval called");
                if(timeoutflagp2==1)
                {
                    debug("interval: 1");
                    setTimeout(function(){me.onDomiciliosInter(grid,rowIndex)},500);
                }
                else if(timeoutflagp2==3)
                {
                    debug("interval: 3 proceder");
                    me.onDomiciliosSave(grid,rowIndex);
                }
                else
                {
                    debug("finish: "+timeoutflagp2)
                }
            },
	        
	        onDomiciliosClick:function(grid,rowIndex)
	        {
	        	var me=this;
	        	debug("domicilios.click");
	        	debug("validarYGuardar");
	        	validarYGuardar();
	        	setTimeout(function(){me.onDomiciliosInter(grid,rowIndex)},500);
	        },
	        
	        onExclusionClick:function(grid,rowIndex)
            {
                var me=this;
                debug("onExclusionClick");
                debug("validarYGuardar");
                validarYGuardar();
                setTimeout(function(){me.onExclusionInter(grid,rowIndex)},500);
            },

	        onRemoveClick: function(grid, rowIndex){
	            this.getStore().removeAt(rowIndex);
	        }
	        ,onTomadorClick : function(grid,rowIndex)
	        {
	            var record=grid.getStore().getAt(rowIndex);
	            debug('es tomador',record);
	            //gridTomadorp2.setDisabled(true);
	            grid.getStore().each(function(rec,idx)
           		{
            		rec.set('estomador','');
           		});
	            record.set('estomador','Si');
	            recordTomadorp2=record.copy();
	            debug('se puso en sesion recordTomadorp2',recordTomadorp2);
                storeTomadorp2.removeAll();
                storeTomadorp2.add(recordTomadorp2);
	        }
	    });
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ////// Fin de declaracion de grid                                                                                //////
	    ////// http://docs.sencha.com/extjs/4.2.1/extjs-build/examples/build/KitchenSink/ext-theme-neptune/#cell-editing //////
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    gridPersonasp2=new EditorIncisosp2();
	    gridTomadorp2=new GridTomadorP2();
		
		Ext.create('Ext.form.Panel',{
			id:'form1p2',
			renderTo:'maindivasegurados',
			frame:false,
			//collapsible:true,
			//titleCollapse:true,
			border:0,
			buttonAlign:'center',
			items:[
			    gridTomadorp2
			    ,gridPersonasp2
	        ],
	        buttons:[
	            <%--
	            {
	            	text:'Regresar',
	            	hidden:true,
	            	icon: contextop2+'/resources/extjs4/resources/ext-theme-neptune/images/toolbar/scroll-left.png',
	            	handler:function()
	            	{
	            		Ext.create('Ext.form.Panel').submit({
                            url : urlDatosComplementariosp2,
                            standardSubmit:true,
                            params:{
                                'cdunieco' :  inputCduniecop2,
                                'cdramo' :    inputCdramop2,
                                'estado' :    inputEstadop2,
                                'nmpoliza' :  inputNmpolizap2
                            }
                        });
	            	}
	            },
	            --%>
	            {
	            	text:'Guardar',
	            	icon: contextop2+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/accept.png',
	            	handler:function(){
	            		if(Ext.getCmp('form1p2').getForm().isValid())
	            		{
		            		var incisosRecords = storePersonasp2.getRange();
		            		if(incisosRecords&&incisosRecords.length>0)
	                        {
	                            var incisosJson = [];
	                            var completos=true;
	                            var sinCdperson=0;
	                            
	                            storePersonasp2.each(function(record,index)
                           		{
	                            	//console.log(record);
	                            	if(
                            			!record.get("nombre")
                            			||record.get("nombre").length==0
                            			||!record.get("Apellido_Paterno")
                                        ||record.get("Apellido_Paterno").length==0
                                        ||!record.get("Apellido_Paterno")
                                        ||record.get("Apellido_Paterno").length==0
                                        ||!record.get("Apellido_Materno")
                                        ||record.get("Apellido_Materno").length==0
                                        ||!record.get("cdrfc")
                                        ||record.get("cdrfc").length==0
                            			)
                            		{
	                            		//console.log("#incompleto:");
	                            		//console.log(record);
	                            	    completos=false;                            		
                            		}
	                            	if(!record.get("cdperson")||record.get("cdperson").length==0)
                            		{
	                            		sinCdperson++;
                            		}
	                                if(record.get('Parentesco')=='T')
                                	{
	                                	incisosJson.push({
		                                	nmsituac:'0',
	                                        cdrol:'1',
	                                        fenacimi: typeof record.get('fenacimi')=='string'?record.get('fenacimi'):Ext.Date.format(record.get('fenacimi'), 'd/m/Y'),
	                                        sexo:typeof record.get('sexo')=='string'?record.get('sexo'):record.get('sexo').get('key'),
	                                        cdperson:record.get('cdperson'),
	                                        nombre: record.get('nombre'),
	                                        segundo_nombre: record.get('segundo_nombre'),
	                                        Apellido_Paterno: record.get('Apellido_Paterno'),
	                                        Apellido_Materno: record.get('Apellido_Materno'),
	                                        cdrfc:record.get('cdrfc')
	                                	});
                                	}
	                                incisosJson.push({
                                        nmsituac:record.get('nmsituac'),
	                                    cdrol:typeof record.get('cdrol')=='string'?record.get('cdrol'):record.get('cdrol').get('key'),
	                                    fenacimi: typeof record.get('fenacimi')=='string'?record.get('fenacimi'):Ext.Date.format(record.get('fenacimi'), 'd/m/Y'),
	                                    sexo:typeof record.get('sexo')=='string'?record.get('sexo'):record.get('sexo').get('key'),
	                                    cdperson:record.get('cdperson'),
	                                    nombre: record.get('nombre'),
	                                    segundo_nombre: record.get('segundo_nombre'),
	                                    Apellido_Paterno: record.get('Apellido_Paterno'),
	                                    Apellido_Materno: record.get('Apellido_Materno'),
	                                    cdrfc:record.get('cdrfc')
	                                });
	                            });
                                
	                           //console.log("sin cd person: "+sinCdperson);
	                            if(completos)
                            	{
	                            	if(sinCdperson==0)
                            		{
	                            		editarDespuesValidacionesp2(incisosJson);//manda el submit
                            		}
	                            	else
	                            	{
		                            	Ext.getCmp('form1p2').setLoading(true);
		                            	//mandar a traer los cdperson de las personas asincrono
		                            	storePersonasp2.each(function(record,index)
                                        {
		                            		//console.log(index);
		                            		setTimeout(function()
		                            		{
		                            			//console.log("trigger");
	                                            Ext.Ajax.request(
                                                {
                                                    url: urlGenerarCdPersonp2,
                                                    success:function(response,opts)
                                                    {
                                                        var jsonResp = Ext.decode(response.responseText);
                                                        //console.log("respuesta cdperson",jsonResp);
                                                        //window.console&&console.log(jsonResp);
                                                        if(jsonResp.success==true)
                                                        {
                                                            try
                                                            {
                                                                record.data.cdperson=jsonResp.cdperson;
                                                                sinCdperson--;
                                                                if(sinCdperson==0)
                                                                {
                                                                    //procesar submit
                                                                    //console.log(storePersonasp2.getRange());
                                                                    //storePersonasp2.sync();
                                                                    //console.log(storePersonasp2.getRange());
                                                                    gridPersonasp2.getView().refresh();
                                                                    incisosJson=[];
                                                                    storePersonasp2.each(function(record,index)
                                                                    {
                                                                    	if(record.get('Parentesco')=='T')
                                                                        {
                                                                            incisosJson.push({
                                                                                nmsituac:'0',
                                                                                cdrol:'1',
                                                                                fenacimi: typeof record.get('fenacimi')=='string'?record.get('fenacimi'):Ext.Date.format(record.get('fenacimi'), 'd/m/Y'),
                                                                                sexo:typeof record.get('sexo')=='string'?record.get('sexo'):record.get('sexo').get('key'),
                                                                                cdperson:record.get('cdperson'),
                                                                                nombre: record.get('nombre'),
                                                                                segundo_nombre: record.get('segundo_nombre'),
                                                                                Apellido_Paterno: record.get('Apellido_Paterno'),
                                                                                Apellido_Materno: record.get('Apellido_Materno'),
                                                                                cdrfc:record.get('cdrfc')
                                                                            });
                                                                        }
                                                                        incisosJson.push({
                                                                            nmsituac:record.get('nmsituac'),
                                                                            cdrol:typeof record.get('cdrol')=='string'?record.get('cdrol'):record.get('cdrol').get('key'),
                                                                            fenacimi: typeof record.get('fenacimi')=='string'?record.get('fenacimi'):Ext.Date.format(record.get('fenacimi'), 'd/m/Y'),
                                                                            sexo:typeof record.get('sexo')=='string'?record.get('sexo'):record.get('sexo').get('key'),
                                                                            cdperson:record.get('cdperson'),
                                                                            nombre: record.get('nombre'),
                                                                            segundo_nombre: record.get('segundo_nombre'),
                                                                            Apellido_Paterno: record.get('Apellido_Paterno'),
                                                                            Apellido_Materno: record.get('Apellido_Materno'),
                                                                            cdrfc:record.get('cdrfc')
                                                                        });
                                                                    });                
                                                                    Ext.getCmp('form1p2').setLoading(false);
                                                                    editarDespuesValidacionesp2(incisosJson);
                                                                }
                                                            }
                                                            catch(e)
                                                            {
                                                                //console.log(e);
                                                                Ext.Msg.show({
                                                                    title:'Error',
                                                                    msg: 'Error al procesar la informaci&oacute;n',
                                                                    buttons: Ext.Msg.OK,
                                                                    icon: Ext.Msg.ERROR
                                                                });
                                                            }
                                                        }
                                                        else
                                                        {
                                                            Ext.Msg.show({
                                                                title:'Error',
                                                                msg: 'Error al obtener la informaci&oacute;n',
                                                                buttons: Ext.Msg.OK,
                                                                icon: Ext.Msg.ERROR
                                                            });
                                                        }
                                                    },
                                                    failure:function(response,opts)
                                                    {
                                                        Ext.Msg.show({
                                                            title:'Error',
                                                            msg: 'Error de comunicaci&oacute;n',
                                                            buttons: Ext.Msg.OK,
                                                            icon: Ext.Msg.ERROR
                                                        });
                                                    }
                                                })
		                            		},(index+1)*500);
                                        });
                            		}
                            	}
	                            else
                            	{
	                            	Ext.Msg.show({
	                                    title:'Datos incompletos',
	                                    msg: 'El nombre, apellidos y RFC son requeridos',
	                                    buttons: Ext.Msg.OK,
	                                    icon: Ext.Msg.WARNING
	                                });
                            	}
	                        }
		            		else
	            			{
		            			Ext.Msg.show({
                                    title:'Datos incompletos',
                                    msg: 'Favor de introducir al menos un asegurado',
                                    buttons: Ext.Msg.OK,
                                    icon: Ext.Msg.WARNING
                                });         			
	            			}
	            		}
	            		else
            			{
	            			Ext.Msg.show({
                                title:'Datos incompletos',
                                msg: 'Favor de llenar los campos requeridos',
                                buttons: Ext.Msg.OK,
                                icon: Ext.Msg.WARNING
                            });
            			}
	            	}
	            }
	            ,{
                    text:'Cancelar',
                    icon : contextop2+ '/resources/fam3icons/icons/cancel.png',
                    handler:function(){
                        expande(1);
                    }
                }
            ]
		});
		
	});
	
</script>
<%--
</head>
<body>
--%>
<div id="maindivasegurados"></div>
<%--
</body>
</html>
--%>