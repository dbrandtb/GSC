Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    

	/*
	 * Ext.ux.Multiselect Example Code
	 */

/*			var formMultiselect = new Ext.form.FormPanel({ 
				labelWidth: 75,
				labelAlign: "right",
				width:700,
				items:[{
					xtype:"multiselect",
					fieldLabel:"Multiselect",
					name:"multiselect",
					dataFields:["code", "desc"], 
					data:[["123","One Hundred Twenty Three"],
						["1", "One"], ["2", "Two"], ["3", "Three"], ["4", "Four"], ["5", "Five"],
						["6", "Six"], ["7", "Seven"], ["8", "Eight"], ["9", "Nine"]],
					valueField:"code",
					displayField:"desc",
					width:250,
					height:200,
					allowBlank:true,
					legend:"Multiselect",
					tbar:[{text:"clear",handler:function(){formMultiselect.getForm().findField("multiselect").reset();}}]
				}],
				buttons:[{
					text:"Get Value",
					handler: function(){
						alert(formMultiselect.getForm().getValues(true));
					}
				},{
					text:"Set Value (2,3)",
					handler: function(){
						formMultiselect.getForm().findField("multiselect").setValue("2,3");
					}
				},{
					text:"Mark Invalid",
					handler: function(){
						formMultiselect.getForm().findField("multiselect").markInvalid("Invalid");
					}
				},{
					text:"Toggle Enabled",
					handler: function(){
						var m=formMultiselect.getForm().findField("multiselect");
						if (!m.disabled)m.disable();
						else m.enable();
					}
				},{
					text:"Reset",
					handler: function(){
						formMultiselect.getForm().findField("multiselect").reset();
					}
				}]
			});
			formMultiselect.render("form-ct-multiselect");
	*/
	/*
	 * Ext.ux.ItemSelector Example Code
	 */
	
					var formItemSelector = new Ext.form.FormPanel({ 
						labelWidth: 75,
						width:700,
						items:[{
							xtype:"itemselector",
							name:"itemselector",
							fieldLabel:"ItemSelector",
							dataFields: ["code", "desc"],
							fromData: [], /*[{"123":"One Hundred Twenty Three"},
								{"1": "One"}, {"2": "Two"}, {"3": "Three"}, {"4": "Four"}, {"5": "Five"},
								{"6": "Six"}, {"7": "Seven"}, {"8": "Eight"}, {"9": "Nine"}],*/
							toData:[], //[{"10": "Ten"}],
							msWidth:250,
							msHeight:200,
							valueField:"code",
							displayField:"desc",
							//imagePath:"ext-ux/multiselect",
							//switchToFrom:true,
							toLegend:"Selected",
							fromLegend:"Available",
							toTBar:[{
								text:"Clear",
								handler:function(){
									var i=formItemSelector.getForm().findField("itemselector");
									i.reset.call(i);
								}
							}]
						}],
						buttons:[{
							text:"Get Value",
							handler: function(){
								alert(formItemSelector.getForm().getValues(true));
							}
						},{	
							text:"Mark Invalid",
							handler: function(){
								formItemSelector.getForm().findField("itemselector").markInvalid("Invalid");
							}
						}]
					});

		Ext.Ajax.request ({
			url: _ACTION_OBTENER_DATOS_MULTI,
			method: 'GET',
			success: function (result, request) {
					alert(33);
					var jsonData = Ext.util.JSON.decode (result.responseText).arreglito;
					formItemSelector.fromData = new Array(jsonData);
					formItemSelector.render("form-ct-itemselector");
					/*console.log(jsonData);
					
					console.log(formItemSelector.fromData);
					console.log(formItemSelector);*/

			
			}
		});
	
});