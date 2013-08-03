Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";

/********* Comienza la grilla ******************************/
	var cm = new Ext.grid.ColumnModel ([
				{
			        header: getLabelFromMap('manErrCmCod',helpMap,'C&oacute;digo'),
			        tooltip: getToolTipFromMap('manErrCmCod',helpMap,'C&oacute;digo de mantenimiento de errores'),
			        hasHelpIcon:getHelpIconFromMap('manErrCmCod',helpMap),
		            Ayuda: getHelpTextFromMap('manErrCmCod',helpMap,''),   
					dataIndex: 'cdError',
					width: 80,
					sortable: true
				},
				{
			        header: getLabelFromMap('manErrCmMsg',helpMap,'Mensaje'),
			        tooltip: getToolTipFromMap('manErrCmMsg',helpMap,'Mensaje de mantenimiento de errores'),
			        hasHelpIcon:getHelpIconFromMap('manErrCmMsg',helpMap),
		            Ayuda: getHelpTextFromMap('manErrCmMsg',helpMap,''),   
					dataIndex: 'dsMensaje',
					width: 310,
					sortable: true
				},
				{
			        header: getLabelFromMap('manErrCmTip',helpMap,'Tipo'),
			        tooltip: getToolTipFromMap('manErrCmTip',helpMap,'Tipo de mantenimiento de errores'),
			        hasHelpIcon:getHelpIconFromMap('manErrCmTip',helpMap),
		            Ayuda: getHelpTextFromMap('manErrCmTip',helpMap,''),   
					dataIndex: 'cdTipo',
					width: 70,
					sortable: true,
					align:'center'
				}
			]);
	var dsGrilla = new Ext.data.Store ({
			proxy: new Ext.data.HttpProxy ({url: ACTION_BUSCAR_MENSAJES}),
			reader: new Ext.data.JsonReader({
						root: 'listaMensajes',
						totalProperty: 'totalCount',
						successProperty: 'success'
					},
					[
						{name: 'cdError', type: 'string', mapping: 'msgId'},
						{name: 'dsMensaje', type: 'string', mapping: 'msgText'},
						{name: 'cdTipo', type: 'string', mapping: 'msgTitle'}
					]
					)
		});
	var grilla = new Ext.grid.GridPanel ({
				id: 'grilla',
				el: 'grillaResultados',
				cm: cm,
				store: dsGrilla,
				border: true,
				stripeRows: true,
				bodyStyle:'background: white',
				collapsible: true,
				frame: true,
				loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
				width: 500,
				height: 300,
				title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
				buttonAlign:'center',
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
				buttons: [
						{
		                    text:getLabelFromMap('manErrBtnAdd',helpMap,'Agregar'),
		                    tooltip: getToolTipFromMap('manErrBtnAdd',helpMap,'Agrega en mantenimiento de error'),
							handler: function(){agregar()}},
						{
		                    text:getLabelFromMap('manErrBtnEd',helpMap,'Editar'),
		                    tooltip: getToolTipFromMap('manErrBtnEd',helpMap,'Edita en mantenimiento de error'),
							handler: function(){
										if (getSelectedRecord(grilla) != null) {
											editar(getSelectedKey(grilla, "cdError"));
										}else {
											Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'))
										}
									}
						},
						{
		                    text:getLabelFromMap('manErrBtnExp',helpMap,'Exportar'),
		                    tooltip: getToolTipFromMap('manErrBtnExp',helpMap,'Exporta en mantenimiento de error'),
							handler: function(){
				                        var url = ACTION_EXPORTAR_MENSAJES + '?cdError=' + Ext.getCmp('el_form').form.findField('codigoError').getValue() + '&dsMensaje=' + Ext.getCmp('el_form').form.findField('mensajeError').getValue();
				                	 	showExportDialog( url )
								}
						}/*,{
						
		                    text:getLabelFromMap('manErrBtnBack',helpMap,'Regresar'),
		                    tooltip: getToolTipFromMap('manErrBtnBack',helpMap,'Regresa a la pantalla anterior'),
							handler: function(){}
						}*/
					],
				bbar: new Ext.PagingToolbar({
						pageSize:itemsPerPage,
						store: dsGrilla,
						displayInfo: true,
		                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
						emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
				    })
		});
/********* Fin grilla **************************************/

