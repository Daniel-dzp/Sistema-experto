package archivos;

import java.io.File;

/**
 * @author dzp
 */
public class Archivo {
    public static final String Maestro="maestro.bin";
    public static final String MaestroTemp="maestroTemp.bin";
    public static final String Indice="indice.bin";
    public static final String IndiceTemp="indiceTemp.bin";
    
    static public void renombrar(String archivo, String nuevoNombre){
        File fichero = new File(archivo);
        
        fichero.renameTo(new File(nuevoNombre));
    }
    
    static public void eliminar(String archivo){
        File fichero = new File(archivo);
        
        fichero.delete();
    }
}
