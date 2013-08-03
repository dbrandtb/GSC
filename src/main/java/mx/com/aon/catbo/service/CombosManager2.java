package mx.com.aon.catbo.service;

import mx.com.aon.catbo.model.ElementoComboBoxVO;
import mx.com.aon.catbo.model.EncuestaVO;
import mx.com.aon.core.ApplicationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface con servicios para todos los combos del proyecto.
 *
 */
public interface CombosManager2 {

    public List comboObtieneFormatos () throws ApplicationException;
    public List comboObtieneTablas () throws ApplicationException;
    public List comboObtieneUsuarios (String pv_cdmatriz_i, String pv_cdmodulo_i,String pv_cdnivatn_i) throws ApplicationException;
    public List comboObtieneAlgoritmos () throws ApplicationException;
    public List comboObtieneTipoGuion () throws ApplicationException;
    public List comboObtieneTipoFax () throws ApplicationException;
    public List comboUsuarioResponsable (String pv_nmconfig_i) throws ApplicationException;
    public List comboCargaArchivos()throws ApplicationException;
    public List comboCargaArchivos2()throws ApplicationException;
    public List comboRamos2(String cdunieco, String cdelemento, String cdramo) throws ApplicationException ;
    public List obtieneTabla()throws ApplicationException;
	public List ComboObtienePolizaAseguradora2 (String p_cve_aseguradora, String  pv_cdelemento ) throws ApplicationException;

  
}
