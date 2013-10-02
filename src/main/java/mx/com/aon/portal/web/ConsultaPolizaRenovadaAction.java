package mx.com.aon.portal.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mx.com.aon.portal.model.DatosMovPolizasVO;
import mx.com.aon.portal.model.DatosVarRolVO;
import mx.com.aon.portal.model.DatosVblesObjAsegurablesVO;
import mx.com.aon.portal.model.EncabezadoPolizaVO;
import mx.com.aon.portal.model.MovTValoPolVO;
import mx.com.aon.portal.model.NuevaCoberturaVO;
import mx.com.aon.portal.service.ConsultaPolizaRenovadaManager;
import mx.com.gseguros.exception.ApplicationException;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

/**Action que atiende las peticiones de la pantalla Datos Generales de Renovacion.
 * 
 *
 */
@SuppressWarnings("serial")
public class ConsultaPolizaRenovadaAction extends ActionSupport{

	private String cdUniEco; 
	private String cdRamo;
	private String estado;
	private String nmPoliza;
	private String cdAtribu;
	private String nmSituac;
	private String nmSuplem;
	private String cdRol;
	private String cdPerson;
	private String status;
	private String nmOrdDom;
	private String swReclam;
	private String accion;
	private String cdGarant;
	private String cdTipObj;
	private String nmObjeto;
	private String dsObjeto;
	private String ptObjeto;
	private String cdAgrupa;
	
	private String messageResult;
	
	private List<EncabezadoPolizaVO> mListPolizaEncabezado;
	private List<DatosMovPolizasVO> datosGralesList;
	private List<MovTValoPolVO> valoresPolizasList;
	private List<DatosVarRolVO> datosVblesFcionPolizaList;
	/**
	 * Lista de objetos de tipo DatosVblesObjAsegurablesVO que es usada para 
	 * guardar los datos variables de la función en la póliza en la pantalla Poliza de Objetos Asegurables
	 *  y los Datos variables de coberturas en la pantalla Datos de Coberturas.
	 */
	private List<DatosVblesObjAsegurablesVO> datosVblesFcionObjAsegList;
	private List<NuevaCoberturaVO> datosNuevaCoberturaList;
	
