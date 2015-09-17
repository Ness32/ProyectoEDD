package Admin;
import Admin.UsuariosAdmin;
public class NodoAvlAdmin{

 //Enlaces
 
 //Rama Izquierda
    
    public NodoAvlAdmin izqda;
 // Rama Derecha
    public NodoAvlAdmin drcha;
 //facto de Equilibrio
    int fe;
    
 //nodo usuario
    private UsuariosAdmin usuario;
    
    public NodoAvlAdmin(UsuariosAdmin usuario){
    this.usuario = usuario;
    }
    
    public NodoAvlAdmin buscarNodo(String cadena){
        if(usuario !=null){
            String DatoComparacion = usuario.getNick();
                if(cadena.equalsIgnoreCase(DatoComparacion)){
                   return this; 
                    }else if(cadena.compareToIgnoreCase(DatoComparacion)<0){
                         if (izqda != null){
                            return izqda.buscarNodo(cadena);
                                }else {
                                    return null;
                                        }
                                            }else if(cadena.compareToIgnoreCase(DatoComparacion)>0){
                                                if (drcha !=null){
                                                        return drcha.buscarNodo(cadena);
                                                                 }else{
                                                                 return null;
                    }
                }
        }
            return null;
    }
    //metodo Get
    public UsuariosAdmin getUsuario(){
        return usuario;
        }
    
    public void setUsuario(UsuariosAdmin usuario){
     this.usuario=usuario;
    }
}