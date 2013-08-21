/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.aon.portal.web;

import java.util.List;

/**
 *
 * @author Jair
 */
public class Dinosaurio
    {
        private Long id;
        private String nombre;
        private int edad;
        private List<Carrito> carritos;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public int getEdad() {
            return edad;
        }

        public void setEdad(int edad) {
            this.edad = edad;
        }

        public List<Carrito> getCarritos() {
            return carritos;
        }

        public void setCarritos(List<Carrito> carritos) {
            this.carritos = carritos;
        }
    }