Ext.onReady(function(){
	Ext.QuickTips.init();
	Ext.QuickTips.enable();
	Ext.form.Field.prototype.msgTarget = "side";
	

	
	
	Ext.override(Ext.form.HtmlEditor, {
	/**
	 * Set a readonly mask over the editor
	 * @param {Boolean} readOnly - True to set the read only property, False to switch to the editor
	 */
	setReadOnly: function(readOnly){
		if(readOnly){
			this.syncValue();
			var roMask = this.wrap.mask();
			roMask.dom.style.filter = "alpha(opacity=30);"; //IE
			roMask.dom.style.opacity = "0"; //Mozilla
			roMask.dom.style.background = "white";
			roMask.dom.style.overflow = "scroll";
			roMask.dom.innerHTML = this.getValue();
			this.el.dom.readOnly = true;
		} else {
			if(this.rendered){
				this.wrap.unmask();
			}
			this.el.dom.readOnly = false;
		}
	},
   // private
    onRender : function(ct, position){
        Ext.form.HtmlEditor.superclass.onRender.call(this, ct, position);
        this.el.dom.style.border = '0 none';
        this.el.dom.setAttribute('tabIndex', -1);
        this.el.addClass('x-hidden');
        if(Ext.isIE){ // fix IE 1px bogus margin
            this.el.applyStyles('margin-top:-1px;margin-bottom:-1px;')
        }
        this.wrap = this.el.wrap({
            cls:'x-html-editor-wrap', cn:{cls:'x-html-editor-tb'}
        });

        this.createToolbar(this);

        this.tb.items.each(function(item){
           if(item.itemId != 'sourceedit'){
                item.disable();
            }
        });

        var iframe = document.createElement('iframe');
        iframe.name = Ext.id();
        iframe.frameBorder = 'no';

        iframe.src=(Ext.SSL_SECURE_URL || "javascript:false");

        this.wrap.dom.appendChild(iframe);

        this.iframe = iframe;

        if(Ext.isIE){
            iframe.contentWindow.document.designMode = 'on';
            this.doc = iframe.contentWindow.document;
            this.win = iframe.contentWindow;
        } else {
            this.doc = (iframe.contentDocument || window.frames[iframe.name].document);
            this.win = window.frames[iframe.name];
            this.doc.designMode = 'on';
        }
        this.doc.open();
        this.doc.write(this.getDocMarkup())
        this.doc.close();

        var task = { // must defer to wait for browser to be ready
            run : function(){
                if(this.doc.body || this.doc.readyState == 'complete'){
                    Ext.TaskMgr.stop(task);
                    this.doc.designMode="on";
                    this.initEditor.defer(10, this);
                }
            },
            interval : 10,
            duration:10000,
            scope: this
        };
        Ext.TaskMgr.start(task);

        if(!this.width){
            this.setSize(this.el.getSize());
        }

		this.setReadOnly(this.readOnly);

    }
});
	
Ext.override(Ext.form.Field, {
    showContainer: function() {
        this.enable();
        this.show();
        this.getEl().up('.x-form-item').setDisplayed(true);
    },
    
    hideContainer: function() {
        this.disable();
        this.hide();
        this.getEl().up('.x-form-item').setDisplayed(false);
    },
    
    setContainerVisible: function(visible) {
        if (visible) {
            this.showContainer();
        } else {
            this.hideContainer();
        }
        return this;
    }
});

Ext.override(Ext.form.ComboBox, {
    showContainer: function() {
        this.enable();
        this.show();
        this.getEl().up('.x-form-item').setDisplayed(true);
    },
    
    hideContainer: function() {
        this.disable();
        this.hide();
        this.getEl().up('.x-form-item').setDisplayed(false);
    },
    
    setContainerVisible: function(visible) {
        if (visible) {
            this.showContainer();
        } else {
            this.hideContainer();
        }
        return this;
    }
});

	
	var aseg_store = new Ext.data.Store ({
		proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ASEGURADORA}),
		reader: new Ext.data.JsonReader({
		root: 'aseguradoraComboBox',
			//	id: 'cdUniEco',
		successProperty: '@success'
		}, [
			{name: 'codigo', type: 'string', mapping: 'codigo'},
			{name: 'descripcion', type: 'string', mapping: 'descripcion'} 
			])
									});
				
			
 var dsNivelCorpo = new Ext.data.Store({
 	proxy: new Ext.data.HttpProxy({
    url: _ACTION_OBTENER_NIVEL_CORPO
 	}),
    reader: new Ext.data.JsonReader({
    root: 'comboClientesCorpBO',
        //id: 'cdElemento',
    successProperty: '@success'
    }, [
      	{name: 'cdElemento', type: 'string', mapping: 'cdElemento'},
        {name: 'cdPerson', type: 'string', mapping: 'cdPerson'},
        {name: 'dsElemen', type: 'string', mapping: 'dsElemen'} 
    ])
    

  });			 			
				
 var desProducto = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
            url: _ACTION_OBTENER_PRODUCTOS
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosComboBox'
         //   id: 'cdRamo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
      		  ])
  									 });		
   	
				
 var dsPago = new Ext.data.Store ({
			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_ISTRUMENTO_PAGO}),
			reader: new Ext.data.JsonReader({
			root: 'comboProcesosCat',
		//	id: 'cdProceso',
			successProperty: '@success'
			}, [
				{name: 'cdProceso', type: 'string', mapping: 'cdProceso'},
				{name: 'dsProceso', type: 'string', mapping: 'dsProceso'}
				]),
			remoteSort: true
								});		
	 var storeListaValores = new Ext.data.Store ({
			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_LISTA_VALORES}),
			reader: new Ext.data.JsonReader({
			root: 'comboListaValoresInstPago',
		//	id: 'cdProceso',
			successProperty: '@success'
			}, [
				{name: 'cdTabla', type: 'string', mapping: 'cdTabla'},
				{name: 'dsTabla', type: 'string', mapping: 'dsTabla'},
				{name: 'nmTabla', type: 'string', mapping: 'nmTabla'}
				
			]),
			remoteSort: true
								});	
	 var storeCondicion = new Ext.data.Store ({
			proxy: new Ext.data.HttpProxy({url: _ACTION_COMBO_CONDICION}),
			reader: new Ext.data.JsonReader({
			root: 'comboCondicionInstPago',
		//	id: 'cdProceso',
			successProperty: '@success'
			}, [
				{name: 'cdcondic', type: 'string', mapping: 'cdcondic'},
				{name: 'dscondic', type: 'string', mapping: 'dscondic'}
				
				
			]),
			remoteSort: true
								});	
	
	
  var nivelCombo = new Ext.form.ComboBox({
		            labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson2}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsNivelCorpo,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdPerson',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    id: 'nivelAgregarComboId', 
		            fieldLabel: getLabelFromMap('nivelAgregarComboId',helpMap,'Nivel'),
	            	tooltip: getToolTipFromMap('nivelAgregarComboId',helpMap,'Nivel'),
		            hasHelpIcon:getHelpIconFromMap('nivelAgregarComboId',helpMap),								 
		            Ayuda: getHelpTextFromMap('nivelAgregarComboId',helpMap,'Help'),
                    //fieldLabel: "Cliente",
                    forceSelection: true,
                     //anchor:'100%',
                     width:160,
                    emptyText:'Seleccione Nivel ...',
                    selectOnFocus:true,
                    allowBlank: false,
                    mode:'local',
                    //labelSeparator:'',
                    onSelect: function (record) {
                    	          this.setValue(record.get("cdElemento"));
                                  formPrincipal.findById(('nivelAgregarComboId')).setValue(record.get("cdElemento"));
                                  
                                   
                                           				formPrincipal.findById(('aseguradoraAgregarComboId')).setValue("");
                                          				aseg_store.removeAll();
                                          				
                                          				aseg_store.load({
	                            											params: {cdElemento: record.get('cdElemento')},
	                            											failure: aseg_store.removeAll()
	                            										});
	                            										
	                            						formPrincipal.findById(('productoAgregarComboId')).setValue("");				
	                            					    desProducto.removeAll();
	                            					    desProducto.load({
	                            											params: {cdunieco: formPrincipal.findById(('aseguradoraAgregarComboId')).getValue(),
	                            											         cdElemento: record.get('cdElemento'),
	                            											         cdRamo: ""
	                            											         },
	                            											failure: aseg_store.removeAll()
	                            										});
	                              this.collapse();
                            }
                });

	var aseguradoraCombo = new Ext.form.ComboBox({
		   labelWidth: 70, 
	       tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
	       store: aseg_store, 
	       displayField:'descripcion', 
	       valueField:'codigo',
	       id:'aseguradoraAgregarComboId', 
	       hiddenName: 'codAseguradora', 
	       typeAhead: true,
	       allowBlank:false,
	       mode: 'local', 
	       triggerAction: 'all', 
	       width:160, 
	       emptyText:'Seleccione Aseguradora...',
	       forceSelection:true,
	       selectOnFocus:true, 
	       loadMask: true,
	       mode:'local',
	       fieldLabel: getLabelFromMap('aseguradoraAgregarComboId',helpMap,'Aseguradora'),
		   tooltip: getToolTipFromMap('aseguradoraAgregarComboId',helpMap,'Aseguradora'),
	       hasHelpIcon:getHelpIconFromMap('aseguradoraAgregarComboId',helpMap),								 
		   Ayuda: getHelpTextFromMap('aseguradoraAgregarComboId',helpMap,'Help'),
     	   onSelect: function (record) {
	            this.setValue(record.get('codigo'));
	                formPrincipal.findById(('productoAgregarComboId')).setValue("");				
	                       desProducto.removeAll();
	                       desProducto.load({
	                       params: {cdunieco: record.get('codigo'),
	                                cdElemento: formPrincipal.findById(('nivelAgregarComboId')).getValue(),
	                                cdRamo: ""
	                          },
	                        failure: aseg_store.removeAll()
	                        });
	                       	this.collapse();
	                                       }
		
	});
	
	var instrPagoCombo = new Ext.form.ComboBox(
	    {                   
           tpl: '<tpl for="."><div ext:qtip="{cdProceso}. {dsProceso}" class="x-combo-list-item">{dsProceso}</div></tpl>',
           //store: dsPago,
           displayField:'dsProceso',
           valueField:'cdProceso',
           hiddenName: 'cdProceso',
           typeAhead: true,mode: 'local',
           triggerAction: 'all',
           fieldLabel: "Instrumento de Pago",
           fieldLabel: getLabelFromMap('instPagoAgregarComboId',helpMap,'Instrumento de Pago'),
		   tooltip: getToolTipFromMap('instPagoAgregarComboId',helpMap,'Instrumento de Pago'),
		   hasHelpIcon:getHelpIconFromMap('instPagoAgregarComboId',helpMap),								 
		   Ayuda: getHelpTextFromMap('instPagoAgregarComboId',helpMap,'Help'),
	       forceSelection: true,width: 160,emptyText:'Seleccione Instrumento de Pago...',
           selectOnFocus:true,labelSeparator:'',
           allowBlank : false,
           id: 'instPagoAgregarComboId' 
            
	});
	
	var productoCombo = new Ext.form.ComboBox({
		   xtype: 'combo',
           tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
           store: desProducto,
           displayField:'descripcion',
           valueField:'codigo',
           hiddenName: 'cdRamo',
           typeAhead: true,
           mode: 'local',
           triggerAction: 'all',
           fieldLabel: getLabelFromMap('productoAgregarComboId',helpMap,'Producto'),
		   tooltip: getToolTipFromMap('productoAgregarComboId',helpMap,'Producto'),
		   hasHelpIcon:getHelpIconFromMap('productoAgregarComboId',helpMap),								 
           Ayuda: getHelpTextFromMap('productoAgregarComboId',helpMap,'Help'),
           forceSelection: true,
           mode:'local',
           width:160,
           emptyText:'Seleccione Producto...',
           selectOnFocus:true,
           id:'productoAgregarComboId',
           allowBlank:false
     });
	
	var nombreText = new Ext.form.TextField({
		id: 'nombreAgregarTextoId', 
		fieldLabel: getLabelFromMap('nombreAgregarTextoId',helpMap,'Nombre'),
		tooltip: getToolTipFromMap('nombreAgregarTextoId',helpMap,'Nombre'),
		hasHelpIcon:getHelpIconFromMap('nombreAgregarTextoId',helpMap),								 
        Ayuda: getHelpTextFromMap('nombreAgregarTextoId',helpMap,'Help'),
		name: 'dsNombre'//,
		//maskRe : /[A-N O-Z a-n o-z 0-9 _'.-?¿¡!*/+`^=]/
	
	});
	
	var listaValoresCombo = new Ext.form.ComboBox({
		tpl: '<tpl for="."><div ext:qtip="{cdTabla}. {dsTabla}" class="x-combo-list-item">{dsTabla}</div></tpl>',
        store: storeListaValores,
        displayField:'dsTabla',
        valueField:'cdTabla',
        hiddenName: 'dsListaValoresCombo',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        fieldLabel: getLabelFromMap('listaValoresAgregarComboId',helpMap,'Lista de Valores'),
		tooltip: getToolTipFromMap('listaValoresAgregarComboId',helpMap,'Lista de Valores'),
		hasHelpIcon:getHelpIconFromMap('listaValoresAgregarComboId',helpMap),								 
        Ayuda: getHelpTextFromMap('listaValoresAgregarComboId',helpMap,'Help'),
        forceSelection: true,
        mode:'local',
        width:140,
        emptyText:'Seleccione Lista de Valores...',
        selectOnFocus:true,
        id:'listaValoresAgregarComboId',
        allowBlank:false
		
	});
	
	var maximaText = new Ext.form.TextField({
		id: 'maximaAgregarTextoId', 
		fieldLabel: getLabelFromMap('maximaAgregarTextoId',helpMap,'M&aacute;xima'),
		tooltip: getToolTipFromMap('maximaAgregarTextoId',helpMap,'M&aacute;xima'),
		hasHelpIcon:getHelpIconFromMap('maximaAgregarTextoId',helpMap),								 
        Ayuda: getHelpTextFromMap('maximaAgregarTextoId',helpMap,'Help'),
		name: 'dsMaxima'
	
	});
	
	var minimaText = new Ext.form.TextField({
		id: 'minimaAgregarTextoId', 
		fieldLabel: getLabelFromMap('minimaAgregarTextoId',helpMap,'M&iacute;nima'),
		tooltip: getToolTipFromMap('minimaAgregarTextoId',helpMap,'M&iacute;nima'),
		hasHelpIcon:getHelpIconFromMap('minimaAgregarTextoId',helpMap),								 
        Ayuda: getHelpTextFromMap('minimaAgregarTextoId',helpMap,'Help'),
		name: 'dsMinima'
	
	});
	/*var alfanumericoCheck = new Ext.form.CheckboxGroup({
    	fieldLabel:'Aseguradora',// getLabelFromMap('alfanumericoCheckId', helpMap,'Alfanumerico'), 
    //	tooltip: getToolTipFromMap('alfanumericoCheckId', helpMap,'Alfanumerico'),  		
     //   hasHelpIcon:getHelpIconFromMap('alfanumericoCheckId',helpMap),								 
     //   Ayuda: getHelpTextFromMap('alfanumericoCheckId',helpMap,'Help'),
      //  width:'40',
     //   id: 'alfanumericoCheckId', 
        name: 'dsAlfaNumCheck',
     //   allowBlank: true,
        items: [
				{boxLabel: 'Item 1', name: 'cb-auto-1'},
				{boxLabel: 'Item 2', name: 'cb-auto-2', checked: true},
				{boxLabel: 'Item 3', name: 'cb-auto-3'},
				{boxLabel: 'Item 4', name: 'cb-auto-4'},
				{boxLabel: 'Item 5', name: 'cb-auto-5'}
				]*/
        
        /* listeners:{
        'check': function() {
        		if(this.checked){
        			alert(1);
	        		Ext.getCmp('numericoCheckId').checked  = false;
	        		Ext.getCmp('fechaCheckId').checked = false;
	        		Ext.getCmp('porcentajeCheckId').checked =false;
        	   }else{
		        	Ext.getCmp('numericoCheckId').checked = true;
		        	Ext.getCmp('fechaCheckId').checked = true;
		        	Ext.getCmp('porcentajeCheckId').checked = true;
		        	}
		        	
               }}*/
  // });
   var numericoCheck = new Ext.form.Checkbox({
    	fieldLabel: getLabelFromMap('numericoCheck', helpMap,'Numerico'), 
    	tooltip: getToolTipFromMap('numericoCheck', helpMap,'Numerico'),  		
        hasHelpIcon:getHelpIconFromMap('numericoCheck',helpMap),								 
        Ayuda: getHelpTextFromMap('numericoCheck',helpMap,'Help'),
        width:'40',
        id: 'numericoCheckId', 
        name: 'dsNumericoCheck',
        allowBlank: true
  });
   var fechaCheck = new Ext.form.Checkbox({
    	fieldLabel: getLabelFromMap('fechaCheckId', helpMap,'Fecha'), 
    	tooltip: getToolTipFromMap('fechaCheckId', helpMap,'Fecha'),  		
        hasHelpIcon:getHelpIconFromMap('fechaCheckId',helpMap),								 
        Ayuda: getHelpTextFromMap('fechaCheckId',helpMap,'Help'),
        width:'40',
        id: 'fechaCheckId', 
        name: 'dsFechaCheck',
        allowBlank: true
    });
   var porcentajeCheck = new Ext.form.Checkbox({
    	fieldLabel: getLabelFromMap('porcentajeCheckId', helpMap,'Porcentaje'), 
    	tooltip: getToolTipFromMap('porcentajeCheckId', helpMap,'Porcentaje'),  		
        hasHelpIcon:getHelpIconFromMap('porcentajeCheckId',helpMap),								 
        Ayuda: getHelpTextFromMap('porcentajeCheckId',helpMap,'Help'),
        width:'40',
        id: 'porcentajeCheckId', 
        name: 'dsPorcentajeCheck',
        allowBlank: true
    });
    
      var emisionVisibleCheck = new Ext.form.Checkbox({
    	//fieldLabel: getLabelFromMap('porcentajeCheckId', helpMap,'Porcentaje'), 
    	//tooltip: getToolTipFromMap('porcentajeCheckId', helpMap,'Porcentaje'),  		
     //   hasHelpIcon:getHelpIconFromMap('porcentajeCheckId',helpMap),								 
      //  Ayuda: getHelpTextFromMap('porcentajeCheckId',helpMap,'Help'),
       labelSeparator:'',
      	width:'40',
        id: 'emisionVisibleCheckId', 
        name: 'dsEmisionVisibleCheck',
        allowBlank: true
    }); 
    
      var emisionActualizable = new Ext.form.Checkbox({
    //	fieldLabel: getLabelFromMap('porcentajeCheckId', helpMap,'Porcentaje'), 
    //	tooltip: getToolTipFromMap('porcentajeCheckId', helpMap,'Porcentaje'),  		
      //  hasHelpIcon:getHelpIconFromMap('porcentajeCheckId',helpMap),								 
       // Ayuda: getHelpTextFromMap('porcentajeCheckId',helpMap,'Help'),
       labelSeparator:'',
        width:'40',
        id: 'emisionActualizableId', 
        name: 'dsEmisionActualizable',
        allowBlank: true
    });  
    
    var emisionObligatorio = new Ext.form.Checkbox({
    	//fieldLabel: getLabelFromMap('porcentajeCheckId', helpMap,'Porcentaje'), 
    	//tooltip: getToolTipFromMap('porcentajeCheckId', helpMap,'Porcentaje'),  		
        //hasHelpIcon:getHelpIconFromMap('porcentajeCheckId',helpMap),								 
        //Ayuda: getHelpTextFromMap('porcentajeCheckId',helpMap,'Help'),
        labelSeparator:'',
    	width:'40',
        id: 'emisionObligatorioId', 
        name: 'dsEmisionObligatorio',
        allowBlank: true
    });
    
    
      var endosoVisibleCheck = new Ext.form.Checkbox({
    	//fieldLabel: getLabelFromMap('porcentajeCheckId', helpMap,'Porcentaje'), 
    	//tooltip: getToolTipFromMap('porcentajeCheckId', helpMap,'Porcentaje'),  		
     //   hasHelpIcon:getHelpIconFromMap('porcentajeCheckId',helpMap),								 
      //  Ayuda: getHelpTextFromMap('porcentajeCheckId',helpMap,'Help'),
       labelSeparator:'',
      	width:'40',
        id: 'endosoVisibleCheckId', 
        name: 'dsEndosoVisibleCheck',
        allowBlank: true
    }); 
    
      var endosoActualizableCheck = new Ext.form.Checkbox({
    //	fieldLabel: getLabelFromMap('porcentajeCheckId', helpMap,'Porcentaje'), 
    //	tooltip: getToolTipFromMap('porcentajeCheckId', helpMap,'Porcentaje'),  		
      //  hasHelpIcon:getHelpIconFromMap('porcentajeCheckId',helpMap),								 
       // Ayuda: getHelpTextFromMap('porcentajeCheckId',helpMap,'Help'),
        labelSeparator:'',
        width:'40',
        id: 'endosoActualizableCheckId', 
        name: 'dsEndosoActualizableCheck',
        allowBlank: true
    });  
    
    var endososObligatorioCheck = new Ext.form.Checkbox({
    	//fieldLabel: getLabelFromMap('porcentajeCheckId', helpMap,'Porcentaje'), 
    	//tooltip: getToolTipFromMap('porcentajeCheckId', helpMap,'Porcentaje'),  		
        //hasHelpIcon:getHelpIconFromMap('porcentajeCheckId',helpMap),								 
        //Ayuda: getHelpTextFromMap('porcentajeCheckId',helpMap,'Help'),
        labelSeparator:'',
    	width:'40',
        id: 'endososObligatorioCheckId', 
        name: 'dsEndososObligatorioCheck',
        allowBlank: true
    });
    
    var leyendaCheck = new Ext.form.Checkbox({
    	fieldLabel: getLabelFromMap('leyendaCheckId', helpMap,'Leyenda'), 
    	tooltip: getToolTipFromMap('leyendaCheckId', helpMap,'Leyenda'),  		
        hasHelpIcon:getHelpIconFromMap('leyendaCheckId',helpMap),								 
        Ayuda: getHelpTextFromMap('leyendaCheckId',helpMap,'Help'),
        width:'40',
        id: 'leyendaCheckId', 
        name: 'dsLeyendaCheck',
        allowBlank: true,
        checked:false,
        listeners:{
        'check': function() {
        		if(this.checked){
	        		Ext.getCmp('leyendaEditorTextId').setReadOnly(false);
        	   }else{
		        	Ext.getCmp('leyendaEditorTextId').setReadOnly(true)}
               }}
       
       
    });
    
	var leyendaEditorText = new Ext.form.HtmlEditor({
        // fieldLabel : getLabelFromMap('editAyCobTxtText',helpMap,'Texto'),
        // tooltip: getToolTipFromMap('editAyCobTxtText',helpMap,'Texto Libre'),
         labelSeparator:'',
         name : 'dsLeyendaEditorText',
         allowBlank : true, 
        // anchor: '100%',
         //anchor:'0 -180',
         width:550,
         height: 100,
         id:'leyendaEditorTextId',
         //disabled:true,
         emptyText: 'Texto de Ayuda [opcional]...'               	
    });
    
    var padreCombo = new Ext.form.ComboBox({
		tpl: '<tpl for="."><div ext:qtip="{cdcondic}. {dscondic}" class="x-combo-list-item">{dscondic}</div></tpl>',
        store: storeCondicion,//cambiar esta para probar
        displayField:'dscondic',
        valueField:'cdcondic',
        hiddenName: 'dsPadreCombo',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        fieldLabel: getLabelFromMap('padreAgregarComboId',helpMap,'Padre'),
		tooltip: getToolTipFromMap('padreAgregarComboId',helpMap,'Padre'),
		hasHelpIcon:getHelpIconFromMap('padreAgregarComboId',helpMap),								 
        Ayuda: getHelpTextFromMap('padreAgregarComboId',helpMap,'Help'),
        forceSelection: true,
        mode:'local',
        width:80,
        emptyText:'Seleccione Padre...',
        selectOnFocus:true,
        id:'padreAgregarComboId',
        allowBlank:false,
        onSelect: function (record) {
        	 ordenText.setContainerVisible(true);
			 agrupadorText.setContainerVisible(true);
			 condicionCombo.setContainerVisible(true);
			 formPrincipal.findById(('condicionAgregarButton')).setVisible(true);
			 this.setValue(record.get("cdcondic"));
             formPrincipal.findById(('padreAgregarComboId')).setValue(record.get("cdcondic"));
              this.collapse();
         }/*,
         listeners: {
			blur: function () {
						if (this.getRawValue() == "") {
							this.setValue();
				};
			ordenText.setContainerVisible(false);
			agrupadorText.setContainerVisible(false);
			condicionCombo.setContainerVisible(false);
			formPrincipal.findById(('condicionAgregarButton')).setVisible(false);
			  }
		   }*/
	});
	
	var ordenText = new Ext.form.TextField({
		id: 'ordenAgregarTextoId', 
		fieldLabel: getLabelFromMap('ordenAgregarTextoId',helpMap,'Orden'),
		tooltip: getToolTipFromMap('ordenAgregarTextoId',helpMap,'Orden'),
		hasHelpIcon:getHelpIconFromMap('ordenAgregarTextoId',helpMap),								 
        Ayuda: getHelpTextFromMap('ordenAgregarTextoId',helpMap,'Help'),
        width:40,
        name: 'dsOrdenText'
	
	});
	var agrupadorText = new Ext.form.TextField({
		id: 'agrupadorTextId', 
		fieldLabel: getLabelFromMap('agrupadorTextId',helpMap,'Agrupador'),
		tooltip: getToolTipFromMap('agrupadorTextId',helpMap,'Agrupador'),
		hasHelpIcon:getHelpIconFromMap('agrupadorTextId',helpMap),								 
        Ayuda: getHelpTextFromMap('agrupadorTextId',helpMap,'Help'),
        width:40,
        name: 'dsAgrupadorTextId'
	
	});
	
	var condicionCombo = new Ext.form.ComboBox({
		tpl: '<tpl for="."><div ext:qtip="{cdcondic}. {dscondic}" class="x-combo-list-item">{dscondic}</div></tpl>',
        store: storeCondicion,
        displayField:'dscondic',
        valueField:'cdcondic',
        hiddenName: 'dsCondicionComboId',
        typeAhead: true,
        mode: 'local',
        triggerAction: 'all',
        fieldLabel: getLabelFromMap('condicionComboId',helpMap,'Condici&oacute;n'),
		tooltip: getToolTipFromMap('condicionComboId',helpMap,'Condici&oacute;n'),
		hasHelpIcon:getHelpIconFromMap('condicionComboId',helpMap),								 
        Ayuda: getHelpTextFromMap('condicionComboId',helpMap,'Help'),
        forceSelection: true,
        mode:'local',
        width:120,
        emptyText:'Seleccione Condición...',
        selectOnFocus:true,
        id:'condicionComboId',
        allowBlank:false
		
	});
	
  /*******************************Combo Principal****************************************/  
	var formPrincipal = new Ext.FormPanel ({
		id:'formPrincipalId',
		renderTo: 'formulario',
		title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('formPrincipalId',helpMap,'Agregar Instrumento de Pago')+'</span>',
		//url : _ACTION_BUSCAR_ALERTAS,
		labelWidth : 80,
	    frame : true,
        bodyStyle:'background: white',
        width : 720,
        autoHeight: true,
        labelAlign:'right',
        waitMsgTarget : true,
		 items: [{
        		layout:'form',
				border: false,
 				items:[
                {
        		bodyStyle:'background: white',
		        labelWidth: 130,
                layout: 'form',
                frame:true,
		       	baseCls: '',
		       	buttonAlign: "center",
        		        items: [{
        		        	layout:'column',
		 				    border:false,
		 				    columnWidth: 1,
        		    		items:[
        		    		{
						    	columnWidth:1,
                				layout: 'form',
		                		border: false,
        		        		items:[{html:'&nbsp;'} ]
								},
        		  /***********************Primera Fila*************************/
        		    			{
						    	columnWidth:.5,
                				layout: 'form',
		                		border: false,
        		        		items:[nivelCombo,
        		        		instrPagoCombo ]
								},
								{
								labelWidth: 100,
								columnWidth:.45,
                				layout: 'form',
		                		border: false,
        		        		items:[aseguradoraCombo, productoCombo]
                				},
                /**********************segunda Fila************************/
                				{
						    	columnWidth:1,
						    	 width:5,
                				layout: 'form',
		                		border: false,
        		        		items:[{html:'&nbsp;'} ]
								},{
								columnWidth:.30,
								labelWidth:50,
                				layout: 'form',
                				border: false,
                				items:[nombreText]
                			   },{
								columnWidth:.48,
                				layout: 'form',
                				labelWidth:105,
                				border: false,
                				items:[listaValoresCombo]
                			   },/*{
						    	columnWidth:.05,
						    	labelWidth:5,
                				layout: 'form',
		                		border: false,
        		        		items:[{html:'&nbsp;'} ]
								},*/{
								columnWidth:.19,
                				layout: 'form',
                				labelWidth:5,
                				border: false,
                				align:'rigth',
                				items:[{xtype: 'button',
   									id: 'agregarNuevaListaButton',
        							text:getLabelFromMap('agregarNuevaListaButton',helpMap,'Agregar Nueva Lista'),
        							tooltip: getToolTipFromMap('agregarNuevaListaButton',helpMap,'Agregar Nueva Lista'),
        							onClick:function(){
				        		           alert('Si');        		//se agrego parametro
				                     }
        						  }]
                			    },
                 /*************************tercer fila**************************/
                			    {
						    	columnWidth:1,
                				layout: 'form',
		                		border: false,
        		        		items:[{html:'&nbsp;'} ]
								},
								/*{
								columnWidth:.2,
								//labelWidth:50,
                				layout: 'form',
                				border: false,
                				items:[{html: '<span>Formato: </span>'
                				}]
                			   },*/{
								columnWidth:1,
								labelWidth:70,
                				layout: 'form',
                				border: false,
                				items: [  {
											xtype:'ux-radiogroup',
											id:'formatoRadioGroupId',
											//fieldLabel:'Formato',
											fieldLabel: getLabelFromMap('formatoRadioGroupId',helpMap,'Formato'),
											tooltip: getToolTipFromMap('formatoRadioGroupId',helpMap,'Formato'),
											hasHelpIcon:getHelpIconFromMap('formatoRadioGroupId',helpMap),								 
									        Ayuda: getHelpTextFromMap('formatoRadioGroupId',helpMap),
											name:'dsFormatoRadioGroup',
											horizontal:true,
											radios:[{
													value:1,
													//boxLabel:'box 1'
													boxLabel: getLabelFromMap('alfanumericoCheckId', helpMap,'Alfanumerico') 
    										}, {
												value:2,
												//boxLabel:'box 2'//,
												boxLabel: getLabelFromMap('numericoCheck', helpMap,'Numerico') 
    											//checked:true
											}, {
												value:3,
												//boxLabel:'box 3',
												boxLabel: getLabelFromMap('fechaCheckId', helpMap,'Fecha'), 
    											listeners:{
													'check':function(r,c){
														alert(r.boxLabel+": "+(c?"checked":"unchecked"));
													}
												}
											}, {
												value:4,
												//boxLabel:'box 4',
												boxLabel: getLabelFromMap('porcentajeCheckId', helpMap,'Porcentaje'), 
    											listeners:{
													'check':function(r,c){
														alert(r.boxLabel+": "+(c?"checked":"unchecked"));
													}
												}
											}]
					}]},
                				
                				/*items:[{
									// Use the default, automatic layout to distribute the controls evenly
									//across a single row
									xtype: 'ux-radiogroup',
									fieldLabel: 'Auto Layout',
									items: [
											{boxLabel: 'Item 1', name: 'cb-auto-1'}]
											/*,
											{boxLabel: 'Item 2', name: 'cb-auto-2', checked: true},
											{boxLabel: 'Item 3', name: 'cb-auto-3'},
											{boxLabel: 'Item 4', name: 'cb-auto-4'},
											{boxLabel: 'Item 5', name: 'cb-auto-5'}
											]*/
								/*	}]
                			    },*/
                			   
                			   /*{
								columnWidth:.2,
								labelWidth:70,
                				layout: 'form',
                				border: false,
                				items:[alfanumericoCheck]
                			    },{
								columnWidth:.2,
								labelWidth:40,
                				layout: 'form',
                				border: false,
                				items:[numericoCheck]
                			    },{
								columnWidth:.2,
								labelWidth:40,
                				layout: 'form',
                				border: false,
                				items:[fechaCheck]
                			    },{
								columnWidth:.2,
								labelWidth:50,
                				layout: 'form',
                				border: false,
                				items:[porcentajeCheck]
                			    },*/
                /***************************cuarta fila*****************************/	
                			    {
						    	columnWidth:1,
                				layout: 'form',
		                		border: false,
        		        		items:[{html:'&nbsp;'} ]
								},{
								columnWidth:.20,
								//labelWidth:50,
								labelAlign:'left',
                				layout: 'form',
                				border: false,
                				items:[{html: '<span>Longitud: </span>'
                				}]
                			   },{
								columnWidth:.40,
								labelWidth:60,
                				layout: 'form',
                				border: false,
                				items:[minimaText]
                			   },{
								columnWidth:.40,
								labelWidth:50,
                				layout: 'form',
                				border: false,
                				items:[maximaText]
                			   },
             /*******************Quinta Fila*************************/
                			   {
						    	columnWidth:1,
                				layout: 'form',
		                		border: false,
        		        		items:[{html:'&nbsp;'} ]
								},{
								columnWidth:.25,
								//labelWidth:50,
                				layout: 'form',
                				border: false,
                				items:[{html: '<span>Procesos </span>'
                				}]
                			   },{
								columnWidth:.25,
								//labelWidth:50,
                				layout: 'form',
                				border: false,
                				items:[{html: '<span>Visible </span>'
                				}]
                			   },{
								columnWidth:.25,
								//labelWidth:50,
                				layout: 'form',
                				border: false,
                				items:[{html: '<span>Actualizable </span>'
                				}]
                			   },{
								columnWidth:.25,
								//labelWidth:50,
                				layout: 'form',
                				border: false,
                				items:[{html: '<span>Obligatorio </span>'
                				}]
                			   }
              /*************************Sexta Fila*********************************/ 
                			   ,
                			   	{
						    	columnWidth:1,
						    	 width:5,
                				layout: 'form',
		                		border: false,
        		        		items:[{html:'&nbsp;'} ]
								},{
								columnWidth:.23,
								//labelWidth:50,
                				layout: 'form',
                				border: false,
                				items:[{html: '<span>Emision: </span>'
                				}]
                			   },{
								columnWidth:.25,
								labelWidth:5,
                				layout: 'form',
                				border: false,
                				labelSeparator:'',
                				items:[emisionVisibleCheck]
                			   },{
								columnWidth:.25,
								labelWidth:10,
                				layout: 'form',
                				border: false,
                				labelSeparator:'',
                				items:[emisionActualizable]
                			   },{
								columnWidth:.25,
								labelWidth:10,
                				layout: 'form',
                				border: false,
                				labelSeparator:'',
                				items:[emisionObligatorio]
                			   },
                			   	{
						    	columnWidth:1,
                				layout: 'form',
		                		border: false,
        		        		items:[{html:'&nbsp;'} ]
								},{
								columnWidth:.22,
								//labelWidth:50,
                				layout: 'form',
                				border: false,
                				items:[{html: '<span>Endosos: </span>'
                				}]
                			   },{
								columnWidth:.25,
								labelWidth:8,
                				layout: 'form',
                				border: false,
                				labelSeparator:'',
                				items:[endosoVisibleCheck]
                			   },{
								columnWidth:.25,
								labelWidth:10,
                				layout: 'form',
                				border: false,
                				labelSeparator:'',
                				items:[endosoActualizableCheck]
                			   },{
								columnWidth:.25,
								labelWidth:10,
                				layout: 'form',
                				border: false,
                				labelSeparator:'',
                				items:[endososObligatorioCheck]
                			   },
             /************************septima fila*************************************/
                			   {
						    	columnWidth:1,
                				layout: 'form',
		                		border: false,
        		        		items:[{html:'&nbsp;'} ]
								},{
								columnWidth:1,
								//labelWidth:40,
                				layout: 'form',
                				border: false,
                				items:[{html: '<span >Agrupación:</span>'}]
                			   },{
								columnWidth:.20,
								labelWidth:40,
                				layout: 'form',
                				border: false,
                				items:[padreCombo]
                			   },{
								columnWidth:.20,
								labelWidth:60,
                				layout: 'form',
                				border: false,
                				items:[ordenText]
                			   },{
								columnWidth:.20,
								labelWidth:60,
                				layout: 'form',
                				border: false,
                				items:[agrupadorText]
                			   },{
								columnWidth:.30,
								labelWidth:50,
                				layout: 'form',
                				border: false,
                				items:[condicionCombo]
                			   },{
								columnWidth:.10,
                				layout: 'form',
                				labelWidth:30,
                				border: false,
                				align:'left',
                				items:[{xtype: 'button',
   									id: 'condicionAgregarButton',
        							text:getLabelFromMap('condicionAgregarButton',helpMap,'Condici&oacute;n'),
        							tooltip: getToolTipFromMap('condicionAgregarButton',helpMap,'Condici&oacute;n'),
        							hidden:true,
        							onClick:function(){
				        		           alert('Si');        		//se agrego parametro
				                     }
        						  }]
                			    },
                			   
             /**************************Ultima Fila**********************************/
                			   {
						    	columnWidth:1,
                				layout: 'form',
		                		border: false,
        		        		items:[{html:'&nbsp;'} ]
								},{
								columnWidth:.15,
								labelWidth:40,
                				layout: 'form',
                				border: false,
                				items:[leyendaCheck]
                			   },{
								columnWidth:.80,
								labelWidth:5,
                				layout: 'form',
                				border: false,
                				items:[leyendaEditorText]
                			   },{
						    	columnWidth:.05,
                				layout: 'form',
		                		border: false,
        		        		items:[{html:'&nbsp;'} ]
								}
                			 ]
                		  }],
                			
                		  buttons:[{
        							text:getLabelFromMap('guardarInstPAgoAgregarButton',helpMap,'Guardar'),
        							tooltip: getToolTipFromMap('guardarInstPAgoAgregarButton',helpMap,'Guarda')
        							
        							},{
        							text:getLabelFromMap('guardarInstPAgoAgregarButton',helpMap,'Guardar y Agregar'),
        							tooltip: getToolTipFromMap('guardarInstPAgoAgregarButton',helpMap,'Guarda y Agrega')
        							
        							},{
        							text:getLabelFromMap('guardarInstPAgoAgregarButton',helpMap,'Eliminar'),
        							tooltip: getToolTipFromMap('guardarInstPAgoAgregarButton',helpMap,'Elimina')
        							
        							},{
        							text:getLabelFromMap('guardarInstPAgoAgregarButton',helpMap,'Valores por defecto'),
        							tooltip: getToolTipFromMap('guardarInstPAgoAgregarButton',helpMap,'Valores por defecto')
        							
        							},{
        							text:getLabelFromMap('guardarInstPAgoAgregarButton',helpMap,'Regresar'),
        							tooltip: getToolTipFromMap('guardarInstPAgoAgregarButton',helpMap,'Regresar')
        							
        							}
        						]
        					}]
        				}]	            
	           
		});
		//aseg_store.load();
		

		formPrincipal.render();
		
		dsNivelCorpo.load();
		
		storeListaValores.load();
		
		storeCondicion.load();
		//console.log(leyendaCheck);
		
		if(leyendaCheck.checked == false)
		{Ext.getCmp('leyendaEditorTextId').setReadOnly(true);}
		
		ordenText.setContainerVisible(false);
		agrupadorText.setContainerVisible(false);
		condicionCombo.setContainerVisible(false);
		leyendaEditorText.setHeight('50');
		//console.log(alfanumericoCheck);
		
	
})