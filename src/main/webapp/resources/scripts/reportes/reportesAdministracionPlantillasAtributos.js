Ext.onReady(function() {
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = "side";
    var url;
    var store;
    var codigoEditart;
    var codigoAtributot;
    var editarAtributot;
    var editarFormatot;
    var editarMinimot;
    var editarMaximot;
    var editarApoyot;
    var desde;
    var hasta;
    var desdeAux;

    function cargarTabla() {
        url = 'reportesPrincipal/obtenerPlantillasAtributos.action';//+'?cdPlantilla='+codigoText.getValue();//Mapeo con struts
        store = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: url
            }),
            reader: new Ext.data.JsonReader({
                root:'atributos',
                totalProperty: 'totalCount',
                id: 'totales'
            }, [
                {name: 'cdPlantilla',  type: 'string',  mapping:'cdPlantilla'},
                {name: 'cdAtributo',  type: 'string',  mapping:'cdAtributo'},
                {name: 'dsAtributo',  type: 'string',  mapping:'dsAtributo'},
                {name: 'swFormat',  type: 'string',  mapping:'swFormat'},
                {name: 'nmLmin',  type: 'string',  mapping:'nmLmin'},
                {name: 'nmLmax',  type: 'string',  mapping:'nmLmax'},
                {name: 'otTabval',  type: 'string',  mapping:'otTabval'},
                {name: 'cdotTabval',  type: 'string',  mapping:'cdotTabval'}

            ]),listeners: {
            load: function(store, response){
                if (store.getTotalCount<1){Ext.MessageBox.show({
                    title: 'Advertencia',
                    msg: 'No hay Datos Disponibles',
                    buttons: Ext.MessageBox.OK

                });}


            }}
        });
        //store.setDefaultSort('totales', 'desc');


        return store;


    };

    var storeCombo;
    var storeComboA;
    var urlCombo;
    var urlComboA;
    function cargarCombo(){
        urlCombo=_ACTION_CARGAR_COMBO;       //Mapeo con struts
        storeCombo = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: urlCombo
            }),
            reader: new Ext.data.JsonReader({
                root:'combo',
                totalProperty: 'totalCountCombo',
                id: 'totalesCombo'
            },[
                {name: 'nmGrafico',  type: 'string',  mapping:'codigo'},
                {name:  'descripcion',  type: 'string',  mapping:'descripC'}

            ]),
            remoteSort: true
        });
        storeCombo.setDefaultSort('totalesCombo', 'desc');
        storeCombo.load({params:{start:0, limit:4}});

        return storeCombo;
    };

    function cargarComboApoyo(){
        urlComboA=_ACTION_CARGAR_COMBO_APOYO;       //Mapeo con struts
        storeComboA = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: urlComboA
            }),
            reader: new Ext.data.JsonReader({
                root:'tapoyo',
                totalProperty: 'totalCountComboA',
                id: 'totalesComboA',
                successProperty : '@success'
            },[
                {name: 'cdtabla',  type: 'string',  mapping:'cdtabla'},
                {name:  'dstabla',  type: 'string',  mapping:'dstabla'}

            ]),listeners: {
            load: function(storeComboA, response){
                if (storeComboA.getCount()<1){Ext.MessageBox.show({
                    title: 'Advertencia',
                    msg: 'No hay Datos Disponibles',
                    buttons: Ext.MessageBox.OK

                });}


            },
            loadexception: function(proxy, storeComboA, response, e) {

                Ext.MessageBox.show({
                    title: 'Advertencia',
                    msg: 'No hay Datos Disponibles',
                    buttons: Ext.MessageBox.OK

                });
            }},
            remoteSort: true
        });
        //storeComboA.setDefaultSort('totalesComboA', 'dstabla');
        storeComboA.load();

        return storeComboA;
    };

    /***************************************
     *Se crea la ventana de agregar atributo.
     ****************************************/



    var editarExpres = new Ext.form.TextField({
        fieldLabel: 'Expresi&oacute;n Asociada',
        allowBlank: true,
        disabled : true,
        name: 'cdExpres',
        width: 250
    });

    var agregar = function() {
        var codigo = new Ext.form.TextField({
            //fieldLabel: 'C&oacute;digo Plantilla',
            value: codigoText.getValue(),
            name: 'cdPlantilla',
            hidden:true,
            width: 60,
            hidden: true,
            allowBlank:false,
            hiddeParent:true,
            labelSeparator:''
        });

        var codigoVista = new Ext.form.TextField({
            fieldLabel: 'C&oacute;digo Plantilla',
            value: codigoText.getValue(),
            name: 'cdPlantilla',
            allowBlank:false,
            disabled: true,
            width: 60
        });

        var atributo = new Ext.form.TextField({
            fieldLabel: 'Descripci&oacute;n de Atributo',
            name: 'dsAtributo',
            allowBlank:false,
            maxLength :120,
            width: 300,
            listeners: {
                change : function(extFormComboBox, extDataRecord, numberIndex ){
                    var cad = trim(atributo.getValue());
                     if(cad == '')  {
                        atributo.setValue('');
                    }
                }
            }
        });

        var formato = new Ext.form.ComboBox({
            //id:'formatoCombo',
            typeAhead: true,
            forceSelection: true,
            //readOnly :true,
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

        var minimo = new Ext.form.NumberField({
            fieldLabel: 'M&iacute;nimo',
            name: 'nmLmin',
            allowBlank:false,
            maxLength :2,
            width: 60
        });
        var maximo = new Ext.form.NumberField({
            fieldLabel: 'M&aacute;ximo',
            name: 'nmLmax',
            allowBlank:false,
            maxLength :2,
            width: 60
        });

        var apoyo = new Ext.form.ComboBox({
            typeAhead: true,
            width: 300,
            //forceSelection: true,
            //readOnly :true,
            fieldLabel: 'Tabla de Apoyo',
            labelAlign: 'right',
            hiddenName:'otTabval',
            store: cargarComboApoyo(),
            displayField:'dstabla',
            valueField: 'cdtabla',
            mode: 'local',
            triggerAction: 'all',
            emptyText:'Selecciona una Tabla...'

        });


        var expresion = new Ext.form.TextField({
            fieldLabel: 'Expresi&oacute;n Asociada',
            name: 'cdExpres',
            anchor:'98%',
            width: 250
        });
        var cmTA = new Ext.grid.ColumnModel([
            new Ext.grid.RowNumberer(),
            {header: "cdtabla",dataIndex:'cdtabla',width: 50, sortable:true,locked:true},
            {header: "Descripci&oacute;n",dataIndex:'dstabla',width:150,sortable:true,locked:false}

        ]);

        var formPanel = new Ext.form.FormPanel({
            baseCls: 'x-plain',
            labelAlign: 'right',
            labelWidth: 75,
            url:'reportesPrincipal/insertarPlantillasAtributos.action',
            items: [
                codigoVista,
                atributo,
                formato,
                minimo, maximo,apoyo,codigo
            ]
        });


        var window = new Ext.Window({
            title: 'Insertar/Actualizar Atributos',
            plain:true,
            closable:false,
            width: 570,
            height:290,
            layout: 'fit',
            bodyStyle:'padding:5px;',
            buttonAlign:'center',
            modal:true,
            items: formPanel,
            buttons: [{
                text: 'Guardar',
                handler: function() {
                    if ((minimo.getValue()>maximo.getValue())&&(minimo.getRawValue()!='')){
                        Ext.MessageBox.alert('Error', 'El campo m&aacute;ximo debe ser mayor que el campo m&iacute;nimo');

                    }else if((minimo.getValue()== 0)&&(minimo.getRawValue()!='')){
                        Ext.MessageBox.alert('Error', 'El campo m&iacute;nimo debe ser mayor que cero');
                    }else{
                        if (formPanel.form.isValid()) {
                            formPanel.form.submit({
                                waitTitle:'Espere',
                                waitMsg:'Procesando...',
                                failure: function(form, action) {
                                    Ext.MessageBox.alert('EL Atributo no fue creado correctamente', action.result.wrongMsg);
                                    window.hide();
                                    reloadGrid();

                                },
                                success: function(form, action) {
                                    Ext.MessageBox.alert('El atributo fue agregado correctamente', action.result.wrongMsg);
                                    window.hide();
                                    reloadGrid();

                                }
                            });
                        } else {
                            Ext.MessageBox.alert('Error', 'Complete la informaci&oacute;n requerida.');
                        }
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


    /*****se crea el mensaje de borrar un atributo***/

    var msgBorrar = new Ext.form.TextField({
        fieldLabel:'¿ Esta seguro que desea Eliminar el atributo seleccionado ?',
        labelSeparator:'',
        hidden:true
    });

    var cdPlantilla = new Ext.form.NumberField({
        //fieldLabel: 'C&oacute;digo Plantilla',
        name:'cdPlantilla',
        //anchor:'70%',
        hidden: true,
        allowBlank:false,
        hiddeParent:true,
        labelSeparator:''

    });

    var cdAtributos = new Ext.form.NumberField({
        //fieldLabel: 'C&oacute;digo Atributo',
        name:'cdAtributo',
        //anchor:'70%',
        hidden: true,
        allowBlank:false,
        hiddeParent:true,
        labelSeparator:''
        //disabled : true,


    });

    var borrarForm = new Ext.FormPanel({
        id:'borrarForm',
        labelAlign:'top',
        baseCls: 'x-plain',
        anchor:'100%',
        url:'reportesPrincipal/borrarPlantillasAtributos.action',
        items:[ msgBorrar, cdPlantilla , cdAtributos]
    });

    var windowBorrar = new Ext.Window({
        title: 'Eliminar Atributo',
        minHeight: 50,
        minWidth: 250,
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
                            Ext.MessageBox.alert('Error Eliminando Atributo', action.result.wrongMsg);
                            windowBorrar.hide();
                            grid2.destroy();
                            createGrid();
                        },
                        success: function(form, action) {
                            Ext.MessageBox.alert('Atributo Eliminado', action.result.wrongMsg);
                            windowBorrar.hide();
                            //reloadGrid();
                            grid2.destroy();
                            createGrid();
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

    /*************************************
     *Crea la ventana de editar
     **************************************/
    //function seditar(codigoEditart,codigoAtributot,editarAtributot,editarFormatot,editarMinimot,editarMaximot,editarApoyot){
    var codigoEditar = new Ext.form.TextField({
        fieldLabel: 'C&oacute;digo Plantilla',
        id:'codigoEditar',
        name: 'cdPlantilla',
        // hidden:true,
        width: 60,
        //hidden: true,
        allowBlank:false,
        disabled : true,
        //hiddeParent:true,
        //labelSeparator:'',
        readOnly:true,
        //disabled : true,
        value:codigoPlantilla
    });
    //codigoEditar.setValue(codigoEditart);
    /* var codigoEditar = new Ext.form.TextField({
     fieldLabel : '',
     name: 'cdPlantilla',
     allowBlank: false,
     anchor:'90%',
     hiddeParent:true,
     labelSeparator:'',
     hidden : true
     //width: 60
     });*/
    var codigoAtributo = new Ext.form.TextField({
        fieldLabel : 'C&oacute;digo Atributo',
        id:'codigoAtributo',
        name: 'cdAtributo',
        // hidden : true,
        allowBlank: false,
        //anchor:'30%',
        // hiddeParent:true,
        //labelSeparator:'',
        //disabled : true,
        readOnly:true,
        disabled:true,
        value:codigoAtributot,
        width: 40
    });
    //codigoAtributo.setValue(codigoAtributot);
    var editarAtributo = new Ext.form.TextField({
        fieldLabel: 'Descripci&oacute;n de Atributo',
        id:'editarAtributo',
        allowBlank: false,
        name: 'dsAtributo',
        maxLength :120,
        width: 300,
        listeners: {
                change : function(extFormComboBox, extDataRecord, numberIndex ){
                    var cad = trim(editarAtributo.getValue());
                     if(cad == '')  {
                        editarAtributo.setValue('');
                    }
                }
            }
    });
    //editarAtributo.setValue(editarAtributot);
    var editarFormato = new  Ext.form.ComboBox({
        id:'formatoCombo1',
        labelAlign: 'right',
        //readOnly :true,
        forceSelection: true,
        fieldLabel: 'Formato',
        hiddenName:'swFormat',
        store: cargarCombo(),
        displayField:'descripcion',
        allowBlank: true,
        valueField: 'nmGrafico',
        mode: 'local',
        triggerAction: 'all',
        emptyText:'Selecciona un formato...'
    });
    //editarFormato.setValue(editarFormatot);
    var editarMinimo = new Ext.form.NumberField({
        fieldLabel: 'M&iacute;nimo',
        allowBlank: false,
        name: 'nmLmin',
        maxLength :2,
        width: 60
    });
    //editarMinimo.setValue(editarMinimot);
    var editarMaximo = new Ext.form.NumberField({
        //readOnly :true,
        fieldLabel: 'M&aacute;ximo',
        allowBlank: false,
        maxLength :2,
        name: 'nmLmax',
        //labelSeparator:'',
        width: 60
    });
    //editarMaximo.setValue(editarMaximot);
    var tapoyoo = new Ext.form.TextField({
        // fieldLabel: 'tapoyo',
        name: 'otTabval',
        hidden : true,
        hiddeParent:true,
        labelSeparator:'',
        width: 60
    });
    var editarApoyo = new Ext.form.ComboBox({
        typeAhead: false,
        forceSelection: false,
        id:'editarApoyo',
        labelAlign: 'right',
        width: 300,
        //readOnly:true,
        fieldLabel: 'Tabla de Apoyo',
        hiddenName:'otTabval',
        store: cargarComboApoyo(),
        displayField:'dstabla',
        valueField: 'cdtabla',
        mode: 'remote',
        triggerAction: 'all',
        emptyText:'Selecciona una Tabla...'


    });
    editarApoyo.on('beforeselect',function(cmb,record,index){
        tapoyoo.setValue(record.get('dstabla'));
    });

    // editarApoyo.setValue(editarApoyot);
    var editarForm = new Ext.form.FormPanel({
        id:'editForm',
        baseCls: 'x-plain',
        labelAlign: 'right',
        labelWidth: 75,
        url:'reportesPrincipal/editarPlantillasAtributos.action',
        items: [
            codigoEditar,
            codigoAtributo,
            editarAtributo,
            editarFormato, editarMinimo,editarMaximo, editarApoyo,tapoyoo
        ]
    });
    var windowEditar = new Ext.Window({
        title: 'Editar/Actualizar Atributos',
        plain:true,
        closable:false,
        width: 470,
        height:320,
        layout: 'fit',
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        modal:true,
        items: editarForm,
        buttons: [{
            text: 'Guardar',
            handler: function() {
                if (editarMinimo.getValue()>editarMaximo.getValue()){
                    Ext.MessageBox.alert('Error', 'El campo m&aacute;ximo debe ser mayor que le campo minimo');

                }else if(editarMinimo.getValue()== 0){
                    Ext.MessageBox.alert('Error', 'El campo minimo debe ser mayor que cero');
                }else{
                    if (editarForm.form.isValid()) {
                        editarForm.form.submit({
                            params:{
                                cdPlantillaAux:Ext.getCmp('codigoEditar').getValue(),
                                cdAtributoAux:Ext.getCmp('codigoAtributo').getValue(),
                                otTabvalAux:Ext.getCmp('editarApoyo').getRawValue()
                            },
                            waitTitle:'Espere',
                            waitMsg:'Procesando...',
                            failure: function(form, action) {
                                Ext.MessageBox.alert('EL Atributo no fue editado correctamente', action.result.wrongMsg);
                                windowEditar.hide();
                                reloadGrid();
                            },
                            success: function(form, action) {
                                Ext.MessageBox.alert('El atributo fue editado correctamente', action.result.wrongMsg);
                                windowEditar.hide();
                                //windowEditar.destroy();
                                grid2.destroy();
                                createGrid();
                            }
                        });
                    } else {
                        Ext.MessageBox.alert('Error', 'Por favor revise los errores!');
                    }

                }
            }
        },{
            text: 'Regresar',
            handler: function() {
                windowEditar.hide();
                //windowEditar.destroy();

            }
        }]
    });

    /******************
     *Form de busqueda para el grid.
     *******************/

    var codigoText = new Ext.form.TextField({
        fieldLabel: '<span style="color:white;font-size:14px;font-family:Arial,Helvetica,sans-serif;">5</span>C&oacute;digo',
        name : 'cdPlantilla',
        value : codigoPlantilla,
        disabled : true,
        width: 50
    });

    var descripcion = new Ext.form.TextField({
        fieldLabel: 'Descripci&oacute;n',
        name : 'dsPlantilla',
        value : descripcionPlantilla,
        disabled : true,
        width: 450
    });


    var atributosForm = new Ext.form.FormPanel({
        title: '<span style="color:black;font-size:14px;">Administraci&oacute;n de Atributos</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',
        //renderto:'formBusqueda',
        frame:true,
        //      url:_ACTION_MANTTO,
        width: 700,
        height:130,
        items: [{
            layout:'form',
            border: false,
            items:[{
                title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Plantilla</span>',
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
    atributosForm.render('formBusqueda');
    //atributosForm.render();

    function toggleDetails(btn, pressed) {
        var view = grid.getView();
        view.showPreview = pressed;
        view.refresh();
    };


    var sm = new Ext.grid.RowNumberer;
    var cm = new Ext.grid.ColumnModel([
        new Ext.grid.RowNumberer(),
        {header: "C&oacute;digo Atributo",  dataIndex:'cdAtributo',    width: 70, sortable:true,     locked:false},
        {header: "Descripci&oacute;n",  dataIndex:'dsAtributo',    width: 250, sortable:true,     locked:false},
        {header: "Formato",   dataIndex:'swFormat',    width: 50, sortable:true},
        {header: "M&iacute;nimo",   dataIndex:'nmLmin',    width: 70, sortable:true},
        {header: "M&aacute;ximo",   dataIndex:'nmLmax',    width: 70, sortable:true},
        {header: "Tabla",   dataIndex:'otTabval',    width: 120, sortable:true},
        {header: "Tabla",   dataIndex:'cdotTabval', hidden:true ,  width: 120, sortable:true}

    ]);

    cm.defaultSortable = true;

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
            id: 'grid2',
            store:cargarTabla(),
            border:true,
            //baseCls:' background:white',
            loadMask: {msg: getLabelFromMap('400058',helpMap,'Cargando datos ...'), disabled: false},
            cm: cm,
            buttonAlign:'center',
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    rowselect: function(sm, row, rec) {
                        seleccion = false;
                        selectedId = store.data.items[row].id;
                        codigoEditart=store.data.items[row].data.cdAtributo;
                        codigoAtributot=store.data.items[row].data.cdAtributo;
                        editarAtributot=store.data.items[row].data.dsAtributo;
                        editarFormatot=store.data.items[row].data.swFormat;
                        editarMinimot=store.data.items[row].data.nmLmin;
                        editarMaximot=store.data.items[row].data.nmLmax;
                        editarApoyot=store.data.items[row].data.otTabval;

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
            buttons:[
                {text:'Agregar',
                    tooltip:'Agregar un nuevo atributo',
                    handler:function() {
                        agregar();
                    }
                },{
                id:'editar',
                text:'Editar',
                tooltip:'Editar el atributo seleccionado',
                handler: function() {
                    if (seleccion) {
                        Ext.MessageBox.alert('Seleccione', 'Seleccione el Atributo a editar');
                    }else{
                        //seditar(codigoEditart,codigoAtributot,editarAtributot,editarFormatot,editarMinimot,editarMaximot,editarApoyot);

                        windowEditar.show();
                        //Ext.getCmp('editForm').getForm().loadRecord(rec);
                        /*
                         Ext.getCmp('editar').on('click', function() {
                         codigoEditar.setValue(codigoEditart);
                         codigoAtributo.setValue(codigoAtributot);
                         editarAtributo.setValue(editarAtributot);
                         editarFormato.setValue(editarFormatot);
                         editarMinimo.setValue(editarMinimot);
                         editarMaximo.setValue(editarMaximot);
                         editarApoyo.setValue(editarApoyot);*/


                        //seditar(codigoEditart,codigoAtributot,editarAtributot,editarFormatot,editarMinimot,editarMaximot,editarApoyot);

                    };//**//**//*

                }

            },{
                text:'Eliminar',
                id:'borrar',
                tooltip:'Eliminar el atributo seleccionado',
                handler: function() {
                    if (seleccion) {
                        Ext.MessageBox.alert('Seleccione', 'Seleccione el Atributo a eliminar');
                    }
                }
            },{
                text:'Exportar',
                tooltip:'Exportra los atributos de la Plantilla',
                handler: function(){
                    showExportDialog(_ACTION_EXPORT +'?cdPlantilla='+codigoText.getValue()+'&descripcion=GraficosPlantilllasAtributos')
                }
            },{
                text:'Regresar',
                id:'regresar',
                tooltip:'Regresar a la pantalla de Plantillas',
                handler: function () {
                    window.location.replace(_REGRESAR);
                }
            }
            ],
            width:700,
            frame:true,
            height:300,
            title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
            collapsible: true,
            renderTo:document.body,
            bbar: new Ext.PagingToolbar({
                //pageSize:itemsPerPage,
                pageSize:200,
                store: store,
                displayInfo: true,
                displayMsg: getLabelFromMap('400009', helpMap,'Mostrando registros {0} - {1} of {2}'),
                emptyMsg: getLabelFromMap('400012', helpMap,'No hay registros para visualizar')
            }),

            viewConfig: {autoFill: true,forceFit:true}
        });
        grid2.render('gridElementos');
        store.load({params:{ start:0,limit:50,codigoPlantilla: codigoPlantilla}});
    };
    createGrid();

    function reloadGrid(){
        var _params = {
            codigoPlantilla  : codigoPlantilla//Ext.getCmp('reportesForm').form.findField('dsReporte').getValue()
        };
        reloadComponentStore(Ext.getCmp('grid2'), _params, cbkReload);
    }
    function cbkReload(_r, _options, _success, _store) {
        if (!_success) {
            _store.removeAll();
            Ext.Msg.alert(getLabelFromMap('400010', helpMap,'Aviso'), _store.reader.jsonData.actionErrors[0]);
        }
    }

    function presionarEnter2( extTextField, event ) {
        var i = event.getCharCode();
        if ( i == 32 ){ // Space
            event.stopPropagation();
            event.stopEvent();
        }
    }

});