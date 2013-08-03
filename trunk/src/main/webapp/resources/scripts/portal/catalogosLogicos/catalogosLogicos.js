Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
		 	
         var storeRegion = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_REGION
                }),

                reader: new Ext.data.JsonReader({
            	root:'regionesComboBox',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'codigo',  type: 'string',  mapping:'codigo'},
		        {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
			])
        });

         var storeIdioma = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({
				url: _ACTION_COMBO_IDIOMA
                }),

                reader: new Ext.data.JsonReader({
            	root:'comboIdiomas',
            	totalProperty: 'totalCount',
            	successProperty : '@success'
	        },[
		        {name: 'codigo',  type: 'string',  mapping:'codigo'},
		        {name: 'descripcion',  type: 'string',  mapping:'descripcion'}
			])
        });
        
        var storeGrilla = new Ext.data.Store({

    			proxy: new Ext.data.HttpProxy({url: _ACTION_BUSCAR_REGISTROS}),
                reader: new Ext.data.JsonReader({
		            	root:'MListaCatalogosLogicos',
		            	totalProperty: 'totalCount',
		            	successProperty : '@success'
		        },
		        [
			        {name: 'cdtabla',  type: 'string'},
			        {name: 'cdregion',  type: 'string'},
			        {name: 'dsregion',  type: 'string'},
			        {name: 'cdidioma',  type: 'string'},
			        {name: 'dsidioma',  type: 'string'},
			        {name: 'codigo',  type: 'string'},
			        {name: 'descripcion',  type: 'string'},
			        {name: 'descripcionLarga',  type: 'string'},
			        {name: 'etiqueta',  type: 'string'}
				])
        });

