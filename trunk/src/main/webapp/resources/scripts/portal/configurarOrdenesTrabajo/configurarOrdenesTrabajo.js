Ext.onReady(function(){
 Ext.QuickTips.init();
 Ext.QuickTips.enable();
 Ext.form.Field.prototype.msgTarget = "side";
    
    var itemsPerPage=20;
     
//**********************************************************************************************************
//CONFIGURACION ATRIBUTOS FORMATO ORDEN TRABAJO
//***********************************************************************************************************

    //READERS Y STORES DE COMBOS*********************************************************
    //**************readers
         var readerBloquesAtributos = new Ext.data.JsonReader( {
            root : 'comboBloquesConfigAtributosFormatoOrdenTrabajo',
            totalProperty: 'totalCount',
            successProperty : '@success'
         },[
          {name : 'otTipo', mapping : 'otTipo', type : 'string'},
             {name : 'cdBloque',mapping : 'cdBloque',type : 'string'},
             {name : 'dsBloque',mapping : 'dsBloque',type : 'string'}]
     );

  var readerCamposBloques = new Ext.data.JsonReader( {
            root : 'comboCampoBloques',
            totalProperty: 'totalCount',
            successProperty : '@success'
         },[
             {name : 'cdCampo', mapping : 'cdCampo', type : 'string'},
             {name : 'dsCampo',mapping : 'dsCampo',type : 'string'},
             {name : 'tipo', mapping : 'tipo', type : 'string'},
             {name : 'nmMin',mapping : 'nmMin',type : 'string'},
             {name : 'nmMax', mapping : 'nmMax', type : 'string'},
             {name : 'nmCampo',mapping : 'nmCampo',type : 'string'}
           ]
     );
     
     
    var readerFormatosAtributos = new Ext.data.JsonReader( {
            root : 'comboFormatosCampo',
            totalProperty: 'totalCount',
            successProperty : '@success'
         },[
          {name : 'id', mapping : 'codigo', type : 'string'},
          {name : 'texto',mapping : 'descripcion',type : 'string'}]
     );
    
  
 
     
      var readerComboSeccionesFormato = new Ext.data.JsonReader( {
            root : 'comboSeccionFormato',
            totalProperty: 'totalCount',
            successProperty : '@success'
         },[
          {name : 'cdSeccion', mapping : 'cdSeccion', type : 'string'},
             {name : 'dsSeccion',mapping : 'dsSeccion',type : 'string'}]
     );
     
     
    //*************stores
     

    
      var dsComboSeccionesFormato = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_COMBO_SECCIONES_FORMATO
            }),
            reader: readerComboSeccionesFormato
        });  
        
     var dsBloques = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_COMBO_BLOQUES
            }),
            reader: readerBloquesAtributos
        });  
    
  
    
     var dsCamposBloques = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_COMBO_CAMPO_BLOQUES
            }),
            reader: readerCamposBloques
        }); 
        
       var dsFormatos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_COMBO_FORMATO
            }),
            reader: readerFormatosAtributos
        }); 
    
   //*************definiciones combos
    var comboCamposBloques = new Ext.form.ComboBox({tpl: '<tpl for="."><div ext:qtip="{cdCampo}.{dsCampo}" class="x-combo-list-item">{dsCampo}</div></tpl>',
                         store: dsCamposBloques, anchor:'100%', displayField:'dsCampo', valueField: 'cdCampo', hiddenName: 'cdCampo',
                         typeAhead: true, triggerAction: 'all', lazyRender:   true, emptyText:'Seleccione Campo Bloque...', selectOnFocus:true,
                         name: 'cdCampo',id:'cdCampoId', forceSelection: true,labelSeparator:'',
		                 listeners: {
               					expand: function (combo){
	           						combo.store.load({
	       								params: {
	       										 otTipo: "P" ,cdBloque: grillaAtributos.getSelectionModel().getSelected().get("cdBloque")
	       										}
	       								
	       								});
	               					}
			                   }
			                   });
   
    var comboBloques = new Ext.form.ComboBox({tpl: '<tpl for="."><div ext:qtip="{cdBloque}.{dsBloque}" class="x-combo-list-item">{dsBloque}</div></tpl>',
                         store: dsBloques, 
                         anchor:'100%', 
                         displayField:'dsBloque', 
                         valueField: 'cdBloque', 
                         hiddenName: 'cdBloque',
                         typeAhead: true, 
                         triggerAction: 'all', 
                         lazyRender:   true, 
                         emptyText:'Seleccione Bloque...', 
                         selectOnFocus:true,
                         mode:'local',
                         name: 'cdBloque',id:'cdBloqueId',forceSelection: true, labelSeparator:'',
                               onSelect: function (record) {
                               dsCamposBloques.removeAll();
                               dsCamposBloques.load({
                                           params: {otTipo: "P" ,cdBloque: record.get('cdBloque')}
                                     });
                               this.collapse();
                               this.setValue(record.get("cdBloque"));
                               grillaAtributos.getSelectionModel().getSelected().set("cdBloque",record.get("cdBloque"));
                            
                             }
                         
                         }); 
                         

 
    var comboFormatos = new Ext.form.ComboBox({tpl: '<tpl for="."><div ext:qtip="{id}.{texto}" class="x-combo-list-item">{texto}</div></tpl>',
                         store: dsFormatos, anchor:'100%', displayField:'texto', valueField: 'id', hiddenName: 'cdFormato',
                         typeAhead: true, triggerAction: 'all', lazyRender:   true, emptyText:'Seleccione Formato...', selectOnFocus:true,
                         mode:'local',
                         name: 'cdFormato',id:'cdFormatoId',forceSelection: true, labelSeparator:''});
                         
  

    //FIN READERS Y STORES DE COMBOS*********************************************************
    
    //READERS Y STORE DE GRILLA***************************************************************
    var recordAtributosFormato = new Ext.data.Record.create([
          {name : 'cdFormatoOrden', mapping : 'cdFormatoOrden', type : 'string'},
          {name : 'dsFormatoOrden', mapping : 'dsFormatoOrden', type : 'string'},
          {name : 'cdSeccion', mapping : 'cdSeccion', type : 'string'},
          {name : 'dsSeccion', mapping : 'dsSeccion', type : 'string'},
          {name : 'cdAtribu', mapping : 'cdAtribu', type : 'string'},
          {name : 'dsAtribu', mapping : 'dsAtribu', type : 'string'},
          {name : 'cdBloque', mapping : 'cdBloque', type : 'string'},
          {name : 'dsBloque', mapping : 'dsBloque', type : 'string'},
          {name : 'cdCampo', mapping : 'cdCampo', type : 'string'},
          {name : 'dsCampo', mapping : 'dsCampo', type : 'string'},
          {name : 'otTabVal', mapping : 'otTabVal', type : 'string'},
          {name : 'swFormat', mapping : 'swFormat', type : 'string'},
          {name : 'nmlMax', mapping : 'nmlMax', type : 'string'},
          {name : 'nmlMin', mapping : 'nmlMin', type : 'string'},
          {name : 'cdExpres', mapping : 'cdExpres', type : 'string'},
          {name : 'nmOrden', mapping : 'nmOrden', type : 'string'}
           
        ]);
    

  var readerAtributosFormato = new Ext.data.JsonReader( {
              root : 'MConfiguracionAtributoFormatoOrdenTrabajoList',
              totalProperty: 'totalCount',
              successProperty : '@success'
          },
          recordAtributosFormato 
          );

 function crearGridAtributosStore(){  
   	dsAtributosFormato = new Ext.data.Store ({
        proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_FORMATO
        }),
        reader: readerAtributosFormato
      });
      
  return dsAtributosFormato;
 }
    
      var comboSeccionesAtributos = new Ext.form.ComboBox(
               {
             tpl: '<tpl for="."><div ext:qtip="{cdSeccion}.{dsSeccion}" class="x-combo-list-item">{dsSeccion}</div></tpl>',
                         store: dsComboSeccionesFormato,
                         displayField:'dsSeccion',
                         valueField: 'cdSeccion',
                         hiddenName: 'cdSeccion',
                         typeAhead: true,
                         fieldLabel: getLabelFromMap('cotCmbSecc',helpMap,'Secci&oacute;n'),
                         tooltip: getToolTipFromMap('cotCmbSecc',helpMap,'Seleccione Secci&oacute;n'),
                         //hasHelpIcon:getHelpIconFromMap('cotCmbSecc',helpMap),
		                // Ayuda: getHelpTextFromMap('cotCmbSecc',helpMap,'g'),                         
                         triggerAction: 'all',
                         lazyRender:true,
                         width: 180,
                         emptyText:'Seleccione Seccion...',
                         selectOnFocus:true,
                         name: 'codigoSeccion',
                         id:'cdSeccionId',
                         forceSelection: true,
                         onSelect: function(record){
                                this.setValue(record.get('cdSeccion'))
                                reloadGridAtributo();
                                this.collapse();
                         },
                            listeners: {
               					expand: function (combo){
	           						combo.store.load({
	       								params: {
	       										 cdFormato: CODIGO_FORMATO_ORDEN
	       										}
	       								
	       								});
	               					}
			                   }
                         });
    
    //FIN READERS Y STORE DE GRILLA***************************************************************
    
 var grillaAtributos;
 function createGridAtributos(){
 //Definición Column Model
    var cmAtributos = new Ext.grid.ColumnModel([
      {
      dataIndex: 'cdFormatoOrden',
      hidden: true
      },
      {
      dataIndex: 'cdSeccion',
      hidden: true
      },
      
      {
      dataIndex: 'cdAtribu',
      hidden: true
      },
      {
      dataIndex: 'cdBloque',
      hidden: true
      },
      {
      dataIndex: 'cdCampo',
      hidden: true
      },
      
      {
      dataIndex: 'cdExpres',
      hidden: true
      },
      {
      header: getLabelFromMap('cotCmNomb',helpMap,'Nombre'),
      tooltip: getToolTipFromMap('cotCmNomb',helpMap,'Nombre'),
      hasHelpIcon:getHelpIconFromMap('cotCmNomb',helpMap),
      Ayuda: getHelpTextFromMap('cotCmNomb',helpMap,''),      
      dataIndex: 'dsAtribu',
      width: 100,
      sortable: true,
      editor: new Ext.form.TextField({
             //  allowBlank: false
               }),  
        // funcion que permite colocarle a todas las celdas el rojo de requerido y el icono de requerido
        renderer:function extGrid_renderer(val, cell, record, row, col, store) {
						if (record != undefined) {
						// Make sure we have a value
						if (val == '') {
						// No value so highlight this cell as in an error state
							cell.css += 'x-form-invalid';
							cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
							//cell.attr = 'ext:qclass="x-form-invalid-tip"';
					} else {
						// Value is valid
						cell.css = '';
						cell.attr = 'ext:qtip=""';
					}
					}
	// Return the value so that it is displayed within the grid
				return val;
			}
               
      },
      {
      header: getLabelFromMap('cotCmBloq',helpMap,'Bloque'),
      tooltip: getToolTipFromMap('cotCmBloq',helpMap,'Bloque'),
      hasHelpIcon:getHelpIconFromMap('cotCmBloq',helpMap),
      Ayuda: getHelpTextFromMap('cotCmBloq',helpMap,''),   
      dataIndex: 'dsBloque',
      width: 100,
      sortable: true,
      editor: comboBloques,
      renderer: renderComboEditor(comboBloques)
      },
      {
      header: getLabelFromMap('cotCmCamp',helpMap,'Campo'),
      tooltip: getToolTipFromMap('cotCmCamp',helpMap,'Campo'),
      hasHelpIcon:getHelpIconFromMap('cotCmCamp',helpMap),
      Ayuda: getHelpTextFromMap('cotCmCamp',helpMap,''),   
      dataIndex: 'cdCampo',
      width: 80,
      sortable: true,
      editor: comboCamposBloques,
      renderer: renderComboEditor(comboCamposBloques)
      },
      {
      header: getLabelFromMap('cotCmOrden',helpMap,'Orden'),
      tooltip: getToolTipFromMap('cotCmOrden',helpMap,'Orden'),
      hasHelpIcon:getHelpIconFromMap('cotCmOrden',helpMap),
      Ayuda: getHelpTextFromMap('cotCmOrden',helpMap,''),   
      dataIndex: 'nmOrden',
      width: 45,
      sortable: true,
      editor: new Ext.form.NumberField({
      	      maxValue:99,
               allowBlank: false
               }),
        // funcion que permite colocarle a todas las celdas el rojo de requerido y el icono de requerido
        renderer:function extGrid_renderer(val, cell, record, row, col, store) {
						if (record != undefined) {
						// Make sure we have a value
						if (val == '') {
						// No value so highlight this cell as in an error state
							cell.css += 'x-form-invalid';
							cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
							//cell.attr = 'ext:qclass="x-form-invalid-tip"';
					} else {
						// Value is valid
						cell.css = '';
						cell.attr = 'ext:qtip=""';
					}
					}
	// Return the value so that it is displayed within the grid
				return val;
			}
      },
      {
      header: getLabelFromMap('cotCmForm',helpMap,'Formato'),
      tooltip: getToolTipFromMap('cotCmForm',helpMap,'Formato'),
      hasHelpIcon:getHelpIconFromMap('cotCmForm',helpMap),
      Ayuda: getHelpTextFromMap('cotCmForm',helpMap,''),   
      dataIndex: 'swFormat',
      width: 80,
      sortable: true,
   //   allowBlank: false,
      editor: comboFormatos,
//      renderer: renderComboEditor(comboFormatos)
      
      renderer: function extGrid_renderer(val, cell, record, row, col, store) {
      	
                    if (record != undefined) {

                         // Make sure we have a value
                          if (val == '') {
                           // No value so highlight this cell as in an error state

                             cell.css += 'x-form-invalid';
                             cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';

                             //cell.attr = 'ext:qclass="x-form-invalid-tip"';
                          }else {
                           //Value is valid

                                 cell.css = '';
                                 cell.attr = 'ext:qtip=""';
                                }
                    }

                     var idx;
                     
                     //value = eval(value);
                     for (var i=0; i < comboFormatos.store.data.items.length; i++) {

                     var registro = comboFormatos.store.getAt(i);
                           if (registro) {
                                if (registro.get(comboFormatos.valueField) == val) {
                                          idx = i;
                                  }//else alert("registro: " + registro.get(combo.valueField) + "\nvalue: " + value);
                           } 
                     }
                      //var idx = combo.store.find(combo.valueField, value, 0, true, true);
                      var rec = comboFormatos.store.getAt(idx);
                    //  console.log(comboFormatos.store);
                      return (rec == null)?val:rec.get(comboFormatos.displayField);
                    //  return val;
              }

      },
      {
      header: getLabelFromMap('cotCmMin',helpMap,'M&iacute;n.'),
      tooltip: getToolTipFromMap('cotCmMin',helpMap,'Valor M&iacute;nimo'),
      hasHelpIcon:getHelpIconFromMap('cotCmMin',helpMap),
      Ayuda: getHelpTextFromMap('cotCmMin',helpMap,''),   
      dataIndex: 'nmlMin',
      width: 45,
      sortable: true,
      editor: new Ext.form.NumberField({
               //allowBlank: false
               }),
               
      renderer:function extGrid_renderer(val, cell, record, row, col, store) {
      	
					if (record != undefined) {
						if(!Ext.isEmpty(record.get('otTabVal'))){
							cell.css = '';
							cell.attr = 'ext:qtip=""';
							return val;
						}
						// Make sure we have a value
						if (val == '') {
							// No value so highlight this cell as in an error state
							cell.css += 'x-form-invalid';
							cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
							//cell.attr = 'ext:qclass="x-form-invalid-tip"';
						} else {
							// Value is valid
							cell.css = '';
							cell.attr = 'ext:qtip=""';
						}
					}
	    // Return the value so that it is displayed within the grid
				return val;
			}
      },
      {
      header: getLabelFromMap('cotCmMax',helpMap,'M&aacute;x.'),
      tooltip: getToolTipFromMap('cotCmax',helpMap,'Valor M&aacute;ximo'),
      hasHelpIcon:getHelpIconFromMap('cotCmMax',helpMap),
      Ayuda: getHelpTextFromMap('cotCmMax',helpMap,''),   
      dataIndex: 'nmlMax',
      width: 45,
      sortable: true,
      editor: new Ext.form.NumberField({
               //allowBlank: false,
               id:"nmlMax"
               }),
        // funcion que permite colocarle a todas las celdas el rojo de requerido y el icono de requerido
        renderer:function extGrid_renderer(val, cell, record, row, col, store) {
					if (record != undefined) {
							
							if(!Ext.isEmpty(record.get('otTabVal'))){
								cell.css = '';
								cell.attr = 'ext:qtip=""';
								return val;
							}
						// Make sure we have a value
						if (val == '') {
							// No value so highlight this cell as in an error state
							cell.css += 'x-form-invalid';
							cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
							//cell.attr = 'ext:qclass="x-form-invalid-tip"';
						} else {
							// Value is valid
							cell.css = '';
							cell.attr = 'ext:qtip=""';
						}
					}
	// Return the value so that it is displayed within the grid
				return val;
			}
               
      },
      {
      header: getLabelFromMap('cotCmLVal',helpMap,'Lista de valores'),
      tooltip: getToolTipFromMap('cotCmLVal',helpMap,'Lista de valores'),
      dataIndex: 'otTabVal',
      width: 85,
      sortable: true,
      editor: new Ext.form.TextField({
               allowBlank: false
               }),
               
        // funcion que permite colocarle a todas las celdas el rojo de requerido y el icono de requerido
        renderer:function extGrid_renderer(val, cell, record, row, col, store) {
						if (record != undefined) {
						// Make sure we have a value
							
							if(!Ext.isEmpty(record.get('nmlMax')) && !Ext.isEmpty(record.get('nmlMin'))){
								cell.css = '';
								cell.attr = 'ext:qtip=""';
								return val;
							}
						if (val == '') {
						// No value so highlight this cell as in an error state
							cell.css += 'x-form-invalid';
							cell.attr = 'ext:qtip="Este campo es requerido"; ext:qclass="x-form-invalid-tip"';
							//cell.attr = 'ext:qclass="x-form-invalid-tip"';
					} else {
						// Value is valid
						cell.css = '';
						cell.attr = 'ext:qtip=""';
					}
					}
	// Return the value so that it is displayed within the grid
				return val;
			}
               
      }
      ]);
 
 grillaAtributos = new Ext.grid.EditorGridPanel({
         el:'gridAtributos',
         store: crearGridAtributosStore(),
		 border:true,
         clicksToEdit:1,
         buttonAlign:'center',
         loadMask: {msg: getLabelFromMap('400070',helpMap,'Cargando datos ...'), disabled: false},
         title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
         cm: cmAtributos,
         plugins:new Ext.ux.plugins.GridValidator(),//plugins para validar la celda en el jsp se debe agregar: <script src="${ctx}/resources/scripts/ValidacionGrilla/Ext.ux.plugins.GridValidator.js" type="text/javascript"></script> 
         buttons:[
              {
	           text:getLabelFromMap('cotBtnAdd',helpMap,'Agregar'),
     		   tooltip: getToolTipFromMap('cotBtnAdd',helpMap,'Agrega configuraci&oacute;n'),
	           handler: function () {
	           							if (formPanel.findById("cdSeccionId").getValue()!=''){
							           	  	    addGridAtributoNewRecord();
					           	  		}else{
					           	  				Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
					           	  		}
					           	  	}
	          },
              {
              text:getLabelFromMap('cotBtnSave',helpMap,'Guardar'),
      		  tooltip: getToolTipFromMap('cotBtnSave',helpMap,'Guarda configuraci&oacute;n'),
      		
      		  handler:function(){
              	cols = grillaAtributos.colModel.getColumnCount(); // determino cantidad de columnas de la grilla
                rows = grillaAtributos.store.getCount();		  // determino cantidad de filas de la grilla
              	
              	var bandValid = 0; // bandera para saber si ya todas las celdas de mi grilla son validas
              	//recorro toda mi grilla preguntando si las celdas son validas
              	for (var i = 0; i < rows; i++) {
              		for (var j = 0; j < cols; j++) {
              		 if(!grillaAtributos.isCellValid(j,i)){
              		   		bandValid =+ 1;
              		   }
              		}
              	}
              	
              	//aseguro de que todas mis celdas son validas
              	if (bandValid != 0){
              		Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400013', helpMap,'Complete la informaci&oacute;n requerida'));
				//	Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe agregar un registro para realizar esta operaci&oacute;n'));
              		
              	}else{
//              		guardarDatosGuionAndDialogo(formWindowEdit)
	            		 guardarDatosAtributo();
              	     }//del else
              }

              /*handler: function () {
             	if (grillaAtributos.store.getModifiedRecords()==0)
					{
					  	  Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe agregar un registro para realizar esta operaci&oacute;n'));
	            	 }else{
	            		 guardarDatosAtributo();
					} 
               }*/
              },
              {
             text:getLabelFromMap('cotBtnDel',helpMap,'Eliminar'),
      		tooltip: getToolTipFromMap('cotBtnDel',helpMap,'Elimina configuracion'),
              handler: function(){
                 if (grillaAtributos.getSelectionModel().getSelections().length > 0) {
                  Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminar&aacute; el registro seleccionado'), 
                     function(btn) {
                       if (btn == "yes") {
                       	if (grillaAtributos.getSelectionModel().getSelected().get("cdAtribu")==""){
                       		grillaAtributos.store.remove(grillaAtributos.getSelectionModel().getSelected());
                       	}else{
                       	    borrarAtributo();
                       	}
                       }
                     }
                  );
                 }else {
                  Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
                 }
                }
              },              
	          {
              text:getLabelFromMap('cotBtnIrVD',helpMap,'Valores por defecto'),
      		tooltip: getToolTipFromMap('cotBtnIrVD',helpMap,'Ir a la pantalla de Valores por defecto'),
              handler: function () {
           		if(comboSeccionesAtributos.getValue()==null || comboSeccionesAtributos.getValue()=="")
           		{Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400044', helpMap,'Debe seleccionar una secci&oacute;n en el combo'));}
           		else if(comboSeccionesAtributos.getValue()!=null || comboSeccionesAtributos.getValue()!="")
           			{if(getSelectedRecord()!= null)
           			  {
						window.location=_ACTION_IR_A_VALORES_POR_DEFECTO+'?cdFormatoOrden='+CODIGO_FORMATO_ORDEN+'&cdSeccion='+comboSeccionesAtributos.getValue()+'&cdAtribu='+getSelectedRecord().get('cdAtribu')+'&dsFormatoOrden='+DESCRIPCION_FORMATO_ORDEN;
					  }
					  else{Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));}
					}
              	}
              },
              {
              text:getLabelFromMap('cotBtnBack',helpMap,'Regresar'),
      		tooltip: getToolTipFromMap('cotBtnBack',helpMap,'Regresa a la pantalla anterior'),
             	handler: function(){window.location.href = _ACTION_REGRESAR;}
              }
              ],
      width:500,
      frame:true,
   height:320,
   sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
   buttonAlign:'center',
   stripeRows: true,
   collapsible: true,
   bbar: new Ext.PagingToolbar({
     pageSize: itemsPerPage,
     store: dsAtributosFormato,
     displayInfo: true,
     displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
	emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
       })
   });
   
   
   grillaAtributos.on("afteredit",function(e){

   	if (e.record.data["nmlMin"]!="" && e.record.data["nmlMax"]!="") {
   			if (eval(e.record.data["nmlMin"]) > eval(e.record.data["nmlMax"])){
   			       Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400045', helpMap,'El valor M&iacute;nimo debe ser menor que el Valor M&aacute;ximo.'));
   			       grillaAtributos.getSelectionModel().getSelected().set('nmlMax', eval(e.record.data["nmlMin"])+1);
   			}
   		}
   }); 
   
 }
 

 function reloadGridAtributo(){
  var _storeDom = grillaAtributos.store;
     var o = {start: 0};
     _storeDom.baseParams = _storeDom.baseParams || {};
     _storeDom.baseParams['cdFormatoOrden'] = CODIGO_FORMATO_ORDEN;
     _storeDom.baseParams['cdSeccion'] = formPanel.findById("cdSeccionId").getValue();
     _storeDom.reload(
               {
                   params:{start:0,limit:itemsPerPage},
                   callback : function(r, options, success) {
                       if (!success) {
                          _storeDom.removeAll();
                       }
                   }
 
               }
             );
 } 
 
 function addGridAtributoNewRecord(){
  var new_record = new recordSeccionesFormato({
               cdFormatoOrden: CODIGO_FORMATO_ORDEN,
               cdSeccion: '',
		       cdAtribu: '',
		       dsAtribu: '',
		       cdBloque: '',
		       cdCampo: '',
		       cdExpres:'',
		       dsFormatoOrden:'',
		       dsBloque:'',
		       dsCampo:'',
		       nmOrden:'',
		       swFormat:'',
		       nmlMin:'',
		       nmlMax:'',
		       nmlCampo:'',
		       otTabVal:''
		      
      });
  grillaAtributos.stopEditing();
  grillaAtributos.store.insert(0, new_record);
  grillaAtributos.startEditing(0, 0);
 }

 function guardarDatosAtributo () {
  var _params = "";
  
  var recs = grillaAtributos.store.getModifiedRecords();
  grillaAtributos.stopEditing();
  for (var i=0; i<recs.length; i++) {
   _params +=  "csoGrillaListAtr[" + i + "].cdFormatoOrden=" + CODIGO_FORMATO_ORDEN + "&" +
      "&csoGrillaListAtr[" + i + "].cdSeccion=" + formPanel.findById("cdSeccionId").getValue() + "&" +
      "&csoGrillaListAtr[" + i + "].cdAtribu=" + recs[i].get('cdAtribu') + "&" +
      "&csoGrillaListAtr[" + i + "].dsAtribu=" + recs[i].get('dsAtribu') + "&" +
      "&csoGrillaListAtr[" + i + "].cdBloque=" + recs[i].get('cdBloque') + "&" +
      "&csoGrillaListAtr[" + i + "].cdCampo=" + recs[i].get('cdCampo') + "&" +
      "&csoGrillaListAtr[" + i + "].nmOrden=" + recs[i].get('nmOrden') + "&" +
      "&csoGrillaListAtr[" + i + "].otTabVal=" + recs[i].get('otTabVal') + "&" +
      "&csoGrillaListAtr[" + i + "].swFormat=" + recs[i].get('swFormat') + "&" +
      "&csoGrillaListAtr[" + i + "].nmlMin=" + recs[i].get('nmlMin') + "&" +
      "&csoGrillaListAtr[" + i + "].nmlMax=" + recs[i].get('nmlMax') + "&" +
      "&csoGrillaListAtr[" + i + "].cdExpres=" + recs[i].get('cdExpres') + "&";
  }
  if (recs.length > 0) {
   var conn = new Ext.data.Connection ();
   conn.request({
     url: _ACTION_GUARDAR__FORMATO,
     params: _params,
     method: 'POST',
     callback: function (options, success, response) {
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Los datos se guardaron con &eacute;xito', function(){reloadGridAtributo();});
          grillaAtributos.store.commitChanges();
         }
       }
   });
  }
 }             
    
 	 
 
 
