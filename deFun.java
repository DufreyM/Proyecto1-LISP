import java.util.List;

public class deFun {
    private String deFunname;
    private List<String> parametros;
    private String instrucciones;

    public deFun(String deFunname, List<String> parametros, String instrucciones){
        this.deFunname = deFunname;
        this.parametros = parametros;
        this.instrucciones = instrucciones;
    }

    // Setter para deFunname
    public void setDeFunname(String deFunname) {
        this.deFunname = deFunname;
    }

    // Getter para deFunname
    public String getDeFunname() {
        return deFunname;
    }

    // Setter para parametros
    public void setParametros(List<String> parametros) {
        this.parametros = parametros;
    }

    // Getter para parametros
    public List<String> getParametros() {
        return parametros;
    }

    // Setter para instrucciones
    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    // Getter para instrucciones
    public String getInstrucciones() {
        return instrucciones;
    }
}


