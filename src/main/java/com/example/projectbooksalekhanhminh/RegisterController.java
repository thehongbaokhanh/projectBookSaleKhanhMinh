package com.example.projectbooksalekhanhminh;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField showPasswordField; // TextField để hiển thị mật khẩu khi chọn "Show Password"

    @FXML
    private TextField showConfirmPasswordField; // TextField để hiển thị xác nhận mật khẩu

    @FXML
    private TextField phoneField; // Trường nhập số điện thoại

    @FXML
    private CheckBox showPasswordCheckBox; // CheckBox để chọn hiển thị mật khẩu

    // Xử lý khi người dùng nhấn nút "Register"
    @FXML
    private void handleRegisterButton() {
        String username = usernameField.getText();
        // Kiểm tra xem người dùng có muốn hiển thị mật khẩu không, nếu có thì lấy từ TextField hiển thị, nếu không thì lấy từ PasswordField
        String password = showPasswordCheckBox.isSelected() ? showPasswordField.getText() : passwordField.getText();
        String confirmPassword = showPasswordCheckBox.isSelected() ? showConfirmPasswordField.getText() : confirmPasswordField.getText();
        String phone = phoneField.getText();

        // Kiểm tra định dạng số điện thoại
        if (!isValidPhoneNumber(phone)) {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Phone number must be 10 digits and start with 0.");
            return;
        }

        // Kiểm tra xem mật khẩu có khớp nhau không
        if (password.equals(confirmPassword)) {
            // Thực hiện đăng ký (Ở đây có thể thêm code xử lý lưu vào cơ sở dữ liệu)
            showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Welcome " + username + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "Passwords do not match.");
        }
    }

    // Kiểm tra tính hợp lệ của số điện thoại (phải là 10 chữ số và bắt đầu bằng số 0)
    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("^0\\d{9}$"); // Kiểm tra xem có phải 10 chữ số bắt đầu bằng 0 không
    }

    // Xử lý khi người dùng nhấn vào link "Login"
    @FXML
    private void handleLoginLink() {
        try {
            // Tải lại trang đăng nhập
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Xử lý việc hiển thị hoặc ẩn mật khẩu khi chọn "Show Password"
    @FXML
    private void handleShowPassword() {
        if (showPasswordCheckBox.isSelected()) {
            // Hiển thị mật khẩu và xác nhận mật khẩu trong các TextField thường
            showPasswordField.setText(passwordField.getText());
            showPasswordField.setVisible(true);
            showPasswordField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);

            showConfirmPasswordField.setText(confirmPasswordField.getText());
            showConfirmPasswordField.setVisible(true);
            showConfirmPasswordField.setManaged(true);
            confirmPasswordField.setVisible(false);
            confirmPasswordField.setManaged(false);
        } else {
            // Ẩn TextField và hiển thị lại PasswordField để ẩn mật khẩu
            passwordField.setText(showPasswordField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            showPasswordField.setVisible(false);
            showPasswordField.setManaged(false);

            confirmPasswordField.setText(showConfirmPasswordField.getText());
            confirmPasswordField.setVisible(true);
            confirmPasswordField.setManaged(true);
            showConfirmPasswordField.setVisible(false);
            showConfirmPasswordField.setManaged(false);
        }
    }

    // Hiển thị cảnh báo cho người dùng
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
