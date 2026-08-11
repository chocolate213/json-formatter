package cn.jxzhang.plugin.jsonformatter.panel;

import com.intellij.codeInsight.folding.CodeFoldingManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.ui.EditorTextField;
import com.intellij.util.concurrency.AppExecutorUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Multiline JSON editor backed by the IntelliJ Platform's maintained editor component.
 */
public class EditorTextPanel extends EditorTextField {

    private boolean foldingUpdateScheduled;

    public EditorTextPanel(Project project, FileType fileType) {
        super(null, project, fileType, false, false);
        setFontInheritedFromLAF(false);
        addSettingsProvider(this::configureEditor);
        addDocumentListener(new DocumentListener() {
            @Override
            public void documentChanged(@NotNull DocumentEvent event) {
                scheduleFoldingUpdate();
            }
        });
    }

    private void configureEditor(@NotNull EditorEx editor) {
        EditorSettings settings = editor.getSettings();
        settings.setFoldingOutlineShown(true);
        settings.setLineNumbersShown(true);
        settings.setLineMarkerAreaShown(true);
        settings.setIndentGuidesShown(true);
        editor.setHorizontalScrollbarVisible(true);
        editor.setVerticalScrollbarVisible(true);
        editor.setBackgroundColor(null);

        scheduleFoldingUpdate();
    }

    /**
     * Light virtual files are skipped by the daemon in recent IDE versions, so request folding explicitly.
     * Rapid document changes are coalesced and stale computations are discarded before their result is applied.
     */
    private void scheduleFoldingUpdate() {
        Project project = getProject();
        if (foldingUpdateScheduled || project == null || project.isDisposed()) {
            return;
        }

        foldingUpdateScheduled = true;
        ApplicationManager.getApplication().invokeLater(() -> {
            foldingUpdateScheduled = false;

            Editor editor = getEditor();
            if (editor == null || editor.isDisposed() || project.isDisposed()) {
                return;
            }

            Document document = editor.getDocument();
            if (document.getTextLength() == 0) {
                return;
            }

            PsiDocumentManager.getInstance(project).commitDocument(document);
            long modificationStamp = document.getModificationStamp();

            ReadAction.nonBlocking(() -> CodeFoldingManager.getInstance(project).updateFoldRegionsAsync(editor, false))
                .coalesceBy(this)
                .expireWhen(() -> editor.isDisposed() || editor != getEditor() || project.isDisposed() ||
                    document.getModificationStamp() != modificationStamp)
                .finishOnUiThread(ModalityState.stateForComponent(this), update -> {
                    if (update != null && !editor.isDisposed() && editor == getEditor() && !project.isDisposed() &&
                        document.getModificationStamp() == modificationStamp) {
                        update.run();
                    }
                })
                .submit(AppExecutorUtil.getAppExecutorService());
        }, ModalityState.stateForComponent(this));
    }
}
