if(extjs_custom_override_mayusculas==true)
{
	debug('extjs_custom_override_mayusculas ENCENDIDO');
	//Se sobreescribe el componente TextField para que solo acepte may�sculas
    Ext.override(Ext.form.TextField,
    {
    	initComponent:function()
    	{
    		if(this.xtype=='textfield')
    		{
    			this.on("change",
				function()
				{
    				try
    				{
	    				if('string' == typeof this.getValue())
	    				{
	    					debug('mayus de '+this.getValue());
	    					this.setValue(this.getValue().toUpperCase());
	    				}
    				}
    				catch(e){}
				},this);
    		}
    		return this.callParent();
    	}
	});
}
else
{
	debug('extjs_custom_override_mayusculas APAGADO');
}

Ext.define('Ext.grid.override.RowEditor', {
    override: 'Ext.grid.RowEditor',
    
    initComponent: function() {
        var me = this;
            
        me.callParent(arguments);
        
        me.getForm().on('validitychange', me.onValidityChange, me);
    },    
    
    onValidityChange: function() {
        var me = this,
            form = me.getForm(),
            valid = form.isValid();
        
        if (me.errorSummary && me.isVisible()) {
            me[valid ? 'hideToolTip' : 'showToolTip']();
        }
        me.updateButton(valid);
        me.isValid = valid;
    }
    
});