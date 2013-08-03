Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";

//Combo de Eleccion del cliente
var desCombcliente = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: ''}),
        reader: new Ext.data.JsonReader({
        root: 'copiaclienteComboBox',
        id: 'codigo'                                           // cambiar datos
        },[
            {name: 'codigo', type: 'string', mapping:'langCode'},
            {name: 'descripcion', type: 'string', mapping:'langName'}
          ])
});

//Combo de Eleccion del Aseguradora
var desCombAsegura = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: ''}),
        reader: new Ext.data.JsonReader({
        root: 'copiaaseguradoraComboBox',
        id: 'codigo'                                          // cambiar datos
        },[
            {name: 'codigo', type: 'string', mapping:'langCode'},
            {name: 'descripcion', type: 'string', mapping:'langName'}
         ])
});

//Combo de Eleccion del Producto
var desCombProducto = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({url: ''}),
        reader: new Ext.data.JsonReader({
        root: 'copiaproductoComboBox',
        id: 'codigo'                                       // cambiar datos
        },[
           {name: 'codigo', type: 'string', mapping:'langCode'},
           {name: 'descripcion', type: 'string', mapping:'langName'}
           ])
});



    var desPersona = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('CopySegmenClienteId',helpMap,'Cliente'),
        tooltip: getToolTipFromMap('CopySegmenClienteId',helpMap,'Cliente'),
        hasHelpIcon:getHelpIconFromMap('CopySegmenClienteId',helpMap),
		Ayuda: getHelpTextFromMap('CopySegmenClienteId',helpMap,''),
        align:'right',
        name: 'desUnieco',            //       modificar
        width: 120,
        id: 'CopySegmenClienteId'
    });

    var desAsegura = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('CopySegmenAseguradoraId',helpMap,'Aseguradora'),
        tooltip: getToolTipFromMap('CopySegmenAseguradoraId',helpMap,'Aseguradora'),
        hasHelpIcon:getHelpIconFromMap('CopySegmenAseguradoraId',helpMap),
		Ayuda: getHelpTextFromMap('CopySegmenAseguradoraId',helpMap,''),
        name: 'desTipram',          //        modificar
        width: 120,
        id: 'CopySegmenAseguradoraId'
    });

    var desProducto = new Ext.form.TextField({
        fieldLabel: getLabelFromMap('CopySegmenProductoId',helpMap,'Producto'),
        tooltip: getToolTipFromMap('CopySegmenProductoId',helpMap,'Producto'),
        hasHelpIcon:getHelpIconFromMap('CopySegmenProductoId',helpMap),
		Ayuda: getHelpTextFromMap('CopySegmenProductoId',helpMap,''),
        name: 'desSubram',                //  modificar
        width: 120,
        id: 'CopySegmenProductoId'
    });

  /*  var desRamo = new Ext.form.TextField({
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
    });*/

