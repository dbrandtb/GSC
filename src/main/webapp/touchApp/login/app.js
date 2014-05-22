Ext.setup(
{
    onReady : function()
	{
		debug('>onReady');
		Ext.Viewport.add(Ext.create('Ext.NavigationView',
		{
		    defaultBackButtonText : 'Atr&aacute;s'
		    ,items                :
		    [
		        Ext.create('FormularioInicioSesion',
		        {
		            textos    :
		            {
		                titulo          : _t01
		                ,tituloFieldSet : _t02
		                ,usuario        : _t03
		                ,contrasena     : _t04
		                ,boton          : _t05
		                ,instrucciones  : _t06
		                ,usuarioValida  : _t07
		                ,passwordValida : _t08
		                ,msgError       : _t09
		                ,msgEspera      : _t10
		            }
		            ,url      : _urlLogin
		            ,callback : function()
		            {
		                self.location.href = _urlRedirect;
		            }
		        })
		    ]
		}));
		debug('<onReady');
	}
});