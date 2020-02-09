package notepad;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FXMLCompileRun extends BorderPane {
    private Stage currentStage;
    private Boolean currentFileIsNew;
    private File currentFile;
    private Boolean textAreaHasChanged;
    private Boolean fileIsHighLevel;
    private String lastCopiedOrCuttedString;
    private Boolean stringIsCopiedNotCutted;

    protected final MenuBar menuBar;
    protected final Menu fileMenu;
    protected final MenuItem newMenuItem;
    protected final MenuItem openLLMenuItem;
    protected final MenuItem openHLMenuItem;
    protected final MenuItem saveLLMenuItem;
    protected final MenuItem saveHLMenuItem;
    protected final MenuItem exitMenuItem;
    protected final MenuItem compileMenuItem;
    protected final MenuItem runMenuItem;
    protected final Menu editMenu;
    protected final MenuItem cutMenuItem;
    protected final MenuItem copyMenuItem;
    protected final MenuItem pasteMenuItem;
    protected final MenuItem deleteMenuItem;
    protected final MenuItem selectAllMenuItem;
    protected final Menu helpMenu;
    protected final MenuItem aboutMenuItem;
    protected final TextArea textArea;

    public FXMLCompileRun(Stage stage) {
        currentStage = stage;
        currentFileIsNew = true;
        textAreaHasChanged = false;
        fileIsHighLevel = false;
        lastCopiedOrCuttedString = "";
        stringIsCopiedNotCutted = false;
        
        menuBar = new MenuBar();
        fileMenu = new Menu();
        newMenuItem = new MenuItem();
        openLLMenuItem = new MenuItem();
        openHLMenuItem = new MenuItem();
        saveLLMenuItem = new MenuItem();
        saveHLMenuItem = new MenuItem();
        exitMenuItem = new MenuItem();
        
        compileMenuItem = new MenuItem();
        runMenuItem = new MenuItem();
        
        editMenu = new Menu();
        cutMenuItem = new MenuItem();
        copyMenuItem = new MenuItem();
        pasteMenuItem = new MenuItem();
        deleteMenuItem = new MenuItem();
        selectAllMenuItem = new MenuItem();
        helpMenu = new Menu();
        aboutMenuItem = new MenuItem();
        textArea = new TextArea();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        BorderPane.setAlignment(menuBar, javafx.geometry.Pos.CENTER);

        fileMenu.setMnemonicParsing(false);
        fileMenu.setText("File");

        newMenuItem.setMnemonicParsing(false);
        newMenuItem.setText("New");
        newMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));

        openLLMenuItem.setMnemonicParsing(false);
        openLLMenuItem.setText("Open (low level)");
        openLLMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));

        openHLMenuItem.setMnemonicParsing(false);
        openHLMenuItem.setText("Open (high level)  ");
        openHLMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHIFT_DOWN));

        saveLLMenuItem.setMnemonicParsing(false);
        saveLLMenuItem.setText("Save (low level)");
        saveLLMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        saveHLMenuItem.setMnemonicParsing(false);
        saveHLMenuItem.setText("Save (high level)");
        saveHLMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN));

        exitMenuItem.setMnemonicParsing(false);
        exitMenuItem.setText("Exit");
        exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));

        compileMenuItem.setMnemonicParsing(false);
        compileMenuItem.setText("Compile");
        
        runMenuItem.setMnemonicParsing(false);
        runMenuItem.setText("Run");
        
        editMenu.setMnemonicParsing(false);
        editMenu.setText("Edit");

        cutMenuItem.setMnemonicParsing(false);
        cutMenuItem.setText("Cut");
        cutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));

        copyMenuItem.setMnemonicParsing(false);
        copyMenuItem.setText("Copy");
        copyMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));

        pasteMenuItem.setMnemonicParsing(false);
        pasteMenuItem.setText("Paste");
        pasteMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));

        deleteMenuItem.setMnemonicParsing(false);
        deleteMenuItem.setText("Delete");
        deleteMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));

        selectAllMenuItem.setMnemonicParsing(false);
        selectAllMenuItem.setText("Select All");
        selectAllMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));

        helpMenu.setMnemonicParsing(false);
        helpMenu.setText("Help");

        aboutMenuItem.setMnemonicParsing(false);
        aboutMenuItem.setText("About");
        aboutMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.H, KeyCombination.CONTROL_DOWN));
        setTop(menuBar);

        BorderPane.setAlignment(textArea, javafx.geometry.Pos.CENTER);
        textArea.setPrefHeight(200.0);
        textArea.setPrefWidth(200.0);
        setCenter(textArea);

        fileMenu.getItems().add(newMenuItem);
        fileMenu.getItems().add(openLLMenuItem);
        fileMenu.getItems().add(openHLMenuItem);
        fileMenu.getItems().add(saveLLMenuItem);
        fileMenu.getItems().add(saveHLMenuItem);
        fileMenu.getItems().add(exitMenuItem);
        fileMenu.getItems().add(compileMenuItem);
        fileMenu.getItems().add(runMenuItem);
        menuBar.getMenus().add(fileMenu);
        editMenu.getItems().add(cutMenuItem);
        editMenu.getItems().add(copyMenuItem);
        editMenu.getItems().add(pasteMenuItem);
        editMenu.getItems().add(deleteMenuItem);
        editMenu.getItems().add(selectAllMenuItem);
        menuBar.getMenus().add(editMenu);
        helpMenu.getItems().add(aboutMenuItem);
        menuBar.getMenus().add(helpMenu);
                
        currentStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent event) {
               checkCurrentlyOpenedFile(); 
            }
        });
        
        textArea.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                textAreaHasChanged = true;
            }  
        });
        compileMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                try{
                    Runtime.getRuntime().exec("javac "+currentFile.getAbsolutePath());
                }catch(IOException ex){
                    ex.printStackTrace();
                }
            }
        
        });
        newMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                checkCurrentlyOpenedFile();
                
                textArea.setText("");
                currentFileIsNew = true;
                
            }
        });
        
        openLLMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                checkCurrentlyOpenedFile();
                FileChooser fileChooser = new FileChooser();
                
                fileChooser.setTitle("Open File");
                currentFile = fileChooser.showOpenDialog(currentStage);
                
                if (currentFile != null) {
                    currentFileIsNew = false;
                    textAreaHasChanged = false;
                    try{
                        FileReader fileReader = new FileReader(currentFile);
                        int oneChar = fileReader.read();
                        String fileContent = "";

                        while(oneChar != -1){
                            fileContent += (char) oneChar;
                            oneChar = fileReader.read();
                        }
                        textArea.setText(fileContent);
                        textArea.positionCaret(textArea.getText().length());
                        
                        fileIsHighLevel = true;
                        fileReader.close();
                        
                    }catch(FileNotFoundException ex){
                        System.out.println(ex.getStackTrace());
                    }catch(IOException ex){
                        System.out.println(ex.getStackTrace());
                    }
                }     
            } 
        });
        
        openHLMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                checkCurrentlyOpenedFile();
                FileChooser fileChooser = new FileChooser();
            
                fileChooser.setTitle("Open File");
                currentFile = fileChooser.showOpenDialog(currentStage);
                
                if (currentFile != null) {
                    currentFileIsNew = false;
                    textAreaHasChanged = false;
                    try{
                        DataInputStream dataInputStream= new DataInputStream(new FileInputStream(currentFile));

                        textArea.setText(dataInputStream.readUTF());
                        textArea.positionCaret(textArea.getText().length());
                        
                    }catch(FileNotFoundException ex){
                        ex.printStackTrace();
                    }catch(IOException ex){
                        ex.printStackTrace();
                    }
                }
            }  
        });
        
        saveLLMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                saveLL();
            }  
        });
        
        saveHLMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                saveHL();
            }  
        });
        
        exitMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                checkCurrentlyOpenedFile();
                currentStage.close();
            }  
        });
        
        cutMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                textArea.cut();
               /* lastCopiedOrCuttedString = textArea.getSelectedText();
                textArea.replaceSelection("");
                stringIsCopiedNotCutted = false;*/
            }  
        });
        
        copyMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                textArea.copy();
               /*lastCopiedOrCuttedString = textArea.getSelectedText();
               stringIsCopiedNotCutted = true;*/
            }  
        });
        
        pasteMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                textArea.paste();
               /*textArea.getCaretPosition();
               textArea.setText(textArea.getText(). + lastCopiedOrCuttedString);
               textArea.positionCaret(textArea.getText().length());
               
               if(!stringIsCopiedNotCutted)
                   lastCopiedOrCuttedString = "";*/
            }  
        });
        
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                textArea.replaceSelection("");
            }  
        });
        
        selectAllMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                textArea.selectAll();
            }  
        });
        
        aboutMenuItem.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.INFORMATION);
                
                alert.setTitle("About Notepad");
                alert.setHeaderText("Notpad");
                alert.setContentText("This program is used to edit text files");
                
                alert.show();
            }  
        });
        
    }
    public void saveHL(){
        if(currentFileIsNew){
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            currentFile = fileChooser.showSaveDialog(currentStage);
        }

        if(currentFile != null){
            try{
                DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(currentFile));

                dataOutputStream.writeUTF(textArea.getText());

                textAreaHasChanged = false;
                currentFileIsNew = false;

                dataOutputStream.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    public void saveLL(){
        if(currentFileIsNew){
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            currentFile = fileChooser.showSaveDialog(currentStage);
        }
        if(currentFile != null){
            try{
                FileWriter fileWriter = new FileWriter(currentFile);

                for(char ch : textArea.getText().toCharArray())
                    fileWriter.write(ch);

                textAreaHasChanged = false;
                currentFileIsNew = false;

                fileWriter.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
    }
    public void checkCurrentlyOpenedFile(){
        
        if(textAreaHasChanged){
            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setTitle("Save");
            alert.setHeaderText("Not Saved");
            alert.setContentText("Do you want to save changes?");

            ButtonType saveButton = new ButtonType("Save");
            ButtonType dontSaveButton = new ButtonType("Don't Save");
            
            alert.getButtonTypes().setAll(saveButton, dontSaveButton);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == saveButton){
                if(fileIsHighLevel){
                    saveHL();
                    fileIsHighLevel = false;
                }
                else
                    saveLL();
            } 
            textAreaHasChanged = false;
        }
    }
}
