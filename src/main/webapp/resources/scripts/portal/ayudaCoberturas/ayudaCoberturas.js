Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//Combo de Eleccion del Idioma
var desIdioma = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_IDIOMAS_AYUDA}),
        reader: new Ext.data.JsonReader({
        root: 'idiomasComboBox',
        id: 'codigo'
        },[
       {name: 'codigo', type: 'string', mapping:'langCode'},
       {name: 'descripcion', type: 'string', mapping:'langName'}
    ])
});


    var desUnieco = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('ayCobTxtAseg',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('ayCobTxtAseg',helpMap,'Aseguradora para ayuda coberturas'),
        hasHelpIcon:getHelpIconFromMap('ayCobTxtAseg',helpMap),
		Ayuda: getHelpTextFromMap('ayCobTxtAseg',helpMap, ''),
        
        align:'right',
        name: 'desUnieco',
        width: 120,
        id: 'desUniecoId'
    });

    var desTipram = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('ayCobTxtRamo',helpMap,'Ramo'),
        tooltip: getToolTipFromMap('ayCobTxtRamo',helpMap,'Ramo para ayuda de coberturas'),
        hasHelpIcon:getHelpIconFromMap('ayCobTxtRamo',helpMap),
		Ayuda: getHelpTextFromMap('ayCobTxtRamo',helpMap,''),
        
        name: 'desTipram', 
        width: 120,
        id: 'desTipramId'
    });

    var desSubram = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('ayCobTxtSRamo',helpMap,'SubRamo'),
        tooltip: getToolTipFromMap('ayCobTxtSRamo',helpMap,'SubRamo para ayuda de coberturas'),
        hasHelpIcon:getHelpIconFromMap('ayCobTxtSRamo',helpMap),
		Ayuda: getHelpTextFromMap('ayCobTxtSRamo',helpMap,''),
        
        name: 'desSubram',
        width: 120,
        id: 'desSubramId'
    });

    var desRamo = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('ayCobTxtProd',helpMap,'Producto'),
        tooltip: getToolTipFromMap('ayCobTxtProd',helpMap,'Producto para ayuda de coberturas'),
        hasHelpIcon:getHelpIconFromMap('ayCobTxtProd',helpMap),
		Ayuda: getHelpTextFromMap('ayCobTxtProd',helpMap,''),
        
        name: 'desRamo',
        width: 120,
        id: 'desRamoId'
    });

    var desGarant = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('ayCobTxtCob',helpMap,'Cobertura'),
        tooltip: getToolTipFromMap('ayCobTxtCob',helpMap,'Cobertura para ayuda de coberturas'),
        hasHelpIcon:getHelpIconFromMap('ayCobTxtCob',helpMap),
		Ayuda: getHelpTextFromMap('ayCobTxtCob',helpMap,''),
        
        name: 'desGarant',
        width: 120,
        id: 'desGarantId'
    });

