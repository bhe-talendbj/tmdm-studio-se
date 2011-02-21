// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.workbench.editors;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.operations.UndoRedoActionGroup;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.xsd.XSDAnnotation;
import org.eclipse.xsd.XSDComplexTypeDefinition;
import org.eclipse.xsd.XSDComponent;
import org.eclipse.xsd.XSDConcreteComponent;
import org.eclipse.xsd.XSDDiagnostic;
import org.eclipse.xsd.XSDDiagnosticSeverity;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDEnumerationFacet;
import org.eclipse.xsd.XSDFactory;
import org.eclipse.xsd.XSDFractionDigitsFacet;
import org.eclipse.xsd.XSDIdentityConstraintDefinition;
import org.eclipse.xsd.XSDImport;
import org.eclipse.xsd.XSDInclude;
import org.eclipse.xsd.XSDLengthFacet;
import org.eclipse.xsd.XSDMaxExclusiveFacet;
import org.eclipse.xsd.XSDMaxInclusiveFacet;
import org.eclipse.xsd.XSDMaxLengthFacet;
import org.eclipse.xsd.XSDMinExclusiveFacet;
import org.eclipse.xsd.XSDMinInclusiveFacet;
import org.eclipse.xsd.XSDMinLengthFacet;
import org.eclipse.xsd.XSDModelGroup;
import org.eclipse.xsd.XSDParticle;
import org.eclipse.xsd.XSDPatternFacet;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaContent;
import org.eclipse.xsd.XSDSimpleTypeDefinition;
import org.eclipse.xsd.XSDTerm;
import org.eclipse.xsd.XSDTotalDigitsFacet;
import org.eclipse.xsd.XSDTypeDefinition;
import org.eclipse.xsd.XSDWhiteSpaceFacet;
import org.eclipse.xsd.XSDWildcard;
import org.eclipse.xsd.XSDXPathDefinition;
import org.eclipse.xsd.XSDXPathVariety;
import org.eclipse.xsd.impl.XSDComplexTypeDefinitionImpl;
import org.eclipse.xsd.impl.XSDElementDeclarationImpl;
import org.eclipse.xsd.impl.XSDIdentityConstraintDefinitionImpl;
import org.eclipse.xsd.impl.XSDImportImpl;
import org.eclipse.xsd.impl.XSDIncludeImpl;
import org.eclipse.xsd.impl.XSDParticleImpl;
import org.eclipse.xsd.impl.XSDSimpleTypeDefinitionImpl;
import org.eclipse.xsd.impl.XSDXPathDefinitionImpl;
import org.talend.mdm.commmon.util.core.CommonUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.amalto.workbench.actions.XSDAnnotationLookupFieldsAction;
import com.amalto.workbench.actions.XSDChangeBaseTypeAction;
import com.amalto.workbench.actions.XSDChangeToComplexTypeAction;
import com.amalto.workbench.actions.XSDChangeToSimpleTypeAction;
import com.amalto.workbench.actions.XSDCopyConceptAction;
import com.amalto.workbench.actions.XSDDefaultValueRuleAction;
import com.amalto.workbench.actions.XSDDeleteConceptAction;
import com.amalto.workbench.actions.XSDDeleteConceptWrapAction;
import com.amalto.workbench.actions.XSDDeleteElementAction;
import com.amalto.workbench.actions.XSDDeleteIdentityConstraintAction;
import com.amalto.workbench.actions.XSDDeleteParticleAction;
import com.amalto.workbench.actions.XSDDeleteTypeDefinition;
import com.amalto.workbench.actions.XSDDeleteXPathAction;
import com.amalto.workbench.actions.XSDEditComplexTypeAction;
import com.amalto.workbench.actions.XSDEditConceptAction;
import com.amalto.workbench.actions.XSDEditElementAction;
import com.amalto.workbench.actions.XSDEditFacetAction;
import com.amalto.workbench.actions.XSDEditIdentityConstraintAction;
import com.amalto.workbench.actions.XSDEditParticleAction;
import com.amalto.workbench.actions.XSDEditXPathAction;
import com.amalto.workbench.actions.XSDGetXPathAction;
import com.amalto.workbench.actions.XSDNewBrowseItemViewAction;
import com.amalto.workbench.actions.XSDNewComplexTypeDefinition;
import com.amalto.workbench.actions.XSDNewConceptAction;
import com.amalto.workbench.actions.XSDNewElementAction;
import com.amalto.workbench.actions.XSDNewGroupFromTypeAction;
import com.amalto.workbench.actions.XSDNewIdentityConstraintAction;
import com.amalto.workbench.actions.XSDNewParticleFromParticleAction;
import com.amalto.workbench.actions.XSDNewParticleFromTypeAction;
import com.amalto.workbench.actions.XSDNewSimpleTypeDefinition;
import com.amalto.workbench.actions.XSDNewXPathAction;
import com.amalto.workbench.actions.XSDPasteConceptAction;
import com.amalto.workbench.actions.XSDSetAnnotaionDisplayFormatAction;
import com.amalto.workbench.actions.XSDSetAnnotationDescriptionsAction;
import com.amalto.workbench.actions.XSDSetAnnotationFKFilterAction;
import com.amalto.workbench.actions.XSDSetAnnotationForeignKeyAction;
import com.amalto.workbench.actions.XSDSetAnnotationForeignKeyInfoAction;
import com.amalto.workbench.actions.XSDSetAnnotationLabelAction;
import com.amalto.workbench.actions.XSDSetAnnotationNoAction;
import com.amalto.workbench.actions.XSDSetAnnotationPrimaryKeyInfoAction;
import com.amalto.workbench.actions.XSDSetAnnotationWrapNoAction;
import com.amalto.workbench.actions.XSDSetAnnotationWrapWriteAction;
import com.amalto.workbench.actions.XSDSetAnnotationWriteAction;
import com.amalto.workbench.actions.XSDSetFacetMessageAction;
import com.amalto.workbench.actions.XSDVisibleRuleAction;
import com.amalto.workbench.availablemodel.AvailableModelUtil;
import com.amalto.workbench.availablemodel.IAvailableModel;
import com.amalto.workbench.dialogs.DataModelFilterDialog;
import com.amalto.workbench.dialogs.ErrorExceptionDialog;
import com.amalto.workbench.dialogs.RoleAssignmentDialog;
import com.amalto.workbench.dialogs.SelectImportedModulesDialog;
import com.amalto.workbench.image.EImage;
import com.amalto.workbench.image.ImageCache;
import com.amalto.workbench.models.TreeObject;
import com.amalto.workbench.models.TreeObjectTransfer;
import com.amalto.workbench.providers.ISchemaContentProvider;
import com.amalto.workbench.providers.TypesLabelProvider;
import com.amalto.workbench.providers.XSDTreeLabelProvider;
import com.amalto.workbench.providers.datamodel.SchemaElementSorter;
import com.amalto.workbench.providers.datamodel.SchemaNameFilter;
import com.amalto.workbench.providers.datamodel.SchemaRoleAccessFilter;
import com.amalto.workbench.providers.datamodel.SchemaTreeContentProvider;
import com.amalto.workbench.providers.datamodel.SchemaUniqueElementFilter;
import com.amalto.workbench.providers.datamodel.TypeElementSorter;
import com.amalto.workbench.providers.datamodel.TypeNameFilter;
import com.amalto.workbench.providers.datamodel.TypesTreeContentProvider;
import com.amalto.workbench.utils.CompositeViewersSelectionProvider;
import com.amalto.workbench.utils.DataModelFilter;
import com.amalto.workbench.utils.FontUtils;
import com.amalto.workbench.utils.ResourcesUtil;
import com.amalto.workbench.utils.SchemaElementNameFilterDes;
import com.amalto.workbench.utils.Util;
import com.amalto.workbench.utils.WorkbenchClipboard;
import com.amalto.workbench.utils.XSDAnnotationsStructure;
import com.amalto.workbench.webservices.WSDataModel;
import com.amalto.workbench.webservices.WSPutDataModel;
import com.amalto.workbench.webservices.XtentisPort;
import com.amalto.workbench.widgets.WidgetFactory;

public class DataModelMainPage extends EditorPart implements ModifyListener {

    private static Log log = LogFactory.getLog(DataModelMainPage.class);

    protected Text descriptionText;

    private Label langeuageLabel;

    private Combo languageCombo;

    protected TreeViewer viewer;

    protected DrillDownAdapter drillDownAdapter;

    // private XSDNewConceptAction newConceptAction = null;
    private XSDDeleteConceptAction deleteConceptAction = null;

    private XSDDeleteConceptWrapAction deleteConceptWrapAction = null;

    private XSDNewBrowseItemViewAction newBrowseItemAction = null;

    private XSDNewElementAction newElementAction = null;

    private XSDDeleteElementAction deleteElementAction = null;

    // private XSDDeleteValidationRulesAction deleteValidationRule=null;
    private XSDChangeToComplexTypeAction changeToComplexTypeAction = null;

    private XSDDeleteParticleAction deleteParticleAction = null;

    private XSDNewParticleFromTypeAction newParticleFromTypeAction = null;

    private XSDNewParticleFromParticleAction newParticleFromParticleAction = null;

    private XSDNewGroupFromTypeAction newGroupFromTypeAction = null;

    private XSDEditParticleAction editParticleAction = null;

    private XSDEditConceptAction editConceptAction = null;

    // private XSDCopyConceptAction copyConceptAction = null;
    // private XSDPasteConceptAction pasteConceptAction = null;

    private XSDEditElementAction editElementAction = null;

    private XSDDeleteIdentityConstraintAction deleteIdentityConstraintAction = null;

    private XSDEditIdentityConstraintAction editIdentityConstraintAction = null;

    private XSDNewIdentityConstraintAction newIdentityConstraintAction = null;

    private XSDDeleteXPathAction deleteXPathAction = null;

    private XSDNewXPathAction newXPathAction = null;

    private XSDEditXPathAction editXPathAction = null;

    private XSDChangeToSimpleTypeAction changeToSimpleTypeAction = null;

    private XSDChangeBaseTypeAction changeBaseTypeAction = null;

    private XSDGetXPathAction getXPathAction = null;

    private XSDSetAnnotationForeignKeyAction setAnnotationForeignKeyAction = null;

    private XSDVisibleRuleAction visibleRuleAction;

    private XSDDefaultValueRuleAction defaultValueRuleAction;

    private XSDSetAnnotationFKFilterAction setAnnotationFKFilterAction = null;

    private XSDSetAnnotationWrapWriteAction setAnnotationWrapWriteAction = null;

    private XSDSetAnnotationForeignKeyInfoAction setAnnotationForeignKeyInfoAction = null;

    private XSDSetAnnotationLabelAction setAnnotationLabelAction = null;

    private XSDSetAnnotationDescriptionsAction setAnnotationDescriptionsAction = null;

    private XSDSetAnnotationNoAction setAnnotationNoAction = null;

    private XSDSetAnnotationWrapNoAction setAnnotationWrapNoAction = null;

    private XSDSetAnnotationWriteAction setAnnotationWriteAction = null;

    private XSDAnnotationLookupFieldsAction setAnnotationLookupFieldsAction = null;

    private XSDSetAnnotationPrimaryKeyInfoAction setAnnotationPrimaryKeyInfoAction = null;

    private XSDSetAnnotaionDisplayFormatAction setAnnotationDisplayFomatAction = null;

    private XSDChangeToComplexTypeAction changeSubElementGroupAction = null;

    private XSDDeleteTypeDefinition deleteTypeDefinition = null;

    private XSDNewComplexTypeDefinition newComplexTypeAction = null;

    private XSDNewSimpleTypeDefinition newSimpleTypeAction = null;

    private XSDEditComplexTypeAction editComplexTypeAction = null;

    private XSDSetFacetMessageAction setFacetMsgAction = null;

    private ObjectUndoContext undoContext;

    private MenuManager menuMgr;

    private String dataModelName;

    private XSDSchema xsdSchema;

    // private XSDTreeContentProvider provider;
    private SchemaTreeContentProvider schemaTreeContentProvider;

    private Map<ObjectUndoContext, Map<Integer, String>> contextToUndoAction = new HashMap<ObjectUndoContext, Map<Integer, String>>();

    private Map<ObjectUndoContext, Map<Integer, String>> contextToRedoAction = new HashMap<ObjectUndoContext, Map<Integer, String>>();

    private int undoLimit = 20;

    private DataModelFilter dataModelFilter;

    // private StructuredSelection sel;

    private SashForm sash;

    private TreeViewer typesViewer;

    // private TypesContentProvider typesProvider;
    private TypesTreeContentProvider typesTreeContentProvider;

    private MenuManager typesMenuMgr;

    boolean isSchemaSelected = true;

    private String modelName = "";

    private boolean isChange = false;

    private Group addLanGroup;

    protected String uriPre;

    private SchemaElementSorter schemaTreeSorter = new SchemaElementSorter();

    private SchemaElementSorter typeTreeSorter = new TypeElementSorter();

