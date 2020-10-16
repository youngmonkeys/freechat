<?php
require 'phpmongodb/vendor/autoload.php';
use MongoDB\Client;

class FreeChatDBConnection {
    private $_db;
    private $_collection;
    private static $_instance;

    private function __construct() {
        $this->_db = new Client('mongodb://localhost', [
            'username' => 'root',
            'password' => '123456',
        ]);
        $this->_collection = $this->_db->selectCollection("freechat", "chat_user_token");
    }

    public static function getInstance() {
        if (!(self::$_instance instanceof self)) {
            self::$_instance = new self();
        }
        return self::$_instance;
    }

    public function getUserByToken($token) {
        $query = array('_id' => "$token");
        $tokenUsers = $this->_collection->find($query);
        foreach ($tokenUsers as $tokenUser) {
            $user = $tokenUser->user;
            file_put_contents('../logs/log_' . date("j.n.Y") . '.log', 'query db get user by token: ' . $user . "\n", FILE_APPEND);
            if ($user)
                return $user;
        };
        return null;
    }
}
?>
