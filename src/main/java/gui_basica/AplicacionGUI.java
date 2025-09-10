package gui_basica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class AplicacionGUI extends JFrame {

    // Lista para guardar los datos ingresados por el usuario
    private ArrayList<String> datos;

    // Componentes de la interfaz
    private JTextField campoTexto;
    private JButton botonAgregar;
    private JButton botonLimpiar;
    private DefaultListModel<String> modeloLista;  // Debe ser inicializado antes
    private JList<String> listaDatos;
    
    private static final String FILE_NAME = "datosGuardados.txt";  // Nombre del archivo donde se guardarán los datos

    // Constructor principal donde armamos toda la ventana
    public AplicacionGUI() {
        // Inicializamos la lista que guardará los datos
        datos = new ArrayList<>();

        // Inicializamos el DefaultListModel (modeloLista debe ser inicializado aquí)
        modeloLista = new DefaultListModel<>();  // Inicializar aquí

        cargarDatosDesdeArchivo(); // Cargamos los datos desde el archivo
        
        // Configuración básica de la ventana
        setTitle("Registro Visual de Datos con Archivo"); // Título descriptivo
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Panel principal con un poco de espacio entre componentes
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior con etiqueta, campo de texto y botones
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelEntrada.setBackground(new Color(220, 220, 220)); // Color ligero de fondo

        // Etiqueta descriptiva
        JLabel etiqueta = new JLabel("Escribe un dato y agrégalo:");
        panelEntrada.add(etiqueta);

        // Campo de texto donde el usuario escribe
        campoTexto = new JTextField(20);
        panelEntrada.add(campoTexto);

        // Botón Agregar
        botonAgregar = new JButton("Agregar");
        botonAgregar.setBackground(new Color(135, 206, 250));
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarDato();
            }
        });
        panelEntrada.add(botonAgregar);

        // Botón Limpiar
        botonLimpiar = new JButton("Limpiar");
        botonLimpiar.setBackground(new Color(250, 128, 114));
        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarDatos();
            }
        });
        panelEntrada.add(botonLimpiar);

        // Lista para mostrar los datos ingresados
        listaDatos = new JList<>(modeloLista);  // Usar modeloLista ya inicializado
        listaDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(listaDatos);

        // Añadimos los paneles al panel principal
        panelPrincipal.add(panelEntrada, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        // Añadimos el panel principal a la ventana
        add(panelPrincipal);

        // Hacemos visible la ventana
        setVisible(true);
    }

    // Método que agrega un dato a la lista y lo guarda en el archivo
    private void agregarDato() {
        String texto = campoTexto.getText().trim();
        if (!texto.isEmpty()) {
            datos.add(texto); // Guardamos en la lista interna
            modeloLista.addElement(texto); // Mostramos en la lista visual
            campoTexto.setText(""); // Limpiamos el campo para el siguiente dato
            guardarDatosEnArchivo(); // Guardamos los datos en el archivo automáticamente
        } else {
            JOptionPane.showMessageDialog(this,
                    "No puedes agregar un dato vacío. Por favor, escribe algo.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Método que limpia todos los datos y los elimina del archivo
    private void limpiarDatos() {
        datos.clear();       // Limpiamos la lista interna
        modeloLista.clear(); // Limpiamos la lista visual
        campoTexto.setText(""); // Limpiamos el campo de texto
        guardarDatosEnArchivo(); // Guardamos la lista vacía en el archivo
    }

    // Método para cargar los datos desde el archivo
    private void cargarDatosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                datos.add(line);  // Añadir cada línea del archivo a la lista
                modeloLista.addElement(line);  // Mostrarlo en la lista visual
            }
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo o el archivo está vacío.");
        }
    }

    // Método para guardar los datos en el archivo
    private void guardarDatosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String dato : datos) {
                writer.write(dato);
                writer.newLine();  // Escribir cada dato en una nueva línea
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error al guardar los datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método principal para arrancar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AplicacionGUI();  // Llama al constructor de la clase
            }
        });
    }
}
