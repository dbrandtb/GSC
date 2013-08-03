<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">
	//COMPONENTES COMUNES... PRIMER SPLIPT 

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
    
    var view = new Ext.DataView({
            store        : ds,
            tpl          : tpl,
            overClass    : 'over',
            selectedClass: 'selected',
            singleSelect : true,
            itemSelector : '.component'
        });
    
        
     view.on('dblclick', function(v,idx,node,e) {
                e.preventDefault();
                var n = grid.currentNode;
                if (!n) { return false; }
                var c = view.store.getAt(idx).data.config;
                if (!c) { return false; }
                if (typeof c == 'function') {
                    c.call(this,function(config) {
                        var newNode = this.appendConfig(config, n, true, true);
                    }, n.elConfig);
                } else {
                    var newNode = this.appendConfig(this.cloneConfig(c), n, true, true);
                }
            }, this);
      
        
       //IMPORTANTE, ESTE HACE EL DRAG AND DROP 
       view.on('render', function() {
                var d = new Ext.dd.DragZone(view.el, {
                        ddGroup         : 'component',
                        containerScroll : true,
                        getDragData     : function(e) {
                                view.onClick(e);
                                var r = view.getSelectedRecords();
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
                view.dragZone = d;
            }, this);
        

   var elementosComunesPanel = new Ext.Panel({
             border:false,
             title: 'Elementos Comunes a las Pantallas',
             autoScroll: true,
             items:[view]
   });   
   
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   // SEGUNDO SPLIT
   
    var fields = [];
    	//TODO: 
        //for (var i in Main.FIELDS) {fields.push([i,i]);}
        var newPropertyField = new Ext.form.ComboBox({
                mode           : 'local',
                valueField     : 'value',
                displayField   : 'name',
                store          : new Ext.data.SimpleStore({
                        sortInfo : {field:'name',order:'ASC'},
                        fields   : ['value','name'],
                        data     : fields
                    })});
   
    var grid = new Ext.grid.PropertyGrid({
                title            : 'Parametros',
                height           : 300,
                split            : true,
                region           : 'center',
                source           : {}
                //bbar             : ['Add :', newPropertyField ],
                //newPropertyField : newPropertyField
                //,
                // bbar             : ['Add :', newPropertyField ]//,
                //customEditors    : Main.getCustomEditors(),
                
            });
            
            
   
    var gridPropiedadesPanel = new Ext.Panel({
             border:false,
             title: 'Grid de Propiedades',
             items:[grid]
             
   });
   
   
   
   ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   //TERCER SPLIT
   
   
   
   var tree = new Ext.tree.TreePanel({
            region          : 'north',
            title           : "&Aacute;rbol de elementos",
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
                text    : 'Expandir Todo',
                tooltip : 'Expandir todos los elementos',
                scope   : this//,
                //handler : function() { this.treePanel.expandAll(); }
            },{
                text    : 'Colapsar Todo',
                tooltip : 'Colapsar todos los elementos',
                scope   : this //,
                //handler : function() { this.treePanel.collapseAll(); }
            }]
        });
        
     var root = new Ext.tree.TreeNode({
        text : 'Interface Grafica de Usuario',
        //id : this.getNewId(),
        draggable : false
    });
    
    tree.setRootNode(root);    
   
    
    tree.on('click', function(node, e) {
            e.preventDefault();
            if (!node.fEl || !node.fEl.el) { return; }
            this.highlightElement(node.fEl.el);// definir funcion, quitar this...
            this.setCurrentNode(node);  //definir funcion , quitar this..
            window.node = node; // debug   verificar window...
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
            this.highlightElement(t.fEl.el); // definir funcion, quitar this...
            return (this.canAppend({}, t) === true); // definir funcion, quitar this...
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
                       // this.removeNode(contextMenu.node);
                    }
            },{
                text    : 'Agregar nuevo elemento como hijo',
                iconCls : 'icon-addEl',
                scope   : this,
                handler : function(item) {
                        //var node = this.appendConfig({}, contextMenu.node, true, true);
                        //this.treePanel.expandPath(node.getPath());
                    }
            },{
                text    : 'Agregar nuevo elemento debajo',
                iconCls : 'icon-addEl',
                scope   : this,
                handler : function(item) {
                        //var node = this.appendConfig({}, contextMenu.node.parentNode, true, true);
                        //this.treePanel.expandPath(node.getPath());
                    }
            },{
                text    : 'Duplicar este elemento',
                iconCls : 'icon-dupEl',
                scope   : this,
                handler : function(item) {
                        var node = contextMenu.node;
                        //this.markUndo("Duplicate " + node.text);
                        var newNode = cloneNode(node);
                        if (node.isLast()) {
                            node.parentNode.appendChild(newNode);
                        } else {
                            node.parentNode.insertBefore(newNode, node.nextSibling);
                        }
                        //this.updateForm(false, node.parentNode);
                    }
            },{
                text    : 'Modificar tama&ntilde;o',
                tooltip : 'Modifica el tama&ntilde;o del elemento.<br/>Tambi&eacute;n se puede mover si est&aacute; dentro de un <b>absolute</b> layout',
                iconCls : 'icon-resize',
                scope   : this,
                handler : function(item) {
                        //this.visualResize(contextMenu.node);
                    }
            },{
    text    : 'Editar el contenido',
    iconCls : 'icon-dupEl',
    scope   : this,
    handler : function(item) {
     	var node = contextMenu.node;
     //this.markUndo("Editar " + node.text);
     	//this.editarContenido(node);
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
        
    
    var arbolElementosPanel = new Ext.Panel({
             border:false,
             title: 'Arbol de Elementos',
             items: [tree]
   });
   
   
   
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
   
   var atributosMasterPanel = new Ext.Panel({
             border:false,
             title: 'Atributos del Master',
             autoScroll:true,
             items:[{
                    xtype: 'treepanel',
                    width: 190,
                    border : false, 
                    autoScroll: true,
                    split: true,
                    loader: new Ext.tree.TreeLoader({
                           dataUrl:'<s:url value="../confbeta/obtenerAtributosMaster.action"/>'
                            
                            
                    }), 
                    root: new Ext.tree.AsyncTreeNode({
                          text: 'Coberturas', 
                          draggable:false, // disable root node dragging
                          id:'source'
                    }),
                   rootVisible: false
                                
             }]
            
   });
   
   
  
   
  
   
   var navegacionPantallasPanel = new Ext.Panel({
             border:false,
             title: 'Navegacion de Pantallas',
             autoScroll:true,
             items:[{
                    xtype: 'treepanel',
                    id:'treeNavegacion',
                    width: 190,
                    border : false, 
                    autoScroll: true,
                    split: true,
                    clearOnLoad : true,
                    lines: false,
                    loader: new Ext.tree.TreeLoader({
                           dataUrl:'<s:url value="../confbeta/obtenerTreeNavegacionPantallas.action"/>'
                            
                            
                    }), 
                    root: new Ext.tree.AsyncTreeNode({
                          text: 'Pantallas', 
                          draggable:false, // disable root node dragging
                          id:'sourceP'
                    }),
                   rootVisible: false,
                   listeners: {
                              click: function(n) {
                              clavePantalla = n.attributes.id;
                              getRegistroPantalla(clavePantalla);
                                storeRegistroPantalla.on('load', function(){
                                    var recordP = storeRegistroPantalla.getAt(0);
                                     formPanelDatos.getForm().loadRecord(recordP);
                                        if(recordP.get('cdPantalla')==null || recordP.get('cdPantalla')=="")
                                            {
                                                 comboTipoPantalla.enable();
                                                 formPanelDatos.getForm().reset();
                                                 Ext.get('btnEliminar').dom.disabled = true;
                                                 Ext.get('btnNPantalla').dom.disabled = true;
                                        }else{
                                                comboTipoPantalla.disable();
                                                Ext.get('btnEliminar').dom.disabled = false;
                                                Ext.get('btnNPantalla').dom.disabled = false;
                                                comboTipoPantalla.store = getTiposPantallas(idProceso);
                                        }
                                
                                      
                                   });                                   
                              }
                    }// end listeners
                                
             }]
             
            
   });
   
/*************************************************************
** Creating the Panel to the main view port
**************************************************************/ 
   
   
   var masterPanel = new Ext.Panel({
        region: 'west',
        title: 'MENU',
        split:true,
        width:200,
        heigth:'500',
        minSize:'200',
        maxSize:'200',
        margins:'0 5 0 5',
        bodyStyle:'padding:5px',
        collapsible: true,
        layout:'accordion',
        layoutConfig:{
             animate:true
        }     
    });     
   
   masterPanel.add(elementosComunesPanel);
   masterPanel.add(gridPropiedadesPanel);
   masterPanel.add(arbolElementosPanel);         
   masterPanel.add(atributosMasterPanel);
   masterPanel.add(navegacionPantallasPanel);


</script>