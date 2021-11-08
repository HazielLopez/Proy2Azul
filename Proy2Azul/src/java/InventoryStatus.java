/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public enum InventoryStatus {
    INSTOCK("Disponible", "DISPONIBLE"),
    OUTOFSTOCK("No Disponible", "NO DISPONIBLE"),
    LOWSTOCK("Baja Disponibilidad", "BAJA DISPONIBILIDAD");

    private String text;
    private String name;

    public String getName() {
        return name;
    }

    InventoryStatus(String text, String name) {
        this.text = text;
        this.name = name;
    }

    public String getText() {
        return text;
    }

}