    private SchemaElementNameFilterDes schemaElementNameFilterDes = new SchemaElementNameFilterDes();

    private SchemaElementNameFilterDes typeElementNameFilterDes = new SchemaElementNameFilterDes();

    private CompositeViewersSelectionProvider selectionProvider;

    WSDataModel datamodel;

    TreeObject xobject;

    boolean dirty;

    Composite mainControl;

    public DataModelMainPage(TreeObject obj) {
        this.xobject = obj;
        this.datamodel = (WSDataModel) obj.getWsObject();
        modelName = datamodel.getName();
        dataModelName = modelName;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
        firePropertyChange(PROP_DIRTY);
    }

    public boolean isSchemaSelected() {
        return isSchemaSelected;
    }

    public void setSchemaSelected(boolean isSchemaSelected) {
        this.isSchemaSelected = isSchemaSelected;
    }

    protected void createCharacteristicsContent(FormToolkit toolkit, Composite mainComposite) {

        try {
            mainComposite.getParent().setLayout(new GridLayout());
            GridData gdMainComposite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
            gdMainComposite.widthHint = 1;
            gdMainComposite.heightHint = 1;
            mainComposite.setLayoutData(gdMainComposite);

            WSDataModel wsObject = (WSDataModel) (xobject.getWsObject());
            // Button importXSDBtn, exportBtn, sortUPBtn, sortDownBtn, expandBtn, collapseBtn, expandSelBtn,
            // sortNaturalBtn, addLanBtn, deleteLanbtn, importSchemaNsBtn;
            Button importXSDBtn, exportBtn, addLanBtn, deleteLanbtn, importSchemaNsBtn;
            // description
            Label descriptionLabel = toolkit.createLabel(mainComposite, "Description", SWT.NULL);
            descriptionLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

            descriptionText = toolkit.createText(mainComposite, "", SWT.BORDER | SWT.MULTI);
            descriptionText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
            descriptionText.setText(wsObject.getDescription() == null ? "" : wsObject.getDescription());
            ((GridData) descriptionText.getLayoutData()).minimumHeight = 30;
            descriptionText.addModifyListener(this);
            // Util.createCompDropTarget(descriptionText);
            Composite btnCmp = toolkit.createComposite(mainComposite);
            btnCmp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
            GridLayout gLayout = new GridLayout();
            gLayout.numColumns = 4;
            gLayout.horizontalSpacing = 20;
            btnCmp.setLayout(gLayout);

            importXSDBtn = toolkit.createButton(btnCmp, "", SWT.PUSH);
            importXSDBtn.setImage(ImageCache.getCreatedImage(EImage.IMPORT.getPath()));
            importXSDBtn.setToolTipText("Import...");

            exportBtn = toolkit.createButton(btnCmp, "", SWT.PUSH);
            exportBtn.setImage(ImageCache.getCreatedImage(EImage.EXPORT.getPath()));
            exportBtn.setToolTipText("Export...");

            importSchemaNsBtn = toolkit.createButton(btnCmp, "", SWT.PUSH);
            importSchemaNsBtn.setImage(ImageCache.getCreatedImage(EImage.CHECKIN_ACTION.getPath()));
            importSchemaNsBtn.setToolTipText("import/include specific Schema Namespace ...");

            addLanGroup = new Group(btnCmp, SWT.NONE);
            addLanGroup.setText("Label Operation");
            addLanGroup.setToolTipText("Add or remove languages in all Entities and elements for the current data model");
            addLanGroup.setBackground(btnCmp.getDisplay().getSystemColor(SWT.COLOR_WHITE));
            addLanGroup.setLayout(new GridLayout(4, false));

            addLanGroup.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));

