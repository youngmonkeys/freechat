<?php
include('FreeChatDBConnection.php');
include('FreeChatLogger.php');
include('FreeChatHttpCode.php');
header('Access-Control-Allow-Origin: *');
class FreeChatFileUpload {
    private $db;
    private $token;

    public function __construct($token) {
        $this->db =  FreeChatDBConnection::getInstance();
        $this->token = $token;
    }

    public function receiveFileFromFreechatApp() {
        $user = $this->db->getUserByToken($this->token);
        if (!$user) {
            $errors = 'token invalid';
            FreeChatLogger::error($errors);
            return FreeChatHttpCode::http_response_code(400, $errors);
        }
        FreeChatLogger::info("user", $user);

        if (isset($_FILES['image'])) {
            $file_name = $_FILES['image']['name'];
            $file_size = $_FILES['image']['size'];
            $file_tmp = $_FILES['image']['tmp_name'];
            $file_type = $_FILES['image']['type'];
            $explode = explode('.', $file_name);
            $file_ext = strtolower(end($explode));
            $expensions = array("jpeg", "jpg", "png");
            if (in_array($file_ext, $expensions) === false) {
                $errors = 'file invalid format';
                FreeChatLogger::error($errors);
                return FreeChatHttpCode::http_response_code(400, $errors);
            }
            if ($file_size > 2097152) {
                $errors = 'overlength file size(2MB)';
                FreeChatLogger::error($errors);
                return FreeChatHttpCode::http_response_code(400, $errors);
            }
            $response = $this->buildResponse($user, $file_name, $file_ext);
            $move_uploaded_file = move_uploaded_file($file_tmp, $response);
            if (!$move_uploaded_file) {
                $errors = 'No such file or directory';
                FreeChatLogger::error($errors);
                return FreeChatHttpCode::http_response_code(500, $errors);
            }
            return "https://freechat.tvd12.com/" . $response;
        }
        return FreeChatHttpCode::http_response_code(400, "INTERNAL ERROR");
    }

    public function buildResponse($user, $file_name, $file_ext) {
        $fileName = $user . $file_name . (new DateTime)->getTimestamp();
        $fileNameEnCode = hash("sha256", $fileName);
        $response = FreeChatConfig::$path . $fileNameEnCode . ".$file_ext";
        return $response;
    }
}

$token = $_REQUEST['token'];
if (!$token) {
    $errors = 'token is empty';
    FreeChatLogger::error($errors);
    return;
}
FreeChatLogger::info("token", $token);
$freeChatFileUpload = new FreeChatFileUpload($token);
$response = $freeChatFileUpload->receiveFileFromFreechatApp();
echo json_encode($response);
?>