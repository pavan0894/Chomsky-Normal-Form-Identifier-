/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnfditector;

/**
 *
 * @author pavanamarnath
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class ProdUtil {

    int type;
    String production;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public ProdUtil(int type, String production) {
        this.type = type;
        this.production = production;
    }

    public ProdUtil() {

    }

}

public class CNFDitector {

    static String trmnls[], ntrmnls[],trmnlStr,ntrmnlStr;
    
        static char strtSymbol;
    public static void main(String[] args) throws Exception {
        // We need to provide file path as the parameter:
        // double backquote is to avoid compiler interpret words
        // like \test as \t (ie. as a escape sequence)
        File file = new File("production.txt");

        List<ProdUtil> alltypes = new ArrayList<ProdUtil>();
        alltypes.add(new ProdUtil(0,"Unrestricted grammar which is recognized by Turing machine"));
        alltypes.add(new ProdUtil(1,"Context-sensitive grammar which is recognized by a linear bounded automaton"));
        alltypes.add(new ProdUtil(2,"Context-free grammar which is recognized by Pushdown automaton"));
        alltypes.add(new ProdUtil(3,"Regular grammar which is recognized by Finite state automaton"));
        
        int lno = 0;
        List<ProdUtil> type = new ArrayList<ProdUtil>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int p;

        if (((st = br.readLine()) != null)) {
            trmnls = st.split(",");
            trmnlStr = st;
            if (((st = br.readLine()) != null)) {
                ntrmnls = st.split(",");
                ntrmnlStr = st;

                if (((st = br.readLine()) != null)) {
                    strtSymbol = st.charAt(0);
                    if (st.contains("->")) {
                        p = getProd(st);
                        type.add(new ProdUtil(p, st));

                    } else {
                        type.add(new ProdUtil(-1, st));
                    }
                    lno++;
                    while ((st = br.readLine()) != null) {
                        if (st.contains("->")) {
                            p = getProd(st);
                            type.add(new ProdUtil(p, st));

                        } else {
                            type.add(new ProdUtil(-1, st));
                        }
                        lno++;

                    }
                    if (type.size() == lno) 
                    {
                        System.out.println(" \n WELCOME TO FORMAL LANGUAGE INDENTIFIER \n");
                        System.out.println("PROCESSED ALL THE PRODUCTIONS.....\n \nGiven Grammar:");
                        
                        System.out.println("V = {"+trmnlStr+"}");
                        System.out.println("T = {"+ntrmnlStr+"}");
                        System.out.println("S = "+strtSymbol);
                        System.out.println("P = {");
                        for (ProdUtil prod : type) 
                        {
                            System.out.println(prod.getProduction());
                        }
                        
                        System.out.print("}\nThe given grammer is");
                        int min = 5;
                        for(ProdUtil pu : type){
                            
                            if(pu.type<min && pu.type>=0){
                                min=pu.type;
                            }
                        }
                        System.out.println(" Type - "+min+" i.e "+alltypes.get(min).getProduction());
                        
                    }
                } else {
                    System.err.println("File is not Valid, Unable to fetch Productions");

                }

            } else {

                System.err.println("File is not Valid unable to fetch non-terminals ");
            }
        } else {
            System.err.println("File is not Valid unable to fetch terminals");
        }

    }

    static boolean arrayContains(String str,String arr[]){
        
        return false;
    }
    
    private static int getProd(String st) {
        st = st.replaceAll(" ", "");
        String toks[] = st.split("->");
        if(toks.length ==2){
            
            if(toks[0].length()==1 && (toks[0].equals(""+strtSymbol) || isTerminal(toks[0]))){
                
                if(checkType3(toks[1])){
                    
                    return 3;
                }else {
                    return 2;
                    
                }
                
            }else{
                
                // LHS contains more than 1 element i.e not 3 and 2
                if(containsTerminal(toks[0]) || strtSymbol == toks[0].charAt(0)){
                    if(containsNonTerminal(toks[1])){
                        return 1;
                    }else{
                        return 0;
                    }
                    
                    
                }else{
                    return -3;
                }
                
                
                
            }
            
        }else{
            return -2;// not valid production, since LHS does not contain a Terminal
        }
    }

    private static boolean checkType3(String tok) {
       
        if(tok.length()==1 && isNonTerminal(tok)){
            
            return true;
        }
        else if(tok.length()==2 && isNonTerminal(tok.substring(0, 1)) && isTerminal(tok.substring(1, 2))){
            
            return true;
            
        }
        return false;
    }
    
     private static boolean isTerminal(String tok) {
         
         if(tok.equals(""+strtSymbol)){
             return true;
         }
        for(int i=0;i<trmnls.length;i++){
            if(trmnls[i].equals(tok))
                return true;
        }
        
        
        return false;
    }
     private static boolean isNonTerminal(String tok) {
       
        for(int i=0;i<ntrmnls.length;i++){
            
            //System.out.println("s ="+tok+" ,  cur="+ntrmnls[i]+" bl = "+ntrmnls[i].contains(tok)+" len = "+ntrmnls[i].length());
            if(ntrmnls[i].equals(tok))
                return true;
        }
        
        return false;
    }

    private static boolean containsTerminal(String tok) {
        for(String t : trmnls){
            if(tok.contains(t)){
                return true;
            }
        }
        if(tok.equals(""+strtSymbol)){
             return true;
         }
        return false;
    }

    private static boolean containsNonTerminal(String tok) {
         for(String t : ntrmnls){
            if(tok.contains(t)){
                return true;
            }
        }
        return false;
    }
    
    
}