var formPanel = new Ext.FormPanel ({
	renderTo: 'formBusqueda',
	id:'formPanelId',
	title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formPanelId',helpMap,'Cat&aacute;logos L&oacute;gicos')+'</span>',
    iconCls:'logo',
   	bodyStyle:'background: white',
   	buttonAlign: "center",
   	labelAlign: 'right',
     
     //se definen los campos del formulario
    items:[
      		{
              layout:'form',
			  border: false,
              baseCls: '',
              title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br/>',
			  bodyStyle : 'padding:5px 5px 0',
	          items : [
					{
                   		fieldLabel: getLabelFromMap('nomTabla',helpMap,'Nombre tabla'),
                   		tooltip:getToolTipFromMap('nomTabla',helpMap,'Nombre tabla'),
                        hasHelpIcon:getHelpIconFromMap('nomTabla',helpMap),
                        Ayuda: getHelpTextFromMap('nomTabla',helpMap,''),   	
                   		xtype: 'textfield',
                   		name : 'cdTabla',
                   		id: 'nomTabla'
               		},
               		{
                   		xtype: 'combo',
                   		id:'cmbRegion',
	                    labelWidth: 50,
	                    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	                    store: storeRegion,
	                    displayField:'descripcion',
	                    valueField: 'codigo',
	                    hiddenName: 'cdRegion',
	                    typeAhead: true,
	                    mode: 'local',
	                    triggerAction: 'all',
	                    fieldLabel: getLabelFromMap('cmbRegion',helpMap,'Regi&oacute;n'),
	                    tooltip:getToolTipFromMap('cmbRegion',helpMap,'Regi&oacute;n'),
                        hasHelpIcon:getHelpIconFromMap('cmbRegion',helpMap),
                        Ayuda: getHelpTextFromMap('cmbRegion',helpMap,''),   	
	                    width: 200,
	                    emptyText:'Seleccione Region ...',
	                    selectOnFocus:true,
	                    forceSelection:true
	                    //allowBlank : false,
	                    //blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido'),
               		},
               		{
	                    xtype: 'combo',
	                    id:'cmbIdioma', 
	                    labelWidth: 70, 
	                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
		                store: storeIdioma,
		                displayField:'descripcion', 
		                valueField:'codigo', 
		                hiddenName: 'cdIdioma', 
		                typeAhead: true,
		                mode: 'local', 
		                triggerAction: 'all', 
		                fieldLabel: getLabelFromMap('cmbIdioma',helpMap,'Idioma'),
	                    tooltip:getToolTipFromMap('cmbIdioma',helpMap,'Idioma'), 
                        hasHelpIcon:getHelpIconFromMap('cmbIdioma',helpMap),
                        Ayuda: getHelpTextFromMap('cmbIdioma',helpMap,''),   	
		                width: 200, 
		                emptyText:'Seleccione Idioma...',
		                selectOnFocus:true, 
		                forceSelection:true 
		                //allowBlank : false,
		                //blankText: getMsgBlankTextFromMap('400013', helpMap, 'Este campo es requerido'),
             		},
               		{
	                	xtype: 'textfield',
	                	fieldLabel: getLabelFromMap('ds_Codigo',helpMap,'C&oacute;digo'),
	                    tooltip:getToolTipFromMap('ds_Codigo',helpMap,'C&oacute;digo'),
                        hasHelpIcon:getHelpIconFromMap('ds_Codigo',helpMap),
                        Ayuda: getHelpTextFromMap('ds_Codigo',helpMap,''),   	
	                 	name: 'codigo',
	                 	id: 'ds_Codigo'
                		},
              			{
	               		xtype: 'textfield',
	                 	fieldLabel: getLabelFromMap('ds_Descripcion',helpMap,'Descripci&oacute;n'),
	                 	tooltip:getToolTipFromMap('ds_Descripcion',helpMap,'Descripci&oacute;n'),
                        hasHelpIcon:getHelpIconFromMap('ds_Descripcion',helpMap),
                        Ayuda: getHelpTextFromMap('ds_Descripcion',helpMap,''),   	
	                   	name: 'descripcion',
	                   	id: 'ds_Descripcion'
					}
          			],
          	buttonAlign: 'center',
          	buttons: [
          				{
            			text: getLabelFromMap('BtnBuscar',helpMap,'Buscar'),
                 		tooltip:getToolTipFromMap('BtnBuscar',helpMap,'Busca cat&aacute;logos l&oacute;gicos'),
                 		handler: function () {reloadGrid(grid2);}
            		},
            		{
            			text: getLabelFromMap('BtnCancelar',helpMap,'Cancelar'),
                 		tooltip:getToolTipFromMap('BtnCancelar',helpMap,'Cancela la b&uacute;squeda de cat&aacute;logos l&oacute;gicos'),
                 		handler: function () {formPanel.form.reset();}
            		}
          			]
          
          	}]
      });


		var cm = new Ext.grid.ColumnModel ([
			{
		        header: getLabelFromMap('cmTabla',helpMap,'Tabla'),
		        tooltip: getToolTipFromMap('cmTabla',helpMap,'Tabla'),
		        dataIndex: 'cdtabla',
		        width:100,
		        sortable: true
			},
			{
		        header: getLabelFromMap('cmRegion',helpMap,'Regi&oacute;n'),
		        tooltip: getToolTipFromMap('cmRegion',helpMap,'Regi&oacute;n'),
		        dataIndex: 'dsregion',
		        width:75,
		        sortable: true
			},
			{
		        header: getLabelFromMap('cmIdioma',helpMap,'Idioma'),
		        tooltip: getToolTipFromMap('cmIdioma',helpMap,'Idioma'),
		        dataIndex: 'dsidioma',
		        width:75,
		        sortable: true
			},
			{
		        header: getLabelFromMap('cmCodigo',helpMap,'C&oacute;digo'),
		        tooltip: getToolTipFromMap('cmCodigo',helpMap,'C&oacute;digo'),
		        dataIndex: 'codigo',
		        width:75,
		        sortable: true
			},
			{
		        header: getLabelFromMap('cmDescripcion',helpMap,'Descripci&oacute;n'),
		        tooltip: getToolTipFromMap('cmDescripcion',helpMap,'Descripci&oacute;n'),
				width: 200,
		        dataIndex: 'descripcion',
		        sortable: true
			},
			{
		        header: getLabelFromMap('cmDescripcionLarga',helpMap,'Descripci&oacute;n Larga'),
		        tooltip: getToolTipFromMap('cmDescripcionLarga',helpMap,'Descripci&oacute;n Larga'),
		        dataIndex: 'descripcionLarga',
		        width: 200,
		        sortable: true
			},
			{
		        header: getLabelFromMap('cmEtiqueta',helpMap,'Etiqueta'),
		        tooltip: getToolTipFromMap('cmEtiqueta',helpMap,'Etiqueta'),
		        dataIndex: 'etiqueta',
		        width: 200,
		        sortable: true
			},
			{hidden:true, dataIndex:'cdregion'},
			{hidden:true, dataIndex:'cdidioma'}
			
		]);
		
	var grid2= new Ext.grid.GridPanel({
        el:'gridElementos',
        id: 'grid2',
        store: storeGrilla,
        
		border:true,
		title: '<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
        cm: cm,
        loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
        buttonAlign:'center',
		buttons:[
        		{text:getLabelFromMap('BtnAdd',helpMap,'Agregar'),
        		tooltip: getToolTipFromMap('BtnAdd',helpMap,'Agrega un nuevo registro'),
            	handler:function(){
                agregar();
                }
            	},{ 
            	text:getLabelFromMap('BtnEd',helpMap,'Editar'),
        		tooltip: getToolTipFromMap('BtnEd',helpMap,'Edita un registro'),
            	handler:function(){
                    if (getSelectedRecord(grid2) != null) {
                        editar(getSelectedRecord(grid2));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
            	}
            	},{
            	text:getLabelFromMap('BtnDel',helpMap,'Eliminar'),
        		tooltip: getToolTipFromMap('BtnDel',helpMap,'Elimina un registro'),
                handler:function(){
                    if (getSelectedRecord(grid2) != null) {
                        borrar(getSelectedRecord(grid2));
                    } else {
                        Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                    }
                }
               },{
               	text:getLabelFromMap('BtnExport',helpMap,'Exportar'),
            	tooltip: getToolTipFromMap('BtnExport',helpMap,'Exporta el listado en un formato determinado'),
            	handler:function(){                		
                		var url=_ACTION_EXPORT+'?cdTabla='+Ext.getCmp('formPanelId').form.findField('nomTabla').getValue();
                	 	url +='&cdRegion='+Ext.getCmp('formPanelId').form.findField('cmbRegion').getValue();
                	 	url +='&cdIdioma='+Ext.getCmp('formPanelId').form.findField('cmbIdioma').getValue();
                	 	url+='&codigo='+Ext.getCmp('formPanelId').form.findField('ds_Codigo').getValue();
                	 	url+='&descripcion='+Ext.getCmp('formPanelId').form.findField('ds_Descripcion').getValue();
                	 	
                	 	showExportDialog(url);
                	 }
                }
            	],            	            	
    	width:500,
    	frame:true,
		height:320,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		collapsible: true,
		bbar: new Ext.PagingToolbar({
				pageSize: itemsPerPage,
				store: storeGrilla,
				displayInfo: true,
				displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
		    })
		});
		
		
	formPanel.render();
    grid2.render();
    
    storeRegion.load({
    	callback: function () {
    		storeIdioma.load();
    	}
    });


	function borrar (record) {
		Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
			{
         		if (btn == "yes"){
         			var _params = {
						cdTabla: record.get("cdtabla"),
					  	cdRegion: record.get("cdregion"),
					  	cdIdioma: record.get("cdidioma"),
					  	codigo: record.get("codigo")
					  };
         			execConnection (_ACTION_BORRAR_REGISTRO, _params, cbkBorrar);
				}
		})		
		
	}
	function cbkBorrar (_success, _message) {
	 	if (!_success) {
	 		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _message);
	 	}else {
	 		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _message, function(){reloadGrid(Ext.getCmp('grid2'));});
	 	}
	}
});

function reloadGrid(grid){
	var _params = {
			cdTabla: Ext.getCmp('formPanelId').form.findField('nomTabla').getValue(),
			cdRegion: Ext.getCmp('formPanelId').form.findField('cmbRegion').getValue(),
			cdIdioma: Ext.getCmp('formPanelId').form.findField('cmbIdioma').getValue(),
			codigo: Ext.getCmp('formPanelId').form.findField('ds_Codigo').getValue(),
			descripcion: Ext.getCmp('formPanelId').form.findField('ds_Descripcion').getValue()
	};
	reloadComponentStore(grid, _params, cbkReload);
}
function cbkReload(_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}