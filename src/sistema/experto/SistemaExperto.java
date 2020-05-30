package sistema.experto;

import archivos.Archivo;
import archivos.ArchivoIndice;
import archivos.ArchivoMaestro;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import sistema.experto.arbol.Arbol;
import sistema.experto.arbol.Nodo;
import clausula.Clausula;
import clausula.ParsearClausula;
/**
 * @author dzp
 */
public class SistemaExperto {
    Scanner teclado;
    ArchivoMaestro maestro, maestroTemp;
    ArchivoIndice indice, indiceTemp;
    Arbol arbol;
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        new SistemaExperto();
        //SistemaExperto.entrada();
        //SistemaExperto.infe();
    }
    
    static public void infe() throws FileNotFoundException, IOException{
        String salir, entrada;
        ArrayList<Clausula> clausulasEntrada = new ArrayList();
        ArrayList<Clausula> clausulasConocimiento;
        Scanner teclado = new Scanner(System.in);
        ParsearClausula pc = new ParsearClausula();
        MotorInferencia mi;
        ArchivoMaestro am = new ArchivoMaestro(Archivo.Maestro);
        
        
        do{
            System.out.print("Dame clausula: ");
            entrada = teclado.nextLine();
            
            if(pc.aClausula(entrada))
                System.out.println("Error");
            else
            {
                System.out.println(pc.clausula);
                clausulasEntrada.add(pc.clausula);
            }
            
            
            System.out.print("Quieres seguir(no): ");
            salir = teclado.nextLine();
            
        }while(!"no".equals(salir));
        
        
        clausulasConocimiento = am.clausulas();
        am.cerrar();
        
        System.out.println("No entrada "+clausulasEntrada.size());
        System.out.println("No conocimiento "+clausulasConocimiento.size());
        
        mi = new MotorInferencia(clausulasConocimiento);
        mi.inferir(clausulasEntrada);
        
    }
    
    static public void entrada(){
        String entrada;
        Scanner teclado = new Scanner(System.in);
        ParsearClausula pc = new ParsearClausula();
        
        System.out.print("Dame clausula: ");
        entrada = teclado.nextLine();
        if(pc.aClausula(entrada))
        {
            System.out.println("Error");
        }
        else
        {
            System.out.println(pc.clausula);
        }
        
    }
    
    public SistemaExperto() throws FileNotFoundException, IOException{
        teclado = new Scanner(System.in);
        maestro = new ArchivoMaestro(Archivo.Maestro);
        indice = new ArchivoIndice(Archivo.Indice);
        
        arbol = indice.obtenerIndice();
        
        String entrada;
        
        do{
            System.out.println("\n---------- MENU -----------\n");
            System.out.print("1. Agregar Clausula\n");
            System.out.print("2. Mostrar Clausula(Secuencial indexado)\n");
            System.out.print("3. Mostrar Clausula(Secuencial)\n");
            System.out.print("4. Mostrar Todas Las Clausulas\n");
            System.out.print("5. Eliminar Clausula\n");
            System.out.print("6. Actualizar Clausula\n");
            System.out.print("7. Inferir\n");
            System.out.print("8. Salir\n");
            System.out.print("OPCION: ");
            
            entrada = teclado.nextLine();
            
            switch(entrada){
                case "1": agregarClausula(); break;
                case "2": mostrarSecuencialIndexado(); break;
                case "3": mostrarSecuencial(); break;
                case "4": mostrarTodas(); break;
                case "5": eliminar(); break;
                case "6": actualizar(); break;
                case "7": inferir(); break;
                case "8": System.out.println("  Saliendo..."); break;
            }
            
        }while(!entrada.equals("8"));
        
        indice.cerrar();
        maestro.cerrar();
    }

    private void agregarClausula() throws IOException {
        /*String datos[];
        Clausula estructura;
        int posicion;
        int noPreAnt=0;
        String argumentos;
        int llave;
        String nombre;
        ArrayList<String> antecedente;
        
        System.out.println("--- Agregar Clausula ---");
        System.out.print("Dame llave: ");
        llave = Integer.parseInt(teclado.nextLine());
        System.out.print("Dame número preposiciones del antecedente(>=0): ");
        noPreAnt = Integer.parseInt(teclado.nextLine());
        
        estructura = new Clausula();
        estructura.clave = llave;
        for(int i=0;i<noPreAnt;i++)
        {
            antecedente = new ArrayList();
            System.out.print("Dame nombre de la preposicion "+(i+1)+": ");
            nombre = teclado.nextLine();
            antecedente.add(nombre);
            System.out.print(" Dame argumentos: ");
            argumentos = teclado.nextLine();
            
            datos = argumentos.split(" ");
            for(int j=0;j<datos.length;j++)
            {
                antecedente.add(datos[j]);
            }
            
            estructura.antecedente.add(antecedente);
        }
        
        System.out.print("Dame nombre del consecuente: ");
        nombre = teclado.nextLine();
        estructura.consecuente.add(nombre);
        System.out.print(" Dame argumentos: ");
        argumentos = teclado.nextLine();
        datos = argumentos.split(" ");
        
        for(int j=0;j<datos.length;j++)
        {
            estructura.consecuente.add(datos[j]);
        }
        
        System.out.println(estructura.toString());

        posicion = maestro.agregar(estructura,0);
        indice.agregar(llave, posicion);

        arbol = indice.obtenerIndice();
        */
        
        Clausula clausula;
        int llave, posicion;
        ParsearClausula pc = new ParsearClausula();
        String cadena;
        
        
        System.out.println("--- Agregar Clausula ---");
        System.out.print("Dame llave: ");
        llave = Integer.parseInt(teclado.nextLine());
        System.out.print("Dame clausula: ");
        cadena = teclado.nextLine();
        if(pc.aClausula(cadena))
            System.out.println("*** Error: formato de la clusula incorrecta. ***");
        else
        {
            clausula = pc.clausula;
            
            clausula.clave = llave;
            System.out.println(clausula.toString());

            posicion = maestro.agregar(clausula,0);
            indice.agregar(llave, posicion);

            arbol = indice.obtenerIndice();
        }
            
        
    }
    
    private void mostrarSecuencialIndexado() throws IOException{
        int llave;
        Nodo nodo;
        Clausula estructura;
       
        System.out.println("--- Mostrar Clausula (Secuencial indexado) ---");
        System.out.print("Dame llave: ");
        llave = Integer.parseInt(teclado.nextLine());
        
        nodo = arbol.buscar(llave);
        if(nodo != null)
        {
            estructura = maestro.obtener(nodo.direccion);
            
            System.out.println(estructura.toString());
        }
        else
            System.out.println(" ***No existe la llave***");
        
    }
    
    private void mostrarSecuencial() throws IOException{
        int noRegistro;
        Clausula estructura;
        
        System.out.println("--- Mostrar Clausula (Secuencial) ---");
        System.out.print("Dame no registro: ");
        noRegistro = Integer.parseInt(teclado.nextLine());
        
        estructura = maestro.obtener(noRegistro);
        
        if(estructura != null)
        {
            System.out.println(estructura.toString());
        }
        else
            System.out.println("\nNo existe la estructura de registros\n");
    }
    
    private void eliminar() throws IOException{
        int llave;
        Nodo nodo;
        Clausula estructura;
        ArrayList<Nodo> direcciones;
        int direccion;
        
        System.out.println("--- Eliminar Clausula ---");
        System.out.print("  Dame la llave: ");
        llave = Integer.parseInt(teclado.nextLine());
        
        nodo = arbol.buscar(llave);
        if(nodo != null)
        {
            estructura = maestro.obtener(nodo.direccion);
            System.out.println(estructura.toString()+"\n");
            maestroTemp = new ArchivoMaestro(Archivo.MaestroTemp);
            indiceTemp = new ArchivoIndice(Archivo.IndiceTemp);
            
            direcciones = arbol.obtenerDatos();
            for(int i=0;i<direcciones.size();i++)
            {
                estructura = maestro.obtener(direcciones.get(i).direccion);
                if(nodo.direccion != direcciones.get(i).direccion)
                {
                    direccion = maestroTemp.agregar(estructura, 0);
                    
                    indiceTemp.agregar(direcciones.get(i).llave, direccion);
                }
            }
            
            indiceTemp.cerrar();
            indice.cerrar();
            maestroTemp.cerrar();
            maestro.cerrar();
            
            Archivo.eliminar(Archivo.Indice);
            Archivo.eliminar(Archivo.Maestro);
            
            Archivo.renombrar(Archivo.IndiceTemp, Archivo.Indice);
            Archivo.renombrar(Archivo.MaestroTemp, Archivo.Maestro);
            
            
            maestro = new ArchivoMaestro(Archivo.Maestro);
            indice = new ArchivoIndice(Archivo.Indice);
            arbol = indice.obtenerIndice();
        }
        else
            System.out.println("***No existe la llave***");
    }
    
    private void actualizar() throws IOException{
        /*String datos[];
        Clausula clausula;
        Clausula clausulaAnt;
        int posicion;
        int noPreAnt=0;
        String argumentos;
        int llave;
        String nombre;
        ArrayList<String> antecedente;
        Nodo nodo;
        
        System.out.println("--- Actualizar Clausula ---");
        System.out.print("Dame llave: ");
        llave = Integer.parseInt(teclado.nextLine());
        
        nodo = arbol.buscar(llave);
        if(nodo != null){
            clausulaAnt = maestro.obtener(nodo.direccion);
            System.out.println(clausulaAnt.toString()+"\n");
            
            System.out.print("Dame número preposiciones del antecedente(>=0): ");
            noPreAnt = Integer.parseInt(teclado.nextLine());

            clausula = new Clausula();
            clausula.clave = llave;
            for(int i=0;i<noPreAnt;i++)
            {
                antecedente = new ArrayList();
                System.out.print("Dame nombre de la preposicion "+(i+1)+": ");
                nombre = teclado.nextLine();
                antecedente.add(nombre);
                System.out.print(" Dame argumentos: ");
                argumentos = teclado.nextLine();

                datos = argumentos.split(" ");
                for(int j=0;j<datos.length;j++)
                {
                    antecedente.add(datos[j]);
                }

                clausula.antecedente.add(antecedente);
            }

            System.out.print("Dame nombre del consecuente: ");
            nombre = teclado.nextLine();
            clausula.consecuente.add(nombre);
            System.out.print(" Dame argumentos: ");
            argumentos = teclado.nextLine();
            datos = argumentos.split(" ");

            for(int j=0;j<datos.length;j++)
            {
                clausula.consecuente.add(datos[j]);
            }

            System.out.println("Se modifico:");
            System.out.println(clausulaAnt.toString());
            System.out.println("a");
            System.out.println(clausula.toString());
            
            posicion = maestro.agregar(clausula,nodo.direccion);
        }
        else
            System.out.println("\n ***No existe la llave***\n");
        */
        
        int llave;
        Nodo nodo;
        String cadena;
        Clausula clausula, clausulaAnt;
        ParsearClausula pc = new ParsearClausula();
        
        System.out.println("--- Actualizar Clausula ---");
        System.out.print("Dame llave: ");
        llave = Integer.parseInt(teclado.nextLine());
        
        nodo = arbol.buscar(llave);
        if(nodo != null){
            clausulaAnt = maestro.obtener(nodo.direccion);
            System.out.println(clausulaAnt.toString()+"\n");
            System.out.print("Dame nueva clausula: ");
            cadena = teclado.nextLine();
            if(pc.aClausula(cadena))
                System.out.println("*** Error: formato de la clusula incorrecta. ***");
            else{
                clausula = pc.clausula;
                clausula.clave = llave;
                System.out.println("Se modifico:");
                System.out.println(clausulaAnt.toString());
                System.out.println("a");
                System.out.println(clausula.toString());

                maestro.agregar(clausula,nodo.direccion);
            }
        }
        else
            System.out.println("\n ***No existe la llave***\n");

    }

    private void mostrarTodas() throws IOException {
        ArrayList<Nodo> direcciones;
        Clausula estructura;
        
        System.out.println("--- Mostrar Todas Las Clausulas ---");
        direcciones = arbol.obtenerDatos();
        for(int i=0;i<direcciones.size();i++)
        {
            estructura = maestro.obtener(direcciones.get(i).direccion);
            System.out.println(estructura.toString());
        }
    }
    
    private void inferir(){
        String cadena;
        
        System.out.println("--- Inferir ---");
        
    }
    
}
