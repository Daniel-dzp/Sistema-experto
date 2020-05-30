package clausula;

import java.util.ArrayList;

public class Clausula {
    public int clave;
    public ArrayList<String> consecuente;
    public ArrayList<ArrayList<String>> antecedente;
    
    public boolean resuelta;
    
    public Clausula(){
        consecuente = new ArrayList();
        antecedente = new ArrayList();
        clave = -1;
        
        resuelta = false;
    }
    
    public Clausula toClausula(String ClausulaCadena)
    {
        Clausula c = new Clausula();
        
        return c;
    }
    
    public boolean isRegla(){
        return antecedente.size()>0;
    }
    
    public boolean isHecho(){
        return antecedente.size()==0;
    }
    
    public Clausula clonar()
    {
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println("*******no se puede duplicar");
        }
        return (Clausula)obj;
    }
    
    @Override
    public String toString()
    {
        String cadena = "";
        
        if(clave >-1)
            cadena = "p"+clave+": ";
        else
            cadena = "px: ";
            
        for(int i=0;i<antecedente.size();i++)
        {
            for(int j=0;j<antecedente.get(i).size();j++)
            {
                if(j==0)
                    cadena += antecedente.get(i).get(j)+"(";
                else
                    if(j==1)
                        cadena += antecedente.get(i).get(j);
                    else
                        cadena +=", "+antecedente.get(i).get(j);
                
                if(j+1==antecedente.get(i).size())
                    cadena += ") ";
            }
            if(i+1<antecedente.size())
                cadena += "^ ";
        }
        
        if(antecedente.size()>0)
            cadena +="→ ";
        
        for(int i=0;i<consecuente.size();i++)
        {
            if(i==0)
                cadena += consecuente.get(i)+"(";
            else
                if(i==1)
                    cadena += consecuente.get(i);
                else
                    cadena +=", "+consecuente.get(i);
            
            
            if(i+1==consecuente.size())
                    cadena += ")";
        }
        
        return cadena;
    }
}