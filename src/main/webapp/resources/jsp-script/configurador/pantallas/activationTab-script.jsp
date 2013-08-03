<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">



/*************************************************************
** FormPanel del TAB 3
**************************************************************/ 

var formPanelActivacion = new Ext.form.FormPanel({                          
                        id:'formPanelActivacion',
                        border:false,
                        hideBorders : true,
                        //labelWidth: 140,
                        layout:'table',
                        layoutConfig: {
                               columns: 5
                        },
                        items: [{
                                colspan: 2,
                                items:[ new Ext.Button({
                                        text: 'Crear Version'
                                        })]
                                },{
                                colspan: 3,
                                items:[ new Ext.Button({
                                        text: 'Cancelar Cambios'
                                        })]
                                },{
                                colspan: 5,
                                items:[ new Ext.Button({
                                        text: 'Cancelar Cambios'
                                        })]
                                },{
                                colspan: 5,
                                items:[ new Ext.form.Checkbox({
                                        boxLabel:'',
                                        name:'1',
                                        id:'1',
                                        inputValue:1
                                        })]
                                },{
                                items:[ new Ext.form.Checkbox({
                                        boxLabel:'V4',
                                        name:'4',
                                        id:'4',
                                        inputValue:1
                                        })]
                                },{ 
                                  html: '<a href="">Cargar</a> &nbsp'        
                                },{ 
                                  html: ' <a href="">Detalle</a> &nbsp'
                                },{ 
                                  html: ' <a href="">Activar</a> &nbsp'
                                },{ 
                                  html: ' <a href="">Probar</a> &nbsp'  
                                },{
                                items:[ new Ext.form.Checkbox({
                                        boxLabel:'V3',
                                        name:'3',
                                        id:'3',
                                        inputValue:1
                                        })]
                                },{ 
                                  html: '<a href="">Cargar</a> &nbsp'        
                                },{ 
                                  html: ' <a href="">Detalle</a> &nbsp'
                                },{ 
                                  html: ' <a href="">Activar</a> &nbsp'
                                },{ 
                                  html: ' <a href="">Probar</a> &nbsp'  
                              },{
                              items:[ new Ext.form.Checkbox({
                                      boxLabel:'V2',
                                      name:'2',
                                      id:'2',
                                      inputValue:1
                                      })]
                                },{ 
                                  html: '<a href="">Cargar</a> &nbsp'        
                                },{ 
                                  html: ' <a href="">Detalle</a> &nbsp'
                                },{ 
                                  html: ' <a href="">Activar</a> &nbsp'
                                },{ 
                                  html: ' <a href="">Probar</a> &nbsp'  
                             },{
                             items:[ new Ext.form.Checkbox({
                                     boxLabel:'V1',
                                     name:'1',
                                     id:'1',
                                     inputValue:1
                                     })]
                             },{ 
                                  html: '<a href="">Cargar</a> &nbsp'        
                             },{ 
                             colspan: 4,
                             html: ' <a href="">Detalle</a> &nbsp'
                             },{
                             colspan: 5,
                            items:[ new Ext.Button({
                                    text: 'Eliminar'
                                   })]
                      }]//end items FormPanel
});//end FormPanel


</script>