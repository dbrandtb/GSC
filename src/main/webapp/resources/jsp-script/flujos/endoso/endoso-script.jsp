<%@ include file="/taglibs.jsp"%>


<script type="text/javascript">

Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    Ext.form.Field.prototype.msgTarget = 'side';
  

/*************************************************************
** TabPanel
**************************************************************/ 
var tabs = new Ext.TabPanel({
        activeTab: 0,
        bodyStyle:'padding:10px',
        deferredRender:false,
        plain:true,
        autoHeight : true ,
        autoWidth : true ,
        frame : true,
        bodyBorder : true,
        border :true,
        layoutOnTabChange : true,
        items:[
        //{html: '<div id="datosGenerales"></div>', title:'Datos Generales'}
        {contentEl:'datosGenerales', title:'Datos Generales'},
        {contentEl:'agentes', title:'Agentes y Contratantes'},
        {contentEl:'incisos', title:'Incisos'},
        {contentEl:'grupos', title:'Grupos'},
        {contentEl:'personas', title:'Personas Aseguradas'},
        {contentEl:'clausulas', title:'Clausulas'},
        {contentEl:'objetos', title:'Objetos'},
        {contentEl:'coberturas', title:'Coberturas y Sumas Aseguradas'},
        {contentEl:'tarificacion', title:'Tarificacion'}
        ]
    });

 

tabs.render('principal');
  
});

</script>