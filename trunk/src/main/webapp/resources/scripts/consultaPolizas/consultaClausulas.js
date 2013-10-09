Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {

    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });
    
    // Conversión para el tipo de moneda
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';
	
    
    Ext.define('modelClau',
    	    {
    	        extend:'Ext.data.Model',
    	        fields:['key','value']
    	    });
    
    var storeClavesClausulas = new Ext.data.Store(
    	    {
    	        model      : 'modelClau'
    	        ,autoLoad  : true
    	        ,proxy     :
    	        {
    	            url     : _URL_CARGA_CLAVES_CLAU
    	            ,type   : 'ajax'
    	            ,reader :
    	            {
    	                type  : 'json'
    	                ,root : 'listaGenerica'
    	            }
    	        }
    });
    
    Ext.define('modeloGridClau',{
        extend:'Ext.data.Model',
        fields:['key','value']
    });
    
    var storGridClau = new Ext.data.Store(
    	    {
    	    	pageSize : 10
    	        ,model      : 'modeloGridClau'
    	        ,autoLoad  : false
    	        ,proxy     :
    	        {
    	            enablePaging : true,
    	            reader       : 'json',
    	            type         : 'memory',
    	            data         : []
    	        }
    });
    
    Ext.create('Ext.panel.Panel',
    	    {
    	        border    : 0
    	        ,renderTo : 'div_clau'
    	        ,items    :
    	        [
    	            Ext.create('Ext.panel.Panel',
    	            {
    	                border  : 0
    	                ,layout :
    	                {
    	                    type     : 'table'
    	                    ,columns : 2
    	                }
    	                ,defaults : 
    	                {
    	                    style : 'margin:5px;'
    	                }
    	                ,items :
    	                [
    	                    Ext.create('Ext.form.field.ComboBox',
    	                    {
    	                        id              : 'idComboClau'
    	                        ,store          : storeClavesClausulas
    	                        ,displayField   : 'key'
    	                        ,valueField     : 'key'
    	                        ,editable       : true
    	                        ,forceSelection : false
    	                        ,style          : 'margin:5px'
    	                        ,fieldLabel     : 'Clave de la cl&aacute;usula'
    	                        ,width          :  250
    	                    })
    	                    ,{
    	                        xtype : 'textfield'
    	                        ,fieldLabel : 'Filtro Descripci&oacute;n'
    	                        ,id         : 'idFiltroDes'
    	                    }
    	                ]
    	                ,buttonAlign: 'center'
    	                ,buttons : [{
    	                	text: "Buscar"
    	                	,handler: function(){
    	                		var params = {
	                				'params.cdclausu' : Ext.getCmp('idComboClau').getValue(),
	                				'params.dsclausu' : Ext.getCmp('idFiltroDes').getValue()
	                			};
    	                		cargaStorePaginadoLocal(storGridClau, _URL_CONSULTA_CLAUSU, 'listaGenerica', params, null);
    	                	}	
    	                },{
    	                	text: "Limpiar"
        	                	,handler: function(){
        	                			Ext.getCmp('idComboClau').reset();
        	                			Ext.getCmp('idFiltroDes').reset();
        	                	}	
        	             }] 
    	            })
    	            ,Ext.create('Ext.grid.Panel',
    	            {
    	                id             : 'clausulasGridId'
    	                ,title         : 'Cl&aacute;usulas'
    	                ,store         :  storGridClau
    	                ,collapsible   : true
    	                ,titleCollapse : true
    	                ,style         : 'margin:5px'
    	                ,height        : 200
    	                ,columns       :
    	                [
    	                    {
    	                        header     : 'Clave'
    	                        ,dataIndex : 'key'
    	                        ,flex      : 1
    	                    }
    	                    ,
    	                    {
    	                        header     : 'Descripcion de la Cl&aacute;usula'
        	                    ,dataIndex : 'value'
        	                    ,flex      : 1
        	                }
    	                ],
    	                bbar     :
    	                {
    	                    displayInfo : true,
    	                    store       : storGridClau,
    	                    xtype       : 'pagingtoolbar'
    	                }
    	            	,buttonAlign: 'center'
		                ,buttons : [{
		                	text: "Agregar"
		                	,handler: function(){
		                		edicionActualizacionClausulas(null,null,0);
		                	}	
		                },{
		                	text: "Editar"
	    	                	,handler: function(){
	    	                		if(Ext.getCmp('clausulasGridId').getSelectionModel().hasSelection()){
	    	                			var rowSelected = Ext.getCmp('clausulasGridId').getSelectionModel().getSelection()[0];
	    	                			edicionActualizacionClausulas(rowSelected.get('key'),rowSelected.get('value'),1);
	    	                		}else {
	    	                			Ext.Msg.alert('Aviso', 'Debe de seleccionar una cl&aacute;usula para realizar la edici&oacute;n');
	    	                		}
	    	                	}	
	    	             }]
    	            })
    	        ]
    	    });
    
});