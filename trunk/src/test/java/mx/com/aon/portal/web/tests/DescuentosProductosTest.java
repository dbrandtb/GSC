package mx.com.aon.portal.web.tests;

import mx.com.aon.portal.model.DescuentoProductoVO;
import mx.com.aon.portal.model.DescuentoVO;
import mx.com.aon.portal.service.DescuentosManager;
import mx.com.aon.portal.service.MantenimientoPlanesManager;

public class DescuentosProductosTest extends AbstractTestCases {
    protected MantenimientoPlanesManager mantenimientoPlanesManager;
    protected DescuentosManager descuentosManager;


	public void testBuscarDescuentosProductos () throws Exception {
		try {
			DescuentoProductoVO descuentoProductoVO = new DescuentoProductoVO();
			DescuentoVO descuentoVO = new DescuentoVO();
			
			descuentoProductoVO = descuentosManager.getEncabezadoProducto("1");
			descuentoVO = descuentosManager.getEncabezadoVolumen("2");
            System.out.println("**********"+descuentoProductoVO.getDsDscto());
            System.out.println("**********"+descuentoProductoVO.getDsNombre());
            System.out.println("**********"+descuentoVO.getDsDscto());
            System.out.println("**********"+descuentoVO.getDsNombre());
            
            
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

    public void testGetDescuentoProducto () throws Exception {
        try {
        	
            // todo implemntar
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void testGuardarDescuentoProducto () throws Exception {
        try {
            // todo implemntar
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void testBorrarDescuentoProducto () throws Exception {
        try {
            // todo implemntar
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void testCopiarDescuentoProducto () throws Exception {
        try {
            // todo implemntar
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
