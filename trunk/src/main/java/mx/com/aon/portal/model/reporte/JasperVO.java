package mx.com.aon.portal.model.reporte;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Oscar Parales
 * Date: 30-jul-2008
 * Time: 11:06:03
 * To change this template use File | Settings | File Templates.
 */
public class JasperVO implements Serializable {

    private static final long serialVersionUID = 3781183579335510222L;

    //****Parametros de Entrada****
    private int otValor01;   //cdUnieco ojo
    private int otValor02;   //cdRamo
    private int otValor03;
    private Date otValor04;
    private Date otValor05;
    //****Parametros de Salida****
    private String dsReporte;
    private int nmReporte;
    private int cdPlanti;
    private int cdElemento;
    private int cdUnieco;
    private int cdRamo;
    private String dsUnieco;      //aseguradora
    private String dsRamo;        //producto
    private String otValor25;     //causa
    private String otValor08;       //Mes  ojo
    private int cantidad;           //contador de otvalor01


    public String getDsReporte() {
        return dsReporte;
    }

    public void setDsReporte(String dsReporte) {
        this.dsReporte = dsReporte;
    }

    public int getNmReporte() {
        return nmReporte;
    }

    public void setNmReporte(int nmReporte) {
        this.nmReporte = nmReporte;
    }

    public int getCdPlanti() {
        return cdPlanti;
    }

    public void setCdPlanti(int cdPlanti) {
        this.cdPlanti = cdPlanti;
    }

    public int getCdElemento() {
        return cdElemento;
    }

    public void setCdElemento(int cdElemento) {
        this.cdElemento = cdElemento;
    }

    public int getCdUnieco() {
        return cdUnieco;
    }

    public void setCdUnieco(int cdUnieco) {
        this.cdUnieco = cdUnieco;
    }

    public int getCdRamo() {
        return cdRamo;
    }

    public void setCdRamo(int cdRamo) {
        this.cdRamo = cdRamo;
    }

    public String getDsUnieco() {
        return dsUnieco;
    }

    public void setDsUnieco(String dsUnieco) {
        this.dsUnieco = dsUnieco;
    }

    public String getDsRamo() {
        return dsRamo;
    }

    public void setDsRamo(String dsRamo) {
        this.dsRamo = dsRamo;
    }

    public String getOtValor25() {
        return otValor25;
    }

    public void setOtValor25(String otValor25) {
        this.otValor25 = otValor25;
    }

    public String getOtValor08() {
        return otValor08;
    }

    public void setOtValor08(String otValor08) {
        this.otValor08 = otValor08;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getOtValor01() {
        return otValor01;
    }

    public void setOtValor01(int otValor01) {
        this.otValor01 = otValor01;
    }

    public int getOtValor02() {
        return otValor02;
    }

    public void setOtValor02(int otValor02) {
        this.otValor02 = otValor02;
    }

    public int getOtValor03() {
        return otValor03;
    }

    public void setOtValor03(int otValor03) {
        this.otValor03 = otValor03;
    }

    public Date getOtValor04() {
        return otValor04;
    }

    public void setOtValor04(Date otValor04) {
        this.otValor04 = otValor04;
    }

    public Date getOtValor05() {
        return otValor05;
    }

    public void setOtValor05(Date otValor05) {
        this.otValor05 = otValor05;
    }

}
