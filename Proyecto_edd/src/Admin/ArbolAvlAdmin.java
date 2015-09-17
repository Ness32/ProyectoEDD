package Admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;







public class ArbolAvlAdmin{

NodoAvlAdmin    raiz;
private boolean AumentoAltura;


//trafico del arbol

private String Nodos = "";

//relacion de nodos

private String Relaciones = "";

// Recorrido

private String  Recorrido ="";

public ArbolAvlAdmin(){
    this.raiz = null;
        File bitacoraVieja = new File("admin", "bitacora.txt");
            bitacoraVieja.delete();
}

//************* Metodo de Busqueda *****************//

public NodoAvlAdmin buscar(String cadena){
if (raiz !=null){
    return raiz.buscarNodo(cadena);
        }else{
            return null;

               }

}
 
public NodoAvlAdmin IniciarSesion(String nick, String password){

    if (raiz !=null){
        try{
            NodoAvlAdmin resultado = raiz.buscarNodo(nick);
                if(resultado !=null){
                    if( resultado.getUsuario().getPassword().equals(password)){
                        System.out.println("Usuario o contraseña incorrecta");
                        return resultado;
                    }else{
                        System.out.println("Usuario o contraseña incorrecta");
                        }
                    }
               } catch (Exception e){
                 return null;
               }    
            }


return null;
}

public void InOrden(){
 InOrdenAVL(raiz);
}

//Recorrido en Pre-Orden

private void PreOrdenAVL(NodoAvlAdmin Nodo){
    if (Nodo == null){
        return;
    }else{
            Recorrido +=Nodo.getUsuario().getNick()+"\n";
            PreOrdenAVL(Nodo.izqda);
            PreOrdenAVL(Nodo.drcha);
    }


}
//Recorrido  Post-Orden
private void PostOrdenAVL(NodoAvlAdmin Nodo){
    if(Nodo == null){
    return;
    }else{
      PostOrdenAVL(Nodo.izqda);
      PostOrdenAVL(Nodo.drcha);
      Recorrido += Nodo.getUsuario().getNick()+"\n";
    }


}

//Recorrido en In-Orden

private void InOrdenAVL(NodoAvlAdmin Nodo){
    if (Nodo == null){
     return;
    }else{
            InOrdenAVL(Nodo.izqda);
            Recorrido += Nodo.getUsuario().getNick()+"\n";
            System.out.println(Nodo.getUsuario().getNick());
            InOrdenAVL(Nodo.drcha);
    }
}

// ********************* Mostrar Los Usuarios ********************//

private String usuarios ="";
public String MostrarUsuarios(){
 usuarios ="";
 recorrer(raiz);
 return usuarios;
}
private void recorrer(NodoAvlAdmin Nodo){
    if(Nodo==null){
    return;
    }else {
        recorrer (Nodo.izqda);
            if(Nodo.getUsuario()!=null){
            usuarios +="<li>";
            usuarios +="<form action =\"admUsuarios.jsp\"method=\"post\">";
            usuarios += "<input type=\"hidden\" name=\"hiddenField\" id=\"hiddenField\" value=\"" + Nodo.getUsuario().getNick() + "\">";
                usuarios += Nodo.getUsuario().getNick() + "&nbsp;&nbsp;-&nbsp;";
                usuarios += "<input type=\"image\" name=\"eliminar\" id=\"eliminar\" src=\"imagenes/eliminar.png\"> | "
                        + "<input type=\"image\" name=\"ver\" id=\"ver\" src=\"imagenes/ver.png\">";
                usuarios += "</form>";
                usuarios += " </li>";
            }
            recorrer(Nodo.drcha);
    
    }

}
//************************* INSERTAR *****************//

public boolean Insertar (UsuariosAdmin usuario){
    if((!UsuarioExistente(usuario.getNick()))){
        NodoAvlAdmin nuevo = new NodoAvlAdmin(usuario);
        raiz = InsertarBalanceado(raiz,nuevo);
        return true;
    }else{
    return false;
    }



}

//********************* INSERTAR BALANCEADO ************//

private NodoAvlAdmin InsertarBalanceado(NodoAvlAdmin subArbol, NodoAvlAdmin Nodo){
NodoAvlAdmin n1;
NodoAvlAdmin NodoAux = Nodo;

if(ArbolVacio(subArbol)){
    subArbol = NodoAux;
    AumentoAltura = true;
}else if(Nodo.getUsuario().getNick().compareToIgnoreCase(subArbol.getUsuario().getNick())<0){
    subArbol.izqda = InsertarBalanceado(subArbol.izqda, Nodo);
        if(AumentoAltura){
            switch (subArbol.fe){
                case 1: {
                    subArbol.fe = 0;
                }
                break;
                case 0:
                    subArbol.fe = -1;
                        break;
                case -1:{
                    n1 = subArbol.izqda;
                    if(n1.fe ==-1){
                        subArbol = RotacionII(subArbol, n1);
                    }else{
                        subArbol = RotacionID(subArbol, n1);
                    }
                    AumentoAltura = false;
                }
                break;
                        
            }
        }
}else {

if(Nodo.getUsuario().getNick().compareToIgnoreCase(subArbol.getUsuario().getNick())>0){
    subArbol.drcha = InsertarBalanceado(subArbol.drcha, Nodo);
        if(AumentoAltura){
            switch (subArbol.fe){
                case -1:
                    subArbol.fe = 0;
                    AumentoAltura = false;
                    break;
                case 0:
                    subArbol.fe =1;
                    break;
                case 1: {
                 n1 = subArbol.drcha;
                 if(n1.fe ==1){
                     subArbol = RotacionDD(subArbol, n1);
                     
                 }else {
                     subArbol = RotacionDI (subArbol , n1);
                }
                 AumentoAltura = false;
                }
                break;
            }
        }
}else{
    AumentoAltura= false;
}

}
return subArbol;
}

// **********************ROTACIONES***************************

private NodoAvlAdmin RotacionDD(NodoAvlAdmin n, NodoAvlAdmin n1){
try{
    Bitacora("Derecha-Derecha", n.getUsuario().getNick()
            +","+n1.getUsuario().getNick()+","
            + n1.drcha.getUsuario().getNick(),Altura(n.drcha) -Altura(n.izqda)
    
    );
}catch(Exception e){}
n.drcha= n1.izqda;
n1.izqda = n;
if(n1.fe ==1){
    n.fe =0;
    n1.fe=0;
}else{
    n.fe = 1;
    n1.fe=-1;
}
n=n1;
return n;
}

//*************************//

private NodoAvlAdmin RotacionDI(NodoAvlAdmin n, NodoAvlAdmin n1){
 try{
 Bitacora("Derecha-Izquierda", n.getUsuario().getNick()
                    + ", " + n1.getUsuario().getNick() + ", "
                    + n1.izqda.getUsuario().getNick(), Altura(n.drcha) - Altura(n.izqda));
 
 }catch(Exception e){}
NodoAvlAdmin n2;

n2=n1.izqda;
n.drcha = n2.izqda;
n2.izqda = n;
n1.izqda = n2.drcha;
n2.drcha = n1;
if(n2.fe==1){
    n.fe =-1;
}else{
    n.fe =0;
}
if(n2.fe==-1){
    n1.fe =1;
}else{
    n1.fe =0;
}
n2.fe =0;
n=n2;
return n;
}

//*********************************************//

private NodoAvlAdmin RotacionII(NodoAvlAdmin n, NodoAvlAdmin n1){
    try {
            Bitacora("Izquierda-Izquierda", n.getUsuario().getNick()
                    + ", " + n1.getUsuario().getNick() + ", "
                    + n1.izqda.getUsuario().getNick(), Altura(n.drcha) - Altura(n.izqda));
        } catch (Exception e) {}
    
    n.izqda =n1.drcha;
    n1.drcha= n;
    if(n1.fe == -1){
        n.fe = 0;
        n1.fe=0;
    }else {
        n.fe=-1;
        n1.fe=1;
    }
    n=n1;
    return n;
}

//*************************************************//

private NodoAvlAdmin RotacionID(NodoAvlAdmin n, NodoAvlAdmin n1){
 try {
            Bitacora("Izquierda-Derecha", n.getUsuario().getNick() + ", "
                    + n1.getUsuario().getNick() + ", "
                    + n1.drcha.getUsuario().getNick(), Altura(n.drcha) - Altura(n.izqda));
        } catch (Exception e) {}
 NodoAvlAdmin n2;
 n2 = n1.drcha;
 n.izqda = n2.drcha;
 n2.drcha = n;
 n1.drcha = n2.izqda;
 n2.izqda = n1;
 if(n2.fe ==1){
     n1.fe =-1;
 }else{
     n1.fe=0;
 }
 if (n2.fe==-1){
     n.fe=1;
 }else {
     n.fe =0;
 }
 n2.fe=0;
 n=n2;
return n;
}


private int Altura(NodoAvlAdmin  raiz){
if(raiz == null){
return 0;
}else{
return 1+Math.max(Altura(raiz.izqda), Altura(raiz.drcha));
}

}
private boolean ArbolVacio(NodoAvlAdmin R){
return (R==null);
}

public boolean UsuarioExistente(String Nick){
NodoAvlAdmin aux = raiz;
boolean miembro = false;
while(aux != null){
    if(Nick.equalsIgnoreCase(aux.getUsuario().getNick())){
        miembro = true;
        aux =null;
    }else{
            if(Nick.compareToIgnoreCase(aux.getUsuario().getNick())>0){
                aux = aux.drcha;                
            }else{
                aux=aux.izqda;
                if(aux == null){
                    miembro = false;
                }
            }
    }
}
 return miembro;
}

//Bitacora para seguir movimientos

private void Bitacora(String TipoDeRotacion, String NodosInvolucrados, int fe) {
        try {
            File bitacora = new File("bitacora.txt");
            FileWriter fw = new FileWriter(bitacora, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            salida.println("Factor de equilibrio: " + fe);
            salida.println("Rotacion " + TipoDeRotacion);
            salida.println("Nodos en la rotación: " + NodosInvolucrados);
            salida.println("Fecha: " + Calendar.getInstance().getTime().toLocaleString());
            salida.println();
            salida.close();
        } catch (IOException io) {
        }
    }

    /**
     * Metodo para abrir la bitacora
     * @return
     */
    public String AbrirBitacora() {
        String s = "";
        String Texto = "";
        try {
            FileReader fr = new FileReader(new File("temp", "bitacora.txt"));
            BufferedReader entrada = new BufferedReader(fr);
            while (true) {
                s = entrada.readLine();
                if (s != null) {
                    Texto = Texto + s + "\n";
                } else {
                    return Texto;
                }
            }
        } catch (Exception x) {
        }
        return Texto;
    }


}