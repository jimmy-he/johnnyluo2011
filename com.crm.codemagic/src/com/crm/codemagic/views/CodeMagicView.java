package com.crm.codemagic.views;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.crm.codemagic.creator.common.CodeCreator;
import com.crm.codemagic.creator.common.FileCreator;
import com.crm.codemagic.creator.common.FileUtil;
import com.crm.codemagic.creator.common.TemplateUtil;
import com.crm.codemagic.creator.java.JavaCreatorFactory;
import com.crm.codemagic.creator.xml.LanguageCreator;
import com.crm.codemagic.pdmparser.Column;
import com.crm.codemagic.pdmparser.DataBase;
import com.crm.codemagic.pdmparser.PdmParser;
import com.crm.codemagic.pdmparser.Table;
import com.crm.framework.util.Path;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view
 * shows data obtained from the model. The sample creates a dummy model on the
 * fly, but a real implementation would connect to the model available either in
 * this or another plug-in (e.g. the workspace). The view is connected to the
 * model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be
 * presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider
 * can be shared between views in order to ensure that objects of the same type
 * are presented in the same way everywhere.
 * <p>
 */

public class CodeMagicView extends ViewPart {
	private TableViewer viewer;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;

	/** 属性 显示PDM文件中所有表的树 */
	private Tree tree;
	/** 属性 生成的源代码的包名 */
	private Text text;
	/** 属性 生成源代码的用户名 */
	private Text author;
	/** 属性 项目名称文本输入域 */
	private Text projectText;
	/** 属性 PDM文件真实路径文本输入域(只读) */
	private Text pdmText;
	/** 属性 PDM文件选择对话框 */
	private FileDialog dialog;
	/** 属性 PDM文件真实路径的值 */
	private String pdmFilePath = "";

	private String workSpace = "";
	
	/** 属性 选择的项目路径 */
	private String projectPath = "";

	/** 属性 生成代码的group */
	private Group group3;
	
	/** 属性 展示表和字段详细信息的group */
	private Group group4;

	/** 属性 所能生成的代码的层 */
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private Button button8;

	/** 属性 保存PDM文件中数据库表名称的HashMap */
	private HashMap tableMap;
	/** 属性 选中的表名的数组 */
	private String[] tableNames;
	/** 属性 在右侧显示数据库表详细信息的表格 */
	private org.eclipse.swt.widgets.Table appTable;
	
	/** 属性 工作空间里的项目集合 */
	IProject[] ips;
	
	/** 属性 显示消息的文本区域 */
	private Text infoText;

	/*
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */

