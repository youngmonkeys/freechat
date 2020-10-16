<?php
class FreeChatLogger {
    public static function info($field, $message) {
        file_put_contents('../logs/log_' . date("j.n.Y") . '.log', "$field: $message \n", FILE_APPEND);
    }

    public static function error($message) {
        error_log($message, 0);
    }
}
?>
