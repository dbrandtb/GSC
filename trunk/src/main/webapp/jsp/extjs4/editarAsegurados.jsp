<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	var inputCdunieco= '<s:property value="map1.cdunieco" />';
	var inputCdramo=   '<s:property value="map1.cdramo" />';
	var inputEstado=   '<s:property value="map1.estado" />';
	var inputNmpoliza= '<s:property value="map1.nmpoliza" />';
	var _URL_OBTEN_CATALOGO_GENERICO='<s:url action="jsonObtenCatalogoGenerico" namespace="/" />';
	var CDATRIBU_ROL='<s:property value="cdatribuRol" />';
	var gridPersonas;
	var CDATRIBU_SEXO='<s:property value="cdatribuSexo" />';
	var storeRoles;
	var storeGeneros;
	var storePersonas;
	var gridPersonas;
	var editorRoles;
	var editorGeneros;
	var urlCargarAsegurados='<s:url namespace="/" action="cargarComplementariosAsegurados" />';
	var urlCargarCatalogos='<s:url namespace="/flujocotizacion" action="cargarCatalogos" />';
	var urlDatosComplementarios='<s:url namespace="/" action="datosComplementarios.action" />';
	var urlGuardarAsegurados='<s:url namespace="/" action="guardarComplementariosAsegurados" />';
	var urlCoberturasAsegurado='<s:url namespace="/" action="editarCoberturas" />';
	var urlGenerarCdPerson='<s:url namespace="/" action="generarCdperson" />';
	var urlDomicilio      ='<s:url namespace="/" action="pantallaDomicilio" />';
	var editorFecha;
	
    function rendererRol(v)
    {
    	var leyenda='';
        if(typeof v == 'string')
	    		   //tengo solo el indice
        {
			//window.console&&console.log('string:');
			storeRoles.each(function(rec){
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
    
    function rendererSexo(v)
    {
        var leyenda='';
        if(typeof v == 'string')
                   //tengo solo el indice
        {
        	//window.console&&console.log('string:');
            storeGeneros.each(function(rec){
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
    
    function editarDespuesValidaciones(incisosJson)
    {
    	var formPanel=Ext.getCmp('form1');
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
            url: urlGuardarAsegurados,
            jsonData:Ext.encode(submitValues),
            success:function(response,opts)
            {
                //Ext.MessageBox.hide();
                formPanel.setLoading(false);
                var jsonResp = Ext.decode(response.responseText);
                //window.console&&console.log(jsonResp);
                if(jsonResp.success==true)
                {
                	Ext.Msg.show({
                        title:'Asegurados guardados',
                        msg: 'Se ha guardado la informaci&oacute;n',
                        buttons: Ext.Msg.OK
                    });
                }
                else
               	{
                	Ext.Msg.show({
                        title:'Error',
                        msg: 'No se pudo guardar',
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
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
            }
        });
    }
	
    Ext.onReady(function(){
		
		Ext.define('Modelo1',{
			extend:'Ext.data.Model',
			<s:property value="item1" />
		});
		
		storeRoles = new Ext.data.Store({
	        model: 'Generic',
	        autoLoad:true,
	        proxy:
	        {
	            type: 'ajax',
	            url : urlCargarCatalogos,
	            extraParams:{catalogo:'<s:property value="CON_CAT_POL_ROL" />'},
	            reader:
	            {
	                type: 'json',
	                root: 'lista'
	            }
	        }
	    })
	    
	    storeGeneros = new Ext.data.Store({
	        model: 'Generic',
	        autoLoad:true,
	        proxy:
	        {
	            type: 'ajax',
	            url : _URL_OBTEN_CATALOGO_GENERICO,
	            extraParams:{cdatribu:CDATRIBU_SEXO},
	            reader:
	            {
	                type: 'json',
	                root: 'lista'
	            }
	        }
	    });
	    
	    storePersonas =new Ext.data.Store(
   	    {
   	        // destroy the store if the grid is destroyed
   	        //autoDestroy: true,
   	        model: 'Modelo1',
   	        autoLoad:true,
   	        proxy:
   	        {
   	        	url:urlCargarAsegurados,
   	        	type:'ajax',
   	        	extraParams:
   	        	{
   	        		'map1.pv_cdunieco':inputCdunieco,
   	        		'map1.pv_cdramo':inputCdramo,
   	        		'map1.pv_estado':inputEstado,
   	        		'map1.pv_nmpoliza':inputNmpoliza
   	        	},
		   	    reader:
		        {
		            type: 'json',
		            root: 'list1'
		        }
   	        }
   	    });
	    
	    editorRoles=Ext.create('Ext.form.ComboBox',
   	    {
   	        store: storeRoles,
   	        queryMode:'local',
   	        displayField: 'value',
   	        valueField: 'key',
   	        allowBlank:false,
   	        editable:false
   	    });
	    
	    editorGeneros=Ext.create('Ext.form.ComboBox',
   	    {
   	        store: storeGeneros,
   	        queryMode:'local',
   	        displayField: 'value',
   	        valueField: 'key',
   	        allowBlank:false,
   	        editable:false
   	    });
	    
	    editorFecha=Ext.create('Ext.form.field.Date',
        {
	    	format:'d/m/Y',
            allowBlank:false
        });
	    
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ////// Inicio de declaracion de grid                                                                             //////
	    ////// http://docs.sencha.com/extjs/4.2.1/extjs-build/examples/build/KitchenSink/ext-theme-neptune/#cell-editing //////
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    Ext.define('EditorIncisos', {
	        extend: 'Ext.grid.Panel',

	        requires: [
	            'Ext.selection.CellModel',
	            'Ext.grid.*',
	            'Ext.data.*',
	            'Ext.util.*',
	            'Ext.form.*'
	        ],
	        xtype: 'cell-editing',

	        //title: 'Asegurados',
	        frame: false,
	        collapsible:true,
	        titleCollapse:true,
	        style:'margin:5px;',
            title:'Asegurados',

	        initComponent: function() {
	            this.cellEditing = new Ext.grid.plugin.CellEditing({
	                clicksToEdit: 1
	            });

	            Ext.apply(this, {
	                //width: 750,
	                height: 200,
	                plugins: [this.cellEditing],
	                store: storePersonas,
	                <s:property value="item2" />,
	                /*columns: 
	                [
	                    {
	                        header: 'Rol',
	                        dataIndex: 'rol',
	                        flex:1,
	                        editor: comboRoles,
	                        renderer:function(v)
	                        {
	                            var leyenda='';
	                            if(typeof v == 'string')
	                            //tengo solo el indice
	                            {
	                                //window.console&&console.log('string:');
	                                storeRoles.each(function(rec){
	                                    //window.console&&console.log('iterando...');
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
	                            return leyenda;
	                        }
	                    },
	                    {
	                        header: 'Fecha de nacimiento',
	                        dataIndex: 'fechaNacimiento',
	                        flex:2,
	                        renderer: Ext.util.Format.dateRenderer('d M Y'),
	                        editor: {
	                            xtype: 'datefield',
	                            format: 'd/m/Y',
	                            editable:true
	                        }
	                    },
	                    {
	                        header: 'Sexo',
	                        dataIndex: 'sexo',
	                        flex:1,
	                        editor: comboGeneros,
	                        renderer:function(v)
	                        {
	                            var leyenda='';
	                            if(typeof v == 'string')
	                            //tengo solo el indice
	                            {
	                                //window.console&&console.log('string:');
	                                storeGeneros.each(function(rec){
	                                    //window.console&&console.log('iterando...');
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
	                            return leyenda;
	                        }
	                    },
	                    {
	                        header: 'Nombre',
	                        dataIndex: 'nombre',
	                        flex: 1,
	                        editor: {
	                            //allowBlank: false
	                        }
	                    },
	                    {
	                        header: 'Segundo nombre',
	                        dataIndex: 'segundoNombre',
	                        flex: 2,
	                        editor: {
	                            //allowBlank: false
	                        }
	                    },
	                    {
	                        header: 'Apellido paterno',
	                        dataIndex: 'apellidoPaterno',
	                        flex: 2,
	                        editor: {
	                            //allowBlank: false
	                        }
	                    },
	                    {
	                        header: 'Apellido materno',
	                        dataIndex: 'apellidoMaterno',
	                        flex: 2,
	                        editor: {
	                            //allowBlank: false
	                        }
	                    },
	                    {
	                        xtype: 'actioncolumn',
	                        width: 30,
	                        sortable: false,
	                        menuDisabled: true,
	                        items: [{
	                            icon:'resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
	                            //iconCls: 'icon-delete',
	                            tooltip: 'Quitar inciso',
	                            scope: this,
	                            handler: this.onRemoveClick
	                        }]
	                    }
	                ],*/
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

	        /*loadStore: function() {
	            this.getStore().load({
	                // store loading is asynchronous, use a load listener or callback to handle results
	                callback: this.onStoreLoad
	            });
	        },

	        onStoreLoad: function(){
	            Ext.Msg.show({
	                title: 'Store Load Callback',
	                msg: 'store was loaded, data available for processing',
	                icon: Ext.Msg.INFO,
	                buttons: Ext.Msg.OK
	            });
	        },*/

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
	        
	        onEditarClick:function(grid,rowIndex)
	        {
	        	var record=this.getStore().getAt(rowIndex);
	        	Ext.create('Ext.form.Panel').submit({
                    url : urlCoberturasAsegurado,
                    standardSubmit:true,
                    params:{
                    	'smap1.pv_cdunieco':inputCdunieco,
                        'smap1.pv_cdramo':inputCdramo,
                        'smap1.pv_estado':inputEstado,
                        'smap1.pv_nmpoliza':inputNmpoliza,
                        'smap1.pv_nmsituac':record.get('nmsituac'),
                        'smap1.pv_cdperson':record.get('cdperson')
                    }
                });
	        },
	        
	        onDomiciliosClick:function(grid,rowIndex)
	        {
	        	var record=this.getStore().getAt(rowIndex);
                Ext.create('Ext.form.Panel').submit({
                    url : urlDomicilio,
                    standardSubmit:true,
                    params:{
                        'smap1.pv_cdunieco':inputCdunieco,
                        'smap1.pv_cdramo':inputCdramo,
                        'smap1.pv_estado':inputEstado,
                        'smap1.pv_nmpoliza':inputNmpoliza,
                        'smap1.pv_nmsituac':record.get('nmsituac'),
                        'smap1.pv_cdperson':record.get('cdperson')
                    }
                });
	        },

	        onRemoveClick: function(grid, rowIndex){
	            this.getStore().removeAt(rowIndex);
	        }
	    });
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    ////// Fin de declaracion de grid                                                                                //////
	    ////// http://docs.sencha.com/extjs/4.2.1/extjs-build/examples/build/KitchenSink/ext-theme-neptune/#cell-editing //////
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    gridPersonas=new EditorIncisos();
		
		Ext.create('Ext.form.Panel',{
			id:'form1',
			renderTo:'maindiv',
			frame:false,
			//collapsible:true,
			//titleCollapse:true,
			buttonAlign:'center',
			items:[
			    gridPersonas
	        ],
	        buttons:[
	            {
	            	text:'Regresar',
	            	icon: 'resources/extjs4/resources/ext-theme-neptune/images/toolbar/scroll-left.png',
	            	handler:function()
	            	{
	            		Ext.create('Ext.form.Panel').submit({
                            url : urlDatosComplementarios,
                            standardSubmit:true,
                            params:{
                                'cdunieco' :  inputCdunieco,
                                'cdramo' :    inputCdramo,
                                'estado' :    inputEstado,
                                'nmpoliza' :  inputNmpoliza
                            }
                        });
	            	}
	            },
	            {
	            	text:'Guardar',
	            	icon: 'resources/extjs4/resources/ext-theme-classic/images/icons/fam/accept.png',
	            	handler:function(){
	            		if(Ext.getCmp('form1').getForm().isValid())
	            		{
		            		var incisosRecords = storePersonas.getRange();
		            		if(incisosRecords&&incisosRecords.length>0)
	                        {
	                            var incisosJson = [];
	                            var completos=true;
	                            var sinCdperson=0;
	                            storePersonas.each(function(record,index)
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
	                            	if(sinCdperson>0)
                            		{
		                            	Ext.getCmp('form1').setLoading(true);
		                            	//mandar a traer los cdperson de las personas asincrono
		                            	storePersonas.each(function(record,index)
                                        {
		                            		//console.log(index);
		                            		setTimeout(function()
		                            		{
		                            			//console.log("trigger");
	                                            Ext.Ajax.request(
                                                {
                                                    url: urlGenerarCdPerson,
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
                                                                    storePersonas.sync();
                                                                    gridPersonas.getView().refresh();
                                                                    incisosJson=[];
                                                                    storePersonas.each(function(record,index)
                                                                    {
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
                                                                    Ext.getCmp('form1').setLoading(false);
                                                                    editarDespuesValidaciones(incisosJson);
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
	                            	else
                            		{
	                            		editarDespuesValidaciones(incisosJson);//manda el submit
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
            ]
		});
		
	});
	
</script>
</head>
<body>
<div id="maindiv"></div>
</body>
</html>