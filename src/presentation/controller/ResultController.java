package presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.util.Callback;
import javafx.util.Pair;
import presentation.Main;
import presentation.common.ResultCell;
import presentation.models.ResultModel;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResultController
{
    public ListView blockLengthListView;
    public TextArea plainTextTextArea;

    public ResultModel model;

    public ResultController()
    {
        this.model = new ResultModel();
    }

    @FXML
    public void initialize()
    {
        blockLengthListView.setCellFactory(param -> new ResultCell());

        blockLengthListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        blockLengthListView.itemsProperty().bind(this.model.resultsProperty());
        blockLengthListView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) ->
            {
                plainTextTextArea.setText(((Pair<Integer, String>) newValue).getValue());
            });
    }

    /**
     * Writes the given Map<T,List<S>> to the model as flat list
     **/
    public void setResult(Map<Integer, List<String>> result)
    {
        for (Map.Entry<Integer, List<String>> entry : result.entrySet())
        {
            for (String s : entry.getValue())
            {
                this.model.getResults().add(new Pair<>(entry.getKey(), s));
            }
        }
    }

    public void OpenMainView(ActionEvent actionEvent)
    {
        try
        {
            Main.getLayout().setCenter(FXMLLoader.load(getClass().getResource("/presentation/views/MainView.fxml")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
