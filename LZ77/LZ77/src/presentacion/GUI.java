/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.Color;
import lz77.LZ77;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author mateo
 */
public class GUI implements ActionListener{
    
    //Declaración de variables globales
    JFrame ventana;
    JTextField tfEntrada;
    JTextArea taSalida;
    JButton btCodificar, btDecodificar;
    
    //Contador de archivos
    int archivoActual = 1;
    
    //Variable del codificador y decodificador LZ77
    LZ77 lz;

    //Constructor de la clase
    public GUI() {
        //Creacion del codificador y decodificador LZ77
        lz =  new LZ77();
        
        //Creación de la ventana
        ventana = new JFrame();
        
        //Añadir los paneles
        ventana.add(paTitulo());
        ventana.add(paIngreso());
        ventana.add(paSalida());
        ventana.add(paBotonera());
        ventana.add(paInformacion());
        
        //Propiedades de la ventana
        ventana.setSize(800, 545);
        ventana.setTitle("Codificación LZ77");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(null);
        ventana.setVisible(true);
        
    }
    
    //Panel que contiene el titulo del programa
    public JPanel paTitulo(){
        //Definición del panel y sus priopiedades
        JPanel pa = new JPanel();
        pa.setBounds(0, 0, 800,50);
        pa.setBackground(new java.awt.Color(204, 166, 166));
        pa.setFont(new java.awt.Font("Cambria", 2, 11));
        
        //Elementos del panel
        JLabel lbTitulo = new JLabel("Codificacion y Decodificación con LZ77", SwingConstants.CENTER);
        lbTitulo.setBounds(0, 0, 80, 50);
        lbTitulo.setVisible(true);
        lbTitulo.setFont(new java.awt.Font("Cambria", 0, 29));
        pa.add(lbTitulo);
        
        //Retornar el panel a la ventana
        pa.setVisible(true);
        return pa;
    }
    
    //Panel que contiene el area de texto para el ingreso de datos
    public JPanel paIngreso(){
        //Definición del panel y sus priopiedades
        JPanel pa = new JPanel();
        pa.setBounds(0, 50, 800,100);
        pa.setLayout(null);
        pa.setBackground(Color.LIGHT_GRAY);
                
        //Elementos del panel
        JLabel lbInformacion = new JLabel("Codificar: ");
        lbInformacion.setFont(new java.awt.Font("Cambria", 0, 20));
        lbInformacion.setForeground(Color.DARK_GRAY);
        lbInformacion.setBounds(90, 35, 100, 20);
        lbInformacion.setVisible(true);
        pa.add(lbInformacion);
        
        tfEntrada = new JTextField();
        tfEntrada.setFont(new java.awt.Font("Cambria", 0, 20));
        tfEntrada.setForeground(Color.DARK_GRAY);
        tfEntrada.setBounds(190, 30, 500, 30);
        tfEntrada.setVisible(true);
        pa.add(tfEntrada);
        
        //Retornar el panel a la ventana
        pa.setVisible(true);
        return pa;
    }
    