	class ViewContentProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}

		public Object[] getElements(Object parent) {
			return new String[] { "One", "Two", "Three" };
		}
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {
		public String getColumnText(Object obj, int index) {
			return getText(obj);
		}

		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	class NameSorter extends ViewerSorter {
	}

	/**
	 * The constructor.
	 */
	public CodeMagicView() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		// viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL |
		// SWT.V_SCROLL);
		// viewer.setContentProvider(new ViewContentProvider());
		// viewer.setLabelProvider(new ViewLabelProvider());
		// viewer.setSorter(new NameSorter());
		// viewer.setInput(getViewSite());
		// makeActions();
		// hookContextMenu();
		// hookDoubleClickAction();
		// contributeToActionBars();

		/** 调用创建界面的方法 */
		createContents(parent);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				CodeMagicView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection)
						.getFirstElement();
				showMessage("Double-click detected on " + obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(),
				"Code Magic", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		// viewer.getControl().setFocus();
	}

	/**
	 * 创建插件界面
	 * */
	private void createContents(Composite composite) {

		composite.setLayout(new GridLayout(2, false));
		/** 2:numColumns 列数 false:二列不等宽 */
		/** group1 start */
		{
			Group group1 = new Group(composite, SWT.NONE);
			group1.setText("所有表");
			GridData gridData = new GridData(SWT.CENTER, SWT.CENTER, false,
					false, 1, 2);
			gridData.widthHint = 160;
			gridData.heightHint = 250;
			group1.setLayoutData(gridData);
			group1.setLayout(new FillLayout());

			{
				Composite treeComposite = new Composite(group1, SWT.NONE);
				treeComposite
						.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
								| GridData.VERTICAL_ALIGN_FILL));
				treeComposite.setLayout(new FillLayout());
				{
					tree = new Tree(treeComposite, SWT.NONE | SWT.MULTI);
				}
			}
		}
		/** group1 end */
		/** group2 start */
		{
			Group group2 = new Group(composite, SWT.NONE);
			group2.setText("载入PDM文件");
			// 横向抢占式、纵向对齐式
			group2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
					| GridData.VERTICAL_ALIGN_FILL));
			group2.setLayout(new GridLayout());

			{
				Composite composite4 = new Composite(group2, SWT.NONE);
				composite4.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
						| GridData.VERTICAL_ALIGN_FILL));
				composite4.setLayout(new GridLayout(2, false));
				{
					new Label(composite4, SWT.NONE).setText("工作空间");
					Text text = new Text(composite4, SWT.BORDER);
					text.setEditable(false);
					text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

					org.eclipse.core.resources.IWorkspace myWorkspace 
						= org.eclipse.core.resources.ResourcesPlugin.getWorkspace();
					IWorkspaceRoot root = myWorkspace.getRoot();
					String rootPath = root.getLocation().toString();
					workSpace = rootPath;
					text.setText(rootPath);
				}
			}

			{
				Composite composite5 = new Composite(group2, SWT.NONE);
				composite5.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
						| GridData.VERTICAL_ALIGN_FILL));
				composite5.setLayout(new GridLayout(2, false));
				{
					new Label(composite5, SWT.NONE).setText("项目名称");
					//projectText = new Text(composite5, SWT.BORDER);
					//projectText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
					
					org.eclipse.core.resources.IWorkspace myWorkspace 
						= org.eclipse.core.resources.ResourcesPlugin.getWorkspace();
					ips = myWorkspace.getRoot().getProjects();
					ArrayList list = new ArrayList();
					for (int i = 0; i < ips.length; i++) {
						IProject ip = ips[i];
						String projectName = ip.getName();
						System.out.println("projectName = " + projectName);
						list.add(projectName);
					}
					String[] projectNames = new String[list.size()];
					Iterator it = list.iterator();
					for(int i=0;i<list.size();i++){
						projectNames[i] = (String)list.get(i);
					}
					Combo combo = new Combo(composite5, SWT.NONE|SWT.READ_ONLY);
					combo.setItems(projectNames);
					combo.addSelectionListener(new SelectionListener(){

						public void widgetDefaultSelected(SelectionEvent event) {
							
						}

						public void widgetSelected(SelectionEvent event) {
							Combo combo = (Combo)event.widget;
							String projectName = combo.getText();
							for(int i=0;i<ips.length;i++){
								IProject ip = ips[i];
								String tempName = ip.getName();
								if(tempName.equals(projectName)){
									String theProjectPath = ip.getLocation().toString();
									dialog.setFilterPath(theProjectPath); 
									pdmText.setText(theProjectPath);
									projectPath = theProjectPath;
									break;
								}								
							}
						}
						
					});
					
				}
			}

			{
				Composite composite6 = new Composite(group2, SWT.NONE);
				composite6.setLayoutData(new GridData(
						GridData.HORIZONTAL_ALIGN_FILL));
				composite6.setLayout(new GridLayout(3, false));
				{
					new Label(composite6, SWT.NONE).setText("数据库设计PDM文件");
					pdmText = new Text(composite6, SWT.BORDER);
					pdmText.setEditable(false);
					pdmText
							.setLayoutData(new GridData(
									GridData.FILL_HORIZONTAL));

					dialog = new FileDialog(composite.getShell(), SWT.SINGLE);
					//dialog.setFilterPath(workSpace); // 设置初始路径
					dialog.setFilterNames(new String[] { "数据库设计文件(*.pdm)" });
					dialog.setFilterExtensions(new String[] { "*.pdm" });

					Button button = new Button(composite6, SWT.NONE);
					button.addSelectionListener(new SelectionAdapter() {
						@Override
						public void widgetSelected(SelectionEvent e) {
							if(dialog.getFilterPath() == null || "".equals(dialog.getFilterPath())){
								
								MessageBox mb = new MessageBox(group3.getShell(),SWT.ICON_INFORMATION|SWT.OK);
								mb.setText("系统提示");
								mb.setMessage("请先选择一个项目。");
								mb.open();
								return;
							}
							pdmFilePath = dialog.open();
							if (pdmFilePath == null)
								return;
							pdmText.setText(pdmFilePath);
							/**
							 * 调用创建树的方法，将所选PDM文件里的所有表创建为树下的根节点
							 * */
							creatPdmTableTree(pdmFilePath);
						}
					});
					button.setText("载 入");
				}
			}
			
			{
				
			}

		}
		/** group2 end */
		
		{
			group4 = new Group(composite, SWT.NONE);
			group4.setText("详细信息");
			group4.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
					| GridData.VERTICAL_ALIGN_FILL));
			group4.setLayout(new GridLayout());
			
			/**显示表结构的Table*/
			Composite composite6 = new Composite(group4, SWT.NONE);
			composite6.setLayoutData(new GridData(
					GridData.FILL_BOTH));
			composite6.setLayout(new GridLayout(1, false));
			{
				appTable = new org.eclipse.swt.widgets.Table(composite6, SWT.BORDER);
				appTable.setLayoutData(new GridData(GridData.FILL_BOTH));
				appTable.setHeaderVisible(true);
				appTable.setLinesVisible(true);
				appTable.setVisible(false);
				TableColumn tableColumn1 = new TableColumn(appTable, SWT.NONE);
				tableColumn1.setWidth(40);
				tableColumn1.setText("id");
				TableColumn tableColumn2 = new TableColumn(appTable, SWT.NONE);
				tableColumn2.setWidth(130);
				tableColumn2.setText("名称");
				TableColumn tableColumn3 = new TableColumn(appTable, SWT.NONE);
				tableColumn3.setWidth(130);
				tableColumn3.setText("代码");
				TableColumn tableColumn4 = new TableColumn(appTable, SWT.NONE);
				tableColumn4.setWidth(50);
				tableColumn4.setText("长度");
				TableColumn tableColumn5 = new TableColumn(appTable, SWT.NONE);
				tableColumn5.setWidth(90);
				tableColumn5.setText("数据类型");
				TableColumn tableColumn6 = new TableColumn(appTable, SWT.NONE);
				tableColumn6.setWidth(90);
				tableColumn6.setText("Not Null");
				
			}
		}

		/** group3 start */
		{
			group3 = new Group(composite, SWT.NONE);
			group3.setText("生成源码");
			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.horizontalSpan = 2;
			group3.setLayoutData(gridData);
			group3.setLayout(new GridLayout());

			{
				Composite composite2 = new Composite(group3, SWT.NONE);
				composite2
						.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
				composite2.setLayout(new GridLayout(9, false));
				{
					new Label(composite2, SWT.NONE).setText("生成源码范围");
					button1 = new Button(composite2, SWT.CHECK);
					button1.setText("model");
					button1.setSelection(true);
					button2 = new Button(composite2, SWT.CHECK);
					button2.setText("page");
					button2.setSelection(true);
					button3 = new Button(composite2, SWT.CHECK);
					button3.setText("action");
					button3.setSelection(true);
					button4 = new Button(composite2, SWT.CHECK);
					button4.setText("service");
					button4.setSelection(true);
					button5 = new Button(composite2, SWT.CHECK);
					button5.setText("validate");
					button5.setSelection(true);
					button6 = new Button(composite2, SWT.CHECK);
					button6.setText("dao");
					button6.setSelection(true);
					button7 = new Button(composite2, SWT.CHECK);
					button7.setText("webService");
					button7.setSelection(true);
					button8 = new Button(composite2, SWT.CHECK);
					button8.setText("所有Base");
					button8.setSelection(true);
				}
			}

			{
				Composite composite4 = new Composite(group3, SWT.NONE);
				composite4.setLayoutData(new GridData(
						GridData.HORIZONTAL_ALIGN_FILL));
				composite4.setLayout(new GridLayout(5, false));
				{
					new Label(composite4, SWT.NONE).setText("创建人");
					author = new Text(composite4, SWT.BORDER);
					GridData grid = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
					grid.widthHint = 100;
					author.setLayoutData(grid);
					author.setText("");
					
					new Label(composite4, SWT.NONE).setText("源代码包名");

					text = new Text(composite4, SWT.BORDER);
					text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
					text.setText("com.crm");

					Button button = new Button(composite4, SWT.NONE);
					button.setText("生 成");
					button.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent event) {
							System.out.println("--开始生成源代码--");
							createSourceCode();
						}
					});
				}
			}
			{
				Composite composite4 = new Composite(group3, SWT.NONE);
				GridData gridData1 = new GridData(GridData.FILL_HORIZONTAL
						| GridData.FILL_VERTICAL);
				composite4.setLayoutData(gridData1);	
				composite4.setLayout(new GridLayout(1, false));
				{
					infoText = new Text(composite4,SWT.BORDER|SWT.READ_ONLY|SWT.MULTI|SWT.V_SCROLL|SWT.WRAP);
					GridData data = new GridData();
					data.horizontalAlignment = GridData.FILL; 
			        data.verticalAlignment = GridData.FILL; 
			        data.grabExcessHorizontalSpace = true;
			        data.grabExcessVerticalSpace = true;
					infoText.setLayoutData(data);
				}
			}
		}
		/** group3 end */

	}

	/**
	 * 在窗口的左上区域显示PDM文件中的所有表，以树状图显示
	 * 
	 * @param String
	 *            pdmFilePath PDM文件的真实物理路径
	 * @return null
	 * */
	private void creatPdmTableTree(String pdmFilePath) {

		tableMap = new HashMap();
		tree.removeAll();
		tree.redraw();
		DataBase dataBase = null;

		try {
			PdmParser pdmParser = new PdmParser();
			dataBase = pdmParser.getDataBase(pdmFilePath);
		} catch (Exception e) {
			MessageBox mb = new MessageBox(group3.getShell(),SWT.ICON_INFORMATION|SWT.OK);
			mb.setText("系统提示");
			mb.setMessage("载入PDM文件失败，请确认文件格式。" + e.getCause());
			mb.open();
			return;
		}
		List<Table> tables = dataBase.getTables();
		Iterator it = tables.iterator();
		while (it.hasNext()) {
			/** 循环处理表 */
			Table table = (Table) it.next();
			TreeItem tableNode = new TreeItem(tree, SWT.NONE);
			tableNode.setText(table.code);
			/** 将表放入以表名为KEY的HashMap中 */
			tableMap.put(table.code, table);

			List<Column> columns = table.columns;
			Iterator it2 = columns.iterator();
			while (it2.hasNext()) {
				Column column = (Column) it2.next();
				TreeItem columnNode = new TreeItem(tableNode, SWT.NONE);
				columnNode.setText(column.code);
			}
		}
		tree.redraw();
		/** 给treeItem绑定事件 */
		tree.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent event) {
			}

			public void widgetSelected(SelectionEvent event) {
				/** 先处理选中的这个节点 */
				TreeItem selectedItem = (TreeItem) event.item;
				String itemName = selectedItem.getText();
				boolean isTable = isTableNode(selectedItem);
				/**
				 * 如果选中的是表,则将表的信息显示在右侧的表中，否则显示字段详细信息
				 * */
				if (isTable) {
					Table table = (Table) tableMap.get(itemName);
					/** 调用在右侧显示表信息的方法 */
					showTableInfo(table);
				} else {
					TreeItem tableNode = selectedItem.getParentItem();
					Table table = (Table) tableMap.get(tableNode.getText());
					List<Column> columns = table.getColumns();
					Iterator it = columns.iterator();
					while (it.hasNext()) {
						Column column = (Column) it.next();
						if ((column.code).equals(itemName)) {
							showColumnInfo(column);
							break;
						}
					}
					selectedItem.setChecked(false);
				}
				TreeItem[] items = tree.getSelection();
				List tableNameList = new ArrayList();
				for (int i = 0; i < items.length; i++) {
					TreeItem treeItem = items[i];
					if (isTableNode(treeItem)) {
						tableNameList.add(treeItem.getText());
					}
				}
				tableNames = new String[tableNameList.size()];
				for (int i = 0; i < tableNameList.size(); i++) {
					tableNames[i] = (String) tableNameList.get(i);
				}
			}
		});
	}

	/**
	 * 生成所选表的源代码的方法
	 * */
	private String createSourceCode() {
		FileCreator fileCreator = new FileCreator();
		String pdmPath = this.pdmFilePath;
		if(projectPath.equals("")){
		}
		/**查看要生成源代码的用户名*/
		String authorStr = author.getText();
		if(authorStr == null || "".equals(authorStr)){					
			showMessageInfo("请输入要创建人姓名。");
			return "error";
		}
		
		/**查看要生成的源代码的包名*/
		String rootPackage = text.getText();
		if(rootPackage == null || "".equals(rootPackage)){					
			showMessageInfo("请输入要生成的源代码的包名。");
			return "error";
		}else{
			System.out.println(rootPackage);
			String[] domain = rootPackage.split("\\.");
			System.out.println(domain.length);
			String resultCode = "";
			if(domain.length < 4 ){
				resultCode = "源代码包名形式须为：com.crm.xxx.xxx";
			}else if(!domain[0].equals("com")){
				resultCode = "源代码包名形式须为：com.crm.xxx.xxx";
			}else if(!domain[1].equals("crm")){
				resultCode = "源代码包名形式须为：com.crm.xxx.xxx";
			}else if(!domain[2].equals("base") && !domain[2].equals("extend") && !domain[2].equals("app") && !domain[2].equals("framework")){
				resultCode = "一级空间只允许是：base extend app framework其中之一。";
			}
			if(!resultCode.equals("")){
				showMessageInfo("请输入正确的源代码的包名。" + resultCode);
				return "error";
			}

			/**查看所选择的表*/
			//String author = authorStr;
			String[] tables = this.tableNames;
			if(tables == null || tables.length < 1){
				showMessageInfo("您还没有选择表，请载入PDM文件后选择相关表。");
				return "error";
			}
			 
			/**查看需要生成代码的层*/
			List creators = getFileLayer();
			/**开始生成代码*/
			try{			 
				String result = fileCreator.create(pdmPath, projectPath, rootPackage, authorStr, tables,
						creators, "crm");
				infoText.setText(result);			 
			 }catch(Exception e){				 
				 showMessageInfo(e.getMessage());				 
				 StackTraceElement[] elements = e.getStackTrace();
				 StringBuffer buf = new StringBuffer();
				 buf.append(e.getMessage());
				 for(int i=0;i<elements.length;i++){
					 StackTraceElement element = elements[i];
					 buf.append(elements[i].toString());
				 }
				 infoText.setText(buf.toString());
				 return "error";
			 }
			 return null;
		}
	}

	/**
	 * 判断一个TreeItem是表节点还是字段节点
	 * */
	private boolean isTableNode(TreeItem treeItem) {
		String itemName = treeItem.getText();
		Iterator it3 = tableMap.keySet().iterator();
		/** 初始判断不是表 */
		boolean isTable = false;
		while (it3.hasNext()) {
			String tableCode = (String) it3.next();
			if (tableCode.equals(itemName)) {
				isTable = true;
				break;
			}
		}
		return isTable;
	}

	/**
	 * 在右侧表中显示表的信息的方法
	 * */
	private void showTableInfo(Table dataTable) {
		group4.setText("表结构详细信息");
		appTable.setVisible(true);
		appTable.removeAll();
		appTable.redraw();
		List<Column> columns = dataTable.getColumns();
		Iterator it = columns.iterator();
		while (it.hasNext()) {
			Column column = (Column) it.next();
			TableItem idItem = new TableItem(appTable, SWT.NONE);
			idItem
					.setText(new String[] { column.id, column.name,
							column.code, column.length + "", column.dataType,
							column.mandatory + "" });
		}
	}

	/**
	 * 在右侧表中显示字段的详细信息的方法
	 * */
	private void showColumnInfo(Column column) {
		group4.setText("表字段详细信息");
		appTable.setVisible(true);
		appTable.removeAll();
		appTable.redraw();
		TableItem idItem = new TableItem(appTable, SWT.NONE);
		idItem.setText(new String[] { column.id, column.name, column.code,
				column.length + "", column.dataType, column.mandatory + "" });
	}
	
	/**
	 * 获取需要生成代码的层
	 * */
	private List<CodeCreator> getFileLayer(){
		//要生成的文件类型
		List<CodeCreator> creators = new ArrayList();
		if(button1.getSelection()){
			creators.add(JavaCreatorFactory.newCreator("model"));
		}
		if(button2.getSelection()){
			creators.add(JavaCreatorFactory.newCreator("page"));
		}
		if(button3.getSelection()){
			creators.add(JavaCreatorFactory.newCreator("action"));
		}
		if(button4.getSelection()){
			creators.add(JavaCreatorFactory.newCreator("service"));
		}
		if(button5.getSelection()){
			creators.add(JavaCreatorFactory.newCreator("validate"));
		}
		if(button6.getSelection()){
			creators.add(JavaCreatorFactory.newCreator("dao"));
		}
		if(button7.getSelection()){
			creators.add(JavaCreatorFactory.newCreator("webService"));
			creators.add(JavaCreatorFactory.newCreator("iWebService"));
		}
		if(button8.getSelection()){
			creators.add(JavaCreatorFactory.newCreator("modelBase"));
			creators.add(JavaCreatorFactory.newCreator("pageBase"));
			creators.add(JavaCreatorFactory.newCreator("actionBase"));
			creators.add(JavaCreatorFactory.newCreator("serviceBase"));
			creators.add(JavaCreatorFactory.newCreator("validateBase"));
			creators.add(JavaCreatorFactory.newCreator("daoBase"));
			creators.add(JavaCreatorFactory.newCreator("webServiceBase"));
		}
		creators.add(new LanguageCreator());
		return creators;
	}
	
	private void showMessageInfo(String message){
		MessageBox mb = new MessageBox(group3.getShell(),SWT.ICON_INFORMATION|SWT.OK);
		mb.setText("系统提示");
		mb.setMessage(message);
		mb.open();
	}
}