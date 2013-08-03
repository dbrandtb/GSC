<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">



<head>

<script type="text/javascript">

Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";



function selectAllOptions()
{
  var selObj = Ext.getDom('copiaPantallasElemento');
  for (var i=0; i<selObj.options.length; i++) {
    selObj.options[i].selected = true;
  }
}

var copiaPantallasHidden = new Ext.form.Hidden({
     	id: 'copiaPantallasId',
     	name:'copiaPantallas'
}); 
      

/*************************************************************
** Store combos
**************************************************************/ 

  var  storeCliente = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
                url: 'confpantallas/obtenerListasConfPantallas.action'
           }),
            reader: new Ext.data.JsonReader({
            root: 'clienteList',
            id: 'cdCliente'
            },[
           {name: 'cdCliente', type: 'string',mapping:'cdCliente'},
           {name: 'dsCliente', type: 'string',mapping:'dsCliente'}    
            ]),
            remoteSort: true
        });
        storeCliente.setDefaultSort('cdCliente', 'desc');
        storeCliente.load();
 

/*************************************************************
** Combos
**************************************************************/ 

 var comboCliente = new Ext.form.ComboBox({
 name: 'clienteCopia',
 fieldLabel: 'Cliente',
 store: storeCliente,
 displayField:'dsCliente',
 hiddenName: 'clienteCopia',  
 valueField:'cdCliente',
 typeAhead: true,
 width: 280,
 mode: 'local',
 allowBlank: false,
 triggerAction: 'all',
 emptyText:'Seleccione...'
  });
 
 
/*************************************************************
** Textfields del originalFieldSet
**************************************************************/ 
 var procesoOriginal = new Ext.form.TextField({
        fieldLabel: 'Proceso',
        disabled : true,
        width : 280,
        value:'<s:property value="%{procesoOriginal}" />',
        name:'procesoOriginal'
   });
 
 var clienteOriginal = new Ext.form.TextField({
        fieldLabel: 'Cliente',
        value:'<s:property value="%{clienteOriginal}" />',
        width : 280,
        disabled : true,
        name:'clienteOriginal'
   });
   
 var productoOriginal = new Ext.form.TextField({
        fieldLabel: 'Producto',
        value:'<s:property value="%{productoOriginal}" />',
        width : 280,
        disabled : true,
        name:'productoOriginal'
   });
 
 var nombreConjOriginal = new Ext.form.TextArea({
        fieldLabel: 'Nombre de Conjunto',
        value:'<s:property value="%{nombreConjuntoOriginal}" />',
        width : 280,
        height:40,  
        disabled : true,
        grow: true,
        preventScrollbars:true,
        name:'nombreConjuntoOriginal'
   });
   
    var descConjOriginal = new Ext.form.TextArea({
        fieldLabel: 'Descripcion Breve',
        value:'<s:property value="%{descripcionConjuntoOriginal}" />',
        name:'descripcionConjuntoOriginal',
        disabled : true,
        grow: true,
        preventScrollbars:true,
        width : 280
        
   });
               
 var cdConjunto = new Ext.form.TextField({
    name:'cdConjunto',
    type:'hidden',
    value:'<s:property value="%{cdConjunto}" />',
    labelSeparator:'',
    hidden:true
 });
 
 var cdProceso = new Ext.form.TextField({
    name:'cdProceso',
    value:'<s:property value="%{cdProceso}" />',
    type:'hidden',
    labelSeparator:'',
    hidden:true
 });
 
 var cdProducto = new Ext.form.TextField({
    name:'cdProducto',
    value:'<s:property value="%{cdProducto}" />',
    type:'hidden',
    labelSeparator:'',
    hidden:true
 });
 
 var procesoCopia = new Ext.form.TextField({
        fieldLabel: 'Proceso',
        disabled : true,
        width : 280,
        value:'<s:property value="%{procesoCopia}" />',
        name:'procesoCopia'
       
   });
 
 var productoCopia = new Ext.form.TextField({
        fieldLabel: 'Producto',
        width : 280,
        disabled : true,
        value:'<s:property value="%{productoCopia}" />',
        name:'productoCopia'
        
   });
   
   var nombreConjCopia = new Ext.form.TextArea({
        fieldLabel: 'Nombre de Conjunto',
        value:'<s:property value="%{nombreConjuntoCopia}" />',
        name:'nombreConjuntoCopia',
        grow: false,
        height:40, 
        allowBlank: false,
        width : 280,
        preventScrollbars:true
       
   });
   
   var descConjCopia = new Ext.form.TextArea({
        fieldLabel: 'Descripcion Breve',
        value:'<s:property value="%{descripcionConjuntoCopia}" />',
        name:'descripcionConjuntoCopia',
        grow: false,
        width : 280,
        allowBlank: false,
        preventScrollbars:true
        
   });
   
   
 /*************************************************************
** FieldSets Original
**************************************************************/ 
 var originalFieldSet = new Ext.form.FieldSet({
    id:'originalFieldSet',
    style:'margin:5px',
    labelWidth: 65,
    bodyStyle:'padding:5px',
    title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Original</span>',  
    height:270,  
    hideBorders : true,
    defaultType: 'textfield',
    items: [procesoOriginal,clienteOriginal,productoOriginal,nombreConjOriginal,descConjOriginal,cdConjunto ]//end items FieldSet
 }); // end FieldSet de Original
 
