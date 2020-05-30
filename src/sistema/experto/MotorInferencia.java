package sistema.experto;

import clausula.Clausula;
import java.util.ArrayList;

/**
 * @author dzp
 */
public class MotorInferencia {
    BaseConocimiento baseConocimiento;
    BaseHechos baseHechos;
    
    public MotorInferencia(ArrayList<Clausula> reglasConocimiento){
        this.baseConocimiento = new BaseConocimiento(reglasConocimiento);
    }
    
    public String inferir(ArrayList<Clausula> clausulasEntrada){
        ArrayList<Clausula> pr, ch,pr2;
        Clausula clausula;
        String salida = "";
        
        salida = "# BASE DE HECHOS #\n";
        salida += "# CLAUSULAS INICIALES #\n";
        for(Clausula c :clausulasEntrada)
            salida +=c+"\n";
        
        baseHechos = new BaseHechos(clausulasEntrada);
        
        do{
            salida += "# CLAUSULAS QUE PODRIAN RESOLVERSE NUEVAS #\n";
            
            pr = baseConocimiento.getPodrianResolverce(clausulasEntrada, baseHechos);
            baseHechos.agregarPodrianSolucionarse(pr);
            pr = baseHechos.podrianSolucionarse;
            pr = baseHechos.eliminarYaSolucionados(pr);

            
            for(Clausula c: pr)
                salida += c+"\n";

            salida += "# TRATAR DE SOLUCIONARSE #\n";

            ch = new ArrayList();
            for(Clausula c: pr)
            {
                clausula = baseHechos.resolver(c);
                if(clausula.resuelta)
                {
                    salida += "Se resolvio: "+clausula+"\n";
                    ch.add(clausula);
                }
                else{
                    salida += "No se resolvio: "+clausula+"\n";
                }
            }
            baseHechos.agregarNuevosHechos(ch);
            salida += "# SE PUDO SOLUCIONAR #\n";
            for(Clausula c: ch)
            {
                salida += c+"\n";
            }
            
            salida += "\n";
        }while(ch.size()>0);
        
        return salida;
    }
    
    public ArrayList<Clausula> solucionarHechos(){
        return null;
    }
}
