package sistema.experto;

import clausula.Clausula;
import java.util.ArrayList;

/**
 * @author dzp
 */
public class BaseConocimiento {
    ArrayList<Clausula> clausulas;
    
    public BaseConocimiento(ArrayList<Clausula> clausulas)
    {
        this.clausulas = clausulas;
    }
    
    public ArrayList<Clausula> getPodrianResolverce(ArrayList<Clausula> clausulasHechos, BaseHechos baseHechos){
        ArrayList<Clausula> r = new ArrayList();
        
        for(Clausula h: clausulasHechos)
        {
            for(Clausula c: clausulas)
            {
                if(estaEnAntecedente(h, c) && !baseHechos.YaAgregado(c.clave))
                {
                    r.add(c);
                }
            }
        }
        
        return r;
    }
    
    private boolean estaEnAntecedente(Clausula hecho, Clausula regla){
        for(ArrayList<String> p:regla.antecedente)
        {
            if(hecho.consecuente.get(0).equals(p.get(0)))
                return true;
        }
        
        return false;
    }
}
