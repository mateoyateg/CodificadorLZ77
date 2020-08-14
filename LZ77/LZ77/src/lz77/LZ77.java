/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lz77;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author KevinB
 */
public class LZ77 {

    static int tamVentana=0;
    static int tamBuffer=0;
    
    //Metodo que codifica una palabra con un tamaño de ventana y buffer determinado
    public String codificar(String texto, int tamVentana, int tamBuffer){
        
        //Se crean los arreglos del Buffer y de la ventana
        String[] letras = texto.split("");
        String[] Buffer = new String [tamBuffer];
        String[] Ventana = new String [tamVentana];
        
        //Se define una variable para la salida
        String salida="";
        
        //Se define un boleano para saber si se acabo la palabra o no
        boolean acabo = false;
        
        //Variable para revisar en que parte de la palabra se va
        int recorr=0;
  
        //Le pasamos al buffer la primera parte de la cadena
        for (recorr=0; recorr<tamBuffer; recorr++){
            Buffer[recorr]=letras[recorr];
        }
        
        //Inicializar ventana
        for (int t=0; t<tamVentana;t++){
            Ventana[t]="_";
        }
        
        //Contador de iteraciones
        int auxiliar = 0;
        
        //Mientras no se acabe la palabra
        while (Buffer[0]!=null){
            
            //Posicion donde habra la coincidencia
            int posc=-1;
            
            //Se muestra por consola la iteracion actual
            System.out.println("Iteracion: " + auxiliar);
            
            //Buscamos donde hay coincidencia
            
            for (int y=0; y<tamVentana;y++){
                //Si existen mas de 2 coincidencias tomaremos la de mas a la izquierda
                if(Ventana[y].equals(Buffer[0])){
                    if(posc==-1){
                        posc=y;
                    }
                    
                    System.out.println("Coinicidencia en: "+(y));
                }
            }
            
            //Si no se encontraron coincidencias...
            if (posc==-1){
                System.out.println("No hay coincidencia");
            }
            
            //Longitud de la coincidencia
            int largo=1;
            
            //Miramos las coicidencias
            if (posc!=-1 && (posc+largo)<tamVentana){
                
                //Miramos si tiene mas coincidencias
                while (Ventana[posc+largo].equals(Buffer[largo])){
                    largo++;
                    System.out.println("El largo por ahora es de: " + largo);
                    
                    //En caso de que se llegue al tope debemos salir
                    if (largo==tamBuffer || (posc+largo)==tamVentana){
                        break;
                    }
                    
                }
                //Se le añade 1 de la primera coincidencia que no se habia sumado
                largo ++;
            }
            
            if(posc==tamVentana-1){
                largo++;
            }
            
            //Se imprime el estado de la variable largo
            System.out.println("Largo Coincidencia Actual: " + largo);
            
            //Reescribimos Ventana la primera parte
            for (int u=0; u<tamVentana-largo; u++){
                Ventana[u]=Ventana[u+largo];
            }
            
            int c=0;
            //Reescribimos ventana con lo del buffer
            for (int y=tamVentana-largo; y<tamVentana;y++){
                
                //En caso de que toque cambiar todo el buffer se le asigna la primera posicion de letras
                if (c == Buffer.length && recorr < letras.length){
                    Ventana[y]=letras[recorr];
                    recorr++;
                }
                //Si todavia queda en el buffer algo se mueve
                else if (c == Buffer.length) {
                    Ventana[y] = null;
                
                } else {
                    Ventana[y] = Buffer[c];
                    c++;
                }
            }
            //Reescribimos buffer la primera parte
            for (int u=0; u<tamBuffer-largo; u++){
                Buffer[u]=Buffer[u+largo];
            }
            
            //Miramos tomo o no todo el buffer mediante la variable
            int dif=tamBuffer-largo;
            if (dif<0) {
                dif=0;
            }

            //Reescribimos buffer con la palabra
            for (int y=dif; y<tamBuffer;y++){
                System.out.println("Recorrido: " + recorr);
                
                if(recorr<letras.length){
                    Buffer[y]=letras[recorr];
                    recorr++;
                } else{
                    System.out.println("PALABRA TERMINADA");
                    Buffer[y]=null;
                }
                
            }
            
            //Se definen variables para mostrar los resultados actuales
            String ven = "";
            String buf = "";
            
            //Se guarda el estado actual del buffer para mostrarlo
            for (int t = 0; t < tamBuffer; t++){
                
                if (Buffer[t] == null){
                    buf = buf + "_";
                } else {
                    buf = buf + Buffer[t];
                }
                
            } 
            
            //Se guarda el estado actual de la ventana para mostrarlo
            for (int t = 0; t < tamVentana; t++) {
                
                
                if (Ventana[t] == null){
                    ven = ven + "_";
                } else {
                    ven = ven + Ventana[t];
                }
                
            }
            
            //Se imprimen los resultados actuales
            System.out.println("Ventana: " + buf);
            System.out.println("Buffer: " + buf);
                
            //Vamos añadiendo a la cadena de salida el conjunto
            String possalida="";
            String tripleta;
            
            if (posc==-1)
                possalida="0";
            else
                possalida=Integer.toString(tamVentana-posc);
            
            boolean escribio = false;
            
            //Si la ventana está vacia
            if (Ventana[tamVentana-1]==null){
                salida=salida+"<"+possalida+","+(largo-1)+","+"EOF"+">"+"\n";
                tripleta = "<"+possalida+","+(largo-1)+","+"EOF"+">";
                acabo = true;
                escribio = true;
            }
            
            //Si no está vacia
            else {
                salida=salida+"<"+possalida+","+(largo-1)+","+Ventana[tamVentana-1]+">"+"\n";
                tripleta = "<"+possalida+","+(largo-1)+","+Ventana[tamVentana-1]+">";
                escribio = true;
            }
            
            //Se muestra la tripleta actual
            System.out.println("Tripleta actual: " + "<"+possalida+","+(largo-1)+","+Ventana[tamVentana-1]+">"+"\n");
            
            //Si el buffer esta vacio
            if (Buffer[0] == null && acabo == false) {

                if (escribio == true) {
                    salida = salida + "<" + 0 + "," + 0 + "," + "EOF" + ">";
                } else {
                    salida = salida + "<" + possalida + "," + (largo - 1) + "," + "EOF" + ">";
                    tripleta = "<" + possalida + "," + (largo - 1) + "," + "EOF" + ">";
                    acabo = true;
                }
                
            }
            
            //Se muestran los resultados parciales en una ventana aparte
            JOptionPane.showMessageDialog(null,"Formato: V|B|T: \n \n " + ven
                    + " | " + buf + " | " + tripleta +
                    "\n \n Salida actual: \n" + salida, "Codificacion por LZ77: Iteracion " + (auxiliar + 1), JOptionPane.INFORMATION_MESSAGE);
            
            //Se aumenta el valor de la variable que cuenta las iteraciones
            auxiliar ++;

        }
        
        //Se muestra la salida final por consola
        System.out.println("SALIDA");
        System.out.println(salida);
        
        //Se retorna la salida final para ser impresa
        return salida;
        
    }
    
