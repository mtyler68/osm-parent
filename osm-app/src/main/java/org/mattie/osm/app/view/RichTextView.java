package org.mattie.osm.app.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TitledPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;

/**
 * FXML Controller class
 *
 * @author Matthew Tyler
 */
@Slf4j
public class RichTextView implements Initializable {

    public WebView richText;

    public Pagination pagination;

    public TitledPane titledPane;

    @Getter
    private WebEngine webEngine;

    @Getter
    private List<String> richTextPages = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webEngine = richText.getEngine();

        pagination.currentPageIndexProperty().addListener((ov, t, t1) -> {
            displayRichTextPage(ov.getValue().intValue());
        });

        // Action configuration
        ActionMap.register(this);
    }

    void clear() {
        getWebEngine().loadContent("");
        titledPane.setText("Script View");
        pagination.setPageCount(1);
    }

    public void setRichTextPages(List<String> richTextPages) {
        this.richTextPages = richTextPages;
        getWebEngine().loadContent("");
        titledPane.setText("Script View");

        pagination.setPageCount(richTextPages.size() > 0 ? richTextPages.size() : 1);
        pagination.setCurrentPageIndex(0);

        if (!richTextPages.isEmpty()) {
            displayRichTextPage(0);
        }
    }

    public void setRichTextPage(int index) {
        pagination.setCurrentPageIndex(index);
    }

    private void displayRichTextPage(int index) {
        setRichTextContent(richTextPages.get(index));
    }

    public void setRichTextContent(String text) {
        getWebEngine().loadContent(text);
        getWebEngine().titleProperty().addListener((t) -> {
            log.info("setRichTextContent(): title={}: {}", getWebEngine().getTitle(), RichTextView.this);
            titledPane.setText(getWebEngine().getTitle());
        });
    }

    @ActionProxy(id = "PAGE_DOWN", text = "Next Page")
    public void nextRichTextPage() {
        int index = pagination.getCurrentPageIndex() + 1;
        if (index < richTextPages.size()) {
            setRichTextPage(index);
        }
    }

    @ActionProxy(id = "PAGE_UP", text = "Prev Page")
    public void prevRichTextPage() {
        int index = pagination.getCurrentPageIndex() - 1;
        if (index >= 0) {
            setRichTextPage(index);
        }
    }
}