    //Panel que contiene el area de salida de resultados del programa
    public JPanel paSalida(){
        //Definición del panel y sus priopiedades
        JPanel pa = new JPanel();
        pa.setBounds(0, 150, 400,320);
        pa.setLayout(null);
        
        //Elementos del panel
        taSalida = new JTextArea();
        taSalida.setEditable(false);
        taSalida.setFont(new java.awt.Font("Cambria", 0, 15));
        taSalida.setBorder(BorderFactory.createTitledBorder("Salida"));
        taSalida.setForeground(Color.DARK_GRAY);
        taSalida.setBounds(0, 0, 400, 320);
        taSalida.setVisible(true);
        
        JScrollPane scroll = new JScrollPane (taSalida);
        scroll.setBounds(0,0,400,320);
        scroll.setVerticalScrollBarPolicy( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pa.add(scroll);
        
        //Retornar el panel a la ventana
        pa.setVisible(true);
        return pa;
    }
    
    //Panel que contiene los botones para ejecutar el programa
    public JPanel paBotonera(){
        //Definición del panel y sus priopiedades
        JPanel pa = new JPanel();
        pa.setBounds(400, 150, 400,320);
        pa.setBackground(Color.DARK_GRAY);
        pa.setLayout(null);
        
        //Elementos del panel
        btCodificar = new JButton("Codificar");
        btCodificar.setBounds(80, 70, 220, 60);
        btCodificar.setFont(new java.awt.Font("Cambria", 2, 20));
        btCodificar.setForeground(Color.white);
        btCodificar.setBorder(new LineBorder(Color.LIGHT_GRAY));
        btCodificar.setBackground(Color.GRAY);
        btCodificar.setVisible(true);
        btCodificar.addActionListener(this);
        pa.add(btCodificar);
        
        //Elementos del panel
        btDecodificar = new JButton();
        btDecodificar.setText("<html>Decodificar desde un"
                + "<br> &nbsp &nbsp &nbsp &nbsp &nbsp "
                + "archivo txt</html>");
        btDecodificar.setBounds(80, 180, 220, 60);
        btDecodificar.setFont(new java.awt.Font("Cambria", 2, 20));
        btDecodificar.setForeground(Color.white);
        btDecodificar.setBorder(new LineBorder(Color.LIGHT_GRAY));
        btDecodificar.setBackground(Color.GRAY);
        btDecodificar.setVisible(true);
        btDecodificar.addActionListener(this);
        pa.add(btDecodificar);
        
        //Retornar el panel a la ventana
        pa.setVisible(true);
        return pa;
    }
    
    //Panel que contiene informacion de los creadores
    public JPanel paInformacion(){
        //Definición del panel y sus priopiedades
        JPanel pa = new JPanel();
        pa.setBounds(0, 470, 800,40);
        pa.setBackground(new java.awt.Color(142, 142, 142));
        pa.setFont(new java.awt.Font("Cambria", 2, 11));
        
        //Elementos del panel
        JLabel lbInformacion = new JLabel("Kevin A. Borda, Mateo Yate G. - UDistrital - 2020-1");
        lbInformacion.setHorizontalAlignment(SwingConstants.CENTER);
        lbInformacion.setFont(new java.awt.Font("Cambria", 0, 20));
        lbInformacion.setForeground(Color.white);
        lbInformacion.setBounds(0, 0, 600, 40);
        lbInformacion.setVisible(true);
        pa.add(lbInformacion);
        
        //Retornar el panel a la ventana
        pa.setVisible(true);
        return pa;
    }
    
    //Metodo para ejecutar la codificacion con un ejemplo predefinido
    public void ejemploDeCodificacion(){
        
        //Se limpia el area de resultados
        taSalida.setText("");
        
        //Se muestra al usuario el ejemplo a codificar
        JOptionPane.showMessageDialog(null, "Ejemplo... \n Texto a codificar: " + 
                "ABBABCABBBBC \n Tamaño de la ventana: 5 \n Tamaño del buffer: 5");
        
        //Se llama a la codificacion con el ejemplo mencionado anteriormente
        //String resultado = lz.codificar("ABBABCABBBBC", 5, 5);
        String resultado = lz.codificar("SALSA SALADA", 6, 4);
        
        //Se imprimen las tripletas resultantes
        taSalida.setText(resultado);

        //Se exportan las tripletas a una archivo de texto
        try {
            //Ruta del archivo donde se exportará
            String ruta = "Codificacion.txt";

            //Se crea un objeto archivo 
            File file = new File(ruta);

            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }

            /*Se crean los objetos necesarios 
                    para escribir en el archivo*/
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            //Se limpia el archivo completamente
            bw.write("");

            //Se escriben los resultados obtenidos
            bw.write(resultado);

            //Se informa al usuario que el proceso fue exitoso
            JOptionPane.showMessageDialog(null, "Se ha exportado correctamente la codificación al archivo: \n"
                    + " Codificacion.txt");

            //Se cierra el archivo
            bw.close();

        } catch (Exception ex) {
            //Se captura la excepcion del archivo
            ex.printStackTrace();

            //Se muestra al usuario el error
            JOptionPane.showMessageDialog(null, "Ocurrio un error al exportar el resultado... \n"
                    + " Error: " + ex.getMessage());
        }
    }
    
