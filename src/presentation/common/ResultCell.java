package presentation.common;

import javafx.scene.control.ListCell;
import javafx.util.Pair;

/**
 * Created by Georg on 17.01.2015.
 * Displays the key of the given pair
 */
public class ResultCell extends ListCell<Pair<Integer, String>> {
    @Override
    public void updateItem(Pair<Integer, String> cache, boolean empty) {
        super.updateItem(cache, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(cache.getKey().toString());
            setGraphic(null);
        }
    }
}
