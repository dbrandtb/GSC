package mx.com.aon.portal.web.tests;

import java.util.ArrayList;
import java.util.List;

//import sun.text.CompactShortArray.Iterator;

import mx.com.aon.portal.model.DescuentoDetVolumenVO;
import mx.com.aon.portal.model.DescuentoProductoEncDetVO;
import mx.com.aon.portal.model.DescuentoVO;
import mx.com.aon.portal.model.DescuentoVolumenVO;
import mx.com.aon.portal.model.DetalleProductoVO;
import mx.com.aon.portal.service.CombosManager;
import mx.com.aon.portal.service.DescuentosManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

public class DescuentosTest extends AbstractTestCases{
	
	protected DescuentosManager descuentosManager;
	
	//*****************************************************************************
    /*
	@SuppressWarnings("unchecked")
	public void testObtieneDescuentos() throws Exception {
    	try {
  //PagedList buscarDescuentos(int start, int limit, String dsDescuento, String otValor, String dsCliente)
    		PagedList pagedList  =  descuentosManager.buscarDescuentos(0, 10, "", "", "");
            List listaResultado = pagedList.getItemsRangeList();
            for (int i = 0; i < listaResultado.size(); i++) {
                DescuentoVO d =  (DescuentoVO)listaResultado.get(i);
                System.out.println("Código de descuento "+ d.getCdDscto());
                System.out.println("Descripcion de descuento "+ d.getDsDscto());
                System.out.println("Nombre de cliente "+ d.getDsNombre());
                System.out.println("Código de estado "+ d.getCdEstado());
                System.out.println("Porcentaje descuento "+ d.getPrDescto());
                System.out.println("Monto de descuento "+ d.getMnDescto());
                System.out.println("FEREGIST "+ d.getFeRegist());
                System.out.println("FEINACTI "+ d.getFeInActi());
            }
    		logger.info("Total Items: " + pagedList.getTotalItems());
    	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
  //*****************************************************************************
  
    /*
    public void testBorraEstructura() throws Exception {
    	try {
			descuentosManager.borrarDescuento("1");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/
	
	 /*
    @SuppressWarnings("unchecked")
	public void testGuardaVolumen() throws Exception {
    	try {
    		DescuentoVolumenVO des = new DescuentoVolumenVO(); 
    		
    		des.setCdDscto("35");
    		des.setCdEstado("1");
    		des.setCdPerson("5051");
    		des.setCdTipo("2");
    		des.setDsNombre("PRUEBA3V MOD4");
    		des.setFgAcumul("1");
    		
    		DescuentoDetVolumenVO d = new DescuentoDetVolumenVO();
    		
    		d.setCdDscto("35");
    		d.setCdDsctod("");
    		d.setMnDescto("");
    		d.setMnVolFin("3200");
    		d.setMnVolIni("1600");
    		d.setPrDescto("15");
    		
    		List<DescuentoDetVolumenVO> lista = new ArrayList();
    		lista.add(d);
    		des.setDetallesVolumen(lista);
			descuentosManager.guardarVolumen(des);
    		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	
	/*
	public void testGuardaProducto() throws Exception {
    	try {
    		DescuentoVO des = new DescuentoVO(); 
    		
    		des.setCdDscto("");
    		des.setCdEstado("1");
    		des.setCdPerson("5051");
    		des.setCdElemento("5051");
    		des.setCdTipo("1");
    		des.setDsDscto("WHY???");
    		des.setFgAcumul("1");
    		des.setMnDescto("99");
    		des.setPrDescto("");
    		*/
    		/*List<DetalleProductoVO> lista = new ArrayList();
    		DetalleProductoVO d = new DetalleProductoVO();
    		d.setCdDscto("3");
    		d.setCdDsctod("4");
    		d.setCdUniEco("930");
    		d.setCdRamo("777");
    		d.setCdTipSit("VA");
    		d.setCdAtribu("");
    		d.setOtValor("");
    		lista.add(d);
    		des.setDetalleProducto(lista);*/
		/*	descuentosManager.guardarProducto(des);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/
    
	/*
    public void testBorraDetalleProducto() throws Exception {
    	try {
			descuentosManager.borrarDetalleProducto("2", "1");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	/*
    public void testBorraDetalleVolumen() throws Exception {
    	try {
			descuentosManager.borrarDetalleVolumen("3", "4");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	*/
    /*
    public void testCopiarDescuento() throws Exception {
    	try {
    		DescuentoVO descuentoVO = new DescuentoVO();
    		descuentoVO.setCdDscto("1");
			descuentosManager.copiarDescuento(descuentoVO);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	/*
    public void testObtenerEncabezadoProducto() throws Exception {
    	try {
    		List<DetalleProductoVO> ldv = descuentosManager.getDetalleProducto("3");
    		DetalleProductoVO dp = new DetalleProductoVO();
    		for(int i=0;i < ldv.size(); i++){
    			dp = ldv.get(i);
    			System.out.println("*****************dsUniEco: "+dp.getDsUnieco());
        		System.out.println("*****************dsRamo: "+dp.getDsRamo());
    		}
    		System.out.println("Pase el test case de DescuentoDetVolumenVO");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	/*
	public void testObtenerDetalleVolumen() throws Exception {
    	try {
    		List<DescuentoDetVolumenVO> ldv = descuentosManager.getDetalleVolumen("4");
    		DescuentoDetVolumenVO dv = new DescuentoDetVolumenVO();
    		for(int i=0;i < ldv.size(); i++){
    			dv = ldv.get(i);
    			System.out.println("*****************mnVolFin: "+dv.getMnVolFin());
        		System.out.println("*****************mnVolIni: "+dv.getMnVolIni());
    		}
    		System.out.println("Pase el test case de DescuentoDetVolumenVO");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	/*
	public void testObtenerEncabezadoVolumen() throws Exception {
    	try {
    		List<DescuentoVO> ldv = (List<DescuentoVO>) descuentosManager.getEncabezadoVolumen("2");
    		DescuentoVO descuentoVO = new DescuentoVO();
    		for(int i=0;i < ldv.size(); i++){
    			descuentoVO = ldv.get(i);
    			System.out.println("*****************cdDscto: "+descuentoVO.getCdDscto());
    			System.out.println("*****************dsNombre: "+descuentoVO.getDsDscto());
    			System.out.println("*****************cdTipo: "+descuentoVO.getCdTipo());
    			System.out.println("*****************dsTipo: "+descuentoVO.getDsTipo());
    			System.out.println("*****************cdElemento: "+descuentoVO.getCdElemento());
    			System.out.println("*****************dsElemento: "+descuentoVO.getDsNombre());
    			System.out.println("*****************dsElemento: "+descuentoVO.getDsNombre());
    			System.out.println("*****************fgAcumula: "+descuentoVO.getFgAcumul());
    			System.out.println("*****************fgAcumula: "+descuentoVO.getFgAcumul());
    			System.out.println("*****************dsAcumula: "+descuentoVO.getDsAcumul());
    			System.out.println("*****************cdEstado: "+descuentoVO.getCdEstado());
    			System.out.println("*****************dsEstado: "+descuentoVO.getDsEstado());
    			
    		}
    		System.out.println("Pase el test case de DescuentoDetVolumenVO");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }*/
	
	
}
