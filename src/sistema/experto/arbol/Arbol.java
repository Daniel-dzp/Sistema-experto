package sistema.experto.arbol;

import java.util.ArrayList;

/**
 * @author dzp
 */
public class Arbol {
    public Nodo raiz;
    ArrayList<Nodo> datos;
    
    public Arbol(){
        raiz = null;
        datos = new ArrayList();
    }
    
    public void agregar(int llave, int direccion)
    {
        Nodo r;
        Nodo ant;
        
        if(raiz == null)
            raiz = new Nodo(llave, direccion);
        else
            _agregar(llave, direccion, raiz);
    }
    
    private void _agregar(int llave, int direccion, Nodo r)
    {
        if(r.llave > llave)
            if(r.izq == null)
                r.izq = new Nodo(llave, direccion);
            else
                _agregar(llave, direccion, r.izq);
        else
            if(r.der == null)
                r.der = new Nodo(llave, direccion);
            else
                _agregar(llave, direccion, r.der);
    }
    
    public Nodo buscar(int llave)
    {
        if(raiz != null)
            return _buscar(llave, raiz);
        else
            return null;
    }
    
    private Nodo _buscar(int llave, Nodo r){
        if(r == null)
            return null;
        else
            if(r.llave == llave)
                return r;
            else
                if(llave > r.llave)
                    return _buscar(llave, r.der);
                else
                    return _buscar(llave, r.izq);
    }
    
    public void mostrar(Nodo r){
        if(r != null)
        {
            mostrar(r.izq);
            System.out.println(r.llave+" - "+r.direccion);
            mostrar(r.der);
        }
    }
    
    public ArrayList<Nodo> obtenerDatos()
    {
        datos = new ArrayList();
        _obtenerDatos(raiz);
        
        return datos;
    }
    
    private void _obtenerDatos(Nodo r)
    {
        if(r != null)
        {
            _obtenerDatos(r.izq);
            //System.out.println(r.llave+" - "+r.direccion);
            datos.add(r);
            _obtenerDatos(r.der);
        }
    }
}
