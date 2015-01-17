package presentation.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.util.Callback;
import javafx.util.Pair;
import presentation.models.ResultModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Patrick on 17.01.2015.
 */
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
        blockLengthListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        blockLengthListView.itemsProperty().bind(this.model.resultsProperty());
        blockLengthListView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) ->
            {
                plainTextTextArea.setText(((Pair<Integer, String>) newValue).getValue());
            });
    }

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
}