    //Metodo para capturar los datos y codificar los mismos
    public void procesoCodificacion(){

        //Texto de Ejemplo: ABBABCABBBBC 5 5
        System.out.println("Texto a codificar: " + tfEntrada.getText());

        //Ingresar la ventana y el buffer de manera manual
        String tamVentana = JOptionPane.showInputDialog(null, "Tamaño de la ventana:");
        
        //Condicion por si no hay nada ingresado en la ventana o si es un valor invalido
        while (tamVentana.length() == 0 || Integer.parseInt(tamVentana) < 1) {
            tamVentana = JOptionPane.showInputDialog(null, "Tamaño de la ventana:");
        }
        
        String tamBuffer = JOptionPane.showInputDialog(null, "Tamaño del buffer:");
        while (tamBuffer.length() == 0 || Integer.parseInt(tamBuffer) > Integer.parseInt(tamVentana) || Integer.parseInt(tamBuffer) > tfEntrada.getText().length()) {
            tamBuffer = JOptionPane.showInputDialog(null, "Tamaño del buffer:");
        }

        //Se limpiar el area de la salida
        taSalida.setText("");

        //Se codifica la entrada
        String resultado = lz.codificar(tfEntrada.getText(), Integer.parseInt(tamVentana), Integer.parseInt(tamBuffer));

        //Se imprimen las tripletas resultantes
        taSalida.setText(resultado);

        //Se exportan las tripletas a una archivo de texto
        try {
            //Ruta del archivo donde se exportará
            String ruta = "Codificacion_" + archivoActual + ".txt";

            //Se crea un objeto archivo 
            File file = new File(ruta);

            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }

            /*Se crean los objetos necesarios 
                    para escribir en el archivo*/
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            //Se limpia el archivo completamente
            bw.write("");

            //Se escriben los resultados obtenidos
            bw.write(resultado);

            //Se informa al usuario que el proceso fue exitoso
            JOptionPane.showMessageDialog(null, "Se ha exportado correctamente la codificación al archivo: \n"
                    + " " + ruta);

            //Se cierra el archivo
            bw.close();
            
            //Aumentar el contador del archivo actual
            archivoActual ++;

        } catch (Exception ex) {
            //Se captura la excepcion del archivo
            ex.printStackTrace();

            //Se muestra al usuario el error
            JOptionPane.showMessageDialog(null, "Ocurrio un error al exportar el resultado... \n"
                    + " Error: " + ex.getMessage());
        }
        
    }
    
    //Metodo para capturar el archivo y decodificar las tripletas
    public void procesoDecodificacion(){
        //Se limpia el area de salida
        taSalida.setText("");

        //Se define una variable para manejar una tripleta mal escrita
        boolean tripletaInvalida = false;

        //Se declara un arrayList para guardar los datos leidos
        ArrayList<String> ingreso = new ArrayList<String>();

        //Se abre el archivo en una nueva variable
        File f = abrirArchivo();

        //Si ocurrio un error abriendolo, se sigue llamando al FileChoooser
        while (f == null) {
            abrirArchivo();
        }

        //Se guardan las tripletas recibidas en un arraylist
        try {
            //Se declara un scanner para el archivo en cuestion
            Scanner input = new Scanner(f);

            //Contador de lineas
            int linea = 0;

            //Mientras que hayan lineas por leer
            while (input.hasNextLine()) {
                
                //Se lee la siguiente linea
                String line = input.nextLine();

                //Se añade al arreglo obtenido
                ingreso.add(line);

                //Se verifica si la tripleta esta mal escrita
                if (comprobarTripleta(line) == false) {
                    System.out.println("La tripleta " + linea + " se encuentra mal escrita");
                    JOptionPane.showMessageDialog(null, "La tripleta " + linea + " se encuentra mal escrita");
                    tripletaInvalida = true;
                    break;
                }

                //Se muestra la linea actual
                System.out.println("Linea: " + line);
                linea++;

            }
            //Se cierra el Scanner
            input.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en el analisis del archivo... \n "
                    + ex.getMessage());
            System.out.println("Error: " + ex.getMessage());
        }

        //Condicion por si hay una tripleta invalida
        if (tripletaInvalida == true) {
            JOptionPane.showMessageDialog(null, "Proceso finalizado. \n Hay una tripleta mal escrita. \n "
                    + "No es posible decodificar", "Decodificacion por LZ77: Finalizado", JOptionPane.INFORMATION_MESSAGE);
            taSalida.setText("Tripleta invalida");

        } else {
            //Se llama al metodo de decodificar LZ77
            String resultadoDecodificacion = lz.decodificar(ingreso);

            //Se muestra el resultado final en una ventana emergente y en el aplicativo
            taSalida.setText(resultadoDecodificacion);
            JOptionPane.showMessageDialog(null, "Proceso finalizado. \n La palabra decodificada es: "
                    + resultadoDecodificacion, "Decodificacion por LZ77: Finalizado", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    
    //Desplegar la apertura de archivos
    private File abrirArchivo() {
        
        //Se crea un JFileChooser vacio
        JFileChooser fileChooser = null;
        
        //Se guarda la apariencia actual de este
        LookAndFeel previousLF = UIManager.getLookAndFeel();

        try {
            //Se toma la apariencia del File Chooser del Sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            //Se crea y abre el File Chooser
            fileChooser = new JFileChooser(".");
            fileChooser.showOpenDialog(fileChooser);
            
            //Se setea la apariencia original de nuevo
            UIManager.setLookAndFeel(previousLF);
            
        } catch (IllegalAccessException | UnsupportedLookAndFeelException | InstantiationException | ClassNotFoundException e) {
            
            //Se muestra si hay algun problema con la apertura y el estilo del FileChooser
            JOptionPane.showMessageDialog(null, "Ocurrio un error con el FileChooser... \n Error: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
            
        }

        try {
            //Se abre el archivo seleccionado y se retorna
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            taSalida.setText("Ruta: " + ruta);
            File f = new File(ruta);
            return f;
            
        } catch (NullPointerException e) {
            //Se muestra por si se cancelo el FileChooser
            JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún fichero");
            System.out.println("No se ha seleccionado ningún fichero");
            
            
        } catch (Exception e) {
            //Por si ocurrió algun tipo de error
            JOptionPane.showMessageDialog(null, "Ocurrio un error... \n Error: " + e.getMessage() );
            System.out.println("Error: " + e.getMessage());
        }
        
        //Por si ocurrió algun tipo de error
        return null;
    }
    
    //Metodo para comprobar la correcta escritura de una tripleta
    public boolean comprobarTripleta(String tripleta) {
        Pattern pat = Pattern.compile("^[<][\\d]{1,},[\\d]{1,},[a-zA-ZáéíóúÁÉÍÓÚ]{0,}[\\s]{0,}[\\d]{0,}[>]$");
        Matcher mat = pat.matcher(tripleta);
        if (mat.matches()) {
            System.out.println("Tripleta " + tripleta + "valida");
            return true;
        } else {
            System.out.println("La linea " + tripleta + "no es una tripleta valida");
            return false;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        //Si el boton de codificar fue presionado...
        if (e.getSource() == btCodificar){
            System.out.println("Boton de Codificar fue presionado...");
            
            //Verificar si el campo de texto esta vacio
            if (tfEntrada.getText().length()==0){
                
                //Se informa que el campo de texto se encuentra vacio
                JOptionPane.showMessageDialog(null, "El texto a codificar está vacio");
                
                //Ejecución de un ejemplo sin el textarea
                //ejemploDeCodificacion();
                
            } else {
                
                //Se llama al metodo que gestiona el ingreso de datos
                procesoCodificacion();
                
            }
            
        }
        
        if (e.getSource() == btDecodificar){
            System.out.println("Boton de decodificar fue presionado...");
            
            //Limpiar el area de texto
            tfEntrada.setText("");
            
            //Se llama al metodo que gestiona el ingreso de datos de decodificacion
            procesoDecodificacion();
            
        }
        
    }
    
}
