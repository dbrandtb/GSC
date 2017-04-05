<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>

Ext.onReady(function(){
    
    // Conversión para el tipo de moneda
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';
	
    var _URL_CONSULTA_DATOS_ASEGURADO =     '<s:url namespace="/consultasPoliza" action="consultaDatosAsegurado" />';
	var _cdUnieco                     = '<s:property value="params.cdunieco" />';
	var _cdRamo                       = '<s:property value="params.cdramo" />';
	var _estado                       = '<s:property value="params.estado" />';
	var _nmpoliza                     = '<s:property value="params.nmpoliza" />';
	var _nmsuplem                     = '<s:property value="params.suplemento" />';
	var _URL_LOADER_VER_EXCLUSIONES       = '<s:url namespace="/consultasPoliza" action="includes/verClausulas" />';
	
	// Modelo
    Ext.define('AseguradosModel', {
        extend: 'Ext.data.Model',
        fields: [
            {type:'string', name:'cdperson'},
            {type:'string',    name:'cdrfc'},
            {type:'string',    name:'cdrol'},
            {type:'string',    name:'dsrol'},
            {type:'date',    name:'fenacimi', dateFormat: 'd/m/Y'},
            {type:'string',    name:'nmsituac'},
            {type:'string',    name:'sexo'},
            {type:'string',    name:'nombre'},
            {type:'string',    name:'status'},
            {type:'string',    name:'parentesco'}
        ]
    });
    
    // Store
    var storeAsegurados = new Ext.data.Store({
     model: 'AseguradosModel',
     proxy:
     {
          type: 'ajax',
          url : _URL_CONSULTA_DATOS_ASEGURADO,
      reader:
      {
           type: 'json',
           root: 'datosAsegurados'
      }
     }
    });
    
    var gridDatosAsegurado = Ext.create('Ext.grid.Panel', {
        title   : 'DATOS DE LOS ASEGURADOS',
        store   : storeAsegurados,
        id      : 'gridDatosAsegurado',
        width   : 830,
        autoScroll:true,
        renderTo: 'divAsegs',
        autoScroll:true,
        items:[{
           xtype:'textfield', name:'cdrfc', fieldLabel: 'RFC', readOnly: true, labelWidth: 120
        }],
        columns: [
            //{text:'Rol',dataIndex:'dsrol',width:130 , align:'left'},
            {text:'Tipo de <br/> asegurado',dataIndex:'parentesco',width:100 , align:'left'},
            {text:'Clave <br/>Asegurado',dataIndex:'cdperson',width:100,align:'left'},
            {text:'Nombre',dataIndex:'nombre',width:250,align:'left'},
            {text:'Estatus',dataIndex:'status',width:90,align:'left'},
            {text:'RFC',dataIndex:'cdrfc',width:110,align:'left'},
            {text:'Sexo',dataIndex:'sexo',width:90 , align:'left'},
            {text:'Fecha Nac.',dataIndex:'fenacimi',width:100, align:'left',renderer: Ext.util.Format.dateRenderer('d/m/Y')},
            {
                xtype        : 'actioncolumn',
                icon         : _CONTEXT+'/resources/fam3icons/icons/lock.png',
                tooltip      : 'Ver endosos',
                width        : 30,
                menuDisabled : true,
                sortable     : false,
                handler      : function(grid,rowIndex)
                {
                    var record = grid.getStore().getAt(rowIndex);
                    
                    params ={
                    	'params.cdunieco': _cdUnieco,
                    	'params.cdramo': _cdRamo,
                    	'params.estado': _estado,
                    	'params.nmpoliza': _nmpoliza,
                    	'params.suplemento': _nmsuplem,
                    	'params.nmsituac':record.get('nmsituac')
                    };
                    
                    
                    Ext.create('Ext.window.Window', {
                        title       : 'Endosos',
                        modal       : true,
                        buttonAlign : 'center',
                        autoScroll  : true,
                        width       : 450,
                        height      : 455,
                        loader      :
                        {
                            url      : _URL_LOADER_VER_EXCLUSIONES,
                            scripts  : true,
                            autoLoad : true,
                            params   : params
                        }
                     }).show();
                }
            }
        ]
    });
    
    storeAsegurados.load({
    	params : {
        	'params.cdunieco': _cdUnieco,
        	'params.cdramo': _cdRamo,
        	'params.estado': _estado,
        	'params.nmpoliza': _nmpoliza,
        	'params.suplemento': _nmsuplem
        },
        callback: function(records, operation, success){
            if(!success){
                showMessage('Error', 'Error al obtener los datos del asegurado', Ext.Msg.OK, Ext.Msg.ERROR);
            }
        }
    });
	
});
</script>
<div id="divAsegs" style="height:500px;"></div>