var incisosForm = new Ext.FormPanel({
		id: 'incisosForm',
        renderTo:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('incisosFormId',helpMap,'Ayuda de coberturas')+'</span>',
        width : 500,
        labelAlign:'right',
        autoHeight: true,
        bodyStyle:'background: white',
        waitMsgTarget : true,
        frame: true,
	    items:[{//Items que encierra todo
			   layout: 'form',
			   border:'false',  
			   items:[{//Items de rotulo
					baseCls: '',
					title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">B&uacute;squeda</span><br>',
					items:[{//Items del layout 
						border: false,     			
						baseCls: '',
						layoutConfig: {columns: 2, align: 'left'},
						layout: 'column',
						defaults: {labelWidth: 90}, 
				        items:[//Items de los campos
				            {
				             layout:'form',
				             columnWidth: .50,
				             align:'right',
				             items:[
				                      desUnieco
				                    ]
				             },
				             {
				              layout: 'form',
				              columnWidth: .50, 
				              items: [
				                    desTipram
				                    ]
				   			},
				   			{
				              layout: 'form',
				              columnWidth: .50, 
				              items: [
				                        desSubram
				                      ]
				   			},
				   			{
				              layout: 'form',
				              columnWidth: .50, 
				              items: [
				                      desRamo
				                      ]
				   			},
				   			{
				              layout: 'form',
				              columnWidth: .50, 
				              items: [
				                      desGarant
				                      ]
				   			},
				   			{
				              // este es obligatorio (que siempre lleve un VALOR)
				              layout: 'form',
				              columnWidth: .50, 
				              items: [
				                      {
				                      xtype: 'combo',
				            		  tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                      store: desIdioma,
				                      forceSelection:true,
				                      displayField:'descripcion',
				                      valueField:'codigo',
				                      hiddenName: 'langCode',
				                      typeAhead: true,
				                      mode: 'local',
				                      triggerAction: 'all',
				                      fieldLabel: getLabelFromMap('ayCobCmbIdioma',helpMap,'Idioma'),
				                      tooltip: getToolTipFromMap('ayCobCmbIdioma',helpMap,'Seleccione un idioma'),
				                      hasHelpIcon:getHelpIconFromMap('ayCobCmbIdioma',helpMap),
		                              Ayuda: getHelpTextFromMap('ayCobCmbIdioma',helpMap,''),				                      
				                      width: 120 ,
				                      emptyText:'Seleccione el Idioma...',
				                      selectOnFocus:true, 
				                      id: 'langCodeF'
				                      }
				            		  ]
				   			},{
				   			html: '<span class="x-form-item" style="font-weight:bold"></span><br>'
				   			}
		   			],
		            buttonAlign: 'center',
		            buttons:[{                     
		                      text: getLabelFromMap('ayCobBtnBuscar',helpMap,'Buscar'),
		                      tooltip: getToolTipFromMap('ayCobBtnBuscar',helpMap,'Busca ayudas de coberturas'),
		                     handler: function() {
		                         if (incisosForm.form.isValid()) {
		                           /*if (incisosForm.findById('langCodeF').getValue())
		                           {*/
		                            if (grid2!=null) {
								        var parametros = {dsUnieco: desUnieco.getValue(),
								        				dsSubram: desSubram.getValue(),
								        				dsGarant: desGarant.getValue(),
								        				dsTipram: desTipram.getValue(), 
								        				dsRamo: desRamo.getValue(),
								        				langCode: incisosForm.findById('langCodeF').getValue()};
								        
		                                  reloadGrid(grid2, parametros, cbkReload);
		                            } else {
		                                  createGrid();
		                            }
		                            /*}else{
		                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400036', helpMap,'Debe seleccionar un Idioma!'));
		                            }*/
		                         
		                         } else{
		                              Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
		                         }
		                     	}	
		                         },
		                         {                     
			                      text: getLabelFromMap('ayCobBtnCancel',helpMap,'Cancelar'),
			                      tooltip: getToolTipFromMap('ayCobBtnCancel',helpMap,'Cancela la operaci&oacute;n de b&uacute;squeda'), 
			                      handler: function() {incisosForm.form.reset();}
		                          }]
        }]
		}]
		}]
});


