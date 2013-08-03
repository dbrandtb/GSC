<%@ include file="/taglibs.jsp"%>
/*************************************************************
** Paneles del TAB (3)
**************************************************************/
var  parametrosPanel = new Ext.Panel({
                id:'parametrosPanel',
                title: TAB_1_TITLE,
                layout:'form',
                border: false,
                bodyBorder: false,
                items: [formPanelParam]
               
 });//end panel Parametros Conjunto


var  datosPantallaPanel = new Ext.Panel({
                id:'datosPantallaPanel',
                title: TAB_2_TITLE,
        		//anchor: '100% 100%',
                border: false,
                bodyBorder: false,
                //layout:'form',
                height: 300,
                items: formPanelDatos/*[
                	{
                		layout: 'form',
                		bodyStyle: 'padding: 3px',
                		items: [formPanelDatos]
                	}
                	
                ]*/// end items panel
 });//end panel Datos de Pantalla

var  activacionConjuntoPanel = new Ext.Panel({
                title: TAB_3_TITLE,
                layout:'form',
                border: false,
                bodyBorder: false,
                items: [formPanelActivacion]// end items panel
 });//end panel Activacion de Conjunto
   
/*************************************************************
** TabPanel
**************************************************************/

var tabs1 = new Ext.TabPanel({
        renderTo: document.body,
        activeTab: 0,
        id: 'panelTabsId',
        bodyStyle:'padding:5px',
        deferredRender:false,
        plain:true,
        height: 245,
        hideMode:'offset',
                border: false,
                bodyBorder: false,
        items:[parametrosPanel, datosPantallaPanel, activacionConjuntoPanel ]
    });

  paramEntradaPanel.add(tabs1);

