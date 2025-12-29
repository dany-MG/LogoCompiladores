/*
DATOS
*Manrique Godinez Daniel Alejandro
*Aguilar Bautista César Fernando
*Proyecto compiladores
*Opcion LOGO
*/
public class SymbolData {

    private String nombre;
    private Object objeto;

    public SymbolData(String nombre, Object objeto){
        this.nombre = nombre;
        this.objeto = objeto;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }
        
}
