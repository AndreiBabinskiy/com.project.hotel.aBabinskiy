package com.project.aBabinskiy.client.windows.profiles;

import com.project.aBabinskiy.data.Account;
import com.project.aBabinskiy.data.Citizenship;
import com.project.aBabinskiy.utils.SocketJsonStreams;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import java.awt.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private javafx.scene.control.TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ToggleGroup sexGroup;

    @FXML
    private RadioButton maleBtn;

    @FXML
    private RadioButton femaleBtn;

    @FXML
    private ComboBox<Citizenship> citizenship;

    @FXML
    private DatePicker arrivalDate;

    @FXML
    private DatePicker dateOfDeparture;

    @FXML
    private CheckBox oneRoom;

   @FXML
   private CheckBox twoRoom;

    @FXML
    private CheckBox threeRoom;

    @FXML
    private CheckBox usualComfort;

    @FXML
    private CheckBox juniorSuiteComfort;

    @FXML
    private CheckBox luxComfort;

    @FXML
    private CheckBox reservation;

    @FXML
    private TextArea allStrs;

    @FXML
    private TableView<Account> allAccountsGrig;

    @FXML
    private TextField citizenshipCodeField;

    @FXML
    private TextField citizenshipNameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sexGroup = new ToggleGroup();
        maleBtn.setToggleGroup(sexGroup);
        femaleBtn.setToggleGroup(sexGroup);

        citizenship.setCellFactory(new CitizenshipComboFactory());
        reloadCitizenshipCombobox();

        TableColumn<Account, CheckBox> isEnabledCheckColumn = new TableColumn<>("Is Enabled Check");
        isEnabledCheckColumn.setMinWidth(150);
        isEnabledCheckColumn.setCellValueFactory(new IsActiveCheckboxFactory());

        TableColumn<Account, GridPane> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setMinWidth(300);
        actionsColumn.setCellValueFactory(new ButtonsCellFactory());

        ObservableList<TableColumn<Account, ?>> columns = allAccountsGrig.getColumns();
        columns.add(3, isEnabledCheckColumn);
        columns.add(actionsColumn);

        ObservableList<Account> allAccountsItems = allAccountsGrig.getItems();
        allAccountsItems.clear();

        reloadAccountsGrid();
    }

    public void addCitizenshipBthPressed() throws Exception {
        String citizenshipCode = citizenshipCodeField.getText();
        String citizenshipName = citizenshipNameField.getText();

        Citizenship citizenship = new Citizenship(citizenshipCode, citizenshipName);
        InetAddress ip = InetAddress.getByName("localhost");
        Socket clientSocket = new Socket(ip, 8848);

        SocketJsonStreams streams = new SocketJsonStreams(clientSocket);
        JSONWriter jsonWriter = streams.getJsonWriter();

        jsonWriter.object();
            jsonWriter.key("request-header").object();
                jsonWriter.key("command-name").value("save-citizenship");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
                jsonWriter.key("citizenship-code").value(citizenship.getCode());
                jsonWriter.key("citizenship-name").value(citizenship.getName());
            jsonWriter.endObject();
        jsonWriter.endObject();

        streams.flushWriter();

        reloadCitizenshipCombobox();
    }

    public void saveBtnPressed() throws Exception {
        String login = loginField.getText();
        String password = passwordField.getText();
        boolean maleSelected = maleBtn.isSelected();
        boolean oneRoomSelected = oneRoom.isSelected();
        boolean twoRoomSelected = twoRoom.isSelected();
        boolean threeRoomSelected = threeRoom.isSelected();
        boolean usualComfortSelected = usualComfort.isSelected();
        boolean juniorSuiteComfortSelected = juniorSuiteComfort.isSelected();
        boolean luxComfortSelected = luxComfort.isSelected();

        Citizenship citizenships = citizenship.getValue();
        if (citizenships == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");

            alert.setHeaderText("Can not save Account without country!!");

            alert.showAndWait();
            return;

        }

        Integer citizienshipId = citizenship.getValue().getId();
        LocalDate arrivalDates = arrivalDate.getValue();
        LocalDate dateOfDepartures = dateOfDeparture.getValue();
        boolean reservationSelected = reservation.isSelected();
        String wishes = allStrs.getText();

        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        account.setMale(maleSelected);
        account.setOneRoom(oneRoomSelected);
        account.setTwoRoom(twoRoomSelected);
        account.setThreeRoom(threeRoomSelected);
        account.setUsualComfort(usualComfortSelected);
        account.setJuniorSuiteComfort(juniorSuiteComfortSelected);
        account.setLuxComfort(luxComfortSelected);
        account.setCitizenshipId(citizienshipId);
        account.setArrivalDate(arrivalDates);
        account.setDateOfDeparture(dateOfDepartures);
        account.setReservation(reservationSelected);
        account.setWishes(wishes);

        InetAddress ip = InetAddress.getByName("localhost");
        Socket clientSocket = new Socket(ip, 8848);

        SocketJsonStreams streams = new SocketJsonStreams(clientSocket);
        JSONWriter jsonWriter = streams.getJsonWriter();

        jsonWriter.object();
            jsonWriter.key("request-header").object();
                jsonWriter.key("command-name").value("save-account");
            jsonWriter.endObject();

            jsonWriter.key("request-data").object();
                jsonWriter.key("login").value(account.getLogin());
                jsonWriter.key("password").value(account.getPassword());
                jsonWriter.key("reservation").value(account.isReservation());
                jsonWriter.key("oneRoom").value(account.isOneRoom());
                jsonWriter.key("twoRoom").value(account.isTwoRoom());
                jsonWriter.key("threeRoom").value(account.isThreeRoom());
                jsonWriter.key("usualComfort").value(account.isUsualComfort());
                jsonWriter.key("juniorSuiteComfort").value(account.isJuniorSuiteComfort());
                jsonWriter.key("luxComfort").value(account.isLuxComfort());
                jsonWriter.key("arrivalDate").value(account.getArrivalDate());
                jsonWriter.key("dateOfDeparture").value(account.getDateOfDeparture());
                jsonWriter.key("citizenship-id").value(account.getCitizenshipId());
                jsonWriter.key("male").value((account.isMale()));
                jsonWriter.key("wishes").value(account.getWishes());
            jsonWriter.endObject();
        jsonWriter.endObject();

        streams.flushWriter();

        JSONTokener jsonTokener = streams.getJsonTokener();
        JSONObject response = (JSONObject) jsonTokener.nextValue();

        JSONObject responseHeader = response.getJSONObject("response-header");
        int responseCode = responseHeader.getInt("response-code");
        if (responseCode != 200) {
            String errorMessage = responseHeader.getString("response-message");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server Error");

            alert.setHeaderText("Can not Save Account due to Server Error!");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return;
        }

        clearForm();

        reloadAccountsGrid();
    }

    private void clearForm() {
        loginField.setText("");
        passwordField.setText("");
        maleBtn.setSelected(false);
        femaleBtn.setSelected(false);
        oneRoom.setSelected(false);
        twoRoom.setSelected(false);
        threeRoom.setSelected(false);
        usualComfort.setSelected(false);
        juniorSuiteComfort.setSelected(false);
        luxComfort.setSelected(false);
        arrivalDate.setValue(null);
        dateOfDeparture.setValue(null);
        reservation.setSelected(false);
        allStrs.setText("");
    }

    private void reloadAccountsGrid() {
        ObservableList<Account> allAccountsItems = allAccountsGrig.getItems();
        allAccountsItems.clear();

        try {
            InetAddress ip = InetAddress.getByName("localhost");
            Socket clientSocket = new Socket(ip, 8848);

            SocketJsonStreams streams = new SocketJsonStreams(clientSocket);
            JSONWriter jsonWriter = streams.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("get-all-accounts");
            jsonWriter.endObject();
            jsonWriter.endObject();

            streams.flushWriter();

            JSONTokener jsonTokener = streams.getJsonTokener();
            JSONObject response = (JSONObject) jsonTokener.nextValue();

            JSONObject responseHeader = response.getJSONObject("response-header");

            int responseCode = responseHeader.getInt("response-code");
            if (responseCode != 200) {
                String errorMessage = responseHeader.getString("response-message");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Server Error");

                alert.setHeaderText("Can not Save Account due to Server Error!");
                alert.setContentText(errorMessage);

                alert.showAndWait();
                return;
            }
            JSONArray responseData = response.getJSONArray("response-data");

            for (int i = 0; i < responseData.length(); i++) {
                JSONObject accountJson = responseData.getJSONObject(i);

                String login = accountJson.getString("login");
                String password = accountJson.getString("password");
                boolean male = accountJson.getBoolean("male");
                boolean reservation = accountJson.getBoolean("reservation");
                boolean oneRoom = accountJson.getBoolean("oneRoom");
                boolean twoRoom = accountJson.getBoolean("twoRoom");
                boolean threeRoom = accountJson.getBoolean("threeRoom");
                boolean usualComfort = accountJson.getBoolean("usualComfort");
                boolean juniorSuiteComfort = accountJson.getBoolean("juniorSuiteComfort");
                boolean luxComfort = accountJson.getBoolean("luxComfort");
                String arrivalDateStr = accountJson.getString("arrivalDate");
                LocalDate arrivalDate = LocalDate.parse(arrivalDateStr);
                String dateOfDepartureStr = accountJson.getString("dateOfDeparture");
                LocalDate dateOfDeparture = LocalDate.parse(dateOfDepartureStr);
                Integer citizenshipId = accountJson.getInt("citizenship-id");
                String citizenshipName = accountJson.getString("citizenship-name");
                String wishes = accountJson.getString("wishes");

                Account account = new Account();
                account.setLogin(login);
                account.setPassword(password);
                account.setMale(male);
                account.setReservation(reservation);
                account.setOneRoom(oneRoom);
                account.setTwoRoom(twoRoom);
                account.setThreeRoom(threeRoom);
                account.setUsualComfort(usualComfort);
                account.setJuniorSuiteComfort(juniorSuiteComfort);
                account.setLuxComfort(luxComfort);
                account.setArrivalDate(arrivalDate);
                account.setDateOfDeparture(dateOfDeparture);
                account.setCitizenshipId(citizenshipId);
                account.setCitizenshipName(citizenshipName);
                account.setWishes(wishes);

                allAccountsItems.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reloadCitizenshipCombobox() {
        try {
            InetAddress ip = InetAddress.getByName("localhost");
            Socket clientSocket = new Socket(ip, 8848);

            SocketJsonStreams streams = new SocketJsonStreams(clientSocket);
            JSONWriter jsonWriter = streams.getJsonWriter();

            jsonWriter.object();
            jsonWriter.key("request-header").object();
            jsonWriter.key("command-name").value("get-all-citizenships");
            jsonWriter.endObject();
            jsonWriter.endObject();

            streams.flushWriter();

            JSONTokener jsonTokener = streams.getJsonTokener();
            JSONObject response = (JSONObject) jsonTokener.nextValue();

            JSONObject responseHeader = response.getJSONObject("response-header");
            JSONArray responseData = response.getJSONArray("response-data");

            ObservableList<Citizenship> citizenshipItems = citizenship.getItems();
            citizenshipItems.clear();

            for (int i = 0; i < responseData.length(); i++) {
                JSONObject citizenshipJson = responseData.getJSONObject(i);

                Integer id = citizenshipJson.getInt("id");
                String name = citizenshipJson.getString("name");
                String code = citizenshipJson.getString("code");

                Citizenship citizenship = new Citizenship(id, name, code);

                citizenshipItems.add(citizenship);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class IsActiveCheckboxFactory implements Callback<TableColumn.CellDataFeatures<Account, CheckBox>, ObservableValue<CheckBox>> {

        @Override
        public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<Account, CheckBox> param) {
            Account account = param.getValue();
            boolean isReservation = account.isReservation();

            CheckBox checkBox = new CheckBox();
            checkBox.selectedProperty().setValue(isReservation);

            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                    account.setReservation(newValue);
                }
            });

            return new SimpleObjectProperty<>(checkBox);
        }
    }

    private class ButtonsCellFactory implements Callback<TableColumn.CellDataFeatures<Account, GridPane>, ObservableValue<GridPane>> {

        @Override
        public ObservableValue<GridPane> call(TableColumn.CellDataFeatures<Account, GridPane> param) {
            Account person = param.getValue();

            Button editBtn = new Button("edit");
            editBtn.setOnAction(new EditAccountEvent(person));

            Button deleteBtn = new Button("delete");
            deleteBtn.setOnAction(new DeleteAccountEvent(person));

            GridPane panel = new GridPane();
            panel.setHgap(10);
            panel.setVgap(10);
            panel.setPadding(new Insets(25, 25, 25, 25));

            panel.add(editBtn, 0, 0);
            panel.add(deleteBtn, 1, 0);

            return new SimpleObjectProperty<>(panel);
        }
    }

    private class CitizenshipComboFactory implements Callback<ListView<Citizenship>, ListCell<Citizenship>> {

        @Override
        public ListCell<Citizenship> call(ListView<Citizenship> param) {
            final ListCell<Citizenship> cell = new ListCell<Citizenship>(){

                @Override
                protected void updateItem(Citizenship t, boolean bln) {
                    super.updateItem(t, bln);

                    if(t != null){
                        setText(t.getCode());
                    }else{
                        setText(null);
                    }
                }
            };

            return cell;
        }
    }

    private class EditAccountEvent implements EventHandler<ActionEvent> {

        private Account editedAccount;

        public EditAccountEvent(Account editedAccount) {
            this.editedAccount = editedAccount;
        }

        @Override
        public void handle(ActionEvent event) {
            loginField.setText(editedAccount.getLogin());
            passwordField.setText(editedAccount.getPassword());
        }
    }

    private class DeleteAccountEvent implements EventHandler<ActionEvent> {

        private Account deletedAccount;

        public DeleteAccountEvent(Account deletedAccount) {
            this.deletedAccount = deletedAccount;
        }

        @Override
        public void handle(ActionEvent event) {
            ObservableList<Account> items = allAccountsGrig.getItems();
            items.remove(deletedAccount);
        }
    }

}
