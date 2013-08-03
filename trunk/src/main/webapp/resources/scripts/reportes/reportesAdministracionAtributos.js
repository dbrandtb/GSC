/**
 * Atributos de reportes principal
 */

Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = "side";
    var store;
    var desde;
    var hasta;
    var desdeAux;
    /**
     * store provisional
     */
    var url;

    function cargarTabla() {
        // alert('este '+codigoText.getValue());
        //alert(url);
        url = _ACTION_BUSCAR_ATRIBUTOS + '?cdReporte=' + codigoText.getValue();       //Mapeo con struts
        // url = _ACTION_BUSCAR_ATRIBUTOS;       //Mapeo con struts
        store = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: url
            }),
            reader: new Ext.data.JsonReader({
                root:'atributos',
                totalProperty: 'totalCount',
                id: 'totales'
            }, [
                {
                    name: 'cdReporte',
                    type: 'string',
                    mapping:'cdReporte'
                },
                {
                    name: 'cdAtributo',
                    type: 'string',
                    mapping:'cdAtributo'
                },
                {
                    name: 'dsAtributo',
                    type: 'string',
                    mapping:'dsAtributo'
                },
                {
                    name: 'swFormat',
                    type: 'string',
                    mapping:'swFormat'
                },
                {
                    name: 'nmLmin',
                    type: 'string',
                    mapping:'nmLmin'
                },
                {
                    name: 'nmLmax',
                    type: 'string',
                    mapping:'nmLmax'
                },
                {
                    name: 'otTabval',
                    type: 'string',
                    mapping:'otTabval'
                },
                {
                    name: 'cdExpres',
                    type: 'string',
                    mapping:'cdExpres'
                }

            ]),
            remoteSort: true
        });
        store.setDefaultSort('totales', 'desc');

        return store;
    }

    ;
    /////combo
    var storeCombo;
    var urlCombo;

    function cargarCombo() {
        urlCombo = _ACTION_CARGAR_COMBO;       //Mapeo con struts
        storeCombo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: urlCombo
            }),
            reader: new Ext.data.JsonReader({
                root:'combo',
                totalProperty: 'totalCountCombo',
                id: 'totalesCombo'
            }, [
                {
                    name: 'nmGrafico',
                    type: 'string',
                    mapping:'codigo'
                },
                {
                    name:  'descripcion',
                    type: 'string',
                    mapping:'descripC'
                }

            ]),
            remoteSort: true
        });
        storeCombo.setDefaultSort('totalesCombo', 'desc');
        storeCombo.load({params:{start:0, limit:50}});


        return storeCombo;
    }

    ;


    /*************************************
     *Crea la ventana de editar
     **************************************/

    var codigoEditar = new Ext.form.TextField({
        //fieldLabel : '',
        name: 'cdReporte',
        allowBlank: false,
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        hidden : true,
        listeners: {
            change : function(extFormComboBox, extDataRecord, numberIndex) {
                var cad = trim(codigoEditar.getValue());
                if (cad == '') {
                    codigoEditar.setValue('');
                }
            }
        }
        //width: 60
    });
    var codigoAtributo = new Ext.form.TextField({
        // fieldLabel : '',
        name: 'cdAtributo',
        hidden : true,
        allowBlank: false,
        anchor:'90%',
        hiddeParent:true,
        labelSeparator:'',
        listeners: {
            change : function(extFormComboBox, extDataRecord, numberIndex) {
                var cad = trim(codigoAtributo.getValue());
                if (cad == '') {
                    codigoAtributo.setValue('');
                }
            }
        }
        //width: 60
    });
    var editarAtributo = new Ext.form.TextField({
        fieldLabel: 'Descripci&oacute;n de Atributo',
        maxLength:100,
        allowBlank: false,
        name: 'dsAtributo',
        anchor : '95%',
        listeners: {
            change : function(extFormComboBox, extDataRecord, numberIndex) {
                var cad = trim(editarAtributo.getValue());
                if (cad == '') {
                    editarAtributo.setValue('');
                }
            }
        }
    });
    var editarFormato = new Ext.form.ComboBox({
        id:'formatoCombo1',
        fieldLabel: 'Formato',
        labelAlign: 'right',
        hiddenName:'swFormat',
        store: cargarCombo(),
        displayField:'descripcion',
        allowBlank: false,
        valueField: 'nmGrafico',
        mode: 'local',
        //readOnly :true,
        triggerAction: 'all',
        emptyText:'Selecciona un formato...'

    });
    var editarForm = new Ext.form.FormPanel({
        id:'editForm',
        name:'editForm',
        baseCls: 'x-plain',
        labelWidth: 75,
        labelAlign: 'right',
        url:'reportesPrincipal/editarAtributos.action',
        items: [
            editarAtributo,
            editarFormato,
            codigoEditar,
            codigoAtributo

        ]
    });

    var windowEditar = new Ext.Window({
        title: 'Editar/Actualizar Atributos',
        plain:true,
        closable:false,
        width: 400,
        height:160,
        layout: 'fit',
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal:true,
        items: editarForm,
        buttons: [
            {
                text: 'Guardar',
                handler: function() {

                    if (editarForm.form.isValid()) {

                        editarForm.form.submit({
                            waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('EL Atributo no fue editado correctamente', 'No se pudo editar el atributo seleccionado');
                                windowEditar.hide();
                                //                            reloadGrid();
                                grid2.destroy();
                                createGrid();
                                //store.load();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('El atributo fue editado correctamente', 'Se ha editado un Atributo');
                                windowEditar.hide();
                                grid2.destroy();
                                createGrid();
                                reloadGrid();
                            }
                        });
                    } else {
                        Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida');
                    }


                }
            },
            {
                text: 'Regresar',
                handler: function() {
                    windowEditar.hide();
                }
            }
        ]
    });

    /*****se crea el mensaje de borrar un atributo***/

    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿Est&aacute; seguro que desea eliminar el atributo seleccionado?',
        labelSeparator:'',
        hidden:true
    });

    var cdReporte = new Ext.form.NumberField({
        //fieldLabel: 'C&oacute;digo de Reporte',
        id:'cdReporteDelete',
        name:'cdReporte',
        //anchor:'90%',
        hidden:true,
        hiddeParent:true,
        labelSeparator:''

    });

    var cdAtributos = new Ext.form.NumberField({
        //fieldLabel: 'C&oacute;digo Atributo',
        id:'cdAtributosDelete',
        name:'cdAtributo',
        //anchor:'90%',
        hidden:true,
        hiddeParent:true,
        labelSeparator:''

    });

    var borrarForm = new Ext.FormPanel({
        id:'borrarForm',
        name:'borrarForm',
        anchor:'100%',
        labelAlign:'top',
        baseCls: 'x-plain',
        url:'reportesPrincipal/borrarAtributos.action',
        items:[ msgBorrar,cdReporte,cdAtributos]
    });

    var windowBorrar = new Ext.Window({
        title: 'Eliminar Atributo',
        width: 250,
        height:120,
        layout: 'fit',
        plain:true,
        modal:true,
        closable:false,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: borrarForm,
        buttons: [
            {
                text: 'Borrar',
                handler: function() {
                    if (borrarForm.form.isValid()) {
                        borrarForm.form.submit({
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('Error Borrando Atributo', 'Ha ocurrido un error al borrar el Atributo');
                                windowBorrar.hide();
                                reloadGrid();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('Atributo Borrado', 'El Atributo  ha sido borrado satisfactoriamente');
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
            },
            {
                text: 'Cancelar',
                handler: function() {
                    windowBorrar.hide();
                }
            }
        ]
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
        //readOnly:true,
        width: 50
    });
    var descripcion = new Ext.form.TextField({
        fieldLabel: 'Descripcion',
        name : 'descripcion',
        value : descripcionAtri,
        disabled : true,
        //readOnly:true,
        width: 450
    });
    var ejecutable = new Ext.form.TextField({
        fieldLabel: 'Nombre Ejecutable',
        name : 'ejecutable',
        value : ejecutableAtri,
        //readOnly:true,
        disabled : true,
        width: 225

    });
    var atributosForm = new Ext.form.FormPanel({
        title: '<span style="color:black;font-size:14px;">Administraci&oacute;n de Atributos</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        frame:true,
        //      url:_ACTION_MANTTO,
        width: 700,
        height:150,
        items: [ codigoText,
            descripcion,
            ejecutable
        ]
    });
    atributosForm.render('formBusqueda');

    var sm = new Ext.grid.RowNumberer;
    var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),

        {
            header: "C&oacute;digo Atributo",
            dataIndex:'cdAtributo',
            width:120,
            sortable:true,
            locked:false
        },
        {
            header: "Descripci&oacute;n",
            dataIndex:'dsAtributo',
            width:200,
            sortable:true,
            locked:false
        },
        {
            header: "Formato",
            dataIndex:'swFormat',
            width:150,
            sortable:true
        }

    ]);

    // cm.defaultSortable = true;

    var grid2;
    var selectedId;
    /*********************
     *Se crea el grid de Planes
     ***********************/
    var aux;

    function createGrid() {
        //alert("En create grid");
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
            store:cargarTabla(),
            border:true,
            //baseCls:' background:white ',
            cm: cm,
            loadMask: {msg: getLabelFromMap('400058', helpMap, 'Cargando datos ...'), disabled: false},
            buttonAlign:'center',
            buttons:[
                {
                    text:'Agregar',
                    tooltip:'Agregar un nuevo atributo',
                    handler:function() {

                        agregar();
                    }
                },
                {
                    id:'editar',
                    text:'Editar',
                    tooltip:'Editar Atributo seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Seleccione', 'Seleccione el Atributo a editar');
                        }
                    }
                },
                {
                    text:'Eliminar',
                    id:'borrar',
                    tooltip:'Eliminar el atributo seleccionado',
                    handler: function() {
                        if (seleccion) {
                            Ext.MessageBox.alert('Seleccione', 'Seleccione el Atributo a borrar');
                        }
                    }
                },
                {
                    text:'Exportar',
                    tooltip:'Exportar Atributos',
                    handler:function() {
                        showExportDialog('reportesPrincipal/exportPlanAtributos.action' + '?cdReporte=' + codRep + '&descripcion=Atributos')
                    }
                },
                {
                    text:'Regresar',
                    id:'regresar',
                    tooltip:'Regresa a la pantalla de Reportes',
                    handler: function () {
                        window.location.replace(_REGRESAR);
                    }
                }
            ],
            width:700,
            frame:true,
            height:280,
            title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
            collapsible: true,
            //renderTo:document.body,
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
        grid2.render('gridElementos');
        store.load({params:{start:0, limit:50}});
    }

    ;
    createGrid();

    //    store.load();
    //    store.load({params:{start:0, limit:50}});
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


        var atributo = new Ext.form.TextField({
            fieldLabel: 'Descripci&oacute;n de Atributo',
            maxLength:100,
            allowBlank: false,
            name: 'dsAtributo',
            anchor : '95%',
            listeners: {
                change : function(extFormComboBox, extDataRecord, numberIndex) {
                    var cad = trim(atributo.getValue());
                    if (cad == '') {
                        atributo.setValue('');
                    }
                }
            }
        });

        var formato = new Ext.form.ComboBox({

            //id:'formatoCombo',
            //typeAhead: true,
            forceSelection: true,
            //            readOnly :true,
            fieldLabel: 'Formato',
            labelAlign: 'right',
            hiddenName:'swFormat',
            store: cargarCombo(),
            displayField:'descripcion',
            allowBlank: false,
            valueField: 'nmGrafico',
            mode: 'local',
            triggerAction: 'all',
            emptyText:'Selecciona un formato...'

        });


        var formPanel = new Ext.form.FormPanel({
            baseCls: 'x-plain',
            labelWidth: 75,
            labelAlign: 'right',
            url:'reportesPrincipal/insertarAtributos.action',
            items: [
                atributo,
                formato,
                codigo
            ]

        });

        var window = new Ext.Window({
            title: 'Insertar/Actualizar Atributos',
            plain:true,
            closable:false,
            width: 380,
            height:160,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal:true,
            items: formPanel,
            buttons: [
                {
                    text: 'Guardar',
                    handler: function() {
                        if (formPanel.form.isValid()) {
                            formPanel.form.submit({
                                waitTitle:'Espere',
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('EL Atributo no fue creado correctamente', action.result.errorInfo);
                                    window.hide();
                                    //                                    reloadGrid();
                                    grid2.destroy();
                                    createGrid();
                                    //store.load();
                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('El atributo fue agregado correctamente', 'Se ha ingresado un nuevo Atributo');
                                    window.hide();
                                    grid2.destroy();
                                    createGrid();
                                    //                                    reloadGrid();
                                }
                            });
                        } else {
                            Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida.');
                        }
                    }


                },
                {
                    text: 'Regresar',
                    handler: function() {
                        window.hide();
                    }
                }
            ]
        });
        window.show();
    };

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

