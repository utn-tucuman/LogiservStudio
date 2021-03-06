package application.view.compra.cruds;

import application.comunes.Alerta;
import application.model.compra.CategoriaArticulo;
import application.repository.info.CategoriaArticuloRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class CategoriaArticuloEditController {
    @FXML
    private Label titulo;
    @FXML
    private TextField nombreCategoriaField;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    private Stage dialogStage;
    private boolean isNew;
    private CategoriaArticulo categoriaArticulo;
    private boolean okClicked = false;
    private CategoriaArticuloRepository reporsitory= new CategoriaArticuloRepository();

    public void setIsNew(boolean bandera){
        this.isNew = bandera;
    }
    @FXML
    private	void initialize(){}

    @FXML
    public void handleOk(){

        if (isInputValid()){
            categoriaArticulo.setNombre(nombreCategoriaField.getText());
            if (isNew){
                reporsitory.save(categoriaArticulo);
            }else {
                reporsitory.update(categoriaArticulo);
            }
            okClicked=true;
            dialogStage.close();
        }

    }
    @FXML
    public void handleCancel(){
        dialogStage.close();
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCategoriaArticulo(CategoriaArticulo categoriaArticulo){
        this.categoriaArticulo = categoriaArticulo;
        nombreCategoriaField.setText(categoriaArticulo.getNombre());
    }
    public boolean isOkClicked(){
        return okClicked;
    }
    private boolean isInputValid() {
        String errorMessage = "";

        if (nombreCategoriaField.getText() == null || nombreCategoriaField.getText().length() == 0) {
            errorMessage += "No valid Category name!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alerta.alertaError("Datos inválidos", errorMessage);
            return false;
        }
    }
}