function borrarAtributo () {
  var recs = grillaAtributos.getSelectionModel().getSelections();
  if (recs.length > 0) {
   var conn = new Ext.data.Connection();
   conn.request ({
      url: _ACTION_ELIMINAR_FORMATO,
      params: {cdFormatoOrden: CODIGO_FORMATO_ORDEN, cdSeccion: formPanel.findById("cdSeccionId").getValue(),cdAtribu:recs[0].get('cdAtribu')},
      method: 'POST',
      callback: function(options, success, response) {
         if (Ext.util.JSON.decode(response.responseText).success == false) {
          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'),Ext.util.JSON.decode(response.responseText).errorMessages[0]);
         } else {
          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Registro eliminado', function() {reloadGridAtributo();});
          
         }
         reloadGridAtributo();
      }
   });
  }else {
   Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
  }
 }
    
 

   
   
    createGridAtributos();
//**********************************************************************************************************
//FIN CONFIGURACION ATRIBUTOS FORMATO ORDEN TRABAJO
//***********************************************************************************************************

 
//************************************************************************************************************************************
// CONFIGURACION SECCIONES FORMATO ORDEN TRABAJO 
//************************************************************************************************************************************
    
    //READERS Y STORES DE COMBOS*********************************************************
    //*************readers
    var readerTipoSituacion = new Ext.data.JsonReader({
            root : 'tiposSituacionComboBox',
            totalProperty: 'totalCount',
            successProperty : '@success'
         },[
          {name : 'codigoTipSit', mapping : 'cdTipSit', type : 'string'},
             {name : 'descripcionTipSit',mapping : 'dsTipSit',type : 'string'}]
     );
    
    var readerTipoObjeto = new Ext.data.JsonReader( {
            root : 'comboTiposObjetos',
            totalProperty: 'totalCount',
            successProperty : '@success'
         },[
          {name : 'codigoTipObj', mapping : 'cdTipObj', type : 'string'},
             {name : 'descripcionTipObj',mapping : 'dsTipObj',type : 'string'}]
     );
    
    var readerSecciones = new Ext.data.JsonReader( {
            root : 'comboSeccionesOrden',
            totalProperty: 'totalCount',
            successProperty : '@success'
         },[
          {name : 'codigoSeccion', mapping : 'cdSeccion', type : 'string'},
             {name : 'descripcionSeccion',mapping : 'dsSeccion',type : 'string'}]
     );
     
      //*************stores
      var dsSecciones = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_COMBO_SECCIONES_ORDEN
            }),
            reader: readerSecciones
        }); 
   
    dsTipoSituacion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_COMBO_TIPOS_SITUACION
            }),
            reader: readerTipoSituacion
        }); 
    
    dsTipoObjeto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_COMBO_TIPOS_OBJETOS
            }),
            reader: readerTipoObjeto
        }); 
    
    //*************definiciones
    var comboSecciones = new Ext.form.ComboBox(
    					{tpl: '<tpl for="."><div ext:qtip="{codigoSeccion}.{descripcionSeccion}" class="x-combo-list-item">{descripcionSeccion}</div></tpl>',
                         store: dsSecciones, 
                         anchor:'100%', 
                         displayField:'descripcionSeccion', 
                         valueField: 'codigoSeccion', 
                         hiddenName: 'cdSeccion',
                         typeAhead: true, 
                         triggerAction: 'all', 
                         lazyRender:   true, 
                         emptyText:'Selecione Seccion...', 
                         selectOnFocus:true,
                         labelSeparator:'',
                         forceSelection: true,
                         id:'comboSeccionesId', 
                         name: 'comboSeccionesId',
                         listeners: {
                         	focus: function () {
                         		if (!this.list) {
                         			this.clearValue();
                         			this.setValue("");
                         			this.setRawValue("");
                         		}
                         		else
                         		{
                         			if (this.getRawValue().indexOf("&nbsp;")){
                         				this.clearValue();
                         				this.setValue("");
                         				this.setRawValue("");
	                         		}
                         		} 
                         	}
                         }
                         });
 
 	var comboTipoSituacion = new Ext.form.ComboBox(
 						{tpl: '<tpl for="."><div ext:qtip="{codigoTipSit}.{descripcionTipSit}" class="x-combo-list-item">{descripcionTipSit}</div></tpl>',
                         store: dsTipoSituacion, 
                         anchor:'100%', 
                         displayField:'descripcionTipSit', 
                         valueField: 'codigoTipSit', 
                         hiddenName: 'cdTipSit',
                         typeAhead: true, 
                         triggerAction: 'all',
                         lazyRender:   true,
                         emptyText:'Selecione Tipo de Situación...',
                         selectOnFocus:true,   
                         labelSeparator:'',
                         forceSelection: true,
                         id:'comboTipoSituacionId', 
                         name:'comboTipoSituacionId',
                         listeners: {
                         	focus: function () {
                         		if (!this.list) {
                         			this.clearValue();
                         			this.setValue("");
                         			this.setRawValue("");
                         		}
                         		else
                         		{
                         			if (this.getRawValue().indexOf("&nbsp;")){
                         				this.clearValue();
                         				this.setValue("");
                         				this.setRawValue("");
	                         		}
                         		} 
                         	}
                         }
                         });

    
    var comboTipoObjeto = new Ext.form.ComboBox(
    					{tpl: '<tpl for="."><div ext:qtip="{codigoTipObj}.{descripcionTipObj}" class="x-combo-list-item">{descripcionTipObj}</div></tpl>',
                         store: dsTipoObjeto, 
                         anchor:'100%', 
                         displayField:'descripcionTipObj', 
                         valueField: 'codigoTipObj', 
                         hiddenName: 'cdTipObj',
                         typeAhead: true, 
                         forceSelection: true,
                         triggerAction: 'all', 
                         lazyRender:   true, 
                         emptyText:'Selecione Corp...', 
                         selectOnFocus:true,
                         labelSeparator:'', 
                         id:'comboTipoObjetoId', 
                         name:'comboTipoObjetoId',
                         listeners: {
                         	focus: function () {
                         		if (!this.list) {
                         			this.clearValue();
                         			this.setValue("");
                         			this.setRawValue("");
                         		}
                         		else
                         		{
                         			if (this.getRawValue().indexOf("&nbsp;")){
                         				this.clearValue();
                         				this.setValue("");
                         				this.setRawValue("");
	                         		}
                         		} 
                         	}
                         }
                         });
    //FIN READERS Y STORES DE COMBOS*********************************************************
    
    //READERS Y STORE DE GRILLA***************************************************************
    var recordSeccionesFormato = new Ext.data.Record.create([
          {name : 'codigoFormatoOrden', mapping : 'cdFormatoOrden', type : 'string'},
          {name : 'descripcionId', mapping : 'dsFormatoOrden', type : 'string'},
          {name : 'codigoSeccion', mapping : 'cdSeccion', type : 'string'},
          {name : 'codigoSeccion2', mapping : 'cdSeccion', type : 'string'},
          {name : 'descripcionSeccion', mapping : 'dsSeccion', type : 'string'},
          {name : 'codigoTipSit', mapping : 'cdTipSit', type : 'string'},
          {name : 'descripcionTipSit', mapping : 'dsTipSit', type : 'string'},
          {name : 'codigoTipObj', mapping : 'cdTipObj', type : 'string'},
          {name : 'descripcionTipObj', mapping : 'dsTipObj', type : 'string'},
          {name : 'numeroOrden', mapping : 'nmOrden', type : 'string'}
        ]);
    
 	function crearGridSeccionesStore(){
  		var readerSeccionesFormato = new Ext.data.JsonReader( {
              root : 'csoEstructuraList',
              totalProperty: 'totalCount',
              successProperty : '@success'
          },
          recordSeccionesFormato 
          );
  
  		var dsSeccionesFormato = new Ext.data.Store ({
	        proxy: new Ext.data.HttpProxy({
	            url: _ACTION_OBTENER_SECCIONES_FORMATO
	        }),
	        reader: readerSeccionesFormato
	    });
      
  		return dsSeccionesFormato;
 	}
    //FIN READERS Y STORE DE GRILLA***************************************************************
    
	 var grillaSecciones;
	 function createGridSecciones(){
	    var cm = new Ext.grid.ColumnModel([
	      {
	      dataIndex: 'codigoFormatoOrden',
	      hidden: true
	      },
	      {
	      dataIndex: 'codigoSeccion',
	      hidden: true
	      },
	      
	      {
	      dataIndex: 'codigoTipSit',
	      hidden: true
	      },
	      {
	      dataIndex: 'codigoSeccion2',
	      hidden: true
	      },
	   	 {
          header: getLabelFromMap('confOTCmNomb',helpMap,'Nombre'),
          tooltip: getToolTipFromMap('confOTCmNomb',helpMap,'Nombre'),
          hasHelpIcon:getHelpIconFromMap('confOTCmNomb',helpMap),
          Ayuda: getHelpTextFromMap('confOTCmNomb',helpMap,''), 
          
          dataIndex: 'codigoSeccion',
          width: 180,
      	  sortable: true,
          editor: comboSecciones,
          renderer: renderComboEditorValida(comboSecciones)
	      },
         {
          header: getLabelFromMap('confOTCmOrd',helpMap,'Orden'),
          tooltip: getToolTipFromMap('confOTCmOrd',helpMap,'Orden'),
          hasHelpIcon:getHelpIconFromMap('confOTCmOrd',helpMap),
          Ayuda: getHelpTextFromMap('confOTCmOrd',helpMap,''), 
          dataIndex: 'numeroOrden',
          width: 50,
      	  sortable: true,
          editor: new Ext.form.NumberField({
               allowBlank: false
               })
         },
         {
          header: getLabelFromMap('confOTCmTipSit',helpMap,'Tipo de Situaci&oacute;n'),
          tooltip: getToolTipFromMap('confOTCmTipSit',helpMap,'Tipo de Situaci&oacute;n'),
          hasHelpIcon:getHelpIconFromMap('confOTCmTipSit',helpMap),
          Ayuda: getHelpTextFromMap('confOTCmTipSit',helpMap,''), 
          dataIndex: 'codigoTipSit',
          width: 125,
      	  sortable: true,
          editor: comboTipoSituacion,
          renderer: renderComboEditor(comboTipoSituacion)
          },
          {
          header: getLabelFromMap('confOTCmTipObj',helpMap,'Tipo de Objeto'),
          tooltip: getToolTipFromMap('confOTCmTipObj',helpMap,'Tipo de Objeto'),
          hasHelpIcon:getHelpIconFromMap('confOTCmTipObj',helpMap),
          Ayuda: getHelpTextFromMap('confOTCmTipObj',helpMap,''), 
          dataIndex: 'codigoTipObj',
          width: 120,
      	  sortable: true,
          editor: comboTipoObjeto,
          renderer: renderComboEditor(comboTipoObjeto)
          }
       ]);
	 
	 grillaSecciones = new Ext.grid.EditorGridPanel({
	         el:'gridSecciones',
	         store: crearGridSeccionesStore(),
	         buttonAlign:'center',
	  		 border:true,
	         clicksToEdit:1,
	         title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
	         cm: cm,
	   		 buttons:[
	              {
	           	  text:getLabelFromMap('confOTBtnAdd',helpMap,'Agregar'),
          		tooltip: getToolTipFromMap('confOTBtnAdd',helpMap,'Agrega configuracion'),
	           	  handler: function () {
	           	  		addGridSeccionNewRecord();
	           	  		}
	           	  		
	              },
	              {
	              text:getLabelFromMap('confOTBtnSave',helpMap,'Guardar'),
          		tooltip: getToolTipFromMap('confOTBtnSave',helpMap,'Guarda configuracion'),
	              handler: function () {
					if (grillaSecciones.store.getModifiedRecords()==0)
					{
					  	  Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe agregar un registro para realizar esta operaci&oacute;n'));
	            	 }else{
	            		 guardarDatosSeccion();
					}
	              }
	              },{
	              text:getLabelFromMap('confOTBtnBorrar',helpMap,'Eliminar'),
          		tooltip: getToolTipFromMap('confOTBtnBorrar',helpMap,'Elimina configuracion'),
	              handler: function()
	              {
	                 if (grillaSecciones.getSelectionModel().getSelections().length > 0) {
	                  Ext.MessageBox.confirm(getLabelFromMap('400000', helpMap,'Aviso'), getLabelFromMap('400031', helpMap,'Se eliminara el registro seleccionado'), 
	                     function(btn) {
	                       if (btn == "yes"){
                       			if (grillaSecciones.getSelectionModel().getSelected().get("codigoSeccion2")==""){
                       				grillaSecciones.store.remove(grillaSecciones.getSelectionModel().getSelected());
                       			}else{
                       	    		 borrarSeccion();
                       			}
                       		} 
	                     }
	                  );
	                 }else {
	                  Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
	                 }
	                }
	              },	              
	              {
	              text:getLabelFromMap('confOTBtnBack',helpMap,'Regresar'),
          		  tooltip: getToolTipFromMap('confOTBtnBack',helpMap,'Regresa a la pagina anterior'),
	              handler: function(){window.location.href = _ACTION_REGRESAR;}
	              }
	              ],
	      	width:500,
	      	frame:true,
	   	  	height:344,
	      	sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
	      	stripeRows: true,
	      	collapsible: true,
	   		bbar: new Ext.PagingToolbar({
		     	pageSize:itemsPerPage,
		    	store: crearGridSeccionesStore(),
		     	displayInfo: true,
		     	title:'<span class="x-form-item" style="color:#98012e;font-size:14px;font:Arial,Helvetica, Sans-serif;">Listado</span>',
		        displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
				emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
	       })
	       
	       
	   });
	   //grillaSecciones.render();
	   
	   grillaSecciones.on('beforeedit',
	                       function(obj){
	                       if (obj.field=='codigoSeccion'){
	                       	  if (obj.record.get('codigoSeccion2')!=""){
	                              obj.cancel=true;
	                          }
	                       }
	                       
	                       });
	 }
	 
	 function reloadGridSeccion(){
	  var _storeDom = grillaSecciones.store;
	     var o = {start: 0};
	     _storeDom.baseParams = _storeDom.baseParams || {};
	     _storeDom.baseParams['cdFormatoOrden'] = CODIGO_FORMATO_ORDEN;
	     _storeDom.reload(
	               {
	                   params:{start:0,limit:itemsPerPage},
	                   callback : function(r, options, success) {
	                       if (!success) {
	                          _storeDom.removeAll();
	                       }
	                   }
	 
	               }
	             );
	 } 
	 
	 function addGridSeccionNewRecord(){
	  var new_record = new recordSeccionesFormato({
	                codigoFormatoOrden: CODIGO_FORMATO_ORDEN,
	                codigoSeccion2: '',
	                descripcionSeccion: '',
	                numeroOrden: '',
	                descripcionTipSit: '',
	                dsTipObj: ''
	      });
	  grillaSecciones.stopEditing();
	  grillaSecciones.store.insert(0, new_record);
	  grillaSecciones.startEditing(0, 0);
	 }
	
	 function guardarDatosSeccion () {
		  var _params = "";
		  var _cdTipObj = "";
		  var recs = grillaSecciones.store.getModifiedRecords();
		  grillaSecciones.stopEditing();
		  for (var i=0; i<recs.length; i++) {
		  	 _cdTipObj = (recs[i].get('codigoTipObj')!=undefined)?recs[i].get('codigoTipObj'):"";
		  	 _cdTipSit = (recs[i].get('cdTipSit')!=undefined)?recs[i].get('cdTipSit'):"";
		   	 _params +=  "csoGrillaList[" + i + "].cdFormatoOrden=" + CODIGO_FORMATO_ORDEN + "&" +
		      "csoGrillaList[" + i + "].cdSeccion=" + recs[i].get('codigoSeccion') + "&" +
		      "csoGrillaList[" + i + "].nmOrden=" + recs[i].get('numeroOrden') + "&" +
		      "csoGrillaList[" + i + "].cdTipSit=" + _cdTipSit + "&" +
		      "csoGrillaList[" + i + "].cdTipObj=" + _cdTipObj + "&";
		  }
		  if (recs.length > 0) {
		   	var conn = new Ext.data.Connection ();
		   	conn.request({
			     url: _ACTION_GUARDAR_SECCIONES_FORMATO,
			     params: _params,
			     method: 'POST',
			     callback: function (options, success, response) {
				         if (Ext.util.JSON.decode(response.responseText).success == false) {
				          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
			         } else {
				          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Registro Creado', function(){reloadGridSeccion();});
				          grillaSecciones.store.commitChanges();
			         }
			      }
		   });
		 }
	 }            
	    
	 function borrarSeccion () {
	  		var recs = grillaSecciones.getSelectionModel().getSelections();
	  		if (recs.length > 0) {
	   			var conn = new Ext.data.Connection();
	   			conn.request ({
				      url: _ACTION_ELIMINAR_SECCIONES_FORMATO,
				      params: {cdFormatoOrden: CODIGO_FORMATO_ORDEN, cdSeccion: recs[0].get('codigoSeccion')},
				      method: 'POST',
				      callback: function(options, success, response) {
					         if (Ext.util.JSON.decode(response.responseText).success == false) {
					          Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Error'), Ext.util.JSON.decode(response.responseText).errorMessages[0]);
					         } else {
					          Ext.Msg.alert(getLabelFromMap('400000', helpMap,'Aviso'), 'Registro Eliminado', function() {reloadGridSeccion()});
					          reloadGridSeccion();
					         }
				      }
	   			});
			  }else{
			   Ext.MessageBox.alert(getLabelFromMap('400000', helpMap,'Aviso'),getLabelFromMap('400011', helpMap,'Debe seleccionar un registro para realizar esta operaci&oacute;n'));
			  }
	 }
	 
	 createGridSecciones();
	
	    var descripcionFormatoOrden = new Ext.form.TextField( {
	           id:'descripcionId',
               fieldLabel : getLabelFromMap('confOTTxtNomb', helpMap,'Nombre'),
               tooltip: getToolTipFromMap('confOTTxtNomb', helpMap,'Nombre'),
               hasHelpIcon:getHelpIconFromMap('confOTTxtNomb',helpMap),
               Ayuda: getHelpTextFromMap('confOTTxtNomb',helpMap,''),               
               name : 'dsFormatoOrden',
               hiddenName: 'descripcionId',
               disabled: true,
               value: DESCRIPCION_FORMATO_ORDEN,
               width: 200
	     });
	
	    var formPanel = new Ext.FormPanel({
	     	renderTo: 'formTab',
        	title: getLabelFromMap('98',helpMap,'Configurar Ordenes de Trabajo'),
        	width: 500,
        	buttonAlign:'center',
        	//deferredRender:false,
        	items: [
		        descripcionFormatoOrden,
		        {
		        xtype:'tabpanel',
		        id: 'ttabs',
				resizeTabs:true,
				minTabWidth: 115,
				tabWidth:135,
				enableTabScroll: true,
				layoutOnTabChange:false,
				width:500,
				height:370,
				activeTab: 0,
				defaults: {labelWidth: 80},
				items:[{
				     title: getLabelFromMap('cotTabPSecciones',helpMap,'Secciones'),
				     tooltip: getToolTipFromMap('cotTabPSecciones',helpMap,'Secciones'),
				     layout: 'fit',
				     id: '4',
				     items: [grillaSecciones]
				     },
				     {
				     title: getLabelFromMap('cotTabPAtributos',helpMap,'Atributos'),
				     tooltip: getToolTipFromMap('cotTabPAtributos',helpMap,'Atributos'),
				     layout: 'fit',
				     id: '5',
				     items: [/*{
				       		layout: 'fit',
				          	id: '6',
				       		items: [comboSeccionesAtributos,grillaAtributos]
				       }*/
				       comboSeccionesAtributos,
				       grillaAtributos
				     ]
				}]
	    	}]
	});
	     
	 formPanel.render();
 	 grillaSecciones.adjustBodyHeight();
	 
	 dsSecciones.load();
	 dsTipoSituacion.load();
	 dsTipoObjeto.load(); 
     dsBloques.load();
	 dsFormatos.load();

	
	
	 
	 if (CODIGO_FORMATO_ORDEN != ""){reloadGridSeccion();
	                                 /*reloadGridAtributo();*/  }
	                                 
	  formPanel.findById('ttabs').doLayout();
	  
	  if(FLAG)
	  {
	  	formPanel.findById('ttabs').setActiveTab(1);
	  	formPanel.findById('ttabs').doLayout();
	  	
		dsComboSeccionesFormato.load({
				                      params:{cdFormato: CODIGO_FORMATO_ORDEN},
				                      
				                      callback: function(r,options,success){
				                          if(success)
				                          {
				                          	formPanel.findById('cdSeccionId').setValue(CODIGO_SECCION);
				                          	formPanel.findById('descripcionId').setValue(DESCRIPCION_FORMATO_ORDEN);
				                          	reloadGridAtributo();
				                          }
				                      }
					});	  	
	  }
	
	 function renderComboEditor (combo) {
	  return function (value) {
	   var idx = combo.store.find(combo.valueField, value);
	   var rec = combo.store.getAt(idx);
	   return (rec == null)?value:rec.get(combo.displayField);
	  }
	 }
	 
	 
	 function renderComboEditorValida (combo) {
			return function (value, meta, record) {
				var idx = combo.store.find(combo.valueField, value);
				var rec = combo.store.getAt(idx);
				var valor = (value != "")?record.get('codigoSeccion2'):"";
				combo.setDisabled((record.get('codigoSeccion2') != "")?true:false);
				//alert((record.get('codigoSeccion2') != ""));
				//if (rec) alert(rec.get(combo.displayField));
				//alert(valor);
				return (rec == null)?valor:rec.get(combo.displayField);
			}
		}
		
	function getSelectedRecord(){
    var m = grillaAtributos.getSelections();
    if (m.length == 1 ) {
       return m[0];
       }
   }
		
	 
});