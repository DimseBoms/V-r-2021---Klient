module com.example.chatroom {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.chatroom to javafx.fxml;
    exports com.example.chatroom;
}