/********* Comienza el form ********************************/
	var el_form = new Ext.FormPanel ({
			id: 'el_form',
			renderTo: 'formulario',
			//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('24',helpMap,'Mantenimiento de mensajes de error')+'</span>',
			title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('el_form', helpMap,' de  de error')+'</span>',
            labelWidth : 70,
            frame : true,
            bodyStyle : 'padding:5px 5px 0',
            bodyStyle:'background: white',
            width : 500,
            autoHeight: true,
            labelAlign:'right',
            waitMsgTarget : true,
            layout: 'form',
 items:[{
	layout: 'form',
	border: false,
	items:[{
		labelWidth: 100,
		layout: 'form',
		frame:true,
		baseCls: '',
		buttonAlign: "center",
			
		items:[{
			layout:'column',
			border: false,
			labelAlign:'right',
			html:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
			columnWidth: 1,
			
			items:[{	
		    	columnWidth:.6,
	           	layout: 'form',
	            border: false,
	            items: [
            		new Ext.form.NumberField({
				        fieldLabel: getLabelFromMap('manErrNFCod',helpMap,'C&oacute;digo'),
        				tooltip:getToolTipFromMap('manErrNFCod',helpMap,'C&oacute;digo de mantenimiento de errores'), 
        				hasHelpIcon:getHelpIconFromMap('manErrNFCod',helpMap),
		                 Ayuda: getHelpTextFromMap('manErrNFCod',helpMap,''),        				
            			anchor: '100%',
            			maxlength: 6,
            			id: 'codigoError'
            		}),
            		new Ext.form.TextField({
				        fieldLabel: getLabelFromMap('manErrNFMsg',helpMap,'Mensaje'),
        				tooltip:getToolTipFromMap('manErrNFMsg',helpMap,'Mensaje de mantenimiento de errores'), 
        				hasHelpIcon:getHelpIconFromMap('manErrNFMsg',helpMap),
		                 Ayuda: getHelpTextFromMap('manErrNFMsg',helpMap,''),        			
            			anchor: '100%',
            			id: 'mensajeError'
            		})
            		]
            		},{
						columnWidth:.4,
              			layout: 'form'
                				
            	}]
            		//buttonAlign: 'center',
            }],
            		buttons: [
            					{
				                 text:getLabelFromMap('manErrBtnSeek',helpMap,'Buscar'),
				                 tooltip: getToolTipFromMap('manErrBtnSeek',helpMap,'Busca en mantenimiento de errores'),
            					 handler: function () {
   														if (el_form.form.isValid()) {
   															if (grilla != null) {
   																dsGrilla.removeAll;
   																reloadGrid();
   															}else {   																
   																createGrid();
   																dsGrilla.removeAll;
   															}
   														}
            										}
            					},
            					{
				                 text:getLabelFromMap('manErrBtnCanc',helpMap,'Cancelar'),
				                 tooltip: getToolTipFromMap('manErrBtnCanc',helpMap,'Cancela operaci&oacute;n en mantenimiento de errores'),
     	       					 handler: function() {el_form.getForm().reset();}}
            				]
            	}]
       }]     				

            //se definen los campos del formulario
	});
	/********* Fin del form ************************************/
	
	
	/********* Fin del grid **********************************/
	
	el_form.render();
	grilla.render();

/*	function getSelectedCodigo(){
              var m = grilla.getSelections();
              var jsonData = "";
              for (var i = 0, len = m.length;i < len; i++) {
                var ss = m[i].get("cdError");
                if (i == 0) {
                jsonData = jsonData + ss;
                } else {
                  jsonData = jsonData + "," + ss;
               }
              }
              return jsonData;
    }
	function getSelectedRecord(){
            var m = grilla.getSelections();
            if (m.length == 1 ) {
               return m[0];
            }
    }
*/	function callbackGrabar (_success, _errorMessages) {
		if (_success) {
			Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400027', helpMap,'Guardando datos...'));
		} else {
			Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _errorMessages);
		}
	}
});

function reloadGrid(){
	var params = {
					cdError: Ext.getCmp('el_form').form.findField('codigoError').getValue(),
					dsMensaje:  Ext.getCmp('el_form').form.findField('mensajeError').getValue()
				}
	reloadComponentStore(Ext.getCmp('grilla'), params, myCallback);
}
function myCallback(_rec, _opt, _success, _store) {
	if (!_success) {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,getLabelFromMap('400010', helpMap,'Error')), _store.reader.jsonData.actionErrors[0]);
		_store.removeAll();
	}
}