    //Metodo que decodifica desde un archivo de texto
    public String decodificar(ArrayList<String> tripletas) {

        //Se define el texto donde se guardará la palabra decodificada 
        String resultado = "";
        
        //Comprobar todas las tripletas
        for (int i = 0; i < tripletas.size(); i++) {
            
            //Salvar la tripleta escrita de manera visual para el usuario
            String auxTripleta = tripletas.get(i);
            
            //Limpiar la tripleta y solo dejar los datos relevantes
            tripletas.set(i, tripletas.get(i).replaceAll("<", ""));
            tripletas.set(i, tripletas.get(i).replaceAll(">", ""));
            //tripletas.set(i, tripletas.get(i).replaceAll(",", ""));
            
            //Mostrar los datos limpios de la tripleta
            System.out.println("Datos de la tripleta: " + tripletas.get(i));
            
            //Separar la tripleta limpia en un arreglo
            String[] tripletaActual = tripletas.get(i).split(",");
            
            System.out.print("Datos limpios de la tripleta: ");
            for (int k=0; k<tripletaActual.length;k++){
                System.out.print(tripletaActual[k]);
            }
            System.out.println("");
            
            //Estructura del arreglo separado
            //String[0] = 0 "Posicion de la coincidencia"
            //String[1] = 0 "Tamaño de la coincidencia"
            //String[2] = R "La siguiente letra"
            
            //Si no se encontró una coincidencia (valor 0)
            if (tripletaActual[0].equals("0")) {

                //Si se detecta un EOF que indica el final de la cadena
                if (tripletaActual[2].equals("EOF")) {
                    System.out.println("Final de la cadena...");
                } else {
                    //Si no, sumar la letra sin coincidencia a la cadena actual
                    resultado = resultado + tripletaActual[2];

                    //Mostrar informacion parcial
                    JOptionPane.showMessageDialog(null, "Tripleta: " + auxTripleta + "\n"
                            + "No se encontró coincidencia... \n"
                            + "Resultado parcial: " + resultado, "Decodificacion por LZ77: Iteracion " + (i + 1), JOptionPane.INFORMATION_MESSAGE);
                }
                //En caso de que se encuentre una coincidencia (valor distinto de 0)    
            } else {
                                
                //Imprimir por consola la informacion de la coincidencia
                System.out.println("");
                System.out.println("Se encontró una coincidencia");
                System.out.println("Longitud actual antes de la coincidencia: " + resultado.length());
                System.out.println("Posicion de la coincidencia original: " + Integer.parseInt(tripletaActual[0]));
                
                //Calcular la posicion de la coincidencia (el arreglo se lee de atras hacia adelante)
                int posicionCoincidencia = resultado.length() - (Integer.parseInt(tripletaActual[0]));
                
                //Seguir imprimiendo por consola la informacion de la coincidencia
                System.out.println("Posicion de la coincidencia relativa: " + posicionCoincidencia);
                System.out.println("");
                
                //Tomar una copia del texto segun la coincidencia y el temaño de la misma
                String aux = resultado.substring(posicionCoincidencia, posicionCoincidencia + Integer.parseInt(tripletaActual[1]));
                
                //Si se detecta una coincidencia al final de las tripletas
                if (tripletaActual[2].equals("EOF")) {
                    //Solo se suma la coincidencia sin el EOF
                    resultado = resultado + aux;
                } else {
                    //Se suma la coincidencia mas la letra de la tripleta
                    resultado = resultado + aux + tripletaActual[2];
                }
                
                //Mostrar informacion parcial
                JOptionPane.showMessageDialog(null, "Tripleta: " + auxTripleta + "\n"
                        + "Se encontró una coincidencia... \n"
                        + "\n Posicion de la coincidencia original: " + Integer.parseInt(tripletaActual[0])
                        + "\n Longitud de la coincidencia: " + Integer.parseInt(tripletaActual[1])
                        + "\n Posicion de la coincidencia relativa: " + posicionCoincidencia
                        + "\n Subcadena de la coincidencia: " + aux
                        + "\n Resultado parcial: " + resultado, "Decodificacion por LZ77: Iteracion " + (i + 1), JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        //Mostrar el resultado de la palabra decodificada
        System.out.println("Proceso de decodificacion finalizado...");
        System.out.println("El resultado fue: " + resultado);
        
        //Devolver el resultado para imprimirlo
        return resultado;
    }
    
}

