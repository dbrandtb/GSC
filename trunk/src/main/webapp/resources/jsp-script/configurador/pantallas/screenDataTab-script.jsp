<%@ include file="/taglibs.jsp"%>




<script type="text/javascript">

/*************************************************************
** Combo Store Section
**************************************************************/

var storeTipoPantalla;
var idProceso= ' ';
function getTiposPantallas(idProceso){  
    url =  'confbeta/obtenerMasters.action'+'?cdProceso='+idProceso
    storeTipoPantalla = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: url
            }),
            reader: new Ext.data.JsonReader({
            root: 'tipoPantallaList',
            id: 'cdMaster'
            },[
           {name: 'cdTipo', type: 'string', mapping:'cdTipo'},
           {name: 'cdMaster', type: 'string', mapping:'cdMaster'},
           {name: 'dsMaster', type: 'string', mapping:'dsMaster'}    
             ]),
            remoteSort: true
        });
        storeTipoPantalla.setDefaultSort('cdMaster', 'desc');
        storeTipoPantalla.load();
        return storeTipoPantalla;
 }
 
/*************************************************************
** Textfields y combo del FormPanel del TAB 2
**************************************************************/ 
var nombrePantalla = new Ext.form.TextField({
     fieldLabel: 'Nombre de Pantalla',
     name:'nombrePantalla',
     allowBlank: false,
     width : 250,
     maxLengthText:'30'
   });
 
 var descripcionPantalla = new Ext.form.TextArea({
     fieldLabel: 'Descripcion de la Pantalla',
     name:'descripcionPantalla',
     allowBlank: false,
     grow: false,
     height:40,  
     preventScrollbars:true,
     width : 250
     
   });
   
   
   var comboTipoPantalla = new Ext.form.ComboBox({
    name: 'tipoPantalla',
    id: 'tipoPantalla',
    fieldLabel: 'Pantalla Master',
    store: getTiposPantallas(),
    displayField:'dsMaster',
    hiddenName: 'htipoPantalla',  
    valueField:'cdMaster',
    typeAhead: true,
    mode: 'local',
    triggerAction: 'all',
    width:240,
    emptyText:'Seleccione...',
    editable:true,
    autoHeight:'true',
    allowBlank: false,
    labelSeparator:'', 
    selectOnFocus:true
});
   
   
   var cdProceso = new Ext.form.TextField({
    name:'cdProceso',
    type:'hidden',
    hidden:true,
    labelSeparator:''
    });
 
  var cdPantalla = new Ext.form.TextField({
    name:'cdPantalla',
    type:'hidden',
    hidden:true,
    labelSeparator:''
});
 
 var cdP = new Ext.form.TextField({
    name:'cdP',
    type:'hidden',
    hidden:true,
    labelSeparator:''
});
  
 
 var ejemAreaTrabajo = new Ext.form.TextField({
     fieldLabel: 'Dato ejemplo',
     type:'hidden',
     hidden:true,
     name:'ejemAreaTrabajo'
    
});


/*************************************************************
** FormPanel del TAB 2
**************************************************************/ 
var formPanelDatos = new Ext.form.FormPanel({                          
                        id:'formPanelDatos',
                        border:false,
                        labelWidth: 140,
                        layout:'form',
                        margins:'5 5 0 5',
                        items: [cdPantalla,
                                nombrePantalla,
                                comboTipoPantalla,
                                descripcionPantalla,
                                cdP
                               
                           ],//end items FormPanel
                        buttons:[{                               
                                 text:'Salvar',
                                      handler: function() { 
                                        if (formPanelDatos.form.isValid()) {
                                                formPanelDatos.form.submit({ 
                                                    url:'confbeta/salvarPantalla.action',
                                                    waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert('Pantalla no salvada', 'Se produjo error');
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert('Pantalla salvada', 'existosamente');
                                                        var treeN = Ext.getCmp('treeNavegacion');  
                                                        treeN.root.reload();
                                                        comboTipoPantalla.disable();
                                                    }
                                                 });                   
                                        } else{
                                             Ext.MessageBox.alert('Error', 'Por favor revise los errores');
                                        }
                                    }      //end handler  
                                   
                                 },{
                                 text:'Eliminar Pantalla',
                                 id:'btnEliminar',
                                 handler: function() { 
                                 Ext.MessageBox.buttonText.ok = 'Aceptar';
                                 Ext.MessageBox.buttonText.cancel = 'Cancelar';
                                 Ext.Msg.show({
                                            title:'Eliminar pantalla',
                                            msg: '¿Desea eliminar esta pantalla?',
                                            buttons: Ext.Msg.OKCANCEL,
                                            fn: procesarResultado,
                                            icon: Ext.MessageBox.QUESTION
                                    
                                });
                                 
                                 function procesarResultado (btn){
                                    if(btn=='ok'){
                                            if (formPanelDatos.form.isValid()) {
                                                formPanelDatos.form.submit({ 
                                                    url:'confbeta/eliminarPantalla.action',
                                                    waitMsg:'Procesando...',
                                                    failure: function(form, action) {
                                                        Ext.MessageBox.alert('Pantalla no eliminada', 'Se produjo error');
                                                    },
                                                    success: function(form, action) {
                                                        Ext.MessageBox.alert('Pantalla eliminada', 'existosamente');
                                                        formPanelDatos.form.reset();
                                                        Ext.get('btnEliminar').dom.disabled = true;
                                                        Ext.get('btnNPantalla').dom.disabled = true;
                                                        comboTipoPantalla.enable();
                                                        var treeN = Ext.getCmp('treeNavegacion');  
                                                        treeN.root.reload();
                                                    }
                                                 });                   
                                        } else{
                                             Ext.MessageBox.alert('Error', 'Por favor revise los errores');
                                        }
                                    }else{
                                     }
                                     }//functionprocesarResultado
                                   }      //end handler  
                                 
                                 },{
                                 text:'Nueva Pantalla',
                                 id:'btnNPantalla',
                                        handler: function() { 
                                            Ext.get('btnEliminar').dom.disabled = true;
                                            Ext.get('btnNPantalla').dom.disabled = true;
                                            formPanelDatos.form.reset();
                                             comboTipoPantalla.enable();
                                               formPanelDatos.form.submit({ 
                                               url:'confpantallas/nuevaPantalla.action'
                                            });
                                            
                                            
                                 }//end handler
                                 },{
                                 text:'Vista Previa de Pantalla',
                                 handler: function() { 
                                 windowVistaPrevia.show();
                                  }//end handler
                              }]// end buttons FormPanel
});//end FormPanel
 
 


</script>