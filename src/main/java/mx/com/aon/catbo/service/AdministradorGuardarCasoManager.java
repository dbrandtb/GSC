package mx.com.aon.catbo.service;


import mx.com.aon.catbo.model.CasoVO;
import mx.com.aon.catbo.model.FormatoOrdenVO;
import mx.com.aon.catbo.model.ResultadoGeneraCasoVO;

public interface AdministradorGuardarCasoManager {

	public String guardarNuevoCaso(CasoVO casoVO,FormatoOrdenVO formatoOrdenVO)throws Exception;
}
