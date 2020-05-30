package archivos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import sistema.experto.arbol.Arbol;

public class ArchivoIndice {
    RandomAccessFile archivo;
    StringBuffer buffer = null;
    int noBloque = 0;
    String nombre;
    
    public ArchivoIndice(String nombre) throws FileNotFoundException{
        this.nombre = nombre;
        archivo = new RandomAccessFile(nombre,"rw");
    }
    
    public void agregar(int llave, int posicion) throws IOException{
        archivo.seek(archivo.length());
        archivo.writeInt(llave);
        
        archivo.writeInt(posicion);
        
        noBloque++;
    }
    
    public int buscar(int llaveBuscar) throws IOException {
        int llave = -1;
        int posicion = -1;
        long longitudArchivo;
        
        archivo.seek(0);
        longitudArchivo = archivo.length();
        
        for(int i=0;(i<longitudArchivo/8) && (llave != llaveBuscar);i++)
        {
            llave = archivo.readInt();
            posicion = archivo.readInt();
        }
        
        if(llave == llaveBuscar)
            return posicion;
        else
            return -1;
    }

    public Arbol obtenerIndice() throws IOException {
        int llave;
        int posicion;
        long longitudArchivo;
        Arbol arbol = new Arbol();
        
        archivo.seek(0);
        longitudArchivo = archivo.length();
        
        for(int i=0;i<longitudArchivo/8;i++)
        {
            llave = archivo.readInt();
            posicion = archivo.readInt();
            arbol.agregar(llave, posicion);
        }
        
        return arbol;
    }
    
    public int eliminar(int llaveBuscar) throws IOException{
        int llave = '\0';
        int posicion = -1;
        long longitudArchivo;
        
        archivo.seek(0);
        longitudArchivo = archivo.length();
        
        for(int i=0;(i<longitudArchivo/8) && (llave != llaveBuscar);i++)
        {
            llave = archivo.readInt();
            posicion = archivo.readInt();
        }
        
        if(llave == llaveBuscar)
            return posicion;
        else
            return -1;
    }
    
    public void cerrar() throws IOException
    {
        archivo.close();
    }
}
