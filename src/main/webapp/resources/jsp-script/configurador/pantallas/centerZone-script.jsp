<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">


/*************************************************************
** Creating the Panel to the main view port
**************************************************************/ 


//inicializando builderPanel
  //          var builderPanel = Ext.ComponentMgr.get('FBBuilderPanel');
            
            // creando variable root con el root de treePanel...
    //        var root = tree.root;
            
            //al atributo fEL le asignamos el builderPanel...
      //      root.fEl = builderPanel;
            
            //a la variable root le asignamos a elConfig el valor de this.builderPanel.initialConfig..
        //    root.elConfig =  builderPanel.initialConfig;
            
          //  builderPanel._node = root;




var areaTrabajoPanel = new Ext.Panel({
        region: 'center',        
        style:'padding:3px 5px;background:black',
        layout:'fit',
        border:false,
        bodyBorder:false,        
        title: 'AREA DE TRABAJO',
        split:true,
        items:{border:false,bodyBorder:false,bodyStyle:'background:black;border:dashed green 1px;',layout:'fit',id:'FBBuilderPanel'}
                //cambiar el id....
    });

</script>