<%@ include file="/taglibs.jsp"%>

/*************************************************************
** FormPanel del TAB 3
**************************************************************/ 
var formPanelActivacion = new Ext.form.FormPanel({                          
                        id:'formPanelActivacion',
                        border:false,
                        hideBorders : true,
                        labelWidth: 50,
                        layout:'form',
                        buttonAlign : 'left',
                        bodyStyle:'margin-top: 10px; margin-left: 10px; margin-bottom: 20px;',
                        items: [comboActivacion, cdProcesoActivacion, cdCliente, cdProductoActivacion
                           ],//end items FormPanel
                        buttons:[{
                                 text: TAB_3_BOTON_ACEPTAR,
                                 id:'btnAceptarActivacion',
                                 tooltip: 'Guarda el nuevo estado',
                                 handler: function() { 
                                   Ext.MessageBox.buttonText.ok = TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK;
                                     Ext.MessageBox.buttonText.cancel = TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_CANCEL;
                                     Ext.Msg.show({
                                                title: TAB_3_BOTON_ACEPTAR_MSGBOX_TITLE,
                                                msg: TAB_3_BOTON_ACEPTAR_MSGBOX_MSG,
                                                buttons: Ext.Msg.OKCANCEL,
                                                fn: procesarResult,
                                                icon: Ext.MessageBox.QUESTION
                                        
                                    });
                                     
                                     function procesarResult (btn){
                                        if(btn=='ok'){
                                         if (formPanelActivacion.form.isValid()) {
                                                    formPanelActivacion.form.submit({ 
                                                       url:'configuradorpantallas/activarConjunto.action',
                                                        waitMsg: TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_WAIT_MSG,
                                                        failure: function(form, action) {
                                                            Ext.MessageBox.alert(TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_FAILURE, action.result.actionErrors[0]/*TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_FAILURE_DESC*/);
                                                        },
                                                        success: function(form, action) {
                                                            Ext.MessageBox.alert(TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_SUCCESS, action.result.actionMessages[0]/*TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_SUCCESS_DESC*/);
                                                           
                                                        }
                                                     });
                                            } else{
                                                 Ext.MessageBox.alert(TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_ERROR, TAB_3_BOTON_ACEPTAR_MSGBOX_BOTON_OK_ERROR_DESC);
                                            }
                                         }else{
                                         }
                                         }//functionprocesarResult
                                    }      //end handler  
                            
                      }]//end buttons FormPanel
});//end FormPanel
