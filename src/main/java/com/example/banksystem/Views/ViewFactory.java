package com.example.banksystem.Views;

import com.example.banksystem.Controllers.Admin.AdminController;
import com.example.banksystem.Controllers.Consultant.ConsultantController;
import com.example.banksystem.Controllers.Worker.WorkerController;
import com.example.banksystem.Controllers.Client.ClientController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ViewFactory {
    private AccountType loginAccountType;
    private SavingType savingAccountType;
    // Client Views
    private final ObjectProperty<ClientMenuOptions> clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane transactionView;
    private AnchorPane accountsView;

    // Worker Views
    private AnchorPane createClientView;
    private final ObjectProperty<WorkerMenuOptions> workerSelectedMenuItem;
    private AnchorPane clientsView;
    private AnchorPane depositView;

    //Admin Views
    private AnchorPane createUserView;
    private final ObjectProperty<AdminMenuOptions> adminSelectedMenuItem;
    private AnchorPane usersView;
    //Consultant Views
    private AnchorPane questionBoardView;
    private final ObjectProperty<ConsultantMenuOptions> consultantSelectedMenuItem;

    public ViewFactory() {
        this.loginAccountType = AccountType.CLIENT;
        this.savingAccountType = SavingType.ЛУЧШИЙ50000;
        this.clientSelectedMenuItem = new SimpleObjectProperty<>();
        this.workerSelectedMenuItem = new SimpleObjectProperty<>();
        this.adminSelectedMenuItem = new SimpleObjectProperty<>();
        this.consultantSelectedMenuItem = new SimpleObjectProperty<>();
    }

    public AccountType getLoginAccountType() {
        return loginAccountType;
    }

    public SavingType getSavingAccountType() {
        return savingAccountType;
    }

    public void setSavingAccountType(SavingType savingAccountType) {
        this.savingAccountType = savingAccountType;
    }


    public void setLoginAccountType(AccountType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    /*
            Client views section
             */
    public ObjectProperty<ClientMenuOptions> getClientSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getTransactionView() {
        if (transactionView == null) {
            try {
                transactionView = new FXMLLoader(getClass().getResource("/FXML/Client/Transactions.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return transactionView;
    }

    public AnchorPane getAccountsView() {
        if (accountsView == null) {
            try {
                accountsView = new FXMLLoader(getClass().getResource("/FXML/Client/Accounts.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return accountsView;
    }

    public void showClientWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }
    /*
    Worker views section
     */

    public ObjectProperty<WorkerMenuOptions> getWorkerSelectedMenuItem() {
        return workerSelectedMenuItem;
    }

    public AnchorPane getCreateClientView() {
        if (createClientView == null) {
            try {
                createClientView = new FXMLLoader(getClass().getResource("/Fxml/Worker/CreateClient.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createClientView;
    }

    public AnchorPane getClientsView() {
        if (clientsView == null) {
            try {
                clientsView = new FXMLLoader(getClass().getResource("/Fxml/Worker/Clients.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return clientsView;
    }

    public AnchorPane getDepositView() {
        if (depositView == null) {
            try {
                depositView = new FXMLLoader(getClass().getResource("/Fxml/Worker/CloseSavingAccount.fxml")).load();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return depositView;
    }

    public void showWorkerWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Worker/Worker.fxml"));
        WorkerController controller = new WorkerController();
        loader.setController(controller);
        createStage(loader);
    }

    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }

    public void showMessageWindow(String pAddress, String messageText) {
        StackPane pane = new StackPane();
        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        Label sender = new Label(pAddress);
        Label message = new Label(messageText);
        hBox.getChildren().addAll(sender, message);
        pane.getChildren().add(hBox);
        Scene scene = new Scene(pane, 300, 100);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL); // всплывающее окно которое блокирует остальные окна
        stage.setTitle("Сообщение");
        stage.setScene(scene);
        stage.show();
    }

    public void showAcceptWindow(String accountNumber) {
        StackPane pane = new StackPane();
        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER);
        Text info = new Text("Деньги со вклада переведутся на карту: ");
        Label accNum = new Label(accountNumber);
        Button accept = new Button("Подтверждаю");
        vBox.getChildren().addAll(info, accNum, accept);
        pane.getChildren().add(vBox);
        Scene scene = new Scene(pane, 300, 100);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL); // всплывающее окно, которое блокирует остальные окна
        stage.setTitle("Сообщение");
        stage.setScene(scene);
        stage.show();
        accept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
    }

    private void createStage(FXMLLoader loader, double... dimensions) { // массив который сохраняет все аргументы которые мы можем передать сюда
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.setResizable(false);
        stage.setTitle("Alobrchuk Bank");
        stage.show();
    }


    /*
    Admin views section
     */
    public ObjectProperty<AdminMenuOptions> getAdminSelectedMenuItem() {
        return adminSelectedMenuItem;
    }

    public AnchorPane getCreateUserView() {
        if (createUserView == null) {
            try {
                createUserView = new FXMLLoader(getClass().getResource("/Fxml/Admin/CreateUser.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return createUserView;
    }

    public AnchorPane getUsersView() {
        if (usersView == null) {
            try {
                usersView = new FXMLLoader(getClass().getResource("/Fxml/Admin/Users.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return usersView;
    }

    public void showAdminWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController controller = new AdminController();
        loader.setController(controller);
        createStage(loader);
    }

    /*
    Consultant views section
     */

    public ObjectProperty<ConsultantMenuOptions> getConsultantSelectedMenuItem() {
        return consultantSelectedMenuItem;
    }

    public AnchorPane getQuestionBoardView() {
        if (questionBoardView == null) {
            try {
                questionBoardView = new FXMLLoader(getClass().getResource("/Fxml/Consultant/QuestionBoard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return questionBoardView;
    }


    public void showConsultantWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Consultant/Consultant.fxml"));
        ConsultantController controller = new ConsultantController();
        loader.setController(controller);
        createStage(loader);
    }

    public void showMessageWindow(String question) {
        StackPane pane = new StackPane();
        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER);
        Text infoButton1 = new Text("Для того, чтобы получить доступ к приложению \nAlobrochukBank, вам необходимо: \nзарегистировать аккаунт у служащего в банке, \nзапросить у него данные для входа. Затем скачайте \nприложение на свое устройство и введите данные логина и пароля.");
        Text infoButton2 = new Text("Для того, чтобы восстановить свои данные в случае их утери, \nобратитесь к служащему банка");
        Text infoButton3 = new Text("""
                В нашем банке есть три типа вклада:\s
                – ЛУЧШИЙ50 000 с наиболее выгодной процентной ставкой, \nтребующий на карте как минимум 50 000 рублей\s
                – ВЫГОДНЫЙ100000 для больших накоплений, \nна карте потребуется не менее 100 000 рублей,
                – МОЛОДЕЖНЫЙ20000 для молодых людей, \nтребующий всего 20 000 на карте для открытия.""");
        Text infoButton4 = new Text("Для создания записи о клиенте, \nвойдите в свою учетную запись, \nна боковом меню найдите кнопку создания клиента. \nВведите данные и нажмите на кнопку создать. \nЕсли такой пользователь уже существует, система сама подскажет Вам.");
        Text infoButton5 = new Text("Для того чтобы открыть вклад, \nперейдите на вкладку Счета в прилоежнии банка.\nТам вы увидите информацию о своих вкладах и дебетовых картах. \nДля открытия или закрытия нажмите на соответствующие кнопки на экране.\nДля открытия вам также понадобиться выбрать тип вклада. \nПодробнее в вопросе про типы вкладов...");
        Text infoButton6 = new Text("Для перевода между своими счетами, \nперейдите на вкладку Счета в приложении банка. \nТам вы увидите информацию о своих вкладах и дебетовых картах.\nДля перевода средств, введите сумму в поле и нажмите на кнопку Перевести");
        infoButton1.setFont(Font.font ("Calibri Light", 16));
        infoButton2.setFont(Font.font ("Calibri Light", 16));
        infoButton3.setFont(Font.font ("Calibri Light", 16));
        infoButton4.setFont(Font.font ("Calibri Light", 16));
        infoButton5.setFont(Font.font ("Calibri Light", 16));
        infoButton6.setFont(Font.font ("Calibri Light", 16));
        switch (question) {
            case "AccessToBank":
                vBox.getChildren().addAll(infoButton1);
                pane.getChildren().add(vBox);
                break;
            case "ForgotPassword":
                vBox.getChildren().addAll(infoButton2);
                pane.getChildren().add(vBox);
                break;
            case "SavingsAccounts":
                vBox.getChildren().addAll(infoButton3);
                pane.getChildren().add(vBox);
                break;
            case "InfoForWorkers":
                vBox.getChildren().addAll(infoButton4);
                pane.getChildren().add(vBox);
                break;
            case "OpeningSavings":
                vBox.getChildren().addAll(infoButton5);
                pane.getChildren().add(vBox);
                break;
            case "DepositMoney":
                vBox.getChildren().addAll(infoButton6);
                pane.getChildren().add(vBox);
                break;
        }
        Scene scene = new Scene(pane, 650, 350);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/icon.png"))));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL); // всплывающее окно которое блокирует остальные окна
        stage.setTitle("Информация");
        stage.setScene(scene);
        stage.show();
    }


    public void closeStage(Stage stage) {
        stage.close();
    }
}
