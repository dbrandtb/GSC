package mx.com.aon.portal.web;

import java.util.List;

import mx.com.aon.catbo.model.AtributosVblesVO;
import mx.com.aon.portal.model.AtributosVariablesInstPagoVO;
import mx.com.aon.portal.service.InstrumentoPagoManager;

import org.apache.log4j.Logger;

public class ListaInstrumentoPagoAction extends AbstractListAction
{
	private static final long serialVersionUID = -2161929117098084652L;
	private static Logger logger = Logger.getLogger(ListaInstrumentoPagoAction.class);
	
	private transient InstrumentoPagoManager instrumentoPagoManager;
	private List<AtributosVariablesInstPagoVO> mListaValoresIntsPago;
	private String ottipotb;
	
	
	

	public String getOttipotb() {
		return ottipotb;
	}

	public void setOttipotb(String ottipotb) {
		this.ottipotb = ottipotb;
	}

	public void setInstrumentoPagoManager(
			InstrumentoPagoManager instrumentoPagoManager) {
		this.instrumentoPagoManager = instrumentoPagoManager;
	}

	public List<AtributosVariablesInstPagoVO> getMListaValoresIntsPago() {
		return mListaValoresIntsPago;
	}

	public void setMListaValoresIntsPago(
			List<AtributosVariablesInstPagoVO> listaValoresIntsPago) {
		mListaValoresIntsPago = listaValoresIntsPago;
	}
	
	
	
	
}
