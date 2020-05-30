package sistema.experto.arbol;

/**
 * @author dzp
 */
public class Nodo {
    public int llave;
    public int direccion;
    public Nodo der;
    public Nodo izq;
    
    public Nodo(int llave, int direccion){
        this.llave = llave;
        this.direccion = direccion;
        der = null;
        izq = null;
    }
}
