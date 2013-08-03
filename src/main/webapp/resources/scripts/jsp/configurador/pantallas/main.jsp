
Main = function() {
};

Main.prototype = {

    init: function() {

        //inicializando atributos..

        //llama e ext quicktips...
        Ext.QuickTips.init();

        //atributo idCounter (int)
        this.idCounter = 0;
        
        //atributo autoUpdate (boolean)
        this.autoUpdate = true;

        //atributo cookies (CookieProvider)   NOT COOKIES
        //this.cookies = new Ext.state.CookieProvider();

        // llamando a la funcion initResizeLayer...        
        //
        this.initResizeLayer();
        
        // llamando a la funcion initUndoHistory..
        //
        this.initUndoHistory();
        
        //llamando a la funcion initTreepanel..
        //
        this.initTreePanel();
        
        // llamando a la funcion initEditPanel (grid de propiedades)..
        //
        this.initEditPanel();
        
        // llamando a la funcion initComponentsPanel..
        //
        this.initComponentsPanel();

        // llamando a la funcion initLayoutPanel..
        //
        this.initLayoutPanel();
        
        // llamando a la funcion initComponentesProcesoPanel..
        //
        this.initComponentesProcesoPanel();
        
        // creando atributo viewport.....
        //
        this.viewport = new Ext.Viewport({
            layout : 'border',
                        
            listeners: {
            beforerender : function() {
                       getRegistro();
                       storeRegistro.on('load', function(){
                       if(storeRegistro.getTotalCount()==null || storeRegistro.getTotalCount()==""){
                       }else{
                       var record = storeRegistro.getAt(0);
                       formPanelParam.getForm().loadRecord(record);
                       cdProducto.setValue(record.get('cdProducto'));
                       cdCliente.setValue(record.get('cdCliente'));
                       cdProcesoActivacion.setValue(record.get('cdProceso'));
                       cdProductoActivacion.setValue(record.get('cdProducto'));
                       cdProceso.setValue(record.get('cdProceso'));
                       claveSituacion.setValue(record.get('dsTipoSituacion'));
                       Ext.getCmp('btnEliminar').disable();
                       Ext.getCmp('btnNPantalla').disable();
                       Ext.getCmp('btnVistaPrevia').disable();
                            if(record.get('cdConjunto')==null || record.get('cdConjunto')=="")
                            {
                                comboProceso.enable();
                                comboNivel.enable();
                                comboProducto.enable();
                                comboTipoSituacion.enable();
                                
                                formPanelParam.getForm().reset();
                                formPanelDatos.getForm().reset();
                                formPanelActivacion.form.reset();
                                Ext.getCmp('btnCopiarConjunto').disable();
                                comboTipoPantalla.clearValue();
                                storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
   	                            storeTipoPantalla.load({
                                        callback : function(r,options,success) {
                                            if (!success) {
                                             //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                                            storeProducto.removeAll();
                                            }  }
                                      });
                                
                                Ext.getCmp('btnAceptarActivacion').disable();
                                Ext.getCmp('btnSalvarPantalla').disable();
                               
                                
                             }else{
                                comboProceso.disable();
                                comboNivel.disable();
                                comboProducto.disable();
                                comboTipoSituacion.disable();
                                
                                Ext.getCmp('btnCopiarConjunto').enable();
                                 comboTipoPantalla.clearValue();
                                storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
                                storeTipoPantalla.load({
                                        callback : function(r,options,success) {
                                            if (!success) {                                               
                                            	storeProducto.removeAll();
                                            }  
                                        }
                                      });
                                
                                Ext.getCmp('btnAceptarActivacion').enable();
                                Ext.getCmp('btnSalvarPantalla').enable();
                                
                            }
                            
            }//else getTotalCount not null                
         });
  
       }//beforerender
       },
            
            
            
            
                items: [                
                    //zona norte.......
                    paramEntradaPanel
                	
                ,
                {
                    // west zone, agrega treePanel y grid de propiedades
                    region: 'west',
                    border: false,
                    width : 255,
                    split : true,
                    xtype : 'panel',
                    layout: 'accordion',                    
                    title: MENU_TITLE,
        			heigth:'500',
        			minSize:'200',        			
        			margins:'0 5 0 5',
        			bodyStyle:'padding:5px',
        			collapsible: true,        			
        			layoutConfig:{
             			animate:true
        			},     
                    
                    
                    items : [
                        this.treePanel,
                        this.editPanel,
                    	this.componentsPanel,
                    	this.layoutPanel,
                    	this.componentesProcesoPanel,
                        navegacionPantallasPanel
                        
                    ]
                },
                    
                    
                {
                    region:'center',
                    layout:'fit',
                    border:false,
                    bodyBorder:false,
                    style:'padding:3px 5px;background:black',
                    items:{border:false,bodyBorder:false,bodyStyle:'background:black;border:dashed green 1px;',layout:'fit',id:'FBBuilderPanel'}
                }
                ]
            });  /// termina view port            
            ////////////////////
            
            //inicializando builderPanel
            this.builderPanel = Ext.ComponentMgr.get('FBBuilderPanel');
            
            // creando variable root con el root de treePanel...
            var root = this.treePanel.root;
            
            //al atributo fEL le asignamos el builderPanel...
            root.fEl = this.builderPanel;
            
            //a la variable root le asignamos a elConfig el valor de this.builderPanel.initialConfig..
            root.elConfig = this.builderPanel.initialConfig;
            
            this.builderPanel._node = root;

            var drop = new Ext.dd.DropZone(main.builderPanel.el, {
                    ddGroup:'component',
                    notifyOver : function(src,e,data) {
                            var node = main.getNodeForEl(e.getTarget());
                            var _node = data.node;

                            //console.log("en notifyOver: %s", data.node.id);
                            //console.log(src);console.log(e);console.log(data);
                            if (!Ext.getCmp(data.node.id)) {
                            	//console.log("campo existente");
                            	//return this.dropNotAllowed;
	                            if (node) {
	                                data.node = node; node.select();
	                                this.highlightElement(node.fEl.el);
	                                data.node = _node;
	                                if (this.canAppend({}, node) === true) {
	                                    return true;
	                                } else {
	                                    data.node = null;
	                                    return false;
	                                }
	                            } else {
	                                data.node = null;
	                                return false;
	                            }
                            }
                        }.createDelegate(this),
                    notifyDrop : function(src,e,data) {
                            //if (!data.node || !data.compData) { return; }
                    		if (data.handles) {
								var clveJson = Ext.getCmp('treeProcesosMaster').getSelectionModel().selNode.attributes.item;
                    			//if (!Ext.getCmp(clveJson.id)) {
									var n1 = this.currentNode;
									var newNode = main.appendConfig(  clveJson, n1, true, true);
                    			//}else return false;
                    		} else {
	                            var c = data.compData.config;
	                            //console.log("c es:");console.log(c);
	                            if (typeof c == 'function') {
	                                c.call(this,function(config) {
	                                	//console.log("config es:"); console.log(config);
	                                    var n = this.appendConfig(config, data.node, true, true);
	                                    this.setCurrentNode(n, true);
	                                }, data.node.elConfig);
	                                return true;
	                            } else {
	                                var n = this.appendConfig(this.cloneConfig(data.compData.config), data.node, true, true);
	                                this.setCurrentNode(n, true);
	                            }
                            }
	                           return true;
                        }.createDelegate(this),
                    notifyOut : function(src,e,data) {
                            data.node = null;
                        }
                }); /// end of variable drop...
                /////////////////////
            //console.log(drop);
            drop.el.on ('nodeover', function(node, source, event, data) {
            	//s.log("nodeover");
            	return drop.dropNotAllowed;
            });
            main.builderPanel.drop = drop;

  
            ////////////////REVISAR ESTE ELEMENTO
            /*
            Ext.ComponentMgr.get('FBAutoUpdateCB').on('check', function(c) {
                this.autoUpdate = c.checked;
            }, this);
*/


            this.treePanel.el.on('contextmenu', function(e) {
                e.preventDefault();
            });

            //NOT COOKIES
            //if (!this.loadConfigFromCookies()) {
            	this.resetAll(); 
            //}

            // select elements on form with single click
            this.builderPanel.el.on('click', function(e,el) {
                    
                	    
            	
                    e.preventDefault();
                    var node = this.getNodeForEl(el);
                    if (!node) { node = this.treePanel.root; }
                    this.highlightElement(node.fEl.el);
                    this.setCurrentNode(node, true);
                }, this);
            ///////////////////////////////    
                
            // menu on form elements
            this.builderPanel.el.on('contextmenu', function(e,el) {
                    e.preventDefault();
                    var node = this.getNodeForEl(el);
                    if (!node) { return; }
                    this.highlightElement(node.fEl.el);
                    this.setCurrentNode(node, true);
                    this.contextMenu.node = node;
                    this.contextMenu.showAt(e.getXY());
                }, this);
                ///////
                
    },
///////////end  of function init..
///////////////////////


    // the tree panel, listing elements
    initTreePanel : function() {
        var tree = new Ext.tree.TreePanel({
            region          : 'north',
            title           : MENU_ARBOL_TITLE,
            iconCls         : "icon-el",
            collapsible     : true,
            floatable       : false,
            autoScroll      : true,
            height          : 200,
            split           : true,
            animate         : false,
            enableDD        : true,
            ddGroup         : 'component',
            containerScroll : true,
            selModel        : new Ext.tree.DefaultSelectionModel(),
            bbar            : [{
                text    : MENU_ARBOL_EXPANDIR,
                tooltip : MENU_ARBOL_EXPANDIR_TOOLTIP,
                scope   : this,
                handler : function() { this.treePanel.expandAll(); }
            },{
                text    : MENU_ARBOL_CONTRAER,
                tooltip : MENU_ARBOL_CONTRAER_TOOLTIP,
                scope   : this,
                handler : function() { this.treePanel.collapseAll(); }
            }/*,{
                // este boton el handler invoca al atributo undo...                        
                id      : 'FBUndoBtn',
                iconCls : 'icon-undo',
                text    : 'Deshacer',
                disabled: true,
                tooltip : "Deshacer ultimo cambio",
                handler : this.undo,
                scope   : this
            }*/]
        });

    var root = new Ext.tree.TreeNode({
        text      : MENU_ARBOL_RAIZ,
        id        : this.getNewId(),
        draggable : false
    });
    tree.setRootNode(root);

        tree.on('click', function(node, e) {
            e.preventDefault();
            if (!node.fEl || !node.fEl.el) { return; }
            this.highlightElement(node.fEl.el);
            this.setCurrentNode(node);
            window.node = node; // debug
        }, this);

        // clone a node
        var cloneNode = function(node) {
            var config = Ext.apply({}, node.elConfig);
            delete config.id;
            var newNode = new Ext.tree.TreeNode({id:this.getNewId(),text:this.configToText(config)});
            newNode.elConfig = config;

            // clone children
            for(var i = 0; i < node.childNodes.length; i++){
                n = node.childNodes[i];
                if(n) { newNode.appendChild(cloneNode(n)); }
            }

            return newNode;

        }.createDelegate(this);

        // assert node drop
        tree.on('nodedragover', function(de) {
            var p = de.point, t= de.target;
            if(p == "above" || t == "below") {
                    t = t.parentNode;
            }
            if (!t) { return false; }
            this.highlightElement(t.fEl.el);
            return (this.canAppend({}, t) === true);
        }, this);

        // copy node on 'ctrl key' drop
        tree.on('beforenodedrop', function(de) {
                if (!de.rawEvent.ctrlKey) {
                    this.markUndo("Moved " + de.dropNode.text);
                    return true;
                }
                this.markUndo("Copied " + de.dropNode.text);
        var ns = de.dropNode, p = de.point, t = de.target;
        if(!(ns instanceof Array)){
            ns = [ns];
        }
        var n;
        for(var i = 0, len = ns.length; i < len; i++){
                        n = cloneNode(ns[i]);
            if(p == "above"){
                t.parentNode.insertBefore(n, t);
            }else if(p == "below"){
                t.parentNode.insertBefore(n, t.nextSibling);
            }else{
                t.appendChild(n);
            }
        }
        n.ui.focus();
        if(de.tree.hlDrop){ n.ui.highlight(); }
        t.ui.endDrop();
        de.tree.fireEvent("nodedrop", de);
                return false;
            }, this);

        // update on node drop
        tree.on('nodedrop', function(de) {
            var node = de.target;
            if (de.point != 'above' && de.point != 'below') {
                node = node.parentNode || node;
            }
            this.updateForm(false, node);
        }, this, {buffer:100});

        // get first selected node
        tree.getSelectedNode = function() {
            return this.selModel.getSelectedNode();
        };

        // context menu to delete / duplicate...
        var contextMenu = new Ext.menu.Menu({items:[{
                text    : 'Eliminar este elemento',
                iconCls : 'icon-deleteEl',
                scope   : this,
                handler : function(item) {
                        this.removeNode(contextMenu.node);
                    }
            },{
                text    : 'Agregar nuevo elemento como hijo',
                iconCls : 'icon-addEl',
                scope   : this,
                handler : function(item) {
                        var node = this.appendConfig({}, contextMenu.node, true, true);
                        this.treePanel.expandPath(node.getPath());
                    }
            },{
                text    : 'Agregar nuevo elemento debajo',
                iconCls : 'icon-addEl',
                scope   : this,
                handler : function(item) {
                        var node = this.appendConfig({}, contextMenu.node.parentNode, true, true);
                        this.treePanel.expandPath(node.getPath());
                    }
            },{
                text    : 'Duplicar este elemento',
                iconCls : 'icon-dupEl',
                scope   : this,
                handler : function(item) {
                        var node = contextMenu.node;
                        this.markUndo("Duplicate " + node.text);
                        var newNode = cloneNode(node);
                        if (node.isLast()) {
                            node.parentNode.appendChild(newNode);
                        } else {
                            node.parentNode.insertBefore(newNode, node.nextSibling);
                        }
                        this.updateForm(false, node.parentNode);
                    }
            },{
                text    : 'Modificar tama&ntilde;o',
                tooltip : 'Modifica el tama&ntilde;o del elemento.<br/>Tambi&eacute;n se puede mover si est&aacute; dentro de un <b>absolute</b> layout',
                iconCls : 'icon-resize',
                scope   : this,
                handler : function(item) {
                        this.visualResize(contextMenu.node);
                    }
            },{
    text    : 'Editar el contenido',
    iconCls : 'icon-dupEl',
    scope   : this,
    handler : function(item) {
     var node = contextMenu.node;
     this.markUndo("Editar " + node.text);
     this.editarContenido(node);
    }
   }
            
            
            ]});
        tree.on('contextmenu', function(node, e) {
                e.preventDefault();
                if (node != this.treePanel.root) {
                    contextMenu.node = node;
                    contextMenu.showAt(e.getXY());
                }
            }, this);
        this.contextMenu = contextMenu;

        this.treePanel = tree;
    },
    //// end of init panel..
    ///////////////////////////////
    

    // layer used for selection resize
    initResizeLayer : function() {

        this.resizeLayer = new Ext.Layer({cls:'resizeLayer',html:'Resize me'});
        this.resizeLayer.setOpacity(0.5);
        this.resizeLayer.resizer = new Ext.Resizable(this.resizeLayer, {
            handles:'all',
            draggable:true,
            dynamic:true});
        this.resizeLayer.resizer.dd.lock();
        this.resizeLayer.resizer.on('resize', function(r,w,h) {
                var n = this.editPanel.currentNode;
                if (!n || !n.elConfig) { return false; }
                this.markUndo("Resize element to " + w + "x" + h);
                var s = n.fEl.el.getSize();
                if (s.width != w) {
                    n.elConfig.width = w;
                    if (n.parentNode.elConfig.layout == 'column') {
                        delete n.elConfig.columnWidth;
                    }
                }
                if (s.height != h) {
                    n.elConfig.height = h;
                    delete n.elConfig.autoHeight;
                }
                this.updateForm(true, n.parentNode);
                this.setCurrentNode(n);
                this.highlightElement(n.fEl.el);
            }, this);
        this.resizeLayer.resizer.dd.endDrag = function(e) {
                var n = this.editPanel.currentNode;
                if (!n || !n.elConfig) { return false; }
                var pos = this.resizeLayer.getXY();
                var pPos = n.parentNode.fEl.body.getXY();
                var x = pos[0] - pPos[0];
                var y = pos[1] - pPos[1];
                this.markUndo("Move element to " + x + "x" + y);
                n.elConfig.x = x;
                n.elConfig.y = y;
                this.updateForm(true, n.parentNode);
                this.setCurrentNode(n);
                this.highlightElement(n.fEl.el);
        }.createDelegate(this);

    },
    /////end of initResizeLayer
    ///////////////////

    // customized property grid for attributes
    initEditPanel : function() {
		/* Codigo para el combo del Tab Parametros que agrega propiedades:*/
        var fields = [];
        for (var i in Main.FIELDS) {fields.push([i,i]);}
        var newPropertyField = new Ext.form.ComboBox({
                mode           : 'local',
                valueField     : 'value',
                displayField   : 'name',
                store          : new Ext.data.SimpleStore({
                        sortInfo : {field:'name',order:'ASC'},
                        fields   : ['value','name'],
                        data     : fields
                    })});
        newPropertyField.on('specialkey', function(tf,e) {
            var name = tf.getValue();
            var ds = this.editPanel.store;
            if (e.getKey() == e.ENTER && name != '' && !ds.getById(name)) {
                var defaultVal = "";
                if (this.attrType(name) == 'object') { defaultVal = "{}"; }
                if (this.attrType(name) == 'number') { defaultVal = 0; }
                ds.add(new Ext.grid.PropertyRecord({name:name, value:defaultVal}, name));
                this.editPanel.startEditing(ds.getCount()-1, 1);
                tf.setValue('');
            }
        }, this);

		Ext.grid.PropertyColumnModel.prototype.nameText = 'Nombre';
		Ext.grid.PropertyColumnModel.prototype.valueText = 'Valor';
		
        var grid = new Ext.grid.PropertyGrid({
                title            : MENU_PARAMETROS_TITLE,
                height           : 300,
                split            : true,
                region           : 'center',
                source           : {},
                bbar             : ['Agregar :', newPropertyField ],
                newPropertyField : newPropertyField,
                customEditors    : Main.getCustomEditors()
            });

        var valueRenderer = function(value, p, r) {
            if (typeof value == 'boolean') {
                p.css = (value ? "typeBoolTrue" : "typeBoolFalse");
                return (value ? "True" : "False");
            } else if (this.attrType(r.id) == 'object') {
                p.css = "typeObject";
                return value;
            } else {
                return value;
            }
        }.createDelegate(this);
        var propertyRenderer = function(value, p) {
            var t = Main.FIELDS[value];
            qtip = (t ? t.desc : '');
            p.attr = 'qtip="' + qtip.replace(/"/g,'&quot;') + '"';
            return value;
        };
        grid.colModel.getRenderer = function(col){
            return (col == 0 ? propertyRenderer : valueRenderer);
        };

        var contextMenu = new Ext.menu.Menu({items:[{
                id      : 'FBMenuPropertyDelete',
                iconCls : 'icon-delete',
                text    : 'Eliminar esta propiedad',
                scope   : this,
                handler : function(item,e) {
                        this.markUndo('Delete property <i>' + item.record.id + '</i>');
                        var ds = grid.store;
                        delete grid.getSource()[item.record.id];
                        ds.remove(item.record);
                        delete item.record;
                        this.updateNode(grid.currentNode);
                        var node = grid.currentNode.parentNode || grid.currentNode;
                        this.updateForm.defer(200, this, [false, node]);
                    }
            }]});

        // property grid contextMenu
        grid.on('rowcontextmenu', function(g, idx, e) {
                e.stopEvent();
                var r = this.store.getAt(idx);
                if (!r) { return false; }
                var i = contextMenu.items.get('FBMenuPropertyDelete');
                i.setText('Eliminar propiedad "' + r.id + '"');
                i.record = r;
                contextMenu.showAt(e.getXY());
            }, grid);


        // update node text & id
        grid.store.on('update', function(s,r,t) {
            if (t == Ext.data.Record.EDIT) {
                this.markUndo('Set <i>' + r.id + '</i> to "' +
                    Ext.util.Format.ellipsis((String)(r.data.value), 20) + '"');
                var node = grid.currentNode;
                this.updateNode(grid.currentNode);
                this.updateForm(false, node.parentNode || node);
            }
        }, this, {buffer:100});

        this.editPanel = grid;

    },

    // Components panel
    initComponentsPanel : function() {

        // components; config is either an object, or a function called with the adding function and parent config
        var data = ScreenCommonsComponents.getComponents();
        var ds = new Ext.data.SimpleStore({
            fields: ['category','name','description','config'],
            data : data
        });
        var tpl = new Ext.XTemplate(
            '<ul id="FormBuilderComponentSelector">',
            '<tpl for=".">',
                '<li class="component" qtip="{description}">{name}</li>',
            '</tpl>',
            '<div class="x-clear"></div>',
            '</ul>');
        var view2 = new Ext.DataView({
            store        : ds,
            tpl          : tpl,
            overClass    : 'over',
            selectedClass: 'selected',
            singleSelect : true,
            itemSelector : '.component'
        });
        view2.on('dblclick', function(v,idx,node,e) {
                e.preventDefault();
                var n = this.editPanel.currentNode;
                if (!n) { return false; }
                var c = view2.store.getAt(idx).data.config;
                if (!c) { return false; }
                if (typeof c == 'function') {
                    c.call(this,function(config) {
                        var newNode = this.appendConfig(config, n, true, true);
                    }, n.elConfig);
                } else {
                    var newNode = this.appendConfig(this.cloneConfig(c), n, true, true);
                }
            }, this);
        view2.on('render', function() {
                var d = new Ext.dd.DragZone(view2.el, {
                        ddGroup         : 'component',
                        containerScroll : true,
                        getDragData     : function(e) {
                                view2.onClick(e);
                                var r = view2.getSelectedRecords();
                                if (r.length == 0) { return false; }
                                var el = e.getTarget('.component');
                                if (el) { return {ddel:el,compData:r[0].data}; }
                            },
                        getTreeNode : function(data, targetNode) {
                                if (!data.compData) { return null; }

                                var c = data.compData.config;
                                if (typeof c == 'function') {
                                    c.call(this,function(config) {
                                        var n = this.appendConfig(config, targetNode, true, true);
                                        this.setCurrentNode(n, true);
                                    }, targetNode.elConfig);
                                } else {
                                    var n = this.appendConfig(this.cloneConfig(data.compData.config), targetNode, true, true);
                                    this.setCurrentNode(n, true);
                                    return n;
                                }
                                return null;

                            }.createDelegate(this)
                    });
                view2.dragZone = d;
            }, this);
		/* submenu de elementos comunes:
        var filter = function(b) { ds.filter('category', new RegExp(b.text)); };
        var tb = ['<b>'+ MENU_COMUNES_CATEGORIAS +':</b>', {
                text         : MENU_COMUNES_TODAS,
                toggleGroup  : 'categories',
                enableToggle : true,
                pressed      : true,
                scope        : ds,
                handler      : ds.clearFilter
            }, '-'];
        var cats = [];
        ds.each(function(r) {
            var tokens = r.data.category.split(",");
            Ext.each(tokens, function(token) {
                if (cats.indexOf(token) == -1) {
                    cats.push(token);
                }
            });
        });
        Ext.each(cats, function(v) {
            tb.push({
                    text         : v,
                    toggleGroup  : 'categories',
                    enableToggle : true,
                    handler      : filter
                });
            });
		*/
            
        var panel = new Ext.Panel({            
            height:100,
            layout:'fit',
            autoScroll:true,
            items:[view2],
            title: MENU_COMUNES_TITLE,
            border:false
            // submenu de elementos comunes:
            //,tbar:tb
        });
        
       
        
       

        panel.view2 = view2;
        this.componentsPanel = panel;

    },
    
        
    // Elementos Layout
    initLayoutPanel : function() {
    	// components; config is either an object, or a function called with the adding function and parent config
        var data = ScreenLayoutComponents.getComponents();
        var ds = new Ext.data.SimpleStore({
            fields: ['category','name','description','config'],
            data : data
        });
        var tpl = new Ext.XTemplate(
            '<ul id="FormBuilderComponentSelector">',
            '<tpl for=".">',
                '<li class="component" qtip="{description}">{name}</li>',
            '</tpl>',
            '<div class="x-clear"></div>',
            '</ul>');
            
        var viewLayouts = new Ext.DataView({
            store        : ds,
            tpl          : tpl,
            overClass    : 'over',
            selectedClass: 'selected',
            singleSelect : true,
            itemSelector : '.component'
        });
        viewLayouts.on('dblclick', function(v,idx,node,e) {
                e.preventDefault();
                var n = this.editPanel.currentNode;
                if (!n) { return false; }
                var c = viewLayouts.store.getAt(idx).data.config;
                
                if (!c) { return false; }
                if (typeof c == 'function') {
                    c.call(this,function(config) {
                        var newNode = this.appendConfig(config, n, true, true);
                    }, n.elConfig);
                } else {
                    var newNode = this.appendConfig(this.cloneConfig(c), n, true, true);
                }
            }, this);
        viewLayouts.on('render', function() {
                var d = new Ext.dd.DragZone(viewLayouts.el, {
                        ddGroup         : 'component',
                        containerScroll : true,
                        getDragData     : function(e) {
                                viewLayouts.onClick(e);
                                var r = viewLayouts.getSelectedRecords();
                                if (r.length == 0) { return false; }
                                var el = e.getTarget('.component');
                                if (el) { return {ddel:el,compData:r[0].data}; }
                            },
                        getTreeNode : function(data, targetNode) {
                                if (!data.compData) { return null; }

                                var c = data.compData.config;
                                if (typeof c == 'function') {
                                    c.call(this,function(config) {
                                        var n = this.appendConfig(config, targetNode, true, true);
                                        this.setCurrentNode(n, true);
                                    }, targetNode.elConfig);
                                } else {
                                    var n = this.appendConfig(this.cloneConfig(data.compData.config), targetNode, true, true);
                                    this.setCurrentNode(n, true);
                                    return n;
                                }
                                return null;

                            }.createDelegate(this)
                    });
                viewLayouts.dragZone = d;
            }, this);

        var filter = function(b) { ds.filter('category', new RegExp(b.text)); };
        var tb = ['<b>' + MENU_LAYOUTS_CATEGORIAS +' : </b>', {
                text         : MENU_LAYOUTS_TODAS,
                toggleGroup  : 'categories',
                enableToggle : true,
                pressed      : true,
                scope        : ds,
                handler      : ds.clearFilter
            }, '-'];
        var cats = [];
        ds.each(function(r) {
            var tokens = r.data.category.split(",");
            Ext.each(tokens, function(token) {
                if (cats.indexOf(token) == -1) {
                    cats.push(token);
                }
            });
        });
        Ext.each(cats, function(v) {
            tb.push({
                    text         : v,
                    toggleGroup  : 'categories',
                    enableToggle : true,
                    handler      : filter
                });
            });
        
       var panelLayouts = new Ext.Panel({
   		border:false,
        title: MENU_LAYOUTS_TITLE,
        autoScroll: true,
        height:100,
        layout:'fit',
        items:[viewLayouts],
        tbar:tb
   		});
   		
        panelLayouts.viewLayouts = viewLayouts;
        this.layoutPanel = panelLayouts;
    },
    
    // Componentes de Proceso panel
    initComponentesProcesoPanel : function() {
    	// components; config is either an object, or a function called with the adding function and parent config
        /*
        var data = ScreenProcesoComponents.getComponents();
        var ds = new Ext.data.SimpleStore({
            fields: ['category','name','description','config'],
            data : data
        });
        var tpl = new Ext.XTemplate(
            '<ul id="FormBuilderComponentSelector">',
            '<tpl for=".">',
                '<li class="component" qtip="{description}">{name}</li>',
            '</tpl>',
            '<div class="x-clear"></div>',
            '</ul>');
            
        var viewProceso = new Ext.DataView({
            store        : ds,
            tpl          : tpl,
            overClass    : 'over',
            selectedClass: 'selected',
            singleSelect : true,
            itemSelector : '.component'
        });
        viewProceso.on('dblclick', function(v,idx,node,e) {
                e.preventDefault();
                var n = this.editPanel.currentNode;
                if (!n) { return false; }
                var c = viewProceso.store.getAt(idx).data.config;
                if (!c) { return false; }
                if (typeof c == 'function') {
                    c.call(this,function(config) {
                        var newNode = this.appendConfig(config, n, true, true);
                    }, n.elConfig);
                } else {
                    var newNode = this.appendConfig(tXhis.cloneConfig(c), n, true, true);
                }
            }, this);
        viewProceso.on('render', function() {
                var d = new Ext.dd.DragZone(viewProceso.el, {
                        ddGroup         : 'component',
                        containerScroll : true,
                        getDragData     : function(e) {
                                viewProceso.onClick(e);
                                var r = viewProceso.getSelectedRecords();
                                if (r.length == 0) { return false; }
                                var el = e.getTarget('.component');
                                if (el) { return {ddel:el,compData:r[0].data}; }
                            },
                        getTreeNode : function(data, targetNode) {
                                if (!data.compData) { return null; }

                                var c = data.compData.config;
                                if (typeof c == 'function') {
                                    c.call(this,function(config) {
                                        var n = this.appendConfig(config, targetNode, true, true);
                                        this.setCurrentNode(n, true);
                                    }, targetNode.elConfig);
                                } else {
                                    var n = this.appendConfig(this.cloneConfig(data.compData.config), targetNode, true, true);
                                    this.setCurrentNode(n, true);
                                    return n;
                                }
                                return null;

                            }.createDelegate(this)
                    });
                viewProceso.dragZone = d;
            }, this);

        var filter = function(b) { ds.filter('category', new RegExp(b.text)); };
        var tb = ['<b>' + MENU_PROCESO_CATEGORIAS + ' : </b>', {
                text         : MENU_PROCESO_TODAS,
                toggleGroup  : 'categories',
                enableToggle : true,
                pressed      : true,
                scope        : ds,
                handler      : ds.clearFilter
            }, '-'];
        var cats = [];
        ds.each(function(r) {
            var tokens = r.data.category.split(",");
            Ext.each(tokens, function(token) {
                if (cats.indexOf(token) == -1) {
                    cats.push(token);
                }
            });
        });
        Ext.each(cats, function(v) {
            tb.push({
                    text         : v,
                    toggleGroup  : 'categories',
                    enableToggle : true,
                    handler      : filter
                });
            });
        
       var panelProcesos = new Ext.Panel({
   		border:false,
        title: MENU_PROCESO_TITLE,
        autoScroll: true,
        height:100,
        layout:'fit',
        items:[viewProceso],
        tbar:tb
   		});
   		*/
   		
    	var treeProcesosMasterPanel = new Ext.Panel({
	border : false,
	title : 'Elementos de Proceso',
	autoScroll : true,
	items : [{
		xtype : 'treepanel',
		id : 'treeProcesosMaster',
		//width : 190,
		autoWidth : true,
		border : false,
		autoScroll : true,
		split : true,
		clearOnLoad : true,
		lines : false,
		enableDD : true,
        //dropConfig: {appendOnly:true},
        dragConfig: {ddGroup: "component", appendOnly:true},
        containerScroll: true,
		loader : new Ext.tree.TreeLoader({
			dataUrl : 'configuradorpantallas/cargarMaster.action'
		}),
		
		root : new Ext.tree.AsyncTreeNode({
					text : 'Menu Procesos Master',
					draggable : false, // TODO: REVISAR PARA HACER DRAG AND DROP DE LOS ELEMENTOS...
					id : 'wizard-master-item'
		}),
		rootVisible : false,
		listeners : {
			
			dblclick : function(node, event) {
				//var clve = node.attributes.id;
				//alert('clve ' + clve);
				
				var clveJson = node.attributes.item;
				//alert('json ' + clveJson );
				
				//alert( 'encode ... ' + Ext.util.JSON. encode(clveJson)  );
				
				//var  encodeJson =  Ext.util.JSON. encode(clveJson);
				//alert('json text ' + encodeJson );
				//alert('node is ' + node);
				//alert('json items ' + encodeJson.items );
				//alert('json length ' + encodeJson.length );
				
				//var clveOtro = node.attributes.otro;
				//alert('clve ' + clveOtro);

				var n1 = this.currentNode;
				//this.appendConfig(  this.cloneConfig(node.attributes.item ), node, true, true);
				if (!Ext.getCmp(clveJson.id)) {
					var newNode = main.appendConfig(  clveJson, n1, true, true);
				}

			}
			/*
			dblclick : function(n) {
				clavePantalla = n.attributes.id;

				getRegistroPantalla(clavePantalla);
				storeRegistroPantalla.on('load', function() {
					
					if (storeRegistroPantalla.getTotalCount() == null || storeRegistroPantalla.getTotalCount() == "") {
					
					} else {
						var recordP = storeRegistroPantalla.getAt(0);
						formPanelDatos.getForm().loadRecord(recordP);
						cdTipoMaster.setValue(recordP.get('htipoPantalla'));
						if (recordP.get('cdPantalla') == null	|| recordP.get('cdPantalla') == "") {
							comboTipoPantalla.enable();
							formPanelDatos.getForm().reset();
							Ext.getCmp('btnEliminar').disable();
							Ext.getCmp('btnNPantalla').disable();
							Ext.getCmp('btnVistaPrevia').disable();
						} else {
						
							comboTipoPantalla.disable();
							comboTipoPantalla.setHeight(25);//Para corregir el error cuando el combo se oculta inesperadamente
							Ext.getCmp('btnEliminar').enable();
							Ext.getCmp('btnNPantalla').enable();
							Ext.getCmp('btnVistaPrevia').enable();

							storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
							storeTipoPantalla.load({

										callback : function(r, options, success) {
											if (!success) {
												//  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
												storeProducto.removeAll();
											}
											//   comboTipoPantalla.setValue(recordP.get('htipoPantalla'));
										}

									});
						}

						//SI dsArchivo TRAE ELEMENTOS, LOS CARGAMOS EN EL EDITOR
						if (dsArchivo.getValue() != "{}") {
							var configuracion = null;
							configuracion = Ext.decode(dsArchivo.getValue());
							main.setConfig({
										items : [configuracion]
							});
						}
						//SINO, DEJAMOS VACIO EL EDITOR
						else {
							main.setConfig( { items : []	} );
						}
					}//else totalcount

				});
			}
			
			*/
			
		}// end listeners

	}]

});

/*
        treeProcesosMasterPanel.on('beforenodedrop', function(de) {
        		alert("before");
                if (!de.rawEvent.ctrlKey) {
                    this.markUndo("Moved " + de.dropNode.text);
                    return true;
                }
                this.markUndo("Copied " + de.dropNode.text);
        var ns = de.dropNode, p = de.point, t = de.target;
        if(!(ns instanceof Array)){
            ns = [ns];
        }
        var n;
        for(var i = 0, len = ns.length; i < len; i++){
                        n = cloneNode(ns[i]);
            if(p == "above"){
                t.parentNode.insertBefore(n, t);
            }else if(p == "below"){
                t.parentNode.insertBefore(n, t.nextSibling);
            }else{
                t.appendChild(n);
            }
        }
        n.ui.focus();
        if(de.tree.hlDrop){ n.ui.highlight(); }
        t.ui.endDrop();
        de.tree.fireEvent("nodedrop", de);
                return false;
            }, this);

        // update on node drop
        treeProcesosMasterPanel.on('nodedrop', function(de) {
            var node = de.target;
            if (de.point != 'above' && de.point != 'below') {
                node = node.parentNode || node;
            }
            this.updateForm(false, node);
        }, this, {buffer:100});

*/

    	
   		
    //    panelProcesos.viewProceso = viewProceso;
      //  this.componentesProcesoPanel = panelProcesos;

	this.componentesProcesoPanel = treeProcesosMasterPanel;
        
    },    

    // Undo history
    initUndoHistory : function() {
        this.undoHistoryMax = 20;
        this.undoHistory = [];
    },

    // add current config to undo
    markUndo : function(text) {
        this.undoHistory.push({text:text,config:this.getTreeConfig()});
        if (this.undoHistory.length > this.undoHistoryMax) {
            this.undoHistory.remove(this.undoHistory[0]);
        }
        this.updateUndoBtn();
    },

    // update undo button according to undo history
    updateUndoBtn : function() {
        if (this.undoHistory.length == 0) {
        	//////////REVISAR ESTE ELEMENTO
            Ext.ComponentMgr.get('FBUndoBtn').disable().setText('Deshacer');
        } else {
        	//////////REVISAR ESTE ELEMENTO
            Ext.ComponentMgr.get('FBUndoBtn').enable().setText('<b>Deshacer</b> : ' + this.undoHistory[this.undoHistory.length-1].text);
        }
    },

    // undo last change
    undo : function() {
        var undo = this.undoHistory.pop();
        this.updateUndoBtn();
        if (!undo || !undo.config) { return false; }
        this.setConfig(undo.config);
        return true;
    },

    // return the node corresponding to an element (search upward)
    getNodeForEl : function(el) {
        var search = 0;
        var target = null;
        while (search < 10) {
            target = Ext.ComponentMgr.get(el.id);
            if (target && target._node) {
                return target._node;
            }
            el = el.parentNode;
            if (!el) { break; }
            search++;
        }
        return null;
    },

    // show the layer to visually resize / move element 
    visualResize : function(node) {
        if (node == this.treePanel.root || !node || !node.fEl) { return; }
        if (node.parentNode && node.parentNode.elConfig && node.parentNode.elConfig.layout == 'fit') {
            Ext.Msg.alert("Error", "You won't be able to resize an element" +
                " contained in a 'fit' layout.<br/>Update the parent element instead.");
        } else {
            if (node.parentNode && node.parentNode.elConfig && node.parentNode.elConfig.layout == 'absolute') {
                this.resizeLayer.resizer.dd.unlock();
                this.resizeLayer.resizer.dd.constrainTo(node.parentNode.fEl.body);
            } else {
                this.resizeLayer.resizer.dd.lock();
            }
            var elem = this.resizeLayer.setBox(node.fEl.el.getBox());
            this.resizeLayer.show();
            if (node.elConfig && node.elConfig.xtype) {
            	if (node.elConfig.xtype == "combo") {
            		node.elConfig.listWidth = elem.width;
					//node.list.setWidth(elem.width);
					//node.innerList.setWidth(elem.width - node.list.getFrameWidth("lr"))
            	}
            }
        }
    },
    
    
    
    // editar contenido
 editarContenido : function(node) {
  var w = new Ext.Window({
   width:740,
   height:500,
   title:"Editar Contenido",
   items:[{
             xtype:"form",
             items:[{
              xtype:"htmleditor",
              fieldLabel:"Contenido",
              id:  "etiqueta",
              name:"etiqueta",                              
              height:200, 
              anchor:'98%',
              //Contenido inicial de la ventana sera el del componente que se esta editando:
              value: node.elConfig.html//((node.elConfig.html)?node.elConfig.html:node.elConfig.fieldLabel)
    }]
   }] , 
   buttons:[
    {
     text:'Ok',
     scope:this,
     handler:function() {
      var values = w.items.first().form.getValues();
      //Asignar el contenido del editor al componente que se esta editando:
      if (node.elConfig.html) {
      	node.elConfig.html = values.etiqueta;
      }else {
      	node.elConfig.fieldLabel = values.etiqueta;
      }
      //actualizar el formulario para que se vean los cambios:
      //this.updateForm.defer(200, this, [false, node]);
      this.updateForm(true, node);
      w.close();
     }
    },{
     text:'Cancel',
     handler:function() {
      w.close();
     }
    }
   ]
  });
  w.show();
 },
 /////editarContenido
 ///////////////
    
    
    

    // hide select layers (e is click event)
    hideHighligt : function(e) {
        if (e) { e.preventDefault(); }
        this.builderPanel.el.select('.selectedElement').removeClass('selectedElement');
        this.builderPanel.el.select('.selectedElementParent').removeClass('selectedElementParent');
    },
    /////hideHighligt
    ///////////////
    

    // set current editing node
    setCurrentNode : function(node, select) {
    	if (this.editPanel) {//Agregado el 03/11/2008
	        var p = this.editPanel;
	        p.enable();
	        if (!node || !node.elConfig) {
	            p.currentNode = null;
	            p.setSource({});
	            p.disable();
	        } else {
	            config = node.elConfig;
	            for (k in config) {
	                if (this.attrType(k) == 'object' && typeof config[k] == 'object') {
	                    try {
	                        var ec = Ext.encode(config[k]);
	                        config[k] = ec;
	                    } catch(e) {}
	                }
	            }
	            p.setSource(config);
	            p.currentNode = node;
	            /* Comentado para que se visualice desde el inicio el tab Parametros en Internet Explorer
	            if (node.fEl == this.builderPanel) {
	                p.disable();
	            }
	            */
	        }
	    }
        if (select) {
        	if (this.treePanel) {//Agregado el 03/11/2008
            	this.treePanel.expandPath(node.getPath());
	            node.select();
            }
        }
    },
    ////setCurrentNode
    /////////////////

    // update node text & id (if necessary)
    updateNode : function(node) {
        if (!node) { return; }
        node.setText(this.configToText(node.elConfig));
        if (node.elConfig.id && node.elConfig.id != node.id) {
//            node.getOwnerTree().unregisterNode(node);
            node.id = node.elConfig.id;
//            node.getOwnerTree().registerNode(node);
        }
    },
/////updateNode
///////////////////

    // update the form at the specified node (if force or autoUpdate is true)
    updateForm : function(force, node) {
            node = node || this.treePanel.root;
            var updateTime = (node == this.treePanel.root);
            var time = null;

            // search container to update, upwards
            node = this.searchForContainerToUpdate(node);

            if (force === true || this.autoUpdate) {
                var config = this.getTreeConfig(node, true);
                time = this.setFormConfig(config, node.fEl);
                this.updateTreeEls(node.fEl);
                this.hideHighligt();

                // save into cookies   NOT COOKIES
                //this.cookies.set('formbuilderconfig', this.getTreeConfig());
            }

            //////REVISAR ESTE ELEMENTO
            /*
            if (time && updateTime) {
                Ext.ComponentMgr.get('FBRenderingTimeBtn').setText(
                    'Rendering time : <i>' + time + 'ms</i>');
            }
            */
            
    },

    // load from cookies if present   NOT COOKIES
    /*loadConfigFromCookies : function() {
        var c = this.cookies.get('formbuilderconfig');
        if (c) {
            try {
                this.setConfig(c);
            } catch(e) {
                return false;
            }
            return true;
        } else {
            return false;
        }
    },*/

    // search upware for a container to update
    searchForContainerToUpdate : function(node) {

        // search for a parent with border or column layout
        var found = null;
        var root = this.treePanel.root;
        var n = node;
        while (n != root) {
            if (n && n.elConfig &&
                    (n.elConfig.layout == 'border' ||
                        n.elConfig.layout == 'table' ||
                        n.elConfig.layout == 'column')) {
                found = n;
            }
            n = n.parentNode;
        }
        if (found !== null) { return found.parentNode; }

        // no column parent, search for first container with items
        n = node;
        while (n != root) {
            if (!n.fEl || !n.fEl.items) {
                n = n.parentNode;
            } else {
                break;
            }
        }
        return n;
    },

    // hilight an element
    highlightElement : function(el) {
            this.resizeLayer.hide();
            if (el == this.builderPanel.el) { return; }
            if (el) {
                var elParent = el.findParent('.x-form-element', 5, true);
                this.hideHighligt();
                if (elParent) { elParent.addClass("selectedElementParent"); }
                el.addClass("selectedElement");
            }
    },

    // get the tree config at the specified node
    getTreeConfig : function(node, addNodeInfos) {
        if (!node) { node = this.treePanel.root; }
        var config = Ext.apply({}, node.elConfig);
        if (!config.id && addNodeInfos) { config.id = node.id; }
        for (k in config) {
            if (this.attrType(k) == 'object') {
                try { config[k] = Ext.decode(config[k]); } catch(e) {}
            }
        }
        if (addNodeInfos) { config._node = node; }
        var items = [];
        node.eachChild(function(n) {
            items.push(this.getTreeConfig(n, addNodeInfos));
        }, this);
        if (items.length > 0) {
            config.items = items;
        } else if (config.xtype == 'form') {
            config.items = {};
        } else {
            delete config.items;
        }

        /*if (config.fieldLabel) {
        	config.fieldLabel = Ext.util.Format.htmlEncode(config.fieldLabel);
        }
        if (config.html) {
        	config.html = Ext.util.Format.htmlEncode(config.html);
        } */
        return config;
    },

    // update node.fEl._node associations
    updateTreeEls : function(el) {
        if (!el) { el = this.builderPanel; }
        if (el._node) {
            el._node.fEl = el;
            // workaround for fieldsets
            if (el.xtype == 'fieldset') {
                el.el.dom.id = el.id;
            }
        }
        if (!el.items) { return; }
        try {
            el.items.each(function(i) { this.updateTreeEls(i); }, this);
        } catch (e) {}
    },

    // node text created from config of el
    configToText : function(c) {
        var txt = [];
        c = c || {};
        if (c.xtype)      { txt.push(c.xtype); }
        if (c.fieldLabel) { txt.push('[' + c.fieldLabel + ']'); }
        if (c.boxLabel)   { txt.push('[' + c.boxLabel + ']'); }
        if (c.layout)     { txt.push('<i>' + c.layout + '</i>'); }
        if (c.title)      { txt.push('<b>' + c.title + '</b>'); }
        if (c.text)       { txt.push('<b>' + c.text + '</b>'); }
        if (c.region)     { txt.push('<i>(' + c.region + ')</i>'); }
        return (txt.length == 0 ? "Element" : txt.join(" "));
    },

    // return type of attribute
    attrType : function(name) {
        if (!Main.FIELDS[name]) { return 'unknown'; }
        return Main.FIELDS[name].type;
    },

    // return a cloned config
    cloneConfig : function(config) {
        if (!config) { return null; }
        var newConfig = {};
        for (i in config) {
            if (typeof config[i] == 'object') {
                newConfig[i] = this.cloneConfig(config[i]);
            } else if (typeof config[i] != 'function') {
                newConfig[i] = config[i];
            }
        }
        return newConfig;
    },

    // erase all
    resetAll : function() {
    	if (this.viewport) { //Agregada el 03/11/2008
        	var w = this.viewport.layout.center.getSize().width - 50;
        }
        var node = this.setConfig({items:[]});
        this.setCurrentNode(node, true);
    },

    // get a new ID
    getNewId : function() {
        return "form-gen-" + (this.idCounter++);
    },

    // return true if config can be added to node, or an error message if it cannot
    canAppend : function(config, node) {
        if (node == this.treePanel.root && this.treePanel.root.hasChildNodes()) {
            return "Only one element can be directly under the GUI Builder";
        }
        var xtype = node.elConfig.xtype;
        if (xtype && ['panel','viewport','form','window','tabpanel','toolbar','fieldset'].indexOf(xtype) == -1) {
            return 'You cannot add element under xtype "'+xtype+'"';
        }
        return true;
    },

    // add a config to the tree
    appendConfig : function(config, appendTo, doUpdate, markUndo) {
		
		//alert('enter in apppendConfig ' + config );
    	
        if (!appendTo) {
            appendTo = this.treePanel.getSelectedNode() ||
                this.treePanel.root;
        }
        var canAppend = this.canAppend(config,appendTo);
        if (canAppend !== true) {
            Ext.Msg.alert("Unable to add element", canAppend);
            return false;
        }
        var items = config.items;
        delete config.items;
        var id = config.id||(config._node ? config._node.id : this.getNewId());
        var newNode = new Ext.tree.TreeNode({id:id,text:this.configToText(config)});
        for(var k in config) { if (config[k]===null) { delete config[k]; }}
        newNode.elConfig = config;

        if (markUndo === true) {
            this.markUndo("Add " + newNode.text);
        }

        appendTo.appendChild(newNode);
        if (items && items.length) {
            for (var i = 0; i < items.length; i++) {
                    this.appendConfig(items[i], newNode, false);
            }
        }
        if (doUpdate !== false) {
            this.updateForm(false, newNode);
        }
        
        //alert('the new node is ' + newNode);
        
        return newNode;

    },

    // remove a node
    removeNode : function(node) {
            if (!node || node == this.treePanel.root) { return; }
            this.markUndo("Remove " + node.text);
            var nextNode = node.nextSibling || node.parentNode;
            var pNode = node.parentNode;
            pNode.removeChild(node);
            this.updateForm(false, pNode);
            this.setCurrentNode(nextNode, true);
    },

    // update the form
    setFormConfig : function(config, el) {

        el = el || this.builderPanel;

        // empty the form
        if (el.items) {
            while (el.items.first()) {
                el.remove(el.items.first(), true);
            }
        }
        if (el.getLayoutTarget) {
            el.getLayoutTarget().update();
        } else {
            el.update();
        }

        // adding items
        var start = new Date().getTime();
        if (config.items) {
            for (var i=0;i<config.items.length;i++) {
                el.add(config.items[i]);
            }
        }
        el.doLayout();
        var time = new Date().getTime() - start;
        return time;

    },
    ////end setFormConfig
    ////////////////

    // show a window with the json config
    editConfig : function() {
        var size = this.viewport.getSize();
        if (!this.jsonWindow) {
            var tf = new Ext.form.TextArea();
            this.jsonWindow = new Ext.Window({
                    title       : "Form Config",
                    width       : 400,
                    height      : size.height - 50,
                    autoScroll  : true,
                    layout      : 'fit',
                    items       : [tf],
                    modal       : true,
                    closeAction : 'hide'
                });
            this.jsonWindow.tf = tf;

            this.jsonWindow.addButton({
                    text    : "Close",
                    scope   : this.jsonWindow,
                    handler : function() { this.hide(); }
                });
            this.jsonWindow.addButton({
                    text    : "Apply",
                    scope   : this,
                    handler : function() {
                        var config = null;
                        try {
                            this.jsonWindow.el.mask("Please wait...");
                            config = Ext.decode(tf.getValue());
                        } catch (e) {
                            config = null;
                            this.jsonWindow.el.unmask();
                            Ext.Msg.alert("Error", "JSON is invalid : " + e.name + "<br/>" + e.message);
                            return;
                        }
                        if (!config) {
                            this.jsonWindow.el.unmask();
                            Ext.Msg.alert("Error", "Config seems invalid");
                            return;
                        } else {
                                this.markUndo("JSON edit");
                                try {
                                    this.setConfig({items:[config]});
                                } catch(e) {
                                    this.jsonWindow.el.unmask();
                                    Ext.Msg.confirm("Error", "Error while adding : " +
                                        e.name + "<br/>" + e.message + '<br/>' +
                                        'Do you wish to revert to previous ?', function(b) {
                                            if (b == 'yes') { this.undo(); }
                                        }, this);

                                }
                        }
                        this.jsonWindow.el.unmask();
                        this.jsonWindow.hide();
                    }
                });
        }///end if
        
        var cleanConfig = this.getTreeConfig();
        cleanConfig = (cleanConfig.items?cleanConfig.items[0]||{}:{});
        cleanConfig = Main.JSON.encode(cleanConfig);
        this.jsonWindow.tf.setValue(cleanConfig);
        this.jsonWindow.show();
    },
    ////end editConfig
    //////////////////////////////////


    // remove all nodes
    removeAll : function() {
    	if (this.treePanel) { //Agregado el 03/11/2008
	        var root = this.treePanel.root;
	        while(root.firstChild){
	                root.removeChild(root.firstChild);
	        }
	    }
    },

    // set config (remove then append a whole new config)
    setConfig : function(config) {
        if (!config || !config.items) { return false; }
        // delete all items
        this.removeAll();
        // add all items
        var node = null;
        if (this.treePanel) {
	        var root = this.treePanel.root;
	        for (var i = 0; i < config.items.length; i++) {
	            try {
	                node = this.appendConfig(config.items[i], root);
	            } catch(e) {
	                Ext.Msg.alert("Error", "Error while adding : " + e.name + "<br/>" + e.message);
	            }
	        }
	        this.updateForm(true, root);
	        return node || root;
	    }
	    return node; //Agregada 03/11/2008
    }
    ///end setConfig
    ////////////////////////////


};

// modified Ext.util.JSON to display a readable config
Main.JSON = new (function(){
    var useHasOwn = {}.hasOwnProperty ? true : false;
    var pad = function(n) { return n < 10 ? "0" + n : n; };
    var m = {
        "\b": '\\b',
        "\t": '\\t',
        "\n": '\\n',
        "\f": '\\f',
        "\r": '\\r',
        '"' : '\\"',
        "\\": '\\\\'
    };
    this.encodeString = function(s){
        	//alert("Valor de htmlEncode: " + Ext.util.Format.htmlEncode(s));
        if (/["\\\x00-\x1f]/.test(s)) {
        	//alert("valor de s: " + s);
            return '"' + s.replace(/([\x00-\x1f\\"])/g, function(a, b) {
                var c = m[b];
                if(c){ return c; }
                c = b.charCodeAt();
                return "\\u00" +
                    Math.floor(c / 16).toString(16) +
                    (c % 16).toString(16);
            }) + '"';
        }
        return '"' + s + '"';
    };

        var indentStr = function(n) {
            var str = "", i = 0;
            while (i<n) {
                str += "  ";
                i++;
            }
            return str;
        };

    var encodeArray = function(o, indent){
                indent = indent || 0;
        var a = ["["], b, i, l = o.length, v;
            for (i = 0; i < l; i += 1) {
                v = o[i];
                switch (typeof v) {
                    case "undefined":
                    case "function":
                    case "unknown":
                        break;
                    default:
                        if (b) {
                            a.push(',');
                        }
                        a.push(v === null ? "null" : Main.JSON.encode(v, indent + 1));
                        b = true;
                }
            }
            a.push("]");
            return a.join("");
    };

    var encodeDate = function(o){
        return '"' + o.getFullYear() + "-" +
                pad(o.getMonth() + 1) + "-" +
                pad(o.getDate()) + "T" +
                pad(o.getHours()) + ":" +
                pad(o.getMinutes()) + ":" +
                pad(o.getSeconds()) + '"';
    };

    this.encode = function(o, indent){
                indent = indent || 0;
        if(typeof o == "undefined" || o === null){
            return "null";
        }else if(o instanceof Array){
            return encodeArray(o, indent);
        }else if(o instanceof Date){
            return encodeDate(o);
        }else if(typeof o == "string"){
            return Main.JSON.encodeString(o);
        }else if(typeof o == "number"){
            return isFinite(o) ? String(o) : "null";
        }else if(typeof o == "boolean"){
            return String(o);
        }else {
            var a = ["{\n"], b, i, v;
                        if (o.items instanceof Array) {
                            var items = o.items;
                            delete o.items;
                            o.items = items;
                        }
            for (i in o) {
                                if (i === "_node") { continue; }
                if(!useHasOwn || o.hasOwnProperty(i)) {
                    v = o[i];
                                        if (i === "id" && /^form-gen-/.test(o[i])) { continue; }
                                        if (i === "id" && /^ext-comp-/.test(o[i])) { continue; }
                    switch (typeof v) {
                    case "undefined":
                    case "function":
                    case "unknown":
                        break;
                    default:
                        if(b){ a.push(',\n'); }
                                                a.push(indentStr(indent), i, ":",
                                v === null ? "null" : this.encode(v, indent + 1));
                        b = true;
                    }
                }
            }
                        a.push("\n" + indentStr(indent-1) + "}");
                        return a.join("");
        }
    };

})();

// parse DocRefs
var fields = {};

//
var fileName;

//
var infos;

//
var type;

//
var desc;

//
for (fileName in DocRefs) {
    for (key in DocRefs[fileName]) {
        infos = DocRefs[fileName][key];
        if (infos.type == "Function") { continue; }
        desc = "<i>"+fileName+"</i><br/><b>"+infos.type+"</b> "+infos.desc;
        if (!fields[key]) {
            fields[key] = { desc:desc };
            if (infos.type == "Boolean") {
                type = "boolean";
            } else if (infos.type == "Number") {
                type = "number";
            } else if (infos.type.match(/Array/)) {
                type = "object";
            } else if (infos.type.match(/Object/)) {
                type = "object";
            } else {
                type = "string";
            }
            fields[key].type = type;
        } else {
            fields[key].desc += "<hr/>" + desc;
        }
    }
}
////end for...
/////////////////

Ext.apply(fields, {
    xtype  : {desc:"",type:"string",values:'component box viewport panel window dataview colorpalette datepicker tabpanel button splitbutton cycle toolbar tbitem tbseparator tbspacer tbfill tbtext tbbutton tbsplit paging editor treepanel field textfield trigger textarea numberfield datefield combo checkbox radio hidden form fieldset htmleditor timefield grid editorgrid progress'.split(' ')},
    region : {desc:"",type:"string",values:'center west north south east'.split(' ')},
    hideMode         : {desc:"",type:"string",values:'visibility display offsets'.split(' ')},
    msgTarget        : {desc:"",type:"string",values:'qtip title under side'.split(' ')},
    shadow           : {desc:"",type:"string",values:'sides frame drop'.split(' ')},
    tabPosition      : {desc:"",type:"string",values:'top bottom'.split(' ')},
    columnWidth      : {desc:"Size of column (0 to 1 for percentage, >1 for fixed width",type:"number"},
    x                : {desc:"X position in pixels (for absolute layouts",type:"string"},
    y                : {desc:"Y position in pixels (for absolute layouts",type:"string"},
    anchor           : {desc:"Anchor size (width) in %",type:"string"},
    onSelect		 : {desc:"Function onSelect",type:"string"},
    
    fieldLabel       : {desc:"Label of the field",type:"string", esEditable:"true"},
    maxLength        : {desc:"Maxime length of the field",type:"number", esEditable:"false"},
    minLength        : {desc:"Minime length of the field",type:"number", esEditable:"false"},
    maxLengthText    : {desc:"Text of maxime length of the field",type:"string", esEditable:"false"},
    minLengthText    : {desc:"Text of minime length of the field",type:"string", esEditable:"false"},
    title            : {desc:"Title of the field",type:"string", esEditable:"true"},
    text             : {desc:"Text of the element",type:"string", esEditable:"true"},
    width			 : {desc:"width of the element",type:"number", esEditable:"true"},
    height			 : {desc:"Height of the element",type:"number", esEditable:"true"},
    collapsible		 : {desc:"Collapsible of the fildset",type:"boolean", esEditable:"true"},
    split            : {desc:"Split",type:"boolean", esEditable:"true"},
    editable         : {desc:"Editable",type:"boolean", esEditable:"true"},
    titleCollapse    : {desc:"Title Collapse",type:"boolean", esEditable:"true"},
    tooltip			 : {desc:"tooltip",type:"string", esEditable:"true"},
    Ayuda			 : {desc:"Ayuda",type:"string", esEditable:"true"},
    format			 : {desc:"Formato de fecha",type:"string",esEditable:"true",values:'datefield'.split(' ')},
    TipoDato		 : {desc:"Tipo de dato",type:"string",esEditable:"true"},
    hasHelpIcon		 : {desc:"Icono de ayuda asociado",type:"boolean",esEditable:"true"},
    helpTitle		 : {desc:"Titulo de la ayuda",type:"string",esEditable:"true"}
});
////
///////////////

fields.layout.values = [];

for (i in Ext.Container.LAYOUTS) { fields.layout.values.push(i); }
fields.vtype.values = [];
for (i in Ext.form.VTypes) { fields.vtype.values.push(i); }
fields.defaultType.values = fields.defaults.xtype;
Main.FIELDS = fields;


// custom editors for attributes
Main.getCustomEditors = function() {
    var g = Ext.grid;
    var f = Ext.form;
    var cmEditors = new g.PropertyColumnModel().editors;

    var eds = {};
    var fields = Main.FIELDS;
    for (i in fields) {
    	//Si esEditable=true, podremos editar esas propiedades
    	if(fields[i].esEditable == 'true') {
			if (fields[i].values) {
            	var values = fields[i].values;

            	var data = [];
            	for (j=0;j<values.length;j++) { data.push([values[j],values[j]]); }
            	eds[i] = new g.GridEditor(new f.SimpleCombo({forceSelection:false,data:data,editable:true}));
        	} else if (fields[i].type == "boolean") {
            	eds[i] = cmEditors['boolean'];
        	} else if (fields[i].type == "number") {
	            eds[i] = cmEditors['number'];
    	    } else if (fields[i].type == "string") {
        	    eds[i] = cmEditors['string'];
        	}
    	}else{//si esEditable=false, entonces no se podr�n editar esas propiedades
    		eds[i] = new g.GridEditor(new f.Field({editable:false,disabled:true}));
    	}
    }
    return eds;
};
/////getCustomEditors
//////////////////////////

main = new Main();
Ext.onReady(main.init, main);

//BORRAR LOS COMPONENTES DEL EDITOR
main.resetAll();