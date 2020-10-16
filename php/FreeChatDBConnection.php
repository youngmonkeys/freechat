<?php
include('FreeChatConfig.php');
require 'phpmongodb/vendor/autoload.php';
use MongoDB\Client;

class FreeChatDBConnection {
    private $_db;
    private $_collection;
    private static $_instance;

    private function __construct() {
        $this->_db = new Client(FreeChatConfig::$host, [
            'username' => FreeChatConfig::$username,
            'password' => FreeChatConfig::$password,
        ]);
        $this->_collection = $this->_db->selectCollection(FreeChatConfig::$databaseName, FreeChatConfig::$collectionName);
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
            file_put_contents(FreeChatConfig::$logInfo.'log_' . date("j.n.Y") . '.log', 'query db get user by token: ' . $user . "\n", FILE_APPEND);
            if ($user)
                return $user;
        };
        return null;
    }
}
?>