/*************************************************************
** FieldSets Copia
**************************************************************/ 
 var copiaFieldSet = new Ext.form.FieldSet({
 id:'copiaFieldSet',
 style:'margin:5px',
 labelWidth: 65,
 bodyStyle:'padding:5px',
 title: '<span style="color:#98012e;font-size:11px;font-family:Arial,Helvetica,sans-serif;">Copia</span>', 
 height:270,  
 hideBorders : true,
 defaultType: 'textfield',
 items: [procesoCopia,comboCliente,productoCopia,nombreConjCopia,descConjCopia,cdProceso,cdProducto]//end items FieldSet
});//end FieldSet copia
                   
                        
 
 var copiadoFormPanel =  new Ext.form.FormPanel({                          
   id:'formPanelCopiado',
   layout:'table',
   layoutConfig: {
            columns: 2
                        },
   border:false,
   autoHeight : true,
   columnWidth: .1,
   hideBorders : true,
   items:[{
          columnWidth:.5,
           items:[originalFieldSet]//end items columna 1     
          },{
          columnWidth:.5,                
          items:[ copiaFieldSet]//end items columna2
          },{
          colspan: 2,
          border: false,
          contentEl:'lista'
        }],//end items FormPanel
  buttons:[{                               
             text:'Copiar',
             handler: function() {  
             selectAllOptions();
             
             var selObj = Ext.getDom('copiaPantallasElemento');
             var pantallas = "";
             
             for(j=0 ; j < selObj.options.length ; j++){
             	pantallas+= "copiaPantallas=" + selObj.options[j].value + "&&";
             }
             
             Ext.getCmp('copiaPantallasId').setValue(pantallas);
                                  
                        if (copiadoFormPanel.form.isValid()) {
                        
                         copiadoFormPanel.form.submit({   
                         params: pantallas,
                         url:'confpantallas/copiarConjunto.action',
                              waitMsg:'Procesando...',
                                     failure: function(form, action) {
                                     
                                           //Ext.MessageBox.alert('Copia Finalizada', 'El cliente seleccionado no cuenta con el producto del original');
                                           Ext.MessageBox.alert('Error en la Copia', Ext.util.JSON.decode(action.response.responseText).resultado);
                                                   },
                                     success: function(form, action) {
                                     
                                     Ext.MessageBox.alert('Copia Finalizada', Ext.util.JSON.decode(action.response.responseText).resultado);
                                      //Ext.MessageBox.alert('Copia Finalizada', 'Exitosamente');
                                                    }
                                        });     
                                        
                             
                      }else{
                          Ext.MessageBox.alert('Error', 'Por favor revise los errores');
                       }  
              }      //end handler   
             },{
               text:'Cerrar',
               handler: function() { 
               window.close();
               }
            }]// end buttons FormPanel
 });//end FormPanel

/*************************************************************
** Panel
**************************************************************/
 var copiadoPanel = new Ext.Panel({
        region: 'north',
        title: 'COPIADO DE PANTALLAS',
        iconCls:'logo',
        split:true,
        height:600,
        id:'up',
        bodyStyle:'padding:5px',
        // collapsible: true,
        layoutConfig:{
             animate:true
        },
       items:[copiadoFormPanel]
   });


 copiadoPanel.render(document.body);
        
});

</script>

</head>
<body>
<div id="lista" style="margin-left: 120px;" >
<s:form id="formList" action="copiarConjunto"  method="post" namespace='/confpantallas'>
<s:optiontransferselect 
     allowUpDownOnLeft="false"
     allowUpDownOnRight="false"
     allowSelectAll="false"
     cssClass="optiontransferselect"
     doubleCssClass="optiontransferselect"
     buttonCssClass="botonTransferSelect"
     size="15"
     multiple="true"
     id="originalPantallas"
     name="originalPantallas"
     list="pantallaList" 
     listKey="value" 
     listValue="label"
     doubleSize ="15" 
     doubleId="copiaPantallasElemento"
     doubleName="copiaPantallasElemento"
     doubleList="pantallaCopiaList"
     doubleListKey = "value"
     doubleListValue ="label"
     doubleMultiple="true"
   />
   
   
   
 </s:form>
</div>
</body>
</html>