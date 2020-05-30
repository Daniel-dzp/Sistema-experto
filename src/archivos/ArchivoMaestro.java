package archivos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import clausula.Clausula;

public class ArchivoMaestro {
    RandomAccessFile archivo;
    StringBuffer buffer = null;
    String nombre;
    
    public ArchivoMaestro(String nombre) throws FileNotFoundException{
        this.nombre = nombre;
        archivo = new RandomAccessFile(nombre,"rw");
    }
        
    public int agregar(Clausula clausula, int noClausula) throws IOException{
        // longitud de la estructura 1,084 bytes
        int posicion;
        
        
        if(noClausula == 0)
            archivo.seek(archivo.length());
        else
            archivo.seek((noClausula-1)*1084);
        
        posicion = (int) archivo.length()/1084 + 1;
        
        archivo.writeInt(clausula.clave);
        
        if(clausula.consecuente.size()>0)
            buffer=new StringBuffer(clausula.consecuente.get(0));
        else
            buffer=new StringBuffer("");
        buffer.setLength(15);
        archivo.writeChars(buffer.toString());
        for(int i=1;i<=5;i++)
        {
            if(clausula.consecuente.size()>i)
                buffer=new StringBuffer(clausula.consecuente.get(i));
            else
                buffer=new StringBuffer("");
            buffer.setLength(15);
            archivo.writeChars(buffer.toString());
        }
        
        for(int i=0;i<5;i++)
        {
            if(clausula.antecedente.size()>i)
            {
                buffer=new StringBuffer(clausula.antecedente.get(i).get(0));
                buffer.setLength(15);
                archivo.writeChars(buffer.toString());
                
                for(int j=1;j<=5;j++)
                {
                    if(clausula.antecedente.get(i).size()>j)
                        buffer=new StringBuffer(clausula.antecedente.get(i).get(j));
                    else
                        buffer=new StringBuffer("");
                    buffer.setLength(15);
                    archivo.writeChars(buffer.toString());
                }
            }
            else
            {
                for(int j=0;j<=5;j++)
                {
                    buffer=new StringBuffer("");
                    buffer.setLength(15);
                    archivo.writeChars(buffer.toString());
                }
            }
        }
        
        return posicion;
    }
    
    public Clausula obtener(int posicion) throws IOException {
        Clausula clausula = null;
        char cadena [] = new char[15];
        String nombre;
        ArrayList<String> antecedente;
        
        clausula = new Clausula();
        
        if(archivo.length() > ((posicion-1)*1084))
        {
            archivo.seek((posicion-1)*1084);
            
            clausula.clave = archivo.readInt();
            
            for(int i=0;i<6;i++)
            {
                for (int c = 0; c<cadena.length;c++)
                    cadena[c] = archivo.readChar();
                //nombre = new String (cadena).replace('\0',' ');
                nombre = String.valueOf(cadena).trim();
                if(!"".equals(nombre))
                    clausula.consecuente.add(nombre);
            }
            
            for(int i=0;i<5;i++)
            {
                antecedente = new ArrayList();
                for(int j=0;j<6;j++)
                {
                    for (int c = 0; c<cadena.length;c++)
                        cadena[c] = archivo.readChar();
                    //nombre = new String (cadena).replace('\0',' ');
                    nombre = String.valueOf(cadena).trim();
                    if(!"".equals(nombre))
                        antecedente.add(nombre);
                }
                if(antecedente.size()>0)
                    clausula.antecedente.add(antecedente);
            }
            
            return clausula;
        }
        else
            return null;
        
    }
    
    public ArrayList<Clausula> clausulas() throws IOException{
        ArrayList<Clausula> clausulas = new ArrayList();
        Clausula clausula;
        int i=1;
        
        do{
            clausula = obtener(i);
            if(clausula != null)
                clausulas.add(clausula);
            i++;
        }while(clausula != null);
        
        return clausulas;
    }
    
    public void cerrar() throws IOException
    {
        archivo.close();
    }
    
}