var incisosForm = new Ext.FormPanel({
		id: 'FormSegmen',
        renderTo:'formBusqueda',
        title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('FormSegmen',helpMap,'Copiar Segmentaci&oacute;n')+'</span>',
        width : 500,
        labelAlign:'right',
        //autoHeight: true,
        height:250,
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
				//		baseCls: '',
						layoutConfig: {columns: 2, align:'left'},
						layout: 'column',
						defaults: {labelWidth: 90}, 
				        items:[//Items de los campos
				           // {  
            		      //    html: '<table width="100%" border="0"> <tr> <td width="50%" align="center">Copiar  de </td> <td width="45%" align="center">  A  </td> </tr> </table>'
            		        //  colspan:3,
                            // xtype: 'box',
                            // autoEl: {cn: 'prueba'}
				            // },
				             {
				              layout:'form',
				              columnWidth: .50,
				              align:'right',
				              items:[ desPersona ]
				             },
				   			 {
				               layout: 'form',
				               columnWidth: .50, 
				               items: [
				                      {
				                      xtype: 'combo',
				            		  tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                      store: desCombcliente,
				                      forceSelection:true,
				                      displayField:'descripcion',
				                      valueField:'codigo',
				                      hiddenName: 'langCode',
				                      typeAhead: true,
				                      mode: 'local',
				                      triggerAction: 'all',
				                      fieldLabel: getLabelFromMap('CopySegmenCmbCienteId',helpMap,''), //cli
				                      tooltip: getToolTipFromMap('CopySegmenCmbCienteId',helpMap,''),
				                      hasHelpIcon:getHelpIconFromMap('CopySegmenCmbCienteId',helpMap),
		                              Ayuda: getHelpTextFromMap('CopySegmenCmbCienteId',helpMap),				                      
				                      width: 120 ,
				                      emptyText:'Seleccione...',
				                      selectOnFocus:true, 
				                      id: 'CopySegmenCmbCienteId'
				                      }
				            		  ]
				   			},
			                {
				              layout: 'form',
				              columnWidth: .50, 
				              items: [ desAsegura ]
				   			},
				   			{
				               layout: 'form',
				               columnWidth: .50, 
				               items: [
				                       {
				                        xtype: 'combo',
				            		    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                        store: desCombcliente,
				                        forceSelection:true,
				                        displayField:'descripcion',
				                        valueField:'codigo',
				                        hiddenName: 'langCode',
				                        typeAhead: true,
				                        mode: 'local',
				                        triggerAction: 'all',
				                        fieldLabel: getLabelFromMap('CopySegmenCmbAseguraId',helpMap),//Aseg
				                        tooltip: getToolTipFromMap('CopySegmenCmbAseguraId',helpMap,''),
				                        hasHelpIcon:getHelpIconFromMap('CopySegmenCmbAseguraId',helpMap),
		                                Ayuda: getHelpTextFromMap('CopySegmenCmbAseguraId',helpMap),				                      
				                        width: 120 ,
				                        emptyText:'Seleccione...',
				                        selectOnFocus:true, 
				                        id: 'CopySegmenCmbAseguraId'
				                       }
				            		  ]
				   			},
				   			{
				              layout: 'form',
				              columnWidth: .50, 
				              items: [desProducto ]
				   			},
				   			{
				               layout: 'form',
				               columnWidth: .50, 
				               items: [
				                       {
				                        xtype: 'combo',
				            		    tpl: '<tpl for="."><div ext:qtip="{codigo}.{descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
				                        store: desCombcliente,
				                        forceSelection:true,
				                        displayField:'descripcion',
				                        valueField:'codigo',
				                        hiddenName: 'langCode',
				                        typeAhead: true,
				                        mode: 'local',
				                        triggerAction: 'all',
				                        fieldLabel: getLabelFromMap('CopySegmenCmbProductoId',helpMap,''), //Prod
				                        tooltip: getToolTipFromMap('CopySegmenCmbProductoId',helpMap,''),
				                        hasHelpIcon:getHelpIconFromMap('CopySegmenCmbProductoId',helpMap),
		                                Ayuda: getHelpTextFromMap('CopySegmenCmbProductoId',helpMap),				                      
				                        width: 120 ,
				                        emptyText:'Seleccione...',
				                        selectOnFocus:true, 
				                        id: 'CopySegmenCmbProductoId'
				                       }
				            		  ]
				   			}
				   			
				   			/*
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
				   			},
				   			{
				   			html: '<span class="x-form-item" style="font-weight:bold"></span><br>'
				   			}*/
		   			],
		            buttonAlign: 'center',
		            buttons:[{                     
		                      text: getLabelFromMap('CopySegmenBtncopiarId',helpMap,'Copiar'),
		                      tooltip: getToolTipFromMap('CopySegmenBtncopiarId',helpMap,'Copiar'),
		                      handler: function() {
		                       //  if (incisosForm.form.isValid()) {
		                           /*if (incisosForm.findById('langCodeF').getValue())
		                           {*/
		                         /*   if (grid2!=null) {
								        var parametros = {dsUnieco: desUnieco.getValue(),
								        				dsSubram: desSubram.getValue(),
								        				dsGarant: desGarant.getValue(),
								        				dsTipram: desTipram.getValue(), 
								        				dsRamo: desRamo.getValue(),
								        				langCode: incisosForm.findById('langCodeF').getValue()};
								        
		                                  reloadGrid(grid2, parametros, cbkReload);
		                            } else {
		                                  createGrid();
		                            }*/
		                            /*}else{
		                                Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400036', helpMap,'Debe seleccionar un Idioma!'));
		                            }*/
		                         
		                       /*  } else{
		                              Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
		                         }*/
		                     	}	
		                         },
		                         {                     
			                      text: getLabelFromMap('CopySegmenBtnRegresarId',helpMap,'Regresar'),
			                      tooltip: getToolTipFromMap('CopySegmenBtnRegresarId',helpMap,'Regresar'), 
			                      handler: function() {incisosForm.form.reset();}
		                          }]
        }]
		}]
		}]
});


// Definicion de las columnas de la grilla
/*var cm = new Ext.grid.ColumnModel([
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

*/
/*function test(){
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
}*/


var grid2;
/*
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
                  text:getLabelFromMap('ayCobBtnDel',helpMap,'Borrar'),
                  tooltip:getToolTipFromMap('ayCobBtnDel',helpMap, 'Borra una ayuda de cobertura seleccionada'),
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
                      var nada='';*/
                       //var url = _ACTION_EXPORTAR_AYUDA_COBERTURA + '?dsUnieco=' + desUnieco.getValue() + '&dsSubram=' + desSubram.getValue()+ '&dsGarant=' + desGarant.getValue() + '&dsTipram=' + desTipram + '&dsRamo=' + desRamo +'&langCode='+Ext.getCmp('langCodeF').getValue();*/
                      /*  var url = _ACTION_EXPORTAR_AYUDA_COBERTURA + '?dsUnieco=' +Ext.getCmp('desUniecoId').getValue() + '&dsSubram=' + Ext.getCmp('desSubramId').getValue() +'&dsGarant=' + Ext.getCmp('desGarantId').getValue() + '&dsTipram=' + Ext.getCmp('desTipramId').getValue() + '&dsRamo=' + '' +'&langCode='+Ext.getCmp('langCodeF').getValue();

                	 	showExportDialog( url );
                        }
                   }],
            frame:true,
            height:320,
            sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
            bbar: new Ext.PagingToolbar({
               pageSize:20,
               store: store,
               displayInfo: true,
               displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
            })
        });
   grid2.render()
}*/


//Muestra los componentes en pantalla

 desCombcliente.load();
 desCombAsegura.load();
 desCombProducto.load();

desIdioma.load();
incisosForm.render();
createGrid();



var store;


/*
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
}*/

/*
function borrar (key){
    var conn = new Ext.data.Connection();
    Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se borrar&aacute; el registro seleccionado'),function(btn)
    {
     if (btn == "yes"){
     	var params = {cdGarantiaxCia: key};
     	execConnection(_ACTION_BORRAR_AYUDA_COBERTURA, params, cbkBorrar);
     }
    })
 
};*/
/*
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
}*/
});


               /********************     Fin  Principal       *****************************/


/*
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
		
}*/
/*
function cbkReload (_r, _options, _success, _store) {
	if (!_success) {
		_store.removeAll();*/
		//Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), _store.reader.jsonData.actionErrors[0]);
	/*	Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
	}
}*/