	private transient ConsultaPolizaRenovadaManager consultaPolizaRenovadaManager;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ConsultaPolizaRenovadaAction.class);
	
	private boolean success;
	
	/**
	 * Metodo del action que obtiene un registro de datos para la pantalla Datos Generales 
	 * de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String obtenPolizaEncabezado()throws ApplicationException{
		try{
			mListPolizaEncabezado = new ArrayList<EncabezadoPolizaVO>();
			EncabezadoPolizaVO vo = consultaPolizaRenovadaManager.getPolizaEncabezado(cdUniEco, cdRamo, estado, nmPoliza); 
			mListPolizaEncabezado.add(vo);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){
			success = false;
			addActionError(ae.getMessage());
			return SUCCESS;
		}catch(Exception e){
			success = false;
			addActionError(e.getMessage());
			return SUCCESS;
		}		
	}
	
	/**
	 * Metodo del action para guardar un movimiento de poliza para la pantalla 
	 * Datos Generales de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String guardarMovMPolizasClick()throws ApplicationException{		
		try{
			for(int i = 0; i < datosGralesList.size(); i++ ){
				DatosMovPolizasVO movPolizaVO =  datosGralesList.get(i);
				messageResult = consultaPolizaRenovadaManager.guardarDatosGenerales(movPolizaVO);
			}			
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	/**
	 * Metodo del action para guardar valores de poliza de los atributos variables 
	 * para la pantalla Datos Generales de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String guardarValoresPolizaClick()throws ApplicationException{		
		try{
			for(int i = 0; i < valoresPolizasList.size(); i++ ){
				MovTValoPolVO movTValoPolVO =  valoresPolizasList.get(i);
				messageResult = consultaPolizaRenovadaManager.guardarValoresPoliza(movTValoPolVO);
			}		
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	/**
	 * Metodo del action para eliminar un inciso del bloque Objeto Asegurable para la pantalla Objetos Asegurables
	 * de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String eliminarObjetoAsegurableClick()throws ApplicationException{		
		try{
			messageResult = consultaPolizaRenovadaManager.eliminarIncisoObjetoAsegurable(cdUniEco, cdRamo, estado, nmPoliza, nmSituac, nmSuplem);		
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	/**
	 * Metodo del action para eliminar una funcion del bloque Funcion en la poliza para la pantalla Objetos Asegurables
	 * de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String eliminarFuncionEnPolizaClick()throws ApplicationException{		
		try{
			messageResult = consultaPolizaRenovadaManager.eliminarFuncionObjetoAsegurable(cdUniEco, cdRamo, estado, nmPoliza, nmSituac, nmSuplem, cdRol, cdPerson, cdAtribu, status, nmOrdDom, swReclam, accion);		
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	
	
	
	/**
	 * Metodo del action para guardar los datos variables de la función en la póliza en la pantalla emergente
	 * Datos Variables de la Funcion en la Poliza de Objetos Asegurables de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String guardarDatosVblesDeFuncionEnPolizaClick()throws ApplicationException{
		try{
			for(int i = 0; i < datosVblesFcionPolizaList.size(); i++ ){
				DatosVarRolVO datosVarRolVO = datosVblesFcionPolizaList.get(i);
				messageResult = consultaPolizaRenovadaManager.guardarDatosVblesDeFuncionEnPoliza(datosVarRolVO);
			}		
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	/**
	 * Metodo del action para guardar una función en la póliza en la pantalla emergente Agregar Funcion en la Poliza
	 * de la pantalla Objetos Asegurables de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String guardarFuncionEnPolizaClick()throws ApplicationException{		
		try{
			messageResult = consultaPolizaRenovadaManager.guardaFuncionEnPoliza(cdUniEco, cdRamo, estado, nmPoliza, nmSituac, nmSuplem, cdRol,
					cdPerson, cdAtribu, status, nmOrdDom, swReclam, accion);				
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	
	
	/**
	 * Metodo del action para guardar los datos variables de la función en la póliza en la pantalla
	 *  Datos Variables del Objeto Asegurable de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String guardarDatosVblesDelObjetoAsegurableClick()throws ApplicationException{
		try{
			for(int i = 0; i < datosVblesFcionObjAsegList.size(); i++ ){
				DatosVblesObjAsegurablesVO datosVblesObjAsegurablesVO = datosVblesFcionObjAsegList.get(i);
				messageResult = consultaPolizaRenovadaManager.guardarDatosVblesObjetoAsegurable(datosVblesObjAsegurablesVO);
			}		
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	/**
	 * Metodo del action para eliminar un inciso de cobertura seleccionado en el grid Renovacion
	 *  para la pantalla Cobertura de Poliza a Renovar de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String eliminarIncisoDeCoberturaClick()throws ApplicationException{		
		try{
			messageResult = consultaPolizaRenovadaManager.eliminarIncisoCobertura(cdUniEco, cdRamo, nmPoliza, nmSituac, cdGarant);		
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	/**
	 * Metodo del action para guardar los datos de una nueva cobertura agregada en la pantalla emergente
	 * Agregar Cobertura de Coberturas de polizas a renovar de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String guardarNuevaCoberturaClick()throws ApplicationException{
		try{
			for(int i = 0; i < datosNuevaCoberturaList.size(); i++ ){
				NuevaCoberturaVO nuevaCoberturaVO = datosNuevaCoberturaList.get(i);
				messageResult = consultaPolizaRenovadaManager.guardarDatosCobertura(nuevaCoberturaVO);
			}		
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	
	/**
	 * Metodo del action para guardar los datos de una nueva cobertura agregada en la pantalla emergente
	 * Agregar Cobertura de Coberturas de polizas a renovar de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String guardarDatosVblesCoberturaClick()throws ApplicationException{
		try{
			for(int i = 0; i < datosNuevaCoberturaList.size(); i++ ){
				DatosVblesObjAsegurablesVO datosVblesObjAsegurablesVO = datosVblesFcionObjAsegList.get(i);
				messageResult = consultaPolizaRenovadaManager.guardarDatosDeCobertura(datosVblesObjAsegurablesVO);
			}		
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	/**
	 * Metodo del action para eliminar un accesorio seleccionado en el grid Equipo Especial
	 *  de la pantalla Accesorios de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String eliminarAccesorioClick()throws ApplicationException{		
		try{
			messageResult = consultaPolizaRenovadaManager.eliminarAccesorioEquipoEspecial(cdUniEco, cdRamo, estado, nmPoliza, nmSituac, cdTipObj, nmSuplem, status, nmObjeto, dsObjeto, ptObjeto, cdAgrupa, accion);		
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	/**
	 * Metodo del action para inserta o actualiza un accesorio en la pantalla Accesorios 
	 * de Consulta de Poliza Renovada.
	 * 
	 * @return success
	 * 
	 * @throws Exception
	 */
	public String incluirAccesorioClick()throws ApplicationException{
		try{
			messageResult = consultaPolizaRenovadaManager.incluirAccesorio(cdUniEco, cdRamo, estado, nmPoliza, nmSituac, cdTipObj, nmSuplem, status, nmObjeto, dsObjeto, ptObjeto, cdAgrupa, accion);
					
			addActionMessage(messageResult);
			success = true;
			return SUCCESS;
		}catch(ApplicationException ae){			
			addActionError(ae.getMessage());
			success = false;
			return SUCCESS;
		}catch(Exception e){			
			addActionError(e.getMessage());
			success = false;
			return SUCCESS;
		}
	}
	
	public String getCdUniEco() {
		return cdUniEco;
	}
	public void setCdUniEco(String cdUniEco) {
		this.cdUniEco = cdUniEco;
	}
	public String getCdRamo() {
		return cdRamo;
	}
	public void setCdRamo(String cdRamo) {
		this.cdRamo = cdRamo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNmPoliza() {
		return nmPoliza;
	}
	public void setNmPoliza(String nmPoliza) {
		this.nmPoliza = nmPoliza;
	}

	public void setConsultaPolizaRenovadaManager(
			ConsultaPolizaRenovadaManager consultaPolizaRenovadaManager) {
		this.consultaPolizaRenovadaManager = consultaPolizaRenovadaManager;
	}
	
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		
	}

	public String getCdAtribu() {
		return cdAtribu;
	}

	public void setCdAtribu(String cdAtribu) {
		this.cdAtribu = cdAtribu;
	}

	public String getMessageResult() {
		return messageResult;
	}

	public void setMessageResult(String messageResult) {
		this.messageResult = messageResult;
	}

	public String getNmSituac() {
		return nmSituac;
	}

	public void setNmSituac(String nmSituac) {
		this.nmSituac = nmSituac;
	}

	public String getNmSuplem() {
		return nmSuplem;
	}

	public void setNmSuplem(String nmSuplem) {
		this.nmSuplem = nmSuplem;
	}

	public String getCdRol() {
		return cdRol;
	}

	public void setCdRol(String cdRol) {
		this.cdRol = cdRol;
	}

	public String getCdPerson() {
		return cdPerson;
	}

	public void setCdPerson(String cdPerson) {
		this.cdPerson = cdPerson;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNmOrdDom() {
		return nmOrdDom;
	}

	public void setNmOrdDom(String nmOrdDom) {
		this.nmOrdDom = nmOrdDom;
	}

	public String getSwReclam() {
		return swReclam;
	}

	public void setSwReclam(String swReclam) {
		this.swReclam = swReclam;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public List<DatosMovPolizasVO> getDatosGralesList() {
		return datosGralesList;
	}

	public void setDatosGralesList(List<DatosMovPolizasVO> datosGralesList) {
		this.datosGralesList = datosGralesList;
	}

	public List<MovTValoPolVO> getValoresPolizasList() {
		return valoresPolizasList;
	}

	public void setValoresPolizasList(List<MovTValoPolVO> valoresPolizasList) {
		this.valoresPolizasList = valoresPolizasList;
	}

	public List<DatosVarRolVO> getDatosVblesFcionPolizaList() {
		return datosVblesFcionPolizaList;
	}

	public void setDatosVblesFcionPolizaList(
			List<DatosVarRolVO> datosVblesFcionPolizaList) {
		this.datosVblesFcionPolizaList = datosVblesFcionPolizaList;
	}


	public String getCdGarant() {
		return cdGarant;
	}


	public void setCdGarant(String cdGarant) {
		this.cdGarant = cdGarant;
	}


	public List<DatosVblesObjAsegurablesVO> getDatosVblesFcionObjAsegList() {
		return datosVblesFcionObjAsegList;
	}


	public void setDatosVblesFcionObjAsegList(
			List<DatosVblesObjAsegurablesVO> datosVblesFcionObjAsegList) {
		this.datosVblesFcionObjAsegList = datosVblesFcionObjAsegList;
	}


	public List<NuevaCoberturaVO> getDatosNuevaCoberturaList() {
		return datosNuevaCoberturaList;
	}


	public void setDatosNuevaCoberturaList(
			List<NuevaCoberturaVO> datosNuevaCoberturaList) {
		this.datosNuevaCoberturaList = datosNuevaCoberturaList;
	}

	public String getCdTipObj() {
		return cdTipObj;
	}

	public void setCdTipObj(String cdTipObj) {
		this.cdTipObj = cdTipObj;
	}

	public String getNmObjeto() {
		return nmObjeto;
	}

	public void setNmObjeto(String nmObjeto) {
		this.nmObjeto = nmObjeto;
	}

	public String getDsObjeto() {
		return dsObjeto;
	}

	public void setDsObjeto(String dsObjeto) {
		this.dsObjeto = dsObjeto;
	}

	public String getPtObjeto() {
		return ptObjeto;
	}

	public void setPtObjeto(String ptObjeto) {
		this.ptObjeto = ptObjeto;
	}

	public String getCdAgrupa() {
		return cdAgrupa;
	}

	public void setCdAgrupa(String cdAgrupa) {
		this.cdAgrupa = cdAgrupa;
	}

	public List<EncabezadoPolizaVO> getMListPolizaEncabezado() {
		return mListPolizaEncabezado;
	}

	public void setMListPolizaEncabezado(
			List<EncabezadoPolizaVO> listPolizaEncabezado) {
		mListPolizaEncabezado = listPolizaEncabezado;
	}

	
}

