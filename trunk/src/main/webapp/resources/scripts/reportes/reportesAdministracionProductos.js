Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = "side";


    /**
     * store provisional
     */
    var url;
    var desde;
    var hasta;
    var desdeAux;

    //function cargarTabla() {

    url = _ACTION_BUSCAR_PRODUCTOS + '?cdReporte=' + codRep;       //Mapeo con struts
    var store = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: url
        }),
        reader: new Ext.data.JsonReader({
            root:'atributos',
            totalProperty: 'totalCount',
            id: 'totales'
        }, [
            {name: 'codProducto',  type: 'string',  mapping:'cdReporte'},
            {name: 'dsProducto',  type: 'string',  mapping:'dsAtributo'}
        ])//,
        // remoteSort: true
    });
    //store.setDefaultSort('totales', 'desc');
    store.load({params:{start:0, limit:50}});
    //    return store;
    //}


    /////combo
    var storeCombo;
    var urlCombo;
    function cargarCombo() {
        storeCombo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_CARGAR_COMBO
            }),
            reader: new Ext.data.JsonReader({
                root:'comboProductos',
                totalProperty: 'totalCountCombo',
                id: 'totalesCombo'
            }, [
                {name: 'codigo',  type: 'string',  mapping:'codigo'},
                {name:  'descripcion',  type: 'string',  mapping:'descripC'}

            ]),
            remoteSort: true
        });
        storeCombo.setDefaultSort('totalesCombo', 'desc');
        storeCombo.load({params:{start:0, limit:50}});

        return storeCombo;
    }


    /***************************************
     *Se crea la ventana de agregar atributo.
     ****************************************/

    var agregar = function() {
        var codigo = new Ext.form.TextField({
            fieldLabel: '',
            value: codigoText.getValue(),
            name: 'cdReporte',
            hidden: true,
            hiddeParent:true,
            labelSeparator:''
        });

        var formato = new Ext.form.ComboBox({
            //id:'formatoCombo',
            //typeAhead: true,
            forceSelection: true,
            //readOnly :true,
            fieldLabel: 'Producto',
            labelAlign: 'right',
            hiddenName:'codProducto',
            store: cargarCombo(),
            displayField:'descripcion',
            allowBlank: false,
            valueField: 'codigo',
            mode: 'local',
            anchor:'95%',
            triggerAction: 'all',
            emptyText: 'Selecciona un formato...'

        });


        var formPanel = new Ext.form.FormPanel({
            baseCls: 'x-plain',
            labelWidth: 75,
            labelAlign: 'right',
            url:'reportesPrincipal/insertarProducto.action',
            items: [
                formato,
                codigo
            ]

        });

        var window = new Ext.Window({
            title: 'Insertar/Productos',
            plain:true,
            closable:false,
            width: 380,
            height:120,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal:true,
            items: formPanel,
            buttons: [{
                text: 'Guardar',
                handler: function() {

                    if (formPanel.form.isValid()) {
                        formPanel.form.submit({
                            waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('El Producto no fue creado correctamente', action.result.wrongMsg);
                                window.hide();
                                grid2.destroy();
                                createGrid();
                                //                                reloadGrid();
                                //store.load();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('El Producto fue agregado correctamente', 'Se ha agregado un nuevo Producto');
                                window.hide();
                                grid2.destroy();
                                createGrid();
                                //                                reloadGrid();
                            }
                        });
                    } else {
                        Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida');
                    }


                }
            },{
                text: 'Regresar',
                handler: function() {
                    window.hide();
                }
            }]
        });
        window.show();
    };

    /*************************************
     *Crea la ventana de editar
     **************************************/

    var codigoEditar = new Ext.form.TextField({
        fieldLabel : '',
        name: 'cdReporte',
        allowBlank: false,
        value : codRep,
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden : true
        //width: 60
    });

    var codigoProducto = new Ext.form.TextField({
        fieldLabel : '',
        name: 'codProducto',
        allowBlank: false,
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden : true
        //width: 60
    });


    var editarFormato = new Ext.form.ComboBox({
        forceSelection: true,
        fieldLabel: 'Producto',
        labelAlign: 'right',
        hiddenName:'dsProducto',
        store: cargarCombo(),
        anchor:'95%',
        displayField:'descripcion',
        allowBlank: false,
        valueField: 'codigo',
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Selecciona un formato...'

    });

    var editarForm = new Ext.form.FormPanel({
        id:'editForm',
        baseCls: 'x-plain',
        labelWidth: 75,
        labelAlign: 'right',
        url:'reportesPrincipal/editarProducto.action',
        items: [
            editarFormato,codigoProducto ,codigoEditar

        ]
    });

    var windowEditar = new Ext.Window({
        title: 'Editar/Actualizar Producto',
        plain:true,
        closable:false,
        width: 400,
        height:115,
        layout: 'fit',
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal:true,
        items: editarForm,
        buttons: [{
            text: 'Guardar',
            handler: function() {

                if (editarForm.form.isValid()) {

                    editarForm.form.submit({
                        waitTitle:'Espere',
                        waitMsg:'Procesando...',
                        failure: function(form, action) {
                            Ext.MessageBox.alert('El Producto no fue creado correctamente', action.result.wrongMsg);
                            windowEditar.hide();
                            grid2.destroy();
                            createGrid();
                            //                            reloadGrid();

                            //store.load();
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert('El Producto fue editado correctamente', action.result.wrongMsg);
                            windowEditar.hide();
                            //                            editarFormato.reset();
                            //                            reloadGrid();
                            grid2.destroy();
                            createGrid();
                            reloadGrid();

                        }
                    });
                } else {
                    Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida');
                }


            }
        },{
            text: 'Regresar',
            handler: function() {
                windowEditar.hide();
            }
        }]
    });

    /*****se crea el mensaje de borrar un atributo***/

    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿Est&aacute; seguro que desea eliminar el producto seleccionado?',
        labelSeparator:'',
        hidden:true
    });

    var cdReporte = new Ext.form.NumberField({
        //fieldLabel: 'C&oacute;digo de Reporte',
        id:'cdReporteDelet',
        name:'cdReporte',
        //anchor:'90%',
        value : codRep,
        hidden:true,
        hiddeParent:true,
        labelSeparator:''

    });

    var cdAtributos = new Ext.form.NumberField({
        //fieldLabel: 'C&oacute;digo Producto',
        id:'cdAtributosDelet',
        name:'codProducto',
        //anchor:'90%',
        hidden:true,
        hiddeParent:true,
        labelSeparator:''

    });

    var borrarForm = new Ext.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        url:'reportesPrincipal/borrarProducto.action',
        items:[ msgBorrar,cdReporte,cdAtributos]
    });

    var windowBorrar = new Ext.Window({
        title: 'Eliminar Producto',
        width: 250,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        closable:false,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: borrarForm,
        buttons: [{
            text: 'Eliminar',
            handler: function() {
                if (borrarForm.form.isValid()) {
                    borrarForm.form.submit({
                        waitMsg:'Procesando...',
                        failure: function(form, action) {
                            Ext.MessageBox.alert('Error Eliminando Producto', 'Ha ocurrido un error al eliminar el Producto');
                            windowBorrar.hide();
                            reloadGrid();
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert('Producto Eliminado', 'El Producto  ha sido eliminado satisfactoriamente');
                            windowBorrar.hide();
                            grid2.destroy();
                            createGrid();
                            reloadGrid();
                        }
                    });
                } else {
                    Ext.MessageBox.alert('Error', 'Verifique!');
                }
            }
        },{
            text: 'Cancelar',
            handler: function() {
                windowBorrar.hide();
            }
        }]
    });
    /*****/


    /******************
     *Form de busqueda para el grid.
     *******************/

    var codigoText = new Ext.form.TextField({
        fieldLabel: '<span style="color:white;font-size:14px;">3</span>C&oacute;digo',
        name : 'codRep',
        value : codRep,
        disabled : true,
        width: 50
    });

    var descripcion = new Ext.form.TextField({
        fieldLabel: 'Descripcion',
        name : 'descripcion',
        value : descripcionAtri,
        disabled : true,
        width: 450
    });


    var atributosForm = new Ext.form.FormPanel({
        title: '<span style="color:black;font-size:14px;">Administraci&oacute;n de Productos</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        //      url:_ACTION_MANTTO,
        width: 700,
        height:120,
        items: [{
            layout:'form',
            border: false,
            items:[{
                title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B&uacute;squeda</span>',
                bodyStyle:'background: white',
                labelWidth: 70,
                layout: 'form',
                frame:true,
                baseCls: '',
                items:[
                    codigoText,
                    descripcion
                ]
            }]
        }]
    });
    atributosForm.render(document.body);

    function toggleDetails(btn, pressed) {

        var view = grid.getView();


        view.showPreview = pressed;
        view.refresh();
    }
    ;

    var sm = new Ext.grid.RowNumberer;
    var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        // {header: "Seleccionar",  dataIndex:'num', width: 50, sortable:true, locked:false, id:'num'},
        {header: "Codigo Producto",  dataIndex:'codProducto',    width: 120, sortable:true,     locked:false},
        {header: "Descripcion",  dataIndex:'dsProducto',    width: 200, sortable:true,     locked:false}

    ]);

    //cm.defaultSortable = true;

    var grid2;
    var selectedId;
    /*********************
     *Se crea el grid de Planes
     ***********************/
    var aux;
    function createGrid() {
        var seleccion = true;
        Ext.PagingToolbar.prototype.onClick = function(which) {
            switch (which) {
                case "first":
                    desde = 0;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta}});
                    break;
                case "prev":
                    desde = this.cursor - this.pageSize;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta}});
                //
                    break;
                case "next":
                //
                    this.cursor = this.cursor + this.pageSize;
                    desde = this.cursor;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta}});
                //
                    break;
                case "last":
                //
                    desdeAux = store.getTotalCount() % this.pageSize;
                    desde = desdeAux ? (store.getTotalCount() - desdeAux) : store.getTotalCount() - this.pageSize;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta}});
                    break;
                case "refresh":

                    desde = 0;
                    hasta = this.pageSize;
                    store.load({params:{start: desde,limit:hasta}});
                    grid2.destroy();
                    createGrid();

                    break;
            }
            ;
        };

        grid2 = new Ext.grid.GridPanel({
            id:'grid2',
            store:store,
            border:true,
            cm: cm,
            buttonAlign:'center',
            loadMask: {msg: getLabelFromMap('400058', helpMap, 'Cargando datos ...'), disabled: false},
            buttons:[
                {text:'Agregar',
                    tooltip:'Agregar un nuevo Producto',
                    handler:function() {

                        agregar();
                    }
                },{
                    id:'editar',
                    text:'Editar',
                    tooltip:'Editar Producto seleccionado',
                    handler: function() {
                        // alert("entro");
                        if (seleccion) {
                            Ext.MessageBox.alert('Seleccione', 'Seleccione el Producto a editar');
                        }
                    }
                },{
                    text:'Eliminar',
                    id:'borrar',
                    tooltip:'Eliminar el Producto seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Seleccione', 'Seleccione el Producto a eliminar');
                        }
                    }
                },{
                    text:'Exportar',
                    tooltip:'Exportar Productos',
                    handler: function() {
                        showExportDialog(_ACTION_EXPORT + '?cdReporte=' + codigoText.getValue() + '&descripcion=Productos')
                    }
                },{
                    text:'Regresar',
                    id:'regresar',
                    tooltip:'Regresa a la pantalla de Reportes',
                    handler: function () {
                        window.location.replace(_REGRESAR);
                    }
                }],
            width:700,
            frame:true,
            height:280,
            title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
            collapsible: true,
            renderTo:document.body,
            bbar: new Ext.PagingToolbar({
                pageSize:itemsPerPage,
                store: store,

                displayInfo: true,
                displayMsg: getLabelFromMap('400009', helpMap, 'Mostrando registros {0} - {1} of {2}'),
                emptyMsg: getLabelFromMap('400012', helpMap, 'No hay registros para visualizar')
            }),
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    rowselect: function(sm, row, rec) {
                        seleccion = false;
                        selectedId = store.data.items[row].id;

                        Ext.getCmp('editar').on('click', function() {
                            windowEditar.show();
                            Ext.getCmp('editForm').getForm().loadRecord(rec);
                        });
                        Ext.getCmp('borrar').on('click', function() {
                            windowBorrar.show();
                            Ext.getCmp('borrarForm').getForm().loadRecord(rec);
                        });

                    }
                }

            }),
            viewConfig: {autoFill: true,forceFit:true}
        });
       store.load({params:{start: 0,limit:this.pageSize}});
    }
    createGrid();


    function reloadGrid() {
        var _params = {
            cdReporte: codRep
        };
        reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
    }
    function cbkReload(_r, _options, _success, _store) {
        if (!_success) {
            _store.removeAll();
            Ext.Msg.alert(getLabelFromMap('400010', helpMap, 'Aviso'), _store.reader.jsonData.actionErrors[0]);
        }
    }

});

