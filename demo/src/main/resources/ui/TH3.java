import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class TH3 extends Application {

    private VBox leftPanel;
    private ImageView mainImageView;
    private Label mainTitle, mainPrice, mainBrand, mainDesc;
    private VBox currentlySelectedCard = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        HBox root = new HBox(30);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #ffffff;");

        List<Product> products = getSampleProducts();

        setupLeftPanel();
        
        updateMainPanel(products.get(0), false);

        TilePane grid = new TilePane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPrefColumns(4); 
        grid.setAlignment(Pos.TOP_LEFT);

        for (Product product : products) {
            VBox card = createProductCard(product);
            
            card.setOnMouseClicked(e -> {
                if (currentlySelectedCard != card) {
                    if (currentlySelectedCard != null) {
                        currentlySelectedCard.setStyle("-fx-background-color: #f4f4f4; -fx-background-radius: 8; -fx-border-color: transparent;");
                    }

                    card.setStyle("-fx-background-color: #f4f4f4; -fx-background-radius: 8; -fx-border-color: #4285F4; -fx-border-radius: 8; -fx-border-width: 2;");
                    currentlySelectedCard = card;
                    
                    updateMainPanel(product, true);
                }
            });
            grid.getChildren().add(card);
        }

        currentlySelectedCard = (VBox) grid.getChildren().get(0);
        currentlySelectedCard.setStyle("-fx-background-color: #f4f4f4; -fx-background-radius: 8; -fx-border-color: #4285F4; -fx-border-radius: 8; -fx-border-width: 2;");

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: #ffffff;");
        scrollPane.setBorder(Border.EMPTY);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        root.getChildren().addAll(leftPanel, scrollPane);

        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setTitle("Cửa hàng Giày - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupLeftPanel() {
        leftPanel = new VBox(10);
        leftPanel.setPrefWidth(350);
        leftPanel.setMinWidth(350);
        leftPanel.setAlignment(Pos.CENTER_LEFT);

        mainImageView = new ImageView();
        mainImageView.setFitWidth(300);
        mainImageView.setFitHeight(200);
        mainImageView.setPreserveRatio(true);
        
        VBox imageContainer = new VBox(mainImageView);
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setPrefHeight(250);

        mainTitle = new Label();
        mainTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        mainTitle.setTextFill(Color.web("#333333"));

        mainPrice = new Label();
        mainPrice.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        mainPrice.setTextFill(Color.web("#333333"));

        mainBrand = new Label();
        mainBrand.setFont(Font.font("Arial", 14));
        mainBrand.setTextFill(Color.web("#666666"));

        mainDesc = new Label();
        mainDesc.setFont(Font.font("Arial", 13));
        mainDesc.setTextFill(Color.web("#999999"));
        mainDesc.setWrapText(true);

        leftPanel.getChildren().addAll(imageContainer, mainTitle, mainPrice, mainBrand, mainDesc);
    }

    private VBox createProductCard(Product product) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setPrefWidth(220);
        card.setStyle("-fx-background-color: #f4f4f4; -fx-background-radius: 8; -fx-border-color: transparent;");

        Label title = new Label(product.name);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        title.setTextFill(Color.web("#333333"));

        Label desc = new Label(product.shortDesc);
        desc.setFont(Font.font("Arial", 11));
        desc.setTextFill(Color.web("#999999"));

        ImageView imageView = new ImageView(new Image(product.imageUrl, true));
        imageView.setFitWidth(180);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);
        
        VBox imgBox = new VBox(imageView);
        imgBox.setAlignment(Pos.CENTER);
        imgBox.setPrefHeight(130);

        HBox footer = new HBox();
        Label brand = new Label(product.brand);
        brand.setTextFill(Color.web("#666666"));
        brand.setFont(Font.font("Arial", 12));
        
        Label price = new Label(product.price);
        price.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        price.setTextFill(Color.web("#333333"));
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        footer.getChildren().addAll(brand, spacer, price);

        card.getChildren().addAll(title, desc, imgBox, footer);
        
        // Hiệu ứng hover cho thẻ
        card.setOnMouseEntered(e -> {
            if (currentlySelectedCard != card) {
                card.setStyle("-fx-background-color: #e9e9e9; -fx-background-radius: 8; -fx-border-color: transparent;");
            }
        });
        card.setOnMouseExited(e -> {
            if (currentlySelectedCard != card) {
                card.setStyle("-fx-background-color: #f4f4f4; -fx-background-radius: 8; -fx-border-color: transparent;");
            }
        });

        return card;
    }

    private void updateMainPanel(Product p, boolean animate) {
        if (!animate) {
            setMainPanelData(p);
            return;
        }

        FadeTransition fadeOut = new FadeTransition(Duration.millis(150), leftPanel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        
        TranslateTransition slideDown = new TranslateTransition(Duration.millis(150), leftPanel);
        slideDown.setByY(20);

        ParallelTransition ptOut = new ParallelTransition(fadeOut, slideDown);
        
        ptOut.setOnFinished(e -> {
            setMainPanelData(p);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), leftPanel);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            TranslateTransition slideUp = new TranslateTransition(Duration.millis(200), leftPanel);
            slideUp.setByY(-20);

            ParallelTransition ptIn = new ParallelTransition(fadeIn, slideUp);
            ptIn.play();
        });

        ptOut.play();
    }

    private void setMainPanelData(Product p) {
        mainTitle.setText(p.name);
        mainPrice.setText(p.price);
        mainBrand.setText(p.brand);
        mainDesc.setText(p.longDesc);
        mainImageView.setImage(new Image(p.imageUrl, true));
    }

    static class Product {
        String name, shortDesc, longDesc, brand, price, imageUrl;

        public Product(String name, String shortDesc, String longDesc, String brand, String price, String imageUrl) {
            this.name = name;
            this.shortDesc = shortDesc;
            this.longDesc = longDesc;
            this.brand = brand;
            this.price = price;
            this.imageUrl = imageUrl;
        }
    }

    // Tạo dữ liệu mẫu
    private List<Product> getSampleProducts() {
        List<Product> list = new ArrayList<>();
        String img1 = "img/img1.png"; 
        String img2 = "img/img2.png"; 
        String img3 = "img/img3.png"; 
        String img4 = "img/img4.png"; 
        String img5 = "img/img5.png"; 
        String img6 = "img/img6.png"; 

        String defaultDesc = "This product is excluded from all\npromotional discounts and offers.";
        
        list.add(new Product("4DFWD PULSE SHOES", "This product is excluded fr...", defaultDesc, "Adidas", "$160.00", img2));
        list.add(new Product("FORUM MID SHOES", "This product is excluded fr...", defaultDesc, "Adidas", "$100.00", img3));
        list.add(new Product("SUPERNOVA SHOES", "NMD City Stock 2", defaultDesc, "Adidas", "$150.00", img4));
        list.add(new Product("Adidas WHITE", "NMD City Stock 2", defaultDesc, "Adidas", "$160.00", img1));
        list.add(new Product("Adidas BLACK", "NMD City Stock 2", defaultDesc, "Adidas", "$120.00", img2));
        list.add(new Product("4DFWD PULSE RED", "This product is excluded fr...", defaultDesc, "Adidas", "$160.00", img1));
        list.add(new Product("4DFWD PULSE GREEN", "This product is excluded fr...", defaultDesc, "Adidas", "$160.00", img5));
        list.add(new Product("FORUM MID SHOES", "This product is excluded fr...", defaultDesc, "Adidas", "$100.00", img6));

        return list;
    }
}
