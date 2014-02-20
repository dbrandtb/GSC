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