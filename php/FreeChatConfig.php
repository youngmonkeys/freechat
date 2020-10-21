<?php
class FreeChatConfig {
    private $path_config = 'freechat.ini';

    private $db_host;
    private $db_username;
    private $db_password;
    private $db_databaseName;
    private $db_collectionName;
    private $app_log_info;
    private $app_repo_path_file;

    private static $_instance;

    private function __construct() {
        $ini_config = parse_ini_file($this->path_config, false);
        $this->db_host = $ini_config['db_host'];
        $this->db_username = $ini_config['db_username'];
        $this->db_password = $ini_config['db_password'];
        $this->db_databaseName = $ini_config['db_databaseName'];
        $this->db_collectionName = $ini_config['db_collectionName'];
        $this->app_log_info = $ini_config['app_log_info'];
        $this->app_repo_path_file = $ini_config['app_repo_path_file'];
    }

    public static function getInstance() {
        if (!(self::$_instance instanceof self)) {
            self::$_instance = new self();
        }
        return self::$_instance;
    }

    public function getDbHost() {
        return $this->db_host;
    }

    public function getDbUsername() {
        return $this->db_username;
    }

    public function getDbPassword() {
        return $this->db_password;
    }

    public function getDbDatabaseName() {
        return $this->db_databaseName;
    }

    public function getDbCollectionName() {
        return $this->db_collectionName;
    }

    public function getAppLogInfo() {
        return $this->app_log_info;
    }

    public function getAppRepoPathFile() {
        return $this->app_repo_path_file;
    }
}
?>