// Definicion de las columnas de la grilla
var cm = new Ext.grid.ColumnModel([
        {
        header: getLabelFromMap('ayCobCmAseg',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('ayCobCmAseg',helpMap,'Aseguradora'),
        hasHelpIcon:getHelpIconFromMap('ayCobCmAseg',helpMap),
		Ayuda: getHelpTextFromMap('ayCobCmAseg',helpMap,''),	        
        dataIndex: 'dsUnieco',
        sortable: true,
        width: 120
        },
        {
        header: getLabelFromMap('ayCobCmRamo',helpMap,'Ramo'),
        tooltip: getToolTipFromMap('ayCobCmRamo',helpMap,'Ramo'),
        hasHelpIcon:getHelpIconFromMap('ayCobCmRamo',helpMap),
		Ayuda: getHelpTextFromMap('ayCobCmRamo',helpMap,''),	
        dataIndex: 'dsTipram',
        sortable: true,
        width: 100
        },
        {
        header: getLabelFromMap('ayCobCmSRamo',helpMap,'Sub-Ramo'),
        tooltip: getToolTipFromMap('ayCobCmSRamo',helpMap,'Sub-Ramo'),
        hasHelpIcon:getHelpIconFromMap('ayCobCmSRamo',helpMap),
		Ayuda: getHelpTextFromMap('ayCobCmSRamo',helpMap,''),	
        dataIndex: 'dsSubram',
        sortable: true,
        width: 100
        },
        {
        header: getLabelFromMap('ayCobCmProd',helpMap,'Producto'),
        tooltip: getToolTipFromMap('ayCobCmProd',helpMap,'Producto'),
        hasHelpIcon:getHelpIconFromMap('ayCobCmProd',helpMap),
		Ayuda: getHelpTextFromMap('ayCobCmProd',helpMap,''),	
        sortable: true,
        dataIndex: 'dsRamo',
        width: 120
        },
        {
        header: getLabelFromMap('ayCobCmCob',helpMap,'Cobertura'),
        tooltip: getToolTipFromMap('ayCobCmCob',helpMap,'Cobertura'),
        hasHelpIcon:getHelpIconFromMap('ayCobCmCob',helpMap),
		Ayuda: getHelpTextFromMap('ayCobCmCob',helpMap,''),	
        sortable: true,
        dataIndex: 'dsGarant',
        width: 120
        },
        {
        //Cuando se solucione el tema del idioma (issue ACW-617) guardar elemento en db tobjetoconfigura
        header: getLabelFromMap('ayCobCmIdioma',helpMap,'Idioma'),
        tooltip: getToolTipFromMap('ayCobCmIdioma',helpMap,'Idioma'),
        hasHelpIcon:getHelpIconFromMap('ayCobCmIdioma',helpMap),
		Ayuda: getHelpTextFromMap('ayCobCmIdioma',helpMap,''),	
        sortable: true,
        dataIndex: 'langName',
        width: 120
        },
        {
        dataIndex: 'cdGarantiaxCia',
        hidden :true
        },
        {
        dataIndex: 'cdUnieco',
        hidden :true
        },
        {
        dataIndex: 'cdSubram',
        hidden :true
        },
        {
        dataIndex: 'cdGarant',
        hidden :true
        },
        {
        dataIndex: 'cdTipram',
        hidden :true
        },
        {
        dataIndex: 'langCode',
        hidden :true
        },
        {
        dataIndex: 'cdRamo',
        hidden :true
        }
]);


function test(){
          store = new Ext.data.Store({
          proxy: new Ext.data.HttpProxy({
          url: _ACTION_BUSCAR_AYUDA_COBERTURA,
          //waitTitle: getLabelFromMap('400021', helpMap,'Espere...'),
          waitMsg: getLabelFromMap('400017',helpMap,'Espere por favor...')
                }),
                reader: new Ext.data.JsonReader({
            	root:'MEstructuraList',
            	totalProperty: 'totalCount',
                successProperty : '@success'
         },[
            {name: 'dsUnieco',  type: 'string',  mapping:'dsUnieco'},
            {name: 'dsSubram',  type: 'string',  mapping:'dsSubram'},
            {name: 'dsGarant',  type: 'string',  mapping:'dsGarant'},
            {name: 'dsTipram',  type: 'string',  mapping:'dsTipram'},
            {name: 'dsRamo',  type: 'string',  mapping:'dsProducto'},
            {name: 'cdGarantiaxCia',  type: 'string',  mapping:'cdGarantiaxCia'},
            {name: 'cdUnieco',  type: 'string',  mapping:'cdUnieco'},
            {name: 'cdSubram',  type: 'string',  mapping:'cdSubram'},
            {name: 'cdTipram',  type: 'string',  mapping:'cdTipram'},
            {name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
            {name: 'cdGarant',  type: 'string',  mapping:'cdGarant'},
            {name: 'langCode', type: 'string', mapping:'langCode'},
            {name: 'langName', type: 'string', mapping:'langName'}
            
      ])
           
        });
     return store;
}


var grid2;

function createGrid(){
       grid2= new Ext.grid.GridPanel({
       		id: 'grid2',
            renderTo:'gridElementos',
            title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
            store:test(),
            border:true,
            width: 500,
            cm: cm,
            buttonAlign:'center',
            loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
            ctCls: 'grid-panel',
            collapsible: true,
            stripeRows: true,
            buttons:[
                  {
                  text:getLabelFromMap('ayCobBtnAdd',helpMap,'Agregar'),
                  tooltip:getToolTipFromMap('ayCobBtnAdd',helpMap,'Agrega una nueva ayuda de cobertura'),
                  handler:function(){agregar();}
                  },
                  {
                  text:getLabelFromMap('ayCobBtnEdit',helpMap,'Editar'),
                  tooltip:getToolTipFromMap('ayCobBtnEdit',helpMap,'Edita una ayuda de cobertura seleccionada'),
                  handler:function(){
                        if (getSelectedKey(grid2, "cdGarantiaxCia") != "") {
                            editar(getSelectedKey(grid2, "cdGarantiaxCia"));
                        } else {
                          Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                        }
                    }
                  },
                  { 
                  text:getLabelFromMap('ayCobBtnDel',helpMap,'Eliminar'),
                  tooltip:getToolTipFromMap('ayCobBtnDel',helpMap, 'Elimina una ayuda de cobertura seleccionada'),
                    handler:function(){		
                      if (getSelectedKey(grid2, "cdGarantiaxCia") != "")
                      {
                        borrar(getSelectedKey(grid2, "cdGarantiaxCia"));
                      }else {
                              Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                            }
                   	}
                  },
                  
                  {                  
                                   
                  text:getLabelFromMap('ayCobBtnCopy',helpMap,'Copiar'),
                  tooltip:getToolTipFromMap('ayCobBtnCopy',helpMap,'Copia una ayuda de cobertura seleccionada'),
                  handler:function(){
                    if (getSelectedKey(grid2, "cdGarantiaxCia") != "")
                    {
                      copiar(getSelectedKey(grid2, "cdGarantiaxCia"));
                    }else {
                             Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                          }
                   }

                  },
                  {                  
                  text:getLabelFromMap('ayCobBtnEx',helpMap,'Exportar'),
                  tooltip:getToolTipFromMap('ayCobBtnEx',helpMap,'Exporta el listado de ayudas a un formato determinado'),
                    handler:function(){
                      var nada='';
                        /*var url = _ACTION_EXPORTAR_AYUDA_COBERTURA + '?dsUnieco=' + desUnieco.getValue() + '&dsSubram=' + desSubram.getValue()+ '&dsGarant=' + desGarant.getValue() + '&dsTipram=' + desTipram + '&dsRamo=' + desRamo +'&langCode='+Ext.getCmp('langCodeF').getValue();*/
                        var url = _ACTION_EXPORTAR_AYUDA_COBERTURA + '?dsUnieco=' +Ext.getCmp('desUniecoId').getValue() + '&dsSubram=' + Ext.getCmp('desSubramId').getValue() +'&dsGarant=' + Ext.getCmp('desGarantId').getValue() + '&dsTipram=' + Ext.getCmp('desTipramId').getValue() + '&dsRamo=' + '' +'&langCode='+Ext.getCmp('langCodeF').getValue();

                	 	showExportDialog( url );
                        }
                   }],
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
            bbar: new Ext.PagingToolbar({
               pageSize:itemsPerPage,
               store: store,
               displayInfo: true,
               displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
            })
        });
   grid2.render()
}


//Muestra los componentes en pantalla
desIdioma.load();
incisosForm.render();
createGrid();



var store;



function getSelectedCodigo(){
         var m = grid2.getSelections();
         var jsonData = "";
         for (var i = 0, len = m.length;i < len; i++) {
           var ss = m[i].get("cdGarantiaxCia");
           if (i == 0) {
           jsonData = jsonData + ss;
           } else {
             jsonData = jsonData + "," + ss;
          }
         }
         
         return jsonData;
}


function borrar (key){
    var conn = new Ext.data.Connection();
    Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'),function(btn)
    {
     if (btn == "yes"){
     	var params = {cdGarantiaxCia: key};
     	execConnection(_ACTION_BORRAR_AYUDA_COBERTURA, params, cbkBorrar);
     }
    })
 
};

function cbkBorrar(_success, _messages) {
	if (_success) {
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _messages, function() {
		        var _params = {dsUnieco: desUnieco.getValue(),
		        				dsSubram: desSubram.getValue(),
		        				dsGarant: desGarant.getValue(),
		        				dsTipram: desTipram.getValue(), 
		        				dsRamo: desRamo.getValue(),
		        				langCode: incisosForm.findById('langCodeF').getValue()};
				reloadGrid(grid2);
		});
	}else {
		Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _messages);
	}
}
});

function reloadGrid(grilla){
		var _params = {
        	dsUnieco: Ext.getCmp('incisosForm').form.findField('desUnieco').getValue(),
        	dsSubram: Ext.getCmp('incisosForm').form.findField('desSubram').getValue(),
        	dsGarant: Ext.getCmp('incisosForm').form.findField('desGarant').getValue(),
        	dsTipram: Ext.getCmp('incisosForm').form.findField('desTipram').getValue(), 
        	dsRamo: Ext.getCmp('incisosForm').form.findField('desRamo').getValue(),
        	langCode: Ext.getCmp('incisosForm').form.findField('langCodeF').getValue()
		};
		reloadComponentStore(grilla, _params, cbkReload);
		
}
function cbkReload (_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
		Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}