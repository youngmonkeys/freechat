# PHP Introduction install:
# 1. Install:
- php install version 7.4.10 (Thread Safe (NTS)) 
# 2. Update php setting: php.ini:
- extension_dir = “ext”
- extension=php_curl.dll
- extension=php_gd2.dll
- extension=php_mysqli.dll
- extension=php_soap.dll
- extension=curl
- extension=mongodb
- extension=openssl
- display_errors = On
- display_startup_errors = Off
- log_errors = On
- file_uploads = On
- upload_tmp_dir = "c:/php/tmp"
- upload_max_filesize = 2M
- max_file_uploads = 20
* refer file php.ini attach
# 3. Update config Apache24: httpd.conf: 
<FilesMatch \.php$>
  LoadModule php7_module "c:/php/php7apache2_4.dll"
  SetHandler application/x-httpd-php
</FilesMatch>

# configure the path to php.ini
PHPIniDir "C:/php"

# Mongodb Introduction Install: refer https://docs.mongodb.com/php-library/master/tutorial/install-php-library/
# 1. Install: download and install version mongodb version: mongodb-1.7.4
- sudo pecl install mongodb
- config in php.ini:
	+ extension=mongodb.so
- Download and Install Composer for you and set up your PATH environment variable so you can simply call composer from any directory.
	+ https://getcomposer.org/download/
- run command after install Composer success todo generate autoload.php in vendor folder, composer.json, composer.lock, 
  autoload.php so to use autoload all class dependency in php: 
	+ composer require mongodb/mongodb