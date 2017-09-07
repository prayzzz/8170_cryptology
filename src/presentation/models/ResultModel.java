package presentation.models;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

/**
 * Created by Patrick on 17.01.2015.
 */
public class ResultModel
{
    private ListProperty<Pair<Integer, String>> results = new SimpleListProperty<>(FXCollections.observableArrayList());

    public javafx.collections.ObservableList<Pair<Integer, String>> getResults()
    {
        return results.get();
    }

    public void setResults(ObservableList<Pair<Integer, String>> results)
    {
        this.results.set(results);
    }

    public ListProperty<Pair<Integer, String>> resultsProperty()
    {
        return results;
    }
}
