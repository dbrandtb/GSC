var vistaTipo=2;
var win_flotante;
function ventana(){ 
createGridFrmDoc();
if (win_flotante != null && win_flotante != undefined) {
    win_flotante.show();
    return;
}
win_flotante = new Ext.Window ({
	//title: '<span style="color:black;font-size:12px;">'+getLabelFromMap('96',helpMap,'Formato de Documentos')+'</span>',
	width: 510,
    modal: true,
	height: true,
	items: [
            incisosForm,
            gridComun
           ]
       
});
win_flotante.show();	
}