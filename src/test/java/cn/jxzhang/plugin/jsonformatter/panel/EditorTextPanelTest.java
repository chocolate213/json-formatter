package cn.jxzhang.plugin.jsonformatter.panel;

import com.intellij.json.JsonFileType;
import com.intellij.json.JsonLanguage;
import com.intellij.lang.folding.LanguageFolding;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.testFramework.EdtTestUtil;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Font;

public class EditorTextPanelTest extends BasePlatformTestCase {

    private EditorTextPanel editorTextPanel;
    private Editor editor;

    public void testCreatesFoldRegionsForInMemoryJsonDocument() throws InterruptedException {
        EdtTestUtil.runInEdtAndWait(() -> {
            editorTextPanel = new EditorTextPanel(getProject(), JsonFileType.INSTANCE);
            editorTextPanel.addNotify();
            editorTextPanel.setText("""
                    {
                      "items": [
                        {"name": "first"},
                        {"name": "second"}
                      ]
                    }
                    """);
            editor = editorTextPanel.getEditor();
        });

        assertNotNull(editor);
        assertTrue(editor.getSettings().isAutoCodeFoldingEnabled());
        assertNotNull(LanguageFolding.INSTANCE.forLanguage(JsonLanguage.INSTANCE));
        long timeout = System.currentTimeMillis() + 10_000;
        while (System.currentTimeMillis() < timeout &&
                !ReadAction.compute(() -> editor.getFoldingModel().getAllFoldRegions().length > 0)) {
            Thread.sleep(10);
        }

        assertTrue("JSON fold regions were not created for the in-memory editor",
                ReadAction.compute(() -> editor.getFoldingModel().getAllFoldRegions().length > 0));
        assertTrue(editor.getSettings().isFoldingOutlineShown());
    }

    public void testUsesGlobalEditorBackgroundAndFont() {
        Color originalTextFieldBackground = UIManager.getColor("TextField.background");
        Color distinctTextFieldBackground = new Color(1, 2, 3);

        EdtTestUtil.runInEdtAndWait(() -> {
            UIManager.put("TextField.background", distinctTextFieldBackground);
            try {
                editorTextPanel = new EditorTextPanel(getProject(), JsonFileType.INSTANCE);
                editorTextPanel.addNotify();
                editor = editorTextPanel.getEditor();
                editorTextPanel.setFont(new Font(Font.SERIF, Font.BOLD, 37));
            } finally {
                UIManager.put("TextField.background", originalTextFieldBackground);
            }
        });

        assertInstanceOf(editor, EditorEx.class);
        EditorEx editorEx = (EditorEx) editor;
        EditorColorsScheme globalScheme = EditorColorsManager.getInstance().getGlobalScheme();
        assertEquals(globalScheme.getDefaultBackground(), editorEx.getBackgroundColor());
        assertEquals(globalScheme.getEditorFontName(), editorEx.getColorsScheme().getEditorFontName());
        assertTrue("Editor font size must not be inherited from the Swing text field",
                editorEx.getColorsScheme().getEditorFontSize() != 37);
    }

    @Override
    protected boolean runInDispatchThread() {
        return false;
    }

    @Override
    protected void tearDown() throws Exception {
        try {
            if (editorTextPanel != null) {
                EdtTestUtil.runInEdtAndWait(editorTextPanel::removeNotify);
            }
        } finally {
            super.tearDown();
        }
    }
}
