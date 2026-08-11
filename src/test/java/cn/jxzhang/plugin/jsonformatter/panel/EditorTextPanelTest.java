package cn.jxzhang.plugin.jsonformatter.panel;

import com.intellij.json.JsonFileType;
import com.intellij.json.JsonLanguage;
import com.intellij.lang.folding.LanguageFolding;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.testFramework.EdtTestUtil;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

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