            langeuageLabel = toolkit.createLabel(addLanGroup, "Language:");
            languageCombo = new Combo(addLanGroup, SWT.READ_ONLY);
            addLanBtn = toolkit.createButton(addLanGroup, "", SWT.NONE);
            addLanBtn.setImage(ImageCache.getCreatedImage(EImage.ADD_OBJ.getPath()));
            addLanBtn.setToolTipText("Add...");
            deleteLanbtn = toolkit.createButton(addLanGroup, "", SWT.NONE);
            deleteLanbtn.setImage(ImageCache.getCreatedImage(EImage.DELETE_OBJ.getPath()));
            deleteLanbtn.setToolTipText("Remove...");
            Set<String> languages = Util.lang2iso.keySet();
            for (Iterator iter = languages.iterator(); iter.hasNext();) {
                String language = (String) iter.next();
                languageCombo.add(language);
            }
            languageCombo.select(0);
            addLanBtn.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    addOrDelLanguage(true);
                }
            });
            deleteLanbtn.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    addOrDelLanguage(false);
                }
            });

            langeuageLabel.setLayoutData(new GridData(SWT.RIGHT_TO_LEFT, SWT.CENTER, false, false, 1, 1));
            languageCombo.setLayoutData(new GridData(SWT.RIGHT_TO_LEFT, SWT.CENTER, false, false, 1, 1));
            addLanBtn.setLayoutData(new GridData(SWT.RIGHT_TO_LEFT, SWT.CENTER, false, false, 1, 1));
            deleteLanbtn.setLayoutData(new GridData(SWT.RIGHT_TO_LEFT, SWT.CENTER, false, false, 1, 1));
            importXSDBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
            exportBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

            importSchemaNsBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

            importXSDBtn.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    FileDialog fd = new FileDialog(getSite().getShell(), SWT.OPEN);
                    fd.setFilterExtensions(new String[] { "*.xsd", "*.dtd", "*.xml" });//$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
                    // set the default path to the workspace.
                    fd.setFilterPath(Platform.getInstanceLocation().getURL().getPath().substring(1));
                    // System.out.println(Platform.getInstanceLocation().getURL().getPath());
                    fd.setText("Select the XML definition for XML Schema");
                    String filename = fd.open();
                    if (filename == null)
                        return;
                    xsdSchema = null;
                    inferXsdFromXml(filename);
                }

                private void inferXsdFromXml(String xmlFile) {
                    int infer = 0;
                    String xsd = "";
                    try {
                        String inputType = xmlFile.substring(xmlFile.lastIndexOf("."));
                        if (inputType.equals(".xsd")) {
                            xsd = Util.getXML(xmlFile);
                            xsdSchema = Util.createXsdSchema(xsd, xobject);
                            // xsdSchema.setTargetNamespace(null);
                            xsd = Util.nodeToString(xsdSchema.getDocument());
                        } else {
                            XSDDriver d = new XSDDriver();
                            infer = d.doMain(new String[] { xmlFile, "out.xsd" });//$NON-NLS-1$
                            if (infer == 0) {
                                xsd = d.outputXSD();
                            }
                        }

                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        infer = 2;
                    } finally {
                        if (infer == 0 && !xsd.equals("")) {
                            WSDataModel wsObj = (WSDataModel) (xobject.getWsObject());
                            wsObj.setXsdSchema(xsd);
                            validateSchema(xsd);
                            refreshData();
                            markDirtyWithoutCommit();
                        } else if (infer != 0) {
                            MessageDialog.openError(getSite().getShell(), "Error",
                                    "XSD schema can not be inferred from the given xml");
                        }
                    }
                }

                void validateSchema(String xsd) {
                    try {
                        boolean elem = false;
                        XSDSchema schema = getXSDSchema(xsd);
                        NodeList nodeList = schema.getDocument().getDocumentElement().getChildNodes();
                        for (int idx = 0; idx < nodeList.getLength(); idx++) {
                            Node node = nodeList.item(idx);
                            if (node instanceof Element && node.getLocalName().indexOf("element") >= 0) {
                                elem = true;
                                break;
                            }
                        }
                        if (!elem) {
                            MessageDialog.openWarning(getSite().getShell(), "Warnning",
                                    "There is no element node in the imported xsd schema");
                        }
                    } catch (Exception e) {

                        log.error(e.getMessage(), e);
                    }
                }
            });

            exportBtn.addSelectionListener(new SelectionAdapter() {

                @Override
                public void widgetSelected(SelectionEvent e) {
                    FileDialog fd = new FileDialog(getSite().getShell(), SWT.SAVE);
                    fd.setFilterExtensions(new String[] { "*.xsd" });//$NON-NLS-1$
                    fd.setFilterPath(Platform.getInstanceLocation().getURL().getPath().substring(1));
                    fd.setText("Save the Data Module as XSD Schema");
                    String filename = fd.open();
                    if (filename == null)
                        return;
                    inferXsdFromDataModule(filename);
                }

                private void inferXsdFromDataModule(String xmlFile) {
                    WSDataModel wsObject = (WSDataModel) (xobject.getWsObject());
                    XSDDriver d = new XSDDriver();
                    // if (d.outputXSD(wsObject.getXsdSchema(), xmlFile) != null) {
                    if (d.outputXSD_UTF_8(wsObject.getXsdSchema(), xmlFile) != null) {
                        MessageDialog.openInformation(getSite().getShell(), "Export XSD",
                                "The operation for Exporting XSD completed successfully!");
                    } else {
                        MessageDialog.openError(getSite().getShell(), "Error", "failed to export XSD file!");
                    }
                }
            });

            importSchemaNsBtn.addSelectionListener(new SelectionAdapter() {

                public void widgetSelected(SelectionEvent e) {
                    SelectImportedModulesDialog dlg = new SelectImportedModulesDialog(getSite().getShell(), xsdSchema, xobject,
                            "Import xsd schema modules");
                    dlg.create();
                    dlg.setBlockOnOpen(true);
                    dlg.open();
                    if (dlg.getReturnCode() == Window.OK) {
                        doImportSchema(dlg.getImportedXSDList(), dlg.getToDelXSDList());
                    }
                }

            });

            // Label xsdLabel = toolkit.createLabel(mainComposite, "Schema",
            // SWT.NULL);
            // xsdLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
            // true, 2, 1));
            // get the XSDSchema
            xsdSchema = getXSDSchema(wsObject.getXsdSchema());
            createSash(mainComposite);
            createCompDropTarget();
            hookContextMenu();

            hookDoubleClickAction();
            // hookTypesDoubleClickAction();
            hookTypesContextMenu();

            // init undo history
            initializeOperationHistory();

        } catch (Exception e) {

            log.error(e.getMessage(), e);
        }

    }// createCharacteristicsContent

    protected void doImportSchema(final List<String> addList, final List<String> delList) {
        // final List<String> addList = dlg.getImportedXSDList();
        // final List<String> delList = dlg.getToDelXSDList();
        TimerTask task = new TimerTask() {

            public void run() {
                getSite().getShell().getDisplay().asyncExec(new Runnable() {

                    public void run() {
                        XSDSchema schemaCpy = null;
                        try {
                            schemaCpy = Util.createXsdSchema(Util.nodeToString(xsdSchema.getDocument()), xobject);
                            xsdSchema.clearDiagnostics();
                            performImport(addList);
                            performDeletion(delList);
                            validateSchema();

                            markDirtyWithoutCommit();
                            // refreshData();
                            // below code is to refill the tree view with xsdScham including one xsd schma which
                            // contains the other xsd ,
                            // and in the case of deleting the included xsd
                            setXsdSchema(xsdSchema);
                            commit();
                            refreshData();
                            XSDSchema mc = xsdSchema;
                            MessageDialog.openInformation(getSite().getShell(), "Import XSDSchema",
                                    "The operation for importing XSDSchema completed successfully!");
                        } catch (Exception ex) {
                            log.error(ex.getMessage(), ex);
                            String detail = "";
                            if (ex.getMessage() != null && !ex.getMessage().equals("")) {
                                detail += " , due to" + "\n" + ex.getMessage();
                            }
                            setXsdSchema(schemaCpy);
                            commit();
                            refresh();
                            MessageDialog.openError(getSite().getShell(), "Error", "The operation for importing XSDSchema failed"
                                    + detail);
                        }
                    }
                });
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(task, 0);

    }

    public void validateSchema() throws IllegalAccessException {
        final String msg_omit[] = {
                "XSD: The value '1' of attribute 'maxOccurs' must be one of  as constrained by 'http://www.w3.org/2001/XMLSchema#maxOccurs_._type'",
                "XSD: The attribute may not have duplicate name and target namespace",
                "XSD: The type may not have duplicate name and target namespace",
                "XSD: The attribute group may not have duplicate name and target namespace",
                "The complex type may not have duplicate name" };

        xsdSchema.clearDiagnostics();
        xsdSchema.validate();
        EList<XSDDiagnostic> diagnoses = xsdSchema.getAllDiagnostics();
        String error = "";
        Set<String> errors = new HashSet<String>();
        for (int i = 0; i < diagnoses.size(); i++) {
            XSDDiagnostic dia = diagnoses.get(i);
            XSDDiagnosticSeverity servity = dia.getSeverity();
            if (servity == XSDDiagnosticSeverity.ERROR_LITERAL || servity == XSDDiagnosticSeverity.FATAL_LITERAL) {
                boolean omit = false;
                for (String msg : msg_omit) {
                    if (dia.getMessage().indexOf(msg) != -1) {
                        omit = true;
                        break;
                    }
                }
                if (!omit && !errors.contains(dia.getMessage())) {
                    error += dia.getMessage() + "\n";
                    errors.add(dia.getMessage());
                }
            }
        }
        if (!error.equals("")) {
            throw new IllegalAccessException(error);
        }

        validateType();
        validateElementation();
    }

    protected void addOrDelLanguage(boolean isAdd) {
        TreeItem[] items = viewer.getTree().getItems();

        addLabelForTheItem(items, isAdd);
        if (isChange) {
            this.markDirtyWithoutCommit();
            this.refresh();
            // this.getTreeViewer().expandToLevel(xSDCom, 2);
        }

    }

    private void addLabelForTheItem(TreeItem[] items, boolean isAdd) {
        XSDComponent xSDCom = null;
        for (int i = 0; i < items.length; i++) {
            TreeItem item = items[i];
            XSDAnnotationsStructure struc = null;
            String labelValue = null;
            if (item.getData() instanceof XSDElementDeclaration) {
                xSDCom = (XSDElementDeclaration) item.getData();
                struc = new XSDAnnotationsStructure(xSDCom);
                labelValue = ((XSDElementDeclaration) xSDCom).getName();
                setLabel(struc, labelValue, isAdd);
                setLabelForElement((XSDElementDeclaration) xSDCom, isAdd);

            }
        }
    }

    private void setLabelForElement(XSDElementDeclaration xSDEle, boolean isAdd) {
        if (((XSDElementDeclaration) xSDEle).getTypeDefinition() instanceof XSDComplexTypeDefinition) {
            XSDAnnotationsStructure struc = null;
            String labelValue = null;
            List childrenList = Util.getComplexTypeDefinitionChildren((XSDComplexTypeDefinition) ((XSDElementDeclaration) xSDEle)
                    .getTypeDefinition());
            for (int j = 0; j < childrenList.size(); j++) {
                List<XSDParticle> particles = new ArrayList<XSDParticle>();
                if (childrenList.get(j) instanceof XSDModelGroup)
                    particles = ((XSDModelGroup) childrenList.get(j)).getParticles();
                for (int k = 0; k < particles.size(); k++) {
                    XSDParticle xSDCom = particles.get(k);
                    struc = new XSDAnnotationsStructure(xSDCom);
                    if (((XSDParticle) xSDCom).getContent() instanceof XSDElementDeclaration) {
                        labelValue = ((XSDElementDeclaration) ((XSDParticle) xSDCom).getContent()).getName();
                        setLabel(struc, labelValue, isAdd);
                        setLabelForElement((XSDElementDeclaration) ((XSDParticle) xSDCom).getContent(), isAdd);
                    }
                }
            }
        }
    }

    private void setLabel(XSDAnnotationsStructure struc, String labelValue, boolean isAdd) {

        String labelKey = Util.lang2iso.get(languageCombo.getText());
        if (isAdd) {
            if (!struc.getLabels().containsKey(labelKey))
                struc.setLabel(labelKey, labelValue);
        } else {
            if (struc.getLabels().containsKey(labelKey))
                struc.removeLabel(labelKey);
        }
        if (struc.hasChanged())
            isChange = true;
    }

    private void createSchemaTreeComp(Composite parent) {

        Composite schemaSash = new Composite(parent, SWT.NONE);
        schemaSash.setLayout(new GridLayout());
        schemaSash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        schemaSash.setBackground(sash.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Composite compInfo = new Composite(schemaSash, SWT.NONE);
        compInfo.setLayout(new GridLayout());
        compInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        compInfo.setBackground(sash.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Composite compSchemaTree = new Composite(schemaSash, SWT.NONE);
        GridLayout glCompSchemaTree = new GridLayout();
        glCompSchemaTree.verticalSpacing = 0;
        glCompSchemaTree.marginWidth = 0;
        glCompSchemaTree.marginHeight = 0;
        glCompSchemaTree.horizontalSpacing = 0;
        compSchemaTree.setLayout(glCompSchemaTree);
        compSchemaTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        compSchemaTree.setBackground(sash.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Label title = new Label(compInfo, SWT.VERTICAL);
        title.setText("Data-model: " + modelName);
        title.setFont(FontUtils.getBoldFont(title.getFont()));
        Color blue = new Color(compInfo.getDisplay(), 0, 0, 255);
        title.setForeground(blue);
        title.setBackground(sash.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Label des = new Label(compInfo, SWT.VERTICAL);
        des.setText("Define the " + modelName + " data-model");
        des.setBackground(sash.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        ToolBar toolBarSchemaTree = createToolbarOnComposite(compSchemaTree);

        viewer = new TreeViewer(compSchemaTree, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        viewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        // viewer.getControl().setLayoutData(
        // new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        //

        addToolItems2SchemaTreeToolBar(toolBarSchemaTree);
        toolBarSchemaTree.pack();

        drillDownAdapter = new DrillDownAdapter(viewer);
        // provider = new XSDTreeContentProvider(this.getSite(), xsdSchema, xobject);
        schemaTreeContentProvider = new SchemaTreeContentProvider(this.getSite(), xsdSchema);
        // viewer.setContentProvider(provider);
        viewer.setContentProvider(schemaTreeContentProvider);

        viewer.setFilters(new ViewerFilter[] { new SchemaRoleAccessFilter(null), new SchemaNameFilter(),
                new SchemaUniqueElementFilter() });

        viewer.getTree().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseDown(MouseEvent e) {
                isSchemaSelected = true;
            }
        });
        viewer.setLabelProvider(new XSDTreeLabelProvider());
        viewer.setSorter(schemaTreeSorter);
        // viewer.setSorter(new ViewerSorter() {
        //
        // public int category(Object element) {
        // // we want facets before Base TypeDefinitions in
        // // SimpleTypeDefinition
        // if (element instanceof XSDFacet)
        // return 100;
        // // unique keys after element declarations and before other
        // // keys
        // if (element instanceof XSDIdentityConstraintDefinition) {
        // XSDIdentityConstraintDefinition icd = (XSDIdentityConstraintDefinition) element;
        //
        // if (icd.getIdentityConstraintCategory().equals(XSDIdentityConstraintCategory.UNIQUE_LITERAL))
        // return 300;
        // else if (icd.getIdentityConstraintCategory().equals(XSDIdentityConstraintCategory.KEY_LITERAL))
        // return 301;
        // else
        // return 302;
        // }
        // return 200;
        // }
        //
        // public int compare(Viewer theViewer, Object e1, Object e2) {
        // int cat1 = category(e1);
        // int cat2 = category(e2);
        // // if(cat1==cat2&&cat1==200){
        // // if(e1 instanceof XSDParticle&&e2 instanceof XSDParticle){
        // // XSDParticle xp1= (XSDParticle)e1;
        // // XSDParticle xp2= (XSDParticle)e2;
        // // String name1 = ((XSDElementDeclaration)xp1.getTerm()).getName();
        // // String name2 = ((XSDElementDeclaration)xp2.getTerm()).getName();
        // // if(isDESC)
        // // return name1.compareToIgnoreCase(name2);
        // // else
        // // return -name1.compareToIgnoreCase(name2);
        // // }
        // // }
        // //
        // return cat1 - cat2;
        // }
        // });
        viewer.setInput(this.getSite());// getViewSite());
        viewer.getTree().addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {

                IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());

                // delete
                if ((e.stateMask == 0) && (e.keyCode == SWT.DEL)) {
                    if (deleteConceptWrapAction.checkInDeletableType(selection.toArray())) {
                        deleteConceptWrapAction.prepareToDelSelectedItems(selection, viewer);
                        deleteConceptWrapAction.run();
                    } else {
                        MessageDialog.openWarning(getSite().getShell(), "Warnning",
                                "Please select the deletable node and try again!");
                    }
                }
            }

            public void keyReleased(KeyEvent e) {

            }

        });

    }

    public void stepUp(TreeViewer targetTreeViewer) {
        TreeItem item;
        TreeItem[] items = targetTreeViewer.getTree().getSelection();
        for (int i = 0; i < items.length; i++) {
            item = items[i];

            if (!(item.getData() instanceof XSDConcreteComponent))
                continue;

            XSDConcreteComponent component = (XSDConcreteComponent) item.getData();
            if (!(component instanceof XSDParticle))
                continue;
            else {

                XSDParticle particle = (XSDParticle) component;
                if (particle.getContainer() instanceof XSDModelGroup) {
                    XSDModelGroup mp = (XSDModelGroup) particle.getContainer();
                    // EList<XSDParticle> el = mp.getParticles();
                    int index = mp.getContents().indexOf(particle);
                    if (index > 0) {
                        mp.getContents().move(index - 1, index);
                        // openXSDParticle();
                        this.refresh();
                    }// else

                }

            }

        }
        this.markDirtyWithoutCommit();
    }

    public void stepDown(TreeViewer targetTreeViewer) {
        TreeItem item;
        TreeItem[] items = targetTreeViewer.getTree().getSelection();
        for (int i = items.length - 1; i >= 0; i--) {
            item = items[i];

            if (!(item.getData() instanceof XSDConcreteComponent))
                continue;

            XSDConcreteComponent component = (XSDConcreteComponent) item.getData();
            if (!(component instanceof XSDParticle))
                continue;
            else {
                XSDParticle particle = (XSDParticle) component;
                if (particle.getContainer() instanceof XSDModelGroup) {
                    XSDModelGroup mp = (XSDModelGroup) particle.getContainer();
                    // EList<XSDParticle> el = mp.getParticles();
                    int index = mp.getContents().indexOf(particle);
                    if (index < mp.getContents().size() - 1) {
                        mp.getContents().move(index, index + 1);
                        this.refresh();
                    }// else
                }

            }
        }
        this.markDirtyWithoutCommit();
    }

    public SashForm createSash(Composite parent) {
        // Splitter
        sash = new SashForm(parent, SWT.HORIZONTAL);
        sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        // ((GridData) sash.getLayoutData()).heightHint = 1000;
        GridLayout layout = new GridLayout();
        sash.setLayout(layout);
        // sash.setBackground(sash.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        // create schema tree
        createSchemaTreeComp(sash);
        // create button
        // createButton();
        // create type tree
        createTypeTreeComp(sash);
        // init

        selectionProvider = new CompositeViewersSelectionProvider(new Viewer[] { viewer, typesViewer });

        sash.setWeights(new int[] { 50, 50 });
        return sash;
    }

    public ISelectionProvider getSiteSelectionProvider() {
        return selectionProvider;
    }

    private void createTypeTreeComp(Composite parent) {

        Composite TypeSash = new Composite(parent, SWT.NONE);
        TypeSash.setLayout(new GridLayout());
        TypeSash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        TypeSash.setBackground(sash.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Composite compInfo = new Composite(TypeSash, SWT.NONE);
        compInfo.setLayout(new GridLayout());
        compInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        compInfo.setBackground(sash.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Composite compTypeTree = new Composite(TypeSash, SWT.NONE);
        GridLayout glCompTypeTree = new GridLayout();
        glCompTypeTree.verticalSpacing = 0;
        glCompTypeTree.marginWidth = 0;
        glCompTypeTree.marginHeight = 0;
        glCompTypeTree.horizontalSpacing = 0;
        compTypeTree.setLayout(glCompTypeTree);
        compTypeTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        compTypeTree.setBackground(sash.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Label title = new Label(compInfo, SWT.VERTICAL);
        title.setText("Reusable types");
        title.setFont(FontUtils.getBoldFont(title.getFont()));
        Color blue = new Color(compInfo.getDisplay(), 0, 0, 255);
        title.setForeground(blue);
        title.setBackground(sash.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        Label des = new Label(compInfo, SWT.VERTICAL);
        des.setText("Define the types reusable in " + modelName);
        des.setBackground(sash.getDisplay().getSystemColor(SWT.COLOR_WHITE));

        ToolBar toolBarTypeTree = createToolbarOnComposite(compTypeTree);

        typesViewer = new TreeViewer(compTypeTree, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        typesViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        // typesViewer.getControl().setLayoutData(
        // new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        addToolItems2TypeTreeToolBar(toolBarTypeTree);
        toolBarTypeTree.pack();

        // typesProvider = new TypesContentProvider(this.getSite(), xsdSchema, xobject);
        typesTreeContentProvider = new TypesTreeContentProvider(this.getSite(), xsdSchema);
        // typesViewer.setContentProvider(typesProvider);
        typesViewer.setContentProvider(typesTreeContentProvider);

        typesViewer.setFilters(new ViewerFilter[] { new SchemaRoleAccessFilter(null), new TypeNameFilter() });

        typesViewer.addSelectionChangedListener(new ISelectionChangedListener() {

            public void selectionChanged(SelectionChangedEvent e) {
                // sel= (StructuredSelection)e.getSelection();

            }
        });
        typesViewer.getTree().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseDown(MouseEvent e) {
                isSchemaSelected = false;
            }
        });
        typesViewer.setLabelProvider(new TypesLabelProvider());
        typesViewer.setSorter(typeTreeSorter);

        typesViewer.setInput(this.getSite());// getViewSite());
        typesViewer.getTree().addKeyListener(new KeyListener() {

            public void keyPressed(KeyEvent e) {

                IStructuredSelection selection = ((IStructuredSelection) typesViewer.getSelection());

                // delete
                if ((e.stateMask == 0) && (e.keyCode == SWT.DEL)) {

                    deleteConceptWrapAction.regisExtraClassToDel(XSDComplexTypeDefinitionImpl.class);
                    if (deleteConceptWrapAction.checkInDeletableType(selection.toArray())) {
                        deleteConceptWrapAction.prepareToDelSelectedItems(selection, viewer);
                        deleteConceptWrapAction.run();
                    } else {
                        deleteConceptWrapAction.clearExtraClassToDel();
                        deleteConceptWrapAction.regisExtraClassToDel(XSDSimpleTypeDefinitionImpl.class);
                        if (deleteConceptWrapAction.checkInDeletableType(selection.toArray())) {
                            deleteConceptWrapAction.prepareToDelSelectedItems(selection, viewer);
                            deleteConceptWrapAction.run();
                        } else {
                            MessageDialog.openWarning(getSite().getShell(), "Warnning",
                                    "Please select the deletable node and try again!");
                        }
                    }
                }
            }

            public void keyReleased(KeyEvent e) {

            }

        });

    }

    public void refreshData() {
        try {

            WSDataModel wsObject = (WSDataModel) (xobject.getWsObject());
            String s;
            s = wsObject.getDescription() == null ? "" : wsObject.getDescription();
            if (!s.equals(descriptionText.getText()))
                descriptionText.setText(s);
            String schema = Util.formatXsdSource(wsObject.getXsdSchema());

            XSDSchema xsd = Util.createXsdSchema(schema, xobject);
            // provider.setXsdSchema(xsd);
            schemaTreeContentProvider.setXsdSchema(xsd);
            // ((XSDTreeContentProvider) viewer.getContentProvider()).setFilter(dataModelFilter);
            getSchemaRoleFilterFromSchemaTree().setDataModelFilter(dataModelFilter);
            // viewer.setAutoExpandLevel(3);
            viewer.setInput(getSite());
            // refresh types
            // typesProvider.setXsdSchema(xsd);
            typesTreeContentProvider.setXsdSchema(xsd);
            typesViewer.setInput(getSite());
            reConfigureXSDSchema(true);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ErrorExceptionDialog.openError(this.getSite().getShell(), "Error refreshing the page", "Error refreshing the page: "
                    + e.getLocalizedMessage());
        }
    }

    public void SaveWithForce(IProgressMonitor monitor) {
        doSave(monitor);
    }

    public int save(String xsd) {
        try {
            WSDataModel wsObject = (WSDataModel) (xobject.getWsObject());
            wsObject.setDescription(descriptionText.getText() == null ? "" : descriptionText.getText());
            String schema = xsd;
            if (xsd == null) {
                // schema = ((XSDTreeContentProvider) viewer.getContentProvider()).getXSDSchemaAsString();
                schema = ((SchemaTreeContentProvider) viewer.getContentProvider()).getXSDSchemaAsString();
                xsdSchema = ((SchemaTreeContentProvider) viewer.getContentProvider()).getXsdSchema();
            }
            // remove 'targetNamespace', 'xmlns' attr, for it will cause xsd validate error, the xsd is
            // invalid
            // schema = schema.replaceAll("targetNamespace\\s*=\\s*\"[^\"]*\"", "");
            // schema = schema.replaceAll("xmlns\\s*=\\s*\"[^\"]*\"", "");
            // end
            wsObject.setXsdSchema(schema);

            // fliu added '<xsd:import namespace="http://www.w3.org/2001/XMLSchema"/>', which is meant to make xsdSchema
            // compatible with allNNI and other new simple Types
            XSDImport xsdImport = XSDFactory.eINSTANCE.createXSDImport();
            xsdImport.setNamespace("http://www.w3.org/2001/XMLSchema");
            if (xsdSchema == null) {
                // xsdSchema = ((XSDTreeContentProvider) viewer.getContentProvider()).getXsdSchema();
                xsdSchema = ((SchemaTreeContentProvider) viewer.getContentProvider()).getXsdSchema();

                if (xsdSchema == null)
                    xsdSchema = Util.createXsdSchema(schema, xobject);
            }

            EList<XSDSchemaContent> elist = xsdSchema.getContents();
            for (XSDSchemaContent cnt : elist) {
                if (cnt instanceof XSDImport) {
                    XSDImport imp = (XSDImport) cnt;
                    if (imp.getNamespace().equals("http://www.w3.org/2001/XMLSchema")) {//$NON-NLS-1$
                        xsdImport = null;
                        break;
                    }
                }
            }
            if (xsdImport != null) {
                xsdSchema.getContents().add(0, xsdImport);
                wsObject.setXsdSchema(schema);
            }
            validateSchema();

            // save to db
            XtentisPort port = Util.getPort(new URL(xobject.getEndpointAddress()), xobject.getUniverse(), xobject.getUsername(),
                    xobject.getPassword());
            port.putDataModel(new WSPutDataModel((WSDataModel) wsObject));
            RoleAssignmentDialog.doSave(port, ((WSDataModel) wsObject).getName(), "Data Model");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ErrorExceptionDialog.openError(this.getSite().getShell(), "Error committing the page",
                    CommonUtil.getErrMsgFromException(e));
            return 1;
        }
        dirty = false;
        firePropertyChange(PROP_DIRTY);
        return 0;
    }

    protected void commit() {
        save(null);
    }

    protected void createActions() {
        // this.newConceptAction = new XSDNewConceptAction(this);
        this.deleteConceptAction = new XSDDeleteConceptAction(this);
        this.newBrowseItemAction = new XSDNewBrowseItemViewAction(this);
        this.deleteConceptWrapAction = new XSDDeleteConceptWrapAction(this);
        this.newElementAction = new XSDNewElementAction(this);
        this.deleteElementAction = new XSDDeleteElementAction(this);
        // this.deleteValidationRule=new XSDDeleteValidationRulesAction(this);
        this.changeToComplexTypeAction = new XSDChangeToComplexTypeAction(this, false);
        this.changeSubElementGroupAction = new XSDChangeToComplexTypeAction(this, true);
        this.deleteParticleAction = new XSDDeleteParticleAction(this);
        this.newParticleFromTypeAction = new XSDNewParticleFromTypeAction(this);
        this.newParticleFromParticleAction = new XSDNewParticleFromParticleAction(this);
        this.newGroupFromTypeAction = new XSDNewGroupFromTypeAction(this);

        this.editParticleAction = new XSDEditParticleAction(this);
        this.editConceptAction = new XSDEditConceptAction(this);
        this.editElementAction = new XSDEditElementAction(this);
        this.deleteIdentityConstraintAction = new XSDDeleteIdentityConstraintAction(this);
        this.editIdentityConstraintAction = new XSDEditIdentityConstraintAction(this);
        this.newIdentityConstraintAction = new XSDNewIdentityConstraintAction(this);
        this.deleteXPathAction = new XSDDeleteXPathAction(this);
        this.newXPathAction = new XSDNewXPathAction(this);
        this.editXPathAction = new XSDEditXPathAction(this);
        this.changeToSimpleTypeAction = new XSDChangeToSimpleTypeAction(this);
        this.changeBaseTypeAction = new XSDChangeBaseTypeAction(this);
        this.getXPathAction = new XSDGetXPathAction(this);
        this.setAnnotationLabelAction = new XSDSetAnnotationLabelAction(this);
        this.setAnnotationDescriptionsAction = new XSDSetAnnotationDescriptionsAction(this);
        this.setAnnotationForeignKeyAction = new XSDSetAnnotationForeignKeyAction(this, dataModelName);
        visibleRuleAction = new XSDVisibleRuleAction(this, dataModelName);
        defaultValueRuleAction = new XSDDefaultValueRuleAction(this, dataModelName);
        this.setAnnotationFKFilterAction = new XSDSetAnnotationFKFilterAction(this, dataModelName);
        this.setAnnotationForeignKeyInfoAction = new XSDSetAnnotationForeignKeyInfoAction(this, dataModelName);
        this.setAnnotationWriteAction = new XSDSetAnnotationWriteAction(this);
        this.setAnnotationWrapWriteAction = new XSDSetAnnotationWrapWriteAction(this);
        this.setAnnotationNoAction = new XSDSetAnnotationNoAction(this, dataModelName);
        this.setAnnotationWrapNoAction = new XSDSetAnnotationWrapNoAction(this, dataModelName);

        this.setAnnotationDisplayFomatAction = new XSDSetAnnotaionDisplayFormatAction(this);
        this.setAnnotationLookupFieldsAction = new XSDAnnotationLookupFieldsAction(this);
        this.setAnnotationPrimaryKeyInfoAction = new XSDSetAnnotationPrimaryKeyInfoAction(this);
        // this.copyConceptAction = new XSDCopyConceptAction(this);
        // this.pasteConceptAction = new XSDPasteConceptAction(this);

        // this.setAnnotationTargetSystemsAction = new XSDSetAnnotationTargetSystemsAction(this,dataModelName);
        // this.setAnnotationSchematronAction = new XSDSetAnnotationSchematronAction(this,dataModelName);
        // this.deleteAnnotationSchematronAction = new XSDDeleteAnnotationSchematronAction(this,dataModelName);
        // this.setAnnotationSourceSystemAction = new XSDSetAnnotationSourceSystemAction(
        // this);
        // this.setAnnotationDocumentationAction = new XSDSetAnnotationDocumentationAction(
        // this);
        this.deleteTypeDefinition = new XSDDeleteTypeDefinition(this);
        this.newComplexTypeAction = new XSDNewComplexTypeDefinition(this);
        this.newSimpleTypeAction = new XSDNewSimpleTypeDefinition(this);

        this.editComplexTypeAction = new XSDEditComplexTypeAction(this);
        this.setFacetMsgAction = new XSDSetFacetMessageAction(this);
        deleteConceptWrapAction.regisDelAction(XSDElementDeclarationImpl.class, deleteConceptAction);
        deleteConceptWrapAction.regisDelAction(XSDParticleImpl.class, deleteParticleAction);
        deleteConceptWrapAction.regisDelAction(XSDIdentityConstraintDefinitionImpl.class, deleteIdentityConstraintAction);
        deleteConceptWrapAction.regisDelAction(XSDXPathDefinitionImpl.class, deleteXPathAction);
        deleteConceptWrapAction.regisDelAction(null, deleteElementAction);
        deleteConceptWrapAction.regisDelAction(XSDComplexTypeDefinitionImpl.class, deleteTypeDefinition);
        deleteConceptWrapAction.regisDelAction(XSDSimpleTypeDefinitionImpl.class, deleteTypeDefinition);
    }

    private int getElementType(Object decl) {

        if (Util.getParent(decl) == decl) {
            if (Util.checkConcept((XSDElementDeclaration) decl)) {
                return 0;
            }
            return 1;
        }
        if (decl instanceof XSDComplexTypeDefinition)
            return 2;
        if (decl instanceof XSDIdentityConstraintDefinition)
            return 3;
        if (decl instanceof XSDXPathDefinition)
            return 4;
        // if(decl instanceof XSDSimpleTypeDefinition
        // &&!((XSDSimpleTypeDefinition)decl).getSchema().getSchemaForSchemaNamespace().equals((
        // (XSDSimpleTypeDefinition) decl).getTargetNamespace()))
        if (decl instanceof XSDSimpleTypeDefinition)
            return 5;
        if (decl instanceof XSDAnnotation)
            return 6;
        if (decl instanceof XSDParticle)
            return 7;
        if (decl instanceof XSDModelGroup)
            return 8;
        if (decl instanceof XSDWhiteSpaceFacet)
            return 201;
        if (decl instanceof XSDLengthFacet)
            return 202;
        if (decl instanceof XSDMinLengthFacet)
            return 203;
        if (decl instanceof XSDMaxLengthFacet)
            return 204;
        if (decl instanceof XSDTotalDigitsFacet)
            return 205;
        if (decl instanceof XSDFractionDigitsFacet)
            return 206;
        if (decl instanceof XSDMaxInclusiveFacet)
            return 207;
        if (decl instanceof XSDMaxExclusiveFacet)
            return 208;
        if (decl instanceof XSDMinInclusiveFacet)
            return 209;
        if (decl instanceof XSDMinExclusiveFacet)
            return 210;
        if (decl instanceof XSDPatternFacet)
            return 211;
        if (decl instanceof XSDEnumerationFacet)
            return 212;
        if (decl instanceof Element) {
            Element e = (Element) decl;
            if (e.getLocalName().equals("appinfo")) {
            }
            String source = e.getAttribute("source");
            if (source != null) {
                if (source.startsWith("X_Label_")) {
                    return 101;
                } else if (source.equals("X_ForeignKey")) {
                    return 102;
                } else if (source.equals("X_ForeignKeyInfo")) {
                    return 103;
                } else if (source.equals("X_SourceSystem")) {
                    return 104;
                } else if (source.equals("X_TargetSystem")) {
                    return 105;
                } else if (source.startsWith("X_Description_")) {
                    return 106;
                } else if (source.equals("X_Write")) {
                    return 107;
                } else if (source.equals("X_Hide")) {
                    return 108;
                } else if (source.equals("X_Schematron")) {
                    return 109;
                } else if (source.startsWith("X_Facet_")) {
                    return 110;
                } else if (source.startsWith("X_Workflow")) {
                    return 111;
                } else if (source.startsWith("X_ForeignKey_Filter")) {
                    return 112;
                } else if (source.startsWith("X_Display_Format_")) {
                    return 113;
                } else if (source.equals("X_Lookup_Field")) {
                    return 114;
                } else if (source.equals("X_PrimaryKeyInfo")) {
                    return 115;
                } else if (source.equals("X_Visible_Rule")) {
                    return 116;
                } else if (source.equals("X_Default_Value_Rule")) {
                    return 117;
                }
            }

        }
        return -1;
    }

    private void hookDoubleClickAction() {
        viewer.addDoubleClickListener(new DoubleClickListener(viewer) {
        });
        typesViewer.addDoubleClickListener(new DoubleClickListener(typesViewer) {
        });
    }

    protected void initxsdEditFacetAction(String element) {
        new XSDEditFacetAction(this, element).run();

    }

    private void hookContextMenu() {
        menuMgr = new MenuManager();
        menuMgr.setRemoveAllWhenShown(true);
        menuMgr.addMenuListener(new IMenuListener() {

            public void menuAboutToShow(IMenuManager manager) {
                DataModelMainPage.this.fillContextMenu(manager, false);
            }
        });
        Menu menu = menuMgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        getSite().registerContextMenu(menuMgr, viewer);
    }

    private void hookTypesContextMenu() {
        typesMenuMgr = new MenuManager();
        typesMenuMgr.setRemoveAllWhenShown(true);
        typesMenuMgr.addMenuListener(new IMenuListener() {

            public void menuAboutToShow(IMenuManager manager) {
                DataModelMainPage.this.fillContextMenu(manager, true);
            }
        });
        Menu menu = typesMenuMgr.createContextMenu(typesViewer.getControl());
        typesViewer.getControl().setMenu(menu);
        getSite().registerContextMenu(typesMenuMgr, typesViewer);
    }

    protected void fillContextMenu(IMenuManager manager, boolean isType) {

        IStructuredSelection selection;
        if (!isType)
            selection = ((IStructuredSelection) viewer.getSelection());
        else
            selection = ((IStructuredSelection) typesViewer.getSelection());

        Object[] selectedObjs = selection.toArray();
        Object obj = selection.getFirstElement();

        if (!isType)
            manager.add(new XSDNewConceptAction(this));
        else {
            manager.add(newComplexTypeAction);
            manager.add(newSimpleTypeAction);
            // add by ymli; fix the bug:0012228. Made the multiple types can be deleted.
            XSDDeleteTypeDefinition deleteTypeDefinition1;
            if (selectedObjs.length > 1)
                deleteTypeDefinition1 = new XSDDeleteTypeDefinition(this, true);
            else
                deleteTypeDefinition1 = new XSDDeleteTypeDefinition(this, false);
            if (selectedObjs.length >= 1 && deleteTypeDefinition1.isTypeDefinition(selectedObjs))
                manager.add(deleteTypeDefinition1);

            deleteConceptWrapAction.regisExtraClassToDel(XSDComplexTypeDefinitionImpl.class);

            if (selectedObjs.length > 1 && deleteConceptWrapAction.checkInDeletableType(selectedObjs)) {
                deleteConceptWrapAction.prepareToDelSelectedItems(selection, viewer);
            }

            if (selectedObjs.length > 1 && deleteConceptWrapAction.outPutDeleteActions() != null) {
                manager.add(deleteConceptWrapAction.outPutDeleteActions());

                if (deleteConceptWrapAction.checkOutAllConcept(selectedObjs))
                    manager.add(newBrowseItemAction);
            }
        }
        manager.add(new Separator());
        if ((selection == null) || (selection.getFirstElement() == null)) {
            manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
            // add by ymli, fix bug 0009770
            String title = "";
            if (WorkbenchClipboard.getWorkbenchClipboard().getConcepts().size() == 1)
                title = "Paste Entity";
            else if (WorkbenchClipboard.getWorkbenchClipboard().getConcepts().size() > 1)
                title = "Paste Entities";

            XSDPasteConceptAction pasteConceptAction = new XSDPasteConceptAction(this, title);
            if (pasteConceptAction.checkInPasteType()) {
                manager.add(new Separator());
                manager.add(pasteConceptAction);
            }
            return;
        }

        // Element Declaration
        if (obj instanceof XSDElementDeclaration && selectedObjs.length == 1) {
            // check if concept or "just" element

            XSDElementDeclaration decl = (XSDElementDeclaration) obj;
            boolean isConcept = Util.checkConcept(decl);
            if (!Util.IsAImporedElement(decl, xsdSchema)) {
                if (isConcept) {
                    manager.add(editConceptAction);
                    manager.add(deleteConceptAction);
                    manager.add(newBrowseItemAction);
                } else {
                    manager.add(editElementAction);
                    manager.add(deleteElementAction);
                }

                // add by ymli. fix bug 0009770 add the copy of concepts
                XSDCopyConceptAction copyConceptAction = new XSDCopyConceptAction(this, "Copy Entity");
                if (Util.checkInCopy(selectedObjs)) {
                    manager.add(new Separator());
                    manager.add(copyConceptAction);

                }
                /*
                 * boolean isMulti = false; if(WorkbenchClipboard.getWorkbenchClipboard().getConcepts().size()>1)
                 * isMulti = true;
                 */
                String title = "";
                if (WorkbenchClipboard.getWorkbenchClipboard().getConcepts().size() > 1)
                    title = "Paste Entities";
                else if (WorkbenchClipboard.getWorkbenchClipboard().getConcepts().size() == 1)
                    title = "Paste Entity";
                else if (WorkbenchClipboard.getWorkbenchClipboard().getParticles().size() > 1)
                    title = "Paste Elements";
                else if (WorkbenchClipboard.getWorkbenchClipboard().getParticles().size() == 1)
                    title = "Paste Element";

                XSDPasteConceptAction pasteConceptAction = new XSDPasteConceptAction(this, title);
                if (pasteConceptAction.checkInPasteType())
                    manager.add(pasteConceptAction);

                manager.add(new Separator());
                // manager.add(new XSDNewConceptAction(this));
                manager.add(newElementAction);
                manager.add(new Separator());
                manager.add(changeToComplexTypeAction);
                manager.add(changeToSimpleTypeAction);
                // add by fliu, see bugID:0009157
                if (((XSDElementDeclaration) obj).getTypeDefinition() instanceof XSDSimpleTypeDefinition) {
                    manager.add(setFacetMsgAction);
                    manager.add(setAnnotationDisplayFomatAction);
                }
                manager.add(new Separator());
                manager.add(newIdentityConstraintAction);
            } else {
                if (isConcept) {
                    manager.add(newBrowseItemAction);
                }
                // manager.add(new XSDNewConceptAction(this));
                manager.add(newElementAction);
            }

            // Annotations
            if (!Util.IsAImporedElement(decl, xsdSchema) || !Util.IsAImporedElement(decl.getTypeDefinition(), xsdSchema))
                setAnnotationActions2(obj, manager);
        }
        // add by rhou.fix bug 0012073: Enable to create element from sub element group
        if (obj instanceof XSDModelGroup) {
            manager.add(new Separator());
            manager.add(newParticleFromTypeAction);
            manager.add(new Separator());
            manager.add(changeSubElementGroupAction);
        }

        if (obj instanceof XSDParticle && selectedObjs.length == 1) {
            XSDTerm term = ((XSDParticle) obj).getTerm();
            if (!(term instanceof XSDWildcard)) {
                if (term instanceof XSDElementDeclaration) {
                    manager.add(editParticleAction);
                    if (!Util.IsAImporedElement(term, xsdSchema) || term.getContainer() instanceof XSDSchema) {
                        // manager.add(newGroupFromParticleAction);
                        manager.add(newParticleFromParticleAction);
                        if (term instanceof XSDModelGroup) {
                            manager.add(newParticleFromTypeAction);
                            manager.add(newGroupFromTypeAction);
                        }
                        manager.add(deleteParticleAction);
                        // edit by ymli. fix the bug:0011523
                        XSDCopyConceptAction copyConceptAction = new XSDCopyConceptAction(this, "Copy Element");
                        manager.add(copyConceptAction);

                        manager.add(new Separator());
                        manager.add(changeToComplexTypeAction);
                        manager.add(changeToSimpleTypeAction);
                        // add by fliu, see bugID:0009157

                        manager.add(new Separator());
                        // manager.add(newIdentityConstraintAction);
                        if (term instanceof XSDElementDeclaration) {
                            // Annotations
                            XSDTypeDefinition type = ((XSDElementDeclaration) term).getTypeDefinition();
                            // if(!Util.IsAImporedElement(type, xsdSchema)){
                            setAnnotationActions(obj, manager);
                            // }
                            if (((XSDElementDeclaration) term).getTypeDefinition() instanceof XSDSimpleTypeDefinition) {
                                manager.add(setFacetMsgAction);
                                manager.add(setAnnotationDisplayFomatAction);
                            }
                            // Xpath
                            manager.add(new Separator());
                            manager.add(getXPathAction);

                        }
                    }
                }
            }
        }

        if (obj instanceof XSDComplexTypeDefinition && selectedObjs.length == 1
                && ((XSDComplexTypeDefinition) obj).getTargetNamespace() == null) {
            if (!isType && !Util.IsAImporedElement((XSDParticle) obj, xsdSchema)) {
                manager.add(newParticleFromTypeAction);
                manager.add(newGroupFromTypeAction);
            }
            String ns = ((XSDComplexTypeDefinition) obj).getTargetNamespace();
            if (ns == null && !Util.IsAImporedElement((XSDComplexTypeDefinition) obj, xsdSchema)) {
                // manager.add(newParticleFromTypeAction);
                // manager.add(deleteTypeDefinition);
                // add by rhou.fix bug 0012073: Enable to create element from sub element group
                manager.add(new Separator());
                manager.add(newParticleFromTypeAction);
                manager.add(new Separator());
                manager.add(editComplexTypeAction);
            }
        }

        if (obj instanceof XSDIdentityConstraintDefinition && selectedObjs.length == 1
                && ((XSDIdentityConstraintDefinition) obj).getTargetNamespace() == null
                && !Util.IsAImporedElement((XSDIdentityConstraintDefinition) obj, xsdSchema)) {
            manager.add(editIdentityConstraintAction);
            manager.add(deleteIdentityConstraintAction);
            manager.add(newIdentityConstraintAction);
            manager.add(new Separator());
            manager.add(newXPathAction);
        }

        if (obj instanceof XSDXPathDefinition && selectedObjs.length == 1
                && ((XSDXPathDefinition) obj).getSchema().getTargetNamespace() == null
                && !Util.IsAImporedElement((XSDXPathDefinition) obj, xsdSchema)) {
            manager.add(editXPathAction);
            manager.add(newXPathAction);
            XSDXPathDefinition xpath = (XSDXPathDefinition) obj;
            if (xpath.getVariety().equals(XSDXPathVariety.FIELD_LITERAL))
                manager.add(deleteXPathAction);
        }
        // for the anonymous simpleType
        if (obj instanceof XSDSimpleTypeDefinition
                && selectedObjs.length == 1
                && (!Util.IsAImporedElement((XSDSimpleTypeDefinition) obj, xsdSchema) || ((XSDSimpleTypeDefinition) obj)
                        .getName() == null)) {
            XSDSimpleTypeDefinition typedef = (XSDSimpleTypeDefinition) obj;

            // if (!typedef.getSchema().getSchemaForSchemaNamespace().equals(
            // typedef.getTargetNamespace())) {
            manager.add(changeBaseTypeAction);
            manager.add(new Separator());
            EList list = typedef.getBaseTypeDefinition().getValidFacets();
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                String element = (String) iter.next();
                manager.add(new XSDEditFacetAction(this, element));
            }

            // }
        }

        // if (obj instanceof XSDAnnotation
        // && selectedObjs.length == 1) {
        // if(((XSDAnnotation)obj).getSchema().getTargetNamespace() == null &&
        // !Util.IsAImporedElement((XSDAnnotation)obj, xsdSchema)){
        // setAnnotationActions(manager);
        //
        // }
        // else if(!Util.IsAImporedElement((XSDAnnotation)obj, xsdSchema))
        // {
        // setAnnotationActions(manager);
        //
        // }
        // }

        if (selectedObjs.length > 1 && deleteConceptWrapAction.checkInDeletableType(selectedObjs)) {
            deleteConceptWrapAction.prepareToDelSelectedItems(selection, viewer);
        }

        if (selectedObjs.length > 1 && deleteConceptWrapAction.checkInAllElementType(selectedObjs)) {
            manager.add(newBrowseItemAction);
        }

        if (selectedObjs.length > 1 && deleteConceptWrapAction.outPutDeleteActions() != null) {

            if (!isType)
                manager.add(deleteConceptWrapAction.outPutDeleteActions());

            String title = "";
            if (Util.checkInCopyTypeElement(selectedObjs))
                title = "Copy Entities";
            else if (Util.checkInCOpyTypeParticle(selectedObjs))
                title = "Copy Elements";
            XSDCopyConceptAction copyConceptAction = new XSDCopyConceptAction(this, title);
            // XSDPasteConceptAction pastyConceptAction = new XSDPasteConceptAction(this,true);

            if (Util.checkInCopy(selectedObjs)) {
                // manager.add(new Separator());
                manager.add(copyConceptAction);
                // manager.add(pastyConceptAction);

            }
            // add by ymli; fix bug:0016645
            if (selectedObjs.length > 1 && isXSDParticles(selectedObjs)) {
                manager.add(newParticleFromParticleAction);
            }

            /*
             * boolean isMulti = false; if(WorkbenchClipboard.getWorkbenchClipboard().getConcepts().size()>1) isMulti =
             * true;
             * 
             * 
             * XSDPasteConceptAction pasteConceptAction = new XSDPasteConceptAction(this,"paste");
             * if(pasteConceptAction.checkInPasteType()) manager.add(pasteConceptAction);
             */

        }

        // add by ymli. fix bug 0009771
        if (Util.IsEnterPrise()) {
            if (selectedObjs.length > 1 && setAnnotationWrapWriteAction.checkInWriteType(selectedObjs)) {
                manager.add(new Separator());
                manager.add(setAnnotationWrapWriteAction);
                // fix bug 0016982: Set role with no access, and Set the workflow access menu actions action are gone
                // if (checkMandatoryElement(selectedObjs))
                manager.add(setAnnotationWrapNoAction);
            }
        }
        // available models
        java.util.List<IAvailableModel> availablemodels = AvailableModelUtil.getAvailableModels();
        for (IAvailableModel model : availablemodels) {
            model.fillContextMenu(obj, manager, this, dataModelName);
        }

        manager.add(new Separator());

        drillDownAdapter.addNavigationActions(manager);
        // Other plug-ins can contribute there actions here
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        deleteConceptWrapAction.clearExtraClassToDel();
    }

    private void setAnnotationActions(Object obj, IMenuManager manager) {

        if (obj instanceof XSDElementDeclaration) {
            manager.add(setAnnotationDescriptionsAction);
            manager.add(setAnnotationLookupFieldsAction);
            manager.add(setAnnotationPrimaryKeyInfoAction);
        }
        if (obj instanceof XSDParticle) {
            manager.add(setAnnotationDescriptionsAction);
            manager.add(setAnnotationLabelAction);
            manager.add(setAnnotationForeignKeyAction);
            manager.add(setAnnotationFKFilterAction);
            manager.add(setAnnotationForeignKeyInfoAction);
        }
        if (Util.IsEnterPrise()) {
            manager.add(setAnnotationWriteAction);
            // fix bug 0016982: Set role with no access, and Set the workflow access menu actions action are gone
            // if (checkMandatoryElement(obj))
            manager.add(setAnnotationNoAction);
            if (obj instanceof XSDParticle) {
                manager.add(visibleRuleAction);
                manager.add(defaultValueRuleAction);
            }
        }
        // available models
        java.util.List<IAvailableModel> availablemodels = AvailableModelUtil.getAvailableModels();
        for (IAvailableModel model : availablemodels) {
            model.fillContextMenu(obj, manager, this, dataModelName);
        }
        // manager.add(setAnnotationSchematronAction);
        // manager.add(setAnnotationSourceSystemAction);
        // manager.add(setAnnotationTargetSystemsAction);
        // manager.add(deleteValidationRule);
    }

    private void setAnnotationActions2(Object obj, IMenuManager manager) {

        if (obj instanceof XSDElementDeclaration) {
            manager.add(setAnnotationLabelAction);
            manager.add(setAnnotationDescriptionsAction);
            manager.add(setAnnotationLookupFieldsAction);
            manager.add(setAnnotationPrimaryKeyInfoAction);
        }

        if (obj instanceof XSDParticle) {
            manager.add(setAnnotationDescriptionsAction);
            manager.add(setAnnotationLabelAction);
        }
        if (Util.IsEnterPrise()) {
            manager.add(setAnnotationWriteAction);
            // fix bug 0016982: Set role with no access, and Set the workflow access menu actions action are gone
            // if (checkMandatoryElement(obj))
            manager.add(setAnnotationNoAction);
            if (obj instanceof XSDParticle) {
                manager.add(visibleRuleAction);
                manager.add(defaultValueRuleAction);
            }
        }
        // available models
        java.util.List<IAvailableModel> availablemodels = AvailableModelUtil.getAvailableModels();
        for (IAvailableModel model : availablemodels) {
            model.fillContextMenu(obj, manager, this, dataModelName);
        }
        // manager.add(setAnnotationTargetSystemsAction);
        // manager.add(setAnnotationSourceSystemAction);
        // manager.add(setAnnotationSchematronAction);
        // manager.add(setAnnotationDocumentationAction);
        // manager.add(deleteValidationRule);
    }

    private boolean checkMandatoryElement(Object obj) {
        if (obj instanceof XSDParticle)
            if (((XSDParticle) obj).getMinOccurs() > 0)
                return false;
        return true;

    }

    private boolean checkMandatoryElement(Object[] objArray) {
        for (Object obj : objArray) {
            if (obj instanceof XSDParticle)
                if (((XSDParticle) obj).getMinOccurs() > 0)
                    return false;
                else
                    continue;

        }
        return true;

    }

    /**
     * @author ymli
     * @param objArray check if all the elements of objArray are XSDParticle
     * @return
     */
    private boolean isXSDParticles(Object[] objArray) {
        for (Object obj : objArray) {
            if (!(obj instanceof XSDParticle)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns and XSDSchema Object from an xsd
     * 
     * @param schema
     * @return
     * @throws Exception
     */
    public XSDSchema getXSDSchema(String schema) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setValidating(false);
        InputSource source = new InputSource(new StringReader(schema));
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(source);

        if (xsdSchema == null) {
            xsdSchema = Util.createXsdSchema(schema, xobject);
        } else {
            xsdSchema.setDocument(document);
        }

        return xsdSchema;
    }

    public XSDSchema getXSDSchema() {
        return xsdSchema;
    }

    public String getXSDSchemaString() throws Exception {
        return ((SchemaTreeContentProvider) viewer.getContentProvider()).getXSDSchemaAsString();
    }

    public TreeViewer getTreeViewer() {
        if (isSchemaSelected) {
            return viewer;
        } else {
            return typesViewer;
        }
    }

    public TreeViewer getElementsViewer() {
        return viewer;
    }

    public TreeViewer getTypesViewer() {
        return typesViewer;
    }

    public void setXsdSchema(XSDSchema xsd) {
        ((ISchemaContentProvider) viewer.getContentProvider()).setXsdSchema(xsd);
        ((ISchemaContentProvider) typesViewer.getContentProvider()).setXsdSchema(xsd);
        xsdSchema = xsd;
    }

    public ISchemaContentProvider getSchemaContentProvider() {
        return (ISchemaContentProvider) viewer.getContentProvider();
    }

    public ISchemaContentProvider getTypeContentProvider() {
        return (ISchemaContentProvider) typesViewer.getContentProvider();
    }

    public void refresh() {
        // TreeItem[] items = viewer.getTree().getSelection();
        // TreeItem[] items1 = typesViewer.getTree().getSelection();
        // viewer.refresh(false);
        // typesViewer.refresh(false);
        //
        // if (items.length > 0) {
        // viewer.getControl().setFocus();
        // viewer.setSelection(new StructuredSelection(items[0].getData()));
        // }
        // if (items1.length > 0) {
        // typesViewer.getControl().setFocus();
        // typesViewer.setSelection(new StructuredSelection(items1[0].getData()));
        // }

        viewer.refresh(true);
        typesViewer.refresh(true);

        if (viewer.getTree().isFocusControl()) {
            ISelection oldSelection = viewer.getSelection();
            viewer.setSelection(null);
            viewer.setSelection(oldSelection);
        }

        if (typesViewer.getTree().isFocusControl()) {
            ISelection oldSelection = typesViewer.getSelection();
            typesViewer.setSelection(null);
            typesViewer.setSelection(oldSelection);
        }
    }

    public void markDirtyWithoutCommit() {
        dirty = true;
        firePropertyChange(PROP_DIRTY);
    }

    public void markDirty() {
        markDirtyWithoutCommit();
    }

    public boolean isDirty() {
        // initializeOperationHistory();
        // UndoActionHandler handler = (UndoActionHandler)
        // getEditorSite().getActionBars().getGlobalActionHandler("undo");
        // handler.update();
        return dirty;
    }

    public XSDSchema reConfigureXSDSchema(boolean force) {

        return xsdSchema;
    }

    /**
     * @author achen
     */
    private void initializeOperationHistory() {

        if (undoContext == null) {
            undoContext = new ObjectUndoContext(this.toString(), xobject.getDisplayName());
        }

        PlatformUI.getWorkbench().getOperationSupport().getOperationHistory().setLimit(undoContext, undoLimit);

        UndoRedoActionGroup undoRedoGroup = new UndoRedoActionGroup(getSite(), undoContext, true);

        undoRedoGroup.fillActionBars(getEditorSite().getActionBars());

        /*
         * // Install an operation approver for this undo context that prompts // according to a user preference.
         * operationApprover = new PromptingUserApprover(undoContext);
         * getOperationHistory().addOperationApprover(operationApprover);
         */
    }

    public ObjectUndoContext getUndoContext() {
        return undoContext;
    }

    public Map<Integer, String> getUndoActionTrack() {
        Map<Integer, String> map = contextToUndoAction.get(undoContext);
        if (map == null) {
            map = new HashMap<Integer, String>();
            contextToUndoAction.put(undoContext, map);
        }

        return map;
    }

    public Map<Integer, String> getRedoActionTrack() {
        Map<Integer, String> map = contextToRedoAction.get(undoContext);
        if (map == null) {
            map = new HashMap<Integer, String>();
            contextToRedoAction.put(undoContext, map);
        }

        return map;
    }

    public int getValue(String name) {
        int value = 0;
        for (int i = 0; i < name.length(); i++)
            value = value * 10 + name.charAt(i);
        return value;
    }

    private void openXSDParticle(TreeViewer targetViewer) {
        if (targetViewer == null)
            return;

        if (targetViewer.equals(viewer))
            openXSDParticleInSchemaTree();
        else if (targetViewer.equals(typesViewer))
            openXSDParticleInTypeTree();
    }

    private void openXSDParticleInTypeTree() {
        for (Object eachSelectedObj : getSelectionInTypeTree()) {
            typesViewer.collapseToLevel(eachSelectedObj, 3);
            if (eachSelectedObj instanceof XSDModelGroup)
                typesViewer.expandToLevel(eachSelectedObj, 1);
            else if (eachSelectedObj instanceof XSDComplexTypeDefinition)
                typesViewer.expandToLevel(eachSelectedObj, 1);
        }
    }

    private void openXSDParticleInSchemaTree() {

        for (Object eachSelectedObj : getSelectionInSchemaTree()) {
            viewer.collapseToLevel(eachSelectedObj, 3);
            if (eachSelectedObj instanceof XSDModelGroup) {
                viewer.expandToLevel(eachSelectedObj, 1);
            }
            if (eachSelectedObj instanceof XSDElementDeclaration) {
                viewer.expandToLevel(eachSelectedObj, 1);
                XSDTypeDefinition type = ((XSDElementDeclaration) eachSelectedObj).getTypeDefinition();
                if (type instanceof XSDComplexTypeDefinition) {
                    XSDModelGroup mg = (XSDModelGroup) ((XSDParticle) ((XSDComplexTypeDefinition) type).getContent()).getTerm();
                    viewer.expandToLevel(mg, 1);
                }
            }
        }

        // Iterator it = sel.iterator();
        // while (it.hasNext()) {
        // Object obj = it.next();
        // targetTreeViewer.collapseToLevel(obj, 3);
        // if (obj instanceof XSDModelGroup) {
        // targetTreeViewer.expandToLevel(obj, 1);
        // }
        // if (obj instanceof XSDElementDeclaration) {
        // targetTreeViewer.expandToLevel(obj, 1);
        // XSDTypeDefinition type = ((XSDElementDeclaration) obj).getTypeDefinition();
        // if (type instanceof XSDComplexTypeDefinition) {
        // XSDModelGroup mg = (XSDModelGroup) ((XSDParticle) ((XSDComplexTypeDefinition) type).getContent()).getTerm();
        // targetTreeViewer.expandToLevel(mg, 1);
        // }
        // }
        //
        // }
    }

    private void createCompDropTarget() {
        DropTarget dropTarget = new DropTarget(sash, DND.DROP_MOVE | DND.DROP_LINK);
        // dropTarget.setTransfer(new ByteArrayTransfer[] { });
        dropTarget.setTransfer(new TreeObjectTransfer[] { TreeObjectTransfer.getInstance() });
        dropTarget.addDropListener(new DropTargetAdapter() {

            public void dragEnter(DropTargetEvent event) {
            }

            public void dragLeave(DropTargetEvent event) {
            }

            public void dragOver(DropTargetEvent event) {
                event.feedback |= DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
            }

            public void drop(DropTargetEvent event) {
                List<String> nameList = new ArrayList<String>();
                if (dropTargetValidate(event, nameList)) {
                    if (MessageDialog.openConfirm(sash.getShell(), "Confirm", "Do you want to include or import schema?")) {
                        HashMap<String, String> customTypesMap = ResourcesUtil.getResourcesMapFromURI(uriPre
                                + TreeObject.CUSTOM_TYPES_URI);
                        List<String> customTypeList = new ArrayList<String>();
                        for (String key : nameList) {
                            customTypeList.add(customTypesMap.get(key));
                        }
                        doImportSchema(customTypeList, new ArrayList<String>());
                    }
                }
            }
        });

    }

    private boolean dropTargetValidate(DropTargetEvent event, List<String> customTypeNamelist) {
        if (event.data == null)
            return false;
        Object obj = event.data;
        if (obj instanceof TreeObject[]) {
            uriPre = ((TreeObject[]) obj)[0].getServerRoot().getEndpointIpAddress();
            for (TreeObject ob : (TreeObject[]) obj) {
                if (ob.getType() == TreeObject.CUSTOM_TYPES_RESOURCE)
                    customTypeNamelist.add(ob.getDisplayName());
                else
                    return false;
            }
            return true;
        }
        return false;
    }

    private void validateElementation() throws IllegalAccessException {
        HashMap<String, Boolean> elemCntMap = new HashMap<String, Boolean>();
        EList<XSDElementDeclaration> elems = xsdSchema.getElementDeclarations();
        for (XSDElementDeclaration elem : elems) {
            if (elemCntMap.get(elem.getName()) == Boolean.TRUE) {
                throw new IllegalAccessException("XSD: The Element may not have duplicate name " + elem.getName());
            }
            elemCntMap.put(elem.getName(), Boolean.TRUE);
        }
    }

    private void validateType() throws IllegalAccessException {
        HashMap<String, Boolean> typeCntMap = new HashMap<String, Boolean>();
        EList<XSDTypeDefinition> types = xsdSchema.getTypeDefinitions();
        String tail = "";
        for (XSDTypeDefinition type : types) {
            if (type instanceof XSDComplexTypeDefinition) {
                tail = "complex";
            } else {
                tail = "simple";
            }
            if (typeCntMap.get(type.getName() + tail + type.getTargetNamespace()) == Boolean.TRUE) {
                // throw new IllegalAccessException("XSD: The " + tail + " type may not have duplicate name " +
                // type.getName());
            }
            typeCntMap.put(type.getName() + tail + type.getTargetNamespace(), Boolean.TRUE);
        }
    }

    private void performDeletion(List<String> toDels) {
        List<XSDSchemaContent> impToDels = new ArrayList<XSDSchemaContent>();
        List<String> nsToDels = new ArrayList<String>();
        for (String delName : toDels) {
            EList<XSDSchemaContent> list = xsdSchema.getContents();
            for (XSDSchemaContent cnt : list) {
                if (cnt instanceof XSDImportImpl) {
                    XSDImportImpl imp = (XSDImportImpl) cnt;
                    String ns = imp.getNamespace();
                    String loct = imp.getSchemaLocation();
                    if (ns == null || loct == null) {
                        continue;
                    }
                    if (loct.equals(delName)) {
                        Iterator<Map.Entry<String, String>> iter = xsdSchema.getQNamePrefixToNamespaceMap().entrySet().iterator();
                        while (iter.hasNext()) {
                            Map.Entry<String, String> entry = iter.next();
                            if (entry.getValue().equalsIgnoreCase(ns)) {
                                nsToDels.add(entry.getKey());
                                impToDels.add(cnt);
                                break;
                            }
                        }
                    }
                } else if (cnt instanceof XSDIncludeImpl) {
                    XSDIncludeImpl inude = (XSDIncludeImpl) cnt;
                    if (inude.getSchemaLocation().equals(delName)) {
                        impToDels.add(cnt);
                        break;
                    }
                }
            }
        }
        if (!impToDels.isEmpty() && xsdSchema.getContents().containsAll(impToDels)) {
            xsdSchema.updateElement();
            xsdSchema.getReferencingDirectives().clear();
            Map<String, String> map = xsdSchema.getQNamePrefixToNamespaceMap();
            for (String ns : nsToDels) {
                map.remove(ns);
            }
            for (XSDSchemaContent cnt : impToDels) {
                xsdSchema.getContents().remove(cnt);
            }

            xsdSchema.updateElement();
            setXsdSchema(xsdSchema);
        }

    }

    private void performImport(List<String> list) throws Exception {
        Pattern httpUrl = Pattern.compile("(http|https|ftp):(\\//|\\\\)(.*)(\\:)+(.*)");

        for (String fileName : list) {
            Matcher match = httpUrl.matcher(fileName);
            if (match.matches()) {
                importSchemaWithURL(fileName);
            } else {
                importSchemaFromFile(fileName);
            }
        }
    }

    private void importSchemaWithURL(String url) throws Exception {
        String response = Util.getResponseFromURL(url, xobject);
        InputSource source = new InputSource(new StringReader(response));
        importSchema(source, url);
    }

    private void importSchemaFromFile(String fileName) throws Exception {
        InputSource source = null;
        Pattern httpUrl = Pattern.compile("^(http|https|ftp):(\\//|\\\\)(.*)(\\.)+(xsd)$");
        Matcher match = httpUrl.matcher(fileName);
        if (match.matches()) {
            URL url = new URL(fileName);
            String urlContent = IOUtils.toString(url.openConnection().getInputStream());
            urlContent = urlContent.replaceAll("<!DOCTYPE(.*?)>", "");
            source = new InputSource(IOUtils.toInputStream(urlContent));
            importSchema(source, fileName);
        } else {
            String inputType = fileName.substring(fileName.lastIndexOf("."));
            if (!inputType.equals(".xsd"))
                return;
            File file = new File(fileName);

            source = new InputSource(new FileInputStream(file));
            importSchema(source, file.getAbsolutePath());
        }

    }

    private void importSchema(InputSource source, String uri) throws Exception {
        String ns = "";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setValidating(false);

        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(source);
        ns = document.getDocumentElement().getAttribute("targetNamespace");
        if (xsdSchema == null)
            xsdSchema = getXSDSchema(Util.nodeToString(document));
        else {
            WSDataModel wsObject = (WSDataModel) (xobject.getWsObject());
            xsdSchema = Util.createXsdSchema(wsObject.getXsdSchema(), xobject);
        }
        boolean exist = false;
        for (int i = 0; i < xsdSchema.getContents().size(); i++) {
            XSDSchemaContent xsdComp = xsdSchema.getContents().get(i);
            if (ns != null && !ns.equals("")) {
                // import xsdschema
                if (xsdComp instanceof XSDImport && ((XSDImport) xsdComp).getNamespace().equals(ns)) {
                    for (Map.Entry entry : xsdSchema.getQNamePrefixToNamespaceMap().entrySet()) {
                        if (entry.getValue().equals(((XSDImport) xsdComp).getNamespace())) {
                            exist = true;
                            break;
                        }
                    }
                    break;
                }
            } else {
                // include xsdschema
                if (xsdComp instanceof XSDInclude) {
                    String xsdLocation = ((XSDInclude) xsdComp).getSchemaLocation();
                    if (xsdLocation.equals(uri)) {
                        exist = true;
                        break;
                    }
                }
            }
        }

        if (!exist) {
            if (ns != null && !ns.equals("")) {
                int last = ns.lastIndexOf("/");
                xsdSchema.getQNamePrefixToNamespaceMap().put(ns.substring(last + 1).replaceAll("[\\W]", ""), ns);
                XSDImport xsdImport = XSDFactory.eINSTANCE.createXSDImport();
                xsdImport.setNamespace(ns);
                xsdImport.setSchemaLocation(uri);
                xsdSchema.getContents().add(0, xsdImport);
            } else {
                XSDInclude xsdInclude = XSDFactory.eINSTANCE.createXSDInclude();
                xsdInclude.setSchemaLocation(uri);
                xsdSchema.getContents().add(0, xsdInclude);
            }
            String xsd = Util.nodeToString(xsdSchema.getDocument());
            // xsdSchema = Util.createXsdSchema(xsd, xobject);
            setXsdSchema(xsdSchema);
            WSDataModel wsObject = (WSDataModel) (xobject.getWsObject());
            wsObject.setXsdSchema(xsd);
        }
    }

    private ToolBar createToolbarOnComposite(Composite parentComp) {

        Color backColor = sash.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);

        Composite compToolBarBackground = new Composite(parentComp, SWT.BORDER);
        final GridLayout glToolBarBackground = new GridLayout();
        glToolBarBackground.verticalSpacing = 0;
        glToolBarBackground.marginWidth = 0;
        glToolBarBackground.marginHeight = 0;
        glToolBarBackground.horizontalSpacing = 0;
        compToolBarBackground.setLayout(glToolBarBackground);
        final GridData gdToolBarBackground = new GridData(SWT.FILL, SWT.CENTER, true, false);
        compToolBarBackground.setLayoutData(gdToolBarBackground);
        compToolBarBackground.setBackground(backColor);

        Composite compToolBar = new Composite(compToolBarBackground, SWT.NONE);
        compToolBar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, true));
        final GridLayout glToolBar = new GridLayout();
        glToolBar.verticalSpacing = 0;
        glToolBar.marginWidth = 0;
        glToolBar.marginHeight = 0;
        glToolBar.horizontalSpacing = 0;
        compToolBar.setLayout(glToolBar);
        compToolBar.setBackground(backColor);

        ToolBar resultToolBar = new ToolBar(compToolBar, SWT.FLAT | SWT.HORIZONTAL);
        resultToolBar.setBackground(backColor);

        return resultToolBar;
    }

    private void addToolItems2SchemaTreeToolBar(ToolBar parentToolBar) {

        createExpandToolItem(parentToolBar, viewer);
        createCollapseToolItem(parentToolBar, viewer);
        createExpandGroupToolItem(parentToolBar, viewer);
        createMoveUpToolItem(parentToolBar, viewer);
        createMoveDownToolItem(parentToolBar, viewer);

        createSortByLabelToolItem(parentToolBar, viewer);
        createFiltUniqueElementToolItem(parentToolBar, viewer);

        if (Util.IsEnterPrise())
            createFilterToolItem(parentToolBar, viewer);

    }

    private void addToolItems2TypeTreeToolBar(ToolBar parentToolBar) {

        createExpandToolItem(parentToolBar, typesViewer);
        createCollapseToolItem(parentToolBar, typesViewer);
        createExpandGroupToolItem(parentToolBar, typesViewer);
        createMoveUpToolItem(parentToolBar, typesViewer);
        createMoveDownToolItem(parentToolBar, typesViewer);

        createSortByLabelToolItem(parentToolBar, typesViewer);

        if (Util.IsEnterPrise())
            createFilterToolItem(parentToolBar, typesViewer);
    }

    private ToolItem createFilterToolItem(ToolBar parentToolBar, final TreeViewer targetTreeViewer) {

        ToolItem filterToolItem = new ToolItem(parentToolBar, SWT.PUSH);
        filterToolItem.setImage(ImageCache.getCreatedImage(EImage.FILTER_PS.getPath()));
        filterToolItem.setToolTipText("Filter...");

        filterToolItem.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                dataModelFilter = new DataModelFilter("", false, false, false, true);
                DataModelFilterDialog dataModelFilterDialog = new DataModelFilterDialog(getSite().getShell(), xobject,
                        dataModelFilter, getSchemaElementNameFilterDesByTreeViewer(targetTreeViewer));

                if (dataModelFilterDialog.open() == Dialog.OK) {
                    getSchemaRoleFilterFromTreeViewer(targetTreeViewer).setDataModelFilter(dataModelFilter);
                    getSchemaTopElementNameFilterFromTreeViewer(targetTreeViewer).setNameFilterDes(
                            getSchemaElementNameFilterDesByTreeViewer(targetTreeViewer));
                    targetTreeViewer.refresh();
                }
            }
        });

        return null;
    }

    private ToolItem createExpandToolItem(ToolBar parentToolBar, final TreeViewer targetTreeViewer) {

        ToolItem expanedToolItem = new ToolItem(parentToolBar, SWT.PUSH);
        expanedToolItem.setImage(ImageCache.getCreatedImage(EImage.EXPAND.getPath()));
        expanedToolItem.setToolTipText("Expand...");
        expanedToolItem.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection) targetTreeViewer.getSelection();
                for (Object eachSelectedObj : selection.toArray())
                    targetTreeViewer.expandToLevel(eachSelectedObj, 3);
            }
        });

        return expanedToolItem;
    }

    private ToolItem createCollapseToolItem(ToolBar parentToolBar, final TreeViewer targetTreeViewer) {

        ToolItem collapseToolItem = new ToolItem(parentToolBar, SWT.PUSH);
        collapseToolItem.setImage(ImageCache.getCreatedImage(EImage.COLLAPSE.getPath()));
        collapseToolItem.setToolTipText("Collapse...");
        collapseToolItem.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection) targetTreeViewer.getSelection();
                for (Object eachSelectedObj : selection.toArray())
                    targetTreeViewer.collapseToLevel(eachSelectedObj, 3);
            }
        });

        return collapseToolItem;
    }

    private ToolItem createExpandGroupToolItem(ToolBar parentToolBar, final TreeViewer targetTreeViewer) {

        ToolItem expandGroupToolItem = new ToolItem(parentToolBar, SWT.PUSH);
        expandGroupToolItem.setImage(ImageCache.getCreatedImage(EImage.ACTIVITY_CATEGORY.getPath()));
        expandGroupToolItem.setToolTipText("Expand ModelGroup...");
        expandGroupToolItem.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                openXSDParticle(targetTreeViewer);
            }
        });

        return expandGroupToolItem;
    }

    private ToolItem createMoveUpToolItem(ToolBar parentToolBar, final TreeViewer targetTreeViewer) {

        ToolItem moveUpToolItem = new ToolItem(parentToolBar, SWT.PUSH);
        moveUpToolItem.setImage(ImageCache.getCreatedImage(EImage.PREV_NAV.getPath()));
        moveUpToolItem.setToolTipText("UP...");
        moveUpToolItem.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                stepUp(targetTreeViewer);
            }
        });

        return moveUpToolItem;
    }

    private ToolItem createMoveDownToolItem(ToolBar parentToolBar, final TreeViewer targetTreeViewer) {

        ToolItem moveDownToolItem = new ToolItem(parentToolBar, SWT.PUSH);
        moveDownToolItem.setImage(ImageCache.getCreatedImage(EImage.NEXT_NAV.getPath()));
        moveDownToolItem.setToolTipText("DOWN...");
        moveDownToolItem.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                stepDown(targetTreeViewer);
            }
        });

        return moveDownToolItem;
    }

    private ToolItem createSortByLabelToolItem(ToolBar parentToolBar, final TreeViewer targetTreeViewer) {

        final ToolItem sortByLabelToolItem = new ToolItem(parentToolBar, SWT.PUSH);
        sortByLabelToolItem.setImage(ImageCache.getCreatedImage(EImage.SORT_DESC.getPath()));
        sortByLabelToolItem.setToolTipText("SORT DESC...");
        sortByLabelToolItem.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                sortByLabel(sortByLabelToolItem, targetTreeViewer);
            }
        });

        return sortByLabelToolItem;
    }

    private ToolItem createFiltUniqueElementToolItem(ToolBar parentToolBar, final TreeViewer targetTreeViewer) {

        final ToolItem filtUniqueElementToolItem = new ToolItem(parentToolBar, SWT.CHECK);
        filtUniqueElementToolItem.setImage(ImageCache.getCreatedImage(EImage.ELEMENT_ONLY_SKIP.getPath()));
        filtUniqueElementToolItem.setToolTipText("Hide Non-Unique Elements...");
        filtUniqueElementToolItem.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                filtUniqueElement(filtUniqueElementToolItem, targetTreeViewer);
            }
        });

        return filtUniqueElementToolItem;

    }

    private Object[] getSelectionInSchemaTree() {
        return getSelectionInViewer(viewer);
    }

    private Object[] getSelectionInTypeTree() {
        return getSelectionInViewer(typesViewer);
    }

    private Object[] getSelectionInViewer(Viewer viewer) {

        if (viewer.getSelection() == null || !(viewer.getSelection() instanceof IStructuredSelection))
            return new Object[0];

        return ((IStructuredSelection) viewer.getSelection()).toArray();

    }

    private SchemaRoleAccessFilter getSchemaRoleFilterFromSchemaTree() {
        return (SchemaRoleAccessFilter) getTreeViewerFilter(viewer, SchemaRoleAccessFilter.class);
    }

    private SchemaRoleAccessFilter getSchemaRoleFilterFromTreeViewer(TreeViewer targetViewer) {
        return (SchemaRoleAccessFilter) getTreeViewerFilter(targetViewer, SchemaRoleAccessFilter.class);
    }

    private SchemaNameFilter getSchemaTopElementNameFilterFromTreeViewer(TreeViewer targetViewer) {
        return (SchemaNameFilter) getTreeViewerFilter(targetViewer, SchemaNameFilter.class);
    }

    private SchemaUniqueElementFilter getSchemaUniqueElementFilterFromSchemaTree() {
        return (SchemaUniqueElementFilter) getTreeViewerFilter(viewer, SchemaUniqueElementFilter.class);
    }

    private ViewerFilter getTreeViewerFilter(TreeViewer viewer, Class<? extends ViewerFilter> filterType) {

        if (viewer == null || filterType == null)
            return null;

        for (ViewerFilter eachFilter : viewer.getFilters()) {
            if (filterType.isAssignableFrom(eachFilter.getClass()))
                return eachFilter;
        }

        return null;
    }

    private SchemaElementNameFilterDes getSchemaElementNameFilterDesByTreeViewer(TreeViewer targetViewer) {

        if (typesViewer.equals(targetViewer))
            return typeElementNameFilterDes;

        return schemaElementNameFilterDes;
    }

    private void sortByLabel(ToolItem sortByLabelToolItem, TreeViewer targetTreeViewer) {

        SchemaElementSorter sorter = getTreeViewerSchemaElementSorter(targetTreeViewer);

        if (sorter == null)
            return;

        sortByLabelToolItem.setImage(getToolImageAfterClickSortByLabel(sorter.isSortedASC()));
        sortByLabelToolItem.setToolTipText(getTooltipAfterClickSortByLabel(sorter.isSortedASC()));

        sorter.setSortedType(!sorter.isSortedASC());

        targetTreeViewer.refresh();
    }

    private void filtUniqueElement(ToolItem filtUniqueElementToolItem, TreeViewer targetTreeViewer) {

        SchemaUniqueElementFilter filter = getSchemaUniqueElementFilterFromSchemaTree();

        filter.setSelector(filtUniqueElementToolItem.getSelection());
        if (filtUniqueElementToolItem.getSelection())
            filtUniqueElementToolItem.setToolTipText("Show Non-Unique Elements...");
        else
            filtUniqueElementToolItem.setToolTipText("Hide Non-Unique Elements...");

        targetTreeViewer.refresh();
    }

    private SchemaElementSorter getTreeViewerSchemaElementSorter(TreeViewer targetTreeViewer) {

        if (targetTreeViewer.getSorter() instanceof SchemaElementSorter)
            return (SchemaElementSorter) targetTreeViewer.getSorter();

        return null;
    }

    private Image getToolImageAfterClickSortByLabel(boolean isCurrentASC) {

        if (isCurrentASC)
            return ImageCache.getCreatedImage(EImage.SORT_ASC.getPath());

        return ImageCache.getCreatedImage(EImage.SORT_DESC.getPath());
    }

    private String getTooltipAfterClickSortByLabel(boolean isCurrentASC) {

        if (isCurrentASC)
            return "SORT ASC...";

        return "SORT DESC...";
    }

    private class DoubleClickListener implements IDoubleClickListener {

        private TreeViewer viewer;

        public DoubleClickListener(TreeViewer viewer) {
            this.viewer = viewer;
        }

        public void doubleClick(DoubleClickEvent event) {
            IStructuredSelection selection = ((IStructuredSelection) viewer.getSelection());
            int elem = getElementType(selection.getFirstElement());
            // available models
            java.util.List<IAvailableModel> availablemodels = AvailableModelUtil.getAvailableModels();
            for (IAvailableModel model : availablemodels) {
                model.doubleClickOnElement(elem, DataModelMainPage.this, dataModelName);
            }
            switch (elem) {
            case 0:
                editConceptAction.run();
                break;
            case 1:
                editElementAction.run();
                break;
            case 2:
                editComplexTypeAction.run();
                break;
            case 3:
                editIdentityConstraintAction.run();
                break;
            case 4:
                editXPathAction.run();
                break;
            case 5:
                changeBaseTypeAction.run();
                break;
            case 6:
                setAnnotationDescriptionsAction.run();
                break;
            case 7:
                editParticleAction.run();
                break;
            case 8:
                changeSubElementGroupAction.run();
                break;
            case 201:
                // new XSDEditFacetAction(viewer,"whiteSpace").run();
                initxsdEditFacetAction("whiteSpace");
                break;
            case 202:
                initxsdEditFacetAction("length");
                break;
            case 203:
                initxsdEditFacetAction("minLength");
                break;
            case 204:
                initxsdEditFacetAction("maxLength");
                break;
            case 205:
                initxsdEditFacetAction("totalDigits");
                break;
            case 206:
                initxsdEditFacetAction("fractionDigits");
                break;
            case 207:
                initxsdEditFacetAction("maxInclusive");
                break;
            case 208:
                initxsdEditFacetAction("maxExclusive");
                break;
            case 209:
                initxsdEditFacetAction("minInclusive");
                break;
            case 210:
                initxsdEditFacetAction("minExclusive");
                break;
            case 211:
                initxsdEditFacetAction("pattern");
                break;
            case 212:
                initxsdEditFacetAction("enumeration");
                break;
            case 101:
                setAnnotationLabelAction.run();
                break;
            case 102:
                setAnnotationForeignKeyAction.run();
                break;
            case 103:
                setAnnotationForeignKeyInfoAction.run();
                break;
            case 112:
                setAnnotationFKFilterAction.run();
                break;
            case 104:
                // setAnnotationSourceSystemAction.run();
                break;
            case 105:
                // setAnnotationTargetSystemsAction.run();
                break;
            case 106:
                setAnnotationDescriptionsAction.run();
                break;
            case 107:
                setAnnotationWriteAction.run();
                break;
            case 108:
                if (Util.IsEnterPrise()) {
                    setAnnotationNoAction.run();
                }
                break;
            case 110:
                setFacetMsgAction.run();
                break;
            case 113:
                setAnnotationDisplayFomatAction.run();
                break;
            case 114:
                setAnnotationLookupFieldsAction.run();
                break;
            case 115:
                setAnnotationPrimaryKeyInfoAction.run();
                break;
            case 116:
                visibleRuleAction.run();
                break;
            case 117:
                defaultValueRuleAction.run();
                break;
            case -1:
                if (drillDownAdapter.canGoInto() == true)
                    drillDownAdapter.goInto();
            }
        }
    }

    public TreeObject getXObject() {
        return this.xobject;
    }

    public Control getMainControl() {
        return mainControl;
    }

    @Override
    public void dispose() {
        // clear operationhistory
        IOperationHistory history = PlatformUI.getWorkbench().getOperationSupport().getOperationHistory();
        for (IUndoableOperation op : history.getRedoHistory(undoContext)) {
            op.dispose();
            op = null;
        }
        for (IUndoableOperation op : history.getUndoHistory(undoContext)) {
            op.dispose();
            op = null;
        }
        history.dispose(undoContext, true, true, true);
        // clear the big objects,
        // provider = null;
        schemaTreeContentProvider = null;
        typesTreeContentProvider = null;
        schemaTreeSorter = null;
        typeTreeSorter = null;
        undoContext = null;
        xsdSchema = null;
        contextToUndoAction.clear();
        contextToRedoAction.clear();
        super.dispose();
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        monitor.beginTask("Saving...", 1);
        commit();
        monitor.done();
    }

    @Override
    public void doSaveAs() {
        // TODO Auto-generated method stub
        commit();
    }

    @Override
    public void init(IEditorSite site, IEditorInput input) throws PartInitException {
        setSite(site);
        setInput(input);
    }

    @Override
    public boolean isSaveAsAllowed() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void createPartControl(Composite parent) {

        mainControl = parent;
        parent.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
        parent.setLayout(new GridLayout(2, false));
        createCharacteristicsContent(WidgetFactory.getWidgetFactory(), parent);
        createActions();
    }

    @Override
    public void setFocus() {

    }

    public void modifyText(ModifyEvent arg0) {
        markDirty();
    }

    public ISelectionProvider getSelectionProvider() {
        return selectionProvider;
    }

    public WSDataModel getDataModel() {
        return datamodel;
    }

}
