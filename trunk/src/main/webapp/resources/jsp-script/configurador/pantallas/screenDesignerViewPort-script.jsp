<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">


Ext.onReady(function(){
    
    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    Ext.form.Field.prototype.msgTarget = "side";
    
    
/*************************************************************
** viewport
**************************************************************/ 
    var viewport = new Ext.Viewport({
        layout: 'border',
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
                       Ext.getCmp('btnEliminar').disable();
                       Ext.getCmp('btnNPantalla').disable();
                       Ext.getCmp('btnVistaPrevia').disable();
                            if(record.get('cdConjunto')==null || record.get('cdConjunto')=="")
                            {
                                comboProceso.enable();
                                comboNivel.enable();
                                comboProducto.enable();
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
                                
                                Ext.getCmp('btnCopiarConjunto').enable();
                                 comboTipoPantalla.clearValue();
                                storeTipoPantalla.baseParams['cdProceso'] = cdProceso.getValue();
                                storeTipoPantalla.load({
                                        callback : function(r,options,success) {
                                            if (!success) {
                                             //  Ext.MessageBox.alert('Aviso','No se encontraron registros');  
                                            storeProducto.removeAll();
                                            }  }
                                      });
                                
                                Ext.getCmp('btnAceptarActivacion').enable();
                                Ext.getCmp('btnSalvarPantalla').enable();
                                
                            }
                            
            }//else getTotalCount not null                
         });
        
            }//beforerender
       },
       
         items:[paramEntradaPanel, masterPanel, areaTrabajoPanel] 
    });
    

 var configuradorPanel = new Ext.form.FormPanel({
         layout: 'fit'
 });

 configuradorPanel.add(viewport);
 configuradorPanel.render(document.body);
    


});



</script>