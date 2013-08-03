<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">

/*************************************************************
** Creating the Panels to the north zone
**************************************************************/ 
var  parametrosPanel = new Ext.Panel({
                id:'parametrosPanel',
                title: 'Parametros Conjunto',
                layout:'form',
                items: [formPanelParam]// end items panel                
 });//end panel Parametros Conjunto
   

var  datosPantallaPanel = new Ext.Panel({
                id:'datosPantallaPanel',
                title: 'Datos de Pantalla',
                layout:'form',
                items: [formPanelDatos]// end items panel
 });//end panel Datos de Pantalla

var  activacionConjuntoPanel = new Ext.Panel({
                title: 'Activacion de Conjunto',
                layout:'form',
                items: [formPanelActivacion]// end items panel
 });//end panel Activacion de Conjunto
    

/*************************************************************
** Creating the TabPanel Container
**************************************************************/ 
var northTabs = new Ext.TabPanel({
        renderTo: document.body,
        activeTab: 0,
        bodyStyle:'padding:10px',
        deferredRender:false,
        plain:true,
        height:235,
        items:[parametrosPanel, datosPantallaPanel, activacionConjuntoPanel ]
    });


/*************************************************************
** Creating the Panel to the main view port
**************************************************************/ 
var paramEntradaPanel = new Ext.Panel({
        region: 'north',
        title: 'PARAMETROS DE ENTRADA',
        iconCls:'logo',
        split:true,        
        height:260,
        id:'up',
        bodyStyle:'padding:5px',
        collapsible: true,
        layoutConfig:{
             animate:true
        }
    });

paramEntradaPanel.add(northTabs);

</script>