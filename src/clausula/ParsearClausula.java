package clausula;

import java.util.ArrayList;

/**
 * @author dzp
 */
public class ParsearClausula {
    ArrayList<String> tokens;
    String act;
    int posicion;
    
    private boolean esHecho;
    private int noPreposiciones;
    public Clausula clausula;
    private ArrayList<String> listTemp;
    boolean error;
    
    public ParsearClausula(){}
    
    public boolean aClausula(String cadena)
    {
        esHecho = true;
        noPreposiciones = 0;
        clausula = new Clausula();
        listTemp = new ArrayList();
        
        tokens = new ArrayList();
        StringALista(cadena);
        
//        System.out.println("\n\n");
        act = "";
        posicion = 0;
        
        error = main();
////        System.out.println(error+" - "+act);
        return error;
    }
    
    private String sig()
    {
        if(tokens.size()>posicion)
        {
            act = tokens.get(posicion);
            posicion++;
//            System.out.println("token: "+act);
            return act;
        }
        else
        {
            act = null;
            return act;
        }
    }
    
    
    private void StringALista(String cadena)
    {
        char c=' ';
        String s;
        boolean seguir=false,seguirF=false;
        
        s = "";
        for(int i=0;i<cadena.length();i++)
        {
            c = cadena.charAt(i);
            
            if(c != '(' && c != ')' && c != ',' && c != '=' &&c!=' '&&c!='>')
            {
                s += c;
                seguir = true;
            }
            else
            {
                if(seguir || c == ' ')
                {
                    seguir = false;
                    if(!s.equals(""))
                    {
//                        System.out.println(s);
                        tokens.add(s);
                    }
                    s="";
                }
            }
            
            if(c == '(')
            {
                tokens.add(c+"");
//                System.out.println(c);
            }
            if(c == ')')
            {
                tokens.add(c+"");
//                System.out.println(c);
            }
            if(c == ',')
            {
                tokens.add(c+"");
//                System.out.println(c);
            }
            if(c == '=')
            {
                seguirF=true;
                s += c;
            }
            if(c == '>' && seguirF)
            {
                seguirF = false;
                s += c;
                if(!s.equals(""))
                {
                    //System.out.println(s);
                    tokens.add(s);
                }
                s="";
            }
        }
        if(!"".equals(s))
        {
            //System.out.println(s);
            tokens.add(s);
        }
    }
    
    
    private boolean main(){
        sig();
        if(antecedente())
            return true;
        
        if(noPreposiciones==1 && act==null)
        {
            clausula.consecuente = clausula.antecedente.get(0);
            clausula.antecedente.clear();
            //System.out.println("Es un hecho");
            return false;
        }
        
        if(!"=>".equals(act))
            return true;
        
        sig();
        if(consecuente())
            return true;
        
        if(act != null)
            return true;
        //System.out.println("Exito");
        return false;
    }
    
    private boolean consecuente(){
        if("(".equals(act) || ")".equals(act) || ",".equals(act) || "(".equals(act) || "=>".equals(act)){
            return true;
        }
        
        listTemp.add(act);
        
        if(!"(".equals(sig()))
            return true;
        sig();
        if(argumentos())
            return true;
        
        if(!")".equals(act))
            return true;
        
        clausula.consecuente=listTemp;
        
        sig();
        return false;
    }
    
    private boolean antecedente(){
        noPreposiciones ++;
        
        if("(".equals(act) || ")".equals(act) || ",".equals(act) || "(".equals(act) || "=>".equals(act)){
            return true;
        }
        
        listTemp.add(act);
        
        if(!"(".equals(sig()))
            return true;
        sig();
        if(argumentos())
            return true;
        
        if(!")".equals(act))
            return true;
        
        sig();
        if(",".equals(act))
        {
            clausula.antecedente.add(listTemp);
            listTemp = new ArrayList();
            
            
            sig();
            if(antecedente())
                return true;
        }
        else{
                clausula.antecedente.add(listTemp);
                listTemp = new ArrayList();
        }
        
        return false;
    }
    
    private boolean argumentos()
    {
        if( "(".equals(act) || ")".equals(act) || ",".equals(act) || "(".equals(act) || "=>".equals(act))
            return true;
        
        listTemp.add(act);
        
        sig();
        if(",".equals(act))
        {
            sig();
            return argumentos();
        }
        
        return false;
    }
    
    
}
