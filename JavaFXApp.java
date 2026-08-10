package assignment;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavaFXApp extends Application {

    // ── State ────────────────────────────────────────────────────────────────
    private product[] products = new product[100];
    private int productCount = 0;
    private UserInfo user = new UserInfo();
    private ObservableList<product> productObsList = FXCollections.observableArrayList();

    // ── Colour palette ───────────────────────────────────────────────────────
    private static final String BG_DARK    = "#0f1117";
    private static final String BG_CARD    = "#1a1d27";
    private static final String BG_CARD2   = "#22263a";
    private static final String ACCENT     = "#4f8ef7";
    private static final String ACCENT2    = "#a78bfa";
    private static final String SUCCESS    = "#34d399";
    private static final String DANGER     = "#f87171";
    private static final String TEXT_MAIN  = "#e2e8f0";
    private static final String TEXT_MUTED = "#64748b";

    // ── Entry point ──────────────────────────────────────────────────────────
    @Override
    public void start(Stage primaryStage) {
        showLoginScreen(primaryStage);
    }

    // ════════════════════════════════════════════════════════════════════════
    // SCREEN 1 – Login / Welcome
    // ════════════════════════════════════════════════════════════════════════
    private void showLoginScreen(Stage stage) {
        VBox root = new VBox(24);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(60, 80, 60, 80));
        root.setStyle("-fx-background-color: " + BG_DARK + ";");

        // Banner
        Text banner = new Text("📦 Stock Management System");
        banner.setFont(Font.font("Georgia", FontWeight.BOLD, 26));
        banner.setFill(Color.web(ACCENT));

        // Date / time
        String dt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss"));
        Label dateLabel = new Label("🕐  " + dt);
        styleLabel(dateLabel, TEXT_MUTED, 12);

        // Group members
        VBox members = card();
        Label mTitle = new Label("Group Members");
        mTitle.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        mTitle.setTextFill(Color.web(ACCENT2));
        members.getChildren().addAll(mTitle,
                memberLabel("1. Cheng Qin Yi"),
                memberLabel("2. Guok Siok Wen"),
                memberLabel("3. Toh Ke Xuan"),
                memberLabel("4. Wong Chi Yee"));

        // Name input
        VBox nameBox = card();
        Label nameTitle = new Label("Enter your full name");
        styleLabel(nameTitle, TEXT_MUTED, 12);
        TextField nameField = new TextField();
        nameField.setPromptText("First name  Surname");
        styleTextField(nameField);

        Button enterBtn = accentButton("Enter System →", ACCENT);
        enterBtn.setMaxWidth(Double.MAX_VALUE);
        enterBtn.setOnAction(e -> {
            String fullName = nameField.getText().trim();
            if (fullName.isEmpty()) {
                showAlert("Please enter your name.", Alert.AlertType.WARNING);
                return;
            }
            // Replicate UserInfo logic without Scanner
            user = new UserInfo() {
                { /* set name via reflection-free trick */ }
            };
            // We'll call the internal logic directly:
            setUserInfoName(fullName);
            showMainScreen(stage);
        });

        nameBox.getChildren().addAll(nameTitle, nameField, enterBtn);
        root.getChildren().addAll(banner, dateLabel, members, nameBox);

        Scene scene = new Scene(root, 520, 560);
        stage.setTitle("SMS – Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // Replicate UserInfo logic without Scanner dependency
    private void setUserInfoName(String fullName) {
        // We extend UserInfo inline so we reuse its generateUserID logic.
        // Since UserInfo.getName() uses Scanner, we mirror its logic here.
        boolean hasSpace = fullName.contains(" ");
        String userID;
        if (hasSpace) {
            String[] parts = fullName.split(" ");
            String firstName = parts[0];
            String surname   = parts[parts.length - 1];
            userID = firstName.substring(0, 1).toUpperCase()
                   + surname.substring(0, 1).toUpperCase()
                   + surname.substring(1);
        } else {
            userID = "guest";
        }
        // Store in a wrapper so we can display later
        this.resolvedName   = fullName;
        this.resolvedUserID = userID;
    }

    private String resolvedName   = "";
    private String resolvedUserID = "";

    // ════════════════════════════════════════════════════════════════════════
    // SCREEN 2 – Main Dashboard
    // ════════════════════════════════════════════════════════════════════════
    private void showMainScreen(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + BG_DARK + ";");

        // ── Left sidebar ──────────────────────────────────────────────────
        VBox sidebar = new VBox(12);
        sidebar.setPadding(new Insets(28, 16, 28, 16));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: " + BG_CARD + ";");

        Text logo = new Text("📦 SMS");
        logo.setFont(Font.font("Georgia", FontWeight.BOLD, 20));
        logo.setFill(Color.web(ACCENT));

        Label userLabel = new Label("👤 " + resolvedName);
        styleLabel(userLabel, TEXT_MAIN, 12);
        Label idLabel = new Label("ID: " + resolvedUserID);
        styleLabel(idLabel, TEXT_MUTED, 11);

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + BG_CARD2 + ";");

        // Nav buttons
        Button btnAdd    = navButton("➕  Add Product");
        Button btnView   = navButton("📋  View Products");
        Button btnStock  = navButton("🔼  Add Stock");
        Button btnDeduct = navButton("🔽  Deduct Stock");
        Button btnDisc   = navButton("🚫  Discontinue");
        Button btnExit   = navButton("🚪  Exit");
        btnExit.setStyle(btnExit.getStyle() + "-fx-text-fill: " + DANGER + ";");

        // ── Center content area ───────────────────────────────────────────
        StackPane center = new StackPane();
        center.setPadding(new Insets(28));

        // Default view
        VBox defaultView = buildProductTableView();
        center.getChildren().add(defaultView);

        // Wiring nav buttons
        btnAdd.setOnAction(e -> {
            center.getChildren().setAll(buildAddProductView());
        });
        btnView.setOnAction(e -> {
            center.getChildren().setAll(buildProductTableView());
        });
        btnStock.setOnAction(e -> {
            center.getChildren().setAll(buildStockAdjustView(true));
        });
        btnDeduct.setOnAction(e -> {
            center.getChildren().setAll(buildStockAdjustView(false));
        });
        btnDisc.setOnAction(e -> {
            center.getChildren().setAll(buildDiscontinueView());
        });
        btnExit.setOnAction(e -> showExitScreen(stage));

        sidebar.getChildren().addAll(logo, userLabel, idLabel, sep,
                btnAdd, btnView, btnStock, btnDeduct, btnDisc,
                new Region(), btnExit);
        VBox.setVgrow(sidebar.getChildren().get(sidebar.getChildren().size() - 2), Priority.ALWAYS);

        root.setLeft(sidebar);
        root.setCenter(center);

        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("SMS – Dashboard");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    // ════════════════════════════════════════════════════════════════════════
    // PANEL – Product Table
    // ════════════════════════════════════════════════════════════════════════
    @SuppressWarnings("unchecked")
    private VBox buildProductTableView() {
        VBox box = new VBox(16);

        Label title = sectionTitle("📋 All Products");

        TableView<product> table = new TableView<>(productObsList);
        table.setStyle("-fx-background-color: " + BG_CARD + ";"
                + "-fx-text-fill: " + TEXT_MAIN + ";"
                + "-fx-border-color: transparent;");
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<product, Integer> colNo   = col("Item #",   "itemNumber");
        TableColumn<product, String>  colName = col("Name",     "productName");
        TableColumn<product, Integer> colQty  = col("Qty",      "quantity");
        TableColumn<product, Double>  colPric = col("Price(RM)","price");
        TableColumn<product, String>  colStat = new TableColumn<>("Status");
        colStat.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().isStatus() ? "✅ Active" : "❌ Discontinued"));

        table.getColumns().addAll(colNo, colName, colQty, colPric, colStat);
        table.setPlaceholder(new Label("No products yet. Use 'Add Product' to get started."));
        VBox.setVgrow(table, Priority.ALWAYS);

        // Detail pane on row click
        TextArea detail = new TextArea();
        detail.setEditable(false);
        detail.setPrefHeight(160);
        detail.setStyle("-fx-control-inner-background: " + BG_CARD2 + ";"
                + "-fx-text-fill: " + TEXT_MAIN + ";"
                + "-fx-font-family: 'Courier New'; -fx-font-size: 12;");
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) detail.setText(sel.toString());
        });

        box.getChildren().addAll(title, table, detail);
        return box;
    }

    // ════════════════════════════════════════════════════════════════════════
    // PANEL – Add Product
    // ════════════════════════════════════════════════════════════════════════
    private VBox buildAddProductView() {
        VBox box = new VBox(16);

        Label title = sectionTitle("➕ Add New Product");

        // Type selector
        ToggleGroup tg = new ToggleGroup();
        RadioButton rbFridge = styledRadio("Refrigerator", tg);
        RadioButton rbTV     = styledRadio("TV",           tg);
        RadioButton rbWM     = styledRadio("Washing Machine", tg);
        rbFridge.setSelected(true);
        HBox typeRow = new HBox(16, rbFridge, rbTV, rbWM);

        // Common fields
        GridPane grid = new GridPane();
        grid.setHgap(12); grid.setVgap(10);

        TextField fName = inputField("Product name");
        TextField fQty  = inputField("Quantity");
        TextField fPrice = inputField("Price (RM)");
        TextField fItem  = inputField("Item number");

        addRow(grid, 0, "Product Name",  fName);
        addRow(grid, 1, "Quantity",       fQty);
        addRow(grid, 2, "Price (RM)",     fPrice);
        addRow(grid, 3, "Item Number",    fItem);

        // Type-specific fields (shown/hidden)
        GridPane extraGrid = new GridPane();
        extraGrid.setHgap(12); extraGrid.setVgap(10);

        TextField fDoor  = inputField("e.g. French door");
        TextField fColor = inputField("e.g. Silver");
        TextField fCap   = inputField("Capacity in litres");
        TextField fScr   = inputField("e.g. OLED");
        TextField fRes   = inputField("e.g. 4K");
        TextField fSize  = inputField("Display size (inches)");
        TextField fLoad  = inputField("e.g. Front load");
        TextField fSpin  = inputField("Spin speed (RPM)");
        TextField fWCap  = inputField("Capacity (kg)");

        // Fridge extras
        VBox fridgeExtra = new VBox(8,
                fieldRow("Door Design", fDoor),
                fieldRow("Color",       fColor),
                fieldRow("Capacity (L)", fCap));

        // TV extras
        VBox tvExtra = new VBox(8,
                fieldRow("Screen Type", fScr),
                fieldRow("Resolution",  fRes),
                fieldRow("Display Size", fSize));

        // WM extras
        VBox wmExtra = new VBox(8,
                fieldRow("Load Type",   fLoad),
                fieldRow("Spin Speed",  fSpin),
                fieldRow("Capacity (kg)", fWCap));

        VBox dynamicExtra = new VBox(fridgeExtra);

        rbFridge.setOnAction(e -> dynamicExtra.getChildren().setAll(fridgeExtra));
        rbTV.setOnAction(e    -> dynamicExtra.getChildren().setAll(tvExtra));
        rbWM.setOnAction(e    -> dynamicExtra.getChildren().setAll(wmExtra));

        Button addBtn = accentButton("Add Product", SUCCESS);
        addBtn.setOnAction(e -> {
            try {
                String name  = fName.getText().trim();
                int qty      = Integer.parseInt(fQty.getText().trim());
                double price = Double.parseDouble(fPrice.getText().trim());
                int item     = Integer.parseInt(fItem.getText().trim());

                if (name.isEmpty()) { showAlert("Product name cannot be empty.", Alert.AlertType.WARNING); return; }
                if (qty < 0)        { showAlert("Quantity cannot be negative.",  Alert.AlertType.WARNING); return; }
                if (price < 0)      { showAlert("Price cannot be negative.",     Alert.AlertType.WARNING); return; }

                product p;
                if (rbFridge.isSelected()) {
                    String door  = fDoor.getText().trim();
                    String color = fColor.getText().trim();
                    double cap   = Double.parseDouble(fCap.getText().trim());
                    p = new Refrigerator(item, name, qty, price, door, color, cap);
                } else if (rbTV.isSelected()) {
                    String scr  = fScr.getText().trim();
                    String res  = fRes.getText().trim();
                    double size = Double.parseDouble(fSize.getText().trim());
                    p = new TV(item, name, qty, price, scr, res, size);
                } else {
                    String load = fLoad.getText().trim();
                    int spin    = Integer.parseInt(fSpin.getText().trim());
                    int wcap    = Integer.parseInt(fWCap.getText().trim());
                    p = new WashingMachine(item, name, qty, price, load, spin, wcap);
                }

                products[productCount++] = p;
                productObsList.add(p);
                showAlert("Product added successfully! ✅", Alert.AlertType.INFORMATION);
                clearFields(fName, fQty, fPrice, fItem,
                        fDoor, fColor, fCap, fScr, fRes, fSize, fLoad, fSpin, fWCap);

            } catch (NumberFormatException ex) {
                showAlert("Please enter valid numeric values for Qty, Price, Item, and type-specific fields.",
                        Alert.AlertType.ERROR);
            }
        });

        ScrollPane scroll = new ScrollPane(new VBox(16, title, typeRow, grid, dynamicExtra, addBtn));
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        box.getChildren().add(scroll);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return box;
    }

    // ════════════════════════════════════════════════════════════════════════
    // PANEL – Add / Deduct Stock
    // ════════════════════════════════════════════════════════════════════════
    private VBox buildStockAdjustView(boolean isAdd) {
        VBox box = new VBox(20);
        Label title = sectionTitle(isAdd ? "🔼 Add Stock" : "🔽 Deduct Stock");

        if (productCount == 0) {
            box.getChildren().addAll(title, new Label("No products available. Please add products first."));
            return box;
        }

        ComboBox<String> combo = new ComboBox<>();
        for (int i = 0; i < productCount; i++)
            combo.getItems().add(i + " – " + products[i].getProductName());
        combo.setPromptText("Select a product");
        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setStyle("-fx-background-color:" + BG_CARD2 + "; -fx-text-fill:" + TEXT_MAIN + ";");

        TextField qtyField = inputField("Quantity");
        Label currentQtyLabel = new Label();
        styleLabel(currentQtyLabel, TEXT_MUTED, 12);

        combo.setOnAction(e -> {
            int idx = combo.getSelectionModel().getSelectedIndex();
            if (idx >= 0)
                currentQtyLabel.setText("Current stock: " + products[idx].getQuantity());
        });

        String btnColor = isAdd ? SUCCESS : DANGER;
        String btnText  = isAdd ? "Add Stock ✅" : "Deduct Stock ❌";
        Button btn = accentButton(btnText, btnColor);
        btn.setOnAction(e -> {
            int idx = combo.getSelectionModel().getSelectedIndex();
            if (idx < 0) { showAlert("Please select a product.", Alert.AlertType.WARNING); return; }
            try {
                int qty = Integer.parseInt(qtyField.getText().trim());
                if (isAdd) {
                    products[idx].addStock(qty);
                    showAlert("Stock added successfully.", Alert.AlertType.INFORMATION);
                } else {
                    boolean ok = products[idx].deductStock(qty);
                    if (ok) showAlert("Stock deducted successfully.", Alert.AlertType.INFORMATION);
                    else    showAlert("Failed: not enough stock or product discontinued.", Alert.AlertType.ERROR);
                }
                productObsList.set(idx, products[idx]); // refresh table
                currentQtyLabel.setText("Current stock: " + products[idx].getQuantity());
                qtyField.clear();
            } catch (NumberFormatException ex) {
                showAlert("Please enter a valid integer quantity.", Alert.AlertType.ERROR);
            }
        });

        box.getChildren().addAll(title,
                labeledNode("Select Product", combo),
                currentQtyLabel,
                labeledNode("Quantity", qtyField),
                btn);
        return box;
    }

    // ════════════════════════════════════════════════════════════════════════
    // PANEL – Discontinue Product
    // ════════════════════════════════════════════════════════════════════════
    private VBox buildDiscontinueView() {
        VBox box = new VBox(20);
        Label title = sectionTitle("🚫 Discontinue Product");

        if (productCount == 0) {
            box.getChildren().addAll(title, new Label("No products available."));
            return box;
        }

        ComboBox<String> combo = new ComboBox<>();
        for (int i = 0; i < productCount; i++)
            combo.getItems().add(i + " – " + products[i].getProductName()
                    + (products[i].isStatus() ? "" : " [Discontinued]"));
        combo.setPromptText("Select a product");
        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setStyle("-fx-background-color:" + BG_CARD2 + "; -fx-text-fill:" + TEXT_MAIN + ";");

        Button btn = accentButton("Discontinue 🚫", DANGER);
        btn.setOnAction(e -> {
            int idx = combo.getSelectionModel().getSelectedIndex();
            if (idx < 0) { showAlert("Please select a product.", Alert.AlertType.WARNING); return; }
            if (!products[idx].isStatus()) { showAlert("Product already discontinued.", Alert.AlertType.WARNING); return; }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Discontinue \"" + products[idx].getProductName() + "\"?",
                    ButtonType.YES, ButtonType.NO);
            confirm.showAndWait().ifPresent(bt -> {
                if (bt == ButtonType.YES) {
                    products[idx].setStatus(false);
                    productObsList.set(idx, products[idx]);
                    combo.getItems().set(idx, idx + " – " + products[idx].getProductName() + " [Discontinued]");
                    showAlert("Product discontinued.", Alert.AlertType.INFORMATION);
                }
            });
        });

        box.getChildren().addAll(title, labeledNode("Select Product", combo), btn);
        return box;
    }

    // ════════════════════════════════════════════════════════════════════════
    // SCREEN 3 – Exit / Goodbye
    // ════════════════════════════════════════════════════════════════════════
    private void showExitScreen(Stage stage) {
        Stage exitStage = new Stage();
        exitStage.initModality(Modality.APPLICATION_MODAL);
        exitStage.initOwner(stage);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40, 60, 40, 60));
        root.setStyle("-fx-background-color: " + BG_DARK + ";");

        Text thanks = new Text("Thank you for using SMS!");
        thanks.setFont(Font.font("Georgia", FontWeight.BOLD, 22));
        thanks.setFill(Color.web(ACCENT));

        VBox info = card();
        info.getChildren().addAll(
                infoRow("User ID", resolvedUserID),
                infoRow("Name",    resolvedName),
                infoRow("Products managed", String.valueOf(productCount)));

        Button closeBtn = accentButton("Close Application", DANGER);
        closeBtn.setOnAction(e -> { exitStage.close(); stage.close(); });

        root.getChildren().addAll(thanks, info, closeBtn);

        exitStage.setScene(new Scene(root, 380, 300));
        exitStage.setTitle("Goodbye!");
        exitStage.show();
    }

    // ════════════════════════════════════════════════════════════════════════
    // Helper UI builders
    // ════════════════════════════════════════════════════════════════════════
    private VBox card() {
        VBox v = new VBox(8);
        v.setPadding(new Insets(16));
        v.setStyle("-fx-background-color: " + BG_CARD2 + "; -fx-background-radius: 10;");
        return v;
    }

    private Label sectionTitle(String text) {
        Label l = new Label(text);
        l.setFont(Font.font("Georgia", FontWeight.BOLD, 18));
        l.setTextFill(Color.web(TEXT_MAIN));
        return l;
    }

    private Label memberLabel(String text) {
        Label l = new Label(text);
        l.setFont(Font.font(13));
        l.setTextFill(Color.web(TEXT_MAIN));
        return l;
    }

    private Button accentButton(String text, String color) {
        Button b = new Button(text);
        b.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white;"
                + "-fx-font-weight: bold; -fx-font-size: 13;"
                + "-fx-background-radius: 8; -fx-padding: 10 20;");
        b.setOnMouseEntered(e -> b.setOpacity(0.85));
        b.setOnMouseExited(e  -> b.setOpacity(1.0));
        return b;
    }

    private Button navButton(String text) {
        Button b = new Button(text);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setStyle("-fx-background-color: transparent; -fx-text-fill: " + TEXT_MAIN + ";"
                + "-fx-font-size: 13; -fx-alignment: CENTER-LEFT;"
                + "-fx-padding: 10 12; -fx-background-radius: 8;");
        b.setOnMouseEntered(e -> b.setStyle(b.getStyle() + "-fx-background-color: " + BG_CARD2 + ";"));
        b.setOnMouseExited(e  -> b.setStyle(b.getStyle().replace("-fx-background-color: " + BG_CARD2 + ";", "")));
        return b;
    }

    private RadioButton styledRadio(String text, ToggleGroup tg) {
        RadioButton rb = new RadioButton(text);
        rb.setToggleGroup(tg);
        rb.setStyle("-fx-text-fill: " + TEXT_MAIN + "; -fx-font-size: 13;");
        return rb;
    }

    private TextField inputField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        styleTextField(tf);
        return tf;
    }

    private void styleTextField(TextField tf) {
        tf.setStyle("-fx-background-color: " + BG_CARD2 + "; -fx-text-fill: " + TEXT_MAIN + ";"
                + "-fx-prompt-text-fill: " + TEXT_MUTED + "; -fx-background-radius: 6;"
                + "-fx-padding: 8 12; -fx-font-size: 13;");
    }

    private void styleLabel(Label l, String color, int size) {
        l.setTextFill(Color.web(color));
        l.setFont(Font.font(size));
    }

    private HBox fieldRow(String labelText, TextField tf) {
        Label l = new Label(labelText);
        l.setMinWidth(140);
        styleLabel(l, TEXT_MUTED, 12);
        HBox row = new HBox(10, l, tf);
        HBox.setHgrow(tf, Priority.ALWAYS);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private VBox labeledNode(String labelText, javafx.scene.Node node) {
        Label l = new Label(labelText);
        styleLabel(l, TEXT_MUTED, 12);
        return new VBox(4, l, node);
    }

    private void addRow(GridPane g, int row, String label, TextField tf) {
        Label l = new Label(label);
        styleLabel(l, TEXT_MUTED, 12);
        g.add(l, 0, row);
        g.add(tf, 1, row);
        GridPane.setHgrow(tf, Priority.ALWAYS);
    }

    private HBox infoRow(String key, String value) {
        Label k = new Label(key + ":");
        styleLabel(k, TEXT_MUTED, 13);
        k.setMinWidth(140);
        Label v = new Label(value);
        styleLabel(v, TEXT_MAIN, 13);
        v.setFont(Font.font("Georgia", FontWeight.BOLD, 13));
        return new HBox(8, k, v);
    }

    private <T> TableColumn<product, T> col(String header, String prop) {
        TableColumn<product, T> c = new TableColumn<>(header);
        c.setCellValueFactory(new PropertyValueFactory<>(prop));
        return c;
    }

    private void clearFields(TextField... fields) {
        for (TextField f : fields) f.clear();
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert a = new Alert(type, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}