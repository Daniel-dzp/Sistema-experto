package sistema.experto;

import clausula.Clausula;
import java.util.ArrayList;
import sistema.experto.arbol.Sustitucion;

/**
 * @author dzp
 */
public class BaseHechos {
    String justificacion;
    ArrayList<Clausula> hechos;
    public ArrayList<Clausula> podrianSolucionarse;
    public ArrayList<Clausula> resultados;
    
    public BaseHechos(ArrayList<Clausula> clausulas){
        this.hechos = clausulas;
        podrianSolucionarse = new ArrayList();
    }
    
    public void agregarNuevosHechos(ArrayList<Clausula> n)
    {
        hechos.addAll(n);
    }
    
    public void agregarPodrianSolucionarse(ArrayList<Clausula> p)
    {
        
        podrianSolucionarse.addAll(p);
    }
    
    public Clausula resolver(Clausula aResolver){
        ArrayList<Sustitucion> variables = new ArrayList();
        ArrayList<String> p;
        Clausula t;
        Clausula c = new Clausula();
        boolean seResolvio = true;
        
        c.clave = aResolver.clave;
        
        for(ArrayList<String> ant: aResolver.antecedente){
//            System.out.print(ant.get(0)+"    ");
            t = existeHecho(ant.get(0));
            if(t!=null){
                if(ant.size() == t.consecuente.size()){
                    p = new ArrayList();
                    p.add(ant.get(0));
                    for(int i=1;i<ant.size();i++){
                        if(esVariable(ant.get(i)) && !esVariable(t.consecuente.get(i)))
                        {
                            
//                            System.out.print(ant.get(i)+"-"+t.consecuente.get(i)+" #  ");
                            if(!existeVariable(variables,ant.get(i)))
                            {
                                variables.add(new Sustitucion(ant.get(i),t.consecuente.get(i)));
                                p.add(t.consecuente.get(i));
                            }
                            else
                                p.add(ant.get(i));
                        }
                        else
                        {
                            if(!esVariable(ant.get(i)) && !esVariable(t.consecuente.get(i)))
                            {
                                if(!ant.get(i).equals(t.consecuente.get(i)))
                                    seResolvio = false;
                            }
                            else
                                seResolvio = false;
//                            System.out.print("No tiene clausula");
                            p.add(ant.get(i));
                        }
                    }
                    c.antecedente.add(p);
                }
                else{
                    seResolvio = false;
//                    System.out.println("Error el numero de argumentos");
                    
                    c.antecedente.add(ant);
                }
            }
            else{
//                System.out.print("No existe clausula 2");
                seResolvio = false;
                
                c.antecedente.add(ant);
            }
//            System.out.println("");
        }
        
        Sustitucion s;
        int i=0;
        for(String h: aResolver.consecuente)
        {
            if(i==0)
                c.consecuente.add(h);
            else
            {
                s = getVariable(variables, h);
                if(s!=null)
                    c.consecuente.add(s.Sustitucion);
                else
                    c.consecuente.add(h);
            }
            s = getVariable(variables, h);
//            System.out.println("h: "+h+" - "+(s==null?"":s.Sustitucion));
            
            i++;
        }
//        if(seResolvio)
//            System.out.println("Se resolvio hecho");
//        else
//            System.out.println("No se resolvio hecho");
        
        c.resuelta = seResolvio;
        
        return c;
    }
    
    public ArrayList<Clausula> eliminarYaAgregadas(ArrayList<Clausula> clausulas){
        ArrayList<Clausula> l = new ArrayList();
        
        for(Clausula c: clausulas)
        {
            if(!(YaAgregado(c.clave)))
                l.add(c);
        }
        
        return l;
    }
    public ArrayList<Clausula> eliminarYaSolucionados(ArrayList<Clausula> clausulas){
        ArrayList<Clausula> l = new ArrayList();
        
        for(Clausula c: clausulas)
        {
            if(!(YaSolucionado(c.clave)))
                l.add(c);
        }
        
        return l;
    }
    
    public boolean YaAgregado(int llave){
        for(Clausula c: podrianSolucionarse)
            if(c.clave==llave)
                return true;
        
        return false;
    }
    
    public boolean YaSolucionado(int llave){
        for(Clausula c:hechos)
            if(c.clave==llave)
                return true;
        
        return false;
    }
    
    private Clausula existeHecho(String h){
        for(Clausula c:hechos)
            if(c.consecuente.get(0).equals(h))
                return c;
        
        return null;
    }
    
    private boolean esVariable(String v){
        return Character.isUpperCase(v.charAt(0));
    }
    
    private boolean existeVariable(ArrayList<Sustitucion> variables, String v)
    {
        for(Sustitucion var: variables)
            if(v.equals(var.variable))
                return true;
        return false;
    }
    
     private Sustitucion getVariable(ArrayList<Sustitucion> variables, String v)
    {
        for(Sustitucion var: variables)
            if(v.equals(var.variable))
                return var;
        return null;
    }
}
