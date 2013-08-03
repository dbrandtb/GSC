Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
    var Tree = Ext.tree;
	var jsonData;
	var tree;


		    var tree = new Tree.TreePanel({
		        el:'tree-div',
		        useArrows:true,
		        autoScroll:true,
		        animate:true,
		        enableDD:true,
		        height: 200,
		        containerScroll: false,
				loader: new Tree.TreeLoader()
		    });
		    root = new Tree.TreeNode({
		                text: 'Prueba', 
		                draggable:false, 
		                id:'listaDatos'
		            });
		    tree.setRootNode(root);


	Ext.Ajax.request ({
		url: _ACTION_OBTENER_DATOS_TREE,
		method: 'GET',
		success: function (result, request) {
			jsonData = Ext.util.JSON.decode(result.responseText).listaDatos;

			for (var i=0; i<jsonData.length; i++) {
				root.appendChild(tree.getLoader().createNode(jsonData[i]));
			}

			var el_formpanel = new Ext.FormPanel ({
					el: "div_form",
		            frame : true,
		            header: false,
		            labelWidth: 70,
		            height: 400,
		            width: 500,
		            layout: 'table',
		            layoutConfig: {columns: 2},
		            defaults: {labelWidth: 60},
		            items: [
		            		{
		            			layout: 'form',
		            			items: [
		            				{xtype: 'combo', store: null, fieldLabel: 'Tipo de Guión'}
		            			]
		            		},
		            		{
		            			layout: 'form',
		            			items: [
		            				{xtype: 'textfield', labelSeparator: '', value: 'Operador CAT'}
		            			]
		            		},
		            		{
		            			layout: 'form',
		            			colspan: 2,
		            			items: [
		            				{xtype: 'textfield', labelSeparator: '', value: 'Script - Guión Atención Inicial'}
		            			]
		            		},
		            		{
		            			layout: 'form',
		            			items: [
		            				{xtype: 'textarea', readOnly: true, labelSeparator: '', cols: 70}
		            			]
		            		},
		            		{
		            			layout: 'form',
		            			items: [
		            				tree
		            			]
		            		}
					]
			});
		
			el_formpanel.render();
		    tree.render();
		}
	});


});