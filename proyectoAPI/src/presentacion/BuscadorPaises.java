package presentacion;

import logica.DatosPaisesDTO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class BuscadorPaises {
    private JPanel ComponentPane;
    private JTextField nomArchivo;
    private JComboBox selectTipoArchivo;
    private JButton procesarB;
    private JButton generarArchivoB;
    private JTextField JTPaises;
    private JCheckBox guardarBase;
    private JLabel nomPais;
    private JLabel nomCapital;
    private JLabel poblacion;
    private JLabel gini;
    private JLabel alertMensajes;
    private JPanel InfoPais;
    private JComboBox selecAno;



    public BuscadorPaises(){
        guardarBase.setEnabled(false);
        selectTipoArchivo.addItem(".txt");
        selectTipoArchivo.addItem(".text");
        selectTipoArchivo.addItem(".data");
        selecAno.addItem("2019");
        selecAno.addItem("2006");
        selecAno.addItem("2012");
        selecAno.addItem("2014");
        selecAno.addItem("2017");

        generarArchivoB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosPaisesDTO datosPaisesDTO = objetoDatosPaisDTO();
                datosPaisesDTO.crearFicheros(nomArchivo, selectTipoArchivo);

            }
        });
        procesarB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosPaisesDTO datosPaisesDTO = objetoDatosPaisDTO();
                if(JTPaises.getText().equals("")){
                    alertMensajes.setText("No se permiten campos vacios!");
                }
                else {
                    try{
                        String[] datos = datosPaisesDTO.getPaisInfo(JTPaises.getText(), (String) selecAno.getSelectedItem());
                        nomPais.setText(datos[0]);
                        gini.setText(datos[1]);
                        nomCapital.setText(datos[2]);
                        poblacion.setText(datos[3]);
                        alertMensajes.setText("Datos procesados con exito!");
                        guardarBase.setEnabled(true);
                        JTPaises.setText("");
                        guardarBase.setSelected(false);
                    }catch (IOException error){
                        error.printStackTrace();
                    }
                }
            }
        });
        guardarBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosPaisesDTO datosPaisesDTO = objetoDatosPaisDTO();
                if(guardarBase.isSelected()){
                    if (datosPaisesDTO.enviarData()) {
                        alertMensajes.setText("Datos enviados con exito!");
                        guardarBase.setEnabled(false);
                        nomPais.setText("");
                        gini.setText("");
                        nomCapital.setText("");
                        poblacion.setText("");
                        logSistema log = new logSistema();
                        log.conexion.setVisible(true);
                    }

                }
            }
        });


    }

    public JPanel getComponentPane(){
        return ComponentPane;
    }

    public DatosPaisesDTO objetoDatosPaisDTO(){
        DatosPaisesDTO datosPaisesDTO = new DatosPaisesDTO(
                nomPais.getText(),
                nomCapital.getText(),
                poblacion.getText(),
                gini.getText(),
                alertMensajes
        );
        return datosPaisesDTO;
    }
}
