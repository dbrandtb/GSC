Ext.onReady(function(){

    Ext.QuickTips.init();
    Ext.QuickTips.enable();
    
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';

     <s:component template="despliegaPantallaGeneradaTest.vm" templateDir="templates" theme="components" >
		<s:param name="item" value="item" />
     </s:component>
 
 });
