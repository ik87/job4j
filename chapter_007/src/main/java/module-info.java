module javafx {
   requires javafx.fxml;
   requires javafx.controls;
   opens ru.job4j.pingpong to javafx.fxml;
   exports ru.job4j.pingpong;
}