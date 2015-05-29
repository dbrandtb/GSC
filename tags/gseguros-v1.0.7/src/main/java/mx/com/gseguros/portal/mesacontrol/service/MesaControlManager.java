package mx.com.gseguros.portal.mesacontrol.service;

public interface MesaControlManager
{
    public String cargarCdagentePorCdusuari(String cdusuari)throws Exception;
    public void guardarRegistroContrarecibo(String ntramite,String cdusuari)throws Exception;
    public void actualizarNombreDocumento(String ntramite,String cddocume,String nuevo)throws Exception;
    public void borrarDocumento(String ntramite,String cddocume)throws Exception;
}